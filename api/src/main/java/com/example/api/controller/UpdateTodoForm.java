package com.example.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTodoForm {
    @JsonProperty("is_completed")
    public Boolean isCompleted;
}
