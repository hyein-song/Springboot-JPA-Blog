package com.cos.blog.config;

import com.cos.blog.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는것

@Configuration // 빈 등록
@EnableWebSecurity // 시큐리티 필터를 추가 = 스프링 시큐리티가 활성화 되어 있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoderPW(){
        return new BCryptPasswordEncoder(); // 빈이 되었으니 스프링이 관리
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .csrf().disable() // csrf 토큰 비활성화 (테스트 시 걸어두는게 좋음)
            .authorizeRequests()
                .antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/auth/loginForm"); // 인증이 필요한 곳으로 가면 로그인 페이지 띄워줌
    }
}
