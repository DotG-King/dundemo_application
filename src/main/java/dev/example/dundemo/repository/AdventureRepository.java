package dev.example.dundemo.repository;

import dev.example.dundemo.domain.Adventure;
import dev.example.dundemo.domain.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class AdventureRepository {

    private final AdventureDataRepository adventureDataRepository;
    private final CharacterRepository characterRepository;

    public Adventure findAdventureByAdventureName(String adventureName) {
        return adventureDataRepository.findAdventureByAdventureName(adventureName);
    }

    public Adventure saveAdventure(Adventure adventure) {
        return adventureDataRepository.save(adventure);
    }

    public List<Character> findCharactersInAdventure(String adventureName) {
        Adventure targetAdventure = findAdventureByAdventureName(adventureName);
        return characterRepository.findCharactersByCharacterId(targetAdventure.getCharacters());
    }

}
