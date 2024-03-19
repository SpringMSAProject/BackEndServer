package msa.project.monologicserver.api.dto.res.product;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import java.time.LocalDateTime;

public record SearchData(
        String title,
        int price,
        LocalDateTime updateAt
) {

    @QueryProjection
    public SearchData(String title, int price, LocalDateTime updateAt) {
        this.title = title;
        this.price = price;
        this.updateAt = updateAt;
    }
}
