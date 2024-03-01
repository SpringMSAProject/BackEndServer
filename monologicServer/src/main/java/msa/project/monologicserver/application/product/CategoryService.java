package msa.project.monologicserver.application.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.CategoryRequestDTO;
import msa.project.monologicserver.api.dto.res.product.CategoryResponseDTO;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = Category.builder()
                .categoryName(categoryRequestDTO.getCategoryName())
                .build();

        return CategoryResponseDTO.builder()
                .categoryName(categoryRepository.save(category).getCategoryName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> CategoryResponseDTO.builder().categoryName(category.getCategoryName()).build())
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다. : " + categoryId));
        existingCategory = Category.builder()
                .id(existingCategory.getId())
                .categoryName(categoryRequestDTO.getCategoryName())
                .build();
        existingCategory = categoryRepository.save(existingCategory);
        return CategoryResponseDTO.builder()
                .categoryName(existingCategory.getCategoryName())
                .build();
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다. : " + categoryId));

        category.setDeletedAt(LocalDateTime.now());
        category.setUseYn(9);
        categoryRepository.save(category);
    }
}
