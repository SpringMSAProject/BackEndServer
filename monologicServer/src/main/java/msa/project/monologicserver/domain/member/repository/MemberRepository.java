package msa.project.monologicserver.domain.member.repository;

import msa.project.monologicserver.presentation.dto.member.MemberRequestDto;
import msa.project.monologicserver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    @Modifying
    @Query("UPDATE Member m SET m.nickname = :#{#dto.nickname}, m.email = :#{#dto.email}, m.phoneNo = :#{#dto.phoneNo}, m.address = :#{#dto.address}, m.modifiedDt = CURRENT_TIMESTAMP WHERE m.memberId = :id")
    int updateMember(@Param("id") Long id, @Param("dto") MemberRequestDto dto);

    @Modifying
    @Query("UPDATE Member m SET m.useYn = 9, m.modifiedDt = CURRENT_TIMESTAMP WHERE m.memberId = :id")
    int changeUseYnAndModifiedDtWhenDeleted(@Param("id") Long id);
}
