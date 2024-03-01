package msa.project.monologicserver.api.dto.req.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
@Schema(description = "카테고리 요청 정보")
public class CategoryRequestDTO {

    @Schema(description = "카테고리 이름", example = "의류")
    private String categoryName;

}
