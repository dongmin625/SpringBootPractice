package root.dongmin.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.dongmin.springbootdeveloper.domain.Article;
import root.dongmin.springbootdeveloper.dto.AddArticleRequest;
import root.dongmin.springbootdeveloper.dto.ArticleResponse;
import root.dongmin.springbootdeveloper.service.BlogService;

import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body 에 객체 데이터를 JSON 형식으로 반환
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle); // 상태코드 201 Created 응답 테이블에 저장된 객체 반환
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new) //Article 타입을 ArticleResponse DTO 로 파싱
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}") //url 변수는 중괄호로 처리
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable("id") Long id){
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}") //아래처럼("id") 명시적으로 해줘야 오류 안남
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id){
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
