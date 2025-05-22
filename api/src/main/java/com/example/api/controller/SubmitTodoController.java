package com.example.api.controller;

import com.example.api.entity.Todo;
import com.example.api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

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


        todoRepository.save(todos);

        return ResponseEntity.ok().build();
    }
}














