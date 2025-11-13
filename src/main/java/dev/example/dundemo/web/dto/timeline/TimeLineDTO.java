package dev.example.dundemo.web.dto.timeline;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TimeLineDTO {
    private DateRangeDTO date;
    private String next;
    private List<TimeLineRowDTO> rows;
}
