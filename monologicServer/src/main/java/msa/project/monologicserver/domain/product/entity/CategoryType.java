package msa.project.monologicserver.domain.product.entity;

public enum CategoryType {
    FASHION("패션/잡화"),
    ACCESSORY("악세사리"),
    DIGITAL("디지털 용품"),
    SPORT("스포츠"),
    LIFE("생활"),
    BEAUTY("뷰티");

    private String categoryName;

    CategoryType(String categoryName) {
        this.categoryName = categoryName;
    }
}
