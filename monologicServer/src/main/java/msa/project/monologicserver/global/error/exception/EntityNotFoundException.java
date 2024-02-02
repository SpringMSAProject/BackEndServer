package msa.project.monologicserver.global.error.exception;

import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.code.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException() {
        super(CommonErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
