package msa.project.monologicserver.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductFilteredRequestDTO;
import msa.project.monologicserver.api.dto.req.product.ProductInsertRequestDTO;
import msa.project.monologicserver.api.dto.res.product.FilteredProductResponseDTO;
import msa.project.monologicserver.api.dto.res.product.ProductResponseDTO;
import msa.project.monologicserver.api.dto.req.product.ProductUpdateRequestDTO;
import msa.project.monologicserver.application.ProductService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    @Operation(summary = "C", description = "상품 등록")
    public ApiResponse<Long> addProduct(
        @Valid @ModelAttribute ProductInsertRequestDTO requestDTO
    ) {
        return ApiResponse.success(productService.addProduct(requestDTO));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "R", description = "상품 단건 조회")
    public ApiResponse<ProductResponseDTO> getProduct(
        @Valid @PathVariable Long productId
    ) {
        return ApiResponse.success(productService.getProduct(productId));
    }

    @GetMapping("/")
    @Operation(summary = "R", description = "상품 전체 조회")
    public ApiResponse<List<FilteredProductResponseDTO>> getAllProduct(
        @PageableDefault(size=10) Pageable pageable,
        @RequestBody ProductFilteredRequestDTO requestDto
    ) {
        return ApiResponse.success(productService.getAllProduct(pageable, requestDto));
    }


    @PutMapping(value = "/{productId}")
    @Operation(summary = "U", description = "상품 갱신")
    public ApiResponse<Long> updateProduct(
        @Valid @ModelAttribute ProductUpdateRequestDTO requestDTO,
        @Valid @PathVariable Long productId
    ) {
        System.out.println(requestDTO.images().get(0).getName());
        return ApiResponse.success(productService.updateProject(productId, requestDTO));
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "D", description = "상품 제거")
    public ApiResponse<Long> deleteProduct(
        @Valid @PathVariable Long productId
    ) {
        return ApiResponse.success(productService.deleteProduct(productId));
    }

}
