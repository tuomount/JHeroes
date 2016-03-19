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
    
    
  }

}
