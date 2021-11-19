package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){ // json이니까 RequestBody 써주기
        System.out.println("UserApiController : save 호출됨");
        int result = userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),result);
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
