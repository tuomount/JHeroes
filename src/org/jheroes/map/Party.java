package org.jheroes.map;



import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.jheroes.game.DebugOutput;
import org.jheroes.map.character.Character;
import org.jheroes.soundplayer.SoundPlayer;
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
 * Party information
 * 
 */
public class Party {
 
  public static final int MAX_PARTY_SIZE = 4;
  
  public static final int MODE_PARTY_MODE = 0;
  public static final int MODE_SOLO_MODE = 1;
  
  public static final int MAX_STORY_VARIABLE=256;
  
  public static final int ROLE_PLAYING_PALADIN = 0;
  public static final int ROLE_PLAYING_MERCENARY = 1;
  public static final int ROLE_PLAYING_BULLY = 2;
  public static final int MAX_ROLE = 3;
  
  /**
   * Very first version of party file
   */
  public final static String PARTY_FILE_VERSION10 = "GAMEDATA#1.0";
  /**
   * Second version of party file added total playing time information
   */
  public final static String PARTY_FILE_VERSION11 = "GAMEDATA#1.1";
  /**
   * Third version of party file added deadline information.
   * This was the released version of JHeroes 1.0
   */
  public final static String PARTY_FILE_VERSION12 = "GAMEDATA#1.2";
  /**
   * Fourth version of party file added map version information
   * so that loading characters can be versioned.
   * 
   */
  public final static String PARTY_FILE_VERSION13 = "GAMEDATA#1.3";
  
  /**
   * Characters in party
   */
  private Character partyChars[];
  private int activeChar;
  private int[] storyVariables;
  private String currentMapName;
  private String[] debugStoryVars;
  private int[] lastCastSpell;
  
  /**
   * Skill points not distributed yet
   */
  private int skillPointsLeft[];
  /**
   * Perks not selected yet
   */
  private int perksLeft[];
  /**
   * What mode party is in?
   */
  private int mode;
  
  /**
   * Time hours
   */
  private int timeHour;
  /**
   * Time minutes
   */
  private int timeMin;
  /**
   * Time seconds
   */
  private int timeSec;
  
  /**
   * Time in moons 1-12
   */
  private int timeMoon;
  /**
   * time in days 1-30
   * 1-6: New Moon
   * 7-12: Crescent Moon
   * 13-18: Full Moon
   * 19-24: Eclipse Moon
   * 25-30: Old Moon  
   */
  private int timeDay;
  
  private static final int logLength=15;
  private String[] logText;
  
  /**
   * Game has ended due the dead line
   */
  private boolean gameEndDueDeadLine=false;

  /**
   * Add information about the dead line
   */
  private boolean addJournalInfoDueDeadLine=false;
  
  /**
   * Quest name with dead line
   */
  public static final String QUEST_NAME_WITH_DEAD_LINE = "Hero of Empire";
  
  /**
   * Total amount of playing time
   */
  private long totalPlayingTime;
  
  /**
   * Set start Time
   */
  private long startTime;
  
  /**
   * After how many days game ends. -1 means that deadline is not set yet.
   */
  private int deadLine=-1;
  
  /**
   * Set start time from system time
   */
  public void setStartTime() {
    startTime = System.currentTimeMillis();
  }
  
  /**
   * Mark ending time and adjust Total playing time
   */
  public void setNewEndTime() {
    long endTime = System.currentTimeMillis();
    long playTime = endTime-startTime;
    startTime = endTime;
    totalPlayingTime=totalPlayingTime+playTime;
  }
  
  private int[][] rolePlayingInfo;
  /** 
   * Is party in combat
   */
  private boolean combat;
  
  /**
   * Has party member died in combat
   */
  private boolean heroDown;
  
  /**
   * Gets characer role as a text. Index is always checked that is valid.
   * Always returns role, which default to "Neutral"
   * @param index, character index
   * @return String
   */
  public String getCharacterRole(int index) {
    String result = "Neutral";
    if (index >= 0 && index < getNumberOfPartyMembers()) {
      int paladin = rolePlayingInfo[index][0];
      int mercenary = rolePlayingInfo[index][1];
      int brute = rolePlayingInfo[index][2];
      int total = paladin+mercenary+brute;
      paladin = paladin*100/total;
      mercenary =mercenary*100/total;
      brute =brute*100/total;
      if (paladin >= 90) {
        result = "Pure paladin";
      } else if (paladin > 60) {
        result = "Paladin";
      } else if (mercenary >= 90) {
        result = "Pure mercenary";
      } else if (mercenary > 60) {
        result = "Mercenary";
      } else if (brute >= 90) {
        result = "Pure brute";
      }else if (brute > 60) {
        result = "Brute";
      } else if (paladin > 40 && mercenary > 40) {
        result = "Greedy paladin";
      } else if (brute > 40 && mercenary > 40) {
        result = "Greedy Brute";
      } else if (brute > 40 && paladin > 40) {
        result = "Brutal paladin";
      } 
    }
    return result;
  }
  
