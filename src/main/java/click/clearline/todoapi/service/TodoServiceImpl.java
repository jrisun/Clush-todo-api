package click.clearline.todoapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import click.clearline.todoapi.domain.Todo;
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
        return todoSqlMapper.findById(id);
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoSqlMapper.findAll();
    }

    @Override
    public void editTodo(Todo todo) {
        todoSqlMapper.update(todo);
    }

    @Override
    public void setCompleted(Long id, Boolean isCompleted) {
        todoSqlMapper.updateCompleted(id, isCompleted);
    }

    @Override
    public void setFixed(Long id, Boolean isFixed) {
        todoSqlMapper.updateFixed(id, isFixed);
    }

    @Override
    public void deleteTodo(Long id) {
        todoSqlMapper.delete(id);
    }
}
