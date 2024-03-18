package msa.project.monologicserver.domain.product.entity;

import lombok.Getter;

import static msa.project.monologicserver.domain.product.entity.CategoryList.MainCategory.*;

@Getter
public enum CategoryList {
    HOME(APPLIANCES, "가정 가전"),
    BUSINESS(APPLIANCES, "기업 가전"),

    MENS(FASHION, "남성 패션"),
    WOMEN(FASHION, "여성 패션"),
    KIDS(FASHION, "키즈 패션"),

    RING(ACCESSORY, "반지"),
    EARRING(ACCESSORY, "귀걸이"),

    LOTION(BEAUTY, "로션"),
    SKIN(BEAUTY, "스킨"),
    COSMETICS(BEAUTY, "화장품");

    public MainCategory mainCategory;
    private String subCategory;

    CategoryList(MainCategory main, String sub) {
        this.mainCategory = main;
        this.subCategory = sub;
    }

    public enum MainCategory {
        APPLIANCES("가전"),
        FASHION("패션/잡화"),
        ACCESSORY("악세사리"),
        DIGITAL("디지털 용품"),
        SPORT("스포츠"),
        LIFE("생활"),
        BEAUTY("뷰티");

        private String mainName;

        MainCategory(String mainName) {
            this.mainName = mainName;
        }
    }

}
