package repository;

import domain.entity.Article;
import domain.entity.Likes;
import domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberAndArticle(Member member, Article article);
}
