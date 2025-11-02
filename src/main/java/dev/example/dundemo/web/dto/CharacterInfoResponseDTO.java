package dev.example.dundemo.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterInfoResponseDTO {
    String serverId;        // 서버 아이디
    String characterId;     // 캐릭터 고유 코드
    String characterName;   // 캐릭터 명
    int level;              // 레벨
    String jobId;           // 캐릭터 직업 고유 코드
    String jobGrowId;       // 캐릭터 전직 직업 고유 코드
    String jobName;         // 캐릭터 직업 명칭
    String jobGrowName;     // 캐릭터 전직 직업 명칭
    int fame;               // 캐릭터 모험가 명성
    String adventureName;   // 캐릭터 모험단 명
    String guildId;         // 캐릭터 길드 고유 코드
    String guildName;       // 캐릭터 길드 명
}
