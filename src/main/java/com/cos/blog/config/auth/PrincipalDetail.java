package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고, 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다.
@Getter
public class PrincipalDetail implements UserDetails{

    private User user; // 콤포지션 (객체를 품고있다.)

    public PrincipalDetail(User user){
        this.user = user;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지를 리턴한다. (true : 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않았는지를 리턴한다. (true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴한다. (true : 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화(사용가능) 인지를 리턴한다(true:활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collectors = new ArrayList<>(); // ArrayList의 부모에 Collection이 있기 때문에 사용
//        collectors.add(new GrantedAuthority() { // 익명 클래스가 만들어지고
//            @Override
//            public String getAuthority() { // 추상메소드가 오버라이딩됨
//                return "ROLE_"+user.getRole(); // 스프링의 규칙이므로 꼭 넣어주어야 한다. ROLE_USER
//            }
//        });
//
//        return collectors;
//    }

    // 위와 동일하지만 람다 사용
    // 계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어 루프를 돌아야 하는데 우리는 한개만)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {return "ROLE_"+user.getRole();});

        return collectors;
    }
}
