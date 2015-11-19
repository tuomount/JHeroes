package org.jheroes.talk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
 * Talk Line
 * 
 */
public class TalkLine {


  private String playerText;
  private String textTrue;
  private String textFalse;
  private int condition;
  private int param1;
  private int param2;
  private String itemName;
  private ArrayList<TalkAction> actionsTrue;
  private ArrayList<TalkAction> actionsFalse;
  private int[] statesWhenVisible;
  
  /**
   * Create empty TalkLine which always shows positive Text
   * @param playerText String
   * @param textTrue String
   * @param states String
   */
  public TalkLine(String playerText, String textTrue, String states) {
    this.playerText = playerText;
    actionsTrue =  new ArrayList<TalkAction>();
    actionsFalse =  new ArrayList<TalkAction>();
    this.setTextTrue(textTrue);
    param1=0;
    param2=0;
    setCondition(Talk.TALK_CONDITION_ALWAYS_TRUE);
    textFalse = "";
    itemName = "";
    setStatesWhenVisible(states);
  }  
  
  /**
   * Creates a copy from Talk Line
   * @param copyFrom
   */
  public void copyOf(TalkLine copyFrom) {
    if (copyFrom != null) {
      playerText = copyFrom.getPlayerText();
      textTrue = copyFrom.getTextTrue();
      textFalse = copyFrom.getTextFalse();
      condition = copyFrom.getCondition();
      param1 = copyFrom.getParam1();
      param2 = copyFrom.getParam2();
      itemName = copyFrom.getItemName();
      actionsFalse.clear();
      if (copyFrom.getActionsSize(false)>0) {
        for (int i=0;i<copyFrom.getActionsSize(false);i++) {
          actionsFalse.add(new TalkAction(copyFrom.getAction(false,i)));
        }
      }
      actionsTrue.clear();
      if (copyFrom.getActionsSize(true)>0) {
        for (int i=0;i<copyFrom.getActionsSize(true);i++) {
          actionsTrue.add(new TalkAction(copyFrom.getAction(true,i)));
        }
      }
      setStatesWhenVisible(copyFrom.getStatesWhenVisibleAsString());
    }
  }
  
