package msa.project.monologicserver.domain.product.repository;

import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    List<Product> findAll();

    //    Page<Product> findByCategoryId(Pageable pageable);
//    Page<Product> findByCategoryId(Category category, Pageable pageable);

//    Page<Product> findByCategoryIdOrderByCategoryIdDesc(Pageable pageable, Category category);


}
