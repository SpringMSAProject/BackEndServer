package msa.project.monologicserver.api.dto.req.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequestDTO(
    @Email @NotBlank String email,
    @NotBlank String name,
    @NotBlank String nickname,
    @NotBlank String phone,
    @NotBlank String address


) {

}
