package root.dongmin.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) //매개변수가 없는 기본 생성자를 protected 로 만듬(final 제외)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id" , updatable= false)
    private Long id;

    @Column(name= "user_id", nullable = false, unique = true)
    private Long userid;

    @Column(name ="refresh_token" , nullable = false)
    private String refreshToken;

    public RefreshToken(Long userid, String refreshToken) {
        this.userid = userid;
        this.refreshToken=refreshToken;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
        return this;
    }
}
