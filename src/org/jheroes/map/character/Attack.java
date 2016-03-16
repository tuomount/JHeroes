package org.jheroes.map.character;

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

  private int minStaminaDamage;
  private int maxStaminaDamage;
  private int minLethalDamage;
  private int maxLethalDamage;
  private int attackBonus;
  private int criticalMultiplier;
  private int staminaCost;
  private int piercing;
  private boolean isRangedAttack;
  private boolean isThrowingAttackPossible;
  private boolean drainHealth;
  private boolean drainStamina;
  private boolean drainVigor;
  private boolean slayer;
  
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
    sb.append("Damage: ");
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
  //TODO: Add attack type to spells
  public String getAttackAsSpell() {
    StringBuilder sb = new StringBuilder();
    sb.append("Damage: ");
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

   
  
}
