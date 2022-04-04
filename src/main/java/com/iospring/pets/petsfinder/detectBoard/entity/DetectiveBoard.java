package com.iospring.pets.petsfinder.detectBoard.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import com.iospring.pets.petsfinder.commond.entity.Status;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardForm;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.iospring.pets.petsfinder.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class DetectiveBoard extends BaseEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detective_board_id")
    private Long id;

    @JoinColumn(name = "D_board_user_fk")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "D_board_pet_fk")
    private Pet pet;

    @Column(length = 50)
    private String missLocation;



    private Double missingLatitude;
    private Double missingLongitude;
    @Column
    private Integer money;

    @Column
    private String missingTime;



    @Column(columnDefinition = "varchar(12) default 'inprogress'")
    private String status;

    @Column(columnDefinition = "text")
    private String content;

    public static DetectiveBoard  createDetectiveBoard (DetectBoardForm detectBoardForm) {
        DetectiveBoard detectiveBoard = new DetectiveBoard();
        detectiveBoard.setMoney(detectBoardForm.getMoney());
        detectiveBoard.setContent(detectBoardForm.getContent());
        detectiveBoard.setMissLocation(detectBoardForm.getMissingLocation());
        detectiveBoard.setMoney(detectBoardForm.getMoney());
        detectiveBoard.setMissingTime(detectBoardForm.getMissingTime());
        detectiveBoard.setMissingLatitude(detectBoardForm.getMissingLatitude());
        detectiveBoard.setMissingLongitude(detectBoardForm.getMissingLongitude());


        return detectiveBoard;
    }


}
