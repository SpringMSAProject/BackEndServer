package msa.project.monologicserver.domain.product.repository;

import static msa.project.monologicserver.domain.category.QProductCategory.productCategory;
import static msa.project.monologicserver.domain.like.QLikes.likes;
import static msa.project.monologicserver.domain.product.entity.QProduct.product;
import static msa.project.monologicserver.domain.views.QViews.views;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductFilteredRequestDTO;
import msa.project.monologicserver.api.dto.res.product.FilteredProductResponseDTO;
import msa.project.monologicserver.domain.category.CategoryType;
import msa.project.monologicserver.domain.product.entity.ProductSortType;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public List<FilteredProductResponseDTO> filteredProducts(
        Pageable pageable, ProductFilteredRequestDTO requestDto) {
        return jpaQueryFactory.select(Projections.bean(FilteredProductResponseDTO.class,
                product.id.as("productId"),
                product.title.as("title"),
                product.price.as("price"),
                product.createdAt.as("createdAt"),
                ExpressionUtils.as(
                    JPAExpressions.select(likes.count())
                        .from(likes)
                        .where(likes.product.id.eq(product.id)),
                    "likeCount"
                )
            )).from(product)
            .leftJoin(productCategory).on(product.id.eq(productCategory.product.id))
            .where(
                eqCategories(requestDto.categories())
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(sortByField(requestDto.productSortType()))
            .groupBy(product.id)
            .fetch();
    }


    private BooleanExpression eqCategories(List<CategoryType> categoryTypes) {
        if (categoryTypes == null || categoryTypes.isEmpty()) {
            return null;
        } else {
            return productCategory.category.in(categoryTypes);
        }
    }

    private OrderSpecifier<?> sortByField(ProductSortType sortType) {
        if (sortType == null) {
            return product.createdAt.desc();

        }
        return switch (sortType) {
            case LIKE ->
                // 좋아요 순으로 정렬
                likes.count().intValue().desc();
            case VIEW ->
                // 조회수 순으로 정렬
                views.count().desc();
            default ->
                // 등록일 순으로 정렬 (기본값)
                product.createdAt.desc();
        };
    }


}
