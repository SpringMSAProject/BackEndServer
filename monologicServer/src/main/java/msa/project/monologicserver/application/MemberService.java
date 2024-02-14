package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.member.MemberRepository;
import msa.project.monologicserver.domain.memberprofile.MemberProfile;
import msa.project.monologicserver.domain.memberprofile.MemberProfileRepository;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import msa.project.monologicserver.presentation.dto.MemberJoinRequestDTO;
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

    @Transactional
    public String deleteMember(String memberId){
        // id로 member 조회
        final Member member = memberRepository.findMemberById(memberId)
            .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_NOT_FOUND));

        System.out.println(member.getDeletedAt());
        if(member.getDeletedAt()==null){
            memberRepository.delete(member);
        }
        else{
            throw new BusinessException(CommonErrorCode.ALREADY_DELETED);
        }

        return memberId;

    }

    private void memberDeletedValidationCheck(String memberId){

    }


    private void userEmailValidationCheck(MemberJoinRequestDTO joinRequestDTO) {
        if(memberRepository.existsByEmail(joinRequestDTO.email())){
            System.out.println("hi");
            throw new BusinessException(CommonErrorCode.USER_MANAGE_USER_EXISTENCE);
        }
    }

    private MemberProfile saveMemberProfile(MemberProfile memberProfile) {
        return memberProfileRepository.save(memberProfile);
    }

    private Member saveMember(MemberJoinRequestDTO joinRequestDTO) {
        return memberRepository.save(joinRequestDTO.toMember());
    }




}