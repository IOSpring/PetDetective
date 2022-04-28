package com.iospring.pets.petsfinder.goldentimemap.controller;

import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectiveBoard.repository.DetectiveBoardRepositoryCustomImpl;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.finderBoard.repository.CustomFinderBoardRepositoryImpl;
import com.iospring.pets.petsfinder.goldentimemap.dto.GoldenTimeDto;
import com.iospring.pets.petsfinder.goldentimemap.repository.query.GoldenTimeQueryRepository;
import com.iospring.pets.petsfinder.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoldenTimeController {
    private final GoldenTimeQueryRepository queryRepository;
    @GetMapping("/goldentime")
    public GoldenTimeDto GoldenTimeRequest(){
        /**
         * 모든 사용자 를 뽑을 건지 , 반경 10km 를 뽑을 건지 결정
         */
        List<DetectiveBoard> detectiveBoards =queryRepository.detectiveBoards();
        List<FinderBoard> finderBoards =queryRepository.finderBoards();

        return new GoldenTimeDto(detectiveBoards,finderBoards);
    }
}
