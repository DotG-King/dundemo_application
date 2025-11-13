package dev.example.dundemo.domain;

import dev.example.dundemo.web.dto.CharacterInfoResponseDTO;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "character")
@Getter
public class Character extends BaseTimeEntity {
    @Id
    String id;

    String serverId;        // 서버 아이디
    String characterId;     // 캐릭터 고유 코드
    String characterName;   // 캐릭터 명
    int level;              // 레벨
    String jobId;           // 캐릭터 직업 고유 코드
    String jobGrowId;       // 캐릭터 전직 직업 고유 코드
    String jobName;         // 캐릭터 직업 명칭
    String jobGrowName;     // 캐릭터 전직 직업 명칭
    int fame;               // 캐릭터 모험가 명성

    @Indexed
    String adventureName;   // 캐릭터 모험단 명
    String guildId;         // 캐릭터 길드 고유 코드
    String guildName;       // 캐릭터 길드 명

    int nabelClearCount;
    int inaeClearCount;

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
    }

    public int increaseNabelClearCount() {
        this.nabelClearCount++;
        return this.nabelClearCount;
    }

    public void setNabelClearCount(int nabelClearCount) {
        this.nabelClearCount = nabelClearCount;
    }

    public int increaseInaeClearCount() {
        this.inaeClearCount++;
        return this.inaeClearCount;
    }

    public void setInaeClearCount(int inaeClearCount) {
        this.inaeClearCount = inaeClearCount;
    }
}
