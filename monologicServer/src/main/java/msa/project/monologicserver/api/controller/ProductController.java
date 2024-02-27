package msa.project.monologicserver.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductRegisterDTO;
import msa.project.monologicserver.api.dto.res.product.ProductDataResponseDto;
import msa.project.monologicserver.application.ProductService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "상품", description = "swagger Test 상품")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/{memberId}")
    @Operation(summary = "C", description = "상품 등록")
    public ApiResponse<Long> registerProduct(
            @PathVariable String memberId,
            @Valid @RequestBody ProductRegisterDTO productRegisterDTO
    ) {
        return ApiResponse.success(productService.registerProduct(memberId, productRegisterDTO));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "RU", description = "상품 조회")
    public ApiResponse<ProductDataResponseDto> findProduct(@PathVariable Long productId) {
        return ApiResponse.success(productService.findProduct(productId));
    }

    @PostMapping("/{productId}/{memberId}")
    @Operation(summary = "RU", description = "상품 좋아요")
    public ApiResponse<ProductDataResponseDto> likeProduct(
            @PathVariable Long productId,
            @PathVariable String memberId
    ) {
        return ApiResponse.success(productService.likeProduct(productId, memberId));
    }

}
