package msa.project.monologicserver.api.dto.res.product;

import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Product;

import java.time.LocalDateTime;

public record ProductDataResponseDto(
        Long productId,
        String memberId,
        String nickname,
//        Long categoryId,
//        String category,
        String title,
        String description,
        Integer price,
        String location,
        String condition,
        String status,
        String thumbImg,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProductDataResponseDto toProductDataResponseDto(Product product) {
        return new ProductDataResponseDto(
                product.getProductId(),
                product.getMemberId().getId(),
                product.getMemberId().getMemberProfile().getNickname(),
//                product.getCategoryId().getId(),
//                product.getCategoryId().getCategory(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getLocation(),
                product.getCondition(),
                product.getStatus(),
                product.getThumbImg(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
