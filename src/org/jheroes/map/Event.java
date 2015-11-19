package org.jheroes.map;

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
 * Events place on the map
 * 
 */
public class Event {

  public static final byte TYPE_POINT = 0;
  public static final byte TYPE_REGION = 1;
  
  public static final String[] EVENT_TYPE_STRINGS = {"Point","Region"};
    
  
  /**
   * TYPE_POINT or TYPE_REGION. Region has two corners
   */
  private byte eventType;
  
  /**
   * Coordinates, x1,y1 are used on TYPE_POINT and on TYPE_REGION
   * as first corner.
   */  
  private int x1,y1,x2,y2;
  
  /**
   * Talk Travel name used when saving should happen after travel
   */
  public static final String TALK_TRAVEL = "#!#TALK_TRAVEL_COMMAND#!#";
  
  public static final byte COMMAND_TYPE_WAYPOINT = 0;
  public static final byte COMMAND_TYPE_SIGN = 1;
  public static final byte COMMAND_TYPE_TRAVEL = 2;
  public static final byte COMMAND_TYPE_QUICK_TRAVEL = 3;
  public static final byte COMMAND_TYPE_LOCKED_DOOR = 4;
  public static final byte COMMAND_TYPE_CLOCK = 5;
  public static final byte COMMAND_TYPE_NPC_YELL = 6;
  public static final byte COMMAND_TYPE_NPC_TALK = 7;
  public static final byte COMMAND_TYPE_PC_YELL = 8;
  public static final byte COMMAND_TYPE_LOOK_INFO = 9;
  public static final byte COMMAND_TYPE_MODIFY_MAP = 10;
  public static final byte COMMAND_TYPE_ENCOUNTER = 11;
  public static final byte COMMAND_TYPE_PC_TALK = 12;
  public static final byte COMMAND_TYPE_CONDITIONAL_TRAVEL = 13;
  public static final byte COMMAND_TYPE_LOCKED_GATE = 14;
  public static final byte COMMAND_TYPE_PLAY_SOUND = 15;
  public static final byte COMMAND_TYPE_YESNO_QUESTION = 16;
  public static final byte COMMAND_TYPE_HOLE_TO_DIG = 17;
  public static final byte COMMAND_TYPE_CONDITIONAL_MAP_TRAVEL = 18;

  public static final String[] EVENT_COMMAND_STRINGS = {"Waypoint","Sign",
          "Travel","Quick Travel","Locked Door","Clock","NPC Yell","NPC Talk",
          "PC Yell","Look Info","Modify Map","Encounter","PC Talk",
          "Conditional travel","Locked Gate","Play sound","YesNo Question",
          "Hole to dig","Cond. map travel"};

  /**
   * Event command
   */
  private byte eventCommand;
  
  /**
   * Event name, very important for waypoint
   */
  private String eventName;
  
  private String parameters[];
  
  public Event(int x,int y) {
    eventType = TYPE_POINT;
    setEventCommand(COMMAND_TYPE_WAYPOINT);
    setEventName("WP");
    parameters = null;
    x1=x;
    y1=y;
  }

  /**
   * Creates a new event from DataInputStream
   * @param is DataInputStream
   * @throws IOException
   */
  public Event(DataInputStream is) throws IOException {
    eventType = is.readByte();
    if (eventType == TYPE_POINT) {
      x1 = is.readInt();
      y1 = is.readInt();     
    } else if (eventType == TYPE_REGION) {
      x1 = is.readInt();
      y1 = is.readInt();     
      x2 = is.readInt();
      y2 = is.readInt();           
    } else {
      throw new IOException("Corrupted event!");
    }
    eventName = StreamUtilities.readString(is);
    eventCommand = is.readByte();
    byte params = is.readByte();
    if (params > 0) {
      parameters = new String[params];
      for (int i=0;i<parameters.length;i++) {
        parameters[i] = StreamUtilities.readString(is);
      }
    } else {
      parameters = null;
    }
  }
  
