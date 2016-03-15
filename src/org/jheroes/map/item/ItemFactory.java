package org.jheroes.map.item;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jheroes.map.DiceGenerator;
import org.jheroes.map.character.CharEffect;
import org.jheroes.map.character.Character;
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
 * Class to create items
 * 
 */
public class ItemFactory {

  public static final int LESSER_RANDOM_ITEM = 0;
  public static final int RANDOM_ITEM = 1;
  public static final int HIGH_RANDOM_ITEM = 2;
  public static final int MAGIC_RANDOM_ITEM = 3;
  
  public static Item Create(int index) {
    Item tmp = null;
    switch (index) {
    case 0:{ tmp = createWeapon(index); break;} // Steel Long Sword
    case 1:{ tmp = createWeapon(index); break;} // Steel Axe
    case 2:{ tmp = createShield(index); break;} // Steel Round Shield
    case 3:{ tmp = createArmor(index); break;} // Steel Plate Mail
    case 4:{ tmp = createArmor(index); break;} // Steel Helmet
    case 5:{ tmp = createWeapon(index); break;} // Steel Gauntlets
    case 6:{ tmp = createWeapon(index); break;} // Iron Long Sword
    case 7:{ tmp = createWeapon(index); break;} // Iron Axe
    case 8:{ tmp = createShield(index); break;} // Iron Round Shield
    case 9:{ tmp = createArmor(index); break;} // Iron Plate Mail
    case 10:{ tmp = createArmor(index); break;} // Iron Helmet
    case 11:{ tmp = createWeapon(index); break;} // Spiked Gauntlets
    case 12:{ tmp = createWeapon(index); break;} // Bronze Long Sword
    case 13:{ tmp = createWeapon(index); break;} // Bronze Axe
    case 14:{ tmp = createShield(index); break;} // Bronze Round Shield
    case 15:{ tmp = createArmor(index); break;} // Bronze Plate Mail
    case 16:{ tmp = createArmor(index); break;} // Bronze Helmet
    case 17:{ tmp = createWeapon(index); break;} // Bronze Gauntlets
    case 18:{ tmp = createWeapon(index); break;} // Pick Axe
    case 19:{ tmp = createArmor(index); break;} // Studded leather armor
    case 20:{ tmp = createArmor(index); break;} // Leather armor
    case 21:{ tmp = createShield(index); break;} // Steel kite shield
    case 22:{ tmp = createShield(index); break;} // Iron kite shield
    case 23:{ tmp = createShield(index); break;} // Bronze kite shield
    case 24:{ tmp = createWeapon(index); break;} // Long bow
    case 25:{ tmp = createWeapon(index); break;} // Short bow
    case 26:{ tmp = createMoney(25); break;} // Money 25 copper pieces
    case 27:{ tmp = createWeapon(index); break;} // Steel spear
    case 28:{ tmp = createWeapon(index); break;} // Iron spear
    case 29:{ tmp = createWeapon(index); break;} // Bronze spear
    case 30:{ tmp = createWeapon(index); break;} // Steel mace
    case 31:{ tmp = createWeapon(index); break;} // Iron mace
    case 32:{ tmp = createWeapon(index); break;} // Bronze mace
    case 33:{ tmp = createWeapon(index); break;} // Small fangs(UNDROPABLE)
    case 34:{ tmp = createArmor(index); break;} // fur(UNDROPABLE)
    case 35:{ tmp = createGeneric(index); break;} // Minor healing potion
    case 36:{ tmp = createGeneric(index); break;} // Steel key
    case 37:{ tmp = createGeneric(index); break;} // Picklocks
    case 38:{ tmp = createWeapon(index); break;} // Cross Bow
    case 39:{ tmp = createArmor(index); break;} // Chain shirt
    case 40:{ tmp = createArmor(index); break;} // Crude leather armor
    case 41:{ tmp = createArmor(index); break;} // Padded cloth jacket
    case 42:{ tmp = createWeapon(index); break;} // Steel short sword
    case 43:{ tmp = createWeapon(index); break;} // Iron short sword
    case 44:{ tmp = createWeapon(index); break;} // Bronze short sword
    case 45:{ tmp = createMoney(5); break;} // Money 5 copper pieces
    case 46:{ tmp = createMoney(100); break;} // Money 100 copper pieces
    case 47:{ tmp = createGeneric(index); break;} // Rat poison
    case 48:{ tmp = createGeneric(index); break;} // Vigor potion
    case 49:{ tmp = createGeneric(index); break;} // Beer
    case 50:{ tmp = createGeneric(index); break;} // Adventurer's diploma
    case 51:{ tmp = createGeneric(index); break;} // Iron key
    case 52:{ tmp = createGeneric(index); break;} // Bronze key
    case 53:{ tmp = createWeapon(index); break;} // Steel dagger
    case 54:{ tmp = createWeapon(index); break;} // Iron dagger
    case 55:{ tmp = createWeapon(index); break;} // Bronze dagger
    case 56:{ tmp = createGeneric(index); break;} // Torch
    case 57:{ tmp = createGeneric(index); break;} // Lantern
    case 58:{ tmp = createScroll(index); break;} // Scroll of Minor Heal
    case 59:{ tmp = createScroll(index); break;} // Scroll of Haze
    case 60:{ tmp = createScroll(index); break;} // Scroll of Magic Fists
    case 61:{ tmp = createScroll(index); break;} // Scroll of Magic Dart
    case 62:{ tmp = createScroll(index); break;} // Scroll of Magic Arrow
    case 63:{ tmp = createScroll(index); break;} // Scroll of Fireball
    case 64:{ tmp = createScroll(index); break;} // Scroll of Wizard Light
    case 65:{ tmp = createGeneric(index); break;} // Cabbage
    case 66:{ tmp = createGeneric(index); break;} // Apple
    case 67:{ tmp = createGeneric(index); break;} // Carrot
    case 68:{ tmp = createGeneric(index); break;} // Fresh salad
    case 69:{ tmp = createGeneric(index); break;} // Goat cheese
    case 70:{ tmp = createScroll(index); break;} // Scroll of Detect Magic
    case 71:{ tmp = createScroll(index); break;} // Scroll of Identify
    case 72:{ tmp = createScroll(index); break;} // Scroll of Cure Poison
    case 73:{ tmp = createScroll(index); break;} // Scroll of Qi Heal
    case 74:{ tmp = createScroll(index); break;} // Scroll of Qi Strength
    case 75:{ tmp = createGeneric(index); break;} // Bread
    case 76:{ tmp = createGeneric(index); break;} // Roasted meat
    case 77:{ tmp = createWeapon(index); break;} // Sting(UNDROPABLE)
    case 78:{ tmp = createWeapon(index); break;} // Bite(UNDROPABLE)
    case 79:{ tmp = createArmor(index); break;} // Chitin armor(UNDROPABLE)
    case 80:{ tmp = createGeneric(index); break;} // Honeycomb
    case 81:{ tmp = createArmor(index); break;} // Witch Hat
    case 82:{ tmp = createWeapon(index); break;} // Oak Staff
    case 83:{ tmp = createWeapon(index); break;} // Fangs(UNDROPABLE)
    case 84:{ tmp = createArmor(index); break;} // Wenni's Amulet
    case 85:{ tmp = createGeneric(index); break;} // Healing potion
    case 86:{ tmp = createScroll(index); break;} // Scroll of Mental Arrow
    case 87:{ tmp = createScroll(index); break;} // Scroll of Healing Aura
    case 88:{ tmp = createWeapon(index); break;} // Rapier
    case 89:{ tmp = createWeapon(index); break;} // Steel 2-handed sword
    case 90:{ tmp = createWeapon(index); break;} // Bronze 2-handed sword
    case 91:{ tmp = createWeapon(index); break;} // Iron 2-handed sword
    case 92:{ tmp = createArmor(index); break;} // Bronze chest plate
    case 93:{ tmp = createWeapon(index); break;} // Sling
    case 94:{ tmp = createWeapon(index); break;} // Bronze battle axe
    case 95:{ tmp = createWeapon(index); break;} // Iron battle axe
    case 96:{ tmp = createWeapon(index); break;} // Steel battle axe
    case 97:{ tmp = createGeneric(index); break;} // Oiled rope
    case 98:{ tmp = createWeapon(index); break;} // Iron Flail
    case 99:{ tmp = createWeapon(index); break;} // Steel Flail
    case 100:{ tmp = createWeapon(index); break;} // Iron Warhammer
    case 101:{ tmp = createWeapon(index); break;} // Steel Warhammer
    case 102:{ tmp = createGeneric(index); break;} // Cooked fish
    case 103:{ tmp = createGeneric(index); break;} // Black powder keg
    case 104:{ tmp = createArmor(index); break;} // Splint mail
    case 105:{ tmp = createArmor(index); break;} // Long overcoat
    case 106:{ tmp = createArmor(index); break;} // Adventurer's cloak
    case 107:{ tmp = createWeapon(index); break;} // Cursed steel long sword
    case 108:{ tmp = createWeapon(index); break;} // Cursed Steel axe
    case 109:{ tmp = createShield(index); break;} // Cursed steel round shield
    case 110:{ tmp = createArmor(index); break;} // Cursed plate mail
    case 111:{ tmp = createWeapon(index); break;} // Cursed gauntlets
    case 112:{ tmp = createWeapon(index); break;} // Cursed pick axe
    case 113:{ tmp = createWeapon(index); break;} // Cursed long bow
    case 114:{ tmp = createWeapon(index); break;} // Cursed spear
    case 115:{ tmp = createWeapon(index); break;} // Cursed cross bow
    case 116:{ tmp = createWeapon(index); break;} // Sniper's bow
    case 117:{ tmp = createWeapon(index); break;} // Cursed devil sword
    case 118:{ tmp = createWeapon(index); break;} // Cursed rapier
    case 119:{ tmp = createWeapon(index); break;} // Pirate's rapier
    case 120:{ tmp = createGeneric(index); break;} // Charm potion
    case 121:{ tmp = createGeneric(index); break;} // Potion of strength
    case 122:{ tmp = createGeneric(index); break;} // Potion of recovery
    case 123:{ tmp = createGeneric(index); break;} // Potion of luck
    case 124:{ tmp = createGeneric(index); break;} // Potion of health
    case 125:{ tmp = createGeneric(index); break;} // Sewer key
    case 126:{ tmp = createGeneric(index); break;} // Ruby
    case 127:{ tmp = createGeneric(index); break;} // Sapphire
    case 128:{ tmp = createGeneric(index); break;} // Emerald
    case 129:{ tmp = createWeapon(index); break;} // Combosite bow
    case 130:{ tmp = createWeapon(index); break;} // Shurikens
    case 131:{ tmp = createGeneric(index); break;} // Lesser random item
    case 132:{ tmp = createGeneric(index); break;} // Random item
    case 133:{ tmp = createGeneric(index); break;} // High random item
    case 134:{ tmp = createGeneric(index); break;} // Magic random item
    case 135:{ tmp = createScroll(index); break;} // Scroll of Remove Curse
    case 136:{ tmp = createScroll(index); break;} // Scroll of Frost Bite
    case 137:{ tmp = createScroll(index); break;} // Scroll of Shock Burst
    case 138:{ tmp = createScroll(index); break;} // Scroll of Flame Burst
    case 139:{ tmp = createScroll(index); break;} // Scroll of Illusionary Death
    case 140:{ tmp = createScroll(index); break;} // Scroll of Warrior's Will
    case 141:{ tmp = createScroll(index); break;} // Scroll of Beast's Will
    case 142:{ tmp = createScroll(index); break;} // Scroll of Archer's Will
    case 143:{ tmp = createScroll(index); break;} // Scroll of Mist
    case 144:{ tmp = createWeapon(index); break;} // Kung-fu Staff
    case 145:{ tmp = createGeneric(index); break;} // Golden ring
    case 146:{ tmp = createGeneric(index); break;} // Ring of Defense
    case 147:{ tmp = createGeneric(index); break;} // Ring of Swordplay
    case 148:{ tmp = createGeneric(index); break;} // Ring of Qi
    case 149:{ tmp = createGeneric(index); break;} // Ring of Sorcery
    case 150:{ tmp = createGeneric(index); break;} // Ring of Wizardy
    case 151:{ tmp = createGeneric(index); break;} // Ring of Furious Fist
    case 152:{ tmp = createGeneric(index); break;} // Ring of Diplomacy
    case 153:{ tmp = createArmor(index); break;} // Amulet of Luck
    case 154:{ tmp = createArmor(index); break;} // Amulet of Strength
    case 155:{ tmp = createArmor(index); break;} // Amulet of Willpower
    case 156:{ tmp = createArmor(index); break;} // Traveller's Boots
    case 157:{ tmp = createArmor(index); break;} // Warrior's Boots
    case 158:{ tmp = createArmor(index); break;} // Boots of Dodging
    case 159:{ tmp = createScroll(index); break;} // Scroll of Burden
    case 160:{ tmp = createScroll(index); break;} // Scroll of Purify Body
    case 161:{ tmp = createScroll(index); break;} // Scroll of Clear Mind
    case 162:{ tmp = createScroll(index); break;} // Scroll of Restore Stamina
    case 163:{ tmp = createScroll(index); break;} // Scroll of Wicked Fatigue
    case 164:{ tmp = createScroll(index); break;} // Scroll of Qi Regeneration
    case 165:{ tmp = createScroll(index); break;} // Scroll of Qi Peace
    case 166:{ tmp = createScroll(index); break;} // Scroll of Mind Blast
    case 167:{ tmp = createScroll(index); break;} // Scroll of Qi Blast
    case 168:{ tmp = createScroll(index); break;} // Scroll of Heal
    case 169:{ tmp = createScroll(index); break;} // Scroll of Miracle Heal
    case 170:{ tmp = createScroll(index); break;} // Scroll of Healing Circle
    case 171:{ tmp = createScroll(index); break;} // Scroll of Fairy Flame
    case 172:{ tmp = createScroll(index); break;} // Scroll of Firestorm
    case 173:{ tmp = createScroll(index); break;} // Scroll of Poison Cloud
    case 174:{ tmp = createScroll(index); break;} // Scroll of Ice Breath
    case 175:{ tmp = createScroll(index); break;} // Scroll of Thunder Strike
    case 176:{ tmp = createScroll(index); break;} // Scroll of Mental Spear
    case 177:{ tmp = createGeneric(index); break;} // Tomb key
    case 178:{ tmp = createGeneric(index); break;} // Hairpin
    case 179:{ tmp = createGeneric(index); break;} // Toolkit
    case 180:{ tmp = createScroll(index); break;} // Scroll of Darkness
    case 181:{ tmp = createGeneric(index); break;} // Logbook
    case 182:{ tmp = createShield(index); break;} // Tower shield
    case 183:{ tmp = createShield(index); break;} // Shield of Magic
    case 184:{ tmp = createGeneric(index); break;} // Ancient Key
    case 185:{ tmp = createWeapon(index); break;} // Cursed Staff of Evil
    case 186:{ tmp = createArmor(index); break;} // Boots of Kickass
    case 187:{ tmp = createGeneric(index); break;} // Moonstone
    case 188:{ tmp = createArmor(index); break;} // Robe of Wizard
    case 189:{ tmp = createArmor(index); break;} // Robe of Sorcerer
    case 190:{ tmp = createArmor(index); break;} // Robe of Adept
    case 191:{ tmp = createWeapon(index); break;} // Magic Blade
    case 192:{ tmp = createGeneric(index); break;} // Archmage Key
    case 193:{ tmp = createGeneric(index); break;} // Cellar key
    case 194:{ tmp = createScroll(index); break;} // Scroll of Ray of Fire
    case 195:{ tmp = createScroll(index); break;} // Scroll of Ray of Ice
    case 196:{ tmp = createGeneric(index); break;} // Beastery Key
    case 197:{ tmp = createWeapon(index); break;} // Tentacle(UNDROPABLE)
    case 198:{ tmp = createWeapon(index); break;} // BigBite(UNDROPPABLE)
    case 199:{ tmp = createArmor(index); break;} // Shell(UNDROPPABLE)
    case 200:{ tmp = createArmor(index); break;} // HeavyArmor(UNDROPPABLE)
    case 201:{ tmp = createGeneric(index); break;} // Ancient Scroll
    case 202:{ tmp = createGeneric(index); break;} // Master Key
    case 203:{ tmp = createGeneric(index); break;} // Northfork Family Ring
    case 204:{ tmp = createScroll(index); break;} // Scroll of Magic Armor
    case 205:{ tmp = createScroll(index); break;} // Scroll of Spiritual Armor
    case 206:{ tmp = createScroll(index); break;} // Scroll of Dimish Armor
    case 207:{ tmp = createScroll(index); break;} // Scroll of Dimish Armor II
    case 208:{ tmp = createScroll(index); break;} // Scroll of Weak Mind
    case 209:{ tmp = createWeapon(index); break;} // Bronze javelin
    case 210:{ tmp = createWeapon(index); break;} // Iron javelin
    case 211:{ tmp = createWeapon(index); break;} // Steel javelin
    case 212:{ tmp = createWeapon(index); break;} // Bronze throwing axe
    case 213:{ tmp = createWeapon(index); break;} // Iron throwing axe
    case 214:{ tmp = createWeapon(index); break;} // Steel throwing axe
    case 215:{ tmp = createWeapon(index); break;} // Hurling Javelin
    case 216:{ tmp = createWeapon(index); break;} // Dagger of Light
    case 217:{ tmp = createGeneric(index); break;} // Healing kit
    case 218:{ tmp = createGeneric(index); break;} // Master healing kit
    case 219:{ tmp = createShield(index); break;} // Buckler
    case 220:{ tmp = createShield(index); break;} // Buckler of Protection
    case 221:{ tmp = createShield(index); break;} // Cursed buckler
    case 222:{ tmp = createShield(index); break;} // Royal Shield
    case 223:{ tmp = createShield(index); break;} // Cursed kite shield
    case 224:{ tmp = createGeneric(index); break;} // Larek's fishing rod
    case 225:{ tmp = createGeneric(index); break;} // Raw fish
    case 226:{ tmp = createGeneric(index); break;} // Troll head
    case 227:{ tmp = createGeneric(index); break;} // Treasure map
    case 228:{ tmp = createGeneric(index); break;} // Rope ladder
    case 229:{ tmp = createGeneric(index); break;} // Shovel
    case 230:{ tmp = createGeneric(index); break;} // Voodoo Head
    case 231:{ tmp = createGeneric(index); break;} // Treasure chamber key
    case 232:{ tmp = createArmor(index); break;} // Pirate captain's coat
    case 233:{ tmp = createWeapon(index); break;} // Rapier of Blood
    case 234:{ tmp = createWeapon(index); break;} // Magical Hoe
    case 235:{ tmp = createWeapon(index); break;} // Ranger's axe
    case 236:{ tmp = createWeapon(index); break;} // Ranger's bow
    case 237:{ tmp = createGeneric(index); break;} // Aconitum
    case 238:{ tmp = createGeneric(index); break;} // Seed of Good
    case 239:{ tmp = createGeneric(index); break;} // Seed of Evil
    case 240:{ tmp = createWeapon(index); break;} // Sword of barbarian
    case 241:{ tmp = createArmor(index); break;} // Armor of barbarian
    case 242:{ tmp = createGeneric(index); break;} // Aconitum potion
    case 243:{ tmp = createShield(index); break;} // Magical Oak shield
    case 244:{ tmp = createShield(index); break;} // Skull shield
    case 245:{ tmp = createWeapon(index); break;} // Boomerang of stamina
    case 246:{ tmp = createWeapon(index); break;} // Mace of lifeforce
    case 247:{ tmp = createWeapon(index); break;} // Short sword of slaying
    case 248:{ tmp = createWeapon(index); break;} // Short sword of vigor
    case 249:{ tmp = createWeapon(index); break;} // Vampiric dagger
    case 250:{ tmp = createWeapon(index); break;} // Dagger of vigor
    case 251:{ tmp = createWeapon(index); break;} // Gauntlets of stamina
    case 252:{ tmp = createWeapon(index); break;} // Gauntlets of slaying
    case 253:{ tmp = createWeapon(index); break;} // Vampiric gauntlets
    case 254:{ tmp = createWeapon(index); break;} // VampiricBite(Undropable)
    case 255:{ tmp = createWeapon(index); break;} // Wooden club
    case 256:{ tmp = createGeneric(index); break;} // Troll key
    case 257:{ tmp = createWeapon(index); break;} // Flail of vigor    
    case 258:{ tmp = createWeapon(index); break;} // Brutal Warhammer    
    case 259:{ tmp = createGeneric(index); break;} // Ancient Tome
    case 260:{ tmp = createGeneric(index); break;} // Tome of Strength
    case 261:{ tmp = createGeneric(index); break;} // Tome of Agility
    case 262:{ tmp = createGeneric(index); break;} // Tome of Endurance
    case 263:{ tmp = createGeneric(index); break;} // Tome of Intelligence
    case 264:{ tmp = createGeneric(index); break;} // Tome of Wisdom
    case 265:{ tmp = createGeneric(index); break;} // Tome of Charisma
    case 266:{ tmp = createGeneric(index); break;} // Tome of Luck
    case 267:{ tmp = createGeneric(index); break;} // Tome of Death
    case 268:{ tmp = createGeneric(index); break;} // Strange jar
    case 269:{ tmp = createWeapon(index); break;} // Axe of Might
    case 270:{ tmp = createWeapon(index); break;} // Fairy's Sickle
    case 271:{ tmp = createWeapon(index); break;} // Sickle of Health
    case 272:{ tmp = createGeneric(index); break;} // Mother's locket
    case 273:{ tmp = createGeneric(index); break;} // Bridge repair kit

    }
    return tmp;
  }
  
