package msa.project.monologicserver.api.dto.res.product;

import msa.project.monologicserver.domain.product.entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record ProductDataResponseDto(
        Long productId,
        String memberId,
        String nickname,
        String title,
        String description,
        int price,
        String location,
        ConditionType condition,
        StatusType status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<CategoryList> categoryTypes,
        List<String> url
) {
    public static ProductDataResponseDto toProductDataResponseDto(Product product, List<Category> categories, List<ProductImage> productImage) {

        List<CategoryList> categoriesNameList = new ArrayList<>();
//        categories.forEach(c -> categoriesNameList.add(CategoryList.valueOf(String.valueOf(c.getCategoryName()))));

        List<String> urlList = new ArrayList<>();
        productImage.forEach(img -> urlList.add(img.getUrl()));

        return new ProductDataResponseDto(
                product.getProductId(),
                product.getMemberId().getId(),
                product.getMemberId().getMemberProfile().getNickname(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getLocation(),
                product.getCondition(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                categoriesNameList,
                urlList
        );
    }

}
