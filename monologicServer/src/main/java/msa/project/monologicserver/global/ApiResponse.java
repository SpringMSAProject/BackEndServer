package msa.project.monologicserver.global;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.project.monologicserver.global.error.code.CommonErrorCode;
import msa.project.monologicserver.global.error.code.ErrorCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private ResultCode resultCode;
    private ErrorCode errorCode;
    private String message;
    private T data;

    @Builder(access = AccessLevel.PROTECTED)
    private ApiResponse(ResultCode resultCode, ErrorCode errorCode, String message, T data) {
        this.resultCode = resultCode;
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
            .resultCode(ResultCode.SUCCESS)
            .message(ResultCode.SUCCESS.getMessage())
            .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .resultCode(ResultCode.SUCCESS)
            .message(ResultCode.SUCCESS.getMessage())
            .data(data)
            .build();
    }

    public static <T> ApiResponse<List<T>> success(List<T> data) {
        return ApiResponse.<List<T>>builder()
            .resultCode(ResultCode.SUCCESS)
            .message(ResultCode.SUCCESS.getMessage())
            .data(data)
            .build();
    }

    public static <T> ApiResponse<T> failure(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
            .resultCode(ResultCode.FAIL)
            .errorCode(errorCode)
            .message(errorCode.getMessage())
            .build();
    }

    public static <T> ApiResponse<T> failure(ErrorCode errorCode, String errorMessage) {
        return ApiResponse.<T>builder()
            .resultCode(ResultCode.FAIL)
            .errorCode(errorCode)
            .message(errorMessage)
            .build();
    }
}
