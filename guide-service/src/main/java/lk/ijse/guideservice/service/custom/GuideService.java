package lk.ijse.guideservice.service.custom;

import lk.ijse.guideservice.dto.GuideDTO;
import lk.ijse.guideservice.service.SuperService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:31 AM on 10/7/2023
 * @project nexttravel
 **/
public interface GuideService extends SuperService {

    GuideDTO saveGuide(GuideDTO guideDTO, List<MultipartFile> imageList) throws IOException;

    GuideDTO updateGuide(GuideDTO guideDTO, List<MultipartFile> imageList) throws IOException;

    void deleteGuide(Integer guideId);

    List<GuideDTO> getPageableGuide(Integer page, Integer count);

    List<GuideDTO> getAll();

    GuideDTO findById(Integer guideId);

    List<GuideDTO> searchByText(String text);
}
