package az.processing.msuser.dto.generic;

import az.processing.msuser.enums.Result;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    Result result;
}
