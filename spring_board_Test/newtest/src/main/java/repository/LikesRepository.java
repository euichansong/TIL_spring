package repository;

import entity.Article;
import entity.Likes;
import entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberAndArticle(Member member, Article article);
}
