package org.jheroes.map.item;

import java.io.DataOutputStream;
import java.io.IOException;

import org.jheroes.map.character.CharEffect;
import org.jheroes.map.character.Character;
import org.jheroes.map.character.CombatModifiers.AttackType;
import org.jheroes.map.character.Spell;
import org.jheroes.map.character.SpellFactory;


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
 * Very generic item class, used both map and inventory
 * 
 *
 */
//TODO: Add attack type into item information if it is something else than 
//normal or blunt
public class Item {
  
  
  /**
   * Generic item which is not generally usable other valuables like gems etc..
   */
  public final static byte TYPE_ITEM_GENERIC = 0;
  /**
   * Weapon items
   */
  public final static byte TYPE_ITEM_WEAPON = 1;
  /**
   * Wearable armor
   */
  public final static byte TYPE_ITEM_ARMOR = 2;
  /**
   * Shield, gives defense and possibility with PERK to use as weapon
   */
  public final static byte TYPE_ITEM_SHIELD = 3;

  /**
   * Wearable headgear, may give some protection
   */
  public final static byte TYPE_ITEM_HEADGEAR = 4;

  /**
   * Wearable boots, may give some protection
   */
  public final static byte TYPE_ITEM_BOOTS = 5;
  
  /**
   * Wearable amulet, may give some protection
   */
  public final static byte TYPE_ITEM_AMULET = 6;

  /**
   * Wearable ring, may give some protection
   */
  public final static byte TYPE_ITEM_RING = 7;

  /**
   * Item which is useable only one time
   */
  public final static byte TYPE_ITEM_SINGLESHOT = 8;

  /**
   * Key
   */
  public final static byte TYPE_ITEM_KEY = 9;

  /**
   * Picklock
   */
  public final static byte TYPE_ITEM_PICKLOCK = 10;

  /**
   *    Quest item cannot be sold, generally not useable
   */
  public final static byte TYPE_ITEM_QUEST = 11;
  
  /**
   *   Item time money, requires saving price too
   */
  public final static byte TYPE_ITEM_MONEY = 12;

  /**
   *   Item which provides light but must use on hand
   */
  public final static byte TYPE_ITEM_LIGHT_DEVICE = 13;

  /**
   *   Item which provides health and stamina when used
   */
  public final static byte TYPE_ITEM_HEALING_KIT = 14;

  /**
   *   Item which can be used for digging
   */
  public final static byte TYPE_ITEM_DIGGING_TOOL = 15;

  /**
   *   Item which can be used for guiding
   */
  public final static byte TYPE_ITEM_MAGICL_COMPASS = 16;

  /**
   *   Item which can raise attributes permanently
   */
  public final static byte TYPE_ITEM_TOME = 17;

  /**
   * Weapon which skill is used
   */
  public final static byte WEAPON_SKILL_UNARMED = 0;
  public final static byte WEAPON_SKILL_MELEE = 1;
  public final static byte WEAPON_SKILL_RANGED = 2;
  
  /**
   * Item's identification level
   * KNOWN(3) means that all information is available
   * MAGICAL_BUT_CURSED(2) means that Detect Curse has revealed both macial and
   * cursed. 
   * MAGICAL(1) means that Detect Magic has revealed it as magical but it still
   * may be cursed
   * UNKNOWN(0) only non-magical information is available
   */
  public final static byte IDENTIFIED_STATUS_UNKNOWN = 0;
  public final static byte IDENTIFIED_STATUS_MAGICAL = 1;
  public final static byte IDENTIFIED_STATUS_MAGICAL_BUT_CURSED = 2;
  public final static byte IDENTIFIED_STATUS_KNOWN = 3;
  
  /**
   * No special effect
   */
  public final static byte EFFECT_TYPE_NO_EFFECT = -1;
  
  /**
   * Item's type this needs to be saved with maps and inventories
   */
  private byte type;
  
  /**
   * Item's index to search from factories. Needs to be save with maps and inventories
   */
  private int index;
  
  /**
   * TileNumber
   */
  private int tileNumber;
  
  /**
   * Item's name real name 
   */
  private String name;

  /**
   * Item's name before identifying 
   */
  private String unknownName;
  
