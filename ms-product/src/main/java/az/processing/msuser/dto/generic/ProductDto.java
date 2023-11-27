package az.processing.msuser.dto.generic;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;

    private Double price;

    private Integer count;
}
