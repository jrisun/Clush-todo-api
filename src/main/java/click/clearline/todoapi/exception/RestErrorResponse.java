package click.clearline.todoapi.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorResponse {
    private final Boolean success = false;
    private String code;
    private String message;
    private LocalDateTime timestamp;

    public static RestErrorResponse of(ErrorCode errorCode) {
        return RestErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
