package com.iospring.pets.petsfinder.detectiveBoard.service;

import com.iospring.pets.petsfinder.commond.entity.BoardForm;
import com.iospring.pets.petsfinder.config.file.FileUploadService;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDetailDTO;
import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectiveBoard.repository.DetectiveBoardRepository;
import com.iospring.pets.petsfinder.detectiveBoard.repository.DetectiveBoardRepositoryCustomImpl;
import com.iospring.pets.petsfinder.exception.CustomException;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.image.service.ImageService;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.iospring.pets.petsfinder.pet.service.PetService;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.iospring.pets.petsfinder.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectiveBoardService {

    private final UserRepository userRepository;
    private final DetectiveBoardRepository detectBoardRepository;
    private final FileUploadService fileUploadService;
    private final ImageService imageService;
    private final PetService petService;

    private User getUserOrThrow(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
    @Transactional
    public DetectiveBoardDTO addDetectiveBoard(BoardForm detectBoardForm, MultipartFile file, String host, String phoneNumber) {

        User user = getUserOrThrow(phoneNumber);

        String imageUrl = fileUploadService.s3Upload(file,host, "detective");
        Image image = imageService.createImage(detectBoardForm ,imageUrl);
        Pet pet = petService.createPet(detectBoardForm);
        pet.connectImage(image);

        DetectiveBoard detectiveBoard = DetectiveBoard.toEntity(detectBoardForm);
        detectiveBoard.setPet(pet);
        detectiveBoard.setUser(user);
        detectBoardRepository.save(detectiveBoard);

        DetectiveBoardDTO detectBoardDTO = DetectiveBoardDTO.createDetectBoardDTO(detectiveBoard, detectiveBoard.getPet().getImage().getUrl(),phoneNumber);
        return detectBoardDTO;
    }

    @Transactional
    public DetectiveBoard updateBoardImage(Long id, MultipartFile file, String host, String phoneNumber) {
        User user = getUserOrThrow(phoneNumber);

        DetectiveBoard detectiveBoard = detectBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        List<DetectiveBoard> detectiveBoards = user.getDetectiveBoards();

        if (!detectiveBoards.contains(detectiveBoard)) {
            throw new CustomException(INVALID_UPDATE);
        }

        Image image = detectiveBoard.getPet().getImage();

        fileUploadService.s3DeleteImage(image.getFileName());

        String newFileName = fileUploadService.s3Upload(file, host,"detective");

        image.setUrl(newFileName);

        return detectiveBoard;
    }

    @Transactional
    public DetectiveBoard updateBoardForm(Long id, BoardForm detectBoardForm, String phoneNumber) {

        User user = getUserOrThrow(phoneNumber);

        DetectiveBoard detectiveBoard = detectBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        List<DetectiveBoard> detectiveBoards = user.getDetectiveBoards();

        if (!detectiveBoards.contains(detectiveBoard)) {
            throw new CustomException(INVALID_UPDATE);

        }

        detectiveBoard.updateImage(detectBoardForm);
        detectiveBoard.updateBoard(detectBoardForm);
        detectiveBoard.updatePet(detectBoardForm);

        return detectiveBoard;
    }


    public long getPageCount() {
        return (detectBoardRepository.count() / DetectiveBoardRepositoryCustomImpl.SHOW_DETECTIVE_BOARD_COUNT) +1;
    }


    public long getPageCountSearchedByLocation(String condition) {
        return (detectBoardRepository.countDetectBoardDtoSearchedByLocation(condition) / DetectiveBoardRepositoryCustomImpl.SHOW_DETECTIVE_BOARD_COUNT) +1;
    }

    public long getPageCountSearchedByBreed(String condition) {
        return (detectBoardRepository.countDetectBoardDtoSearchedByBreed(condition) / DetectiveBoardRepositoryCustomImpl.SHOW_DETECTIVE_BOARD_COUNT) +1;
    }

    public long getPageCountSearchedByColor(String condition){
        return (detectBoardRepository.countDetectBoardDtoSearchedByColor(condition) / DetectiveBoardRepositoryCustomImpl.SHOW_DETECTIVE_BOARD_COUNT) +1;

    }

    public DetectiveBoardDetailDTO getDetailDetectBoard(Long boardId) {
        DetectiveBoard detectiveBoard = detectBoardRepository.getById(boardId);

        DetectiveBoardDetailDTO detectBoardDetailDTO = DetectiveBoardDetailDTO.createDetectBoardDetailDTO(detectiveBoard);
        return detectBoardDetailDTO;

    }

    @Transactional
    public Long deleteBoard(Long id, String phoneNumber) {
        User user = getUserOrThrow(phoneNumber);

        DetectiveBoard detectiveBoard =null;
        try {

            detectiveBoard = detectBoardRepository.getById(id);
        } catch (CustomException e) {
            new CustomException(BOARD_NOT_FOUND);
        }

        List<DetectiveBoard> detectiveBoards = user.getDetectiveBoards();

        if (!detectiveBoards.contains(detectiveBoard)) {
            throw new CustomException(INVALID_DELETE);
        }

        Image image = detectiveBoard.getPet().getImage();
        try {
            fileUploadService.s3DeleteImage(image.getFileName());
        } catch (RuntimeException e) {
            throw new CustomException(FAIL_DELETE_IN_S3);
        }

        detectBoardRepository.deleteById(id);

        return id;
    }

    @Transactional
    public Long deleteBoard(Long id) {

        DetectiveBoard detectiveBoard = detectBoardRepository.getById(id);


        Image image = detectiveBoard.getPet().getImage();
        try {
            fileUploadService.s3DeleteImage(image.getFileName());
        } catch (RuntimeException e) {
            throw new CustomException(FAIL_DELETE_IN_S3);
        }

        detectBoardRepository.deleteById(id);

        return id;
    }
}
