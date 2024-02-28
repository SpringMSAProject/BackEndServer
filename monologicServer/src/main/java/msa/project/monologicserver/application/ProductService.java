package msa.project.monologicserver.application;


import static msa.project.monologicserver.global.validation.ListValidation.listValidation;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductInsertRequestDTO;
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

    @Transactional
    public String addProduct(ProductInsertRequestDTO requestDTO) {

        // List는 Valid 어노테이션으로 유효성 검사를 사용할 수 없어 직접 구현.
        listValidation(requestDTO.images());

        final Product product = productRepository.save(requestDTO.of());

        final MultipartFile thumbnailImage = requestDTO.images().get(0);
        if(thumbnailImage.getContentType()==null){
            throw new BusinessException(CommonErrorCode.BAD_REQUEST);
        }
        // 썸네일 지정 (S3환경에서는 name이 아닌 S3 url)
        product.setThumbImg(thumbnailImage.getOriginalFilename());

        //이미지 저장
        requestDTO.images().forEach(multipartFile ->
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
        return product.getId().toString();
    }



}
