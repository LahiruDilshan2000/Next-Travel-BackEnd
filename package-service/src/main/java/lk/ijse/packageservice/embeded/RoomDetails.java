package lk.ijse.packageservice.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lahiru Dilshan
 * @created Thu 10:52 AM on 10/19/2023
 * @project package-service
 **/

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDetails {

    @Column(nullable = false)
    private String RoomType;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Double price;
}
