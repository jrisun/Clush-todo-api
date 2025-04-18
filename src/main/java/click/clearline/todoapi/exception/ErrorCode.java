package click.clearline.todoapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    VALIDATION_ERROR("E400", "입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    TODO_NOT_FOUND("E404", "존재하지 않는 번호입니다.", HttpStatus.NOT_FOUND);
    
    private final String code;
    private final String message;
    private final HttpStatus status;

    private ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
