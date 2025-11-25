package dev.example.dundemo.web.dto.Adventure;

import dev.example.dundemo.web.dto.Character.CharacterCardDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AdventureRaidClearCountResponseDTO {
    private String adventureName;
    private int nabelClearCount;
    private int inaeClearCount;
    private List<CharacterCardDTO> characterList;
}
