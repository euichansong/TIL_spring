package newtest.newtest.repository;

import newtest.newtest.entity.Article;
import newtest.newtest.entity.Likes;
import newtest.newtest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberAndArticle(Member member, Article article);
}
