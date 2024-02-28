package msa.project.monologicserver.domain.product.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import msa.project.monologicserver.domain.member.QMember;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    //    Page<Product> findByCategoryId(Pageable pageable);
    Page<Product> findByCategoryId(Category category, Pageable pageable);

    Page<Product> findByCategoryIdOrderByCategoryIdDesc(Pageable pageable, Category category);


}
