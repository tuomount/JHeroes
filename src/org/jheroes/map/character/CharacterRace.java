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
      + "against non lethal and fire attacks.",false,false),
  WATERELEMENTAL(6,"Waterelemental","Waterelementals are magical creatures "
      + "consisting of water. They are immune to fire, poison attacks but have weakness"
      + "against electric and ice attacks.",false,false),
  INSECT(7,"Insect","Insects are animals with three bodies and six legs. They have "
      + "weakness against blunt, fire attacks and ice attacks. Insects are immune to mind "
      + "affecting attacks.",false,false),
  SPIDER(8,"Spider","Spiders are animals with two bodies and eight legs. They have"
      + "weakness against blunt, fire attacks and ice attacks. Insects are immune to mind "
      + "affecting attacks and poison.",false,false),
  CAT(9,"Cat","Cats are immune to mind-affecting but have weakness against ice.",false,false),
  SNAKE(10,"Snake","Snakes are carnivours serpents. They have weakness against ice attacks and"
      + "resistance against fire attacks. Snakes are immune to poison.",false,false),
  HOBGOBLIN(11,"Hobgoblin","Hobgoblins are humanoid like creatures. Hobgoblins have weakness "
      + "againts mind-affecting attacks. They do have good resistance against ice attacks.",false,false),
  TROLL(12,"Troll","Trolls are big and strong humanoid like creatures. Trolls have weakness "
      + "againts fire and magic attacks. They have resistance against non-lethal and ice attacks.",false,false),
  SKELETON(13,"Skeleton","Skeletons are undead creatures which are immune to poison and "
      + "mind affecting but have weakness against blunt-weapons.",false,true),
  IMP(14,"Imp","Imp are flying evil creatures which are immune to fire and "
      + "magic attacks but have weakness against electricty and mind-affecting attacks.",false,false),
  BRAINHORROR(15,"Brain horror","Brain horrors are walking brain creatures which are immune to "
      + "mind-affecting and magic attacks but have weakness against poison and normal attacks.",false,false);
  
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
    case BRAINHORROR: {return DamageModifier.WEAKNESS;}
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
    case SPIDER: {return DamageModifier.WEAKNESS;}
    case SKELETON: {return DamageModifier.WEAKNESS;}
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
    case TROLL: {return DamageModifier.RESISTANCE;}
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
    case IMP: {return DamageModifier.WEAKNESS;}
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
    case INSECT: {return DamageModifier.WEAKNESS;}
    case SPIDER: {return DamageModifier.WEAKNESS;}
    case CAT: {return DamageModifier.WEAKNESS;}
    case SNAKE: {return DamageModifier.WEAKNESS;}
    case HOBGOBLIN: {return DamageModifier.RESISTANCE;}
    case TROLL: {return DamageModifier.RESISTANCE;}
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
    case SPIDER: {return DamageModifier.WEAKNESS;}
    case SNAKE: {return DamageModifier.RESISTANCE;}
    case TROLL: {return DamageModifier.WEAKNESS;}
    case IMP: {return DamageModifier.IMMUNITY;}
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
    case SPIDER: {return DamageModifier.IMMUNITY;}
    case SNAKE: {return DamageModifier.IMMUNITY;}
    case SKELETON: {return DamageModifier.IMMUNITY;}
    case BRAINHORROR: {return DamageModifier.WEAKNESS;}
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
    case SPIDER: {return DamageModifier.IMMUNITY;}
    case CAT: {return DamageModifier.IMMUNITY;}
    case HOBGOBLIN: {return DamageModifier.WEAKNESS;}
    case SKELETON: {return DamageModifier.IMMUNITY;}
    case IMP: {return DamageModifier.WEAKNESS;}
    case BRAINHORROR: {return DamageModifier.IMMUNITY;}
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
    case TROLL: {return DamageModifier.WEAKNESS;}
    case IMP: {return DamageModifier.IMMUNITY;}
    case BRAINHORROR: {return DamageModifier.IMMUNITY;}
    default:
      return DamageModifier.NORMAL;
    }
  }

  /**
   * Returns experience modifier. Value is between 50-300. Experience should
   * be first multiplied with this and then divide with 100. So return value
   * is actually percentage modifier for monster experience
   * @return int 50-300
   */
  public int getExpModifier() {
    int result = 100;
    // Blunt weapons about half weapons are blunt thus bigger effect
    DamageModifier mod = this.damageModifierForBlunt();
    switch (mod) {
    case WEAKNESS: {result = result -12;break;}
    case RESISTANCE: {result = result +12;break;}
    case IMMUNITY: {result = result +24;break;}
    default:
      break;
    }
    // Other half of weapons are normal, and quite many spells too
    mod = this.damageModifierForNormal();
    switch (mod) {
    case WEAKNESS: {result = result -15;break;}
    case RESISTANCE: {result = result +15;break;}
    case IMMUNITY: {result = result +30;break;}
    default:
      break;
    }
    // Non lethal is basicly hand and stamina damage from actions.
    mod = this.damageModifierForNonLethal();
    switch (mod) {
    case WEAKNESS: {result = result -8;break;}
    case RESISTANCE: {result = result +8;break;}
    case IMMUNITY: {result = result +16;break;}
    default:
      break;
    }
    mod = this.damageModifierForFire();
    switch (mod) {
    case WEAKNESS: {result = result -10;break;}
    case RESISTANCE: {result = result +10;break;}
    case IMMUNITY: {result = result +20;break;}
    default:
      break;
    }
    mod = this.damageModifierForIce();
    switch (mod) {
    case WEAKNESS: {result = result -10;break;}
    case RESISTANCE: {result = result +10;break;}
    case IMMUNITY: {result = result +20;break;}
    default:
      break;
    }
    mod = this.damageModifierForElectricity();
    switch (mod) {
    case WEAKNESS: {result = result -10;break;}
    case RESISTANCE: {result = result +10;break;}
    case IMMUNITY: {result = result +20;break;}
    default:
      break;
    }
    mod = this.damageModifierForMagic();
    switch (mod) {
    case WEAKNESS: {result = result -12;break;}
    case RESISTANCE: {result = result +12;break;}
    case IMMUNITY: {result = result +24;break;}
    default:
      break;
    }
    mod = this.damageModifierForMindAffecting();
    // Mind effecting has different change to hit than others thus making
    // it a bit more "valuable"
    switch (mod) {
    case WEAKNESS: {result = result -12;break;}
    case RESISTANCE: {result = result +12;break;}
    case IMMUNITY: {result = result +24;break;}
    default:
      break;
    }
    // There are only few ways to give poisoning to monster thus value it
    // lower.
    mod = this.damageModifierForPoison();
    switch (mod) {
    case WEAKNESS: {result = result -8;break;}
    case RESISTANCE: {result = result +8;break;}
    case IMMUNITY: {result = result +16;break;}
    default:
      break;
    }
    if (result < 50) {
      result = 50;
    }
    if (result > 300) {
      result = 300;
    }
    return result;
  }

}
