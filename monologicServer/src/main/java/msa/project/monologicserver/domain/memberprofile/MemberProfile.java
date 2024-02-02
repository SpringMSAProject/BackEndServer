package msa.project.monologicserver.domain.memberprofile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

@Entity
@NoArgsConstructor
@Table(name = "member_profile")
public class MemberProfile extends BaseTimeEntity {

    @Id
    @Column(name="id")
    private String id;




}
