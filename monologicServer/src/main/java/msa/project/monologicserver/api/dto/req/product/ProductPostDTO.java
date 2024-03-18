package msa.project.monologicserver.api.dto.req.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.product.entity.CategoryList;
import msa.project.monologicserver.domain.product.entity.CategoryList.MainCategory;
import msa.project.monologicserver.domain.product.entity.ConditionType;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.StatusType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductPostDTO(
    @NotBlank String title,
    @NotBlank String description,
    @NotNull int price,
    @NotBlank String location,
    @NotNull ConditionType condition,
    StatusType status,

    List<MultipartFile> images,

    @NotNull MainCategory mainCategory,

    @Valid
    @NotNull
    @Size(min = 1)
    List<CategoryList> categories

) {
    public Product of(Member member) {

        String thumbImg = null;
        if (this.images() != null && this.images().isEmpty()) {
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
                .mainCategory(this.mainCategory)
                .thumbImg(thumbImg)
                .build();
    }

}
