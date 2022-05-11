package com.iospring.pets.petsfinder.goldentimemap.controller;

import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.goldentimemap.dto.GoldenTimeDto;
import com.iospring.pets.petsfinder.goldentimemap.repository.query.GoldenTimeQueryRepository;
import com.iospring.pets.petsfinder.goldentimemap.service.GoldenTimeService;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoldenTimeController {
    private final GoldenTimeService goldenTimeService;
    private final UserService userService;
    @GetMapping("/goldentime")
    public List<Object[]> GoldenTimeRequest(@RequestParam(name = "userId") Long userId){
        Double petLatitude;
        Double petLongitude;
        String dbtargetTime =goldenTimeService.getThreeHoursAgo();
        System.out.println("여긴가?0");

        User u = userService.findByUserId(userId);
        List<Object[]> detectiveBoards =goldenTimeService.detectBoardsByLocatoin(u.getLatitude(),u.getLongitude(),dbtargetTime);
        List<Object[]> finderBoards;
        System.out.println("여긴가?1");
        //해당 유저의 3시간 이내 최신 게시판 추출
        List<Object[]> latestFindBoarder = goldenTimeService.findeBoardsByUserId(userId, dbtargetTime);
        //해당 유저가 3시간 이내 올린 게시판이 존재하지않을경우 유저 주변 모든 목격 정보 보내주기
        if(latestFindBoarder.isEmpty()){
            System.out.println("여긴가?2");
            finderBoards = goldenTimeService.findeBoardsByAll(u.getLatitude(), u.getLongitude(), dbtargetTime);
            petLongitude= u.getLongitude();
            petLatitude = u.getLatitude();
        }
        // 존재 할시 해당 정보에 대한 목격 정보 보내주기
        else{
            System.out.println("여긴가?3");
            finderBoards =goldenTimeService.finderBoardsByLocationAndPet((Double) latestFindBoarder.get(0)[0],(Double) latestFindBoarder.get(0)[1],(String)latestFindBoarder.get(0)[2],(String)latestFindBoarder.get(0)[3],(String)latestFindBoarder.get(0)[4]);
            petLatitude = (Double) latestFindBoarder.get(0)[0];
            petLongitude=(Double) latestFindBoarder.get(0)[1];
        }



//        return new GoldenTimeDto(detectiveBoards, finderBoards,u.getLatitude(),u.getLongitude(),petLatitude,petLongitude);
        return detectiveBoards;
    }
}
