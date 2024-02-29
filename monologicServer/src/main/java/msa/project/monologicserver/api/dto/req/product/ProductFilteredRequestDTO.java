package msa.project.monologicserver.api.dto.req.product;

import msa.project.monologicserver.domain.product.entity.ProductSortType;

public record ProductFilteredRequestDTO(
    ProductSortType productSortType,
    String location,
    String searchString
) {

}
