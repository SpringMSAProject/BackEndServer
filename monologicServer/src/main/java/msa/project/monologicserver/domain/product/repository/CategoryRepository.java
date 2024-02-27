package msa.project.monologicserver.domain.product.repository;

import msa.project.monologicserver.domain.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategory(String category);
}