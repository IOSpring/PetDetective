package com.iospring.pets.petsfinder.board.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FinderBoardForm {
    private String missingDay;
    private String missingTime;
    private String missingLocation;
    private String feature; // sepc -> feature 수정 요청
    private int money;
    private String gender;
    private String isOperation;
    private String disease;
    private int age;

}
