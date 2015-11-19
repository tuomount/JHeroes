package org.jheroes.map.character;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
 * All perks are listed in this class.
 * 
 */
public class Perks {
  
  public static final String PERK_TRAINED_STRENGTH = "Trained strength";
  public static final String PERK_TRAINED_STRENGTH_DESC = "Increase strength by one";
  public static final String PERK_TRAINED_STRENGTH_REQU = "Strength less than 10.";
  public static final String PERK_TRAINED_STRENGTH2 = "Trained strength(2)";
  public static final String PERK_TRAINED_STRENGTH2_DESC = "Increase strength by one";
  public static final String PERK_TRAINED_STRENGTH2_REQU = "Strength less than 10 and trained strength perk.";
  public static final String PERK_TRAINED_AGILITY = "Trained agility";
  public static final String PERK_TRAINED_AGILITY_DESC = "Increase agility by one";
  public static final String PERK_TRAINED_AGILITY_REQU = "Agility less than 10.";
  public static final String PERK_TRAINED_AGILITY2 = "Trained agility(2)";
  public static final String PERK_TRAINED_AGILITY2_DESC = "Increase agility by one";
  public static final String PERK_TRAINED_AGILITY2_REQU = "Agility less than 10 and trained agility perk.";
  public static final String PERK_TRAINED_ENDURANCE = "Trained endurance";
  public static final String PERK_TRAINED_ENDURANCE_DESC = "Increase endurance by one";
  public static final String PERK_TRAINED_ENDURANCE_REQU = "Endurance less than 10.";
  public static final String PERK_TRAINED_ENDURANCE2 = "Trained endurance(2)";
  public static final String PERK_TRAINED_ENDURANCE2_DESC = "Increase agility by one";
  public static final String PERK_TRAINED_ENDURANCE2_REQU = "Endurance less than 10 and trained endurance perk.";
  public static final String PERK_TRAINED_INTELLIGENCE = "Trained intelligence";
  public static final String PERK_TRAINED_INTELLIGENCE_DESC = "Increase intelligence by one";
  public static final String PERK_TRAINED_INTELLIGENCE_REQU = "Intelligence less than 10.";
  public static final String PERK_TRAINED_INTELLIGENCE2 = "Trained intelligence(2)";
  public static final String PERK_TRAINED_INTELLIGENCE2_DESC = "Increase intelligence by one";
  public static final String PERK_TRAINED_INTELLIGENCE2_REQU = "Intelligence less than 10 and trained intelligence perk.";
  public static final String PERK_TRAINED_WISDOM = "Trained wisdom";
  public static final String PERK_TRAINED_WISDOM_DESC = "Increase wisdom by one";
  public static final String PERK_TRAINED_WISDOM_REQU = "Wisdom less than 10.";
  public static final String PERK_TRAINED_WISDOM2 = "Trained wisdom(2)";
  public static final String PERK_TRAINED_WISDOM2_DESC = "Increase wisdom by one";
  public static final String PERK_TRAINED_WISDOM2_REQU = "Wisdom less than 10 and trained wisdom perk.";
  public static final String PERK_TRAINED_CHARISMA = "Trained charisma";
  public static final String PERK_TRAINED_CHARISMA_DESC = "Increase charisma by one";
  public static final String PERK_TRAINED_CHARISMA_REQU = "Charisma less than 10.";
  public static final String PERK_TRAINED_CHARISMA2 = "Trained charisma(2)";
  public static final String PERK_TRAINED_CHARISMA2_DESC = "Increase charisma by one";
  public static final String PERK_TRAINED_CHARISMA2_REQU = "Charisma less than 10 and trained charisma perk.";
  public static final String PERK_TRAINED_LUCK = "Trained luck";
  public static final String PERK_TRAINED_LUCK_DESC = "Increase luck by one";
  public static final String PERK_TRAINED_LUCK_REQU = "Luck less than 10.";
  public static final String PERK_TRAINED_LUCK2 = "Trained luck(2)";
  public static final String PERK_TRAINED_LUCK2_DESC = "Increase luck by one";
  public static final String PERK_TRAINED_LUCK2_REQU = "Luck less than 10 and trained luck perk.";
  public static final String PERK_SHIELD_PROFIENCY = "Shield proficiency";
  public static final String PERK_SHIELD_PROFIENCY_DESC = "Equip shields";
  public static final String PERK_SHIELD_PROFIENCY_REQU = "Strength greater than 4.";
  public static final String PERK_SHIELD_FIGHTER = "Shield fighter";
  public static final String PERK_SHIELD_FIGHTER_DESC = "Gain attack with shield. Attack with shield is 50% of melee skill.";
  public static final String PERK_SHIELD_FIGHTER_REQU = "Shield profiency perk.";
  public static final String PERK_IMPROVED_SHIELD_FIGHTER = "Improved shield fighter";
  public static final String PERK_IMPROVED_SHIELD_FIGHTER_DESC = "Attack with shield is 75% of melee skill.";
  public static final String PERK_IMPROVED_SHIELD_FIGHTER_REQU = "Shield fighter perk and melee skill 50 or greater.";
  public static final String PERK_MASTER_SHIELD_FIGHTER = "Master shield fighter";
  public static final String PERK_MASTER_SHIELD_FIGHTER_DESC = "Attack with shield is 90% of melee skill.";
  public static final String PERK_MASTER_SHIELD_FIGHTER_REQU = "Improved shield fighter perk and melee skill 75 or greater.";
  public static final String PERK_LETHAL_FISTS = "Lethal fists";
  public static final String PERK_LETHAL_FISTS_DESC = "Unarmed damage 2-6. Half of damage is now lethal.";
  public static final String PERK_LETHAL_FISTS_REQU = "Strength greater than 4.";
  public static final String PERK_QI_FISTS = "Qi fists";
  public static final String PERK_QI_FISTS_DESC = "Unarmed damage 2-8.  Extra attack with bare hands.";
  public static final String PERK_QI_FISTS_REQU = "Lethal fists perk and unarmed skill 30 or greater.";
  public static final String PERK_QI_STRIKE = "Qi strike";
  public static final String PERK_QI_STRIKE_DESC = "Unarmed damage 4-10. Unarmed piercing power is 1.";
  public static final String PERK_QI_STRIKE_REQU = "Qi fists perk and unarmed skill 60 or greater.";
  public static final String PERK_QI_BODY = "Qi body";
  public static final String PERK_QI_BODY_DESC = "Increase armor rating by 2";
  public static final String PERK_QI_BODY_REQU = "QI strike perk, wisdom greater than 5 and level greater than 9.";
  public static final String PERK_KUNG_FU_MASTER = "Kung-Fu master";
  public static final String PERK_KUNG_FU_MASTER_DESC = "Increase unarmed skill by 10. Unarmed damage 6-12. Unarmed piercing power is 2.";
  public static final String PERK_KUNG_FU_MASTER_REQU = "Qi body perk, unarmed skill 90 or greater and level greater than 15.";
  public static final String PERK_NEGOTIATOR = "Negotiator";
  public static final String PERK_NEGOTIATOR_DESC = "Increase diplomacy and bartering skills by 15";
  public static final String PERK_NEGOTIATOR_REQU = "Charisma greater than 5.";
  public static final String PERK_DUAL_WIELDER = "Dual wielder";
  public static final String PERK_DUAL_WIELDER_DESC = "Equip off hand weapon including unarmed weapons. Attack with 50% of melee skill.";
  public static final String PERK_DUAL_WIELDER_REQU = "Agility greater than 5.";
  public static final String PERK_IMPROVED_DUAL_WIELDER = "Improved dual wielder";
  public static final String PERK_IMPROVED_DUAL_WIELDER_DESC = "Off hand attack is 75% of melee skill.";
  public static final String PERK_IMPROVED_DUAL_WIELDER_REQU = "Dual wielder perk and level greater than 5.";
  public static final String PERK_MASTER_DUAL_WIELDER = "Master dual wielder";
  public static final String PERK_MASTER_DUAL_WIELDER_DESC = "Off hand attack is 90% of melee skill.";
  public static final String PERK_MASTER_DUAL_WIELDER_REQU = "Improved dual wield perk and level greater than 11.";
  public static final String PERK_MAGICALLY_ACTIVE = "Magically active";
  public static final String PERK_MAGICALLY_ACTIVE_DESC = "Increase all magic skill by 5";
  public static final String PERK_MAGICALLY_ACTIVE_REQU = "Intelligence, wisdom and charisma all must be 7 or greater.";
  public static final String PERK_MAGICALLY_AWAKENED = "Magically awakened";
  public static final String PERK_MAGICALLY_AWAKENED_DESC = "Increase all magic skill by 10";
  public static final String PERK_MAGICALLY_AWAKENED_REQU = "Magically active perk and level 8 or greater.";
  public static final String PERK_MAGICALLY_ENLIGHTENED = "Magically enlightened";
  public static final String PERK_MAGICALLY_ENLIGHTENED_DESC = "Increase all magic skill by 15";
  public static final String PERK_MAGICALLY_ENLIGHTENED_REQU = "Magically awakened and level 15 or greater.";
  public static final String PERK_ATHLETIC = "Athletic";
  public static final String PERK_ATHLETIC_DESC = "All actions cost 2 stamina points less. Stamina is also recovered twice as fast";
  public static final String PERK_ATHLETIC_REQU = "Endurance is greater than 5.";
  public static final String PERK_DIEHARD = "Diehard";
  public static final String PERK_DIEHARD_DESC = "Gain extra health on each level";
  public static final String PERK_DIEHARD_REQU = "Endurance is greater than 5 and level is greater than 5.";
  public static final String PERK_UNPREDICTABLE_MOVES = "Unpredictable moves";
  public static final String PERK_UNPREDICTABLE_MOVES_DESC = "Increase dodging skill by 15 and unarmed skill by 5";
  public static final String PERK_UNPREDICTABLE_MOVES_REQU = "Agility is greater than 6 and unarmed skill is 40 or more.";
  public static final String PERK_RANGED_FIGHTER = "Ranged fighter";
  public static final String PERK_RANGED_FIGHTER_DESC = "Increase ranged attacks by 5";
  public static final String PERK_RANGED_FIGHTER_REQU = "Agility is greater than 4.";
  public static final String PERK_IMPROVED_RANGED_FIGHTER = "Improved ranged fighter";
  public static final String PERK_IMPROVED_RANGED_FIGHTER_DESC = "Increase ranged attacks by 10";
  public static final String PERK_IMPROVED_RANGED_FIGHTER_REQU = "Ranged fighter perk and ranged attacks is 30 or more.";
  public static final String PERK_MASTER_RANGED_FIGHTER = "Master ranged fighter";
  public static final String PERK_MASTER_RANGED_FIGHTER_DESC = "Increase ranged attacks by 15";
  public static final String PERK_MASTER_RANGED_FIGHTER_REQU = "Improved ranged fighter perk and ranged attacks is 60 or more.";
  public static final String PERK_LOCK_SMITH = "Lock smith";
  public static final String PERK_LOCK_SMITH_DESC = "Increase lock picking skill by 10";
  public static final String PERK_LOCK_SMITH_REQU = "Locking picking is 30 or more.";
  public static final String PERK_MASTER_LOCK_SMITH = "Master lock smith";
  public static final String PERK_MASTER_LOCK_SMITH_DESC = "Increase lock picking skill by 20";
  public static final String PERK_MASTER_LOCK_SMITH_REQU = "Lock smith perk and locking picking is 60 or more.";
  public static final String PERK_MERCENARY = "Mercenary";
  public static final String PERK_MERCENARY_DESC = "Increase armed melee attacks by 5";
  public static final String PERK_MERCENARY_REQU = "Melee skill is 25 or more.";
  public static final String PERK_MANATARMS = "Man-at-arms";
  public static final String PERK_MANATARMS_DESC = "Increase armed melee attacks by 10";
  public static final String PERK_MANATARMS_REQU = "Mercenary perk and melee skil is 50 or more.";
  public static final String PERK_MASTER_OF_WEAPONS = "Master of weapons";
  public static final String PERK_MASTER_OF_WEAPONS_DESC = "Increase armed melee attacks by 15";
  public static final String PERK_MASTER_OF_WEAPONS_REQU = "Man-at-arms perk and melee skill is 75 or more.";
  public static final String PERK_STRONG_BACK = "Strong back";
  public static final String PERK_STRONG_BACK_DESC = "Increase carrying capacity by 15kg";
  public static final String PERK_STRONG_BACK_REQU = "Strength is greater than 5.";
  public static final String PERK_STRONG_BONES ="Strong bones";
  public static final String PERK_STRONG_BONES_DESC ="Improve armor rating and critical armor rating by 1";
  public static final String PERK_STRONG_BONES_REQU ="Endurance is greater than 7 and level is 8 or more.";
  public static final String PERK_RAPID_SHOT ="Rapid shot";
  public static final String PERK_RAPID_SHOT_DESC ="Gain extra attack with ranged weapons. Attack with 50% of ranged skill";
  public static final String PERK_RAPID_SHOT_REQU ="Ranged attack is 30 or more and agility is greater than 6.";
  public static final String PERK_IMPROVED_RAPID_SHOT ="Improved rapid shot";
  public static final String PERK_IMPROVED_RAPID_SHOT_DESC ="Attack with 75% of ranged skill";
  public static final String PERK_IMPROVED_RAPID_SHOT_REQU ="Rapid shot perk and ranged attack skill is 50 or more.";
  public static final String PERK_MASTER_RAPID_SHOT ="Master rapid shot";
  public static final String PERK_MASTER_RAPID_SHOT_DESC ="Attack with 90% of ranged skill";
  public static final String PERK_MASTER_RAPID_SHOT_REQU ="Improved rapid shot perk and ranged attack skill is 75 or more.";
  public static final String PERK_TWO_HANDED_WEAPON_FIGHTER ="Two handed weapon fighter";
  public static final String PERK_TWO_HANDED_WEAPON_FIGHTER_DESC ="Attack with two handed weapons deals one extra damage.";
  public static final String PERK_TWO_HANDED_WEAPON_FIGHTER_REQU ="Strength is greater than 6.";
  public static final String PERK_IMPROVED_TWO_HANDED_WEAPON_FIGHTER ="Improved 2-H weapon fighter";
  public static final String PERK_IMPROVED_TWO_HANDED_WEAPON_FIGHTER_DESC ="Attack with two handed weapons deals two extra damage.";
  public static final String PERK_IMPROVED_TWO_HANDED_WEAPON_FIGHTER_REQU ="Two handed weapon fighter perk and melee skill is 30 or more.";
  public static final String PERK_MASTER_TWO_HANDED_WEAPON_FIGHTER ="Master 2-H weapon fighter";
  public static final String PERK_MASTER_TWO_HANDED_WEAPON_FIGHTER_DESC ="Attack with two handed weapons gain strength bonus as 2 times";
  public static final String PERK_MASTER_TWO_HANDED_WEAPON_FIGHTER_REQU ="Improved two handed weapon fighter perk and melee skill is 60 or more.";
  public static final String PERK_STRONG_WILLPOWER ="Strong Willpower";
  public static final String PERK_STRONG_WILLPOWER_DESC ="10% resistance for mind affecting spells.";
  public static final String PERK_STRONG_WILLPOWER_REQU ="Wisdom is greater than 6.";
  public static final String PERK_INNER_WILLPOWER ="Inner Willpower";
  public static final String PERK_INNER_WILLPOWER_DESC ="10% resistance for mind affecting spells.";
  public static final String PERK_INNER_WILLPOWER_REQU ="Strong willpower perk and level is 8 or more.";
  public static final String PERK_BONEHEAD ="Bonehead";
  public static final String PERK_BONEHEAD_DESC ="20% resistance for mind affecting spells.";
  public static final String PERK_BONEHEAD_REQU ="Wisdom is less than 4 but endurance is 6 or more.";
  public static final String PERK_FENCER = "Fencer";
  public static final String PERK_FENCER_DESC = "Increase both dodge and melee attacks by 5 if light weighted.";
  public static final String PERK_FENCER_REQU = "Agility is greater than 6.";
  public static final String PERK_MASTER_FENCER = "Master fencer";
  public static final String PERK_MASTER_FENCER_DESC = "Increase both dodge and melee attacks by 5 if light weighted.";
  public static final String PERK_MASTER_FENCER_REQU = "Fencer perk and level is 8 or more.";
  public static final String PERK_COMBAT_CASTING = "Combat casting";
  public static final String PERK_COMBAT_CASTING_DESC = "Cast spells with only one empty hand.";
  public static final String PERK_COMBAT_CASTING_REQU = "Agility is 5 or more and one magic skill is 5 or more.";
  public static final String PERK_MASTER_COMBAT_CASTING = "Master combat casting";
  public static final String PERK_MASTER_COMBAT_CASTING_DESC = "Cast spells without empty hands.";
  public static final String PERK_MASTER_COMBAT_CASTING_REQU = "Combat casting and one magic skill is 30 or more and melee skill is 30 or more";
  public static final String PERK_POINT_BLANK_SHOT = "Point blank shot";
  public static final String PERK_POINT_BLANK_SHOT_DESC = "Able to shoot in close range combat";
  public static final String PERK_POINT_BLANK_SHOT_REQU = "Ranged fighter perk";
  public static final String PERK_SHARP_SHOOTER = "Sharp shooter";
  public static final String PERK_SHARP_SHOOTER_DESC = "Increase critical chance 5% with ranged weapons.";
  public static final String PERK_SHARP_SHOOTER_REQU = "Improved ranged fighter perk.";
  public static final String PERK_CRITICAL_STRIKE = "Critical strike";
  public static final String PERK_CRITICAL_STRIKE_DESC = "Increase critical chance 5% in melee combat.";
  public static final String PERK_CRITICAL_STRIKE_REQU = "Luck is 4 or more and melee or unarmed skill is 30 or more.";
  public static final String PERK_ASSASIN_STRIKE = "Assassin strike";
  public static final String PERK_ASSASIN_STRIKE_DESC = "Increase critical multiplier by one in melee combat.";
  public static final String PERK_ASSASIN_STRIKE_REQU = "Criticial strike perk and melee or unarmed skill is 60 or more.";
  public static final String PERK_DARK_VISION = "Dark vision";
  public static final String PERK_DARK_VISION_DESC = "Negates effects of darkness in combat.";
  public static final String PERK_DARK_VISION_REQU = "Endurance is 4 or more.";
  public static final String PERK_MASTER_SORCERER = "Master Sorcerer";
  public static final String PERK_MASTER_SORCERER_DESC = "Increases damage of high level(skill req. 50 or more) sorcery spells by character levels.";
  public static final String PERK_MASTER_SORCERER_REQU = "Sorcery skill is 60 or more.";
  public static final String PERK_MASTER_HEALER = "Master Healer";
  public static final String PERK_MASTER_HEALER_DESC = "Increases healing power of wizardy spells according character levels";
  public static final String PERK_MASTER_HEALER_REQU = "Wizardy skill is 60 or more.";
  public static final String PERK_MASTER_QI_MAGICIAN = "Master Qi Magician";
  public static final String PERK_MASTER_QI_MAGICIAN_DESC = "Increases duration of Qi boosting spells by character levels.";
  public static final String PERK_MASTER_QI_MAGICIAN_REQU = "Qi Magic skill is 60 or more.";
  public static final String PERK_SMITE_UNDEAD = "Smite Undead";
  public static final String PERK_SMITE_UNDEAD_DESC = "Powerful spell against undead creatures which is only available via perk.";
  public static final String PERK_SMITE_UNDEAD_REQU = "Wizardy skill is 60 or more.";
  public static final String PERK_FAST_THROWER = "Fast thrower";
  public static final String PERK_FAST_THROWER_DESC = "Equip automatically similar weapon that was thrown.";
  public static final String PERK_FAST_THROWER_REQU = "Ranged figter and ranged attack skill is 30 or more.";
  public static final String PERK_POWER_THROWER = "Power thrower";
  public static final String PERK_POWER_THROWER_DESC = "Throwing a weapon causes one extra piercing damage.";
  public static final String PERK_POWER_THROWER_REQU = "Ranged figter and ranged attack skill is 60 or more.";
  public static final String PERK_SMASHER = "Smasher";
  public static final String PERK_SMASHER_DESC = "Blunt weapons deal one extra damage and one extra non-lethal damage.";
  public static final String PERK_SMASHER_REQU = "Strength is 5 or more and melee or unarmed skill is 30 or more.";
  public static final String PERK_IMPROVED_SMASHER = "Improved smasher";
  public static final String PERK_IMPROVED_SMASHER_DESC = "Blunt weapons deal one extra damage and one extra non-lethal damage.";
  public static final String PERK_IMPROVED_SMASHER_REQU = "Smasher perk, strength is 6 or more and melee or unarmed skill is 60 or more.";
  public static final String PERK_MASTER_SMASHER = "Master smasher";
  public static final String PERK_MASTER_SMASHER_DESC = "Blunt weapons deal one extra damage and one extra non-lethal damage.";
  public static final String PERK_MASTER_SMASHER_REQU = "Improved smasher perk, strength is 7 or more and melee or unarmed skill is 90 or more.";


  
  public static final String[] ALL_PERK_NAMES = {PERK_TRAINED_STRENGTH,
    PERK_TRAINED_STRENGTH2, PERK_TRAINED_AGILITY, PERK_TRAINED_AGILITY2,
    PERK_TRAINED_ENDURANCE, PERK_TRAINED_ENDURANCE2, PERK_TRAINED_INTELLIGENCE,
    PERK_TRAINED_INTELLIGENCE2, PERK_TRAINED_WISDOM, PERK_TRAINED_WISDOM2,
    PERK_TRAINED_CHARISMA, PERK_TRAINED_CHARISMA2, PERK_TRAINED_LUCK,
    PERK_TRAINED_LUCK2,PERK_SHIELD_PROFIENCY,PERK_SHIELD_FIGHTER,
    PERK_IMPROVED_SHIELD_FIGHTER, PERK_MASTER_SHIELD_FIGHTER, PERK_LETHAL_FISTS,
    PERK_QI_FISTS, PERK_QI_STRIKE, PERK_QI_BODY, PERK_KUNG_FU_MASTER,
    PERK_NEGOTIATOR, PERK_DUAL_WIELDER, PERK_IMPROVED_DUAL_WIELDER, 
    PERK_MASTER_DUAL_WIELDER, PERK_MAGICALLY_ACTIVE, PERK_MAGICALLY_AWAKENED,
    PERK_MAGICALLY_ENLIGHTENED, PERK_ATHLETIC, PERK_DIEHARD, 
    PERK_UNPREDICTABLE_MOVES,PERK_RANGED_FIGHTER,PERK_IMPROVED_RANGED_FIGHTER,
    PERK_MASTER_RANGED_FIGHTER, PERK_LOCK_SMITH, PERK_MASTER_LOCK_SMITH,
    PERK_MERCENARY, PERK_MANATARMS, PERK_MASTER_OF_WEAPONS, PERK_STRONG_BACK,
    PERK_STRONG_BONES,PERK_RAPID_SHOT, PERK_IMPROVED_RAPID_SHOT, 
    PERK_MASTER_RAPID_SHOT,PERK_TWO_HANDED_WEAPON_FIGHTER,
    PERK_IMPROVED_TWO_HANDED_WEAPON_FIGHTER,
    PERK_MASTER_TWO_HANDED_WEAPON_FIGHTER,PERK_STRONG_WILLPOWER,
    PERK_INNER_WILLPOWER,PERK_BONEHEAD, PERK_FENCER,PERK_MASTER_FENCER,
    PERK_COMBAT_CASTING,PERK_MASTER_COMBAT_CASTING,PERK_POINT_BLANK_SHOT,
    PERK_SHARP_SHOOTER,PERK_CRITICAL_STRIKE,PERK_ASSASIN_STRIKE,
    PERK_DARK_VISION,PERK_MASTER_SORCERER,PERK_MASTER_HEALER,
    PERK_MASTER_QI_MAGICIAN,PERK_FAST_THROWER,PERK_POWER_THROWER,
    PERK_SMITE_UNDEAD,PERK_SMASHER,PERK_IMPROVED_SMASHER,
    PERK_MASTER_SMASHER};

