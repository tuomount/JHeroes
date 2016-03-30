package org.jheroes.gui;

/**
 * 
 * JHeroes CRPG Engine and Game
 * Copyright (C) 2014  Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 * 
 * 
 * Static class containing all ACTIONS used in GAME
 * 
 */
public class ActionCommands {
  public static final String ACTION_MAINMENU_NEWGAME= "MainMenuNewGame";
  public static final String ACTION_MAINMENU_CONTINUE= "MainMenuContinue";
  public static final String ACTION_MAINMENU_LOADGAME= "MainMenuLoadGame";
  public static final String ACTION_MAINMENU_SAVEGAME= "MainMenuSaveGame";
  public static final String ACTION_MAINMENU_CREDITS= "MainMenuCredits";
  public static final String ACTION_MAINMENU_EXIT= "MainMenuExit";
  public static final String ACTION_MAINMENU_HELP= "MainMenuHelp";
  public static final String ACTION_MAINMENU_OPTIONS= "MainMenuOptions";
  public static final String ACTION_CREDITS_OK= "CREDITS_OK";
  public static final String ACTION_OPTIONS_EXIT= "optionsExit";
  public static final String ACTION_OPTIONS_SAVENEXIT= "optionsSaveAndExit";

  public static final String ACTION_HERODOWN_MAINMENU= "HeroDownMainMenu";
  public static final String ACTION_HERODOWN_CONTINUE= "HeroDownContinue";
  public static final String ACTION_HERODOWN_LOADGAME= "HeroDownLoadGame";

  public static final String ACTION_STORY_CONTINUE ="StoryContinue";
  
  public static final String NEWGAME_PREV_CHAR = "PrevChar";
  public static final String NEWGAME_NEXT_CHAR = "NextChar";
  public static final String NEWGAME_SELECT_PERK = "SelectPerk";
  
  public static final String NEWGAME_MINUS_STRENGTH ="Strength-";
  public static final String NEWGAME_PLUS_STRENGTH ="Strength+";
  public static final String NEWGAME_MINUS_AGILITY ="Agility-";
  public static final String NEWGAME_PLUS_AGILITY ="Agility+";
  public static final String NEWGAME_MINUS_ENDURANCE ="Endurance-";
  public static final String NEWGAME_PLUS_ENDURANCE ="Endurance+";
  public static final String NEWGAME_MINUS_INTELLIGENCE ="Intelligence-";
  public static final String NEWGAME_PLUS_INTELLIGENCE ="Intelligence+";
  public static final String NEWGAME_MINUS_WISDOM ="Wisdom-";
  public static final String NEWGAME_PLUS_WISDOM ="Wisdom+";
  public static final String NEWGAME_MINUS_CHARISMA ="Charisma-";
  public static final String NEWGAME_PLUS_CHARISMA ="Charisma+";
  public static final String NEWGAME_MINUS_LUCK ="Luck-";
  public static final String NEWGAME_PLUS_LUCK ="Luck+";
  public static final String[] NEWGAME_MINUS_ATTRIBUTES = {NEWGAME_MINUS_STRENGTH,
    NEWGAME_MINUS_AGILITY, NEWGAME_MINUS_ENDURANCE, NEWGAME_MINUS_INTELLIGENCE,
    NEWGAME_MINUS_WISDOM, NEWGAME_MINUS_CHARISMA, NEWGAME_MINUS_LUCK};
  public static final String[] NEWGAME_PLUS_ATTRIBUTES = {NEWGAME_PLUS_STRENGTH,
    NEWGAME_PLUS_AGILITY, NEWGAME_PLUS_ENDURANCE, NEWGAME_PLUS_INTELLIGENCE,
    NEWGAME_PLUS_WISDOM, NEWGAME_PLUS_CHARISMA, NEWGAME_PLUS_LUCK};
  
  public static final String ATTRIBUTE_HELP_STRENGTH ="AttributeHelpStrength";
  public static final String ATTRIBUTE_HELP_AGILITY ="AttributeHelpAgility";
  public static final String ATTRIBUTE_HELP_ENDURANCE ="AttributeHelpEndurance";
  public static final String ATTRIBUTE_HELP_INTELLIGENCE ="AttributeHelpIntelligence";
  public static final String ATTRIBUTE_HELP_WISDOM ="AttributeHelpWisdom";
  public static final String ATTRIBUTE_HELP_CHARISMA ="AttributeHelpCharisma";
  public static final String ATTRIBUTE_HELP_LUCK ="AttributeHelpLuck";
  
  public static final String[] ATTRIBUTE_HELPS = {ATTRIBUTE_HELP_STRENGTH,
    ATTRIBUTE_HELP_AGILITY,ATTRIBUTE_HELP_ENDURANCE,ATTRIBUTE_HELP_INTELLIGENCE,
    ATTRIBUTE_HELP_WISDOM,ATTRIBUTE_HELP_CHARISMA,ATTRIBUTE_HELP_LUCK
  };
  
