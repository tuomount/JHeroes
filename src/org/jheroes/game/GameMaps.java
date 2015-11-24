package org.jheroes.game;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jheroes.journal.Journal;
import org.jheroes.map.Map;
import org.jheroes.map.Party;
import org.jheroes.map.character.Character;



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
 * Contains list of MAPS in game and helper methods when saving and loading maps
 * 
 */
public class GameMaps {

  public final static String MAPS_IN_JAR = "/res/maps/";
  public final static String MAP_FILE_TUTORIAL = "tutorial.map";
  
  public final static String PARTY_DATA_FILE = "party.dat";
  public final static String DEBUG_DATA_FILE = "debug.dat";
  
  public final static String SCREEN_FILE = "screen.png";
  
  public final static String CHARACTER_LIST_FILE = "/res/characterlist.res";
  
  private final static String[] GAMEMAPS = {MAP_FILE_TUTORIAL};
  
  private static volatile int progress;
  
  private static Map newMap=null;
  private static String newMapName=null;
  private static Journal newJournal=null;
  private static Party newParty= null;
  private static ArrayList<Character> characterList=null;
  
  /**
   * Get character list
   * @return ArrayList<Character> or null if read fails
   */
  public static ArrayList<Character> getCharacterList() {
    if (characterList == null) {
      DataInputStream is;
      characterList = new ArrayList<Character>();
      InputStream tmpIs = GameMaps.class.getResourceAsStream(CHARACTER_LIST_FILE);
      if (tmpIs != null) {
        is = new DataInputStream(tmpIs);
        try {
          DebugOutput.debugLog("Reading character list...");
          // CHRLIST
          byte[] magicBytes = {67,72,82,76,73,83,84};
          for (int i = 0;i<7;i++) {
           byte byt = is.readByte();
           if (byt != magicBytes[i]) {
             is.close();
             DebugOutput.debugLog("Character list is empty!");
             return null;
           }
          }
          int size = is.readInt();
          if (size > 0) {
            characterList.clear();
            for (int i=0;i<size;i++) {
              Character tmpChr = new Character(0);
              tmpChr.loadCharacter(is);
              characterList.add(tmpChr);
            }
          } else {
            is.close();
            DebugOutput.debugLog("Character list is empty!");
            return null;
          }
          
          is.close();
          return characterList;
        } catch (IOException e) {
          e.printStackTrace();
          DebugOutput.debugLog("Failed reading it...");
          return null;
        }
      } else {
        DebugOutput.debugLog("Character list not found");     
        return null;
      }
    } else {
      return characterList;
    }
  }
  
  /**
   * Get character from list by Long name
   * @param name String
   * @return Character if found otherwise null
   */
  public static Character getCharacterFromListByName(String name) {
    if (characterList == null) {
      getCharacterList();      
    }
    for (int i=0;i<characterList.size();i++) {
      Character tmp = characterList.get(i);
      if(tmp.getLongName().equalsIgnoreCase(name)) {
        return tmp;
      }
    }
    return null;
  }
  
  /**
   * Save current game when game starts
   * @param party
   * @param journal
   * @throws IOException
   */
  public static void createNewGameFolder(Party party, Journal journal) 
      throws IOException {
    progress = 0;
    int progressStep = 100/(GAMEMAPS.length*2+1);
    File dir = new File("current");
    if (!dir.exists()) {
      if (!dir.mkdir()) {
        throw new IOException("Cannot create folder \"current\"!");
      }
    }
    // Folder created and creating now map files
    for (int i=0;i<GAMEMAPS.length;i++) {
      InputStream is = GameMaps.class.getResourceAsStream(GameMaps.MAPS_IN_JAR+GAMEMAPS[i]);
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      Map map = new Map(dis);
      map.generateRandomItems();
      // Map file read
      progress = progress +progressStep;
      // Writing map file
      File file = new File("current/"+GAMEMAPS[i]);
      DebugOutput.debugLog("Copying maps:"+GAMEMAPS[i]);
      try {
        FileOutputStream os = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);
        map.saveMap(dos);
      } catch (SecurityException e) {
        throw new IOException("Cannot write file:"+file.getAbsolutePath());
      } catch (FileNotFoundException e) {
        throw new IOException("Cannot open file:"+file.getAbsolutePath());
      }
      progress = progress +progressStep;
    }
    File file = new File("current/"+PARTY_DATA_FILE);
    DebugOutput.debugLog("Creating party file:"+PARTY_DATA_FILE);
    File debugFile = new File("current/"+DEBUG_DATA_FILE);
    try {
      FileOutputStream os = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(os);
      DataOutputStream dos = new DataOutputStream(bos);
      party.saveParty(dos);
      progress = progress +progressStep;
      journal.saveJournal(dos);
      progress = 100;
      dos.close();
      if (Game.debugMode) {
        os = new FileOutputStream(debugFile);
        dos = new DataOutputStream(os);
        party.saveDebugInfo(dos);
      }
    } catch (SecurityException e) {
      throw new IOException("Cannot write file:"+file.getAbsolutePath());
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
  }
  
