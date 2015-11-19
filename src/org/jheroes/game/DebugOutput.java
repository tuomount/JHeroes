package org.jheroes.game;

import java.util.Date;

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
 * 
 * Logs debug information to STDOUT
 * 
 */ 
public class DebugOutput {

  /**
   * Is Debug log enable or not
   */
  private static boolean debugLog = false;
  
  /**
   * Enabled or disable debug logging
   * @param logging Boolean
   */
  public static void setDebugLog(boolean logging) {
    debugLog = logging;
  }
  
  /**
   * Output debug log
   * @param logLine String
   */
  public static void debugLog(String logLine) {
    if (debugLog) {
      Date date = new Date();
      System.out.print(date.toString()+": "+logLine+"\n");
    }
  }
}
