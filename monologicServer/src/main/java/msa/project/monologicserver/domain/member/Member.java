package msa.project.monologicserver.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.project.monologicserver.domain.memberprofile.MemberProfile;
import msa.project.monologicserver.global.entity.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;

    private String password;

    @ColumnDefault("true")
    private Boolean isActivated;

    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Setter
    private MemberProfile memberProfile;

    @Builder
    public Member(String email, String password){
        this.email = email;
        this.password = password;
    }
}
