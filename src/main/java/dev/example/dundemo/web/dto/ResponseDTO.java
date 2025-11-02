package dev.example.dundemo.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseDTO<T> {
    List<T> rows;
}
