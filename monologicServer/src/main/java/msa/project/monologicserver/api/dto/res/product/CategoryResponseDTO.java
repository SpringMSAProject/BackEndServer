package msa.project.monologicserver.api.dto.res.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
public class CategoryResponseDTO {

    @Schema(description = "카테고리 이름")
    private String categoryName;

}

