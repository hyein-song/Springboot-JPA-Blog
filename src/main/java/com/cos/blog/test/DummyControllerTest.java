package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입 (DI)
    private UserRepository userRepository;

    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //http://localhost:8000/blog/dummy/user?page=0 (page로 넘길 수 있음음)
   @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){// 2건식 들고오기
        //Page<User> users = userRepository.findAll(pageable); // 밑에 pageable 설정도 같이 넘어옴
        //List<User> users = userRepository.findAll(pageable).getContent(); // user 정보만 넘어옴
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();
        return users;
    }

    // {id} 주소로 파라미터를 전달 받을 수 있음
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        // findById의 return은 Optional
        // 찾는게 없으면 프로그램에 문제가 생길 수 있다.

        // get : 그냥 return
        // orElseGet:있으면 return하고 없으면 새로운  User를 생성해서 return 해라
        // 전부 null 리턴
//        User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//            @Override
//            public User get() {
//                return new User();
//            }
//        });

        // 위의것들 대신 throws를 선호
//        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//            @Override
//            public IllegalArgumentException get() {
//                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//            }
//        });

        // 위의 코드 람다로 변경
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저는 없습니다. id : " + id));

        // return 부분에서 user 객체는 java object다
        // 요청 : 웹브라우저
        // 웹브라우저는 자바 객체를 이해하지 못하니까 웹 브라우저가 이해할수 있는 데이터로 변환해야 한다. 대표적으로 json
        // springboot는 MessageConverter라는 애가 응답시에 자동 작동함
        // 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        // user 오브젝트를 json으로 변황해서 브라우저에게 전달해 줌
        return user;

    }


    @PostMapping("dummy/join")
    public String join(User user){
        System.out.println("id : "+ user.getId());
        System.out.println("username : "+ user.getUsername());
        System.out.println("password : "+ user.getPassword());
        System.out.println("email : "+ user.getEmail());
        System.out.println("role : "+ user.getRole()); //그냥 만들면 null값이 들어감
        System.out.println("createDate : "+ user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }
}
