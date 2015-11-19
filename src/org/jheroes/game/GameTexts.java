package org.jheroes.game;


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
 * Class of static texts used all around the game, including some helps
 * and loading screen tips.
 * 
 */
public class GameTexts {
  
  public final static String HERO_KNIGHT_BACKGROUND ="He is a brave warrior in "+
                                                     "shiny armor. He has "+
                                                     "extra ordinary courage "+
                                                     "and fears nothing. His "+
                                                     "charismatic appearance "+
                                                     "makes him an excellent  "+
                                                     "leader.";
  public final static String HERO_KNIGHT_FIRST1 = "Daramur";
  public final static String HERO_KNIGHT_FIRST2 = "Brandir";
  public final static String HERO_KNIGHT_LAST = "Silverblade";
  public final static String HERO_RANGER_BACKGROUND ="She is an excellent "+
                                                     "archer. She handles her "+
                                                     "bows and arrows masterly. "+
                                                     "She also is able to fight "+
                                                     "in close range combat. "+
                                                     "Her looks makes her a "+
                                                     "good leader.";
  public final static String HERO_RANGER_FIRST1 = "Joanna";
  public final static String HERO_RANGER_FIRST2 = "Kiranda";
  public final static String HERO_RANGER_LAST = "Mightybow";
  public final static String HERO_WIZARD_BACKGROUND ="He is a bold wizard. "+
                                                     "He is able to cast "+
                                                     "mysterious spells which "+
                                                     "help venturing forth and "+
                                                     "cause fear in his "+
                                                     "enemies. His wisdom makes "+
                                                     "him a wise leader.";
  public final static String HERO_WIZARD_FIRST1 = "Zack";
  public final static String HERO_WIZARD_FIRST2 = "Jimmy";
  public final static String HERO_WIZARD_LAST = "Blackrobe";
  public final static String HERO_KUNGFU_MAN_BACKGROUND ="He is at his best when "+
                                                         "fighting without weapons "+
                                                         "or armors. His agility "+
                                                         "and speed makes him "+
                                                         "dangerous. He surely "+
                                                         "manages to get things "+
                                                         "done. That makes him "+
                                                         "good leader.";
  public final static String HERO_KUNGFU_MAN_FIRST1 = "Trevor";
  public final static String HERO_KUNGFU_MAN_FIRST2 = "Jake";
  public final static String HERO_KUNGFU_MAN_LAST = "Armbreaker";
  public final static String HERO_SPELLSWORD_BACKGROUND ="He is a spellsword. So "+
                                                          "he fights like a "+
                                                          "warrior and cast "+
                                                          "spells like wizard. "+
                                                          "Combination of these "+
                                                          "two makes him valiant "+
                                                          "leader.";
  public final static String HERO_SPELLSWORD_FIRST1 = "Erdar";
  public final static String HERO_SPELLSWORD_FIRST2 = "Felbern";
  public final static String HERO_SPELLSWORD_LAST = "Brimstone";
  public final static String HERO_SWASHBUCKLER_BACKGROUND ="She is a master with "+
                                                           "swords and light armors. "+
                                                           "She is a swashbuckler. "+
                                                           "Her stunning looks make "+
                                                           "her very influencing "+
                                                           "and thus great leader.";  
  public final static String HERO_SWASHBUCKLER_FIRST1 = "Elise";
  public final static String HERO_SWASHBUCKLER_FIRST2 = "Jane";
  public final static String HERO_SWASHBUCKLER_LAST = "Noblehouse";
  public final static String HERO_HEALER_BACKGROUND ="She is a healer. "+
                                                     "She knows how to heal "+
                                                     "wounds and remove curses. "+
                                                     "Her knowledge makes her "+
                                                     "very valueable in combat. "+
                                                     "Healers are also wise "+
                                                     "which makes her good "+
                                                     "leader.";  
  public final static String HERO_HEALER_FIRST1 = "Elenia";
  public final static String HERO_HEALER_FIRST2 = "Samantha";
  public final static String HERO_HEALER_LAST = "Strongwood";
  public final static String HERO_KUNGFU_WOMAN_BACKGROUND ="She is at her best when "+
                                                     "fighting without weapons "+
                                                     "or armors. Her agility "+
                                                     "and speed makes her "+
                                                     "dangerous. Her inner chi "+
                                                     "makes her stronger than "+
                                                     "she looks. All this make "+
                                                     "her excellent leader.";
  public final static String HERO_KUNGFU_WOMAN_FIRST1 = "Taylor";
  public final static String HERO_KUNGFU_WOMAN_FIRST2 = "Lisa";
  public final static String HERO_KUNGFU_WOMAN_LAST = "Swiftfist";
  
