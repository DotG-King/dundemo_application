package dev.example.dundemo.web.controller;

import dev.example.dundemo.service.AdventureService;
import dev.example.dundemo.utils.model.SingleResult;
import dev.example.dundemo.utils.response.ResponseService;
import dev.example.dundemo.web.dto.Adventure.AdventureRaidClearCountRequestDTO;
import dev.example.dundemo.web.dto.Adventure.AdventureRaidClearCountResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/adventure")
@RestController
public class AdventureController {

    private final ResponseService responseService;
    private final AdventureService adventureService;

    public AdventureController(ResponseService responseService, AdventureService adventureService) {
        this.responseService = responseService;
        this.adventureService = adventureService;
    }

    @PostMapping()
    public SingleResult<AdventureRaidClearCountResponseDTO> getAdventureRaidClearCount(@RequestBody AdventureRaidClearCountRequestDTO adventureRaidClearCountRequestDTO) {
        AdventureRaidClearCountResponseDTO result = adventureService.getAdventureRaidClearCount(adventureRaidClearCountRequestDTO);
        return responseService.getSingleResult(result);
    }
}
