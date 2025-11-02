package dev.example.dundemo.service;

import dev.example.dundemo.client.NeopleApiClient;
import dev.example.dundemo.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CharacterService {
    private final NeopleApiClient neopleApiClient;

    public List<CharacterDTO> getCharacter(CharacterSearchRequestDTO requestDTO) {
        return neopleApiClient.getCharacterId(requestDTO.getServerName(), requestDTO.getCharacterName()).getRows();
    }

    public CharacterInfoResponseDTO getCharacterInfo(CharacterInfoRequestDTO requestDTO) {
        return neopleApiClient.getCharacterInfo(requestDTO.getServerName(), requestDTO.getCharacterId());
    }
}
