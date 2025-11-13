package dev.example.dundemo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document(collection= "adventure")
public class Adventure extends BaseTimeEntity {
    @Id
    private String id;

    @Indexed
    private String adventureName;

    private List<Character> character = new ArrayList<>();

    @Builder
    public Adventure(String adventureName) {
        this.adventureName = adventureName;
    }

    public void addCharacter(Character character) {
        this.character.add(character);
    }

}
