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
    public ApiResponse<String> resist(@ModelAttribute ProductDTO pdto) {
        return ApiResponse.success(productService.resist(pdto));
    }

    @GetMapping("/")
    public List<Product> productAllList() {
        return productService.getAllList();
    }

    @PutMapping("/{productID}")
    public ApiResponse<String> update(@PathVariable(name = "productID") Long pid, @RequestBody ProductDTO pdto) {
        return ApiResponse.success(productService.update(pid, pdto));
    }

    @DeleteMapping("/{productID}")
    public ApiResponse<String> delete(@PathVariable(name = "productID") Long pid) {
        return ApiResponse.success(productService.delete(pid));
    }
}
