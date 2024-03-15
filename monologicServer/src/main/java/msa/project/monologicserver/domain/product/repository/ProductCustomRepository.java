package msa.project.monologicserver.domain.product.repository;

import msa.project.monologicserver.api.dto.req.product.SearchConditionDto;
import msa.project.monologicserver.api.dto.res.product.SearchData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCustomRepository {

    Page<SearchData> readAll(SearchConditionDto searchConditionDto, Pageable pageable);

}
