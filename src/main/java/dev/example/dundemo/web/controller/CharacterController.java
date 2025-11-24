package dev.example.dundemo.web.controller;

import dev.example.dundemo.service.CharacterService;
import dev.example.dundemo.utils.response.ResponseService;
import dev.example.dundemo.utils.model.ListResult;
import dev.example.dundemo.utils.model.SingleResult;
import dev.example.dundemo.web.dto.Character.*;
import dev.example.dundemo.web.dto.timeline.TimeLineResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public SingleResult<CharacterRaidClearCountResponseDTO> getCharacterRaidClearCount(@RequestBody CharacterRaidClearCountRequestDTO characterRaidClearCountRequestDTO) {
        CharacterRaidClearCountResponseDTO result = characterService.getCharacterRaidClearCount(characterRaidClearCountRequestDTO);
        return responseService.getSingleResult(result);
    }
}
