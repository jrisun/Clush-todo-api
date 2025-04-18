package click.clearline.todoapi.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
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
        log.warn("[유효성 검사 오류] {}", e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RestErrorResponse.builder()
                        .code(ErrorCode.VALIDATION_ERROR.getCode())
                        .message(errorMsg)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleTodoNotFound(TodoNotFoundException e) {
        log.warn("[할일 조회 실패] {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RestErrorResponse.of(ErrorCode.TODO_NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorResponse> handleException(Exception e) {
        log.error("[알 수 없는 예외 발생] {}", e.getMessage(), e);

        RestErrorResponse restErrorResponse = RestErrorResponse.builder()
                    .message("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
                    .timestamp(LocalDateTime.now())
                    .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorResponse);
    }

}
