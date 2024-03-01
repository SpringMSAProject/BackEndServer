package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;

//    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    private int price;

    private int viewCount;

    private int likeCount;

    private String location;

    private String condition;

    private String status;

    @Lob
    private String thumbImg;

    @Setter
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    private LocalDateTime deletedAt;


    @PrePersist
    public void prePersist() {
        if (this.viewCount == 0) {
            this.viewCount = 0;
        }
        if (this.likeCount == 0) {
            this.likeCount = 0;
        }
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
