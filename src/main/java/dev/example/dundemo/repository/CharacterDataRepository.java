package dev.example.dundemo.repository;

import dev.example.dundemo.domain.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterDataRepository extends MongoRepository<Character, String> {

    Character findCharacterByCharacterNameAndServerId(String characterName, String serverId);
}
