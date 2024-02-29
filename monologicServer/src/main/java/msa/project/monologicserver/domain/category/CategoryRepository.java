package msa.project.monologicserver.domain.category;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ProductCategory,Long> {

    void deleteAllByProductId(Long productId);

    List<ProductCategory> findByProductId(Long productId);

    void deleteByProductId(Long id);

    void deleteById(Long productId);
}
