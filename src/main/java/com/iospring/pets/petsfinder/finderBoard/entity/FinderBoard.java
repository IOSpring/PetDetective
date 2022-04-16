package com.iospring.pets.petsfinder.finderBoard.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import com.iospring.pets.petsfinder.commond.entity.Status;
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

    @OneToOne(fetch = FetchType.LAZY)
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
}
