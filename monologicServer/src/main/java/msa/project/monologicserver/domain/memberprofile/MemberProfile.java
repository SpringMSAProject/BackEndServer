package msa.project.monologicserver.domain.memberprofile;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.project.monologicserver.api.dto.req.member.MemberUpdateRequestDTO;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.global.entity.BaseTimeEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.cglib.core.Local;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_profile")
@SQLDelete(sql = "UPDATE member_profile SET deleted_at = now() WHERE member_id = ?")
public class MemberProfile extends BaseTimeEntity {

    @Id
    @Column(name="id")
    private Long id;

    private String name;
    private String nickname;
    private String phone;
    private String address;
    private LocalDateTime deletedAt;

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

//    public void addMember(Member member) {
//        if (this.member != null) {
//            this.member.setMemberProfile(null);
//        }
//        this.member = member;
//        this.member.setMemberProfile(this);
//    }

    public void update(MemberUpdateRequestDTO requestDTO){
        this.name=requestDTO.name();
        this.nickname= requestDTO.nickname();
        this.phone=requestDTO.phone();
        this.address=requestDTO.address();
    }

}
