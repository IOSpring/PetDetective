package com.iospring.pets.petsfinder.board.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import com.iospring.pets.petsfinder.commond.entity.Status;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.iospring.pets.petsfinder.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class FinderBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "finder_board_id")
    private Long id;

    @JoinColumn(name = "F_board_user_id")
    @ManyToOne
    private User user;

    @OneToOne
    @JoinColumn(name = "F_board_pet_id")
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

    @Column
    private Integer money;

    @Column(columnDefinition = "text")
    private String content;
}
