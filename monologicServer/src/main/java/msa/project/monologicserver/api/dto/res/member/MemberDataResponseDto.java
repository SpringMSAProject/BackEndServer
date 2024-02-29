package msa.project.monologicserver.api.dto.res.member;


import java.time.LocalDateTime;
import lombok.Data;
import msa.project.monologicserver.domain.member.Member;

public record MemberDataResponseDto (
    Long id,
    String email,
    String name,
    String nickname,
    String phone,
    String address,
    boolean isActivated,

    LocalDateTime createdAt,
    LocalDateTime updatedAt


){

    public static MemberDataResponseDto fromEntity(Member member) {
        return new MemberDataResponseDto(
            member.getId(),
            member.getEmail(),
            member.getMemberProfile().getName(),
            member.getMemberProfile().getNickname(),
            member.getMemberProfile().getPhone(),
            member.getMemberProfile().getAddress(),
            member.isActivated(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }
}
