package root.dongmin.springbootdeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.dongmin.springbootdeveloper.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email); //이메일로 사용자 정보를 가져옴
}
