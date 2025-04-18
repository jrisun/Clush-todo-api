package click.clearline.todoapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("E500", "서버에서 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR("E400", "입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    TODO_NOT_FOUND("E404", "할 일이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    
    private final String code;
    private final String message;
    private final HttpStatus status;

    private ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