  /**
   * Constructor for party
   * @param firstCharacter
   */
  public Party(Character firstCharacter) {
    partyChars = new Character[MAX_PARTY_SIZE];
    skillPointsLeft = new int[MAX_PARTY_SIZE];
    perksLeft = new int[MAX_PARTY_SIZE];
    lastCastSpell = new int[MAX_PARTY_SIZE];
    for (int i =0;i<MAX_PARTY_SIZE;i++) {
      partyChars[i] = null;
    }
    partyChars[0] = firstCharacter;
    skillPointsLeft[0] = 0;
    perksLeft[0] = 0;
    activeChar = 0;
    mode = MODE_PARTY_MODE;
    setTime("12:00:00");
    setDate("15.6");
    setCombat(false);
    logText = new String[logLength];
    storyVariables = new int[MAX_STORY_VARIABLE];
    debugStoryVars = new String[MAX_STORY_VARIABLE];
    rolePlayingInfo = new int[MAX_PARTY_SIZE][MAX_ROLE];
    currentMapName = "start.map";
    totalPlayingTime = 0;
    deadLine = -1;
    this.setStartTime();
  }
  
  /**
   * Check if main character is still alive
   * @return true if it is
   */
  public boolean isMainCharacterAlive() {
    if (partyChars[0] != null) {
      if (!partyChars[0].isDead()) {
        return true;
      }
    } 
    return false;
  }
  
  /**
   * Saves debug info and closes the stream
   * @param os
   * @throws IOException
   */
  public void saveDebugInfo(DataOutputStream os) throws IOException {
    StreamUtilities.writeString(os, "DEBUGINFO");    
    for (int i =0;i<MAX_STORY_VARIABLE;i++) {
      StreamUtilities.writeString(os, debugStoryVars[i]);
    }
    os.close();
  }
  
  /**
   * Loads debug info and closes the stream
   * @param is
   * @throws IOException
   */
  public void loadDebugInfo(DataInputStream is) throws IOException {
    String magicStr = StreamUtilities.readString(is);
    if (magicStr.equals("DEBUGINFO")) {
      for (int i =0;i<MAX_STORY_VARIABLE;i++) {
        debugStoryVars[i] = StreamUtilities.readString(is);
      }      
    }
    is.close();
  }
  
  /**
   * Constructor for party this reads party data from DataInputStream
   * @param is DataInputStream
   * @throws IOException
   */
  public Party(DataInputStream is) throws IOException {
    partyChars = new Character[MAX_PARTY_SIZE];
    skillPointsLeft = new int[MAX_PARTY_SIZE];
    perksLeft = new int[MAX_PARTY_SIZE];
    logText = new String[logLength];
    storyVariables = new int[MAX_STORY_VARIABLE];
    debugStoryVars = new String[MAX_STORY_VARIABLE];
    rolePlayingInfo = new int[MAX_PARTY_SIZE][MAX_ROLE];
    lastCastSpell = new int[MAX_PARTY_SIZE];
    for (int i =0;i<MAX_PARTY_SIZE;i++) {
      partyChars[i] = null;
    }
    String magicStr = StreamUtilities.readString(is);
    if (magicStr.startsWith("GAMEDATA")) {
      String mapVersion = Map.MAP_VERSION_1_0;
      if (magicStr.equals(PARTY_FILE_VERSION13)) {
        mapVersion = StreamUtilities.readString(is);
      }
      int partySize = is.read();
      for (int i=0;i<partySize;i++) {
        partyChars[i] = new Character(0);
        partyChars[i].loadCharacter(is,mapVersion);
        skillPointsLeft[i] = is.read();
        perksLeft[i] = is.read();
        for (int j=0;j<MAX_ROLE;j++) {
          rolePlayingInfo[i][j] = is.read();
        }
      }
      activeChar = is.read();
      mode = is.read();
      timeHour = is.read();
      timeMin = is.read();
      timeSec = is.read();
      timeDay = is.read();
      timeMoon = is.read();
      combat = is.readBoolean();
      for (int i=0;i<MAX_STORY_VARIABLE;i++) {
        storyVariables[i] = is.read();
      }
      currentMapName = StreamUtilities.readString(is);
      if (magicStr.equalsIgnoreCase(PARTY_FILE_VERSION11)) {
        totalPlayingTime = is.readLong();
      } else if (magicStr.equalsIgnoreCase(PARTY_FILE_VERSION12)) {
        totalPlayingTime = is.readLong();
        deadLine = is.readInt();
      } else if (magicStr.equalsIgnoreCase(PARTY_FILE_VERSION13)) {
        totalPlayingTime = is.readLong();
        deadLine = is.readInt();
      } else {
        totalPlayingTime = 0;
      }
      this.setStartTime();
    } else {
      throw new IOException("Not GameData file!");
    }
  }
  