  public static final int MAX_ITEM = 274;
  
  private static ArrayList<Integer> lesserRandomItems;
  private static ArrayList<Integer> randomItems;
  private static ArrayList<Integer> highRandomItems;
  private static ArrayList<Integer> magicRandomItems;
  
  private static void getRandomLists() {
    lesserRandomItems = new ArrayList<Integer>(50);
    randomItems = new ArrayList<Integer>(50);
    highRandomItems = new ArrayList<Integer>(50);
    magicRandomItems = new ArrayList<Integer>(50);
    for (int i=0;i<MAX_ITEM;i++) {
      Item tmp = Create(i);
      if (tmp.isRandomItem()) {
        if (tmp.getPrice() <= 25) {
          lesserRandomItems.add(Integer.valueOf(i));
        }
        if (tmp.getPrice() >25 && tmp.getPrice() <= 150) {
          randomItems.add(Integer.valueOf(i));
        }
        if (tmp.getPrice() >150 && tmp.getPrice() <= 600 ) {
          highRandomItems.add(Integer.valueOf(i));
        }
        if (tmp.getPrice() >=50 && tmp.getPrice() <= 1000 && tmp.isMagical()) {
          magicRandomItems.add(Integer.valueOf(i));
        }
      }
    }
  }
  
  /**
   * Generate Random Item according the random item, if not random item
   * then just return similar item.
   * @param input Item
   * @return Item or null if could not create proper random item
   */
  public static Item generateRandomItem(int randomItemList) {
    if (lesserRandomItems == null) {
      getRandomLists();
    }
    Item tmp = null;;
    switch(randomItemList) {
    case LESSER_RANDOM_ITEM: {
      int index = lesserRandomItems.size();
      index = DiceGenerator.getRandom(index-1);
      index = lesserRandomItems.get(index).intValue();
      tmp = Create(index);
      break;
    }
    case RANDOM_ITEM: {
      int index = randomItems.size();
      index = DiceGenerator.getRandom(index-1);
      index = randomItems.get(index).intValue();
      tmp = Create(index);
      break;
    }
    case HIGH_RANDOM_ITEM: {
      int index = highRandomItems.size();
      index = DiceGenerator.getRandom(index-1);
      index = highRandomItems.get(index).intValue();
      tmp = Create(index);
      break;
    }
    case MAGIC_RANDOM_ITEM: {
      int index = magicRandomItems.size();
      index = DiceGenerator.getRandom(index-1);
      index = magicRandomItems.get(index).intValue();
      tmp = Create(index);
      break;
    }
    default: {
      int index = lesserRandomItems.size();
      index = DiceGenerator.getRandom(index-1);
      index = lesserRandomItems.get(index).intValue();
      tmp = Create(index);
      break;
    }
    }
    return tmp;
  }
  
