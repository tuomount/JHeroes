package org.jheroes.map.character;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
 * List of active magical effects of character
 * 
 */
public class CharEffects {
  
  private ArrayList<CharEffect> activeEffects;
  
  public CharEffects() {
    activeEffects = new ArrayList<CharEffect>();
  }
  
  public void passRounds(int rounds) {
    Iterator<CharEffect> it = activeEffects.iterator();
    while (it.hasNext()) {
      CharEffect eff = it.next();
      if (eff.passRounds(rounds)) {
        it.remove();
      }
    }
  }
  
  public void addNewEeffect(CharEffect eff) {
    int foundIndex = -1;
    for (int i=0;i<activeEffects.size();i++) {
      CharEffect effect = activeEffects.get(i);
      if (eff.getName().equalsIgnoreCase(effect.getName())) {
        foundIndex = i;
        break;
      }
    }
    if (foundIndex == -1) {
      activeEffects.add(eff);
    } else {
      activeEffects.remove(foundIndex);
      activeEffects.add(eff);
    }
  }
  
  /**
   * Checks if character has effect by certain name
   * Useful to check certain spell for example
   * @param name String
   * @return boolean
   */
  public boolean isEffectByName(String name) {
    for (int i=0;i<activeEffects.size();i++) {
      CharEffect effect = activeEffects.get(i);
      if (effect.getName().equalsIgnoreCase(name)) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Get total effect from all active effects
   * @param effect see CharEffect
   * @param attrOrSkill attribute or skill number
   * @return int can be even positive or negative
   */
  public int getTotalEffect(byte effect, int attrOrSkill) {
    int result =0;
    for (CharEffect eff:activeEffects) {
      if (effect == CharEffect.EFFECT_ON_ATTRIBUTE) {
        if ((eff.getEffect() == effect) && (eff.getAttributeOrSkill()==attrOrSkill)) {
          result = result +eff.getValue();
        }
      }
      if (effect == CharEffect.EFFECT_ON_SKILL) {
        if ((eff.getEffect() == effect) && (eff.getAttributeOrSkill()==attrOrSkill)) {
          result = result +eff.getValue();
        }
      }
      if (eff.getEffect()==CharEffect.EFFECT_ON_MIGHTY && effect == CharEffect.EFFECT_ON_SKILL) {
        if (attrOrSkill == Character.SKILL_MELEE) {
          result = result+eff.getValue();
        }
        if (attrOrSkill == Character.SKILL_DODGING) {
          result = result+eff.getValue();
        }
      }
      if ((effect == CharEffect.EFFECT_ON_HEALTH) &&(eff.getEffect() == effect)) {
          result = result +eff.getValue();
      }
      if ((effect == CharEffect.EFFECT_ON_STAMINA) &&(eff.getEffect() == effect)) {
        result = result +eff.getValue();
      }
      if ((effect == CharEffect.EFFECT_ON_WILLPOWER) &&(eff.getEffect() == effect)) {
        result = result +eff.getValue();
      }
      if ((effect == CharEffect.EFFECT_ON_LIGHT) &&(eff.getEffect() == effect)) {
        result = result +eff.getValue();
        if (result > 3) {
          result = 3;
        }
        if (result < -2) {
          result = -2;
        }
      }
      if ((effect == CharEffect.EFFECT_PACIFISM) &&(eff.getEffect() == effect)) {
        result = 1;
      }
      if ((effect == CharEffect.EFFECT_ON_CARRYING_CAPACITY) &&(eff.getEffect() == effect)) {
        result = result +eff.getValue();
      }
      if ((effect == CharEffect.EFFECT_ON_ARMOR) &&(eff.getEffect() == effect)) {
        result = result +eff.getValue();
      }
    }
    return result;
  }
  
 
  
  /**
   * Remove certain types of effects, use beatDifficulty MAX_DIFFICULTY on items
   * @param type
   * @param beatDifficulty
   * @return boolean wasSuccesfull
   */
  public boolean removeEffects(byte type, int beatDifficulty) {
    boolean removed = false;
    Iterator<CharEffect> it = activeEffects.iterator();
    while (it.hasNext()) {
      CharEffect eff = it.next();
      if ((eff.getType()==type) && (eff.getDifficulty() <= beatDifficulty)) {
        it.remove();
        removed = true;
      }
    }
    return removed;
  }
  
  public ArrayList<CharEffect> getActiveEffects() {
    return activeEffects;
  }
  
  /**
   * Write Active Effects into DataOutputStream
   * @param os DataOutputStream
   * @throws IOException if write fails
   */
  public void writeActiveEffects(DataOutputStream os) throws IOException {
    // Write number of active effects
    os.writeByte(activeEffects.size());
    for (int i=0;i<activeEffects.size();i++) {
      CharEffect eff = activeEffects.get(i);
      eff.writeEffect(os);
    }
  }
  
  /**
   * Reads Active effect from DataInputStream
   * @param is DataInputStream
   * @param mapVersion Version field of map
   * @throws IOException if read fails
   */
  public void readActiveEffects(DataInputStream is, String mapVersion)
      throws IOException {
    int num = is.readByte();
    activeEffects.clear();
    for (int i=0;i<num;i++) {
      CharEffect eff = new CharEffect();
      eff.readEffect(is,mapVersion);
      activeEffects.add(eff);
    }
  }
}
