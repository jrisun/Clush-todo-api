package click.clearline.todoapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import click.clearline.todoapi.domain.Todo;

@Mapper
public interface TodoSqlMapper {
    public void insert(Todo todo);
    public List<Todo> findAll();
    public void update(Todo todo);
    public void delete(Long id);
    public void updateCompleted(
        @Param("id") Long id, 
        @Param("isCompleted") Boolean isCompleted
    );
    public void updateFixed(
        @Param("id") Long id, 
        @Param("isFixed") Boolean isFixed
    );
}
