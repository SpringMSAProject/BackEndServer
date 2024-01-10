package msa.project.monologicserver.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.domain.common.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "oauth_id")
    private String oauthId;

    @Column(name = "email")
    private String email;

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

    @Column(name = "use_yn")
    private int useYn;

    @Builder
    public Member(Long memberId, String oauthId, String email, String password,
                  String name, String nickname, String address, String dealLocation,
                  String phoneNo, String role, int useYn) {
        this.memberId = memberId;
        this.oauthId = oauthId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.dealLocation = dealLocation;
        this.phoneNo = phoneNo;
        this.role = role;
        this.useYn = useYn;
    }
}