  public final static String[] HERO_FULL_NAMES = 
    {HERO_KNIGHT_FIRST1+" "+HERO_KNIGHT_LAST,
     HERO_KNIGHT_FIRST2+" "+HERO_KNIGHT_LAST,
     HERO_RANGER_FIRST1+" "+HERO_RANGER_LAST,
     HERO_RANGER_FIRST2+" "+HERO_RANGER_LAST,
     HERO_WIZARD_FIRST1+" "+HERO_WIZARD_LAST,
     HERO_WIZARD_FIRST2+" "+HERO_WIZARD_LAST,
     HERO_KUNGFU_MAN_FIRST1+" "+HERO_KUNGFU_MAN_LAST,
     HERO_KUNGFU_MAN_FIRST2+" "+HERO_KUNGFU_MAN_LAST,
     HERO_SPELLSWORD_FIRST1+" "+HERO_SPELLSWORD_LAST,
     HERO_SPELLSWORD_FIRST2+" "+HERO_SPELLSWORD_LAST,
     HERO_SWASHBUCKLER_FIRST1+" "+HERO_SWASHBUCKLER_LAST,
     HERO_SWASHBUCKLER_FIRST2+" "+HERO_SWASHBUCKLER_LAST,
     HERO_HEALER_FIRST1+" "+HERO_HEALER_LAST,
     HERO_HEALER_FIRST2+" "+HERO_HEALER_LAST,     
     HERO_KUNGFU_WOMAN_FIRST1+" "+HERO_KUNGFU_WOMAN_LAST,
     HERO_KUNGFU_WOMAN_FIRST2+" "+HERO_KUNGFU_WOMAN_LAST};
  public final static String HELP_ATTRIBUTE_STRENGTH ="Big strength enables to carry more stuff, " +
  		"deal more damage and get more hitpoints. Skills: "+
  		"Melee, unarmed and ranged combat.";
  public final static String HELP_ATTRIBUTE_AGILITY ="High agility deals more damage on ranged weapons. "+
    "Skills: Dodging, ranged combat and unarmed.";
  public final static String HELP_ATTRIBUTE_ENDURANCE ="Greater endurance gives more hitpoints "+
  "and stamina points. Skills: Melee and unarmed";
  public final static String HELP_ATTRIBUTE_INTELLIGENCE ="Greater intelligence gives more skill points." +
  		" Skills: Wizardy, Qi Magic and Lock picking.";
  public final static String HELP_ATTRIBUTE_WISDOM ="Greater wisdom gives more skill points and" +
      " provides resistance to mind affecting spells." +
      " Skills: Diplomacy, Qi Magic and Sorcery.";
  public final static String HELP_ATTRIBUTE_CHARISMA ="Greater Charisma deals more damage with targeted spells." +
      " Skills: Bartering, Diplomacy and Sorcery";
  public final static String HELP_ATTRIBUTE_LUCK ="Luck determines critical chance for each " +
      "skill. This includes also attacking. Low luck increases failure chance.";
    