  /**
   * Writes Party data into DataOutputStream. Does not close the stream.
   * @param os DataOutputStream
   * @throws IOException
   */
  public void saveParty(DataOutputStream os) throws IOException {
    StreamUtilities.writeString(os, PARTY_FILE_VERSION13);
    StreamUtilities.writeString(os, Map.CURRENT_MAP_VERSION);
    os.writeByte(this.getPartySize());
    for (int i =0;i<this.getPartySize();i++) {
      partyChars[i].saveCharacter(os);
      os.writeByte(skillPointsLeft[i]);
      os.writeByte(perksLeft[i]);
      for (int j=0;j<MAX_ROLE;j++) {
        os.writeByte(rolePlayingInfo[i][j]);
      }
    }
    os.writeByte(activeChar);
    os.writeByte(mode);
    os.writeByte(timeHour);
    os.writeByte(timeMin);
    os.writeByte(timeSec);
    os.writeByte(timeDay);
    os.writeByte(timeMoon);
    os.writeBoolean(combat);
    for (int i=0;i<MAX_STORY_VARIABLE;i++) {
      os.writeByte(storyVariables[i]);
    }
    StreamUtilities.writeString(os, currentMapName);
    this.setNewEndTime();
    os.writeLong(totalPlayingTime);
    os.writeInt(deadLine);
  }
  
  /**
   * Set role for certain party member
   * @param i index
   * @param role0 ROLE_PALADIN value
   * @param role1 ROLE_MERCENARY value
   * @param role2 ROLE_BULLY value
   */
  public void setRolesForCharacter(int i, int role0,int role1,int role2) {
    if ((i>=0) && (i<MAX_PARTY_SIZE)) {
      rolePlayingInfo[i][0]=role0;
      rolePlayingInfo[i][1]=role1;
      rolePlayingInfo[i][2]=role2;
    }
  }
  
  /**
   * Check role for certain party member
   * @param i index
   * @param role ROLE_PALADIN, ROLE_MERCENARY, ROLE_BULLY
   * @param reward in experience
   */
  public void checkRole(int i, int role, int reward) {
    boolean hasRole=true;
    int sum=0;
    if ((role >=0) && (role < MAX_ROLE)) {
      if ((i>=0) && (i<MAX_PARTY_SIZE)) {
        for (int j=0;j<MAX_ROLE;j++) {
          sum = sum+rolePlayingInfo[i][j];
          if (role != j) {
            if (rolePlayingInfo[i][role] <= rolePlayingInfo[i][j]) {
              hasRole = false;
            }
          }
        }
        if (hasRole) {
          Character chr = this.getPartyChar(i);
          if (chr != null) {
            int xp = reward*rolePlayingInfo[i][role]/sum;
            chr.setExperience(chr.getExperience()+xp);
            this.addLogText(chr.getName()+" gains "+xp+" experience points from role playing.");
          }
        }
        rolePlayingInfo[i][role]++;
      }
    }
  }
  /**
   * Return story variable
   * @param i index
   * @return int
   */
  public int getStoryVariable(int i) {
    if ((i>=0) &&(i < MAX_STORY_VARIABLE)) {
      return storyVariables[i];
    } else {
      return 0;
    }
  }
  
  /**
   * Set story variable
   * @param i
   * @param value
   * @param debugInfo
   */
  public void setStoryVariable(int i, int value,String debugInfo) {
    if ((i>=0) &&(i < MAX_STORY_VARIABLE)) {
      storyVariables[i] = value;
      debugStoryVars[i] = debugInfo;
    }
  }
  
  /**
   * Set story variables according comma separated clause
   * @param clause story[X] = n,story[Y] = m
   * @param debugInfo
   */
  public void setStoryVariable(String clause,String debugInfo) {
    if (clause != null) {
      String[] vars = clause.split(",");
      for (int i=0;i<vars.length;i++) {
        String row = vars[i];
        row = row.trim();
        if (row.startsWith("story")) {
          /* Sample:
           * story[1] = 24
           */
          String[] params = row.split(" ");
          if (params.length == 3) {
            String[] parts = params[0].split("\\[");
            if (parts.length==2) {
              parts[1] = parts[1].replaceAll("\\]", "");
              try {
                int variable = Integer.valueOf(parts[1]);
                String sign = params[1];
                int value = Integer.valueOf(params[2]);
                if (sign.equals("=")) {
                  this.setStoryVariable(variable, value,debugInfo);
                } else {
                  DebugOutput.debugLog("Story var line missing equal sign!");
                }
                DebugOutput.debugLog("Story["+variable+"]"+"="+value);
              } catch (NumberFormatException e) {
                // Cannot understand the saving story variable clause
              }
            }
          } else {        
            DebugOutput.debugLog("Story var line missing spaces!");
          }
        }
      }
    }
  }
  
