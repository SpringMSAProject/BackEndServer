package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    private String title;

    @Column(name = "description")
    private String description;

    private int price;

    private String location;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Setter
    private String thumbImg;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy="product",fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();


    @Builder
    public Product(Member memberId, String title, String description, int price, String location,
        Condition condition, ProductStatus status) {
        this.memberId = memberId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.condition = condition;
        this.status = status;
    }

}
