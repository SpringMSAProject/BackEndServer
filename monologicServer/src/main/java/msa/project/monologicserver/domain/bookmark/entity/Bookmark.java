package msa.project.monologicserver.domain.bookmark.entity;

import jakarta.persistence.*;
import msa.project.monologicserver.domain.member.entity.Member;
import msa.project.monologicserver.domain.product.entity.Product;

@Entity
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;

}
