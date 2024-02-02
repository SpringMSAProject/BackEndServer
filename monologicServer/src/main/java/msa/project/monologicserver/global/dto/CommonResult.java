package msa.project.monologicserver.global.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResult {

    @Schema(description = "API요청 성공 여부", example = "T/F")
    public String success;

    @Schema(description = "처리결과 메세지")
    public String message;

}
