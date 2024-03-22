package msa.project.monologicserver.domain.product.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.api.dto.res.product.QSearchData;
import msa.project.monologicserver.api.dto.res.product.SearchData;
import msa.project.monologicserver.domain.product.entity.CategoryList;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.global.entity.SortSpec;
import msa.project.monologicserver.global.entity.OrderSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static msa.project.monologicserver.domain.product.entity.QProduct.product;

@Component
@Slf4j
public class ProductRepositoryImpl extends QuerydslRepositorySupport  implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<SearchData> readAll(SearchConditionDto searchConditionDto, Pageable pageable) {

        List<SearchData> contents = queryFactory
                .select(
                        new QSearchData(product.title,
                                product.price,
                                product.likeCount,
                                product.viewCount,
                                product.updatedAt))
                .from(product)
                .where(keywordContains(searchConditionDto.keyword()),
                        categoryEq(searchConditionDto.category()))
                .orderBy(orderCond(searchConditionDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(Wildcard.count)
                .from(product)
                .where(keywordContains(searchConditionDto.keyword()),
                        (categoryEq(searchConditionDto.category())))
                .fetchOne();

        return new PageImpl<>(contents, pageable, count);
    }

    public BooleanExpression keywordContains(String keyword) {
        return keyword != null && !keyword.equals("") ? product.title.contains(keyword) : null;
    }

    public BooleanExpression categoryEq(String category) {
        return category != null && !category.equals("") ? product.mainCategory.eq(CategoryList.MainCategory.valueOf(category)) : null;
    }

    public OrderSpecifier[] orderCond(SearchConditionDto searchConditionDto) {
        OrderSpec orderSpec = OrderSpec.valueOf(searchConditionDto.order());
        SortSpec sortSpec = SortSpec.valueOf(searchConditionDto.sort());

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        switch (sortSpec) {
            case UPDATE:
                orderSpecifiers.add((orderSpec == OrderSpec.ASC) ? product.updatedAt.asc() : product.updatedAt.desc());
                break;
            case LIKE:
                orderSpecifiers.add((orderSpec == OrderSpec.ASC) ? product.likeCount.asc() : product.likeCount.desc());
                orderSpecifiers.add(product.updatedAt.desc());
                break;
            case VIEWCOUNT:
                orderSpecifiers.add((orderSpec == OrderSpec.ASC) ? product.viewCount.asc() : product.viewCount.desc());
                orderSpecifiers.add(product.updatedAt.desc());
                break;
        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

}