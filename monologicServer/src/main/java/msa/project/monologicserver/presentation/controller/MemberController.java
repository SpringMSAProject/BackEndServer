package msa.project.monologicserver.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.application.MemberService;
import msa.project.monologicserver.global.dto.CommonResult;
import msa.project.monologicserver.global.dto.ResponseService;
import msa.project.monologicserver.global.dto.SingleResult;
import msa.project.monologicserver.global.message.CommonMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "회원", description = "swagger Test 회원")
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;


}