  public static final String[] ALL_PERK_NAMES_DESC = {PERK_TRAINED_STRENGTH_DESC,
    PERK_TRAINED_STRENGTH2_DESC, PERK_TRAINED_AGILITY_DESC,
    PERK_TRAINED_AGILITY2_DESC, PERK_TRAINED_ENDURANCE_DESC,
    PERK_TRAINED_ENDURANCE2_DESC, PERK_TRAINED_INTELLIGENCE_DESC,
    PERK_TRAINED_INTELLIGENCE2_DESC, PERK_TRAINED_WISDOM_DESC,
    PERK_TRAINED_WISDOM2_DESC, PERK_TRAINED_CHARISMA_DESC, 
    PERK_TRAINED_CHARISMA2_DESC, PERK_TRAINED_LUCK_DESC, PERK_TRAINED_LUCK2_DESC,
    PERK_SHIELD_PROFIENCY_DESC,PERK_SHIELD_FIGHTER_DESC,
    PERK_IMPROVED_SHIELD_FIGHTER_DESC, PERK_MASTER_SHIELD_FIGHTER_DESC,
    PERK_LETHAL_FISTS_DESC, PERK_QI_FISTS_DESC, PERK_QI_STRIKE_DESC, 
    PERK_QI_BODY_DESC, PERK_KUNG_FU_MASTER_DESC, PERK_NEGOTIATOR_DESC,
    PERK_DUAL_WIELDER_DESC, PERK_IMPROVED_DUAL_WIELDER_DESC, 
    PERK_MASTER_DUAL_WIELDER_DESC, PERK_MAGICALLY_ACTIVE_DESC,
    PERK_MAGICALLY_AWAKENED_DESC, PERK_MAGICALLY_ENLIGHTENED_DESC,
    PERK_ATHLETIC_DESC, PERK_DIEHARD_DESC, PERK_UNPREDICTABLE_MOVES_DESC,
    PERK_RANGED_FIGHTER_DESC, PERK_IMPROVED_RANGED_FIGHTER_DESC,
    PERK_MASTER_RANGED_FIGHTER_DESC, PERK_LOCK_SMITH_DESC, 
    PERK_MASTER_LOCK_SMITH_DESC, PERK_MERCENARY_DESC, PERK_MANATARMS_DESC,
    PERK_MASTER_OF_WEAPONS_DESC, PERK_STRONG_BACK_DESC, PERK_STRONG_BONES_DESC,
    PERK_RAPID_SHOT_DESC, PERK_IMPROVED_RAPID_SHOT_DESC,
    PERK_MASTER_RAPID_SHOT_DESC,PERK_TWO_HANDED_WEAPON_FIGHTER_DESC,
    PERK_IMPROVED_TWO_HANDED_WEAPON_FIGHTER_DESC,
    PERK_MASTER_TWO_HANDED_WEAPON_FIGHTER_DESC,PERK_STRONG_WILLPOWER_DESC,
    PERK_INNER_WILLPOWER_DESC,PERK_BONEHEAD_DESC,PERK_FENCER_DESC,
    PERK_MASTER_FENCER_DESC,PERK_COMBAT_CASTING_DESC,
    PERK_MASTER_COMBAT_CASTING_DESC,PERK_POINT_BLANK_SHOT_DESC,
    PERK_SHARP_SHOOTER_DESC,PERK_CRITICAL_STRIKE_DESC,PERK_ASSASIN_STRIKE_DESC,
    PERK_DARK_VISION_DESC,PERK_MASTER_SORCERER_DESC,PERK_MASTER_HEALER_DESC,
    PERK_MASTER_QI_MAGICIAN_DESC,PERK_FAST_THROWER_DESC,PERK_POWER_THROWER_DESC,
    PERK_SMITE_UNDEAD_DESC,PERK_SMASHER_DESC,PERK_IMPROVED_SMASHER_DESC,
    PERK_MASTER_SMASHER_DESC};

