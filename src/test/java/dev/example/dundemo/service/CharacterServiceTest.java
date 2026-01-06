package dev.example.dundemo.service;

import dev.example.dundemo.client.NeopleApiClient;
import dev.example.dundemo.domain.Character;
import dev.example.dundemo.enums.ServerName;
import dev.example.dundemo.repository.AdventureRepository;
import dev.example.dundemo.repository.CharacterRepository;
import dev.example.dundemo.utils.RaidClearCountUtil;
import dev.example.dundemo.web.dto.Character.CharacterRaidClearCountRequestDTO;
import dev.example.dundemo.web.dto.Character.CharacterRaidClearCountResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    // @Mock: 테스트에 필요한 의존성들의 가짜(Mock) 객체를 생성합니다.
    // 이 객체들은 실제 로직을 수행하지 않고, 우리가 시키는 대로만 동작합니다.
    @Mock
    private NeopleApiClient neopleApiClient;
    @Mock
    private RaidClearCountUtil raidClearCountUtil;
    @Mock
    private CharacterRepository characterRepository;
    @Mock
    private AdventureRepository adventureRepository;

    // @InjectMocks: @Mock으로 생성된 가짜 객체들을 테스트 대상인 CharacterService에 주입합니다.
    @InjectMocks
    private CharacterService characterService;

    @Test
    @DisplayName("DB에 캐릭터가 존재할 경우: 레이드 횟수를 갱신하고 결과를 반환한다")
    void getCharacterRaidClearCount_whenCharacterExistInDB() {

        // 1. 준비 (Arrange)
        // 테스트용 요청 DTO 생성
        var testRequest = new CharacterRaidClearCountRequestDTO();
        testRequest.setServerName(ServerName.CAIN.getCode());
        testRequest.setCharacterName("mockCharacterName");

        // DB에서 찾아올 가상의 캐릭터 객체 생성
        var mockCharacter = Character.builder()
                .serverId(ServerName.CAIN.getCode())
                .characterId("mockCharacterId")
                .characterName("mockCharacterName")
                .level(115)
                .jobId("mockJobId")
                .jobGrowId("mockJobGrowId")
                .jobName("mockJobName")
                .jobGrowName("mockJobGrowName")
                .fame(78000)
                .adventureName("mockAdventureName")
                .guildId("mockGuildId")
                .guildName("mockGuildName")
                .build();
        mockCharacter.setModifiedAt(LocalDateTime.now().minusHours(1));

        // characterRepository.findCharacterByServerAndName(...)가 호출되면
        // 위에서 만든 가짜 캐릭터 객체를 반환하도록 설정합니다. (실제 DB 조회 X)
        when(characterRepository.findCharacterByServerAndName(testRequest.getServerName(), testRequest.getCharacterName()))
                .thenReturn(mockCharacter);
        // raidClearCountUtil.refreshRaidClearCount(...) 메서드는 아무 동작도 하지 않도록 설정합니다
        // 이 메서드의 내부 로직은 RaidClearCountUtilTest에서 별도로 테스트해야 합니다
        doNothing().when(raidClearCountUtil).refreshRaidClearCount(any(Character.class), any(LocalDateTime.class));
        // neopleApiClient.getCharacterImage(...)가 호출되면 가짜 이미지 코드를 반환하도록 설정합니다
        when(neopleApiClient.getCharacterImage(anyString(), anyString())).thenReturn("mockImageCode");

        // 2. 실행 (Act)
        // 테스트 대상 메서드를 호출합니다
        CharacterRaidClearCountResponseDTO testResponse = characterService.getCharacterRaidClearCount(testRequest);

        // 3. 검증 (Assert)
        // 반환된 DTO의 내용이 기대한 값과 일치하는지 확인합니다
        assertThat(testResponse).isNotNull();
        assertThat(testResponse.getCharacterList()).hasSize(1);
        assertThat(testResponse.getCharacterList().get(0).getCharacterName()).isEqualTo("mockCharacterName");

        // raidClearCountUtil.refreshRaidClearCount가 1번 호출되었는지 검증합니다
        // 이를 통해 "DB에 캐릭터가 있을 때"의 로직 분기를 올바르게 탔는지 확인할 수 있습니다
        verify(raidClearCountUtil, times(1)).refreshRaidClearCount(any(Character.class), any(LocalDateTime.class));
        // characterRepository.saveCharacter가 1번 호출되었는지 검증합니다
        verify(characterRepository, times(1)).saveCharacter(any(Character.class));
        // 외부 API 호출은 없어야 합니다 (캐릭터 검색)
        verify(neopleApiClient, never()).getCharacterId(any(), any());
    }
}
