package org.jheroes.game;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jheroes.journal.Quest;
import org.jheroes.map.Event;
import org.jheroes.map.Map;
import org.jheroes.map.Party;
import org.jheroes.map.character.Character;
import org.jheroes.map.character.CharacterAnimation;
import org.jheroes.map.character.Faces;
import org.jheroes.map.item.Item;
import org.jheroes.map.item.ItemFactory;
import org.jheroes.soundplayer.SoundPlayer;



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
 * Game event handler. This handles event placed on map when player
 * walks over.
 * 
 */ 
public class GameEventHandler {

  private Event event;
  
  /**
   * Target X coordinate
   */
  private int targetX;
  /**
   * Target Y coordinate
   */
  private int targetY;
  
  /**
   * Character who is saying/thinking things
   */
  private Character character;
  /**
   * Current X coordinate
   */
  private int currentX;
  /**
   * Current Y coordinate
   */
  private int currentY;
  
  /**
   * Is event handling done?
   */
  private boolean isDone;
  
  /**
   * Let's party. Required for handling story variables, times
   */
  private Party party;
    
  public boolean isDone() {
    return isDone;
  }

  public void setDone(boolean isDone) {
    this.isDone = isDone;
  }

  private String[] scriptRows;
  private String[] texts;
  private ArrayList<String> npcNamesToMove;
  private ArrayList<String> npcNewTargetWP;
  
  private int textIndex;
  private int faceOverride=-1;
  
  private int eventEffect;
  private int eventX;
  private int eventY;
  
  /**
   * How many turns to pass
   */
  private int passTurns;
  
  /**
   * Journal quest name
   */
  private String journalQuestName;
  /**
   * Journal entry
   */
  private String journalEntry;
  /**
   * Journal Quest status
   */
  private int journalQuestStatus;
  /**
   * Should event be removed
   */
  private boolean removeEvent;
  
  /**
   * List SFX which needs activation
   */
  private ArrayList<String> activateSFXList;
  /**
   * List SFX activation command. For example:
   * Loop, Day, Night, Day#NN, Night#NN or NN
   */
  private ArrayList<String> activateSFXCommandList;
  
  /**
   * Is there SFX to activate?
   */
  private boolean activateSFX;
  /**
   * List of map modifications
   */
  private ArrayList<String> runModifyMapsList;
  /**
   * Should map modification do?
   */
  private boolean runModifyMaps;
  /**
   * List of removed NPCs
   */
  private ArrayList<String> removeNPCsList;
  /**
   * Should NPCs remove?
   */
  private boolean removeNPC;
  private boolean pcTalk;
  private boolean conditionalTalk;
  
  private BufferedImage image;
  private int slowmove;
  private final static int MAX_SLOW_MOVE=2;
  
