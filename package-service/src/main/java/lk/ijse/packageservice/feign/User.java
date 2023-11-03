package lk.ijse.packageservice.feign;

import feign.Headers;
import feign.RequestLine;
import lk.ijse.packageservice.util.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Lahiru Dilshan
 * @created Fri 1:06 PM on 10/20/2023
 * @project package-service
 **/
@FeignClient("IDENTITY-SERVICE")
public interface User {

    @GetMapping(value = "nexttravel/api/v1/auth/user/get", params = {"nic"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Headers({"Authorization: {token}"})
    ResponseUtil findByNic(@RequestHeader("Authorization") String token, @RequestParam("nic") String nic);

    @GetMapping(value = "nexttravel/api/v1/auth/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Headers({"Authorization: {token}"})
    ResponseUtil getAll(@RequestHeader("Authorization") String token);
}