  /**
   * Save current game. Used when pressed ESC in game
   * @param party
   * @param journal
   * @param map
   * @param image
   * @throws IOException
   */
  public static void saveCurrentMapAndParty(Party party, Journal journal, 
      Map map, BufferedImage image) throws IOException
  {
    progress = 0;
    int progressStep = 100/4;
    File dir = new File("current");
    if (!dir.exists()) {
      throw new IOException("Folder \"current\" does not exists!?! Game has not started?!?");
    }
    // Save current map
    File file = new File("current/"+party.getCurrentMapName());
    DebugOutput.debugLog("Saving current map:"+party.getCurrentMapName());
    try {
      FileOutputStream os = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(os);
      DataOutputStream dos = new DataOutputStream(bos);
      map.saveMap(dos);
    } catch (SecurityException e) {
      throw new IOException("Cannot write file:"+file.getAbsolutePath());
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    progress = progress +progressStep;
    
    // Save party and journal
    file = new File("current/"+PARTY_DATA_FILE);
    DebugOutput.debugLog("Saving party file:"+PARTY_DATA_FILE);
    File debugFile = new File("current/"+DEBUG_DATA_FILE);
    try {
      FileOutputStream os = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(os);
      DataOutputStream dos = new DataOutputStream(bos);
      party.saveParty(dos);
      progress = progress +progressStep;
      journal.saveJournal(dos);
      progress = progress +progressStep;
      dos.close();
      if (Game.debugMode) {
        os = new FileOutputStream(debugFile);
        dos = new DataOutputStream(os);
        party.saveDebugInfo(dos);
      }
    } catch (SecurityException e) {
      throw new IOException("Cannot write file:"+file.getAbsolutePath());
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    
    // Saving the screen shot
    file = new File("current/"+SCREEN_FILE);
    DebugOutput.debugLog("Saving screen file");
    try {
      ImageIO.write(image, "png", file);
    } catch (IOException e) {
      System.err.println("Failing to write screen shot!");
      e.printStackTrace();
    }
    progress = 100;

  }
  
  /**
   * Check if current folder exists or not
   * @return boolean true if exists
   */
  public static boolean isCurrentFolderThere() {
    File dir = new File("current");
    return dir.exists();
  }
  
  /**
   * Check if save folder exists or not
   * @param folder String
   * @return boolean
   */
  public static boolean isSaveFolderThere(String folder) {
    File dir = new File(folder);
    return dir.exists();
  }
  
  /**
   * Save current map and travel to new target map
   * Target map can be get via GameMaps.getNewMap()
   * @param party Will be saved
   * @param journal Will be saved
   * @param map Will be saved
   * @param image Will be saved
   * @param targetMap String of new target map
   * @throws IOException
   */
  public static void saveAndTravelToNewMap(Party party, Journal journal, 
  Map map, BufferedImage image, String targetMap ) throws IOException
  {
    progress = 0;
    int progressStep = 100/5;
    File dir = new File("current");
    if (!dir.exists()) {
      throw new IOException("Folder \"current\" does not exists!?! Game has not started?!?");
    }
    // Save current map
    File file = new File("current/"+party.getCurrentMapName());
    DebugOutput.debugLog("Saving current map:"+party.getCurrentMapName());
    try {
      FileOutputStream os = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(os);
      DataOutputStream dos = new DataOutputStream(bos);
      map.saveMap(dos);
    } catch (SecurityException e) {
      throw new IOException("Cannot write file:"+file.getAbsolutePath());
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    progress = progress +progressStep;
    
    // Save party and journal
    file = new File("current/"+PARTY_DATA_FILE);
    DebugOutput.debugLog("Saving party file:"+PARTY_DATA_FILE);
    File debugFile = new File("current/"+DEBUG_DATA_FILE);
    try {
      FileOutputStream os = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(os);
      DataOutputStream dos = new DataOutputStream(bos);
      party.saveParty(dos);
      progress = progress +progressStep;
      journal.saveJournal(dos);
      progress = progress +progressStep;
      dos.close();
      if (Game.debugMode) {
        os = new FileOutputStream(debugFile);
        dos = new DataOutputStream(os);
        party.saveDebugInfo(dos);
      }
    } catch (SecurityException e) {
      throw new IOException("Cannot write file:"+file.getAbsolutePath());
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    
    // Saving the screen shot
    file = new File("current/"+SCREEN_FILE);
    DebugOutput.debugLog("Saving screen shot");
    try {
      ImageIO.write(image, "png", file);
    } catch (IOException e) {
      System.err.println("Failing to write screen shot!");
      e.printStackTrace();
    }
    progress = progress +progressStep;
    
    file = new File("current/"+targetMap);
    DebugOutput.debugLog("Loading new map"+targetMap);
    newMap = null;
    try {
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      newMap = new Map(dis);
      newMap.generateRandomItems();
      newMapName = targetMap;
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    progress = 100;

  }
  
  /**
   * Game is saved to folder
   * @param party
   * @param journal
   * @param image
   * @param folder
   * @throws IOException
   */
  public static void saveGame(Party party, Journal journal, 
      String folder) throws IOException {
    progress = 0;
    int progressStep = 100/(GAMEMAPS.length*2+3);
    File dir = new File(folder);
    if (!dir.exists()) {
      if (!dir.mkdir()) {
        throw new IOException("Cannot create folder \""+folder+"\"!");
      }
    }
    
    // Folder created and creating now map files
    for (int i=0;i<GAMEMAPS.length;i++) {
      File file = new File("current/"+GAMEMAPS[i]);
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      Map map = new Map(dis);
      DebugOutput.debugLog("Loading map file:"+GAMEMAPS[i]);
      // Map file read
      progress = progress +progressStep;
      // Writing map file
      DebugOutput.debugLog("Saving map file:"+GAMEMAPS[i]);
      file = new File(folder+"/"+GAMEMAPS[i]);
      try {
        FileOutputStream os = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);
        map.saveMap(dos);
      } catch (SecurityException e) {
        throw new IOException("Cannot write file:"+file.getAbsolutePath());
      } catch (FileNotFoundException e) {
        throw new IOException("Cannot open file:"+file.getAbsolutePath());
      }
      progress = progress +progressStep;
    }
    File file = new File(folder+"/"+PARTY_DATA_FILE);
    DebugOutput.debugLog("Saving party file:"+PARTY_DATA_FILE);
    File debugFile = new File(folder+"/"+DEBUG_DATA_FILE);
    try {
      FileOutputStream os = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(os);
      DataOutputStream dos = new DataOutputStream(bos);
      party.saveParty(dos);
      progress = progress +progressStep;
      journal.saveJournal(dos);
      progress = progress +progressStep;
      dos.close();
      if (Game.debugMode) {
        os = new FileOutputStream(debugFile);
        dos = new DataOutputStream(os);
        party.saveDebugInfo(dos);
      }
    } catch (SecurityException e) {
      throw new IOException("Cannot write file:"+file.getAbsolutePath());
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    // Saving the screen shot
    file = new File("current/"+SCREEN_FILE);
    DebugOutput.debugLog("Saving screen shot");
    BufferedImage image = ImageIO.read(file);
    file = new File(folder+"/"+SCREEN_FILE);
    try {
      ImageIO.write(image, "png", file);
    } catch (IOException e) {
      System.err.println("Failing to write screen shot!");
      e.printStackTrace();
    }
    progress = 100;


  }
  
  public static Party readPartyFromSave(String folder) throws IOException {
    File dir = new File(folder);
    if (!dir.exists()) {
        throw new IOException("Cannot open folder \""+folder+"\"!");
    }
    File file = new File(folder+"/"+PARTY_DATA_FILE);
    try {
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      Party party = new Party(dis);
      dis.close();
      return party;
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }        
  }
  
  /**
   * Loads new game from folder and saves all to current
   * use GameMaps.getNewJournal, getNewMap, getNewParty
   * @param folder String
   * @throws IOException
   */
  public static void loadGame(String folder) throws IOException {
    progress = 0;
    int progressStep = 100/(GAMEMAPS.length*2+6);
    File dir = new File("current");
    if (!dir.exists()) {
      if (!dir.mkdir()) {
        throw new IOException("Cannot create folder \"current\"!");
      }
    }
    
    // Folder created and creating now map files
    for (int i=0;i<GAMEMAPS.length;i++) {
      File file = new File(folder+"/"+GAMEMAPS[i]);
      DebugOutput.debugLog("Loading map file:"+GAMEMAPS[i]);
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      Map map = new Map(dis);
      map.generateRandomItems();
      // Map file read
      progress = progress +progressStep;
      // Writing map file
      file = new File("current/"+GAMEMAPS[i]);
      DebugOutput.debugLog("Saving map file:"+GAMEMAPS[i]);
      try {
        FileOutputStream os = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);
        map.saveMap(dos);
      } catch (SecurityException e) {
        throw new IOException("Cannot write file:"+file.getAbsolutePath());
      } catch (FileNotFoundException e) {
        throw new IOException("Cannot open file:"+file.getAbsolutePath());
      }
      progress = progress +progressStep;
    }
    // Load party and journal
    File file = new File(folder+"/"+PARTY_DATA_FILE);
    File debugFile = new File(folder+"/"+DEBUG_DATA_FILE);
    try {
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      newParty = new Party(dis);
      progress = progress +progressStep;
      newJournal = new Journal(dis);
      progress = progress +progressStep;
      dis.close();     
      if (Game.debugMode && debugFile.exists()) {
        is = new FileInputStream(debugFile);
        dis = new DataInputStream(is);
        newParty.loadDebugInfo(dis);
      }
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    
    // Save party and journal to current
    file = new File("current/"+PARTY_DATA_FILE);
    DebugOutput.debugLog("Saving party file:"+PARTY_DATA_FILE);
    debugFile = new File("current/"+DEBUG_DATA_FILE);
    try {
      FileOutputStream os = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(os);
      DataOutputStream dos = new DataOutputStream(bos);
      newParty.saveParty(dos);
      progress = progress +progressStep;
      newJournal.saveJournal(dos);
      progress = progress +progressStep;
      dos.close();
      if (Game.debugMode) {
        os = new FileOutputStream(debugFile);
        dos = new DataOutputStream(os);
       newParty.saveDebugInfo(dos);
      }
    } catch (SecurityException e) {
      throw new IOException("Cannot write file:"+file.getAbsolutePath());
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    file = new File(folder+"/"+SCREEN_FILE);
    BufferedImage image = ImageIO.read(file);
    // Saving the screen shot
    file = new File("current/"+SCREEN_FILE);
    try {
      ImageIO.write(image, "png", file);
    } catch (IOException e) {
      System.err.println("Failing to write screen shot!");
      e.printStackTrace();
    }
    progress = progress +progressStep;
    
    file = new File("current/"+newParty.getCurrentMapName());
    newMap = null;
    try {
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      newMap = new Map(dis);
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    progress = 100;

  }
  
  /**
   * Loads game from current folder
   * @throws IOException
   */
  public static void loadCurrent() throws IOException {
    progress = 0;
    int progressStep = 100/3;
    File dir = new File("current");
    if (!dir.exists()) {
        throw new IOException("Cannot open folder \"current\"!");
    }
    
    // Load party and journal
    File file = new File("current/"+PARTY_DATA_FILE);
    DebugOutput.debugLog("Loading party file:"+PARTY_DATA_FILE);
    try {
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      newParty = new Party(dis);
      progress = progress +progressStep;
      newJournal = new Journal(dis);
      progress = progress +progressStep;
      dis.close();     
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    
    
    file = new File("current/"+newParty.getCurrentMapName());
    DebugOutput.debugLog("Loading map file:"+newParty.getCurrentMapName());
    newMap = null;
    try {
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      newMap = new Map(dis);
      newMap.generateRandomItems();
    } catch (FileNotFoundException e) {
      throw new IOException("Cannot open file:"+file.getAbsolutePath());
    }
    progress = 100;

  }
  
  /**
   * Get current progress of handling maps.
   * @return int 0-100
   */
  public static int getProgress() {
    return progress;
  }

  /**
   * Get loaded new map
   * @return Map or null
   */
  public static Map getNewMap() {
    return newMap;
  }
  
  /**
   * Get travelled map name
   * @return String or null
   */
  public static String getNewMapName() {
    return newMapName;
  }

  /**
   * Get loaded new Journal
   * @return Journal or null
   */
  public static Journal getNewJournal() {
    return newJournal;
  }


  /**
   * Get loaded new party
   * @return Party or null
   */
  public static Party getNewParty() {
    return newParty;
  }


}
