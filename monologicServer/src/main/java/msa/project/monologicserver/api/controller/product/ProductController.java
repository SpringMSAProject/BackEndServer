package msa.project.monologicserver.api.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductPageRequestDTO;
import msa.project.monologicserver.api.dto.req.product.ProductRequestDTO;
import msa.project.monologicserver.api.dto.res.product.ProductPageResponseDTO;
import msa.project.monologicserver.api.dto.res.product.ProductResponseDTO;
import msa.project.monologicserver.application.product.ProductService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 관련 API")
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping("/")
    @Operation(summary = "상품 등록", description = "새로운 상품 등록")
    public ApiResponse<ProductResponseDTO> create(@RequestBody ProductRequestDTO productRequestDTO) {
        return ApiResponse.success(productService.createProduct(productRequestDTO));
    }

    // 모든 상품 조회
    @GetMapping("/")
    @Operation(summary = "모든 상품 조회", description = "모든 상품 조회")
    public ApiResponse<Page<ProductPageResponseDTO>> readAll(ProductPageRequestDTO productPageRequestDTO) {
        return ApiResponse.success(productService.getAllProducts(productPageRequestDTO));
    }

    // 카테고리별 상품 조회
    @GetMapping("/byCategory")
    @Operation(summary = "카테고리별 상품 조회", description = "특정 카테고리에 속한 상품 조회")
    public ApiResponse<Page<ProductPageResponseDTO>> readByCategory(
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
            @RequestBody ProductRequestDTO productRequestDTO
    ) {
        productService.updateProduct(productId, productRequestDTO);
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
