package org.jheroes.map.character;

import org.jheroes.map.character.CombatModifiers.AttackType;
import org.jheroes.map.item.Item;

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
 * Information about single attack
 * 
 */
public class Attack {

  /**
   * Minimum non lethal damage
   */
  private int minStaminaDamage;
  /**
   * Maximum non lethal damage
   */
  private int maxStaminaDamage;
  /**
   * Minimum lethal damage
   */
  private int minLethalDamage;
  /**
   * Maximum lethal damage
   */
  private int maxLethalDamage;
  /**
   * Attack bonus to skill
   */
  private int attackBonus;
  /**
   * If hit is critical then what multiplier is used to calculate
   * higher damage
   */
  private int criticalMultiplier;
  /**
   * How much stamina one attack requires
   */
  private int staminaCost;
  /**
   * Armor piercing damage. This is amount damage always passes through the
   * armor.
   */
  private int piercing;
  /**
   * Is attack ranged or melee
   */
  private boolean isRangedAttack;
  /**
   * Is attack done with throwable weapon
   */
  private boolean isThrowingAttackPossible;
  /**
   * Successful hit drains health from target to attacker
   */
  private boolean drainHealth;
  /**
   * Successful hit drains stamina from target to attacker
   */
  private boolean drainStamina;
  /**
   * Successful hit drains health and stamina from target to attacker
   */
  private boolean drainVigor;
  /**
   * Is attack so called slayer? If hit is critical then
   * attack deals extra slaying damage which is 10*attacker's levels.
   */
  private boolean slayer;
  /**
   * Attack type for attack. This should be get from weapon or spell
   */
  private AttackType attackType;
  
  /**
   * Effect on target when attack hits
   */
  private byte effectOnHit;
  
  /**
   * How long effect last in turns
   */
  private int effectLast;
  
  /**
   * Effect value mainly damage per turn
   */
  private int effectValue;
  
  /**
   * What kind of damage effect causes.
   */
  private AttackType effectAttackType;
  
  public Attack() {
    setMinStaminaDamage(0);
    setMaxStaminaDamage(0);
    setMinLethalDamage(0);
    setMaxLethalDamage(0);
    setAttackBonus(0);
    setCriticalMultiplier(2);
    setStaminaCost(1);
    setPiercing(0);
    setRangedAttack(false);
    setThrowingAttackPossible(false);
    setDrainHealth(false);
    setDrainStamina(false);
    setDrainVigor(false);
    setSlayer(false);
    setAttackType(AttackType.NORMAL);
    setEffectOnHit(Item.EFFECT_TYPE_NO_EFFECT);
  }
  
  public int getMinStaminaDamage() {
    return minStaminaDamage;
  }
  public void setMinStaminaDamage(int minStaminaDamage) {
    if (minStaminaDamage >= 0) { 
      this.minStaminaDamage = minStaminaDamage;
    } else {
      this.minStaminaDamage=0;
    }
  }
  public int getMaxStaminaDamage() {
    return maxStaminaDamage;
  }
  public void setMaxStaminaDamage(int maxStaminaDamage) {
    if (maxStaminaDamage >= this.minStaminaDamage) {
      this.maxStaminaDamage = maxStaminaDamage;
    } else {
      this.maxStaminaDamage = this.minStaminaDamage;
    }
  }
  public int getMinLethalDamage() {
    return minLethalDamage;
  }
  public void setMinLethalDamage(int minLethalDamage) {
    if (minLethalDamage >= 0) {
      this.minLethalDamage = minLethalDamage;
    } else {
      this.minLethalDamage = 0;
    }
  }
  public int getMaxLethalDamage() {
    return maxLethalDamage;
  }
  public void setMaxLethalDamage(int maxLethalDamage) {
    if (maxLethalDamage > this.minLethalDamage) { 
      this.maxLethalDamage = maxLethalDamage;
    } else {
      this.maxLethalDamage = this.minLethalDamage;
    }
    if (this.maxLethalDamage < 1) {
      this.maxLethalDamage = 1;
    }
  }
  public int getAttackBonus() {
    return attackBonus;
  }
  public void setAttackBonus(int attackBonus) {
    this.attackBonus = attackBonus;
  }
  public int getCriticalMultiplier() {
    return criticalMultiplier;
  }
  public void setCriticalMultiplier(int criticalMultiplier) {
    if (criticalMultiplier > 1) {
      this.criticalMultiplier = criticalMultiplier;
    } else {
      this.criticalMultiplier = 2;
    }
  }
  public int getStaminaCost() {
    return staminaCost;
  }
  public void setStaminaCost(int staminaCost) {
    if (staminaCost > 0) {
      this.staminaCost = staminaCost;
    } else {
      this.staminaCost = 1;
    }
  }

  public void setPiercing(int piercing) {
    if (piercing >= 0) {
      this.piercing = piercing;
    } else {
      piercing = 0;
    }
  }

  public int getPiercing() {
    return piercing;
  }
  
