package service;


import common.ResourceNotFoundException;
import domain.dto.request.comment.CommentRequestDto;
import domain.dto.response.comment.CommentResponseDto;
import domain.entity.Article;
import domain.entity.Comment;
import domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ArticleRepository;
import repository.CommentRepository;
import repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CommentService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto write(Member member, CommentRequestDto dto, Long articleId){
        // articleId로 게시글 주인 찾고 -> member.getId로 댓글작성자 찾고 -> comment 엔티티 객체에 정보 기입 -> save
        Member commentWriter = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member id", String.valueOf(member.getId())));
        Article findArticle = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article", "Article id", String.valueOf(articleId)));
        Comment comment = CommentRequestDto.ofEntity(dto);
        comment.setArticle(findArticle);
        comment.setMember(commentWriter);
        findArticle.addComment(); //댓글 수 1 추가
        commentRepository.save(comment);
        return CommentResponseDto.fromEntity(comment);
    }
}
