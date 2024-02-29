package msa.project.monologicserver.api.dto.res.product;

import java.util.List;
import msa.project.monologicserver.domain.category.CategoryType;
import msa.project.monologicserver.domain.product.entity.Condition;
import msa.project.monologicserver.domain.product.entity.ProductStatus;

public record ProductResponseDTO(
    Long productId,
    Long userId,
    String title,
    String description,
    Integer price,
    String location,
    Condition condition,
    ProductStatus status,
    String thumbImg,
    List<CategoryType> categories,
    List<String> images
) {

}
