package msa.project.monologicserver.domain.product.repository;

import static msa.project.monologicserver.domain.category.QProductCategory.productCategory;
import static msa.project.monologicserver.domain.product.entity.QProduct.product;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductFilteredRequestDTO;
import msa.project.monologicserver.api.dto.res.product.FilteredProductResponseDTO;
import msa.project.monologicserver.domain.category.CategoryType;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;


    public List<FilteredProductResponseDTO> filteredProducts(
        Pageable pageable, ProductFilteredRequestDTO requestDto) {
        return jpaQueryFactory.select(Projections.bean(FilteredProductResponseDTO.class,
                product.id.as("productId"),
                product.title.as("title"),
                product.price.as("price"),
                product.createdAt.as("createdAt")
//            .as("likeCount")
            )).from(product)
            .where(
                eqCategories(requestDto.categories())

            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy()
            .fetch();
    }



    private BooleanExpression eqCategories(List<CategoryType> categoryTypes) {
        if (categoryTypes == null || categoryTypes.isEmpty()) {
            return null;
        }
        else {
            return productCategory.category.in(categoryTypes);
        }
    }


//    private BooleanExpression eqCustomer(List<String> customerIds) {
//        if (customerIds == null || customerIds.isEmpty()) {
//            return null;
//        } else {
//            return projectsView.customerId.in(customerIds);
//        }
//    }

}
