package click.clearline.todoapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import click.clearline.todoapi.domain.Todo;
import click.clearline.todoapi.exception.ErrorCode;
import click.clearline.todoapi.exception.TodoNotFoundException;
import click.clearline.todoapi.mapper.TodoSqlMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoSqlMapper todoSqlMapper;

    @Override
    public void createTodo(Todo todo) {
        todoSqlMapper.insert(todo);
    }

    @Override
    public Todo getTodoById(Long id) {
        return getTodoAndThrowExceptionIfNotExists(id);
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoSqlMapper.findAll();
    }

    @Override
    public void editTodo(Todo todo) {
        getTodoAndThrowExceptionIfNotExists(todo.getId());
        todoSqlMapper.update(todo);
    }

    @Override
    public void setCompleted(Long id, Boolean isCompleted) {
        Todo todo = getTodoAndThrowExceptionIfNotExists(id);

        if(todo.getIsCompleted() != null && todo.getIsCompleted().equals(isCompleted)) {
            return;
        }
        todoSqlMapper.updateCompleted(id, isCompleted);
    }

    @Override
    public void setFixed(Long id, Boolean isFixed) {
        Todo todo = getTodoAndThrowExceptionIfNotExists(id);

        if(todo.getIsFixed() != null && todo.getIsFixed().equals(isFixed)) {
            return;
        }
        todoSqlMapper.updateFixed(id, isFixed);
    }

    @Override
    public void deleteTodo(Long id) {
        getTodoAndThrowExceptionIfNotExists(id);
        todoSqlMapper.delete(id);
    }

    private Todo getTodoAndThrowExceptionIfNotExists(Long id) {
        Todo todo = todoSqlMapper.findById(id);
        if(todo == null) {
            throw new TodoNotFoundException(ErrorCode.TODO_NOT_FOUND);
        }
        return todo;
    }
}
