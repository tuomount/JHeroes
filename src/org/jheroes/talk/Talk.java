package org.jheroes.talk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
 * One single talk
 *
 */
public class Talk {

  public static final int TALK_CONDITION_ALWAYS_TRUE = 0;
  public static final int TALK_CONDITION_PLAYER_HAS_ITEM = 1;
  public static final int TALK_CONDITION_STORY_VARIABLE_EQUAL = 2;
  public static final int TALK_CONDITION_STORY_VARIABLE_LESS = 3;
  public static final int TALK_CONDITION_STORY_VARIABLE_GREATER = 4;
  public static final int TALK_CONDITION_PLAYER_LEVEL_EQUAL = 5;
  public static final int TALK_CONDITION_PLAYER_LEVEL_LESS = 6;
  public static final int TALK_CONDITION_PLAYER_LEVEL_GREATER = 7;
  public static final int TALK_CONDITION_PLAYER_ATTRIBUTE_EQUAL = 8;
  public static final int TALK_CONDITION_PLAYER_ATTRIBUTE_LESS = 9;
  public static final int TALK_CONDITION_PLAYER_ATTRIBUTE_GREATER = 10;
  public static final int TALK_CONDITION_PLAYER_GOLD_EQUAL = 11;
  public static final int TALK_CONDITION_PLAYER_GOLD_LESS = 12;
  public static final int TALK_CONDITION_PLAYER_GOLD_GREATER = 13;
  public static final int TALK_CONDITION_PLAYER_SKILL_CHECK_GREATER = 14;
  public static final int TALK_CONDITION_PLAYER_SKILL_CHECK_AGAINST_NPC = 15;
  public static final int TALK_CONDITION_TIME_HOUR_EQUALS = 16;
  public static final int TALK_CONDITION_TIME_HOUR_GREATER = 17;
  public static final int TALK_CONDITION_TIME_HOUR_LESS = 18;
  public static final int TALK_CONDITION_TIME_IS_DAY = 19;
  public static final int TALK_CONDITION_TIME_IS_NIGHT = 20;
  public static final int TALK_CONDITION_IS_PARTY_MEMBER = 21;
  public static final int TALK_CONDITION_IS_ROOM_IN_PARTY = 22;
  public static final int TALK_CONDITION_PLAYER_NAME_IS = 23;
  public static final int TALK_CONDITION_STORY_VARIABLE_EQUAL_AND_PLAYER_NAME_IS = 24;
  public static final int TALK_CONDITION_IS_SOLO_MODE = 25;
  public static final int TALK_CONDITION_TIME_HOUR_IS_BETWEEN = 26;
  private static final int MAX_TALK_CONDITION = 27;
  
  private static final String VERSION_STRING = "TALK1.0";
  
  
  private ArrayList<GreetingLine> greetings;
  private ArrayList<TalkLine> lines;
  
  /**
   * Create empty Talk
   */
  public Talk() {
    greetings = new ArrayList<GreetingLine>();
    lines = new ArrayList<TalkLine>();
  }
  
  public void moveUp(int index) {
    if ((index > 0) &&(index <lines.size())) {
      TalkLine upper = lines.get(index-1);
      TalkLine current = lines.get(index);
      lines.set(index, upper);
      lines.set(index-1,current);
    }
  }
  public void moveDown(int index) {
    if ((index >= 0) &&(index+1 <lines.size())) {
      TalkLine downner = lines.get(index+1);
      TalkLine current = lines.get(index);
      lines.set(index, downner);
      lines.set(index+1,current);
    }
  }
  
  /**
   * Create talk from DataInputStream
   * @param is
   * @throws IOException
   */
  public Talk(DataInputStream is) throws IOException {
    String version = StreamUtilities.readString(is);
    if (version.equals(VERSION_STRING)) {
      int size = is.read();
      greetings = new ArrayList<GreetingLine>();
      for (int i=0;i<size;i++) {
        GreetingLine line = new GreetingLine(is);
        greetings.add(line);
      }
      lines = new ArrayList<TalkLine>();
      size = is.read();
      for (int i=0;i<size;i++) {
        TalkLine line = new TalkLine(is);
        lines.add(line);
      }
      // Close DataInputStream
      is.close();
    } else {
      throw new IOException("Not talk file!");
    }
  }
  
  /**
   * Save Talk into DataOutputStream
   * @param os DataOutput Stream
   * @throws IOException
   */
  public void saveTalk(DataOutputStream os) throws IOException {
    StreamUtilities.writeString(os, VERSION_STRING);
    os.writeByte(greetings.size());
    for (int i=0;i<greetings.size();i++) {
      GreetingLine line = greetings.get(i);
      if (line != null) {
        line.saveGreeting(os);
      }
    }
    os.writeByte(lines.size());
    for (int i=0;i<lines.size();i++) {
      TalkLine line = lines.get(i);
      if (line != null) {
        line.saveTalkLine(os);
      }
    }
    os.close();
  }
  
  public void addGreetingLine(GreetingLine greet) {
    greetings.add(greet);
  }

  public void removeAllGreetings() {
    greetings.clear();
  }
  
  public void addTalkLine(TalkLine line) {
    lines.add(line);
  }

  public TalkLine[] getAllLines() {
    TalkLine[] result = new TalkLine[lines.size()];
    for (int i=0;i<result.length;i++) {
      result[i] = lines.get(i);
    }
    return result;
  }
  
