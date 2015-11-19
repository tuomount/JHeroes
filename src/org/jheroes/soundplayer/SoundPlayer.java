package org.jheroes.soundplayer;

import java.util.ArrayList;

import org.jheroes.map.DiceGenerator;

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
 * Contains URLs for the soundfile and adds playable sounds to list where
 * another thread picks them up and plays
 *
 */
public class SoundPlayer {

  public static String SOUND_FILE_DOOR_OPEN = "/res/sounds/dooropen.wav";
  public static String SOUND_FILE_SWING = "/res/sounds/swing.wav";
  public static String SOUND_FILE_HIT = "/res/sounds/hit.wav";
  public static String SOUND_FILE_HORSE = "/res/sounds/horse.wav";
  public static String SOUND_FILE_BLOCK = "/res/sounds/block.wav";
  public static String SOUND_FILE_FLAMES = "/res/sounds/flames.wav";
  public static String SOUND_FILE_CRICKET1 = "/res/sounds/cricket1.wav";
  public static String SOUND_FILE_CRICKET2 = "/res/sounds/cricket2.wav";
  public static String SOUND_FILE_MAGIC1 = "/res/sounds/magic1.wav";
  public static String SOUND_FILE_MAGIC2 = "/res/sounds/magic2.wav";
  public static String SOUND_FILE_MAGIC3 = "/res/sounds/magic3.wav";
  public static String SOUND_FILE_MAGIC4 = "/res/sounds/magic4.wav";
  public static String SOUND_FILE_POISON = "/res/sounds/poison.wav";
  public static String SOUND_FILE_LADDER = "/res/sounds/ladder.wav";
  public static String SOUND_FILE_RAT = "/res/sounds/rat.wav";
  public static String SOUND_FILE_ZOMBIE1 = "/res/sounds/zombie-1.wav";
  public static String SOUND_FILE_ZOMBIE2 = "/res/sounds/zombie-2.wav";
  public static String SOUND_FILE_ZOMBIE3 = "/res/sounds/zombie-3.wav";
  public static String SOUND_FILE_BUY1 = "/res/sounds/buy1.wav";
  public static String SOUND_FILE_BUY2 = "/res/sounds/buy2.wav";
  public static String SOUND_FILE_ARMOR = "/res/sounds/armor.wav";
  public static String SOUND_FILE_WEAPON = "/res/sounds/weapon.wav";
  public static String SOUND_FILE_SHIELD = "/res/sounds/shield.wav";
  public static String SOUND_FILE_POTION = "/res/sounds/potion.wav";
  public static String SOUND_FILE_PICKLOCK = "/res/sounds/picklock.wav";
  public static String SOUND_FILE_LEVELUP = "/res/sounds/levelup.wav";
  public static String SOUND_FILE_SCROLL = "/res/sounds/scroll.wav";
  public static String SOUND_FILE_SLIME1 = "/res/sounds/slime1.wav";
  public static String SOUND_FILE_SLIME2 = "/res/sounds/slime2.wav";
  public static String SOUND_FILE_SLIME3 = "/res/sounds/slime3.wav";
  public static String SOUND_FILE_GIANT_BEE = "/res/sounds/giant-bee.wav";
  public static String SOUND_FILE_BITE_SMALL = "/res/sounds/bite-small.wav";
  public static String SOUND_FILE_BITE_SMALL2 = "/res/sounds/bite-small2.wav";
  public static String SOUND_FILE_BITE_SMALL3 = "/res/sounds/bite-small3.wav";
  public static String SOUND_FILE_BOW = "/res/sounds/bow.wav";
  public static String SOUND_FILE_MISSING_ARROW = "/res/sounds/missing_arrow.wav";
  public static String SOUND_FILE_EATING = "/res/sounds/eating.wav";
  public static String SOUND_FILE_HEADWEAR = "/res/sounds/headwear.wav";
  public static String SOUND_FILE_HOBGOBLIN1 = "/res/sounds/hobgoblin1.wav";
  public static String SOUND_FILE_HOBGOBLIN2 = "/res/sounds/hobgoblin2.wav";
  public static String SOUND_FILE_HOBGOBLIN3 = "/res/sounds/hobgoblin3.wav";
  public static String SOUND_FILE_TROLL1 = "/res/sounds/troll1.wav";
  public static String SOUND_FILE_TROLL2 = "/res/sounds/troll2.wav";
  public static String SOUND_FILE_TROLL3 = "/res/sounds/troll3.wav";
  public static String SOUND_FILE_TELEPORT = "/res/sounds/teleport.wav";
  public static String SOUND_FILE_WITCH = "/res/sounds/witch.wav";
  public static String SOUND_FILE_FROST = "/res/sounds/frost.wav";
  public static String SOUND_FILE_SHOCK = "/res/sounds/shock.wav";
  public static String SOUND_FILE_AMULET = "/res/sounds/amulet.wav";
  public static String SOUND_FILE_BOOTS = "/res/sounds/boots.wav";
  public static String SOUND_FILE_LOCKEDDOOR = "/res/sounds/lockeddoor.wav";
  public static String SOUND_FILE_PICKLOCK_FAIL = "/res/sounds/picklock-fail.wav";
  public static String SOUND_FILE_RING = "/res/sounds/ring.wav";
  public static String SOUND_FILE_DARKNESS = "/res/sounds/darkness.wav";
  public static String SOUND_FILE_SKELETON1 = "/res/sounds/skeleton1.wav";
  public static String SOUND_FILE_SKELETON2 = "/res/sounds/skeleton2.wav";
  public static String SOUND_FILE_ANIMATED_OBJECT1 = "/res/sounds/animated-object1.wav";
  public static String SOUND_FILE_ANIMATED_OBJECT2 = "/res/sounds/animated-object2.wav";
  public static String SOUND_FILE_LICH1 = "/res/sounds/lich1.wav";
  public static String SOUND_FILE_LICH2 = "/res/sounds/lich2.wav";
  public static String SOUND_FILE_BRAIN_MONSTER1 = "/res/sounds/brain-monster.wav";
  public static String SOUND_FILE_BRAIN_MONSTER2 = "/res/sounds/brain-monster2.wav";
  public static String SOUND_FILE_CLOCK = "/res/sounds/clock.wav";
  public static String SOUND_FILE_FIREPLACE = "/res/sounds/fireplace.wav";
  public static String SOUND_FILE_BUBBLES = "/res/sounds/bubbles.wav";
  public static String SOUND_FILE_WATER_WAVE1 = "/res/sounds/water-wave1.wav";
  public static String SOUND_FILE_WATER_WAVE2 = "/res/sounds/water-wave2.wav";
  public static String SOUND_FILE_WATER_WAVE3 = "/res/sounds/water-wave3.wav";
  public static String SOUND_FILE_WATER_WAVE4 = "/res/sounds/water-wave4.wav";
  public static String SOUND_FILE_WATERDRIP = "/res/sounds/waterdrip.wav";
  public static String SOUND_FILE_BOIL = "/res/sounds/boil.wav";
  public static String SOUND_FILE_BIRDS1 = "/res/sounds/birds1.wav";
  public static String SOUND_FILE_BIRDS2 = "/res/sounds/birds2.wav";
  public static String SOUND_FILE_BIRDS3 = "/res/sounds/birds3.wav";
  public static String SOUND_FILE_BIRDS4 = "/res/sounds/birds4.wav";
  public static String SOUND_FILE_ALCHEMIST1 = "/res/sounds/alchemist1.wav";
  public static String SOUND_FILE_ALCHEMIST2 = "/res/sounds/alchemist2.wav";
  public static String SOUND_FILE_SMITH1 = "/res/sounds/smith1.wav";
  public static String SOUND_FILE_SMITH2 = "/res/sounds/smith2.wav";
  public static String SOUND_FILE_BUILD1 = "/res/sounds/build1.wav";
  public static String SOUND_FILE_BUILD2 = "/res/sounds/build2.wav";
  public static String SOUND_FILE_STREAM = "/res/sounds/stream.wav";
  public static String SOUND_FILE_SMITE = "/res/sounds/smite.wav";
  public static String SOUND_FILE_WOLFMAN = "/res/sounds/wolfman.wav";
  public static String SOUND_FILE_WATERSPLASH = "/res/sounds/watersplash.wav";
  public static String SOUND_FILE_FOUNTAIN = "/res/sounds/fountain.wav";
  public static String SOUND_FILE_RAVEN1 = "/res/sounds/raven.wav";
  public static String SOUND_FILE_RAVEN2 = "/res/sounds/raven2.wav";
  public static String SOUND_FILE_SEAGULL1 = "/res/sounds/seagull1.wav";
  public static String SOUND_FILE_SEAGULL2 = "/res/sounds/seagull2.wav";
  
  
  private static boolean soundEnabled=true;
  
  
  
