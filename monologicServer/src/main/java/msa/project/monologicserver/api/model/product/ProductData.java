package msa.project.monologicserver.api.model.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.domain.product.entity.ProductImage;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductData {

    private Long productId;

    private String memberId;

    private Long categoryId;

    private String title;

    private String description;

    private int price;

    private int viewCount;

    private int likeCount;

    private String location;

    private String condition;

    private String status;

    private String thumbImg;

    private ProductImage productImage;

    @QueryProjection
    public ProductData(Long productId, String memberId, Long categoryId, String title, String description, int price, int viewCount, int likeCount, String location, String condition, String status, String thumbImg, ProductImage productImage) {
        this.productId = productId;
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.location = location;
        this.condition = condition;
        this.status = status;
        this.thumbImg = thumbImg;
        this.productImage = productImage;
    }
}