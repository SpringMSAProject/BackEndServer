package msa.project.monologicserver.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.member.MemberJoinRequestDTO;
import msa.project.monologicserver.api.dto.res.member.MemberDataResponseDto;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.member.MemberRepository;
import msa.project.monologicserver.domain.memberprofile.MemberProfile;
import msa.project.monologicserver.domain.memberprofile.MemberProfileRepository;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;


    @Transactional
    public String createMember(MemberJoinRequestDTO joinRequestDTO) {
        //유효성 검사
        userEmailValidationCheck(joinRequestDTO);

        final Member member = saveMember(joinRequestDTO);
        final MemberProfile memberProfile = joinRequestDTO.toMemberProfile();

        memberProfile.setMember(member);
        saveMemberProfile(memberProfile);

        return member.getId();
    }

    @Transactional(readOnly = true)
    public MemberDataResponseDto readMember(String memberId) {
        return MemberDataResponseDto.fromEntity(memberRepository.findMemberByIdAndDeletedAtIsNull(memberId)
            .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_NOT_FOUND)));
    }

    @Transactional
    public String deleteMember(String memberId) {
        // id로 member 조회
        final Member member = memberRepository.findMemberById(memberId)
            .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_NOT_FOUND));

        System.out.println(member.getDeletedAt());
        if (member.getDeletedAt() == null) {
            System.out.println("삭제처리시작");

            memberRepository.delete(member);
        } else {
            throw new BusinessException(CommonErrorCode.ALREADY_DELETED);
        }

        return memberId;

    }


    private void userEmailValidationCheck(MemberJoinRequestDTO joinRequestDTO) {
        if (memberRepository.existsByEmail(joinRequestDTO.email())) {
            System.out.println("hi");
            throw new BusinessException(CommonErrorCode.USER_MANAGE_USER_EXISTENCE);
        }
    }

    private void saveMemberProfile(MemberProfile memberProfile) {
        memberProfileRepository.save(memberProfile);
    }

    private Member saveMember(MemberJoinRequestDTO joinRequestDTO) {
        return memberRepository.save(joinRequestDTO.toMember());
    }


    public String updateMember(String memberId) {
        return null;
    }

    public List<MemberDataResponseDto> readAllMember() {


        return null;
    }
}