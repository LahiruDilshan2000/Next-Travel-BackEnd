package lk.ijse.packageservice.dto;

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
public class GuideDTO {

    private Integer guideId;

    @NotNull(message = "Name cannot be null !")
    @NotEmpty(message = "Name cannot be empty !")
    @Pattern(regexp = "^[A-Za-z ]+$" , message = "Name invalid or empty !")
    private String name;

    @NotNull(message = "Address cannot be null !")
    @NotEmpty(message = "Address cannot be empty !")
    @Pattern(regexp = "^[A-Za-z ]+$" , message = "Address invalid or empty !")
    private String address;

    @Max(60)
    @Min(18)
    @Positive
    private Integer age;

    @NotNull(message = "Gender cannot be null !")
    @NotBlank(message = "Gender cannot be empty !")
    private String gender;

    @NotNull(message = "Contact cannot be null !")
    @Size(max = 10)
    @Pattern(regexp = "^(075|077|071|074|078|076|070|072)([0-9]{7})$" , message = "Contact invalid or empty !")
    private String contact;

    @Positive
    private Double manDayValue;

    private List<byte[]> imageList;
}
