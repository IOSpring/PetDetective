package com.iospring.pets.petsfinder.pet.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import com.iospring.pets.petsfinder.commond.entity.BoardForm;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardForm;
import com.iospring.pets.petsfinder.image.entity.Image;
import lombok.*;

import javax.persistence.*;

@Entity
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

    @Builder
    public Pet(String gender, String disease, String feature, Integer age, boolean isOperation) {
        this.gender = gender;
        this.disease = disease;
        this.feature = feature;
        this.age = age;
        this.isOperation = isOperation;
    }

    @JoinColumn(name = "pet_image_fk")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Image image;


    public void connectImage(Image image) {
        this.image = image;
    }

    public void update(String feature, Integer age , String gender, String disease, boolean isOperation) {
        this.feature = feature;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.isOperation = isOperation;
    }
}
