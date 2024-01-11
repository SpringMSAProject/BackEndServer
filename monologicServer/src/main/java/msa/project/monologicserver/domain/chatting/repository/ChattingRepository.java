package msa.project.monologicserver.domain.chatting.repository;

import msa.project.monologicserver.domain.chatting.entity.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRepository extends JpaRepository<Chatting,Long> {

}
