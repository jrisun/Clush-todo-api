package click.clearline.todoapi.dto.request;

import click.clearline.todoapi.domain.Todo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoCompletionRequestDto {
    @NotNull(message = "완료 여부를 전달해주세요.")
    private Boolean isCompleted;

    public Todo toTodo() {
        Todo todo = new Todo();
        todo.setIsCompleted(this.isCompleted);
        return todo;
    }
}
