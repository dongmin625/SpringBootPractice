package root.dongmin.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="content",nullable = false)
    private String content;

    @Builder
    public Article(String title, String content){
        this.title = title;
        this.content = content;
    } //이렇게 쓰지않고 @AllArgsConstructor 어노테이션 으로 같은 효과를 낼수있다.

    @CreatedDate // 엔티티가 생성될때 시간 저장
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 시간 저장
    @Column(name="updated_at")
    private LocalDateTime updatedAt;


//    protected Article(){
//    }//이것도 @NoArgsConstructor 사용하면 같은효과 낼수 있다.

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }
    //위 3개의 get 메서드 들도 @Getter 어노테이션으로 대체 가능하다.

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}


