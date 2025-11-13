package dev.example.dundemo.advice;

import dev.example.dundemo.advice.exception.CharacterNotFoundException;
import dev.example.dundemo.advice.exception.ManyCharacterFoundException;
import dev.example.dundemo.enums.exception.ErrorCode;
import dev.example.dundemo.utils.LoggingUtil;
import dev.example.dundemo.utils.model.CommonResult;
import dev.example.dundemo.utils.response.ResponseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class CustomAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(CharacterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult characterNotFoundException(HttpServletRequest request, CharacterNotFoundException ex) {
        LoggingUtil.loggingWarn();
        return responseService.getFailResult(
                ErrorCode.CHARACTER_NOT_FOUND.getCode(), ErrorCode.CHARACTER_NOT_FOUND.getDescription());
    }

    @ExceptionHandler(ManyCharacterFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult manyCharacterFoundException(HttpServletRequest request, ManyCharacterFoundException ex) {
        LoggingUtil.loggingWarnWithMessage("여러 캐릭터가 검색되었습니다. : " + ex.getCharacters());
        return responseService.getFailResult(
                ErrorCode.MANY_CHARACTER_FOUND.getCode(), ErrorCode.MANY_CHARACTER_FOUND.getDescription());
    }
}
