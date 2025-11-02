package dev.example.dundemo.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApiKeyProvider {

    private final String apiKey;

    public ApiKeyProvider(@Value("${API_KEY}") String apiKey) {
        this.apiKey = apiKey;
    }
}
