package dev.example.dundemo.service;

import dev.example.dundemo.advice.exception.CharacterNotFoundException;
import dev.example.dundemo.advice.exception.ManyCharacterFoundException;
import dev.example.dundemo.client.NeopleApiClient;
import dev.example.dundemo.domain.Adventure;
import dev.example.dundemo.domain.Character;
import dev.example.dundemo.enums.ServerName;
import dev.example.dundemo.repository.AdventureRepository;
import dev.example.dundemo.repository.CharacterRepository;
import dev.example.dundemo.utils.RaidClearCountUtil;
import dev.example.dundemo.web.dto.Character.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CharacterService {

    private final NeopleApiClient neopleApiClient;
    private final RaidClearCountUtil raidClearCountUtil;
    private final CharacterRepository characterRepository;
    private final AdventureRepository adventureRepository;

    public CharacterRaidClearCountResponseDTO getCharacterRaidClearCount(CharacterRaidClearCountRequestDTO requestDTO) {

        // 처음에 도메인에서 캐릭터 검색후 있으면 그 항목의 마지막 수정시간 이후로 검색
        Character targetCharacter = characterRepository.findCharacterByServerAndName(requestDTO.getServerName(), requestDTO.getCharacterName());

        // DB에서 검색된 캐릭터가 없을때 API에서 캐릭터 검색
        if (targetCharacter == null) {
            List<CharacterDTO> searchResult = neopleApiClient.getCharacterId(requestDTO.getServerName(), requestDTO.getCharacterName()).getRows();

            if (searchResult.size() == 1) {
                CharacterDTO targetCharacterDTO = searchResult.get(0);
                CharacterInfoDTO targetCharacterInfoDTO = neopleApiClient.getCharacterInfo(targetCharacterDTO.getServerId(), targetCharacterDTO.getCharacterId());
                Character newCharacter = targetCharacterInfoDTO.toCharacterEntity();
                characterRepository.saveCharacter(newCharacter);
                targetCharacter = newCharacter;
                Adventure targetAdventure = adventureRepository.findAdventureByAdventureName(targetCharacterInfoDTO.getAdventureName());

                // 해당 캐릭터가 속한 모험단이 없으면 모험단 생성하고 캐릭터 추가
                if (targetAdventure == null) {
                    Adventure newAdventure = targetCharacterInfoDTO.toAdventureEntity();
                    newAdventure.addCharacter(newCharacter.getCharacterId());
                    adventureRepository.saveAdventure(newAdventure);
                // 해당 캐릭터가 속한 모험단이 있으면 캐릭터만 추가
                } else {
                    targetAdventure.addCharacter(newCharacter.getCharacterId());
                    adventureRepository.saveAdventure(targetAdventure);
                }

                // 처음 등록된 캐릭터는 레이드 횟수를 세팅
                raidClearCountUtil.initRaidClearCount(targetCharacter);
                targetCharacter.setImage(neopleApiClient.getCharacterImage(targetCharacter.getServerId(), targetCharacter.getCharacterId()));

            // 검색된 캐릭터가 2개 이상이면 예외 반환
            } else if (searchResult.size() > 1) {
                Map<String, String> characters = searchResult.stream().collect(Collectors.toMap(
                        CharacterDTO::getServerId,
                        CharacterDTO::getCharacterName
                ));
                throw new ManyCharacterFoundException(characters);
            } else {
                throw new CharacterNotFoundException();
            }
        // DB에 캐릭터가 있을때는 캐릭터가 마지막으로 수정된 시간부터 클리어 횟수 카운트
        } else {
            LocalDateTime start = targetCharacter.getModifiedAt();
            raidClearCountUtil.refreshRaidClearCount(targetCharacter, start);
            String imageCode = neopleApiClient.getCharacterImage(targetCharacter.getServerId(), targetCharacter.getCharacterId());
            targetCharacter.setImage(imageCode);
        }

        characterRepository.saveCharacter(targetCharacter);

        List<CharacterCardDTO> resultList = new ArrayList<>();

        resultList.add(CharacterCardDTO.builder()
                .characterName(targetCharacter.getCharacterName())
                .serverName(ServerName.getDescription(targetCharacter.getServerId()))
                .adventureName(targetCharacter.getAdventureName())
                .fame(targetCharacter.getFame())
                .inaeClearCount(targetCharacter.getInaeClearCount())
                .nabelClearCount(targetCharacter.getNabelClearCount())
                .diregieClearCount(targetCharacter.getDiregieClearCount())
                .image(targetCharacter.getImage())
                .build());

        return CharacterRaidClearCountResponseDTO.builder()
                .characterList(resultList)
                .build();
    }

    public List<Character> refreshCharacters(String adventureName) {
        List<Character> targetCharacters = characterRepository.findCharactersByAdventureName(adventureName);
        targetCharacters.forEach(character -> raidClearCountUtil.refreshRaidClearCount(character, LocalDateTime.now()));
        return targetCharacters;
    }
}
