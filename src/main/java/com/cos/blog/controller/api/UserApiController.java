package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user){ // json이니까 RequestBody 써주기
        System.out.println("UserApiController : save 호출됨");
        // 실제로 insert 하고 아래에서 return
        return new ResponseDto<Integer>(HttpStatus.OK,1);
    }

}
