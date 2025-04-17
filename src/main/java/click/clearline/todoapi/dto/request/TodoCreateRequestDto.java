package click.clearline.todoapi.dto.request;

import click.clearline.todoapi.domain.Todo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoCreateRequestDto {
    @NotBlank(message = "할 일 내용은 공백일 수 없습니다.")
    @Size(max = 100, message = "할 일은 100자 이하로 입력해주세요.")
    private String description;

    public Todo toTodo() {
        Todo todo = new Todo();
        todo.setDescription(this.description);
        return todo;
    }
}
