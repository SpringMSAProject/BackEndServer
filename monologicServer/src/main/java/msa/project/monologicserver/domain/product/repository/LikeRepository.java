package msa.project.monologicserver.domain.product.repository;

import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.product.entity.Like;
import msa.project.monologicserver.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByProductIdAndMemberId(Product productId, Member memberId);
}
