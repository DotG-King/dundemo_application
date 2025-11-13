package dev.example.dundemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TimeLineCode {
    CHARACTER_CREATE(101, "캐릭터 생성"),
    CHARACTER_NAME_CHANGE(102, "캐릭터명 변경"),
    CHARACTER_JOB_GROW(103, "캐릭터 전직"),
    CHARACTER_MAX_LEVEL(104, "캐릭터 최고 레벨 달성"),
    ADVENTURE_NAME_CHANGE(105, "모험단명 변경"),
    RAID(201, "레이드"),
    CLEAR_TOWER_OF_SORROW(202, "비탄의 탑 정복"),
    CLEAR_TOWER_OF_DESPAIR(203, "절망의 탑 정복"),
    CLEAR_OLD_DEMON_BEAST_DUNGEON(204, "(구)마수던전 토벌"),
    CLEAR_HARD_IMPERIAL_ARENA(205, "제국투기장 하드모드 클리어"),
    CLEAR_DEMON_BEAST_DUNGEON(206, "마수던전 토벌"),
    CLEAR_FIEND_WAR(207, "핀드워 클리어"),
    CLEAR_TOWER_OF_GRAVE(208, "무덤의 탑 정복"),
    CLEAR_LEGION(209, "레기온 클리어"),
    RAID_ADVANCE_PARTY(210, "레이드(선발대)"),
    CARTEL_EXP_RANK_UP(301, "결투장 경험치 등급 상승"),
    ITEM_ENFORCE(401, "아이템 강화"),
    ITEM_AMPLIFICATION(402, "아이템 증폭"),
    ITEM_SMELTING(403, "아이템 제련"),
    ITEM_ALTERATION(404, "아이템 개조"),
    ITEM_ENGRAVING(405, "아이템 새김"),
    ITEM_SUCCESSION(406, "아이템 계승"),
    ITEM_FORGING(407, "아이템 단조"),
    GET_SEALED_LOCK_ITEM(501, "봉인된 자물쇠 아이템 획득"),
    GET_LEGENDARY_ITEM(502, "레전더리 획득"),
    GET_EKERN_LEGENDARY_ITEM(503, "에컨 레전더리 획득"),
    GET_ITEM_BOX_JAR(504, "아이템 획득(항아리&상자)"),
    GET_ITEM_DUNGEON_DROP(505, "아이템 획득(던전 드랍)"),
    GET_ITEM_TRADING_PIECE(506, "아이템 획득(조각 교환)"),
    GET_ITEM_RAID_CARD(507, "아이템 획득(레이드 카드 보상)"),
    GET_ITEM_SHOP(508, "아이템 획득(상점)"),
    ITEM_TRANSCENDENCE_TRANSMISSION(509, "아이템 초월 전승(NPC)"),
    ITEM_TRADING(510, "아이템 교환"),
    GET_ITEM_UPGRADE(511, "아이템 획득(업그레이드)"),
    WARRANT(512, "권능"),
    GET_ITEM_DUNGEON_CARD(513, "아이템 획득(던전 카드 보상)"),
    GET_ITEM_PRODUCTION_BOOK(514, "아이템 획득(제작서)"),
    ITEM_TRANSCENDENCE_RECEIPT(515, "아이템 초월 수령(NPC)"),
    ITEM_TRANSCENDENCE_STONE(516, "아이템 초월(초월의돌)"),
    ITEM_FUSION_SEPARATION(517, "아이템 융합 분리"),
    GET_SPECIAL_ITEM(518, "특수 아이템 획득"),
    ITEM_CONVERSION(519, "아이템 변환"),
    GET_ITEM_CRAFTING(520, "아이템 획득(장비 제작)"),
    GET_ITEM_RAID_AUCTION(521, "아이템 획득(레이드 경매 보상)");

    public final int code;
    public final String description;
}
