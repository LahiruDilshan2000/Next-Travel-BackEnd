package lk.ijse.financialserivce.feign;

import lk.ijse.financialserivce.dto.PackageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Fri 8:41 AM on 10/27/2023
 * @project financial-serivce
 **/

@FeignClient("PACKAGE-SERVICE")
public interface Package {

    @GetMapping(value = "nexttravel/api/v1/package/getAllPackages", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<PackageDTO> getAllPackages();
}
