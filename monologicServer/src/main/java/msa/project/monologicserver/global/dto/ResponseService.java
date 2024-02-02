package msa.project.monologicserver.global.dto;


import msa.project.monologicserver.global.message.CommonMessage;
import msa.project.monologicserver.global.values.CommonValue;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    /**
     * 데이터와 기본 성공 메시지 출력
     *
     * @param data 데이터
     * @return 결과
     */
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        result.setSuccess(CommonValue.SUCCESS);
        result.setMessage(CommonMessage.COMMON_SUCCESS_MESSAGE);
        return result;
    }

    /**
     * 데이터와 성공 메시지 출력
     *
     * @param data    데이터
     * @param message 메시지
     * @return 결과값
     */
    public <T> SingleResult<T> getSingleResult(T data, String message) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        result.setSuccess(CommonValue.SUCCESS);
        result.setMessage(message);
        return result;
    }

    /**
     * 성공 시 메시지만 추가
     *
     * @param message 메시지
     * @return 결과
     */

    public CommonResult getSuccessResult(String message) {
        CommonResult result = new CommonResult();
        result.setSuccess(CommonValue.SUCCESS);
        result.setMessage(message);
        return result;
    }

    /**
     * 일반적으로 실패의 경우 error 패키지에서 처리하지만 특수한 경우를 위해 추가(추후 제거될 수 있음)
     *
     * @param message 메시지
     * @return 결과
     */
    public CommonResult getFailResult(String message) {
        CommonResult result = new CommonResult();
        result.setSuccess(CommonValue.FAIL);
        result.setMessage(message);
        return result;
    }

}
