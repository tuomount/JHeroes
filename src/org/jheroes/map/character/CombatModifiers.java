package org.jheroes.map.character;
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
 * Combat modifiers class
 * 
 */
public class CombatModifiers {

  /**
   * Damage modifiers for certain damage type
   * Weakness, double the damage
   * Normal, normal damage
   * Resistance, half damage
   * Immunity, zero damage
   */
  public enum DamageModifier {
    IMMUNITY, RESISTANCE, NORMAL, WEAKNESS
  }
  
  /**
   * Enum of attack types. These attack type races can have resistance, weakness
   * or immunity.
   *
   */
  public enum AttackType {
    NORMAL,BLUNT, FIRE, ICE, ELECTRIC, MINDAFFECTING, POISON, MAGIC, NONLETHAL;

    @Override
    public String toString() {
      switch (this) {
      case NORMAL: {return "normal";}
      case BLUNT: {return "blunt";}
      case FIRE: {return "fire";}
      case ICE: {return "ice";}
      case ELECTRIC: {return "electric";}
      case MINDAFFECTING: {return "mindaffecting";}
      case POISON: {return "poison";}
      case MAGIC: {return "magic";}
      case NONLETHAL: {return "non-lethal";}
      }
      return "normal";
    }
    
    /**
     * Get AttackType as byte. These are just byte values starting from 0
     * @return byte
     */
    public byte toByte() {
      switch (this) {
      case NORMAL: {return 0;}
      case BLUNT: {return 1;}
      case FIRE: {return 2;}
      case ICE: {return 3;}
      case ELECTRIC: {return 4;}
      case MINDAFFECTING: {return 5;}
      case POISON: {return 6;}
      case MAGIC: {return 7;}
      case NONLETHAL: {return 8;}
      }
      //Default to Normal
      return 0;
    }
    
    public static AttackType getAttackTypeByByte(byte b) {      
      switch (b) {
      case 0: return AttackType.NORMAL;
      case 1: return AttackType.BLUNT;
      case 2: return AttackType.FIRE;
      case 3: return AttackType.ICE;
      case 4: return AttackType.ELECTRIC;
      case 5: return AttackType.MINDAFFECTING;
      case 6: return AttackType.POISON;
      case 7: return AttackType.MAGIC;
      case 8: return AttackType.NONLETHAL;
      default: return AttackType.NORMAL;
      }
    }
  }

}
