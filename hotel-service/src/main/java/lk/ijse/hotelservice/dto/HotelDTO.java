package lk.ijse.hotelservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

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
public class HotelDTO {

    private Integer hotelId;

    @NotNull(message = "Name cannot be null !")
    @NotEmpty(message = "Name cannot be empty !")
    @Pattern(regexp = "^[A-Za-z ]+$" , message = "Name invalid or empty !")
    private String hotelName;

    @Min(1)
    @Max(5)
    @Positive
    private Integer hotelCategory;

    @NotNull(message = "Location cannot be null !")
    @NotEmpty(message = "Location cannot be empty !")
    @Pattern(regexp = "^[A-Za-z ]+$" , message = "Location invalid or empty !")
    private String hotelLocation;

    @NotNull(message = "Location url cannot be null !")
    @NotEmpty(message = "Location url cannot be empty !")
    @URL
    private String locationUrl;

    @NotNull(message = "Email cannot be null !")
    @NotEmpty(message = "Email cannot be empty !")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$" , message = "Email invalid or empty !")
    @Email
    private String hotelEmail;

    @NotNull(message = "Contact list cannot be null !")
    @NotEmpty(message = "Contact list cannot be empty !")
    private List<HotelContactDTO> contactList;

    @NotNull(message = "Is pet allow cannot be null !")
    @NotEmpty(message = "Is pet allow cannot be empty !")
    private String isPetAllow;

    @Positive
    private Double fullDPrice;

    @Positive
    private Double halfDPrice;

    @Positive
    private Double fullTPrice;

    @Positive
    private Double halfTPrice;

    @NotNull(message = "CancellationCriteria cannot be null !")
    @NotEmpty(message = "CancellationCriteria cannot be empty !")
    private String cancellationCriteria;

    private byte[] hotelImageLocation;
}