  public String getDebugInfo(int i) {
    if ((i>=0) &&(i < MAX_STORY_VARIABLE)) {
      if (debugStoryVars[i] != null) {
        return debugStoryVars[i];
      } else {
        return "Not set";
      }
    } else {
      return "";
    }
  }
  
  /**
   * Add One line of lg
   * @param line Log line
   */
  private void addOneLineText(String line) {
    for (int i=0;i<logLength-1;i++) {
      logText[i]=logText[i+1];
    }
    logText[logLength-1] = line;
  }
  
  /**
   * Add text to game log
   * @param str String
   */
  public void addLogText(String str) {
    if (str != null) {
      String[] lines = str.split("\n");
      for (int i=lines.length-1;i>=0;i--) {
        addOneLineText(lines[i]);
      }
    }
  }
  
  /**
   * Handle character effects for whole party.
   */
  public void handleEffectsOnParty() {
    for (int i=0;i<getPartySize();i++) {
      Character chr = getPartyChar(i);
      if (chr != null && chr.getCurrentHP() > 0) {
        // Effects are handled only if character is living one        
        chr.handleEffects(this);
      }
    }
  }
  
  /**
   * Share experience between all party members
   * @param exp experience points gained
   */
  public void shareExperience(int exp) {
    if (getMode() == MODE_PARTY_MODE) {
      exp = exp /getPartySize();
      addLogText("Each party member gained: "+exp+" XP!");
      for (int i=0;i<getPartySize();i++) {
        Character chr = getPartyChar(i);
        if (chr != null) {
          chr.setExperience(chr.getExperience()+exp);
        }
      }
    } else {
      Character chr = getActiveChar();
      if (chr != null) {
        addLogText(chr.getName()+" gained: "+exp+" XP!");
        chr.setExperience(chr.getExperience()+exp);
      }
    }
  }

  /**
   * Check party level up, this should be called once per turn
   */
  public void checkLevelUps() {
    for (int i=0;i<getPartySize();i++) {
      Character chr = getPartyChar(i);
      if (chr != null) {
        if (chr.getExperience() >= chr.getNextNeededExp()) {
          int oldHP = chr.getMaxHP();
          int oldSP = chr.getMaxStamina();
          chr.setLevel(chr.getLevel()+1);
          int inc = chr.getMaxHP()-oldHP;
          chr.setCurrentHP(chr.getCurrentHP()+inc);
          inc = chr.getMaxStamina()-oldSP;
          chr.setCurrentSP(chr.getCurrentSP()+inc);
          String logText = chr.getName()+" has gained a level up!";
          switch (chr.getLevel()) {
          case 2: case 4: case 6: case 8: case 10: case 12: 
          case 14: case 16: case 18:case 20:{
            this.setPerksLeft(i, this.getPerksLeft(i)+1);
            logText = logText +" New level provided a perk!";
            break;
          }
          }
          int skillPoints = 10+chr.getAttribute(Character.ATTRIBUTE_INTELLIGENCE)+chr.getAttribute(Character.ATTRIBUTE_WISDOM);
          this.setSkillPointsLeft(i, this.getSkillPointsLeft(i)+skillPoints);
          this.addLogText(logText);
          SoundPlayer.playSoundBySoundName("LEVELUP");
        }
      }
    }
  }
  
  /**
   * Heal whole party with one single method
   */
  public void healWholeParty() {
    for (int i=0;i<getPartySize();i++) {
      Character chr = getPartyChar(i);
      if (chr != null) {
        chr.setCurrentHP(chr.getMaxHP());
        chr.setCurrentSP(chr.getMaxStamina());
      }
    }
    
  }
  
  /**
   * Whole party rests one turn
   */
  public void restOneTurn() {
    for (int i=0;i<getPartySize();i++) {
      Character chr = getPartyChar(i);
      if (chr != null) {
        chr.getStaminaInRestTurn();
      }
    }
  }
  
  /**
   * Get all the log lines
   * @return String array
   */
  public String[] getLogText() {
    return logText;
  }
  
  /**
   * Get current party size
   * @return int, number between 1 - MAX_PARTY_SIZE
   */
  public int getPartySize() {
    int size =0;
    for (int i =0;i<MAX_PARTY_SIZE;i++) {
      if (partyChars[i] != null) {
        size++;
      }
    }
    return size;
  }
  
  public String getPartyMembersAsString() {
    StringBuilder sb = new StringBuilder();
    for (int i=0;i<MAX_PARTY_SIZE;i++) {
      Character chr = getPartyChar(i);
      if (chr !=  null) {
        sb.append(chr.getLongName());
        sb.append("\n");
        sb.append("Level:");
        sb.append(chr.getLevel());
        sb.append(" Experience:");
        sb.append(chr.getExperience());
        sb.append("\n");
      }
    }
    return sb.toString();
  }
  
