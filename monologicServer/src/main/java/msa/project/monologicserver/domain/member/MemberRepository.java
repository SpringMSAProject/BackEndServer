package msa.project.monologicserver.domain.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Boolean existsByEmail(String email);

    @Query(value = "select m.* from member m where m.id = :id", nativeQuery = true)
    Optional<Member> findMemberById(String id);
}
