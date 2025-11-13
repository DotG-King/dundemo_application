package dev.example.dundemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NeopleApiUrl {
    CHARACTER_SEARCH("/df/servers/{serverId}/characters"),
    CHARACTER_INFO("/df/servers/{serverId}/characters/{characterId}"),
    CHARACTER_TIMELINE("/df/servers/{serverId}/characters/{characterId}/timeline");

    public final String uri;
}
