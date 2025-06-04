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
import java.util.List;

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

    @Test
    void getTodos_shouldReturnPersistedTodos() {
        Todo todo = new Todo();
        todo.setTitle("task");
        todo.setIsCompleted(false);
        todoRepository.save(todo);

        ResponseEntity<Todo[]> response = restTemplate.getForEntity("/api/todos", Todo[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getTitle()).isEqualTo("task");
    }

    @Test
    void updateTodo_shouldChangeCompletionStatus() {
        Todo todo = new Todo();
        todo.setTitle("task");
        todo.setIsCompleted(false);
        todoRepository.save(todo);

        Map<String, Object> request = new HashMap<>();
        request.put("is_completed", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response = restTemplate.exchange("/api/todos/" + todo.getId(), HttpMethod.PUT, entity, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Todo updated = todoRepository.findById(todo.getId()).orElseThrow();
        assertThat(updated.getIsCompleted()).isTrue();
    }

    @Test
    void deleteTodo_shouldRemoveTodo() {
        Todo todo = new Todo();
        todo.setTitle("task");
        todo.setIsCompleted(false);
        todoRepository.save(todo);

        ResponseEntity<Void> response = restTemplate.exchange("/api/todos/" + todo.getId(), HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(todoRepository.existsById(todo.getId())).isFalse();
    }
}
