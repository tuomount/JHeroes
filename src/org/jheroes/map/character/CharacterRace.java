package org.jheroes.map.character;

import org.jheroes.map.character.CombatModifiers.DamageModifier;

/**
 * 
 * JHeroes CRPG Engine and Game
 * Copyright (C) 2016  Tuomo Untinen
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
 * Information of character race
 * 
 */

public enum CharacterRace {
  
  DEFAULT(0,"Default","",false,false),
  HUMAN(1,"Human","",true,false),
  ZOMBIE(2,"Zombie","",false,true),
  RAT(3,"Rat","",false,false);
  
  CharacterRace(int index,String name, String description, boolean isPlayerRace,
      boolean isUndead) {
    this.playableRace =isPlayerRace;
    this.undead = isUndead;
  }
  
  
  
  /**
   * Is Race selectable in character creation as a player race
   * @return true if it is selectable
   */
  public boolean isPlayerRace() {
    return playableRace;
  }


  /**
   * Is race undead and has undead weakness
   * @return true if race is undead
   */
  public boolean isUndead() {
    return undead;
  }


  /**
   * Is race selectable for player character in character creation
   */
  private boolean playableRace;
  
  /**
   * Is race undead and thus weakness for certain spells
   */
  private boolean undead;
  
  /**
   * Race index for saving the character
   */
  private int index;
  
  /**
   * Race name for displaying it
   */
  private String name;
  
  /**
   * Race description
   */
  private String description;

  /**
   * Damage modifier for normal damage.
   * @return DamageModifier
   */
  public DamageModifier damageModifierForNormal() {
    switch (this) {
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Damage modifier for blunt damage.
   * @return DamageModifier
   */
  public DamageModifier damageModifierForBlunt() {
    switch (this) {
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Damage modifier for non-lethal damage.
   * @return DamageModifier
   */
  public DamageModifier damageModifierForNonLethal() {
    switch (this) {
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Damage modifier for electric damage.
   * @return DamageModifier
   */
  public DamageModifier damageModifierForElectricity() {
    switch (this) {
    case ZOMBIE: {return DamageModifier.WEAKNESS;}
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Damage modifier for ice damage.
   * @return DamageModifier
   */
  public DamageModifier damageModifierForIce() {
    switch (this) {
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Damage modifier for Fire damage. 
   * @return DamageModifier
   */
  public DamageModifier damageModifierForFire() {
    switch (this) {
    case RAT: {return DamageModifier.WEAKNESS;}
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Damage modifier for poison damage.
   * @return DamageModifier
   */
  public DamageModifier damageModifierForPoison() {
    switch (this) {
    case ZOMBIE: {return DamageModifier.IMMUNITY;}
    case RAT: {return DamageModifier.RESISTANCE;}
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Damage modifier for mindaffecting damage.
   * @return DamageModifier
   */
  public DamageModifier damageModifierForMindAffecting() {
    switch (this) {
    case ZOMBIE: {return DamageModifier.IMMUNITY;}
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Damage modifier for magic damage.
   * @return DamageModifier
   */
  public DamageModifier damageModifierForMagic() {
    switch (this) {
    default:
      return DamageModifier.NORMAL;
    }
  }

}
