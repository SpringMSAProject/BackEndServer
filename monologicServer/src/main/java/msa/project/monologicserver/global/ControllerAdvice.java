package msa.project.monologicserver.global;

import static msa.project.monologicserver.global.error.code.CommonErrorCode.LogType.ERROR;
import static msa.project.monologicserver.global.error.code.CommonErrorCode.LogType.WARN;

import lombok.extern.slf4j.Slf4j;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.code.ErrorCode;
import msa.project.monologicserver.global.error.exception.BusinessException;
import org.hibernate.exception.SQLGrammarException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    private final static String SUFFIX = ": {}";

    @ExceptionHandler({BusinessException.class})
    protected ResponseEntity<ApiResponse> handleObtServerException(BusinessException e) {
        errorLog("Server Exception occurred", e);
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ApiResponse.failure(e.getErrorCode()));
    }


    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        errorLog("HttpRequestMethodNotSupportedException", e);
        return ResponseEntity.status(e.getStatusCode().value()).body(ApiResponse.failure(
            CommonErrorCode.BAD_REQUEST));
    }

    @ExceptionHandler({IllegalStateException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<ApiResponse> handleIllegalStateException(IllegalStateException e) {
        errorLog("IllegalStateException", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(ApiResponse.failure(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException e) {
        errorLog("Method Argument Validation failed", e);

        return ResponseEntity.status(e.getStatusCode().value()).body(ApiResponse.failure(CommonErrorCode.BAD_REQUEST));
    }

    @ExceptionHandler(SQLGrammarException.class)
    protected ResponseEntity<ApiResponse> handleExceptions(SQLGrammarException e) {
        errorLog("SQL 구문 에러. 스키마 전환 오류거나 테이블 또는 칼럼을 못찾습니다.", e);
        return ResponseEntity.status(e.getErrorCode()).body(ApiResponse.failure(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        errorLog("handleHttpMessageNotReadableException ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(ApiResponse.failure(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        errorLog("handleException", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(ApiResponse.failure(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }

    private void errorLog(String errorMessage, Throwable throwable) {
        if (throwable instanceof BusinessException) {
            switch (((BusinessException) throwable).getErrorCode().getLogType()) {
                case WARN -> log.warn(errorMessage, throwable);
                case ERROR -> log.error(errorMessage, throwable);
            }
        } else {
            log.error(errorMessage, throwable);
        }
    }

}