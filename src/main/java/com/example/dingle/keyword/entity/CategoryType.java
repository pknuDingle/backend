package com.example.dingle.keyword.entity;

public enum CategoryType {

    SCHOOL(1, "학사"),
    CAREER_SUPPORT(2, "진로취업지원");

    private final long num;
    private final String name;

    CategoryType(long num, String name) {
        this.num = num;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
