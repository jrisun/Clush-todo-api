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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import click.clearline.todoapi.domain.Todo;
import click.clearline.todoapi.dto.RestResponse;
import click.clearline.todoapi.dto.request.TodoCreateRequestDto;
import click.clearline.todoapi.dto.request.TodoEditRequestDto;
import click.clearline.todoapi.dto.response.TodoResponseDto;
import click.clearline.todoapi.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/todo")
public class TodoController {

    private final TodoService todoService;
    
    @PostMapping("")
    public RestResponse<TodoResponseDto> create(@RequestBody @Valid TodoCreateRequestDto request) {
        Todo todo = request.toTodo();
        todoService.createTodo(todo);

        Todo createdTodo = todoService.getTodoById(todo.getId());
        return RestResponse.ok(TodoResponseDto.from(createdTodo));
    }

    @GetMapping("")
    public RestResponse<List<TodoResponseDto>> getAllTodos(@RequestParam(value = "word", required = false) String word) {
        List<Todo> todos = todoService.getAllTodos(word);
        List<TodoResponseDto> response = todos.stream()
                .map(TodoResponseDto::from)
                .collect(Collectors.toList());
        return RestResponse.ok(response);
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
        return RestResponse.ok(TodoResponseDto.from(editedTodo));
    }

    @PostMapping("/{id}/complete")
    public RestResponse<TodoResponseDto> completeTodo(@PathVariable("id") Long id) {
        todoService.setCompleted(id, true);
        Todo todo = todoService.getTodoById(id);
        return RestResponse.ok(TodoResponseDto.from(todo));
    }

    @PostMapping("/{id}/uncomplete")
    public RestResponse<TodoResponseDto> uncompleteTodo(@PathVariable("id") Long id) {
        todoService.setCompleted(id, false);
        Todo todo = todoService.getTodoById(id);
        return RestResponse.ok(TodoResponseDto.from(todo));
    }

    @PostMapping("/{id}/pin")
    public RestResponse<TodoResponseDto> pinTodo(@PathVariable("id") Long id) {
        todoService.setFixed(id, true);
        Todo todo = todoService.getTodoById(id);
        return RestResponse.ok(TodoResponseDto.from(todo));
    }

    @PostMapping("/{id}/unpin")
    public RestResponse<TodoResponseDto> unpinTodo(@PathVariable("id") Long id) {
        todoService.setFixed(id, false);
        Todo todo = todoService.getTodoById(id);
        return RestResponse.ok(TodoResponseDto.from(todo));
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return RestResponse.ok();
    }

}
