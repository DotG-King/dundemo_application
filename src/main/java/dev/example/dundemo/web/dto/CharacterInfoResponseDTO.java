package dev.example.dundemo.web.dto;

import dev.example.dundemo.domain.Adventure;
import dev.example.dundemo.domain.Character;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CharacterInfoResponseDTO {

    private String serverId;        // 서버 아이디
    private String characterId;     // 캐릭터 고유 코드
    private String characterName;   // 캐릭터 명
    private int level;              // 레벨
    private String jobId;           // 캐릭터 직업 고유 코드
    private String jobGrowId;       // 캐릭터 전직 직업 고유 코드
    private String jobName;         // 캐릭터 직업 명칭
    private String jobGrowName;     // 캐릭터 전직 직업 명칭
    private int fame;               // 캐릭터 모험가 명성
    private String adventureName;   // 캐릭터 모험단 명
    private String guildId;         // 캐릭터 길드 고유 코드
    private String guildName;       // 캐릭터 길드 명

    public Character toCharacterEntity() {
        return Character.builder()
                .serverId(this.serverId)
                .characterId(this.characterId)
                .characterName(this.characterName)
                .level(this.level)
                .jobId(this.jobId)
                .jobGrowId(this.jobGrowId)
                .jobName(this.jobName)
                .jobGrowName(this.jobGrowName)
                .fame(this.fame)
                .adventureName(this.adventureName)
                .guildId(this.guildId)
                .guildName(this.guildName)
                .build();
    }

    public Adventure toAdventureEntity() {
        return Adventure.builder()
                .adventureName(this.adventureName)
                .build();
    }

}