  public TalkLine(DataInputStream is) throws IOException {
    this.playerText = StreamUtilities.readString(is);
    textTrue = StreamUtilities.readString(is);
    actionsTrue =  new ArrayList<TalkAction>();
    actionsFalse =  new ArrayList<TalkAction>();
    param1=0;
    param2=0;
    setCondition(Talk.TALK_CONDITION_ALWAYS_TRUE);
    textFalse = "";
    itemName = "";
    int size = is.read();
    if(size >= 0) {
      statesWhenVisible = new int[size];
      for (int i=0;i<size;i++) {        
        statesWhenVisible[i] = is.read(); 
      }
    }
    condition = is.read();
    // Read condition parameters
    switch (condition) {
    case Talk.TALK_CONDITION_ALWAYS_TRUE: break;
    case Talk.TALK_CONDITION_TIME_IS_DAY: 
    case Talk.TALK_CONDITION_TIME_IS_NIGHT:
    case Talk.TALK_CONDITION_IS_PARTY_MEMBER:
    case Talk.TALK_CONDITION_IS_SOLO_MODE:
    case Talk.TALK_CONDITION_IS_ROOM_IN_PARTY: {
           textFalse = StreamUtilities.readString(is);
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_GREATER:
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_LESS:
    case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_GREATER:
    case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL:
    case Talk.TALK_CONDITION_STORY_VARIABLE_GREATER:
    case Talk.TALK_CONDITION_STORY_VARIABLE_LESS:
    case Talk.TALK_CONDITION_TIME_HOUR_IS_BETWEEN:{
           textFalse = StreamUtilities.readString(is);
           param1 = is.read();
           param2 = is.read();
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_GOLD_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_GOLD_GREATER:
    case Talk.TALK_CONDITION_PLAYER_GOLD_LESS:{
           textFalse = StreamUtilities.readString(is);
           param1 = is.readShort();
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_HAS_ITEM:
    case Talk.TALK_CONDITION_PLAYER_NAME_IS: {
           textFalse = StreamUtilities.readString(is);
           itemName = StreamUtilities.readString(is);
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_LEVEL_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_LEVEL_GREATER:
    case Talk.TALK_CONDITION_PLAYER_LEVEL_LESS:
    case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_AGAINST_NPC:
    case Talk.TALK_CONDITION_TIME_HOUR_EQUALS:
    case Talk.TALK_CONDITION_TIME_HOUR_GREATER:
    case Talk.TALK_CONDITION_TIME_HOUR_LESS:{
           textFalse = StreamUtilities.readString(is);
           param1 = is.read();
           break;
         }
    case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL_AND_PLAYER_NAME_IS:{
      textFalse = StreamUtilities.readString(is);
      param1 = is.read();
      param2 = is.read();
      itemName = StreamUtilities.readString(is);
      break;
    }
    }
    size = is.read();
    for (int i=0;i<size;i++) {
      TalkAction action = new TalkAction(is);
      actionsTrue.add(action);
    }
    size = is.read();
    for (int i=0;i<size;i++) {
      TalkAction action = new TalkAction(is);
      actionsFalse.add(action);
    }
  }
  
  /**
   * Save talk line into DataOutputStream
   * @param os DataOutputStream
   * @throws IOException
   */
  public void saveTalkLine(DataOutputStream os) throws IOException {
    StreamUtilities.writeString(os, playerText);
    StreamUtilities.writeString(os, textTrue);
    os.writeByte(statesWhenVisible.length);
    for (int i=0;i<statesWhenVisible.length;i++) {
      os.writeByte(statesWhenVisible[i]);
    }
    os.writeByte(condition);
    switch (condition) {
    case Talk.TALK_CONDITION_ALWAYS_TRUE: break;
    case Talk.TALK_CONDITION_TIME_IS_DAY: 
    case Talk.TALK_CONDITION_TIME_IS_NIGHT:
    case Talk.TALK_CONDITION_IS_PARTY_MEMBER:
    case Talk.TALK_CONDITION_IS_SOLO_MODE:
    case Talk.TALK_CONDITION_IS_ROOM_IN_PARTY: {
            StreamUtilities.writeString(os, textFalse);
            break;
          }
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_GREATER:
    case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_LESS:
    case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_GREATER:
    case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL:
    case Talk.TALK_CONDITION_STORY_VARIABLE_GREATER:
    case Talk.TALK_CONDITION_STORY_VARIABLE_LESS:
    case Talk.TALK_CONDITION_TIME_HOUR_IS_BETWEEN:{
           StreamUtilities.writeString(os, textFalse);
           os.writeByte(param1);
           os.writeByte(param2);
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_GOLD_EQUAL:
    case Talk.TALK_CONDITION_PLAYER_GOLD_GREATER:
    case Talk.TALK_CONDITION_PLAYER_GOLD_LESS:{
           StreamUtilities.writeString(os, textFalse);
           os.writeShort(param1);
           break;
         }
    case Talk.TALK_CONDITION_PLAYER_HAS_ITEM:
    case Talk.TALK_CONDITION_PLAYER_NAME_IS: {
           StreamUtilities.writeString(os, textFalse);
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
           StreamUtilities.writeString(os, textFalse);
           os.writeByte(param1);
           break;
         }
    case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL_AND_PLAYER_NAME_IS:{
           StreamUtilities.writeString(os, textFalse);
           os.writeByte(param1);
           os.writeByte(param2);
           StreamUtilities.writeString(os, itemName);
           break;
         }

    }
    os.writeByte(actionsTrue.size());
    for (int i=0;i<actionsTrue.size();i++) {
      TalkAction action = actionsTrue.get(i);
      if (action != null) {
        action.saveTalkAction(os);
      }
    }
    os.writeByte(actionsFalse.size());
    for (int i=0;i<actionsFalse.size();i++) {
      TalkAction action = actionsFalse.get(i);
      if (action != null) {
        action.saveTalkAction(os);
      }
    }
  }
  
  /**
   * Set state when talk line is visible. State are give as numeric string
   * where states are separated by space
   * @param states String
   */
  public void setStatesWhenVisible(String states) {
    if (states != null) {
      String[] state = states.split(" ");
      statesWhenVisible = new int[state.length];
      for (int i=0;i<state.length;i++) {
        statesWhenVisible[i] = Integer.valueOf(state[i]);
      }
    }
  }
  
  /**
   * Check if talk line is visible in state
   * @param state
   * @return boolean
   */
  public boolean isVisibleInState(int state) {
    if (statesWhenVisible != null) {
      for (int i=0;i<statesWhenVisible.length;i++) {
        if (statesWhenVisible[i]==state) {
          return true;
        }           
      }
    }
    return false;
  }
  
  /**
   * Get states as one string
   * @return String
   */
  public String getStatesWhenVisibleAsString() {
    StringBuilder sb = new StringBuilder();
    if (statesWhenVisible != null) {
      for (int i=0;i<statesWhenVisible.length;i++) {
        sb.append(statesWhenVisible[i]);
        if (i<statesWhenVisible.length-1) {
          sb.append(" ");
        }
        
      }
    }
    return sb.toString();
  }
  
  
  public int getActionsSize(boolean isTrue) {
    if (isTrue) {
      return actionsTrue.size();
    } else {
      return actionsFalse.size();  
    }
  }
  
  
  /**
   * Get talk action by index
   * @param isTrue is true actions or false
   * @param index int
   * @return TalkAction
   */
  public TalkAction getAction(boolean isTrue, int index) {
    if(isTrue) {
      if ((index >= 0) && (index < actionsTrue.size())) {
        return actionsTrue.get(index);
      } else {
        return null;
      }
    } else {
      if ((index >= 0) && (index < actionsFalse.size())) {
        return actionsFalse.get(index);
      } else {
        return null;
      }      
    }
  }
  
  /**
   * Get next talk state from the line
   * @param isTrue, true or false branch
   * @return state number if found, otherwise -1
   */
  public int getNextState(boolean isTrue) {
    int result = -1;
    int size = getActionsSize(isTrue);
    if (size > 0) {
      for (int i=0;i<size;i++) {
        TalkAction action = getAction(isTrue, i);
        if (action != null) {
          if (action.getAction() == TalkAction.ACTION_CHANGE_TALK_STATE) {
            result = action.getValue();
          }
        }
      }
    }
    return result;
  }
  
  public void removeAction(boolean isTrue, int index) {
    if(isTrue) {
      if ((index >= 0) && (index < actionsTrue.size())) {
        actionsTrue.remove(index);
      } 
    } else {
      if ((index >= 0) && (index < actionsFalse.size())) {
        actionsFalse.remove(index);
      }      
    }
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getStatesWhenVisibleAsString());
    sb.append("/");
    sb.append(getPlayerText());
    return sb.toString();
  }

  public void addAction(boolean isTrue, TalkAction action) {
    if (isTrue) {
      actionsTrue.add(action);
    } else {
      actionsFalse.add(action);      
    }
  }
  
  public void removeAllActions(boolean isTrue) {
    if (isTrue) {
      actionsTrue.clear();
    } else {
    actionsFalse.clear();
    }
  }

  public String getTextTrue() {
    return textTrue;
  }

  public void setTextTrue(String textTrue) {
    this.textTrue = textTrue;
  }

  public String getTextFalse() {
    return textFalse;
  }

  public void setTextFalse(String textFalse) {
    this.textFalse = textFalse;
  }

  public int getCondition() {
    return condition;
  }

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

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getPlayerText() {
    return playerText;
  }

  public void setPlayerText(String playerText) {
    this.playerText = playerText;
  }
}
