package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @OneToMany(mappedBy="product",fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    private int price;

    private int viewCount;

    private int likeCount;

    private String location;

    private String condition;

    private String status;

    @Lob
    private String thumbImg;

    private LocalDateTime deletedAt;

}
