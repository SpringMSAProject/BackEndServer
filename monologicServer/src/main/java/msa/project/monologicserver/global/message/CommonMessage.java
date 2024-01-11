package msa.project.monologicserver.global.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonMessage {

    public static final String COMMON_SUCCESS_MESSAGE = "메시지 출력에 성공했습니다.";
    public static final String SELECT_SUCCESS = "정상적으로 데이터를 조회 하였습니다.";
    public static final String CREATE_SUCCESS = "정상적으로 데이터를 생성 하였습니다";
    public static final String UPDATE_SUCCESS = "정상적으로 데이터를 수정 하였습니다";
    public static final String DELETE_SUCCESS = "정상적으로 데이터를 삭제 하였습니다";

    public static final String SELECT_FAIL = "데이터 조회에 실패 하였습니다.";
    // 유효성 관련 공통 사항
    public static final String VALID_INPUT_TYPE_NOT_NULL = "NULL 값이 들어올 수 없습니다.";
    public static final String VALID_INPUT_TYPE_NOT_EMPTY = "값을 입력해주세요.";
    public static final String VALID_NOT_NULL_DATE = "날짜의 형식이 맞지 않거나 NULL 값이 있습니다.";
    public static final String VALID_STRING_NOT_EMPTY = "해당 값은 빈 값이 들어올 수 없습니다.";
    public static final String VALID_LIST_SIZE_EMPTY = "최소 1개 이상의 리스트 값이 필요합니다.";
    public static final String PHONE_REGEX_MESSAGE = "전화번호는 숫자만 입력 가능합니다.";
    public static final String SYSTEM_AUTHORITY_REGEX_MESSAGE = "시스템 권한 정보가 올바르지 않습니다.";
    public static final String PASSWORD_REGEX_MESSAGE = "비밀번호는 영문,숫자,특수문자 순서로 8~20자 입니다.";

    public static final String INVALID_USER_ID = "사용자 ID 형식이 맞지 않습니다.";

    // 사용자 관리
    public static final String USER_CREATE_SUCCESS = "사용자 생성에 성공하였습니다.";
    public static final String USER_SELECT_SUCCESS = "사용자 조회에 성공하였습니다.";
    public static final String USER_UPDATE_SUCCESS = "사용자 수정에 성공하였습니다.";
    public static final String USER_DELETE_SUCCESS = "사용자 삭제에 성공하였습니다.";
    public static final String USER_CHECK_DUPLICATE_SUCCESS = "해당 사용자는 등록 가능합니다";

    // 개인 정보 수정
    public static final String MYSELF_UPDATE_SUCCESS = "개인 정보 수정에 성공하였습니다.";

}
