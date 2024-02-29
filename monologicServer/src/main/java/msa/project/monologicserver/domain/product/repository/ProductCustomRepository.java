package msa.project.monologicserver.domain.product.repository;

import java.util.List;
import msa.project.monologicserver.api.dto.req.product.ProductFilteredRequestDTO;
import msa.project.monologicserver.api.dto.res.product.FilteredProductResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {

    List<FilteredProductResponseDTO> filteredProducts(Pageable pageable, ProductFilteredRequestDTO requestDto);
}
