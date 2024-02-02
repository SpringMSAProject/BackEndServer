package msa.project.monologicserver.presentation.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.domain.member.entity.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "회원 요청 정보")
public class MemberRequestDto {

    @NotBlank(message = "OAuth ID는 필수입니다.")
    @Schema(description = "OAuth ID", example = "OAuthID")
    private String oauthId;

    @NotBlank(message = "이메일은 필수입니다.")
    @Schema(description = "이메일", example = "test@naver.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Schema(description = "OAuth ID", example = "password")
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

    public Member toEntity() {
        return Member.builder()
                .oauthId(oauthId)
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .address(address)
                .dealLocation(dealLocation)
                .phoneNo(phoneNo)
                .role(role)
                .build();
    }
}