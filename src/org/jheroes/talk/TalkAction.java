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
 * Talk action aka what happens from single line
 * 
 */
public class TalkAction {
  
  public static final int ACTION_NO_ACTION = 0;
  public static final int ACTION_CHANGE_TALK_STATE = 1;
  public static final int ACTION_REMOVE_ITEM = 2;
  public static final int ACTION_GIVE_ITEM = 3;
  public static final int ACTION_SET_VARIABLE = 4;
  public static final int ACTION_GIVE_PARTY_EXPERIENCE = 5;
  public static final int ACTION_CHANGE_MONEY = 6;
  public static final int ACTION_PASS_TURNS = 7;
  public static final int ACTION_HEAL_PARTY = 8;
  public static final int ACTION_ADD_JOURNAL = 9;
  public static final int ACTION_START_SHOP = 10;
  public static final int ACTION_ROLE_PLAYING_REWARD = 11;
  public static final int ACTION_JOIN_PARTY = 12;
  public static final int ACTION_CAST_IDENTIFY = 13;
  public static final int ACTION_START_TRADE = 14;
  public static final int ACTION_RUN_TO_EXIT = 15;
  public static final int ACTION_FIGHT = 16;
  public static final int ACTION_START_SOLO = 17;
  public static final int ACTION_TAKE_ITEM = 18;
  public static final int ACTION_LEAVE_PARTY = 19;
  public static final int ACTION_MOVE_AWAY = 20;
  public static final int ACTION_TELEPORT_TO_EXIT = 21;
  public static final int ACTION_MOVE_TO_WP = 22;
  public static final int ACTION_SET_TRAVEL_WP = 23;
  public static final int ACTION_SET_TRAVEL_MAP = 24;
  public static final int ACTION_DIE = 25;
  public static final int ACTION_SET_DEADLINE=26;
  public static final int ACTION_END_GAME=27;
  
  public static String[] ACTIONS = {"No Action","Change talk state",
    "Remove item","Give Item","Set story variable","Share Experience",
    "Change money","Pass turns","Heal Party","Add journal","Start shop",
    "RP reward","Join party","Cast Identify","Start trade","Run Exit(0,1,2,3)",
    "Fight","Start solo","Take Item","Leave Party","Move Away(0,1,2,3)",
    "Teleport to Exit(WP_NAME)","Move to WP(WP_NAME)","Travel WP","Travel Map",
    "Die","Change deadline","End game"};
  
  private int action;
  private String itemOrQuestName;
  private String journalEntry;
  private int storyVariable;
  private int value;
  
  /**
   * Create empty TalkAction
   */
  public TalkAction() {
    setAction(ACTION_NO_ACTION);
    setItemName("");
    setStoryVariable(0);
    setValue(0);
    setJournalEntry("");    
  }
  
  /**
   * Copy construct
   * @param copyFrom
   */
  public TalkAction(TalkAction copyFrom) {
    setAction(copyFrom.getAction());
    setItemName(copyFrom.getItemName());
    setStoryVariable(copyFrom.getStoryVariable());
    setValue(copyFrom.getValue());
    setJournalEntry(copyFrom.getJournalEntry());
  }
  
