package controller;


import common.AlreadyExistsException;
import common.ResourceNotFoundException;
import domain.dto.request.article.ArticleWriteDto;
import domain.dto.response.article.ArticleResponseDetailDto;
import domain.dto.response.article.ArticleResponseListDto;
import domain.dto.response.article.ArticleResponseWriteDto;
import domain.entity.Member;
import domain.enum_class.ArticleCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import service.ArticleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 게시판 글쓰기
     */
    @PostMapping("/write/{category}")
    public ResponseEntity<ArticleResponseWriteDto> write(
            @PathVariable("category") String category,
            @RequestBody ArticleWriteDto articleWriteDto,
            @AuthenticationPrincipal Member member) {
        Thread currentThread = Thread.currentThread();
//        log.info("현재 실행 중인 스레드: " + currentThread.getName());
        ArticleResponseWriteDto saveArticle = articleService.write(category, articleWriteDto, member); //저장정보 리턴
        return ResponseEntity.status(HttpStatus.OK).body(saveArticle);
    }

    /**
     * 게시판 전체 목록 (홈화면)
     */
    @GetMapping("/list")
    public ResponseEntity<Page<ArticleResponseListDto>> articleList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
            ){
        Page<ArticleResponseListDto> allArticles = articleService.getAllArticles(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allArticles);
    }


    /**
     * 게시판 카테고리별로 불러오기
     */
    @GetMapping("/list/{category}")
    public ResponseEntity<List<ArticleResponseListDto>> articleListByCategory(@PathVariable("category") String category) {
        ArticleCategory articleCategory = ArticleCategory.of(category);
        List<ArticleResponseListDto> responseArticleList = articleService.articleListByCategory(articleCategory);
        return ResponseEntity.status(HttpStatus.OK).body(responseArticleList);
    }

    /**
     * 제목, 글쓴이, 본문 키워드로 검색
     */
//    @GetMapping("/search")
//    public ResponseEntity<List<ArticleResponseListDto>> search(
//            @RequestParam(required = false, name = "title") String title, // 해당 파라미터가 필수가 아니어도 괜찮음
//            @RequestParam(required = false, name = "writer") String writer,
//            @RequestParam(required = false, name = "content") String body) {
//        SearchData searchData = SearchData.createSearchData(title, writer, body);
//        List<ArticleResponseListDto> searchDto = articleService.search(searchData);
//        return ResponseEntity.status(HttpStatus.OK).body(searchDto);
//    }

    /**
     * 게시글 하나를 클릭할 때 (리턴 값으로 게시글 상세 정보 줘야함)
     */
    @GetMapping("/detail/{articleId}")
    public ResponseEntity<ArticleResponseDetailDto> detail(
            @PathVariable("articleId") Long articleId) {
        ArticleResponseDetailDto findArticleDto = articleService.detail(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(findArticleDto);
    }

    /**
     * 게시글 수정하기
     */
    @PostMapping("/edit/{articleId}")
    public ResponseEntity<ArticleResponseDetailDto> edit(
            @AuthenticationPrincipal Member member,
            @PathVariable("articleId") Long articleId,
            @RequestBody ArticleWriteDto dto) {
        ArticleResponseDetailDto editArticle = articleService.edit(member, articleId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(editArticle);
    }

    @GetMapping("/likes/{articleId}")
    // 게시물에는 한 번만 추천을 누를 수 있음.
    public ResponseEntity<?> likes(
            @AuthenticationPrincipal Member member,
            @PathVariable("articleId") Long articleId) {
        try {
            ArticleResponseDetailDto likes = articleService.likes(member, articleId);
            return ResponseEntity.status(HttpStatus.OK).body(likes);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 추천 취소
    @GetMapping("/likes/undo/{articleId}")
    public ResponseEntity<?> likes_undo(
            @AuthenticationPrincipal Member member,
            @PathVariable("articleId") Long articleId) {
        ArticleResponseDetailDto articleResponseDetailDto = articleService.likes_undo(member, articleId);
        return ResponseEntity.status(HttpStatus.OK).body(articleResponseDetailDto);
    }

//    // 게시글 정렬 (댓글 갯수 순, 추천 순) // 사용자인증인가 없이도 동작 가능
//    @GetMapping("/sort/{type}")
//    public ResponseEntity<List<ArticleResponseListDto>> sorting(@PathVariable("type") String type){
//        List<ArticleResponseListDto> sortingRes = articleService.sorting(type);
//        return ResponseEntity.status(HttpStatus.OK).body(sortingRes);
//    }

}
