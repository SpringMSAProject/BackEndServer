package msa.project.monologicserver.application;


import static msa.project.monologicserver.global.validation.ListValidation.listValidation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductInsertRequestDTO;
import msa.project.monologicserver.api.dto.req.product.ProductUpdateRequestDTO;
import msa.project.monologicserver.domain.product.entity.Category;
import msa.project.monologicserver.domain.product.entity.CategoryRepository;
import msa.project.monologicserver.domain.product.entity.CategoryType;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.domain.product.entity.ProductImage;
import msa.project.monologicserver.domain.product.entity.ProductImageRepository;
import msa.project.monologicserver.domain.product.repository.ProductRepository;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public String addProduct(ProductInsertRequestDTO requestDTO) {

        // List는 Valid 어노테이션으로 유효성 검사를 사용할 수 없어 직접 구현.
        listValidation(requestDTO.images());

        final Product product = productRepository.save(requestDTO.of());

        // 카테고리 저장
        saveCategories(requestDTO.categories(), product);

        // 이미지 저장
        saveImage(requestDTO.images(), product);

        return product.getId().toString();
    }

    private void saveCategories(List<CategoryType> categoriesRequestDTO, Product product) {
        final List<Category> categories = categoriesRequestDTO.stream()
            .map(i->Category.builder().category(i).product(product).build())
            .toList();
        product.setCategories(categories);
        categoryRepository.saveAll(categories);
    }

    private void saveImage(List<MultipartFile> images, Product product) {
        final MultipartFile thumbnailImage = images.get(0);
        if (thumbnailImage.getContentType() == null) {
            throw new BusinessException(CommonErrorCode.BAD_REQUEST);
        }
        // 썸네일 지정 (S3환경에서는 name이 아닌 S3 url)
        product.setThumbImg(thumbnailImage.getOriginalFilename());

        //이미지 저장
        images.forEach(multipartFile ->
            // + 추가로 S3에 집어넣는 함수 추가해야 함
            productImageRepository.save(
                ProductImage.builder()
                    .productId(product)
                    .ext(multipartFile.getContentType())
                    .name(multipartFile.getName())
                    .url("여기에 생성한 s3 url")
                    .status("status")
                    .build()
            )
        );
    }


    @Transactional
    public Long deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new BusinessException(CommonErrorCode.PRODUCT_IS_NOT_FOUND);
        } else {
            productRepository.deleteById(productId);
            productImageRepository.deleteAll(productImageRepository.findByProductId(productId));
        }
        return productId;
    }

    public Long updateProject(Long productId, ProductUpdateRequestDTO requestDTO) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException(CommonErrorCode.PRODUCT_IS_NOT_FOUND));

        product.update(requestDTO);

        //카테고리
        updateCategory(productId, requestDTO, product);

        //이미지
        updateImage(productId, requestDTO, product);

        return productId;
    }

    private void updateImage(Long productId, ProductUpdateRequestDTO requestDTO, Product product) {
        productImageRepository.deleteAllByProductId(productId);
        saveImage(requestDTO.images(), product);
    }

    private void updateCategory(Long productId, ProductUpdateRequestDTO requestDTO,
        Product product) {
        categoryRepository.deleteAllByProductId(productId);
        saveCategories(requestDTO.categories(), product);

        product.setCategories(requestDTO.categories().stream()
            .map(i->Category.builder().category(i).product(product).build())
            .toList());
    }
}