  public final static String HELP_SKILL_UNARMED = "Martial arts skill is used when attacking unarmed "+
  " or unarmed weapons like gauntlets.";
  public final static String HELP_SKILL_MELEE = "Melee skill is used when attacking with "+
      "close range weapons.";
  public final static String HELP_SKILL_RANGED_ATTACK = "Ranged combat is used when attacking with "+
      "long range weapons like bows.";
  public final static String HELP_SKILL_DODGING = "Dodging is used when being under attack. "+
      "Heavier armor makes dodging more difficult.";
  public final static String HELP_SKILL_WIZARDY = "Wizardy is magical skill. Wizardy spells "+
      "contains enchants, cursings and healing.";
  public final static String HELP_SKILL_SORCERY = "Sorcery is magical skill. Sorcery spells "+
      "contains only combat spells.";
  public final static String HELP_SKILL_QI_MAGIC = "Qi magic is magical skill and contains "+
      "spells that boost caster itself.";
  public final static String HELP_SKILL_BARTERING = "Bartering skill is used when buying "+
      "and selling equipment and items.";
  public final static String HELP_SKILL_DIPLOMACY = "Diplomacy skill is used when "+
      "influencing others while speaking.";
  public final static String HELP_SKILL_LOCK_PICKING = "Lock picking skill is used when "+
      "trying to open locked doors.";
  
  
  public final static String LOAD_TEXTS[] =  {
      "Blunt weapons does half of lethal and half of non-lethal damage.",
      "Piercing damage always passes through armor.", 
      "When there is not enough stamina then non-lethal damage becomes lethal.",
      "Casting spells uses stamina. One can cast spell without stamina left then casting uses hit points.",
      "Perk skill point requirements are how many skill poinst are assigned to " +
      "skill not the total amount of skill.",
      "Press F10 to take a screen shot.",
      "Heavy armor provides more protection against lethal damage but reduces dodging skill.",
      "All of sorcery spells are damage dealing combat spells.",
      "Most of Qi magic spells are personal boosts for caster. These spells focus on martial arts skills.",
      "Wizardy spells are generic spells that heal and boosts others.",
      "Casting requires two empty hands, unless combat casting perks are selected.",
      "Experience is divided among party members except personal experience.",
      "Any party member can talk to non player characters.",
      "Cursed weapons always require more stamina to use.",
      "Party is fully healed if sleeps at Inns or taverns.",
      "New spells can be learned from scrolls. Scrolls can be read only once.",
      "Wizardy spells Identify and Detect Magic help to recognize magical or cursed items.",
      "Armor or dodging does not protect against mental spells but willpower does.",
      "Hitting is difficult in darkness, but torches, lanterns and spells can make it easier.",
      "Darkness spell can hide target and if target has darkvision there is no negative effect about it.",
      "Brigthly light targets are easier to hit in combat.",
      "Throwable weapons can be thrown in combat. Such are daggers, spears and throwing axes.",
      "Order of equiping dual wield weapons is important. Second wield weapon is placed as secondary weapon.",
      "Smite Undead is very powerful against undead creatures like skeletons, zombies etc, but useless against others.",
      "All joinable party members have special quests.",
      "Playing the role is rewarded with special role playing experience points.",
      "If your party is missing healer, use healing kits instead.",
      "Smasher perks works both blunt weapon fighter and unarmed but unarmed fighter benefits more on own perks.",
      "Game is automatically saved when game is closed when game is in main game screen.",
      "Digging with shovel will reveal hidden treasures but one need to know where to dig.",
      "Tomes are rare magic items that permantly increase attributes when read.",
      "Might boost both melee and dodging skills.",
      "Slaying weapons cause massive extra damage on critical hits."};
  
  public final static String HELP_TEXT_KEYS = "Quick help for keys:\n\n" +
  		                                 "Arrows: Move the character\n"+
                                       "I: Inventory\n"+
                                       "C: Cast spell if character is able\n"+
                                       "A: Full Attack\n"+
                                       "Z: Single Attack\n"+
                                       "J: View journal\n"+
                                       "T: Talk to someone\n"+
                                       "L: Look something\n"+
                                       "P: Pick up item under the character\n"+
                                       "S: Search for items from surroundings\n"+
                                       "R: Rest for one Hour, recovers stamina\n"+
                                       "W: Wait for one turn, recovers some stamina. Usable also in combat.\n"+
                                       "Enter: Confirm action, for example attacking, looking etc.\n"+
                                       "F1: Game help\n"+
                                       "F10: Take a screenshot in game\n"+
                                       "Esc: Back to main menu or go back";
  
