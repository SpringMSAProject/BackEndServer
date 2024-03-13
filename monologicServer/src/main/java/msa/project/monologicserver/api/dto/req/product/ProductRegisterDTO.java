package msa.project.monologicserver.api.dto.req.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.product.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductRegisterDTO (
    @NotBlank String title,
    @NotBlank String description,
    @NotNull int price,
    @NotBlank String location,
    @NotBlank String condition,
    @NotBlank String status,

    @Valid
    @NotNull
    @Size(min = 1)
    List<CategoryType> categories,

    List<MultipartFile> images

) {
    public Product of(Member member) {

        String thumbImg = null;
        if (this.images() != null) {
            MultipartFile firstFile = this.images().get(0);
            thumbImg = firstFile.getOriginalFilename();
        }

        return Product.builder()
                .memberId(member)
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .location(this.location)
                .condition(this.condition)
                .status(this.status)
                .thumbImg(thumbImg)
                .build();
    }

}