  public static final String[] ALL_PERK_NAMES_REQU = {PERK_TRAINED_STRENGTH_REQU,
    PERK_TRAINED_STRENGTH2_REQU, PERK_TRAINED_AGILITY_REQU,
    PERK_TRAINED_AGILITY2_REQU, PERK_TRAINED_ENDURANCE_REQU,
    PERK_TRAINED_ENDURANCE2_REQU, PERK_TRAINED_INTELLIGENCE_REQU,
    PERK_TRAINED_INTELLIGENCE2_REQU, PERK_TRAINED_WISDOM_REQU,
    PERK_TRAINED_WISDOM2_REQU, PERK_TRAINED_CHARISMA_REQU, 
    PERK_TRAINED_CHARISMA2_REQU, PERK_TRAINED_LUCK_REQU, PERK_TRAINED_LUCK2_REQU,
    PERK_SHIELD_PROFIENCY_REQU,PERK_SHIELD_FIGHTER_REQU,
    PERK_IMPROVED_SHIELD_FIGHTER_REQU, PERK_MASTER_SHIELD_FIGHTER_REQU,
    PERK_LETHAL_FISTS_REQU, PERK_QI_FISTS_REQU, PERK_QI_STRIKE_REQU, 
    PERK_QI_BODY_REQU, PERK_KUNG_FU_MASTER_REQU, PERK_NEGOTIATOR_REQU,
    PERK_DUAL_WIELDER_REQU, PERK_IMPROVED_DUAL_WIELDER_REQU, 
    PERK_MASTER_DUAL_WIELDER_REQU, PERK_MAGICALLY_ACTIVE_REQU,
    PERK_MAGICALLY_AWAKENED_REQU, PERK_MAGICALLY_ENLIGHTENED_REQU,
    PERK_ATHLETIC_REQU, PERK_DIEHARD_REQU, PERK_UNPREDICTABLE_MOVES_REQU,
    PERK_RANGED_FIGHTER_REQU, PERK_IMPROVED_RANGED_FIGHTER_REQU,
    PERK_MASTER_RANGED_FIGHTER_REQU, PERK_LOCK_SMITH_REQU, 
    PERK_MASTER_LOCK_SMITH_REQU, PERK_MERCENARY_REQU, PERK_MANATARMS_REQU,
    PERK_MASTER_OF_WEAPONS_REQU, PERK_STRONG_BACK_REQU, PERK_STRONG_BONES_REQU,
    PERK_RAPID_SHOT_REQU, PERK_IMPROVED_RAPID_SHOT_REQU,
    PERK_MASTER_RAPID_SHOT_REQU,PERK_TWO_HANDED_WEAPON_FIGHTER_REQU,
    PERK_IMPROVED_TWO_HANDED_WEAPON_FIGHTER_REQU,
    PERK_MASTER_TWO_HANDED_WEAPON_FIGHTER_REQU,PERK_STRONG_WILLPOWER_REQU,
    PERK_INNER_WILLPOWER_REQU,PERK_BONEHEAD_REQU,PERK_FENCER_REQU,
    PERK_MASTER_FENCER_REQU,PERK_COMBAT_CASTING_REQU,
    PERK_MASTER_COMBAT_CASTING_REQU,PERK_POINT_BLANK_SHOT_REQU,
    PERK_SHARP_SHOOTER_REQU,PERK_CRITICAL_STRIKE_REQU,PERK_ASSASIN_STRIKE_REQU,
    PERK_DARK_VISION_REQU,PERK_MASTER_SORCERER_REQU,PERK_MASTER_HEALER_REQU,
    PERK_MASTER_QI_MAGICIAN_REQU,PERK_FAST_THROWER_REQU,PERK_POWER_THROWER_REQU,
    PERK_SMITE_UNDEAD_REQU,PERK_SMASHER_REQU,PERK_IMPROVED_SMASHER_REQU,
    PERK_MASTER_SMASHER_REQU};


