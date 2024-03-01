package msa.project.monologicserver.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "가지마켓",
                description = "프로젝트 초기 세팅",
                version = "1.0.0"))
@Configuration
public class SwaggerConfig {

    @Getter
    @RequiredArgsConstructor
    enum Menu {

        MEMBER("회원", "/member/**"),
        PRODUCT("상품", "/product/**"),
        PRODUCT_IMGS("상품이미지", "/ex"),
        CATEGORY("카테고리", "/category/**"),
        CHATTING("채팅", "/ex"),
        NOTIFICATION("알림", "/ex"),
        REPORT("신고 리포트", "/ex"),
        REVIEW("리뷰", "/ex"),
        LIKES("좋아요", "/ex"),
        BOOKMARK("찜하기", "/ex");

        private final String groupName;
        private final String paths;
    }

    private GroupedOpenApi buildGroupedOpenApi(Menu menu) {
        return GroupedOpenApi.builder()
                .group(menu.getGroupName())
                .pathsToMatch(menu.getPaths())
                .build();
    }

    @Bean
    public GroupedOpenApi member() {
        return buildGroupedOpenApi(Menu.MEMBER);
    }

    @Bean
    public GroupedOpenApi product() {
        return buildGroupedOpenApi(Menu.PRODUCT);
    }

    @Bean
    public GroupedOpenApi productImgs() {
        return buildGroupedOpenApi(Menu.PRODUCT_IMGS);
    }

    @Bean
    public GroupedOpenApi category() {
        return buildGroupedOpenApi(Menu.CATEGORY);
    }

    @Bean
    public GroupedOpenApi chatting() {
        return buildGroupedOpenApi(Menu.CHATTING);
    }

    @Bean
    public GroupedOpenApi notification() {
        return buildGroupedOpenApi(Menu.NOTIFICATION);
    }

    @Bean
    public GroupedOpenApi report() {
        return buildGroupedOpenApi(Menu.REPORT);
    }

    @Bean
    public GroupedOpenApi review() {
        return buildGroupedOpenApi(Menu.REVIEW);
    }

    @Bean
    public GroupedOpenApi likes() {
        return buildGroupedOpenApi(Menu.LIKES);
    }

    @Bean
    public GroupedOpenApi bookmark() {
        return buildGroupedOpenApi(Menu.BOOKMARK);
    }

}
