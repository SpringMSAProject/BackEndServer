package msa.project.monologicserver.api.dto.req.product;

import java.util.List;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.CategoryType;
import msa.project.monologicserver.domain.product.entity.Condition;
import msa.project.monologicserver.domain.product.entity.ProductStatus;
import org.springframework.web.multipart.MultipartFile;

public record ProductUpdateRequestDTO(
    String title,
    String description,
    int price,
    String location,
    Condition condition,
    ProductStatus status,

    List<CategoryType> categories,
    List<MultipartFile> images

) {

}
