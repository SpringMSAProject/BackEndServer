package msa.project.monologicserver.api.controller;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductDTO;
import msa.project.monologicserver.application.ProductService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductContoller {

    private final ProductService productService;
    @PostMapping("/")
    public ApiResponse<String> resist(ProductDTO pdto) {
        return ApiResponse.success(productService.resist(pdto));
    }



}
