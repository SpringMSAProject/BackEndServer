package msa.project.monologicserver.application;

import jakarta.validation.Valid;
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

    public Long registerProduct(String memberId, @Valid ProductRegisterDTO productRegisterDTO) {
        Member member = getMember(memberId);
        Category category = getCategory(productRegisterDTO.getCategory());
        Product product = productRepository.save(productRegisterDTO.toEntity(member, category));

        return product.getProductId();
    }

    public ProductDataResponseDto findProduct(Long productId) {
        Product product = getEntityById(productId, productRepository);
        product.viewCountPlusOne();

        return ProductDataResponseDto.toProductDataResponseDto(product);
    }

    public ProductDataResponseDto likeProduct(Long productId, String memberId) {
        Product product = getEntityById(productId, productRepository);
        Member member = getMember(memberId);


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

    public Member getMember(String memberId) {
        return memberRepository.findMemberByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.USER_NOT_FOUND));
    }
    public Category getCategory(String category) {
        return categoryRepository.findByCategory(category)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ENTITY_NOT_FOUND));
    }

    public <T, R extends JpaRepository<T, Long>> T getEntityById(Long id, R jpaRepository) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.ENTITY_NOT_FOUND));
    }
}
