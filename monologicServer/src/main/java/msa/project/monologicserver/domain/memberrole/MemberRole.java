package msa.project.monologicserver.domain.memberrole;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
@Table(name = "member_role")
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
}
