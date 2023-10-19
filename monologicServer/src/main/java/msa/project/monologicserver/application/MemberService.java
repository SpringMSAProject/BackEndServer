package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.domain.Member;
import msa.project.monologicserver.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public String getFirstMember(){
        return memberRepository.findById(1L).get().getUserId();
    }

    @Transactional
    public void setAnyMember(){
        memberRepository.save(Member.builder().userId("test").password("1234").build());
    }

}
