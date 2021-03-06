package com.iospring.pets.petsfinder.user.entity;


import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.commond.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    @Column(unique = true, length = 20, nullable = false)
    private String phoneNumber;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 40, nullable = false)
    private String loadAddress;
    @Column
    private Double latitude;
    @Column
    private Double longitude;

    @Column(length = 50)
    private String searchLocation;

    @Column(columnDefinition = "int(2) default 0")
    private int alterCount;

    @Column
    private String deviceToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<DetectiveBoard> detectiveBoards = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<FinderBoard> finderBoards = new ArrayList<>();


    public void checkDeviceToken(String deviceToken) {
        if(!this.deviceToken.equals(deviceToken)){
            this.setDeviceToken(deviceToken);
        }
    }


}
