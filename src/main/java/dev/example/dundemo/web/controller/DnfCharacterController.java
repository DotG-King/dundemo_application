package dev.example.dundemo.web.controller;

import dev.example.dundemo.service.CharacterService;
import dev.example.dundemo.utils.ResponseService;
import dev.example.dundemo.utils.model.ListResult;
import dev.example.dundemo.utils.model.SingleResult;
import dev.example.dundemo.web.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/character")
@RestController
public class DnfCharacterController {

    private final ResponseService responseService;
    private final CharacterService characterService;

    public DnfCharacterController(ResponseService responseService, CharacterService characterService) {
        this.responseService = responseService;
        this.characterService = characterService;
    }

    @PostMapping()
    public ListResult<CharacterDTO> getCharacter(@RequestBody CharacterSearchRequestDTO characterSearchRequestDTO) {
        List<CharacterDTO> character = characterService.getCharacter(characterSearchRequestDTO);
        return responseService.getListResult(character);
    }

    @PostMapping("/info")
    public SingleResult<CharacterInfoResponseDTO> getCharacterInfo(@RequestBody CharacterInfoRequestDTO characterInfoRequestDTO) {
        CharacterInfoResponseDTO characterInfo = characterService.getCharacterInfo(characterInfoRequestDTO);
        return responseService.getSingleResult(characterInfo);
    }
}
