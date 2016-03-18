package org.jheroes.map.character;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.jheroes.game.DebugOutput;
import org.jheroes.game.GameMaps;
import org.jheroes.map.Map;
import org.jheroes.utilities.FileUtil;
import org.jheroes.utilities.StreamUtilities;

/**
 * 
 * JHeroes CRPG Engine and Game
 * Copyright (C) 2015  Tuomo Untinen
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
 * Character List contains method for reading and writing character list
 * from and into file streams.
 *
 */
public class CharacterList {

  
  /**
   * Old Magic string for CharacterList file
   */
  private final static String OLD_MAGIC_STRING = "CHRLIST";

  /**
   * New Magic string for CharacterList file
   * New file format contains map version, so
   * reading the character matches the one on map files
   */
  private final static String NEW_MAGIC_STRING = "NPCLIST";

  /**
   * Read Character list from the file
   * @param filename String as filename
   * @return ArrayList of characters or null if read fails
   */
  public static ArrayList<Character> readCharacterList(String filename) {
    ArrayList<Character> characterList = new ArrayList<Character>();
    DataInputStream is;
    String mapVersion = null;
    characterList = new ArrayList<Character>();
    InputStream tmpIs = GameMaps.class.getResourceAsStream(filename);
    if (tmpIs != null) {
      is = new DataInputStream(tmpIs);
      try {
        DebugOutput.debugLog("Reading character list...");
        // CHRLIST
        byte[] magicBytes = NEW_MAGIC_STRING.getBytes("US-ASCII");
        byte[] buf = new byte[7];
        for (int i = 0;i<7;i++) {
         buf[i] = is.readByte();
        }
        if (FileUtil.equalByteArray(buf, magicBytes)) {
           mapVersion = StreamUtilities.readString(is);
        } else {
          magicBytes = OLD_MAGIC_STRING.getBytes("US-ASCII");
          if (!FileUtil.equalByteArray(buf, magicBytes)) {
            DebugOutput.debugLog("Not a character list file");
            return null;
          }
        }

        int size = is.readInt();
        if (size > 0) {
          characterList.clear();
          for (int i=0;i<size;i++) {
            Character tmpChr = new Character(0);
            tmpChr.loadCharacter(is,mapVersion);
            characterList.add(tmpChr);
          }
        } else {
          DebugOutput.debugLog("Character list is empty!");
          return null;
        }
        
        return characterList;
      } catch (IOException e) {
        e.printStackTrace();
        DebugOutput.debugLog("Failed reading it...");
        return null;
      } finally {
        if (is != null) {
          try {
            is.close();
          } catch (IOException e) {
          }
          is = null;
        }
      }
    } else {
      DebugOutput.debugLog("Character list not found");     
      return null;
    }
  }
  
  public static void writeCharacterList(String filename, 
      ArrayList<Character> characterList) {
    DataOutputStream os;
    try {
      os = new DataOutputStream(new FileOutputStream(filename));
      try {
        os.writeBytes(NEW_MAGIC_STRING);
        StreamUtilities.writeString(os, Map.CURRENT_MAP_VERSION);
        os.writeInt(characterList.size());
        for (int i=0;i<characterList.size();i++) {
          Character tmpChr = characterList.get(i);
          tmpChr.saveCharacter(os);
        }
        
        
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          os.close();
        } catch (IOException e) {
          // DO nothing
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  
}
