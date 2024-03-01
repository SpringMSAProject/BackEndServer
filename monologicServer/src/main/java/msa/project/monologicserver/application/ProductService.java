package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductDTO;
import msa.project.monologicserver.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import msa.project.monologicserver.domain.product.entity.Product;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public String resist(ProductDTO pdto) {
        Product product = productRepository.save(pdto.toProduct());
        return product.getTitle();
    }
}
