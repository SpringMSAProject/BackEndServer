package msa.project.monologicserver.domain.member;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member,String> {

    Boolean existsByEmail(String email);

    Optional<Member> findMemberById(String id);

    @EntityGraph(attributePaths = {"memberProfile"})
    Optional<Member> findMemberByIdAndDeletedAtIsNull(String id);

    @EntityGraph(attributePaths = {"memberProfile"})
    List<Member> findAllByDeletedAtIsNull();

    Optional<Member> findByIdAndDeletedAtIsNull(String memberId);
}
