package com.iospring.pets.petsfinder.goldentimemap.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoldenTimeRepository {
//    private final EntityManager em;
//    //SELECT * FROM "TABLE NAME" ORDER BY "COLUMN NAME" DESC LIMIT 1
//    public List<Object[]> finderBoardByPetAnd3km(Double latitude, Double longitude, String breed, String color , String targetTime) {
//        return em.createNativeQuery(
//                "SELECT fb.finder_board_id,i.url ,fb.modified_at,fb.missing_latitude,fb.missing_longitude,fb.miss_location,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(missing_latitude))*COS(RADIANS(missing_longitude)-RADIANS(:longitude))+SIN(RADIANS(:latitude))*SIN(RADIANS(missing_latitude)))) AS distance\n" +
//                        "FROM finder_board fb\n" +
//                        "inner join pet p\n" +
//                        "on fb.f_board_pet_fk = p.pet_id\n" +
//                        "inner join image i\n" +
//                        "on p.pet_image_fk = i.image_id\n" +
//                        "where fb.modified_at > :targetTime\n" +
//                        "and i.color = :color and i.breed = :breed\n" +
//                        "HAVING distance < 3\n" +
//                        "ORDER BY distance DESC;")
//                .setParameter("latitude",latitude)
//                .setParameter("longitude",longitude)
//                .setParameter("targetTime",targetTime)
//                .setParameter("breed",breed)
//                .setParameter("color",color)
//                .getResultList();
//    }
//    public List<Object[]> findeBoardByLatest(Long userId,String targetTime){
//        return em.createNativeQuery(
//                "select fb.missing_latitude,fb.missing_longitude,i.breed, i.color,fb.modified_at\n" +
//                        "from finder_board  fb\n" +
//                        "inner join pet p on fb.f_board_pet_fk = p.pet_id\n" +
//                        "inner join image i on p.pet_image_fk = i.image_id\n" +
//                        "where fb.f_board_user_fk = :userId and fb.modified_at > :targetTime\n" +
//                        "ORDER BY fb.modified_at DESC;")
//                .setParameter("userId",userId)
//                .setParameter("targetTime",targetTime)
//                .getResultList();
//    }
//    public List<Object[]> findeBoardByALLIn3km(Double latitude, Double longitude,String targetTime) {
//        List<Object[]> resultList = em.createNativeQuery("SELECT fb.finder_board_id,i.url ,fb.modified_at,fb.missing_latitude,fb.missing_longitude,fb.miss_location,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(missing_latitude))*COS(RADIANS(missing_longitude)-RADIANS(:longitude))+SIN(RADIANS(:latitude))*SIN(RADIANS(missing_latitude)))) AS distance\n" +
//                "FROM finder_board fb\n" +
//                "inner join pet p\n" +
//                "on fb.f_board_pet_fk = p.pet_id\n" +
//                "inner join image i\n" +
//                "on p.pet_image_fk = i.image_id\n" +
//                "where fb.modified_at > :targetTime\n" +
//                "HAVING distance < 3\n" +
//                "ORDER BY distance DESC;").
//                setParameter("latitude", latitude).
//                setParameter("longitude", longitude).
//                setParameter("targetTime",targetTime).
//                getResultList();
//        return resultList;
//    }
//    public List<Object[]> detectiveBoardIn3km(Double latitude, Double longitude,String targetTime) {
//        List<Object[]> resultList = em.createNativeQuery(
//                "SELECT detective_board_id,i.url ,db.modified_at,db.missing_latitude,db.missing_longitude,db.miss_location,db.money,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(missing_latitude))*COS(RADIANS(missing_longitude)-RADIANS(:longitude))+SIN(RADIANS(:latitude))*SIN(RADIANS(missing_latitude)))) AS distance\n" +
//                        "FROM detective_board db\n" +
//                        "inner join pet p\n" +
//                        "on db.d_board_pet_fk = p.pet_id\n" +
//                        "inner join image i\n" +
//                        "on p.pet_image_fk = i.image_id\n" +
//                        "where db.modified_at > :targetTime \n" +
//                        "HAVING distance < 3\n" +
//                        "ORDER BY distance DESC;").
//                setParameter("latitude", latitude).
//                setParameter("longitude", longitude).
//                setParameter("targetTime", targetTime).
//                getResultList();
//        return resultList;
//    }
private final EntityManager em;
    //SELECT * FROM "TABLE NAME" ORDER BY "COLUMN NAME" DESC LIMIT 1
    public List<Object[]> finderBoardByPetAnd3km(Double latitude, Double longitude, String breed, String color , String targetTime) {
        targetTime = "'2022-4-4 13:30:46 +0000'";
        return em.createNativeQuery(
                "SELECT fb.finder_board_id,i.url ,fb.missing_time,fb.missing_latitude,fb.missing_longitude,fb.miss_location,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(missing_latitude))*COS(RADIANS(missing_longitude)-RADIANS(:longitude))+SIN(RADIANS(:latitude))*SIN(RADIANS(missing_latitude)))) AS distance\n" +
                        "FROM finder_board fb\n" +
                        "inner join pet p\n" +
                        "on fb.f_board_pet_fk = p.pet_id\n" +
                        "inner join image i\n" +
                        "on p.pet_image_fk = i.image_id\n" +
                        "where fb.missing_time > :targetTime\n" +
                        "and i.color = :color and i.breed = :breed\n" +
                        "HAVING distance < 3\n" +
                        "ORDER BY distance DESC;")
                .setParameter("latitude",latitude)
                .setParameter("longitude",longitude)
                .setParameter("targetTime",targetTime)
                .setParameter("breed",breed)
                .setParameter("color",color)
                .getResultList();
    }
    public List<Object[]> findeBoardByLatest(Long userId,String targetTime){
        return em.createNativeQuery(
                "select fb.missing_latitude,fb.missing_longitude,i.breed, i.color,fb.missing_time\n" +
                        "from finder_board  fb\n" +
                        "inner join pet p on fb.f_board_pet_fk = p.pet_id\n" +
                        "inner join image i on p.pet_image_fk = i.image_id\n" +
                        "where fb.f_board_user_fk = :userId and fb.missing_time > :targetTime\n" +
                        "ORDER BY fb.missing_time DESC;")
                .setParameter("userId",userId)
                .setParameter("targetTime",targetTime)
                .getResultList();
    }
    public List<Object[]> findeBoardByALLIn3km(Double latitude, Double longitude,String targetTime) {
        targetTime = "'2022-4-4 13:30:46 +0000'";
        List<Object[]> resultList = em.createNativeQuery(
                "SELECT fb.finder_board_id,i.url ,fb.missing_time,fb.missing_latitude,fb.missing_longitude,fb.miss_location,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(missing_latitude))*COS(RADIANS(missing_longitude)-RADIANS(:longitude))+SIN(RADIANS(:latitude))*SIN(RADIANS(missing_latitude)))) AS distance\n" +
                "FROM finder_board fb\n" +
                "inner join pet p\n" +
                "on fb.f_board_pet_fk = p.pet_id\n" +
                "inner join image i\n" +
                "on p.pet_image_fk = i.image_id\n" +
                "where fb.missing_time > :targetTime\n" +
                "HAVING distance < 3\n" +
                "ORDER BY distance DESC;").
                setParameter("latitude", latitude).
                setParameter("longitude", longitude).
                setParameter("targetTime",targetTime).
                getResultList();
        return resultList;
    }
    public List<Object[]> detectiveBoardIn3km(Double latitude, Double longitude,String targetTime) {
        targetTime = "'2022-4-4 13:30:46 +0000'";
        List<Object[]> resultList = em.createNativeQuery(
                "SELECT detective_board_id,i.url ,db.missing_time,db.missing_latitude,db.missing_longitude,db.miss_location,db.money,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(missing_latitude))*COS(RADIANS(missing_longitude)-RADIANS(:longitude))+SIN(RADIANS(:latitude))*SIN(RADIANS(missing_latitude)))) AS distance\n" +
                        "FROM detective_board db\n" +
                        "inner join pet p\n" +
                        "on db.d_board_pet_fk = p.pet_id\n" +
                        "inner join image i\n" +
                        "on p.pet_image_fk = i.image_id\n" +
                        "where db.missing_time > :targetTime \n" +
                        "HAVING distance < 3\n" +
                        "ORDER BY distance DESC;").
                setParameter("latitude", latitude).
                setParameter("longitude", longitude).
                setParameter("targetTime", targetTime).
                getResultList();
        return resultList;
    }

}
