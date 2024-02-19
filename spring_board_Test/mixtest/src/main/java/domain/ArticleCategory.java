package domain;

public enum ArticleCategory {

    TIP, FREE, QNA, SHOW;

    public static ArticleCategory of(String category) {
        if (category.equalsIgnoreCase("tip")) return ArticleCategory.TIP;
        else if (category.equalsIgnoreCase("free")) return ArticleCategory.FREE;
        else if (category.equalsIgnoreCase("qna")) return ArticleCategory.QNA;
        else if (category.equalsIgnoreCase("show")) return ArticleCategory.SHOW;
        else return null;
    }

}