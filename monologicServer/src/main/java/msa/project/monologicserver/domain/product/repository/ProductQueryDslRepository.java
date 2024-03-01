package msa.project.monologicserver.domain.product.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductPageRequestDTO;
import msa.project.monologicserver.api.dto.res.product.ProductPageResponseDTO;
import msa.project.monologicserver.api.model.product.ProductData;
import msa.project.monologicserver.api.model.product.QProductData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static msa.project.monologicserver.domain.product.entity.QProduct.product;
import static msa.project.monologicserver.domain.product.entity.QProductImage.productImage;

@RequiredArgsConstructor
@Repository
public class ProductQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ProductPageResponseDTO> getAllProducts(ProductPageRequestDTO pageRequestDTO) {
        List<ProductData> productDataList = queryFactory
                .select(new QProductData(
                        product.productId,
                        product.member.id,
                        product.category.id,
                        product.title,
                        product.description,
                        product.price,
                        product.viewCount,
                        product.likeCount,
                        product.location,
                        product.condition,
                        product.status,
                        product.thumbImg,
                        productImage
                ))
                .from(product)
                .leftJoin(product.images, productImage)
                .where(searchCondition(pageRequestDTO.getSearch()))
                .offset((long) pageRequestDTO.getPageNumber() * pageRequestDTO.getPageSize())
                .limit(pageRequestDTO.getPageSize())
                .orderBy(getOrderSpecifier(pageRequestDTO.getSort(), pageRequestDTO.isAscending()))
                .fetch();

        long totalCount = queryFactory
                .selectFrom(product)
                .where(searchCondition(pageRequestDTO.getSearch()))
                .fetchCount();

        return new PageImpl<>(Collections.singletonList(new ProductPageResponseDTO(productDataList)), PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize()), totalCount);
    }

    public Page<ProductPageResponseDTO> getProductsByCategoryId(Long categoryId, ProductPageRequestDTO pageRequestDTO) {
        List<ProductData> productDataList = queryFactory
                .select(new QProductData(
                        product.productId,
                        product.member.id,
                        product.category.id,
                        product.title,
                        product.description,
                        product.price,
                        product.viewCount,
                        product.likeCount,
                        product.location,
                        product.condition,
                        product.status,
                        product.thumbImg,
                        productImage
                ))
                .from(product)
                .leftJoin(product.images, productImage)
                .where(categoryIdEq(categoryId), searchCondition(pageRequestDTO.getSearch()))
                .offset((long) pageRequestDTO.getPageNumber() * pageRequestDTO.getPageSize())
                .limit(pageRequestDTO.getPageSize())
                .orderBy(getOrderSpecifier(pageRequestDTO.getSort(), pageRequestDTO.isAscending()))
                .fetch();

        long totalCount = queryFactory
                .selectFrom(product)
                .where(categoryIdEq(categoryId), searchCondition(pageRequestDTO.getSearch()))
                .fetchCount();

        return new PageImpl<>(Collections.singletonList(new ProductPageResponseDTO(productDataList)), PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize()), totalCount);
    }

    public void deleteProductAndImages(Long productId) {
        // 상품 삭제
        queryFactory
                .update(product)
                .set(product.useYn, 9)
                .set(product.deletedAt, LocalDateTime.now())
                .where(product.productId.eq(productId))
                .execute();

        // 상품 이미지 삭제
        queryFactory
                .update(productImage)
                .set(productImage.deletedAt, LocalDateTime.now())
                .set(productImage.useYn, 9)
                .where(productImage.product.productId.eq(productId))
                .execute();
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId != null ? product.category.id.eq(categoryId) : null;
    }

    private BooleanExpression searchCondition(String search) {
        if (!StringUtils.hasText(search)) {
            return null;
        }
        return product.title.containsIgnoreCase(search)
                .or(product.description.containsIgnoreCase(search));
    }

    private OrderSpecifier<?> getOrderSpecifier(String sort, boolean ascending) {
        if ("likes".equals(sort)) {
            return ascending ? product.likeCount.asc() : product.likeCount.desc();
        } else if ("views".equals(sort)) {
            return ascending ? product.viewCount.asc() : product.viewCount.desc();
        } else if ("recent".equals(sort)) {
            return ascending ? product.createdAt.asc() : product.createdAt.desc();
        }
        return ascending ? product.productId.asc() : product.productId.desc();
    }
}