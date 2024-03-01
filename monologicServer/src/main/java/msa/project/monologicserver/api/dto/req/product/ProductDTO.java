package msa.project.monologicserver.api.dto.req.product;

import jakarta.validation.constraints.NotBlank;
import msa.project.monologicserver.domain.product.entity.Product;

public record ProductDTO(
    @NotBlank String title,
    @NotBlank int price,
    @NotBlank String location,
    @NotBlank String condition
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
