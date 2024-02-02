package msa.project.monologicserver.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", name = "created_dt")
    @Comment("생성일")
    private LocalDateTime createdDt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP", name = "modified_dt")
    private LocalDateTime modifiedDt;
}