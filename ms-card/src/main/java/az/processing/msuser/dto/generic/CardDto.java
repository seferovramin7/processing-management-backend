package az.processing.msuser.dto.generic;

import java.util.Date;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CardDto {
    @NotBlank(message = "Card number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String cardNumber;

    @FutureOrPresent(message = "Expiration date must be in the future or present")
    private Date expirationDate;

    @NotBlank(message = "CVV is required")
    @Size(min = 3, max = 3, message = "CVV must be 3 characters long")
    private String cvv;

    @DecimalMin(value = "0.0", inclusive = false, message = "Balance must be greater than 0")
    private Double balance;

    private Long userId;
}

