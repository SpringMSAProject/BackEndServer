package msa.project.monologicserver.application.product;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductPageRequestDTO;
import msa.project.monologicserver.api.dto.req.product.ProductRequestDTO;
import msa.project.monologicserver.api.dto.res.product.ProductPageResponseDTO;
import msa.project.monologicserver.api.dto.res.product.ProductResponseDTO;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.member.MemberRepository;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.ProductImage;
import msa.project.monologicserver.domain.product.repository.CategoryRepository;
import msa.project.monologicserver.domain.product.repository.ProductQueryDslRepository;
import msa.project.monologicserver.domain.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ProductQueryDslRepository productQueryDslRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Member member = memberRepository.findMemberById(productRequestDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // 상품 및 상품 이미지 생성
        Product product = productRequestDTO.createProduct(member, category);
        List<ProductImage> productImages = product.getImages();
        ;

        // 상품과 상품 이미지 저장
        product.setImages(productImages);
        productRepository.save(product);

        // 응답 DTO 생성
        return ProductResponseDTO.builder()
                .memberId(productRequestDTO.getMemberId())
                .categoryId(productRequestDTO.getCategoryId())
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .location(productRequestDTO.getLocation())
                .condition(productRequestDTO.getCondition())
                .thumbImg(productRequestDTO.getThumbImg())
                .build();
    }

    public Page<ProductPageResponseDTO> getAllProducts(ProductPageRequestDTO productPageRequestDTO) {
        return productQueryDslRepository.getAllProducts(productPageRequestDTO);
    }

    public Page<ProductPageResponseDTO> getProductsByCategory(Long categoryId, ProductPageRequestDTO productPageRequestDTO) {
        return productQueryDslRepository.getProductsByCategoryId(categoryId, productPageRequestDTO);

    }

    public void updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found for ID: " + productId));

        // 상품 이미지 업데이트
        List<ProductImage> updatedImages = product.getImages();

        product.setImages(updatedImages);

        Member member = memberRepository.findMemberById(productRequestDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        Product updatedProduct = Product.builder()
                .productId(productId)
                .member(member)
                .category(category)
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .location(productRequestDTO.getLocation())
                .condition(productRequestDTO.getCondition())
                .thumbImg(productRequestDTO.getThumbImg())
                .build();

        productRepository.save(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException();
        }

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent() && product.get().getUseYn() == 9) {
            throw new IllegalArgumentException();
        }

        productQueryDslRepository.deleteProductAndImages(productId);
    }

    @Transactional
    public void increaseProductViewCount(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        optionalProduct.ifPresent(product -> {
            product.increaseViewCount();
            productRepository.save(product);
        });
    }

    @Transactional
    public void likeProduct(Long productId) {
        Product product = getProductById(productId);
        product.increaseLikeCount();
        productRepository.save(product);
    }

    @Transactional
    public void unlikeProduct(Long productId) {
        Product product = getProductById(productId);
        product.decreaseLikeCount();
        productRepository.save(product);
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
    }
}
