package org.jheroes.map.character;

import org.jheroes.map.Map;
import org.jheroes.map.character.CombatModifiers.AttackType;

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
 * Create a spell from the name
 * 
 */
public class SpellFactory {

  // Power mage spells
  public static final String SPELL_NAME_POWER_OF_HEALING = "Power of healing"; //Wizardy
  public static final String SPELL_NAME_POWER_OF_DESTRUCTION = "Power of destruction"; // Sorcery
  public static final String SPELL_NAME_POWER_OF_MIGHTY = "Power of mighty"; // Qi
  
  // Sorcery Spells
  
  /**
   * Needs to hit target, only piercing damage, Casting cost is 1
   * Sorcery 
   */
  public static final String SPELL_NAME_MAGIC_DART = "Magic Dart";

  /**
   * Poison cloud, Casting cost 10
   * -2 HP for 10 rounds
   * Sorcery
   */
  public static final String SPELL_NAME_POISON_CLOUD = "Poison Cloud";

  /**
   * Fireball, 8-30(6-12), Casting cost 10
   * Sorcery
   */
  public static final String SPELL_NAME_FIREBALL = "Fireball";

  /**
   * Ray of Fire, 6-20(2-12), Casting cost 6
   * Sorcery
   */
  public static final String SPELL_NAME_RAY_OF_FIRE = "Ray of Fire";

  /**
   * Firestrom, 12-40(9-18), Casting cost 15
   * Sorcery
   */
  public static final String SPELL_NAME_FIRESTORM = "Firestorm";

  /**
   * Poison spit, 1-10(1 each round), Casting cost 3
   * Monster only(Sorcery)
   */
  public static final String SPELL_NAME_POISON_SPIT = "Poison spit";

  /**
   * Fear of Darkness, 2-20 HP (1 SP each round), Casting cost 6
   * Monster only(Sorcery)
   */
  public static final String SPELL_NAME_FEAR_OF_DARKNESS = "Fear of Darkness";

  /**
   * Drowning, 5 non lethal (2 non lethal each round), Casting cost 4
   * Monster only(Sorcery)
   */
  public static final String SPELL_NAME_DROWNING = "Drowning";
  /**
   * Needs to hit target, Damage 1-5, 1 piercing Casting cost is 2
   * Sorcery
   */
  public static final String SPELL_NAME_MAGIC_ARROW = "Magic Arrow";

  /**
   * Pacifism. Casting cost is 4. Duration 5 turns.
   * Sorcery, Monster only
   */
  public static final String SPELL_NAME_PACIFISM = "Pacifism";

  /**
   * Mental Arrow Casting cost is 2. 
   * Needs to hit target, Damage 1-6,  Casting cost is 2
   * Sorcery
   */
  public static final String SPELL_NAME_MENTAL_ARROW = "Mental Arrow";

  /**
   * Mental Spear Casting cost is 3 
   * Needs to hit target, Damage 2-8, 
   * Sorcery
   */
  public static final String SPELL_NAME_MENTAL_SPEAR = "Mental Spear";

  /**
   * No need to hit, Damage 1-6, Casting cost is 4
   * Dodging -15 for 5 turns
   * Sorcery
   */
  public static final String SPELL_NAME_FROST_BITE = "Frost Bite";

  /**
   * No need to hit, Damage 4-24, Casting cost is 8
   * Dodging -15 for 5 turns
   * Sorcery
   */
  public static final String SPELL_NAME_ICE_BREATH = "Ice Breath";

  /**
   * Damage 4-16, Casting cost is 6
   * Dodging -15 for 5 turns
   * Sorcery
   */
  public static final String SPELL_NAME_RAY_OF_ICE = "Ray of Ice";

  /**
   * No need to hit, 1 radius, Damage 1-8, Casting cost is 6
   * Damage stamina 1 point for 5 turns
   * Sorcery
   */
  public static final String SPELL_NAME_SHOCK_BURST = "Shock burst";

  /**
   * No need to hit, 13 radius, Damage 10-35, Casting cost is 15
   * Damage stamina 3 point for 10 turns
   * Sorcery
   */
  public static final String SPELL_NAME_THUNDER_STRIKE = "Thunder Strike";

