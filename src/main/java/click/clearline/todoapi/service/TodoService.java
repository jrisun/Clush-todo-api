package click.clearline.todoapi.service;

import java.util.List;

import click.clearline.todoapi.domain.Todo;

public interface TodoService {
    public void createTodo(Todo todo);
    public Todo getTodoById(Long id);
    public List<Todo> getAllTodos(String word);
    public void editTodo(Todo todo);
    public void setCompleted(Long id, Boolean isCompleted);
    public void setFixed(Long id, Boolean isFixed);
    public void deleteTodo(Long id);
    public String getTodoSummary();
}
