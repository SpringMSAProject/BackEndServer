package msa.project.monologicserver.global.error.code;

import lombok.Getter;

@Getter
public enum CommonErrorCode implements ErrorCode {

    PERMISSION_DENIED("ERROR_001", LogType.ERROR,500,"해당 권한이 올바르지 않습니다. 다시 로그인 해주세요."),

    // Token Error
    WRONG_TOKEN("ERROR_001", LogType.ERROR,500,"잘못된 토큰 정보입니다. 다시 로그인 해주세요."),
    UNSUPPORTED_TOKEN("ERROR_001", LogType.ERROR,500,"지원하지 않은 토큰 형식입니다. 다시 로그인 해주세요."),
    UNKNOWN_TOKEN_ERROR("ERROR_001", LogType.ERROR,500,"알 수 없는 토큰 오류입니다. 다시 로그인 해주세요."),
    EXPIRED_REFRESH_TOKEN("ERROR_001",LogType.ERROR,500, "해당 토큰 정보가 올바르지 않습니다. 다시 로그인 해주세요."),
    EXPIRED_ACCESS_TOKEN("ERROR_001", LogType.ERROR,500,"해당 토큰은 유효기간이 만료입니다. 새로운 토큰을 발급 받아주세요."),
    ACCESS_TOKEN_NOT_EXPIRED("ERROR_001", LogType.ERROR,500,"해당 토큰의 유효기간이 만료되지 않았습니다."),

    // Common Error
    INVALID_INPUT_VALUE("COMMON_001",LogType.ERROR,500, "입력값에 문제가 있습니다. 확인 후 다시 시도해 주세요."),
    METHOD_NOT_ALLOWED("COMMON_001", LogType.ERROR,500,"입력값에 문제가 있습니다. 확인 후 다시 시도해 주세요."),
    INTERNAL_SERVER_ERROR("COMMON_001",LogType.ERROR,500, "Server Error"),
    INVALID_TYPE_VALUE("COMMON_001", LogType.ERROR,500,"올바른 입력 방법이 아닙니다."),
    ENTITY_NOT_FOUND("COMMON_001",LogType.ERROR,500, "구성 요소를 찾지 못했습니다."),
    ENUM_MAPPING_NOT_FOUND("COMMON_001", LogType.ERROR,500,"DB와 저장된 상태 정보를 찾을 수 없습니다."),
    ENUM_MAPPING_TO_DATABASE("COMMON_001",LogType.ERROR,500, "DB 저장중 매핑되는 정보를 찾지 못했습니다."),
    ENUM_MAPPING_TO_ENTITY("COMMON_001",LogType.ERROR,500, "DB와 매핑되는 정보를 찾지 못했습니다."),
    PAYLOAD_TOO_LARGE("COMMON_001",LogType.ERROR,500, "요청된 정보가 너무 큽니다."),
    REQUEST_HEADER_NOT_FOUND("COMMON_001",LogType.ERROR,500, "요청된 헤더 정보가 올바르지 않습니다."),

    // Auth Error
    USER_NOT_FOUND("AUTH_001",LogType.ERROR,500, "사용자를 찾을 수 없습니다."),

    // 사용자 관리
    USER_MANAGE_USER_EXISTENCE("COMMON_001",LogType.ERROR,500, "해당 사용자 ID는 이미 존재합니다."),
    USER_MANAGE_USER_DELETE_FAIL("COMMON_001",LogType.ERROR,500, "사용자 삭제가 정상적으로 동작하지 않았습니다."),
    USER_MANAGE_USER_UPDATE_FAIL("COMMON_001",LogType.ERROR,500, "사용자 수정이 정상적으로 동작하지 않았습니다."),
    USER_MANAGE_USER_ROLE_FAIL("COMMON_001",LogType.ERROR,500, "사용자 권한 설정과 관련되어 오류가 발생했습니다."),
    USER_MANAGE_USER_NOT_FOUND("COMMON_001",LogType.ERROR,500, "해당 사용자를 찾을 수 없습니다."),

    USER_MANAGE_USER_ID_LENGTH("COMMON_001",LogType.ERROR,500, "해당 사용자 ID는 4자 ~ 20자 입니다."),
    USER_MANAGE_USER_NOT_PHONE_MEMBER("COMMON_001",LogType.ERROR,500, "전화번호를 정확히 입력해주세요.(01012341234)"),
    WEATHER_NOT_FOUND("COMMON_001",LogType.ERROR,500, "날씨 정보를 찾지 못했습니다."),

    ALREADY_DELETED("COMMON_001",LogType.ERROR,500, "이미 삭제된 회원 입니다."),
    CANNOT_UPDATE_DELETED_POST("COMMON_001",LogType.ERROR,500, "삭제된 회원은 수정할 수 없습니다."),
    BAD_REQUEST("COMMON_001",LogType.ERROR,400 , "요청이 유효하지 않습니다.");

    private final String code;
    private final String message;
    private final int status;
    private final LogType logType;
    CommonErrorCode(final String code,LogType logType,final int status, final String message) {
        this.message = message;
        this.logType = logType;
        this.status = status;
        this.code = code;
    }
    public enum LogType {
        WARN,
        ERROR
    }
}
