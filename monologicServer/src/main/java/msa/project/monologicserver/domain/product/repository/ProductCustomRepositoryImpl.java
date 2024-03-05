package msa.project.monologicserver.domain.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.member.QMember;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.QCategory;
import msa.project.monologicserver.domain.product.entity.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ProductCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QProduct product = QProduct.product;
    private final QCategory category = QCategory.category1;

    public ProductCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

    public Page<Product> findByCategoryAndPaging(String categoryName, Pageable pageable) {
        List<Product> result = queryFactory
                .selectFrom(product)
                .join(product)
                .on(product.categoryId.eq(category))
                .orderBy(category.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return null;
    }

    @Override
    public List<Product> readAll(SearchConditionDto searchConditionDto) {


        JPAQuery<Product> query = queryFactory.selectFrom(product)
                .join(product)
                .on(product.categoryId.eq(category))
                .where(product.title.eq(searchConditionDto.keyword()));

        if (searchConditionDto.isOrderCategoryDesc()) {
            query.orderBy(category.category.desc());
        } else {
            query.orderBy(category.category.asc());
        }

        if (searchConditionDto.isOrderLikeDesc()) {
            query.orderBy(product.likeCount.desc());
        } else {
            query.orderBy(product.likeCount.asc());
        }

        if (searchConditionDto.isOrderUpdateDesc()) {
            query.orderBy(product.updatedAt.desc());
        } else {
            query.orderBy(product.updatedAt.asc());
        }

        List<Product> result = query.fetch();

        return result;
    }

//    public Page<Product> findByCategoryAndPaging(String categoryName, Pageable pageable) {
//        return applyPagination(pageable, () -> queryFactory
//                .selectFrom(product)
//                .where(eqCategoryName(categoryName))
//                .orderBy(product.createdDate.desc()));
//    }
//
//    private BooleanExpression eqCategoryName(String categoryName) {
//        return categoryName != null ? product.category.name.eq(categoryName) : null;
//    }
}