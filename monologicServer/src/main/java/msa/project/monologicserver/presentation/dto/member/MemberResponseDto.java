package msa.project.monologicserver.presentation.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "회원 응답 정보")
public class MemberResponseDto {

    @Schema(description = "회원 번호", example = "1")
    private Long memberId;

    @Schema(description = "OAuth ID", example = "OAuthID")
    private String oauthId;

    @Schema(description = "이메일", example = "test@naver.com")
    private String email;

    @Schema(description = "비밀 번호", example = "password")
    private String password;

    @Schema(description = "이름", example = "tester")
    private String name;

    @Schema(description = "닉네임", example = "nickTest")
    private String nickname;

    @Schema(description = "주소", example = "서울시")
    private String address;

    @Schema(description = "주 거래 지역", example = "강남구")
    private String dealLocation;

    @Schema(description = "전화번호", example = "01011112222")
    private String phoneNo;

    @Schema(description = "권한", example = "Admin")
    private String role;

    @Schema(description = "생성일", example = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdDt;

    @Schema(description = "수정일", example = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDt;

    @Schema(description = "탈퇴 여부", example = "사용: 0, 탈퇴: 9")
    private int useYn;


    @Builder
    public MemberResponseDto(Long memberId, String oauthId, String email, String password,
                             String name, String nickname, String address, String dealLocation,
                             String phoneNo, String role, LocalDateTime createdDt, LocalDateTime modifiedDt, int useYn) {
        this.memberId = memberId;
        this.oauthId = oauthId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.dealLocation = dealLocation;
        this.phoneNo = phoneNo;
        this.role = role;
        this.createdDt = createdDt;
        this.modifiedDt = modifiedDt;
        this.useYn = useYn;
    }
}