package com.iospring.pets.petsfinder.commond.entity;

import lombok.Data;

@Data
public class BoardForm {
    private String breed;
    private String color;
    private String missingTime;
    private String missingLocation;
    private Double missingLatitude;
    private Double missingLongitude;
    private String content;
    private String feature; // sepc -> feature 수정 요청
    private int money;
    private String gender;

    private boolean isOperation;
    private String disease;
    private int age;

}
