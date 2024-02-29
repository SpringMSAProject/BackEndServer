package msa.project.monologicserver.domain.product.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {

    List<ProductImage> findByProductId(Long productId);

    void deleteAllByProductId(Long productId);

    void deleteByProductId(Long id);
}
