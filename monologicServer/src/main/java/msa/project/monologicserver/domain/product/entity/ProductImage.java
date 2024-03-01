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

    //확장자
    private String ext;

    private String url;

    private String status;

    private LocalDateTime deletedAt;
}