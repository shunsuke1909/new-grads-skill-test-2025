package com.example.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

//フォームで送られてきた入力内容を、一時的にまとめて受け取るための入れ物（データの箱）
public class ToDoForm {
    public String title;

    @JsonProperty("is_completed")
    public Boolean isCompleted;
}