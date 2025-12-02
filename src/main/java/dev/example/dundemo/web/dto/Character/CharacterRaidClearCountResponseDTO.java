package dev.example.dundemo.web.dto.Character;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CharacterRaidClearCountResponseDTO {
    private List<CharacterCardDTO> characterList;
}
