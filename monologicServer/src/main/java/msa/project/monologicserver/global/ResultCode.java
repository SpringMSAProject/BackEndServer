package msa.project.monologicserver.global;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS("성공"),
    FAIL("잘못된 요청입니다.");

    private final String message;

    ResultCode(String message) {
        this.message = message;
    }
}
