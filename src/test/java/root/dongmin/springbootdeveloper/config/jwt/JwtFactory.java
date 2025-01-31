package root.dongmin.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter
public class JwtFactory {
    private String subject = "test@email.com";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = emptyMap();

    // 빌더 패턴을 사용해 설정이 필요한 데이터만 선택 설정
    @Builder
    public JwtFactory(String subject, Date issuedAt, Date expiration,
                      Map<String,Object> claims){
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims != null ? claims : this.claims;
    }

    public static JwtFactory withDefaultValues(){
        return JwtFactory.builder().build();
    }

    // jjwt 라이브러리를 사용해 JWT 토큰 생성
    public String createToken(JwtProperties jwtProperties){
        return Jwts.builder()
                .setSubject(subject) //토큰의 주제를 설정
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더에 JWT 타입을 명시
                .setIssuer(jwtProperties.getIssuer()) // 발급자 정보설정
                .setIssuedAt(issuedAt) // 토큰 발급시간 설정
                .setExpiration(expiration) // 토큰 만료시간 설정
                .addClaims(claims) // 추가적 클레임 설정
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // HS256 알고리즘 서명
                .compact(); //JWT 토큰을 문자열로 변환하여 반환
    }
}
