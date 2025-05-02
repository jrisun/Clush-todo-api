package click.clearline.todoapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import click.clearline.todoapi.domain.Todo;
import click.clearline.todoapi.exception.ErrorCode;
import click.clearline.todoapi.exception.TodoNotFoundException;
import click.clearline.todoapi.mapper.TodoSqlMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoSqlMapper todoSqlMapper;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Override
    public void createTodo(Todo todo) {
        todoSqlMapper.insert(todo);
    }

    @Override
    public Todo getTodoById(Long id) {
        return getTodoAndThrowExceptionIfNotExists(id);
    }

    @Override
    public List<Todo> getAllTodos(String word) {
        return todoSqlMapper.findAll(word);
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

    @Override
    public String getTodoSummary() {
        String text = getJoinTodoDescriptions();
        return askToOpenAi(text);
    }

    private String getJoinTodoDescriptions() {
        List<Todo> todoList = todoSqlMapper.findAll(null);
        List<String> descriptions = todoList.stream()
                .filter(todo -> !todo.getIsCompleted())
                .map(todo -> todo.getDescription())
                .collect(Collectors.toList());
        return String.join(",", descriptions);
    }

    private String askToOpenAi(String text) {
        OpenAIClient client = OpenAIOkHttpClient.builder().apiKey(openaiApiKey).build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage("나열된 할 일 목록을 요약해서 가볍게 정리해줘. 만약 요약할 내용이 없으면 가볍게 격려해줘")
                .addUserMessage("결과는 꼭 50자 내로 줄여줘.")
                .addUserMessage(String.format("할 일 목록 : '%s", text))
                .model(ChatModel.GPT_4O_MINI)
                .build();
        ChatCompletion chatCompletion = client.chat().completions().create(params);

        return chatCompletion.choices().get(0).message().content().orElseThrow();
    }
}
