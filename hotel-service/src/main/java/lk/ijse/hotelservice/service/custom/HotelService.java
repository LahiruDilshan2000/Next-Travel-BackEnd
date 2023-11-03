package lk.ijse.hotelservice.service.custom;

import lk.ijse.hotelservice.dto.HotelDTO;
import lk.ijse.hotelservice.service.SuperService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:31 AM on 10/7/2023
 * @project nexttravel
 **/
public interface HotelService extends SuperService {

    HotelDTO saveHotel(HotelDTO hotelDTO, MultipartFile file) throws IOException;

    HotelDTO updateHotel(HotelDTO hotelDTO, MultipartFile file) throws IOException;

    void deleteHotel(Integer hotelId);

    List<HotelDTO> getPageableHotels(Integer page, Integer count);

    List<HotelDTO> getHotelsWithCategory(Integer page, Integer count, Integer category);

    HotelDTO findById(Integer hotelId);
}
