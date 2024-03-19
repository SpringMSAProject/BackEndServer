package msa.project.monologicserver.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.api.dto.res.product.QSearchData;
import msa.project.monologicserver.api.dto.res.product.SearchData;
import msa.project.monologicserver.domain.product.entity.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static msa.project.monologicserver.domain.product.entity.QCategory.category;
import static msa.project.monologicserver.domain.product.entity.QProduct.product;

@Component
public class ProductRepositoryImpl extends QuerydslRepositorySupport  implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<SearchData> readAll(SearchConditionDto searchConditionDto, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();
        if (searchConditionDto.keyword() != null) {
            builder.and(product.title.contains(searchConditionDto.keyword()));
        }
        if (searchConditionDto.category() != null && !searchConditionDto.category().equals("")) {
            builder.and(product.mainCategory.eq(CategoryList.MainCategory.valueOf(searchConditionDto.category())));
        }

        OrderSpecifier<?> descCondition =
                Optional.ofNullable(searchConditionDto.isDesc())
                        .orElse(true) ? product.updatedAt.desc() : product.updatedAt.asc();

        if (searchConditionDto.sortType() != null && !searchConditionDto.sortType().equals("")) {
            switch (searchConditionDto.sortType()) {
                case LIKE:
                    descCondition = searchConditionDto.isDesc() ? product.likeCount.desc() : product.likeCount.asc();
                    break;
                case VIEWCOUNT:
                    descCondition = searchConditionDto.isDesc() ? product.viewCount.desc() : product.viewCount.asc();
                    break;
                case UPDATE:
                    descCondition = searchConditionDto.isDesc() ? product.updatedAt.desc() : product.updatedAt.asc();
                    break;
            }
        }

        List<SearchData> contents = queryFactory
                .select(
                        new QSearchData(product.title,
                                product.price,
                                product.updatedAt))
                .from(product)
                .where(builder)
                .orderBy(descCondition)
                .offset(searchConditionDto.pageOffset())
                .limit(searchConditionDto.pageSize())
                .fetch();

        Long count = queryFactory
                .select(Wildcard.count)
                .from(product)
                .where(builder) // Use BooleanBuilder
                .fetchOne();

        return new PageImpl<>(contents, pageable, count);
    }

}