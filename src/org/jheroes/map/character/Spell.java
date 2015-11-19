package org.jheroes.map.character;

import org.jheroes.map.Map;
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
 * Informationa about single spell
 * 
 */
public class Spell {

  public static final int SPELL_TARGET_CASTER = 0;
  public static final int SPELL_TARGET_PARTY = 1;
  public static final int SPELL_TARGET_TARGET = 2;

  public final static int DISPEL_TYPE_NONE=0;
  public final static int DISPEL_TYPE_REMOVE_CURSE=1;
  public final static int DISPEL_TYPE_CURE_DISEASE=2;
  public final static int DISPEL_TYPE_CURE_POISON=3;
  public final static int DISPEL_TYPE_DISPLE_MAGIC=4;
  public final static int DISPEL_TYPE_DETECT_MAGIC=5;
  public final static int DISPEL_TYPE_IDENTIFY_ITEMS=6;

  public final static int SPELL_RADIUS_NEED_HIT = 0;
  
  /** ...
   *  .O.
   *  ...
   */
  public final static int SPELL_RADIUS_1_TILE = 1;
  
  /** .o.
   *  oOo
   *  .o.
   */
  public final static int SPELL_RADIUS_5_TILE = 2;
  /** ooo
   *  oOo
   *  ooo
   */
  public final static int SPELL_RADIUS_9_TILE = 3;

  /**  o
   *  ooo
   * ooOoo
   *  ooo
   *   o
   */
  public final static int SPELL_RADIUS_13_TILE = 4;

  private int targetType;
  private CharEffect spellEffect;
  private String spellName;
  private Attack spellAttack;
  private int spellCastingCost;
  private int dispelType;
  private int minimumSkill;
  /**
   * Matches to effects tile
   */
  private int spellAnimation;
  /**
   * Spell skill matches character skill
   */
  private int spellSkill;
  
  /**
   * Spell effective area, mostly used in attack spells
   */
  private int spellRadius;
  
  private boolean mindEffecting;
  
  public Spell(String name) {
    spellName = name;
    targetType = SPELL_TARGET_CASTER;
    spellEffect = null;
    spellAttack = null;
    spellCastingCost = 1;
    dispelType = DISPEL_TYPE_NONE;
    spellAnimation = Map.GRAPH_EFFECT_BUBBLE_SLEEP;
    spellSkill = Character.SKILL_QI_MAGIC;
    spellRadius = SPELL_RADIUS_NEED_HIT;
    mindEffecting = false;
    minimumSkill = 10;
  }
  
  /**
   * Does spell need a target 
   * @return boolean return true if needs to select target for spell
   */
  public boolean needTarget() {
    if (targetType == SPELL_TARGET_TARGET) {
      return true;
    } else {
      return false;
    }
  }
  
  public int getTargetType() {
    return targetType;
  }
  public void setTargetType(int targetType) {
    this.targetType = targetType;
  }
  public CharEffect getEffect() {
    return spellEffect;
  }
  public void setEffect(CharEffect spellEffect) {
    this.spellEffect = spellEffect;
  }
  public String getName() {
    return spellName;
  }
  public void setName(String spellName) {
    this.spellName = spellName;
  }
  public int getSkill() {
    return spellSkill;
  }
  public void setSkill(int spellSkill) {
    this.spellSkill = spellSkill;
  }
  public Attack getAttack() {
    return spellAttack;
  }
  public void setAttack(Attack spellAttack) {
    this.spellAttack = spellAttack;
  }
  public int getCastingCost() {
    return spellCastingCost;
  }
  public void setCastingCost(int spellCastingCost) {
    this.spellCastingCost = spellCastingCost;
  }
  public int getDispelType() {
    return dispelType;
  }
  public void setDispelType(int dispelType) {
    this.dispelType = dispelType;
  }
  public int getAnimation() {
    return spellAnimation;
  }
  public void setAnimation(int spellAnimation) {
    this.spellAnimation = spellAnimation;
  }
  public int getRadius() {
    return spellRadius;
  }
  public void setRadius(int spellRadius) {
    this.spellRadius = spellRadius;
  }
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(spellName);
    sb.append(" "+spellCastingCost+" SP\n");
    switch (spellSkill) {
    case Character.SKILL_QI_MAGIC: sb.append("School: Qi-Magic"); break;
    case Character.SKILL_SORCERY: sb.append("School: Sorcery"); break;
    case Character.SKILL_WIZARDRY: sb.append("School: Wizardy"); break;
    }
    sb.append("\nSkill Req.: "+minimumSkill);
    sb.append(" ");
    if (spellName.equalsIgnoreCase(SpellFactory.SPELL_NAME_POWER_OF_HEALING)) {
      sb.append("Bonus on health ");
      sb.append("2 per level");
      sb.append("\nDuration: 1");
      sb.append("\nTarget: Aim");
    }
    if (spellEffect != null) {
    sb.append(spellEffect.getEffectAsString());
    }
    if (spellAttack != null) {
      if (spellName.equalsIgnoreCase(SpellFactory.SPELL_NAME_SMITE_UNDEAD)) {        
        sb.append("Damage: 1-6 per level x2");
        sb.append("\nPiercing: 0");
        sb.append("\nTarget: Aim");
        return sb.toString(); 
      } if (spellName.equalsIgnoreCase(SpellFactory.SPELL_NAME_POWER_OF_DESTRUCTION)) {
        sb.append("Damage: 1-3 per level x2");
        sb.append("\nPiercing: 0");
        sb.append("\nTarget: Aim");
        return sb.toString();         
      } else {
        sb.append(spellAttack.getAttackAsSpell());
      }
    }
    switch (dispelType) {
    case DISPEL_TYPE_CURE_POISON: sb.append("\nCures poison."); break;
    case DISPEL_TYPE_CURE_DISEASE: sb.append("\nCures disease."); break;
    case DISPEL_TYPE_DISPLE_MAGIC: sb.append("\nDispels magic."); break;
    case DISPEL_TYPE_REMOVE_CURSE: sb.append("\nRemoves curse."); break;
    case DISPEL_TYPE_DETECT_MAGIC: sb.append("\nDetects magical and cursed items."); break;
    case DISPEL_TYPE_IDENTIFY_ITEMS: sb.append("\nIdentifies magical and cursed items."); break;
    }
    switch (targetType) {
    case SPELL_TARGET_CASTER: sb.append("\nTarget: Caster"); break;
    case SPELL_TARGET_PARTY: sb.append("\nTarget: Party"); break;
    case SPELL_TARGET_TARGET: sb.append("\nTarget: Aim"); break;
    }
    
    return sb.toString();
  }

  public boolean isMindEffecting() {
    return mindEffecting;
  }

  public void setMindEffecting(boolean isMindEffecting) {
    this.mindEffecting = isMindEffecting;
  }

  public int getMinimumSkill() {
    return minimumSkill;
  }

  public void setMinimumSkill(int minimumSkill) {
    this.minimumSkill = minimumSkill;
  }
}
