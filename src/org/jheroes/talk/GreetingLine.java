package org.jheroes.talk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
 * Talk greeting line
 *
 */
public class GreetingLine {

  private String text;
  private int condition;
  private int param1;
  private int param2;
  private int nextState;
  private String itemName;
  
  /**
   * Make greeting text which always passed the check and next state is 1
   * @param text String
   */
  public GreetingLine(String text) {
    setText(text);
    condition = Talk.TALK_CONDITION_ALWAYS_TRUE;
    setParam1(0);
    setParam2(0);
    nextState = 1;
    itemName ="";
  }

  public GreetingLine(DataInputStream is) throws IOException {
    setText(StreamUtilities.readString(is));
    condition = is.read();
    setParam1(0);
    setParam2(0);
    nextState = 1;
    itemName ="";
    switch (condition) {
    case Talk.TALK_CONDITION_ALWAYS_TRUE:
    case Talk.TALK_CONDITION_TIME_IS_DAY:
    case Talk.TALK_CONDITION_TIME_IS_NIGHT:
    case Talk.TALK_CONDITION_IS_SOLO_MODE:break;
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_GREATER:
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_LESS:
    case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_GREATER:
    case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL:
    case Talk.TALK_CONDITION_STORY_VARIABLE_GREATER:
    case Talk.TALK_CONDITION_TIME_HOUR_IS_BETWEEN:
    case Talk.TALK_CONDITION_STORY_VARIABLE_LESS:{
           setParam1(is.read());
           setParam2(is.read());
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_GOLD_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_GOLD_GREATER:
    case Talk.TALK_CONDITION_PLAYER_GOLD_LESS:{
           setParam1(is.readShort());
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_HAS_ITEM:
    case Talk.TALK_CONDITION_PLAYER_NAME_IS:{
            setItemName(StreamUtilities.readString(is));
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_LEVEL_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_LEVEL_GREATER:
    case Talk.TALK_CONDITION_PLAYER_LEVEL_LESS:
    case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_AGAINST_NPC:
    case Talk.TALK_CONDITION_TIME_HOUR_EQUALS:
    case Talk.TALK_CONDITION_TIME_HOUR_GREATER:
    case Talk.TALK_CONDITION_TIME_HOUR_LESS:{
           setParam1(is.read());
           break;
         }
    case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL_AND_PLAYER_NAME_IS:{
      setParam1(is.read());
      setParam2(is.read());
      setItemName(StreamUtilities.readString(is));
      break;
    }
    }
    setNextState(is.read());
  }
  /**
   * Save greeting line to DataOutputStream. This does not close the stream
   * @param os DataOutputStream
   * @throws IOException
   */
  public void saveGreeting(DataOutputStream os) throws IOException {
    StreamUtilities.writeString(os, text);
    os.writeByte(condition);
    switch (condition) {
    case Talk.TALK_CONDITION_ALWAYS_TRUE:
    case Talk.TALK_CONDITION_TIME_IS_DAY:
    case Talk.TALK_CONDITION_TIME_IS_NIGHT:
    case Talk.TALK_CONDITION_IS_SOLO_MODE:break;
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_GREATER:
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_LESS:
    case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_GREATER:
    case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL:
    case Talk.TALK_CONDITION_STORY_VARIABLE_GREATER:
    case Talk.TALK_CONDITION_STORY_VARIABLE_LESS:
    case Talk.TALK_CONDITION_TIME_HOUR_IS_BETWEEN:{
           os.writeByte(param1);
           os.writeByte(param2);
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_GOLD_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_GOLD_GREATER:
    case Talk.TALK_CONDITION_PLAYER_GOLD_LESS:{
           os.writeShort(param1);
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_HAS_ITEM:
    case Talk.TALK_CONDITION_PLAYER_NAME_IS:{
           StreamUtilities.writeString(os, itemName);
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_LEVEL_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_LEVEL_GREATER:
    case Talk.TALK_CONDITION_PLAYER_LEVEL_LESS:
    case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_AGAINST_NPC:
    case Talk.TALK_CONDITION_TIME_HOUR_EQUALS:
    case Talk.TALK_CONDITION_TIME_HOUR_GREATER:
    case Talk.TALK_CONDITION_TIME_HOUR_LESS:{
           os.writeByte(param1);
           break;
         }
    case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL_AND_PLAYER_NAME_IS:{
      os.writeByte(param1);
      os.writeByte(param2);
      StreamUtilities.writeString(os, itemName);
      break;
    }
    }
    os.writeByte(nextState);
  }
  
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getCondition() {
    return condition;
  }

  /**
   * Set condition when greeting line is shown
   * @param condition
   */
  public void setCondition(int condition) {
    this.condition = condition;
  }

  public int getParam1() {
    return param1;
  }

  public void setParam1(int param1) {
    this.param1 = param1;
  }

  public int getParam2() {
    return param2;
  }

  public void setParam2(int param2) {
    this.param2 = param2;
  }

  public int getNextState() {
    return nextState;
  }

  public void setNextState(int nextState) {
    this.nextState = nextState;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }
  
}
