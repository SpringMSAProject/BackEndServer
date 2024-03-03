package msa.project.monologicserver.api.dto.req.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProductRequestDTO {

    @Schema(description = "회원 ID", example = "1")
    private String memberId;

    @Schema(description = "카테고리 ID 리스트", example = "[1, 2, 3]")
    private List<Long> categoryId;

    @Schema(description = "상품 제목", example = "앨범")
    private String title;

    @Schema(description = "상품 설명", example = "아이유 앨범 입니다.")
    private String description;

    @Schema(description = "가격", example = "10000")
    private int price;

    @Schema(description = "거래 희망 장소", example = "서울시 강남구")
    private String location;

    @Schema(description = "물품 상태", example = "S")
    private String condition;

    @Schema(description = "썸네일 이미지 파일경로", example = "https://i.namu.wiki/i/R0AhIJhNi8fkU2Al72pglkrT8QenAaCJd1as-d_iY6MC8nub1iI5VzIqzJlLa-1uzZm--TkB-KHFiT-P-t7bEg.webp")
    private String thumbImg;
}
