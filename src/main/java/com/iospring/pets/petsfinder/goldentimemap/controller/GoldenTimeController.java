package com.iospring.pets.petsfinder.goldentimemap.controller;

import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.goldentimemap.dto.DetectiveRequestDto;
import com.iospring.pets.petsfinder.goldentimemap.dto.FinderRequestDto;
import com.iospring.pets.petsfinder.goldentimemap.dto.GoldenTimeDto;
import com.iospring.pets.petsfinder.goldentimemap.repository.query.GoldenTimeQueryRepository;
import com.iospring.pets.petsfinder.goldentimemap.service.GoldenTimeService;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repository.UserRepository;
import com.iospring.pets.petsfinder.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GoldenTimeController {
    private final GoldenTimeService goldenTimeService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/goldentime")
    public GoldenTimeDto GoldenTimeRequest(HttpSession httpSession) {

//        String phoneNumber = (String) httpSession.getAttribute("phoneNumber");
        String phoneNumber ="01034343434";

        Double petLatitude;
        Double petLongitude;
        String dbtargetTime = goldenTimeService.getThreeHoursAgo();

        User u = userService.findByUserPhoneNumber(phoneNumber);
        List<Object[]> detectiveBoards = goldenTimeService.detectBoardsByLocatoin(u.getLatitude(), u.getLongitude(), dbtargetTime);
        List<Object[]> finderBoards;

        //해당 유저의 3시간 이내 최신 게시판 추출
        List<Object[]> latestFindBoarder = goldenTimeService.findeBoardsByUserId(u.getId(), dbtargetTime);
        //해당 유저가 3시간 이내 올린 게시판이 존재하지않을경우 유저 주변 모든 목격 정보 보내주기
        if (latestFindBoarder.isEmpty()) {

            finderBoards = goldenTimeService.findeBoardsByAll(u.getLatitude(), u.getLongitude(), dbtargetTime);
            petLongitude = u.getLongitude();
            petLatitude = u.getLatitude();
        }
        // 존재 할시 해당 정보에 대한 목격 정보 보내주기
        else {

            finderBoards = goldenTimeService.finderBoardsByLocationAndPet((Double) latestFindBoarder.get(0)[0], (Double) latestFindBoarder.get(0)[1], (String) latestFindBoarder.get(0)[2], (String) latestFindBoarder.get(0)[3], (String) latestFindBoarder.get(0)[4]);
            petLatitude = (Double) latestFindBoarder.get(0)[0];
            petLongitude = (Double) latestFindBoarder.get(0)[1];
        }


        List<DetectiveRequestDto> detectiveRequestDtos = goldenTimeService.creatDetectiveRequestDto(detectiveBoards);
        List<FinderRequestDto> finderRequestDtos = goldenTimeService.creatFinderRequestDto(finderBoards);
        return new GoldenTimeDto(finderRequestDtos, detectiveRequestDtos, u.getLatitude(), u.getLongitude(), petLatitude, petLongitude);
    }



}
