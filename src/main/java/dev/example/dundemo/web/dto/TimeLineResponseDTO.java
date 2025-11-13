package dev.example.dundemo.web.dto;

import dev.example.dundemo.web.dto.timeline.TimeLineDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeLineResponseDTO {
    String serverId;
    String characterId;
    String characterName;
    int level;
    String jobId;
    String jobGrowId;
    String jobName;
    String jobGrowName;
    int fame;
    String adventureName;
    String guildId;
    String guildName;
    TimeLineDTO timeline;
}
