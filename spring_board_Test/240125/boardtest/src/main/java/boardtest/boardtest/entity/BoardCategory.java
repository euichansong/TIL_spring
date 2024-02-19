package boardtest.boardtest.entity;

public enum BoardCategory {
    AAA, BBB, CCC, DDD;

    // article 연관관계 메서드 of 정의
    public static BoardCategory of(String category) {
        if (category.equalsIgnoreCase("aaa")) return BoardCategory.AAA;
        else if (category.equalsIgnoreCase("bbb")) return BoardCategory.BBB;
        else if (category.equalsIgnoreCase("ccc")) return BoardCategory.CCC;
        else if (category.equalsIgnoreCase("ddd")) return BoardCategory.DDD;
        else return null;
    }
}

