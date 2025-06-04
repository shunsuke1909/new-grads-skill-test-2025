package com.example.api.controller;

import java.util.List;

//フォームで送られてきた入力内容を、一時的にまとめて受け取るための入れ物（データの箱）
public class ToDoForm {
    public String title;
    public Boolean isCompleted;
}