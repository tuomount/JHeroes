package org.jheroes.map.character;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jheroes.map.DiceGenerator;
import org.jheroes.map.MapUtilities;
import org.jheroes.map.Party;
import org.jheroes.map.character.CombatModifiers.AttackType;
import org.jheroes.map.item.Item;
import org.jheroes.map.item.ItemFactory;
import org.jheroes.soundplayer.SoundPlayer;
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
 * Information of single character for both NPC and PC
 * 
 */
public class Character extends CharacterAnimation {
  
  /**
   * Attributes
   */
  public final static int ATTRIBUTE_STRENGTH        = 0;
  public final static int ATTRIBUTE_AGILITY         = 1;
  public final static int ATTRIBUTE_ENDURANCE       = 2;
  public final static int ATTRIBUTE_INTELLIGENCE    = 3;
  public final static int ATTRIBUTE_WISDOM          = 4;
  public final static int ATTRIBUTE_CHARISMA        = 5;
  public final static int ATTRIBUTE_LUCK            = 6;
  public final static int MAX_NUMBERS_OF_ATTRIBUTES = 7;

  /**
   * Skill
   */
  public final static int SKILL_UNARMED             = 0;
  public final static int SKILL_MELEE               = 1;
  public final static int SKILL_RANGED_WEAPONS      = 2;
  public final static int SKILL_DODGING             = 3;
  public final static int SKILL_WIZARDRY            = 4;
  public final static int SKILL_SORCERY             = 5;
  public final static int SKILL_QI_MAGIC            = 6;
  public final static int SKILL_BARTERING           = 7;
  public final static int SKILL_DIPLOMACY           = 8;
  public final static int SKILL_LOCK_PICKING        = 9;
  public final static int MAX_NUMBERS_OF_SKILL      = 10;

  public final static int HOSTILITY_LEVEL_PLAYER = -1;
  public final static int HOSTILITY_LEVEL_AVOID = 0;
  public final static int HOSTILITY_LEVEL_AGGRESSIVE = 1;
  public final static int HOSTILITY_LEVEL_GUARD = 2;
  
  public final static int CHECK_NOT_DONE_YET = -1;
  public final static int CHECK_FAIL = 0;
  public final static int CHECK_SUCCESS = 1;
  public final static int CHECK_CRITICAL_SUCCESS = 2;
  
  public final static int MAX_ITEMS_ON_SHOPKEEPERS = 25;
  
  /**
   * Array of attributes, how much points given in the beginning
   * Max 10 and min 1
   */
  private int[] attributes;
  /**
   * Amount of skill points for each skill, max here is 100 and min 0
   */
  private int[] skills;
  
  /**
   * Character's current HP
   */
  private int currentHP;
  
  /**
   * Character's current SP
   */
  private int currentSP;
  
  /**
   * Character name, usually just first name
   */
  private String name;
  
  private Perks perks;
  
  private CharEffects activeEffects;
  
  private int faceNumber;
  
  private int money;
  /**
   * Character's whole name
   */
  private String longName;
  
  /**
   * Default character description
   */
  private String description;
  
  /**
   * Inventory, basically backpack
   */
  private ArrayList<Item> inventory;
  
  private Item firstHand;
  private Item secondHand;
  private Item armor;
  private Item headGear;
  private Item boots;
  private Item amulet;
  private Item ring;

  /**
   * Character experience points, only used with Player Characters
   * NPC how much PCs gain experience from defeating NPC
   */
  private int experience;
  
  /**
   * Character Level PC are between 1-20
   * 
   */   
  private int level;
  /**
   * Spell list character knows, List contains spell names which can be used
   * to find actual information of spells.
   */
  private ArrayList<String> spellList;
  
  /**
   * Task list used in NPCs
   */
  private ArrayList<CharTask> taskList;
  private int currentTaskIndex=-1;
  private boolean currentTaskDone=false;
  private boolean allTasksDone=false;
  private boolean amIOnScreen=false;
  
  
  private int hostilityLevel;
  
  private int lastDirection;
  
  
  public Character(int tileOffset) {
    super(CharacterAnimation.ANIMATION_TYPE_NORMAL,1,1,tileOffset);
    setName("Joe");
    setLongName("Joe Doe");
    attributes = new int[MAX_NUMBERS_OF_ATTRIBUTES];
    for (int i =0;i<MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      attributes[i] = 5;
    }
    skills = new int[MAX_NUMBERS_OF_SKILL];
    perks = new Perks();
    activeEffects = new CharEffects();
    inventory = new ArrayList<Item>();
    firstHand = null;
    secondHand = null;
    armor = null;
    headGear = null;
    boots = null;
    amulet = null;
    ring = null;
    setFaceNumber(0);
    setMoney(0);
    taskList = new ArrayList<CharTask>();
    spellList = new ArrayList<String>();
    setDescription("");
    setExperience(0);
    setLevel(1);
    setCurrentHP(getMaxHP());
    setCurrentSP(getMaxStamina());
    setHostilityLevel(HOSTILITY_LEVEL_AVOID);
  }  
  
  /**
   * Adds new spell to spell book
   * @param spellName
   * @return boolean, true if spell added
   */
  public boolean addSpell(String spellName) {
    boolean found = false;
    for (int i=0;i<spellList.size();i++) {
      String tmp = spellList.get(i);
      if (tmp.equalsIgnoreCase(spellName)) {
        found = true;
      }
    }
    if (found == false) {
      spellList.add(spellName);
    }
    if (found == false) {
      return true;
    } else {
      return false;
    }
  }
  
  public void removeSpell(String spellName) {
    for (int i=0;i<spellList.size();i++) {
      String tmp = spellList.get(i);
      if (tmp.equalsIgnoreCase(spellName)) {
        spellList.remove(i);
        break;
      }
    }
    
  }
  
  public ArrayList<String> getSpellList() {
    return spellList;
  }
  
