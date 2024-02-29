package msa.project.monologicserver.api.dto.req.product;

import java.util.List;
import msa.project.monologicserver.domain.category.CategoryType;
import msa.project.monologicserver.domain.product.entity.Condition;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.ProductStatus;
import org.springframework.web.multipart.MultipartFile;

public record ProductInsertRequestDTO(
    String title,
    String description,
    int price,
    String location,
    Condition condition,
    ProductStatus status,

    List<CategoryType> categories,

    List<MultipartFile> images

) {

    public Product of() {
        return Product.builder()
            .title(title)
            .description(description)
            .price(price)
            .location(location)
            .condition(condition)
            .status(status)
            .build();
    }

}
