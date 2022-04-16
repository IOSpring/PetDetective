package com.iospring.pets.petsfinder.image.entity;


import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String breed;

    @Column(length = 20, nullable = false)
    private String color;

    @Column
    private String url;

    @Builder
    public Image(String breed, String color, String url) {
        this.breed = breed;
        this.color = color;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public  String getFileName() {
        int lastIndexOf = this.getUrl().lastIndexOf("com/");
        return this.getUrl().substring(lastIndexOf + 4);
    }

    public void update(String breed, String color) {
        this.breed = breed;
        this.color = color;
    }
}