  /**
   * Item's weight in 100g
   */
  private short weight;
  
  /**
   * Item's price in copper, must be save with type 12
   */
  private int price;
  
  /**
   * Can item be randomized into game.
   * Default is true
   */
  private boolean randomItem;
  
  /**
   * Is weapon throwable
   */
  private boolean throwableWeapon;
  
  public boolean isRandomItem() {
    return randomItem;
  }

  /**
   * Can item got via random items. True if can(default)
   * false cannot get via random items
   * @param randomItem Boolean
   */
  public void setRandomItem(boolean randomItem) {
    this.randomItem = randomItem;
  }

  public static final int PRICE_NORMAL = 0;
  public static final int PRICE_PLAYER_SELLING = 1;
  public static final int PRICE_PLAYER_BUYING = 2;
  
  /**
   * Is item on map or in inventory
   */
  private boolean itemOnMap;
  
  /**
   * Is item droppable by dead enemies.
   * For example natural weapons are undroppable
   */
  private boolean droppable;
  /**
   * Item coordinates on map, needs to be save on maps
   */
  private int x;
  private int y;
  
  /**
   * Weapon damage is between
   * minDamage - (minDamage+plusDamage)
   */
  private byte minDamage;
  private byte maxDamage;
  
  /**
   * If weapon is bluntWeapon then
   * half of damage is stamina and other is lethal
   */
  private boolean bluntWeapon;
  
  /**
   * How much armor is subtracted before dealing the damage 
   */
  private byte piercingPower;
   
  /**
   * On critical hit damage is multiplied with this
   */
  private byte criticalMultiplier;
  
  /**
   * Which weaponSkill is used
   * 0 unarmed, 1 Melee, 2 Ranged
   */
  private byte weaponSkill;
  
  /**
   * Is weapon twohanded
   */
  private boolean twoHandedWeapon;
  
  /**
   * Armor value of wearable armor
   */
  private byte armorValue;

  /**
   * Max dodge value with this armor
   */
  private int maxDodgeValue;
  
  /**
   * On critical hits armor value
   */
  private byte criticalArmorValue;
  
  /**
   * Defensive value of armor
   */
  private int defensiveValue;
  
  /**
   * Key name for unlocking the doors
   */
  private String keyValue;
  
  /**
   * Picklock bonus
   */
  private int bonusPickLock;
  
  /**
   * Item's identification status
   * This needs to be saved with map and inventories
   */
  private byte itemStatus; 
  
  /**
   * Is item magical
   */
  private boolean magical;
  /**
   * Is item cursed?
   * Cursed weapon uses more stamina and could have other ill effects
   */
  private boolean cursed;
  
  
  /**
   * Magical Weapon damage is between
   * minMagicDamage - (minMagicDamage+plusMagicDamage)
   */
  private byte minMagicDamage;
  private byte maxMagicDamage;
  
  /**
   * Magical defensive value
   */
  private int magicalDefensiveValue;
  
  /**
   * Magical armor value of wearable armor
   */
  private byte magicalArmorValue;
  
  /**
   * Effect, could be magical or non-magical, see CharEffect
   */
  private byte effect;
  
  private int effectAttrOrSkill;
  /**
   * Effect value, see CharEffect
   */
  private int effectValue;
  
  /**
   * How many turn effect last, used in SingleShot items
   */
  private int effectLasting;
  
  /**
   * Effect's attack type
   */
  private AttackType effectAttackType;
  
  /**
   * Attack type with current item
   */
  private AttackType attackType;
  
  public Item(int index,byte type, String name, int tileNumber) {
    this.index = index;
    this.tileNumber = tileNumber;
    this.setType(type);
    this.setRandomItem(true);
    if (type == TYPE_ITEM_QUEST) {
      // Quest items are either random items
      this.setRandomItem(false);
    } 
    if (type == TYPE_ITEM_KEY) {
      // Keys are either random items
      this.setRandomItem(false);
    } 
    this.setWeaponSkill((byte) Character.SKILL_MELEE);
    this.setName(name);
    this.setUnknownName(name);
    this.setWeight(1);
    this.setPrice(1);    
    this.putItemInInventory();
    this.setItemStatus(IDENTIFIED_STATUS_KNOWN);
    this.setMagical(false);
    this.setCursed(false);
    this.setEffect(EFFECT_TYPE_NO_EFFECT);
    this.setEffectValue(0);
    this.setEffectAttrOrSkill(0);
    this.setEffectLasting(0);
    this.setEffectAttackType(AttackType.NORMAL);
    this.setDroppable(true);
    this.setThrowableWeapon(false);
    this.setAttackType(AttackType.NORMAL);
  }

