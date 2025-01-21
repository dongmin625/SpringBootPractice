package root.dongmin.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import root.dongmin.springbootdeveloper.domain.Article;
import root.dongmin.springbootdeveloper.dto.ArticleListViewResponse;
import root.dongmin.springbootdeveloper.dto.ArticleViewResponse;
import root.dongmin.springbootdeveloper.service.BlogService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model){
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList(); // 엔티티를 DTO 로 만듬
        model.addAttribute("articles",articles); //Spring MVC에서 컨트롤러가 뷰에 데이터를 전달

        return "articleList"; // articleList.html 이라는 뷰 조회
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable("id") Long id, Model model){
        Article article = blogService.findById(id);
        model.addAttribute("article" , new ArticleViewResponse(article));

        return "article";
    }
}
