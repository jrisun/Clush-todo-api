package click.clearline.todoapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import click.clearline.todoapi.domain.Todo;
import click.clearline.todoapi.exception.TodoNotFoundException;
import click.clearline.todoapi.mapper.TodoSqlMapper;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TodoServiceTest {

    @Mock
    private TodoSqlMapper todoSqlMapper;
    
    @InjectMocks
    private TodoServiceImpl todoService;

    @BeforeEach
    void 테스트_준비() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 할일_생성_성공() {
        // given
        Todo todo = new Todo();
        todo.setDescription("새로운 할 일");
        
        // when
        todoService.createTodo(todo);

        // then
        verify(todoSqlMapper).insert(todo);
    }

    @Test
    void 할일_단건_조회_성공() {
        // given
        Long id = 1L;
        Todo todo = new Todo();
        todo.setId(id);
        todo.setDescription("중요한 일");
        when(todoSqlMapper.findById(id)).thenReturn(todo);

        // when
        Todo result = todoService.getTodoById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(todo);
    }

    @Test
    void 할일_단건_조회_대상_없음() {
        // given
        Long id = 999L;
        when(todoSqlMapper.findById(id)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> todoService.getTodoById(id))
            .isInstanceOf(TodoNotFoundException.class)
            .hasMessage("존재하지 않는 번호입니다.");
    }

    @Test
    void 할일_목록_전체_조회_성공() {
        // given
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setDescription("할일 1");
        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setDescription("할일 2");
        when(todoSqlMapper.findAll("")).thenReturn(List.of(todo1, todo2));

        // when
        List<Todo> result = todoService.getAllTodos("");

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(todo1.getId());
        assertThat(result.get(0).getDescription()).isEqualTo(todo1.getDescription());

        verify(todoSqlMapper, times(1)).findAll("");
    }

    @Test
    void 할일_목록_검색_조회_성공() {
        // given
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setDescription("할일 1");
        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setDescription("할일 2");
        when(todoSqlMapper.findAll("할일")).thenReturn(List.of(todo1, todo2));

        // when
        List<Todo> result = todoService.getAllTodos("할일");

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(todo1.getId());
        assertThat(result.get(1).getDescription()).isEqualTo(todo2.getDescription());

        verify(todoSqlMapper, times(1)).findAll("할일");
    }

    @Test
    void 할일_내용_수정_성공() {
        // given
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("수정된 할일");
        when(todoSqlMapper.findById(1L)).thenReturn(todo);

        // when
        todoService.editTodo(todo);

        // then
        assertThat(todo.getDescription()).isEqualTo("수정된 할일");
        verify(todoSqlMapper, times(1)).update(todo);
    }

    @Test
    void 할일_내용_수정_대상_없음() {
        // given
        Todo todo = new Todo();
        todo.setId(999L);
        todo.setDescription("수정할 할일");
        when(todoSqlMapper.findById(todo.getId())).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> todoService.editTodo(todo))
            .isInstanceOf(TodoNotFoundException.class)
            .hasMessageContaining("존재하지 않는 번호입니다.");

        verify(todoSqlMapper, times(1)).findById(999L);
        verify(todoSqlMapper, never()).update(todo);
    }

    @Test
    void 할일_완료_활성화_처리_성공() {
        // given
        Long id = 1L;
        Todo todo = new Todo();
        todo.setId(id);
        todo.setDescription("완료할 일");
        when(todoSqlMapper.findById(id)).thenReturn(todo);

        // when
        todoService.setCompleted(id, true);

        // then
        verify(todoSqlMapper, times(1)).updateCompleted(id, true);
    }

    @Test
    void 할일_완료_처리_대상_없음() {
        // given
        Long id = 999L;
        when(todoSqlMapper.findById(id)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> todoService.setCompleted(id, true))
            .isInstanceOf(TodoNotFoundException.class)
            .hasMessageContaining("존재하지 않는 번호입니다.");
    }

    @Test
    void 할일_고정_활성화_처리_성공() {
        // given
        Long id = 1L;
        Todo todo = new Todo();
        todo.setId(id);
        todo.setDescription("고정할 일");
        when(todoSqlMapper.findById(id)).thenReturn(todo);

        // when
        todoService.setFixed(id, true);

        // then
        verify(todoSqlMapper, times(1)).updateFixed(id, true);
    }

    @Test
    void 할일_고정_처리_대상_없음() {
        // given
        Long id = 999L;
        when(todoSqlMapper.findById(id)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> todoService.setFixed(id, true))
            .isInstanceOf(TodoNotFoundException.class)
            .hasMessageContaining("존재하지 않는 번호입니다.");
    }

    @Test
    void 할일_삭제_성공() {
        // given
        Long id = 1L;
        Todo todo = new Todo();
        todo.setId(id);
        todo.setDescription("삭제할 할일");
        when(todoSqlMapper.findById(id)).thenReturn(todo);

        // when
        todoService.deleteTodo(1L);

        // then
        verify(todoSqlMapper, times(1)).findById(1L);
        verify(todoSqlMapper, times(1)).delete(1L);
    }

    @Test
    void 할일_삭제_대상_없음() {
        // given
        Long id = 999L;
        when(todoSqlMapper.findById(id)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> todoService.deleteTodo(id))
            .isInstanceOf(TodoNotFoundException.class)
            .hasMessageContaining("존재하지 않는 번호입니다.");

        verify(todoSqlMapper, times(1)).findById(id);
        verify(todoSqlMapper, never()).delete(anyLong());
    }

}
