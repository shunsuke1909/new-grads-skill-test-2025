package com.example.api.controller;

import com.example.api.entity.Todo;
import com.example.api.repository.TodoRepository;
import com.example.api.controller.UpdateTodoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//API用コントローラ-,フロントからのリクエストを受け付ける
@RestController
//フロントからのアクセスを許可
@CrossOrigin(origins = "http://localhost:3000")
public class SubmitTodoController {

    //データベース操作（保存や検索）を行うための部品を挿入
    @Autowired
    private TodoRepository todoRepository;

    //フロントからPOSTで「/api/todos」にデータが送られてきたとき、このメソッドを実行
    @PostMapping("/api/todos")
    public ResponseEntity<?> submit(@RequestBody ToDoForm form) {
        Todo todos = new Todo();

        todos.setTitle(form.title);
        todos.setIsCompleted(form.isCompleted);


        todoRepository.save(todos);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/todos")
    public List<Todo> list() {
        return todoRepository.findAll();
    }

    @PutMapping("/api/todos/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateTodoForm form) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setIsCompleted(form.isCompleted);
                    todoRepository.save(todo);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/todos/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}














