package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 서비스가 필요한 이유
// 1. transaction 관리
// 2. 서비스의 의미를 알아야 한다.
//EX) 송금 서비스
//    1) 보내는 사람의 금액 update() - Commit
//    2) 받는 사람의 금액 update() - Commit
//    홍길동이 임꺽정에게 2만원을 보낸다면 홍길동 잔고는 -20000, 임꺽정 잔고는 +20000으로 변경해야 한다. (2개의 트랜잭션이 발생해야함)
//
//=> Service를 통해 1), 2)와 같은 각각의 트랜잭션을 하나의 트랜잭션으로 묶어서 서비스화 할 수 있다.
//     Service객체 내 @Transactional 어노테이션을 통해 여러 트랜잭션을 하나로 묶을 수 있다.


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User 회원찾기(String username){
       User user = userRepository.findByUsername(username).orElseGet(()->{
           return new User();
       });
       return user;
    }

    @Transactional
    public int 회원가입(User user){
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword); // 해시
            user.setPassword(encPassword);
            user.setRole(RoleType.USER);
            try{
                userRepository.save(user); //ConstraintViolationException
                return 1;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("UserService : 회원가입()" + e.getMessage());
            return -1;
        }


    }
    
    @Transactional
    public void 회원수정(User user){
        // 수정시에는 영속성 컨텍스트에 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
        // select를 해서 User 오브젝트를 DB로 부터 가져와서 영속화를 한다.
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌

        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });

        // validation check
        if (persistance.getOauth() == null || persistance.getOauth().equals("")){
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }


        //회원 수정 함수 종료 = 서비스 종료 = 트랜잭션 종료 = 커밋이 자동으로 된다.
        // 영속화된 퍼시스터스 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.

    }
    
    
//
//    @Transactional(readOnly = true) // Select 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료 (정합성 유지)
//    public User 로그인(User user){
//        return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
//
//    }


}
