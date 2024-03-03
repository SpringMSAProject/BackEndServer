package msa.project.monologicserver.application.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.project.monologicserver.api.dto.req.product.ProductPageRequestDTO;
import msa.project.monologicserver.api.dto.req.product.ProductRequestDTO;
import msa.project.monologicserver.api.dto.res.product.ProductPageResponseDTO;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.member.MemberRepository;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.ProductCategory;
import msa.project.monologicserver.domain.product.entity.ProductImage;
import msa.project.monologicserver.domain.product.repository.CategoryRepository;
import msa.project.monologicserver.domain.product.repository.ProductQueryDslRepository;
import msa.project.monologicserver.domain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductQueryDslRepository productQueryDslRepository;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 상품 생성 메서드
     *
     * @param productRequestDTO 상품 요청 DTO
     * @param productImages     상품 이미지 파일
     * @throws IOException 입출력 예외 발생 시
     */
    public void createProduct(ProductRequestDTO productRequestDTO, MultipartFile[] productImages) throws IOException {
        Member member = memberRepository.findMemberById(productRequestDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        List<Category> categories = new ArrayList<>();
        for (Long categoryId : productRequestDTO.getCategoryId()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
            categories.add(category);
        }

        Product product = Product.builder()
                .member(member)
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .location(productRequestDTO.getLocation())
                .condition(productRequestDTO.getCondition())
                .thumbImg(productRequestDTO.getThumbImg())
                .build();

        for (Category category : categories) {
            ProductCategory productCategory = new ProductCategory(product, category);
            product.addProductCategory(productCategory);
        }

        List<ProductImage> images = processProductImages(product, productImages);
        product.setImages(images);

        productRepository.save(product);
    }

    /**
     * 상품 이미지 처리 메서드
     *
     * @param product       상품 엔티티
     * @param productImages 상품 이미지 파일
     * @return 이미지 리스트
     * @throws IOException 입출력 예외 발생 시
     */
    private List<ProductImage> processProductImages(Product product, MultipartFile[] productImages) throws IOException {
        List<ProductImage> images = new ArrayList<>();
        if (productImages != null) {
            for (MultipartFile imageFile : productImages) {
                String imageUrl = saveImage(imageFile);
                ProductImage productImage = new ProductImage(product, imageUrl);
                images.add(productImage);
            }
        }
        return images;
    }

    /**
     * 이미지 저장 메서드
     *
     * @param imageFile 이미지 파일
     * @return 저장된 이미지 URL
     * @throws IOException 입출력 예외 발생 시
     */
    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("이미지 파일이 비어 있습니다.");
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
        String contentType = imageFile.getContentType();
        if (!isValidImageContentType(contentType)) {
            throw new IllegalArgumentException("올바른 이미지 형식이 아닙니다.");
        }

        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        File uploadDir = new File(uploadPath);

        // 폴더가 이미 존재하는지 확인하고, 없는 경우 폴더를 생성합니다.
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("폴더 생성에 실패했습니다.");
        }

        String newFileName = UUID.randomUUID() + "." + ext;
        File destFile = new File(uploadDir, newFileName);
        imageFile.transferTo(destFile);

        return uploadPath + "/" + newFileName;
    }

    /**
     * 이미지 형식 유효성 검사 메서드
     *
     * @param contentType 이미지 컨텐츠 타입
     * @return 유효한 이미지 여부
     */
    private boolean isValidImageContentType(String contentType) {
        return contentType != null && (contentType.startsWith("image/jpeg") || contentType.startsWith("image/png"));
    }

    /**
     * 특정 카테고리에 속한 상품을 페이징하여 반환하는 메서드
     *
     * @param categoryId            카테고리 ID
     * @param productPageRequestDTO 상품 페이지 요청 DTO
     * @return 상품 페이지 응답 DTO
     */
    public ProductPageResponseDTO getProductsByCategory(Long categoryId, ProductPageRequestDTO productPageRequestDTO) {
        return productQueryDslRepository.getProducts(categoryId, productPageRequestDTO);
    }

    /**
     * 상품을 업데이트하는 메서드
     *
     * @param productId         상품 ID
     * @param productRequestDTO 상품 요청 DTO
     * @param productImages     상품 이미지 파일
     * @throws IOException 입출력 예외 발생 시
     */
    public void updateProduct(Long productId, ProductRequestDTO productRequestDTO, MultipartFile[] productImages) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        if (productImages != null) {
            List<ProductImage> images = new ArrayList<>();
            for (MultipartFile imageFile : productImages) {
                String imageUrl = saveImage(imageFile);
                ProductImage productImage = new ProductImage(product, imageUrl); // 생성자 수정
                images.add(productImage);
            }
            product.setImages(images);
        }

        Product updatedProduct = Product.builder()
                .productId(product.getProductId())
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .location(productRequestDTO.getLocation())
                .condition(productRequestDTO.getCondition())
                .thumbImg(productRequestDTO.getThumbImg())
                .images(product.getImages())
                .build();

        // 새로운 카테고리 ID로 카테고리 정보 조회 및 설정
        if (productRequestDTO.getCategoryId() != null) {
            List<Category> newCategories = new ArrayList<>();
            for (Long categoryId : productRequestDTO.getCategoryId()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
                newCategories.add(category);
            }
            product.getCategories().clear(); // 기존 카테고리 제거
            product.setCategories(newCategories); // 새로운 카테고리 추가
        }

        productRepository.save(updatedProduct);
    }

    /**
     * 상품을 삭제하는 메서드
     *
     * @param productId 상품 ID
     */
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 상품 이미지 파일 삭제
        for (ProductImage image : product.getImages()) {
            File imageFile = new File(image.getImageUrl());
            if (imageFile.exists()) {
                if (!imageFile.delete()) {
                    log.warn("이미지 파일을 삭제할 수 없습니다: {}", image.getImageUrl());
                }
            }
        }

        // 상품 삭제
        productQueryDslRepository.deleteProduct(productId);
    }

    /**
     * 상품 조회 수를 증가시키는 메서드
     *
     * @param productId 상품 ID
     */
    public void increaseProductViewCount(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        product.increaseViewCount();
        productRepository.save(product);
    }

    /**
     * 상품을 좋아요하는 메서드
     *
     * @param productId 상품 ID
     */
    public void likeProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        product.increaseLikeCount();
        productRepository.save(product);
    }

    /**
     * 상품 좋아요를 취소하는 메서드
     *
     * @param productId 상품 ID
     */
    public void unlikeProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        product.decreaseLikeCount();
        productRepository.save(product);
    }
}
