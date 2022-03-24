package com.example.mycoronav.common;

import org.jetbrains.annotations.NotNull;

public final class Constants2 {
    @NotNull
    public static final String BASE_URL = "http://apis.data.go.kr/B551182/";
    @NotNull
    public static final String KEY = "1xT6NE02ueQJNwDuzxN7bq6UQvuc1mZtoPeSfOOM8iS9x74wKTYORahGxFfbnGTe2a86QjXW4C3/W+RWCuXhtw==";
    @NotNull
    public static final String TYPE = "json";
    @NotNull
    public static final String SERVICE = "Corona19Status";
    public static final int START_INDEX = 1;
    public static final int COUNT_INDEX = 4;
    public static final int START_PAGE = 1;
    public static final int ROW_NUM = 1;

    @NotNull
    public static final Constants2 INSTANCE;

    private Constants2() {
    }

    static {
        INSTANCE = new Constants2();
    }
}
