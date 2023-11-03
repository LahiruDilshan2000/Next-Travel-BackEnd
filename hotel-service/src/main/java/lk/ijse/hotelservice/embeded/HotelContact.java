package lk.ijse.hotelservice.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lahiru Dilshan
 * @created Mon 12:43 PM on 10/9/2023
 * @project nexttravel
 **/
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelContact {

    @Column(nullable = false)
    private String contact;
}
