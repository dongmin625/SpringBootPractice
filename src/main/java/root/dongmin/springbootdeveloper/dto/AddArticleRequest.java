package root.dongmin.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import root.dongmin.springbootdeveloper.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    public Article toEntity(){ // 생성자를 이용해 객체 생성
        return Article.builder() //빌더 패턴을 사용
                .title(title) //필드 값 설정
                .content(content) //필드 값 설정
                .build(); // 객체 생성
    }
}
