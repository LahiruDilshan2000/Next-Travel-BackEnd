package lk.ijse.packageservice.api;

import lk.ijse.packageservice.dto.PackageDTO;
import lk.ijse.packageservice.service.custom.PackageService;
import lk.ijse.packageservice.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:38 AM on 10/7/2023
 * @project nexttravel
 **/
@RestController
@RequestMapping("/api/v1/package")
@CrossOrigin
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @GetMapping(value = "/getVehicles", params = {"page", "count", "category"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getVehiclesByCategory(@RequestParam Integer page,
                                              @RequestParam Integer count,
                                              @RequestParam String category) {

            return packageService.getVehicles(page, count, category);

    }

    @GetMapping(value = "/getHotels", params = {"page", "count", "category"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getHotelsByCategory(@RequestParam Integer page,
                                            @RequestParam Integer count,
                                            @RequestParam Integer category) {

            return packageService.getHotels(page, count, category);

    }

    @GetMapping(value = "/getHotelById", params = {"hotelId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getHotelsById(@RequestParam Integer hotelId) {

            return packageService.getHotelById(hotelId);

    }

    @GetMapping(value = "/getVehicleById", params = {"vehicleId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getVehicleById(@RequestParam Integer vehicleId) {

            return packageService.getVehicleById(vehicleId);

    }

    @GetMapping(value = "/getGuideById", params = {"guideId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getGuideById(@RequestParam Integer guideId) {

            return packageService.getGuideById(guideId);

    }

    @GetMapping(value = "/getUsers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getAllUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        return packageService.getAllUsers(header);

    }

    @GetMapping(value = "/getUserByNic", params = {"nic"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getUserByNic(@RequestParam String nic, @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        return packageService.getUserByNic(nic, header);

    }

    @GetMapping(value = "/getNextPk")
    public ResponseUtil getNextId() {

            return ResponseUtil
                    .builder()
                    .code(200)
                    .data(packageService.getNextPk())
                    .message("Next Id get successful")
                    .build();

    }

    @PostMapping(value = "/post", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil savePackage(@RequestBody PackageDTO packageDTO) {

            return ResponseUtil
                    .builder()
                    .code(200)
                    .data(packageService.savePackage(packageDTO))
                    .message("Package save successful")
                    .build();

    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil updatePackage(@RequestBody PackageDTO packageDTO) {

        return ResponseUtil
                .builder()
                .code(200)
                .data(packageService.updatePackage(packageDTO))
                .message("Package update successful")
                .build();
    }

    @PutMapping(value = "/updateUserPackage", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil updateUserPackage(@RequestBody PackageDTO packageDTO) {

        return ResponseUtil
                .builder()
                .code(200)
                .data(packageService.updateNotExpirePackage(packageDTO))
                .message("Package update successful")
                .build();
    }

    @DeleteMapping(params = {"packageId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil deletePackage(@RequestParam String packageId) {

        packageService.deletePackage(packageId);
        return ResponseUtil
                .builder()
                .code(200)
                .message("Package delete successful")
                .build();
    }

    @GetMapping(params = {"packageId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getById(@RequestParam String packageId) {

        return ResponseUtil
                .builder()
                .code(200)
                .data(packageService.getById(packageId))
                .message("Package getting successful")
                .build();
    }

    @GetMapping(value = "/getAll", params = {"page", "count"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getPackagePageable(@RequestParam Integer page,
                                           @RequestParam Integer count) {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting pageable package successfully !")
                .data(packageService.getPageablePackage(page, count))
                .build();
    }

    @GetMapping(value = "/getFreeGuide", params = {"startDate", "endDate"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil GetFreeGuide(@RequestParam String startDate,
                                     @RequestParam String endDate) {

            return ResponseUtil
                    .builder()
                    .code(200)
                    .data(packageService.getFreeGuides(startDate, endDate))
                    .message("Package getting all successful")
                    .build();
    }

    @GetMapping(value = "/getPackageByNic", params = {"page", "count", "nic"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getPackagesByUserNic(@RequestParam Integer page,
                                             @RequestParam Integer count,
                                             @RequestParam String nic) {

        return ResponseUtil
                .builder()
                .code(200)
                .data(packageService.getPackageByUserNic(page, count, nic))
                .message("User packages getting successful")
                .build();
    }

    @PutMapping(value = "/payment", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil addPayment(@RequestPart("slip") MultipartFile file,
                                   @RequestPart("packageId") String packageId) {

        packageService.addPaymentSlip(packageId, file);
        return ResponseUtil
                .builder()
                .code(200)
                .message("Payment add successful")
                .build();
    }

    @PutMapping(value = "/confirm", params = "packageId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil confirmPayment(@RequestParam String packageId) {

        packageService.confirmPayment(packageId);
        return ResponseUtil
                .builder()
                .code(200)
                .message("Package payment confirm successful")
                .build();
    }

    @PutMapping(value = "/reject", params = "packageId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil rejectPayment(@RequestParam String packageId) {

        packageService.rejectPayment(packageId);
        return ResponseUtil
                .builder()
                .code(200)
                .message("Package payment rejected successful")
                .build();
    }

    @GetMapping(value = "/getPending", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getPendingAllPayment() {

        return ResponseUtil
                .builder()
                .code(200)
                .data(packageService.getPending())
                .message("All pending payment getting successful")
                .build();
    }

    @GetMapping(value = "/getPendingOne", params = "packageId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getPendingPayment(@RequestParam String packageId) {

        return ResponseUtil
                .builder()
                .code(200)
                .data(packageService.getPendingPaymentByPackageId(packageId))
                .message("Selected payment getting successful")
                .build();
    }

    @GetMapping(value = "/getAllPackages", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<PackageDTO> getAllPackages() {

        return packageService.getAllPackages();
    }

    @GetMapping(value = "/search", params = {"text"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil searchByText(@RequestParam String text) {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Search package by text successfully !")
                .data(packageService.searchByText(text))
                .build();
    }
}
