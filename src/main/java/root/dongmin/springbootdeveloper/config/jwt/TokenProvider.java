package root.dongmin.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import root.dongmin.springbootdeveloper.domain.User;


import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()) ,user);
    }

    // JWT 토큰 생성 메서드
    private String makeToken(Date expiry, User user){
        Date now = new Date();

        return Jwts.builder() // JWT 토큰 생성
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE) // JWT 헤더 타입 설정 (JWT)
                .setIssuer(jwtProperties.getIssuer()) // 발급자 정보설정
                .setIssuedAt(now) // 발급 일시 설정
                .setExpiration(expiry) // 만료 시간 설정
                .setSubject(user.getEmail()) // 토큰의 주체를 설정
                .claim("id",user.getId()) // 토큰에 담고 싶은 추가정보(claim 으로 id 값을 담음)
                .signWith(SignatureAlgorithm.ES256, jwtProperties.getSecretKey()) // 서명추가 ES256 알고리즘으로 암호화
                .compact(); // 문자열로 압축
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 복호화
                    .parseClaimsJws(token);

            return true;
        } catch(Exception e){ // 복호화 과정에서 오류나면 유효하지 않은키
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token); // Claim 추출
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new
                SimpleGrantedAuthority("ROLE_USER")); // 권한부여
        //사용자 정보와 권한을 담은 객체를 생성하고, 그 객체에 JWT 토큰을 연결해서 Spring Security 의 인증 시스템에 전달하는 역할
        return new UsernamePasswordAuthenticationToken(new org.springframework.
                security.core.userdetails.User(claims.getSubject(),"",authorities ), token, authorities);
    }

    // 토큰 기반으로 유저 ID 를 가져오는 메서드
    public Long getUserId(String token){
        Claims claims = getClaims(token); // 토큰에 있는 정보를 가져옴
        return claims.get("id",Long.class); // Claim 에서 "id" 라는 키에 해당하는 값을 Long 타입으로 가져옴
    }

    private Claims getClaims(String token){
        return Jwts.parser() // 클레임 조회(구문분석)
                .setSigningKey(jwtProperties.getSecretKey()) // 서명 검증
                .parseClaimsJws(token) // 토큰 파싱 서명검증 -> Claim 을 추출
                .getBody(); // 페이로드(담긴 정보) 추출
    }
}
