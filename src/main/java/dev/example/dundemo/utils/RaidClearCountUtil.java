package dev.example.dundemo.utils;

import dev.example.dundemo.client.NeopleApiClient;
import dev.example.dundemo.domain.Character;
import dev.example.dundemo.enums.RaidName;
import dev.example.dundemo.enums.TimeLineCode;
import dev.example.dundemo.web.dto.TimeLineResponseDTO;
import dev.example.dundemo.web.dto.timeline.TimeLineRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class RaidClearCountUtil {

    private final LocalDateTime raidLaunchDay;
    private final NeopleApiClient neopleApiClient;
    private final RefreshTimeCheckUtil refreshTimeCheckUtil;

    public RaidClearCountUtil(NeopleApiClient neopleApiClient, RefreshTimeCheckUtil refreshTimeCheckUtil) {
        // 나벨 출시일 -91로 검색
        this.raidLaunchDay = LocalDateTime.of(2025, 4, 17, 0, 0, 0).minusDays(91);
        this.neopleApiClient = neopleApiClient;
        this.refreshTimeCheckUtil = refreshTimeCheckUtil;
    }

    public void initRaidClearCount(Character targetCharacter) {

        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(90);
        initCharacterRaidClearCount(targetCharacter, start, end);
        initCharacterAdvanceRaidClearCount(targetCharacter, start, end);
    }

    public void refreshRaidClearCount(Character targetCharacter, LocalDateTime lastModifiedTime) {

        // 갱신할때 너무 자주 갱신되지 않도록 하는 코드 필요할듯
        LocalDateTime end = LocalDateTime.now();
        refreshCharacterRaidClearCount(targetCharacter, lastModifiedTime, end);
        refreshCharacterAdvanceRaidClearCount(targetCharacter, lastModifiedTime, end);
    }

    private void initCharacterRaidClearCount(Character targetCharacter, LocalDateTime start, LocalDateTime end) {
        do {
            String next = "";
            do {
                TimeLineRequestDTO raidRequest = TimeLineRequestDTO.builder()
                        .serverId(targetCharacter.getServerId())
                        .characterId(targetCharacter.getCharacterId())
                        .start(start)
                        .end(end)
                        .code(TimeLineCode.RAID.getCode())
                        .next(next)
                        .build();

                TimeLineResponseDTO raidTimeLine = neopleApiClient.getCharacterTimeLine(raidRequest.toMap());

                if (raidTimeLine == null || raidTimeLine.getTimeline() == null || raidTimeLine.getTimeline().getRows() == null) {
                    break;
                }

                raidTimeLine.getTimeline().getRows().forEach(
                        row -> {
                            if (row.getData() != null) {
                                if (RaidName.NABEL.matches(row.getData().getRaidName(), row.getData().getModeName())) {
                                    targetCharacter.increaseNabelClearCount();
                                } else if (RaidName.INAE.matches(row.getData().getRaidName(), row.getData().getModeName())){
                                    targetCharacter.increaseInaeClearCount();
                                }
                            }
                        });
                // next가 null이면 ""로 변환 값이 있으면 그대로
                next = Optional.ofNullable(raidTimeLine.getTimeline().getNext()).orElse("");
            } while (!next.isEmpty());

            end = start;
            start = end.minusDays(90);

        } while (!start.isBefore(raidLaunchDay));
    }

    private void initCharacterAdvanceRaidClearCount(Character targetCharacter, LocalDateTime start, LocalDateTime end) {
        do {
            String next = "";
            do {
                TimeLineRequestDTO advanceRaidRequest = TimeLineRequestDTO.builder()
                        .serverId(targetCharacter.getServerId())
                        .characterId(targetCharacter.getCharacterId())
                        .start(start)
                        .end(end)
                        .code(TimeLineCode.RAID_ADVANCE_PARTY.getCode())
                        .next(next)
                        .build();

                TimeLineResponseDTO advanceRaidTimeLine = neopleApiClient.getCharacterTimeLine(advanceRaidRequest.toMap());

                if (advanceRaidTimeLine == null || advanceRaidTimeLine.getTimeline() == null || advanceRaidTimeLine.getTimeline().getRows() == null) {
                    break;
                }

                advanceRaidTimeLine.getTimeline().getRows().forEach(
                        row -> {
                            if (row.getData() != null) {
                                if (RaidName.NABEL.matches(row.getData().getRaidName(), row.getData().getModeName())) {
                                    targetCharacter.increaseNabelClearCount();
                                } else if (RaidName.INAE.matches(row.getData().getRaidName(), row.getData().getModeName())){
                                    targetCharacter.increaseInaeClearCount();
                                }
                            }
                        });
                // next가 null이면 ""로 변환 값이 있으면 그대로
                next = Optional.ofNullable(advanceRaidTimeLine.getTimeline().getNext()).orElse("");
            } while (!next.isEmpty());

            end = start;
            start = end.minusDays(90);

        } while (!start.isBefore(raidLaunchDay));
    }

    private void refreshCharacterRaidClearCount(Character targetCharacter, LocalDateTime start, LocalDateTime end) {
        LocalDateTime lastModifiedTime = start;

        // 30초 내로는 갱신 불가능 하도록 제한
        if (!refreshTimeCheckUtil.canRefresh(lastModifiedTime)) {
            return;
        }

        do {
            String next = "";
            do {
                TimeLineRequestDTO raidRequest = TimeLineRequestDTO.builder()
                        .serverId(targetCharacter.getServerId())
                        .characterId(targetCharacter.getCharacterId())
                        .start(start)
                        .end(end)
                        .code(TimeLineCode.RAID.getCode())
                        .next(next)
                        .build();

                TimeLineResponseDTO raidTimeLine = neopleApiClient.getCharacterTimeLine(raidRequest.toMap());

                if (raidTimeLine == null || raidTimeLine.getTimeline() == null || raidTimeLine.getTimeline().getRows() == null) {
                    break;
                }

                raidTimeLine.getTimeline().getRows().forEach(
                        row -> {
                            if (row.getData() != null) {
                                if (RaidName.NABEL.matches(row.getData().getRaidName(), row.getData().getModeName())) {
                                    targetCharacter.increaseNabelClearCount();
                                } else if (RaidName.INAE.matches(row.getData().getRaidName(), row.getData().getModeName())){
                                    targetCharacter.increaseInaeClearCount();
                                }
                            }
                        });
                // next가 null이면 ""로 변환 값이 있으면 그대로
                next = Optional.ofNullable(raidTimeLine.getTimeline().getNext()).orElse("");
            } while (!next.isEmpty());

            end = start;
            start = end.minusDays(90);

        } while (!start.isBefore(lastModifiedTime));
    }

    private void refreshCharacterAdvanceRaidClearCount(Character targetCharacter, LocalDateTime start, LocalDateTime end) {
        LocalDateTime lastModifiedTime = start;

        // 30초 내로는 갱신 불가능 하도록 제한
        if (!refreshTimeCheckUtil.canRefresh(lastModifiedTime)) {
            return;
        }

        do {
            String next = "";
            do {
                TimeLineRequestDTO raidRequest = TimeLineRequestDTO.builder()
                        .serverId(targetCharacter.getServerId())
                        .characterId(targetCharacter.getCharacterId())
                        .start(start)
                        .end(end)
                        .code(TimeLineCode.RAID.getCode())
                        .next(next)
                        .build();

                TimeLineResponseDTO raidTimeLine = neopleApiClient.getCharacterTimeLine(raidRequest.toMap());

                if (raidTimeLine == null || raidTimeLine.getTimeline() == null || raidTimeLine.getTimeline().getRows() == null) {
                    break;
                }

                raidTimeLine.getTimeline().getRows().forEach(
                        row -> {
                            if (row.getData() != null) {
                                if (RaidName.NABEL.matches(row.getData().getRaidName(), row.getData().getModeName())) {
                                    targetCharacter.increaseNabelClearCount();
                                } else if (RaidName.INAE.matches(row.getData().getRaidName(), row.getData().getModeName())){
                                    targetCharacter.increaseInaeClearCount();
                                }
                            }
                        });
                // next가 null이면 ""로 변환 값이 있으면 그대로
                next = Optional.ofNullable(raidTimeLine.getTimeline().getNext()).orElse("");
            } while (!next.isEmpty());

            end = start;
            start = end.minusDays(90);

        } while (!start.isBefore(lastModifiedTime));
    }
}
