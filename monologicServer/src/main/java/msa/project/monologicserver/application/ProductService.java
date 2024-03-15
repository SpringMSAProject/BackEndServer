package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductPostDTO;
import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.api.dto.res.product.ProductDataResponseDto;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.member.MemberRepository;
import msa.project.monologicserver.domain.product.entity.*;
import msa.project.monologicserver.domain.product.repository.CategoryRepository;
import msa.project.monologicserver.domain.product.repository.LikeRepository;
import msa.project.monologicserver.domain.product.repository.ProductImageRepository;
import msa.project.monologicserver.domain.product.repository.ProductRepository;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public ProductDataResponseDto createProduct(String memberId, ProductPostDTO productPostDTO) {

        if (productPostDTO.status() != null && productPostDTO.status() != StatusType.PRE) {
            throw new BusinessException(CommonErrorCode.BAD_REQUEST);
        }

        Member member = findMemberById(memberId);
        Product product = productPostDTO.of(member);

        productRepository.save(product);

        List<Category> categoryList = saveCategories(productPostDTO, product);
        List<ProductImage> productImageList = saveProductImages(productPostDTO, product);

        return ProductDataResponseDto
                .toProductDataResponseDto(
                        product,
                        categoryList,
                        productImageList);
    }

    @Transactional
    public ProductDataResponseDto updateProduct(Long productId, ProductPostDTO productPostDTO) {

        if (productPostDTO.status() == null) {
            throw new BusinessException(CommonErrorCode.BAD_REQUEST);
        }

        Product product = findProductById(productId);
        product.update(productPostDTO);

        categoryRepository.deleteByProduct(product);
        List<Category> categoryList = saveCategories(productPostDTO, product);

        productImageRepository.deleteByProduct(product);
        List<ProductImage> productImageList = saveProductImages(productPostDTO, product);

        return ProductDataResponseDto
                .toProductDataResponseDto(
                        product,
                        categoryList,
                        productImageList);
    }

    @Transactional(readOnly = true)
    public ProductDataResponseDto readProduct(Long productId) {
        Product product = findProductById(productId);
        product.viewCountPlusOne();

        List<Category> categoryList = findCategoryList(product);
        List<ProductImage> productImages = findProductImageList(product);

        return ProductDataResponseDto
                .toProductDataResponseDto(
                        product,
                        categoryList,
                        productImages);
    }

    public ProductDataResponseDto likeProduct(Long productId, String memberId) {
        Member member = findMemberById(memberId);
        Product product = findProductById(productId);

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

        List<Category> categoryList = findCategoryList(product);
        List<ProductImage> productImages = findProductImageList(product);

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

        return null;
    }

    private Member findMemberById(String memberId) {
        return memberRepository.findMemberByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_NOT_FOUND));
    }

    private List<ProductImage> findProductImageList(Product product) {
        return productImageRepository.findByProduct(product).orElse(new ArrayList<>());
    }

    private List<Category> findCategoryList(Product product) {
        return categoryRepository.findByProduct(product)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ENTITY_NOT_FOUND));
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ENTITY_NOT_FOUND));
    }

    private List<Category> saveCategories(ProductPostDTO productPostDTO, Product product) {
        List<Category> savedCategories = new ArrayList<>();
        productPostDTO.categories().forEach(categoryType ->
                savedCategories.add(categoryRepository.save(
                        Category.builder()
                                .categoryName(CategoryType.valueOf(categoryType.name()))
                                .product(product)
                                .build())

                )
        );
        return savedCategories;
    }

    private List<ProductImage> saveProductImages(ProductPostDTO productPostDTO, Product product) {
        List<ProductImage> savedImages = new ArrayList<>();

        if (productPostDTO.images() != null && !productPostDTO.images().isEmpty()) {
            productPostDTO.images().forEach(multipartFile -> {
                if (!multipartFile.isEmpty()) {
                    savedImages.add(productImageRepository.save(
                            ProductImage.builder()
                                    .product(product)
                                    .ext(multipartFile.getContentType())
                                    .name(multipartFile.getOriginalFilename())
                                    .url("url")
                                    .status("status")
                                    .build())
                    );
                }
            });
        }
        return savedImages;
    }
}
