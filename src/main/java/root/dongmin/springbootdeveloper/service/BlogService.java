package root.dongmin.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.dongmin.springbootdeveloper.domain.Article;
import root.dongmin.springbootdeveloper.dto.AddArticleRequest;
import root.dongmin.springbootdeveloper.repository.BlogRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // 글 추가 저장 메서드
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    // 글 목록 조회 메서드
    public List<Article> findAll(){return blogRepository.findAll();}


}
