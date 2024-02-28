package msa.project.monologicserver.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductInsertRequestDTO;
import msa.project.monologicserver.application.ProductService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/")
    public ApiResponse<String> addProduct(
        @Valid @ModelAttribute ProductInsertRequestDTO requestDTO
    ) {

        return ApiResponse.success(productService.addProduct(requestDTO));
    }
}