  /**
   * Setter for Type
   * @param type
   */
  public void setType(byte type) {
    this.type = type;
  }

  /** 
   * Getter for type
   * @return
   */
  public byte getType() {
    return type;
  }

  /**
   * Setter for name
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for name
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * setter for weight, weight in 100g
   * @param weight
   */
  public void setWeight(int weight) {
    if (weight < 0) {
      this.weight = 0;
    } else
    this.weight = (short) weight;
  }

  /**
   * Getter for weight in 100g
   * @return
   */
  public int getWeight() {
    return (int) weight;
  }
  
  /**
   * Get weigth as string formated into 100g precision
   * @return
   */
  public String getWeightAsString() {
    String result = "";
    int i = (int) weight;    
    if (i > 9) {
      String tmp = String.valueOf(i/10)+".";
      String tmp2 = String.valueOf(i);
      result = tmp +tmp2.charAt(tmp.length()-1);      
    } else {
      result = "0."+String.valueOf(i);
    }
    return result;
  }

  /**
   * Setter for price
   * @param price
   */
  public void setPrice(int price) {
    if (price < 0) {
     this.price = 0;
    } else {
      this.price = price;
    }
  }

  /**
   * Getter for price
   * @return
   */
  public int getPrice() {
    return price;
  }

  /**
   * put item into someone inventory
   */
  public void putItemInInventory() {
    this.itemOnMap = false;
    this.x = 0;
    this.y = 0;
  }

  /**
   * Put item on map
   * @param x
   * @param y
   */
  public void putItemOnMap(int x, int y) {
    this.itemOnMap = true;
    this.x = x;
    this.y = y;
  }
  
  /**
   * Is item on map or not
   * @return
   */
  public boolean isItemOnMap() {
    return itemOnMap;
  }
  
  /**
   * Getter for x-coordinate
   * @return -1 if not on map, otherwise x coordinate
   */
  public int getX() {
    if (isItemOnMap()) {
      return this.x;
    } else {
      return -1;
    }
  }
  
  /**
   * Getter for y-coordinate
   * @return -1 if not on map, otherwise y coordinate
   */
  public int getY() {
    if (isItemOnMap()) {
      return this.y;
    } else {
      return -1;
    }
  }

  public void setMinDamage(int minDamage) {
    this.minDamage = (byte) minDamage;
  }

  public int getMinDamage() {
    return (int) minDamage;
  }

  public void setMaxDamage(int maxDamage) {
    this.maxDamage = (byte) maxDamage;
  }

  public int getMaxDamage() {
    return (int) maxDamage;
  }

  /**
   * Is weapon blunt weapon or not. If this is set
   * then attack type is also changed to blunt attack
   * @param bluntWeapon Boolean
   */
  public void setBluntWeapon(boolean bluntWeapon) {
    this.bluntWeapon = bluntWeapon;
    if (bluntWeapon) {
      this.setAttackType(AttackType.BLUNT);
    }
  }

  public boolean isBluntWeapon() {
    return bluntWeapon;
  }

  public void setPiercingPower(int piercingPower) {
    this.piercingPower = (byte) piercingPower;
  }

  public int getPiercingPower() {
    return (int) piercingPower;
  }

  public void setCriticalMultiplier(int criticalMultiplier) {
    if (criticalMultiplier > 4) {
      criticalMultiplier = 4;
    }
    if (criticalMultiplier < 2) {
      criticalMultiplier = 2;
    }
    this.criticalMultiplier = (byte) criticalMultiplier;
  }

  public int getCriticalMultiplier() {
    return (int) criticalMultiplier;
  }

