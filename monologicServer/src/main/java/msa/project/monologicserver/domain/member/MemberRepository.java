package msa.project.monologicserver.domain.member;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByEmail(String email);

    Optional<Member> findMemberById(Long id);

    @EntityGraph(attributePaths = {"memberProfile"})
    Optional<Member> findMemberByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = {"memberProfile"})
    List<Member> findAllByDeletedAtIsNull();

    Optional<Member> findByIdAndDeletedAtIsNull(Long memberId);
}
