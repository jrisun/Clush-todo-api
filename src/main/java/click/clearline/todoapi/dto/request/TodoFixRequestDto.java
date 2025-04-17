package click.clearline.todoapi.dto.request;

import click.clearline.todoapi.domain.Todo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoFixRequestDto {
    @NotNull(message = "고정 여부를 전달해주세요.")
    private Boolean isFixed;

    public Todo toTodo() {
        Todo todo = new Todo();
        todo.setIsFixed(this.isFixed);
        return todo;
    }
}