  public void setWeaponSkill(byte weaponSkill) {
    if ((weaponSkill == WEAPON_SKILL_MELEE) || (weaponSkill == WEAPON_SKILL_RANGED) ||
       (weaponSkill == WEAPON_SKILL_UNARMED)) {
      this.weaponSkill = weaponSkill;
    } else {
      this.weaponSkill = WEAPON_SKILL_MELEE;
    }
  }

  public byte getWeaponSkill() {
    return weaponSkill;
  }

  public void setArmorValue(int armorValue) {
    this.armorValue = (byte) armorValue;
  }

  public int getArmorValue() {
    return (int) armorValue;
  }

  public void setMaxDodgeValue(int maxDodgeValue) {
    this.maxDodgeValue = maxDodgeValue;
  }

  public int getMaxDodgeValue() {
    return maxDodgeValue;
  }

  public void setCriticalArmorValue(int criticalArmorValue) {
    this.criticalArmorValue = (byte) criticalArmorValue;
  }

  public int getCriticalArmorValue() {
    return (int) criticalArmorValue;
  }

  public void setDefensiveValue(int defensiveValue) {
    this.defensiveValue = defensiveValue;
  }

  public int getDefensiveValue() {
    return defensiveValue;
  }

  public void setKeyValue(String keyValue) {
    this.keyValue = keyValue;
  }

  public String getKeyValue() {
    return keyValue;
  }

  public void setBonusPickLock(int bonusPickLock) {
    this.bonusPickLock = bonusPickLock;
  }

  public int getBonusPickLock() {
    return bonusPickLock;
  }

  public void setItemStatus(byte itemStatus) {
    if ((itemStatus == IDENTIFIED_STATUS_KNOWN)||(itemStatus == IDENTIFIED_STATUS_MAGICAL)
        ||(itemStatus == IDENTIFIED_STATUS_MAGICAL_BUT_CURSED) 
        || (itemStatus == IDENTIFIED_STATUS_UNKNOWN) ){
    this.itemStatus = itemStatus;
    } else {
      this.itemStatus = IDENTIFIED_STATUS_UNKNOWN;
    }
  }

  public byte getItemStatus() {
    return itemStatus;
  }

  public void setUnknownName(String unknownName) {
    this.unknownName = unknownName;
  }

  public String getUnknownName() {
    return unknownName;
  }

  public String getItemNameInGame() {
    if (!isMagical()&&!isCursed()) {
      return getName();
    }
    switch (getItemStatus()) {
    case IDENTIFIED_STATUS_UNKNOWN: return getUnknownName();
    case IDENTIFIED_STATUS_MAGICAL: return getUnknownName()+"*";
    case IDENTIFIED_STATUS_MAGICAL_BUT_CURSED: return getUnknownName()+"*!";
    case IDENTIFIED_STATUS_KNOWN: return getName();
    }
    return getName();
  }
  
  public void setMinMagicDamage(int minMagicDamage) {
    this.minMagicDamage = (byte) minMagicDamage;
  }

  public int getMinMagicDamage() {
    return (int) minMagicDamage;
  }

  public void setMaxMagicDamage(int maxMagicDamage) {
    this.maxMagicDamage = (byte) maxMagicDamage;
  }

  public int getMaxMagicDamage() {
    return (int) maxMagicDamage;
  }

  public void setMagicalDefensiveValue(int magicalDefensiveValue) {
    this.magicalDefensiveValue = magicalDefensiveValue;
  }

  public int getMagicalDefensiveValue() {
    return magicalDefensiveValue;
  }

  public void setMagicalArmorValue(int magicalArmorValue) {
    this.magicalArmorValue = (byte) magicalArmorValue;
  }

  public int getMagicalArmorValue() {
    return (int) magicalArmorValue;
  }

  public void setMagical(boolean magical) {
    this.magical = magical;
  }

  public boolean isMagical() {
    return magical;
  }

  public void setCursed(boolean cursed) {
    this.cursed = cursed;
  }

  public boolean isCursed() {
    return cursed;
  }

  public int getIndex() {
    return index;
  }

  public int getTileNumber() {
    return tileNumber;
  }

  public void setEffect(byte effect) {
    this.effect = effect;
  }

  public byte getEffect() {
    return effect;
  }

  public void setEffectValue(int effectValue) {
    this.effectValue = effectValue;
  }

