package dev.example.dundemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum RaidName {

    NABDL_MATCHING("만들어진 신 나벨", "무의지의 장벽"),
    NABEL("만들어진 신 나벨", "무지의 악"),
    INAE("이내 황혼전", null);

    private final String raidName;
    private final String modeName;

    public boolean matches(String raidName, String modeName) {
        if (!Objects.equals(this.raidName, raidName)) return false;

        return this.modeName == null || Objects.equals(this.modeName, modeName);
    }
}
