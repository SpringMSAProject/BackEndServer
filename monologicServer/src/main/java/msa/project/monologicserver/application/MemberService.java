package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.domain.member.MemberRepository;
import msa.project.monologicserver.presentation.dto.MemberJoinRequestDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;

    public String createMember(MemberJoinRequestDTO joinRequestDTO) {

        return "";
    }
}