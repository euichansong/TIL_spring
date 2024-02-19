package domain.enum_class;


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

/*  GREETING : 가입인사
    FREE : 자유게시판 (ASSOCIATE 회원은 작성 불가)
    ADMIN : 관리자 공지사항
    TOTAL : 전체게시판
*
*/


