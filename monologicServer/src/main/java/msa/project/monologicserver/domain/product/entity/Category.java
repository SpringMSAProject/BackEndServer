package msa.project.monologicserver.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import msa.project.monologicserver.global.entity.BaseTimeEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @Setter
    private LocalDateTime deletedAt;

}
