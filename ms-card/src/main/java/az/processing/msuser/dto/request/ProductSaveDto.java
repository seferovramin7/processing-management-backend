package az.processing.msuser.dto.request;

import lombok.Data;

@Data
public class ProductSaveDto {
    private String name;

    private Double price;

    private Integer count;
}