  public TalkAction(DataInputStream is) throws IOException {
    // Just get dummy values
    setAction(ACTION_NO_ACTION);
    setItemName("");
    setStoryVariable(0);
    setValue(0);
    setJournalEntry("");
    action = is.readByte();
    switch (action) {
    case ACTION_NO_ACTION: 
    case ACTION_START_TRADE:
    case ACTION_START_SHOP:
    case ACTION_JOIN_PARTY:
    case ACTION_LEAVE_PARTY:
    case ACTION_FIGHT:
    case ACTION_CAST_IDENTIFY:
    case ACTION_DIE:
    case ACTION_START_SOLO:
    case ACTION_END_GAME:{
      // No need to load params with these six
      break;
    }
    case ACTION_CHANGE_TALK_STATE:
    case ACTION_RUN_TO_EXIT:
    case ACTION_MOVE_AWAY:
    case ACTION_SET_DEADLINE:{
      value = is.read();
      break;
      }
    case ACTION_MOVE_TO_WP:  {
      value = is.read();
      itemOrQuestName =  StreamUtilities.readString(is);
      break;
      }
    case ACTION_ADD_JOURNAL:  {
      value = is.read();
      itemOrQuestName =  StreamUtilities.readString(is);
      journalEntry = StreamUtilities.readString(is);
      break;
      }
    case ACTION_HEAL_PARTY:
    case ACTION_PASS_TURNS:
    case ACTION_GIVE_PARTY_EXPERIENCE:
    case ACTION_CHANGE_MONEY:  {
      value = is.readShort();
      break;
      }
    case ACTION_REMOVE_ITEM:
    case ACTION_TAKE_ITEM:
    case ACTION_GIVE_ITEM:
    case ACTION_TELEPORT_TO_EXIT:
    case ACTION_SET_TRAVEL_MAP:
    case ACTION_SET_TRAVEL_WP:{
      itemOrQuestName =  StreamUtilities.readString(is);
      break;
      }
    case ACTION_ROLE_PLAYING_REWARD:
    case ACTION_SET_VARIABLE:  {
      storyVariable = is.read();
      value = is.read();
      break;
      }
    
    }
  }
  /**
   * Write talk action into DataOutputStream
   * @param os DataOutputStream
   * @throws IOException
   */
  public void saveTalkAction(DataOutputStream os) throws IOException {
    os.writeByte(action);
    switch (action) {
    case ACTION_NO_ACTION: 
    case ACTION_START_SHOP:
    case ACTION_START_TRADE:
    case ACTION_JOIN_PARTY:
    case ACTION_LEAVE_PARTY:
    case ACTION_FIGHT:
    case ACTION_CAST_IDENTIFY:
    case ACTION_DIE:
    case ACTION_START_SOLO:
    case ACTION_END_GAME:{
      // No need to save params with these six
      break;
    }
    case ACTION_CHANGE_TALK_STATE:
    case ACTION_RUN_TO_EXIT:
    case ACTION_MOVE_AWAY:
    case ACTION_SET_DEADLINE:{
      os.writeByte(value);
      break;
      }
    case ACTION_MOVE_TO_WP:  {
      os.writeByte(value);
      StreamUtilities.writeString(os, itemOrQuestName);
      break;
      }
    case ACTION_ADD_JOURNAL:  {
      os.writeByte(value);
      StreamUtilities.writeString(os, itemOrQuestName);
      StreamUtilities.writeString(os, journalEntry);
      break;
      }
    case ACTION_HEAL_PARTY:
    case ACTION_PASS_TURNS:
    case ACTION_GIVE_PARTY_EXPERIENCE:
    case ACTION_CHANGE_MONEY:  {
      os.writeShort(value);
      break;
      }
    case ACTION_REMOVE_ITEM:
    case ACTION_TAKE_ITEM:
    case ACTION_GIVE_ITEM:
    case ACTION_TELEPORT_TO_EXIT:
    case ACTION_SET_TRAVEL_MAP:
    case ACTION_SET_TRAVEL_WP:{
      StreamUtilities.writeString(os, itemOrQuestName);
      break;
      }
    case ACTION_ROLE_PLAYING_REWARD: 
    case ACTION_SET_VARIABLE:  {
      os.writeByte(storyVariable); // Role in role playing reward
      os.writeByte(value);
      break;
      }
    
    }
  }

  public int getAction() {
    return action;
  }

  public void setAction(int action) {
    this.action = action;
  }

  public String getItemName() {
    return itemOrQuestName;
  }

  public void setItemName(String itemName) {
    this.itemOrQuestName = itemName;
  }

  public int getStoryVariable() {
    return storyVariable;
  }

  public void setStoryVariable(int storyVariable) {
    this.storyVariable = storyVariable;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getJournalEntry() {
    return journalEntry;
  }

  public void setJournalEntry(String journalEntry) {
    this.journalEntry = journalEntry;
  }
  
}
