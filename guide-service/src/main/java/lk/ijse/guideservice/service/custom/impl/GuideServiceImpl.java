package lk.ijse.guideservice.service.custom.impl;

import lk.ijse.guideservice.dto.GuideDTO;
import lk.ijse.guideservice.entity.Guide;
import lk.ijse.guideservice.exception.NotFoundException;
import lk.ijse.guideservice.repository.GuideRepository;
import lk.ijse.guideservice.service.custom.GuideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:32 AM on 10/7/2023
 * @project nexttravel
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class GuideServiceImpl implements GuideService {

    private final GuideRepository guideRepository;

    private final ModelMapper modelMapper;

    private final String mainPath = "C:\\Images\\Guide\\";

    @Override
    public GuideDTO saveGuide(GuideDTO guideDTO, List<MultipartFile> imageList) throws IOException {

        Optional<Guide> last = guideRepository.findLastInsertedData();

        if (imageList.size() != 5)
            throw new NotFoundException("Guide All images not found !");

        String folderPath = mainPath + UUID.randomUUID();
        File file = new File(folderPath);

        if (!file.mkdir())
            throw new RuntimeException("Guide Image directory create failed !");

        Guide guide = modelMapper.map(guideDTO, Guide.class);

        if (last.isPresent()){
            guide.setGuideId(last.get().getGuideId()+1);
        }else {
            guide.setGuideId(1);
        }
        guide.setFolderLocation(folderPath);
        guide.setGuideImage(saveAndGetPath(folderPath, "1", imageList.get(0)));
        guide.setNicImage1(saveAndGetPath(folderPath, "2", imageList.get(1)));
        guide.setNicImage2(saveAndGetPath(folderPath, "3", imageList.get(2)));
        guide.setGuideIdImage1(saveAndGetPath(folderPath, "4", imageList.get(3)));
        guide.setGuideIdImage2(saveAndGetPath(folderPath, "5", imageList.get(4)));

        return modelMapper.map(guideRepository.save(guide), GuideDTO.class);

    }

    private String saveAndGetPath(String folderPath, String name, MultipartFile file) throws IOException {

        String imgPath = folderPath + "\\" + name + file.getOriginalFilename();
        file.transferTo(Paths.get(imgPath));
        return imgPath;
    }

    @Override
    public GuideDTO updateGuide(GuideDTO guideDTO, List<MultipartFile> imageList) throws IOException {

        if (!guideRepository.existsById(guideDTO.getGuideId()))
            throw new NotFoundException(guideDTO.getGuideId() + " Guide doesn't exists !");

        Guide exitGuide = guideRepository.findById(guideDTO.getGuideId()).get();

        deleteExistingImg(exitGuide.getGuideImage());
        deleteExistingImg(exitGuide.getNicImage1());
        deleteExistingImg(exitGuide.getNicImage2());
        deleteExistingImg(exitGuide.getGuideIdImage1());
        deleteExistingImg(exitGuide.getGuideIdImage2());

        Guide newGuide = modelMapper.map(guideDTO, Guide.class);

        newGuide.setFolderLocation(exitGuide.getFolderLocation());
        newGuide.setGuideImage(saveAndGetPath(exitGuide.getFolderLocation(), "1", imageList.get(0)));
        newGuide.setNicImage1(saveAndGetPath(exitGuide.getFolderLocation(), "2", imageList.get(1)));
        newGuide.setNicImage2(saveAndGetPath(exitGuide.getFolderLocation(), "3", imageList.get(2)));
        newGuide.setGuideIdImage1(saveAndGetPath(exitGuide.getFolderLocation(), "4", imageList.get(3)));
        newGuide.setGuideIdImage2(saveAndGetPath(exitGuide.getFolderLocation(), "5", imageList.get(4)));

        return modelMapper.map(guideRepository.save(newGuide), GuideDTO.class);
    }

    private void deleteExistingImg(String imagePath) {

        File oldFile = new File(imagePath);

        if (!oldFile.exists()){
            throw new NotFoundException("Existing guide image is not found !");
        }

        if(!oldFile.delete())
            throw new RuntimeException("Existing guide image delete failed !");
    }


    @Override
    public void deleteGuide(Integer guideId) {

        if (!guideRepository.existsById(guideId))
            throw new NotFoundException(guideId + " Guide doesn't exists !");

        Guide guide = guideRepository.findById(guideId).get();

        deleteExistingImg(guide.getGuideImage());
        deleteExistingImg(guide.getNicImage1());
        deleteExistingImg(guide.getNicImage2());
        deleteExistingImg(guide.getGuideIdImage1());
        deleteExistingImg(guide.getGuideIdImage2());

        File folder = new File(guide.getFolderLocation());

        if(!folder.delete())
            throw new RuntimeException("Guide Image directory delete failed !");

        guideRepository.deleteById(guideId);
    }

    @Override
    public List<GuideDTO> getPageableGuide(Integer page, Integer count) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return guideRepository
                .findAll(pageRequest)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public List<GuideDTO> getAll() {

        return guideRepository
                .findAll()
                .stream()
                .map(this::getDTO)
                .toList();
    }


    @Override
    public GuideDTO findById(Integer guideId) {

        if (!guideRepository.existsById(guideId))
            throw new NotFoundException(guideId +" Vehicle doesn't exist !");

        return getDTO(guideRepository.findById(guideId).get());

    }

    @Override
    public List<GuideDTO> searchByText(String text) {

        return guideRepository
                .searchByText(text, text, text, text)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    private GuideDTO getDTO(Guide guide) {

        GuideDTO guideDTO = modelMapper.map(guide, GuideDTO.class);

        try {

            byte[] guideImg = Files.readAllBytes(new File(guide.getGuideImage()).toPath());

            if (guideImg.length == 0)
                throw new NotFoundException(guide.getGuideId() +" Guide image not found !");

            byte[] nicImg1 = Files.readAllBytes(new File(guide.getNicImage1()).toPath());
            byte[] nicImg2 = Files.readAllBytes(new File(guide.getNicImage2()).toPath());

            if (nicImg1.length == 0 || nicImg2.length == 0)
                throw new NotFoundException(guide.getGuideId() +" Guide Nic images not found !");

            byte[] guideIdImg1 = Files.readAllBytes(new File(guide.getGuideIdImage1()).toPath());
            byte[] guideIdImg2 = Files.readAllBytes(new File(guide.getGuideIdImage2()).toPath());

            if (guideIdImg1.length == 0 || guideIdImg2.length == 0)
                throw new NotFoundException(guide.getGuideId() +" Guide ID images not found !");

            List<byte[]> imageList = new ArrayList<>();
            imageList.add(guideImg);
            imageList.add(nicImg1);
            imageList.add(nicImg2);
            imageList.add(guideIdImg1);
            imageList.add(guideIdImg2);


            guideDTO.setImageList(imageList);

            return guideDTO;

        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
