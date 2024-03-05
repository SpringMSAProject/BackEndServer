package msa.project.monologicserver.api.dto.req.product;

public record SearchConditionDto (
    String keyword,
    boolean isOrderCategoryDesc,
    boolean isOrderLikeDesc,
    boolean isOrderUpdateDesc
) {

    @Override
    public String toString() {
        return "SearchConditionDto{" +
                ", keyword='" + keyword + '\'' +
                ", isOrderCategoryDesc=" + isOrderCategoryDesc +
                ", isOrderLikeDesc=" + isOrderLikeDesc +
                ", isOrderUpdateDesc=" + isOrderUpdateDesc +
                '}';
    }
}
