package msa.project.monologicserver.domain.notification;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.domain.member.entity.Member;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Lob
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "is_read")
    private String isRead;

}
