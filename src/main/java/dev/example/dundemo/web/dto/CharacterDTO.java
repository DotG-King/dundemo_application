package dev.example.dundemo.web.dto;

import dev.example.dundemo.domain.Character;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CharacterDTO {

    private String serverId;        // 서버 아이디
    private String characterId;     // 캐릭터 고유 코드
    private String characterName;   // 캐릭터 명
    private int level;              // 레벨
    private String jobId;           // 캐릭터 직업 고유 코드
    private String jobGrowId;       // 캐릭터 전직 직업 고유 코드
    private String jobName;         // 캐릭터 직업 명칭
    private String jobGrowName;     // 캐릭터 전직 직업 명칭
    private int fame;               // 캐릭터 모험가 명성

}
