package msa.project.monologicserver.global.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "페이지 응답 정보")
public abstract class CommonPageResponseDTO {

    @Min(value = 1)
    @Schema(description = "페이지 수", example = "1", required = true)
    protected int totalPage;

    @Range(min = 0)
    @Schema(description = "총 아이템 수", example = "25", required = true)
    protected Long totalCount;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Schema(description = "시작 날짜", example = "2022-01-01")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Schema(description = "종료 날짜", example = "2024-12-31")
    private LocalDate endDate;

    protected CommonPageResponseDTO(int totalPage, Long totalCount, int pageNumber, int pageSize) {
        this.totalPage = totalPage;
        this.totalCount = totalCount;
    }
}
