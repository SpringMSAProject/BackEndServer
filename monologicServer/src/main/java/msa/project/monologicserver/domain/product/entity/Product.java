package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductPostDTO;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.global.entity.BaseTimeEntity;
import org.springframework.web.multipart.MultipartFile;

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

    private String title;

    @Lob
    @Column(columnDefinition = "text")
    private String description;

    private int price;

    private int viewCount = 0;

    private int likeCount = 0;

    private String location;

    @Enumerated(EnumType.STRING)
    private CategoryList.MainCategory mainCategory;

    @Enumerated(EnumType.STRING)
    private ConditionType condition;

    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.PRE;

    @Lob
    private String thumbImg;

    private LocalDateTime deletedAt;

    @PrePersist
    public void defaultStatus() {
        this.status = this.status == null ? StatusType.PRE : this.status;
    }

    @Builder
    public Product(Member memberId, String title, String description, int price, String location, CategoryList.MainCategory mainCategory, ConditionType condition, StatusType status, String thumbImg) {
        this.memberId = memberId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.mainCategory = mainCategory;
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
        //Todo 좋아요 테이블 INSERT
    }

    public void update(ProductPostDTO productRegisterDTO) {
        this.title = productRegisterDTO.title();
        this.description = productRegisterDTO.description();
        this.price = productRegisterDTO.price();
        this.location = productRegisterDTO.location();
        this.condition = productRegisterDTO.condition();
        this.status = productRegisterDTO.status();

        if (productRegisterDTO.images().size() > 0) {
            MultipartFile firstFile = productRegisterDTO.images().get(0);
            this.thumbImg = firstFile.getOriginalFilename();
        }
    }
}
