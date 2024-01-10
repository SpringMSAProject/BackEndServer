package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.domain.common.entity.BaseTimeEntity;
import msa.project.monologicserver.domain.member.entity.Member;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "view_cnt")
    private Long viewCnt;

    @Column(name = "like_cnt")
    private Long likeCnt;

    @Column(name = "location")
    private String location;

    @Column(name = "condition")
    private String condition;

    @Column(name = "status")
    private String status;

    @Lob
    @Column(name = "thumb_img")
    private String thumbImg;

}
