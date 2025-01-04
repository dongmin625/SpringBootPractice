package root.dongmin.springbootdeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.dongmin.springbootdeveloper.domain.Article;

@Repository
public interface BlogRepository extends JpaRepository<Article, Long> {
}