  /**
   * No need to hit, Damage 1-6, 2 piercing, Casting cost is 4
   * Sorcery
   */
  public static final String SPELL_NAME_FLAME_BURST = "Flame burst";

  /**
   * Illusionary Death Casting cost is 6. 
   * Needs to hit target, Damage 10, Stamina 10
   * Sorcery
   */
  public static final String SPELL_NAME_ILLUSIONARY_DEATH = "Illusionary Death";

  // Wizardy Spells
  
  /**
   * Heals target 5 hp, casting cost is 4.
   * Wizardy
   */
  public static final String SPELL_NAME_MINOR_HEAL = "Minor Heal";

  /**
   * Heals target 3 hp/turn, casting cost is 6. Duration 4 turns.
   * Wizardy
   */
  public static final String SPELL_NAME_HEALING_AURA = "Healing Aura";

  /**
   * Heals all party members 3 hp/turn, casting cost is 10. Duration 5 turns.
   * Wizardy
   */
  public static final String SPELL_NAME_HEALING_CIRCLE = "Healing Circle";

  /**
   * Heals target 10 hp, casting cost is 8.
   * Wizardy
   */
  public static final String SPELL_NAME_HEAL = "Heal";

  /**
   * Heals target 20 hp, casting cost is 15.
   * Wizardy
   */
  public static final String SPELL_NAME_MIRACLE_HEAL = "Miracle Heal";

  /**
   * Wizard light, Casting cost 4, last 40 rounds
   * Wizardy
   */
  public static final String SPELL_NAME_WIZARD_LIGHT = "Wizard Light";
  
  /**
   * Detect magic, Casting cost is 2. 
   * Wizardy
   */
  public static final String SPELL_NAME_DETECT_MAGIC = "Detect Magic";

  /**
   * Cure poison, Casting cost is 4 
   * Wizardy
   */
  public static final String SPELL_NAME_CURE_POISON = "Cure Poison";

  /**
   * Identify, Casting cost is 10. 
   * Wizardy
   */
  public static final String SPELL_NAME_IDENTIFY = "Identify";
  
  /**
   * Cure poison, Casting cost is 4
   * Wizardy 
   */
  public static final String SPELL_NAME_REMOVE_CURSE = "Remove Curse";
  
  /**
   * Burden. Decreases carrying capacity by 20kg, Casting cost 6. 
   * Duration 15 turns
   * Wizardy
   */
  public static final String SPELL_NAME_BURDEN = "Burden";

  /**
   * Fairy Flame, Mindaffecting spell, 1-6 damage, lits around target 
   * Duration 15 turns
   * Wizardy
   */
  public static final String SPELL_NAME_FAIRY_FLAME = "Fairy Flame";

  /**
   * Darkness, shrouds area into darkness around target 
   * Duration 15 turns
   * Wizardy
   */
  public static final String SPELL_NAME_DARKNESS = "Darkness";
  
  /**
   * Magic Armor, give 4 armor points duration 15 turns, casting cost 10
   */
  public static final String SPELL_NAME_MAGIC_ARMOR ="Magic Armor";

  /**
   * Spritual Armor, give 15 willpower, duration 15 turns, casting cost 10
   */
  public static final String SPELL_NAME_SPIRITUAL_ARMOR ="Spiritual Armor";

  /**
   * Dimish Armor, reduce armor by 2, duration 15 turns, casting cost 5
   */
  public static final String SPELL_NAME_DIMISH_ARMOR ="Dimish Armor";

  /**
   * Dimish Armor II, reduce armor by 6, duration 15 turns, casting cost 10
   */
  public static final String SPELL_NAME_DIMISH_ARMORII ="Dimish Armor II";

  /**
   * Weak mind, dimish 25 willpower, duration 15 turns, casting cost 10
   */
  public static final String SPELL_NAME_WEAK_MIND ="Weak Mind";
  /**
   * Smite Undead, 1D6 per level, castion cost 6
   */
  public static final String SPELL_NAME_SMITE_UNDEAD ="Smite Undead";
  // QI magic spells
  
