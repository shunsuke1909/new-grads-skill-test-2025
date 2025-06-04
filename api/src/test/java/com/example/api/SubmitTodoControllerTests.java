package com.example.api;

import com.example.api.entity.Todo;
import com.example.api.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class SubmitTodoControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
    }

    @Test
    void submitTodo_shouldPersistTodoAndReturnOk() {
        Map<String, Object> request = new HashMap<>();
        request.put("title", "test todo");
        request.put("isCompleted", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity("/api/todos", entity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(todoRepository.count()).isEqualTo(1);
        Todo todo = todoRepository.findAll().get(0);
        assertThat(todo.getTitle()).isEqualTo("test todo");
    }
}
