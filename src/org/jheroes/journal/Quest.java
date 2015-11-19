package org.jheroes.journal;

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
 * Journal information about one single quest
 * 
 */
public class Quest {

  
  public static final int QUEST_STATUS_NOT_ACTIVE=0;
  public static final int QUEST_STATUS_ACTIVE=1;
  public static final int QUEST_STATUS_COMPLETED=2;
  public static final int QUEST_STATUS_FAILED=3;
  private String questName;
  private int status;
  private ArrayList<String> questEntries;
  
  public Quest(String name) {
    questName = name;
    setStatus(QUEST_STATUS_ACTIVE);
    questEntries = new ArrayList<String>();
  }
  
  /**
   * Create Quest entry from DataInputStream
   * @param is DataInputStream
   * @throws IOException
   */
  public Quest(DataInputStream is) throws IOException {
    status = is.read();
    questName = StreamUtilities.readString(is);
    questEntries = new ArrayList<String>();
    int size = is.read();
    for (int i=0;i<size;i++) {
      questEntries.add(StreamUtilities.readString(is));
    }
  }
  
  public void addQuestEntry(String entry) {
    questEntries.add(entry);
  }
  public int sizeOfQuestEntries() {
    return questEntries.size();
  }
  public String getQuestEntry(int index) {
    return questEntries.get(index);
  }
  public String getQuestName() {
    return questName;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
  
  /**
   * Save quest entry to DataOutputStream
   * @param os DataOutputStream
   * @throws IOException
   */
  public void saveQuest(DataOutputStream os) throws IOException {
    os.write(status);
    StreamUtilities.writeString(os, questName);
    os.write(questEntries.size());
    for (int i=0;i<questEntries.size();i++) {
      StreamUtilities.writeString(os, questEntries.get(i));
    }
  }  
}