  public final static String HELP_TEXT_ATTRIBUTES = "There are seven different attributes:\n"+
    "Strength, Agility, Endurance, Intelligence, Wisdom, Charisma and Luck.\n"+
    "\nStrength: "+HELP_ATTRIBUTE_STRENGTH+"\n\nAgility: "+HELP_ATTRIBUTE_AGILITY+
    "\n\nEndurance: "+HELP_ATTRIBUTE_ENDURANCE+"\n\nIntelligence: "+HELP_ATTRIBUTE_INTELLIGENCE+
    "\n\nWisdom: "+HELP_ATTRIBUTE_WISDOM+"\n\nCharisma: "+HELP_ATTRIBUTE_CHARISMA+
    "\n\nLuck: "+HELP_ATTRIBUTE_LUCK+ " Low effects how often " +
        "cursed items causes life drain.";

  public final static String HELP_TEXT_SKILLS = "There are ten different skills:\n"+
     "\nMartial arts: "+HELP_SKILL_UNARMED+"\n\nMelee: "+HELP_SKILL_MELEE+
     "\n\nRanged combat:"+HELP_SKILL_RANGED_ATTACK+"\n\nDodging: "+HELP_SKILL_DODGING+
     "\n\nWizardy: "+HELP_SKILL_WIZARDY+"\n\nSorcery: "+HELP_SKILL_SORCERY+
     "\n\nQi Magic: "+HELP_SKILL_QI_MAGIC+"\n\nBartering: "+HELP_SKILL_BARTERING+
     "\n\nDiplomacy: "+HELP_SKILL_DIPLOMACY+"\n\nLock picking: "+HELP_SKILL_LOCK_PICKING;
  public final static String HELP_TEXT_PERKS = "Perks are special abilities which allow character\n"+
      "do special things or directly boost skills or attributes. Perks have requirements which" +
      " must be filled before perk can be seletected. Perk skill requirement is always about how"+
      " many skill points is adjusted for the skill not the total value of skill.\n\nPerks are gained in following manner:"
      +"First level two can be selected. For there on each even level gives one perk.\n\n"+
      "Shortly saying perks are character features which makes each character unique. One can be"+
      " great with martial arts, one can be great with two handed weapons. Perks can also provide" +
      " tactical advantages like point blank shot allows character use ranged attacks against enemies next to" +
      " him or her. Without it he or she would need to change melee weapon before attacking.";
  public final static String HELP_TEXT_LEVELING_UP = "When character gains level up arrow appears"+
      "n ext character face. Each level character gains 10+wisdom+intellinge amount of skill points"+
      " and on even level character gains one perk. It is recommended to save before actually make" +
      " the level up. Since once selections are made they are final. So think carefully what to select" +
      " and think which way to develop each character.";  
  public final static String HELP_TEXT_HOW_TO_START = "Game will be easier when your party members" +
      " are able to perform tasks. This means that focus first only couple of skills. One or two" +
      " combat skill and depending on combat skills whether dodging is needed or not. For example" +
      " character with high ranged attack skill might now require dodging. Also dodging might" +
      " not be that important if character wears heavy armor. So trying to master each skill in" +
      " very beginning is impossible and only makes game more difficult.\n\n" +
      "Perks are also important what to select. For example character with martial-arts might not" +
      " need combat casting perks, since unarmed weapons do not affect on casting. Athletic perk is" +
      " maybe the best perk in game and would benefit almost every character. Since this" +
      " allows character gain stamina points more rapidly and stamina points are even saved" +
      " when attacking or casting spells.";

}
