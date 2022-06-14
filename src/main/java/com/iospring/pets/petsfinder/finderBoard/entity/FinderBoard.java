package com.iospring.pets.petsfinder.finderBoard.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import com.iospring.pets.petsfinder.commond.entity.BoardForm;
import com.iospring.pets.petsfinder.commond.entity.Status;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.iospring.pets.petsfinder.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class FinderBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "finder_board_id")
    private Long id;

    @JoinColumn(name = "F_board_user_fk")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "F_board_pet_fk")
    private Pet pet;

    @Column(length = 50)
    private String missLocation;
    private Double missingLatitude;
    private Double missingLongitude;

    @Column
    private String missingTime;

    @Column(columnDefinition = "varchar(12) default 'inprogress'")
    private String status;

    @Column(columnDefinition = "bool not null default false")
    private boolean isCare;

    @Column(columnDefinition = "text")
    private String content;

    public void updatePet(BoardForm boardForm) {
        Pet pet = this.getPet();
        pet.update(boardForm.getFeature(),boardForm.getAge(),boardForm.getGender(),boardForm.getDisease(),boardForm.isOperation());
    }

    public void updateImage(BoardForm board) {
        Image image= this.getPet().getImage();
        image.update(board.getBreed(), board.getColor());
    }

    public void updateBoard(FinderBoardForm finderBoardForm) {
        this.setMissingTime(finderBoardForm.getMissingTime());
        this.setMissLocation(finderBoardForm.getMissingLocation());
        this.setMissingLongitude(finderBoardForm.getMissingLongitude());
        this.setContent(finderBoardForm.getContent());
        this.setCare(finderBoardForm.isCare());
        this.setMissingLatitude(finderBoardForm.getMissingLatitude());
    }

    @Builder
    public FinderBoard(String missLocation, Double missingLatitude, Double missingLongitude, String missingTime, String status, boolean isCare, String content) {
        this.missLocation = missLocation;
        this.missingLatitude = missingLatitude;
        this.missingLongitude = missingLongitude;
        this.missingTime = missingTime;
        this.status = status;
        this.isCare = isCare;
        this.content = content;
    }
    public void setUser(User user) {
        if (!user.getFinderBoards().contains(this)) {
            user.getFinderBoards().add(this);
        }
        this.user = user;
    }
}
