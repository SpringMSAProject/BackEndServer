package msa.project.monologicserver.api.dto.req.product;

import jakarta.validation.constraints.NotBlank;
import msa.project.monologicserver.domain.product.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductDTO(
    @NotBlank String title,
    @NotBlank int price,
    @NotBlank String location,
    @NotBlank String condition

//    List<MultipartFile> imgFiles
    ){

    public Product toProduct(){
        return Product.builder()
                .title(this.title)
                .price(this.price)
                .location(this.location)
                .condition(this.condition)
                .build();
    }


}
