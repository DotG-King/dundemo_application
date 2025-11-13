package dev.example.dundemo.repository;

import dev.example.dundemo.domain.Adventure;
import dev.example.dundemo.domain.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AdventureRepository {

    private final AdventureDataRepository adventureDataRepository;

    public Adventure findAdventureByAdventureName(String adventureName) {
        return adventureDataRepository.findAdventureByAdventureName(adventureName);
    }

    public Adventure saveAdventure(Adventure adventure) {
        return adventureDataRepository.save(adventure);
    }

}
