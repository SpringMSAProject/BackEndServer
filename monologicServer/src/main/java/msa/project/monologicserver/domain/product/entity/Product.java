package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    // 여기는 왜 이렇게 해놨는지 물어보자.
    private Category categoryId;

    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    private int price;

    private int viewCount = 0;

    private int likeCount = 0;

    private String location;

    // 물품 상태
    private String condition;

    // 거래 상태
    private String status = "pre";

    @Lob
    private String thumbImg;

    private LocalDateTime deletedAt;


    @Builder
    public Product(String title, int price, String location, String condition){
        this.title = title;
        this.price = price;
        this.location = location;
        this.condition = condition;
    }
}


