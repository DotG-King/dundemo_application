package dev.example.dundemo.repository;

import dev.example.dundemo.domain.Adventure;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdventureDataRepository extends MongoRepository<Adventure, String> {

    Adventure findAdventureByAdventureName(String adventureName);
}
