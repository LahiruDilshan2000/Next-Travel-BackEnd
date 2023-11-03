package lk.ijse.financialserivce.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
public class PackageDTO {

    @NotNull(message = "Package Id cannot be null !")
    @NotEmpty(message = "Package Id cannot be empty !")
    private String packageId;

    @NotNull(message = "Category cannot be null !")
    @NotEmpty(message = "Category cannot be empty !")
    private String packageCategory;

    @NotNull(message = "User nic cannot be null !")
    @NotEmpty(message = "User nic cannot be empty !")
    @Pattern(regexp = "^([A-Za-z0-9]{10,})$" , message = "User nic invalid or empty !")
    private String userNic;

    @Positive
    private Integer vehicleId;

    @Positive
    private Integer hotelId;

    @NotNull(message = "Hotel name cannot be null !")
    @NotEmpty(message = "Hotel name cannot be empty !")
    @Pattern(regexp = "^[A-Za-z ]+$" , message = "Hotel name invalid or empty !")
    private String hotelName;

    @NotNull(message = "Travel list area cannot be null !")
    @NotEmpty(message = "Travel list area cannot be empty !")
    private List<String> travelArea;

    @NotNull(message = "Contact cannot be null !")
    @NotEmpty(message = "Contact cannot be empty !")
    @Pattern(regexp = "^(075|077|071|074|078|076|070|072)([0-9]{7})$" , message = "Contact invalid or empty !")
    private String contact;

    @NotNull(message = "Email cannot be null !")
    @NotEmpty(message = "Email cannot be empty !")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$" , message = "Email invalid or empty !")
    @Email
    private String email;

    @Positive
    private Double packageValue;

    @NotNull(message = "Paid value cannot be null !")
    @NotEmpty(message = "Paid value cannot be empty !")
    private String paidValue;

    @NotNull(message = "Start date cannot be null !")
    @NotEmpty(message = "Start date cannot be empty !")
    @Past(message = "Start date shouldn 't be before current date")
    private Date startDate;

    @NotNull(message = "End date cannot be null !")
    @NotEmpty(message = "End date cannot be empty !")
    private Date endDate;

    @NotNull(message = "Booked date cannot be null !")
    @NotEmpty(message = "Booked date cannot be empty !")
    @Past(message = "Start date shouldn 't be before current date")
    private Date bookedDate;

    @NotNull(message = "Room details list cannot be null !")
    @NotEmpty(message = "Room details list cannot be empty !")
    private List<RoomDetailsDTO> roomDetailList;

    @Positive
    @Min(0)
    private Integer adultCount;

    @Positive
    @Min(0)
    private Integer childrenCount;

    @Positive
    @Min(0)
    private Integer headCount;

    @NotNull(message = "With pet cannot be null !")
    @NotEmpty(message = "With pet cannot be empty !")
    private String withPetOrNo;

    @NotNull(message = "Guide Id cannot be null !")
    @NotEmpty(message = "Guide Id cannot be empty !")
    private String guideId;
}
