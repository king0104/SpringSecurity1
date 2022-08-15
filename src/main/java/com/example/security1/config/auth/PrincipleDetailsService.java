package com.example.security1.config.auth;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 .loginProcessingUrl("/login")으로 함
// "/login" 으로 요청이 오면 자동으로 UserDetailsService 타입의 빈을 조회하고 해당 빈의 loadUserByUsername 함수를 실행
// loadUserByUsername메서드의 파라미터로, 요청 시 같이 온 파라미터가 들어온다. 이때, 두 파라미터의 이름은 같아야 한다!
@Service
@RequiredArgsConstructor
public class PrincipleDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
