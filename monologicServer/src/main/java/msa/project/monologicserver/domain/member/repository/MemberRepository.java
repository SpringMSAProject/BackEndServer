package msa.project.monologicserver.domain.member.repository;

import msa.project.monologicserver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