  /**
   * Improves dodging by 10. Casting cost is 2. Duration 15 turns.
   * Qi Magic
   */
  public static final String SPELL_NAME_HAZE = "Haze";

  /**
   * Purifiy body, removes poison from caster
   * Qi Magic
   */
  public static final String SPELL_NAME_PURIFY_BODY = "Purify Body";

  /**
   * Clear mind, removes curses from caster
   * Qi Magic
   */
  public static final String SPELL_NAME_CLEAR_MIND = "Clear Mind";

  /**
   * Improves unarmed by 10. Casting cost is 2. Duration 15 turns.
   * Qi Magic
   */
  public static final String SPELL_NAME_MAGIC_FISTS = "Magic Fists";

  /**
   * Heals caster 5 hp, casting cost is 6.
   * Qi Magic
   */
  public static final String SPELL_NAME_QI_HEAL = "Qi Heal";

  /**
   * Improves strength by 2. Casting cost is 4. Duration 15 turns.
   * Qi Magic
   */
  public static final String SPELL_NAME_QI_STRENGTH = "Qi Strength";
 
  /**
   * Improves melee by 25. Casting cost is 6. Duration 15 turns.
   * Qi Magic
   */
  public static final String SPELL_NAME_WARRIOR_S_WILL = "Warrior's Will";

  /**
   * Improves unarmed by 25. Casting cost is 6. Duration 15 turns.
   * Qi Magic
   */
  public static final String SPELL_NAME_BEAST_S_WILL = "Beast's Will";

  /**
   * Improves ranged attacks by 25. Casting cost is 6. Duration 15 turns.
   * Qi Magic
   */
  public static final String SPELL_NAME_ARCHER_S_WILL = "Archer's Will";

  /**
   * Improves dodging by 25. Casting cost is 6. Duration 15 turns.
   * Qi Magic
   */
  public static final String SPELL_NAME_MIST = "Mist";

  /**
   * Restore Stamina, target stamina is restored by 8. Casting cost is 10.
   * Qi Magic
   */
  public static final String SPELL_NAME_RESTORE_STAMINA = "Restore Stamina";

  /**
   * Wicked Fatigue, target get stamina damage 1-12 and -1 stamina for 15 turns
   * Qi Magic
   */
  public static final String SPELL_NAME_WICKED_FATIQUE = "Wicked Fatique";
  /**
   * Heals caster 2 hp each turn. Duration 15 turns. casting cost is 15.
   * Qi Magic
   */
  public static final String SPELL_NAME_QI_REGENERATION = "Qi Regeneration";

  /**
   * Heals caster 1 hp/st each turn. Duration 15 turns. casting cost is 15.
   * Qi Magic
   */
  public static final String SPELL_NAME_QI_PEACE = "Qi Peace";

  /**
   * Mind blast, 1-3 Damage, 1-3 stamina, casting cost 3
   * Qi Magic
   */
  public static final String SPELL_NAME_MIND_BLAST = "Mind Blast";

  /**
   * Qi blast, 3-12 Damage, 3-12 stamina, casting cost 8
   * Qi Magic
   */
  public static final String SPELL_NAME_QI_BLAST = "Qi Blast";