  public String getAttackAsString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Attack: ");
    sb.append(attackBonus);
    sb.append("\n");
    sb.append("Damage");
    if (this.getAttackType() != AttackType.NORMAL) {
      sb.append("(");
      sb.append(this.getAttackType().toString());
      sb.append(")");
    }
    sb.append(": ");
    sb.append(minLethalDamage);
    sb.append("-");
    sb.append(maxLethalDamage);
    sb.append(" ");
    if (maxStaminaDamage > 0) {
      sb.append("(");
      sb.append(minStaminaDamage);
      sb.append("-");
      sb.append(maxStaminaDamage);
      sb.append(") ");
    }
    sb.append("x");
    sb.append(criticalMultiplier);
    sb.append("\nPiercing: ");
    sb.append(piercing);
    sb.append("\nStamina: ");
    sb.append(staminaCost);
    return sb.toString();
  }
  
  /**
   * Generate spell's attack information as a string
   * @return String
   */
  public String getAttackAsSpell() {
    StringBuilder sb = new StringBuilder();
    sb.append("Damage");
    if (this.getAttackType() != AttackType.NORMAL) {
      sb.append("(");
      sb.append(this.getAttackType().toString());
      sb.append(")");
    }
    sb.append(": ");
    sb.append(minLethalDamage);
    sb.append("-");
    sb.append(maxLethalDamage);
    sb.append(" ");
    if (maxStaminaDamage > 0) {
      sb.append("(");
      sb.append(minStaminaDamage);
      sb.append("-");
      sb.append(maxStaminaDamage);
      sb.append(") ");
    }
    sb.append("x");
    sb.append(criticalMultiplier);
    if (piercing > 0) {
      sb.append("\nPiercing: ");
      sb.append(piercing);
    }

    return sb.toString();
    
  }
  
  public String getAttackAsHTMLString() {
    String tmp ="<html>";
    tmp = tmp+"Attack: "+String.valueOf(attackBonus)+"<br>";
    tmp = tmp+"Damage: "+String.valueOf(minLethalDamage)+"-"+String.valueOf(maxLethalDamage)+" ";
    if (maxStaminaDamage > 0) {
      tmp = tmp+"("+String.valueOf(minStaminaDamage)+"-"+String.valueOf(maxStaminaDamage)+") ";
    }
    tmp = tmp +"x"+String.valueOf(criticalMultiplier);
    tmp = tmp+"<br>Piercing: "+String.valueOf(piercing);
    tmp = tmp+"<br>Stamina: "+String.valueOf(staminaCost);
    tmp = tmp+"</html>";
    return tmp;
  }

  public boolean isRangedAttack() {
    return isRangedAttack;
  }

  public void setRangedAttack(boolean isRangedAttack) {
    this.isRangedAttack = isRangedAttack;
  }

  public boolean isThrowingAttackPossible() {
    return isThrowingAttackPossible;
  }

  public void setThrowingAttackPossible(boolean isThrowingAttackPossible) {
    this.isThrowingAttackPossible = isThrowingAttackPossible;
  }

  public boolean isDrainHealth() {
    return drainHealth;
  }

  public void setDrainHealth(boolean drainHealth) {
    this.drainHealth = drainHealth;
  }

  public boolean isDrainStamina() {
    return drainStamina;
  }

  public void setDrainStamina(boolean drainStamina) {
    this.drainStamina = drainStamina;
  }

  public boolean isDrainVigor() {
    return drainVigor;
  }

  public void setDrainVigor(boolean drainVigor) {
    this.drainVigor = drainVigor;
  }

  public boolean isSlayer() {
    return slayer;
  }

  public void setSlayer(boolean slayer) {
    this.slayer = slayer;
  }

  public AttackType getAttackType() {
    return attackType;
  }

  public void setAttackType(AttackType attackType) {
    this.attackType = attackType;
  }

  public byte getEffectOnHit() {
    return effectOnHit;
  }

  public void setEffectOnHit(byte effectOnHit) {
    this.effectOnHit = effectOnHit;
  }

  public int getEffectLast() {
    return effectLast;
  }

  public void setEffectLast(int effectLast) {
    this.effectLast = effectLast;
  }

  public int getEffectValue() {
    return effectValue;
  }

  public void setEffectValue(int effectValue) {
    this.effectValue = effectValue;
  }

  public AttackType getEffectAttackType() {
    return effectAttackType;
  }

  public void setEffectAttackType(AttackType effectAttackType) {
    this.effectAttackType = effectAttackType;
  }
  
  /**
   * Create item effect when hit to attack
   * @param item Item used to hit
   */
  public void createEffectFromItem(Item item) {
    if (item.getEffect() == CharEffect.EFFECT_ON_HIT_DISEASE ||
        item.getEffect() == CharEffect.EFFECT_ON_HIT_POISON ||
        item.getEffect() == CharEffect.EFFECT_ON_HIT_ENCHANT) {
      this.setEffectAttackType(item.getEffectAttackType());
      this.setEffectOnHit(item.getEffect());
      this.setEffectLast(item.getEffectLasting());
      this.setEffectValue(item.getEffectValue());
    }
  }

   
  
}
