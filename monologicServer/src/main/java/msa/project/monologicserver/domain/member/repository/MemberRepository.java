package msa.project.monologicserver.domain.member.repository;

import msa.project.monologicserver.domain.MemberEx;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEx,Long> {

}
