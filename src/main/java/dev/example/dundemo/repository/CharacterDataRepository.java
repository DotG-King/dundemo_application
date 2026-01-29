package dev.example.dundemo.repository;

import dev.example.dundemo.domain.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterDataRepository extends MongoRepository<Character, String> {

    Character findCharacterByCharacterNameAndServerId(String characterName, String serverId);
    Character findCharacterByCharacterId(String characterId);
    List<Character> findCharactersByAdventureName(String adventureName);
}
