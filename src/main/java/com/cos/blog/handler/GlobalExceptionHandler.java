package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 Exception이 발생하면 이 class로 들어오게 해줌
@RestController
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = IllegalArgumentException.class)
//    public String handleArgumentException(IllegalArgumentException e){
//        return "<h1>" + e.getMessage() + "</h1>";
//
//    }

    // 모든 Exception
    @ExceptionHandler(value = Exception.class)
    public String handleArgumentException(Exception e){
        return "<h1>" + e.getMessage() + "</h1>";

    }

}
