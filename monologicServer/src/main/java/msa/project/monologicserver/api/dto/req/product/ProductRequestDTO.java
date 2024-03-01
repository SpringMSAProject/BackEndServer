package msa.project.monologicserver.api.dto.req.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.ProductImage;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProductRequestDTO {

    @Schema(description = "회원 ID", example = "example_member_id")
    private String memberId;

    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @Schema(description = "상품 제목", example = "앨범")
    private String title;

    @Schema(description = "상품 설명", example = "아이유 앨범 입니다.")
    private String description;

    @Schema(description = "가격", example = "10000")
    private int price;

    @Schema(description = "거래 희망 장소", example = "서울시 강남구")
    private String location;

    @Schema(description = "물품 상태", example = "S")
    private String condition;

    @Schema(description = "썸네일 이미지 파일경로", example = "https://i.namu.wiki/i/R0AhIJhNi8fkU2Al72pglkrT8QenAaCJd1as-d_iY6MC8nub1iI5VzIqzJlLa-1uzZm--TkB-KHFiT-P-t7bEg.webp")
    private String thumbImg;

    @Schema(description = "상품 이미지 리스트")
    private List<ProductImageRequestDTO> productImages;

    public Product createProduct(Member member, Category category) { // Member와 Category 객체를 인수로 받도록 변경
        return Product.builder()
                .category(category)
                .member(member)
                .title(title)
                .description(description)
                .price(price)
                .location(location)
                .condition(condition)
                .thumbImg(thumbImg)
                .images(convertToProductImages(productImages)) // productImages를 ProductImage 객체로 변환하여 설정
                .build();
    }

    private List<ProductImage> convertToProductImages(List<ProductImageRequestDTO> productImageRequestDTOs) {
        List<ProductImage> productImages = new ArrayList<>();
        if (productImageRequestDTOs != null) {
            for (ProductImageRequestDTO productImageRequestDTO : productImageRequestDTOs) {
                productImages.add(ProductImage.builder()
                        .url(productImageRequestDTO.getUrl())
                        .build());
            }
        }
        return productImages;
    }
}