  /**
   * Changes event type to region
   * @param x1
   * @param y1
   * @param x2, must be bigger than x1, if not does nothing
   * @param y2, must be bigger than y1, if not does nothing
   */
  public void setRegion(int x1,int y1,int x2,int y2) {
    if ((x2 > x1) && (y2 > y1)) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      eventType = TYPE_REGION;
    }
  }
  
  public int getX1() {
    return x1;
  }

  public int getX2() {
    if (eventType == TYPE_REGION) {
      return x2;  
    } else
      return x1;
    
  }

  public int getY1() {
    return y1;
  }

  public int getY2() {
    if (eventType == TYPE_REGION) {
      return y2;  
    } else
      return y1;
    
  }

  /**
   * For regions get middle point of X
   * @return int
   */
  public int getX() {
    if (eventType == TYPE_REGION) {
      return (x1+x2)/2;  
    } else
      return x1;
    
  }
  /**
   * For regions get middle point of Y
   * @return int
   */
  public int getY() {
    if (eventType == TYPE_REGION) {
      return (y1+y2)/2;  
    } else
      return y1;
    
  }
  
  /**
   * Get Random point inside of region. For point just X coordinate
   * @return int
   */
  public int getRandomX() {
    if (eventType == TYPE_REGION) {
      return DiceGenerator.getRandom(x1, x2);  
    } else
      return x1;

  }
  /**
   * Get Random point inside of region. For point just X coordinate
   * @return int
   */
  public int getRandomY() {
    if (eventType == TYPE_REGION) {
      return DiceGenerator.getRandom(y1, y2);  
    } else
      return y1;

  }
  
  
  /**
   * Changes event type to point
   * @param x
   * @param y
   */
  public void setPoint(int x, int y) {
    this.x1 = x;
    this.y1 = y;
    eventType = TYPE_POINT;
  }
  /**
   * get event type
   * @return TYPE_POINT = 0;
   *         TYPE_REGION = 1;
   */
  public byte getType() {
    return eventType;
  }
    
  /**
   * Is coordinates at event
   * @param x
   * @param y
   * @return true if coordinates are at event
   */
  public boolean isEventAt(int x, int y) {
    boolean result = false;
    if (getType()==TYPE_POINT) {
      if ((x==x1) && (y==y1)) {
        result = true;
      }
    }
    if (getType()==TYPE_REGION) {
      if ((x>=x1) && (y>=y1)&&(x<=x2)&&(y<=y2)) {
        result = true;
      }
    }
    return result;
  }


  public void setEventName(String eventName) {
    this.eventName = eventName;
  }


  public String getEventName() {
    return eventName;
  }


  public void setEventCommand(byte eventCommand) {
    this.eventCommand = eventCommand;
    switch (this.eventCommand) {
    case COMMAND_TYPE_WAYPOINT:     parameters = null; break;
    /**
     * Parameter is displayed when sign is looked
     */
    case COMMAND_TYPE_SIGN:         parameters = new String[1]; break;
    /**
     * Parameter 0: Is map name where to travel (Empty is current map)
     * Parameter 1: Is waypoint at target map
     * Parameter 2: Is Text displayed before travelling, question yes/on
     */
    case COMMAND_TYPE_TRAVEL:       parameters = new String[3]; break;
    /**
     * Parameter 0: Is map name where to travel (Empty is current map)
     * Parameter 1: Is waypoint at target map
     * Parameter 2: Sound, Door, entrance, rope etc...
     */
    case COMMAND_TYPE_QUICK_TRAVEL: parameters = new String[3]; break;    
    /**
     * Parameter 0: Key to open / Difficulty to open lock with pick locks 1000 cannot be
     * pick locked. This is one single string with / as separator
     * Parameter 1: Is map name where to travel (Empty is current map)
     * Parameter 2: Is waypoint at target map
     */
    case COMMAND_TYPE_LOCKED_DOOR: parameters = new String[3]; break;
    /**
     * Parameter 0: Clock type SUN/CLOCK
     */
    case COMMAND_TYPE_CLOCK: parameters = new String[1]; break;
    /**
     * Param0: NPC name
     * Param1: Text, # Page change
     * Param2: Script
     */
    case COMMAND_TYPE_NPC_YELL: parameters = new String[3]; break;
    /**
     * Param0: NPC name
     * Param1: Talk
     * Param2: Script
     */
    case COMMAND_TYPE_NPC_TALK: parameters = new String[3]; break;
    /**
     * Param0: PC name/Empty for active PC
     * Param1: Text, # Page change
     * Param2: Script
     */
    case COMMAND_TYPE_PC_YELL: parameters = new String[3]; break;
    /**
     * Parameter is displayed when target is looked
     */
    case COMMAND_TYPE_LOOK_INFO:         parameters = new String[1]; break;
    /**
     * Param0: New tile number
     * Param1: Graph effect number
     * Param2: Add(Place tile to default position/Remove(0 Object, 1 Deco, 2 top)
     */
    case COMMAND_TYPE_MODIFY_MAP:         parameters = new String[3]; break;
    /**
     * Param0: NPCs names separated by #
     * Param1: Waypoint
     * Param2: Script
     */
    case COMMAND_TYPE_ENCOUNTER:         parameters = new String[3]; break;
    /**
     * Param0: PC name
     * Param1: Talk
     * Param2: Script
     */
    case COMMAND_TYPE_PC_TALK: parameters = new String[3]; break;
    /**
     * Parameter 0: Is Text displayed before travelling, question yes/on, if empty no question
     * Parameter 1: Is waypoint at target map # SOUND
     * Parameter 2: Script
     */
    case COMMAND_TYPE_CONDITIONAL_TRAVEL:  parameters = new String[3]; break;
    /**
     * Parameter 0: Key to open / Difficulty to open lock with pick locks 1000 cannot be
     * pick locked. This is one single string with / as separator. This can be also
     * "-" which means gate is not locked
     * Parameter 1: Single/North Remove the gate position or also one above
     * Parameter 2: Opening sound
     */
    case COMMAND_TYPE_LOCKED_GATE: parameters = new String[3]; break;
    /**
     * Parameter 0: Sound name
     * Parameter 1: NN/Loop
     */
    case COMMAND_TYPE_PLAY_SOUND: parameters = new String[2]; break;
    /**
     * Parameter 0: Question to ask
     * Parameter 1: Set story variables story[N] = X,story[M] = Y on YES answer
     * Parameter 2: Set story variables story[N] = X,story[M] = Y on NO answer
     */
    case COMMAND_TYPE_YESNO_QUESTION: parameters = new String[3]; break;
    /**
    * Param0: Reserved for future use
    * Param1: Text, # Page change
    * Param2: Script
    */
   case COMMAND_TYPE_HOLE_TO_DIG: parameters = new String[3]; break;
   /**
    * Parameter 0: Is Text displayed before travelling, question yes/on, if empty no question
    * Parameter 1: Is waypoint at target map # Target Map
    * Parameter 2: Script
    */
   case COMMAND_TYPE_CONDITIONAL_MAP_TRAVEL:  parameters = new String[3]; break;
    }

  }

  /**
   * Get target map named used on traveling commands
   * @return String, null if not found
   */
  public String getTargetMapName() {
    String result = null;
    switch (this.eventCommand) {
    case COMMAND_TYPE_TRAVEL:       result = getParam(0); break;
    case COMMAND_TYPE_QUICK_TRAVEL: result = getParam(0); break;
    }
    return result;    
  }  
  
  /**
   * Get displayed text
   * @return String, null if not found
   */
  public String getDisplayedText() {
    String result = null;
    switch (this.eventCommand) {
    case COMMAND_TYPE_SIGN:         result = getParam(0); break;
    case COMMAND_TYPE_TRAVEL:       result = getParam(1); break;
    case COMMAND_TYPE_QUICK_TRAVEL: result = getParam(1); break;
    case COMMAND_TYPE_LOCKED_DOOR:  result = getParam(0); break;
    case COMMAND_TYPE_LOOK_INFO:    result = getParam(0); break;
    case COMMAND_TYPE_NPC_TALK:    result = getParam(0); break;
    case COMMAND_TYPE_NPC_YELL:    result = getParam(0); break;
    case COMMAND_TYPE_PC_YELL:    result = getParam(0); break;
    case COMMAND_TYPE_MODIFY_MAP:   result = getParam(2); break;
    case COMMAND_TYPE_ENCOUNTER:   result = getParam(1); break;
    case COMMAND_TYPE_PC_TALK:    result = getParam(0); break;
    case COMMAND_TYPE_CONDITIONAL_TRAVEL: result = getParam(1); break;
    case COMMAND_TYPE_LOCKED_GATE:  result = getParam(0); break;
    case COMMAND_TYPE_PLAY_SOUND:  result = getParam(0); break;
    case COMMAND_TYPE_YESNO_QUESTION:  result = getParam(0); break;
    case COMMAND_TYPE_HOLE_TO_DIG:  result = getParam(0); break;
    case COMMAND_TYPE_CONDITIONAL_MAP_TRAVEL: result = getParam(1); break;
    }
    return result;    
  }
  
  /**
   * Get target waypoint name used on traveling commands
   * @return String, null if not found
   */
  public String getTargetWaypointName() {
    String result = null;
    switch (this.eventCommand) {
    case COMMAND_TYPE_TRAVEL:       result = getParam(1); break;
    case COMMAND_TYPE_QUICK_TRAVEL: result = getParam(1); break;
    case COMMAND_TYPE_LOCKED_DOOR: result = getParam(2); break;
    case COMMAND_TYPE_ENCOUNTER: result = getParam(1); break;
    case COMMAND_TYPE_CONDITIONAL_TRAVEL: { 
      String[] results = getParam(1).split("#");
      result = results[0]; 
      break;
    }
    case COMMAND_TYPE_CONDITIONAL_MAP_TRAVEL: { 
      String[] results = getParam(1).split("#");
      result = results[0]; 
      break;
    }
    }
    return result;
    
  }
  
  /**
   * Get sound effect made by event
   * @return String, if no sound then return empty string
   */
  public String getEventSound() {
    String result = "";
    switch (this.eventCommand) {
      case COMMAND_TYPE_LOCKED_DOOR: result = "Door"; break;
      case COMMAND_TYPE_QUICK_TRAVEL: result = getParam(2); break;
      case COMMAND_TYPE_CONDITIONAL_TRAVEL: { 
       String[] results = getParam(1).split("#");
       if (results.length == 2) {
         result = results[1]; 
       }
       break;
      }
      case COMMAND_TYPE_LOCKED_GATE: result = getParam(2); break;
      case COMMAND_TYPE_PLAY_SOUND: result = getParam(0); break;
    }
    return result;
  }
  
  /**
   * Get LockDoorKeyName if eventcommand if locked door or locked gate
   * @return String, keyname or ""
   */
  public String getLockDoorKeyName() {
    if (this.eventCommand==COMMAND_TYPE_LOCKED_DOOR) {        
      String[] results = getParam(0).split("/");
      return results[0];
    } else if (this.eventCommand==COMMAND_TYPE_LOCKED_GATE) {
        if (getParam(0).equals("-")) {
          return "";
        } else {
          String[] results = getParam(0).split("/");
          return results[0];
        }
    } else {
      return "";
    }
  }

  /**
   * Get locked door/gate difficulty
   * Gate can be open then difficulty is 0.
   * @return int
   */
  public int getLockDoorDifficulty() {
    if (this.eventCommand==COMMAND_TYPE_LOCKED_DOOR) {
      String[] results = getParam(0).split("/");
      if (results.length==2) {
        try {
          return Integer.valueOf(results[1]);
        } catch (NumberFormatException e) {
          return 1000;
        }
      } else {
        return 1000;
      }
    } else if (this.eventCommand==COMMAND_TYPE_LOCKED_GATE) {
      if (getParam(0).equals("-") || getParam(0).isEmpty()) {
        return 0;
      } else {
        String[] results = getParam(0).split("/");
        if (results.length==2) {
          try {
            return Integer.valueOf(results[1]);
          } catch (NumberFormatException e) {
            return 1000;
          }
        } else {
          return 1000;
        }
      }
      
    } else{
      return 1000;
    }
  }

  public void setParam(int index, String value) {
    if ((index >-1) && (index < getMaxParams())) {
      parameters[index] = value;
    }         
  }
  
  /**
   * Get parameter from index
   * @param index
   * @return if no parameter is available for index "" is returned.
   */
  public String getParam(int index) {
    String result = "";
    if ((index >-1) && (index < getMaxParams())) {
      result = parameters[index];
    }
    return result;
  }
  
  /**
   * Get number of parameters
   * @return 0-3
   */
  public int getMaxParams() {
    int result = 0;
    if (parameters != null) {
      result = parameters.length;
    }
    return result;
  }

  public byte getEventCommand() {
    return eventCommand;
  }
 
  /**
   * Get event command type as a string
   * @return String
   */
  public String getEventCommandAsString() {
    String result = "Unknown";
    switch (this.eventCommand) {
    case COMMAND_TYPE_WAYPOINT:           result = "Waypoint"; break;
    case COMMAND_TYPE_SIGN:               result = "Sign"; break;
    case COMMAND_TYPE_TRAVEL:             result = "Travel"; break;
    case COMMAND_TYPE_QUICK_TRAVEL:       result = "Quick Travel"; break;
    case COMMAND_TYPE_LOCKED_DOOR:        result = "Locked Door"; break;
    case COMMAND_TYPE_CLOCK:              result = "Clock"; break;
    case COMMAND_TYPE_NPC_YELL:           result = "NPC Yell"; break;
    case COMMAND_TYPE_NPC_TALK:           result = "NPC Talk"; break;
    case COMMAND_TYPE_PC_YELL:            result = "PC Yell"; break;
    case COMMAND_TYPE_LOOK_INFO:          result = "Look Info"; break;
    case COMMAND_TYPE_MODIFY_MAP:         result = "Modify map"; break;
    case COMMAND_TYPE_ENCOUNTER:          result = "Encounter"; break;
    case COMMAND_TYPE_PC_TALK:            result = "PC Talk"; break;
    case COMMAND_TYPE_CONDITIONAL_TRAVEL: result = "Conditional travel"; break;
    case COMMAND_TYPE_LOCKED_GATE:        result = "Locked Gate"; break;
    case COMMAND_TYPE_PLAY_SOUND:         result = "Play sound"; break;
    case COMMAND_TYPE_YESNO_QUESTION:     result = "YESNO question"; break;
    case COMMAND_TYPE_HOLE_TO_DIG:        result = "Hole to dig"; break;
    case COMMAND_TYPE_CONDITIONAL_MAP_TRAVEL: result = "Cond. map travel"; break;
    }
    return result;
  }
  
  /**
   * Is Event waypoint
   * @return boolean
   */
  public boolean isWaypoint() {
    if (this.eventCommand == COMMAND_TYPE_WAYPOINT) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Writes event into DataOutputStream
   * @param os DataOutputStream
   * @throws IOException
   */
  public void saveEvent(DataOutputStream os) throws IOException {
    os.writeByte(eventType);
    if (eventType == TYPE_POINT) {
      os.writeInt(x1);
      os.writeInt(y1);
    }
    if (eventType == TYPE_REGION) {
      os.writeInt(x1);
      os.writeInt(y1);
      os.writeInt(x2);
      os.writeInt(y2);
    }
    StreamUtilities.writeString(os, eventName);
    os.writeByte(eventCommand);
    if (parameters != null) {
      os.writeByte(parameters.length);
      for (int i=0;i<parameters.length;i++) {
        StreamUtilities.writeString(os,parameters[i]);
      }
    } else {
      os.writeByte(0);
    }
  }
  
  @Override 
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getEventName());
    if (getType()==TYPE_POINT) {
      sb.append(" X:");
      sb.append(getX1());
      sb.append(" Y:");
      sb.append(getY1());
    } else {
      sb.append(" X1:");
      sb.append(getX1());
      sb.append(" Y1:");
      sb.append(getY1());
      sb.append(" X2:");
      sb.append(getX2());
      sb.append(" Y2:");
      sb.append(getY2());      
    }
    sb.append(" ");
    sb.append(getEventCommandAsString());
    if (getMaxParams()>0) {
      for (int i=0;i<getMaxParams();i++) {
        sb.append(" ");
        sb.append(getParam(i));
      }
    }
    return sb.toString();
  }
  
}
