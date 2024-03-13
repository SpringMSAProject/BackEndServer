package msa.project.monologicserver.domain.product.repository;

import msa.project.monologicserver.domain.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
