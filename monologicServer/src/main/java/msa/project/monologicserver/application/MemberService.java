package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.presentation.dto.member.MemberRequestDto;
import msa.project.monologicserver.presentation.dto.member.MemberResponseDto;
import msa.project.monologicserver.domain.member.entity.Member;
import msa.project.monologicserver.domain.member.repository.MemberRepository;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.exception.InvalidValueException;
import msa.project.monologicserver.global.values.CommonValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getAllMember() {
        return memberRepository.findAll()
                .stream()
                .map(member -> new MemberResponseDto(
                        member.getMemberId(),
                        member.getOauthId(),
                        member.getEmail(),
                        member.getPassword(),
                        member.getName(),
                        member.getNickname(),
                        member.getAddress(),
                        member.getDealLocation(),
                        member.getPhoneNo(),
                        member.getRole(),
                        member.getCreatedDt(),
                        member.getModifiedDt(),
                        member.getUseYn()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMemberById(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return MemberResponseDto.builder()
                    .memberId(member.getMemberId())
                    .oauthId(member.getOauthId())
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .address(member.getAddress())
                    .dealLocation(member.getDealLocation())
                    .phoneNo(member.getPhoneNo())
                    .role(member.getRole())
                    .createdDt(member.getCreatedDt())
                    .modifiedDt(member.getModifiedDt())
                    .useYn(member.getUseYn())
                    .build();
        } else {
            throw new InvalidValueException(CommonErrorCode.USER_NOT_FOUND);
        }
    }

    @Transactional
    public MemberResponseDto registerMember(MemberRequestDto memberRequestDto) {
        Member member = memberRequestDto.toEntity();
        member.setCreatedDt(LocalDateTime.now());

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        memberRepository.save(member);

        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .oauthId(member.getOauthId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNo(member.getPhoneNo())
                .address(member.getAddress())
                .dealLocation(member.getDealLocation())
                .role(member.getRole())
                .createdDt(member.getCreatedDt())
                .modifiedDt(member.getModifiedDt())
                .build();
    }

    @Transactional
    public int updateMember(Long id, MemberRequestDto memberRequestDto) {

        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new InvalidValueException(CommonErrorCode.USER_NOT_FOUND));

        if (existingMember.getUseYn() == CommonValue.DELETE_USE_YN) {
            throw new InvalidValueException(CommonErrorCode.CANNOT_UPDATE_DELETED_POST);
        }

        int updatedCnt = memberRepository.updateMember(id, memberRequestDto);

        if (updatedCnt <= 0) {
            throw new InvalidValueException(CommonErrorCode.USER_MANAGE_USER_UPDATE_FAIL);
        }

        return updatedCnt;
    }

    @Transactional
    public void changeUseYnWhenDeleted(Long id) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new InvalidValueException(CommonErrorCode.USER_NOT_FOUND));

        if (existingMember.getUseYn() == CommonValue.DELETE_USE_YN) {
            throw new InvalidValueException(CommonErrorCode.ALREADY_DELETED);
        }

        int updatedCnt = memberRepository.changeUseYnAndModifiedDtWhenDeleted(id);

        if (updatedCnt <= 0) {
            throw new InvalidValueException(CommonErrorCode.USER_MANAGE_USER_DELETE_FAIL);
        }
    }
}