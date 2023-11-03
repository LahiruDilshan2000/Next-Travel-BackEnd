package lk.ijse.vehicleservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:41 AM on 10/7/2023
 * @project nexttravel
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VehicleDTO {

    private Integer vehicleId;

    @NotNull(message = "Brand cannot be null !")
    @NotEmpty(message = "Brand cannot be empty !")
    @Pattern(regexp = "^[A-Za-z0-9 ]+$" , message = "Brand invalid or empty !")
    private String vehicleBrand;

    @NotNull(message = "Category cannot be null !")
    @NotEmpty(message = "Category cannot be empty !")
    private String vehicleCategory;

    @NotNull(message = "Fuel and trans cannot be null !")
    @NotEmpty(message = "Fuel and trans cannot be empty !")
    private String fuelAndTransmissionType;

    @NotNull(message = "Vehicle type cannot be null !")
    @NotEmpty(message = "Vehicle type cannot be empty !")
    private String vehicleType;

    @NotNull(message = "Version cannot be null !")
    @NotEmpty(message = "Version cannot be empty !")
    private String versionType;

    @Positive
    private Double freeForDay;

    @Positive
    private Double freeFor1Km;

    @Positive
    @Max(50)
    private Integer fuelUsage;

    @Positive
    @Min(4)
    @Max(52)
    private Integer seatCapacity;

    @Positive
    @Min(1)
    private Integer qty;

    @NotNull(message = "Driver name cannot be null !")
    @NotEmpty(message = "Driver name cannot be empty !")
    @Pattern(regexp = "^[A-Za-z ]+$" , message = "Driver name invalid or empty !")
    private String driverName;

    @NotNull(message = "Driver contact cannot be null !")
    @NotEmpty(message = "Driver contact cannot be empty !")
    @Pattern(regexp = "^(075|077|071|074|078|076|070|072)([0-9]{7})$" , message = "Driver contact invalid or empty !")
    private String driverContact;

    private List<byte[]> imageList;
}
