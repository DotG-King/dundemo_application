package dev.example.dundemo.enums;

import dev.example.dundemo.advice.exception.IllegalServerCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ServerName {
    ANTON("anton", "안톤"),
    BAKAL("bakal", "바칼"),
    CAIN("cain", "카인"),
    CASILLAS("casillas", "카시야스"),
    DIREGIE("diregie", "디레지에"),
    HILDER("hilder", "힐더"),
    PREY("prey", "프레이"),
    SIROCO("siroco", "시로코");

    private final String code;
    private final String description;

    public static String getDescription(String code) {
        return Arrays.stream(values())
                .filter(server -> server.code.equals(code))
                .findFirst()
                .map(ServerName::getDescription)
                .orElseThrow(IllegalServerCodeException::new);
    }
}
