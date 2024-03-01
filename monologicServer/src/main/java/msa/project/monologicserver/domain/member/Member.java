package msa.project.monologicserver.domain.member;

import jakarta.persistence.*;
import lombok.*;
import msa.project.monologicserver.api.dto.req.member.MemberUpdateRequestDTO;
import msa.project.monologicserver.domain.memberprofile.MemberProfile;
import msa.project.monologicserver.global.entity.BaseTimeEntity;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted_at = now() WHERE id = ?")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;

    private String password;

    private final boolean isActivated = true;

    private LocalDateTime deletedAt;

    //


    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    @Setter
    private MemberProfile memberProfile;


    @Builder
    public Member(String email, String password){
        this.email = email;
        this.password = password;
    }


    public void update(MemberUpdateRequestDTO requestDTO) {
        this.email=requestDTO.email();
    }
}
