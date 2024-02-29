package msa.project.monologicserver.domain.memberprofile;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile,Long> {

    Optional<MemberProfile> findByIdAndDeletedAtIsNull(Long memberId);
}