  /**
   * List of sound Descriptions going to AudioManager
   */
  private static ArrayList<String> soundDescList = null;
  /**
   * List of Sound names going to AudioManager
   */
  private static ArrayList<String> soundNameList = null;
  
  /**
   * Thread for AudioManager
   */
  private static Thread AudioManagerThread = null;
  /**
   * Audio Manager
   */
  private static AudioManager audioManager = null;

  /**
   * Sound Volume default is 75%
   */
  private static int soundVolume=75;
  

  /**
   * Check if certain audio file is being played
   * @param name
   * @return boolean
   */
  public static synchronized boolean isSoundPlaying(String name) {
    if (audioManager == null) {
      return false;
    }
    if (audioManager.isSoundPlaying(name)) {
      return true;
    }
    if (soundDescList != null) {
      for (int i=0;i<soundDescList.size();i++) {
        if (soundDescList.get(i).equalsIgnoreCase(name)) {
          return true;
        }
      }
    }
    return false;
    
  }
  
  /**
   * Get Next sound name
   * @return String or null if list is empty
   */
  public static synchronized String getNextSoundName() {
    String result = null;
    if (soundNameList.size() >0) {
      result = soundNameList.get(0);
      soundNameList.remove(0);
    }
    return result;
  }
  
  /**
   * Get next Sound descripiton
   * @return String or null if list is empty
   */
  public static synchronized String getNextSoundDesc() {
    String result = null;
    if (soundDescList.size() >0) {
      result = soundDescList.get(0);
      soundDescList.remove(0);
    }
    return result;
  }
  
  

  
  /**
   * Place sound name and description into list which goes to AudioManager
   * This method also initialize lists and AudioManager if they are null
   * @param name String
   * @param soundDesc String
   */
  public static synchronized void playSound(String name,String soundDesc) {
    if (soundEnabled) {
      if (soundDescList == null || soundNameList == null) {
        soundDescList = new ArrayList<String>();
        soundNameList = new ArrayList<String>();
      }
      if (soundDescList.size() < 32) {
        soundDescList.add(soundDesc);
        soundNameList.add(name);
        if (audioManager == null) {
          audioManager = new AudioManager();
          AudioManagerThread = new Thread(audioManager);
          AudioManagerThread.start();
        }
      }
    }
  }
  
