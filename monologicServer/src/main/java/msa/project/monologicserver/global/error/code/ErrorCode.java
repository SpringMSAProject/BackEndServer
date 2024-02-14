package msa.project.monologicserver.global.error.code;

import msa.project.monologicserver.global.error.code.CommonErrorCode.LogType;

public interface ErrorCode {

    String getCode();
    String getMessage();
    LogType getLogType();
    int getStatus();
}
