package msa.project.monologicserver.api.dto.req.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Product;

public record ProductRegisterDTO (
    @Getter @NotBlank String category,
    @NotBlank String title,
    @NotBlank String description,
    @NotNull int price,
    @NotBlank String location,
    @NotBlank String condition,
    @NotBlank String status,
    String thumbImg

) {
    public Product toEntity(Member member, Category category) {
        return Product.builder()
                .memberId(member)
                .categoryId(category)
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .location(this.location)
                .condition(this.condition)
                .status(this.status)
                .thumbImg(this.thumbImg)
                .build();
    }
}
