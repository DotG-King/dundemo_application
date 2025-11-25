package dev.example.dundemo.repository;

import dev.example.dundemo.domain.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CharacterRepository {

    private final CharacterDataRepository characterDataRepository;

    public Character findCharacterByServerAndName(String serverName, String characterName) {
        return characterDataRepository.findCharacterByCharacterNameAndServerId(characterName, serverName);
    }

    public Character saveCharacter(Character character) {
        return characterDataRepository.save(character);
    }

    public List<Character> findCharactersByCharacterId(List<String> characterIds) {
        return characterIds.stream().map(characterDataRepository::findCharacterByCharacterId).toList();
    }

}
