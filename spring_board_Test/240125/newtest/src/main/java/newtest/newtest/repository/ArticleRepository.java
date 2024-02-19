package newtest.newtest.repository;

import newtest.newtest.entity.Article;
import newtest.newtest.entity.ArticleCategory;
import newtest.newtest.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 특정 카테고리 글 검색
    List<Article> findByArticleCategory(ArticleCategory category);
    List<Article> findAllByMember(Member member);

    Page<Article> findAll(Pageable pageable);
    
}