  private boolean[] active_perks;
  
  /**
   * Get Perk index by perk name
   * @param name, perk name
   * @return index if found, if not found then -1
   */
  public static int getPerkIndex(String name) {
    for (int i=0;i<ALL_PERK_NAMES.length;i++) {
     if (name.equalsIgnoreCase(ALL_PERK_NAMES[i])) {
       return i;
     }
    }
    return -1;
  }
  
  /**
   * Constructor for Perks, all are off
   */
  public Perks() {
    active_perks = new boolean[ALL_PERK_NAMES.length];
    for (int i=0;i<ALL_PERK_NAMES.length;i++) {
      active_perks[i] = false;     
    }
  }
  
  public int getNumberOfActiveAttributePerks() {
    int result = 0;
    // Last attribute Perk is PERK_TRAINED_LUCK2 number 13
    for (int i=0;i<14;i++) {
      if (active_perks[i]) {
        result = result +1;
      }
    }
    return result;
  }
  
  /**
   * Get number of active perks
   * @return number of active perks
   */
  public int getNumberOfActive() {
    int result = 0;
    for (int i=0;i<ALL_PERK_NAMES.length;i++) {
      if (active_perks[i]) {
        result = result +1;
      }
    }
    return result;
  }

  /**
   * Check if perk is active
   * @param name, name of the perk
   * @return boolean
   */
  public boolean isPerkActive(String name) {
    return active_perks[getPerkIndex(name)];
  }
  
