package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){ // json이니까 RequestBody 써주기
        System.out.println("UserApiController : save 호출됨");
        int result = userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),result);
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user){
        userService.회원수정(user);
        // 여기서는 트랜잭션이 종료되었지만 세션이 종료되지 않은 상태이기 때문에 세션의 값이 변경이 되어있지 않음

        //세션등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }


//    @Autowired
//    private HttpSession session;
    // 로그인은 security를 이용해 구현
//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user){ // spring에서는 Session을 매개변수로 받을 수 있다. // session을 매개변수로 받지 않고 변수로 할 수도 있다.
//        System.out.println("UserApiController : login 호출됨");
//        User principal = userService.로그인(user); // principal : 접근 주체
//
//        if(principal != null) {
//            session.setAttribute("principal",principal);
//        }
//
//        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
//
//    }

}
