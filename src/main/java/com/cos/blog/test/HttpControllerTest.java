package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 : HTML 파일 응답 : @Controller
// 사용자가 요청 : 응답(Data)
@RestController
public class HttpControllerTest {

    @GetMapping("http/lombok")
    public String lombokTest(){
        Member m = Member.builder().username("ssar1").build();
        System.out.println(m.getId());
        m.setId(5000);
        System.out.println(m.getId());
        return "lombok test 완료";
    }

    @GetMapping("http/get")
    public String getTest(Member m){
        return "get 요청"+m.getId()+m.getUsername();
    }

//    @PostMapping("http/post")
//    public String postTest(@RequestBody String text){
//        return "post 요청 " + text;
//    }

    @PostMapping("http/post")
    public String postTest(@RequestBody Member m){
        return "post 요청 " + m.getId()+m.getUsername() + m.getPassword() + m.getEmail();
    }

    @PutMapping("http/put")
    public String putTest(@RequestBody Member m){
        return "put 요청 "+ m.getId() +m.getUsername() + m.getPassword() + m.getEmail();
    }

    @DeleteMapping("http/delete")
    public String deleteTest(){
        return "delete 요청";
    }

}
