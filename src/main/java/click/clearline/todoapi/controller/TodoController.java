package click.clearline.todoapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import click.clearline.todoapi.domain.Todo;
import click.clearline.todoapi.dto.RestResponse;
import click.clearline.todoapi.dto.request.TodoCompletionRequestDto;
import click.clearline.todoapi.dto.request.TodoCreateRequestDto;
import click.clearline.todoapi.dto.request.TodoEditRequestDto;
import click.clearline.todoapi.dto.request.TodoFixRequestDto;
import click.clearline.todoapi.dto.response.TodoResponseDto;
import click.clearline.todoapi.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/todo")
public class TodoController {

    private final TodoService todoService;
    
    @PostMapping("/")
    public RestResponse<TodoResponseDto> create(@RequestBody @Valid TodoCreateRequestDto request) {
        Todo todo = request.toTodo();
        todoService.createTodo(todo);

        Todo createdTodo = todoService.getTodoById(todo.getId());
        return RestResponse.success(TodoResponseDto.from(createdTodo));
    }

    @GetMapping("/")
    public RestResponse<List<TodoResponseDto>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        List<TodoResponseDto> response = todos.stream()
                .map(TodoResponseDto::from)
                .collect(Collectors.toList());
        return RestResponse.success(response);
    }

    @PatchMapping("/{id}")
    public RestResponse<TodoResponseDto> editTodo(
        @PathVariable("id") Long id,
        @RequestBody @Valid TodoEditRequestDto request
    ) {
        Todo todo = request.toTodo();
        todo.setId(id);
        todoService.editTodo(todo);

        Todo editedTodo = todoService.getTodoById(id);
        return RestResponse.success(TodoResponseDto.from(editedTodo));
    }

    @PatchMapping("/{id}/completed")
    public RestResponse<TodoResponseDto> setCompleted(
        @PathVariable("id") Long id,
        @RequestBody @Valid TodoCompletionRequestDto request
    ) {
        todoService.setCompleted(id, request.getIsCompleted());
        Todo todo = todoService.getTodoById(id);
        return RestResponse.success(TodoResponseDto.from(todo));
    }

    @PatchMapping("/{id}/fixed")
    public RestResponse<TodoResponseDto> setFixed(
        @PathVariable("id") Long id,
        @RequestBody @Valid TodoFixRequestDto request
    ){
        todoService.setFixed(id, request.getIsFixed());
        Todo todo = todoService.getTodoById(id);
        return RestResponse.success(TodoResponseDto.from(todo));
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return RestResponse.success(null);
    }

}
