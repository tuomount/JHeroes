package org.jheroes.map.character;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.jheroes.map.DiceGenerator;
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
 * Character task for NPC
 * 
 */
public class CharTask {

  /**
   * Move to target WayPoint
   */
  public static final String TASK_MOVE = "!Move";
  /**
   * Move randomly
   */
  public static final String TASK_MOVE_RANDOM = "!MoveRandom";
  /**
   * Move randomly and attack
   */
  public static final String TASK_MOVE_RANDOM_AND_ATTACK = "!MoveRandomAndAttack";
  /**
   * Keep shop
   */
  public static final String TASK_KEEP_SHOP = "!Shop";
  /**
   * Move randomly inside WP
   */
  public static final String TASK_MOVE_INSIDE_WP = "!MoveInsideWP";
  /**
   * Move randomly inside WP and Attack
   */
  public static final String TASK_MOVE_INSIDE_WP_AND_ATTACK = "!MoveInsideWPAndAttack";
  /**
   * Jump to WP
   */
  public static final String TASK_JUMP_TO_WP = "!JumpToWP";
  /**
   * Sleep
   */
  public static final String TASK_SLEEP = "!SLEEP";
  /**
   * Work, generates items
   */
  public static final String TASK_WORK = "!WORK";

  /**
   * Eat
   */
  public static final String TASK_EAT = "!EAT";

  /**
   * Child Play
   */
  public static final String TASK_CHILDPLAY = "!CHILDPLAY";

  /**
   * Attack PC, not selectable from mapeditor
   */
  public static final String TASK_ATTACK_PC = "#AttackPC";
  
  /**
   * Attack NPC, not selectable from mapeditor
   */
  public static final String TASK_ATTACK_NPC = "#AttackNPC";

  /**
   * Run Exit, not selectable from mapeditor
   */
  public static final String TASK_RUN_EXIT = "#RunExit";

  /**
   * Run To WP, not selectable from mapeditor
   */
  public static final String TASK_RUN_TO_WP = "#RunTOWP";

  /**
   * Move away, not selectable from mapeditor
   */
  public static final String TASK_MOVE_AWAY = "#MoveAway";
  /**
   * Jump once to WP, not selectable from mapeditor
   */
  public static final String TASK_JUMP_ONCE_TO_WP = "#JumpOnceToWP";

  /**
   * TASKS list for combobox
   */
  public static final String[] TASKS = {TASK_MOVE,TASK_MOVE_RANDOM, 
    TASK_MOVE_RANDOM_AND_ATTACK, TASK_MOVE_INSIDE_WP, 
    TASK_MOVE_INSIDE_WP_AND_ATTACK, TASK_KEEP_SHOP,TASK_JUMP_TO_WP,TASK_SLEEP,
    TASK_WORK,TASK_EAT,TASK_CHILDPLAY    
  };
  
  /**
   * Time when to start doing Task, 
   * "-" means that continue from previous task immediately
   */
  private String time;
  /**
   * Task see TASK_NNNN above
   */
  private String task;
  /**
   * Waypoint name
   */
  private String wayPointName;
  /**
   * Description when character is looked. If empty default description is used.
   */
  private String description;
  
  public CharTask(String time, String task, String wayPointName, String desc) {
    this.setTime(time);
    this.setTask(task);
    this.setWayPointName(wayPointName);
    this.setDescription(desc);
  }

  /**
   * Reads task from DataInputStream
   * @param is
   * @throws IOException
   */
  public CharTask(DataInputStream is) throws IOException {
    time = StreamUtilities.readString(is);
    task = StreamUtilities.readString(is);
    wayPointName = StreamUtilities.readString(is);
    description = StreamUtilities.readString(is);    
  }
  public void setTime(String time) {
    this.time = time;
  }

  public String getTime() {
    return time;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public String getTask() {
    return task;
  }

  public void setWayPointName(String wayPointName) {
    this.wayPointName = wayPointName;
  }

  public String getWayPointName() {
    return wayPointName;
  }
  
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * Gets random item name from work description.
   * He is strong#Long sword|Short sword|Battle Axe
   * He is strong#Long sword|Short sword|Battle Axe#Smith1
   * @return String Itemname if found otherwise empty
   */
  public String getWorkItem() {
    String[] itemString = this.description.split("#");
    if (itemString.length == 2) {
      String[] items = itemString[1].split("\\|");
      return items[DiceGenerator.getRandom(items.length-1)];
    }
    if (itemString.length == 3) {
      String[] items = itemString[1].split("\\|");
      return items[DiceGenerator.getRandom(items.length-1)];
    }
    return "";
  }

  /**
   * Gets random working sound from work description
   * He is strong#Long sword|Short sword|Battle Axe#Smith1
   * Sound is always on third
   * @return String Soundname if no work sound is playing
   */
  public String getWorkSound() {
    String[] soundString = this.description.split("#");
    if (soundString.length == 3) {
      String[] sounds = soundString[2].split("\\|");
      boolean notPlaying = true;
      for (int i=0;i<sounds.length;i++) {
        if (SoundPlayer.isSoundPlaying(sounds[i])) {
          notPlaying = false;
        }
      }
      if (notPlaying) {
        return sounds[DiceGenerator.getRandom(sounds.length-1)];
      } else {
        return "";
      }
    }
    return "";
  }

  @Override 
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(time);
    sb.append(" ");
    sb.append(task);
    sb.append(" ");
    sb.append(wayPointName);
    sb.append(" ");
    sb.append(description);
    return sb.toString();
  }

  /**
   * Write Task into Data output stream
   * @param os DataOutputStream
   * @throws IOException
   */
  public void writeTask(DataOutputStream os) throws IOException {
    StreamUtilities.writeString(os, time);
    StreamUtilities.writeString(os, task);
    StreamUtilities.writeString(os, wayPointName);
    StreamUtilities.writeString(os, description);
  }
    
}
