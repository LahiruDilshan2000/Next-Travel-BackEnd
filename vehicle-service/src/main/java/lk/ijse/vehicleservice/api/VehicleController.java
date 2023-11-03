package lk.ijse.vehicleservice.api;

import jakarta.validation.Valid;
import lk.ijse.vehicleservice.dto.VehicleDTO;
import lk.ijse.vehicleservice.service.custom.VehicleService;
import lk.ijse.vehicleservice.util.ResponseUtil;
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
@RequestMapping("/api/v1/vehicle")
@CrossOrigin
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveVehicle(@RequestPart("imageList") List<MultipartFile> imageList,
                                    @Valid @RequestPart("vehicle") VehicleDTO vehicleDTO) throws IOException {

        /*if (!role.equals(Role.ADMIN_VEHICLE))
            throw new UnauthorizedException("Un authorized access to application");*/

        return ResponseUtil
                .builder()
                .code(200)
                .message("Vehicle Save successfully !")
                .data(vehicleService.saveVehicle(vehicleDTO, imageList))
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateVehicle(@RequestPart("imageList") List<MultipartFile> imageList,
                                      @Valid @RequestPart("vehicle") VehicleDTO vehicleDTO) throws IOException {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Vehicle update successfully !")
                .data(vehicleService.updateVehicle(vehicleDTO, imageList))
                .build();
    }

    @DeleteMapping(params = {"vehicleId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteVehicle(@RequestParam Integer vehicleId) {

        /*if (!role.equals(Role.ADMIN_VEHICLE))
            throw new UnauthorizedException("Un authorized access to application");*/

        vehicleService.deleteVehicle(vehicleId);
        return ResponseUtil
                .builder()
                .code(200)
                .message("Vehicle delete successfully !")
                .build();
    }

    @GetMapping(value = "/get", params = {"vehicleId"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getVehicleById(@RequestParam Integer vehicleId) {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting vehicle by ID successfully !")
                .data(vehicleService.findById(vehicleId))
                .build();
    }

    @GetMapping(value = "/getAll", params = {"page", "count"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getVehiclePageable(@RequestParam Integer page, @RequestParam Integer count) {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting pageable vehicle successfully !")
                .data(vehicleService.getPageableHotels(page, count))
                .build();
    }

    @GetMapping(value = "/getAllWithCategory", params = {"page", "count", "category"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getVehicleWithCategory(@RequestParam Integer page,
                                               @RequestParam Integer count,
                                               @RequestParam String category) {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting category vehicle successfully !")
                .data(vehicleService.getVehiclesWithCategory(page, count, category))
                .build();
    }

    @GetMapping(value = "/getAllWithSeatCapacity", params = {"page", "count", "seatCapacity"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getVehicleBySeatCapacity(@RequestParam Integer page,
                                                 @RequestParam Integer count,
                                                 @RequestParam Integer seatCapacity) {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting seat capacity vehicle successfully !")
                .data(vehicleService.getVehiclesWithSeatCapacity(page, count, seatCapacity))
                .build();
    }

    @GetMapping(value = "/getAllWithFuelAndTransmission", params = {"page", "count", "fuelAndTrans"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getVehicleByFuelAndTransmission(@RequestParam Integer page,
                                                        @RequestParam Integer count,
                                                        @RequestParam String fuelAndTrans) {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting fuel and transmission vehicle successfully !")
                .data(vehicleService.getVehiclesWithFuelAndTransmission(page, count, fuelAndTrans))
                .build();
    }

    @GetMapping(value = "/getAllWithSeatAndFulWithTrans", params = {"page", "count", "seatCapacity", "fuelAndTrans"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getVehicleBySeatAndFuelAndTransmission(@RequestParam Integer page,
                                                               @RequestParam Integer count,
                                                               @RequestParam Integer seatCapacity,
                                                               @RequestParam String fuelAndTrans) {

        return ResponseUtil
                .builder()
                .code(200)
                .message("Getting seat and fuel with trans vehicle successfully !")
                .data(vehicleService.getVehiclesBySeatAndFuelWithTransmission(page, count, seatCapacity, fuelAndTrans))
                .build();
    }
}
