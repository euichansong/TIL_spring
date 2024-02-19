package newtest.newtest.service;

import lombok.RequiredArgsConstructor;
import newtest.newtest.repository.ArticleRepository;
import newtest.newtest.repository.CommentRepository;
import newtest.newtest.repository.LikesRepository;
import newtest.newtest.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;


}