  /**
   * Play sound
   * @param name String
   */
  public static void playSound(String name) {
    playSound(name,name);
  }
  
  /**
   * Play sound by description
   * @param name String
   */
  public static void playSoundBySoundName(String name) {
    if (name.equalsIgnoreCase("SEAGULL")) {
      switch (DiceGenerator.getRandom(1)) {
      case 0: playSound(SOUND_FILE_SEAGULL1,name); break;
      case 1: playSound(SOUND_FILE_SEAGULL2,name); break;
      }
    }
    if (name.equalsIgnoreCase("RAVEN1")) {
      playSound(SOUND_FILE_RAVEN1,name);
    }
    if (name.equalsIgnoreCase("RAVEN2")) {
      playSound(SOUND_FILE_RAVEN2,name);
    }
    if (name.equalsIgnoreCase("FOUNTAIN")) {
      playSound(SOUND_FILE_FOUNTAIN,name);
    }
    if (name.equalsIgnoreCase("BUBBLES")) {
      playSound(SOUND_FILE_BUBBLES,name);
    }
    if (name.equalsIgnoreCase("SMITE")) {
      playSound(SOUND_FILE_SMITE,name);
    }
    if (name.equalsIgnoreCase("CRICKET1")) {
      playSound(SOUND_FILE_CRICKET1,name);
    }
    if (name.equalsIgnoreCase("CRICKET2")) {
      playSound(SOUND_FILE_CRICKET2,name);
    }
    if (name.equalsIgnoreCase("HORSE")) {
      playSound(SOUND_FILE_HORSE,name);
    }
    if (name.equalsIgnoreCase("ALCHEMIST1")) {
      playSound(SOUND_FILE_ALCHEMIST1,name);
    }
    if (name.equalsIgnoreCase("ALCHEMIST2")) {
      playSound(SOUND_FILE_ALCHEMIST2,name);
    }
    if (name.equalsIgnoreCase("BUILD1")) {
      playSound(SOUND_FILE_BUILD1,name);
    }
    if (name.equalsIgnoreCase("BUILD2")) {
      playSound(SOUND_FILE_BUILD2,name);
    }
    if (name.equalsIgnoreCase("STREAM")) {
      playSound(SOUND_FILE_STREAM,name);
    }
    if (name.equalsIgnoreCase("SMITH1")) {
      playSound(SOUND_FILE_SMITH1,name);
    }
    if (name.equalsIgnoreCase("SMITH2")) {
      playSound(SOUND_FILE_SMITH2,name);
    }
    if (name.equalsIgnoreCase("BIRDS1")) {
      playSound(SOUND_FILE_BIRDS1,name);
    }
    if (name.equalsIgnoreCase("BIRDS2")) {
      playSound(SOUND_FILE_BIRDS2,name);
    }
    if (name.equalsIgnoreCase("BIRDS3")) {
      playSound(SOUND_FILE_BIRDS3,name);
    }
    if (name.equalsIgnoreCase("BIRDS4")) {
      playSound(SOUND_FILE_BIRDS4,name);
    }
    if (name.equalsIgnoreCase("BOIL")) {
      playSound(SOUND_FILE_BOIL,name);
    }
    if (name.equalsIgnoreCase("WAVE1")) {
      playSound(SOUND_FILE_WATER_WAVE1,name);
    }
    if (name.equalsIgnoreCase("WAVE2")) {
      playSound(SOUND_FILE_WATER_WAVE2,name);
    }
    if (name.equalsIgnoreCase("WAVE3")) {
      playSound(SOUND_FILE_WATER_WAVE3,name);
    }
    if (name.equalsIgnoreCase("WAVE4")) {
      playSound(SOUND_FILE_WATER_WAVE4,name);
    }
    if (name.equalsIgnoreCase("DOOR")) {
      playSound(SOUND_FILE_DOOR_OPEN,name);
    }
    if (name.equalsIgnoreCase("CLOCK")) {
      playSound(SOUND_FILE_CLOCK,name);
    }
    if (name.equalsIgnoreCase("FIREPLACE")) {
      playSound(SOUND_FILE_FIREPLACE,name);
    }
    if (name.equalsIgnoreCase("LOCKEDDOOR")) {
      playSound(SOUND_FILE_LOCKEDDOOR,name);
    }
    if (name.equalsIgnoreCase("AMULET")) {
      playSound(SOUND_FILE_AMULET,name);
    }
    if (name.equalsIgnoreCase("BOOTS")) {
      playSound(SOUND_FILE_BOOTS,name);
    }
    if (name.equalsIgnoreCase("RING")) {
      playSound(SOUND_FILE_RING,name);
    }
    if (name.equalsIgnoreCase("HIT")) {
      playSound(SOUND_FILE_HIT,name);
    }
    if (name.equalsIgnoreCase("SWING")) {
      playSound(SOUND_FILE_SWING,name);
    }
    if (name.equalsIgnoreCase("BLOCK")) {
      playSound(SOUND_FILE_BLOCK,name);
    }
    if (name.equalsIgnoreCase("FLAMES")) {
      playSound(SOUND_FILE_FLAMES,name);
    }
    if (name.equalsIgnoreCase("MAGIC1")) {
      playSound(SOUND_FILE_MAGIC1,name);
    }
    if (name.equalsIgnoreCase("DARKNESS")) {
      playSound(SOUND_FILE_DARKNESS,name);
    }
    if (name.equalsIgnoreCase("MAGIC2")) {
      playSound(SOUND_FILE_MAGIC2,name);
    }
    if (name.equalsIgnoreCase("MAGIC3")) {
      playSound(SOUND_FILE_MAGIC3,name);
    }
    if (name.equalsIgnoreCase("MAGIC4")) {
      playSound(SOUND_FILE_MAGIC4,name);
    }
    if (name.equalsIgnoreCase("POISON")) {
      playSound(SOUND_FILE_POISON,name);
    }
    if (name.equalsIgnoreCase("LADDER")) {
      playSound(SOUND_FILE_LADDER,name);
    }
    if (name.equalsIgnoreCase("RAT")) {
      playSound(SOUND_FILE_RAT,name);
    }
    if (name.equalsIgnoreCase("BUY1")) {
      playSound(SOUND_FILE_BUY1,name);
    }
    if (name.equalsIgnoreCase("BUY2")) {
      playSound(SOUND_FILE_BUY2,name);
    }
    if (name.equalsIgnoreCase("ZOMBIE1")) {
      playSound(SOUND_FILE_ZOMBIE1,name);
    }
    if (name.equalsIgnoreCase("ZOMBIE2")) {
      playSound(SOUND_FILE_ZOMBIE2,name);
    }
    if (name.equalsIgnoreCase("ZOMBIE")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_ZOMBIE1,name); break;
      case 1: playSound(SOUND_FILE_ZOMBIE2,name); break;
      case 2: playSound(SOUND_FILE_ZOMBIE3,name); break;
      }
    }
    if (name.equalsIgnoreCase("WATERDRIP")) {
      playSound(SOUND_FILE_WATERDRIP,name);
    }
    if (name.equalsIgnoreCase("WATERSPLASH")) {
      playSound(SOUND_FILE_WATERSPLASH,name);
    }    
    if (name.equalsIgnoreCase("ARMOR")) {
      playSound(SOUND_FILE_ARMOR,name);
    }
    if (name.equalsIgnoreCase("WEAPON")) {
      playSound(SOUND_FILE_WEAPON,name);
    }
    if (name.equalsIgnoreCase("FROST")) {
      playSound(SOUND_FILE_FROST,name);
    }
    if (name.equalsIgnoreCase("SHIELD")) {
      playSound(SOUND_FILE_SHIELD,name);
    }
    if (name.equalsIgnoreCase("POTION")) {
      playSound(SOUND_FILE_POTION,name);
    }
    if (name.equalsIgnoreCase("PICKLOCK")) {
      playSound(SOUND_FILE_PICKLOCK,name);
    }
    if (name.equalsIgnoreCase("PICKLOCKFAIL")) {
      playSound(SOUND_FILE_PICKLOCK_FAIL,name);
    }
    if (name.equalsIgnoreCase("LEVELUP")) {
      playSound(SOUND_FILE_LEVELUP,name);
    }
    if (name.equalsIgnoreCase("SCROLL")) {
      playSound(SOUND_FILE_SCROLL,name);
    }
    if (name.equalsIgnoreCase("SLIME1")) {
      playSound(SOUND_FILE_SLIME1,name);
    }
    if (name.equalsIgnoreCase("SLIME2")) {
      playSound(SOUND_FILE_SLIME2,name);
    }
    if (name.equalsIgnoreCase("Brutal bookshelf")) {
      switch (DiceGenerator.getRandom(1)) {
      case 0: playSound(SOUND_FILE_ANIMATED_OBJECT1,name); break;
      case 1: playSound(SOUND_FILE_ANIMATED_OBJECT2,name); break;
      }
    }
    if (name.equalsIgnoreCase("Brain horror")) {
      switch (DiceGenerator.getRandom(1)) {
      case 0: playSound(SOUND_FILE_BRAIN_MONSTER1,name); break;
      case 1: playSound(SOUND_FILE_BRAIN_MONSTER2,name); break;
      }
    }
    if (name.equalsIgnoreCase("WATER ELEMENTAL") ||
        name.equalsIgnoreCase("SMALL WATERELEMENTAL") ||
       name.equalsIgnoreCase("SLIMEY SNAIL")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_SLIME1,name); break;
      case 1: playSound(SOUND_FILE_SLIME2,name); break;
      case 2: playSound(SOUND_FILE_SLIME3,name); break;
      } 
    }
    if (name.equalsIgnoreCase("SLIME")) {
      playSound(SOUND_FILE_SLIME3,name);
    }
    if (name.equalsIgnoreCase("GIANT BEE")) {
      playSound(SOUND_FILE_GIANT_BEE,name);
    }
    if (name.equalsIgnoreCase("BITE SMALL")) {
      playSound(SOUND_FILE_BITE_SMALL,name);
    }
    if (name.equalsIgnoreCase("BITE SMALL2")) {
      playSound(SOUND_FILE_BITE_SMALL2,name);
    }
    if (name.equalsIgnoreCase("BITE SMALL3")) {
      playSound(SOUND_FILE_BITE_SMALL3,name);
    }
    if (name.equalsIgnoreCase("BOW")) {
      playSound(SOUND_FILE_BOW,name);
    }
    if (name.equalsIgnoreCase("MISSING_ARROW")) {
      playSound(SOUND_FILE_MISSING_ARROW,name);
    }
    if (name.equalsIgnoreCase("HOBGOBLIN")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_HOBGOBLIN1,name); break;
      case 1: playSound(SOUND_FILE_HOBGOBLIN2,name); break;
      case 2: playSound(SOUND_FILE_HOBGOBLIN3,name); break;
      }
    }
    if (name.equalsIgnoreCase("EAT")) {
      playSound(SOUND_FILE_EATING,name);
    }
    if (name.equalsIgnoreCase("HEADGEAR")) {
      playSound(SOUND_FILE_HEADWEAR,name);
    }
    if (name.equalsIgnoreCase("TROLL")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_TROLL1,name); break;
      case 1: playSound(SOUND_FILE_TROLL2,name); break;
      case 2: playSound(SOUND_FILE_TROLL3,name); break;
      }
    }
    if (name.equalsIgnoreCase("WOLFMAN") ||
        name.equalsIgnoreCase("VEGETARIAN WOLFMAN")||
        name.equalsIgnoreCase("WOLFMAN PACK LEADER")) {
      playSound(SOUND_FILE_WOLFMAN,name);
    }
    if (name.equalsIgnoreCase("TELEPORT")) {
      playSound(SOUND_FILE_TELEPORT,name);
    }
    if (name.equalsIgnoreCase("WITCH")) {
      playSound(SOUND_FILE_WITCH,name);
    }
    if (name.equalsIgnoreCase("SHOCK")) {
      playSound(SOUND_FILE_SHOCK,name);
    }
  }
  
  /**
   * Play Sound by Enemy sound
   * @param name String
   */
  public static void playEnemySoundsByName(String name) {
    if (name.equalsIgnoreCase("RAT")) {
      playSound(SOUND_FILE_RAT);
    }
    if (name.equalsIgnoreCase("TROLL")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_TROLL1,name); break;
      case 1: playSound(SOUND_FILE_TROLL2,name); break;
      case 2: playSound(SOUND_FILE_TROLL3,name); break;
      }
    }
    if (name.equalsIgnoreCase("WOLFMAN") ||
        name.equalsIgnoreCase("VEGETARIAN WOLFMAN")||
        name.equalsIgnoreCase("WOLFMAN PACK LEADER")) {
      playSound(SOUND_FILE_WOLFMAN,name);
    }

    if (name.equalsIgnoreCase("ZOMBIE") ||
        name.equalsIgnoreCase("ROTTING ZOMBIE")||
        name.equalsIgnoreCase("PIRATE ZOMBIE")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_ZOMBIE1); break;
      case 1: playSound(SOUND_FILE_ZOMBIE2); break;
      case 2: playSound(SOUND_FILE_ZOMBIE3); break;
      }
    }
    if (name.equalsIgnoreCase("Undead King")||
        name.equalsIgnoreCase("Heroon the Righteous")||
        name.equalsIgnoreCase("Heroon")||
        name.equalsIgnoreCase("Lich")||
        name.equalsIgnoreCase("Ancient Lich")||
        name.equalsIgnoreCase("Black Rudolf")) {
      switch (DiceGenerator.getRandom(1)) {
      case 0: playSound(SOUND_FILE_LICH1); break;
      case 1: playSound(SOUND_FILE_LICH2); break;
      }
    }
    if (name.equalsIgnoreCase("Brain horror")) {
      switch (DiceGenerator.getRandom(1)) {
      case 0: playSound(SOUND_FILE_BRAIN_MONSTER1); break;
      case 1: playSound(SOUND_FILE_BRAIN_MONSTER2); break;
      }
    }
    if (name.equalsIgnoreCase("Brutal bookshelf")) {
      switch (DiceGenerator.getRandom(1)) {
      case 0: playSound(SOUND_FILE_ANIMATED_OBJECT1); break;
      case 1: playSound(SOUND_FILE_ANIMATED_OBJECT2); break;
      }
    }
    if (name.equalsIgnoreCase("Skeleton Warrior")||
        name.equalsIgnoreCase("Skeleton Mage")) {
      switch (DiceGenerator.getRandom(1)) {
      case 0: playSound(SOUND_FILE_SKELETON1); break;
      case 1: playSound(SOUND_FILE_SKELETON2); break;
      }
    }
    if (name.equalsIgnoreCase("BARTERING")) {
      switch (DiceGenerator.getRandom(1)) {
      case 0: playSound(SOUND_FILE_BUY1); break;
      case 1: playSound(SOUND_FILE_BUY2); break;      
      }
    }
    if (name.equalsIgnoreCase("WATER ELEMENTAL") ||
        name.equalsIgnoreCase("SMALL WATERELEMENTAL") ||
        name.equalsIgnoreCase("SLIMEY SNAIL")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_SLIME1); break;
      case 1: playSound(SOUND_FILE_SLIME2); break;
      case 2: playSound(SOUND_FILE_SLIME3); break;
      }     
    }
    if (name.equalsIgnoreCase("GIANT SPIDER")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_BITE_SMALL); break;
      case 1: playSound(SOUND_FILE_BITE_SMALL2); break;
      case 2: playSound(SOUND_FILE_BITE_SMALL3); break;
      }     
    }
    if (name.equalsIgnoreCase("GIANT BEE")) {
      playSound(SOUND_FILE_GIANT_BEE);
    }
    if (name.equalsIgnoreCase("HOBGOBLIN")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_HOBGOBLIN1); break;
      case 1: playSound(SOUND_FILE_HOBGOBLIN2); break;
      case 2: playSound(SOUND_FILE_HOBGOBLIN3); break;
      }
    }
    if (name.equalsIgnoreCase("TROLL")) {
      switch (DiceGenerator.getRandom(2)) {
      case 0: playSound(SOUND_FILE_TROLL1); break;
      case 1: playSound(SOUND_FILE_TROLL2); break;
      case 2: playSound(SOUND_FILE_TROLL3); break;
      }
    }
    if (name.equalsIgnoreCase("WITCH")) {
      playSound(SOUND_FILE_WITCH);
    }
    if (name.equalsIgnoreCase("Witch of Orphanage")) {
      playSound(SOUND_FILE_WITCH);
    }

  }

  /**
   * Is Sound enabled or not
   * @return boolean
   */
  public static boolean isSoundEnabled() {
      return soundEnabled;
  }
  
  /**
   * Enable or disable sound
   * @param sound boolean
   */
  public static void setSoundEnabled(boolean sound) {
      soundEnabled = sound;
  }
  
  /**
   * Get sound volume
   * @return int
   */
  public static int getSoundVolume() {
    return soundVolume;
  }
  
  /**
   * Set Sound volume
   * @param soundVolume
   */
  public static void setSoundVolume(int soundVolume) {
    SoundPlayer.soundVolume = soundVolume;
  }

}
