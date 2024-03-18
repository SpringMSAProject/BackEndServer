package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryList.MainCategory mainCategory;

    @Enumerated(EnumType.STRING)
    private CategoryList subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Category(CategoryList.MainCategory mainCategory, CategoryList subCategory, Product product) {
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.product = product;
    }
}
