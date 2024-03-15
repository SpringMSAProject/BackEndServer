package msa.project.monologicserver.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.api.dto.res.product.QSearchData;
import msa.project.monologicserver.api.dto.res.product.SearchData;
import msa.project.monologicserver.domain.product.entity.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static msa.project.monologicserver.domain.product.entity.QProduct.product;

@Component
public class ProductCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;
//    private final QProduct product = QProduct.product;
//    private final QCategory category = QCategory.category;
//    private final QLike like = QLike.like;
//    String keyword,
//    boolean isCategoryDesc,
//    boolean isViewCountDesc,
//    boolean isLikeDesc,
//    boolean isUpdateDesc


    public ProductCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<SearchData> readAll(SearchConditionDto searchConditionDto,Pageable pageable) {
        int pageSize = 20;
        long offset = (long) searchConditionDto.page() * pageSize;

        System.out.println(searchConditionDto);

        BooleanBuilder builder = new BooleanBuilder();
        if (searchConditionDto.keyword() != null && !searchConditionDto.keyword().isEmpty()) {
            builder.and(product.title.like("%" + searchConditionDto.keyword() + "%"))
                    .or(product.description.like("%" + searchConditionDto.keyword() + "%"));
        }

        List<SearchData> content = queryFactory.select(new QSearchData(product.title,
                        product.price,
                        product.updatedAt))
                .from(product)
                .where(builder)
                .orderBy((Optional.ofNullable(searchConditionDto.isUpdateDesc()).orElse(true) ? product.updatedAt.desc() : product.updatedAt.asc()),
                        (Optional.ofNullable(searchConditionDto.isViewCountDesc()).orElse(true) ? product.viewCount.desc() : product.viewCount.asc()),
                        (Optional.ofNullable(searchConditionDto.isLikeDesc()).orElse(true) ? product.likeCount.desc() : product.likeCount.asc())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = queryFactory
                .from(product)
                .where(builder)
                .orderBy((Optional.ofNullable(searchConditionDto.isUpdateDesc()).orElse(true) ? product.updatedAt.desc() : product.updatedAt.asc()),
                        (Optional.ofNullable(searchConditionDto.isViewCountDesc()).orElse(true) ? product.viewCount.desc() : product.viewCount.asc()),
                        (Optional.ofNullable(searchConditionDto.isLikeDesc()).orElse(true) ? product.likeCount.desc() : product.likeCount.asc())
                )
                .fetchCount();

        return new PageImpl<>(content, pageable, count);
    }

}