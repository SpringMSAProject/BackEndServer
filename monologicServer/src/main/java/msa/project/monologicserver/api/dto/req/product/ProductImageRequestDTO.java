package msa.project.monologicserver.api.dto.req.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "카테고리 요청 정보")
public class ProductImageRequestDTO {

    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @Schema(description = "파일명", example = "example_file")
    private String name;

    @Schema(description = "확장자", example = "txt")
    private String ext;

    @Schema(description = "경로", example = "/path/to/example_file.txt")
    private String url;

    @Schema(description = "파일상태", example = "updated")
    private String status;
}
