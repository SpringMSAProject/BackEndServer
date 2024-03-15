package msa.project.monologicserver.api.dto.req.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SearchConditionDto (
        @NotNull @Size(min = 0) int page,
        String keyword,
        boolean isCategoryDesc,
        boolean isViewCountDesc,
        boolean isLikeDesc,
        boolean isUpdateDesc
) {
    @Override
    public String toString() {
        return "SearchConditionDto{" +
                "page=" + page +
                ", keyword='" + keyword + '\'' +
                ", isCategoryDesc=" + isCategoryDesc +
                ", isViewCountDesc=" + isViewCountDesc +
                ", isLikeDesc=" + isLikeDesc +
                ", isUpdateDesc=" + isUpdateDesc +
                '}';
    }
}
