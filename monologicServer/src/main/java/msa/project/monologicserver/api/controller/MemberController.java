package msa.project.monologicserver.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.member.MemberJoinRequestDTO;
import msa.project.monologicserver.api.dto.req.member.MemberUpdateRequestDTO;
import msa.project.monologicserver.api.dto.res.member.MemberDataResponseDto;
import msa.project.monologicserver.application.MemberService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "회원", description = "swagger Test 회원")
public class MemberController {

    private final MemberService memberService;

    //Create
    @PostMapping("/")
    @Operation(summary = "C", description = "회원 등록")
    public ApiResponse<Long> create(@RequestBody MemberJoinRequestDTO joinRequestDTO) {
        return ApiResponse.success(memberService.createMember(joinRequestDTO));
    }

    //Read - once
    @GetMapping("/{memberId}")
    @Operation(summary = "R", description = "회원 단건 조회")
    public ApiResponse<MemberDataResponseDto> read(@PathVariable Long memberId) {
        return ApiResponse.success(memberService.readMember(memberId));
    }

    //Read - all
    @GetMapping("/")
    @Operation(summary = "R", description = "회원 목록 조회")
    public ApiResponse<List<MemberDataResponseDto>> readAll() {
        return ApiResponse.success(memberService.readAllMember());
    }

    //Update
    @PutMapping("/{memberId}")
    @Operation(summary = "U", description = "회원 목록 조회")
    public ApiResponse<Long> update(
        @PathVariable Long memberId,
        @RequestBody MemberUpdateRequestDTO requestDTO
    ) {
        return ApiResponse.success(memberService.updateMember(memberId, requestDTO));
    }

    //Delete

    @DeleteMapping("/{memberId}")
    @Operation(summary = "D", description = "회원 삭제")
    public ApiResponse<Long> delete(@PathVariable Long memberId) {
        return ApiResponse.success(memberService.deleteMember(memberId));
    }


}
