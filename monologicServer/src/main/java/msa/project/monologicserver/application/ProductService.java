package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductRegisterDTO;
import msa.project.monologicserver.api.dto.res.product.ProductDataResponseDto;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.domain.member.MemberRepository;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.Like;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.repository.CategoryRepository;
import msa.project.monologicserver.domain.product.repository.LikeRepository;
import msa.project.monologicserver.domain.product.repository.ProductRepository;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;

    public Long registerProduct(String memberId, ProductRegisterDTO productRegisterDTO) {
        Member member = getMemberEntity(memberId);
        Category category = getEntityById(productRegisterDTO.category(), categoryRepository);
        Product product = productRepository.save(productRegisterDTO.toEntity(member, category));

        return product.getProductId();
    }

    public ProductDataResponseDto updateProduct(Long productId, ProductRegisterDTO productRegisterDTO) {
        Product product = getEntityById(productId, productRepository);
        Category category = getEntityById(productRegisterDTO.category(), categoryRepository);

        product.update(productRegisterDTO,category);

        return ProductDataResponseDto.toProductDataResponseDto(product);
    }

    public ProductDataResponseDto readProduct(Long productId) {
        Product product = getEntityById(productId, productRepository);
        product.viewCountPlusOne();

        return ProductDataResponseDto.toProductDataResponseDto(product);
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

        return ProductDataResponseDto.toProductDataResponseDto(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDataResponseDto> readAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductDataResponseDto::toProductDataResponseDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductDataResponseDto> readByCategory(Pageable pageable, Long category) {

//        for (Sort.Order order : pageable.getSort()) {
//            if (order.getDirection() == Sort.Direction.DESC) {
//                    return productRepository.findByCategoryIdOrderByCategoryIdDesc(pageable, getEntityById(category, categoryRepository))
//                            .map(ProductDataResponseDto::toProductDataResponseDto);
//            }
//        }
        Category entityById = getEntityById(category, categoryRepository);
        System.out.println("==========================");
        return productRepository.findByCategoryId(entityById, pageable)
                .map(ProductDataResponseDto::toProductDataResponseDto);
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
