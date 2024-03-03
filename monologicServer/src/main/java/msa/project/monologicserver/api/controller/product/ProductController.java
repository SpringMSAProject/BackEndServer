package msa.project.monologicserver.api.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductPageRequestDTO;
import msa.project.monologicserver.api.dto.req.product.ProductRequestDTO;
import msa.project.monologicserver.api.dto.res.product.ProductPageResponseDTO;
import msa.project.monologicserver.application.product.ProductService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 관련 API")
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping(value = "/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "상품 생성", description = "새로운 상품을 생성합니다.")
    public ApiResponse<Void> create(
            @Parameter(
                    description = "multipart/form-data 형식의 이미지 리스트를 input으로 받습니다. 이때 key 값은 multipartFile 입니다.")
            @RequestPart ProductRequestDTO productRequestDTO,
            @RequestPart(value = "productImages", required = false) MultipartFile[] productImages
    ) throws IOException {
        productService.createProduct(productRequestDTO, productImages);
        return ApiResponse.success();
    }

    // 카테고리별 상품 조회
    @GetMapping("/byCategory")
    @Operation(summary = "카테고리별 상품 조회", description = "특정 카테고리에 속한 상품 조회")
    public ApiResponse<ProductPageResponseDTO> readByCategory(
            @RequestParam long categoryId,
            ProductPageRequestDTO productPageRequestDTO
    ) {
        return ApiResponse.success(productService.getProductsByCategory(categoryId, productPageRequestDTO));
    }

    // 상품 수정
    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정", description = "상품 정보 수정")
    public ApiResponse<Void> update(
            @PathVariable long productId,
            @RequestParam("productRequestDTO") ProductRequestDTO productRequestDTO,
            @RequestParam(value = "productImages", required = false) MultipartFile[] productImages
    ) throws IOException {
        productService.updateProduct(productId, productRequestDTO, productImages);
        return ApiResponse.success();
    }


    // 상품 삭제
    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "특정 상품 삭제")
    public ApiResponse<String> delete(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.success("삭제 완료");
    }

    // 조회수 증가
    @PutMapping("/{productId}/increase-view-count")
    @Operation(summary = "조회수 증가", description = "상품 조회수 증가")
    public ApiResponse<Void> increaseViewCount(@PathVariable long productId) {
        productService.increaseProductViewCount(productId);
        return ApiResponse.success();
    }

    // 좋아요 증가
    @PutMapping("/{productId}/like")
    @Operation(summary = "좋아요 증가", description = "상품 좋아요 증가")
    public ApiResponse<?> likeProduct(@PathVariable Long productId) {
        productService.likeProduct(productId);
        return ApiResponse.success();
    }

    // 좋아요 삭제
    @DeleteMapping("/{likeId}")
    @Operation(summary = "좋아요 삭제", description = "상품 좋아요 삭제")
    public ApiResponse<String> unlikeProduct(@PathVariable Long likeId) {
        productService.unlikeProduct(likeId);
        return ApiResponse.success("좋아요가 삭제되었습니다.");
    }
}
