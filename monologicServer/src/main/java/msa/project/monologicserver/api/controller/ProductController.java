package msa.project.monologicserver.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.project.monologicserver.api.dto.req.product.ProductPostDTO;
import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.application.ProductService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "상품", description = "swagger Test 상품")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/{memberId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "C", description = "상품 등록")
    public ResponseEntity<?> createProduct(
            @PathVariable String memberId,
            @Valid @ModelAttribute ProductPostDTO productPostDTO
    ) {
        log.info("====\n", productPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(productService.createProduct(memberId, productPostDTO)));
    }

    @PutMapping("/{productId}")
    @Operation(summary = "RU", description = "상품 수정")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @Valid @ModelAttribute ProductPostDTO productPostDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(productService.updateProduct(productId, productPostDTO)));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "RU", description = "상품 조회 및 조회수 증가")
    public ResponseEntity<?> read(@PathVariable Long productId) {
        return ResponseEntity
                .ok(ApiResponse.success(productService.readProduct(productId)));
    }


    @PostMapping("/")
    @Operation(summary = "R", description = "모든 상품 조회")
    public ResponseEntity<?> readAll(@RequestBody SearchConditionDto searchConditionDto, Pageable pageable) {
        return ResponseEntity
                .ok(ApiResponse.success(productService.readAll(searchConditionDto, pageable)));
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