  private int eventIndex; 
  /**
   * Creates a new Game Event Handler.
   * @param event Event
   * @param character Character who is saying/thinking something
   * @param startX Map coordinates, where effect starts
   * @param startY See above
   * @param party Party variable
   */
  public GameEventHandler(Event event,Character character,
      int startX, int startY, Party party) {
    this.event = event;
    this.party = party;
    this.setEventIndex(-1);
    npcNamesToMove = null;
    npcNewTargetWP = null;
    isDone = false;
    if (character != null) {
      this.character = character;
      targetX = this.character.getX();
      targetY = this.character.getY();
    } else {
      isDone = true;
    }
    currentX = startX;
    currentY = startY;  
    slowmove = 0;
    conditionalTalk = false;
    scriptRows = this.event.getParam(2).split(";");
    if (this.event.getEventCommand() == Event.COMMAND_TYPE_ENCOUNTER) {
      texts = this.event.getParam(0).split("#");
    } else if (this.event.getEventCommand() == Event.COMMAND_TYPE_CONDITIONAL_TRAVEL ||
        this.event.getEventCommand() == Event.COMMAND_TYPE_CONDITIONAL_MAP_TRAVEL) {
      texts = new String[1];
      texts[0] = "";
      conditionalTalk = true;
    } else {
      texts = this.event.getParam(1).split("#");
    }
    textIndex = 0;
    eventEffect = -1;
    eventX = targetX;
    eventY = targetY;
    journalQuestName = null;
    journalQuestStatus = 0;
    journalEntry = null;
    passTurns = 0;
    runModifyMapsList = new ArrayList<String>();
    runModifyMaps = false;
    removeNPCsList = new ArrayList<String>();
    removeNPC = false;
    activateSFXList = new ArrayList<String>();
    activateSFXCommandList = new ArrayList<String>();
    activateSFX = false;
    pcTalk = false;
    this.image = null;
    switch (this.event.getEventCommand()) {
      case Event.COMMAND_TYPE_NPC_YELL: {
        eventEffect = Map.GRAPH_EFFECT_TALK_CURSOR;
        if (this.character.getType()==CharacterAnimation.ANIMATION_TYPE_NORMAL) {
          eventX = this.character.getHeadX();
          eventY = this.character.getHeadY();
        }
        break;
      }
      case Event.COMMAND_TYPE_HOLE_TO_DIG: {
        this.character = null;
        isDone = false;
        targetX = startX;
        targetY = startY;
        break;
      }
      case Event.COMMAND_TYPE_NPC_TALK: {
        eventEffect = Map.GRAPH_EFFECT_TALK_CURSOR;
        texts = new String[1];
        String[] npcNames = event.getParam(0).split("#");
        if (npcNames.length==1) {
          texts[0] = this.character.getLongName()+" starts to talk...";
        } else {
          texts[0] = this.character.getLongName()+" starts to talk to "+npcNames[1]+"...";
        }
        if (this.character.getType()==CharacterAnimation.ANIMATION_TYPE_NORMAL) {
          eventX = this.character.getHeadX();
          eventY = this.character.getHeadY();
        }
        break;
      }
      case Event.COMMAND_TYPE_PC_YELL: {
        String[] names = event.getParam(0).split("#");
        if (names.length == 2) {
          try {
            faceOverride = Integer.valueOf(names[1]);
          } catch (NumberFormatException e) {
            faceOverride = -1;
          }
        }
        break;
      }
      case Event.COMMAND_TYPE_PC_TALK: {
        eventEffect = Map.GRAPH_EFFECT_TALK_CURSOR;
        texts = new String[1];
        pcTalk = true;
        texts[0] = this.character.getLongName()+" starts to talk...";
        if (this.character.getType()==CharacterAnimation.ANIMATION_TYPE_NORMAL) {
          eventX = this.character.getHeadX();
          eventY = this.character.getHeadY();
        }
        break;
      }

    }
  }

  /**
   * Is event PC Talk or not
   * @return boolean
   */
  public boolean isPCTalk() {
    return pcTalk;
  }
  
  public Character getCharacter() {
    return character;
  }
  
  /**
   * Get talk file. Only useable in talk commands
   * @return String
   */
  public String getTalkFile() {
    if (this.event.getEventCommand() == Event.COMMAND_TYPE_NPC_TALK) {
      return "/res/talks/"+this.event.getParam(1)+".tlk";
    }
    if (this.event.getEventCommand() == Event.COMMAND_TYPE_PC_TALK) {
      return "/res/talks/"+this.event.getParam(1)+".tlk";
    }
    return null;
  }
  
  /**
   * Should event removed after running the script
   * @return boolean
   */
  public boolean shouldRemoveEvent() {
    return removeEvent;
  }
  
  /**
   * Should run modify maps run after running the script
   * @return boolean
   */
  public boolean shouldRunModifyMaps() {
    return runModifyMaps;
  }
  
  public ArrayList<String> getRunModifyMapEvents() {
    return runModifyMapsList;
  }
  
  public boolean shouldRemoveNPC() {
    return removeNPC;
  }
  
  public ArrayList<String> getRemoveNPCList() {
    return removeNPCsList;
  }
  
  /**
   * Move camera coordinates toward target
   * @return boolean true if camera moved
   */
  public boolean moveCamera() {    
    boolean moved = false;
    if (slowmove == MAX_SLOW_MOVE) {
      slowmove = 0;
      if (currentX < targetX) {
        currentX++;
        moved = true;
      }
      if (currentX > targetX) {
        currentX--;
        moved = true;
      }
      if (currentY < targetY) {
        currentY++;
        moved = true;
      }
      if (currentY > targetY) {
        currentY--;
        moved = true;
      }     
    } else {
      slowmove++;
    }
    return moved;
  }
  
