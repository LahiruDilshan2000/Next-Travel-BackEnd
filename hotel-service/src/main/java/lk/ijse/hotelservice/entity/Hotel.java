package lk.ijse.hotelservice.entity;

import jakarta.persistence.*;
import lk.ijse.hotelservice.embeded.HotelContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:26 AM on 10/7/2023
 * @project nexttravel
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "_hotel")
public class Hotel implements SuperEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelId;

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private Integer hotelCategory;

    @Column(nullable = false)
    private String hotelLocation;

    @Column(nullable = false)
    private String locationUrl;

    @Column(nullable = false)
    private String hotelEmail;

    @ElementCollection
    @CollectionTable(name = "_hotel_contact", joinColumns = @JoinColumn(name = "hotelId"))
    private List<HotelContact> contactList;

    @Column(nullable = false)
    private String isPetAllow;

    @Column(nullable = false)
    private Double fullDPrice;

    @Column(nullable = false)
    private Double halfDPrice;

    @Column(nullable = false)
    private Double fullTPrice;

    @Column(nullable = false)
    private Double halfTPrice;

    @Column(nullable = false)
    private String CancellationCriteria;

    @Column(nullable = false)
    private String folderPath;

    @Column(nullable = false)
    private String hotelImage;
}
