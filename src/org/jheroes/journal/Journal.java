package org.jheroes.journal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jheroes.map.Party;


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
 * Journal information
 * 
 */
public class Journal {
  
  private ArrayList<Quest> quests;
  
  public Journal() {
    quests = new ArrayList<Quest>();
  }
  
  /**
   * Read journal from DataInputStream
   * @param is DataInputStream
   * @throws IOException
   */
  public Journal(DataInputStream is) throws IOException {
    quests = new ArrayList<Quest>();
    int size = is.read();
    for (int i=0;i<size;i++) {
      Quest tmp = new Quest(is);
      quests.add(tmp);
    }
  }
    
  private int findQuestByName(String name) {
    for (int i=0;i<quests.size();i++) {
      Quest tmp = quests.get(i);
      if (tmp.getQuestName().equalsIgnoreCase(name)) {
        return i;
      }
    }
    return -1;
  }
  
  /**
   * Add quest, searches first if quest with same name is found adds there
   * @param party Used for getting correct date
   * @param questName String
   * @param entry String
   * @param status QuestStatus, 1 active, 2 done, 3 failed
   */
  public void addQuestEntry(Party party, String questName, String entry, int status) {
    int index = findQuestByName(questName);
    if (index != -1) {
      Quest tmp = quests.get(index);      
      tmp.setStatus(status);
      StringBuilder sb = new StringBuilder();
      sb.append(party.getDateAsString());
      sb.append(":");
      sb.append(entry);
      tmp.addQuestEntry(sb.toString());
    } else {
      Quest tmp = new Quest(questName);
      tmp.setStatus(status);
      StringBuilder sb = new StringBuilder();
      sb.append(party.getDateAsString());
      sb.append(": ");
      sb.append(entry);
      tmp.addQuestEntry(sb.toString());
      quests.add(tmp);
    }
  }
  
  /**
   * Get Quests in sorted array, first active, completed and failed
   * @return Quest array
   */
  public Quest[] getQuestsSorted() {
    Quest[] result = new Quest[quests.size()];
    int index=0;
    if (quests.size() > 0) {
      for (int i=quests.size()-1;i>-1;i--) {
        Quest tmp = quests.get(i);
        if ((tmp.getStatus()==Quest.QUEST_STATUS_ACTIVE) && (index < result.length)){
          result[index] = tmp;
          index++;
        }
      }
      for (int i=quests.size()-1;i>-1;i--) {
        Quest tmp = quests.get(i);
        if ((tmp.getStatus()==Quest.QUEST_STATUS_COMPLETED) && (index < result.length)){
          result[index] = tmp;
          index++;
        }
      }
      for (int i=quests.size()-1;i>-1;i--) {
        Quest tmp = quests.get(i);
        if ((tmp.getStatus()==Quest.QUEST_STATUS_FAILED) && (index < result.length)) {
          result[index] = tmp;
          index++;
        }
      }
    }
    return result;
  }
  
  /**
   * Save journal to DataOutputStream
   * @param os DataOutputStream
   * @throws IOException
   */
  public void saveJournal(DataOutputStream os) throws IOException {
    os.write(quests.size());
    for (int i=0;i<quests.size();i++) {
      Quest tmp = quests.get(i);
      tmp.saveQuest(os);
    }
  }
}
