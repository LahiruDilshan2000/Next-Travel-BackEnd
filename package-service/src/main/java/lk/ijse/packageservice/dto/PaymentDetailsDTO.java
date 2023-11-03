package lk.ijse.packageservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lahiru Dilshan
 * @created Wed 1:47 PM on 10/25/2023
 * @project package-service
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDetailsDTO {

    private Integer id;

    @NotNull(message = "Package Id cannot be null !")
    @NotEmpty(message = "Package Id cannot be empty !")
    private String packageId;

    @NotNull(message = "Package status cannot be null !")
    @NotEmpty(message = "Package status cannot be empty !")
    private String status;

    @Positive
    private Double packageValue;

    @NotNull(message = "Paid value cannot be null !")
    @NotEmpty(message = "Paid value cannot be empty !")
    private Double paidValue;

    private byte[] paymentSlip;
}
