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

    @PostMapping("/read")
    @Operation(summary = "전체 조회", description = "Swagger Test 회원 전체 조회")
    public SingleResult<List<MemberResponseDto>> getAllMember() {
        return responseService.getSingleResult(memberService.getAllMember(), CommonMessage.USER_SELECT_SUCCESS);
    }

    @PostMapping("/read/{id}")
    @Operation(summary = "조회", description = "swagger Test 회원 조회")
    public SingleResult<MemberResponseDto> getMember(@PathVariable Long id) {
        return responseService.getSingleResult(memberService.getMemberById(id), CommonMessage.USER_SELECT_SUCCESS);
    }

    @PostMapping("/register")
    @Operation(summary = "회원 등록", description = "swagger Test 회원 등록")
    public SingleResult<MemberResponseDto> registerMember(@RequestBody MemberRequestDto memberRequestDto) {
        return responseService.getSingleResult(memberService.registerMember(memberRequestDto), CommonMessage.USER_CREATE_SUCCESS);
    }

    @PostMapping("/update/{id}")
    @Operation(summary = "수정", description = "swagger Test 회원 수정")
    public CommonResult update(@PathVariable("id") Long id, @RequestBody MemberRequestDto memberRequestDto) {
        memberService.updateMember(id, memberRequestDto);
        return responseService.getSuccessResult(CommonMessage.USER_UPDATE_SUCCESS);
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "삭제", description = "swagger Test 회원 삭제(삭제 시 UseYn: 0(사용) -> 9(사용 안함)로 변경)")
    public CommonResult changeUseYnWhenDeleted(@PathVariable("id") Long id) {
        memberService.changeUseYnWhenDeleted(id);
        return responseService.getSuccessResult(CommonMessage.USER_DELETE_SUCCESS);
    }


}