  /**
   * Get position which should viewed
   * @return int
   */
  public int getCameraX() {
    return currentX;
  }

  /**
   * Get position which should viewed
   * @return int
   */
  public int getCameraY() {
    return currentY;
  }

  /**
   * Get next text to show. Return boolean. If all is done then boolean is true.
   * @return boolean
   */
  public boolean getNextText() {
    if (textIndex < texts.length-1) {
      textIndex++;
    } else {
      isDone = true;
    }
    return isDone;
  }
  
  /**
   * Get next showable text. If no more text then empty string
   * @return String
   */
  public String getText() {
    if (textIndex < texts.length) {
      return texts[textIndex];
    } else {
      return "";
    }
  }
  
  /**
   * Get all texts as array
   * @return
   */
  public String[] getTexts() {
    return texts;
  }
  /**
   * Get sayer's face
   * @return BufferedImage
   */
  public BufferedImage getFaceImage() {
    if (faceOverride != -1) {
      return Faces.getFace(faceOverride);
    } else if (event.getEventCommand() != Event.COMMAND_TYPE_HOLE_TO_DIG) {
      return Faces.getFace(character.getFaceNumber());
    } else {
      return Faces.getFace(Faces.getFaceIndex(5, 12));
    }
  }
  
  /**
   * Get graphical effect, usually bubble
   * @return int, -1 if no graphical effect
   */
  public int getEffect() {
    return eventEffect;
  }
  public int getEffectX() {
    return eventX;
  }
  public int getEffectY() {
    return eventY;
  }
  /**
   * Checks if event is currently playable. This checks the even condition
   * from the script. All conditions are with logical AND. Logical OR is not 
   * possible.
   * @return boolean true if event is playable
   */
  public boolean isPlayable() {
    /*
     * Sample:
     * if day && every 5 mins && story[1] == 1 && playerHas(Rat_poison)
     */
    boolean result = true;
    String[] conditionWords = scriptRows[0].split(" ");
    for (int i=0;i<conditionWords.length;i++) {
      if (conditionWords[i].equalsIgnoreCase("IF"))  {
        if (i!=0) {
       // IF is not the first word or found some where else.
          result = false;
          break;  
        }
        // Nothing to do here
      }
      // Is day
      if (conditionWords[i].equalsIgnoreCase("DAY")) {
        /*
         * Sample: day
         */
        if (!party.isDay()) {
          result = false;
          DebugOutput.debugLog("Is not day!");
        }
      }
      // Is night
      if (conditionWords[i].equalsIgnoreCase("NIGHT")) {
        /*
         * Sample: night
         */
        if (party.isDay()) {
          result = false;
          DebugOutput.debugLog("Is not night!");
        }
      }
      // Is solo
      if (conditionWords[i].equalsIgnoreCase("SOLO")) {
        /*
         * Sample: solo
         */
        // Not solo mode if party size bigger than 1 and not solo mode
        if (party.getMode() != Party.MODE_SOLO_MODE &&
            party.getPartySize() > 1) {
          result = false;
          DebugOutput.debugLog("Is not solo!");
        }
      }

      if ((conditionWords[i].equalsIgnoreCase("EVERY")) &&(i+2<conditionWords.length)) {
        /*
         *  Sample: every 5 mins
         */
        if (conditionWords[i+2].equalsIgnoreCase("MINS")) {
          int value = Integer.valueOf(conditionWords[i+1]);
          if ((party.getMins() % value != 0) || (party.getSecs()==30)) {
            result = false;
            DebugOutput.debugLog("Minutes does not match!");
          }
        }
        if (conditionWords[i+2].equalsIgnoreCase("HOURS")) {
          int value = Integer.valueOf(conditionWords[i+1]);
          if ((party.getHours() % value != 0) || (party.getSecs()==30)){
            result = false;
            DebugOutput.debugLog("Hours does not match!");
          }
        }
      }
      // Check the story variable
      if ((conditionWords[i].startsWith("story")) &&(i+2<conditionWords.length)) {
        /*
         * Sample: story[0] == 1
         */
        String[] parts = conditionWords[i].split("\\[");
        if (parts.length==2) {
          parts[1] = parts[1].replaceAll("\\]", "");
          try {
            int variable = Integer.valueOf(parts[1]);
            String sign = conditionWords[i+1];
            int value = Integer.valueOf(conditionWords[i+2]);
            if (sign.equals("==")) {
              if (party.getStoryVariable(variable)!=value) {
                result = false;
                DebugOutput.debugLog("Story variable not equal to "+value+".");
              }
            }
            if (sign.equals(">")) {
              if (party.getStoryVariable(variable)<=value) {
                result = false;
                DebugOutput.debugLog("Story variable not greater than "+value+".");
              }
            }
            if (sign.equals(">=")) {
              if (party.getStoryVariable(variable)<value) {
                DebugOutput.debugLog("Story variable not greater or equal than "+value+".");
                result = false;
              }
            }
            if (sign.equals("<")) {
              if (party.getStoryVariable(variable)>=value) {
                DebugOutput.debugLog("Story variable not less than "+value+".");
                result = false;
              }
            }
            if (sign.equals("<=")) {
              if (party.getStoryVariable(variable)>value) {
                DebugOutput.debugLog("Story variable not less or equal than "+value+".");
                result = false;
              }
            }
            if (sign.equals("!=")) {
              if (party.getStoryVariable(variable)==value) {
                DebugOutput.debugLog("Story variable equals to"+value+".");
                result = false;
              }
            }
          } catch (NumberFormatException e) {
            // Cannot understand the if clause
            result = false;
            break;
          }
        }
      } // End of Checking story variable
      // Player has certain item
      if (conditionWords[i].startsWith("playerHas")) {
        String[] parts = conditionWords[i].split("\\(");
        if (parts.length==2) {
          parts[1] = parts[1].replaceAll("\\)", "");
          parts[1] = parts[1].replaceAll("_", " ");
          int index = party.getActiveChar().inventoryFindItemByName(parts[1]);
          if (index == -1) {
            
            result = false;
          }
        }
      }
      // Player has NOT certain item
      if (conditionWords[i].startsWith("playerHasNot")) {
        String[] parts = conditionWords[i].split("\\(");
        if (parts.length==2) {
          parts[1] = parts[1].replaceAll("\\)", "");
          parts[1] = parts[1].replaceAll("_", " ");
          int index = party.getActiveChar().inventoryFindItemByName(parts[1]);
          if (index != -1) {            
            result = false;
          }
        }
      }
      // Check player name
      if (conditionWords[i].startsWith("playerIs")) {
        String[] parts = conditionWords[i].split("\\(");
        if (parts.length==2) {
          parts[1] = parts[1].replaceAll("\\)", "");
          parts[1] = parts[1].replaceAll("_", " ");
          boolean playerIs = false;
          if (party.getActiveChar().getName().equalsIgnoreCase(parts[1]) ||
              party.getActiveChar().getLongName().equalsIgnoreCase(parts[1])) {
            playerIs = true;
          }
          if (!playerIs) {
            result = false;
            DebugOutput.debugLog("Player is not "+parts[1]);
          }
        }
      }
    }
    return result;
  }
  
