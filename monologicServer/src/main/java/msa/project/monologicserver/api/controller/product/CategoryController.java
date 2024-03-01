package msa.project.monologicserver.api.controller.product;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.CategoryRequestDTO;
import msa.project.monologicserver.api.dto.res.product.CategoryResponseDTO;
import msa.project.monologicserver.application.product.CategoryService;
import msa.project.monologicserver.global.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "카테고리", description = "swagger Test 카테고리")
public class CategoryController {

    private final CategoryService categoryService;

    //Create
    @PostMapping("/")
    @Operation(summary = "C", description = "카테고리 등록")
    public ApiResponse<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        return ApiResponse.success(categoryService.createCategory(categoryRequestDTO));
    }

    //Read - all
    @GetMapping("/")
    @Operation(summary = "R", description = "카테고리 목록 조회")
    public ApiResponse<List<CategoryResponseDTO>> readAll() {
        return ApiResponse.success(categoryService.getAllCategories());
    }

    //Update
    @PutMapping("/{categoryId}")
    @Operation(summary = "U", description = "카테고리 수정")
    public ApiResponse<CategoryResponseDTO> update(
            @PathVariable long categoryId,
            @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        return ApiResponse.success(categoryService.updateCategory(categoryId, categoryRequestDTO));
    }

    //Delete
    @DeleteMapping("/{categoryId}")
    @Operation(summary = "D", description = "카테고리 삭제")
    public ApiResponse<Long> delete(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.success();
    }
}
