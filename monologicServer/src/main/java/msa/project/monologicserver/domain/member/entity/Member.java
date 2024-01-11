package msa.project.monologicserver.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import msa.project.monologicserver.global.entity.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "oauth_id")
    private String oauthId;

    @Column(name = "email")
    private String email;

    @Setter
    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "deal_location")
    private String dealLocation;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "role")
    private String role;

    @ColumnDefault("0")
    @Comment("사용 여부 0:사용, 9:삭제")
    @Column(name = "use_yn")
    private int useYn;

}
