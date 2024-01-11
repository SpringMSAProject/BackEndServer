package msa.project.monologicserver.global.error.exception;

import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.code.ErrorCode;

public class InvalidValueException extends BusinessException {

    public InvalidValueException() {
        super(CommonErrorCode.INVALID_TYPE_VALUE);
    }

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }

}
