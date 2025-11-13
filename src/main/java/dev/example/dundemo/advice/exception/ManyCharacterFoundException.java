package dev.example.dundemo.advice.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ManyCharacterFoundException extends RuntimeException {
    private final Map<String, String> characters;

    public ManyCharacterFoundException(Map<String, String> characters) {
        this.characters = characters;
    }

}
