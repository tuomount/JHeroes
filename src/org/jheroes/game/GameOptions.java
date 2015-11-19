package org.jheroes.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jheroes.musicplayer.OggPlayer;
import org.jheroes.soundplayer.SoundPlayer;



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
 * 
 * 
 * Game Options Class for reading options from the file and writing them to back
 * @author tuomount
 *
 */
public class GameOptions {

  /**
   * Comment character, line needs to start with this
   */
  private static final String COMMENT = "#";
  /**
   * Between key and value
   */
  private static final String DELIM = "=";
  /**
   * Sound Volume option
   */
  private static final String SoundVolume = "soundVolume";
  /**
   * Music Volume Option
   */
  private static final String MusicVolume = "musicVolume";
  /**
   * Options file name which is read/write
   */
  private static final String optionsFile = "options.conf";
  
  /**
   * List of options which are currently supported
   */
  private static final String[] optionLines = {SoundVolume,MusicVolume};
  
  /**
   * Write Options into options configuration file.
   * Writes also information box in configuration file
   * @return boolean, true if successful
   */
  public static boolean writeOptions() {
    boolean result = false;
    BufferedWriter bw;
    try {
      bw = new BufferedWriter(new FileWriter(optionsFile));
      StringBuilder sb = new StringBuilder();
      sb.append("###################################\n");
      sb.append("## ");
      sb.append(Game.GAME_TITLE);
      sb.append(" ");
      sb.append(Game.GAME_VERSION);
      sb.append("\n");
      sb.append("## Options Configuration file\n");
      sb.append("##\n");
      sb.append("###################################\n");
      bw.write(sb.toString());
      sb = new StringBuilder();
      for (int i=0;i<optionLines.length;i++) {
        sb.append(optionLines[i]);
        sb.append("=");
        if (i == 0) {
          // Sound volume
          sb.append(SoundPlayer.getSoundVolume());
        }
        if (i == 1) {
          // Music volume
          sb.append(OggPlayer.getMusicVolume());
        }
        sb.append("\n");
      }
      bw.write(sb.toString());
      bw.close();
      result = true;
    } catch (IOException e) {
      System.out.println("Error while writing "+optionsFile+": "+e.getMessage());
    }
    return result;
  }
  
  /**
   * Read and set options according configuration file
   * @return boolean, true if successful
   */
  public static boolean readAndSetOptions() {
    boolean result = false;
    File file = new File(optionsFile);
    if (file.exists()) {
      try {
        BufferedReader br;
        br = new BufferedReader(new FileReader(optionsFile));
        String line = br.readLine();
          while (line != null) {
            if (!line.startsWith(COMMENT)) {
              String[] params = line.split(DELIM);
              if (params.length == 2) {
                for (int i = 0;i<optionLines.length;i++)
                if (params[0].equalsIgnoreCase(optionLines[i])) {
                  if (i==0) {
                    // Sound Volume
                    int value = 75;
                    try {
                      value = Integer.valueOf(params[1]);
                    } catch (NumberFormatException e) {
                      // Do nothing
                    }
                    SoundPlayer.setSoundVolume(value);
                  }
                  if (i==1) {
                    //MusicVolume
                    int value = 50;
                    try {
                      value = Integer.valueOf(params[1]);
                    } catch (NumberFormatException e) {
                      // Do nothing
                    }
                    OggPlayer.setMusicVolume(value);
                  }
                }
              }
            }
            line = br.readLine();
          }
          br.close();
          result = true;
      } catch (IOException e) {
        System.out.println("Error while reading "+ optionsFile+": "+e.getMessage());
      }
    }
    return result;
  }
}
