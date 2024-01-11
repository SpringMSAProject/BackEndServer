package msa.project.monologicserver.global.values;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonValue {

    public static final String SUCCESS = "T";
    public static final String FAIL = "F";
    public static final String SUCCESS_KEY = "success";
    public static final String MESSAGE_KEY = "message";
    public static final String CODE = "code";

    public static final int SEARCH_USE_YN = 0;
    public static final int DELETE_USE_YN = 9;


    public static final String AUTHORITY_ADMIN = "관리자";
    public static final String AUTHORITY_USER = "사용자";
    public static final String AUTHORITY_GUEST = "게스트";

}
