package com.example.security1.config.auth;

import com.example.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 시큐리티가 /login으로 들어오는 요청을 낚아채서 로그인을 진행시킨다
 * 로그인 진행이 완료가 되면, 시큐리티 session을 만들어준다(같은 세션 공간인데 key값으로 구분)
 * (key값 = Security ContextHolder)
 * <p>
 * 시큐리티 session에 들어갈 수 있는 객체가 정해져있다
 * -> Authentication 타입 객체
 * Authentication 타입 객체 안에, User 정보가 있는데, User도 객체가 정해져있다
 * -> UserDetails 타입 객체
 * <p>
 * Security Session -> Authentication -> UserDetails
 */


public class PrincipalDetails implements UserDetails {
    private User user; // 컴포지

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    // 해당 User의 권한을 리턴하는 곳
    // collection 타입으로 맞춰줘야 한다
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 우리 사이트 1년동안 로그인 안하면 휴면계정으로 하기로 함
        // 현재시간 - 로그인시간 => 1년 초과하면 return false
        return true;
    }
}
