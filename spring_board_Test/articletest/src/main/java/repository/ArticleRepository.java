package repository;

import domain.entity.Article;
import domain.entity.Member;
import domain.enum_class.ArticleCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByArticleCategory(ArticleCategory articleCategory);
    List<Article> findAllByMember(Member member);

    Page<Article> findAll(Pageable pageable);

    // articleId로 검색 : 게시글 상세보기
    @Query(value = "SELECT b FROM Article b JOIN FETCH b.member WHERE b.id = :articleId")
    Optional<Article> findByArticleId(@Param("articleId") Long articleId);

    @Query("SELECT b FROM Article b JOIN FETCH b.member WHERE b.title LIKE %:title%")
    List<Article> findAllTitleContaining(@Param("title")String title);

    @Query("SELECT b FROM Article b JOIN FETCH b.member WHERE b.member.nickname LIKE %:writer%")
    List<Article> findAllWriterContaining(@Param("writer")String writer);

    @Query("SELECT b FROM Article b JOIN FETCH b.member WHERE b.content LIKE %:content%")
    List<Article> findAllBodyContaining(@Param("content") String content);

    @Query("SELECT b FROM Article b JOIN FETCH b.member WHERE b.title = :title")
    List<Article> findByTitle(@Param("title")String title);


    @Query("SELECT b FROM Article b ORDER BY b.commentCnt DESC, b.modifiedDate")
    List<Article> findAllOrderByCommentCnt();

    @Query("SELECT b FROM Article b ORDER BY b.likesCnt DESC, b.modifiedDate")
    List<Article> findAllOrderByRecommendCnt();
}
