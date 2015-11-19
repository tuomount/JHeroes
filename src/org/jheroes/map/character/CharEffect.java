package org.jheroes.map.character;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.jheroes.utilities.StreamUtilities;


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
 * One single magical effect on character
 * 
 */
public class CharEffect {

  public static final byte TYPE_CURSE = 1; //Negate remove curse
  public static final byte TYPE_DISEASE = 2; // Cure disease
  public static final byte TYPE_POISON = 3; // Cure poison
  public static final byte TYPE_ENCHANT = 4; // Dispel Magic
  public static final byte TYPE_WEARABLE_FIRSTHAND = 5;
  public static final byte TYPE_WEARABLE_SECONDHAND = 6;
  public static final byte TYPE_WEARABLE_ARMOR = 7;
  public static final byte TYPE_WEARABLE_HEADGEAR = 8;
  public static final byte TYPE_WEARABLE_BOOTS = 9;
  public static final byte TYPE_WEARABLE_AMULET = 10;
  public static final byte TYPE_WEARABLE_RING = 11;
  
  public static final int DURATION_UNTIL_REMOVED = -1;
  public static final int MAX_DIFFICULTY = 500;
  
  public static final byte EFFECT_ON_ATTRIBUTE = 0;
  public static final byte EFFECT_ON_SKILL = 1;
  public static final byte EFFECT_ON_HEALTH = 2;
  public static final byte EFFECT_ON_STAMINA = 3;
  public static final byte EFFECT_ON_WILLPOWER = 4;
  public static final byte EFFECT_ON_HEALTH_AND_STAMINA = 5;
  /**
   * Effect on light
   * Value: -2 Darkness, -1 Dim darkness,0 No light, 1 Dim light, 2 Torch, 3 bright light
   */
  public static final byte EFFECT_ON_LIGHT = 6;
  public static final byte EFFECT_PACIFISM = 7;
  public static final byte EFFECT_ON_CARRYING_CAPACITY = 8;
  public static final byte EFFECT_ON_ARMOR = 9;
  public static final byte EFFECT_DRAIN_HEALTH = 10;
  public static final byte EFFECT_DRAIN_STAMINA = 11;
  public static final byte EFFECT_DRAIN_VIGOR = 12;
  public static final byte EFFECT_SLAYER = 13;
  public static final byte EFFECT_ON_MIGHTY = 14;
  
  private byte type;
  private int duration;
  private byte effect;
  private int attrOrSkill;
  private int value;
  private int difficulty;
  private String name;
  
  
  /**
   * Creates a new effect
   * @param name String
   * @param type byte
   * @param duration number of rounds
   * @param effect effect type
   * @param attrOrSkill Attribute or Skill where effect affects
   * @param value effect value
   * @param difficulty how difficulty effect is to remove
   */
  public CharEffect(String name,byte type, int duration, byte effect, 
                    int attrOrSkill, int value, int difficulty) {
     this.name = name;
     this.type = type;
     if (duration >= -1) {
       this.duration = duration;
     } else {
       this.duration = -1;
     }
     this.effect = effect;
     this.attrOrSkill = attrOrSkill;
     this.value = value;
     this.difficulty = difficulty;
     if (this.difficulty >= MAX_DIFFICULTY) {
       this.difficulty = MAX_DIFFICULTY-1;
     }
  }
  
  /**
   * Copy construct
   * @param input CharEffect
   */
  public CharEffect(CharEffect input) {
    this.name = input.getName();
    this.type = input.getType();
    this.duration = input.duration;
    this.effect = input.getEffect();
    this.attrOrSkill = input.getAttributeOrSkill();
    this.value = input.getValue();
    this.difficulty = input.difficulty;
  }
  
  /**
   * Empty charEffect
   */
  public CharEffect() {
    
  }
  
  public byte getType() {
    return type; 
  }
  
