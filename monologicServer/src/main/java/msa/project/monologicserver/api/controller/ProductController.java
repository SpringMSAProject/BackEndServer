package msa.project.monologicserver.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductRegisterDTO;
import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.api.dto.res.product.ProductDataResponseDto;
import msa.project.monologicserver.application.ProductService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "상품", description = "swagger Test 상품")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/{memberId}")
    @Operation(summary = "C", description = "상품 등록")
    public ResponseEntity<?> registerProduct(
            @PathVariable String memberId,
            @Valid @RequestBody ProductRegisterDTO productRegisterDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(productService.registerProduct(memberId, productRegisterDTO)));
    }

    @PutMapping("/{productId}")
    @Operation(summary = "RU", description = "상품 수정")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRegisterDTO productRegisterDTO
    ) {
        return ResponseEntity
                .ok(ApiResponse.success(productService.updateProduct(productId, productRegisterDTO)));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "RU", description = "상품 조회")
    public ResponseEntity<?> read(@PathVariable Long productId) {
        return ResponseEntity
                .ok(ApiResponse.success(productService.readProduct(productId)));
    }


    @PostMapping("/")
    @Operation(summary = "R", description = "모든 상품 조회")
    public ResponseEntity<?> readAll(@RequestBody SearchConditionDto searchConditionDto) {

        searchConditionDto.toString();

        return ResponseEntity
                .ok(ApiResponse.success(productService.readAll(searchConditionDto)));
    }

//    @GetMapping("/categories/{categoryId}")
//    @Operation(summary = "R", description = "카테고리별 모든 상품 조회")
//    public ResponseEntity<?> readByCategory(Pageable pageable, @PathVariable Long categoryId) {
//        return ResponseEntity
//                .ok(ApiResponse.success(productService.readByCategory(pageable, categoryId)));
//    }

    @PostMapping("/{productId}/{memberId}")
    @Operation(summary = "RU", description = "상품 좋아요")
    public ResponseEntity<?> likeProduct(
            @PathVariable Long productId,
            @PathVariable String memberId
    ) {
        return ResponseEntity
                .ok(ApiResponse.success(productService.likeProduct(productId, memberId)));
    }

}
