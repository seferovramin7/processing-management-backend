package az.processing.msuser.records;


import az.processing.msuser.enums.Result;
import lombok.Builder;

@Builder
public final class ApiResponse {
    private final Result result;
    private final Object data;

    public ApiResponse(Result result, Object data) {
        this.result = result;
        this.data = data;
    }

    public Result result() {
        return result;
    }

    public Object data() {
        return data;
    }

    @Override
    public String toString() {
        return "ApiResponse[" +
                "result=" + result + ", " +
                "data=" + data + ']';
    }

}
