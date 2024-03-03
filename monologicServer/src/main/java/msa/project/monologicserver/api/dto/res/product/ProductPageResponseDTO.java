package msa.project.monologicserver.api.dto.res.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import msa.project.monologicserver.api.model.product.ProductData;

import java.util.List;

@Getter
@Schema(description = "게시글 리스트 및 페이징 정보")
public class ProductPageResponseDTO {

    @Schema(description = "상품 리스트")
    private List<ProductData> productData;

    @Schema(description = "전체 데이터 수")
    private long totalCount;

    @Schema(description = "전체 페이지 수")
    private int totalPage;

    @Schema(description = "현재 페이지 번호")
    private int pageNumber;

    public ProductPageResponseDTO(int totalPage, long totalCount, int pageNumber, List<ProductData> productDataList) {
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.pageNumber = pageNumber;
        this.productData = productDataList;
    }
}
