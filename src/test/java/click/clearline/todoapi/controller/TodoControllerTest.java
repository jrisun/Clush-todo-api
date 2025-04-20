package click.clearline.todoapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import click.clearline.todoapi.dto.request.TodoCreateRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Disabled // 테스트 클래스 전체 비활성화
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 할일_생성_성공() throws Exception {
        TodoCreateRequestDto request = new TodoCreateRequestDto();
        request.setDescription("새로운 할일");

        String requestBody = """
            {
                "description": "새로운 할일"
            }
        """;

        mockMvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.description").value("새로운 할일"));
    }

    @Test
    void 할일_목록_조회_성공() throws Exception {
        mockMvc.perform(get("/api/todo"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void 할일_수정_성공() throws Exception {
        String requestBody = """
            {
                "description": "수정된 할일"
            }
        """;

        mockMvc.perform(patch("/api/todo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.description").value("수정된 할일"));
    }

    @Test
    void 할일_삭제_성공() throws Exception {
        mockMvc.perform(delete("/api/todo/1"))
            .andExpect(status().isOk());
    }

    @Test
    void 할일_완료_처리_성공() throws Exception {
        mockMvc.perform(post("/api/todo/1/complete"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.isCompleted").value(true));
    }

    @Test
    void 할일_고정_처리_성공() throws Exception {
        mockMvc.perform(post("/api/todo/1/pin"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.isFixed").value(true));
    }
}
