package com.iospring.pets.petsfinder.image.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;


    @Column(length = 50, nullable = false)
    private String breed;

    @Column(length = 20, nullable = false)
    private String color;
}
