package lk.ijse.hotelservice.api;

import jakarta.validation.Valid;
import lk.ijse.hotelservice.dto.HotelDTO;
import lk.ijse.hotelservice.service.custom.HotelService;
import lk.ijse.hotelservice.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:38 AM on 10/7/2023
 * @project nexttravel
 **/
@RestController
@RequestMapping("/api/v1/hotel")
@CrossOrigin
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveHotel(@RequestPart("file") MultipartFile file,
                                  @Valid @RequestPart("hotel") HotelDTO hotelDTO) throws IOException {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Hotel save successfully !")
                .data(hotelService.saveHotel(hotelDTO, file))
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateHotel(@RequestPart("file") MultipartFile file,
                                    @Valid @RequestPart("hotel") HotelDTO hotelDTO) throws IOException {


        return ResponseUtil
                .builder()
                .code(200)
                .message("Hotel update successfully !")
                .data(hotelService.updateHotel(hotelDTO, file))
                .build();
    }

    @DeleteMapping(params = {"hotelId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteUser(@RequestParam Integer hotelId){

        hotelService.deleteHotel(hotelId);
        return ResponseUtil
                .builder()
                .code(200)
                .message("Hotel delete successfully !")
                .build();
    }


    @GetMapping(value = "/get", params = {"hotelId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getHotelById(@RequestParam Integer hotelId){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting hotel by ID successfully !")
                .data(hotelService.findById(hotelId))
                .build();
    }
    @GetMapping(value = "/getAll", params = {"page", "count"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getHotelPageable(@RequestParam Integer page,
                                         @RequestParam Integer count){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting pageable hotel successfully !")
                .data(hotelService.getPageableHotels(page, count))
                .build();
    }

    @GetMapping(value = "/getAllWithCategory", params = {"page", "count", "category"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getHotelWithCategory(@RequestParam Integer page,
                                             @RequestParam Integer count,
                                             @RequestParam Integer category){
        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting category hotel successfully !")
                .data(hotelService.getHotelsWithCategory(page, count, category))
                .build();
    }
}
