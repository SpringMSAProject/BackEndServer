package msa.project.monologicserver.api.dto.req.product;

import java.util.List;
import msa.project.monologicserver.domain.category.CategoryType;
import msa.project.monologicserver.domain.product.entity.ProductSortType;

public record ProductFilteredRequestDTO(
    List<CategoryType> categories,
    ProductSortType productSortType,
    String location,
    String searchString

) {

}
