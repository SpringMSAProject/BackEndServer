package msa.project.monologicserver.api.dto.req.product;

import jakarta.validation.constraints.NotBlank;
import msa.project.monologicserver.domain.product.entity.CategoryList;
import msa.project.monologicserver.domain.product.entity.SortType;

public record SearchConditionDto (
        String keyword,
        @NotBlank CategoryList categoryType,
        @NotBlank SortType sortType,
        boolean isDesc
) {
}
