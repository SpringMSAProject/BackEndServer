package msa.project.monologicserver.domain.memberprofile;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile,String> {

    Optional<MemberProfile> findByIdAndDeletedAtIsNull(String memberId);
}
