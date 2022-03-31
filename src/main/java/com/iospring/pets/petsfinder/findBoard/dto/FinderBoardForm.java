package com.iospring.pets.petsfinder.findBoard.dto;


import lombok.Data;



@Data
public class FinderBoardForm {
    private String breed;
    private String color;
    private String url;

    private boolean isCare;

    private String missingTime;
    private String missingLocation;
    private String feature; // sepc -> feature 수정 요청
    private int money;
    private String gender;
    private String isOperation;
    private String disease;
    private int age;

}

