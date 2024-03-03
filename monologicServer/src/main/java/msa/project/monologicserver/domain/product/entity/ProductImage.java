package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_image")
public class ProductImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;

    private String ext;

    // 이미지 URL 필드
    private String imageUrl;

    private String status;

    private LocalDateTime deletedAt;

    // 생성자 추가 - 이미지 URL을 받아서 저장
    public ProductImage(Product product, String imageUrl) {
        this.product = product;
        this.imageUrl = imageUrl;
    }
}