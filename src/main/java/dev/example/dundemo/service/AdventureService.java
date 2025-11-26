package dev.example.dundemo.service;

import dev.example.dundemo.advice.exception.RefreshCooldownException;
import dev.example.dundemo.domain.Adventure;
import dev.example.dundemo.domain.Character;
import dev.example.dundemo.enums.ServerName;
import dev.example.dundemo.repository.AdventureRepository;
import dev.example.dundemo.utils.RefreshTimeCheckUtil;
import dev.example.dundemo.web.dto.Adventure.AdventureRaidClearCountRequestDTO;
import dev.example.dundemo.web.dto.Adventure.AdventureRaidClearCountResponseDTO;
import dev.example.dundemo.web.dto.Character.CharacterCardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AdventureService {

    private final AdventureRepository adventureRepository;

    private final CharacterService characterService;
    private final RefreshTimeCheckUtil refreshTimeCheckUtil;

    public AdventureRaidClearCountResponseDTO getAdventureRaidClearCount(AdventureRaidClearCountRequestDTO requestDTO) {
        Adventure targetAdventure = adventureRepository.findAdventureByAdventureName(requestDTO.getAdventureName());
        List<Character> characters = adventureRepository.findCharactersInAdventure(requestDTO.getAdventureName());
        return AdventureRaidClearCountResponseDTO.builder()
                .adventureName(targetAdventure.getAdventureName())
                .nabelClearCount(characters.stream().mapToInt(Character::getNabelClearCount).sum())
                .inaeClearCount(characters.stream().mapToInt(Character::getInaeClearCount).sum())
                .characterList(characters.stream().map(
                        character -> CharacterCardDTO.builder()
                                .characterName(character.getCharacterName())
                                .serverName(ServerName.getDescription(character.getServerId()))
                                .AdventureName(character.getAdventureName())
                                .fame(character.getFame())
                                .inaeClearCount(character.getInaeClearCount())
                                .nabelClearCount(character.getNabelClearCount())
                                .image(character.getImage()).build()
                        ).collect(Collectors.toList()))
                .build();
    }

    public AdventureRaidClearCountResponseDTO refreshAdventureRaidClearCount(AdventureRaidClearCountRequestDTO requestDTO) {
        Adventure targetAdventure = adventureRepository.findAdventureByAdventureName(requestDTO.getAdventureName());

        if (!refreshTimeCheckUtil.canAdventureRefresh(targetAdventure.getModifiedAt())) {
            throw new RefreshCooldownException();
        }

        List<Character> characters = characterService.refreshCharacters(requestDTO.getAdventureName());
        // 한번 저장해서 수정시간 갱신
        adventureRepository.saveAdventure(targetAdventure);
        return AdventureRaidClearCountResponseDTO.builder()
                .adventureName(targetAdventure.getAdventureName())
                .nabelClearCount(characters.stream().mapToInt(Character::getNabelClearCount).sum())
                .inaeClearCount(characters.stream().mapToInt(Character::getInaeClearCount).sum())
                .characterList(characters.stream().map(
                        character -> CharacterCardDTO.builder()
                                .characterName(character.getCharacterName())
                                .serverName(ServerName.getDescription(character.getServerId()))
                                .AdventureName(character.getAdventureName())
                                .fame(character.getFame())
                                .inaeClearCount(character.getInaeClearCount())
                                .nabelClearCount(character.getNabelClearCount())
                                .image(character.getImage()).build())
                        .collect(Collectors.toList()))
                .build();
    }
}