  public static final String SPELL_LIST[] = {SPELL_NAME_MAGIC_DART,
    SPELL_NAME_MINOR_HEAL,SPELL_NAME_HAZE,SPELL_NAME_FIREBALL,
    SPELL_NAME_POISON_SPIT,SPELL_NAME_WIZARD_LIGHT,SPELL_NAME_MAGIC_ARROW,
    SPELL_NAME_MAGIC_FISTS,SPELL_NAME_DETECT_MAGIC,SPELL_NAME_IDENTIFY,
    SPELL_NAME_CURE_POISON,SPELL_NAME_QI_HEAL,SPELL_NAME_QI_STRENGTH,
    SPELL_NAME_PACIFISM,SPELL_NAME_MENTAL_ARROW, SPELL_NAME_HEALING_AURA,
    SPELL_NAME_REMOVE_CURSE,SPELL_NAME_FROST_BITE,SPELL_NAME_SHOCK_BURST,
    SPELL_NAME_FLAME_BURST,SPELL_NAME_ILLUSIONARY_DEATH,
    SPELL_NAME_WARRIOR_S_WILL,SPELL_NAME_BEAST_S_WILL,SPELL_NAME_ARCHER_S_WILL,
    SPELL_NAME_BURDEN,SPELL_NAME_FIRESTORM,SPELL_NAME_ICE_BREATH,
    SPELL_NAME_POISON_CLOUD,SPELL_NAME_THUNDER_STRIKE,SPELL_NAME_HEALING_CIRCLE,
    SPELL_NAME_HEAL,SPELL_NAME_MIRACLE_HEAL,SPELL_NAME_FAIRY_FLAME,
    SPELL_NAME_PURIFY_BODY,SPELL_NAME_CLEAR_MIND,SPELL_NAME_RESTORE_STAMINA,
    SPELL_NAME_WICKED_FATIQUE,SPELL_NAME_QI_REGENERATION,SPELL_NAME_QI_PEACE,
    SPELL_NAME_MIND_BLAST,SPELL_NAME_QI_BLAST,SPELL_NAME_DARKNESS,
    SPELL_NAME_RAY_OF_FIRE,SPELL_NAME_RAY_OF_ICE,SPELL_NAME_MENTAL_SPEAR,
    SPELL_NAME_MIST,SPELL_NAME_MAGIC_ARMOR,SPELL_NAME_SPIRITUAL_ARMOR,
    SPELL_NAME_DIMISH_ARMOR,SPELL_NAME_DIMISH_ARMORII,SPELL_NAME_WEAK_MIND,
    SPELL_NAME_SMITE_UNDEAD,SPELL_NAME_FEAR_OF_DARKNESS,SPELL_NAME_DROWNING};
  