  public static final String NEWGAME_MINUS_UNARMED="Unarmed-";
  public static final String NEWGAME_PLUS_UNARMED="Unarmed+";
  public static final String NEWGAME_MINUS_MELEE="MELEE-";
  public static final String NEWGAME_PLUS_MELEE="MELEE+";
  public static final String NEWGAME_MINUS_RANGED_WEAPONS="Ranged-";
  public static final String NEWGAME_PLUS_RANGED_WEAPONS="Ranged+";
  public static final String NEWGAME_MINUS_DODGING="Dodging-";
  public static final String NEWGAME_PLUS_DODGING="Dodging+";
  public static final String NEWGAME_MINUS_WIZARDY="Wizardy-";
  public static final String NEWGAME_PLUS_WIZARDY="Wizardy+";
  public static final String NEWGAME_MINUS_SORCERY="Sorcery-";
  public static final String NEWGAME_PLUS_SORCERY="Sorcery+";
  public static final String NEWGAME_MINUS_QI_MAGIC="QiMagic-";
  public static final String NEWGAME_PLUS_QI_MAGIC="QiMagic+";
  public static final String NEWGAME_MINUS_BARTERING="Bartering-";
  public static final String NEWGAME_PLUS_BARTERING="Bartering+";
  public static final String NEWGAME_MINUS_DIPLOMACY="Diplomacy-";
  public static final String NEWGAME_PLUS_DIPLOMACY="Diplomacy+";
  public static final String NEWGAME_MINUS_LOCKPICKING="LockPicking-";
  public static final String NEWGAME_PLUS_LOCKPICKING="LockPicking+";
  public static final String[] NEWGAME_MINUS_SKILLS = {NEWGAME_MINUS_UNARMED,
    NEWGAME_MINUS_MELEE, NEWGAME_MINUS_RANGED_WEAPONS, NEWGAME_MINUS_DODGING,
    NEWGAME_MINUS_WIZARDY, NEWGAME_MINUS_SORCERY, NEWGAME_MINUS_QI_MAGIC,
    NEWGAME_MINUS_BARTERING, NEWGAME_MINUS_DIPLOMACY, NEWGAME_MINUS_LOCKPICKING};
  public static final String[] NEWGAME_PLUS_SKILLS = {NEWGAME_PLUS_UNARMED,
    NEWGAME_PLUS_MELEE, NEWGAME_PLUS_RANGED_WEAPONS, NEWGAME_PLUS_DODGING,
    NEWGAME_PLUS_WIZARDY, NEWGAME_PLUS_SORCERY, NEWGAME_PLUS_QI_MAGIC,
    NEWGAME_PLUS_BARTERING, NEWGAME_PLUS_DIPLOMACY, NEWGAME_PLUS_LOCKPICKING};
  
  public static final String SKILL_HELP_UNARMED = "SkillHelpUnarmed";
  public static final String SKILL_HELP_MELEE = "SkillHelpMelee";
  public static final String SKILL_HELP_RANGED_WEAPONS = "SkillHelpRandgedWeapons";
  public static final String SKILL_HELP_DODGING = "SkillHelpDodging";
  public static final String SKILL_HELP_WIZARDY = "SkillHelpWizardy";
  public static final String SKILL_HELP_SORCERY = "SkillHelpSorcery";
  public static final String SKILL_HELP_QI_MAGIC = "SkillHelpQiMagic";
  public static final String SKILL_HELP_BARTERING = "SkillHelpBartering";
  public static final String SKILL_HELP_DIPLOMACY = "SkillHelpDiplomacy";
  public static final String SKILL_HELP_LOCKPICKING = "SkillHelpLockPicking";
  
  public static final String[] SKILL_HELPS =  {SKILL_HELP_UNARMED,
    SKILL_HELP_MELEE,SKILL_HELP_RANGED_WEAPONS,SKILL_HELP_DODGING,
    SKILL_HELP_WIZARDY,SKILL_HELP_SORCERY,SKILL_HELP_QI_MAGIC,
    SKILL_HELP_BARTERING,SKILL_HELP_DIPLOMACY,SKILL_HELP_LOCKPICKING
  };

  
  public static final String GENERIC_BACK = "Back";
  public static final String GENERIC_NEXT = "Next";
  public static final String GENERIC_MUSIC_TIMER = "MusicTimer";
  public static final String GENERIC_TIMER ="GenericTimer";
  public static final String GAME_TURN_TIMER ="TurnTimer";
  
  public static final String GAME_LEADER_PARTY_1 = "LeaderParty1";
  public static final String GAME_LEADER_PARTY_2 = "LeaderParty2";
  public static final String GAME_LEADER_PARTY_3 = "LeaderParty3";
  public static final String GAME_LEADER_PARTY_4 = "LeaderParty4";
  public static final String GAME_SELECT_MEMBER1 = "PartyMember1";
  public static final String GAME_SELECT_MEMBER2 = "PartyMember2";
  public static final String GAME_SELECT_MEMBER3 = "PartyMember3";
  public static final String GAME_SELECT_MEMBER4 = "PartyMember4";
  public static final String GAME_SEARCH_TAKE = "SearchTake";
  public static final String GAME_SEARCH_TAKE_ALL = "SearchTakeAll";
  public static final String GAME_SPELL_CAST = "SpellCast";
  public static final String GAME_SPELL_CANCEL = "SpellCancel";
  public static final String GAME_TRAVEL_YES = "TravelYes";
  public static final String GAME_TRAVEL_NO = "TravelNo";
  
