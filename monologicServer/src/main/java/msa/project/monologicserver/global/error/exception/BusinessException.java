package msa.project.monologicserver.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import msa.project.monologicserver.global.error.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;


}
