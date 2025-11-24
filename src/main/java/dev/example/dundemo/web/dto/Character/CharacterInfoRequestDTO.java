package dev.example.dundemo.web.dto.Character;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterInfoRequestDTO {
    private String serverName;
    private String characterId;
}