  /**
   * Check if character has attack spell and enough stamina to cast it
   * @return spell if yes, otherwise null
   */
  public Spell doIHaveAttackSpell(Character target) {
    int dist = MapUtilities.getDistance(this.getX(), this.getY(), 
        target.getX(), target.getY());
    int maxSpellDist=0;
    if (dist <= 1) {
      maxSpellDist = 0;
    } else if (dist == 2) {
      maxSpellDist = 3;
    } else {
      maxSpellDist = 4;
    }
    Spell result = null;
    ArrayList<Spell> listOfAttackSpells = new ArrayList<Spell>();
    if (spellList.size() > 0) {
      for (int i=0;i<spellList.size();i++) {
        Spell spell = SpellFactory.getSpell(spellList.get(i));
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_FIRESTORM)) &&
            (spell.getCastingCost() < getCurrentSP()) &&
            (spell.getRadius()<=maxSpellDist)){
           listOfAttackSpells.add(spell);
           listOfAttackSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_ICE_BREATH)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)){
           listOfAttackSpells.add(spell);
           listOfAttackSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_THUNDER_STRIKE)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)){
           listOfAttackSpells.add(spell);
           listOfAttackSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_FEAR_OF_DARKNESS)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)){
           listOfAttackSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_DROWNING)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)){
           listOfAttackSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_POISON_CLOUD)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)){
           listOfAttackSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_FIREBALL)) &&
           (spell.getCastingCost() < getCurrentSP())&&
           (spell.getRadius()<=maxSpellDist)){
          listOfAttackSpells.add(spell);
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_BURDEN)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)){
           listOfAttackSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_FLAME_BURST)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_FROST_BITE)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_ILLUSIONARY_DEATH)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_SHOCK_BURST)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_POISON_SPIT)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_MAGIC_DART)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_MAGIC_ARROW)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_MENTAL_ARROW)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_MENTAL_SPEAR)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_PACIFISM)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_FAIRY_FLAME)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_DARKNESS)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_WICKED_FATIQUE)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_MIND_BLAST)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_QI_BLAST)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (spell.getRadius()<=maxSpellDist)) {
          listOfAttackSpells.add(spell);
        }
      }
      if (listOfAttackSpells.size() > 0) {
        result = listOfAttackSpells.get(DiceGenerator.getRandom(listOfAttackSpells.size()-1));
      }
    }    
    return result;
  }

  /**
   * Check if character has healing spell and enough stamina to cast it
   * @return spell if yes, otherwise null
   */
  public Spell doIHaveHealingSpell() {
    Spell result = null;
    if (spellList.size() > 0) {
      for (int i=0;i<spellList.size();i++) {
        Spell spell = SpellFactory.getSpell(spellList.get(i));
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_QI_HEAL)) &&
            (spell.getCastingCost() < getCurrentSP())){
           return spell;
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_MINOR_HEAL)) &&
           (spell.getCastingCost() < getCurrentSP())){
          return spell;
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_HEAL)) &&
            (spell.getCastingCost() < getCurrentSP())){
           return spell;
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_MIRACLE_HEAL)) &&
            (spell.getCastingCost() < getCurrentSP())){
           return spell;
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_HEALING_AURA)) &&
            (spell.getCastingCost() < getCurrentSP())){
           return spell;
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_HEALING_CIRCLE)) &&
            (spell.getCastingCost() < getCurrentSP())){
           return spell;
         }
      }
    }
    return result;
  }

  /**
   * Check if character has uncasted boosting spell and enough stamina to cast it
   * @return spell if yes, otherwise null
   */
  public Spell doIHaveBoostSpell() {
    Spell result = null;
    ArrayList<Spell> listOfBoostSpells = new ArrayList<Spell>();
    if (spellList.size() > 0) {
      for (int i=0;i<spellList.size();i++) {
        Spell spell = SpellFactory.getSpell(spellList.get(i));
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_HAZE)) &&
           (spell.getCastingCost() < getCurrentSP()) &&
           (!activeEffects.isEffectByName(SpellFactory.SPELL_NAME_HAZE))){
          listOfBoostSpells.add(spell);
        }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_MAGIC_FISTS)) &&
            (spell.getCastingCost() < getCurrentSP()) &&
            (getAttackSkill() == SKILL_UNARMED)&&
            (!activeEffects.isEffectByName(SpellFactory.SPELL_NAME_MAGIC_FISTS))){
          listOfBoostSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_QI_STRENGTH)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (!activeEffects.isEffectByName(SpellFactory.SPELL_NAME_QI_STRENGTH))){
          listOfBoostSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_WIZARD_LIGHT)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (!activeEffects.isEffectByName(SpellFactory.SPELL_NAME_WIZARD_LIGHT))){
          listOfBoostSpells.add(spell);
         }
        // Darkness spell is boost spell only if character has dark vision
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_DARKNESS)) &&
            (spell.getCastingCost() < getCurrentSP()) && 
            (this.getPerks().isPerkActive(Perks.PERK_DARK_VISION))&&
            (!activeEffects.isEffectByName(SpellFactory.SPELL_NAME_DARKNESS))){
          listOfBoostSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_ARCHER_S_WILL)) &&
            (spell.getCastingCost() < getCurrentSP()) &&
            (getAttackSkill() == SKILL_RANGED_WEAPONS)&&
            (!activeEffects.isEffectByName(SpellFactory.SPELL_NAME_ARCHER_S_WILL))){
          listOfBoostSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_BEAST_S_WILL)) &&
            (spell.getCastingCost() < getCurrentSP()) &&
            (getAttackSkill() == SKILL_UNARMED)&&
            (!activeEffects.isEffectByName(SpellFactory.SPELL_NAME_BEAST_S_WILL))){
          listOfBoostSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_MIST)) &&
            (spell.getCastingCost() < getCurrentSP())&&
            (!activeEffects.isEffectByName(SpellFactory.SPELL_NAME_MIST))){
          listOfBoostSpells.add(spell);
         }
        if ((spell.getName().equals(SpellFactory.SPELL_NAME_WARRIOR_S_WILL)) &&
            (spell.getCastingCost() < getCurrentSP()) &&
            (getAttackSkill() == SKILL_MELEE)&&
            (!activeEffects.isEffectByName(SpellFactory.SPELL_NAME_WARRIOR_S_WILL))){
          listOfBoostSpells.add(spell);
         }
      }
      if (listOfBoostSpells.size() > 0) {
        result = listOfBoostSpells.get(DiceGenerator.getRandom(listOfBoostSpells.size()-1));
      }
    }
    return result;
  }

  public void copyOf(Character copyFrom) {
    if (copyFrom != null) {
      setTileOffset(copyFrom.getTileOffset());
      setType(copyFrom.getType());
      setHeading(copyFrom.getHeading());
      setPosition(copyFrom.getX(), copyFrom.getY());
      
      setName(copyFrom.getName());
      setLongName(copyFrom.getLongName());
      setDescription(copyFrom.getDescription());
      
      for (int i =0;i<MAX_NUMBERS_OF_ATTRIBUTES;i++) {
        attributes[i] = copyFrom.getAttribute(i);
      }
      for (int i =0;i<MAX_NUMBERS_OF_SKILL;i++) {
        skills[i] = copyFrom.getSkill(i);
      }
      for (int i=0;i<Perks.ALL_PERK_NAMES.length;i++) {
        perks.setPerk(i, copyFrom.perks.getPerk(i));
      }
      
      ArrayList<CharEffect> effects = copyFrom.activeEffects.getActiveEffects();
      activeEffects = new CharEffects();
      for (int i=0;i<effects.size();i++){
        CharEffect eff = effects.get(i);
        CharEffect newEff = new CharEffect(eff);
        activeEffects.addNewEeffect(newEff);
      }
      faceNumber = copyFrom.getFaceNumber();
      money = copyFrom.getMoney();
      // Copying inventory
      inventory.clear();
      for (int i=0;i<copyFrom.inventorySize();i++) {
        inventory.add(ItemFactory.copy(copyFrom.inventoryGetIndex(i)));
      }
      if (copyFrom.inventoryGetFirstHand()!=null) {
        firstHand = ItemFactory.copy(copyFrom.inventoryGetFirstHand());
      } else {
        firstHand = null;
      }
      if (copyFrom.inventoryGetSecondHand()!=null) {
        secondHand = ItemFactory.copy(copyFrom.inventoryGetSecondHand());
      } else {
        secondHand = null;
      }
      if (copyFrom.inventoryGetArmor()!=null) {
        armor = ItemFactory.copy(copyFrom.inventoryGetArmor());
      } else {
        armor = null;
      }
      if (copyFrom.inventoryGetHeadGear()!=null) {
        headGear = ItemFactory.copy(copyFrom.inventoryGetHeadGear());
      } else {
        headGear = null;
      }
      if (copyFrom.inventoryGetBoots()!=null) {
        boots = ItemFactory.copy(copyFrom.inventoryGetBoots());
      } else {
        boots = null;
      }
      if (copyFrom.inventoryGetAmulet()!=null) {
        amulet = ItemFactory.copy(copyFrom.inventoryGetAmulet());
      } else {
        amulet = null;
      }
      if (copyFrom.inventoryGetRing()!=null) {
        ring = ItemFactory.copy(copyFrom.inventoryGetRing());
      } else {
        ring = null;
      }
      experience = copyFrom.getExperience();
      hostilityLevel = copyFrom.getHostilityLevel();
      level = copyFrom.getLevel();
      currentHP = copyFrom.getCurrentHP();
      currentSP = copyFrom.getCurrentSP();
      // Spell list
      spellList.clear();
      for (int i=0;i<copyFrom.spellList.size();i++) {
        spellList.add(copyFrom.spellList.get(i));
      }
      // task list
      taskList.clear();
      for (int i=0;i<copyFrom.getNumberOfTasks();i++) {
        CharTask task = new CharTask(copyFrom.getTaskByIndex(i).getTime(),
                          copyFrom.getTaskByIndex(i).getTask(),
                          copyFrom.getTaskByIndex(i).getWayPointName(),
                          copyFrom.getTaskByIndex(i).getDescription());
        taskList.add(task);
      }
    }
  }
  
  public void clearTaskList() {
    taskList.clear();
  }
  
  public void clearSpellList() {
    spellList.clear();
  }
  
  private int calculateEquipmentValue() {
    int value = 0;
    if (amulet != null) {
      value = value +amulet.getPrice();
    }
    if (armor != null) {
      value = value +armor.getPrice();
    }
    if (boots != null) {
      value = value +boots.getPrice();
    }
    if (headGear != null) {
      value = value +headGear.getPrice();
    }
    if (ring != null) {
      value = value +ring.getPrice();
    }
    if (firstHand != null) {
      value = value +firstHand.getPrice();
    }
    if (secondHand != null) {
      value = value +secondHand.getPrice();
    }
    return value;
  }
  
  private int calculateSpellListValue() {
    int value = 0;
    if (spellList.size() > 0) {
      for (int i=0;i<spellList.size();i++) {
        Spell spell = SpellFactory.getSpell(spellList.get(i));
        value = value +spell.getCastingCost();
      }
    }
    return value;
  }
  
  /**
   * Calculate effective Will power
   * @return int
   */
  public int getEffectiveWillPower() {
    int base = 50;
    base=base+5*getEffectiveAttribute(ATTRIBUTE_WISDOM);
    if (perks.isPerkActive(Perks.PERK_BONEHEAD)) {
      base=base+20;
    }
    if (perks.isPerkActive(Perks.PERK_STRONG_WILLPOWER)) {
      base=base+10;
    }
    if (perks.isPerkActive(Perks.PERK_INNER_WILLPOWER)) {
      base=base+10;
    }
    int bonus =activeEffects.getTotalEffect(CharEffect.EFFECT_ON_WILLPOWER, 0);
    base=base+bonus;
    return base;
  }
  
  public void calculateNPCLevelAndExperience() {
    int totalSkill = 0;
    int skillPointsPerLvl = 10+attributes[ATTRIBUTE_INTELLIGENCE]+attributes[ATTRIBUTE_WISDOM];
    for (int i=0;i<MAX_NUMBERS_OF_SKILL;i++) {
      totalSkill = totalSkill + skills[i];
    }
    int skillLvl = 1+(totalSkill / skillPointsPerLvl);
    
    int perkLvl = (perks.getNumberOfActive()-2)*2;
    if (perkLvl < 1) {
      perkLvl = 1;
    }
    
    int allowedAttributes = 7*6+perks.getNumberOfActiveAttributePerks();
    int totalAttrbitutes = 0;
    for (int i=0;i<MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      totalAttrbitutes = totalAttrbitutes+attributes[i];
    }
    setLevel((perkLvl+skillLvl)/2);
    int exp=getLevel()*50;   
    int attributeModifier = (totalAttrbitutes-allowedAttributes);
    if (attributeModifier < 0) {
      attributeModifier = attributeModifier *2;
    } else {
      attributeModifier = attributeModifier*5;
    }
    int expmod = exp;
    expmod = expmod *attributeModifier/100;
    exp = exp +expmod+calculateEquipmentValue()/10;
    exp = exp +calculateSpellListValue()*10;
    setExperience(exp);
  }
  
  /**
   * How much experience is needed for next level
   * @return int
   */
  public int getNextNeededExp() {
    int result = 999999;
    switch (getLevel()) {
    case 1: result = 1000; break;
    case 2: result = 2500; break;
    case 3: result = 4500; break;
    case 4: result = 7000; break;
    case 5: result = 10000; break;
    case 6: result = 13500; break;
    case 7: result = 17500; break;
    case 8: result = 22000; break;
    case 9: result = 27000; break;
    case 10: result = 32500; break;
    case 11: result = 38500; break;
    case 12: result = 45000; break;
    case 13: result = 52000; break;
    case 14: result = 59500; break;
    case 15: result = 67500; break;
    case 16: result = 76000; break;
    case 17: result = 85000; break;
    case 18: result = 94500; break;
    case 19: result = 104500; break;
    }
    return result;
  }
  
  /**
   * Calculate max hit point
   * @return int
   */
  public int getMaxHP() {
    int maxHP = attributes[ATTRIBUTE_STRENGTH]+attributes[ATTRIBUTE_ENDURANCE]*2;
    maxHP = maxHP +(getLevel()-1)*(attributes[ATTRIBUTE_STRENGTH]/2+attributes[ATTRIBUTE_ENDURANCE]);
    if (perks.isPerkActive(Perks.PERK_DIEHARD)) {
      maxHP = maxHP+(getLevel()*3);
    }
    return maxHP;
  }
  
  /**
   * If character is resting restore stamina
   */
  public void getStaminaInRestTurn() {
    int amount = 0;
    switch (getEffectiveAttribute(ATTRIBUTE_ENDURANCE)) {
    case 1: amount = 1; break;
    case 2: amount = 1; break;
    case 3: amount = 1; break;
    case 4: amount = 2; break;
    case 5: amount = 2; break;
    case 6: amount = 3; break;
    case 7: amount = 3; break;
    case 8: amount = 4; break;
    case 9: amount = 5; break;
    case 10: amount = 6; break;
    }
    if (perks.isPerkActive(Perks.PERK_ATHLETIC)) {
      amount = amount*2;      
      if (getEffectiveAttribute(ATTRIBUTE_ENDURANCE)==7) {
        amount=amount+1;
      }
    }
    currentSP=currentSP+amount;
    if (currentSP > getMaxStamina()) {
      currentSP = getMaxStamina();
    }
  }
  
  public int getMaxStamina() {
    int maxStamina = 10+attributes[ATTRIBUTE_WISDOM]+attributes[ATTRIBUTE_ENDURANCE];
    maxStamina = maxStamina +(getLevel()-1)*(attributes[ATTRIBUTE_WISDOM]/2+attributes[ATTRIBUTE_ENDURANCE]);
    return maxStamina;
  }
  
  public String calculateNPCLevelAndExperienceAsString() {
    int totalSkill = 0;
    int skillPointsPerLvl = 10+attributes[ATTRIBUTE_INTELLIGENCE]+attributes[ATTRIBUTE_WISDOM];
    for (int i=0;i<MAX_NUMBERS_OF_SKILL;i++) {
      totalSkill = totalSkill + skills[i];
    }
    int skillLvl = 1+(totalSkill / skillPointsPerLvl);
    
    int perkLvl = (perks.getNumberOfActive()-2)*2;
    if (perkLvl < 1) {
      perkLvl = 1;
    }
    
    int allowedAttributes = 7*6+perks.getNumberOfActiveAttributePerks();
    int totalAttrbitutes = 0;
    for (int i=0;i<MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      totalAttrbitutes = totalAttrbitutes+attributes[i];
    }
    int lvl = (perkLvl+skillLvl)/2;
    int exp=lvl*50;   
    int attributeModifier = (totalAttrbitutes-allowedAttributes);
    if (attributeModifier < 0) {
      attributeModifier = attributeModifier *2;
    } else {
      attributeModifier = attributeModifier*5;
    }
    int expmod = exp;
    expmod = expmod *attributeModifier/100;
    exp = exp +expmod+calculateEquipmentValue()/10;
    exp = exp +calculateSpellListValue()*10;
    String result = "<html>"+"SkillLvl:"+String.valueOf(skillLvl)+" PerkLvl:" +
    		String.valueOf(perkLvl)+" Lvl:"+String.valueOf(lvl)+"<br>" +
    				"Attribute Mod:"+String.valueOf(attributeModifier)+"% Exp:" 
    						+String.valueOf(exp)+"</html>";
    return result;
  }
  
  
  /**
   * Return number of tasks in list
   * @return int
   */
  public int getNumberOfTasks() {
    return taskList.size();
  }
  
  /**
   * Get Task from list by index
   * @param i index
   * @return TaskList or null if not found
   */
  public CharTask getTaskByIndex(int i) {
    if ((i>= 0) && (i<taskList.size())) {
      return taskList.get(i);
    } else {
      return null;
    }
  }
  
  /**
   * Remove task by index
   * @param i index
   */
  public void removeTaskByIndex(int i)
  {
    if ((i>= 0) && (i<taskList.size())) {
      taskList.remove(i);
    }
  }
  
  /**
   * Add task to list
   * @param task Task to add
   */
  public void addTask(CharTask task) {
    taskList.add(task);
  }
  
  /**
   * Add task to list
   * @param task Task to add
   * @param index Index where to Add
   */
  public void addTask(CharTask task,int index) {
    if (index > -1) {
      taskList.add(index, task);
    }
  }

  /**
   * add kill task where target is party member
   * @param name Party member's name
   */
  public void addTaskKillPartyMember(String name) {
    CharTask task = new CharTask("-",CharTask.TASK_ATTACK_PC, name, "");
    if (currentTaskIndex != -1) {
      taskList.add(currentTaskIndex, task);
    } else {
      taskList.add(task);
    }
  }
  
  /**
   * add kill task where target is npc
   * @param name NPC's name
   */
  public void addTaskKillNPC(String name) {
    CharTask task = new CharTask("-",CharTask.TASK_ATTACK_NPC, name, "");
    if (currentTaskIndex != -1) {
      taskList.add(currentTaskIndex, task);
    } else {
      taskList.add(task);
    }
  }

  /**
   * Add task which makes NPC to run out of the map
   * @param direction
   */
  public void addTaskRunExit(int direction) {
    
    CharTask task = new CharTask("-",CharTask.TASK_RUN_EXIT, Integer.toString(direction), "");
    if (currentTaskIndex != -1) {
      taskList.add(currentTaskIndex, task);
    } else {
      taskList.add(task);
    }
  }

  /**
   * Add task which makes NPC to move away until cannot move anymore
   * @param direction
   */
  public void addTaskMoveAway(int direction) {
    
    CharTask task = new CharTask("-",CharTask.TASK_MOVE_AWAY, Integer.toString(direction), "");
    if (currentTaskIndex != -1) {
      taskList.add(currentTaskIndex, task);
    } else {
      taskList.add(task);
    }
  }

  
  /**
   * Clears previous tasks, moves away from screen, then jumps to WP and stays 
   * there for ever.
   * @param wp Waypoint name
   * @param direction Direction
   */
  public void addTaskToMoveWP(String wp, int direction) {
    taskList.clear();
    currentTaskIndex = -1;
    CharTask task = new CharTask("-",CharTask.TASK_RUN_TO_WP, Integer.toString(direction), "");
    taskList.add(task);
    task = new CharTask("-",CharTask.TASK_JUMP_ONCE_TO_WP, wp, "");
    taskList.add(task);
    task = new CharTask("-",CharTask.TASK_MOVE_INSIDE_WP, wp, "");
    taskList.add(task);
  }
  
  /**
   * Add task which makes NPC to move away until cannot move anymore
   * @param direction
   */
  public void addTaskTeleportToExit(String wp) {
    CharTask task = new CharTask("-",CharTask.TASK_JUMP_TO_WP, wp, "#Teleport");
    if (currentTaskIndex != -1) {
      addTaskRunExit(0);
      taskList.add(currentTaskIndex, task);
    } else {
      taskList.add(task);
      addTaskRunExit(0);
    }
  }

  /**
   * Remove current task if it is move away
   */
  public void removeTaskMoveAway() {
    CharTask task = taskList.get(currentTaskIndex);
    if (task.getTask()==CharTask.TASK_MOVE_AWAY) {      
      taskList.remove(currentTaskIndex);
      if (currentTaskIndex >= taskList.size()) {
        currentTaskIndex--;
      }
    }
  }

  /**
   * Remove current task if it is move away
   */
  public void removeTaskRunToWP() {
    CharTask task = taskList.get(currentTaskIndex);
    if (task.getTask()==CharTask.TASK_RUN_TO_WP) {      
      taskList.remove(currentTaskIndex);
      if (currentTaskIndex >= taskList.size()) {
        currentTaskIndex--;
      }
    }
  }

  /**
   * Remove current task if it is move away
   */
  public void removeTaskJumpOnceToWP() {
    CharTask task = taskList.get(currentTaskIndex);
    if (task.getTask()==CharTask.TASK_JUMP_ONCE_TO_WP) {      
      taskList.remove(currentTaskIndex);
      if (currentTaskIndex >= taskList.size()) {
        currentTaskIndex--;
      }
    }
  }

  /**
   * Remove current kill task from task list
   */
  public void removeKillTask() {
    CharTask task = taskList.get(currentTaskIndex);
    if ((task.getTask()==CharTask.TASK_ATTACK_PC) || 
      (task.getTask()==CharTask.TASK_ATTACK_NPC)) {      
      taskList.remove(currentTaskIndex);
      if (currentTaskIndex >= taskList.size()) {
        currentTaskIndex--;
      }
    }
  }
  
  /**
   * Set attribute
   * @param attr, attribute which to set
   * @param value, value must be between 1-10
   */
  public void setAttribute(int attr, int value) {
    if ((attr >= 0) && (attr < MAX_NUMBERS_OF_ATTRIBUTES)) {
      if ((value > 0) && (value <= 10)) {
        attributes[attr] = value;
      }
    }
  }
  
  /**
   * Get attribute
   * @param attr, attribute which to get
   * @return 1-10, if not valid attribute then return 0
   */
  public int getAttribute(int attr) {
    if ((attr >= 0) && (attr < MAX_NUMBERS_OF_ATTRIBUTES)) {
      return attributes[attr];
    } else {
      return 0;
    }
  }

  /**
   * Get effective attribute, check character effects too
   * @param attr, attribute which to get
   * @return 1-10, if not valid attribute then return 0
   */
  public int getEffectiveAttribute(int attr) {
    if ((attr >= 0) && (attr < MAX_NUMBERS_OF_ATTRIBUTES)) {
      int i= attributes[attr]+activeEffects.getTotalEffect(CharEffect.EFFECT_ON_ATTRIBUTE, attr);
      if (i>10) { i = 10;}
      if (i<1) { i = 1;}
      return i;
    } else {
      return 0;
    }
  }
  
  public int getBonusDamageFromStrength() {
    int result = 0;
    switch (getEffectiveAttribute(ATTRIBUTE_STRENGTH)) {
    case 1: result = -3; break;
    case 2: result = -2; break;
    case 3: result = -1; break;
    case 4: result = -1; break;
    case 5: result = 0; break;
    case 6: result = 1; break;
    case 7: result = 1; break;
    case 8: result = 2; break;
    case 9: result = 3; break;
    case 10: result = 4; break;
    }
    return result;
  }
  
  public int getBonusDamageFromAgility() {
    int result = 0;
    switch (getEffectiveAttribute(ATTRIBUTE_AGILITY)) {
    case 1: result = -2; break;
    case 2: result = -1; break;
    case 3: result = -1; break;
    case 4: result = 0; break;
    case 5: result = 0; break;
    case 6: result = 0; break;
    case 7: result = 1; break;
    case 8: result = 2; break;
    case 9: result = 3; break;
    case 10: result = 4; break;
    }
    return result;
  }
  
  public int getBonusDamageFromCharisma() {
    int result = 0;
    switch (getEffectiveAttribute(ATTRIBUTE_CHARISMA)) {
    case 1: result = -2; break;
    case 2: result = -1; break;
    case 3: result = -1; break;
    case 4: result = 0; break;
    case 5: result = 0; break;
    case 6: result = 1; break;
    case 7: result = 2; break;
    case 8: result = 3; break;
    case 9: result = 4; break;
    case 10: result = 5; break;
    }
    return result;
  }
  
  /**
   * Get Character Perks
   * @return, Perks
   */
  public Perks getPerks() {
    return perks;
  }
  
  /**
   * Set skill
   * @param skill, skill which to set
   * @param value, value must be between 0-100
   * @return boolean is new value is set
   */
  public boolean setSkill(int skill, int value) {
    if ((skill >= 0) && (skill < MAX_NUMBERS_OF_SKILL)) {
      if ((value >= 0) && (value <= 100)) {
        skills[skill] = value;
        return true;
      }
    }
    return false;
  }
  
  /**
   * Get skill
   * @param skill, skill which to get
   * @return 0-100, if not valid attribute then return -1
   */
  public int getSkill(int skill) {
    if ((skill >= 0) && (skill < MAX_NUMBERS_OF_SKILL)) {
      return skills[skill];
    } else {
      return -1;
    }
  }
  
  /**
   * Get effective Skill value
   * @param skill, skill index
   * @return int, -1 if not valid skill
   */
  public int getEffectiveSkill(int skill) {
    if ((skill >= 0) && (skill < MAX_NUMBERS_OF_SKILL)) {
      int points = skills[skill];
      int base = 0;
      int perkPoints = 0;
      int effPoints = activeEffects.getTotalEffect(CharEffect.EFFECT_ON_SKILL, skill);
      switch(skill) {
      case SKILL_BARTERING: {
          base = getEffectiveAttribute(ATTRIBUTE_CHARISMA)*2;
          if (perks.isPerkActive(Perks.PERK_NEGOTIATOR)) {
            perkPoints = 15;
          }
          break;
      }
      case SKILL_DIPLOMACY:{
          base = getEffectiveAttribute(ATTRIBUTE_CHARISMA)+getEffectiveAttribute(ATTRIBUTE_WISDOM);
          if (perks.isPerkActive(Perks.PERK_NEGOTIATOR)) {
            perkPoints = 15;
          }
          break;
      }
      case SKILL_DODGING:{
        base = getEffectiveAttribute(ATTRIBUTE_AGILITY)*2; 
        if (perks.isPerkActive(Perks.PERK_UNPREDICTABLE_MOVES)) {
          perkPoints = 15;
        }
        if ((perks.isPerkActive(Perks.PERK_FENCER)) && 
           (isLightLoad() || isEmptyLoad())) {
          perkPoints = perkPoints+5;
        }
        if ((perks.isPerkActive(Perks.PERK_MASTER_FENCER)) && 
            (isLightLoad() || isEmptyLoad())) {
           perkPoints = perkPoints+5;
         }
        break;
      }
      case SKILL_LOCK_PICKING:{
        base = getEffectiveAttribute(ATTRIBUTE_AGILITY)+getEffectiveAttribute(ATTRIBUTE_INTELLIGENCE); 
        if (perks.isPerkActive(Perks.PERK_LOCK_SMITH)) {
          perkPoints = 10;
        }        
        if (perks.isPerkActive(Perks.PERK_MASTER_LOCK_SMITH)) {
          perkPoints = 20;
        }        
        break;
      }
      case SKILL_MELEE:{
        base = getEffectiveAttribute(ATTRIBUTE_STRENGTH)+getEffectiveAttribute(ATTRIBUTE_ENDURANCE); 
        if (perks.isPerkActive(Perks.PERK_MERCENARY)) {
          perkPoints = 5;
        }
        if (perks.isPerkActive(Perks.PERK_MANATARMS)) {
          perkPoints = 10;
        }
        if (perks.isPerkActive(Perks.PERK_MASTER_OF_WEAPONS)) {
          perkPoints = 15;
        }
        if ((perks.isPerkActive(Perks.PERK_FENCER)) && 
            (isLightLoad() || isEmptyLoad())) {
           perkPoints = perkPoints+5;
         }
         if ((perks.isPerkActive(Perks.PERK_MASTER_FENCER)) && 
             (isLightLoad() || isEmptyLoad())) {
            perkPoints = perkPoints+5;
          }
        break;
      }
      case SKILL_QI_MAGIC:{
        base = getEffectiveAttribute(ATTRIBUTE_WISDOM)+getEffectiveAttribute(ATTRIBUTE_INTELLIGENCE); 
        if (perks.isPerkActive(Perks.PERK_MAGICALLY_ACTIVE)) {
          perkPoints = 5;
        }
        if (perks.isPerkActive(Perks.PERK_MAGICALLY_AWAKENED)) {
          perkPoints = 10;
        }
        if (perks.isPerkActive(Perks.PERK_MAGICALLY_ENLIGHTENED)) {
          perkPoints = 15;
        }
        break;
      }
      case SKILL_RANGED_WEAPONS:{
        base = getEffectiveAttribute(ATTRIBUTE_AGILITY)+getEffectiveAttribute(ATTRIBUTE_STRENGTH); 
        if (perks.isPerkActive(Perks.PERK_RANGED_FIGHTER)) {
          perkPoints = 5;
        }
        if (perks.isPerkActive(Perks.PERK_IMPROVED_RANGED_FIGHTER)) {
          perkPoints = 10;
        }
        if (perks.isPerkActive(Perks.PERK_MASTER_RANGED_FIGHTER)) {
          perkPoints = 15;
        }
        break;
      }
      case SKILL_SORCERY:{
        base = getEffectiveAttribute(ATTRIBUTE_WISDOM)+getEffectiveAttribute(ATTRIBUTE_CHARISMA); 
        if (perks.isPerkActive(Perks.PERK_MAGICALLY_ACTIVE)) {
          perkPoints = 5;
        }
        if (perks.isPerkActive(Perks.PERK_MAGICALLY_AWAKENED)) {
          perkPoints = 10;
        }
        if (perks.isPerkActive(Perks.PERK_MAGICALLY_ENLIGHTENED)) {
          perkPoints = 15;
        }
        break;
      }
      case SKILL_WIZARDRY:{
        base = getEffectiveAttribute(ATTRIBUTE_INTELLIGENCE)*2; 
        if (perks.isPerkActive(Perks.PERK_MAGICALLY_ACTIVE)) {
          perkPoints = 5;
        }
        if (perks.isPerkActive(Perks.PERK_MAGICALLY_AWAKENED)) {
          perkPoints = 10;
        }
        if (perks.isPerkActive(Perks.PERK_MAGICALLY_ENLIGHTENED)) {
          perkPoints = 15;
        }
        break;
      }
      case SKILL_UNARMED:{
        base = getEffectiveAttribute(ATTRIBUTE_ENDURANCE)+getEffectiveAttribute(ATTRIBUTE_AGILITY)/2+getEffectiveAttribute(ATTRIBUTE_STRENGTH)/2;
        if (base < 20) {
          base++;
        }
        if (perks.isPerkActive(Perks.PERK_UNPREDICTABLE_MOVES)) {
          perkPoints = 5;
        }
        if (perks.isPerkActive(Perks.PERK_KUNG_FU_MASTER)) {
          perkPoints = perkPoints+10;
        }
        break;
      }
      }
      return base+points+perkPoints+effPoints;
    } else {
      return -1;
    }
  }
  
  /**
   * Set Character's full name
   * @param longName
   */
  public void setLongName(String longName) {
    this.longName = longName;
  }

  /**
   * Get long name
   * @return
   */
  public String getLongName() {
    return longName;
  }

  /**
   * Set character's short name, most commonly used
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get character's short name
   * @return
   */
  public String getName() {
    return name;
  }
  
  /**
   * Get Maximum carrying capacity in (100g)
   * @return int
   */
  public int inventoryGetMaxCarryingCapacity() {
    int result = getEffectiveAttribute(ATTRIBUTE_STRENGTH)*100;
    if (perks.isPerkActive(Perks.PERK_STRONG_BACK)) {
      result = result +150;
    }
    result = result +activeEffects.getTotalEffect(CharEffect.EFFECT_ON_CARRYING_CAPACITY, 0)*10;
    return result;
  }
  
  /**
   * Get Maximum carrying capacity in kg and as String
   * @return String
   */
  public String inventoryGetMaxCarryingCapacityAsString() {
    String result;
    int i = this.inventoryGetMaxCarryingCapacity();
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
   * Is character on empty load
   * @return boolean
   */
  public boolean isEmptyLoad() {
    int max = inventoryGetMaxCarryingCapacity();
    int load = inventoryGetCurrentLoad();
    if (load <= max/4) {
      return true;
    } else {
      return false;
    }
    
  }
  
  /**
   * Is character on light load?
   * @return boolean
   */
  public boolean isLightLoad() {
    int max = inventoryGetMaxCarryingCapacity();
    int load = inventoryGetCurrentLoad();
    if ((load <= max/2) &&(load > max/4)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Is character on medium load?
   * @return boolean
   */
  public boolean isMediumLoad() {
    int max = inventoryGetMaxCarryingCapacity();
    int load = inventoryGetCurrentLoad();
    if ((load <= max/4*3) && (load > max/2)) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Is character on heavyLoad
   * @return boolean
   */
  public boolean isHeavyLoad() {
    int max = inventoryGetMaxCarryingCapacity();
    int load = inventoryGetCurrentLoad();
    if (load > max/4*3) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Is character on over load
   * @return boolean
   */
  public boolean isOverLoad() {
    int max = inventoryGetMaxCarryingCapacity();
    int load = inventoryGetCurrentLoad();
    if (load > max) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * get Current inventory load including wearable things in 100g
   * @return int
   */
  public int inventoryGetCurrentLoad() {
    int result = 0;
    for (Item it : inventory) {
      result = result +it.getWeight();
    }
    if(firstHand!=null) {
      result = result +firstHand.getWeight();
    }
    if(secondHand!=null) {
      result = result +secondHand.getWeight();
    }
    if(armor!=null) {
      result = result+armor.getWeight();
    }
    if(headGear!=null) {
      result = result+headGear.getWeight();
    }
    if(boots!=null) {
      result = result+boots.getWeight();
    }
    if(amulet!=null) {
      result = result+amulet.getWeight();
    }
    if(ring!=null) {
      result = result+ring.getWeight();
    }
    return result;
  }
  
  /**
   * Get current load inventory in kgs and string
   * @return String
   */
  public String inventoryGetCurrentLoadAsString() {
    String result;
    int i =this.inventoryGetCurrentLoad();
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
   * Pick up item to inventory
   * @param target item to pick up
   * @return boolean, true if successful, false if cannot carry anymore
   */
  public boolean inventoryPickUpItem(Item target) {
    if (target.getType()==Item.TYPE_ITEM_MONEY) {
      money = money+target.getPrice();
      return true;
    } 
    if (target.getWeight()+inventoryGetCurrentLoad()<=inventoryGetMaxCarryingCapacity()) {
      target.putItemInInventory();
      inventory.add(target);
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Pick up item without caring carrying capacity.
   * @param target item to pick up
   */
  public void inventoryPickUpItemForce(Item target) {
    if (target.getType()==Item.TYPE_ITEM_MONEY) {
      money = money+target.getPrice();
      return;
    } 
    target.putItemInInventory();
    inventory.add(target);
  }
  
  /**
   * Unequip item from first hand and put it back to inventory
   */
  public void unequipFirstHand() {
    if (firstHand != null) {
      activeEffects.removeEffects(CharEffect.TYPE_WEARABLE_FIRSTHAND, CharEffect.MAX_DIFFICULTY);
      inventory.add(firstHand);
      firstHand = null;
    }
  }
  
  /**
   * Unequip item from first hand throw it on map
   */
  public Item throwFirstHandWeapon(Party party) {
    if (firstHand != null) {
      activeEffects.removeEffects(CharEffect.TYPE_WEARABLE_FIRSTHAND, CharEffect.MAX_DIFFICULTY);
      Item result = firstHand;
      firstHand = null;
      int index = -1;
      if (getPerks().isPerkActive(Perks.PERK_FAST_THROWER)) {
        index = inventoryFindItemByName(result.getName());
      }
      if (index != -1) {
        equipItemByIndex(index,party);
      } else
      if (secondHand != null && secondHand.getType() == Item.TYPE_ITEM_WEAPON) {
        activeEffects.removeEffects(CharEffect.TYPE_WEARABLE_SECONDHAND, CharEffect.MAX_DIFFICULTY);
        firstHand = secondHand;
        secondHand = null;
        if (firstHand.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
          CharEffect eff = new CharEffect(firstHand.getItemNameInGame(),
               CharEffect.TYPE_WEARABLE_FIRSTHAND,
               CharEffect.DURATION_UNTIL_REMOVED, firstHand.getEffect(),
               firstHand.getEffectAttrOrSkill(), firstHand.getEffectValue(), 10);
          activeEffects.addNewEeffect(eff);
        }
      }
      return result;
    } 
    return null;  
  }
  
  /**
   * Unequip item from second hand and put it back to inventory
   */
  public void unequipSecondHand() {
    if (secondHand != null) {
      activeEffects.removeEffects(CharEffect.TYPE_WEARABLE_SECONDHAND, CharEffect.MAX_DIFFICULTY);
      inventory.add(secondHand);
      secondHand = null;
    }
  }
  
  /**
   * Unequip item from armor and put it back to inventory
   */
  public void unequipArmor() {
    if (armor != null) {
      activeEffects.removeEffects(CharEffect.TYPE_WEARABLE_ARMOR, CharEffect.MAX_DIFFICULTY);
      inventory.add(armor);
      armor = null;
    }
  }
  
  /**
   * Unequip item from amulet and put it back to inventory
   */
  public void unequipAmulet() {
    if (amulet != null) {
      activeEffects.removeEffects(CharEffect.TYPE_WEARABLE_AMULET, CharEffect.MAX_DIFFICULTY);
      inventory.add(amulet);
      amulet = null;
    }
  }
  
  
  /**
   * Unequip item from head gear and put it back to inventory
   */
  public void unequipHeadGear() {
    if (headGear != null) {
      activeEffects.removeEffects(CharEffect.TYPE_WEARABLE_HEADGEAR, CharEffect.MAX_DIFFICULTY);
      inventory.add(headGear);
      headGear = null;
    }
  }
  
  /**
   * Unequip item from boots and put it back to inventory
   */
  public void unequipBoots() {
    if (boots != null) {
      activeEffects.removeEffects(CharEffect.TYPE_WEARABLE_BOOTS, CharEffect.MAX_DIFFICULTY);
      inventory.add(boots);
      boots = null;
    }
  }
  
  /**
   * Unequip item from ring and put it back to inventory
   */
  public void unequipRing() {
    if (ring != null) {
      activeEffects.removeEffects(CharEffect.TYPE_WEARABLE_RING, CharEffect.MAX_DIFFICULTY);
      inventory.add(ring);
      ring = null;
    }
  }

  public int getInventoryItemIndexByReversed(int index) {
    int result= inventorySize()-index-1;
    if (result < 0) {
      result = -1;
    }
    if (result > inventorySize()) {
      result = -1;
    }
    return result;
  }
  
  /**
   * Equip/Use item by Index
   * @param index Item index
   * @return boolean, true if equip/use was successful, false if item cannot be used
   */
  public boolean equipItemByIndex(int index,Party party) {
    boolean result = false;
    Item it = inventoryGetIndex(index);
    if (it != null) {
      // Equipping weapons
      if (it.getType() == Item.TYPE_ITEM_WEAPON) {       
        if (firstHand == null) {
          //Main hand is empty
          firstHand = it;
          if (firstHand.isTwoHandedWeapon() && (secondHand != null)) {
            unequipSecondHand();
          }
          if (firstHand.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
            CharEffect eff = new CharEffect(firstHand.getItemNameInGame(),
                 CharEffect.TYPE_WEARABLE_FIRSTHAND,
                 CharEffect.DURATION_UNTIL_REMOVED, firstHand.getEffect(),
                 firstHand.getEffectAttrOrSkill(), firstHand.getEffectValue(), 10);
            activeEffects.addNewEeffect(eff);
          }
          inventoryRemoveItem(index);
          result = true;
          SoundPlayer.playSoundBySoundName("Weapon");
          party.addLogText(this.getName()+" wields "+it.getItemNameInGame()+".");
        } else {
          // Main hand is not empty
          if (it.isTwoHandedWeapon()) {
            // If weapon is two-handed just unequip both hands
            unequipFirstHand();
            unequipSecondHand();
            firstHand = it;
            if (firstHand.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
              CharEffect eff = new CharEffect(firstHand.getItemNameInGame(),
                   CharEffect.TYPE_WEARABLE_FIRSTHAND,
                   CharEffect.DURATION_UNTIL_REMOVED, firstHand.getEffect(),
                   firstHand.getEffectAttrOrSkill(), firstHand.getEffectValue(), 10);
              activeEffects.addNewEeffect(eff);
            }
            inventoryRemoveItem(index);
            result = true;
            SoundPlayer.playSoundBySoundName("Weapon");
            party.addLogText(this.getName()+" wields "+it.getItemNameInGame()+".");
          } else {
            if (perks.isPerkActive(Perks.PERK_DUAL_WIELDER) &&(!firstHand.isTwoHandedWeapon())) {
             // Is dual wielder
              unequipSecondHand();
              secondHand = it;
              if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
                CharEffect eff = new CharEffect(it.getItemNameInGame(),
                     CharEffect.TYPE_WEARABLE_SECONDHAND,
                     CharEffect.DURATION_UNTIL_REMOVED, it.getEffect(),
                     it.getEffectAttrOrSkill(), it.getEffectValue(), 10);
                activeEffects.addNewEeffect(eff);
              }
              SoundPlayer.playSoundBySoundName("Weapon");
              party.addLogText(this.getName()+" wields "+it.getItemNameInGame()+".");
              inventoryRemoveItem(index);
              result = true;
            } else {
              // Not dual wield so just equipping weapon to main hand
              unequipFirstHand();
              firstHand = it;
              if (firstHand.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
                CharEffect eff = new CharEffect(firstHand.getItemNameInGame(),
                     CharEffect.TYPE_WEARABLE_FIRSTHAND,
                     CharEffect.DURATION_UNTIL_REMOVED, firstHand.getEffect(),
                     firstHand.getEffectAttrOrSkill(), firstHand.getEffectValue(), 10);
                activeEffects.addNewEeffect(eff);
              }
              inventoryRemoveItem(index);
              result = true;
              SoundPlayer.playSoundBySoundName("Weapon");
              party.addLogText(this.getName()+" wields "+it.getItemNameInGame()+".");
            }
          }
        }
      } // End of equipping weapon
      // Equipping shield
      if (it.getType() == Item.TYPE_ITEM_SHIELD) {
        if (perks.isPerkActive(Perks.PERK_SHIELD_PROFIENCY)) {
          if (firstHand != null) {
             if (firstHand.isTwoHandedWeapon()) {
               unequipFirstHand();
             }
          }
          unequipSecondHand();
          secondHand = it;
          if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
            CharEffect eff = new CharEffect(it.getItemNameInGame(),
                 CharEffect.TYPE_WEARABLE_SECONDHAND,
                 CharEffect.DURATION_UNTIL_REMOVED, it.getEffect(),
                 it.getEffectAttrOrSkill(), it.getEffectValue(), 10);
            activeEffects.addNewEeffect(eff);
          }
          inventoryRemoveItem(index);
          SoundPlayer.playSoundBySoundName("Shield");
          party.addLogText(this.getName()+" wields "+it.getItemNameInGame()+".");
          result = true;
        }
      } // End of equipping shield
      if (it.getType() == Item.TYPE_ITEM_LIGHT_DEVICE) {
        if (firstHand != null) {
           if (firstHand.isTwoHandedWeapon()) {
             unequipFirstHand();
           }
        }
        unequipSecondHand();
        secondHand = it;
        if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
          CharEffect eff = new CharEffect(it.getItemNameInGame(),
               CharEffect.TYPE_WEARABLE_SECONDHAND,
               CharEffect.DURATION_UNTIL_REMOVED, it.getEffect(),
               it.getEffectAttrOrSkill(), it.getEffectValue(), 10);
          activeEffects.addNewEeffect(eff);
        }
        inventoryRemoveItem(index);
        party.addLogText(this.getName()+" lights "+it.getItemNameInGame()+".");
        result = true;        
      } // End of equipping light device
      if (it.getType() == Item.TYPE_ITEM_HEALING_KIT) {
        if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
          CharEffect eff = new CharEffect(it.getItemNameInGame(),
               CharEffect.TYPE_ENCHANT,
               it.getEffectLasting(), it.getEffect(),
               it.getEffectAttrOrSkill(), it.getEffectValue(), 10);
          activeEffects.addNewEeffect(eff);
        }
        if (this.luckCheck(false)==CHECK_FAIL) {
          inventoryRemoveItem(index);
        }
        result = true;        
        party.addLogText(this.getName()+" uses "+it.getItemNameInGame()+".");
      } // End of equipping Healing kit
      if (it.getType() == Item.TYPE_ITEM_AMULET) {
          unequipAmulet();
          amulet = it;
          if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
            CharEffect eff = new CharEffect(it.getItemNameInGame(),
                 CharEffect.TYPE_WEARABLE_AMULET,
                 CharEffect.DURATION_UNTIL_REMOVED, it.getEffect(),
                 it.getEffectAttrOrSkill(), it.getEffectValue(), 10);
            activeEffects.addNewEeffect(eff);
          }
          SoundPlayer.playSoundBySoundName("AMULET");
          inventoryRemoveItem(index);
          result = true;
          party.addLogText(this.getName()+" equips "+it.getItemNameInGame()+".");
      } // End of equipping amulet
      if (it.getType() == Item.TYPE_ITEM_ARMOR) {
        unequipArmor();
        armor = it;
        if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
          CharEffect eff = new CharEffect(it.getItemNameInGame(),
               CharEffect.TYPE_WEARABLE_ARMOR,
               CharEffect.DURATION_UNTIL_REMOVED, it.getEffect(),
               it.getEffectAttrOrSkill(), it.getEffectValue(), 10);
          activeEffects.addNewEeffect(eff);
        }
        inventoryRemoveItem(index);
        SoundPlayer.playSoundBySoundName("Armor");
        party.addLogText(this.getName()+" wears "+it.getItemNameInGame()+".");
        result = true;
      } // End of equipping headgear
      // Equipping headgear
      if (it.getType() == Item.TYPE_ITEM_HEADGEAR) {
        unequipHeadGear();
        headGear = it;
        if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
          CharEffect eff = new CharEffect(it.getItemNameInGame(),
               CharEffect.TYPE_WEARABLE_HEADGEAR,
               CharEffect.DURATION_UNTIL_REMOVED, it.getEffect(),
               it.getEffectAttrOrSkill(), it.getEffectValue(), 10);
          activeEffects.addNewEeffect(eff);
        }
        inventoryRemoveItem(index);
        SoundPlayer.playSoundBySoundName("Headgear");
        party.addLogText(this.getName()+" wears "+it.getItemNameInGame()+".");
        result = true;
      } // End of equipping headGear
      // equipping boots
      if (it.getType() == Item.TYPE_ITEM_BOOTS) {
        unequipBoots();
        boots = it;
        if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
          CharEffect eff = new CharEffect(it.getItemNameInGame(),
               CharEffect.TYPE_WEARABLE_BOOTS,
               CharEffect.DURATION_UNTIL_REMOVED, it.getEffect(),
               it.getEffectAttrOrSkill(), it.getEffectValue(), 10);
          activeEffects.addNewEeffect(eff);
        }
        SoundPlayer.playSoundBySoundName("Boots");
        party.addLogText(this.getName()+" wears "+it.getItemNameInGame()+".");
        inventoryRemoveItem(index);
        result = true;
      } // End of equipping boots
      // equipping ring
      if (it.getType() == Item.TYPE_ITEM_RING) {
        unequipRing();
        ring = it;
        if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
          CharEffect eff = new CharEffect(it.getItemNameInGame(),
               CharEffect.TYPE_WEARABLE_RING,
               CharEffect.DURATION_UNTIL_REMOVED, it.getEffect(),
               it.getEffectAttrOrSkill(), it.getEffectValue(), 10);
          activeEffects.addNewEeffect(eff);
        }
        SoundPlayer.playSoundBySoundName("Ring");
        party.addLogText(this.getName()+" wears "+it.getItemNameInGame()+".");
        inventoryRemoveItem(index);
        result = true;
      } // End of equipping ring 
      // Using single shot
      if (it.getType() == Item.TYPE_ITEM_SINGLESHOT) {
        if (it.getEffect() != Item.EFFECT_TYPE_NO_EFFECT) {
          if (it.getEffectValue() >0) {
            CharEffect eff = new CharEffect(it.getItemNameInGame(),
                CharEffect.TYPE_ENCHANT,
                it.getEffectLasting(), it.getEffect(),
                it.getEffectAttrOrSkill(), it.getEffectValue(), 50);
           activeEffects.addNewEeffect(eff);
          } else {
            CharEffect eff = new CharEffect(it.getItemNameInGame(),
                CharEffect.TYPE_POISON,
                it.getEffectLasting(), it.getEffect(),
                it.getEffectAttrOrSkill(), it.getEffectValue(), 50);
           activeEffects.addNewEeffect(eff);
          }
          inventoryRemoveItem(index);
        }
        if (it.getName().startsWith("Scroll of")) {
          if (addSpell(it.getName().replaceFirst("Scroll of ", ""))) {
            inventoryRemoveItem(index);
            SoundPlayer.playSoundBySoundName("Scroll");
            party.addLogText(this.getName()+" reads "+it.getItemNameInGame()+".");
          } 
        }
        String itemName = it.getName().toLowerCase();
        if (itemName.contains("potion")) {
          SoundPlayer.playSoundBySoundName("potion");
          party.addLogText(this.getName()+" drinks "+it.getItemNameInGame()+".");
        }
        if (itemName.contains("beer")) {
          SoundPlayer.playSoundBySoundName("potion");
          party.addLogText(this.getName()+" drinks "+it.getItemNameInGame()+".");
        }
        if (itemName.equals("cabbage") || itemName.equals("apple") ||
            itemName.equals("carrot") || itemName.equals("fresh salad") ||
            itemName.equals("goat cheese") || itemName.equals("bread") ||
            itemName.equals("roasted meat") || itemName.equals("honeycomb") ||
            itemName.equals("raw fish") || itemName.equals("cooked fish")){
          SoundPlayer.playSoundBySoundName("eat");
          party.addLogText(this.getName()+" eats "+it.getItemNameInGame()+".");
        }
        result = true;
      } // End of single shot item
      if (it.getType() == Item.TYPE_ITEM_TOME) {
        if (it.getName().equalsIgnoreCase("Ancient tome")) {
          inventoryRemoveItem(index);
          SoundPlayer.playSoundBySoundName("Scroll");
          party.addLogText(this.getName()+" reads "+it.getItemNameInGame()+".");
          party.setStoryVariable(6,5, "Ancient tome has been read.");
          addSpell(SpellFactory.SPELL_NAME_POWER_OF_DESTRUCTION);
          addSpell(SpellFactory.SPELL_NAME_POWER_OF_HEALING);
          addSpell(SpellFactory.SPELL_NAME_POWER_OF_MIGHTY);
          result = true;
        } else if (it.getName().equalsIgnoreCase("Tome of Death")) {
          inventoryRemoveItem(index);
          SoundPlayer.playSoundBySoundName("Scroll");
          party.addLogText(this.getName()+" reads "+it.getItemNameInGame()+".");
          CharEffect eff = new CharEffect(it.getItemNameInGame(),
              CharEffect.TYPE_CURSE,
              it.getEffectLasting(), it.getEffect(),
              it.getEffectAttrOrSkill(), it.getEffectValue(), 50);
          activeEffects.addNewEeffect(eff);
          result = true;
        } else {
          if (it.getName().startsWith("Tome of")) {
            String itemName = it.getName().replaceFirst("Tome of ", "");
            int attr = -1;
            if (itemName.equalsIgnoreCase("Strength")) {
              attr = Character.ATTRIBUTE_STRENGTH;
            }
            if (itemName.equalsIgnoreCase("Agility")) {
              attr = Character.ATTRIBUTE_AGILITY;
            }
            if (itemName.equalsIgnoreCase("Endurance")) {
              attr = Character.ATTRIBUTE_ENDURANCE;
            }
            if (itemName.equalsIgnoreCase("Intelligence")) {
              attr = Character.ATTRIBUTE_INTELLIGENCE;
            }
            if (itemName.equalsIgnoreCase("Wisdom")) {
              attr = Character.ATTRIBUTE_WISDOM;
            }
            if (itemName.equalsIgnoreCase("Charisma")) {
              attr = Character.ATTRIBUTE_CHARISMA;
            }
            if (itemName.equalsIgnoreCase("Luck")) {
              attr = Character.ATTRIBUTE_LUCK;
            }
            if (attr != -1 && getAttribute(attr)<10) {
              setAttribute(attr, getAttribute(attr)+1);
              inventoryRemoveItem(index);
              SoundPlayer.playSoundBySoundName("Scroll");
              party.addLogText(this.getName()+" reads "+it.getItemNameInGame()+".");
              result = true;
            }
          }
        }
      }
    } 
    return result;
  }
  
  /**
   * Remove item from inventory by index
   * @param index
   * @return boolean true if removed succesfully, false if not found by index
   */
  public boolean inventoryRemoveItem(int index) {
    if ((index >= 0) && (index < inventory.size())) {
      inventory.remove(index);
      return true;
    } else {
      return false;
    }
  }
  
  public int inventorySize() {
    return inventory.size();
  }
  
  public Item inventoryGetFirstHand() {
    return firstHand;
  }
  public Item inventoryGetSecondHand() {
    return secondHand;
  }
  public Item inventoryGetArmor() {
    return armor;
  }
  public Item inventoryGetHeadGear() {
    return headGear;
  }
  public Item inventoryGetBoots() {
    return boots;
  }
  public Item inventoryGetAmulet() {
    return amulet;
  }
  public Item inventoryGetRing() {
    return ring;
  }
  
  /**
   * Calculate damage for Weapon
   * @param target Item as weapon
   * @return Attack
   */
  private Attack calculateDamageForWeapon(Item target) {
    int bonusDamage = getBonusDamageFromStrength();
    int minDamage = 0;
    int maxDamage = 0;
    Attack result = new Attack();
    if (target != null) {
     result.setAttackType(target.getAttackType()); 
     if (target.getWeaponSkill() == SKILL_MELEE) {
       // Calculate bonus damage for melee weapons from perks
       if (target.isTwoHandedWeapon() &&(bonusDamage > 0)) {
         if (this.perks.isPerkActive(Perks.PERK_MASTER_TWO_HANDED_WEAPON_FIGHTER)) {
           bonusDamage = bonusDamage*2; 
         } else {
         bonusDamage = bonusDamage/2+bonusDamage;
         }
         if (this.perks.isPerkActive(Perks.PERK_TWO_HANDED_WEAPON_FIGHTER)) {
           bonusDamage = bonusDamage+1;
         }
         if (this.perks.isPerkActive(Perks.PERK_IMPROVED_TWO_HANDED_WEAPON_FIGHTER)) {
           bonusDamage = bonusDamage+1;
         }
       }
       if (target.isThrowableWeapon()) {
         result.setThrowingAttackPossible(true);
       }
     }

     if (target.getEffect()==CharEffect.EFFECT_DRAIN_HEALTH) {
       result.setDrainHealth(true);
     }
     if (target.getEffect()==CharEffect.EFFECT_DRAIN_STAMINA) {
       result.setDrainStamina(true);
     }
     if (target.getEffect()==CharEffect.EFFECT_DRAIN_VIGOR) {
       result.setDrainVigor(true);
     }
     if (target.getEffect()==CharEffect.EFFECT_SLAYER) {
       result.setSlayer(true);
     }
     if (target.getWeaponSkill() == SKILL_RANGED_WEAPONS) {
       result.setRangedAttack(true);
       minDamage = target.getMinDamage()+getBonusDamageFromAgility();
       maxDamage = target.getMaxDamage()+getBonusDamageFromAgility();
     } else {
       minDamage = target.getMinDamage()+bonusDamage;
       maxDamage = target.getMaxDamage()+bonusDamage;
     }
     if (target.isBluntWeapon()) {
       minDamage = minDamage /2+bonusDamage/2;
       maxDamage = maxDamage /2+bonusDamage/2;
       if (minDamage == 0) {
         minDamage = 1;
       }
       if (maxDamage < minDamage) {
         maxDamage = minDamage;
       }
       if (this.perks.isPerkActive(Perks.PERK_SMASHER)) {
         minDamage = minDamage+1;
         maxDamage = maxDamage+1;
       }
       if (this.perks.isPerkActive(Perks.PERK_IMPROVED_SMASHER)) {
         minDamage = minDamage+1;
         maxDamage = maxDamage+1;
       }
       if (this.perks.isPerkActive(Perks.PERK_MASTER_SMASHER)) {
         minDamage = minDamage+1;
         maxDamage = maxDamage+1;
       }
       result.setMinStaminaDamage(minDamage);
       result.setMaxStaminaDamage(maxDamage);
     }
     result.setMinLethalDamage(minDamage+target.getMinMagicDamage());
     result.setMaxLethalDamage(maxDamage+target.getMaxMagicDamage());
     result.setPiercing(target.getPiercingPower());
     int critMult = target.getCriticalMultiplier();
     if ((target.getWeaponSkill() != SKILL_RANGED_WEAPONS) &&
         (this.perks.isPerkActive(Perks.PERK_ASSASIN_STRIKE))){
       critMult++;
     }
     result.setCriticalMultiplier(critMult);
     int staminaCost = 1;
     if (target.getWeight() < 25) {
       staminaCost = 1;
     } else if (target.getWeight() < 55) {
       staminaCost = 2;
     } else if (target.getWeight() < 70) {
       staminaCost = 3;
     } else if (target.getWeight() < 80) {
       staminaCost = 4;
     } else {
       staminaCost = 5;
     }
     if (target.isCursed()) {
       staminaCost++;
     }
     result.setStaminaCost(staminaCost);
    } else {
      // Unarmed damage, which is calculate using perks
      if (perks.isPerkActive(Perks.PERK_KUNG_FU_MASTER)) {
        result.setAttackType(AttackType.BLUNT);
        minDamage = 6+bonusDamage*2;
        maxDamage = 12+bonusDamage*2;
        if (minDamage < 1) {
          minDamage = 1;
        }
        if (maxDamage < 1) {
          maxDamage = 1;
        }
        minDamage = minDamage /2;
        maxDamage = maxDamage /2;
        if (minDamage == 0) {
          minDamage = 1;
        }
        if (maxDamage < minDamage) {
          maxDamage = minDamage;
        }
        if (this.perks.isPerkActive(Perks.PERK_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        if (this.perks.isPerkActive(Perks.PERK_IMPROVED_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        if (this.perks.isPerkActive(Perks.PERK_MASTER_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        result.setMinStaminaDamage(minDamage);
        result.setMaxStaminaDamage(maxDamage);
        result.setMinLethalDamage(minDamage);
        result.setMaxLethalDamage(maxDamage);
        result.setPiercing(2);        
      } else if (perks.isPerkActive(Perks.PERK_QI_STRIKE)) {
        result.setAttackType(AttackType.BLUNT);
        minDamage = 4+bonusDamage;
        maxDamage = 10+bonusDamage;
        if (minDamage < 1) {
          minDamage = 1;
        }
        if (maxDamage < 1) {
          maxDamage = 1;
        }
        minDamage = minDamage /2;
        maxDamage = maxDamage /2;
        if (minDamage == 0) {
          minDamage = 1;
        }
        if (maxDamage < minDamage) {
          maxDamage = minDamage;
        }
        if (this.perks.isPerkActive(Perks.PERK_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        if (this.perks.isPerkActive(Perks.PERK_IMPROVED_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        if (this.perks.isPerkActive(Perks.PERK_MASTER_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        result.setMinStaminaDamage(minDamage);
        result.setMaxStaminaDamage(maxDamage);
        result.setMinLethalDamage(minDamage);
        result.setMaxLethalDamage(maxDamage);
        result.setPiercing(1);        
      } else if (perks.isPerkActive(Perks.PERK_QI_FISTS)) {
        result.setAttackType(AttackType.BLUNT);
        minDamage = 2+bonusDamage*2;
        maxDamage = 8+bonusDamage*2;
        if (minDamage < 1) {
          minDamage = 1;
        }
        if (maxDamage < 1) {
          maxDamage = 1;
        }
        minDamage = minDamage /2;
        maxDamage = maxDamage /2;
        if (minDamage == 0) {
          minDamage = 1;
        }
        if (maxDamage < minDamage) {
          maxDamage = minDamage;
        }
        if (this.perks.isPerkActive(Perks.PERK_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        if (this.perks.isPerkActive(Perks.PERK_IMPROVED_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        if (this.perks.isPerkActive(Perks.PERK_MASTER_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        result.setMinStaminaDamage(minDamage);
        result.setMaxStaminaDamage(maxDamage);
        result.setMinLethalDamage(minDamage);
        result.setMaxLethalDamage(maxDamage);
      }else if (perks.isPerkActive(Perks.PERK_LETHAL_FISTS)) {
        result.setAttackType(AttackType.BLUNT);
        minDamage = 2+bonusDamage*2;
        maxDamage = 6+bonusDamage*2;
        if (minDamage < 1) {
          minDamage = 1;
        }
        if (maxDamage < 1) {
          maxDamage = 1;
        }
        minDamage = minDamage /2;
        maxDamage = maxDamage /2;
        if (minDamage == 0) {
          minDamage = 1;
        }
        if (maxDamage < minDamage) {
          maxDamage = minDamage;
        }
        if (this.perks.isPerkActive(Perks.PERK_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        if (this.perks.isPerkActive(Perks.PERK_IMPROVED_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        if (this.perks.isPerkActive(Perks.PERK_MASTER_SMASHER)) {
          minDamage = minDamage+1;
          maxDamage = maxDamage+1;
        }
        result.setMinStaminaDamage(minDamage);
        result.setMaxStaminaDamage(maxDamage);
        result.setMinLethalDamage(minDamage);
        result.setMaxLethalDamage(maxDamage);
      } else {
        result.setAttackType(AttackType.NONLETHAL);
        minDamage = 1+bonusDamage;
        maxDamage = 3+bonusDamage;
        if (minDamage < 1) {
          minDamage = 1;
        }
        if (maxDamage < 1) {
          maxDamage = 1;
        }
        result.setMinStaminaDamage(minDamage);
        result.setMaxStaminaDamage(maxDamage);        
      }
      if (this.perks.isPerkActive(Perks.PERK_ASSASIN_STRIKE)) {
        result.setCriticalMultiplier(result.getCriticalMultiplier()+1);
      }
    }
    return result;
  }
  
  /**
   * Get extra stamina cost from carrying load
   * @return int;
   */
  public int getStaminaCostFromLoad() {
    int result = 0;
    if (isOverLoad()) {
      result = 6;
    } else if (isHeavyLoad()) {
      result = 3;
    } else if (isMediumLoad()) {
      result = 2;
    } else if (isLightLoad()) {
      result = 1;
    }
    return result;
  }
  
  /**
   * Get first attack
   * @return always returns Attack
   */
  public Attack getFirstAttack() {
    Attack result = new Attack();
    if (firstHand != null) {
      result = calculateDamageForWeapon(firstHand);
      result.setAttackBonus(getEffectiveSkill(firstHand.getWeaponSkill()));
    } else {
      result = calculateDamageForWeapon(null);      
      result.setAttackBonus(getEffectiveSkill(SKILL_UNARMED));
    }
    int staminaCost = result.getStaminaCost();
    result.setStaminaCost(staminaCost +getStaminaCostFromLoad());
    if (perks.isPerkActive(Perks.PERK_ATHLETIC)) {
      staminaCost = result.getStaminaCost();
      staminaCost = staminaCost -2;
      if (staminaCost < 1) {
        staminaCost = 1;
      }
      result.setStaminaCost(staminaCost);
    }
    return result;
  }
  /**
   * Get first attack as throwable
   * @return always returns Attack
   */
  public Attack getFirstAttackThrowable() {
    Attack result = new Attack();
    if (firstHand != null) {
      result = calculateDamageForWeapon(firstHand);
      if (firstHand.isThrowableWeapon()) {
        result.setAttackBonus(getEffectiveSkill(Character.SKILL_RANGED_WEAPONS));
      } else {
        result.setAttackBonus(0);
      }
    } else {
      result.setAttackBonus(0);
    }
    int staminaCost = result.getStaminaCost();
    result.setStaminaCost(staminaCost +getStaminaCostFromLoad());
    if (perks.isPerkActive(Perks.PERK_ATHLETIC)) {
      staminaCost = result.getStaminaCost();
      staminaCost = staminaCost -2;
      if (staminaCost < 1) {
        staminaCost = 1;
      }
      result.setStaminaCost(staminaCost);
    }
    return result;
  }

  /**
   * Get possible second attack
   * @return if character has only one attack then null is returned otherwise
   * Attack
   */
  public Attack getSecondAttack() {
    if (getNumberOfAttacks() == 2) {
      Attack result = new Attack();
      if (secondHand != null) {
        result = calculateDamageForWeapon(secondHand);
        if (secondHand.getWeaponSkill() == SKILL_UNARMED) {
          result.setAttackBonus(getEffectiveSkill(secondHand.getWeaponSkill()));
        }
        if ((secondHand.getWeaponSkill() == SKILL_MELEE) &&
           (secondHand.getType() == Item.TYPE_ITEM_WEAPON)) {
          int skill = getEffectiveSkill(secondHand.getWeaponSkill());
          if (perks.isPerkActive(Perks.PERK_MASTER_DUAL_WIELDER)) {
            skill = skill*9/10;
            result.setAttackBonus(skill);
          } else if (perks.isPerkActive(Perks.PERK_IMPROVED_DUAL_WIELDER)) {
            skill = skill*3/4;
            result.setAttackBonus(skill);
          } else if (perks.isPerkActive(Perks.PERK_DUAL_WIELDER)) {
            skill = (skill/2);
            result.setAttackBonus(skill);
          }
        }
        if ((secondHand.getWeaponSkill() == SKILL_MELEE) &&
            (secondHand.getType() == Item.TYPE_ITEM_SHIELD)) {
           int skill = getEffectiveSkill(secondHand.getWeaponSkill());
           if (perks.isPerkActive(Perks.PERK_MASTER_SHIELD_FIGHTER)) {
             skill = skill*9/10;
             result.setAttackBonus(skill);
           } else if (perks.isPerkActive(Perks.PERK_IMPROVED_SHIELD_FIGHTER)) {
             skill = skill*3/4;
             result.setAttackBonus(skill);
           } else if (perks.isPerkActive(Perks.PERK_SHIELD_FIGHTER)) {
             skill = (skill/2);
             result.setAttackBonus(skill);
           }
         }
        if (secondHand.getWeaponSkill() == SKILL_RANGED_WEAPONS) {
          int skill = getEffectiveSkill(secondHand.getWeaponSkill());
          if (perks.isPerkActive(Perks.PERK_MASTER_RAPID_SHOT)) {
            skill = skill*9/10;
            result.setAttackBonus(skill);
          } else if (perks.isPerkActive(Perks.PERK_IMPROVED_RAPID_SHOT)) {
            skill = skill*3/4;
            result.setAttackBonus(skill);
          } else if (perks.isPerkActive(Perks.PERK_RAPID_SHOT)) {
            skill = (skill/2);
            result.setAttackBonus(skill);
          }
        }
      } else {
        if (firstHand != null) {
          if ((firstHand.getWeaponSkill()== SKILL_UNARMED) &&
          (firstHand.isTwoHandedWeapon()) &&    
          (perks.isPerkActive(Perks.PERK_DUAL_WIELDER))) {
            result = calculateDamageForWeapon(firstHand);
            int skill = getEffectiveSkill(firstHand.getWeaponSkill());
            if (perks.isPerkActive(Perks.PERK_MASTER_DUAL_WIELDER)) {
              skill = skill*9/10;
              result.setAttackBonus(skill);
            } else if (perks.isPerkActive(Perks.PERK_IMPROVED_DUAL_WIELDER)) {
              skill = skill*3/4;
              result.setAttackBonus(skill);
            } else if (perks.isPerkActive(Perks.PERK_DUAL_WIELDER)) {
              skill = (skill/2);
              result.setAttackBonus(skill);
            }

          }
          if ((firstHand.getWeaponSkill()== SKILL_UNARMED) &&
          (!firstHand.isTwoHandedWeapon()) &&    
          (perks.isPerkActive(Perks.PERK_QI_FISTS))) {
            result = calculateDamageForWeapon(null);
            result.setAttackBonus(getEffectiveSkill(SKILL_UNARMED));

          }
          if ((firstHand.getWeaponSkill()== SKILL_RANGED_WEAPONS) &&
              (perks.isPerkActive(Perks.PERK_RAPID_SHOT))) {
            result = calculateDamageForWeapon(firstHand);
            int skill = getEffectiveSkill(firstHand.getWeaponSkill());
            if (perks.isPerkActive(Perks.PERK_MASTER_RAPID_SHOT)) {
              skill = skill*9/10;
              result.setAttackBonus(skill);
            } else if (perks.isPerkActive(Perks.PERK_IMPROVED_RAPID_SHOT)) {
              skill = skill*3/4;
              result.setAttackBonus(skill);
            } else if (perks.isPerkActive(Perks.PERK_RAPID_SHOT)) {
              skill = (skill/2);
              result.setAttackBonus(skill);
            }

          }
        } else {
          result = calculateDamageForWeapon(null);      
          result.setAttackBonus(getEffectiveSkill(SKILL_UNARMED));
        }
      }
      int staminaCost = result.getStaminaCost();
      result.setStaminaCost(staminaCost +getStaminaCostFromLoad());
      if (perks.isPerkActive(Perks.PERK_ATHLETIC)) {
        staminaCost = result.getStaminaCost();
        staminaCost = staminaCost -2;
        if (staminaCost < 1) {
          staminaCost = 1;
        }
        result.setStaminaCost(staminaCost);
      }
      return result;
    } else {
      return null;
    }
  }
  
  /**
   * Get Character's defense
   * @return Defense never null
   */
  public Defense getDefense() {
    Defense result = new Defense();
    int dodge = getEffectiveSkill(SKILL_DODGING);
    int shieldBonus=0;
    int armorValue = 0;
    int critArmor = 0;    
    if (armor != null) {
      dodge = dodge +armor.getDefensiveValue();
      if (dodge >armor.getMaxDodgeValue() ) {
        dodge = armor.getMaxDodgeValue();
      }
      armorValue = armor.getArmorValue()+armor.getMagicalArmorValue();        
      critArmor = critArmor+armor.getCriticalArmorValue();      
    }
    if (perks.isPerkActive(Perks.PERK_QI_BODY)) {
      armorValue = armorValue +2;
    }
    if (perks.isPerkActive(Perks.PERK_STRONG_BONES)) {
      armorValue = armorValue +1;
      critArmor = critArmor +1;
    }
    armorValue = armorValue +activeEffects.getTotalEffect(CharEffect.EFFECT_ON_ARMOR,0);  
    if (secondHand != null) { 
      if (secondHand.getType() == Item.TYPE_ITEM_SHIELD) {
        if (dodge > secondHand.getMaxDodgeValue() ) {
          dodge = secondHand.getMaxDodgeValue();
        }
        armorValue = armorValue +secondHand.getArmorValue()+secondHand.getMagicalArmorValue();
        critArmor = critArmor+secondHand.getCriticalArmorValue();
        shieldBonus = secondHand.getDefensiveValue()+secondHand.getMagicalDefensiveValue();
      }
    }
    if (headGear != null) {
      critArmor = critArmor+headGear.getCriticalArmorValue();
      shieldBonus = shieldBonus+headGear.getDefensiveValue()+headGear.getMagicalDefensiveValue();
      armorValue = armorValue+headGear.getArmorValue()+headGear.getMagicalArmorValue();
    }
    if (ring != null) {
      critArmor = critArmor+ring.getCriticalArmorValue();
      shieldBonus = shieldBonus+ring.getDefensiveValue()+ring.getMagicalDefensiveValue();
      armorValue = armorValue+ring.getArmorValue()+ring.getMagicalArmorValue();
    }
    if (amulet != null) {
      critArmor = critArmor+amulet.getCriticalArmorValue();
      shieldBonus = shieldBonus+amulet.getDefensiveValue()+amulet.getMagicalDefensiveValue();
      armorValue = armorValue+amulet.getArmorValue()+amulet.getMagicalArmorValue();
    }
    if (boots != null) {
      critArmor = critArmor+boots.getCriticalArmorValue();
      shieldBonus = shieldBonus+boots.getDefensiveValue()+boots.getMagicalDefensiveValue();
      armorValue = armorValue+boots.getArmorValue()+boots.getMagicalArmorValue();
    }
    if (armorValue < 0) {
      armorValue = 0;
    }
    result.setArmorRating(armorValue);
    result.setCriticalArmorRating(armorValue+critArmor);
    result.setDefense(50+dodge+shieldBonus);
    result.setWillPower(this.getEffectiveWillPower());
    return result;
  }

  /**
   * Get current attack skill which is used,
   * used for determing boost spell
   * @return SKILL
   */
  public int getAttackSkill() {
    int result = SKILL_UNARMED;
    if (firstHand != null) {
      result = firstHand.getWeaponSkill();
    }
    return result;
  }
  
  /**
   * Get number of attacks character has
   * These depend on weapons and perks.
   * @return 1 or 2
   */
  public int getNumberOfAttacks() {
    int result = 1;
    if ((firstHand != null) && (secondHand != null)) {
      if ((secondHand.getType() == Item.TYPE_ITEM_WEAPON) && 
         (perks.isPerkActive(Perks.PERK_DUAL_WIELDER))) {
        result = 2;
      }
      if ((secondHand.getType() == Item.TYPE_ITEM_SHIELD) && 
         (perks.isPerkActive(Perks.PERK_SHIELD_FIGHTER))) {
        result = 2;
      }
    }
    if ((firstHand != null) && (secondHand == null)) {
      if ((firstHand.getWeaponSkill() == Character.SKILL_UNARMED) &&
         (firstHand.isTwoHandedWeapon()) && 
         (perks.isPerkActive(Perks.PERK_DUAL_WIELDER))) {
        result = 2;
      }
      if ((firstHand.getWeaponSkill() == Character.SKILL_UNARMED) &&
          (!firstHand.isTwoHandedWeapon()) && 
          (perks.isPerkActive(Perks.PERK_QI_FISTS))) {
         result = 2;
       }
    }
    if ((firstHand == null) && (secondHand == null)) {
      if (perks.isPerkActive(Perks.PERK_QI_FISTS)) {
        result = 2;
      }
    }
    if ((firstHand != null) && (secondHand == null)) {
      if ((firstHand.getWeaponSkill() == Character.SKILL_RANGED_WEAPONS) &&
         (firstHand.isTwoHandedWeapon()) && 
         (perks.isPerkActive(Perks.PERK_RAPID_SHOT))) {
        result = 2;
      }
    }
    return result;
  }
  
  /**
   * Get item by index from inventory
   * @param index
   * @return Item or null if not found
   */
  public Item inventoryGetIndex(int index) {
    if ((index < inventory.size()) && (index >=0)) {
      return inventory.get(index);
    } else {
      return null;
    }
  }
    
  /**
   * Find item by name from the inventory
   * @param name ItemName
   * @return int, index number if found, otherwise -1
   */
  public int inventoryFindItemByName(String name) {
    int result = -1;
    for (int i=0;i<inventorySize();i++) {
      Item item = inventoryGetIndex(i);
      if (item != null) {
        if (item.getName().equalsIgnoreCase(name)) {
          return i;
        }
      }
    }
    return result;
  }
  
  /**
   * Detect magic on items or Identify on items. This can used when
   * detect magic or identify is casted.
   * @param identify booolean, true if identify
   */
  public void inventoryDetectOrIdentify(boolean identify) {
    for (int i=0;i<inventorySize();i++) {
      Item item = inventoryGetIndex(i);
      if (item != null) {
        if ((item.isMagical()) && (item.getItemStatus()!=Item.IDENTIFIED_STATUS_KNOWN)){          
          if (item.isCursed()) {
            item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL_BUT_CURSED);
          } else {
            item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL);
          }
          if (identify) {
            item.setItemStatus(Item.IDENTIFIED_STATUS_KNOWN);
          }
        }
      }
    }
    
    Item item = firstHand;
    if (item != null) {
      if ((item.isMagical()) && (item.getItemStatus()!=Item.IDENTIFIED_STATUS_KNOWN)){          
        if (item.isCursed()) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL_BUT_CURSED);
        } else {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL);
        }
        if (identify) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_KNOWN);
        }
      }
    }
    item = secondHand;
    if (item != null) {
      if ((item.isMagical()) && (item.getItemStatus()!=Item.IDENTIFIED_STATUS_KNOWN)){          
        if (item.isCursed()) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL_BUT_CURSED);
        } else {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL);
        }
        if (identify) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_KNOWN);
        }
      }
    }
    item = amulet;
    if (item != null) {
      if ((item.isMagical()) && (item.getItemStatus()!=Item.IDENTIFIED_STATUS_KNOWN)){          
        if (item.isCursed()) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL_BUT_CURSED);
        } else {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL);
        }
        if (identify) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_KNOWN);
        }
      }
    }
    item = armor;
    if (item != null) {
      if ((item.isMagical()) && (item.getItemStatus()!=Item.IDENTIFIED_STATUS_KNOWN)){          
        if (item.isCursed()) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL_BUT_CURSED);
        } else {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL);
        }
        if (identify) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_KNOWN);
        }
      }
    }
    item = boots;
    if (item != null) {
      if ((item.isMagical()) && (item.getItemStatus()!=Item.IDENTIFIED_STATUS_KNOWN)){          
        if (item.isCursed()) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL_BUT_CURSED);
        } else {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL);
        }
        if (identify) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_KNOWN);
        }
      }
    }
    item = headGear;
    if (item != null) {
      if ((item.isMagical()) && (item.getItemStatus()!=Item.IDENTIFIED_STATUS_KNOWN)){          
        if (item.isCursed()) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL_BUT_CURSED);
        } else {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL);
        }
        if (identify) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_KNOWN);
        }
      }
    }
    item = ring;
    if (item != null) {
      if ((item.isMagical()) && (item.getItemStatus()!=Item.IDENTIFIED_STATUS_KNOWN)){          
        if (item.isCursed()) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL_BUT_CURSED);
        } else {
          item.setItemStatus(Item.IDENTIFIED_STATUS_MAGICAL);
        }
        if (identify) {
          item.setItemStatus(Item.IDENTIFIED_STATUS_KNOWN);
        }
      }
    }
    
  }
  
  /* PUBLIC STATIC METHODS */
  
  /**
   * Get Attribute name
   * @param i index
   * @return String
   */
  public static String getAttributeName(int i) {
    String result = "";
    switch (i) {
    case ATTRIBUTE_AGILITY: result = "Agility"; break;
    case ATTRIBUTE_CHARISMA: result = "Charisma"; break;
    case ATTRIBUTE_ENDURANCE: result = "Endurance"; break;
    case ATTRIBUTE_INTELLIGENCE: result = "Intelligence"; break;
    case ATTRIBUTE_LUCK: result = "Luck"; break;
    case ATTRIBUTE_STRENGTH: result = "Strength"; break;
    case ATTRIBUTE_WISDOM: result = "Wisdom"; break;
    }
    return result;
  }
  
  /**
   * Get Skill name
   * @param i index
   * @return String
   */
  public static String getSkillName(int i) {
    String result ="";
    switch (i) {
    case SKILL_BARTERING: result = "Bartering"; break;
    case SKILL_DIPLOMACY: result = "Diplomacy"; break;
    case SKILL_DODGING: result = "Dodging"; break;
    case SKILL_LOCK_PICKING: result = "Lock picking"; break;
    case SKILL_MELEE: result = "Melee combat"; break;
    case SKILL_QI_MAGIC: result = "Qi magic"; break;
    case SKILL_RANGED_WEAPONS: result = "Ranged combat"; break;
    case SKILL_SORCERY: result = "Sorcery"; break;
    case SKILL_UNARMED: result = "Martial arts"; break;
    case SKILL_WIZARDRY: result = "Wizardy"; break;
    }
    return result;
  }

  public void setFaceNumber(int faceNumber) {
    this.faceNumber = faceNumber;
  }

  public int getFaceNumber() {
    return faceNumber;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public int getMoney() {
    return money;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    if (currentTaskIndex != -1) {
      CharTask task = getTaskByIndex(currentTaskIndex);
      if (task != null) {
        if ((!task.getDescription().isEmpty()) &&(!task.getDescription().equals(" "))) {
          String desc[] = task.getDescription().split("#");
          if (desc.length > 1) {
            if (!desc[0].isEmpty()) {
              return desc[0];
            }
          } else {
            return task.getDescription();
          }
        }
      }
    }
    return description;
  }
  
  /**
   * Retun what character wears as string
   * @return String
   */
  public String getWearings() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    boolean hadWeapons=false;
    boolean hadArmor=false;
    if (firstHand != null) {
      if (firstHand.isDroppable()) {
        sb.append(" uses ");
        sb.append(firstHand.getItemNameInGame());
        hadWeapons = true;
      }
    }
    if (secondHand != null) {
      if (secondHand.isDroppable()) {
        if ((firstHand != null) && (firstHand.isDroppable())) {
          sb.append(" and ");
        } else {
          sb.append(" uses ");
        }
        sb.append(secondHand.getItemNameInGame());
        hadWeapons = true;
      }
    }
    if ((armor != null) && (armor.isDroppable())) {
      if (hadWeapons) {
        sb.append(" and wears ");        
      } else {
        sb.append(" wears ");
      }
      sb.append(armor.getItemNameInGame());
      hadArmor = true;
    }
    if ((!hadWeapons) && (!hadArmor)) {
      sb.append(" is not carrying anything");
    }
    if (perks.isPerkActive(Perks.PERK_DARK_VISION)) {
      sb.append(" and ");
      sb.append(getName());
      sb.append(" has dark vision");
    }
    sb.append(".");
    return sb.toString();
  }

  /**
   * Retun what character health.
   * @return String
   */
  public String getHealthAsString() {
    int percent = getCurrentHP()*100/getMaxHP();
    if (percent == 100) {
      return "Unharmed";
    }
    if (percent > 74) {
      return "Slightly wounded";
    }
    if (percent > 50) {
      return "Wounded";
    }
    if (percent > 24) {
      return "Seriously wounded";
    }
    return "Near death";
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public int getExperience() {
    return experience;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }
    
  public int getCurrentHP() {
    return currentHP;
  }

  public void setCurrentHP(int currentHP) {
    if (currentHP < getMaxHP()) {
      this.currentHP = currentHP;
    } else {
      this.currentHP = getMaxHP();
    }
  }

  public int getCurrentSP() {
    return currentSP;
  }
  
  public void setCurrentSP(int currentSP) {
    if (currentSP <= getMaxStamina()) {
      this.currentSP = currentSP;
    } else {
      this.currentSP = getMaxStamina();
    }
  }

  public void saveCharacter(DataOutputStream os) throws IOException {
    // First attributes
    StringBuilder sb = new StringBuilder(64);
    for (int i=0;i<MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      os.write(attributes[i]);
      sb.append(attributes[i]);
    }
    // Second skills
    for (int i=0;i<MAX_NUMBERS_OF_SKILL;i++) {
      os.write(skills[i]);
      sb.append(skills[i]);
    }
    // Name written, 4 octet length of unicode characters and then written
    // with characters
    StreamUtilities.writeString(os, name);
    sb.append(name);
    // write coordinates
    os.writeInt(getX());
    os.writeInt(getY());
    sb.append(getX());
    sb.append(getY());
    // write hostility Level
    os.writeByte(hostilityLevel);
    // write type
    os.writeByte(getType());
    // write tileoffset
    os.writeInt(getTileOffset());
    // Write perks
    perks.writePerks(os);
    // Write effects
    activeEffects.writeActiveEffects(os);
    // Write face number
    os.writeInt(faceNumber);
    // Write money
    os.writeInt(money);
    sb.append(money);
    // Write long name
    StreamUtilities.writeString(os, longName);
    // Write description
    StreamUtilities.writeString(os, description);
    // Write inventory
    os.writeInt(inventorySize());
    for (int i=0;i<inventorySize();i++) {
      Item it = inventoryGetIndex(i);
      it.writeItem(os);
    }
    if (firstHand != null) {
      os.writeByte(1);
      firstHand.writeItem(os);
    } else {
      os.writeByte(0);      
    }
    if (secondHand != null) {
      os.writeByte(1);
      secondHand.writeItem(os);
    } else {
      os.writeByte(0);      
    }
    if (armor != null) {
      os.writeByte(1);
      armor.writeItem(os);
    } else {
      os.writeByte(0);      
    }
    if (headGear != null) {
      os.writeByte(1);
      headGear.writeItem(os);
    } else {
      os.writeByte(0);      
    }
    if (boots != null) {
      os.writeByte(1);
      boots.writeItem(os);
    } else {
      os.writeByte(0);      
    }
    if (amulet != null) {
      os.writeByte(1);
      amulet.writeItem(os);
    } else {
      os.writeByte(0);      
    }
    if (ring != null) {
      os.writeByte(1);
      ring.writeItem(os);
    } else {
      os.writeByte(0);      
    }
    // Write experience and level
    os.writeInt(experience);
    sb.append(experience);
    os.writeInt(level);
    sb.append(level);
    // Writing HP and SP
    os.writeInt(currentHP);
    sb.append(currentHP);
    os.writeInt(currentSP);
    sb.append(currentSP);
    // Writing spell list
    os.writeInt(spellList.size());
    for (int i = 0; i<spellList.size();i++) {
      StreamUtilities.writeString(os, spellList.get(i));
      sb.append(spellList.get(i));
    }
    // Write task list
    os.writeInt(taskList.size());
    for (int i = 0; i<taskList.size();i++) {
      CharTask task = taskList.get(i);
      task.writeTask(os);
    }
    // Write task index
    os.writeByte(currentTaskIndex);
    // And finally write MD5 HASH for preveting tampering
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IOException("Saving character failed!");
    }
    byte[] data = sb.toString().getBytes("UTF-8");
    os.write(digest.digest(data));
  }
  
  public void loadCharacter(DataInputStream is) throws IOException {
    // Used for hashing
    StringBuilder sb = new StringBuilder(64);
    // First attributes    
    for (int i=0;i<MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      attributes[i] = is.read();
      sb.append(attributes[i]);
    }
    // Second skills
    for (int i=0;i<MAX_NUMBERS_OF_SKILL;i++) {
      skills[i] = is.read();
      sb.append(skills[i]);
    }
    // Name written, 4 octet length of unicode characters and then written
    // with characters
    name = StreamUtilities.readString(is);
    sb.append(name);
    // read coordinates
    int tmpx = is.readInt();
    int tmpy = is.readInt();    
    sb.append(tmpx);
    sb.append(tmpy);
    setPosition(tmpx, tmpy);
    // read hostility level
    setHostilityLevel(is.readByte());
    // read type
    setType(is.readByte());
    // read tileoffset
    setTileOffset(is.readInt());
    // read perks
    perks.readPerks(is);
    // read effects
    activeEffects.readActiveEffects(is);
    // read face number
    faceNumber = is.readInt();
    // Read money
    money = is.readInt();
    sb.append(money);
    // Read long name
    longName = StreamUtilities.readString(is);
    // Read description
    description = StreamUtilities.readString(is);
    // Read inventory
    int size = is.readInt();
    inventory.clear();
    for (int i=0;i<size;i++) {
      Item it = ItemFactory.readItem(is);
      inventory.add(it);
    }
    if (is.readByte() == 1) {
      firstHand = ItemFactory.readItem(is);
    }
    if (is.readByte() == 1) {
      secondHand = ItemFactory.readItem(is);
    }
    if (is.readByte() == 1) {
      armor = ItemFactory.readItem(is);
    }
    if (is.readByte() == 1) {
      headGear = ItemFactory.readItem(is);
    }
    if (is.readByte() == 1) {
      boots = ItemFactory.readItem(is);
    }
    if (is.readByte() == 1) {
      amulet = ItemFactory.readItem(is);
    }
    if (is.readByte() == 1) {
      ring = ItemFactory.readItem(is);
    }
    // Read experience and level
    experience = is.readInt();
    sb.append(experience);
    level = is.readInt();
    sb.append(level);
    // Reading HP and SP
    currentHP = is.readInt();
    sb.append(currentHP);
    currentSP = is.readInt();
    sb.append(currentSP);
    // Reading spell list
    size = is.readInt();
    spellList.clear();
    for (int i = 0; i<size;i++) {
      String spell = StreamUtilities.readString(is);
      spellList.add(spell);
      sb.append(spell);
    }
    // Read task list
    size = is.readInt();
    taskList.clear();
    for (int i = 0; i<size;i++) {
      CharTask task = new CharTask(is);
      taskList.add(task);
    }
    currentTaskIndex = is.readByte();
    // And finally read MD5 HASH and check it
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IOException("Loading character failed!");
    }
    byte[] data = sb.toString().getBytes("UTF-8");
    byte[] result = digest.digest(data);
    byte[] hash = new byte[16];    
    char[] errorMsg = {'C','o','r','r','u','p','t','e','d',' ','f','i','l','e','!'};  
    int amount = 0;
    while (amount < hash.length) {
      int more = is.read(hash,amount,hash.length-amount);
      amount = amount +more;
      if (more < 1) {
        break;
      }
    }
    if (hash.length == result.length) {
      for (int i=0;i<result.length;i++)
      {
        if (!(hash[i]==result[i])) {
          throw new IOException(String.valueOf(errorMsg));
        }
      }
    } else {
      throw new IOException(String.valueOf(errorMsg));
    }
    
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Name:"+getLongName()+" Lvl:"+getLevel()+" Exp:"+getExperience()+"\n");
    sb.append("X:"+getX()+" Y:"+getY()+" Type:"+getType()+" TileOffset:"+getTileOffset()+" Heading:"+getHeading());
    return sb.toString();
  }

  /**
   * Get character's hostility level
   * @return HOSTILITY_LEVEL_AGGRESSIVE, HOSTILITY_LEVEL_AVOID, 
   * HOSTILITY_LEVEL_GUARD
   */
  public int getHostilityLevel() {    
  	return hostilityLevel;
  }
  
  public void setHostilityLevel(int hostilityLevel) {
  	this.hostilityLevel = hostilityLevel;
  }
  
  public int[] getAttributes() {
    return attributes;
  }
    
  public int[] getSkills() {
    return skills;
  }
  
  /**
   * Handle all character effects this method must be called once in a turn
   * @param Party, party is used for logging
   */
  public void handleEffects(Party party) {
    if (luckCheck(false)==CHECK_FAIL && party.getMins()%30==0) {
      int drain = 0;
      // Character has bad luck and possible cursed items drain life
      if (firstHand != null && firstHand.isCursed()) {
        drain++;
      }
      if (secondHand != null && secondHand.isCursed()) {
        drain++;
      }
      if (armor != null && armor.isCursed()) {
        drain++;
      }
      if (ring != null && ring.isCursed()) {
        drain++;
      }
      if (amulet != null && amulet.isCursed()) {
        drain++;
      }
      if (boots != null && boots.isCursed()) {
        drain++;
      }
      if (headGear != null && headGear.isCursed()) {
        drain++;
      }
      if (drain > 0 && isPlayer()) {
        setCurrentHP(getCurrentHP()-drain);
        switch (DiceGenerator.getRandom(2)) {
        case 0: party.addLogText(this.getName()+" feels bad karma going through the bones..."); break;
        case 1: party.addLogText("Something drains lifeforce from "+this.getName()+"..."); break;
        case 2: party.addLogText("Something suddenly weakens "+this.getName()+"."); break;
        }
        
      }
    }
    Iterator<CharEffect> it = activeEffects.getActiveEffects().iterator();
    while (it.hasNext()) {
      CharEffect eff = it.next();
      if (eff.getEffect() == CharEffect.EFFECT_ON_HEALTH) {
        setCurrentHP(getCurrentHP()+ eff.getValue());        
      }
      if (eff.getEffect() == CharEffect.EFFECT_ON_STAMINA) {
        if (eff.getValue() < 0 && Math.abs(eff.getValue()) > getCurrentSP()) {
         int i =  Math.abs(eff.getValue())-getCurrentSP();
         setCurrentSP(0);
         setCurrentHP(getCurrentHP()-i);
        } else {
          setCurrentSP(getCurrentSP()+ eff.getValue());
        }
      }
      if (eff.getEffect() == CharEffect.EFFECT_ON_HEALTH_AND_STAMINA) {
        setCurrentHP(getCurrentHP()+ eff.getValue());
        if (eff.getValue() < 0 && Math.abs(eff.getValue()) > getCurrentSP()) {
          int i =  Math.abs(eff.getValue())-getCurrentSP();
          setCurrentSP(0);
          setCurrentHP(getCurrentHP()-i);
         } else {
           setCurrentSP(getCurrentSP()+ eff.getValue());
         }
      }
    }
    activeEffects.passRounds(1);
    
    it = activeEffects.getActiveEffects().iterator();
    while (it.hasNext()) {
      CharEffect eff = it.next();
      // Torches and latern can burn out
      if (eff.getEffect() == CharEffect.EFFECT_ON_LIGHT) {
         int dice = DiceGenerator.getRandom(999)+1;
         if (eff.getName().equalsIgnoreCase("Torch")) {
           if (dice > 990) {
             unequipSecondHand();
             int index =inventoryFindItemByName("Torch");
             if (inventoryRemoveItem(index)) {
               party.addLogText(this.getName()+"'s torch burn out.");
             }
             break;
           }
         }
         if (eff.getName().equalsIgnoreCase("Lantern")) {
           if (dice > 995) {
             unequipSecondHand();
             int index =inventoryFindItemByName("Lantern");
             if (inventoryRemoveItem(index)) {
               party.addLogText(this.getName()+"'s latern burn out.");
             }
             break;
           }           
         }
      }
    }
  }

  /**
   * Get character effects in array
   * @return CharEffects as array, null if no effects.
   */
  public CharEffect[] getEffects() {
    Iterator<CharEffect> it = activeEffects.getActiveEffects().iterator();
    CharEffect[] effects = null;
    int i = 0;
    if (activeEffects.getActiveEffects().size() > 0) {
      effects = new CharEffect[activeEffects.getActiveEffects().size()];
      while (it.hasNext()) {
        CharEffect eff = it.next();
        effects[i]=eff;
        i++;
      }
    }
    return effects;
  }

  
  /**
   * Add new effect for character
   * @param eff
   */
  public void addEffect(CharEffect eff) {
    activeEffects.addNewEeffect(eff);
  }
  
  /**
   * Get light effects from character
   * @return -2 to 3
   */
  public int getLigthEffect() {
    return activeEffects.getTotalEffect(CharEffect.EFFECT_ON_LIGHT,0);
  }
  
  /**
   * Is character pacified or not
   * @return boolean
   */
  public boolean isPacified() {
    if (activeEffects.getTotalEffect(CharEffect.EFFECT_PACIFISM, 0)==1) {
      return true;
    } else
      return false;
  }

  /**
   * Is character under influence of Burden
   * @return boolean
   */
  public boolean hasBurden() {
    return activeEffects.isEffectByName("Burden");
  }

  /**
   * Tries to remove certain type of effects
   * @param effectType
   * @param power
   * @return boolean, was removing successful
   */
  public boolean removeEffect(byte effectType, int power) {
    return activeEffects.removeEffects(effectType, power);
  }
  
  public void getNextTask() {
    int i = currentTaskIndex+1;
    if (i<getNumberOfTasks()) {
      currentTaskIndex=i;
    } else {
      currentTaskIndex = 0;
    }
  }
  
  public boolean goNextTask(int hour, int min) {
    boolean result = false;
    if (currentTaskIndex == -1) {
      getCurrentTaskIndex(hour,min);
    }
    if (currentTaskIndex == -1) {
      int i = currentTaskIndex;
      CharTask task = getTaskByIndex(i);
      if (task != null) {
        if (MapUtilities.isTimePast(hour, min, task.getTime())) {
          getNextTask();
          result = true;
        } else {        
          if ((currentTaskDone) &&(task.getTime().equals("-"))) {
            getNextTask();
            result = true;           
          }
        }
      }
    }
    return result;        
  }
  /**
   * Gets currentTaskIndex or if task index is missing then by time
   * @param hour
   * @param min
   * @return currentTaskIndex
   */
  public int getCurrentTaskIndex(int hour, int min) {
    if (currentTaskIndex != -1) {
      if ((currentTaskDone) && (!allTasksDone)) {
        currentTaskIndex++;
        currentTaskDone = false;
        if (currentTaskIndex >= getNumberOfTasks()) {
          currentTaskIndex = 0;
          allTasksDone=true;
        }
      }
      if (hour==0) {
        allTasksDone=false;
        currentTaskDone = false;
      }
      
      return currentTaskIndex;
    } else {
      if (getNumberOfTasks() > 0) {
        int lastPassed = 0;
        for (int i=0;i<getNumberOfTasks();i++) {
          CharTask task = getTaskByIndex(i);
          if (MapUtilities.isTimePast(hour, min, task.getTime())) {
            lastPassed = i;
          }
        }
        currentTaskIndex = lastPassed;
        currentTaskDone = false;
      }
      return currentTaskIndex;
    }
  }

  /**
   * Get description of current task.
   * @return String
   */
  public String getCurrentTaskDesc() {
    if (currentTaskIndex != -1) {
      CharTask task = taskList.get(currentTaskIndex);
      return task.toString();
    } else {
      return "No current task";
    }
  }
  
  /**
   * When shopkeeper has full inventory it starts sell stuff away
   * which generates more money into game and new items to shopkeepers.
   */
  public void sellExtraStuff() {
    if (this.inventorySize() >= MAX_ITEMS_ON_SHOPKEEPERS) {
      int amountToSell = this.inventorySize()-MAX_ITEMS_ON_SHOPKEEPERS+1;
      for (int i=0;i<amountToSell;i++) {
        int index = DiceGenerator.getRandom(this.inventorySize()-1);
        Item item = this.inventory.get(index);
        if (item != null) {
          this.money = this.money +item.getPrice();
          this.inventoryRemoveItem(index);
        }
      }
    }
  }
  
  /**
   * Get current task for Character
   * @param hour time
   * @param min time
   * @param mapLoad was Maploaded? If it was then generate all WORK stuff
   * @return taskIndex
   */
  public int getCurrentTaskIndexByTime(int hour, int min, boolean mapLoad) {
    boolean passedOK = false;
    boolean wasReallyPassed = false;
    if (getNumberOfTasks() > 0) {
      int lastPassed = 0;
      for (int i=0;i<getNumberOfTasks();i++) {
        CharTask task = getTaskByIndex(i);
        if (MapUtilities.isTimePast(hour, min, task.getTime())) {
          lastPassed = i;
          passedOK = false;
          wasReallyPassed = true;
          if (task.getTask().equals(CharTask.TASK_WORK) || task.getTask().equals(CharTask.TASK_KEEP_SHOP)) {
            // Generate working stuff if map has loaded            
            if ((this.inventorySize() < MAX_ITEMS_ON_SHOPKEEPERS) &&(mapLoad)) {
              Item item = ItemFactory.createItemByName(task.getWorkItem());
              if (item != null) {
                this.inventoryPickUpItem(item);
              }
            } else {
              // Generate only every 6th on resting
              if ((this.inventorySize() < MAX_ITEMS_ON_SHOPKEEPERS) &&(DiceGenerator.getRandom(5)==0)) {
                Item item = ItemFactory.createItemByName(task.getWorkItem());
                if (item != null) {
                  this.inventoryPickUpItem(item);
                }
              } else {
                // Full inventory selling stuff and then generating new one
                this.sellExtraStuff();
                Item item = ItemFactory.createItemByName(task.getWorkItem());
                if (item != null) {
                  this.inventoryPickUpItem(item);
                }

              }
            }
          }
        }
        if (i == lastPassed+1)  {
          String time = task.getTime().trim();
          if ((time.equals("-")) && (wasReallyPassed)) {
            passedOK = true;
            wasReallyPassed = true;
            lastPassed = i;
          } else {
            if (passedOK) {
              currentTaskIndex = lastPassed;      
              currentTaskDone = false;
            }
            passedOK = false;
            wasReallyPassed = false;
          }
        }
      }
      currentTaskIndex = lastPassed;      
      currentTaskDone = false;
    }
    return currentTaskIndex;
  }

  public boolean isAmIOnScreen() {
    return amIOnScreen;
  }

  public void setAmIOnScreen(boolean amIOnScreen) {
    this.amIOnScreen = amIOnScreen;
  }

  public boolean isCurrentTaskDone() {
    return currentTaskDone;
  }

  public void setCurrentTaskDone(boolean currentTaskDone) {
    this.currentTaskDone = currentTaskDone;
  }
  
  /**
   * Set if character joins to party and false if leaves
   * @param isPlayer boolean
   */
  public void setIsPlayer(boolean isPlayer) {
    if (isPlayer == true) {
      setHostilityLevel(HOSTILITY_LEVEL_PLAYER);
    } else {
      // Characater leaves party
      setHostilityLevel(HOSTILITY_LEVEL_GUARD);
    }
  }
  
  /**
   * Character receives lethal damage
   * @param damage
   */
  public void receiveLethalDamage(int damage) {
    currentHP=currentHP-damage;
  }

  /**
   * Character receives non lethal damage. If Stamina is less than
   * damage then character receives rest of damage in lethal.
   * @param damage
   */
  public void receiveNonLethalDamage(int damage) {
    if (currentSP > damage) {
      currentSP = currentSP -damage;
    } else {
      damage = damage-currentSP;
      currentSP = 0;
      receiveLethalDamage(damage);
    }
  }

  /**
   * Check if character is player or not
   * @return true if player and false if not
   */
  public boolean isPlayer() {
    if (getHostilityLevel()==HOSTILITY_LEVEL_PLAYER) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Is character dead or alive?
   * @return true if dead
   */
  public boolean isDead() {
    if (currentHP < 1) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Get number of empty hands. Without combat casting perk casting spells
   * requires two empty hands. With combat casting only one and Master combat casting
   * without empty hands. Unarmed weapons are calculated as empty.
   * @return 0,1,2
   */
  public int getEmptyHands() {
    int result = 0;
    if (inventoryGetFirstHand()==null) {
      result++;
    } else {
      if (inventoryGetFirstHand().getWeaponSkill() == SKILL_UNARMED) {
        result++;
        if (inventoryGetFirstHand().isTwoHandedWeapon()) {
          return 2;
        }
      } else {
        if (inventoryGetFirstHand().isTwoHandedWeapon()) {
          return 0;
        }
      }
    }
    if (inventoryGetSecondHand()==null) {
      result++;
    } else {
      if (inventoryGetSecondHand().getWeaponSkill() == SKILL_UNARMED) {
        result++;
      }
    }
    return result;
  }
  
  /**
   * Check if character has enough empty hands to cast a spell
   * @return boolean
   */
  public boolean canCastSpells() {
    int hands = getEmptyHands();
    int required=2;
    if (perks.getPerk(Perks.getPerkIndex(Perks.PERK_COMBAT_CASTING))) {
      required = 1;
    }
    if (perks.getPerk(Perks.getPerkIndex(Perks.PERK_MASTER_COMBAT_CASTING))) {
      required = 0;
    }
    if (hands >= required) {
      return true;
    } else {
      return false;
    }
  }
  /**
   * Calculate Character's stamina cost when attacking
   * @return int
   */
  public int getAttackStaminaCost() {
    int result = getFirstAttack().getStaminaCost();
    if (getSecondAttack()!=null) {
      result = result +getSecondAttack().getStaminaCost();
    }
    return result;
  }
  
  /**
   * Make a luck check. Check can be againts good or bad luck. If player wins
   * this will return CHECK_SUCCESS. Otherwise CHECK_FAIL. Good luck means
   * that dice must be under the luck attribute. Bad luck means that
   * check is failed if dice is bigger than 100-(10-LUCK).
   * @param isGoodLuck true for good luck
   * @return CHECK_FAIL CHECK_SUCCESS
   */
  public int luckCheck(boolean isGoodLuck) {
    int result = CHECK_FAIL;
    int dice = DiceGenerator.getRandom(1, 100);
    if (isGoodLuck) {
      if (dice <= getEffectiveAttribute(ATTRIBUTE_LUCK)) {
        result = CHECK_SUCCESS;
      }
    } else {
      if (dice >= 100-(10-getEffectiveAttribute(ATTRIBUTE_LUCK)) ) {
        result = CHECK_FAIL;
      } else {
        result = CHECK_SUCCESS;
      }
    }
    return result;
  }
  
  /**
   * Do skill check for character. This will check character's luck
   * if check was critical success. Also if character is player then
   * on success he/she gains more experience.
   * @param skill Skill index
   * @param targetNumber which needs to be achieve to success
   * @return CHECK_FAIL, CHECK_SUCCESS, CHECK_CRITICAL_SUCCESS
   */
  public int skillCheck(int skill,int targetNumber) {
    if ((skill >=0) && (skill < MAX_NUMBERS_OF_SKILL)) {
      return skillBonusCheck(getEffectiveSkill(skill),targetNumber);
    }
    return CHECK_FAIL;
  }
  
  
  /**
   * Do skill check for character. This will check character's luck
   * if check was critical success. Also if character is player then
   * on success he/she gains more experience.
   * @param skillbonus use already calculated skill bonus here
   * @param targetNumber which needs to be achieve to success
   * @return CHECK_FAIL, CHECK_SUCCESS, CHECK_CRITICAL_SUCCESS
   */
  public int skillBonusCheck(int skillBonus,int targetNumber) {
    return skillBonusCheck(skillBonus, targetNumber,0);
  }
  
  /**
   * Calculate exprience reward according targetnumber and current level
   * @param targetNumber
   * @return int
   */
  private int calculateExprienceReward(int targetNumber) {
    int expReward =0;
    if (level < 3) {
      if (targetNumber < 50) {
        expReward = 5;
      } else
      if (targetNumber < 75) {
        expReward = 10;
      } else
      if (targetNumber < 100) {
        expReward = 15;
      } else
      if (targetNumber < 125) {
        expReward = 20;
      } else
      if (targetNumber < 150) {
        expReward = 25;
      } else
      if (targetNumber < 175) {
        expReward = 30;
      } else
      if (targetNumber < 200) {
        expReward = 35;
      } else
      if (targetNumber < 212) {
        expReward = 40;
      }else
      if (targetNumber < 225) {
        expReward = 45;
      } else {
        expReward = 50;
      } 
    } else if (level < 6) {
      if (targetNumber < 50) {
        expReward = 2;
      } else
      if (targetNumber < 75) {
        expReward = 4;
      } else
      if (targetNumber < 100) {
        expReward = 8;
      } else
      if (targetNumber < 125) {
        expReward = 10;
      } else
      if (targetNumber < 150) {
        expReward = 15;
      } else
      if (targetNumber < 175) {
        expReward = 20;
      } else
      if (targetNumber < 200) {
        expReward = 25;
      } else
      if (targetNumber < 212) {
        expReward = 30;
      }else
      if (targetNumber < 225) {
        expReward = 35;
      } else {
        expReward = 40;
      } 
    } else if (level < 9) {
      if (targetNumber < 50) {
        expReward = 1;
      } else
      if (targetNumber < 75) {
        expReward = 2;
      } else
      if (targetNumber < 100) {
        expReward = 4;
      } else
      if (targetNumber < 125) {
        expReward = 8;
      } else
      if (targetNumber < 150) {
        expReward = 12;
      } else
      if (targetNumber < 175) {
        expReward = 15;
      } else
      if (targetNumber < 200) {
        expReward = 20;
      } else
      if (targetNumber < 212) {
        expReward = 25;
      }else
      if (targetNumber < 225) {
        expReward = 30;
      } else {
        expReward = 35;
      } 
    } else if (level < 12) {
      if (targetNumber < 50) {
        expReward = 1;
      } else
      if (targetNumber < 75) {
        expReward = 1;
      } else
      if (targetNumber < 100) {
        expReward = 3;
      } else
      if (targetNumber < 125) {
        expReward = 6;
      } else
      if (targetNumber < 150) {
        expReward = 10;
      } else
      if (targetNumber < 175) {
        expReward = 15;
      } else
      if (targetNumber < 200) {
        expReward = 20;
      } else
      if (targetNumber < 212) {
        expReward = 25;
      }else
      if (targetNumber < 225) {
        expReward = 30;
      } else {
        expReward = 35;
      } 
    }
    else if (level < 15) {
      if (targetNumber < 50) {
        expReward = 1;
      } else
      if (targetNumber < 75) {
        expReward = 1;
      } else
      if (targetNumber < 100) {
        expReward = 2;
      } else
      if (targetNumber < 125) {
        expReward = 4;
      } else
      if (targetNumber < 150) {
        expReward = 8;
      } else
      if (targetNumber < 175) {
        expReward = 10;
      } else
      if (targetNumber < 200) {
        expReward = 15;
      } else
      if (targetNumber < 212) {
        expReward = 20;
      }else
      if (targetNumber < 225) {
        expReward = 25;
      } else {
        expReward = 30;
      } 
    } else {
      if (targetNumber < 50) {
        expReward = 0;
      } else
      if (targetNumber < 75) {
        expReward = 1;
      } else
      if (targetNumber < 100) {
        expReward = 2;
      } else
      if (targetNumber < 125) {
        expReward = 3;
      } else
      if (targetNumber < 150) {
        expReward = 6;
      } else
      if (targetNumber < 175) {
        expReward = 8;
      } else
      if (targetNumber < 200) {
        expReward = 12;
      } else
      if (targetNumber < 212) {
        expReward = 15;
      }else
      if (targetNumber < 225) {
        expReward = 20;
      } else {
        expReward = 30;
      }      
    }
    return expReward;
  }
  /**
   * Do skill check for character. This will check character's luck
   * if check was critical success. Also if character is player then
   * on success he/she gains more experience.
   * @param skillbonus use already calculated skill bonus here
   * @param targetNumber which needs to be achieve to success
   * @param luckBonus Is luck modified by a perk. Use positive numbers to give bonus
   * @return CHECK_FAIL, CHECK_SUCCESS, CHECK_CRITICAL_SUCCESS
   */
  public int skillBonusCheck(int skillBonus,int targetNumber, int luckBonus) {
    int result = CHECK_FAIL;
    int expReward=0;
    if (isPlayer()) {
      expReward = calculateExprienceReward(targetNumber);
    }
    int dice = DiceGenerator.getRandom(1, 100);
    if (dice <= 11-getEffectiveAttribute(ATTRIBUTE_LUCK)) {
      // There needs to be possibility to fail even good skill
      result = CHECK_FAIL;
    } else if (dice+skillBonus >= targetNumber) {
      if (dice > 100-getEffectiveAttribute(ATTRIBUTE_LUCK)-luckBonus) {
        result = CHECK_CRITICAL_SUCCESS;
      } else {
        result = CHECK_SUCCESS;
      }
      if (isPlayer()) {
        experience=experience+expReward;
      }
    } else {
      // Was not succesfull, but was it on critical range
      if (dice > 100-getEffectiveAttribute(ATTRIBUTE_LUCK)-luckBonus) {
        // Yes it was, then it is normal success.
        result = CHECK_SUCCESS;
        if (isPlayer()) {
          experience=experience+expReward;
        }
      }
      
    }
    return result;
    
  }

  /**
   * Return string which is "/res/talks/"+LongNameWithoutSpaces+".tlk".
   * @return String
   */
  public String getTalkFileName() {
    StringBuilder sb = new StringBuilder("/res/talks/");
    String tmp = getLongName();
    tmp = tmp.replaceAll(" ", "");
    sb.append(tmp);
    sb.append(".tlk");
    return sb.toString();
  }
  
  /**
   * Is character undead creature or not
   * Remember add all undead creatures here!!!
   * @return boolean
   */
  public boolean isUndead() {
    if (name.equalsIgnoreCase("Zombie")||
        name.equalsIgnoreCase("Skeleton Mage")||
        name.equalsIgnoreCase("Skeleton Warrior")||
        name.equalsIgnoreCase("Rotting zombie")||
        name.equalsIgnoreCase("Undead king")||
        name.equalsIgnoreCase("Heroon")||
        name.equalsIgnoreCase("Black Rudolf")||
        name.equalsIgnoreCase("Ancient undead king")||
        name.equalsIgnoreCase("Ancient Lich")||
        name.equalsIgnoreCase("Pirate zombie")||
        name.equalsIgnoreCase("Zombie crew")||
        name.equalsIgnoreCase("Undead bodyguard")) {
      return true;
    }
    return false;
  }

  public int getLastDirection() {
    return lastDirection;
  }

  public void setLastDirection(int lastDirection) {
    this.lastDirection = lastDirection;
  }
}
