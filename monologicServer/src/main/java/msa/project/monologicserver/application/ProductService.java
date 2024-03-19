package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.project.monologicserver.api.dto.req.product.ProductPostDTO;
import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.api.dto.res.product.ProductDataResponseDto;
import msa.project.monologicserver.api.dto.res.product.SearchData;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.member.MemberRepository;
import msa.project.monologicserver.domain.product.entity.*;
import msa.project.monologicserver.domain.product.repository.*;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public ProductDataResponseDto createProduct(String memberId, ProductPostDTO productPostDTO) {

        checkMainCategoryIncludedInCategories(productPostDTO);

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

        checkMainCategoryIncludedInCategories(productPostDTO);

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

    @Transactional
    public Page<SearchData> readAll(SearchConditionDto searchConditionDto, Pageable pageable) {
        return productRepository.readAll(searchConditionDto, pageable);
    }

    @Transactional
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
        productPostDTO.categories().forEach(categoryList ->
                savedCategories.add(categoryRepository.save(
                        Category.builder()
                                .mainCategory(product.getMainCategory())
                                .subCategory(CategoryList.valueOf(categoryList.name()))
                                .product(product)
                                .build())
                )
        );
        return savedCategories;
    }

    private List<ProductImage> saveProductImages(ProductPostDTO productPostDTO, Product product) {
        List<ProductImage> savedImages = new ArrayList<>();

        if (productPostDTO.images() != null && !productPostDTO.images().isEmpty()) {

            String uploadDir = System.getProperty("user.dir") + "/user_uploads/" + product.getMember().getId();
            File dir = new File(uploadDir);
            if (!dir.exists()){
                dir.mkdirs();
            }

            productPostDTO.images().forEach(multipartFile -> {
                try {
                    //TODO 배치로 일정 기간이 지나면 파일 자동 삭제
                    String originalFilename = multipartFile.getOriginalFilename();
                    String ext = "." + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                    String savedFilename = UUID.randomUUID() + ext;

                    String filePath = Paths.get(uploadDir, savedFilename).toString();
                    byte[] bytes = multipartFile.getBytes();
                    Path path = Paths.get(filePath);
                    Files.write(path, bytes);

                    if (!multipartFile.isEmpty()) {
                        savedImages.add(productImageRepository.save(
                                ProductImage.builder()
                                        .product(product)
                                        .ext(multipartFile.getContentType())
                                        .originalFilename(originalFilename)
                                        .savedFilename(savedFilename)
                                        .url(filePath)
                                        .status("status")
                                        .build())
                        );
                    }
                } catch (IOException e) {
                    log.error("file upload error: {}", e.getMessage());
                    new BusinessException(CommonErrorCode.BAD_REQUEST);
                }

            });
        }

        return savedImages;
    }

    public CategoryList.MainCategory checkMainCategoryIncludedInCategories(ProductPostDTO productPostDTO) {
        for (CategoryList category : productPostDTO.categories()) {
            if (category.getMainCategory().equals(productPostDTO.mainCategory())) {
                return productPostDTO.mainCategory();
            }
        }
        throw new BusinessException(CommonErrorCode.BAD_REQUEST);
    }
}
