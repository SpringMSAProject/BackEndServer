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
//    private final QProduct product = QProduct.product;
//    private final QCategory category = QCategory.category;
//    private final QLike like = QLike.like;
//    String keyword,
//    boolean isCategoryDesc,
//    boolean isViewCountDesc,
//    boolean isLikeDesc,
//    boolean isUpdateDesc


    @Override
    public Page<SearchData> readAll(SearchConditionDto searchConditionDto, Pageable pageable) {

        JPAQuery<SearchData> query = queryFactory
                .select(
                        new QSearchData(product.title,
                                product.price,
                                product.updatedAt))
                .from(product);

        if (searchConditionDto.categoryType() != null && searchConditionDto.categoryType().equals("")) {
//            query.leftJoin(product, category.product)
//                    .on(category.categoryName.eq(searchConditionDto.categoryType()));
        }

        BooleanBuilder builder = new BooleanBuilder();
        if (searchConditionDto.keyword() != null && !searchConditionDto.keyword().isEmpty()) {
            String keyword = searchConditionDto.keyword();
            BooleanExpression titleContainsKeyword = product.title.contains(keyword);
            BooleanExpression descriptionContainsKeyword = Expressions.booleanTemplate(
                    "position(?1 in cast(product.description as text)) > 0", keyword);
            builder.and(titleContainsKeyword)
                    .or(descriptionContainsKeyword);

            if (searchConditionDto.categoryType() != null && searchConditionDto.categoryType().equals("")) {
//                BooleanExpression categoryEqName = QCategory.category.categoryName.eq(searchConditionDto.categoryType());
//                builder.and(categoryEqName);
            }
        }
        query.where(builder);

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
        List<SearchData> content = query.orderBy(descCondition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long countQuery = queryFactory
                .select(Wildcard.count)
                .from(product)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, countQuery);
    }

}