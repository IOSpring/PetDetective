package com.iospring.pets.petsfinder.detectBoard.dto;


import lombok.Data;


@Data
public class DetectBoardForm {
    private String breed;
    private String color;


    private String missingTime;
    private String missingLocation;

    private String feature; // sepc -> feature 수정 요청

    private int money;

    private String gender;
    private boolean isOperation;
    private String disease;
    private int age;

}

