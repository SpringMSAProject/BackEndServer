package msa.project.monologicserver.api.dto.res.product;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FilteredProductResponseDTO {
    private Long productId;
    private String title;
    private Integer price;
    private LocalDateTime createdAt;
    private Long likeCount;

    @QueryProjection
    public FilteredProductResponseDTO(Long productId, String title, Integer price,
        LocalDateTime createdAt, Long likeCount) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }
}
