//package msa.project.monologicserver.domain.chatting;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//import msa.project.monologicserver.domain.member.Member;
//import msa.project.monologicserver.domain.product.entity.Product;
//import msa.project.monologicserver.global.entity.BaseTimeEntity;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Chatting extends BaseTimeEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "message_id")
//    private Long messageId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product productId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sender_id")
//    private Member senderId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "receiver_id")
//    private Member receiverId;
//
//    @Lob
//    @Column(name = "message_content", columnDefinition = "TEXT")
//    private String messageContent;
//
//    @Column(name = "is_read")
//    private Boolean isRead;
//
//}
