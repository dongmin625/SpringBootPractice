package root.dongmin.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
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

    // 글 1개 조회 메서드
    public Article findById(Long id){
        return blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found:" + id));
    }

    // 글 삭제 메서드
    public void delete(Long id){
        blogRepository.deleteById(id);
    }
}
