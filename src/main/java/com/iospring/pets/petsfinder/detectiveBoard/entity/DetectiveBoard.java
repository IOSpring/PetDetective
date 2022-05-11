package com.iospring.pets.petsfinder.detectiveBoard.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import com.iospring.pets.petsfinder.commond.entity.BoardForm;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardForm;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.iospring.pets.petsfinder.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class DetectiveBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detective_board_id")
    private Long id;

    @JoinColumn(name = "D_board_user_fk")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public static DetectiveBoard toEntity(BoardForm boardForm) {
        DetectiveBoard detectiveBoard = new DetectiveBoard();
        detectiveBoard.setMoney(boardForm.getMoney());
        detectiveBoard.setContent(boardForm.getContent());
        detectiveBoard.setMissLocation(boardForm.getMissingLocation());
        detectiveBoard.setMissingTime(boardForm.getMissingTime());
        detectiveBoard.setMissingLatitude(boardForm.getMissingLatitude());
        detectiveBoard.setMissingLongitude(boardForm.getMissingLongitude());
        detectiveBoard.setStatus("inprogress");
        return detectiveBoard;
    }


    public void updatePet(BoardForm boardForm) {
        Pet pet = this.getPet();
        pet.update(boardForm.getFeature(),boardForm.getAge(),boardForm.getGender(),boardForm.getDisease(),boardForm.isOperation());
    }

    public void updateImage(BoardForm board) {
        Image image= this.getPet().getImage();
        image.update(board.getBreed(), board.getColor());
    }

    public void updateBoard(BoardForm boardForm) {
        this.setMissingTime(boardForm.getMissingTime());
        this.setMissLocation(boardForm.getMissingLocation());
        this.setMissingLongitude(boardForm.getMissingLongitude());
        this.setContent(boardForm.getContent());
        this.setMoney(boardForm.getMoney());
        this.setMissingLatitude(boardForm.getMissingLatitude());
    }

    public void setUser(User user) {
        if (!user.getDetectiveBoards().contains(this)) {
            user.getDetectiveBoards().add(this);
        }
            this.user = user;
    }

}
