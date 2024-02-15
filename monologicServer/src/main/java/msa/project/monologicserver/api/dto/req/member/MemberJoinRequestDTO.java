package msa.project.monologicserver.api.dto.req.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.memberprofile.MemberProfile;

public record MemberJoinRequestDTO(
    @NotBlank @Email String email,
    @NotBlank String password,
    @NotBlank String name,
    @NotBlank String nickname,
    @NotBlank String phone,
    @NotBlank String address


) {
    public Member toMember(){
        return Member.builder()
            .email(this.email)
            .password(this.password)
            .build();
    }

    public MemberProfile toMemberProfile(){
        return MemberProfile.builder()
            .name(this.name)
            .nickname(this.nickname)
            .address(this.address)
            .phone(this.phone)
            .build();
    }
}
