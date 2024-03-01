package msa.project.monologicserver.application;

import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.api.dto.req.product.ProductDTO;
import msa.project.monologicserver.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import msa.project.monologicserver.domain.product.entity.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public String resist(ProductDTO pdto) {
        Product product = productRepository.save(pdto.toProduct());
        return product.getTitle();
    }

    public List<Product> getList() {
        List<Product> allprocuctList = productRepository.findAll();

        return allprocuctList;
    }

}
