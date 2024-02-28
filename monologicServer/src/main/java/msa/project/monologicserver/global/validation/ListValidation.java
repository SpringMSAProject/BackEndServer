package msa.project.monologicserver.global.validation;

import java.util.List;
import msa.project.monologicserver.domain.member.Member;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import org.springframework.data.jpa.repository.JpaRepository;

public class ListValidation {

    public static void listValidation(List<?> lists) {

        if (lists == null || lists.isEmpty()) {
            throw new BusinessException(CommonErrorCode.LIST_CANNOT_EMPTY);
        }
    }

}
