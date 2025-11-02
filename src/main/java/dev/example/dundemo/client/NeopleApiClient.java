package dev.example.dundemo.client;

import dev.example.dundemo.enums.NeopleApiUrl;
import dev.example.dundemo.utils.ApiKeyProvider;
import dev.example.dundemo.web.dto.CharacterDTO;
import dev.example.dundemo.web.dto.CharacterInfoResponseDTO;
import dev.example.dundemo.web.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                    log.info("URL    : {}", clientRequest.url());
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
                // bodyToMono에 ParameterizedTypeReference를 사용하여 제네릭 타입을 명시
                .bodyToMono(CharacterInfoResponseDTO.class)
                .block();
    }
}
