package msa.project.monologicserver.api.dto.req.product;

import msa.project.monologicserver.global.entity.SortSpec;
import msa.project.monologicserver.global.entity.OrderSpec;

public record SearchConditionDto (
        String keyword,
        String category,
        String sort, //enum은 NotNull 필수 -> 생성자에서 기본 설정
        String order // boolean은 NotNull 필수 -> 생성자에서 기본 설정
) {

    public SearchConditionDto(String keyword, String category, String sort, String order) {
        this.keyword = keyword;
        this.category = (category != null) ? category.toUpperCase() : null;
        this.sort = (sort == null) || (sort.equals(""))
                ? SortSpec.UPDATE.name() : sort.toUpperCase();
        this.order = (order == null) || (order.equals(""))
                ? OrderSpec.DESC.name() : order.toUpperCase();
    }
}
