package dev.example.dundemo.utils.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    private boolean success;
    private String message;
    private int code;
}
