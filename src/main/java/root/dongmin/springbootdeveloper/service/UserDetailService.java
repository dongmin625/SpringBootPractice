package root.dongmin.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import root.dongmin.springbootdeveloper.domain.User;
import root.dongmin.springbootdeveloper.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 사용자 이름(이메일) 로 사용자의 정보를 가져옴
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        // 이메일로 사용자 검색
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));  // UsernameNotFoundException으로 변경
    }
}
