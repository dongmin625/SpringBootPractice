package root.dongmin.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import root.dongmin.springbootdeveloper.service.UserDetailService;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;

    // spring security 기능 비활성화(H2console , static 경로의 경우)
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring() //특정경로 무시 + 람다 표현식으로 customize 메서드 구현
                .requestMatchers(toH2Console())
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth // 요청에 대한 권한 설정
                        .requestMatchers( // 특정 경로에 대한 권한 설정
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll() // 위 3개의 경로는 인증 없이 접근 가능
                        .anyRequest().authenticated()) // 그 외의 모든 요청은 인증 필요
                .formLogin(formLogin -> formLogin // 로그인 설정
                        .loginPage("/login") // 로그인 페이지 경로
                        .defaultSuccessUrl("/articles") // 로그인 성공시 이동 페이지
                )
                .logout(logout -> logout // 로그아웃 설정
                        .logoutSuccessUrl("/login") // 로그아웃 후 이동페이지
                        .invalidateHttpSession(true) // 로그아웃 후 Session 무효화
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider); // 비밀번호 검증
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 알고리즘을 사용해 비밀번호를 안전하게 해시
    }

}
