package org.jheroes.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.labels.ImageLabel;
import org.jheroes.gui.panels.GamePanel;
import org.jheroes.journal.Journal;
import org.jheroes.map.Party;
import org.jheroes.map.character.Character;
import org.jheroes.map.character.Faces;
import org.jheroes.map.item.Item;
import org.jheroes.map.item.ItemFactory;
import org.jheroes.talk.GreetingLine;
import org.jheroes.talk.Talk;
import org.jheroes.talk.TalkAction;
import org.jheroes.talk.TalkLine;




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
 * Game talk screen when actually talking to some NPC
 * 
 */

public class GameTalk extends GamePanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static int MAX_LINES = 8;
  private Party party;
  private Journal journal;
  private Character npc;
  private Talk talk;
  private ActionListener listener;
  private GameTextArea sayText;
  private GameButton[] playerLines;
  private int state;
  private boolean endTalk;
  private boolean runShop;
  private boolean runTrade;
  private boolean runExit;
  private int exitDirection;
  private boolean moveAway;
  private String teleportWP;
  private boolean teleportToExit;
  private int moveAwayDirection;
  private boolean checkNPCPosition;
  private boolean removeNPC;
  private boolean addNPC;
  private boolean moveToWP;
  private String moveWP;
  private String travelWP;
  private String travelMap;
  private boolean dead;
  
  /**
   * 
   * @param party Party variable
   * @param npc Whom to talk or who talks
   * @param talk Talk file
   * @param journal Journal for adding notes
   * @param listener ActionListener
   */
  public GameTalk(Party party, Character npc,Talk talk, Journal journal,
      ActionListener listener) {
    super(true);
    this.journal = journal;
    this.party = party;
    this.npc = npc;
    this.talk = talk;
    this.listener = listener;
    state = 0;
    endTalk = false;
    dead = false;
    runShop = false;
    runTrade = false;
    removeNPC = false;
    addNPC = false;
    checkNPCPosition = false;
    teleportToExit = false;
    travelWP = null;
    travelMap = null;
    this.setLayout(new GridLayout(0, 1));
    this.add(createNPCTalkPanel());
    playerLines = new GameButton[MAX_LINES];
    this.add(createPlayerTalkPanel());
    updateGreeting();
    updatePlayerLines();    
    setRunExit(false);
    setExitDirection(0);
  }
  
  /**
   * Create player talk panel
   * @return GamePanel
   */
  private GamePanel createPlayerTalkPanel() {
    GamePanel playerPane = new GamePanel(false);
    GamePanel result = new GamePanel(true);    
    result.setLayout(new BorderLayout());
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameLabel label = new GameLabel(party.getActiveChar().getLongName());
    result.add(label,BorderLayout.NORTH);
    label = new GameLabel("");
    result.add(label,BorderLayout.SOUTH);
    
    playerPane.setLayout(new BoxLayout(playerPane, BoxLayout.X_AXIS));
    playerPane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    ImageLabel face = new ImageLabel(Faces.getFace(party.getActiveChar().getFaceNumber()), true);
    playerPane.add(Box.createRigidArea(new Dimension(5, 25)));
    playerPane.add(face);
    playerPane.add(Box.createRigidArea(new Dimension(5, 5)));
    playerPane.add(createPlayerSaying());
    playerPane.add(Box.createRigidArea(new Dimension(15, 15)));
    result.add(playerPane,BorderLayout.CENTER);
    return result;
  }
  
  private static final String KEYWORD_NPC_NAME = "<NPC>";
  private static final String KEYWORD_NPC_FULLNAME = "<NPCLONG>";
  
  private String changeAllMagicWords(String input) {
    String result = input;
    result = result.replaceAll(KEYWORD_NPC_NAME, npc.getName());
    result = result.replaceAll(KEYWORD_NPC_FULLNAME, npc.getLongName());
    result = party.changeAllKeyWord(result);
    return result;
  }
  
  private void updateGreeting() {
    boolean matchFound = false;
    int size = talk.getNumberOfGreetings();
    for (int i=0;i<size;i++) {
      GreetingLine line = talk.getGreetingByIndex(i);
      switch (line.getCondition()) {
      case Talk.TALK_CONDITION_ALWAYS_TRUE: {
        sayText.setText(changeAllMagicWords(line.getText()));
        state = line.getNextState();
        matchFound = true;
        break;
      }
      case Talk.TALK_CONDITION_TIME_HOUR_EQUALS: {
        if (party.getHours()==line.getParam1()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_TIME_HOUR_GREATER: {
        if (party.getHours()>line.getParam1()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_TIME_HOUR_LESS: {
        if (party.getHours()<line.getParam1()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_TIME_IS_DAY: {
        if (party.isDay()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_TIME_IS_NIGHT : {
        if (!party.isDay()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_EQUAL: {
        if (party.getActiveChar().getEffectiveAttribute(line.getParam1())==line.getParam2()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_GREATER: {
        if (party.getActiveChar().getEffectiveAttribute(line.getParam1())>line.getParam2()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_LESS: {
        if (party.getActiveChar().getEffectiveAttribute(line.getParam1())<line.getParam2()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_GOLD_EQUAL: {
        if (party.getActiveChar().getMoney()==line.getParam1()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_GOLD_GREATER: {
        if (party.getActiveChar().getMoney()>line.getParam1()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_GOLD_LESS: {
        if (party.getActiveChar().getMoney()<line.getParam1()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_LEVEL_EQUAL: {
        if (party.getActiveChar().getLevel()==line.getParam1()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_LEVEL_GREATER: {
        if (party.getActiveChar().getLevel()>line.getParam1()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_LEVEL_LESS: {
        if (party.getActiveChar().getLevel()<line.getParam1()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_HAS_ITEM: {
        if (party.getActiveChar().inventoryFindItemByName(line.getItemName())!=-1) {
          sayText.setText(line.getText());
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_AGAINST_NPC: {
        int targetNumber = npc.getEffectiveSkill(line.getParam1())+50;        
        if (party.getActiveChar().skillCheck(line.getParam1(), targetNumber)>=Character.CHECK_SUCCESS) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_GREATER: {
        int targetNumber = line.getParam2();        
        if (party.getActiveChar().skillCheck(line.getParam1(), targetNumber)>=Character.CHECK_SUCCESS) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL: {        
        if (party.getStoryVariable(line.getParam1())==line.getParam2()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_STORY_VARIABLE_GREATER: {        
        if (party.getStoryVariable(line.getParam1())>line.getParam2()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_STORY_VARIABLE_LESS: {        
        if (party.getStoryVariable(line.getParam1())<line.getParam2()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_IS_SOLO_MODE: {        
        if (party.getMode()==Party.MODE_SOLO_MODE ||
            party.getPartySize() == 1) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_IS_PARTY_MEMBER: {        
        if (npc.isPlayer()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_IS_ROOM_IN_PARTY: {        
        if (party.getPartySize() < Party.MAX_PARTY_SIZE) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_PLAYER_NAME_IS: {
        if (party.getActiveChar().getLongName().equalsIgnoreCase(line.getItemName())) {
          sayText.setText(line.getText());
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL_AND_PLAYER_NAME_IS: {        
        if (party.getStoryVariable(line.getParam1())==line.getParam2() &&
            party.getActiveChar().getLongName().equalsIgnoreCase(line.getItemName())) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }
      case Talk.TALK_CONDITION_TIME_HOUR_IS_BETWEEN: {
        if (party.getHours()>=line.getParam1()&&
            party.getHours()<=line.getParam2()) {
          sayText.setText(changeAllMagicWords(line.getText()));
          state = line.getNextState();
          matchFound = true;
        }
        break;
      }

      
      } // END OF SWITCH
      if (matchFound) {
        break;
      }
    }
  }
  
  private GamePanel createPlayerSaying() {
    GamePanel result = new GamePanel(true);
    result.setLayout(new GridLayout(0,1));
    for (int i=0;i<MAX_LINES;i++) {
      playerLines[i] = new GameButton("Foo", ActionCommands.TALK_LINES[i]);
      playerLines[i].addActionListener(listener);
      result.add(playerLines[i]);
    }
    return result;
  }
  
  private void updatePlayerLines() {
    TalkLine[] lines = talk.getAllLinesWithState(state);
    for (int i=0;i<MAX_LINES;i++) {
      playerLines[i].setVisible(false);
    }
    for (int i=0;i<Math.min(MAX_LINES, lines.length);i++) {
      playerLines[i].setText(changeAllMagicWords( lines[i].getPlayerText()));
      playerLines[i].setVisible(true);
    }
  }
  /**
   * Create NPC Talk Panel
   * @return GamePanel
   */
  private GamePanel createNPCTalkPanel() {
    GamePanel npcPane = new GamePanel(false);
    GamePanel result = new GamePanel(true);
    result.setLayout(new BorderLayout());
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameLabel label = new GameLabel(npc.getLongName());
    result.add(label,BorderLayout.NORTH);
    label = new GameLabel("");
    result.add(label,BorderLayout.SOUTH);
    npcPane.setLayout(new BoxLayout(npcPane, BoxLayout.X_AXIS));
    npcPane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    ImageLabel face = new ImageLabel(Faces.getFace(npc.getFaceNumber()), true);
    npcPane.add(Box.createRigidArea(new Dimension(5, 25)));
    npcPane.add(face);
    npcPane.add(Box.createRigidArea(new Dimension(5, 5)));
    sayText = new GameTextArea();
    sayText.setWrapStyleWord(true);
    sayText.setLineWrap(true);
    sayText.setEditable(false);
    npcPane.add(sayText);
    npcPane.add(Box.createRigidArea(new Dimension(15, 15)));
    result.add(npcPane,BorderLayout.CENTER);
    return result;
  }
  
  private void handleTalkLineActions(TalkLine line, boolean istrue){
    endTalk = true;
    for (int i=0;i<line.getActionsSize(istrue);i++) {
      TalkAction action = line.getAction(istrue, i);
      switch (action.getAction()) {
      case TalkAction.ACTION_ADD_JOURNAL: {
        journal.addQuestEntry(party, action.getItemName(), action.getJournalEntry(),
            action.getValue());
        break;
      }
      case TalkAction.ACTION_CHANGE_MONEY: {
        party.getActiveChar().setMoney(party.getActiveChar().getMoney()+action.getValue());
        break;
      }
      case TalkAction.ACTION_RUN_TO_EXIT: {
        setExitDirection(action.getValue());
        setRunExit(true);
        break;
      }
      case TalkAction.ACTION_MOVE_AWAY: {
        setMoveAwayDirection(action.getValue());
        setMoveAway(true);
        break;
      }
      case TalkAction.ACTION_SET_DEADLINE: {
        if (action.getValue() == 0) {
          party.setDeadLine(-1);
        } if (party.getDeadLine() == -1) {
          party.setDeadLine(action.getValue());
        } else {
          int result = party.getDeadLine()+action.getValue();
          if (result < 1) {
            result = 1;
          } else {
            party.setDeadLine(result);
          }
        }
        break;
      }
      case TalkAction.ACTION_END_GAME: {
        party.setGameEndDueDeadLine(true);
        party.setDeadLine(0);
        break;
      }
      case TalkAction.ACTION_MOVE_TO_WP: {
        setMoveAwayDirection(action.getValue());
        setMoveWP(action.getItemName());
        setMoveToWP(true);
        break;
      }
      case TalkAction.ACTION_CHANGE_TALK_STATE: {
        // State 255 means keep the old state but do not end
        // conversation
        if (action.getValue() != 255) {
          state = action.getValue();
        } 
        endTalk = false;
        break;
      }
      case TalkAction.ACTION_TELEPORT_TO_EXIT: {
        setTeleportToExit(true);
        setTeleportWP(action.getItemName());
        break;
      }
      case TalkAction.ACTION_GIVE_ITEM: {
        Character chr = party.getActiveChar();
        Item tmp = ItemFactory.createItemByName(action.getItemName());
        if (tmp != null) {
          chr.inventoryPickUpItemForce(tmp);
        }
        break;
      }
      case TalkAction.ACTION_GIVE_PARTY_EXPERIENCE: {
        party.shareExperience(action.getValue());
        break;
      }
      case TalkAction.ACTION_HEAL_PARTY: {
        party.healWholeParty();
        break;
      }
      case TalkAction.ACTION_FIGHT: {
        Character chr = party.getActiveChar();
        npc.setHostilityLevel(Character.HOSTILITY_LEVEL_AGGRESSIVE);
        npc.addTaskKillPartyMember(chr.getLongName());
        party.setCombat(true);
        setCheckNPCPosition(true);
        break;
      }
      case TalkAction.ACTION_NO_ACTION: {
        // Do nothing
        break;
      }
      case TalkAction.ACTION_START_SOLO: {
        int index =party.getPartyMemberByName(npc.getLongName());
        if (index != -1 || index != 0) {
          party.setActiveChar(index);
          party.setMode(Party.MODE_SOLO_MODE);
        }
        break;
      }
      case TalkAction.ACTION_PASS_TURNS: {
        for (int j=0;j<action.getValue();j++) {
          party.timeAddTurn();
        }
        setCheckNPCPosition(true);
        break;
      }
      case TalkAction.ACTION_ROLE_PLAYING_REWARD: {
        party.checkRole(party.getActiveCharIndex(), action.getStoryVariable(), action.getValue());
        break;
      }
      case TalkAction.ACTION_SET_VARIABLE: {
        party.setStoryVariable(action.getStoryVariable(), action.getValue(),
               "Talk set:"+npc.getLongName());
        break;
      }
      case TalkAction.ACTION_START_SHOP: {
        setRunShop(true);
        break;
      }
      case TalkAction.ACTION_DIE: {
        setDead(true);
        break;
      }
      case TalkAction.ACTION_START_TRADE: {
        setRunTrade(true);
        break;
      }
      case TalkAction.ACTION_REMOVE_ITEM: {
        Character chr = party.getActiveChar();
        int index = chr.inventoryFindItemByName(action.getItemName());
        if (index != -1) {
          chr.inventoryRemoveItem(index);
        }
        break;
      }
      case TalkAction.ACTION_SET_TRAVEL_WP: {
        travelWP = action.getItemName();
        break;
      }
      case TalkAction.ACTION_SET_TRAVEL_MAP: {
        travelMap = action.getItemName();
        break;
      }
      case TalkAction.ACTION_TAKE_ITEM: {
        Character chr = party.getActiveChar();
        int index = chr.inventoryFindItemByName(action.getItemName());
        if (index != -1) {
          Item item = chr.inventoryGetIndex(index);
          npc.inventoryPickUpItem(item);
          chr.inventoryRemoveItem(index);
        }
        break;
      }
      case TalkAction.ACTION_LEAVE_PARTY: {
        int index = party.getPartyMemberByName(npc.getLongName());
        if (index != -1) {
          party.removePartyChar(index);
          Character newNPC = new Character(0);
          newNPC.clearTaskList();
          newNPC.copyOf(npc);
          newNPC.setHostilityLevel(Character.HOSTILITY_LEVEL_AVOID);
          npc = newNPC;
          addNPC = true;
        } else {
          Character chr = party.getActiveChar();
          index = party.getPartyMemberByName(chr.getLongName()); 
          if (index != -1 && index != 0) {
            party.removePartyChar(index);            
            Character newNPC = new Character(0);
            newNPC.clearTaskList();
            newNPC.copyOf(chr);
            newNPC.setHostilityLevel(Character.HOSTILITY_LEVEL_AVOID);
            npc = newNPC;
            addNPC = true;
            party.setActiveChar(0);
          }
        }
        break;
      }
      case TalkAction.ACTION_JOIN_PARTY: {
        int index = party.getNextFreePartyPosition();
        Character newMember = new Character(0);
        npc.clearTaskList();
        newMember.copyOf(npc);
        newMember.setHostilityLevel(Character.HOSTILITY_LEVEL_PLAYER);
        if (index != -1) {
          party.setPartyChar(index, newMember);
          //Set base roles for joining characters
          if (newMember.getLongName().equalsIgnoreCase("Elnora Tanolian")) {
            party.setRolesForCharacter(index, 0, 2, 0);
            newMember.setExperience(0);
          }
          if (newMember.getLongName().equalsIgnoreCase("Crulamin Tigersoul")) {
            party.setRolesForCharacter(index, 0, 0, 2);
            newMember.setExperience(0);
          }
          if (newMember.getLongName().equalsIgnoreCase("Werdinor Blackbone")) {
            party.setRolesForCharacter(index, 0, 0, 4);
            newMember.setExperience(4500);
            newMember.setLevel(4);
          }
          if (newMember.getLongName().equalsIgnoreCase("Brunor Whitebeard")) {
            party.setRolesForCharacter(index, 4, 0, 0);
            newMember.setExperience(10000);
            newMember.setLevel(6);
          }
          if (newMember.getLongName().equalsIgnoreCase("Beatrice Silvershield")) {
            party.setRolesForCharacter(index, 3, 0, 0);
            newMember.setExperience(4500);
            newMember.setLevel(4);
          }
          if (newMember.getLongName().equalsIgnoreCase("Nehess Battlehawk")) {
            party.setRolesForCharacter(index, 0, 4, 0);
            newMember.setExperience(10000);
            newMember.setLevel(6);
          }
          removeNPC = true;
        }
        break;
      }
      case TalkAction.ACTION_CAST_IDENTIFY: {
        party.castDetectMagicOrIdentifyOnParty(true);
        break;
      }

      }
    }
  }
  
  public void handleActions(ActionEvent arg0) {
    for (int i=0;i<ActionCommands.TALK_LINES.length;i++) {
      if (ActionCommands.TALK_LINES[i].equals(arg0.getActionCommand())) {
        TalkLine[] lines = talk.getAllLinesWithState(state);       
        switch (lines[i].getCondition()) {
        case Talk.TALK_CONDITION_ALWAYS_TRUE: {
          sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
          handleTalkLineActions(lines[i],true);
          break;
        }
        case Talk.TALK_CONDITION_TIME_HOUR_EQUALS: {
          if (party.getHours()==lines[i].getParam1()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_TIME_HOUR_GREATER: {
          if (party.getHours()>lines[i].getParam1()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_TIME_HOUR_LESS: {
          if (party.getHours()<lines[i].getParam1()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_TIME_IS_DAY: {
          if (party.isDay()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_IS_SOLO_MODE: {
          if (party.getMode()==Party.MODE_SOLO_MODE ||
              party.getPartySize() == 1) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
            
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);            
          }
          break;
        }
        case Talk.TALK_CONDITION_TIME_IS_NIGHT : {
          if (!party.isDay()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_EQUAL: {
          if (party.getActiveChar().getEffectiveAttribute(lines[i].getParam1())==lines[i].getParam2()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_GREATER: {
          if (party.getActiveChar().getEffectiveAttribute(lines[i].getParam1())>lines[i].getParam2()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_ATTRIBUTE_LESS: {
          if (party.getActiveChar().getEffectiveAttribute(lines[i].getParam1())<lines[i].getParam2()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_GOLD_EQUAL: {
          if (party.getActiveChar().getMoney()==lines[i].getParam1()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_GOLD_GREATER: {
          if (party.getActiveChar().getMoney()>lines[i].getParam1()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_GOLD_LESS: {
          if (party.getActiveChar().getMoney()<lines[i].getParam1()) {
            sayText.setText(lines[i].getTextTrue());
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(lines[i].getTextFalse());
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_LEVEL_EQUAL: {
          if (party.getActiveChar().getLevel()==lines[i].getParam1()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_LEVEL_GREATER: {
          if (party.getActiveChar().getLevel()>lines[i].getParam1()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_LEVEL_LESS: {
          if (party.getActiveChar().getLevel()<lines[i].getParam1()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_HAS_ITEM: {
          if (party.getActiveChar().inventoryFindItemByName(lines[i].getItemName())!=-1) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_AGAINST_NPC: {
          int targetNumber = npc.getEffectiveSkill(lines[i].getParam1())+50;        
          if (party.getActiveChar().skillCheck(lines[i].getParam1(), targetNumber)>=Character.CHECK_SUCCESS) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(lines[i].getTextFalse());
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_SKILL_CHECK_GREATER: {
          int targetNumber = lines[i].getParam2();        
          if (party.getActiveChar().skillCheck(lines[i].getParam1(), targetNumber)>=Character.CHECK_SUCCESS) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL: {        
          if (party.getStoryVariable(lines[i].getParam1())==lines[i].getParam2()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_STORY_VARIABLE_GREATER: {        
          if (party.getStoryVariable(lines[i].getParam1())>lines[i].getParam2()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_STORY_VARIABLE_LESS: {        
          if (party.getStoryVariable(lines[i].getParam1())<lines[i].getParam2()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_IS_PARTY_MEMBER: {        
          if (npc.isPlayer()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_IS_ROOM_IN_PARTY: {        
          if (party.getPartySize() < Party.MAX_PARTY_SIZE) {
              sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
              handleTalkLineActions(lines[i],true);
          } else {
              sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
              handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_PLAYER_NAME_IS: {
          if (party.getActiveChar().getLongName().equalsIgnoreCase(lines[i].getItemName())) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_STORY_VARIABLE_EQUAL_AND_PLAYER_NAME_IS: {        
          if (party.getStoryVariable(lines[i].getParam1())==lines[i].getParam2()&&
              party.getActiveChar().getLongName().equalsIgnoreCase(lines[i].getItemName())) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }
        case Talk.TALK_CONDITION_TIME_HOUR_IS_BETWEEN: {
          if (party.getHours()>=lines[i].getParam1()&&
              party.getHours()<=lines[i].getParam2()) {
            sayText.setText(changeAllMagicWords(lines[i].getTextTrue()));
            handleTalkLineActions(lines[i],true);
          } else {
            sayText.setText(changeAllMagicWords(lines[i].getTextFalse()));
            handleTalkLineActions(lines[i],false);
          }
          break;
        }

        
        }
        updatePlayerLines();   
        repaint();
      }
    }
  }

  /**
   * Did talk end
   * @return boolean
   */
  public boolean isEndTalk() {
    return endTalk;
  }

  public void setEndTalk(boolean endTalk) {
    this.endTalk = endTalk;
  }

  /**
   * Should run shop? Also check NPC by getNPC()
   * @return boolean
   */
  public boolean isRunShop() {
    return runShop;
  }
  
  /**
   * Should NPC be removed from map?
   * @return boolean;
   */
  public boolean isRemoveNPC() {
    return removeNPC;
  }

  public void setRunShop(boolean runShop) {
    this.runShop = runShop;
  }
  public Character getNPC() {
    return npc;
  }

  public boolean isRunTrade() {
    return runTrade;
  }

  /**
   * Should run trade? Also check NPC by getNPC()
   * @return boolean
   */
  public void setRunTrade(boolean runTrade) {
    this.runTrade = runTrade;
  }



  /**
   * Should run checkNPCPosition method
   * @return boolean
   */
  public boolean isCheckNPCPosition() {
    return checkNPCPosition;
  }

  public void setCheckNPCPosition(boolean checkNPCPosition) {
    this.checkNPCPosition = checkNPCPosition;
  }

  public boolean isRunExit() {
    return runExit;
  }

  public void setRunExit(boolean runExit) {
    this.runExit = runExit;
  }

  public int getExitDirection() {
    return exitDirection;
  }

  public void setExitDirection(int exitDirection) {
    this.exitDirection = exitDirection;
  }

  public boolean isMoveAway() {
    return moveAway;
  }

  public void setMoveAway(boolean moveAway) {
    this.moveAway = moveAway;
  }

  public int getMoveAwayDirection() {
    return moveAwayDirection;
  }

  public void setMoveAwayDirection(int moveAwayDirection) {
    this.moveAwayDirection = moveAwayDirection;
  }

  public boolean isTeleportToExit() {
    return teleportToExit;
  }

  public void setTeleportToExit(boolean teleportToExit) {
    this.teleportToExit = teleportToExit;
  }

  public String getTeleportWP() {
    return teleportWP;
  }

  public void setTeleportWP(String teleportWP) {
    this.teleportWP = teleportWP;
  }

  /**
   * Should NPC add to map in case of leaving party
   * @return boolean
   */
  public boolean isAddNPC() {
    return addNPC;
  }

  /**
   * Has move to Waypoint
   * @return boolean
   */
  public boolean isMoveToWP() {
    return moveToWP;
  }

  public void setMoveToWP(boolean moveToWP) {
    this.moveToWP = moveToWP;
  }

  /**
   * Get Move Waypoint name
   * @return String
   */
  public String getMoveWP() {
    return moveWP;
  }

  public void setMoveWP(String moveWP) {
    this.moveWP = moveWP;
  }

  /**
   * Get travel Waypoint, can be null if not set
   * @return String or null
   */
  public String getTravelWP() {
    return travelWP;
  }

  /**
   * Get travel map, can be null if not set
   * @return String
   */
  public String getTravelMap() {
    return travelMap;
  }
  
  /**
   * If travel is possible return true
   * @return boolean
   */
  public boolean isTravelSet() {
    if (travelWP != null) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isDead() {
    return dead;
  }

  public void setDead(boolean isDead) {
    this.dead = isDead;
  }

}
