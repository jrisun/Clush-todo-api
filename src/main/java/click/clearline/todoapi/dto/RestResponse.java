package click.clearline.todoapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {
    private Boolean success;
    private String message;
    private T data;

    public static <T> RestResponse<T> ok(T data) {
        return RestResponse.<T>builder()
                .success(true)
                .message("요청이 정상 처리되었습니다.")
                .data(data)
                .build();
    }

    public static <T> RestResponse<T> ok() {
        return RestResponse.<T>builder()
                .success(true)
                .message("요청이 정상 처리되었습니다.")
                .build();
    }
}
