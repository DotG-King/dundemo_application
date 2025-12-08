package dev.example.dundemo.web.dto.Character;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CharacterCardDTO {
    private String characterName;
    private String serverName;
    private String adventureName;
    private int fame;
    private int inaeClearCount;
    private int nabelClearCount;
    private int diregieClearCount;
    private String image;
}
