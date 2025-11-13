package dev.example.dundemo.web.dto.timeline;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeLineRowDTO {
    private String code;
    private String name;
    private String date;
    private RaidInfoDTO data;
}
