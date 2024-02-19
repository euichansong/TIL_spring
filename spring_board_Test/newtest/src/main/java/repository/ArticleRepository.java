package repository;

import entity.Article;
import entity.ArticleCategory;
import entity.Member;
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
