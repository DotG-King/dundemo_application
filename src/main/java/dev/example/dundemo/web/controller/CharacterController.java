package dev.example.dundemo.web.controller;

import dev.example.dundemo.service.CharacterService;
import dev.example.dundemo.utils.response.ResponseService;
import dev.example.dundemo.utils.model.SingleResult;
import dev.example.dundemo.web.dto.Character.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/character")
@RestController
public class CharacterController {

    private final ResponseService responseService;
    private final CharacterService characterService;

    public CharacterController(ResponseService responseService, CharacterService characterService) {
        this.responseService = responseService;
        this.characterService = characterService;
    }

    @PostMapping("/count")
    public SingleResult<CharacterCardDTO> getCharacterRaidClearCount(@RequestBody CharacterRaidClearCountRequestDTO characterRaidClearCountRequestDTO) {
        CharacterCardDTO result = characterService.getCharacterRaidClearCount(characterRaidClearCountRequestDTO);
        return responseService.getSingleResult(result);
    }
}