  /**
   * Set game time with string two formats:
   * hh:mm and hh:mm:ss
   * @param time String
   */
  public void setTime(String time) {
    if (time != null) {
      String times[] = time.split(":");
      if (times.length == 2) {
        try {
          int i = Integer.valueOf(times[0]);
          int j = Integer.valueOf(times[1]);
          if ((i >=0) && (i < 24) && (j>=0) && (j<60)) {
            timeHour = i;
            timeMin = j;
          }
        } catch (NumberFormatException e) {
          // Do nothing
        }
      }
      if (times.length == 3) {
        try {
          int i = Integer.valueOf(times[0]);
          int j = Integer.valueOf(times[1]);
          int k = Integer.valueOf(times[2]);
          if ((i >=0) && (i < 24) && (j>=0) && (j<60) &&(k>=0)&&(k<60)) {
            timeHour = i;
            timeMin = j;
            timeSec = k;
          }
        } catch (NumberFormatException e) {
          // Do nothing
        }
      }

    }
  }
  
  public String getTimeAsString(boolean isSunClock) {
    StringBuilder sb = new StringBuilder();
    if (isSunClock) {
      if (isDay()) {
        sb.append("Sun is at ");
        sb.append(getHours());
      }
      else {
        sb.append("Sun is down.");
      }
    } else {
      sb.append("Time is ");
      sb.append(getHours());
      
      sb.append(":");
      if (getMins() > 9) {
        sb.append(getMins());
      } else {
        sb.append("0");
        sb.append(getMins());
      }
    }
    return sb.toString();
  }
  
  public String getDeadLineAsString() {
    StringBuilder sb = new StringBuilder();
    if (deadLine != -1) {
      sb.append("in future");
    }
    if (deadLine == 0) {
      sb.append("today");
    }
    if (deadLine == 1) {
      sb.append("tomorrow");
    }
    if (deadLine > 1) {
      if (deadLine == 7) {
        sb.append("in a week");
      } else if (deadLine == 14) {
        sb.append("in two week");
      } else if (deadLine == 21) {
        sb.append("in three week");
      } else if (deadLine == 28) {
        sb.append("in a month");
      } else {
        sb.append("in ");
        sb.append(deadLine);
        sb.append(" days");
      }
    }
    return sb.toString();
  }
  
  /**
   * Returns date as string in format "dd of mm TYPE moon"
   * @return String
   */
  public String getDateAsString() {
    StringBuilder sb = new StringBuilder();
    sb.append(timeDay);
    if (timeDay==1) {
      sb.append("st of ");
    } else
    if (timeDay==2) {
      sb.append("nd of ");
    } else
    if (timeDay==3) {
        sb.append("rd of ");
    } else {
      sb.append("th of ");
    }
    sb.append(timeMoon);
    if (timeMoon ==1) {
      sb.append("st ");
    } else
    if (timeMoon ==2) {
      sb.append("nd ");
    } else
    if (timeMoon ==1) {
      sb.append("rd ");
    } else {
      sb.append("th ");
    }
    /* 1-6: New Moon
    * 7-12: Crescent Moon
    * 13-18: Full Moon
    * 19-24: Eclipse Moon
    * 25-30: Old Moon  
    */
    if ((timeDay>0)&&(timeDay<7)) {
      sb.append("New moon");
    } else 
    if ((timeDay>6)&&(timeDay<13)) {
      sb.append("Cresent moon");
    } else 
    if ((timeDay>12)&&(timeDay<19)) {
      sb.append("Full moon");
    } else 
    if ((timeDay>18)&&(timeDay<25)) {
      sb.append("Eclipse moon");
    } else 
    if ((timeDay>24)&&(timeDay<31)) {
      sb.append("Old moon");
    }
    return sb.toString();
  }
  
