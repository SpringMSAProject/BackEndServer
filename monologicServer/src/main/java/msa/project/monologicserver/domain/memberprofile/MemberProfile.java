package msa.project.monologicserver.domain.memberprofile;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.global.entity.BaseTimeEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_profile")
@OnDelete(action = OnDeleteAction.CASCADE)
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


    @Builder
    MemberProfile(String name, String nickname, String phone, String address){
        this.name = name;
        this.nickname=nickname;
        this.address=address;
        this.phone=phone;
    }

    public void addMember(Member member) {
        if (this.member != null) {
            this.member.setMemberProfile(null);
        }
        this.member = member;
        this.member.setMemberProfile(this);
    }

}
