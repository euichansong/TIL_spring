package newtest.newtest.entity;

public enum ArticleCategory {
    AAA, BBB, CCC, DDD;

    // article 연관관계 메서드 of 정의
    public static ArticleCategory of(String category) {
        if (category.equalsIgnoreCase("aaa")) return ArticleCategory.AAA;
        else if (category.equalsIgnoreCase("bbb")) return ArticleCategory.BBB;
        else if (category.equalsIgnoreCase("ccc")) return ArticleCategory.CCC;
        else if (category.equalsIgnoreCase("ddd")) return ArticleCategory.DDD;
        else return null;
    }
}
