package root.dongmin.springbootdeveloper.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Table(name = "`user`")

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", updatable = false)
    private Long id;

    @Column(name="email",nullable = false, unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Builder
    public User(String email, String password, String auth){
        this.email = email;
        this.password = password;
    }

    public User(){

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user")); // List 가 Collection 을 상속받은 구조
    }

    @Override
    public String getUsername(){ // 반드시 unique
        return email;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired(){
        // 만료 되었는지 확인
        return true; // -> 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠겼는지 확인
        return true; // -> 잠금되지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인
        return true; // -> 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        // 계정 사용 가능 여부 반환
        return true; // -> 사용가능
    }

}
