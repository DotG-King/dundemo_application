package dev.example.dundemo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CharacterRaidClearCountResponseDTO {
    private int nabelClearCount;
    private int inaeClearCount;
}
