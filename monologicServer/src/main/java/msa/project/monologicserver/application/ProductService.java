package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductDTO;
import msa.project.monologicserver.domain.product.repository.ProductRepository;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import msa.project.monologicserver.domain.product.entity.Product;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private String uploadPath = "C:/Users/SoraCyan/Downloads/imgs";


    @Transactional
    public String resist(ProductDTO pdto) {
        Product product = productRepository.save(pdto.toProduct());

//        // 이미지 파일 저장
//        List<MultipartFile> images = pdto.imgFiles();
//        if (images != null && !images.isEmpty()) {
//            for (MultipartFile image : images) {
//                saveImage(image);
//            }
//        }

        return product.getTitle();
    }

    @Transactional
    public List<Product> getAllList() {
        List<Product> allprocuctList = productRepository.findAll();

        return allprocuctList;
    }

    @Transactional
    public String update(Long pid, ProductDTO pdto) {
        Optional<Product> exist = productRepository.findById(pid);
        exist.orElseThrow(() -> new BusinessException(CommonErrorCode.PRODUCT_NOT_FOUND));
        Product updateProduct = exist.get();
        updateProduct.setTitle(pdto.title());
        // 해당 항목이 없을 수도? 이건 프론트쪽에다가 수정창 띄울 때 기존 것을 불러와달라고 요청을 해야.
        // updateProduct.setCategoryId(pdto.categoryID());
        // updateProduct.setStatus(pdto.status());
        updateProduct.setPrice(pdto.price());
        updateProduct.setUpdatedAt(LocalDateTime.now());
        updateProduct.setCondition(pdto.condition());
        // updateProduct.setDescription(pdto.description());
        // updateProduct.setThumbImg(pdto.thumbImg());
        productRepository.save(updateProduct);
        return updateProduct.getTitle();
    }

    @Transactional
    public String delete(Long pid) {
        final Optional<Product> exist = productRepository.findById(pid);
        exist.orElseThrow(() -> new BusinessException(CommonErrorCode.PRODUCT_NOT_FOUND));
        productRepository.deleteById(exist.get().getProductId());
        return exist.get().getTitle();
    }

//    private void saveImage(MultipartFile image) {
//        try {
//            // 파일 저장 경로 설정 (예시: /uploads)
//            String savePath = uploadPath + "/uploads/";
//
//                // 파일 저장
//            byte[] bytes = image.getBytes();
//            Path path = Paths.get(savePath + image.getOriginalFilename());
//            Files.write(path, bytes);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to save image", e);
//        }
//    }
}
