package lk.ijse.packageservice.feign;

import feign.Headers;
import lk.ijse.packageservice.util.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Lahiru Dilshan
 * @created Wed 7:41 PM on 10/18/2023
 * @project package-service
 **/
@FeignClient("VEHICLE-SERVICE")
public interface Vehicle {

    @GetMapping(value = "nexttravel/api/v1/vehicle/getAllWithCategory", params = {"page", "count", "category"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseUtil getVehicleWithCategory(@RequestParam Integer page,
                                        @RequestParam Integer count,
                                        @RequestParam String category);

    @GetMapping(value = "nexttravel/api/v1/vehicle/get", params = {"vehicleId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseUtil getVehicleById(@RequestParam Integer vehicleId);

}
