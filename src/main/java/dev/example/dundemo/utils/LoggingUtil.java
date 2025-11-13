package dev.example.dundemo.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingUtil {
    public static void loggingWarn() {
        log.warn("error");
    }

    public static void loggingWarnWithMessage(String message) {
        log.warn(message);
    }
}
