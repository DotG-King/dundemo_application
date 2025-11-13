package dev.example.dundemo.service;

import dev.example.dundemo.advice.exception.CharacterNotFoundException;
import dev.example.dundemo.advice.exception.ManyCharacterFoundException;
import dev.example.dundemo.client.NeopleApiClient;
import dev.example.dundemo.domain.Adventure;
import dev.example.dundemo.domain.Character;
import dev.example.dundemo.enums.TimeLineCode;
import dev.example.dundemo.repository.AdventureRepository;
import dev.example.dundemo.repository.CharacterRepository;
import dev.example.dundemo.utils.RaidClearCountUtil;
import dev.example.dundemo.web.dto.*;
import dev.example.dundemo.web.dto.timeline.TimeLineRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public List<CharacterDTO> getCharacter(CharacterSearchRequestDTO requestDTO) {
        return neopleApiClient.getCharacterId(requestDTO.getServerName(), requestDTO.getCharacterName()).getRows();
    }

    public CharacterInfoResponseDTO getCharacterInfo(CharacterInfoRequestDTO requestDTO) {
        return neopleApiClient.getCharacterInfo(requestDTO.getServerName(), requestDTO.getCharacterId());
    }

    public TimeLineResponseDTO getCharacterRaidClearTimeLine(CharacterRaidClearCountRequestDTO requestDTO) {

        TimeLineRequestDTO request = TimeLineRequestDTO.builder()
                .serverId(requestDTO.getServerName())
                .characterId(requestDTO.getCharacterName())
                .start(LocalDateTime.now().minusDays(90))
                .end(LocalDateTime.now())
                .code(TimeLineCode.RAID.getCode())
                .next("")
                .build();

        return neopleApiClient.getCharacterTimeLine(request.toMap());
    }

    public CharacterRaidClearCountResponseDTO getCharacterRaidClearCount(CharacterRaidClearCountRequestDTO requestDTO) {

        // 처음에 도메인에서 캐릭터 검색후 있으면 그 항목의 마지막 수정시간 이후로 검색

        LocalDateTime start = LocalDateTime.now().minusDays(90);
        LocalDateTime end = LocalDateTime.now();

        Character targetCharacter = characterRepository.findCharacterByServerAndName(requestDTO.getServerName(), requestDTO.getCharacterName());

        // DB에서 검색된 캐릭터가 없을때 API에서 캐릭터 검색
        if (targetCharacter == null) {
            List<CharacterDTO> searchResult = neopleApiClient.getCharacterId(requestDTO.getServerName(), requestDTO.getCharacterName()).getRows();

            if (searchResult.size() == 1) {
                CharacterDTO targetCharacterDTO = searchResult.get(0);
                CharacterInfoResponseDTO targetCharacterInfoDTO = neopleApiClient.getCharacterInfo(targetCharacterDTO.getServerId(), targetCharacterDTO.getCharacterId());
                Character newCharacter = targetCharacterInfoDTO.toCharacterEntity();
                characterRepository.saveCharacter(newCharacter);
                targetCharacter = newCharacter;
                Adventure targetAdventure = adventureRepository.findAdventureByAdventureName(targetCharacterInfoDTO.getAdventureName());

                // 해당 캐릭터가 속한 모험단이 없으면 모험단 생성
                if (targetAdventure == null) {
                    Adventure newAdventure = targetCharacterInfoDTO.toAdventureEntity();
                    newAdventure.addCharacter(newCharacter);
                    adventureRepository.saveAdventure(newAdventure);
                } else {
                    targetAdventure.addCharacter(newCharacter);
                    adventureRepository.saveAdventure(targetAdventure);
                }

                // 처음 등록된 캐릭터는 레이드 횟수를 세팅
                raidClearCountUtil.initRaidClearCount(targetCharacter);

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
            start = targetCharacter.getModifiedAt();
            raidClearCountUtil.refreshRaidClearCount(targetCharacter, start);
        }

        return CharacterRaidClearCountResponseDTO
                .builder()
                .nabelClearCount(targetCharacter.getNabelClearCount())
                .inaeClearCount(targetCharacter.getInaeClearCount())
                .build();
    }
}