  /**
   * Create item by name
   * @param name String
   * @return item if able to create otherwise null
   */
  public static Item createItemByName(String name) {
    Item tmp = null;
    for (int i=0;i<MAX_ITEM;i++) {
      tmp = Create(i);
      if ((tmp != null) && (tmp.getName().equalsIgnoreCase(name))) {
        return tmp;
      }
    }
    return null;
  }
  
  public static Item createScroll(int index) {
    Item tmp = null;
    if (index == 58) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Minor Heal",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 59) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Haze",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 60) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Magic Fists",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 61) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Magic Dart",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 62) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Magic Arrow",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 63) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Fireball",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 64) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Wizard Light",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 70) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Detect Magic",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 71) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Identify",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 72) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Cure Poison",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 73) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Qi Heal",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 74) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Qi Strength",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 86) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Mental Arrow",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 87) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Healing Aura",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 135) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Remove Curse",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 136) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Frost Bite",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 137) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Shock Burst",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 138) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Flame Burst",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 139) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Illusionary Death",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 140) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Warrior's Will",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 141) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Beast's Will",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 142) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Archer's Will",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 143) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Mist",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 159) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Burden",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 160) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Purify Body",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 161) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Clear Mind",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 162) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Restore Stamina",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 163) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Wicked Fatigue",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 164) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Qi Regeneration",55);
      tmp.setPrice(300);
      tmp.setMagical(true);
    }
    if (index == 165) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Qi Peace",55);
      tmp.setPrice(300);
      tmp.setMagical(true);
    }
    if (index == 166) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Mind Blast",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 167) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Qi Blast",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 168) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Heal",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 169) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Miracle Heal",55);
      tmp.setPrice(300);
      tmp.setMagical(true);
    }
    if (index == 170) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Healing Circle",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 171) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Fairy Flame",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 172) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Firestorm",55);
      tmp.setPrice(300);
      tmp.setMagical(true);
    }
    if (index == 173) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Poison Cloud",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 174) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Ice Breath",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 175) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Thunder Strike",55);
      tmp.setPrice(300);
      tmp.setMagical(true);
    }
    if (index == 176) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Mental Spear",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 180) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Darkness",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 194) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Ray of Fire",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 195) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Ray of Ice",55);
      tmp.setPrice(200);
      tmp.setMagical(true);
    }
    if (index == 204) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Magic Armor",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 205) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Spiritual Armor",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 206) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Dimish Armor",55);
      tmp.setPrice(50);
      tmp.setMagical(true);
    }
    if (index == 207) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Dimish Armor II",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }
    if (index == 208) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Scroll of Weak Mind",55);
      tmp.setPrice(100);
      tmp.setMagical(true);
    }

    return tmp;
  }
  
  public static Item createGeneric(int index) {
    Item tmp = null;
    if (index == 35) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Minor healing potion",33);
      tmp.setPrice(20);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH);
      tmp.setEffectValue(20);
      tmp.setEffectLasting(1);
      tmp.setMagical(true);
    }
    if (index == 36) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Steel key",38);
      tmp.setPrice(20);
      tmp.setKeyValue("Steel key");
      tmp.setRandomItem(false);
    }
    if (index == 37) {
      tmp = new Item(index,Item.TYPE_ITEM_PICKLOCK,"Picklocks",39);
      tmp.setPrice(40);
      tmp.setBonusPickLock(5);
    }
    if (index == 47) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Rat poison",35);
      tmp.setPrice(15);
    }
    if (index == 48) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Vigor potion",34);
      tmp.setPrice(20);
      tmp.setEffect(CharEffect.EFFECT_ON_STAMINA);
      tmp.setEffectValue(15);
      tmp.setEffectLasting(3);
      tmp.setMagical(true);
    }
    if (index == 49) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Beer",36);
      tmp.setPrice(5);
      tmp.setEffect(CharEffect.EFFECT_ON_ATTRIBUTE);
      tmp.setEffectAttrOrSkill(Character.ATTRIBUTE_WISDOM);
      tmp.setEffectValue(-1);
      tmp.setEffectLasting(30);
    }
    if (index == 50) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Adventurer's Diploma",47);
      tmp.setPrice(500);
    }
    if (index == 51) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Iron key",48);
      tmp.setPrice(15);
      tmp.setKeyValue("Iron key");
      tmp.setRandomItem(false);
    }
    if (index == 52) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Bronze key",49);
      tmp.setPrice(10);
      tmp.setKeyValue("Bronze key");
      tmp.setRandomItem(false);
    }
    if (index == 56) {
      tmp = new Item(index,Item.TYPE_ITEM_LIGHT_DEVICE,"Torch",53);
      tmp.setPrice(5);
      tmp.setWeight(10);
      tmp.setEffect(CharEffect.EFFECT_ON_LIGHT);
      tmp.setEffectValue(2);
    }
    if (index == 57) {
      tmp = new Item(index,Item.TYPE_ITEM_LIGHT_DEVICE,"Lantern",54);
      tmp.setPrice(10);
      tmp.setWeight(10);
      tmp.setEffect(CharEffect.EFFECT_ON_LIGHT);
      tmp.setEffectValue(3);
    }
    if (index == 65) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Cabbage",56);
      tmp.setPrice(1);
      tmp.setWeight(5);
      tmp.setEffect(CharEffect.EFFECT_ON_STAMINA);
      tmp.setEffectValue(1);
      tmp.setEffectLasting(5);
    }
    if (index == 66) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Apple",57);
      tmp.setPrice(1);
      tmp.setWeight(2);
      tmp.setEffect(CharEffect.EFFECT_ON_STAMINA);
      tmp.setEffectValue(1);
      tmp.setEffectLasting(5);
    }
    if (index == 67) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Carrot",58);
      tmp.setPrice(1);
      tmp.setWeight(2);
      tmp.setEffect(CharEffect.EFFECT_ON_STAMINA);
      tmp.setEffectValue(1);
      tmp.setEffectLasting(8);
    }
    if (index == 68) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Fresh salad",59);
      tmp.setPrice(10);
      tmp.setWeight(5);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH_AND_STAMINA);
      tmp.setEffectValue(1);
      tmp.setEffectLasting(10);
    }
    if (index == 69) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Goat cheese",60);
      tmp.setPrice(12);
      tmp.setWeight(10);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH_AND_STAMINA);
      tmp.setEffectValue(1);
      tmp.setEffectLasting(10);
    }
    if (index == 75) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Bread",61);
      tmp.setPrice(8);
      tmp.setWeight(5);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH_AND_STAMINA);
      tmp.setEffectValue(1);
      tmp.setEffectLasting(8);
    }
    if (index == 76) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Roasted meat",62);
      tmp.setPrice(15);
      tmp.setWeight(8);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH);
      tmp.setEffectValue(2);
      tmp.setEffectLasting(6);
    }
    if (index == 80) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Honeycomb",63);
      tmp.setPrice(25);
      tmp.setWeight(5);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH_AND_STAMINA);
      tmp.setEffectValue(1);
      tmp.setEffectLasting(14);
    }
    if (index == 85) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Healing potion",33);
      tmp.setPrice(50);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH);
      tmp.setEffectValue(60);
      tmp.setEffectLasting(1);
      tmp.setMagical(true);
    }
    if (index == 97) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Oiled rope",76);
      tmp.setPrice(5);
    }
    if (index == 102) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Cooked fish",81);
      tmp.setPrice(15);
      tmp.setWeight(6);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH);
      tmp.setEffectValue(2);
      tmp.setEffectLasting(6);
    }
    if (index == 103) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Black powder keg",82);
      tmp.setPrice(50);
    }
    if (index == 120) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Charm potion",36);
      tmp.setPrice(50);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_DIPLOMACY);
      tmp.setEffectValue(20);
      tmp.setEffectLasting(30);
      tmp.setMagical(true);
    }
    if (index == 121) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Potion of strength",33);
      tmp.setPrice(50);
      tmp.setEffect(CharEffect.EFFECT_ON_ATTRIBUTE);
      tmp.setEffectAttrOrSkill(Character.ATTRIBUTE_STRENGTH);
      tmp.setEffectValue(2);
      tmp.setEffectLasting(20);
      tmp.setMagical(true);
    }
    if (index == 122) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Potion of recovery",34);
      tmp.setPrice(70);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH_AND_STAMINA);
      tmp.setEffectValue(50);
      tmp.setEffectLasting(1);
      tmp.setMagical(true);
    }
    if (index == 123) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Potion of luck",35);
      tmp.setPrice(25);
      tmp.setEffect(CharEffect.EFFECT_ON_ATTRIBUTE);
      tmp.setEffectAttrOrSkill(Character.ATTRIBUTE_LUCK);
      tmp.setEffectValue(2);
      tmp.setEffectLasting(20);
      tmp.setMagical(true);
    }
    if (index == 124) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Potion of Health",33);
      tmp.setPrice(100);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH);
      tmp.setEffectValue(100);
      tmp.setEffectLasting(1);
      tmp.setMagical(true);
    }
    if (index == 125) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Sewer key",38);
      tmp.setPrice(20);
      tmp.setKeyValue("Sewer key");
      tmp.setRandomItem(false);
    }
    if (index == 126) {
      tmp = new Item(index,Item.TYPE_ITEM_GENERIC,"Ruby",87);
      tmp.setPrice(300);
    }
    if (index == 127) {
      tmp = new Item(index,Item.TYPE_ITEM_GENERIC,"Sapphire",88);
      tmp.setPrice(50);
    }
    if (index == 128) {
      tmp = new Item(index,Item.TYPE_ITEM_GENERIC,"Emerald",89);
      tmp.setPrice(170);
    }
    if (index == 131) {
      tmp = new Item(index,Item.TYPE_ITEM_GENERIC,"Lesser random item",35);
      tmp.setPrice(25);
      tmp.setMagical(false);
      tmp.setRandomItem(false);
    }
    if (index == 132) {
      tmp = new Item(index,Item.TYPE_ITEM_GENERIC,"Random item",0);
      tmp.setPrice(150);
      tmp.setMagical(false);
      tmp.setRandomItem(false);
    }
    if (index == 133) {
      tmp = new Item(index,Item.TYPE_ITEM_GENERIC,"High random item",3);
      tmp.setPrice(600);
      tmp.setMagical(false);
      tmp.setRandomItem(false);
    }
    if (index == 134) {
      tmp = new Item(index,Item.TYPE_ITEM_GENERIC,"Magic random item",86);
      tmp.setPrice(1000);
      tmp.setMagical(false);
      tmp.setRandomItem(false);
    }
    if (index == 145) {
      tmp = new Item(index,Item.TYPE_ITEM_RING,"Golden ring",91);
      tmp.setPrice(200);
      tmp.setWeight(1);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);

    }
    if (index == 146) {
      tmp = new Item(index,Item.TYPE_ITEM_RING,"Ring of Defense",92);
      tmp.setPrice(250);
      tmp.setMagical(true);
      tmp.setWeight(1);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden ring");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_DODGING);
      tmp.setEffectValue(10);
    }    
    if (index == 147) {
      tmp = new Item(index,Item.TYPE_ITEM_RING,"Ring of Swordplay",93);
      tmp.setPrice(250);
      tmp.setMagical(true);
      tmp.setWeight(1);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden ring");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_MELEE);
      tmp.setEffectValue(10);
      
    }
    if (index == 148) {
      tmp = new Item(index,Item.TYPE_ITEM_RING,"Ring of Qi",91);
      tmp.setPrice(250);
      tmp.setMagical(true);
      tmp.setWeight(1);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden ring");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_QI_MAGIC);
      tmp.setEffectValue(10);
     
    }
    if (index == 149) {
      tmp = new Item(index,Item.TYPE_ITEM_RING,"Ring of Sorcery",91);
      tmp.setPrice(250);
      tmp.setMagical(true);
      tmp.setWeight(1);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden ring");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_SORCERY);
      tmp.setEffectValue(10);
     
    }
    if (index == 150) {
      tmp = new Item(index,Item.TYPE_ITEM_RING,"Ring of Wizardy",92);
      tmp.setPrice(250);
      tmp.setMagical(true);
      tmp.setWeight(1);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden ring");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_WIZARDRY);
      tmp.setEffectValue(10);
     
    }
    if (index == 151) {
      tmp = new Item(index,Item.TYPE_ITEM_RING,"Ring of Furious Fist",93);
      tmp.setPrice(250);
      tmp.setMagical(true);
      tmp.setWeight(1);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden ring");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_UNARMED);
      tmp.setEffectValue(10);
     
    }
    if (index == 152) {
      tmp = new Item(index,Item.TYPE_ITEM_RING,"Ring of Diplomacy",93);
      tmp.setPrice(250);
      tmp.setMagical(true);
      tmp.setWeight(1);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden ring");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_DIPLOMACY);
      tmp.setEffectValue(10);
     
    }
    if (index == 151) {
      tmp = new Item(index,Item.TYPE_ITEM_RING,"Ring of Burden",91);
      tmp.setPrice(200);
      tmp.setMagical(true);
      tmp.setWeight(10);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden ring");
      tmp.setEffect(CharEffect.EFFECT_ON_CARRYING_CAPACITY);
      tmp.setEffectAttrOrSkill(Character.SKILL_DIPLOMACY);
      tmp.setEffectValue(-10);
     
    }
    if (index == 177) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Tomb key",38);
      tmp.setPrice(20);
      tmp.setKeyValue("Tomb key");
      tmp.setRandomItem(false);
    }
    if (index == 178) {
      tmp = new Item(index,Item.TYPE_ITEM_PICKLOCK,"Hairpin",98);
      tmp.setPrice(10);
      tmp.setBonusPickLock(0);
    }
    if (index == 179) {
      tmp = new Item(index,Item.TYPE_ITEM_PICKLOCK,"Toolkit",99);
      tmp.setPrice(60);
      tmp.setWeight(8);
      tmp.setBonusPickLock(10);
    }
    if (index == 181) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Logbook",100);
      tmp.setPrice(35);
      tmp.setRandomItem(false);
    }
    if (index == 184) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Ancient key",103);
      tmp.setPrice(20);
      tmp.setKeyValue("Ancient key");
      tmp.setRandomItem(false);
    }
    if (index == 187) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Moonstone",106);
      tmp.setMagical(true);
      tmp.setPrice(200);
    }
    if (index == 192) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Archmage key",48);
      tmp.setPrice(20);
      tmp.setKeyValue("Archmage Key");
      tmp.setRandomItem(false);
    }
    if (index == 193) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Cellar key",49);
      tmp.setPrice(20);
      tmp.setKeyValue("Mage guild cellar key");
      tmp.setRandomItem(false);
    }
    if (index == 196) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Beastery key",49);
      tmp.setPrice(10);
      tmp.setKeyValue("Mage guild beastery key");
      tmp.setRandomItem(false);
    }
    if (index == 201) {
      tmp = new Item(index,Item.TYPE_ITEM_GENERIC,"Ancient Scroll",111);
      tmp.setPrice(50);
      tmp.setRandomItem(false);
    }
    if (index == 202) {
      tmp = new Item(index,Item.TYPE_ITEM_PICKLOCK,"Master Key",112);
      tmp.setPrice(400);
      tmp.setBonusPickLock(50);
      tmp.setWeight(5);
      tmp.setMagical(true);
      tmp.setRandomItem(false);
    }
    if (index == 203) {
        tmp = new Item(index,Item.TYPE_ITEM_RING,"Northfork Family Ring",92);
        tmp.setPrice(150);
        tmp.setMagical(true);
        tmp.setWeight(1);
        tmp.setDefensiveValue(0);
        tmp.setArmorValue(0);
        tmp.setCriticalArmorValue(0);
        tmp.setMagical(true);
        tmp.setRandomItem(false);
        tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
        tmp.setUnknownName("Golden ring");
        tmp.setEffect(CharEffect.EFFECT_ON_ATTRIBUTE);
        tmp.setEffectAttrOrSkill(Character.ATTRIBUTE_CHARISMA);
        tmp.setEffectValue(1);
        
      }
    if (index == 217) {
      tmp = new Item(index,Item.TYPE_ITEM_HEALING_KIT,"Healing kit",121);
      tmp.setPrice(20);
      tmp.setWeight(25);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH);
      tmp.setEffectValue(1);
      tmp.setEffectLasting(20);
    }
    if (index == 218) {
      tmp = new Item(index,Item.TYPE_ITEM_HEALING_KIT,"Master healing kit",121);
      tmp.setPrice(40);
      tmp.setWeight(35);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH_AND_STAMINA);
      tmp.setEffectValue(1);
      tmp.setEffectLasting(30);
    }
    if (index == 224) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Larek's fishing rod",124);
      tmp.setPrice(30);
      tmp.setRandomItem(false);
    }
    if (index == 225) {
      tmp = new Item(index,Item.TYPE_ITEM_SINGLESHOT,"Raw fish",125);
      tmp.setPrice(10);
      tmp.setWeight(8);
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH);
      tmp.setEffectValue(2);
      tmp.setEffectLasting(4);
    }
    if (index == 226) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Troll head",126);
      tmp.setWeight(80);
      tmp.setPrice(0);
    }
    if (index == 227) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Treasure map",127);
      tmp.setWeight(1);
      tmp.setPrice(80);
    }
    if (index == 228) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Rope ladder",128);
      tmp.setWeight(1);
      tmp.setPrice(10);
    }
    if (index == 229) {
      tmp = new Item(index,Item.TYPE_ITEM_DIGGING_TOOL,"Shovel",129);
      tmp.setWeight(20);
      tmp.setPrice(20);
    }
    if (index == 230) {
      tmp = new Item(index,Item.TYPE_ITEM_MAGICL_COMPASS,"Voodoo head",130);
      tmp.setUnknownName("Head on string");
      tmp.setRandomItem(false);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setWeight(10);
      tmp.setPrice(20);
      tmp.setMagical(true);
    }
    if (index == 231) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Treasure chamber key",131);
      tmp.setWeight(1);
      tmp.setKeyValue("Treasure chamber key");
      tmp.setPrice(20);
    }
    if (index == 237) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Aconitum",133);
      tmp.setWeight(1);
      tmp.setPrice(80);
    }
    if (index == 238) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Seed of Good",134);
      tmp.setWeight(1);
      tmp.setPrice(5);
    }
    if (index == 239) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Seed of Evil",135);
      tmp.setWeight(1);
      tmp.setPrice(5);
    }
    if (index == 242) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Aconitum potion",35);
      tmp.setPrice(400);
    }
    if (index == 256) {
      tmp = new Item(index,Item.TYPE_ITEM_KEY,"Troll key",49);
      tmp.setPrice(10);
      tmp.setKeyValue("Troll Key");
      tmp.setRandomItem(false);
    }
    if (index == 259) {
      tmp = new Item(index,Item.TYPE_ITEM_TOME,"Ancient tome",144);
      tmp.setPrice(1000);
      tmp.setMagical(true);
      tmp.setWeight(50);
    }
    if (index == 260) {
      tmp = new Item(index,Item.TYPE_ITEM_TOME,"Tome of Strength",145);
      tmp.setPrice(800);
      tmp.setMagical(true);
      tmp.setWeight(50);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Old tome");
    }
    if (index == 261) {
      tmp = new Item(index,Item.TYPE_ITEM_TOME,"Tome of Agility",145);
      tmp.setPrice(800);
      tmp.setMagical(true);
      tmp.setWeight(50);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Old tome");
    }
    if (index == 262) {
      tmp = new Item(index,Item.TYPE_ITEM_TOME,"Tome of Endurance",145);
      tmp.setPrice(800);
      tmp.setMagical(true);
      tmp.setWeight(50);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Old tome");
    }
    if (index == 263) {
      tmp = new Item(index,Item.TYPE_ITEM_TOME,"Tome of Intelligence",145);
      tmp.setPrice(800);
      tmp.setMagical(true);
      tmp.setWeight(50);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Old tome");
    }
    if (index == 264) {
      tmp = new Item(index,Item.TYPE_ITEM_TOME,"Tome of Wisdom",145);
      tmp.setPrice(800);
      tmp.setMagical(true);
      tmp.setWeight(50);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Old tome");
    }
    if (index == 265) {
      tmp = new Item(index,Item.TYPE_ITEM_TOME,"Tome of Charisma",145);
      tmp.setPrice(800);
      tmp.setMagical(true);
      tmp.setWeight(50);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Old tome");
    }
    if (index == 266) {
      tmp = new Item(index,Item.TYPE_ITEM_TOME,"Tome of Luck",145);
      tmp.setPrice(800);
      tmp.setMagical(true);
      tmp.setWeight(50);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Old tome");
    }
    if (index == 267) {
      tmp = new Item(index,Item.TYPE_ITEM_TOME,"Tome of Death",145);
      tmp.setPrice(800);
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setWeight(50);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Old tome");
      tmp.setEffect(CharEffect.EFFECT_ON_HEALTH);
      tmp.setEffectValue(-1);
      tmp.setEffectLasting(500);
    }
    if (index == 268) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Strange jar",146);
      tmp.setPrice(10);
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setWeight(10);
    }
    if (index == 272) {
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Mother's locket",148);
      tmp.setPrice(200);
    }
    if (index == 273) {
      //FIXME: Wrong tile
      tmp = new Item(index,Item.TYPE_ITEM_QUEST,"Bridge repair kit",99);
      tmp.setPrice(200);
      tmp.setWeight(20);
    }

    
    return tmp;
  }

  public static Item createMoney(int amount) {
    Item tmp = null;
    if (amount > 1) {
      tmp = new Item(26,Item.TYPE_ITEM_MONEY,String.valueOf(amount)+" copper pieces",26);
    } else {
      tmp = new Item(26,Item.TYPE_ITEM_MONEY,"One copper piece",26);
    }
    tmp.setRandomItem(false);
    tmp.setPrice(amount);
    return tmp;
  }

  public static Item createWeapon(int index) {
    Item tmp = null;
    if (index == 0) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel long sword",0);
      tmp.setPrice(60);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(40);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 1) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel axe",1);
      tmp.setPrice(55);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(55);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 5) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel gauntlets",5);
      tmp.setPrice(40);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(8);
      tmp.setWeight(10);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 6) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron long sword",6);
      tmp.setPrice(40);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(7);
      tmp.setWeight(40);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 7) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron axe",7);
      tmp.setPrice(35);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(7);
      tmp.setWeight(55);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 11) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Spiked gauntlets",11);
      tmp.setPrice(45);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(8);
      tmp.setWeight(11);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 12) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze long sword",12);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(30);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 13) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze axe",13);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(45);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 17) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze gauntlets",17);
      tmp.setPrice(20);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(8);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 18) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Pick axe",18);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setWeight(50);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(4);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(4);
    }
    if (index == 24) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Long bow",24);
      tmp.setPrice(60);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(35);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 25) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Short bow",25);
      tmp.setPrice(50);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setWeight(25);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 27) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel spear",27);
      tmp.setPrice(45);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(35);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
    }
    if (index == 28) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron spear",28);
      tmp.setPrice(40);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(7);
      tmp.setWeight(35);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
    }
    if (index == 29) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze spear",29);
      tmp.setPrice(35);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(30);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
      tmp.setThrowableWeapon(true);
    }
    if (index == 30) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel mace",30);
      tmp.setPrice(50);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(10);
      tmp.setWeight(55);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 31) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron mace",31);
      tmp.setPrice(40);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(8);
      tmp.setWeight(55);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 32) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze mace",32);
      tmp.setPrice(30);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(50);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 33) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Small fangs",33);
      tmp.setPrice(15);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(3);
      tmp.setWeight(0);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setDroppable(false);
    }
    if (index == 38) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cross bow",40);
      tmp.setPrice(120);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(10);
      tmp.setWeight(55);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 42) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel short sword",44);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(30);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 43) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron short sword",45);
      tmp.setPrice(25);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(5);
      tmp.setWeight(30);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 44) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze short sword",46);
      tmp.setPrice(20);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(5);
      tmp.setWeight(25);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 53) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel dagger",50);
      tmp.setPrice(25);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setWeight(14);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
    }
    if (index == 54) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron dagger",51);
      tmp.setPrice(22);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(3);
      tmp.setWeight(15);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
    }
    if (index == 55) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze dagger",52);
      tmp.setPrice(20);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(3);
      tmp.setWeight(10);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
    }
    if (index == 77) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Sting",52);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(2);
      tmp.setWeight(0);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(4);
      tmp.setDroppable(false);
    }
    if (index == 78) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bite",52);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(0);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setDroppable(false);
    }
    if (index == 82) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Ancient Oak Staff",65);
      tmp.setPrice(600);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(25);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Old staff");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_SORCERY);
      tmp.setEffectValue(15);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(2);
    }
    if (index == 83) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Fangs",33);
      tmp.setPrice(25);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(5);
      tmp.setWeight(0);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setDroppable(false);
    }
    if (index == 88) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Rapier",67);
      tmp.setPrice(60);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(5);
      tmp.setWeight(23);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 89) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel 2-handed sword",68);
      tmp.setPrice(90);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(12);
      tmp.setWeight(60);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 90) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze 2-handed sword",69);
      tmp.setPrice(70);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(10);
      tmp.setWeight(45);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 91) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron 2-handed sword",70);
      tmp.setPrice(80);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(11);
      tmp.setWeight(55);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 93) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Sling",72);
      tmp.setPrice(30);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(8);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(4);
    }
    if (index == 94) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze battle axe",73);
      tmp.setPrice(60);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(60);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 95) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron battle axe",74);
      tmp.setPrice(70);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(9);
      tmp.setWeight(75);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 96) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel battle axe",75);
      tmp.setPrice(80);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(10);
      tmp.setWeight(75);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 98) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron flail",77);
      tmp.setPrice(60);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(10);
      tmp.setWeight(55);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 99) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel flail",78);
      tmp.setPrice(70);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(12);
      tmp.setWeight(55);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 100) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron warhammer",79);
      tmp.setPrice(70);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(16);
      tmp.setWeight(70);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 101) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel warhammer",80);
      tmp.setPrice(80);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(18);
      tmp.setWeight(70);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 107) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed long sword",0);
      tmp.setUnknownName("Steel long sword");
      tmp.setPrice(60);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(50);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
    }
    if (index == 108) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed axe",1);
      tmp.setUnknownName("Steel axe");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(55);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(60);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 111) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed gauntlets",5);
      tmp.setUnknownName("Steel gauntlets");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(40);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(8);
      tmp.setWeight(15);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 112) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed pick axe",18);
      tmp.setUnknownName("Pick axe");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setWeight(55);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(4);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(4);
    }
    if (index == 113) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed long bow",24);
      tmp.setUnknownName("Long bow");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(60);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(40);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 114) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed spear",27);
      tmp.setUnknownName("Steel spear");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(45);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(40);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
    }
    if (index == 115) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed cross bow",40);
      tmp.setUnknownName("Cross bow");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(120);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(10);
      tmp.setWeight(60);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 116) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Sniper's bow",40);
      tmp.setUnknownName("Cross bow");
      tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_RANGED_WEAPONS);
      tmp.setEffectValue(15);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(2);
      tmp.setPrice(400);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(10);
      tmp.setWeight(55);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 117) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed devil sword",86);
      tmp.setUnknownName("Curved short sword");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(450);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(3);
      tmp.setWeight(35);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_HEALTH);
      tmp.setEffectValue(1);
    }
    if (index == 118) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed rapier",67);
      tmp.setUnknownName("Rapier");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(60);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(5);
      tmp.setWeight(27);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 119) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Pirate's rapier",67);
      tmp.setUnknownName("Rapier");
      tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);      
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_DODGING);
      tmp.setEffectValue(15);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(3);
      tmp.setPrice(500);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(5);
      tmp.setWeight(23);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 129) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Combosite bow",24);
      tmp.setPrice(80);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(38);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 130) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Shurikens",90);
      tmp.setPrice(40);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(3);
      tmp.setWeight(10);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(4);
    }
    if (index == 144) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Brunor's kung-fu staff",65);
      tmp.setPrice(600);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(8);
      tmp.setWeight(25);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setMagical(true);
      tmp.setRandomItem(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Brunor's staff");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_DODGING);
      tmp.setEffectValue(15);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(2);
    }
    if (index == 185) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Cursed staff of evil",104);
      tmp.setUnknownName("Staff");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(500);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(8);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(3);
      tmp.setWeight(40);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_SORCERY);
      tmp.setEffectValue(10);
    }
    if (index == 191) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Magic Blade",110);
      tmp.setUnknownName("Long sword");
      tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);      
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(5);
      tmp.setPrice(500);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(40);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 197) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Tentacle",52);
      tmp.setPrice(30);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(10);
      tmp.setWeight(0);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setDroppable(false);
    }
    if (index == 198) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Big Bite",52);
      tmp.setPrice(50);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(10);
      tmp.setWeight(0);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setDroppable(false);
    }
    if (index == 209) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze javelin",113);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setWeight(20);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setThrowableWeapon(true);
    }
    if (index == 210) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron javelin",114);
      tmp.setPrice(40);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(5);
      tmp.setWeight(23);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
    }
    if (index == 211) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel javelin",115);
      tmp.setPrice(50);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(23);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
    }
    if (index == 212) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Bronze throwing axe",116);
      tmp.setPrice(25);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setWeight(15);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setThrowableWeapon(true);
    }
    if (index == 213) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Iron throwing axe",117);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(5);
      tmp.setWeight(18);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setThrowableWeapon(true);
    }
    if (index == 214) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Steel throwing axe",118);
      tmp.setPrice(35);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(18);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setThrowableWeapon(true);
    }
    if (index == 215) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Hurling Javelin",119);
      tmp.setUnknownName("Steel javelin");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(300);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(2);
      tmp.setWeight(23);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_RANGED_WEAPONS);
      tmp.setEffectValue(10);
    }
    if (index == 216) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Dagger of Light",120);
      tmp.setUnknownName("Steel dagger");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(150);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(2);
      tmp.setWeight(5);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
      tmp.setEffect(CharEffect.EFFECT_ON_LIGHT);
      tmp.setEffectValue(3);
    }
    if (index == 233) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Rapier of blood",67);
      tmp.setUnknownName("Rapier");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setMinMagicDamage(2);
      tmp.setMaxMagicDamage(5);
      tmp.setPrice(500);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(5);
      tmp.setWeight(23);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(4);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(4);
    }
    if (index == 234) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Magical Hoe",132);
      tmp.setUnknownName("Hoe");
      tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setRandomItem(false);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(3);
      tmp.setPrice(150);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(40);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setAttackType(AttackType.MAGIC);
    }
    if (index == 235) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Ranger's axe",1);
      tmp.setUnknownName("Steel axe");
      tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(155);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(3);
      tmp.setWeight(50);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 236) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Ranger's bow",24);
      tmp.setUnknownName("Long bow");
      tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(180);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(3);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(33);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(3);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
    }
    if (index == 240) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Sword of barbarian",68);
      tmp.setUnknownName("Steel 2-handed sword");
     tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(500);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(12);
      tmp.setWeight(60);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(4);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
      tmp.setEffect(CharEffect.EFFECT_ON_ATTRIBUTE);
      tmp.setEffectAttrOrSkill(Character.ATTRIBUTE_STRENGTH);
      tmp.setEffectValue(2);
    }
    if (index == 245) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Boomerang of stamina",137);
      tmp.setUnknownName("Boomerang");
      tmp.setMagical(true);
      tmp.setRandomItem(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(300);
      tmp.setMinDamage(4);
      tmp.setMaxDamage(12);
      tmp.setWeight(5);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_RANGED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(false);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_STAMINA);
      tmp.setEffectValue(1);
    }
    if (index == 246) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Mace of lifeforce",139);
      tmp.setUnknownName("Steel mace");
      tmp.setMagical(true);
      tmp.setRandomItem(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(300);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(4);
      tmp.setCursed(true);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(10);
      tmp.setWeight(55);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setThrowableWeapon(false);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_HEALTH);
      tmp.setEffectValue(1);
    }
    if (index == 247) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Short sword of slaying",140);
      tmp.setUnknownName("Steel short sword");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(350);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setWeight(30);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setEffect(CharEffect.EFFECT_SLAYER);
      tmp.setEffectValue(1);
    }
    if (index == 248) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Short sword of vigor",141);
      tmp.setUnknownName("Steel short sword");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(300);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(6);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(3);
      tmp.setWeight(30);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_VIGOR);
      tmp.setEffectValue(1);
    }
    if (index == 249) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Vampiric dagger",142);
      tmp.setUnknownName("Steel dagger");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(250);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(4);
      tmp.setWeight(5);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_HEALTH);
      tmp.setEffectValue(1);
    }
    if (index == 250) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Dagger of vigor",142);
      tmp.setUnknownName("Steel dagger");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(225);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(4);
      tmp.setWeight(5);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(true);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_VIGOR);
      tmp.setEffectValue(1);
    }
    if (index == 251) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Gauntlets of stamina",5);
      tmp.setUnknownName("Steel gauntlets");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(250);
      tmp.setMinDamage(4);
      tmp.setMaxDamage(12);
      tmp.setWeight(10);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(3);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_STAMINA);
      tmp.setEffectValue(1);
    }
    if (index == 252) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Gauntlets of slaying",11);
      tmp.setUnknownName("Spiked gauntlets");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(300);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(8);
      tmp.setWeight(11);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
      tmp.setEffect(CharEffect.EFFECT_SLAYER);
      tmp.setEffectValue(1);
    }
    if (index == 253) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Vampiric gauntlets",11);
      tmp.setUnknownName("Spiked gauntlets");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(300);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(8);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(4);
      tmp.setWeight(11);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_HEALTH);
      tmp.setEffectValue(1);
    }
    if (index == 254) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"VampiricBite",52);
      tmp.setPrice(30);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setWeight(0);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_UNARMED);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(4);
      tmp.setDroppable(false);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_HEALTH);
      tmp.setEffectValue(1);
    }
    if (index == 255) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Club of stamina",32);
      tmp.setUnknownName("Wooden club");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(300);
      tmp.setMinDamage(4);
      tmp.setMaxDamage(12);
      tmp.setWeight(50);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_STAMINA);
      tmp.setEffectValue(1);
    }
    if (index == 257) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Flail of stamina",78);
      tmp.setUnknownName("Steel flail");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(325);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(12);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(4);
      tmp.setWeight(53);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(3);
      tmp.setThrowableWeapon(false);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_STAMINA);
      tmp.setEffectValue(1);
    }
    if (index == 258) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Brutal warhammer",80);
      tmp.setUnknownName("Steel warhammer");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(400);
      tmp.setCursed(true);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(18);
      tmp.setMinMagicDamage(2);
      tmp.setMaxMagicDamage(8);
      tmp.setWeight(70);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(true);
      tmp.setCriticalMultiplier(2);
    }
    if (index == 269) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Axe of Might",1);
      tmp.setUnknownName("Steel axe");
      tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(200);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(8);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(2);
      tmp.setWeight(50);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(2);
      tmp.setEffect(CharEffect.EFFECT_ON_MIGHTY);
      tmp.setEffectValue(10);

    }
    if (index == 270) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Fairy's Sickle",147);
      tmp.setUnknownName("Sickle");
      tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setRandomItem(false);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(4);
      tmp.setPrice(200);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(3);
      tmp.setWeight(20);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(4);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_WIZARDRY);
      tmp.setEffectValue(10);
      tmp.setAttackType(AttackType.MAGIC);
    }
    if (index == 271) {
      tmp = new Item(index,Item.TYPE_ITEM_WEAPON,"Sickle of Health",147);
      tmp.setUnknownName("Sickle");
      tmp.setMagical(true);
      tmp.setCursed(false);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(4);
      tmp.setPrice(175);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(3);
      tmp.setWeight(20);
      tmp.setBluntWeapon(false);
      tmp.setPiercingPower(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setCriticalMultiplier(4);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_HEALTH);
      tmp.setEffectValue(1);
    }

    
    return tmp;
  }

  public static Item createShield(int index) {
    Item tmp = null;
    if (index == 2) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Steel round shield",2);
      tmp.setPrice(60);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(4);
      tmp.setWeight(60);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(25);
      tmp.setArmorValue(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(80);      
    }
    if (index == 8) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Iron round shield",8);
      tmp.setPrice(40);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(4);
      tmp.setWeight(60);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(25);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(80);      
    }
    if (index == 14) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Bronze round shield",14);
      tmp.setPrice(30);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(4);
      tmp.setWeight(50);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(1);
      tmp.setDefensiveValue(25);
      tmp.setArmorValue(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(85);      
    }
    if (index == 21) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Steel kite shield",21);
      tmp.setPrice(80);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(80);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(35);
      tmp.setArmorValue(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(75);      
    }
    if (index == 22) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Iron kite shield",22);
      tmp.setPrice(60);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(80);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(35);
      tmp.setCriticalArmorValue(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(75);      
    }
    if (index == 23) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Bronze kite shield",23);
      tmp.setPrice(50);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(70);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(35);
      tmp.setArmorValue(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(75);      
    }
    if (index == 109) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Cursed round shield",2);
      tmp.setUnknownName("Steel round shield");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(60);
      tmp.setMinDamage(1);
      tmp.setMaxDamage(4);
      tmp.setWeight(65);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(25);
      tmp.setArmorValue(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(80);      
    }
    if (index == 182) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Steel tower shield",101);
      tmp.setPrice(120);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(90);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(50);
      tmp.setArmorValue(2);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(65);      
    }
    if (index == 183) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Shield of Magic",102);
      tmp.setUnknownName("Steel tower shield");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(450);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(90);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(50);
      tmp.setArmorValue(2);
      tmp.setEffect(CharEffect.EFFECT_ON_WILLPOWER);
      tmp.setEffectValue(20);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(65);      
    }
    if (index == 219) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Buckler",122);
      tmp.setPrice(30);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(4);
      tmp.setWeight(25);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(1);
      tmp.setDefensiveValue(20);
      tmp.setArmorValue(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(150);      
    }
    if (index == 220) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Buckler of Protection",122);
      tmp.setPrice(300);
      tmp.setUnknownName("Buckler");
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setMagical(true);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(4);
      tmp.setWeight(25);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(1);
      tmp.setDefensiveValue(20);
      tmp.setMagicalArmorValue(1);
      tmp.setMagicalDefensiveValue(15);
      tmp.setArmorValue(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(150);      
    }
    if (index == 221) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Cursed buckler",122);
      tmp.setPrice(30);
      tmp.setUnknownName("Buckler");
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(4);
      tmp.setWeight(40);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(1);
      tmp.setDefensiveValue(20);
      tmp.setArmorValue(0);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(100);      
    }
    if (index == 222) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Royal Shield",123);
      tmp.setUnknownName("Steel kite shield");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(350);
      
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(80);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(35);
      tmp.setMagicalDefensiveValue(10);
      tmp.setArmorValue(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(75);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_DIPLOMACY);
      tmp.setEffectValue(10);

    }
    if (index == 223) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Cursed kite shield",21);
      tmp.setUnknownName("Steel kite shield");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setCursed(true);
      tmp.setPrice(80);
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setWeight(100);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(35);
      tmp.setArmorValue(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(75);      
    }
    if (index == 243) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Magical oak shield",136);
      tmp.setUnknownName("Wooden shield");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(300);
      
      tmp.setMinDamage(2);
      tmp.setMaxDamage(4);
      tmp.setWeight(40);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(25);
      tmp.setMagicalDefensiveValue(10);
      tmp.setArmorValue(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(95);
      tmp.setEffect(CharEffect.EFFECT_ON_WILLPOWER);
      tmp.setEffectValue(10);
    }
    if (index == 244) {
      tmp = new Item(index,Item.TYPE_ITEM_SHIELD,"Skull shield",138);
      tmp.setUnknownName("Steel kite shield");
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(350);      
      tmp.setMinDamage(2);
      tmp.setMaxDamage(6);
      tmp.setMinMagicDamage(1);
      tmp.setMaxMagicDamage(4);
      tmp.setWeight(85);
      tmp.setBluntWeapon(true);
      tmp.setPiercingPower(0);
      tmp.setCriticalMultiplier(2);
      tmp.setDefensiveValue(35);
      tmp.setMagicalDefensiveValue(10);
      tmp.setArmorValue(1);
      tmp.setWeaponSkill(Item.WEAPON_SKILL_MELEE);
      tmp.setTwoHandedWeapon(false);
      tmp.setMaxDodgeValue(70);
      tmp.setEffect(CharEffect.EFFECT_DRAIN_HEALTH);
      tmp.setEffectValue(1);

    }


    return tmp;
  }

  public static Item createArmor(int index) {
    Item tmp = null;
    if (index == 3) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Steel plate mail",3);
      tmp.setPrice(500);
      tmp.setWeight(250);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(8);
      tmp.setMaxDodgeValue(40);
    }
    if (index == 4) {
      tmp = new Item(index,Item.TYPE_ITEM_HEADGEAR,"Steel helmet",4);
      tmp.setPrice(40);
      tmp.setWeight(10);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(1);
      tmp.setCriticalArmorValue(1);
    }
    if (index == 9) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Iron plate mail",9);
      tmp.setPrice(450);
      tmp.setWeight(250);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(7);
      tmp.setMaxDodgeValue(40);
    }
    if (index == 10) {
      tmp = new Item(index,Item.TYPE_ITEM_HEADGEAR,"Iron helmet",10);
      tmp.setPrice(30);
      tmp.setWeight(10);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(1);
    }
    if (index == 15) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Bronze plate mail",15);
      tmp.setPrice(375);
      tmp.setWeight(200);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(6);
      tmp.setMaxDodgeValue(45);
    }
    if (index == 16) {
      tmp = new Item(index,Item.TYPE_ITEM_HEADGEAR,"Bronze helmet",16);
      tmp.setPrice(20);
      tmp.setWeight(8);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(1);
    }
    if (index == 19) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Studded leather armor",19);
      tmp.setPrice(80);
      tmp.setWeight(35);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(3);
      tmp.setMaxDodgeValue(80);
    }
    if (index == 20) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Leather armor",20);
      tmp.setPrice(60);
      tmp.setWeight(20);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(2);
      tmp.setMaxDodgeValue(100);
    }
    if (index == 34) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Fur",34);
      tmp.setDroppable(false);
      tmp.setPrice(25);
      tmp.setWeight(0);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(1);
      tmp.setMaxDodgeValue(150);
    }
    if (index == 39) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Chain Shirt",41);
      tmp.setPrice(120);
      tmp.setWeight(40);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(4);
      tmp.setMaxDodgeValue(75);
    }
    if (index == 40) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Crudge leather armor",42);
      tmp.setPrice(30);
      tmp.setWeight(40);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(2);
      tmp.setMaxDodgeValue(75);
    }
    if (index == 41) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Padded cloth jacket",43);
      tmp.setPrice(30);
      tmp.setWeight(30);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(1);
      tmp.setMaxDodgeValue(150);
    }
    if (index == 79) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Chitin armor",42);
      tmp.setDroppable(false);
      tmp.setPrice(80);
      tmp.setWeight(0);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(3);
      tmp.setCriticalArmorValue(2);
      tmp.setMaxDodgeValue(80);
    }
    if (index == 81) {
      tmp = new Item(index,Item.TYPE_ITEM_HEADGEAR,"Gray Witch Hat",64);
      tmp.setPrice(500);
      tmp.setWeight(8);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Witch Hat");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_WIZARDRY);
      tmp.setEffectValue(15);
      tmp.setRandomItem(false);
    }
    if (index == 84) {
      tmp = new Item(index,Item.TYPE_ITEM_AMULET,"Wenni's Amulet",66);
      tmp.setPrice(100);
      tmp.setWeight(4);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Wenni's Amulet");
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_DIPLOMACY);
      tmp.setEffectValue(15);
      tmp.setRandomItem(false);
    }
    if (index == 92) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Bronze chest plate",71);
      tmp.setPrice(200);
      tmp.setWeight(80);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(5);
      tmp.setMaxDodgeValue(60);
    }
    if (index == 104) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Splint mail",83);
      tmp.setPrice(400);
      tmp.setWeight(120);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(6);
      tmp.setMaxDodgeValue(50);
    }
    if (index == 105) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Long overcoat",84);
      tmp.setPrice(45);
      tmp.setWeight(34);
      tmp.setDefensiveValue(5);
      tmp.setArmorValue(1);
      tmp.setMaxDodgeValue(150);
    }
    if (index == 106) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Adventurer's cloak",85);
      tmp.setPrice(40);
      tmp.setWeight(10);
      tmp.setDefensiveValue(15);
      tmp.setArmorValue(0);
      tmp.setMaxDodgeValue(200);
      tmp.setCriticalArmorValue(1);
    }
    if (index == 110) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Cursed plate mail",3);
      tmp.setUnknownName("Steel plate mail");
      tmp.setMagical(true);
      tmp.setCursed(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(500);
      tmp.setWeight(270);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(8);
      tmp.setMaxDodgeValue(40);
    }
    if (index == 153) {
      tmp = new Item(index,Item.TYPE_ITEM_AMULET,"Amulet of Luck",96);
      tmp.setPrice(100);
      tmp.setWeight(4);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden amulet");
      tmp.setEffect(CharEffect.EFFECT_ON_ATTRIBUTE);
      tmp.setEffectAttrOrSkill(Character.ATTRIBUTE_LUCK);
      tmp.setEffectValue(1);
    }
    if (index == 154) {
      tmp = new Item(index,Item.TYPE_ITEM_AMULET,"Amulet of Strength",96);
      tmp.setPrice(100);
      tmp.setWeight(4);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden amulet");
      tmp.setEffect(CharEffect.EFFECT_ON_ATTRIBUTE);
      tmp.setEffectAttrOrSkill(Character.ATTRIBUTE_STRENGTH);
      tmp.setEffectValue(1);
    }
    if (index == 155) {
      tmp = new Item(index,Item.TYPE_ITEM_AMULET,"Amulet of Willpower",97);
      tmp.setPrice(160);
      tmp.setWeight(4);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setUnknownName("Golden amulet");
      tmp.setEffect(CharEffect.EFFECT_ON_WILLPOWER);
      tmp.setEffectAttrOrSkill(Character.ATTRIBUTE_STRENGTH);
      tmp.setEffectValue(10);
    }
    if (index == 156) {
      tmp = new Item(index,Item.TYPE_ITEM_BOOTS,"Traveller's Boots",94);
      tmp.setPrice(50);
      tmp.setWeight(8);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(false);
      tmp.setEffect(CharEffect.EFFECT_ON_CARRYING_CAPACITY);
      tmp.setEffectAttrOrSkill(Character.ATTRIBUTE_STRENGTH);
      tmp.setEffectValue(5);
    }
    if (index == 157) {
      tmp = new Item(index,Item.TYPE_ITEM_BOOTS,"Warrior's Boots",94);
      tmp.setPrice(50);
      tmp.setWeight(8);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(1);
      tmp.setMagical(false);
    }
    if (index == 158) {
      tmp = new Item(index,Item.TYPE_ITEM_BOOTS,"Boots of Dodging",95);
      tmp.setPrice(300);
      tmp.setWeight(8);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(0);
      tmp.setMagical(true);
      tmp.setUnknownName("Leather boots");
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_DODGING);
      tmp.setEffectValue(15);
    }
    if (index == 186) {
      tmp = new Item(index,Item.TYPE_ITEM_BOOTS,"Boots of kickass",105);
      tmp.setPrice(275);
      tmp.setWeight(8);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(0);
      tmp.setCriticalArmorValue(1);
      tmp.setMagical(true);
      tmp.setUnknownName("Leather boots");
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_UNARMED);
      tmp.setEffectValue(10);
    }
    if (index == 188) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Robe of Wizard",107);
      tmp.setPrice(500);
      tmp.setWeight(10);
      tmp.setDefensiveValue(5);
      tmp.setMagical(true);
      tmp.setUnknownName("Robe");
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_WIZARDRY);
      tmp.setEffectValue(15);
      tmp.setArmorValue(1);
      tmp.setMagicalArmorValue(1);
      tmp.setMaxDodgeValue(150);
    }
    if (index == 189) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Robe of Sorcerer",108);
      tmp.setPrice(500);
      tmp.setWeight(10);
      tmp.setDefensiveValue(5);
      tmp.setMagical(true);
      tmp.setUnknownName("Robe");
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_SORCERY);
      tmp.setEffectValue(15);
      tmp.setArmorValue(1);
      tmp.setMagicalArmorValue(1);
      tmp.setMaxDodgeValue(150);
    }
    if (index == 190) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Robe of Adept",109);
      tmp.setPrice(500);
      tmp.setWeight(10);
      tmp.setDefensiveValue(5);
      tmp.setMagical(true);
      tmp.setUnknownName("Robe");
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_QI_MAGIC);
      tmp.setEffectValue(15);
      tmp.setArmorValue(1);
      tmp.setMagicalArmorValue(1);
      tmp.setMaxDodgeValue(150);
    }
    if (index == 199) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Shell",34);
      tmp.setDroppable(false);
      tmp.setPrice(200);
      tmp.setWeight(0);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(6);
      tmp.setMaxDodgeValue(60);
    }
    if (index == 200) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"HeavyArmor",34);
      tmp.setDroppable(false);
      tmp.setPrice(300);
      tmp.setWeight(0);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(7);
      tmp.setMaxDodgeValue(40);
    }
    if (index == 232) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Pirate Captains's coat",84);
      tmp.setPrice(300);
      tmp.setWeight(34);
      tmp.setDefensiveValue(5);
      tmp.setArmorValue(1);
      tmp.setMaxDodgeValue(150);
      tmp.setMagical(true);
      tmp.setUnknownName("Long overcoat");
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_BARTERING);
      tmp.setEffectValue(15);
      tmp.setMagicalArmorValue(1);
    }
    if (index == 241) {
      tmp = new Item(index,Item.TYPE_ITEM_ARMOR,"Armor of barbarian",20);
      tmp.setMagical(true);
      tmp.setUnknownName("Leather armor");
      tmp.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
      tmp.setPrice(300);
      tmp.setWeight(20);
      tmp.setDefensiveValue(0);
      tmp.setArmorValue(2);
      tmp.setMaxDodgeValue(100);
      tmp.setMagicalArmorValue(2);
      tmp.setEffect(CharEffect.EFFECT_ON_SKILL);
      tmp.setEffectAttrOrSkill(Character.SKILL_MELEE);
      tmp.setEffectValue(10);
    }

    return tmp;
  }

  public static Item readItem(DataInputStream is) throws IOException {
    byte type = is.readByte();
    int index = is.readInt();
    byte status = is.readByte();
    int price = 0;
    Item item;
    if (type == Item.TYPE_ITEM_MONEY) {
       price = is.readInt(); 
       item = ItemFactory.createMoney(price);
    } else {
    item = ItemFactory.Create(index);
    
    item.setItemStatus(status);}
    return item;
  }
  
  /**
   * Read Map item from Input stream
   * @param is DataInputStream which was used to read the map
   * @param mapVersion Map Version currently reading
   * @return Item
   * @throws IOException
   */
  public static Item readMapItem(DataInputStream is,String mapVersion) throws IOException {
    Item item = readItem(is);
    int x = is.readInt();
    int y = is.readInt();
    item.putItemOnMap(x, y);
    return item;
  }
  
  /**
   * Creates a new item from another one
   * @param copyFrom Item
   * @return Item
   */
  public static Item copy(Item copyFrom) {
    byte type = copyFrom.getType();
    int index = copyFrom.getIndex();
    byte status = copyFrom.getItemStatus();
    int price = copyFrom.getPrice();
    Item item;
    if (type == Item.TYPE_ITEM_MONEY) {
      item = ItemFactory.createMoney(price);
    } else {
      item = ItemFactory.Create(index);
    }
    item.setItemStatus(status);
    return item;
  }
  
}
