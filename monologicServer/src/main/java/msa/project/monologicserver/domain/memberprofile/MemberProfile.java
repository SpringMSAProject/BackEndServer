package msa.project.monologicserver.domain.memberprofile;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

@Entity
@NoArgsConstructor
@Table(name = "member_profile")
public class MemberProfile extends BaseTimeEntity {

    @Id
    @Column(name="id")
    private String id;

    private String name;
    private String nickname;
    private String phone;
    private String address;


    // 일대일관계 복합키
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Setter
    private Member member;





}
