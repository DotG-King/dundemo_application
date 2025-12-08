package dev.example.dundemo.domain;

import dev.example.dundemo.web.dto.timeline.TimeLineResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "character")
@Getter
@Setter
public class Character extends BaseTimeEntity {
    @Id
    private String id;

    private String serverId;        // 서버 아이디
    private String characterId;     // 캐릭터 고유 코드
    private String characterName;   // 캐릭터 명
    private int level;              // 레벨
    private String jobId;           // 캐릭터 직업 고유 코드
    private String jobGrowId;       // 캐릭터 전직 직업 고유 코드
    private String jobName;         // 캐릭터 직업 명칭
    private String jobGrowName;     // 캐릭터 전직 직업 명칭
    private int fame;               // 캐릭터 모험가 명성

    @Indexed
    private String adventureName;   // 캐릭터 모험단 명
    private String guildId;         // 캐릭터 길드 고유 코드
    private String guildName;       // 캐릭터 길드 명

    private int nabelClearCount;    // 나벨 레이드 클리어 횟수
    private int inaeClearCount;     // 이내 레이드 클리어 횟수
    private int diregieClearCount;  // 디레지에 레이드 클리어 횟수
    private String image;           // 캐릭터 이미지 base64 코드

    @Builder
    public Character(String serverId, String characterId, String characterName, int level, String jobId, String jobGrowId, String jobName, String jobGrowName, int fame, String adventureName, String guildId, String guildName) {
        this.serverId = serverId;
        this.characterId = characterId;
        this.characterName = characterName;
        this.level = level;
        this.jobId = jobId;
        this.jobGrowId = jobGrowId;
        this.jobName = jobName;
        this.jobGrowName = jobGrowName;
        this.fame = fame;
        this.adventureName = adventureName;
        this.guildId = guildId;
        this.guildName = guildName;
        this.nabelClearCount = 0;
        this.inaeClearCount = 0;
        this.diregieClearCount = 0;
        this.image = null;
    }

    public int increaseNabelClearCount() {
        this.nabelClearCount++;
        return this.nabelClearCount;
    }

    public int increaseInaeClearCount() {
        this.inaeClearCount++;
        return this.inaeClearCount;
    }

    public int increaseDiregieClearCount() {
        this.diregieClearCount++;
        return this.diregieClearCount;
    }

    public void refreshInfo(TimeLineResponseDTO timeLineResponseDTO) {
        this.serverId = timeLineResponseDTO.getServerId();
        this.characterId = timeLineResponseDTO.getCharacterId();
        this.characterName = timeLineResponseDTO.getCharacterName();
        this.level = timeLineResponseDTO.getLevel();
        this.jobId = timeLineResponseDTO.getJobId();
        this.jobGrowId = timeLineResponseDTO.getJobGrowId();
        this.jobName = timeLineResponseDTO.getJobName();
        this.jobGrowName = timeLineResponseDTO.getJobGrowName();
        this.fame = timeLineResponseDTO.getFame();
        this.adventureName = timeLineResponseDTO.getAdventureName();
        this.guildId = timeLineResponseDTO.getGuildId();
        this.guildName = timeLineResponseDTO.getGuildName();
    }
}
