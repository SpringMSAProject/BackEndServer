package msa.project.monologicserver.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.application.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/ex")
    public String ex(){
        return "테스트";
    }

    @GetMapping("/insert")
    public String insert(){
        memberService.setAnyMember();
        return "insert";
    }
}