  /**
   * Set date in format dd.mm
   * @param date String
   */
  public void setDate(String date) {
    if (date != null) {
      String dates[] = date.split("\\.");
      if (dates.length == 2) {
        try {
          int i = Integer.valueOf(dates[0]);
          int j = Integer.valueOf(dates[1]);
          if ((j >=1) && (j < 13) && (i>=1) && (i<31)) {
            timeMoon = j;
            timeDay = i;
          }
        } catch (NumberFormatException e) {
          // Do nothing
        }
      }
    }
  }
  /**
   * Is day time
   * @return true if time is 6-18 and otherwise false
   */
  public boolean isDay() {
    if ((timeHour >= 6) && (timeHour < 18)) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Add one hour to time
   */
  public void timeAddHour() {
    for (int i=0;i<120;i++) {
      timeAddTurn();
    }
  }  
  /**
   * Add one turn(30s) to time
   */
  public void timeAddTurn() {
    handleEffectsOnParty();
    timeSec=timeSec+30;
    if (timeSec>=60) {
      timeSec=timeSec-60;
      timeMin++;
    }
    if (timeMin>=60) {
      timeMin=timeMin-60;
      timeHour++;
    }
    if (timeHour>=24) {
      timeHour=timeHour-24;
      timeDay++;
    }
    if (timeDay>=31) {
      timeDay=timeDay-30;
      timeMoon++;
    }
    if (timeMoon>=13) {
      timeMoon=timeMoon-12;
    }
    if ((timeHour ==12) && (timeMin==0) && (timeSec==0)) {
      if (deadLine > 0) {
        setDeadLine(deadLine -1);
        if (deadLine % 5 == 0) {
          addJournalInfoDueDeadLine=true;
          this.addLogText(deadLine+" days before crowning...");
        }
        if (deadLine < 3 && deadLine > 0 ) {
          addJournalInfoDueDeadLine=true;
          this.addLogText(deadLine+" days before crowning. You should travel to Hawks Haven Castle.");
        }
      }
      if (deadLine == 0) {
        gameEndDueDeadLine = true;
      }
      this.addLogText("Midday!");
    }
    if ((timeHour ==17) && (timeMin==30) && (timeSec==0)) {
      this.addLogText("Sun is setting!");
    }
    if ((timeHour ==00) && (timeMin==00) && (timeSec==0)) {
      this.addLogText("Midnight!");
    }
    if ((timeHour ==5) && (timeMin==30) && (timeSec==0)) {
      this.addLogText("Sun is rising!");
    }
  }
  
  public void drawTimeBar(BufferedImage screen, int x, int y,int width) {
    int height = 15;
    int hourX = 0;
    Color COLOR_GOLD = new Color(255,216,0);
    Color COLOR_GOLD_DARK = new Color(213,185,0);
    Color COLOR_BLACK = new Color(0,0,0);
    Color COLOR_GRAY_BACKGROUND= new Color(50,50,50);
    Color COLOR_GRAY= new Color(120,120,120);
    Color COLOR_BLUE= new Color(50,50,255);
    Color COLOR_DARK_BLUE= new Color(0,0,50);
    Color COLOR_RED= new Color(255,50,50);
    Color COLOR_DARK_RED= new Color(50,0,0);
    
    
    Graphics2D g = screen.createGraphics();
    GradientPaint gradient = new GradientPaint(0,0, COLOR_BLUE,
        width,height, COLOR_DARK_BLUE, true);
    GradientPaint gradientSun = new GradientPaint(0,0, COLOR_GOLD,
        10,10, COLOR_GOLD_DARK, true);
    if (isDay()) {
      gradient = new GradientPaint(0,0, COLOR_BLUE,
          width,height, COLOR_DARK_BLUE, true);
      hourX = getHours()-6;
    } else {
      gradient = new GradientPaint(0,0, COLOR_BLACK,
          width,height, COLOR_DARK_BLUE, true);
      gradientSun = new GradientPaint(0,0, COLOR_GRAY,
          10,10, COLOR_GRAY_BACKGROUND, true);
      if (getHours()>17) {
        hourX = getHours()-18;
      } else if(getHours() < 6) {
        hourX = getHours()+6;
      }
    }
    if (isCombat()) {
      gradient = new GradientPaint(0,0, COLOR_DARK_RED,
          width,height, COLOR_RED, true);
    }
    g.setPaint(gradient);   
    g.fillRect(x, y, width, height);
    
    hourX = (width-15)/12*hourX;
    int minX =(width-15)/12*getMins()/60;
    g.setPaint(gradientSun);
    g.fillOval(x+hourX+minX+2, y+2, 10, 10);
    g.setColor(COLOR_GOLD_DARK);
    g.drawLine(x, y, x+width, y);
    g.drawLine(x, y, x, y+height);
    g.setColor(COLOR_GOLD);
    g.drawLine(x, y+height, x+width, y+height);
    g.drawLine(x+width, y, x+width, y+height);
  }
  
  /**
   * Get Hours
   * @return int
   */
  public int getHours() {
    return timeHour;
  }
  /**
   * Get Mins
   * @return int
   */
  public int getMins() {
    return timeMin;
  }
  
  public int getSecs() {
    return timeSec;
  }
  
  /**
   * Get Time as string in format hh:mm:ss
   * @return String
   */
  public String getTime() {
    StringBuilder sb = new StringBuilder(8);
    sb.append(String.valueOf(timeHour));
    sb.append(":");
    sb.append(String.valueOf(timeMin));
    sb.append(":");
    sb.append(String.valueOf(timeSec));
    return sb.toString();
  }
  
  private boolean characterChanged = false;

  /**
   * Is setActiveChar called? Use this method only from MAP
   * @return boolean
   */
  public boolean isCharacterChanged() {
    if (characterChanged) {
      characterChanged = false;
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Change active Char
   * @param index
   * 
   */
  public void setActiveChar(int index) {
    if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
      if (partyChars[index] != null) {
        activeChar = index;
        characterChanged = true;
      } else {
        activeChar = getNextActiveChar(index);
        characterChanged = true;
      }
    }
  }
  
  private int getNextActiveChar(int index) {
    do  {    
      if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
        if (partyChars[index] != null) {
          return index;
        } else {
          index++;
        }
          
      }
    } while ((index >= 0) && (index < MAX_PARTY_SIZE));
    return 0;
  }
  
  /**
   * Is Index active char
   * @param index
   * @return boolean if true
   */
  public boolean isActiveChar(int index) {
    if (index == activeChar) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Return active character from party
   * @return Character
   */
  public Character getActiveChar() {
    return partyChars[activeChar];
  }
  
  /**
   * Return active character index
   * @return int
   */
  public int getActiveCharIndex() {
    return activeChar;
  }
  
  /**
   * Get skill points left by index
   * @param index
   * @return int
   */
  public int getSkillPointsLeft(int index) {
    if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
       return skillPointsLeft[index];
    } else {
      return 0;
    }
  }

  /**
   * Set skill points left by index
   * @param index
   * @param value
   */
  public void setSkillPointsLeft(int index, int value) {
    if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
       skillPointsLeft[index] = value;
    }
  }
  
