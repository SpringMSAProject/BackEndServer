package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;

    //확장자
    private String ext;

    private String url;

    private String status;

    @Builder
    public ProductImage(Product productId, String name, String ext, String url, String status) {
        this.product = productId;
        this.name = name;
        this.ext = ext;
        this.url = url;
        this.status = status;
    }
}