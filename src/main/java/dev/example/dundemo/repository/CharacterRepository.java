package dev.example.dundemo.repository;

import dev.example.dundemo.domain.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

}
