package com.iospring.pets.petsfinder.board.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import com.iospring.pets.petsfinder.commond.entity.Status;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.iospring.pets.petsfinder.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class DetectiveBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detective_board_id")
    private Long id;

    @JoinColumn(name = "D_board_user_id")
    @ManyToOne
    private User user;

    @OneToOne
    @JoinColumn(name = "D_board_pet_id")
    private Pet pet;

    @Column(length = 50)
    private String location;


    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime uploadTime;

    @Column(columnDefinition = "varchar(12) default 'inprogress'")
    private Status status;


    @Column(columnDefinition = "varchar(2) not null default 'F'")
    private boolean isCare;
}
