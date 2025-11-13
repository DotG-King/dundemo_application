package dev.example.dundemo.client;

import dev.example.dundemo.enums.NeopleApiUrl;
import dev.example.dundemo.enums.TimeLineCode;
import dev.example.dundemo.utils.ApiKeyProvider;
import dev.example.dundemo.web.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class NeopleApiClient {

    public static final String BASE_URL = "https://api.neople.co.kr";

    private final WebClient webClient;
    private final ApiKeyProvider apiKeyProvider;

    public NeopleApiClient(WebClient.Builder webClientBuilder,
                           ApiKeyProvider apiKeyProvider) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).filter(logRequest()).build();
        this.apiKeyProvider = apiKeyProvider;
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    log.info("URL : {}", clientRequest.url());
                    return Mono.just(clientRequest);
                }
        );
    }

    public ResponseDTO<CharacterDTO> getCharacterId(String serverId, String characterName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(NeopleApiUrl.CHARACTER_SEARCH.uri)
                        .queryParam("characterName", characterName)
                        .queryParam("apikey", apiKeyProvider.getApiKey())
                        .build(serverId))
                .retrieve()
                // bodyToMono에 ParameterizedTypeReference를 사용하여 제네릭 타입을 명시
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<CharacterDTO>>() {})
                .block();
    }

    public CharacterInfoResponseDTO getCharacterInfo(String serverId, String characterId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(NeopleApiUrl.CHARACTER_INFO.uri)
                        .queryParam("apikey", apiKeyProvider.getApiKey())
                        .build(serverId, characterId))
                .retrieve()
                .bodyToMono(CharacterInfoResponseDTO.class)
                .block();
    }

    public TimeLineResponseDTO getCharacterTimeLine(Map<String, Object> requestMap) {
        String serverId = requestMap.get("serverId").toString();
        String characterId = requestMap.get("characterId").toString();
        String start = requestMap.get("start").toString();
        String end = requestMap.get("end").toString();
        String code = requestMap.get("code").toString();
        String next = requestMap.get("next").toString();

        log.info("\nserverId : {}\ncharacterId : {}\nstart : {}\nend : {}\nnext : {}",
                serverId, characterId, start, end, Objects.equals(next, "") ? "next is blank" : "next is NOT blank");

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                            .path(NeopleApiUrl.CHARACTER_TIMELINE.uri)
                            .queryParam("apikey", apiKeyProvider.getApiKey())
                            .queryParamIfPresent("limit", Objects.equals(next, "") ? Optional.of(100) : Optional.empty())
                            .queryParamIfPresent("code", Objects.equals(next, "") ? Optional.of(code) : Optional.empty())
                            .queryParamIfPresent("endDate", Objects.equals(next, "") ? Optional.of(end) : Optional.empty())
                            .queryParamIfPresent("startDate", Objects.equals(next, "") ? Optional.of(start) : Optional.empty())
                            .queryParamIfPresent("next", Optional.ofNullable(next))
                            .build(serverId, characterId))
                .retrieve()
                .bodyToMono(TimeLineResponseDTO.class)
                .block();
    }
}
