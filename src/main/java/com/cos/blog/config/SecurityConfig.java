package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encoderPWD(){
        return new BCryptPasswordEncoder(); // 빈이 되었으니 스프링이 관리
    }

    // 시큐리티가 대신 로그인 해주는데 비밀번호를 가로채기 하는데
    // 해당 비밀번호가 뭘로 해쉬가 되어 회원가입이 되었는지를 알아야 동일한 해쉬로 암호화해서 비교 가능


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD()); // principalDetailService를 넣어줘서 패스워드를 인코딩해서 알아서 비교해줌
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
                .loginPage("/auth/loginForm") // 인증이 필요한 곳으로 가면 로그인 페이지 띄워줌
                .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
                .defaultSuccessUrl("/"); // 정상일 때 이동
    }
}
// configure(HttpSecurity http)의 loginProcessingUrl에 오는 요청을 가로챈다.
// 가로채서 PrincipalDetailService에 있는 loadUserByUsername로 던진다.
// username에 들어온걸 비교해서 principal에 넣고 return해주는데
// 그 전에  auth.userDetailsService를 통해서 패스워드 비교를 한번 더 함
// 아이디와 비밀번호가 모두 동일하면 PrincipalDetails로 감싸져서 스프링 시큐리티 세션에 저장이 됨
// 그리고 / 로 이동함
