package dev.example.dundemo.enums.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CHARACTER_NOT_FOUND(1001, "닉네임으로 캐릭터를 찾지 못했습니다."),
    MANY_CHARACTER_FOUND(1002, "다수의 캐릭터가 검색되었습니다."),
    INTERNAL_SERVER_ERROR(200000, "서버에러 입니다.");

    private final int code;
    private final String description;
}
