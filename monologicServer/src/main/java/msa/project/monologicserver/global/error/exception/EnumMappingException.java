package msa.project.monologicserver.global.error.exception;

import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.code.ErrorCode;

public class EnumMappingException extends BusinessException {

    public EnumMappingException() {
        super(CommonErrorCode.ENUM_MAPPING_NOT_FOUND);
    }

    public EnumMappingException(ErrorCode errorCode) {
        super(errorCode);
    }

}