  public static final String GAME_BACK_TO_MAIN_MENU ="GoBackToMainMenu";
  public static final String GAME_INVENTORY ="ShowInventory";
  public static final String GAME_HELP ="ShowHelp";
  public static final String GAME_FULL_ATTACK ="GameFullAttack";
  
  public static final String SHEET_NEXT_CHAR = "SheetNextChar";
  public static final String SHEET_PREV_CHAR = "SheetPrevChar";
  public static final String SHEET_BACK_TO_GAME = "SheetBackToGame";
  public static final String SHEET_SELECT_PERK = "SheetSelectPerk";
  public static final String SHEET_FIRSTHAND = "SheetFirstHand";
  public static final String SHEET_SECONDHAND = "SheetSecondHand";
  public static final String SHEET_ARMOR = "SheetArmor";
  public static final String SHEET_RING = "SheetRing";
  public static final String SHEET_HEADGEAR = "SheetHeadGear";
  public static final String SHEET_AMULET = "SheetAmulet";
  public static final String SHEET_BOOTS = "SheetBoots";
  public static final String SHEET_USE_EQUIP = "SheetUseEquip";
  public static final String SHEET_DROP = "SheetDrop";
  public static final String SHEET_CAST = "SheetCast";
    
  public static final String TALK_END = "TalkEnd";
  public static final String TALK_LINES[] = {"TalkLine1","TalkLine2",
    "TalkLine3","TalkLine4","TalkLine5","TalkLine6","TalkLine7","TalkLine8"};
  
  public static final String JOURNAL_EXIT ="JournalExit";
  public static final String JOURNAL_NEXT ="JournalNext";
  public static final String JOURNAL_PREV ="JournalPrev";
  
  public static final String BARTERING_EXIT ="BarteringExit";
  public static final String BARTERING_BUY ="BarteringBuy";
  public static final String BARTERING_SELL ="BarteringSell";
  public static final String BARTERING_GIVE_1_COPPER="BarteringGive1Copper";
  public static final String BARTERING_GIVE_10_COPPER="BarteringGive10Copper";
  public static final String BARTERING_TAKE_1_COPPER="BarteringTake1Copper";
  public static final String BARTERING_TAKE_10_COPPER="BarteringTake10Copper";
  public static final String BARTERING_GIVE="BarteringGive";
  public static final String BARTERING_TAKE="BarteringTake";
  
  public static final String DEBUGMODE_EXIT = "DebugModeExit";
  public static final String DEBUGMODE_PLUS = "DebugModePlus";
  public static final String DEBUGMODE_MINUS = "DebugModeMinus";
    
  public static final String GAMES_SCREEN_LOAD1 = "GamesLoad1";
  public static final String GAMES_SCREEN_LOAD2 = "GamesLoad2";
  public static final String GAMES_SCREEN_LOAD3 = "GamesLoad3";
  public static final String GAMES_SCREEN_LOAD4 = "GamesLoad4";
  public static final String GAMES_SCREEN_LOAD5 = "GamesLoad5";
  public static final String GAMES_SCREEN_LOAD6 = "GamesLoad6";
  public static final String[] GAMES_SCREEN_LOAD = {GAMES_SCREEN_LOAD1,
    GAMES_SCREEN_LOAD2,GAMES_SCREEN_LOAD3,GAMES_SCREEN_LOAD4,GAMES_SCREEN_LOAD5,
    GAMES_SCREEN_LOAD6};
  public static final String GAMES_SCREEN_SAVE1 = "GamesSave1";
  public static final String GAMES_SCREEN_SAVE2 = "GamesSave2";
  public static final String GAMES_SCREEN_SAVE3 = "GamesSave3";
  public static final String GAMES_SCREEN_SAVE4 = "GamesSave4";
  public static final String GAMES_SCREEN_SAVE5 = "GamesSave5";
  public static final String GAMES_SCREEN_SAVE6 = "GamesSave6";
  public static final String[] GAMES_SCREEN_SAVE = {GAMES_SCREEN_SAVE1,
    GAMES_SCREEN_SAVE2,GAMES_SCREEN_SAVE3,GAMES_SCREEN_SAVE4,GAMES_SCREEN_SAVE5,
    GAMES_SCREEN_SAVE6};
  public static final String GAMES_SCREEN_CANCEL = "GamesCancel";

  public static final String GAMEHELP_EXIT = "GameHelpExit";
  public static final String GAMEHELP_SELECT = "GameHelpSelect";

}
