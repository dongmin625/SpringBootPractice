package root.dongmin.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import root.dongmin.springbootdeveloper.domain.Article;
import root.dongmin.springbootdeveloper.dto.AddArticleRequest;
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
        //model.addAttribute(참조키,실제 객체데이터) -> JSP 나 Thymeleaf 에서 사용
        return "article";
    }

    @GetMapping("/new-article")
    // id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑(id는 없을수도 있음)
    public String newArticle(@RequestParam(value = "id",required = false) Long id, Model model){
        if(id == null){ //글 id가 없다면 생성
            model.addAttribute("article",new ArticleViewResponse());
        }else{ //글 id가 있다면 조회
            Article article = blogService.findById(id);
            model.addAttribute("article",new ArticleViewResponse(article));
        }
        return "newArticle"; // newArticle.html 이라는 뷰 조회
    }

    @PostMapping("/new-article")
    public String createArticle(@ModelAttribute AddArticleRequest request) {
        blogService.save(request); // 새로운 글을 저장
        return "redirect:/articles"; // 글 목록 페이지로 리다이렉트
    }


}
