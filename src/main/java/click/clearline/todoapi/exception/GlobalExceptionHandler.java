package click.clearline.todoapi.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldError().getDefaultMessage();
        log.warn("[유효성 검사 오류] {}", errorMsg);

        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getStatus())
                .body(RestErrorResponse.builder()
                        .code(ErrorCode.VALIDATION_ERROR.getCode())
                        .message(errorMsg)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleTodoNotFound(TodoNotFoundException e) {
        log.warn("[할일 조회 실패] {}", e.getMessage());
        return ResponseEntity.status(ErrorCode.TODO_NOT_FOUND.getStatus())
                .body(RestErrorResponse.of(ErrorCode.TODO_NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorResponse> handleException(Exception e) {
        log.error("[알 수 없는 예외 발생]", e);
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(RestErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
