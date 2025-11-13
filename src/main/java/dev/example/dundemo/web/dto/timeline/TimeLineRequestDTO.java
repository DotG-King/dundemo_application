package dev.example.dundemo.web.dto.timeline;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@Setter
@Builder
public class TimeLineRequestDTO {

    private String serverId;
    private String characterId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;
    private int code;
    private String next;

    public Map<String, Object> toMap() {
        return Map.of(
                "serverId", serverId,
                "characterId", characterId,
                "start", start.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm")),
                "end", end.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm")),
                "code", code,
                "next", next
        );
    }
}