  /**
   * Get Talk lines with state
   * @param state int
   * @return TalkLine array, might be zero length if no lines found. In this case
   * conversation should end.
   */
  public TalkLine[] getAllLinesWithState(int state) {
    int size = 0;
    for (int i=0;i<lines.size();i++) {
      if (lines.get(i).isVisibleInState(state)) {
        size++;
      }
    }
    TalkLine[] result = new TalkLine[size];
    int j = 0;
    for (int i=0;i<lines.size();i++) {
      if (lines.get(i).isVisibleInState(state)) {
        result[j] = lines.get(i);
        j++;
      }
    }
    return result;
  }
  
  /**
   * Remove greeting by index
   * @param i
   */
  public void removeGreetingLine(int i) {
    if ((i >= 0) && (i< greetings.size())) {
      greetings.remove(i);
    }
  }
  
  /**
   * Remove talk line by index
   * @param i
   */
  public void removeTalkLine(int i) {
    if ((i >= 0) && (i< lines.size())) {
      lines.remove(i);
    }
    
  }
  
  /**
   * Get greetingline by index
   * @param i int
   * @return GreetingLine or null if not found
   */
  public GreetingLine getGreetingByIndex(int i) {
    if ((i >= 0) && (i< greetings.size())) {
      return greetings.get(i);
    } else {
      return null;
    }
  }
  
  /**
   * Get talk line by index
   * @param i int
   * @return TalkLine or null if not found
   */
  public TalkLine getTalkLineByIndex(int i) {
    if ((i >= 0) && (i< lines.size())) {
      return lines.get(i);
    } else {
      return null;
    }
    
  }
  
  /**
   * Get number of greetings in talk
   * @return int
   */
  public int getNumberOfGreetings() {
    return greetings.size();
  }
  
  /**
   * Get number of lines in talk
   * @return int
   */
  public int getNumberOfTalkLines() {
    return lines.size();
  }
  
  public static String getConditionAsString(int condition) {
    String result ="";
    switch (condition) {
    case TALK_CONDITION_ALWAYS_TRUE: result ="Always true"; break;
    case TALK_CONDITION_PLAYER_ATTRIBUTE_EQUAL: result ="Player attribute equals"; break;
    case TALK_CONDITION_PLAYER_ATTRIBUTE_GREATER: result ="Player attribute greater than"; break;
    case TALK_CONDITION_PLAYER_ATTRIBUTE_LESS: result ="Player attribute less than"; break;
    case TALK_CONDITION_PLAYER_GOLD_EQUAL: result ="Player gold equals"; break;
    case TALK_CONDITION_PLAYER_GOLD_GREATER: result ="Player gold greater than"; break;
    case TALK_CONDITION_PLAYER_GOLD_LESS: result ="Player gold less than"; break;
    case TALK_CONDITION_PLAYER_HAS_ITEM: result ="Player has item"; break;
    case TALK_CONDITION_PLAYER_LEVEL_EQUAL: result ="Player level equals"; break;
    case TALK_CONDITION_PLAYER_LEVEL_GREATER: result ="Player level greater than"; break;
    case TALK_CONDITION_PLAYER_LEVEL_LESS: result ="Player level less than"; break;
    case TALK_CONDITION_PLAYER_SKILL_CHECK_AGAINST_NPC: result ="Skill check against NPC"; break;
    case TALK_CONDITION_PLAYER_SKILL_CHECK_GREATER: result ="Skill greater than"; break;
    case TALK_CONDITION_STORY_VARIABLE_EQUAL: result ="Story variable equals"; break;
    case TALK_CONDITION_STORY_VARIABLE_LESS: result ="Story variable less than"; break;
    case TALK_CONDITION_STORY_VARIABLE_GREATER: result ="Story variable greater than"; break;
    case TALK_CONDITION_TIME_HOUR_EQUALS: result ="Time Hour equals"; break;
    case TALK_CONDITION_TIME_HOUR_GREATER: result ="Time Hour greater than"; break;
    case TALK_CONDITION_TIME_HOUR_LESS: result ="Time Hour less than"; break;
    case TALK_CONDITION_TIME_IS_DAY: result ="Time is day?"; break;
    case TALK_CONDITION_TIME_IS_NIGHT: result ="Time is night?"; break;
    case TALK_CONDITION_IS_PARTY_MEMBER: result = "Is party member?"; break;
    case TALK_CONDITION_IS_ROOM_IN_PARTY: result = "Is room in party?"; break;
    case TALK_CONDITION_PLAYER_NAME_IS: result = "Player name is"; break;
    case TALK_CONDITION_STORY_VARIABLE_EQUAL_AND_PLAYER_NAME_IS: result = "Story = and player ="; break;
    case TALK_CONDITION_IS_SOLO_MODE: result = "Is solo mode?"; break;
    case TALK_CONDITION_TIME_HOUR_IS_BETWEEN: result ="Time Hour is between"; break;
    }
    return result;
  }
  
  public static String[] getTalkConditionsAsArray() {
    String[] result = new String[MAX_TALK_CONDITION];
    for (int i=0;i<MAX_TALK_CONDITION;i++) {
      result[i] = getConditionAsString(i);
    }
    return result;
  }
  
  /**
   * Reads talk resource from file. Returns talk if succeeds otherwise null is 
   * returned
   * @param filename String
   * @return Talk
   */
  public static Talk readTalkResource(String filename) {
    InputStream is =Talk.class.getResourceAsStream(filename);    
    if (is != null) {
      DataInputStream dis = new DataInputStream(is);
      Talk talk;
      try {
        talk = new Talk(dis);
        return talk;
      } catch (IOException e) {
        System.out.println("Load talk failed:"+e.getMessage());
        e.printStackTrace();
        return null;
      }
      
    } else {
      return null;
    }    
  }
}
