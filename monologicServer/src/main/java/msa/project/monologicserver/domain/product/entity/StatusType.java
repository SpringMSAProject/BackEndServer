package msa.project.monologicserver.domain.product.entity;

import lombok.Getter;

@Getter
public enum StatusType {
    PRE("판매중"),
    RESERVATION("예약중"),
    POST("판매완료");

    private String status;

    StatusType(String status) {
        this.status = status;
    }
}
