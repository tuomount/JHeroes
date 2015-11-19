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
 * Information about defense
 * 
 */
public class Defense {

  /**
   * Defense value
   */
  private int defense;
  /**
   * Armor value
   */
  private int armorRating;
  /**
   * Critical armor value
   */
  private int criticalArmorRating;
  /**
   * Willpower
   */
  private int willPower;

  public Defense() {
    
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public int getDefense() {
    return defense;
  }

  public void setArmorRating(int armorRating) {
    this.armorRating = armorRating;
  }

  public int getArmorRating() {
    return armorRating;
  }

  public void setCriticalArmorRating(int criticalArmorRating) {
    this.criticalArmorRating = criticalArmorRating;
  }

  public int getCriticalArmorRating() {
    return criticalArmorRating;
  }
  
  /**
   * Get Defense as text with line changes
   * @return String
   */
  public String getDefenseAsString() {
    StringBuilder sb = new StringBuilder();
    
    sb.append("Defense: ");
    sb.append(String.valueOf(defense));
    sb.append("\n");
    sb.append("Armor rating: ");
    sb.append(String.valueOf(armorRating));
    sb.append("\n");
    sb.append("Critical Armor rating: ");
    sb.append(String.valueOf(criticalArmorRating));
    sb.append("\n");
    sb.append("Willpower: ");
    sb.append(String.valueOf(willPower));
    return sb.toString();
  }
  
  /**
   * Get Defense as HTML string
   * @return String
   */
  public String getDefenseAsHTMLString() {
    StringBuilder sb = new StringBuilder();
    sb.append("<html>");
    sb.append("Defense: "+String.valueOf(defense)+"<br>");
    sb.append("Armor rating: "+String.valueOf(armorRating)+"<br>");
    sb.append("Critical Armor rating: "+String.valueOf(criticalArmorRating)+"<br>");
    sb.append("Willpower: ");
    sb.append(String.valueOf(willPower));
    sb.append("</html>");
    return sb.toString();
  }

  public int getWillPower() {
    return willPower;
  }

  public void setWillPower(int willPower) {
    this.willPower = willPower;
  }

}
