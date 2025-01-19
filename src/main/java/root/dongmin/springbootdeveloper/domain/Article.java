package root.dongmin.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
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

    protected Article(){
    }//이것도 @NoArgsConstructor 사용하면 같은효과 낼수 있다.

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


