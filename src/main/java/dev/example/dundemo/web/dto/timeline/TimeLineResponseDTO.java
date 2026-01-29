package dev.example.dundemo.web.dto.timeline;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeLineResponseDTO {
    private String serverId;
    private String characterId;
    private String characterName;
    private int level;
    private String jobId;
    private String jobGrowId;
    private String jobName;
    private String jobGrowName;
    private int fame;
    private String adventureName;
    private String guildId;
    private String guildName;
    private TimeLineDTO timeline;
}
