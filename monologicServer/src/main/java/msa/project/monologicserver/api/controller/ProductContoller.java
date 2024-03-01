package msa.project.monologicserver.api.controller;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductDTO;
import msa.project.monologicserver.application.ProductService;
import msa.project.monologicserver.domain.product.entity.Product;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductContoller {

    private final ProductService productService;
    @PostMapping("/")
    public ApiResponse<String> resist(@RequestBody ProductDTO pdto) {
        return ApiResponse.success(productService.resist(pdto));
    }

    @GetMapping("/")
    public List<Product> productList() {
        return productService.getList();
    }



}
