package lk.ijse.vehicleservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:25 AM on 10/7/2023
 * @project nexttravel
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "_vehicle")
public class Vehicle implements SuperEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleId;

    @Column(nullable = false)
    private String vehicleBrand;

    @Column(nullable = false)
    private String vehicleCategory;

    @Column(nullable = false)
    private String fuelAndTransmissionType;

    @Column(nullable = false)
    private String vehicleType;

    @Column(nullable = false)
    private String versionType;

    @Column(nullable = false)
    private Double freeForDay;

    @Column(nullable = false)
    private Double freeFor1Km;

    @Column(nullable = false)
    private Integer fuelUsage;

    @Column(nullable = false)
    private Integer seatCapacity;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private String driverName;

    @Column(nullable = false)
    private String driverContact;

    @Column(nullable = false, name = "_vehicle_img_1")
    private String vehicleImg1;

    @Column(nullable = false, name = "_vehicle_img_2")
    private String vehicleImg2;

    @Column(nullable = false, name = "_vehicle_img_3")
    private String vehicleImg3;

    @Column(nullable = false, name = "_vehicle_img_4")
    private String vehicleImg4;

    @Column(nullable = false, name = "_vehicle_img_5")
    private String vehicleImg5;

    @Column(nullable = false, name = "_driving_license_img_1")
    private String drivingLicenseImg1;

    @Column(nullable = false, name = "_driving_license_img_2")
    private String drivingLicenseImg2;

    @Column(nullable = false)
    private String folderLocation;

}
