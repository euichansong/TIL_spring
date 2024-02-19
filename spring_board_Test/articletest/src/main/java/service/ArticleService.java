package service;

import common.AlreadyExistsException;
import common.ResourceNotFoundException;
import domain.dto.request.article.ArticleWriteDto;
import domain.dto.response.article.ArticleResponseDetailDto;
import domain.dto.response.article.ArticleResponseListDto;
import domain.dto.response.article.ArticleResponseWriteDto;
import domain.entity.Article;
import domain.entity.Likes;
import domain.entity.Member;
import domain.enum_class.ArticleCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ArticleRepository;
import repository.CommentRepository;
import repository.LikesRepository;
import repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

    // 게시글 작성하는 서비스 로직
    public ArticleResponseWriteDto write(String category, ArticleWriteDto articleWriteDto, Member member) {
        //DTO를 ENTITY로 바꾸기 -> member 객체로 회원 찾기 -> 로그인아이디로 멤버 판별 -> 글 저장
        Article article = ArticleWriteDto.applyCategory(articleWriteDto, category); //카테고리 적용까지 완료
        Member writeMember = memberRepository.findByLoginId(member.getLoginId()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member LoginId", member.getLoginId()));

//        if (category.equalsIgnoreCase("greeting") && member.getMemberRole() == MemberRole.ASSOCIATE) {
//            member.rankUp(MemberRole.REGULAR);
//        } //준회원 + 가입인사 게시판 글쓰기 => 등업
        article.setMappingMember(writeMember); // 양방향 연관관계 적용
        Article saveArticle = articleRepository.save(article);
        return ArticleResponseWriteDto.fromEntity(saveArticle, writeMember.getNickname());
    }

    // 전체 게시물 조회(홈화면)
    public Page<ArticleResponseListDto> getAllArticles(Pageable pageable){
        Page<Article> articleList = articleRepository.findAll(pageable);
        List<ArticleResponseListDto> returnList = articleList.stream().map(ArticleResponseListDto::fromEntity).toList();
        return new PageImpl<>(returnList, pageable, articleList.getTotalElements());
    }

    // 카테고리로 게시물 조회하는 서비스 로직
    public List<ArticleResponseListDto> articleListByCategory(ArticleCategory category) {
        List<Article> articles = articleRepository.findByArticleCategory(category);
        return articles.stream().map(ArticleResponseListDto::fromEntity).toList();
    }

//    //검색 서비스 로직
//    public List<ArticleResponseListDto> search(SearchData searchData) {
//        List<Article> articleList = new ArrayList<>();
//        if (searchData.getTitle() != null) {
//            articleList = articleRepository.findAllTitleContaining(searchData.getTitle());
//        } else if (searchData.getWriter() != null) {
//            articleList = articleRepository.findAllWriterContaining(searchData.getWriter());
//        } else if (searchData.getContent() != null) {
//            articleList = articleRepository.findAllBodyContaining(searchData.getContent());
//        }
//        return articleList.stream().map(ArticleResponseListDto::fromEntity).collect(Collectors.toList());
//    }

    // 게시글 상세보기 ( 게시글 하나 클릭했을 때)
    public ArticleResponseDetailDto detail(Long articleId) {
        Article findArticle = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article", "Article Id", String.valueOf(articleId)));
        return ArticleResponseDetailDto.fromEntity(findArticle);
    }

    // 게시글 수정하기
    public ArticleResponseDetailDto edit(Member member, Long articleId, ArticleWriteDto dto) {

        Article article = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article", "Article Id", String.valueOf(articleId)));
        //어찌 처리할꼬? 글쓴이와 동일하지 않으면 수정이 안되는걸루 그냥 가자!
        if (Objects.equals(article.getMember().getLoginId(), member.getLoginId())) { // 게시글 작성자와 member가 동일인이여야 수정 진행
            article.update(dto.getTitle(), dto.getContent());
        }
        return ArticleResponseDetailDto.fromEntity(article);
    }

    // 게시글 추천 누르기
    public ArticleResponseDetailDto likes(Member member, Long articleId) {
        // 추천 누른 게시글 가져오기 -> 게시글 추천 +1 -> 누가 눌렀는지 정보 가져오고 -> likes 테이블에 기입
        Article findArticle = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article", "Article Id", String.valueOf(articleId))
        );
        likesRepository.findByMemberAndArticle(member, findArticle).ifPresent(
                likes -> {
                    throw new AlreadyExistsException(member.getId());
                    //리턴값 처리하기
                }
        );

        Likes likes = new Likes(findArticle, member);
        likesRepository.save(likes);
        findArticle.plusLikes();
        return ArticleResponseDetailDto.fromEntity(findArticle);
    }

    // 게시글 추천 취소
    public ArticleResponseDetailDto likes_undo(Member member, Long articleId) {
        // 게시글 가져오기 -> 추천 누른 사람 중에 member가 있는지 확인 -> 있다면 1 감소, 없으면 그냥 리턴
        Article findArticle = articleRepository.findByArticleId(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article", "Article Id", String.valueOf(articleId))
        );
        Optional<Likes> findLikes = likesRepository.findByMemberAndArticle(member, findArticle);
        if (findLikes.isPresent()) {
            findArticle.minusLikes();
        }
        return ArticleResponseDetailDto.fromEntity(findArticle);
    }

//    // 게시글 정렬(댓글수,추천수) - 처음에 list할땐 시간순으로 보여줌!
//    public List<ArticleResponseListDto> sorting(String type){
//        List<Article> articleList = new ArrayList<>();
//        if(Objects.equals(type, "commentCnt")){
//            articleList = articleRepository.findAllOrderByCommentCnt();
//        } else if (Objects.equals(type, "likesCnt")) {
//            articleList = articleRepository.findAllOrderByLikesCnt();
//        }
//        return articleList.stream().map(ArticleResponseListDto::fromEntity).collect(Collectors.toList());
//    }
}
