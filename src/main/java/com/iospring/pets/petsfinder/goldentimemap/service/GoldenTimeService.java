package com.iospring.pets.petsfinder.goldentimemap.service;

import com.iospring.pets.petsfinder.goldentimemap.repository.GoldenTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoldenTimeService {
    private final GoldenTimeRepository goldenTimeQueryRepository;
    public List<Object[]> finderBoardsByLocationAndPet(Double latitude, Double longitude, String breed, String color , String targetTime) {
        return goldenTimeQueryRepository.finderBoardByPetAnd3km(latitude,longitude,breed,color,targetTime);
    }


    public List<Object[]> detectBoardsByLocatoin( Double latitude, Double longitude ,String targetTime) {
        return goldenTimeQueryRepository.detectiveBoardIn3km(latitude,longitude,targetTime);
    }

    public List<Object[]> findeBoardsByUserId(Long userId,String targetTime){
        List<Object[]> objects = goldenTimeQueryRepository.findeBoardByLatest(userId, targetTime);
        return objects;
    }
    //해당 위치 반경 3키로미터, targetTime 이후 올라온 find게시판 전부 탐색
    public List<Object[]> findeBoardsByAll(Double latitude, Double longitude,String targetTime){
        return goldenTimeQueryRepository.findeBoardByALLIn3km(latitude,longitude,targetTime);
    }
    public String getThreeHoursAgo(){
        Date date = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -3);
        String targettime = sdformat.format(cal.getTime());
        return targettime;
    }


}
