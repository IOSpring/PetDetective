package com.iospring.pets.petsfinder.pet.entity;


import com.iospring.pets.petsfinder.board.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.board.entity.FinderBoard;
import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import com.iospring.pets.petsfinder.image.entity.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Pet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;


    @Column(length = 5)
    private String gender;

    @Column // 255
    private String disease;

    @Column(columnDefinition = "text")
    private String feature;

    @Column(columnDefinition = "int(2)")
    private Integer age;


    @Column(columnDefinition = "bool")
    private boolean isOperation;

    @JoinColumn(name = "pet_image_fk")
    @OneToOne
    private Image image;



}
