package root.dongmin.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication //애플리케이션 시작점(main)
@EnableJpaAuditing
public class SpringbootDeveloperApplication {
    public static void main(String[] args){
        SpringApplication.run(SpringbootDeveloperApplication.class,args);
    }
}