  public String getTypeAsString() {
    String tmp="";
    switch (type) {
    case TYPE_CURSE: tmp = "Curse"; break;
    case TYPE_ENCHANT: tmp = "Enchant"; break;
    case TYPE_POISON: tmp = "Poison"; break;
    case TYPE_DISEASE: tmp = "Disease"; break;
    case TYPE_WEARABLE_AMULET:
    case TYPE_WEARABLE_ARMOR:
    case TYPE_WEARABLE_BOOTS:
    case TYPE_WEARABLE_FIRSTHAND:
    case TYPE_WEARABLE_HEADGEAR:
    case TYPE_WEARABLE_RING:
    case TYPE_WEARABLE_SECONDHAND: tmp = "Magical item"; break;
    }
    return tmp;
  }
  
  /**
   * Number of rounds passes and check if effect should stop
   * @param rounds
   * @return boolean, true if effect should stop
   */
  public boolean passRounds(int rounds) {
    if (rounds > 0) {
      if (duration == DURATION_UNTIL_REMOVED) {
        return false;
      } else {
        if (duration > rounds) {
          duration = duration -rounds;
          return false;
        } else {
          duration = 0;
          return true;
        }
      }
    } else {
      return false;
    }
  }
  
  public byte getEffect() {
    return effect;
  }
  
  public int getAttributeOrSkill() {
    return attrOrSkill;
  }
  
  public int getValue() {
    return value;   
  }
  
  public void setValue(int val) {
    value = val;
  }
  
  public int getDifficulty() {
    return difficulty;
  }
  
  public void setDifficulty(int diff) {
    difficulty = diff;
  }
  
  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getName() {
    return name;
  }
  
  public String getEffectAsString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    sb.append(", "+getTypeAsString()+"\n");
    if (value<0) {
      sb.append("Dimish ");
    }
    if (value>0) {
      sb.append("Bonus on ");
    }
    switch (effect) {
    case EFFECT_ON_ATTRIBUTE: sb.append(Character.getAttributeName(attrOrSkill)+" "+String.valueOf(value)); break;
    case EFFECT_ON_SKILL: sb.append(Character.getSkillName(attrOrSkill)+" "+String.valueOf(value)); break;
    case EFFECT_ON_HEALTH: sb.append("health "+String.valueOf(value)); break;
    case EFFECT_ON_STAMINA: sb.append("stamina "+String.valueOf(value)); break;
    case EFFECT_ON_WILLPOWER: sb.append("willpower "+String.valueOf(value)); break;
    case EFFECT_ON_HEALTH_AND_STAMINA: sb.append("health and stamina "+String.valueOf(value)); break;
    case EFFECT_ON_LIGHT: sb.append("light"); break;
    case EFFECT_PACIFISM: sb.append("Pacifism"); break;
    case EFFECT_ON_CARRYING_CAPACITY: sb.append("carrying capacity "+String.valueOf(value)); break;
    case EFFECT_ON_ARMOR: sb.append("armor "+String.valueOf(value)); break;
    case EFFECT_DRAIN_HEALTH: sb.append("drain health"); break;
    case EFFECT_DRAIN_STAMINA: sb.append("drain stamina"); break;
    case EFFECT_DRAIN_VIGOR: sb.append("drain vigor"); break;
    case EFFECT_SLAYER: sb.append("slaying"); break;
    case EFFECT_ON_MIGHTY: sb.append("mighty "+String.valueOf(value)); break;
    }
    sb.append(".");
    if ((getType()==TYPE_CURSE) ||
       (getType()==TYPE_DISEASE) ||
       (getType()==TYPE_ENCHANT) ||
       (getType()==TYPE_POISON)) {
      sb.append(" Duration:"+duration);
    }
    return sb.toString();
  }
  
  /**
   * Write effect into DataOutputStream
   * @param os DataOutputStream
   * @throws IOException if write fails
   */
  public void writeEffect(DataOutputStream os) throws IOException {
    os.writeByte(type);
    os.writeInt(duration);
    os.writeByte(effect);
    os.writeByte(attrOrSkill);
    os.writeInt(value);
    os.writeInt(difficulty);
    StreamUtilities.writeString(os, name);
  }
  
  /**
   * Read effect from DataInputStream
   * @param is DataInputStream
   * @throws IOException
   */
  public void readEffect(DataInputStream is) throws IOException {
    type = is.readByte();
    duration = is.readInt();
    effect = is.readByte();
    attrOrSkill = is.readByte();
    value = is.readInt();
    difficulty = is.readInt();
    name = StreamUtilities.readString(is);
  }
}


