package az.processing.msuser.dto.response;

import az.processing.msuser.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

    private ProductEntity product;
    private Integer quantity;

}
