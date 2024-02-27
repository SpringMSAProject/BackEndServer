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
    private Category categoryId;

    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    private int price;

    private int viewCount = 0;

    private int likeCount = 0;

    private String location;

    private String condition;

    private String status;

    @Lob
    private String thumbImg;

    private LocalDateTime deletedAt;

    @Builder
    public Product(Member memberId, Category categoryId, String title, String description, int price, String location, String condition, String status, String thumbImg) {
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.condition = condition;
        this.status = status;
        this.thumbImg = thumbImg;
    }

    public void viewCountPlusOne() {
        viewCount++;
    }
    public void likeCountPlusOne() {
        likeCount++;
    }

        public void likeCountMinusOne() {
        likeCount--;
    }
}
