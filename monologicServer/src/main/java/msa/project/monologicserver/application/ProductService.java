package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.CategoryType;
import msa.project.monologicserver.api.dto.req.product.ProductRegisterDTO;
import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.api.dto.res.product.ProductDataResponseDto;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.member.MemberRepository;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Like;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.ProductImage;
import msa.project.monologicserver.domain.product.repository.CategoryRepository;
import msa.project.monologicserver.domain.product.repository.LikeRepository;
import msa.project.monologicserver.domain.product.repository.ProductImageRepository;
import msa.project.monologicserver.domain.product.repository.ProductRepository;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.code.ErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;

    public ProductDataResponseDto registerProduct(String memberId, ProductRegisterDTO productRegisterDTO) {

        Member member = getMemberEntity(memberId);
        Product product = productRegisterDTO.of(member);

        productRepository.save(product);

        productRegisterDTO.categories().forEach(categoryType ->
            categoryRepository.save(
                    Category.builder()
                            .categoryName(categoryType.name())
                            .product(product)
                            .build())
        );

        if (productRegisterDTO.images().size() > 0) {
            productRegisterDTO.images().forEach(multipartFile ->
                    productImageRepository.save(
                            ProductImage.builder()
                                    .product(product)
                                    .ext(multipartFile.getContentType())
                                    .name(multipartFile.getOriginalFilename())
                                    .url("url")
                                    .status("status")
                                    .build())
            );
        }


        List<Category> categoryList = categoryRepository.findByProduct(product)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ENTITY_NOT_FOUND));
        List<ProductImage> productImages = productImageRepository.findByProduct(product).isPresent() ? productImageRepository.findByProduct(product).get() : new ArrayList<>();

        return ProductDataResponseDto
                .toProductDataResponseDto(
                        product,
                        categoryList,
                        productImages);
    }

    public ProductDataResponseDto updateProduct(Long productId, ProductRegisterDTO productRegisterDTO) {
        Product product = getEntityById(productId, productRepository);
//        Category category = getEntityById(productRegisterDTO.category(), categoryRepository);

//        product.update(productRegisterDTO,category);

        List<Category> categoryList = categoryRepository.findByProduct(product)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ENTITY_NOT_FOUND));
        List<ProductImage> productImages = productImageRepository.findByProduct(product).isPresent() ? productImageRepository.findByProduct(product).get() : new ArrayList<>();

        return ProductDataResponseDto
                .toProductDataResponseDto(
                        product,
                        categoryList,
                        productImages);
    }

    public ProductDataResponseDto readProduct(Long productId) {
        Product product = getEntityById(productId, productRepository);
        product.viewCountPlusOne();

        List<Category> categoryList = categoryRepository.findByProduct(product)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ENTITY_NOT_FOUND));
        List<ProductImage> productImages = productImageRepository.findByProduct(product).isPresent() ? productImageRepository.findByProduct(product).get() : new ArrayList<>();

        return ProductDataResponseDto
                .toProductDataResponseDto(
                        product,
                        categoryList,
                        productImages);
    }

    public ProductDataResponseDto likeProduct(Long productId, String memberId) {
        Product product = getEntityById(productId, productRepository);
        Member member = getMemberEntity(memberId);

        likeRepository.findByProductIdAndMemberId(product, member)
                .ifPresentOrElse(
                        existingLike -> {
                            likeRepository.delete(existingLike);
                            product.likeCountMinusOne();
                        },
                        () -> {
                            Like like = Like.builder()
                                            .memberId(member)
                                            .productId(product)
                                            .build();
                            likeRepository.save(like);
                            product.likeCountPlusOne();
                        });

        List<Category> categoryList = categoryRepository.findByProduct(product)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ENTITY_NOT_FOUND));
        List<ProductImage> productImages = productImageRepository.findByProduct(product).isPresent() ? productImageRepository.findByProduct(product).get() : new ArrayList<>();

        return ProductDataResponseDto
                .toProductDataResponseDto(
                        product,
                        categoryList,
                        productImages);
    }

    @Transactional(readOnly = true)
    public List<ProductDataResponseDto> readAll(SearchConditionDto searchConditionDto) {
//        return productRepository.findAll(pageable).map(ProductDataResponseDto::toProductDataResponseDto);
//        searchConditionDto.getKeyword();
        searchConditionDto.toString();

//        return productRepository.readAll(searchConditionDto).stream()
//                .map(ProductDataResponseDto::toProductDataResponseDto)
//                .collect(Collectors.toList());
        return null;
    }

    @Transactional(readOnly = true)
    public Page<ProductDataResponseDto> readByCategory(Pageable pageable, Long category) {

//        for (Sort.Order order : pageable.getSort()) {
//            if (order.getDirection() == Sort.Direction.DESC) {
//                return productRepository.findByCategoryIdOrderByCategoryIdDesc(pageable, getEntityById(category, categoryRepository))
//                        .map(ProductDataResponseDto::toProductDataResponseDto);
//            }
//        }
        Category entityById = getEntityById(category, categoryRepository);




//        return productRepository.findByCategoryId(entityById, pageable)
//                .map(ProductDataResponseDto::toProductDataResponseDto);
        return null;
    }

    public Member getMemberEntity(String memberId) {
        return memberRepository.findMemberByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_NOT_FOUND));
    }

    public <T, R extends JpaRepository<T, Long>> T getEntityById(Long id, R jpaRepository) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ENTITY_NOT_FOUND));
    }
}
