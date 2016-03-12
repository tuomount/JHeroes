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
  HUMAN(1,"Human","Humans do not have any special weakneses or strength.",true,false),
  ELF(2,"Elf","Elves have better dodging skill but have weakness against ice attacks.",true,false),
  ZOMBIE(3,"Zombie","Zombies are undead creatures which are immune to poison and"
      + "mind affecting but have weakness against electricity.",false,true),
  RAT(4,"Rat","Rats are immune to poison but have weakness against fire.",false,false),
  LIVESTOCK(5,"Livestock","Livestock is animal that can be herded. Livestock has weakness"
      + "against non lethat and fire attacks.",false,false),
  WATERELEMENTAL(6,"Waterelemental","Waterelementals are magical creatures "
      + "consisting of water. They are immune to fire, poison attacks but have weakness"
      + "against electric and ice attacks.",false,false),
  INSECT(7,"Insect","Insects are animals with three bodies and six legs. They "
      + "weakness against blunt and fire attacks. Insects are immune to mind "
      + "affecting attacks.",false,false);
  
  CharacterRace(int index,String name, String description, boolean isPlayerRace,
      boolean isUndead) {
    this.playableRace =isPlayerRace;
    this.undead = isUndead;
    this.index = index;
    this.name = name;
    this.description = description;
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
   * Get race index for saving characters.
   * @return int
   */
  public int getIndex() {
    return index;
  }
  
  /**
   * Get name as string
   * @return String
   */
  public String getName() {
    return name;
  }
  
  /**
   * Get race description as a string.
   * @return String without any view modifications
   */
  public String getDescription() {
    return description;
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
    case INSECT: {return DamageModifier.WEAKNESS;}
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Damage modifier for non-lethal damage.
   * @return DamageModifier
   */
  public DamageModifier damageModifierForNonLethal() {
    if (this.isUndead()) {
      // Undeads are immune to non lethal
      return DamageModifier.IMMUNITY;
    }
    switch (this) {
    case LIVESTOCK: {return DamageModifier.WEAKNESS;}
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
    case WATERELEMENTAL: {return DamageModifier.WEAKNESS;}
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
    case ELF: {return DamageModifier.WEAKNESS;}
    case WATERELEMENTAL: {return DamageModifier.WEAKNESS;}
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
    case LIVESTOCK: {return DamageModifier.WEAKNESS;}
    case WATERELEMENTAL: {return DamageModifier.IMMUNITY;}
    case INSECT: {return DamageModifier.WEAKNESS;}
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
    case WATERELEMENTAL: {return DamageModifier.IMMUNITY;}
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
    case INSECT: {return DamageModifier.IMMUNITY;}
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
