package click.clearline.todoapi.exception;

import lombok.Getter;

@Getter
public class TodoNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public TodoNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