  public static Spell getSpell(String spellName) {
    Spell result = new Spell(spellName);
    // Power spells
    if (SPELL_NAME_POWER_OF_HEALING.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_POWER_OF_HEALING, CharEffect.TYPE_ENCHANT,
          1, CharEffect.EFFECT_ON_HEALTH, Character.SKILL_DODGING, 1, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(4);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);      
    }
    if (SPELL_NAME_POWER_OF_DESTRUCTION.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(6);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setAttack(attack);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_SMITE);
      result.setMinimumSkill(50);      
    }
    if (SPELL_NAME_POWER_OF_MIGHTY.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_POWER_OF_MIGHTY, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_MIGHTY, Character.SKILL_DODGING, 1, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_CASTER);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);      
    }
    // Sorcery Spells
    if (SPELL_NAME_MAGIC_DART.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(3);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(1);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_MINOR_ATTACK);
      result.setMinimumSkill(15);
      result.setAttackType(AttackType.MAGIC);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_POISON_SPIT.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(10);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_NEED_HIT);
      result.setCastingCost(3);
      result.setSkill(Character.SKILL_SORCERY);
      CharEffect eff = new CharEffect(SPELL_NAME_POISON_SPIT, CharEffect.TYPE_POISON,
          10, CharEffect.EFFECT_ON_STAMINA, Character.SKILL_DODGING, -1, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POISON);      
      result.setMinimumSkill(15);
      result.setAttackType(AttackType.POISON);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);

    }
    if (SPELL_NAME_FEAR_OF_DARKNESS.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(2);
      attack.setMaxLethalDamage(20);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setMindEffecting(true);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_NEED_HIT);
      result.setCastingCost(6);
      result.setSkill(Character.SKILL_SORCERY);
      CharEffect eff = new CharEffect(SPELL_NAME_FEAR_OF_DARKNESS, CharEffect.TYPE_CURSE,
          20, CharEffect.EFFECT_ON_STAMINA, Character.SKILL_DODGING, -1, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_FEAR);      
      result.setMinimumSkill(25);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_DROWNING.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(0);
      attack.setMaxLethalDamage(0);
      attack.setMinStaminaDamage(5);
      attack.setMaxStaminaDamage(5);
      result.setMindEffecting(true);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_NEED_HIT);
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_SORCERY);
      CharEffect eff = new CharEffect(SPELL_NAME_DROWNING, CharEffect.TYPE_CURSE,
          10, CharEffect.EFFECT_ON_STAMINA, Character.SKILL_DODGING, -2, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_DROWNING);      
      result.setMinimumSkill(25);
      result.setAttackType(AttackType.MINDAFFECTING);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_MAGIC_ARROW.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(1);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(5);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(2);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_MINOR_ATTACK);
      result.setMinimumSkill(15);
      result.setAttackType(AttackType.MAGIC);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_PACIFISM.equalsIgnoreCase(spellName)) {
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_SORCERY);
      result.setMindEffecting(true);
      CharEffect eff = new CharEffect(SPELL_NAME_PACIFISM, CharEffect.TYPE_CURSE,
          5, CharEffect.EFFECT_PACIFISM, Character.SKILL_DODGING, 0, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_CURSE);
      result.setMinimumSkill(25);
    }
    if (SPELL_NAME_MENTAL_ARROW.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(6);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(2);
      result.setMindEffecting(true);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_MINDAFFECTING);
      result.setMinimumSkill(25);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_MENTAL_SPEAR.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(2);
      attack.setMaxLethalDamage(9);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(3);
      result.setMindEffecting(true);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_MINDAFFECTING);
      result.setMinimumSkill(50);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_FROST_BITE.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(6);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_1_TILE);
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_SORCERY);
      CharEffect eff = new CharEffect(SPELL_NAME_FROST_BITE, CharEffect.TYPE_CURSE,
          5, CharEffect.EFFECT_ON_SKILL, Character.SKILL_DODGING, -15, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_FROST);      
      result.setMinimumSkill(50);
      result.setAttackType(AttackType.ICE);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_SHOCK_BURST.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(8);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_5_TILE);
      result.setCastingCost(6);
      result.setSkill(Character.SKILL_SORCERY);
      CharEffect eff = new CharEffect(SPELL_NAME_SHOCK_BURST, CharEffect.TYPE_CURSE,
          5, CharEffect.EFFECT_ON_STAMINA, Character.SKILL_DODGING, -1, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_SHOCK);
      result.setMinimumSkill(50);
      result.setAttackType(AttackType.ELECTRIC);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_FLAME_BURST.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(2);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(6);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_1_TILE);
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_FLAME);      
      result.setMinimumSkill(50);
      result.setAttackType(AttackType.FIRE);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_ILLUSIONARY_DEATH.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(10);
      attack.setMaxLethalDamage(10);
      attack.setMinStaminaDamage(10);
      attack.setMaxStaminaDamage(10);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(6);
      result.setMindEffecting(true);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_CURSE);
      result.setMinimumSkill(75);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_POISON_CLOUD.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(0);
      attack.setMaxLethalDamage(0);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_13_TILE);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_SORCERY);
      CharEffect eff = new CharEffect(SPELL_NAME_POISON_CLOUD, CharEffect.TYPE_POISON,
          10, CharEffect.EFFECT_ON_HEALTH, Character.SKILL_DODGING, -2, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POISON);      
      result.setMinimumSkill(75);
      result.setAttackType(AttackType.POISON);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_ICE_BREATH.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(4);
      attack.setMaxLethalDamage(24);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_5_TILE);
      result.setCastingCost(8);
      result.setSkill(Character.SKILL_SORCERY);
      // Effect is same as in frost bite so not changing effect name
      CharEffect eff = new CharEffect(SPELL_NAME_FROST_BITE, CharEffect.TYPE_CURSE,
          5, CharEffect.EFFECT_ON_SKILL, Character.SKILL_DODGING, -15, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_FROST);      
      result.setMinimumSkill(75);
      result.setAttackType(AttackType.ICE);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_RAY_OF_ICE.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(4);
      attack.setMaxLethalDamage(16);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(6);
      result.setSkill(Character.SKILL_SORCERY);
      // Effect is same as in frost bite so not changing effect name
      CharEffect eff = new CharEffect(SPELL_NAME_FROST_BITE, CharEffect.TYPE_CURSE,
          5, CharEffect.EFFECT_ON_SKILL, Character.SKILL_DODGING, -15, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_FROST);      
      result.setMinimumSkill(75);
      result.setAttackType(AttackType.ICE);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_FIREBALL.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(8);
      attack.setMaxLethalDamage(30);
      attack.setMinStaminaDamage(6);
      attack.setMaxStaminaDamage(12);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_9_TILE);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_FLAME);
      result.setMinimumSkill(75);
      result.setAttackType(AttackType.FIRE);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_RAY_OF_FIRE.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(6);
      attack.setMaxLethalDamage(20);
      attack.setMinStaminaDamage(2);
      attack.setMaxStaminaDamage(15);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_NEED_HIT);
      result.setCastingCost(6);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_FLAME);
      result.setMinimumSkill(75);
      result.setAttackType(AttackType.FIRE);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_FIRESTORM.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(12);
      attack.setMaxLethalDamage(40);
      attack.setMinStaminaDamage(9);
      attack.setMaxStaminaDamage(18);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_13_TILE);
      result.setCastingCost(15);
      result.setSkill(Character.SKILL_SORCERY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_FLAME);
      result.setMinimumSkill(100);
      result.setAttackType(AttackType.FIRE);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_THUNDER_STRIKE.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(10);
      attack.setMaxLethalDamage(35);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_13_TILE);
      result.setCastingCost(15);
      result.setSkill(Character.SKILL_SORCERY);
      CharEffect eff = new CharEffect(SPELL_NAME_THUNDER_STRIKE, CharEffect.TYPE_CURSE,
          10, CharEffect.EFFECT_ON_STAMINA, Character.SKILL_DODGING, -3, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_SHOCK);      
      result.setMinimumSkill(100);
      result.setAttackType(AttackType.ELECTRIC);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    // Wizardy Spells
    if (SPELL_NAME_MINOR_HEAL.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_MINOR_HEAL, CharEffect.TYPE_ENCHANT,
          1, CharEffect.EFFECT_ON_HEALTH, Character.SKILL_DODGING, 5, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(15);
    }
    if (SPELL_NAME_WIZARD_LIGHT.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_WIZARD_LIGHT, CharEffect.TYPE_ENCHANT,
          40, CharEffect.EFFECT_ON_LIGHT, Character.SKILL_DODGING, 3, 50);
      result.setEffect(eff);
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(15);
    }
    if (SPELL_NAME_HEAL.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_HEAL, CharEffect.TYPE_ENCHANT,
          1, CharEffect.EFFECT_ON_HEALTH, Character.SKILL_DODGING, 10, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(7);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(25);
    }
    if (SPELL_NAME_DARKNESS.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_DARKNESS, CharEffect.TYPE_ENCHANT,
          40, CharEffect.EFFECT_ON_LIGHT, Character.SKILL_DODGING, -2, 50);
      result.setEffect(eff);
      result.setCastingCost(5);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_DARKNESS);
      result.setMinimumSkill(25);
    }
    if (SPELL_NAME_DETECT_MAGIC.equalsIgnoreCase(spellName)) {
      result.setCastingCost(2);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(25);
      result.setDispelType(Spell.DISPEL_TYPE_DETECT_MAGIC);
    }
    if (SPELL_NAME_CURE_POISON.equalsIgnoreCase(spellName)) {
      result.setCastingCost(4);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(25);
      result.setDispelType(Spell.DISPEL_TYPE_CURE_POISON);
    }
    if (SPELL_NAME_REMOVE_CURSE.equalsIgnoreCase(spellName)) {
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setMinimumSkill(25);
      result.setDispelType(Spell.DISPEL_TYPE_REMOVE_CURSE);
    }
    if (SPELL_NAME_IDENTIFY.equalsIgnoreCase(spellName)) {
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);
      result.setDispelType(Spell.DISPEL_TYPE_IDENTIFY_ITEMS);
    }
    if (SPELL_NAME_HEALING_AURA.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_HEALING_AURA, CharEffect.TYPE_ENCHANT,
          4, CharEffect.EFFECT_ON_HEALTH, Character.SKILL_DODGING, 3, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(6);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);
    }
    if (SPELL_NAME_BURDEN.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_BURDEN, CharEffect.TYPE_CURSE,
          15, CharEffect.EFFECT_ON_CARRYING_CAPACITY, Character.SKILL_MELEE, -20, 50);
      result.setEffect(eff);
      result.setCastingCost(4);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setMindEffecting(true);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_CURSE);
      result.setMinimumSkill(50);
    }
    if (SPELL_NAME_FAIRY_FLAME.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(6);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      CharEffect eff = new CharEffect(SPELL_NAME_FAIRY_FLAME, CharEffect.TYPE_CURSE,
          15, CharEffect.EFFECT_ON_LIGHT, Character.SKILL_MELEE, 2, 50);
      result.setEffect(eff);
      result.setCastingCost(3);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setMindEffecting(true);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_MINDAFFECTING);
      result.setMinimumSkill(50);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_MAGIC_ARMOR.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_MAGIC_ARMOR, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_ARMOR, Character.SKILL_DODGING, 4, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);
    }
    if (SPELL_NAME_SPIRITUAL_ARMOR.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_SPIRITUAL_ARMOR, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_WILLPOWER, Character.SKILL_DODGING, 25, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);
    }
    if (SPELL_NAME_DIMISH_ARMOR.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_DIMISH_ARMOR, CharEffect.TYPE_CURSE,
          15, CharEffect.EFFECT_ON_ARMOR, Character.SKILL_DODGING, -2, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(5);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_CURSE);
      result.setMinimumSkill(50);
    }
    if (SPELL_NAME_HEALING_CIRCLE.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_HEALING_CIRCLE, CharEffect.TYPE_ENCHANT,
          5, CharEffect.EFFECT_ON_HEALTH, Character.SKILL_DODGING, 3, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_PARTY);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(75);
    }
    if (SPELL_NAME_DIMISH_ARMORII.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_DIMISH_ARMOR, CharEffect.TYPE_CURSE,
          15, CharEffect.EFFECT_ON_ARMOR, Character.SKILL_DODGING, -6, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_CURSE);
      result.setMinimumSkill(75);
    }
    if (SPELL_NAME_WEAK_MIND.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_WEAK_MIND, CharEffect.TYPE_CURSE,
          15, CharEffect.EFFECT_ON_WILLPOWER, Character.SKILL_DODGING, -25, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_CURSE);
      result.setMinimumSkill(75);
    }
    if (SPELL_NAME_MIRACLE_HEAL.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_MIRACLE_HEAL, CharEffect.TYPE_ENCHANT,
          1, CharEffect.EFFECT_ON_HEALTH, Character.SKILL_DODGING, 20, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(12);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(100);
    }
    if (SPELL_NAME_SMITE_UNDEAD.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(6);
      attack.setMinStaminaDamage(0);
      attack.setMaxStaminaDamage(0);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(8);
      result.setSkill(Character.SKILL_WIZARDRY);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_SMITE);
      result.setMinimumSkill(50);
      result.setAttackType(AttackType.MAGIC);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    // QI magic spells
    if (SPELL_NAME_HAZE.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_HAZE, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_SKILL, Character.SKILL_DODGING, 10, 50);
      result.setEffect(eff);
      result.setCastingCost(2);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(15);
    }
    if (SPELL_NAME_MAGIC_FISTS.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_MAGIC_FISTS, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_SKILL, Character.SKILL_UNARMED, 10, 50);
      result.setEffect(eff);
      result.setCastingCost(2);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(15);
    }
    if (SPELL_NAME_QI_HEAL.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_QI_HEAL, CharEffect.TYPE_ENCHANT,
          1, CharEffect.EFFECT_ON_HEALTH, Character.SKILL_DODGING, 8, 50);
      result.setEffect(eff);
      result.setCastingCost(3);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(25);
    }
    if (SPELL_NAME_QI_STRENGTH.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_QI_STRENGTH, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_ATTRIBUTE, Character.ATTRIBUTE_STRENGTH, 2, 50);
      result.setEffect(eff);
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(25);
    }
    if (SPELL_NAME_MIND_BLAST.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(1);
      attack.setMaxLethalDamage(3);
      attack.setMinStaminaDamage(1);
      attack.setMaxStaminaDamage(3);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(4);
      result.setMindEffecting(true);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_MINDAFFECTING);
      result.setMinimumSkill(25);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_WARRIOR_S_WILL.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_WARRIOR_S_WILL, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_SKILL, Character.SKILL_MELEE, 25, 50);
      result.setEffect(eff);
      result.setCastingCost(6);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);
    }
    if (SPELL_NAME_BEAST_S_WILL.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_BEAST_S_WILL, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_SKILL, Character.SKILL_UNARMED, 25, 50);
      result.setEffect(eff);
      result.setCastingCost(6);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);
    }
    if (SPELL_NAME_ARCHER_S_WILL.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_ARCHER_S_WILL, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_SKILL, Character.SKILL_RANGED_WEAPONS, 25, 50);
      result.setEffect(eff);
      result.setCastingCost(6);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);
    }
    if (SPELL_NAME_MIST.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_MIST, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_SKILL, Character.SKILL_DODGING, 25, 50);
      result.setEffect(eff);
      result.setCastingCost(2);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);
    }
    if (SPELL_NAME_PURIFY_BODY.equalsIgnoreCase(spellName)) {
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(50);
      result.setDispelType(Spell.DISPEL_TYPE_CURE_POISON);
    }
    if (SPELL_NAME_CLEAR_MIND.equalsIgnoreCase(spellName)) {
      result.setCastingCost(4);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setTargetType(Spell.SPELL_TARGET_CASTER);
      result.setMinimumSkill(50);
      result.setDispelType(Spell.DISPEL_TYPE_REMOVE_CURSE);
    }
    if (SPELL_NAME_RESTORE_STAMINA.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_RESTORE_STAMINA, CharEffect.TYPE_ENCHANT,
          1, CharEffect.EFFECT_ON_STAMINA, Character.SKILL_DODGING, 8, 50);
      result.setEffect(eff);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(10);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(75);
    }
    if (SPELL_NAME_WICKED_FATIQUE.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(0);
      attack.setMaxLethalDamage(0);
      attack.setMinStaminaDamage(1);
      attack.setMaxStaminaDamage(12);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setRadius(Spell.SPELL_RADIUS_NEED_HIT);
      result.setCastingCost(5);
      result.setMindEffecting(true);
      result.setSkill(Character.SKILL_QI_MAGIC);
      CharEffect eff = new CharEffect(SPELL_NAME_WICKED_FATIQUE, CharEffect.TYPE_CURSE,
          15, CharEffect.EFFECT_ON_STAMINA, Character.SKILL_DODGING, -1, 50);
      result.setEffect(eff);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_MINDAFFECTING);
      result.setMinimumSkill(75);
      result.setAttackType(AttackType.NONLETHAL);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_QI_BLAST.equalsIgnoreCase(spellName)) {
      Attack attack = new Attack();      
      attack.setPiercing(0);
      attack.setMinLethalDamage(3);
      attack.setMaxLethalDamage(12);
      attack.setMinStaminaDamage(3);
      attack.setMaxStaminaDamage(12);
      result.setTargetType(Spell.SPELL_TARGET_TARGET);
      result.setCastingCost(8);
      result.setMindEffecting(true);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_MINDAFFECTING);
      result.setMinimumSkill(75);
      result.setAttackType(AttackType.BLUNT);
      attack.setAttackType(result.getAttackType());
      result.setAttack(attack);
    }
    if (SPELL_NAME_QI_REGENERATION.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_QI_REGENERATION, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_HEALTH, Character.SKILL_DODGING, 2, 50);
      result.setEffect(eff);
      result.setCastingCost(15);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(100);
    }
    if (SPELL_NAME_QI_PEACE.equalsIgnoreCase(spellName)) {
      CharEffect eff = new CharEffect(SPELL_NAME_QI_PEACE, CharEffect.TYPE_ENCHANT,
          15, CharEffect.EFFECT_ON_HEALTH_AND_STAMINA, Character.SKILL_DODGING, 1, 50);
      result.setEffect(eff);
      result.setCastingCost(15);
      result.setSkill(Character.SKILL_QI_MAGIC);
      result.setAnimation(Map.GRAPH_EFFECT_SPELL_POSITIVE);
      result.setMinimumSkill(100);
    }

    return result;
  }
}