  /**
   * Get perks left by index
   * @param index
   * @return int
   */
  public int getPerksLeft(int index) {
    if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
       return perksLeft[index];
    } else {
      return 0;
    }
  }

  /**
   * Set perks left by index
   * @param index
   * @param value
   */
  public void setPerksLeft(int index, int value) {
    if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
       perksLeft[index] = value;
    }
  }

  /**
   * Get Party character
   * @param index
   * @return Character if available otherwise null
   */
  public Character getPartyChar(int index) {
    if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
      return partyChars[index];
    } else {
      return null;
    }
  }
  
  /**
   * Get next free party member index. Returns -1 if no free
   * @return int
   */
  public int getNextFreePartyPosition() {
    int i=0;
    for (i=0;i<MAX_PARTY_SIZE;i++) {
      Character chr = getPartyChar(i);
      if (chr == null) {
        return i;
      }
    }
    return -1;
  }
  
  /**
   * Get party member index by coordinates
   * @param x
   * @param y
   * @return index if found, -1 if not found
   */
  public int getPartyMemberByCoordinates(int x, int y) {
    for (int i=0;i<MAX_PARTY_SIZE;i++) {
      if (partyChars[i]!=null) {
        if ((x==partyChars[i].getX()) && (y==partyChars[i].getY())) {
          return i;
        }
      }
    }
    return -1;
  }
  
  /**
   * Get party member index by name
   * @param name Character name, can be long or short name
   * @return index if found, -1 if not found
   */
  public int getPartyMemberByName(String name) {
    for (int i=0;i<MAX_PARTY_SIZE;i++) {
      if (partyChars[i]!=null) {
        if ((partyChars[i].getName().equals(name)) ||
            (partyChars[i].getLongName().equals(name))) {
          return i;
        }
      }
    }
    return -1;
  }

  public int getNumberOfPartyMembers() {
    int count = 0;
    for (int i=0;i<MAX_PARTY_SIZE;i++) {
      if (partyChars[i] != null) {
        count++;
      }
    }
    return count;
  }
  
  private static final String KEYWORD_PLAYER_NAME = "<PLAYER>";
  private static final String KEYWORD_PLAYER_FULLNAME = "<PLAYERLONG>";
  private static final String KEYWORD_TIME = "<TIME>";
  private static final String KEYWORD_DATE = "<DATE>";
  private static final String KEYWORD_IWE_LOWER = "<I am/we are>";
  private static final String KEYWORD_IWE_UPPER = "<I am/We are>";
  private static final String KEYWORD_DEADLINE = "<Deadline>";
  
  public String changeAllKeyWord(String input) {
    String result = input;
    result = result.replaceAll(KEYWORD_PLAYER_NAME, this.getActiveChar().getName());
    result = result.replaceAll(KEYWORD_PLAYER_FULLNAME, this.getActiveChar().getLongName());
    result = result.replaceAll(KEYWORD_TIME, this.getTimeAsString(false));
    result = result.replaceAll(KEYWORD_DATE, this.getDateAsString());
    result = result.replaceAll(KEYWORD_DEADLINE, this.getDeadLineAsString());
    if (getNumberOfPartyMembers() ==1) {
      result = result.replaceAll(KEYWORD_IWE_LOWER,"I am");
      result = result.replaceAll(KEYWORD_IWE_UPPER,"I am");
    } else {
      result = result.replaceAll(KEYWORD_IWE_LOWER,"we are");
      result = result.replaceAll(KEYWORD_IWE_UPPER,"We are");
    }
    return result;
  }
  
  /**
   * Set Party character
   * @param index
   * @param newChar
   */
  public void setPartyChar(int index, Character newChar) {
    if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
      partyChars[index] = newChar;
      skillPointsLeft[index] = 0;
      perksLeft[index] = 0;
    }
  }

  /**
   * Remove party character. Move also all the character indexes if needed
   * @param index
   */
  public void removePartyChar(int index) {
    if ((index >= 1) && (index < MAX_PARTY_SIZE)) {
      partyChars[index] = null;
      skillPointsLeft[index] = 0;
      perksLeft[index] = 0;
      for (int i=index;i<MAX_PARTY_SIZE;i++) {
        if ((i+1<MAX_PARTY_SIZE) && (partyChars[i+1] != null)) {
          partyChars[i] = partyChars[i+1];
          skillPointsLeft[i] = skillPointsLeft[i+1];
          perksLeft[i] = perksLeft[i+1];
          partyChars[i+1] = null;
          skillPointsLeft[i+1] = 0;
          perksLeft[i+1] = 0;
        }
      }
    }
    
  }
  
  /**
   * Cast detect magic or identify on whole party
   * @param identify boolean, true if identify spell
   */
  public void castDetectMagicOrIdentifyOnParty(boolean identify) {
    for (int i=0;i<getPartySize();i++) {
      Character chr = getPartyChar(i);
      if (chr != null) {
        chr.inventoryDetectOrIdentify(identify);
      }
    }
    
  }
  
  /**
   * Check that party memeber are close each other. All members
   * should be 8 squares away. if that is true then return true
   * @return boolean
   */
  public boolean checkPartyDistancesClose() {
    boolean result = true;
    int x = this.getActiveChar().getX();
    int y = this.getActiveChar().getY();
    for (int i=0;i<getPartySize();i++) {
      Character chr = getPartyChar(i);
      if (chr != null) {
        if (MapUtilities.getDistance(x, y, chr.getX(), chr.getY()) > 8) {
          result = false;
        }
      }
    }
    return result;
  }
  
  public boolean isCombat() {
    return combat;
  }

  /**
   * Calculate total level of party if party mode
   * if Solo mode only active character level is used
   * @return int
   */
  public int getTotalLevel() {
    int result = 0;
    if (getMode()==MODE_PARTY_MODE) {
      for (int i=0;i<getPartySize();i++) {
        Character chr = getPartyChar(i);
        if (chr != null) {
          result = result +chr.getLevel();        
        }
      }
    }else {
      Character chr = getPartyChar(getActiveCharIndex());
      if (chr != null) {
        result = result +chr.getLevel();        
      }
    }
    return result;
  }
  
  public void setCombat(boolean combat) {
    if ((!isCombat()) && (combat) && (getMode()==MODE_PARTY_MODE)) {
      this.setActiveChar(0);
    }
    if (!combat) {
      setHeroDown(false);
    }
    this.combat = combat;    
  }

  public int getMode() {
    return mode;
  }

  public void setMode(int mode) {
    this.mode = mode;
  }

  public String getCurrentMapName() {
    return currentMapName;
  }

  public void setCurrentMapName(String currentMapName) {
    this.currentMapName = currentMapName;
  }

  public boolean isHeroDown() {
    return heroDown;
  }

  public void setHeroDown(boolean heroDown) {
    this.heroDown = heroDown;
  }
 
  public int getLastCastSpell(int index) {
    if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
      return lastCastSpell[index];
    } else {
      return 0;
    }
  }
  
  public void setLastCastSpell(int index, int spell) {
    if ((index >= 0) && (index < MAX_PARTY_SIZE)) {
      lastCastSpell[index] = spell;
    }
  }

  public long getTotalPlayingTime() {
    return totalPlayingTime;
  }
  
  public String getTotalPlayingTimeAsString() {
    int hours=0;
    int mins=0;
    hours = (int) getTotalPlayingTime()/1000/60/60;
    mins = (int) (getTotalPlayingTime()-hours*1000*3600)/1000/60;
    StringBuilder sb = new StringBuilder();
    sb.append(hours);
    sb.append(":");
    if (mins < 10) {
      sb.append("0");
    }
    sb.append(mins);
    return sb.toString();
  }
  
  public int getDeadLine() {
    return deadLine;
  }

  public void setDeadLine(int deadLine) {
    this.deadLine = deadLine;
  }

  public boolean isGameEndDueDeadLine() {
    return gameEndDueDeadLine;
  }

  public void setGameEndDueDeadLine(boolean gameEndDueDeadLine) {
    this.gameEndDueDeadLine = gameEndDueDeadLine;
  }

  public boolean isAddJournalInfoDueDeadLine() {
    return addJournalInfoDueDeadLine;
  }

  public void setAddJournalInfoDueDeadLine(boolean addJournalInfoDueDeadLine) {
    this.addJournalInfoDueDeadLine = addJournalInfoDueDeadLine;
  }

}
