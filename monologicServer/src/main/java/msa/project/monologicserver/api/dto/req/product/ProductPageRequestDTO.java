package msa.project.monologicserver.api.dto.req.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "페이징 요청 정보")
public class ProductPageRequestDTO {

    @Schema(description = "검색어", example = " ")
    private String search;

    @Schema(description = "페이지 번호", example = "1", required = true)
    private int pageNumber;

    @Schema(description = "페이지에 보여줄 content 개수", example = "25", required = true)
    private int pageSize;

    @Schema(description = "정렬 기준 (likes, views, recent)", example = "recent", allowableValues = {"likes", "views", "recent"})
    private String sort;

    @Schema(description = "정렬 방향 (true: 오름차순, false: 내림차순)", example = "true", defaultValue = "true")
    private boolean ascending = true;

    public ProductPageRequestDTO(String search, int pageNumber, int pageSize, String sort, boolean ascending) {
        this.search = search;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        this.ascending = ascending;
    }
}
