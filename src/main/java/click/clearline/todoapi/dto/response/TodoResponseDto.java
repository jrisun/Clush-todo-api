package click.clearline.todoapi.dto.response;

import java.time.LocalDateTime;

import click.clearline.todoapi.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String description;
    private Boolean isCompleted;
    private Boolean isFixed;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public static TodoResponseDto from(Todo todo) {
        return TodoResponseDto.builder()
                .id(todo.getId())
                .description(todo.getDescription())
                .isCompleted(todo.getIsCompleted())
                .isFixed(todo.getIsFixed())
                .createdAt(todo.getCreatedAt())
                .completedAt(todo.getCompletedAt())
                .build();
    }
}
