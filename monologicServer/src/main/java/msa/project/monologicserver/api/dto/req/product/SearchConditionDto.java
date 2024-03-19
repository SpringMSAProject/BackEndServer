package msa.project.monologicserver.api.dto.req.product;

import jakarta.validation.constraints.NotBlank;
import msa.project.monologicserver.domain.product.entity.SortType;

public record SearchConditionDto (
        String keyword,
        String category,
        @NotBlank SortType sortType,
        boolean isDesc,

        long pageOffset,
        int pageSize

) {
}
