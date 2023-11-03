package lk.ijse.packageservice.entity;

import jakarta.persistence.*;
import lk.ijse.packageservice.dto.RoomDetailsDTO;
import lk.ijse.packageservice.embeded.RoomDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:27 AM on 10/7/2023
 * @project nexttravel
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "_packge")
public class Package implements SuperEntity{

    @Id
    private String packageId;

    @Column(nullable = false)
    private String packageCategory;

    @Column(nullable = false)
    private String userNic;

    @Column(nullable = false)
    private Integer vehicleId;

    @Column(nullable = false)
    private Integer hotelId;

    @Column(nullable = false)
    private String hotelName;

    @ElementCollection
    @CollectionTable(name = "_travel_area", joinColumns = @JoinColumn(name = "packageId"))
    private List<String> travelArea;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Double packageValue;

    @Column(nullable = false)
    private String paidValue;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private Date bookedDate;

    @ElementCollection
    @CollectionTable(name = "_room_details", joinColumns = {
            @JoinColumn(name = "packageId", referencedColumnName = "packageId"),
            @JoinColumn(name = "hotelId", referencedColumnName = "hotelId")})
    private List<RoomDetails> roomDetailList;

    @Column(nullable = false)
    private Integer adultCount;

    @Column(nullable = false)
    private Integer childrenCount;

    @Column(nullable = false)
    private Integer headCount;

    @Column(nullable = false)
    private String withPetOrNo;

    @Column(nullable = false)
    private String guideId;

}