  public int getEffectValue() {
    return effectValue;
  }

  public void setEffectAttrOrSkill(int effectAttrOrSkill) {
    this.effectAttrOrSkill = effectAttrOrSkill;
  }

  public int getEffectAttrOrSkill() {
    return effectAttrOrSkill;
  }

  public void setTwoHandedWeapon(boolean twoHandedWeapon) {
    this.twoHandedWeapon = twoHandedWeapon;
  }

  public boolean isTwoHandedWeapon() {
    return twoHandedWeapon;
  }
  
  public String toString() {
    return getItemNameInGame();
  }

  
  /**
   * If item is not droppable then it not random either
   * @param droppable
   */
  public void setDroppable(boolean droppable) {
    this.droppable = droppable;
    if (!droppable) {
      this.setRandomItem(false);
    }
  }

  public boolean isDroppable() {
    return droppable;
  }
  
  public String getDamageAsString() {
    StringBuilder sb = new StringBuilder(20);
    int minDam=0;
    int maxDam=0;    
    sb.append("Damage");
    if (this.getAttackType() != AttackType.NORMAL) {
      sb.append("(");
      sb.append(this.getAttackType().toString());
      sb.append(")");
    }
    sb.append(":");
    if (isBluntWeapon()) {
      minDam = getMinDamage();
      maxDam = getMaxDamage();
      minDam=minDam/2;
      maxDam=maxDam/2;
      int minLetDam = minDam;
      int maxLetDam = maxDam;
      if (getItemStatus()==IDENTIFIED_STATUS_KNOWN) {
        minLetDam = minLetDam+getMinMagicDamage();
        maxLetDam = maxLetDam+getMaxMagicDamage();      
      }
      sb.append(minLetDam);
      sb.append("-");
      sb.append(maxLetDam);
      sb.append("(");
      sb.append(minDam);
      sb.append("-");
      sb.append(maxDam);
      sb.append(")");
    } else {
      if (getItemStatus()==IDENTIFIED_STATUS_KNOWN) {
        minDam = getMinDamage()+getMinMagicDamage();
        maxDam = getMaxDamage()+getMaxMagicDamage();      
      } else {
        minDam = getMinDamage();
        maxDam = getMaxDamage();            
      }
      sb.append(minDam);
      sb.append("-");
      sb.append(maxDam);      
    }
    sb.append(" x");
    sb.append(getCriticalMultiplier());
    sb.append("\n");
    sb.append("Piercing:");
    sb.append(getPiercingPower());
    sb.append("\n");
    sb.append("Skill to use: ");
    sb.append(Character.getSkillName(weaponSkill));
    return sb.toString();
  }
  
  public String getDefenseAsString() {
    StringBuilder sb = new StringBuilder(20);
    int defense=0;
    int armor=0;    
    if (getItemStatus()==IDENTIFIED_STATUS_KNOWN) {
      defense = getDefensiveValue()+getMagicalDefensiveValue();
      armor = getArmorValue()+getMagicalArmorValue();
    } else {
      defense = getDefensiveValue();
      armor = getArmorValue();
    }
    sb.append("Defense:");
    sb.append(defense);
    sb.append(" armor:");
    sb.append(armor);
    sb.append("\nCritical Armor:");
    sb.append(armor+getCriticalArmorValue());
    if ((getType() == Item.TYPE_ITEM_ARMOR) || (getType() == Item.TYPE_ITEM_SHIELD)) {
      sb.append("\nMax dodge:");
      sb.append(getMaxDodgeValue());
    }
    return sb.toString();
  }
  
