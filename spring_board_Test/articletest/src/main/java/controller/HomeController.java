package controller;


import domain.dto.request.member.MemberLoginDto;
import domain.dto.request.member.MemberTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final MemberService memberService;

    /**
     * 로그인 절차
     */
    @PostMapping("/login")
    public ResponseEntity<MemberTokenDto> login(@RequestBody MemberLoginDto memberLoginDto) {
        MemberTokenDto memberTokenDto = memberService.login(memberLoginDto);
        return ResponseEntity.status(HttpStatus.OK).header(memberTokenDto.getToken()).body(memberTokenDto);
    }


}
