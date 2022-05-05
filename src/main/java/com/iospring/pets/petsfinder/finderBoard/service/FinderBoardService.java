package com.iospring.pets.petsfinder.finderBoard.service;

import com.iospring.pets.petsfinder.config.file.FileUploadService;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDetailDTO;
import com.iospring.pets.petsfinder.detectiveBoard.repository.DetectiveBoardRepositoryCustomImpl;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDetailDTO;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.finderBoard.repository.CustomFinderBoardRepositoryImpl;
import com.iospring.pets.petsfinder.finderBoard.repository.FinderBoardRepository;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.image.service.ImageService;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.iospring.pets.petsfinder.pet.service.PetService;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FinderBoardService {
    private final FinderBoardRepository finderBoardRepository;
    private final ImageService imageService;
    private final PetService petService;
    private final FileUploadService fileUploadService;
    private final UserRepository userRepository;

    private User getUserOrThrow(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Transactional
    public FinderBoardDTO addFindBoard(FinderBoardForm finderBoardForm, MultipartFile file, String host,String phoneNumber) {

        User user = getUserOrThrow(phoneNumber);

        String imageUrl = fileUploadService.s3Upload(file, host,"finder");

        Image image = imageService.createImage(finderBoardForm, imageUrl);
        Pet pet = petService.createPet(finderBoardForm);
        pet.connectImage(image);
        FinderBoard finderBoard = finderBoardForm.toEntity(finderBoardForm);
        finderBoard.setPet(pet);
        finderBoard.setUser(user);
        finderBoardRepository.save(finderBoard);


        return FinderBoardDTO.createDetectBoardDTO(finderBoard, imageUrl);
    }


    public List<UserDTO> userListMatchingBreedAndColor(String breed, String color) {
        return finderBoardRepository.findUserListMatchingBreedAndColor(breed,color);
    }

    public long getCareFindBoardPage() {
        return (finderBoardRepository.getIsCareCount() / CustomFinderBoardRepositoryImpl.SHOW_FINDER_BOARD_COUNT) +1;
    }

    public long getNotCareFindBoardPage() {
        return (finderBoardRepository.getIsNotCareCount() / CustomFinderBoardRepositoryImpl.SHOW_FINDER_BOARD_COUNT) +1;
    }

    public long getFindBoardPage() {
        return (finderBoardRepository.count() / CustomFinderBoardRepositoryImpl.SHOW_FINDER_BOARD_COUNT) +1;
    }
    @Transactional
    public Long deleteBoard(Long id, String phoneNumber) {

        User user = getUserOrThrow(phoneNumber);

        FinderBoard finderBoard = finderBoardRepository.getById(id);

        List<FinderBoard> finderBoards = user.getFinderBoards();
        if (!finderBoards.contains(finderBoard)) {
            throw new RuntimeException("발견/분실 게시판을 작성한 유저가 아닙니다.");
        }


        Image image = finderBoard.getPet().getImage();
        try {
            fileUploadService.s3DeleteImage(image.getFileName());
        } catch (RuntimeException e) {
            // Todo Exception Process
            throw e;
        }
        finderBoardRepository.deleteById(id);
        return id;
    }

    @Transactional
    public FinderBoard updateBoardImage(Long id, MultipartFile file, String host,String phoneNumber) {
        User user = getUserOrThrow(phoneNumber);

        FinderBoard finderBoard = finderBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found board"));

        List<FinderBoard> finderBoards = user.getFinderBoards();
        if (!finderBoards.contains(finderBoard)) {
            throw new RuntimeException("발견/분실 게시판을 작성한 유저가 아닙니다.");
        }



        Image image = finderBoard.getPet().getImage();
        fileUploadService.s3DeleteImage(image.getFileName());
        String newFileName = fileUploadService.s3Upload(file, host,"finder");
        System.out.println("newFileName = " + newFileName);
        image.setUrl(newFileName);
        return finderBoard;
    }

    @Transactional
    public FinderBoard updateBoardForm(Long id, FinderBoardForm finderBoardForm,String phoneNumber) {

        User user = getUserOrThrow(phoneNumber);

        FinderBoard finderBoard = finderBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found board"));

        List<FinderBoard> finderBoards = user.getFinderBoards();
        if (!finderBoards.contains(finderBoard)) {
            throw new RuntimeException("발견/분실 게시판을 작성한 유저가 아닙니다.");
        }


        finderBoard.updateImage(finderBoardForm);
        finderBoard.updateBoard(finderBoardForm);
        finderBoard.updatePet(finderBoardForm);

        return finderBoard;
    }

    public FinderBoardDetailDTO getDetailDetectBoard(Long boardId) {
        FinderBoard finderBoard = finderBoardRepository.getById(boardId);

        FinderBoardDetailDTO finderBoardDetailDTO = FinderBoardDetailDTO.createDetectBoardDetailDTO(finderBoard);
        return finderBoardDetailDTO;
    }


    public long getPageCountSearchedByLocation(String condition) {
        return (finderBoardRepository.countFinderBoardDtoSearchedByLocation(condition) / CustomFinderBoardRepositoryImpl.SHOW_FINDER_BOARD_COUNT) +1;
    }

    public long getPageCountSearchedByBreed(String condition) {
        return (finderBoardRepository.countDetectBoardDtoSearchedByBreed(condition) / CustomFinderBoardRepositoryImpl.SHOW_FINDER_BOARD_COUNT) +1;
    }

    public long getPageCountSearchedByColor(String condition) {
        return (finderBoardRepository.countFinderBoardDtoSearchedByColor(condition) / CustomFinderBoardRepositoryImpl.SHOW_FINDER_BOARD_COUNT) +1;

    }
}
