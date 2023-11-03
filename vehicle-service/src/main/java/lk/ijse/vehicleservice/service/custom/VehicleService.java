package lk.ijse.vehicleservice.service.custom;

import lk.ijse.vehicleservice.dto.VehicleDTO;
import lk.ijse.vehicleservice.service.SuperService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:31 AM on 10/7/2023
 * @project nexttravel
 **/
public interface VehicleService extends SuperService {

    VehicleDTO saveVehicle(VehicleDTO vehicleDTO, List<MultipartFile> imageList) throws IOException;

    VehicleDTO updateVehicle(VehicleDTO vehicleDTO, List<MultipartFile> imageList) throws IOException;

    void deleteVehicle(Integer vehicleId);

    List<VehicleDTO> getPageableHotels(Integer page, Integer count);

    List<VehicleDTO> getVehiclesWithCategory(Integer page, Integer count, String category);

    List<VehicleDTO> getVehiclesWithSeatCapacity(Integer page, Integer count, Integer seatCapacity);

    List<VehicleDTO> getVehiclesWithFuelAndTransmission(Integer page, Integer count, String fuelAndTrans);

    List<VehicleDTO> getVehiclesBySeatAndFuelWithTransmission(Integer page,
                                                              Integer count,
                                                              Integer seatCapacity,
                                                              String fuelAndTrans);

    VehicleDTO findById(Integer vehicleId);

}
