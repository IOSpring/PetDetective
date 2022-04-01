package com.iospring.pets.petsfinder.pet.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import com.iospring.pets.petsfinder.image.entity.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@NoArgsConstructor
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
    @OneToOne(fetch = FetchType.LAZY)
    private Image image;


    @Builder
    public Pet(String gender, String disease, String feature, Integer age, boolean isOperation) {
        this.gender = gender;
        this.disease = disease;
        this.feature = feature;
        this.age = age;
        this.isOperation = isOperation;
    }
}
