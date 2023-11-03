package lk.ijse.hotelservice.service.custom.impl;

import lk.ijse.hotelservice.dto.HotelDTO;
import lk.ijse.hotelservice.entity.Hotel;
import lk.ijse.hotelservice.exception.NotFoundException;
import lk.ijse.hotelservice.repository.HotelRepository;
import lk.ijse.hotelservice.service.custom.HotelService;
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
import java.util.List;
import java.util.UUID;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:32 AM on 10/7/2023
 * @project nexttravel
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final ModelMapper modelMapper;
    private final String mainPath = "C:\\Images\\Hotel\\";

    @Override
    public HotelDTO saveHotel(HotelDTO hotelDTO, MultipartFile file) throws IOException {

        String folderPath = mainPath + UUID.randomUUID();
        File pathFile = new File(folderPath);
        if (!pathFile.mkdir())
            throw new RuntimeException("Hotel Image directory create failed !");

        String imagePth = folderPath + "\\" + file.getOriginalFilename();
        file.transferTo(Paths.get(imagePth));

        Hotel hotel = modelMapper.map(hotelDTO, Hotel.class);
        hotel.setFolderPath(folderPath);
        hotel.setHotelImage(imagePth);

        return modelMapper.map(hotelRepository.save(hotel), HotelDTO.class);
    }

    @Override
    public HotelDTO updateHotel(HotelDTO hotelDTO, MultipartFile file) throws IOException {

        if (!hotelRepository.existsById(hotelDTO.getHotelId()))
            throw new NotFoundException(hotelDTO.getHotelId() +" Hotel doesn't exist !");

        Hotel existHotel = hotelRepository.findById(hotelDTO.getHotelId()).get();
        deleteExistingImg(existHotel.getHotelImage());

        Hotel hotel = modelMapper.map(hotelDTO, Hotel.class);
        hotel.setFolderPath(existHotel.getFolderPath());
        hotel.setHotelImage(saveAndGetPath(existHotel.getFolderPath(), file));

        return modelMapper.map(hotelRepository.save(hotel), HotelDTO.class);
    }

    private String saveAndGetPath(String folderPath, MultipartFile file) throws IOException {

        String imgPath = folderPath + "\\" + file.getOriginalFilename();
        file.transferTo(Paths.get(imgPath));
        return imgPath;
    }

    private void deleteExistingImg(String imagePath) {

        File oldFile = new File(imagePath);

        if (!oldFile.exists()){
            throw new NotFoundException("Existing hotel image is not found !");
        }

        if(!oldFile.delete())
            throw new RuntimeException("Existing hotel image delete failed !");
    }

    @Override
    public void deleteHotel(Integer hotelId) {

        if (!hotelRepository.existsById(hotelId))
            throw new NotFoundException(hotelId +" Hotel doesn't exist !");

        Hotel hotel = hotelRepository.findById(hotelId).get();
        deleteExistingImg(hotel.getHotelImage());

        File folder = new File(hotel.getFolderPath());

        if(!folder.delete())
            throw new RuntimeException("Hotel Image directory delete failed !");

        hotelRepository.deleteById(hotelId);
    }

    @Override
    public List<HotelDTO> getPageableHotels(Integer page, Integer count) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return hotelRepository
                .getHotelHQLWithPageable(pageRequest)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public List<HotelDTO> getHotelsWithCategory(Integer page, Integer count, Integer category) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return hotelRepository
                .getAllByHotelCategory(category, category + 1, pageRequest)
                .stream()
                .map(this::getDTO)
                .toList();


    }

    @Override
    public HotelDTO findById(Integer hotelId) {

        if (!hotelRepository.existsById(hotelId))
            throw new NotFoundException(hotelId +" Hotel doesn't exist !");

        return getDTO(hotelRepository
                .findById(hotelId)
                .get());

    }

    private HotelDTO getDTO(Hotel hotel) {

        HotelDTO hotelDTO = modelMapper.map(hotel, HotelDTO.class);

        try {

            byte[] guideImg = Files.readAllBytes(new File(hotel.getHotelImage()).toPath());

            if (guideImg.length == 0)
                throw new NotFoundException(hotel.getHotelId() +" Hotel image not found !");

            hotelDTO.setHotelImageLocation(guideImg);
            return hotelDTO;

        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
