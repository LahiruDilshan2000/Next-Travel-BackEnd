package lk.ijse.packageservice.feign;

import feign.Headers;
import lk.ijse.packageservice.dto.GuideDTO;
import lk.ijse.packageservice.util.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Wed 7:42 PM on 10/18/2023
 * @project package-service
 **/
@FeignClient("GUIDE-SERVICE")
public interface Guide {

    @GetMapping(value = "nexttravel/api/v1/guide/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<GuideDTO> getAll();

    @GetMapping(value = "nexttravel/api/v1/guide/get", params = {"guideId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseUtil getGuideById(@RequestParam Integer guideId);
}
