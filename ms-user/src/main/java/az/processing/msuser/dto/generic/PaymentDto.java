package az.processing.msuser.dto.generic;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentDto {

    List<ProductDto> products;
    @NotBlank(message = "Card number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String cardNumber;
    @NotBlank(message = "CVV is required")
    @Size(min = 3, max = 3, message = "CVV must be 3 characters long")
    private String cvv;


}