  /**
   * Get Array of String of Active Perks
   * @return, Array of Strings
   */
  public String[] getActivePerks() {
    String[] result = new String[getNumberOfActive()];
    int j = 0;
    for (int i=0;i<ALL_PERK_NAMES.length;i++) {
      if (active_perks[i]) {
        result[j] = ALL_PERK_NAMES[i];
        j++;
      }
    }
    return result;
  }
  
  /**
   * Set Perk 
   * @param index, perk index number, see getPerkIndex()
   * @param enabled, boolean
   */
  public void setPerk(int index, boolean enabled) {
    if ((index < ALL_PERK_NAMES.length) && (index > -1)) {
      active_perks[index] = enabled;
    }
  }
  
  /**
   * Is perk active or not
   * @param index, perk index number, see getPerkIndex()
   * @return
   */
  public boolean getPerk(int index) {
    if ((index < ALL_PERK_NAMES.length) && (index > -1)) {
      return active_perks[index];
    }
    return false;
  }
  
  /**
   * Writes perks into DataOutputSteam
   * @param os DataOutputStream
   * @throws IOException if write fails
   */
  public void writePerks(DataOutputStream os) throws IOException {
    // Writes number of active perks as one byte
    os.writeByte(getNumberOfActive());
    for (int i=0;i<active_perks.length;i++) {
      if (active_perks[i]) {
        // Each active perk is written into DataOutputStream
        os.writeByte(i);
      }
    }
  }
  
