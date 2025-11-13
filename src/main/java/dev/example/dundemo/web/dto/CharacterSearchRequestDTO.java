package dev.example.dundemo.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterSearchRequestDTO {
    String serverName;
    String characterName;
}
