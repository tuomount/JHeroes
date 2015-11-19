package org.jheroes.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jheroes.journal.Journal;
import org.jheroes.map.Map;
import org.jheroes.map.Party;



/**
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
 * Thread that does the saving and loading the game
 * 
 */ 
public class GameSaveAndLoadTask implements Runnable {

  public static final int TASK_CREATE_NEW_GAME = 0;
  public static final int TASK_LOAD_GAME = 1;
  public static final int TASK_SAVE_GAME = 2;
  public static final int TASK_TRAVEL_MAP = 3;
  public static final int TASK_LOAD_CURRENT_GAME = 4;
  
  private Party party;
  private Journal journal;
  private String folder;
  private BufferedImage image;
  private Map map;
  private int task;
  private boolean success;
  
  /**
   * TASK_CREATE_NEW_GAME: Party and Journal are required
   * TASK_LOAD_GAME: Folder is just required
   * TASK_SAVE_GAME: party, journal and folder are required
   * TASK_TRAVEL_MAP: party, journal, map, screen and folder are required.
   * Folder in this case is next target map
   * TASK_LOAD_CURRENT GAME: nothing is required
   * @param party
   * @param journal
   * @param screen
   * @param map
   * @param task
   * @param folder
   */
  public GameSaveAndLoadTask(Party party, Journal journal,
      BufferedImage screen, Map map, int task, String folder) {
    this.party = party;
    this.journal = journal;
    this.folder = folder;
    this.task = task;
    this.map = map;
    this.image = screen;
    success = true;
  }
    
  @Override
  public void run() {
    try {
      if (task == TASK_CREATE_NEW_GAME) {
        GameMaps.createNewGameFolder(party, journal);
      }
      if (task == TASK_LOAD_GAME) {
        GameMaps.loadGame(folder);
      }
      if (task == TASK_SAVE_GAME) {
        GameMaps.saveGame(party, journal, folder);
      }
      if (task == TASK_TRAVEL_MAP) {
        GameMaps.saveAndTravelToNewMap(party, journal, map, image, folder);
      }
      if (task == TASK_LOAD_CURRENT_GAME) {
        GameMaps.loadCurrent();
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      success = false;
    }

  }
  
  public boolean isSuccesful() {
    return success;
  }

}
