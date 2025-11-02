package dev.example.dundemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
