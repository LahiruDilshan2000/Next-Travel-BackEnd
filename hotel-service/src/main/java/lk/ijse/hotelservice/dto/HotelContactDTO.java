package lk.ijse.hotelservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lahiru Dilshan
 * @created Mon 1:35 PM on 10/9/2023
 * @project nexttravel
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelContactDTO {

    @NotNull(message = "Contact cannot be null !")
    @NotEmpty(message = "Contact cannot be empty !")
    @Pattern(regexp = "^(075|077|071|074|078|076|070|072)([0-9]{7})$" , message = "Contact invalid or empty !")
    private String contact;
}