  private void runScriptLine(String row) {
    if (row.startsWith(" ")) {
      DebugOutput.debugLog("Warning script command starts with space!!"+row);
    }
    if (row.startsWith("addJournal")) { // addJournal command
      /* SAMPLE:
       * addJournal(Quest Name#Journal Entry is here#Active)
       */      
      String paramLine = row.substring(row.indexOf("(")+1);
      String[] params = paramLine.split("#");
      if (params.length == 3) {
        journalQuestName = params[0];
        journalEntry = params[1];
        if (params[2].equalsIgnoreCase("Active)")) {
          journalQuestStatus = Quest.QUEST_STATUS_ACTIVE;
        }
        if ((params[2].equalsIgnoreCase("Done)")) || 
            (params[2].equalsIgnoreCase("Completed)"))){
          journalQuestStatus = Quest.QUEST_STATUS_COMPLETED;
        }
        if (params[2].equalsIgnoreCase("Failed)")) {
          journalQuestStatus = Quest.QUEST_STATUS_FAILED;
        }
        DebugOutput.debugLog("Journal updated("+journalQuestName+") is ("+journalQuestStatus+")");
      } else {
        party.addLogText("addJournal line missing #!");
      }
    }
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
              party.setStoryVariable(variable, value,
                  "Event set:"+event.getEventName());
            } else {
              party.addLogText("Story var line missing equal sign!");
            }
            DebugOutput.debugLog("Story["+variable+"]"+"="+value);
          } catch (NumberFormatException e) {
            // Cannot understand the saving story variable clause
          }
        }
      } else {        
        party.addLogText("Story var line missing spaces!");
      }
    }
    if (row.startsWith("shareExp")) {
      /* Sample:
       * shareExp(500)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      int variable = Integer.valueOf(paramLine);
      party.shareExperience(variable);
      DebugOutput.debugLog("shareExp("+variable+")");
    }
    if (row.startsWith("removeEvent()")) {
      /* Sample:
       * removeEvent()
       */
      DebugOutput.debugLog("removeEvent()");
      removeEvent = true;
    }
    if (row.startsWith("endGame()")) {
      /* Sample:
       * endGame()
       */
      DebugOutput.debugLog("endGame()");
      party.setDeadLine(0);
      party.setGameEndDueDeadLine(true);
    }
    if (row.startsWith("giveItem(")) {
      /* Sample:
       * giveItem(Adventurer's diploma)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      Item tmp = ItemFactory.createItemByName(paramLine);
      if (tmp != null) {
        party.getActiveChar().inventoryPickUpItem(tmp);
        DebugOutput.debugLog("giveItem("+tmp.getItemNameInGame()+")");
      }
    }
    if (row.startsWith("removeItem(")) {
      /* Sample:
       * removeItem(Adventurer's diploma)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      int index = party.getActiveChar().inventoryFindItemByName(paramLine);
      if (index != -1) {
        party.getActiveChar().inventoryRemoveItem(index);
        DebugOutput.debugLog("removeItem("+paramLine+")");
      }
    }
    if (row.startsWith("moveNPC(")) {
      /* Sample:
       * moveNPC(NPC Name#Target WP)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      String[] params = paramLine.split("#");
      if (params.length == 2) {
        String npcNameToMove = params[0];
        params[1] = params[1].replaceAll("\\)", "");
        String npcWP = params[1];
        if (npcNamesToMove == null) {
          npcNamesToMove = new ArrayList<String>();
        }
        if (npcNewTargetWP == null) {
          npcNewTargetWP = new ArrayList<String>();
        }
        npcNamesToMove.add(npcNameToMove);
        npcNewTargetWP.add(npcWP);
        DebugOutput.debugLog("NPC move "+npcNameToMove+" to "+npcWP);
      } else {
        party.addLogText("moveNPC line missing #!");
      }

      
    }
    if  (row.startsWith("removeNPC(")) {
      /* Sample:
       * removeNPC(NPC Long Name)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      removeNPC = true;
      removeNPCsList.add(paramLine);
      DebugOutput.debugLog("NPC "+paramLine+" removed.");
    }
    if (row.startsWith("turnChar(") &&
        event.getEventCommand() != Event.COMMAND_TYPE_HOLE_TO_DIG) {
      /*
       * Sample:
       * turnChar(UP)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      if (paramLine.equalsIgnoreCase("UP")) {
        character.setHeading(CharacterAnimation.DIRECTION_NORTH);
        DebugOutput.debugLog("Turn(UP)");
      }
      if (paramLine.equalsIgnoreCase("DOWN")) {
        character.setHeading(CharacterAnimation.DIRECTION_SOUTH);
        DebugOutput.debugLog("Turn(DOWN)");
      }
      if (paramLine.equalsIgnoreCase("LEFT")) {
        character.setHeading(CharacterAnimation.DIRECTION_WEST);
        DebugOutput.debugLog("Turn(LEFT)");
      }
      if (paramLine.equalsIgnoreCase("RIGHT")) {
        character.setHeading(CharacterAnimation.DIRECTION_EAST);
        DebugOutput.debugLog("Turn(RIGHT)");
      }
    }
    if (row.startsWith("runModifyMap(")) {
      /* Sample:
       * runModifyMap(Rock fall)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      runModifyMaps = true;
      runModifyMapsList.add(paramLine);
      DebugOutput.debugLog("runModifyMap("+paramLine+")");
    }
    if (row.startsWith("activateSFX(")) {
      /* Sample:
       * activateSFX(SFXFireplace,Loop)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      String[] params = paramLine.split(",");
      if (params.length == 2) {
        activateSFX =true;
        activateSFXList.add(params[0]);
        activateSFXCommandList.add(params[1]);
        DebugOutput.debugLog("activateSFX("+params[0]+","+params[1]+")");
      } else {
        DebugOutput.debugLog("activateSFX had wrong number of parameters:+"+params.length);
      }
    }
    if (row.startsWith("playSound(")) {
      /* Sample:
       * playSound("Door")
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      SoundPlayer.playSoundBySoundName(paramLine);
      DebugOutput.debugLog("playSound("+paramLine+")");
    }
    if (row.startsWith("passTurns(")) {
      /* Sample:
       * passTurns(5)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      int variable = Integer.valueOf(paramLine);
      addPassTurns(variable);
      DebugOutput.debugLog("passTurns("+variable+")");
    }
    if (row.startsWith("showImage(")) {
      /* Sample:
       * showImage(res/images/ship.png)
       */
      String paramLine = row.substring(row.indexOf("(")+1);
      paramLine = paramLine.replaceAll("\\)", "");
      setImage(paramLine);
    }
  }
  
  /**
   * Run script in event
   */
  public void runScript() {
    DebugOutput.debugLog("Running script");
    if (scriptRows.length >= 1) {
      for (int i = 0;i<scriptRows.length;i++) {
          DebugOutput.debugLog("Row:"+i+" '"+scriptRows[i]+"' parsing...");
         runScriptLine(scriptRows[i]);
      }
    }
  }

  public String getJournalQuestName() {
    return journalQuestName;
  }

  public void setJournalQuestName(String journalQuestName) {
    this.journalQuestName = journalQuestName;
  }

  public String getJournalEntry() {
    return journalEntry;
  }

  public void setJournalEntry(String journalEntry) {
    this.journalEntry = journalEntry;
  }

  public int getJournalQuestStatus() {
    return journalQuestStatus;
  }

  public void setJournalQuestStatus(int journalQuestStatus) {
    this.journalQuestStatus = journalQuestStatus;
  }

  public int getEventIndex() {
    return eventIndex;
  }

  public void setEventIndex(int eventIndex) {
    this.eventIndex = eventIndex;
  }
  
  public boolean isConditionalTalk() {
    return conditionalTalk;
  }

  /**
   * Add number turns should be passed
   * @param i number of turn
   */
  private void addPassTurns(int i) {
    passTurns = passTurns +i;
  }
  
  public int getPassTurns() {
    return passTurns;
  }

  public BufferedImage getImage() {
    return image;
  }

  /**
   * Read image into buffer
   * @param imageFilename
   */
  public void setImage(String imageFilename) {
    try {
      try {
        this.image = ImageIO.read(GameEventHandler.class.getResource(imageFilename));
      } catch (IOException e){
        this.image = null;
        DebugOutput.debugLog("Failed reading image: "+imageFilename+"\nError:"+e.getMessage());
      }
    } catch (IllegalArgumentException e) {
      this.image = null;
      DebugOutput.debugLog("Failed reading image: "+imageFilename+"\nError:"+e.getMessage());      
    }
  }
  
  /**
   * Is there something in NPC going to move lists
   * @return true if NPC are going to move
   */
  public boolean isNPCGoingToMove() {
    if (npcNamesToMove != null && npcNewTargetWP != null) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Get the string array with NPC names to move
   * @return empty array if no moving
   */
  public String[] getNPCsToMove() {
    if (npcNamesToMove != null) {
      String[] array = new String[npcNamesToMove.size()];
      for (int i=0;i<array.length;i++) {
        array[i] = npcNamesToMove.get(i);
      }
      return array;
    } else {
      return new String[0];
    }
  }

  /**
   * Get the String array with target WP names
   * @return empty array if no moving
   */
  public String[] getNPCsNewWP() {
    if (npcNewTargetWP != null) {
      String[] array = new String[npcNewTargetWP.size()];
      for (int i=0;i<array.length;i++) {
        array[i] = npcNewTargetWP.get(i);
      }
      return array;
    } else {
      return new String[0];
    }
  }

  public ArrayList<String> getActivateSFXList() {
    return activateSFXList;
  }

  public ArrayList<String> getActivateSFXCommandList() {
    return activateSFXCommandList;
  }

  public boolean isActivateSFX() {
    return activateSFX;
  }

}
