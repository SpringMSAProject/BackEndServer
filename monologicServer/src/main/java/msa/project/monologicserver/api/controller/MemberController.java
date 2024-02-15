package msa.project.monologicserver.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.res.member.MemberDataResponseDto;
import msa.project.monologicserver.application.MemberService;
import msa.project.monologicserver.global.ApiResponse;
import msa.project.monologicserver.api.dto.req.member.MemberJoinRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "회원", description = "swagger Test 회원")
public class MemberController {

    private final MemberService memberService;

    //Create
    @PostMapping("/")
    @Operation(summary = "C", description = "회원 등록")
    public ApiResponse<String> create(@RequestBody MemberJoinRequestDTO joinRequestDTO){
        return ApiResponse.success(memberService.createMember(joinRequestDTO));
    }

    //Read - once
    @GetMapping("/{memberId}")
    @Operation(summary = "R", description = "회원 단건 조회")
    public ApiResponse<MemberDataResponseDto> read(@PathVariable String memberId){
        return ApiResponse.success(memberService.readMember(memberId));
    }

    //Read - all
    @GetMapping("/")
    @Operation(summary = "R", description = "회원 목록 조회")
    public ApiResponse<List<MemberDataResponseDto>> read(){
        return ApiResponse.success(memberService.readAllMember());
    }
    //Update
    @PutMapping("/{memberId}")
    @Operation(summary = "U", description = "회원 목록 조회")
    public ApiResponse<String> update(@PathVariable String memberId){
        return ApiResponse.success(memberService.updateMember(memberId));
    }
    //Delete

    @DeleteMapping("/{memberId}")
    @Operation(summary = "D", description = "회원 삭제")
    public ApiResponse<String> delete(@PathVariable String memberId){
        return ApiResponse.success(memberService.deleteMember(memberId));
    }


}
