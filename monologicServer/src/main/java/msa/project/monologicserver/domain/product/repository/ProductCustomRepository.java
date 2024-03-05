package msa.project.monologicserver.domain.product.repository;

import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.domain.product.entity.Product;

import java.util.List;

public interface ProductCustomRepository {

    List<Product> readAll(SearchConditionDto searchConditionDto);



}
