package lk.ijse.guideservice.api;

import jakarta.validation.Valid;
import lk.ijse.guideservice.dto.GuideDTO;
import lk.ijse.guideservice.entity.Role;
import lk.ijse.guideservice.exception.UnauthorizedException;
import lk.ijse.guideservice.service.custom.GuideService;
import lk.ijse.guideservice.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:38 AM on 10/7/2023
 * @project nexttravel
 **/
@RestController
@RequestMapping("/api/v1/guide")
@CrossOrigin
@RequiredArgsConstructor
public class GuideController {

    private final GuideService guideService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveGuide(@RequestPart("imageList") List<MultipartFile> imageList,
                                  @Valid @RequestPart("guide") GuideDTO guideDTO) throws IOException {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Guide added successfully !")
                .data(guideService.saveGuide(guideDTO, imageList))
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateGuide(@RequestPart("imageList") List<MultipartFile> imageList,
                                    @Valid @RequestPart("guide") GuideDTO guideDTO) throws IOException {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Guide update successfully !")
                .data(guideService.updateGuide(guideDTO, imageList))
                .build();
    }


    @DeleteMapping(params = {"guideId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteGuide(@RequestParam Integer guideId){

        guideService.deleteGuide(guideId);
        return ResponseUtil
                .builder()
                .code(200)
                .message("Guide delete successfully !")
                .build();
    }

    @GetMapping(value = "/get", params = {"guideId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getGuideById(@RequestParam Integer guideId){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting guide by ID successfully !")
                .data(guideService.findById(guideId))
                .build();
    }

    @GetMapping(value = "/getAll", params = {"page", "count"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getGuidePageable(@RequestParam Integer page,
                                         @RequestParam Integer count){

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting pageable guide successfully !")
                .data(guideService.getPageableGuide(page, count))
                .build();
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<GuideDTO> getAll(){

        return guideService.getAll();
    }

    @GetMapping(value = "/search", params = {"text"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil searchByText(@RequestParam String text) {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Search guide by text successfully !")
                .data(guideService.searchByText(text))
                .build();
    }
}
