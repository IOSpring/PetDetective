package com.iospring.pets.petsfinder.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
class PredictDTO {
    String dto;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
class PredictDTO2 {
    String prediction;
    String score;
}
@RestController
public class TeachableController {

    @PostMapping("/breed")
    String testUploadMultipart(MultipartFile uploadFile) {
        System.out.println("uploadFile.getOriginalFilename() = " + uploadFile.getOriginalFilename());
        return uploadFile.getOriginalFilename();

    }

@GetMapping(value = "/teachable2")
public List<PredictDTO2>  ttest(
        @RequestParam(value = "pre1") String pre1,
        @RequestParam(value = "score1") String score1,
        @RequestParam(value = "pre2") String pre2,
        @RequestParam(value = "score2") String score2)  {

    List<PredictDTO2> predictDTO2s = new LinkedList<>();
    predictDTO2s.add(new PredictDTO2(pre1, score1));
    predictDTO2s.add(new PredictDTO2(pre2, score2));
    for (PredictDTO2 predictDTO2 : predictDTO2s) {
        System.out.println("predictDTO2.getPrediction() = " + predictDTO2.getPrediction());
        System.out.println("predictDTO2.getScore() = " + predictDTO2.getScore());
    }
    return predictDTO2s;
}


    @PostMapping(value = "/teachable")
    public List<PredictDTO2> test(@RequestBody Map<String, List<PredictDTO2>> predictions) {
        System.out.println("predictions = " + predictions);
        List<PredictDTO2> predictDTO2s = predictions.get("predictions");

        for (PredictDTO2 predictDTO2 : predictDTO2s) {
            System.out.println("predictDTO2.getPrediction() = " + predictDTO2.getPrediction());
            System.out.println("predictDTO2.getScore() = " + predictDTO2.getScore());
        }


        return predictDTO2s;
    }

}
