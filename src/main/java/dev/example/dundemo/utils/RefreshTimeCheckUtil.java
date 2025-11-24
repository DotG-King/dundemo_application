package dev.example.dundemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class RefreshTimeCheckUtil {

    public boolean canRefresh(LocalDateTime lastModifiedTime) {
        return LocalDateTime.now().minusSeconds(30).isAfter(lastModifiedTime);
    }
}
