package az.processing.msuser.records;


import az.processing.msuser.enums.Result;
import lombok.Builder;

@Builder
public record ApiResponse(Result result, Object data) {
}