  public String getEffectAsString() {
    StringBuilder sb = new StringBuilder(20);
    if ((getItemStatus()==IDENTIFIED_STATUS_KNOWN) && (getEffectValue() != 0)) {
      if (getEffectValue() > 0) {
        sb.append("\nBoosts ");
      } 
      if (getEffectValue() < 0){
        sb.append("\nCurses ");
      }
      switch (getEffect()) {
      case CharEffect.EFFECT_ON_ATTRIBUTE: {
        sb.append(Character.getAttributeName(getEffectAttrOrSkill()));
        sb.append(" by ");
        sb.append(getEffectValue());
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_ON_SKILL: {
        sb.append(Character.getSkillName(getEffectAttrOrSkill()));
        sb.append(" by ");
        sb.append(getEffectValue());
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_ON_WILLPOWER: {
        sb.append("willpower");
        sb.append(" by ");
        sb.append(getEffectValue());
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_ON_STAMINA: {
        sb.append("stamina");
        sb.append(" by ");
        sb.append(getEffectValue());
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_ON_HEALTH: {
        sb.append("health");
        sb.append(" by ");
        sb.append(getEffectValue());
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_ON_CARRYING_CAPACITY: {
        sb.append("carrying capacity");
        sb.append(" by ");
        sb.append(getEffectValue());
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_ON_HEALTH_AND_STAMINA: {
        sb.append("health and stamina");
        sb.append(" by ");
        sb.append(getEffectValue());
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_DRAIN_HEALTH: {
        sb = new StringBuilder(20);
        sb.append("\nDrain health");
        sb.append(" by ");
        sb.append("half damage");
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_DRAIN_STAMINA: {
        sb = new StringBuilder(20);
        sb.append("\nDrain stamina");
        sb.append(" by ");
        sb.append("half stamina damage");
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_DRAIN_VIGOR: {
        sb = new StringBuilder(20);
        sb.append("Drain stamina");
        sb.append(" by ");
        sb.append("half damage");
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_SLAYER: {
        sb = new StringBuilder(20);
        sb.append("\nExtra damage on crit. hit");
        sb.append(" by ");
        sb.append("10 per lvl");
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_ON_MIGHTY: {
        sb.append("might");
        sb.append(" by ");
        sb.append(getEffectValue());
        sb.append(".");
        break;
      }
      case CharEffect.EFFECT_ON_HIT_DISEASE:
      case CharEffect.EFFECT_ON_HIT_POISON:
      case CharEffect.EFFECT_ON_HIT_ENCHANT:{
        sb = new StringBuilder(20);
        sb.append("\n");
        sb.append(this.getEffectValue());
        sb.append(" ");
        sb.append(this.getEffectAttackType().toString());
        sb.append(" damages for ");
        sb.append(this.getEffectLasting());
        sb.append(" turns.");
        break;
      }
      }
      if (getType()==Item.TYPE_ITEM_SINGLESHOT) {
        sb.append("\nEffect lasts:");
        sb.append(getEffectLasting());
        sb.append(" turn");
        if (getEffectLasting()>1) {
          sb.append("s");
        }
        sb.append(".");
      }
    } 
    return sb.toString();
  }
  
  /**
   * Get item value when player is selling it.
   * @param PCBartering Player's Bartering skill
   * @param NPCBartering NPC's bartering skill
   * @return price, limits 1 copper -1.5xPrice
   */
  public int getSellPrice(int PCBartering, int NPCBartering) {
    int newPrice = getPrice()/2;
    int modify = 100+(PCBartering-NPCBartering);
    newPrice = newPrice*modify/100;   
    if (newPrice > getPrice()*3/2) {
      newPrice = getPrice()*3/2;
    }
    if (newPrice < getPrice()/4) {
      newPrice = getPrice()/4;
    }      
    return newPrice;
  }
  
  /**
   * Get item value when player is buying it
   * @param PCBartering Player's Bartering skill
   * @param NPCBartering NPC's bartering skill
   * @return price, limits sellPrice - 2xprice
   */
  public int getBuyPrice(int PCBartering, int NPCBartering) {
    int newPrice = getPrice();
    int modify = 100+(NPCBartering-PCBartering);
    newPrice = newPrice*modify/100;
    if (newPrice > getPrice()*2) {
      newPrice = getPrice()*2;
    }
    if (newPrice < getSellPrice(PCBartering, NPCBartering)) {
      newPrice = getSellPrice(PCBartering, NPCBartering);
    }      
    return newPrice;
  }
  
  /**
   * Get item information depending on is item in buying, selling or just normal
   * @param priceType 0 normal 1 selling 2 buying
   * @param PCBartering required when calculating selling and buying price
   * @param NPCBartering required when calculating selling and buying price
   * @return
   */
  public String getItemInformationPrice(int priceType, int PCBartering, int NPCBartering) {
    StringBuilder sb = new StringBuilder(20);
    sb.append(getItemNameInGame());
    sb.append("\n");
    sb.append("Value:");
    if (priceType == PRICE_PLAYER_BUYING) {
      sb.append(this.getBuyPrice(PCBartering, NPCBartering));
    } else
    if (priceType == PRICE_PLAYER_SELLING) {
      sb.append(this.getSellPrice(PCBartering, NPCBartering));
    } else { 
      sb.append(this.getPrice());
    }
    sb.append(" coppers\n");
    sb.append("Weigth:");
    sb.append(this.getWeightAsString());
    sb.append(" kg\n");
    switch (this.getType()) {
    case TYPE_ITEM_WEAPON: {
      sb.append(getDamageAsString());
      sb.append(getEffectAsString());
      if (isThrowableWeapon()) {
        sb.append("\nThrowable");
        if (isTwoHandedWeapon()) {
          sb.append(", Two handed");
        }
      } else {
        if (isTwoHandedWeapon()) {
          sb.append("\nTwo handed");
        }
      }
      break;
    }
    case TYPE_ITEM_ARMOR: {
      sb.append(getDefenseAsString());
      sb.append(getEffectAsString());
      break;
    }
    case TYPE_ITEM_SHIELD: {
      sb.append(getDefenseAsString());
      sb.append("\n");
      sb.append(getDamageAsString());
      sb.append(getEffectAsString());
      break;
    }
    case TYPE_ITEM_HEADGEAR: {
      sb.append(getDefenseAsString());
      sb.append(getEffectAsString());
      break;
    }
    case TYPE_ITEM_AMULET: {
      sb.append(getDefenseAsString());
      sb.append(getEffectAsString());
      break;
    }
    case TYPE_ITEM_BOOTS: {
      sb.append(getDefenseAsString());
      sb.append(getEffectAsString());
      break;
    }
    case TYPE_ITEM_RING: {
      sb.append(getDefenseAsString());
      sb.append(getEffectAsString());
      break;
    }
    case TYPE_ITEM_SINGLESHOT: {
      if (this.getName().startsWith("Scroll of")) {
        String spellName = this.getName().replace("Scroll of ","");
        Spell spell = SpellFactory.getSpell(spellName);
        sb.append(spell.toString());
      } else {
        sb.append(getEffectAsString());
      }
      break;
    }
    case TYPE_ITEM_PICKLOCK: {
      sb.append("Picklock bonus:");
      sb.append(getBonusPickLock());
      break;
    }
    case TYPE_ITEM_QUEST: {
      sb.append("Quest item");
      break;
    }
    case TYPE_ITEM_DIGGING_TOOL: {
      sb.append("Digging tool");
      break;
    }
    case TYPE_ITEM_TOME: {
      sb.append("Tome");
      break;
    }
    }
    
    return sb.toString();

  }
  
  /**
   * Get Item information with normal price
   * @return String
   */
  public String getItemInformation() {
    return getItemInformationPrice(PRICE_NORMAL, 1, 1);
  }
  
  public void writeItem(DataOutputStream os) throws IOException {
    os.writeByte((int) this.type);
    os.writeInt(this.index);
    os.writeByte((int)this.itemStatus);
    if (this.type == TYPE_ITEM_MONEY) {
      os.writeInt(this.price);
    }    
  }
  
  public void writeMapItem(DataOutputStream os) throws IOException {
    this.writeItem(os);
    os.writeInt(getX());
    os.writeInt(getY());
  }

  public int getEffectLasting() {
    return effectLasting;
  }

  public void setEffectLasting(int effectLasting) {
    this.effectLasting = effectLasting;
  }

  public boolean isThrowableWeapon() {
    return throwableWeapon;
  }

  public void setThrowableWeapon(boolean throwableWeapon) {
    this.throwableWeapon = throwableWeapon;
  }

  public AttackType getAttackType() {
    return attackType;
  }

  public void setAttackType(AttackType attackType) {
    this.attackType = attackType;
  }

  public AttackType getEffectAttackType() {
    return effectAttackType;
  }

  public void setEffectAttackType(AttackType effectAttackType) {
    this.effectAttackType = effectAttackType;
  }
}