  /**
   * Reads peks from DataInputStream
   * @param is DataInputStream
   * @throws IOException if read fails
   */
  public void readPerks(DataInputStream is) throws IOException {
    // How many active perks
    int numberOfPerks = is.readByte();
    // Set all perks to disabled
    for (int i=0;i<active_perks.length;i++) {
      setPerk(i, false);
    }
    // Read from DataInputStream and set Perks
    for (int i=0;i<numberOfPerks;i++) {
      int index = is.readByte();
      setPerk(index,true);
    }
  }
  

  /** Return true if perk is available
  * @param i perk number
  * @param attributes, character attributes in array
  * @param skills, character skills in array
  * @param level, character level
  * @return boolean
  */
  public boolean isAvailablePerk(int i, int[] attributes, int[] skills, int level) {

    if (i==getPerkIndex(PERK_TRAINED_STRENGTH)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_STRENGTH] < 10) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_TRAINED_STRENGTH2)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_STRENGTH] < 10) && 
          (active_perks[getPerkIndex(PERK_TRAINED_STRENGTH)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_TRAINED_AGILITY)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_AGILITY] < 10) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_TRAINED_AGILITY2)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_AGILITY] < 10) && 
          (active_perks[getPerkIndex(PERK_TRAINED_AGILITY)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_TRAINED_ENDURANCE)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_ENDURANCE] < 10) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_TRAINED_ENDURANCE2)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_ENDURANCE] < 10) && 
          (active_perks[getPerkIndex(PERK_TRAINED_ENDURANCE)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_TRAINED_INTELLIGENCE)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_INTELLIGENCE] < 10) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_TRAINED_INTELLIGENCE2)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_INTELLIGENCE] < 10) && 
          (active_perks[getPerkIndex(PERK_TRAINED_INTELLIGENCE)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_TRAINED_WISDOM)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_WISDOM] < 10) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_TRAINED_WISDOM2)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_WISDOM] < 10) && 
          (active_perks[getPerkIndex(PERK_TRAINED_WISDOM)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_TRAINED_CHARISMA)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_CHARISMA] < 10) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_TRAINED_CHARISMA2)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_CHARISMA] < 10) && 
          (active_perks[getPerkIndex(PERK_TRAINED_CHARISMA)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_TRAINED_LUCK)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_LUCK] < 10) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_TRAINED_LUCK2)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_LUCK] < 10) && 
          (active_perks[getPerkIndex(PERK_TRAINED_LUCK)])) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_SHIELD_PROFIENCY)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_STRENGTH] > 4) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_SHIELD_FIGHTER)) { // Check the perk
      // Check the perk requirements
       if (active_perks[getPerkIndex(PERK_SHIELD_PROFIENCY)]) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_IMPROVED_SHIELD_FIGHTER)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_MELEE] >= 50) && 
          (active_perks[getPerkIndex(PERK_SHIELD_FIGHTER)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_MASTER_SHIELD_FIGHTER)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_MELEE] >= 75) && 
          (active_perks[getPerkIndex(PERK_IMPROVED_SHIELD_FIGHTER)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_LETHAL_FISTS)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_STRENGTH] > 4) {
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_QI_FISTS)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_UNARMED] >= 30) && 
           (active_perks[getPerkIndex(PERK_LETHAL_FISTS)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_QI_STRIKE)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_UNARMED] >= 60) && 
          (active_perks[getPerkIndex(PERK_QI_FISTS)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_QI_BODY)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_WISDOM] > 5) && 
          (active_perks[getPerkIndex(PERK_QI_STRIKE)]) &&
          (level > 9)) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_KUNG_FU_MASTER)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_UNARMED] >= 90) && 
          (active_perks[getPerkIndex(PERK_QI_BODY)]) &&
          (level > 15)) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_NEGOTIATOR)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_CHARISMA] > 5)) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_DUAL_WIELDER)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_AGILITY] > 5) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_IMPROVED_DUAL_WIELDER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_DUAL_WIELDER)]) &&
          (level > 5)) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_MASTER_DUAL_WIELDER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_IMPROVED_DUAL_WIELDER)]) &&
          (level > 11)) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_MAGICALLY_ACTIVE)) { // Check the perk
      // Check the perk requirements
      if ((attributes[Character.ATTRIBUTE_WISDOM] > 6) && 
          (attributes[Character.ATTRIBUTE_INTELLIGENCE] > 6) &&
          (attributes[Character.ATTRIBUTE_CHARISMA] > 6)){
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_MAGICALLY_AWAKENED)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_MAGICALLY_ACTIVE)]) &&
          (level > 7)) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_MAGICALLY_ENLIGHTENED)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_MAGICALLY_AWAKENED)]) &&
          (level > 15)) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_ATHLETIC)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_ENDURANCE]>5) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_DIEHARD)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_ENDURANCE]>5) &&
          (level > 5)){
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_UNPREDICTABLE_MOVES)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_AGILITY]>6) &&
          (skills[Character.SKILL_UNARMED]>=40)){
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_RANGED_FIGHTER)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_AGILITY]>4) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_IMPROVED_RANGED_FIGHTER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_RANGED_FIGHTER)]) &&
          (skills[Character.SKILL_RANGED_WEAPONS]>=30)){
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_MASTER_RANGED_FIGHTER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_IMPROVED_RANGED_FIGHTER)]) &&
          (skills[Character.SKILL_RANGED_WEAPONS]>=60)){
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_LOCK_SMITH)) { // Check the perk
      // Check the perk requirements
       if (skills[Character.SKILL_LOCK_PICKING]>=30){
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_MASTER_LOCK_SMITH)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_LOCK_SMITH)]) &&
          (skills[Character.SKILL_LOCK_PICKING]>=60)){
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_MERCENARY)) { // Check the perk
      // Check the perk requirements
       if (skills[Character.SKILL_MELEE]>=25){
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_MANATARMS)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_MELEE]>=50) && 
          (active_perks[getPerkIndex(PERK_MERCENARY)])){
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_MASTER_OF_WEAPONS)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_MELEE]>=75) && 
          (active_perks[getPerkIndex(PERK_MANATARMS)])){
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_STRONG_BACK)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_STRENGTH]>5) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_STRONG_BONES)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_ENDURANCE]>7) &&(level > 7)) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_RAPID_SHOT)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_RANGED_WEAPONS]>=30) && 
          (attributes[Character.ATTRIBUTE_AGILITY]>6)) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_IMPROVED_RAPID_SHOT)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_RANGED_WEAPONS]>=50) && 
          (active_perks[getPerkIndex(PERK_RAPID_SHOT)])){
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_MASTER_RAPID_SHOT)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_RANGED_WEAPONS]>=75) && 
          (active_perks[getPerkIndex(PERK_IMPROVED_RAPID_SHOT)])){
         // Requirements filled then add it
         return true;
       }
    }
    
    if (i==getPerkIndex(PERK_TWO_HANDED_WEAPON_FIGHTER)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_STRENGTH]>6) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_IMPROVED_TWO_HANDED_WEAPON_FIGHTER)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_MELEE]>=30) && 
           (active_perks[getPerkIndex(PERK_TWO_HANDED_WEAPON_FIGHTER)])) {
         // Requirements filled then add it
         return true;
       }
    }

    if (i==getPerkIndex(PERK_MASTER_TWO_HANDED_WEAPON_FIGHTER)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_MELEE]>=60) && 
           (active_perks[getPerkIndex(PERK_IMPROVED_TWO_HANDED_WEAPON_FIGHTER)])) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_STRONG_WILLPOWER)) { // Check the perk
      // Check the perk requirements
       if (attributes[Character.ATTRIBUTE_WISDOM]>6) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_INNER_WILLPOWER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_STRONG_WILLPOWER)]) &&
          (level > 7)) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_BONEHEAD)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_WISDOM]<3) &&
         (attributes[Character.ATTRIBUTE_ENDURANCE]>5)) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_FENCER)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_AGILITY]>6))  {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_MASTER_FENCER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_FENCER)]) &&
          (level > 7)) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_COMBAT_CASTING)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_AGILITY]>4) &&
           ( (skills[Character.SKILL_QI_MAGIC] > 4)||
             (skills[Character.SKILL_SORCERY] > 4) ||
             (skills[Character.SKILL_WIZARDRY] > 4))) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_MASTER_COMBAT_CASTING)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_COMBAT_CASTING)]) &&
           (skills[Character.SKILL_MELEE]>29) &&
           ( (skills[Character.SKILL_QI_MAGIC] > 29)||
             (skills[Character.SKILL_SORCERY] > 29) ||
             (skills[Character.SKILL_WIZARDRY] > 29))) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_POINT_BLANK_SHOT)) { // Check the perk
      // Check the perk requirements
       if (active_perks[getPerkIndex(PERK_RANGED_FIGHTER)]) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_SHARP_SHOOTER)) { // Check the perk
      // Check the perk requirements
       if (active_perks[getPerkIndex(PERK_IMPROVED_RANGED_FIGHTER)]) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_CRITICAL_STRIKE)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_LUCK]>4) &&
           ( (skills[Character.SKILL_UNARMED] > 29)||
             (skills[Character.SKILL_MELEE] > 29))) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_ASSASIN_STRIKE)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_CRITICAL_STRIKE)]) &&
           ( (skills[Character.SKILL_UNARMED] > 59)||
             (skills[Character.SKILL_MELEE] > 59))) {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_DARK_VISION)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_ENDURANCE]>4))  {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_MASTER_SORCERER)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_SORCERY] > 59))  {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_MASTER_HEALER)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_WIZARDRY] > 59))  {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_MASTER_QI_MAGICIAN)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_QI_MAGIC] > 59))  {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_SMITE_UNDEAD)) { // Check the perk
      // Check the perk requirements
       if ((skills[Character.SKILL_WIZARDRY] > 59))  {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_FAST_THROWER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_RANGED_FIGHTER)]) &&
          (skills[Character.SKILL_RANGED_WEAPONS]>=30)){
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_POWER_THROWER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_RANGED_FIGHTER)]) &&
          (skills[Character.SKILL_RANGED_WEAPONS]>=60)){
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_SMASHER)) { // Check the perk
      // Check the perk requirements
       if ((attributes[Character.ATTRIBUTE_STRENGTH]>4) &&
           ( (skills[Character.SKILL_UNARMED] > 29)||
               (skills[Character.SKILL_MELEE] > 29)))  {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_IMPROVED_SMASHER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_SMASHER)]) &&
           ( (skills[Character.SKILL_UNARMED] > 59)||
               (skills[Character.SKILL_MELEE] > 59)) &&
           (attributes[Character.ATTRIBUTE_STRENGTH]>5))  {
         // Requirements filled then add it
         return true;
       }
    }
    if (i==getPerkIndex(PERK_MASTER_SMASHER)) { // Check the perk
      // Check the perk requirements
       if ((active_perks[getPerkIndex(PERK_IMPROVED_SMASHER)]) &&
           ( (skills[Character.SKILL_UNARMED] > 89)||
               (skills[Character.SKILL_MELEE] > 89)) &&
           (attributes[Character.ATTRIBUTE_STRENGTH]>6))  {
         // Requirements filled then add it
         return true;
       }
    }

    return false;

  }
  
  /**
   * Return ArrayList of string of perks which all available
   * @param attributes, character attributes in array
   * @param skills, character skills in array
   * @param level, character level
   * @return ArrayList
   */
  public ArrayList<String> getAvailablePerks(int[] attributes, int[] skills, int level) {
    ArrayList<String> result = new ArrayList<String>();
    for (int i=0;i<ALL_PERK_NAMES.length;i++) {
      if ((isAvailablePerk(i, attributes, skills, level)) &&(!isPerkActive(ALL_PERK_NAMES[i]))) {
        result.add(ALL_PERK_NAMES[i]);
      }

      } //  END OF LOOP GOINGING THROUGH ALL PERK
    return result;
  }
  
  /**
   * Get perks which are not available
   * @param attributes, character attributes in array
   * @param skills, character skills in array
   * @param level, character level
   * @return ArrayList
   */
  public ArrayList<String> getNotAvailablePerks(int[] attributes, int[] skills, int level) {
    ArrayList<String> result = new ArrayList<String>();
    for (int i=0;i<ALL_PERK_NAMES.length;i++) {
      if (!isAvailablePerk(i, attributes, skills, level)) {
        result.add(ALL_PERK_NAMES[i]);
      }

      } //  END OF LOOP GOINGING THROUGH ALL PERK
    return result;
    
  }

}

