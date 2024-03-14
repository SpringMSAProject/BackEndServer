package msa.project.monologicserver.api.dto.res.product;

import msa.project.monologicserver.api.dto.req.product.CategoryType;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.ProductImage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record ProductDataResponseDto(
        Long productId,
        String memberId,
        String nickname,
//        Long categoryId,
//        String category,
        String title,
        String description,
        int price,
        String location,
        String condition,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<CategoryType> categoryTypes,
        List<String> url
) {
    public static ProductDataResponseDto toProductDataResponseDto(Product product, List<Category> categories, List<ProductImage> productImage) {

        List<CategoryType> categoriesNameList = new ArrayList<>();
        categories.forEach(c -> categoriesNameList.add(CategoryType.valueOf(c.getCategoryName())));

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
