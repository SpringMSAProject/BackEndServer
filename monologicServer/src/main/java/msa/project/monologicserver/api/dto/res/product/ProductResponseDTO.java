//package msa.project.monologicserver.api.dto.res.product;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.*;
//
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Builder
//@Schema(description = "상품 응답 정보")
//public class ProductResponseDTO {
//
//    @Schema(description = "상품 ID")
//    private Long productId;
//
//    @Schema(description = "회원 ID")
//    private String memberId;
//
//    @Schema(description = "카테고리 ID")
//    private Long categoryId;
//
//    @Schema(description = "상품 제목")
//    private String title;
//
//    @Schema(description = "상품 설명")
//    private String description;
//
//    @Schema(description = "가격")
//    private int price;
//
//    @Schema(description = "조회 수")
//    private int viewCount;
//
//    @Schema(description = "좋아요 수")
//    private int likeCount;
//
//    @Schema(description = "거래 희망 장소")
//    private String location;
//
//    @Schema(description = "물품 상태")
//    private String condition;
//
//    @Schema(description = "거래 상태")
//    private String status;
//
//    @Schema(description = "썸네일 이미지 파일경로")
//    private String thumbImg;
//}