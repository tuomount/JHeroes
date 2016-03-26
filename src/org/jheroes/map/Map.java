package org.jheroes.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

import javax.imageio.ImageIO;

import org.jheroes.gui.RasterFonts;
import org.jheroes.map.character.AIPath;
import org.jheroes.map.character.Attack;
import org.jheroes.map.character.CharEffect;
import org.jheroes.map.character.CharTask;
import org.jheroes.map.character.Character;
import org.jheroes.map.character.CharacterRace;
import org.jheroes.map.character.CombatModifiers.DamageModifier;
import org.jheroes.map.character.Defense;
import org.jheroes.map.character.Perks;
import org.jheroes.map.character.Spell;
import org.jheroes.map.character.SpellFactory;
import org.jheroes.map.item.Item;
import org.jheroes.map.item.ItemFactory;
import org.jheroes.musicplayer.MusicPlayer;
import org.jheroes.soundplayer.SoundPlayer;
import org.jheroes.tileset.Tile;
import org.jheroes.tileset.TileInfo;
import org.jheroes.tileset.Tileset;
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
 * 
 * Single map for the game
 * 
 */
public class Map {
  
  /**
   * Original map version 1.0
   */
  public final static String MAP_VERSION_1_0 = "MAP1.0";
  /**
   * Map version 1.1, characters have race information
   */
  public final static String MAP_VERSION_1_1 = "MAP1.1";
  /**
   * Current MAP version. This string is also in map files
   */
  public final static String CURRENT_MAP_VERSION = MAP_VERSION_1_1;
  
  /**
   * Engine Name
   */
  public final static String ENGINE_NAME ="JHeroes CRPG Engine";

  /**
   * Engine Version
   */
  public final static String ENGINE_VERSION ="1.0.0";

  public final static int NORTH_DIRECTION_UP = 0;
  public final static int NORTH_DIRECTION_RIGHT = 1;
  public final static int NORTH_DIRECTION_DOWN = 2;
  public final static int NORTH_DIRECTION_LEFT = 3;
  
  public final static int NON_BLOCKED = -1;
  public final static int BLOCKED_PARTY_MEMBER0 = 0;
  public final static int BLOCKED_PARTY_MEMBER1 = 1;
  public final static int BLOCKED_PARTY_MEMBER2 = 2;
  public final static int BLOCKED_PARTY_MEMBER3 = 3;
  public final static int BLOCKED = 999;
  
  public final static int CURSOR_MODE_DISABLE=0;
  public final static int CURSOR_MODE_LOOK=1;
  public final static int CURSOR_MODE_ATTACK=2;
  public final static int CURSOR_MODE_CAST=3;
  public final static int CURSOR_MODE_USE=4;
  public final static int CURSOR_MODE_TALK=5;
  public final static int CURSOR_MODE_SINGLE_ATTACK=6;
  public final static int CURSOR_MODE_EVALUATE=7;
  
  public final static int VIEW_X_RADIUS = 10;
  public final static int VIEW_Y_RADIUS = 8;
  public final static int MAX_RANGED_RANGE = 8;
  public final static int MAX_TALK_RANGE = 5;
  public final static int OUT_OF_COMBAT_RANGE = 15;
  
  //Graphical effects
  public final static int GRAPH_EFFECT_REMOVE_EFFECT = 0;
  public final static int GRAPH_EFFECT_LOOK_CURSOR = 1;
  public final static int GRAPH_EFFECT_BUBBLE_SLEEP = 5;
  public final static int GRAPH_EFFECT_SWEAT = 12;
  public final static int GRAPH_EFFECT_BUBBLE_SELL = 15;
  public final static int GRAPH_EFFECT_ATTACK_CURSOR = 20;
  public final static int GRAPH_EFFECT_ATTACK_HIT = 21;
  public final static int GRAPH_EFFECT_ATTACK_MISS_NORTH = 27;
  public final static int GRAPH_EFFECT_ATTACK_MISS_EAST = 30;
  public final static int GRAPH_EFFECT_ATTACK_MISS_SOUTH = 33;
  public final static int GRAPH_EFFECT_ATTACK_MISS_WEST = 36;
  public final static int GRAPH_EFFECT_RANGED_ATTACK_MISS = 39;
  public final static int GRAPH_EFFECT_SPELL_CURSOR = 42;
  public final static int GRAPH_EFFECT_SPELL_MINOR_ATTACK = 43;
  public final static int GRAPH_EFFECT_SPELL_POSITIVE = 47;
  public final static int GRAPH_EFFECT_SPELL_POISON = 54;
  public final static int GRAPH_EFFECT_SPELL_FLAME = 59;
  public final static int GRAPH_EFFECT_TALK_CURSOR = 67;
  public final static int GRAPH_EFFECT_USE_CURSOR = 71;
  public final static int GRAPH_EFFECT_BUBBLE_WORK = 72;
  public final static int GRAPH_EFFECT_SPELL_MINDAFFECTING = 85;
  public final static int GRAPH_EFFECT_SPELL_CURSE = 91;
  public final static int GRAPH_EFFECT_SPELL_TELEPORT = 100;
  public final static int GRAPH_EFFECT_SPELL_FROST = 107;
  public final static int GRAPH_EFFECT_SPELL_SHOCK = 121;
  public final static int GRAPH_EFFECT_SPELL_DARKNESS = 132;
  public final static int GRAPH_EFFECT_SPELL_SMITE = 139;
  public final static int GRAPH_EFFECT_SPELL_FEAR = 146;
  public final static int GRAPH_EFFECT_SPELL_DROWNING = 153;
  public final static int GRAPH_EFFECT_BUBBLE_EAT = 160;
  public final static int GRAPH_EFFECT_BUBBLE_PLAY = 164;
  public final static int GRAPH_EFFECT_SPELL_QI_FIST = 169;
  public final static int GRAPH_EFFECT_EVALUATE_CURSOR = 174;

  private static int getLightEffectForGraphEffect(int effect) {
    if (effect >= 47 && effect < 54) { // Spell positive
      return 1; // Dim light
    }
    if (effect >= 59 && effect < 67) { // Spell flame
      return 2; // Flame light
    }
    if (effect >= 85 && effect < 91) { // Spell Mindaffecting
      return 1; // Dim light
    }
    if (effect >= 100 && effect < 107) { // Spell Teleport
      return 1; // Dim light
    }
    if (effect >= 107 && effect < 121) { // Spell Frost
      return 1; // Dim light
    }
    if (effect >= 121 && effect < 132) { // Spell Shock
      return 3; // Bright light
    }
    if (effect >= 139 && effect < 145) { // Spell Smite
      return 3; // Bright light
    }
    if (effect >= 146 && effect < 153) { // Spell Fear
      return -1; // darkness
    }
    if (effect >= 153) { // Spell Drowning
      return 1; // darkness
    }
    return 0;
  }
  
  /**
   * Full attack with both hands
   */
  public final static int ATTACK_TYPE_FULL_ATTACK = 0;
  /**
   * Single attack with main weapon
   */
  public final static int ATTACK_TYPE_SINGLE_ATTACK = 1;
  /**
   * Throwing attack
   */
  public final static int ATTACK_TYPE_THROW_ATTACK = 2;
  
  private static BufferedImage IMAGE_ATTACK=null;
  private static BufferedImage IMAGE_DEFENSE=null;
  /**
   * Tileset for items, which is static for all maps
   * needs to be set before drawing the map
   * No need to save Map file
   */
  private static Tileset itemsTileset = null;

  /**
   * Tileset for characters, which is static for all maps
   * needs to be set before drawing the map
   * No need to save Map file
   */
  private static Tileset charactersTileset = null;

  /**
   * Tileset for effects, which is static for all maps
   * needs to be set before drawing the map
   * No need to save Map file
   */
  private static Tileset effectsTileset = null;
  /**
   * Map Maximum X size. Needs to be written into map file
   */
  private int maxX;
  /**
   * Map Maximum Y size. Needs to be written into map file
   */
  private int maxY;

  /**
   * Map walls, needs to be saved into map file
   */
  private int[][] walls;
  /**
   * Map objects, needs to be saved into map file
   */
  private int[][] objects;
  /**
   * Map decorations, needs to be saved into map file
   */
  private int[][] decorations;
  /**
   * Map top objects, needs to be saved into map file
   */
  private int[][] top;
  /**
   * Map lights needs to be saved into map file
   */
  private short[][] light;
  
  private int[][] graphEffects;
  /**
   * Map Tile set, no need to save
   */
  private Tileset mapTileset;
  /**
   * Map Tileset Index, needs to be written into map file
   */
  private int mapTilesetIndex;
  /**
   * Number of map sectors, needs to be written into map file
   */
  private int mapSectors; 
  /**
   * NorthDirection in sectors, needs to be written into map file
   */
  private int[] northDirection;
  
  /**
   * DefaultDayShade in sectors, needs to be written into map file
   */
  private int[] defaultDayShade;
  /**
   * DefaultNightShade in sectors, needs to be written into map file
   */
  private int[] defaultNightShade;
  /**
   * Music file at day in sectors, needs to be written into map file
   */  
  private String[] musicDay;
  /**
   * Music file at night in sectors, needs to be written into map file
   */  
  private String[] musicNight;
  /**
   * Music file at combat in sectors, needs to be written into map file
   */    
  private String[] musicCombat;
  
  /**
   * Used when drawing the map. No need to write into file
   */
  private int animationSpeed =0;
  /**
   * Used when drawing the map. No need to write into file
   */  
  private Compass mapCompass;
  /**
   * Map Events. Needs to be written into map file
   */
  private ArrayList<Event> listEvents;
  /**
   * Map items. Needs to be written into map file
   */
  private ArrayList<Item> listItems;  
  /**
   * List of NPCs, these will be save into map file
   */
  private ArrayList<Character> npcList;
  
  /**
   * Casting spell for map
   */
  private Spell castingSpell;
  
  public void setCastingSpell(Spell spell) {
    this.castingSpell = spell;
  }
  
  /**
   * Used item in Use Cursor mode
   */
  private Item usedItem;
  
  public void setUsedItem(Item item) {
    this.usedItem = item;
  }
  
  /**
   * Party information for map
   */
  private Party party=null;
  
  private String displayPopupText;
  private String drawableDisplayPopupText;
  
  private String attackText;
  private String defenseText;
  
  private boolean textsWritten=false;
  private BufferedImage popupImage;
  boolean needAlwaysRedraw[][] = null;
  
  public void setParty(Party party) {
    this.party = party;
  }
  
  
  /**
   * "cursor" in editor mode. No need to write into file
   */
  private Character myChar;
  
  private boolean forceRePaint;
  
  private int cursorTile;
  private int cursorMode=CURSOR_MODE_DISABLE;
  private int cursorX;
  private int cursorY;
  private int lastDrawnX=15;
  private int lastDrawnY=15;
  
  private int pulsatingShade=0;
  
  public Character getMyChar() {
    return myChar;
  }

  public void setMyChar(Character myChar) {
    this.myChar = myChar;
  }

  public int getLastDrawnX() {
    return lastDrawnX;
  }
  public int getLastDrawnY() {
    return lastDrawnY;
  }
  
  private HashMap<String, String> hashMapWP;    
  
  /**
   * Used only map methods, get event sets this up. No need to write into file
   */
  private Event currentEvent;
    
  /**
   * Constructor for Map
   * @param sizeX, Size of X  MAX 255
   * @param sizeY, Size of Y, MAX 255
   * @param forWalls tileset for walls and floors
   * @throws Exception, if something goes wrong
   */
  public Map(int sizeX, int sizeY, int TilesetIndex) throws IOException {
    if ((sizeX > 31) && (sizeY > 31) && (sizeX < 256) && (sizeY < 256)) {
      party = null;
      maxX = sizeX;
      maxY = sizeY;
      this.mapTilesetIndex = TilesetIndex;
      walls = new int[sizeX][sizeY];
      objects = new int[sizeX][sizeY];
      decorations = new int[sizeX][sizeY];
      top = new int[sizeX][sizeY];
      graphEffects = new int[sizeX][sizeY];
      for (int i=0;i<maxX;i++) {
        for (int j=0;j<maxY;j++) {
          graphEffects[i][j]=-1;
        }
      }
      light = new short[sizeX][sizeY];
      setMapSectors(1);
      northDirection = new int[4]; // Max sector is 4;
      defaultDayShade = new int[4];
      defaultNightShade = new int[4];
      musicNight = new String[4];
      musicDay = new String[4];
      musicCombat = new String[4];
      musicNight[0] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicNight[1] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicNight[2] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicNight[3] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicDay[0] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicDay[1] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicDay[2] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicDay[3] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicCombat[0] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicCombat[1] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicCombat[2] = MusicPlayer.MUSIC_FILE_VILLAGE;
      musicCombat[3] = MusicPlayer.MUSIC_FILE_VILLAGE;
      defaultDayShade[0] = 0;
      defaultDayShade[1] = 0;
      defaultDayShade[2] = 0;
      defaultDayShade[3] = 0;
      defaultNightShade[0] = 7;
      defaultNightShade[1] = 7;
      defaultNightShade[2] = 7;
      defaultNightShade[3] = 7;
      currentEvent = null;
      listEvents = new ArrayList<Event>();
      listItems = new ArrayList<Item>();
      npcList = new ArrayList<Character>();
      myChar = new Character(0);
      myChar.setPosition(5, 5);
      mapCompass = new Compass();
      mapTileset = new Tileset();
      setDisplayPopupText(null);
      setAttackText(null);
      setDefenseText(null);
      setPopupImage(null);
      DataInputStream dis = new DataInputStream(new BufferedInputStream(
          Map.class.getResourceAsStream(Tileset.TILESET_NAME_RESOURCE[TilesetIndex])));
      int err =mapTileset.loadTileSet(dis);
      if (err != 0){
        throw new IOException("Error while reading tile set! Error code:"+String.valueOf(err));
      }
        
    } else {
      throw new IOException("Wrong map size!");
    }
    
  }
   
  
  /**
   * Set items tileset
   * @param items
   */
  public static void setItemsTileset(Tileset items) {
    itemsTileset = items;
  }
  
  
  /**
   * Set characters tileset
   * @param characters
   */
  public static void setCharactersTileset(Tileset characters) {
    charactersTileset = characters;
  }
  
  /**
   * Set effects tileset
   * @param effects
   */
  public static void setEffectsTileset(Tileset effects) {
    effectsTileset = effects;
  }
  
  public int getMaxX() {
    return maxX;
  }
  public int getMaxY() {
    return maxY;
  }
  
  /**
   * Fill map with tileNumber
   * Fill is done only to walls others are emptied
   * @param tileNumber
   */
  public void fillMap(int tileNumber) {
    
    if (tileNumber < mapTileset.size()) {
      for (int i=0;i<maxX;i++) {
        for (int j=0;j<maxY;j++) {
          walls[i][j] = tileNumber;
          objects[i][j] = -1;
          decorations[i][j] = -1;
          top[i][j] = -1;
          light[i][j] = 0;
        }
      }
    }

  }
  
  /**
   * Get character by index
   * @param i index
   * @return character null if not found
   */
  public Character getNPCbyIndex(int i) {
    if ((i>=0) &&(i<npcList.size())) {
      return npcList.get(i);
    }
    return null;
  }
  
  /**
   * Remove NPC from list by index
   * @param i index
   */
  public void removeNPCbyIndex(int i) {
    if ((i>=0) &&(i<npcList.size())) {
      npcList.remove(i);
    }
  }
  
  /**
   * Adds NPC to list, checks that there is no another NPC is same place
   * @param npc Character
   * @return true if added otherwise false
   */
  public boolean addNPC(Character npc) {
    if (findNPCbyCoordinates(npc.getX(), npc.getY()) == -1) {
      npcList.add(npc);
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Looks for NPC from coordinates. Uses both head and body coordinates
   * If both found then body is returned
   * @param x coordinate x
   * @param y coordinate y
   * @return index -1 if not found
   */
  public int findNPCbyCoordinates(int x, int y) {
    int body = -1;
    int head = -1;
    for (int i=0;i<npcList.size();i++) {
      Character chr = npcList.get(i);
      if ((chr.getX() == x) && (chr.getY() == y)) {
        body = i;
      }
      if ((chr.getHeadX() == x) && (chr.getHeadY() == y)) {
        head = i;
      }
    }
    if (body != -1) {
      return body;
    }
    if ((body == -1) && (head != -1)) {
      return head;
    }
    return -1;
  }
  
  /**
   * Return character index by coordinates. Does not look for heads, only body
   * @param x
   * @param y
   * @return index if found otherwise -1
   */
  public int getCharacterByCoordinates(int x, int y) {
    for (int i=0;i<npcList.size();i++) {
      Character chr = npcList.get(i);
      if ((chr.getX() == x) && (chr.getY() == y)) {
        return i;
      }
    }
    return -1;
  }
  
  /**
   * Find NPC by name uses both long and short names
   * @param name to search
   * @return index if found otherwise -1
   */
  public int getNPCByName(String name) {
    for (int i=0;i<npcList.size();i++) {
      Character chr = npcList.get(i);
      if ((chr.getName().equalsIgnoreCase(name)) ||
          (chr.getLongName().equalsIgnoreCase(name))) {
        return i;
      }
    }
    return -1;
    
  }
  
  /**
   * Travel whole party according travel event
   * Notice: If traveling requires loading another map
   * this needs to be done before calling this method.
   * @param event
   */
  public void partyTravel(Event event) {
    String targetWP = event.getTargetWaypointName();
    int index = this.getEventWaypointByName(targetWP);
    Event target = this.getEventByIndex(index);
    if (target != null) {
      if (party.getMode()==Party.MODE_PARTY_MODE) {
        if (target.getType() == Event.TYPE_POINT) {
          for (int i=0;i<party.getPartySize();i++) {
            Character member = party.getPartyChar(i);
            if (member != null) {
              member.setPosition(target.getX1(), target.getY1());
            }
          }
        } else {
          int xs = target.getX2()-target.getX1();
          int ys = target.getY2()-target.getY1();
          for (int i=0;i<party.getPartySize();i++) {
            if (party.getPartyChar(i)!= null) {
              // Try to find free spot
              for (int j=0;j<25;j++) {
                int newX = target.getX1()+DiceGenerator.getRandom(xs);
                int newY = target.getY1()+DiceGenerator.getRandom(ys);
                if (this.isTargetBlocked(newX,newY)==false) {
                  party.getPartyChar(i).setPosition(newX,newY);
                  party.getPartyChar(i).setHeading(Character.DIRECTION_SOUTH);
                  break;
                }
                if (j==24) { // Not found so just take something
                  party.getPartyChar(i).setPosition(newX,newY);
                  party.getPartyChar(i).setHeading(Character.DIRECTION_SOUTH);              
                }
              }
            }
          }
        }
      } else {
        // SOLO MODE
        if (target.getType() == Event.TYPE_POINT) {
          party.getActiveChar().setPosition(target.getX1(), target.getY1());
        } else {
          int xs = target.getX2()-target.getX1();
          int ys = target.getY2()-target.getY1();
          // Try to find free spot
          for (int j=0;j<25;j++) {
            int newX = target.getX1()+DiceGenerator.getRandom(xs);
            int newY = target.getY1()+DiceGenerator.getRandom(ys);
            if (this.isTargetBlocked(newX,newY)==false) {
              party.getActiveChar().setPosition(newX,newY);
              party.getActiveChar().setHeading(Character.DIRECTION_SOUTH);
              break;
            }
            if (j==24) { // Not found so just take something
              party.getActiveChar().setPosition(newX,newY);
              party.getActiveChar().setHeading(Character.DIRECTION_SOUTH);              
            }
          }
        }
      }
    }
  }
  

  private byte getDirectionToLook(int fromX, int fromY, int targetX, int targetY) {
    int mx = targetX-fromX;
    int my = targetY-fromY;
    if (mx == 0) {
      if (my > 0) {
        return Character.DIRECTION_SOUTH;
      }
      if (my < 0) {
        return Character.DIRECTION_NORTH;
      }
    }
    if (mx < 0){
          return Character.DIRECTION_WEST;
    }
    if (mx > 0){
      return Character.DIRECTION_EAST;
    }
    return Character.DIRECTION_NORTH;
  }

  
  
  /**
   * After time passed check that each NPC is in correct places
   * Should be used after map load / rest
   * @param hour
   * @param min
   */
  public void doNPCsCheckCorrectPositions(int hour, int min, boolean mapLoad) {
    Iterator<Character> npcIte = npcList.iterator();
    while (npcIte.hasNext()) {
      Character npc = npcIte.next();           
      int taskIndex = npc.getCurrentTaskIndexByTime(hour, min,mapLoad);      
      if (taskIndex != -1) {
        CharTask task = npc.getTaskByIndex(taskIndex);
        int index = getEventWaypointByName(task.getWayPointName());
        Event event = getEventByIndex(index);
        if (CharTask.TASK_RUN_EXIT.equals(task.getTask())) {
          // Remove NPC from map
          npcIte.remove();
          event = null;
        }
        if (event != null) {
          if ((CharTask.TASK_MOVE_INSIDE_WP.equals(task.getTask())) ||
             ((CharTask.TASK_MOVE_INSIDE_WP_AND_ATTACK.equals(task.getTask())))) {
            for (int j=0;j<10;j++) {
              int newX = event.getRandomX();      
              int newY = event.getRandomY();      
              if (this.isTargetBlocked(newX,newY)==false) {
                npc.setPosition(newX,newY);
              }
              
            }
            npc.setHeading(Character.DIRECTION_SOUTH);
          } if ((CharTask.TASK_MOVE_RANDOM.equals(task.getTask())) ||
              ((CharTask.TASK_MOVE_RANDOM_AND_ATTACK.equals(task.getTask())))) {
            // These should move randomly which would be kind of difficult since
            // not possible know where they are allowed to move unless really moved.
            
            // Move randomly inside sector
            int sector = getSectorByCoordinates(npc.getX(),npc.getY());
            int direction = AIPath.getDirectionToMove(npc.getX(), npc.getY(),
               DiceGenerator.getRandom(getSectorMinX(sector), getSectorMaxX(sector)),
               DiceGenerator.getRandom(getSectorMinY(sector), getSectorMaxY(sector)));
            
            int mx = npc.getX();
            int my = npc.getY();
            switch (direction) {
            case DIRECTION_UP: my = my-1; break;
            case DIRECTION_DOWN: my = my+1; break;
            case DIRECTION_LEFT: mx = mx-1; break;
            case DIRECTION_RIGHT: mx = mx+1; break;
            }
            if (!isBlocked(npc.getX(),npc.getY(), mx, my)) {
              npc.doMove(mx, my);
            } else {
              mx = npc.getX();
              my = npc.getY();
              switch (direction) {
              case DIRECTION_UP:  {if (DiceGenerator.getRandom(1)==0) {
                                    mx--;
                                  } else {
                                      mx++;
                                  } break; }
              case DIRECTION_DOWN: {if (DiceGenerator.getRandom(1)==0) {
                                    mx--;
                                  } else {
                                      mx++;
                                  } break; }
              case DIRECTION_LEFT: {if (DiceGenerator.getRandom(1)==0) {
                                      my--;
                                   } else {
                                      my++;
                                   } break; }
              case DIRECTION_RIGHT: {if (DiceGenerator.getRandom(1)==0) {
                                      my--;
                                   } else {
                                      my++;
                                   } break; }
              }
              if (!isBlocked(npc.getX(),npc.getY(), mx, my)) {
                npc.doMove(mx, my);
              }

          }} // END OF MOVING RANDOMLY
          else if ((CharTask.TASK_MOVE_INSIDE_WP.equals(task.getTask())) ||
              ((CharTask.TASK_MOVE_INSIDE_WP_AND_ATTACK.equals(task.getTask())))) {
            int xs = event.getX2()-event.getX1();
            int ys = event.getY2()-event.getY1();
            // Try to find free spot
            for (int j=0;j<25;j++) {
              int newX = event.getX1()+DiceGenerator.getRandom(xs);
              int newY = event.getY1()+DiceGenerator.getRandom(ys);
              if (this.isTargetBlocked(newX,newY)==false) {
                npc.setPosition(newX,newY);
                npc.setHeading(Character.DIRECTION_SOUTH);
                break;
              }
            }
          }
          else {
            if (this.isTargetBlocked(event.getX(),event.getY())==false) {
              npc.setPosition(event.getX(),event.getY());
              npc.setHeading(Character.DIRECTION_SOUTH);
            } else {
              int xs = event.getX2()-event.getX1();
              int ys = event.getY2()-event.getY1();
              // Try to find free spot
              for (int j=0;j<25;j++) {
                int newX = event.getX1()+DiceGenerator.getRandom(xs);
                int newY = event.getY1()+DiceGenerator.getRandom(ys);
                if (this.isTargetBlocked(newX,newY)==false) {
                  npc.setPosition(newX,newY);
                  npc.setHeading(Character.DIRECTION_SOUTH);
                  break;
                }
              }

            }
          }
          
          //npc.setCurrentTaskDone(true);
        }
      }      
    }
  }
  
  /**
   * Get Attacking target from party member. This works only for aggressive NPCs
   * @param attacker NPC 
   * @return party member index, if not found return -1
   */
  public int getAttackingTargetFromParty(Character npc) {
    int result = -1;
    int[] distTable = new int[Party.MAX_PARTY_SIZE];
    if (npc.getHostilityLevel()==Character.HOSTILITY_LEVEL_AGGRESSIVE) {
      for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
        distTable[i] = 999;
      }
      if (party != null) {
        for (int i=0;i<party.getPartySize();i++) {
          Character tmp = party.getPartyChar(i);
          int dist = 999;
          if (tmp != null) {
            dist =MapUtilities.getDistance(npc.getX(), npc.getY(), tmp.getX(),
                tmp.getY());
            if (!isClearShot(npc.getX(), npc.getY(), tmp.getX(),
                tmp.getY())) {
              dist = 999;
            }
          }
          distTable[i] = dist;      
          }
        int dist = 8;        
        for (int i=0;i<party.getPartySize();i++) {
          if (dist > distTable[i]) {
            result = i;
            dist = distTable[i];
          }
        }
      }
    }
    return result;
  }
  
  /**
   * Get attacking target from npc list. This works for both aggresive and guarding
   * NPCs
   * @param attacker NPC 
   * @return NPC index, if not found return -1
   */
  @SuppressWarnings("unused")
  private int getAttackingTargetFromNPCs(Character npc) {
    int result = -1;
    for (int i=0;i<npcList.size();i++) {
      Character tmp = getNPCbyIndex(i);
      if (tmp != npc) {
      if (MapUtilities.getDistance(npc.getX(), npc.getY(), tmp.getX(),
          tmp.getY()) < 8) {
        if (npc.getHostilityLevel()==Character.HOSTILITY_LEVEL_AGGRESSIVE) {
          if ((tmp.getHostilityLevel()==Character.HOSTILITY_LEVEL_AVOID) ||
          (tmp.getHostilityLevel()==Character.HOSTILITY_LEVEL_GUARD)) {
            return i;
          }
        } else if (npc.getHostilityLevel()==Character.HOSTILITY_LEVEL_GUARD) {
          if (tmp.getHostilityLevel()==Character.HOSTILITY_LEVEL_AGGRESSIVE) {
            return i;
          }

        }
      }
      }
    }
    return result;
  }
  
  /**
   * NPC drops all items.
   * @param npc Character
   */
  public void dropAllItems(Character npc) {
    int x = npc.getX();
    int y = npc.getY();
    if ((npc.inventoryGetAmulet() != null) && (npc.inventoryGetAmulet().isDroppable())) {
      addNewItem(x, y, npc.inventoryGetAmulet());
    }
    if ((npc.inventoryGetArmor() != null) && (npc.inventoryGetArmor().isDroppable())) {
      addNewItem(x, y, npc.inventoryGetArmor());
    }
    if ((npc.inventoryGetRing() != null) && (npc.inventoryGetRing().isDroppable())) {
      addNewItem(x, y, npc.inventoryGetRing());
    }
    if ((npc.inventoryGetBoots() != null) && (npc.inventoryGetBoots().isDroppable())) {
      addNewItem(x, y, npc.inventoryGetBoots());
    }
    if ((npc.inventoryGetHeadGear() != null) && (npc.inventoryGetHeadGear().isDroppable())) {
      addNewItem(x, y, npc.inventoryGetHeadGear());
    }
    if ((npc.inventoryGetFirstHand() != null) && (npc.inventoryGetFirstHand().isDroppable())) {
      addNewItem(x, y, npc.inventoryGetFirstHand());
    }
    if ((npc.inventoryGetSecondHand() != null) && (npc.inventoryGetSecondHand().isDroppable())) {
      addNewItem(x, y, npc.inventoryGetSecondHand());
    }
    for (int i=0;i<npc.inventorySize();i++) {
      Item item = npc.inventoryGetIndex(i);
      if ((item != null) && (item.isDroppable())) {
        addNewItem(x,y,item);
      }
    }
    if (npc.getMoney() > 0) {
      Item item = ItemFactory.createMoney(npc.getMoney());
      addNewItem(x,y,item);
    }
  }
  
  private int doSpellNextTo(Character caster, Character mainTarget, int x, int y, Spell spell) {
    int exp = 0;
    Character target = getNPCbyIndex(findNPCbyCoordinates(x, y));
    if (target == null) {
      target = party.getPartyChar(party.getPartyMemberByCoordinates(x, y));
      if ((target != null) && (mainTarget != target)) {
        exp = doSpell(caster,target,spell, false);
      }
    } else {
      if (mainTarget != target) {
        exp = doSpell(caster,target,spell, false);
      }
    }
    addNewGraphEffect(x, y, spell.getAnimation());
    return exp;
  }

  /**
   * Calculate Spell hit chance
   * @param caster
   * @param target
   * @param spell
   * @return
   */
  public int calculateSpellHitChance(Character caster, Character target, Spell spell) {
    if (spell.getRadius() == Spell.SPELL_RADIUS_NEED_HIT) {
      if (spell.isMindEffecting()) {
        int bonus = caster.getEffectiveSkill(spell.getSkill());
        int tn = target.getEffectiveWillPower();
        if (100+bonus <tn) {
          // Hit only with critical bonus
          return caster.getEffectiveAttribute(Character.ATTRIBUTE_LUCK);
        } else {
          bonus = 100-(tn-bonus);
          if (bonus > 100) {
            return 100-(11-caster.getEffectiveAttribute(Character.ATTRIBUTE_LUCK));
          }
          return bonus;
        }
      } else {
        int bonus = caster.getEffectiveSkill(spell.getSkill());
        int tn = target.getDefense().getDefense();
        if (100+bonus <tn) {
          // Hit only with critical bonus
          return caster.getEffectiveAttribute(Character.ATTRIBUTE_LUCK);
        } else {
          bonus = 100-(tn-bonus);
          if (bonus > 100-(11-caster.getEffectiveAttribute(Character.ATTRIBUTE_LUCK))) {
            return 100-(11-caster.getEffectiveAttribute(Character.ATTRIBUTE_LUCK));
          }
          return bonus;
        }
      }
    } else {
      // No need to hit
      return 100;
    }
  }
  
  public int doSpell(Character caster, Character target, Spell spell,boolean center) {
    int exp = 0;
    if ((center) && (caster != target)) {
      byte direction = getDirectionToLook(caster.getX(), caster.getY(),
          target.getX(), target.getY());
      caster.setHeading(direction);
    }
    Attack att = spell.getAttack();
    if (caster.getPerks().isPerkActive(Perks.PERK_MASTER_SORCERER) 
        && spell.getSkill()==Character.SKILL_SORCERY && spell.getMinimumSkill() > 49) {
      int maxDam = att.getMaxLethalDamage();
      maxDam = maxDam +caster.getLevel();
      att.setMaxLethalDamage(maxDam);
    }
    if (spell.getName().equalsIgnoreCase(SpellFactory.SPELL_NAME_SMITE_UNDEAD)) {
      if (target.isUndead()) {
        att.setMinLethalDamage(caster.getLevel());
        att.setMaxLethalDamage(6*caster.getLevel());
      } else {
        party.addLogText(target.getName()+" is not undead!");
        att.setMinLethalDamage(0);
        att.setMaxLethalDamage(0);
      }
    }
    if (spell.getName().equalsIgnoreCase(SpellFactory.SPELL_NAME_POWER_OF_DESTRUCTION)) {
      att.setMinLethalDamage(caster.getLevel());
      att.setMaxLethalDamage(3*caster.getLevel());
    }
    int check = Character.CHECK_NOT_DONE_YET;
    if (att != null) {      
      if (spell.getRadius() == Spell.SPELL_RADIUS_NEED_HIT) {
        int maxDam = att.getMaxLethalDamage();
        maxDam = maxDam +caster.getBonusDamageFromCharisma();
        att.setMaxLethalDamage(maxDam);
        int minDam = att.getMinLethalDamage();
        minDam = minDam +caster.getBonusDamageFromCharisma();
        att.setMinLethalDamage(minDam);
        if (spell.isMindEffecting()) {
          check = caster.skillCheck(spell.getSkill(), target.getEffectiveWillPower());
        } else {
          check = caster.skillCheck(spell.getSkill(), target.getDefense().getDefense());
        }
      } else {
        check = Character.CHECK_SUCCESS;
        if ((spell.getRadius() == Spell.SPELL_RADIUS_5_TILE) && (center)) {
          int mx = target.getX();
          int my = target.getY();
          exp=exp+doSpellNextTo(caster,target,mx,my-1,spell);
          exp=exp+doSpellNextTo(caster,target,mx,my+1,spell);
          exp=exp+doSpellNextTo(caster,target,mx-1,my,spell);
          exp=exp+doSpellNextTo(caster,target,mx+1,my,spell);
        }
        if ((spell.getRadius() == Spell.SPELL_RADIUS_9_TILE) && (center)) {
          int mx = target.getX();
          int my = target.getY();
          exp=exp+doSpellNextTo(caster,target,mx,my-1,spell);
          exp=exp+doSpellNextTo(caster,target,mx,my+1,spell);
          exp=exp+doSpellNextTo(caster,target,mx-1,my,spell);
          exp=exp+doSpellNextTo(caster,target,mx+1,my,spell);
          exp=exp+doSpellNextTo(caster,target,mx-1,my-1,spell);
          exp=exp+doSpellNextTo(caster,target,mx+1,my+1,spell);
          exp=exp+doSpellNextTo(caster,target,mx-1,my+1,spell);
          exp=exp+doSpellNextTo(caster,target,mx+1,my-1,spell);
        }
        if ((spell.getRadius() == Spell.SPELL_RADIUS_13_TILE) && (center)) {
          int mx = target.getX();
          int my = target.getY();
          exp=exp+doSpellNextTo(caster,target,mx,my-1,spell);
          exp=exp+doSpellNextTo(caster,target,mx,my+1,spell);
          exp=exp+doSpellNextTo(caster,target,mx-1,my,spell);
          exp=exp+doSpellNextTo(caster,target,mx+1,my,spell);
          exp=exp+doSpellNextTo(caster,target,mx-1,my-1,spell);
          exp=exp+doSpellNextTo(caster,target,mx+1,my+1,spell);
          exp=exp+doSpellNextTo(caster,target,mx-1,my+1,spell);
          exp=exp+doSpellNextTo(caster,target,mx+1,my-1,spell);
          exp=exp+doSpellNextTo(caster,target,mx,my-2,spell);
          exp=exp+doSpellNextTo(caster,target,mx,my+2,spell);
          exp=exp+doSpellNextTo(caster,target,mx-2,my,spell);
          exp=exp+doSpellNextTo(caster,target,mx+2,my,spell);
        }
      }
      if (check > Character.CHECK_FAIL) {
        int lethalDamage = DiceGenerator.getRandom(att.getMinLethalDamage(), att.getMaxLethalDamage());
        int nonLethalDamage = DiceGenerator.getRandom(att.getMinStaminaDamage(), att.getMaxStaminaDamage());
        switch ( (target.getRace().damageModifierFor(att.getAttackType()))) {
        case IMMUNITY: { lethalDamage = 0; nonLethalDamage = 0; break;}
        case NORMAL: { /*DO nothing*/ break;}
        case RESISTANCE: { lethalDamage = lethalDamage / 2; 
                            nonLethalDamage = nonLethalDamage /2; break;
        }
        case WEAKNESS: { lethalDamage = lethalDamage * 2; 
        nonLethalDamage = nonLethalDamage *2; break;}
        }

        if (check == Character.CHECK_CRITICAL_SUCCESS) {
          party.addLogText(caster.getName()+" hits critically "+target.getName());
          lethalDamage = lethalDamage*att.getCriticalMultiplier();
          nonLethalDamage = nonLethalDamage*att.getCriticalMultiplier();
          if (!spell.isMindEffecting()) {
            lethalDamage = lethalDamage-target.getDefense().getCriticalArmorRating();
          }
        } else {
          if (!spell.isMindEffecting()) {
            lethalDamage = lethalDamage-target.getDefense().getArmorRating();
          }
          party.addLogText(caster.getName()+" hits "+target.getName());
        }
        int totalDamage = 0;
        if (lethalDamage < 1) {
          lethalDamage = 0;
        } else {
          totalDamage = lethalDamage;
        }
        target.receiveLethalDamage(lethalDamage);
        target.receiveNonLethalDamage(nonLethalDamage,true);
        if (att.getPiercing()>0) {
          target.receiveLethalDamage(att.getPiercing());
          totalDamage = totalDamage+att.getPiercing();
        }        
        if ((totalDamage==0) && (nonLethalDamage==0)) {
          if (target.getRace().damageModifierFor(att.getAttackType())
              ==DamageModifier.IMMUNITY) {
            party.addLogText("but "+target.getName()+" has immunity against "+
              att.getAttackType().toString()+" attacks.");
          } else {
            party.addLogText("but deals no damage.");
          }
          SoundPlayer.playSound(SoundPlayer.SOUND_FILE_BLOCK);
        } else {
          StringBuilder sb = new StringBuilder();
          if (totalDamage > 0) {
            sb.append("and deals "+totalDamage+" points of damage");
          }
          if (nonLethalDamage > 0) {
            if (totalDamage > 0) {
              sb.append(" ");
            }
            sb.append("and deals "+nonLethalDamage+" points of non-lethal damage");
          }
          party.addLogText(sb.toString()+".");
          switch (target.getRace().damageModifierFor(att.getAttackType())) {
          case RESISTANCE: {party.addLogText("Hit was not that effective!"); break;}
          case WEAKNESS: {party.addLogText("Hit was very effective!"); break;}
          default: // Do nothing
          break;
          }
          if (target.isDead()) {
            party.addLogText(target.getName()+" is dead.");
            if (caster.isPlayer()) {
              party.shareExperience(target.getExperience());
            }
            if (!target.isPlayer()) {
              dropAllItems(target);
              int index = findNPCbyCoordinates(target.getX(), target.getY());
              removeNPCbyIndex(index);
            } else {
              dropAllItems(target);
              party.setHeroDown(true);
              int index = party.getPartyMemberByCoordinates(target.getX(), target.getY());
              if (index != -1) {
                party.removePartyChar(index);
              }
            }
          }
        }
        
      } else {// END OF CHECK FAIL
        party.addLogText(caster.getName()+" fails casting of "+spell.getName());
        if (target.isPlayer()) {
          target.setExperience(target.getExperience()+2);
        }

      }
    }
    CharEffect eff = spell.getEffect();
    if (eff!=null) {
      int power = DiceGenerator.getRandom(1, 100);
      power = power +caster.getEffectiveSkill(spell.getSkill());
      if ((spell.isMindEffecting()) && (check == Character.CHECK_NOT_DONE_YET) &&
          (spell.getRadius() == Spell.SPELL_RADIUS_NEED_HIT) ) {
        check = caster.skillCheck(spell.getSkill(), target.getEffectiveWillPower());
        if (check == Character.CHECK_FAIL) {
          party.addLogText(caster.getName()+" fails casting of "+spell.getName());       
        }
      }
      if ((check == Character.CHECK_NOT_DONE_YET) ||
         (check >= Character.CHECK_SUCCESS)){
        check = Character.CHECK_SUCCESS;
        // Always just 1 experience point from effect
        if (caster.isPlayer()) {
          exp = exp+1;
        }
        if (spell.getName().equalsIgnoreCase(SpellFactory.SPELL_NAME_POWER_OF_HEALING)) {
          eff.setValue(2*caster.getLevel());
        }
        if (spell.getName().equalsIgnoreCase(SpellFactory.SPELL_NAME_POWER_OF_MIGHTY)) {
          eff.setValue(caster.getLevel());
        }
        if (caster.getPerks().isPerkActive(Perks.PERK_MASTER_HEALER) &&
            spell.getSkill() == Character.SKILL_WIZARDRY && eff.getEffect() == CharEffect.EFFECT_ON_HEALTH &&
            eff.getValue() > 0) {
            // Master Healer perk, wizardy spell and healing spell
            int duration = eff.getDuration();
            int value = eff.getValue();
            if (duration == 1) {
              value = value+caster.getLevel()/4;
              eff.setValue(value);
            } else {
              duration = duration+caster.getLevel()/4;
              eff.setDuration(duration);
            }
            
        }
        if (caster.getPerks().isPerkActive(Perks.PERK_MASTER_QI_MAGICIAN) &&
            spell.getSkill() == Character.SKILL_QI_MAGIC && 
            (eff.getEffect() == CharEffect.EFFECT_ON_SKILL || eff.getEffect() == CharEffect.EFFECT_ON_ATTRIBUTE)
            && eff.getValue() > 0) {
            // Master QI Magician perk, Qi boost spell
            int duration = eff.getDuration();
            duration = duration+caster.getLevel();
            eff.setDuration(duration);          
        }
        eff.setDifficulty(power);
        target.addEffect(eff);
        party.addLogText(caster.getName()+" casts "+spell.getName());
      }
    }
    if (spell.getDispelType() != Spell.DISPEL_TYPE_NONE) {
      int power = DiceGenerator.getRandom(1, 100);
      power = power +caster.getEffectiveSkill(spell.getSkill());
      if (spell.getDispelType() < Spell.DISPEL_TYPE_DETECT_MAGIC) {
        boolean success = target.removeEffect((byte)spell.getDispelType(), power);      
        // Always just 3 experience point from removing effect
        if (success) {
          party.addLogText(caster.getName()+" casts "+spell.getName());
        } else {
          party.addLogText(caster.getName()+" fails casting of "+spell.getName());        
        }
        if ((caster.isPlayer()) && (success)) {
          exp = exp+3;
        }
      } else {
        if (spell.getDispelType() == Spell.DISPEL_TYPE_DETECT_MAGIC) {
          party.castDetectMagicOrIdentifyOnParty(false);
        }
        if (spell.getDispelType() == Spell.DISPEL_TYPE_IDENTIFY_ITEMS) {
          party.castDetectMagicOrIdentifyOnParty(true);
        }
      }
    }
    addNewGraphEffect(target.getX(), target.getY(), spell.getAnimation());
    if (center) {
      switch (spell.getAnimation()) {
      case GRAPH_EFFECT_SPELL_FLAME: SoundPlayer.playSoundBySoundName("FLAMES"); break;
      case GRAPH_EFFECT_SPELL_POSITIVE: SoundPlayer.playSoundBySoundName("MAGIC1"); break;
      case GRAPH_EFFECT_SPELL_MINOR_ATTACK: SoundPlayer.playSoundBySoundName("MAGIC2"); break;
      case GRAPH_EFFECT_SPELL_POISON: SoundPlayer.playSoundBySoundName("POISON"); break;
      case GRAPH_EFFECT_SPELL_MINDAFFECTING: SoundPlayer.playSoundBySoundName("MAGIC3"); break;
      case GRAPH_EFFECT_SPELL_CURSE: SoundPlayer.playSoundBySoundName("MAGIC4"); break;
      case GRAPH_EFFECT_SPELL_FROST: SoundPlayer.playSoundBySoundName("FROST"); break;
      case GRAPH_EFFECT_SPELL_SHOCK: SoundPlayer.playSoundBySoundName("SHOCK"); break;
      case GRAPH_EFFECT_SPELL_DARKNESS: SoundPlayer.playSoundBySoundName("DARKNESS"); break;
      case GRAPH_EFFECT_SPELL_SMITE: SoundPlayer.playSoundBySoundName("SMITE"); break;
      case GRAPH_EFFECT_SPELL_FEAR: SoundPlayer.playSoundBySoundName("MAGIC4"); break;
      case GRAPH_EFFECT_SPELL_DROWNING: SoundPlayer.playSoundBySoundName("WATERSPLASH"); break;
      //TODO: Make sure SFX fits for effect
      case GRAPH_EFFECT_SPELL_QI_FIST: SoundPlayer.playSoundBySoundName("MAGIC3"); break;
      }
    }
    return exp;
  }
  
  public int calculateHitChance(Character attacker, Character defenser,int attackNum) {
    Attack att = null;
    if (attackNum==0) {
      att = attacker.getFirstAttack();
    } else {
      att = attacker.getSecondAttack();
    }
    int luckBonus =0;
    if ((attacker.getPerks().isPerkActive(Perks.PERK_CRITICAL_STRIKE)) && (!att.isRangedAttack())) {
      luckBonus = 5;
    }
    if ((attacker.getPerks().isPerkActive(Perks.PERK_SHARP_SHOOTER)) && (att.isRangedAttack())) {
      luckBonus = 5;
    }
    int distBonus = 0;
    int dist = MapUtilities.getDistance(attacker.getX(),attacker.getY(),
        defenser.getX(), defenser.getY());
    if (att.isRangedAttack()) {
      switch (dist) {
      case 1: { distBonus = 15; break;}
      case 2: { distBonus = 10; break;}
      case 3: { distBonus = 5; break;}
      case 4: { distBonus = 0; break;}
      case 5: { distBonus = -5; break;}
      case 6: { distBonus = -10; break;}
      case 7: { distBonus = -15; break;} // This is the last Max Range
      case 8: { distBonus = -20; break;}
      case 9: { distBonus = -25; break;}
      case 10: { distBonus = -30; break;}
      }
    } 
    if (dist > 1 && att.isThrowingAttackPossible()) {
      att = attacker.getFirstAttackThrowable();
      switch (dist) {
      case 2: { distBonus = 5; break;}
      case 3: { distBonus = 0; break;}
      case 4: { distBonus = -5; break;}
      case 5: { distBonus = -10; break;}
      case 6: { distBonus = -15; break;}
      case 7: { distBonus = -20; break;} // This is the last Max Range
      case 8: { distBonus = -25; break;}
      case 9: { distBonus = -30; break;}
      case 10: { distBonus = -35; break;}
      }
    }
    if (dist > 1 && (!att.isRangedAttack() && !att.isThrowingAttackPossible())) {
      // Requires ranged attack or throwing but not available
      // zero chance to hit
      return 0;
    }
    // Darkness gives negative bonus
    int darknessPenalty = 0;
    if (!attacker.getPerks().isPerkActive(Perks.PERK_DARK_VISION)) { 
      darknessPenalty = this.getShade(attacker.getX(), attacker.getX(), this.party.isDay());
      switch (attacker.getLigthEffect()) {
      case -2: darknessPenalty = darknessPenalty +5; break; // Darkness
      case -1: darknessPenalty = darknessPenalty +4; break; // Dim darkness
      case 1: darknessPenalty = darknessPenalty -2; break; // Dim light
      case 2: darknessPenalty = darknessPenalty -4; break; // Torch light
      case 3: darknessPenalty = darknessPenalty -5; break; // Bright light
      }
      // Limiting darkness penalty
      if (darknessPenalty > 7) {
        darknessPenalty = 7;
      }
      if (darknessPenalty < 0) {
        darknessPenalty = 0;
      }
    }
    // If target is surround by darkess or light effect also that affects hitting
    switch (defenser.getLigthEffect()) {
    case -2: darknessPenalty = darknessPenalty +5; break; // Darkness
    case -1: darknessPenalty = darknessPenalty +4; break; // Dim darkness
    case 1: darknessPenalty = darknessPenalty -2; break; // Dim light
    case 2: darknessPenalty = darknessPenalty -4; break; // Torch light
    case 3: darknessPenalty = darknessPenalty -5; break; // Bright light
    }
    int bonus = att.getAttackBonus()+distBonus-darknessPenalty;
    int tn = defenser.getDefense().getDefense(); 
    luckBonus = attacker.getEffectiveAttribute(Character.ATTRIBUTE_LUCK)+luckBonus;
    if (100+bonus <tn) {
      // Hit only with critical bonus
      return luckBonus;
    } else {
      bonus = 100-(tn-bonus);
      if (bonus > 100-(11-attacker.getEffectiveAttribute(Character.ATTRIBUTE_LUCK))) {
        return 100-(11-attacker.getEffectiveAttribute(Character.ATTRIBUTE_LUCK));
      }
      return bonus;
    }
    
  }
  
  /**
   * Make character to attack against another with selectable attack style
   * @param attacker Character
   * @param defenser Character
   * @param attackType int ATTACK_TYPE_FULL_ATTACK, ATTACK_TYPE_SINGLE_ATTACK,
   * ATTACK_TYPE_THROW_ATTACK
   * @return experience points if attack was PC
   */
  public int doAttack(Character attacker, Character defenser, int attackType) {
    int exp = 0;
    Defense def = defenser.getDefense();
    
    boolean isAlreadyDead = defenser.isDead();
    byte direction = getDirectionToLook(attacker.getX(), attacker.getY(),
                            defenser.getX(), defenser.getY());
    attacker.setHeading(direction);
    if (!attacker.isPlayer()) {
      if (attacker.getRace()==CharacterRace.DEFAULT) {
        SoundPlayer.playEnemySoundsByName(attacker.getName());
      } else {
        SoundPlayer.playEnemySoundsByName(attacker.getRace().getName());
      }
    }
    for (int i=0;i<attacker.getNumberOfAttacks();i++) {
      if (i>0 && attackType!=ATTACK_TYPE_FULL_ATTACK) {
        // Not making full attack
        break;
      }
      Attack att = null;
      if (i==0) {
        if (attackType == ATTACK_TYPE_THROW_ATTACK) {
          att = attacker.getFirstAttackThrowable();
          if (attacker.getPerks().isPerkActive(Perks.PERK_POWER_THROWER)) {
            int piercing = att.getPiercing();
            piercing=piercing+1;
            att.setPiercing(piercing);
          }
        } else {
          att = attacker.getFirstAttack();
        }
      } else {
        att = attacker.getSecondAttack();
      }
      int luckBonus =0;
      if ((attacker.getPerks().isPerkActive(Perks.PERK_CRITICAL_STRIKE)) && (!att.isRangedAttack())) {
        luckBonus = 5;
      }
      if ((attacker.getPerks().isPerkActive(Perks.PERK_SHARP_SHOOTER)) && (att.isRangedAttack())) {
        luckBonus = 5;
      }
      int distBonus = 0;
      int dist = MapUtilities.getDistance(attacker.getX(),attacker.getY(),
          defenser.getX(), defenser.getY());
      if (att.isRangedAttack()) {
        
        switch (dist) {
        case 1: { distBonus = 15; break;}
        case 2: { distBonus = 10; break;}
        case 3: { distBonus = 5; break;}
        case 4: { distBonus = 0; break;}
        case 5: { distBonus = -5; break;}
        case 6: { distBonus = -10; break;}
        case 7: { distBonus = -15; break;} // This is the last Max Range
        case 8: { distBonus = -20; break;}
        case 9: { distBonus = -25; break;}
        case 10: { distBonus = -30; break;}
        }
      }     
      if (dist > 1 && attackType == Map.ATTACK_TYPE_THROW_ATTACK) {
        switch (dist) {
        case 2: { distBonus = 5; break;}
        case 3: { distBonus = 0; break;}
        case 4: { distBonus = -5; break;}
        case 5: { distBonus = -10; break;}
        case 6: { distBonus = -15; break;}
        case 7: { distBonus = -20; break;} // This is the last Max Range
        case 8: { distBonus = -25; break;}
        case 9: { distBonus = -30; break;}
        case 10: { distBonus = -35; break;}
        }
      }
      // Darkness gives negative bonus
      int darknessPenalty = 0;
      if (!attacker.getPerks().isPerkActive(Perks.PERK_DARK_VISION)) { 
        darknessPenalty = this.getShade(attacker.getX(), attacker.getX(), this.party.isDay());
        switch (attacker.getLigthEffect()) {
        case -2: darknessPenalty = darknessPenalty +5; break; // Darkness
        case -1: darknessPenalty = darknessPenalty +4; break; // Dim darkness
        case 1: darknessPenalty = darknessPenalty -2; break; // Dim light
        case 2: darknessPenalty = darknessPenalty -4; break; // Torch light
        case 3: darknessPenalty = darknessPenalty -5; break; // Bright light
        }
        // Limiting darkness penalty
        if (darknessPenalty > 7) {
          darknessPenalty = 7;
        }
        if (darknessPenalty < 0) {
          darknessPenalty = 0;
        }
      }
      // If target is surround by darkess or light effect also that affects hitting
      switch (defenser.getLigthEffect()) {
      case -2: darknessPenalty = darknessPenalty +5; break; // Darkness
      case -1: darknessPenalty = darknessPenalty +4; break; // Dim darkness
      case 1: darknessPenalty = darknessPenalty -2; break; // Dim light
      case 2: darknessPenalty = darknessPenalty -4; break; // Torch light
      case 3: darknessPenalty = darknessPenalty -5; break; // Bright light
      }
      int check = attacker.skillBonusCheck(att.getAttackBonus()+distBonus-darknessPenalty, def.getDefense(),luckBonus);
      if (!att.isRangedAttack()) {
        SoundPlayer.playSound(SoundPlayer.SOUND_FILE_SWING);
        if (attackType != Map.ATTACK_TYPE_THROW_ATTACK) {
          switch (direction) {
          case Character.DIRECTION_NORTH: addNewGraphEffect(defenser.getX(), defenser.getY(), GRAPH_EFFECT_ATTACK_MISS_NORTH); break;
          case Character.DIRECTION_SOUTH: addNewGraphEffect(defenser.getX(), defenser.getY(), GRAPH_EFFECT_ATTACK_MISS_SOUTH); break;
          case Character.DIRECTION_EAST: addNewGraphEffect(defenser.getX(), defenser.getY(), GRAPH_EFFECT_ATTACK_MISS_EAST); break;
          case Character.DIRECTION_WEST: addNewGraphEffect(defenser.getX(), defenser.getY(), GRAPH_EFFECT_ATTACK_MISS_WEST); break;
          }
        }
      } else {
        SoundPlayer.playSoundBySoundName("BOW");
      }
      attacker.receiveNonLethalDamage(att.getStaminaCost(),false);
      if (attacker.getCurrentSP()<attacker.getMaxStamina()/2) {
        addNewGraphEffect(attacker.getX(), attacker.getY(), GRAPH_EFFECT_SWEAT);
      }
      if (attackType == Map.ATTACK_TYPE_THROW_ATTACK) {
        Item throwable = attacker.throwFirstHandWeapon(party);
        if (throwable != null) {
          this.addNewItem(defenser.getX(), defenser.getY(), throwable);          
        }
      }
      if (check != Character.CHECK_FAIL) {
        int lethalDamage = DiceGenerator.getRandom(att.getMinLethalDamage(), att.getMaxLethalDamage());
        int nonLethalDamage = DiceGenerator.getRandom(att.getMinStaminaDamage(), att.getMaxStaminaDamage());
        switch ( (defenser.getRace().damageModifierFor(att.getAttackType()))) {
        case IMMUNITY: { lethalDamage = 0; nonLethalDamage = 0; break;}
        case NORMAL: { /*DO nothing*/ break;}
        case RESISTANCE: { lethalDamage = lethalDamage / 2; 
                            nonLethalDamage = nonLethalDamage /2; break;
        }
        case WEAKNESS: { lethalDamage = lethalDamage * 2; 
        nonLethalDamage = nonLethalDamage *2; break;}
        }
        if (check == Character.CHECK_CRITICAL_SUCCESS) {
          party.addLogText(attacker.getName()+" hits critically "+defenser.getName());
          lethalDamage = lethalDamage*att.getCriticalMultiplier();
          if (att.isSlayer()) {
            int slayerDamage = 10*attacker.getLevel();
            lethalDamage=lethalDamage+slayerDamage;
          }
          nonLethalDamage = nonLethalDamage*att.getCriticalMultiplier();
          lethalDamage = lethalDamage-def.getCriticalArmorRating();
        } else {
          lethalDamage = lethalDamage-def.getArmorRating();  
          party.addLogText(attacker.getName()+" hits "+defenser.getName());
        }
        int totalDamage = 0;
        if (lethalDamage < 1) {
          lethalDamage = 0;
        } else {
          totalDamage = lethalDamage;
        }
        defenser.receiveLethalDamage(lethalDamage);
        defenser.receiveNonLethalDamage(nonLethalDamage,true);
        if (att.isDrainHealth()) {
          attacker.setCurrentHP(attacker.getCurrentHP()+lethalDamage/2);
        }
        if (att.isDrainStamina()) {
          attacker.setCurrentSP(attacker.getCurrentSP()+nonLethalDamage/2);
        }
        if (att.isDrainVigor()) {
          attacker.setCurrentSP(attacker.getCurrentSP()+lethalDamage/2);
        }
        if (att.getPiercing()>0) {
          defenser.receiveLethalDamage(att.getPiercing());
          totalDamage = totalDamage+att.getPiercing();
        }
        if (att.getEffectOnHit()==CharEffect.EFFECT_ON_HIT_DISEASE ) {
          CharEffect eff = new CharEffect(attacker.getName()+" disease", 
              CharEffect.TYPE_DISEASE, att.getEffectLast(), CharEffect.EFFECT_ON_HEALTH,
              Character.ATTRIBUTE_STRENGTH, -att.getEffectValue(), 75);
          eff.setAttackType(att.getEffectAttackType());
          defenser.addEffect(eff);
        }
        if (att.getEffectOnHit()==CharEffect.EFFECT_ON_HIT_POISON ) {
          CharEffect eff = new CharEffect(attacker.getName()+" poison", 
              CharEffect.TYPE_POISON, att.getEffectLast(), CharEffect.EFFECT_ON_HEALTH,
              Character.ATTRIBUTE_STRENGTH, -att.getEffectValue(), 75);
          eff.setAttackType(att.getEffectAttackType());
          defenser.addEffect(eff);
        }
        if (att.getEffectOnHit()==CharEffect.EFFECT_ON_HIT_ENCHANT ) {
          CharEffect eff = new CharEffect(att.getEffectAttackType().toString()+" damage", 
              CharEffect.TYPE_ENCHANT, att.getEffectLast(), CharEffect.EFFECT_ON_HEALTH,
              Character.ATTRIBUTE_STRENGTH, -att.getEffectValue(), 75);
          eff.setAttackType(att.getEffectAttackType());
          defenser.addEffect(eff);
        }
        
        if ((totalDamage==0) && (nonLethalDamage==0)) {
          if (defenser.getRace().damageModifierFor(att.getAttackType())
              ==DamageModifier.IMMUNITY) {
            party.addLogText("but "+defenser.getName()+" has immunity against "+
              att.getAttackType().toString()+" attacks.");
          } else {
            party.addLogText("but deals no damage.");
          }
          SoundPlayer.playSound(SoundPlayer.SOUND_FILE_BLOCK);
        } else {
          StringBuilder sb = new StringBuilder();
          if (totalDamage > 0) {
            sb.append("and deals "+totalDamage+" points of damage");
          }
          if (nonLethalDamage > 0) {
            if (totalDamage > 0) {
              sb.append(" ");
            }
            sb.append("and deals "+nonLethalDamage+" points of non-lethal damage");
          }
          party.addLogText(sb.toString()+".");
          switch (defenser.getRace().damageModifierFor(att.getAttackType())) {
            case RESISTANCE: {party.addLogText("Hit was not that effective!"); break;}
            case WEAKNESS: {party.addLogText("Hit was very effective!"); break;}
          default: // Do nothing
            break;
          }
          if ((defenser.isDead()) && (!isAlreadyDead)) {
            isAlreadyDead = true;
            party.addLogText(defenser.getName()+" is dead.");
            if (attacker.isPlayer()) {
              exp = defenser.getExperience();
            }
            if (!defenser.isPlayer()) {
              dropAllItems(defenser);
              int index = findNPCbyCoordinates(defenser.getX(), defenser.getY());
              removeNPCbyIndex(index);
            } else {
              dropAllItems(defenser);
              party.setHeroDown(true);
              int index = party.getPartyMemberByCoordinates(defenser.getX(), defenser.getY());
              if (index != -1) {
                party.removePartyChar(index);
                party.addLogText(defenser.getLongName()+" has died!");
              }
            }
          }
          addNewGraphEffect(defenser.getX(), defenser.getY(), GRAPH_EFFECT_ATTACK_HIT);
          SoundPlayer.playSound(SoundPlayer.SOUND_FILE_HIT);
        }
      } else {
        party.addLogText(attacker.getName()+" misses "+defenser.getName()+".");
        if (att.isRangedAttack()) { 
          SoundPlayer.playSoundBySoundName("MISSING_ARROW");
        }

        if (defenser.isPlayer()) {
          defenser.setExperience(defenser.getExperience()+2);
        }
        if (att.isRangedAttack()) {
          addNewGraphEffect(defenser.getX(), defenser.getY(), GRAPH_EFFECT_RANGED_ATTACK_MISS);
        }
      }
    }
    return exp;   
  }
  
  /**
   * Make character to attack against another with full attack
   * @param attacker
   * @param defenser
   * @return amount of experience points gained
   */
  public int doAttack(Character attacker, Character defenser) {
    return doAttack(attacker,defenser,ATTACK_TYPE_FULL_ATTACK);
  }
  
  /**
   * Move party members. They move towards party leader.
   */
  public void doMovePartyMembers() {
    int leaderIndex = party.getActiveCharIndex();
    for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
      if (i != leaderIndex) {
        Character leader = party.getActiveChar();
        Character pc = party.getPartyChar(i);
        if (pc != null) {
          AIPath path = new AIPath(pc.getX(), pc.getY(),
              leader.getX(), leader.getY());
          path.updateMovementByBlockMap(this.getBlockMapSurround(pc.getX(), pc.getY()), 5, 5);
          int  direction = path.getFirstDirection();
          
          int mx = pc.getX();
          int my = pc.getY();
          switch (direction) {
          case DIRECTION_UP: my = my-1; break;
          case DIRECTION_DOWN: my = my+1; break;
          case DIRECTION_LEFT: mx = mx-1; break;
          case DIRECTION_RIGHT: mx = mx+1; break;
          }
          if (!isBlocked(pc.getX(),pc.getY(), mx, my)) {
            pc.doMove(mx, my);
          } else {
            mx = pc.getX();
            my = pc.getY();
            switch (path.getSecondDirection()) {
            case DIRECTION_UP: my = my-1; break;
            case DIRECTION_DOWN: my = my+1; break;
            case DIRECTION_LEFT: mx = mx-1; break;
            case DIRECTION_RIGHT: mx = mx+1; break;
            }
            if (!isBlocked(pc.getX(),pc.getY(), mx, my)) {
              pc.doMove(mx, my);
            }
  
          }
        }
        
      }
    }
  }
  
  private boolean shouldCombatContinue;
  
  private int NPCattackTask(Character npc,CharTask task, Character target) {
    boolean combatContinue = false; 
    int direction = DIRECTION_NONE;
    if ((npc.getCurrentSP() < npc.getAttackStaminaCost()) ||
        (npc.getCurrentSP() < 5)){
          npc.getStaminaInRestTurn();
          party.addLogText(npc.getName()+" catches a breath.");
          direction = DIRECTION_ATTACK;
          combatContinue = true;
    }
    int dist = MapUtilities.getDistance(npc.getX(), npc.getY(), 
        target.getX(), target.getY());
    if (dist >= OUT_OF_COMBAT_RANGE) {
      npc.removeKillTask();
      direction = DIRECTION_ATTACK;
      combatContinue = false;

    }
    Spell spell = npc.doIHaveHealingSpell();
    // Check if NPC needs to cast healing spell and is able to cast it
    if ((direction == DIRECTION_NONE) && (npc.getCurrentHP() < npc.getMaxHP()/3) && (spell != null))
    {
      doSpell(npc,npc,spell,true);
      combatContinue = true;
      direction = DIRECTION_ATTACK;
      npc.receiveNonLethalDamage(spell.getCastingCost()+npc.getStaminaCostFromLoad(),false);
      if (npc.getCurrentSP()<npc.getMaxStamina()/2) {
        addNewGraphEffect(npc.getX(), npc.getY(), GRAPH_EFFECT_SWEAT);
      }
    }
    spell = npc.doIHaveBoostSpell();
    // Check if NPC has boost spells
    if (spell != null) {
      if (DiceGenerator.getRandom(99)<50) {
        doSpell(npc,npc,spell,true);
        combatContinue = true;
        direction = DIRECTION_ATTACK;
        npc.receiveNonLethalDamage(spell.getCastingCost()+npc.getStaminaCostFromLoad(),false);
        if (npc.getCurrentSP()<npc.getMaxStamina()/2) {
          addNewGraphEffect(npc.getX(), npc.getY(), GRAPH_EFFECT_SWEAT);
        }
      }
    }
    spell = npc.doIHaveAttackSpell(target);
    // Check if NPC has attacking spell
    if ((direction == DIRECTION_NONE) && (spell != null) && (dist < MAX_RANGED_RANGE)) {
      if ((spell.getName().equals(SpellFactory.SPELL_NAME_PACIFISM)) && (target.isPacified())) {
        // Target is already pacified so not going to cast this
        direction = DIRECTION_NONE;
      } else
      if ((spell.getName().equals(SpellFactory.SPELL_NAME_DARKNESS)) &&
          ((target.getLigthEffect()<  0) ||
          (target.getPerks().isPerkActive(Perks.PERK_DARK_VISION)))) {
          // Total light effect is already negative or target has dark vision
          direction = DIRECTION_NONE;
      } else
      if ((spell.getName().equals(SpellFactory.SPELL_NAME_BURDEN)) && (target.hasBurden())) {
        // Target has already burden so not going to cast this
        direction = DIRECTION_NONE;
      }
      else {
        int cx = target.getX();
        int cy = target.getY();
        if ((dist <Map.MAX_RANGED_RANGE) &&
            (isClearShot(npc.getX(), npc.getY(), cx, cy))) {
          // Casting spell is possible but is it wise?
          int spellHit = calculateSpellHitChance(npc, target, spell);
          int hitHit = calculateHitChance(npc, target, 0);
          if (spellHit > hitHit) {
            // Yes it is wise
            direction = DIRECTION_ATTACK;
            combatContinue = true;
            doSpell(npc, target, spell,true);
            npc.receiveNonLethalDamage(spell.getCastingCost()+npc.getStaminaCostFromLoad(),false);
            if (npc.getCurrentSP()<npc.getMaxStamina()/2) {
              addNewGraphEffect(npc.getX(), npc.getY(), GRAPH_EFFECT_SWEAT);
            }
          }
        }
      }
    }
    // Good old fashioned attack
    if (direction == DIRECTION_NONE) {
      if (!npc.isPacified()) {
        if (npc.getFirstAttack().isRangedAttack()) {
          if ((dist < MAX_RANGED_RANGE) &&
              (isClearShot(npc.getX(),npc.getY(),target.getX(), target.getY()))) {
            if (((dist == 1) && (npc.getPerks().isPerkActive(Perks.PERK_POINT_BLANK_SHOT))) ||
                (dist > 1)) { 
               combatContinue = true;
               doAttack(npc, target);
               direction = DIRECTION_ATTACK;
            }
          }
        } else {
          if (dist == 1) {
            doAttack(npc, target);
            direction = DIRECTION_ATTACK;
            combatContinue = true;
          } else {
            if (CharTask.TASK_ATTACK_PC.equals(task.getTask())) {
              // So target if not next to NPC, let see if there is some else
              // closer
              int targetIndex = getAttackingTargetFromParty(npc);
              if (targetIndex != -1) {
                Character targetChr = party.getPartyChar(targetIndex);
                if ((targetChr != null) && (!targetChr.getName().equals(target.getName()))) {
                    // Yup there was so let's change target
                    task.setWayPointName(targetChr.getName());
                    target = targetChr;
                    dist = MapUtilities.getDistance(npc.getX(), npc.getY(), 
                        targetChr.getX(), targetChr.getY());
                    if (dist == 1) {
                      // And even next to NPC, so it can be attacked
                      doAttack(npc, targetChr);
                      direction = DIRECTION_ATTACK;
                      combatContinue = true;
                    }
                }
              }
            }
          }
        }      
      } else {
        party.addLogText(npc.getName()+" is pacified and cannot attack!");
        if (dist == 1) {
          direction = DIRECTION_ATTACK;
          npc.getStaminaInRestTurn();
          combatContinue = true;
        }
      }
    }
    if (direction == DIRECTION_NONE) {
      combatContinue = true;
      direction = AIPath.getDirectionToMove(npc.getX(), npc.getY(),
       target.getX(), target.getY());
      if (DiceGenerator.getRandom(10)==0) {
        if (npc.getRace()==CharacterRace.DEFAULT) {
          SoundPlayer.playEnemySoundsByName(npc.getName());
        } else {
          SoundPlayer.playEnemySoundsByName(npc.getRace().getName());
        }
      }
    } 
    shouldCombatContinue = combatContinue;
    return direction;
  }
  
  
  

  
  /**
   * Make NPCs to make their moves. Start moving NPCs from index 0
   * @param hour time
   * @param min time
   */
  public void doNPCsMove(int hour, int min) {
    doNPCsMove(hour, min, true);
  }

  /**
   * NPC iteration Index
   */
  private int npcIterationIndex=0;

  /**
   * Is NPC movement iteration on going or not
   * @return boolean
   */
  public boolean isNPCMovementInIteration() {
    if (npcIterationIndex > 0) {
      return true;
    } else {
      return false;
    }
  }
  /**
   * Make NPC to make their moves. Might continue where last where left
   * @param hour time
   * @param min time
   * @param startZero boolean
   */
  public void doNPCsMove(int hour, int min,boolean startZero) {
    boolean combatStillOn=false;
    boolean pauseIteration=false;
    if (startZero) {
      npcIterationIndex=0;
    }
    ListIterator<Character> npcIte = npcList.listIterator(npcIterationIndex);
    while (npcIte.hasNext()) {
      Character npc = npcIte.next();
      npc.handleEffects(party);
      if (npc.isDead()) {
        if (npc.getHostilityLevel()==Character.HOSTILITY_LEVEL_AGGRESSIVE) {
          party.shareExperience(npc.getExperience());
        }
        party.addLogText(npc.getName()+" has died!");
        npcIte.remove();
      } else {
        int taskIndex = npc.getCurrentTaskIndex(hour, min);
        CharTask task = null;
        if (taskIndex != -1) {
          task = npc.getTaskByIndex(taskIndex);
        }
        if (taskIndex != -1 && task != null) {
          
          int direction = DIRECTION_NONE;
          AIPath path = null;
          if (CharTask.TASK_RUN_EXIT.equals(task.getTask())) {
            // Tries to run exit
            if (task.getWayPointName().equals("0")) {
              direction = DIRECTION_UP;
            }
            if (task.getWayPointName().equals("1")) {
              direction = DIRECTION_RIGHT;
            }
            if (task.getWayPointName().equals("2")) {
              direction = DIRECTION_DOWN;
            }
            if (task.getWayPointName().equals("3")) {
              direction = DIRECTION_LEFT;
            }
            if (!npc.isAmIOnScreen()) {
              npcIte.remove();
            }
          }
          if (CharTask.TASK_RUN_TO_WP.equals(task.getTask())) {
            // Tries to run to WP
            if (task.getWayPointName().equals("0")) {
              direction = DIRECTION_UP;
            }
            if (task.getWayPointName().equals("1")) {
              direction = DIRECTION_RIGHT;
            }
            if (task.getWayPointName().equals("2")) {
              direction = DIRECTION_DOWN;
            }
            if (task.getWayPointName().equals("3")) {
              direction = DIRECTION_LEFT;
            }
            if (!npc.isAmIOnScreen()) {
              npc.removeTaskRunToWP();
            }
          }
          if (CharTask.TASK_MOVE_AWAY.equals(task.getTask())) {
            // Tries to run exit
            if (task.getWayPointName().equals("0")) {
              direction = DIRECTION_UP;
            }
            if (task.getWayPointName().equals("1")) {
              direction = DIRECTION_RIGHT;
            }
            if (task.getWayPointName().equals("2")) {
              direction = DIRECTION_DOWN;
            }
            if (task.getWayPointName().equals("3")) {
              direction = DIRECTION_LEFT;
            }
          }
          if (CharTask.TASK_ATTACK_NPC.equals(task.getTask())) {
            // Tries to kill NPC
            int npcIndex = getNPCByName(task.getWayPointName());
            Character chr = getNPCbyIndex(npcIndex);
            if (chr != null) {
              direction = NPCattackTask(npc, task, chr);
              path = new AIPath(npc.getX(), npc.getY(), chr.getX(), chr.getY());
              path.setFirstDirection(direction);
            } else {
              npc.removeKillTask();
            }
          }
          if (CharTask.TASK_ATTACK_PC.equals(task.getTask())) {
            // Tries to kill party member
            int pcIndex = party.getPartyMemberByName(task.getWayPointName());
            Character chr = party.getPartyChar(pcIndex);
            
            if (chr != null) {
              direction = NPCattackTask(npc, task, chr);
              path = new AIPath(npc.getX(), npc.getY(), chr.getX(), chr.getY());
              path.setFirstDirection(direction);
              if (shouldCombatContinue) {
                combatStillOn = true;
                pauseIteration=true;
              }
            } else {
              npc.removeKillTask();
            }
          }
          int index = getEventWaypointByName(task.getWayPointName());
          Event event = getEventByIndex(index);
          if (event != null) {
            if (CharTask.TASK_MOVE.equals(task.getTask())) {
              // Move to waypoint
              path = new AIPath(npc.getX(), npc.getY(),
                  event.getX(), event.getY());
              path.setLastDirection(npc.getLastDirection());
              path.updateMovementByBlockMap(this.getBlockMapSurround(npc.getX(), npc.getY(),11), 11, 11);
              direction = path.getFirstDirection();
              npc.setLastDirection(direction);
            } else {
              npc.setLastDirection(DIRECTION_NONE);
            }
            if (CharTask.TASK_MOVE_INSIDE_WP.equals(task.getTask())) {
              // Move randomly inside waypoint
              direction = AIPath.getDirectionToMove(npc.getX(), npc.getY(),
                 event.getRandomX(), event.getRandomY());
              if (MapUtilities.isTimePast(hour, min, task.getTime())) {
                npc.setCurrentTaskDone(true);
              }
            }
            if (CharTask.TASK_MOVE_INSIDE_WP_AND_ATTACK.equals(task.getTask())) {
              // Move to randomly inside waypoint and possible attack
              direction = AIPath.getDirectionToMove(npc.getX(), npc.getY(),
                 event.getRandomX(), event.getRandomY());
              int target = getAttackingTargetFromParty(npc);
              if (target != -1) {
                Character chr = party.getPartyChar(target);
                if (chr != null) {
                  party.setCombat(true);
                  npc.addTaskKillPartyMember(chr.getName());
                  // Task was placed for current one, so this task should not
                  // ever end
                  direction = DIRECTION_ATTACK;
                }
              }  /*else { // This seemed to cause more problem than coolness
                target = getAttackingTargetFromNPCs(npc);
                Character chr = getNPCbyIndex(target);
                if (chr != null) {
                  npc.addTaskKillNPC(chr.getName());
                  // Task was placed for current one, so this task should not
                  // ever end
                  direction = DIRECTION_ATTACK;
                }
              }*/
            }
            if (CharTask.TASK_MOVE_RANDOM.equals(task.getTask())) {
              // Move randomly
              int sector = getSectorByCoordinates(npc.getX(),npc.getY());
              direction = AIPath.getDirectionToMove(npc.getX(), npc.getY(),
                 DiceGenerator.getRandom(getSectorMinX(sector), getSectorMaxX(sector)),
                 DiceGenerator.getRandom(getSectorMinY(sector), getSectorMaxY(sector)));
              if (MapUtilities.isTimePast(hour, min, task.getTime())) {
                npc.setCurrentTaskDone(true);
              }
            }
            if (CharTask.TASK_MOVE_RANDOM_AND_ATTACK.equals(task.getTask())) {
              // Move randomly and possible attack
              int sector = getSectorByCoordinates(npc.getX(),npc.getY());
              direction = AIPath.getDirectionToMove(npc.getX(), npc.getY(),
                 DiceGenerator.getRandom(getSectorMinX(sector), getSectorMaxX(sector)),
                 DiceGenerator.getRandom(getSectorMinY(sector), getSectorMaxY(sector)));
              int target = getAttackingTargetFromParty(npc);
              if (target != -1) {
                Character chr = party.getPartyChar(target);
                if (chr != null) {
                  party.setCombat(true);
                  npc.addTaskKillPartyMember(chr.getName());
                  // Task was placed for current one, so this task should not
                  // ever end
                  direction = DIRECTION_ATTACK;
                }
              } /*else { // This seemed to cause more problem than coolness
                target = getAttackingTargetFromNPCs(npc);
                Character chr = getNPCbyIndex(target);
                if (chr != null) {
                  npc.addTaskKillNPC(chr.getName());
                  // Task was placed for current one, so this task should not
                  // ever end
                  direction = DIRECTION_ATTACK;
                }
              }*/
            }
            if (CharTask.TASK_JUMP_TO_WP.equals(task.getTask())) {
              // Just jump to target WayPoint, check for sounds
              String desc[] =task.getDescription().split("#");
              if ((desc.length == 2) && (npc.isAmIOnScreen())) {
                // Sound found let's play it
                SoundPlayer.playSoundBySoundName(desc[1]);
              }
              if (desc.length == 2) {
                if (desc[1].equalsIgnoreCase("Teleport")) {
                  addNewGraphEffect(npc.getX(),npc.getY(), GRAPH_EFFECT_SPELL_TELEPORT);
                  addNewGraphEffect(event.getX(),event.getY(), GRAPH_EFFECT_SPELL_TELEPORT);
                }
              }
              npc.setPosition(event.getX(),event.getY());
              npc.setHeading(Character.DIRECTION_SOUTH);
            }
            if (CharTask.TASK_JUMP_ONCE_TO_WP.equals(task.getTask())) {
              npc.setPosition(event.getX(),event.getY());
              npc.setHeading(Character.DIRECTION_SOUTH);
              npc.removeTaskJumpOnceToWP();
            }
            if (CharTask.TASK_KEEP_SHOP.equals(task.getTask())) {
              if (MapUtilities.isTimePast(hour, min, task.getTime())) {
                npc.setCurrentTaskDone(true);
                if (npc.inventorySize() < Character.MAX_ITEMS_ON_SHOPKEEPERS) {
                  Item item = ItemFactory.createItemByName(task.getWorkItem());
                  if (item != null) {
                    npc.inventoryPickUpItem(item);
                  }
                } else {
                  // Full inventory selling stuff and then generating new one
                  npc.sellExtraStuff();
                  Item item = ItemFactory.createItemByName(task.getWorkItem());
                  if (item != null) {
                    npc.inventoryPickUpItem(item);
                  }
                }
              } else {
                // Task is not complete so let's check for some animation
                if (DiceGenerator.getRandom(5)==0) {
                  if (npc.getType()==Character.ANIMATION_TYPE_NORMAL) {
                    addNewGraphEffect(npc.getHeadX(), npc.getHeadY(), GRAPH_EFFECT_BUBBLE_SELL);
                    if (npc.isAmIOnScreen()) {
                      SoundPlayer.playSoundBySoundName(task.getWorkSound());
                    }
                  } else {
                    addNewGraphEffect(npc.getX(), npc.getY(), GRAPH_EFFECT_BUBBLE_SELL);
                    if (npc.isAmIOnScreen()) {
                      SoundPlayer.playSoundBySoundName(task.getWorkSound());
                    }
                  }
                }
              }
            }
            if (CharTask.TASK_WORK.equals(task.getTask())) {
              if (MapUtilities.isTimePast(hour, min, task.getTime())) {
                npc.setCurrentTaskDone(true);
                if (npc.inventorySize() < Character.MAX_ITEMS_ON_SHOPKEEPERS) {
                  Item item = ItemFactory.createItemByName(task.getWorkItem());
                  if (item != null) {
                    npc.inventoryPickUpItem(item);
                  }
                } else {
                  // Full inventory selling stuff and then generating new one
                  npc.sellExtraStuff();
                  Item item = ItemFactory.createItemByName(task.getWorkItem());
                  if (item != null) {
                    npc.inventoryPickUpItem(item);
                  }
                }
              } else {
                // Task is not complete so let's check for some animation
                if (DiceGenerator.getRandom(5)==0) {
                  if (npc.getType()==Character.ANIMATION_TYPE_NORMAL) {
                    addNewGraphEffect(npc.getHeadX(), npc.getHeadY(), GRAPH_EFFECT_BUBBLE_WORK);
                    if (npc.isAmIOnScreen()) {
                      SoundPlayer.playSoundBySoundName(task.getWorkSound());
                    }
                  } else {
                    addNewGraphEffect(npc.getX(), npc.getY(), GRAPH_EFFECT_BUBBLE_WORK);
                    if (npc.isAmIOnScreen()) {
                      SoundPlayer.playSoundBySoundName(task.getWorkSound());
                    }
                  }
                }
              }
            }
            if (CharTask.TASK_SLEEP.equals(task.getTask())) {
              if (MapUtilities.isTimePast(hour, min, task.getTime())) {
                npc.setCurrentTaskDone(true);
              } else {
                // Task is not complete so let's check for some animation
                if (DiceGenerator.getRandom(5)==0) {
                  if (npc.getType()==Character.ANIMATION_TYPE_NORMAL) {
                    addNewGraphEffect(npc.getHeadX(), npc.getHeadY(), GRAPH_EFFECT_BUBBLE_SLEEP);
                  } else {
                    addNewGraphEffect(npc.getX(), npc.getY(), GRAPH_EFFECT_BUBBLE_SLEEP);
                  }
                }
              }
            }
            if (CharTask.TASK_EAT.equals(task.getTask())) {
              if (MapUtilities.isTimePast(hour, min, task.getTime())) {
                npc.setCurrentTaskDone(true);
              } else {
                // Task is not complete so let's check for some animation
                if (DiceGenerator.getRandom(5)==0) {
                  if (npc.getType()==Character.ANIMATION_TYPE_NORMAL) {
                    addNewGraphEffect(npc.getHeadX(), npc.getHeadY(), GRAPH_EFFECT_BUBBLE_EAT);
                  } else {
                    addNewGraphEffect(npc.getX(), npc.getY(), GRAPH_EFFECT_BUBBLE_EAT);
                  }
                }
              }
            }
            if (CharTask.TASK_CHILDPLAY.equals(task.getTask())) {
              if (MapUtilities.isTimePast(hour, min, task.getTime())) {
                npc.setCurrentTaskDone(true);
              } else {
                // Task is not complete so let's check for some animation
                if (DiceGenerator.getRandom(5)==0) {
                  if (npc.getType()==Character.ANIMATION_TYPE_NORMAL) {
                    addNewGraphEffect(npc.getHeadX(), npc.getHeadY(), GRAPH_EFFECT_BUBBLE_PLAY);
                  } else {
                    addNewGraphEffect(npc.getX(), npc.getY(), GRAPH_EFFECT_BUBBLE_PLAY);
                  }
                }
              }
            }
            
          }
          int mx = npc.getX();
          int my = npc.getY();
          switch (direction) {
          case DIRECTION_UP: my = my-1; break;
          case DIRECTION_DOWN: my = my+1; break;
          case DIRECTION_LEFT: mx = mx-1; break;
          case DIRECTION_RIGHT: mx = mx+1; break;
          case DIRECTION_NONE: {
             if ((MapUtilities.isTimePast(hour, min, task.getTime()) ||
                 (task.getTime().trim().equals("-")))) {               
               npc.setCurrentTaskDone(true);                
             }
             break;
            }
          }
          if (!isBlocked(npc.getX(),npc.getY(), mx, my)) {
            npc.doMove(mx, my);
          } else {
            if (CharTask.TASK_MOVE_AWAY.equals(task.getTask())) {
              npc.removeTaskMoveAway();  
            } else {
              mx = npc.getX();
              my = npc.getY();
              if (path == null) {
                switch (direction) {
                case DIRECTION_UP:  {
                                      if (DiceGenerator.getRandom(1)==0) {
                                        mx--;
                                      } else {
                                        mx++;
                                      }                                   
                                      break;
                                     }
                case DIRECTION_DOWN: {if (DiceGenerator.getRandom(1)==0) {
                                      mx--;
                                    } else {
                                        mx++;
                                    } break; }
                case DIRECTION_LEFT: {if (DiceGenerator.getRandom(1)==0) {
                                        my--;
                                     } else {
                                        my++;
                                     } break; }
                case DIRECTION_RIGHT: {if (DiceGenerator.getRandom(1)==0) {
                                        my--;
                                     } else {
                                        my++;
                                     } break; }
                }
              } else {
                switch (path.getSecondDirection()) {
                case DIRECTION_UP: my = my-1; break;
                case DIRECTION_DOWN: my = my+1; break;
                case DIRECTION_LEFT: mx = mx-1; break;
                case DIRECTION_RIGHT: mx = mx+1; break;
                }
              }
              if (!isBlocked(npc.getX(),npc.getY(), mx, my)) {
                npc.doMove(mx, my);
              }
            }
  
          } // End of first blocked
          
        } // End of Task handling
        if (pauseIteration && npcIte.hasNext()) {
          party.setCombat(combatStillOn);
          npcIterationIndex = npcIte.nextIndex();
          return;
        }
      } // End of Living NPC
    } // End of Iteration
    npcIterationIndex=0;
    if (startZero) {
      party.setCombat(combatStillOn);
    }
  }

  
  /**
   * Add new graphical effect to map
   * @param x coordinate
   * @param y coordinate
   * @param effect Effect tile GRAPH_EFFECT_*
   */
  public void addNewGraphEffect(int x, int y, int effect) {
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
      if (graphEffects[x][y]==-1) {
        graphEffects[x][y] = effect;
      } else {
        int effectInPlace =graphEffects[x][y];
        switch (effect) {
        case GRAPH_EFFECT_ATTACK_HIT: {
          if (effectInPlace == GRAPH_EFFECT_ATTACK_MISS_EAST  ||
              effectInPlace == GRAPH_EFFECT_ATTACK_MISS_SOUTH ||
              effectInPlace == GRAPH_EFFECT_ATTACK_MISS_NORTH ||
              effectInPlace == GRAPH_EFFECT_ATTACK_MISS_WEST) {
            // Blood is over draw by misses
            graphEffects[x][y] = effect;            
          }
          break;
        }
        case GRAPH_EFFECT_SWEAT: {
          // SWEAT does not overdraw anything
          break;
        }
        default: {
          graphEffects[x][y] = effect;
          break;
        }
        }
      }
    }
  }
  
  /**
   * Clear all graphical effects from map
   */
  public void clearAllGraphEffects() {
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        graphEffects[i][j] = -1;
      }
    }
    
  }
  
  
  /**
   * Draw full size map without characters, items and effects
   * @param day
   * @return BufferedImage
   */
  public BufferedImage drawFullMap(boolean day) {
    BufferedImage screen = new BufferedImage(maxX*Tile.MAX_WIDTH,maxY*Tile.MAX_HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);
    for (int x=0;x<maxX;x++) {
      for (int y=0;y<maxY;y++) {
        int shade = getEffectiveShade(x, y, day);
        if (walls[x][y]!=-1) {
            Tile tmp = mapTileset.getTile(walls[x][y]);
            tmp.putTile(screen, x*Tile.MAX_WIDTH, y*Tile.MAX_HEIGHT,shade);
          }
        if (objects[x][y]!=-1) {
          Tile tmp = mapTileset.getTile(objects[x][y]);
          tmp.putTile(screen, x*Tile.MAX_WIDTH, y*Tile.MAX_HEIGHT,shade);
        }
        if (decorations[x][y]!=-1) {
          Tile tmp = mapTileset.getTile(decorations[x][y]);
          tmp.putTile(screen, x*Tile.MAX_WIDTH, y*Tile.MAX_HEIGHT,shade);
        }
        if (top[x][y]!=-1) {
          Tile tmp = mapTileset.getTile(top[x][y]);
          tmp.putTile(screen, x*Tile.MAX_WIDTH, y*Tile.MAX_HEIGHT,shade);
        }

        }
    }            
    return screen;
  }
  
  private void setNeedReDraw(int x, int y, boolean reDraw) {
    if ((!needAlwaysRedraw[x][y]) && (reDraw)) {
      needAlwaysRedraw[x][y] = reDraw;
    }
  }
  
  public void forceMapFullRepaint() {
    textsWritten = false;
    forceRePaint = true;
  }  
  
  public boolean isFullRepaint() {
    return forceRePaint;
  }
  
  /**
   * Draw compass and possible popup message
   * @param screen where to draw
   * @param cx, coordinates to map
   * @param cy, coordinates to map
   * @param offsetX, Offsets in screen
   * @param offsetY, Offsets in screen
   * @param g Graphics for Screen
   */
  private void drawCompassAndPopup(BufferedImage screen, int cx,int cy,
                int offsetX,int offsetY,int topYOffset,Graphics2D g) {
    mapCompass.updateCompass(this.getNorthDirection(cx, cy)*2);
    mapCompass.drawCompass(screen, screen.getWidth() / 2 -40, screen.getHeight()-90);
    if (party != null) {
      party.drawTimeBar(screen, offsetX,screen.getHeight()-120,Tile.MAX_WIDTH*(VIEW_X_RADIUS*2+1));
      if (party.isCharacterChanged()) {
        StringBuilder sb = new StringBuilder();
        sb.append("Defense: ");
        sb.append(party.getActiveChar().getDefense().getDefense());
        sb.append("\nWillpower: ");
        sb.append(party.getActiveChar().getDefense().getWillPower());
        setDefenseText(sb.toString());
      }
      if (this.isCursorMode()) {
        if (this.getCursorMode() == Map.CURSOR_MODE_USE) {
          int curx = this.getCursorX();
          int cury = this.getCursorY();
          Character chr = party.getActiveChar();
          int dist = MapUtilities.getDistance(chr.getX(), chr.getY(), curx, cury);
          Event event = this.getEventByIndex(this.getEvent(curx, cury));
          if (event != null && dist < 2 && usedItem != null) {
            if (event.getEventCommand() == Event.COMMAND_TYPE_LOCKED_DOOR ||
                event.getEventCommand() == Event.COMMAND_TYPE_LOCKED_GATE) {
              if (usedItem.getType() == Item.TYPE_ITEM_PICKLOCK &&
                  event.getLockDoorDifficulty() > 0 && event.getLockDoorDifficulty() < 500) {
                int bonus = chr.getEffectiveSkill(Character.SKILL_LOCK_PICKING)+usedItem.getBonusPickLock();
                int tn = event.getLockDoorDifficulty();
                int luckBonus = chr.getEffectiveAttribute(Character.ATTRIBUTE_LUCK);
                if (100+bonus <tn) {
                  // Hit only with critical bonus
                  bonus = luckBonus;
                } else {
                  bonus = 100-(tn-bonus);
                  if (bonus > 100) {
                    bonus = 100-(11-chr.getEffectiveAttribute(Character.ATTRIBUTE_LUCK));
                  }                  
                }
                setAttackText("Picklock: "+bonus+" %");
              } else if (usedItem.getType() == Item.TYPE_ITEM_KEY &&
                  event.getLockDoorKeyName().equalsIgnoreCase(usedItem.getKeyValue())) {
                setAttackText("Key matches to\ndoor lock.");
              } else {
                setAttackText("Key does not \nfit to lock.");
              }
            }
              
          }
        }
        if (this.getCursorMode() == Map.CURSOR_MODE_ATTACK 
            || this.getCursorMode() == Map.CURSOR_MODE_SINGLE_ATTACK) {
          int curx = this.getCursorX();
          int cury = this.getCursorY();
          Character chr = party.getActiveChar();
          int dist = MapUtilities.getDistance(chr.getX(), chr.getY(), curx, cury);
          Character npc = this.getNPCbyIndex(this.getCharacterByCoordinates(curx, cury));
          if (dist <Map.MAX_RANGED_RANGE && chr.getFirstAttack().isRangedAttack() &&
              this.isClearShot(chr.getX(), chr.getY(), curx, cury) && npc != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("1st attack: ");
            sb.append(this.calculateHitChance(chr, npc, 0));
            sb.append(" %");
            if (chr.getNumberOfAttacks()==2 && this.getCursorMode() == Map.CURSOR_MODE_ATTACK) {
              sb.append("\n2nd attack: ");
              sb.append(this.calculateHitChance(chr, npc, 1));
              sb.append(" %");
            }
            setAttackText(sb.toString());
          } else if (dist > 1 && dist <Map.MAX_RANGED_RANGE && chr.getFirstAttackThrowable().isThrowingAttackPossible() &&
              this.isClearShot(chr.getX(), chr.getY(), curx, cury) && npc != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("1st attack: ");
            sb.append(this.calculateHitChance(chr, npc, 0));
            sb.append(" %");            
            setAttackText(sb.toString());
          } else if (dist <2 && !chr.getFirstAttack().isRangedAttack() &&
              this.isClearShot(chr.getX(), chr.getY(), curx, cury) && npc != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("1st attack: ");
            sb.append(this.calculateHitChance(chr, npc, 0));
            sb.append(" %");
            if (chr.getNumberOfAttacks()==2 && this.getCursorMode() == Map.CURSOR_MODE_ATTACK) {
              sb.append("\n2nd attack: ");
              sb.append(this.calculateHitChance(chr, npc, 1));
              sb.append(" %");
            }
            setAttackText(sb.toString());
          } else{
            setAttackText("No target");
          }
          
        } // END of Attack
        if ((this.getCursorMode() == Map.CURSOR_MODE_CAST) && (castingSpell != null)) {
          int curx = this.getCursorX();
          int cury = this.getCursorY();
          Character chr = party.getActiveChar();
          int dist = MapUtilities.getDistance(chr.getX(), chr.getY(), curx, cury);
          Character npc = this.getNPCbyIndex(this.getCharacterByCoordinates(curx, cury));
          if (dist <Map.MAX_RANGED_RANGE && 
              this.isClearShot(chr.getX(), chr.getY(), curx, cury) && npc != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Spell: ");
            sb.append(this.calculateSpellHitChance(chr, npc, castingSpell));
            sb.append(" %");
            setAttackText(sb.toString());
          } else{
            setAttackText("No target");
          }
        } // END of Casting            
      }
    }
    
    if (!textsWritten) {
      if (attackText != null) {
        if (IMAGE_ATTACK == null) {
          try {
            IMAGE_ATTACK= ImageIO.read(Map.class.getResource("/res/images/attack.png"));
          } catch (IOException e) {
            System.out.println("Could not read /res/images/attack.png!");
            return;
          }
        }
        Color cBLACK= new Color(0,0,0);
        Color cWHITE = new Color(255,255,255);
        Color cDARKGRAY= new Color(25,25,25);
        Color cGRAY= new Color(50,50,50);
        int topX = 20+offsetX;
        int topY = screen.getHeight()-topYOffset+170;
        int popupWidth = screen.getWidth()/2-100;
        int popupHeight = 50;
        GradientPaint gradient = new GradientPaint(topX,topY, cBLACK,
            topX+popupWidth,topY+popupHeight, cDARKGRAY, true);
        g.setPaint(gradient);
        g.fillRect(topX, topY, popupWidth, popupHeight);
  
        g.drawImage(IMAGE_ATTACK, topX+10, topY+5, IMAGE_ATTACK.getWidth(), IMAGE_ATTACK.getHeight(), null);
  
        StringBuilder sb = new StringBuilder(attackText);
        
        String[] texts = sb.toString().split("\n");
        g.setFont(new Font("monospaced",Font.BOLD,18));
        RasterFonts fonts = new RasterFonts(g);
        fonts.setFontType(RasterFonts.FONT_TYPE_SMALL);
        for (int i=0;i<texts.length;i++) {
          fonts.drawString(topX+60, topY+i*18+5, texts[i]);
        }
        
        g.setColor(cGRAY);
        g.drawRect(topX, topY, popupWidth, popupHeight);
        g.setColor(cWHITE);
        g.drawRect(topX+1, topY+1, popupWidth-2, popupHeight-2);
        g.setColor(cGRAY);
        g.drawRect(topX+2, topY+2, popupWidth-4, popupHeight-4);
        
      }
      if (defenseText != null) {
        if (IMAGE_DEFENSE == null) {
          try {
            IMAGE_DEFENSE= ImageIO.read(Map.class.getResource("/res/images/defense.png"));
          } catch (IOException e) {
            System.out.println("Could not read /res/images/defense.png!");
            return;
          }
        }
        Color cBLACK= new Color(0,0,0);
        Color cWHITE = new Color(255,255,255);
        Color cDARKGRAY= new Color(25,25,25);
        Color cGRAY= new Color(50,50,50);
        int topX = screen.getWidth()/2+70;
        int topY = screen.getHeight()-topYOffset+170;
        int popupWidth = screen.getWidth()/2-100;
        int popupHeight = 50;
        GradientPaint gradient = new GradientPaint(topX,topY, cBLACK,
            topX+popupWidth,topY+popupHeight, cDARKGRAY, true);
        g.setPaint(gradient);
        g.fillRect(topX, topY, popupWidth, popupHeight);
        
        g.drawImage(IMAGE_DEFENSE, topX+10, topY+5, IMAGE_DEFENSE.getWidth(), IMAGE_DEFENSE.getHeight(), null);
        
        StringBuilder sb = new StringBuilder(defenseText);
        
        String[] texts = sb.toString().split("\n");
        g.setFont(new Font("monospaced",Font.BOLD,18));
        RasterFonts fonts = new RasterFonts(g);
        fonts.setFontType(RasterFonts.FONT_TYPE_SMALL);
        for (int i=0;i<texts.length;i++) {
          fonts.drawString(topX+60, topY+i*18+5, texts[i]);
        }
  
        
        g.setColor(cGRAY);
        g.drawRect(topX, topY, popupWidth, popupHeight);
        g.setColor(cWHITE);
        g.drawRect(topX+1, topY+1, popupWidth-2, popupHeight-2);
        g.setColor(cGRAY);
        g.drawRect(topX+2, topY+2, popupWidth-4, popupHeight-4);
        
      }
      textsWritten = true;
    }
    if (drawableDisplayPopupText != null) {
      Color cBLUE= new Color(50,50,255);
      Color cDARK_BLUE= new Color(0,0,50);
      Color cWHITE = new Color(255,255,255);
      Color cGRAY= new Color(50,50,50);


      int topX = 20+offsetX;
      int topY = screen.getHeight()-topYOffset;
      int popupWidth = screen.getWidth()-topX*2;
      int popupHeight = 150;
      GradientPaint gradient = new GradientPaint(topX,topY, cBLUE,
          topX+popupWidth,topY+popupHeight, cDARK_BLUE, true);
      g.setPaint(gradient);
      g.fillRect(topX, topY, popupWidth, popupHeight);
      if (popupImage != null) {
        g.drawImage(popupImage, topX+10, topY+popupHeight/2-24, 48, 48, null);
      }
      
      StringBuilder sb = new StringBuilder(drawableDisplayPopupText);
      int lastSpace = -1;
      int rowLen = 0;
      int maxRowLen = (popupWidth-90)/12;
      for (int i=0;i<sb.length();i++) {
        if (sb.charAt(i)==' ') {
          lastSpace = i;
        }
        if (sb.charAt(i)=='\n') {
          lastSpace = -1;
          rowLen=0;
        } else {
          rowLen++;
        }
        if (rowLen > maxRowLen) {
          if (lastSpace != -1) {
            sb.setCharAt(lastSpace, '\n');
            rowLen=i-lastSpace;
            lastSpace = -1;
          }
        }
      }
      
      String[] texts = sb.toString().split("\n");
      g.setFont(new Font("monospaced",Font.BOLD,18));
      RasterFonts fonts = new RasterFonts(g);
      fonts.setFontType(RasterFonts.FONT_TYPE_WHITE);
      for (int i=0;i<texts.length;i++) {
        fonts.drawString(topX+70, topY+i*18+5, texts[i]);
      }
      
      g.setColor(cGRAY);
      g.drawRect(topX, topY, popupWidth, popupHeight);
      g.setColor(cWHITE);
      g.drawRect(topX+1, topY+1, popupWidth-2, popupHeight-2);
      g.setColor(cGRAY);
      g.drawRect(topX+2, topY+2, popupWidth-4, popupHeight-4);
    }
    
  }
  
  /**
   * Draw image instead of map and then draw compass and pop up
   * @param screen Where to draw
   * @param vx Coordinates for map
   * @param vy Coordinates for map
   * @param offsetX Offset in Screen
   * @param offsetY Offset in Screen
   * @param image Image what to draw
   */
  public void drawImageAndCompass(BufferedImage screen, int vx, int vy,
      int offsetX,int offsetY,BufferedImage image) {
    getDrawableTexts();
    Graphics2D g = screen.createGraphics();
    g.drawImage(image,offsetX,offsetY,null);
    drawCompassAndPopup(screen, vx, vy, offsetX, offsetY, 250, g);
  }
  
  private void getDrawableTexts() {
    drawableDisplayPopupText = displayPopupText;
  }
  
  /**
   * Draw map to bufferedImage
   * @param screen where to draw
   * @param vx map coordinates
   * @param vy map coordinate
   * @param offsetX coordinates in screen
   * @param offsetY coordinates in screen
   * @param editorMode Is editormode?
   * @param day Is day?
   */
  public void drawMap(BufferedImage screen, int vx,int vy,int offsetX,int offsetY, boolean editorMode, boolean day) {
    int cx = vx;
    int cy = vy;
    int sector = getSectorByCoordinates(cx, cy);
//    int lastWallIndex = -1;
//    int lastWallShade = 0;    
    getDrawableTexts();
    boolean rePaint = false;    
    if (forceRePaint) {
      rePaint = true;
      forceRePaint = false;
    }
    
    //BufferedImage wallImage = null;
    pulsatingShade++;
    if (pulsatingShade > 7) {
      pulsatingShade = 0;
    }
    if (!myChar.doMove(cx,cy)) {
      myChar.doStand();
    }
    
    if (vx < getSectorMinX(sector)+VIEW_X_RADIUS) { vx = getSectorMinX(sector)+VIEW_X_RADIUS; }
    if (vy < getSectorMinY(sector)+VIEW_Y_RADIUS) { vy = getSectorMinY(sector)+VIEW_Y_RADIUS;}    
    if (vx > getSectorMaxX(sector)-VIEW_X_RADIUS-1) {vx = getSectorMaxX(sector)-VIEW_X_RADIUS-1;}
    if (vy > getSectorMaxY(sector)-VIEW_Y_RADIUS-1) {vy = getSectorMaxY(sector)-VIEW_Y_RADIUS-1;}
    if ((vx != lastDrawnX) || (vy != lastDrawnY)) {
      rePaint = true;
    }
    if (editorMode) {
      rePaint = true;
    }
    lastDrawnX = vx;
    lastDrawnY = vy;
    
    int itemLookUp[][] = new int[VIEW_X_RADIUS*2+1][VIEW_Y_RADIUS*2+1];
    if  ((needAlwaysRedraw == null) || (rePaint)) {
      needAlwaysRedraw = new boolean[VIEW_X_RADIUS*2+1][VIEW_Y_RADIUS*2+1];
    }
    for (int i=0;i<VIEW_X_RADIUS*2+1;i++) {
      for (int j=0;j<VIEW_Y_RADIUS*2+1;j++) {
        itemLookUp[i][j] = -1;
      }
    }
    int charHeadLookUp[][] = new int[VIEW_X_RADIUS*2+1][VIEW_Y_RADIUS*2+1];
    for (int i=0;i<VIEW_X_RADIUS*2+1;i++) {
      for (int j=0;j<VIEW_Y_RADIUS*2+1;j++) {
        charHeadLookUp[i][j] = -1;
      }
    }
    int charBodyLookUp[][] = new int[VIEW_X_RADIUS*2+1][VIEW_Y_RADIUS*2+1];
    for (int i=0;i<VIEW_X_RADIUS*2+1;i++) {
      for (int j=0;j<VIEW_Y_RADIUS*2+1;j++) {
        charBodyLookUp[i][j] = -1;
      }
    }
    int soundLookUp[][] = new int[VIEW_X_RADIUS*2+1][VIEW_Y_RADIUS*2+1];
    for (int i=0;i<VIEW_X_RADIUS*2+1;i++) {
      for (int j=0;j<VIEW_Y_RADIUS*2+1;j++) {
        soundLookUp[i][j] = -1;
      }
    }
    int lightLookUp[][] = new int[maxX][maxY];
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        lightLookUp[i][j] = 0;
      }
    }
    if ((cy == 50) || (cy == 51)) {
      cy = vy;
    }



    // Get items into Items lookup list
    if (listItems.size() > 0) {
      for (int i=0;i<listItems.size();i++) {
        Item tmp = listItems.get(i);
        int x = tmp.getX()-vx+VIEW_X_RADIUS;
        int y = tmp.getY()-vy+VIEW_Y_RADIUS;
        if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
          itemLookUp[x][y] = tmp.getTileNumber();
      }
    }
    if (listEvents.size() > 0) {
      for (int i=0;i<listEvents.size();i++) {
        Event myEvent = listEvents.get(i);
        if (myEvent.getEventCommand() == Event.COMMAND_TYPE_PLAY_SOUND) {
          if (myEvent.getType()==Event.TYPE_POINT) {
            int x = myEvent.getX()-vx+VIEW_X_RADIUS;
            int y = myEvent.getY()-vy+VIEW_Y_RADIUS;
            if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
              soundLookUp[x][y] = i;
          } else {
            int x = myEvent.getX1()-vx+VIEW_X_RADIUS;
            int y = myEvent.getY1()-vy+VIEW_Y_RADIUS;
            if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
              soundLookUp[x][y] = i;
            x = myEvent.getX2()-vx+VIEW_X_RADIUS;
            y = myEvent.getY1()-vy+VIEW_Y_RADIUS;
            if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
              soundLookUp[x][y] = i;
            x = myEvent.getX2()-vx+VIEW_X_RADIUS;
            y = myEvent.getY2()-vy+VIEW_Y_RADIUS;
            if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
              soundLookUp[x][y] = i;
            x = myEvent.getX1()-vx+VIEW_X_RADIUS;
            y = myEvent.getY2()-vy+VIEW_Y_RADIUS;
            if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
              soundLookUp[x][y] = i;
          }
        }
      }
    }

    // Get NPCs into character lookup list
    if (npcList.size() > 0) {
      for (int i=0;i<npcList.size();i++) {
        Character tmp = npcList.get(i);
        tmp.doStand();
        int light = tmp.getLigthEffect();
        switch (light) {
        case -2: makeDarkness(tmp.getX(),tmp.getY(),lightLookUp); break;
        case -1: makeDimDarkness(tmp.getX(),tmp.getY(),lightLookUp); break;
        case 1:  makeDimLight(tmp.getX(),tmp.getY(),lightLookUp); break;
        case 2:  makeTorchLight(tmp.getX(),tmp.getY(),lightLookUp); break;
        case 3:  makeBrightLight(tmp.getX(),tmp.getY(),lightLookUp); break;
        }
        tmp.setAmIOnScreen(false);
        int x = tmp.getX()-vx+VIEW_X_RADIUS;
        int y = tmp.getY()-vy+VIEW_Y_RADIUS;
        if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1)) {
          charBodyLookUp[x][y] = tmp.getBodyTile();
          tmp.setAmIOnScreen(true);
        }
          
        x = tmp.getHeadX()-vx+VIEW_X_RADIUS;
        y = tmp.getHeadY()-vy+VIEW_Y_RADIUS;
        if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1)) {
          charHeadLookUp[x][y] = tmp.getHeadTile();
          tmp.setAmIOnScreen(true);
        }
      }

    }
    // Get party into Character lookup list
    if (party != null) {
      for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
        Character tmp = party.getPartyChar(i);
        if (tmp != null) {
          tmp.doStand();
          int light = tmp.getLigthEffect();
          switch (light) {
          case -2: makeDarkness(tmp.getX(),tmp.getY(),lightLookUp); break;
          case -1: makeDimDarkness(tmp.getX(),tmp.getY(),lightLookUp); break;
          case 1:  makeDimLight(tmp.getX(),tmp.getY(),lightLookUp); break;
          case 2:  makeTorchLight(tmp.getX(),tmp.getY(),lightLookUp); break;
          case 3:  makeBrightLight(tmp.getX(),tmp.getY(),lightLookUp); break;
          }          
          int x = tmp.getX()-vx+VIEW_X_RADIUS;
          int y = tmp.getY()-vy+VIEW_Y_RADIUS;
          if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
            charBodyLookUp[x][y] = tmp.getBodyTile();
          x = tmp.getHeadX()-vx+VIEW_X_RADIUS;
          y = tmp.getHeadY()-vy+VIEW_Y_RADIUS;
          if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
            charHeadLookUp[x][y] = tmp.getHeadTile();
          
        }
      }
    }

    for (int i=0;i<VIEW_X_RADIUS*2+1;i++) {
      int y = vy-VIEW_Y_RADIUS+i;
      for (int j=0;j<VIEW_Y_RADIUS*2+1;j++) {
        int x = vx-VIEW_X_RADIUS+j;
        if (x > -1 && x < graphEffects.length && y > -1 && y < graphEffects[x].length) {
          if (graphEffects[x][y]!=-1) {
            int light = getLightEffectForGraphEffect(graphEffects[x][y]);
            switch (light) {
            case -2: makeDarkness(x,y,lightLookUp); break;
            case -1: makeDimDarkness(x,y,lightLookUp); break;
            case 1:  makeDimLight(x,y,lightLookUp); break;
            case 2:  makeTorchLight(x,y,lightLookUp); break;
            case 3:  makeBrightLight(x,y,lightLookUp); break;
            }
          }
        }
        
      }
    }

    
    if (editorMode) {
      int x = myChar.getX()-vx+VIEW_X_RADIUS;
      int y = myChar.getY()-vy+VIEW_Y_RADIUS;
      if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
        charBodyLookUp[x][y] = myChar.getBodyTile();
      y = myChar.getHeadX()-vx+VIEW_X_RADIUS;
      y = myChar.getHeadY()-vy+VIEW_Y_RADIUS;
      if ((x>=0) && (x<VIEW_X_RADIUS*2+1) && (y>=0) && (y<VIEW_Y_RADIUS*2+1))
        charHeadLookUp[x][y] = myChar.getHeadTile();
    }
    
    int x = vx-VIEW_X_RADIUS;
    int y = vy-VIEW_Y_RADIUS;
    Graphics2D g = screen.createGraphics();
    for (int i=0;i<VIEW_X_RADIUS*2+1;i++) {
      y = vy-VIEW_Y_RADIUS;
      for (int j=0;j<VIEW_Y_RADIUS*2+1;j++) {
        boolean drawnCharacter = false;
        // Drawing the walls and floors        
        int shade = getEffectiveShade(x, y, day);
        if (isInsideArray(x, y, lightLookUp)) {
          shade = shade +lightLookUp[x][y];
          if (lightLookUp[x][y]!=0) {
            setNeedReDraw(i,j,true);
          }
          if (shade < 0) {
            shade = 0;
          }
          if (shade > 7) {
            shade = 7;
          }
        }
        if (getSpecial(x, y, day) > 0) {
          setNeedReDraw(i,j,true);
        }
        if (walls[x][y]!=-1) {
          if ((rePaint) || (needAlwaysRedraw[i][j])) {
            setNeedReDraw(i, j, mapTileset.isTileAnimated(walls[x][y]));
            Tile tmp = mapTileset.getTile(walls[x][y]);
            if (tmp != null) {
              tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
            }
          }
        }
        // Drawing the objects
        if (objects[x][y]!=-1) {
          if ((rePaint) || (needAlwaysRedraw[i][j])) {
            setNeedReDraw(i, j, mapTileset.isTileAnimated(objects[x][y]));
            Tile tmp = mapTileset.getTile(objects[x][y]);
            if (tmp != null) {
              tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
            }
          }
        }
        // Draw items
        if (itemsTileset != null) {
          if ((itemLookUp[i][j]> -1) && ((decorations[x][y]==-1)||(editorMode))) {
            Tile tmp = itemsTileset.getTile(itemLookUp[i][j]);
            setNeedReDraw(i, j, true);
            if (tmp != null) {
              tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
            }
          }
        }
        // Drawing the decorations
        if (decorations[x][y]!=-1) {
          if ((rePaint) || (needAlwaysRedraw[i][j])) {
            setNeedReDraw(i, j, mapTileset.isTileAnimated(decorations[x][y]));
            Tile tmp = mapTileset.getTile(decorations[x][y]);
            if (tmp != null) {
              tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
            }
          }
        }
        // Draw characters
        if (charactersTileset != null) {
          if (charBodyLookUp[i][j]> -1) {
            setNeedReDraw(i, j, true);
            Tile tmp = charactersTileset.getTile(charBodyLookUp[i][j]);
            if (tmp != null) {
              tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
            }
            drawnCharacter = true;
          }
          if (charHeadLookUp[i][j]> -1) {
            setNeedReDraw(i, j, true);
            Tile tmp = charactersTileset.getTile(charHeadLookUp[i][j]);
            if (tmp != null) {
              tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
            }
            drawnCharacter = true;
          }
        }
        // Draw graphical effects
        if (effectsTileset != null) {          
          if (graphEffects[x][y]!=-1) {
            if ((graphEffects[x][y] >= 5 && graphEffects[x][y] < 20) || 
               (graphEffects[x][y] >= 72 && graphEffects[x][y] < 85) ||
               (graphEffects[x][y] >= 160 && graphEffects[x][y] < 167)) {
              if (drawnCharacter) {
                setNeedReDraw(i, j, true);
                Tile tmp = effectsTileset.getTile(graphEffects[x][y]);
                if (tmp != null) {
                  tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
                }
              } else {
                graphEffects[x][y] = -1;
              }
            } else {
              setNeedReDraw(i, j, true);
              Tile tmp = effectsTileset.getTile(graphEffects[x][y]);
              if (tmp != null) {
                tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
              } 
            }
          }
        }
        
        // Drawing the top
        if (top[x][y]!=-1) {
          if ((rePaint) || (needAlwaysRedraw[i][j])) {
            setNeedReDraw(i, j, mapTileset.isTileAnimated(top[x][y]));
            Tile tmp = mapTileset.getTile(top[x][y]);
            if (tmp != null) {
              tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
            }
          }
        }
        // Draw cursor if in cursor mode
        if ((isCursorMode()) &&(getCursorX()==x) &&(getCursorY()==y)&&(effectsTileset!=null)) {
          Tile tmp = effectsTileset.getTile(getCursorTile());
          setNeedReDraw(i, j, true);
          if (tmp != null) {
            tmp.putFast(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT);
          }
        }
        if ((shade > 0) && (rePaint || needAlwaysRedraw[i][j])) {
          setNeedReDraw(i, j, true);
          Tile.drawShade(g, offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT, shade);
        }
        if (soundLookUp[i][j]!=-1) {
          currentEvent = getEventByIndex(soundLookUp[i][j]);
          if (currentEvent.getEventCommand() == Event.COMMAND_TYPE_PLAY_SOUND) {
            if (!SoundPlayer.isSoundPlaying(currentEvent.getEventSound())) {
              if (currentEvent.getParam(1).equalsIgnoreCase("Loop")) {
                SoundPlayer.playSoundBySoundName(currentEvent.getEventSound());
              }else if (currentEvent.getParam(1).equalsIgnoreCase("Day") && day) {
                SoundPlayer.playSoundBySoundName(currentEvent.getEventSound());
              }else if (currentEvent.getParam(1).equalsIgnoreCase("Night") && !day) {
                SoundPlayer.playSoundBySoundName(currentEvent.getEventSound());
              }else if (currentEvent.getParam(1).startsWith("Day#") && day) {
                String params[] = currentEvent.getParam(1).split("#");
                if (params.length == 2) {
                  try {
                    int maxAmount = Integer.valueOf(params[1]);
                    if (DiceGenerator.getRandom(maxAmount)==0) {
                      SoundPlayer.playSoundBySoundName(currentEvent.getEventSound());
                    }
                  } catch (NumberFormatException e) {
                    // Do nothing
                  }                
                }
              }else if (currentEvent.getParam(1).startsWith("Night#") && !day) {
                String params[] = currentEvent.getParam(1).split("#");
                if (params.length == 2) {
                  try {
                    int maxAmount = Integer.valueOf(params[1]);
                    if (DiceGenerator.getRandom(maxAmount)==0) {
                      SoundPlayer.playSoundBySoundName(currentEvent.getEventSound());
                    }
                  } catch (NumberFormatException e) {
                    // Do nothing
                  }                
                }
              } else {
                try {
                  int maxAmount = Integer.valueOf(currentEvent.getParam(1));
                  if (DiceGenerator.getRandom(maxAmount)==0) {
                    SoundPlayer.playSoundBySoundName(currentEvent.getEventSound());
                  }
                } catch (NumberFormatException e) {
                  // Do nothing
                }
              }
            }
          }
        }
        if (editorMode)  {
          int index = getEvent(x, y);
          if (index != -1) {
  /*          if (currentEvent.getType() == Event.TYPE_POINT) {
              g.setColor(Color.white);
            }
            if (currentEvent.getType() == Event.TYPE_REGION) {
              g.setColor(Color.red);
            }*/
            switch (currentEvent.getEventCommand()) {
            case Event.COMMAND_TYPE_NPC_TALK:
            case Event.COMMAND_TYPE_NPC_YELL:
            case Event.COMMAND_TYPE_PC_YELL:
            case Event.COMMAND_TYPE_HOLE_TO_DIG:
            case Event.COMMAND_TYPE_PC_TALK:g.setColor(Color.green); break;
            case Event.COMMAND_TYPE_QUICK_TRAVEL:
            case Event.COMMAND_TYPE_CONDITIONAL_TRAVEL:
            case Event.COMMAND_TYPE_CONDITIONAL_MAP_TRAVEL:
            case Event.COMMAND_TYPE_TRAVEL:g.setColor(Color.cyan); break;
            case Event.COMMAND_TYPE_CLOCK:
            case Event.COMMAND_TYPE_LOOK_INFO:
            case Event.COMMAND_TYPE_YESNO_QUESTION:
            case Event.COMMAND_TYPE_SIGN: g.setColor(Color.white); break;
            case Event.COMMAND_TYPE_LOCKED_DOOR: g.setColor(Color.red); break;
            case Event.COMMAND_TYPE_LOCKED_GATE: g.setColor(Color.red); break;
            case Event.COMMAND_TYPE_MODIFY_MAP: g.setColor(Color.yellow); break;
            case Event.COMMAND_TYPE_PLAY_SOUND: g.setColor(Color.yellow); break;
            case Event.COMMAND_TYPE_WAYPOINT: g.setColor(Color.blue); break;
            case Event.COMMAND_TYPE_ENCOUNTER: g.setColor(Color.magenta); break;
            }
            g.setFont(MapUtilities.smallSansSerif);
            g.drawString(String.valueOf(index), offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT+8);          
            g.drawString(currentEvent.getEventCommandAsString(), offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT+16);
            g.drawString(currentEvent.getEventName(), offsetX+i*Tile.MAX_WIDTH, offsetY+j*Tile.MAX_HEIGHT+24);
          }
        }
        if ((editorMode) && (cx == x) && (cy == y) ) {
          g.setColor(Color.white);
          g.setFont(MapUtilities.normalSansSerif);
          g.drawString("X", offsetX+i*Tile.MAX_WIDTH+16, offsetY+j*Tile.MAX_HEIGHT+16);
        }
        y++;
      }
      x++;
    }
    drawCompassAndPopup(screen, cx, cy, offsetX, offsetY, 250,g);
  }

  private boolean isToTileBlocked(TileInfo toTile, int mx, int my) {
    if (toTile != null) {
      if (toTile.isBlocked()) {
        return true;
      }
      if ((mx == -1) && (my == 0) && (toTile.isEastBlocked())) {
        return true;
      }
      if ((mx == 1) && (my == 0) && (toTile.isWestBlocked())) {
        return true;
      }
      if ((mx == 0) && (my == 1)&& (toTile.isNorthBlocked())) {
        return true;
      }
      if ((mx == 0) && (my == -1)&& (toTile.isSouthBlocked())) {
        return true;
      }
    }    
    return false;
  }
  
  private boolean isFromTileBlocked(TileInfo fromTile, int mx, int my) {
    if (fromTile != null) {
      if ((mx == -1) && (my == 0) && (fromTile.isWestBlocked())) {
        return true;
      }
      if ((mx == 1) && (my == 0) && (fromTile.isEastBlocked())) {
        return true;
      }
      if ((mx == 0) && (my == 1)&& (fromTile.isSouthBlocked())) {
        return true;
      }
      if ((mx == 0) && (my == -1)&& (fromTile.isNorthBlocked())) {
        return true;
      }
    }    
    return false;
  }
  
  /**
   * Is blocked or is party member blocking
   * Party must be initialized!!
   * @param fromX 
   * @param fromY
   * @param toX
   * @param toY
   * @return -1 if not blocked, 999 is for blocked, other number is for party member
   */
  public int isBlockOrPartyMember(int fromX, int fromY, int toX, int toY) {
    return isBlockOrPartyMember(fromX, fromY, toX, toY,false);
  }

  /**
   * Is blocked or is party member blocking
   * Party must be initialized!!
   * @param fromX 
   * @param fromY
   * @param toX
   * @param toY
   * @param player boolean, if true then add lock if combat and trying to travel
   * @return -1 if not blocked, 999 is for blocked, other number is for party member
   */
  public int isBlockOrPartyMember(int fromX, int fromY, int toX, int toY, boolean player) {
    int mx = toX-fromX;
    int my = toY-fromY;
    if ((toX>=0) && (toY>=0) && (toX<this.maxX) && (toY<this.maxY)) {
      int fromSector = getSectorByCoordinates(fromX, fromY);
      int toSector = getSectorByCoordinates(toX, toY);
      if (fromSector != toSector) {
        return BLOCKED;
      }
      TileInfo toTile = mapTileset.getTileInfo(walls[toX][toY]);
      if (isToTileBlocked(toTile, mx, my)) {
        return BLOCKED;
      }
      TileInfo fromTile = mapTileset.getTileInfo(walls[fromX][fromY]);
      if (isFromTileBlocked(fromTile, mx, my)) {
        return BLOCKED;
      }
      toTile = mapTileset.getTileInfo(objects[toX][toY]);
      if (isToTileBlocked(toTile, mx, my)) {
        return BLOCKED;
      }
      fromTile = mapTileset.getTileInfo(objects[fromX][fromY]);
      if (isFromTileBlocked(fromTile, mx, my)) {
        return BLOCKED;
      }
      toTile = mapTileset.getTileInfo(decorations[toX][toY]);
      if (isToTileBlocked(toTile, mx, my)) {
        return BLOCKED;
      }
      fromTile = mapTileset.getTileInfo(decorations[fromX][fromY]);
      if (isFromTileBlocked(fromTile, mx, my)) {
        return BLOCKED;
      }
      if (getCharacterByCoordinates(toX, toY) != -1) {
        return BLOCKED;
      }
      int index = getEvent(toX, toY);
      if (index != -1) {
        Event event = getEventByIndex(index);
        if (event.getEventCommand() == Event.COMMAND_TYPE_LOCKED_DOOR) {
          if (player) {
            party.addLogText("There is a locked door.");
            SoundPlayer.playSoundBySoundName("LOCKEDDOOR");
          }
          return BLOCKED;
        }
        if (event.getEventCommand() == Event.COMMAND_TYPE_LOCKED_GATE) {
          if (event.getLockDoorDifficulty() != 0) {
            if (player) {
              party.addLogText("There is a locked door.");
              SoundPlayer.playSoundBySoundName("LOCKEDDOOR");
            }
            return BLOCKED;
          }
          if (!player && isCoordinateOnScreen(toX, toY)) {
            SoundPlayer.playSoundBySoundName("LOCKEDDOOR");
            return BLOCKED;
          }
        }
        if ((event.getEventCommand() == Event.COMMAND_TYPE_TRAVEL) && (party.isCombat())) {
          if (player) {
            party.addLogText("Cannot travel while combat.");
          }
          return BLOCKED;
        }
        if ((event.getEventCommand() == Event.COMMAND_TYPE_TRAVEL) &&
            (party.getMode()==Party.MODE_SOLO_MODE) && (!event.getParam(0).isEmpty())) {
          if (player) {
            party.addLogText("Gather your party before venturing forth...");
          }
          return BLOCKED;
        }
        if ((event.getEventCommand() == Event.COMMAND_TYPE_QUICK_TRAVEL) && (party.isCombat())) {
          if (player) {
            party.addLogText("Cannot travel while combat.");
          }
          return BLOCKED;
        }
        if ((event.getEventCommand() == Event.COMMAND_TYPE_QUICK_TRAVEL) && 
            (party.getMode()==Party.MODE_SOLO_MODE) && (!event.getParam(0).isEmpty())){
          if (player) {
            party.addLogText("Gather your party before venturing forth...");
          }
          return BLOCKED;
        }
      }
      // If party member is not found then return -1
      return party.getPartyMemberByCoordinates(toX, toY);
    } else {
      return BLOCKED;
    }
    
  }

  /**
   * Is coordinates on last drawn screen
   * @param x
   * @param y
   * @return boolean
   */
  private boolean isCoordinateOnScreen(int x, int y) {
    if (x >= lastDrawnX-VIEW_X_RADIUS && x <= lastDrawnX+VIEW_X_RADIUS &&
        y >= lastDrawnY-VIEW_Y_RADIUS && y <= lastDrawnY+VIEW_Y_RADIUS) {
      return true;
    }
    return false;
  }
  
  /**
   * Is Move allowed
   * @param fromX
   * @param fromY
   * @param toX
   * @param toY
   * @return false if move is allowed, otherwise true
   */
  public boolean isBlocked(int fromX, int fromY, int toX,int toY) {
    if (isBlockOrPartyMember(fromX, fromY, toX, toY)==-1) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Is target tile blocked? Used only when traveling/encounters
   * @param toX
   * @param toY
   * @return boolean
   */
  public boolean isTargetBlocked(int toX, int toY) {
    if (isTargetBlockedByMap(toX, toY)) {
      return true;
    }
    // If party member is not found then return -1
   if (party.getPartyMemberByCoordinates(toX, toY)!=-1) {
     return true;
   }   
   if (getCharacterByCoordinates(toX, toY) != -1) {
     return true;
   }
   return false;
    
  }
  
  public int[][] getBlockMapSurround(int x, int y) {
    int[][] result = new int[5][5];
    for (int i=-2;i<3;i++) {
      for (int j=-2;j<3;j++) {
        if (isTargetBlockedByMap(x+i, y+j)) {
          result[i+2][j+2] = 1;
        } else {
          result[i+2][j+2] = 0;
        }
      }
    }
    return result;
  }

  public int[][] getBlockMapSurround(int x, int y,int size) {
    int center=2;
    switch (size) {
    case 5: center =2; break;
    case 7: center =3; break;
    case 9: center =4; break;
    case 11: center =5; break;
    default: size=5; center=2;break;
    }
    int[][] result = new int[size][size];
    for (int i=-center;i<center+1;i++) {
      for (int j=-center;j<center+1;j++) {
        if (isTargetBlockedByMap(x+i, y+j)) {
          result[i+center][j+center] = 1;
        } else {
          result[i+center][j+center] = 0;
        }
      }
    }
    return result;
  }

  /**
   * Is target tile blocked?
   * @param toX
   * @param toY
   * @return boolean
   */
  private boolean isTargetBlockedByMap(int toX, int toY) {    
    if ((toX>=0) && (toY>=0) && (toX<this.maxX) && (toY<this.maxY)) {
      TileInfo toTile = mapTileset.getTileInfo(walls[toX][toY]);
      if (toTile.isBlocked()) {
        return true;
      }
      toTile = mapTileset.getTileInfo(objects[toX][toY]);
      if ((toTile !=null)&&(toTile.isBlocked())) {
        return true;
      }
      toTile = mapTileset.getTileInfo(decorations[toX][toY]);
      if ((toTile !=null)&&(toTile.isBlocked())) {
        return true;
      }
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Is movement possible from(X,Y) to to(X,y)
   * @param fromX
   * @param fromY
   * @param toX
   * @param toY
   * @return boolean
   */
  private boolean isTargetBlockedByMap(int fromX,int fromY,int toX,int toY) {
    if ((toX>=0) && (toY>=0) && (toX<this.maxX) && (toY<this.maxY) &&
        (fromX>=0) && (fromY>=0) && (fromX<this.maxX) && (fromY<this.maxY)) {
      TileInfo toTile = mapTileset.getTileInfo(walls[toX][toY]);
      TileInfo fromTile = mapTileset.getTileInfo(walls[fromX][fromY]);
      int mx = toX-fromX;
      int my = toY-fromY;
      if (toTile.isBlocked()) {
        return true;
      }
      if (isToTileBlocked(toTile, mx, my)) {
        return true;
      }
      if (isFromTileBlocked(fromTile, mx, my)) {
        return true;
      }
      toTile = mapTileset.getTileInfo(objects[toX][toY]);
      fromTile = mapTileset.getTileInfo(objects[fromX][fromY]);
      if ((toTile !=null)&&(toTile.isBlocked())) {
        return true;
      }
      if (isToTileBlocked(toTile, mx, my)) {
        return true;
      }
      if (isFromTileBlocked(fromTile, mx, my)) {
        return true;
      }
      toTile = mapTileset.getTileInfo(decorations[toX][toY]);
      fromTile = mapTileset.getTileInfo(decorations[fromX][fromY]);
      if ((toTile !=null)&&(toTile.isBlocked())) {
        return true;
      }
      if (isToTileBlocked(toTile, mx, my)) {
        return true;
      }
      if (isFromTileBlocked(fromTile, mx, my)) {
        return true;
      }
      return false;
    } else {
      return true;
    }
    
  }
  
  /**
   * Check if there is clear line between two points
   * @param fromX
   * @param fromY
   * @param toX
   * @param toY
   * @return boolean returns true if clear line
   */
  public boolean isClearShot(int fromX, int fromY, int toX, int toY) {
    int mx = Math.abs(fromX-toX);
    int my = Math.abs(fromY-toY);
    int len = Math.max(mx, my);
    boolean result = true;
    if (len > 0) {
      double dx = (toX-fromX)/(double)len;
      double dy = (toY-fromY)/(double)len;
      double x = fromX;
      double y = fromY;
      for (int i=0;i<len;i++) {
        int oldX = (int) Math.round(x);
        int oldY = (int) Math.round(y);
        x=x+dx;
        y=y+dy;
        if (isTargetBlockedByMap(oldX,oldY,(int)Math.round(x), (int) Math.round(y))) {
          result = false;
          break;
        }
      }
    } else {
      result = true;
    }
    return result;
  }
  
  /**
   * Put tile to map by using default place
   * @param x
   * @param y
   * @param tileNumber
   */
  public void setMap(int x,int y, int tileNumber) {
    if (tileNumber == -1) {
      objects[x][y] = -1;
      decorations[x][y] = -1;
      top[x][y] = -1;
    } else
    if (tileNumber < mapTileset.size()) {
      if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
        TileInfo info = mapTileset.getTileInfo(tileNumber);
        if (info != null) {
          byte place = info.getDefaultPlace();
          if (place == TileInfo.TILE_PLACE_WALL_OR_FLOOR) {
            walls[x][y] = tileNumber;
          }
          if (place == TileInfo.TILE_PLACE_OBJECT) {
            objects[x][y] = tileNumber;
          }
          if (place == TileInfo.TILE_PLACE_DECORATION) {
            decorations[x][y] = tileNumber;
          }
          if (place == TileInfo.TILE_PLACE_TOP) {
            top[x][y] = tileNumber;
          }
        }
      }
    }
  }
  
  public void setMap(int x, int y, int tileNumber, byte place) {
    if ((tileNumber == -1) && (place == TileInfo.TILE_PLACE_WALL_OR_FLOOR)) {
      objects[x][y] = -1;
      decorations[x][y] = -1;
      top[x][y] = -1;
    } else
    if (tileNumber < mapTileset.size()) {
      if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
          if (place == TileInfo.TILE_PLACE_WALL_OR_FLOOR) {
            walls[x][y] = tileNumber;
          }
          if (place == TileInfo.TILE_PLACE_OBJECT) {
            objects[x][y] = tileNumber;
          }
          if (place == TileInfo.TILE_PLACE_DECORATION) {
            decorations[x][y] = tileNumber;
          }
          if (place == TileInfo.TILE_PLACE_TOP) {
            top[x][y] = tileNumber;
          }
      }
    }
  }
  
  /**
   * getFloor tile index
   * @param x
   * @param y
   * @return int, -1 if tile is missing
   */
  public int getFloor(int x,int y) {
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
      return walls[x][y];
    }
    return -1;
  }

  /**
   * get Object tile index
   * @param x
   * @param y
   * @return int, -1 if tile is missing
   */
  public int getObjects(int x,int y) {
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
      return objects[x][y];
    }
    return -1;
  }
  
  /**
   * get decorations tile index
   * @param x
   * @param y
   * @return int, -1 if tile is missing
   */
  public int getDecoration(int x,int y) {
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
      return decorations[x][y];
    }
    return -1;
  }
  
  /**
   * get top tile index
   * @param x
   * @param y
   * @return int, -1 if tile is missing
   */
  public int getTop(int x,int y) {
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
      return top[x][y];
    }
    return -1;
  }

  /**
   * Get light info for night
   * @param input
   * @return 
   */
  private int getNightLight(short input) {
    int tmp = input & 0xff00;
    tmp = tmp >> 8;
    return tmp;
  }
  /**
   * Get light info for day
   * @param input
   * @return
   */
  private int getDayLight(short input) {
    int tmp = input & 0x00ff;
    return tmp;
  }

  /**
   * Get light shade from light info
   * @param input
   * @return
   */
  private int getLightShade(int input) {
    int tmp = input & 0x0f;
    return tmp;
  }
  /**
   * Get light special from light info
   * @param input
   * @return
   */
  private int getLightSpecial(int input) {
    int tmp = input & 0xf0;
    tmp = tmp >> 4;
    return tmp;
  }

  /**
   * Set light shade
   * @param x
   * @param y
   * @param day, true is day, false is night
   * @param shade 0-7
   */
  public void setLightShade(int x,int y, boolean day, int shade) {
    short mask;
    short shadeIn = (short) shade;
    if ((shadeIn >= 0) && (shadeIn < 8)) {
      if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
        if (day) {
          mask = 0x000f;
          mask = (short) (mask & shadeIn);
          light[x][y] = (short) (light[x][y] & 0xfff0); 
          light[x][y] = (short) (light[x][y] | mask);
        } else {
          mask = (short) 0x0f00;
          shadeIn = (short) (shadeIn << 8);
          mask = (short) (mask & shadeIn);
          light[x][y] = (short) (light[x][y] & 0xf0ff); 
          light[x][y] = (short) (light[x][y] | mask);
        }
      }
    }
  }
  
  /**
   * setLight special effect for map
   * @param x
   * @param y
   * @param day true is day, false is night
   * @param special, 0 no special effect, 1 pulsating, 2 pulsating fast, 3 random,
   * 4 random with limits, 5 strobo
   */
  public void setLightSpecial(int x,int y, boolean day, int special) {
    short mask;
    short specialIn = (short) special;
    if ((specialIn >= 0) && (specialIn < 8)) {
      if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
        if (day) {
          mask = 0x00f0;
          specialIn = (short) (specialIn << 4);
          mask = (short) (mask & specialIn);
          short tmp = light[x][y];
          tmp = (short) (tmp & 0xff0f); 
          tmp = (short) (tmp | mask);
          light[x][y] = (short) (light[x][y] & 0xff0f); 
          light[x][y] = (short) (light[x][y] | mask);
        } else {
          mask = (short) 0xf000;
          specialIn = (short) (specialIn << 12);
          mask = (short) (mask & specialIn);
          short tmp = light[x][y];
          tmp = (short) (tmp & 0x0fff); 
          tmp = (short) (tmp | mask);
          light[x][y] = (short) (light[x][y] & 0x0fff); 
          light[x][y] = (short) (light[x][y] | mask);
        }
      }
    }
  }
    
  /**
   * Get light shade
   * @param x
   * @param y
   * @param day
   * @return int 0-7
   */
  public int getShade(int x,int y,boolean day) {
    int result = 0;
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
      if (day) {
        result =getLightShade(getDayLight(light[x][y]));
      } else {
        result =getLightShade(getNightLight(light[x][y]));
      }
    }
    return result;
  }
  /**
   * Get lighting special effect
   * @param x
   * @param y
   * @param day
   * @return 0 no special effect, 1 pulsating, 2 pulsating fast, 3 random,
   * 4 random with limits, 5 strobo
   */
  public int getSpecial(int x,int y,boolean day) {
    int result = 0;
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
      if (day) {
        result =getLightSpecial(getDayLight(light[x][y]));
      } else {
        result =getLightSpecial(getNightLight(light[x][y]));
      }
    }
    return result;
  }
  
  /**
   * Get light shade
   * @param x
   * @param y
   * @param day
   * @return int 0-7
   */
  public int getEffectiveShade(int x,int y,boolean day) {
    int shade = 0;
    int special = 0;
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
        shade = getShade(x, y, day);
        special = getSpecial(x, y, day);
        switch (special) {
        case 0: break; // Do nothing
        case 1: { // Pulsating
                 shade = pulsatingShade;
                break;}
        case 2: { // Pulsating fast
          shade = pulsatingShade;
         break;}
        case 3: { // Random
          shade=DiceGenerator.getRandom(8);
         break;}
        case 4: { // Random with limits
          if (shade != 0) {
            shade=DiceGenerator.getRandom(shade,7);
          }
         break;}
        case 5: { // strobo     
          if (shade != 0) {
            shade = 0;
            setLightShade(x, y, day, shade);
          } else {
            shade = 7;
            setLightShade(x, y, day, shade);
          }
         break;}        
        }
        
        
    }
    return shade;
  }
  
  public final static int DIRECTION_ATTACK = -2;
  public final static int DIRECTION_NONE = AIPath.DIRECTION_NONE;
  private final static int DIRECTION_UP       =AIPath.DIRECTION_UP;
  private final static int DIRECTION_UPRIGHT  =AIPath.DIRECTION_UPRIGHT;
  private final static int DIRECTION_RIGHT    =AIPath.DIRECTION_RIGHT;
  private final static int DIRECTION_DOWNRIGHT=AIPath.DIRECTION_DOWNRIGHT;
  private final static int DIRECTION_DOWN     =AIPath.DIRECTION_DOWN;
  private final static int DIRECTION_DOWNLEFT =AIPath.DIRECTION_DOWNLEFT;
  private final static int DIRECTION_LEFT     =AIPath.DIRECTION_LEFT;
  private final static int DIRECTION_UPLEFT   =AIPath.DIRECTION_UPLEFT;
  /**
   * Check if nearby tile belongs to same series
   * @param x
   * @param y
   * @param dir, 0 up, 1 up/right, 2 right, 3 down/right, 4 down, 5 down/left
   * 6 left, 7 up/left
   * @return true is tile belongs same series
   */
  private boolean checkDirectionSameSeries(int x, int y, int dir, byte position) {
    boolean result = false;
    int series=-2;
    int target=-1;
    if (position == TileInfo.TILE_PLACE_WALL_OR_FLOOR) {
      series = mapTileset.getSeries(walls[x][y]);
    }
    if (position == TileInfo.TILE_PLACE_OBJECT) {
      series = mapTileset.getSeries(objects[x][y]);
    }
    if (position == TileInfo.TILE_PLACE_DECORATION) {
      series = mapTileset.getSeries(decorations[x][y]);
    }
    if (position == TileInfo.TILE_PLACE_TOP) {
      series = mapTileset.getSeries(top[x][y]);
    }
    // Do the direction
    switch (dir) {
    case 0: y = y -1; break;
    case 1: y = y -1; x=x+1; break;
    case 2: x = x +1; break;
    case 3: x = x +1; y=y+1; break;
    case 4: y = y +1; break;
    case 5: y = y +1; x=x-1; break;
    case 6: x = x -1; break;
    case 7: y = y -1; x=x-1; break;
    }
    // Check that point is still in map
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
      if (position == TileInfo.TILE_PLACE_WALL_OR_FLOOR) {
        target = mapTileset.getSeries(walls[x][y]);
      }
      if (position == TileInfo.TILE_PLACE_OBJECT) {
        target = mapTileset.getSeries(objects[x][y]);
      }
      if (position == TileInfo.TILE_PLACE_DECORATION) {
        target = mapTileset.getSeries(decorations[x][y]);
      }
      if (position == TileInfo.TILE_PLACE_TOP) {
        target = mapTileset.getSeries(top[x][y]);
      }
      if ((target == series) && (target != -1)) {
        result = true;
      }
    }
    return result;
  }
  
  private void smoothTile(int x, int y,byte position) {
    int index=-1;
    int series=-1;
    int seriesBasicType=-1;
    if ((x > -1) && (y > -1) && (x < maxX) && (y < maxY)) {
      if (position == TileInfo.TILE_PLACE_WALL_OR_FLOOR) {
        series=mapTileset.getSeries(walls[x][y]);
        seriesBasicType=mapTileset.getSeriesBasicType(walls[x][y]);
      } else
      if (position == TileInfo.TILE_PLACE_OBJECT) {
        series=mapTileset.getSeries(objects[x][y]);
        seriesBasicType=mapTileset.getSeriesBasicType(objects[x][y]);
      } else
      if (position == TileInfo.TILE_PLACE_DECORATION) {
        series=mapTileset.getSeries(decorations[x][y]);
        seriesBasicType=mapTileset.getSeriesBasicType(decorations[x][y]);
      } else
      if (position == TileInfo.TILE_PLACE_TOP) {
        series=mapTileset.getSeries(top[x][y]);
        seriesBasicType=mapTileset.getSeriesBasicType(top[x][y]);
      }
        
      if (seriesBasicType == TileInfo.TILE_SERIES_BASIC_TYPE_BASE) {
        
        if ( (checkDirectionSameSeries(x, y, DIRECTION_UP, position)) &&
             (checkDirectionSameSeries(x, y, DIRECTION_LEFT, position)) &&
             (checkDirectionSameSeries(x, y, DIRECTION_DOWN, position)) &&
             (checkDirectionSameSeries(x, y, DIRECTION_RIGHT, position))) {
               index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_CENTER);
               if (!checkDirectionSameSeries(x, y, DIRECTION_UPRIGHT, position)) {
                 index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_INNER_CORNER_NE);
               }
               if (!checkDirectionSameSeries(x, y, DIRECTION_UPLEFT, position)) {
                 index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_INNER_CORNER_NW);
               }
               if (!checkDirectionSameSeries(x, y, DIRECTION_DOWNLEFT, position)) {
                 index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_INNER_CORNER_SW);
               }
               if (!checkDirectionSameSeries(x, y, DIRECTION_DOWNRIGHT, position)) {
                 index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_INNER_CORNER_SE);
               }
             } else
        if ( (checkDirectionSameSeries(x, y, DIRECTION_UP, position)) &&
            (checkDirectionSameSeries(x, y, DIRECTION_DOWN, position))) {
              if (checkDirectionSameSeries(x, y, DIRECTION_RIGHT, position)) {
                index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_WEST);
              }
              if (checkDirectionSameSeries(x, y, DIRECTION_LEFT, position)) {
                index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_EAST);
              }
            } else
        if ( (checkDirectionSameSeries(x, y, DIRECTION_LEFT, position)) &&
            (checkDirectionSameSeries(x, y, DIRECTION_RIGHT, position))) {
              if (checkDirectionSameSeries(x, y, DIRECTION_UP, position)) {
                index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_SOUTH);
              }
              if (checkDirectionSameSeries(x, y, DIRECTION_DOWN, position)) {
                index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_NORTH);
              }
            } else
        if ( checkDirectionSameSeries(x, y, DIRECTION_UP, position) &&
            checkDirectionSameSeries(x, y, DIRECTION_RIGHT, position) &&
            checkDirectionSameSeries(x, y, DIRECTION_UPRIGHT, position)) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_CORNER_SW);
            } else
        if ( checkDirectionSameSeries(x, y, DIRECTION_UP, position) &&
            checkDirectionSameSeries(x, y, DIRECTION_LEFT, position)&&
            checkDirectionSameSeries(x, y, DIRECTION_UPLEFT, position)) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_CORNER_SE);
            } else
        if ( checkDirectionSameSeries(x, y, DIRECTION_DOWN, position) &&
            checkDirectionSameSeries(x, y, DIRECTION_LEFT, position) &&
            checkDirectionSameSeries(x, y, DIRECTION_DOWNLEFT, position)) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_CORNER_NE);
            } else
        if ( checkDirectionSameSeries(x, y, DIRECTION_DOWN, position) &&
            checkDirectionSameSeries(x, y, DIRECTION_RIGHT, position) &&
            checkDirectionSameSeries(x, y, DIRECTION_DOWNRIGHT, position)) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_CORNER_NW);
            }
        if (index != -1) {
          setMap(x, y, index);
        }
      } else // END OF BASIC_TYPE_BASE
      if (seriesBasicType == TileInfo.TILE_SERIES_BASIC_TYPE_WALL) {
        if ((checkDirectionSameSeries(x, y, DIRECTION_LEFT, position)) &&
            (checkDirectionSameSeries(x, y, DIRECTION_DOWN, position)) &&
            (checkDirectionSameSeries(x, y, DIRECTION_RIGHT, position))) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_WALL_CENTER_TOP);
            } else
        if ((checkDirectionSameSeries(x, y, DIRECTION_LEFT, position)) &&
            (!checkDirectionSameSeries(x, y, DIRECTION_DOWN, position)) &&
            (checkDirectionSameSeries(x, y, DIRECTION_RIGHT, position))) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_WALL_CENTER_BOTTOM);
            } else
        if ((!checkDirectionSameSeries(x, y, DIRECTION_LEFT, position)) &&
            (checkDirectionSameSeries(x, y, DIRECTION_DOWN, position))) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_WALL_WEST_TOP);
            } else
        if ((!checkDirectionSameSeries(x, y, DIRECTION_LEFT, position)) &&
            (!checkDirectionSameSeries(x, y, DIRECTION_DOWN, position))) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_WALL_WEST_BOTTOM);
            } else
        if ((!checkDirectionSameSeries(x, y, DIRECTION_RIGHT, position)) &&
            (checkDirectionSameSeries(x, y, DIRECTION_DOWN, position))) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_WALL_EAST_TOP);
            } else
        if ((!checkDirectionSameSeries(x, y, DIRECTION_RIGHT, position)) &&
            (!checkDirectionSameSeries(x, y, DIRECTION_DOWN, position))) {
              index = mapTileset.getSeriesType(series, TileInfo.TILE_SERIES_TYPE_WALL_EAST_BOTTOM);
            }
        if (index != -1) {
          setMap(x, y, index);
        }
      }
    }
  }
  
  /**
   * Do smoothing for whole map
   */
  public void smoothMap(int vx,int vy) {
    if (vx < VIEW_X_RADIUS) { vx = VIEW_X_RADIUS; }
    if (vy < VIEW_Y_RADIUS) { vy = VIEW_Y_RADIUS;}
    if (vx > maxX-VIEW_X_RADIUS-1) {vx = maxX-VIEW_X_RADIUS-1;}
    if (vy > maxY-VIEW_Y_RADIUS-1) {vy = maxX-VIEW_Y_RADIUS-1;}
    int x = vx-VIEW_X_RADIUS;
    int y = vy-VIEW_Y_RADIUS;
    for (int j=0;j<VIEW_Y_RADIUS*2+1;j++) {
      x = vx-VIEW_X_RADIUS;
        for (int i=0;i<VIEW_X_RADIUS*2+1;i++) {
        smoothTile(x+i,y+j,TileInfo.TILE_PLACE_WALL_OR_FLOOR);
        smoothTile(x+i,y+j,TileInfo.TILE_PLACE_OBJECT);
        smoothTile(x+i,y+j,TileInfo.TILE_PLACE_DECORATION);
        smoothTile(x+i,y+j,TileInfo.TILE_PLACE_TOP);
      }
    }
  }
  
  /**
   * Set amount of map sectors
   * 1: One single map
   * 2: Two sector divided by half of X coordinate
   * 4: Four sectors each quarter is own sector
   * @param i, valid values are 1,2,4
   */
  public void setMapSectorsNumber(int i) {
    if ((i==1) || (i==2) || (i==4)) {
      setMapSectors(i);
    }
  }
  
  /**
   * Do map animation
   */
  public void doAnim() {
    animationSpeed++;
    if ((isCursorMode()) && (effectsTileset!=null)) {
      TileInfo tmp = effectsTileset.getTileInfo(getCursorTile());
      if (tmp != null) {
        if (tmp.getNextAnimTile() != getCursorTile()) {
          if (animationSpeed%tmp.getAnimSpeed()==0) {
            setCursorTile(tmp.getNextAnimTile());
          }
        }
      }
      
    }
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        TileInfo tmp = mapTileset.getTileInfo(walls[i][j]);
        if (tmp != null) {
          if (tmp.getNextAnimTile() != walls[i][j]) {
            if (animationSpeed%tmp.getAnimSpeed()==0) {
              walls[i][j] = tmp.getNextAnimTile();
            }
          }
        }
        tmp = mapTileset.getTileInfo(objects[i][j]);
        if (tmp != null) {
          if (tmp.getNextAnimTile() != objects[i][j]) {
            if (animationSpeed%tmp.getAnimSpeed()==0) {
              objects[i][j] = tmp.getNextAnimTile();
            }
          }
        }
        tmp = mapTileset.getTileInfo(decorations[i][j]);
        if (tmp != null) {
          if (tmp.getNextAnimTile() != decorations[i][j]) {
            if (animationSpeed%tmp.getAnimSpeed()==0) {
              decorations[i][j] = tmp.getNextAnimTile();
            }
          }
        }
        tmp = mapTileset.getTileInfo(top[i][j]);
        if (tmp != null) {
          if (tmp.getNextAnimTile() != top[i][j]) {
            if (animationSpeed%tmp.getAnimSpeed()==0) {
              top[i][j] = tmp.getNextAnimTile();
            }
          }
        }
        if (effectsTileset != null) {
          tmp = effectsTileset.getTileInfo(graphEffects[i][j]);
          if (tmp != null) {
            if (tmp.getNextAnimTile() != graphEffects[i][j]) {
              if (animationSpeed%tmp.getAnimSpeed()==0) {
                graphEffects[i][j] = tmp.getNextAnimTile();
                if (graphEffects[i][j]==0) {
                  graphEffects[i][j]=-1;
                }
              }
            }
          }
        }
        if (animationSpeed == TileInfo.ANIM_SPEED_SLOW) {
          animationSpeed = 0;
        }
        
      }
    }
  }

  /**
   * Make light value lighter
   * @param x
   * @param y
   * @param lux, how much lighter
   * @param special, 0 no special effect, 1 pulsating, 2 pulsating fast, 3 random,
   * 4 random with limits, 5 strobo
   */
  private void makeBrighter(int x, int y, int lux, int special) {
    int shade;
    int spe;
    if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
      shade = getShade(x, y, true);
      spe = getSpecial(x, y, true);
      if ((spe == 0) && (special != 0)) {
        spe = special;
      }
      shade = shade -lux;
      if (shade < 0) { 
        shade = 0; 
        // If special is random with limit put that also zero
        if (spe == 4) {spe = 0;}
      }
      setLightShade(x, y, true, shade);
      setLightSpecial(x, y, true, spe);
      shade = getShade(x, y, false);
      spe = getSpecial(x, y, false);
      if ((spe == 0) && (special != 0)) {
        spe = special;
      }
      shade = shade -lux;
      if (shade < 0) { 
        shade = 0; 
        // If special is random with limit put that also zero
        if (spe == 4) {spe = 0;}
      }
      setLightShade(x, y, false, shade);
      setLightSpecial(x, y, false, spe);
    }
  }
  
  /**
   * Create light effect using radius calculation
   * @param x coordinate
   * @param y coordinate
   * @param radius Light radius this is the farthest radius where light will be
   * @param lux how effective light is
   * @param special 0 no special effect, 1 pulsating, 2 pulsating fast, 3 random,
   * 4 random with limits, 5 strobo
   * @param brightCenter Is there a bright center where is no special effect
   */
  private void createLightEffectRadius(int x, int y, int radius, int lux,
      int special, boolean brightCenter) {
    for (int i=-radius;i<=radius;i++) {
      for (int j=-radius;j<=radius;j++) {
        int distance = (i*i)+(j*j);
        distance = (int) Math.round(Math.sqrt(distance));
        int tmpLux = lux-distance/2;
        if (tmpLux < 1) {
          tmpLux =1;
        }
        if (distance < radius) {
          if (brightCenter) {
            if (distance > radius/2) {
              makeBrighter(x+i, y+j, tmpLux, special);
            } else {
              makeBrighter(x+i, y+j, tmpLux, 0);
            }
          } else {
            makeBrighter(x+i, y+j, tmpLux, special);
          }
        }
      }
    }
  }

  /**
   * Checks if coordinates are inside array
   * @param x
   * @param y
   * @param array
   * @return boolean
   */
  private boolean isInsideArray(int x,int y,int[][] array) {
    boolean result = false;
    if ((x >=0) && (y >= 0) && (x < array.length) && (y < array[x].length)) {
      result = true;
    }      
    return result;
  }
  
  /**
   * Sets up lightLookUp table for slightly darker area
   * @param x center point of darkness
   * @param y center point of darkness
   * @param lightLookUp Lookup Table
   */
  private void makeDimDarkness(int x,int y,int[][] lightLookUp) {
    modifyLightLookUp(x, y, lightLookUp, 3);
    modifyLightLookUp(x-1,y,lightLookUp,3);
    modifyLightLookUp(x+1,y,lightLookUp, 3);
    modifyLightLookUp(x,y-1,lightLookUp, 3);
    modifyLightLookUp(x,y+1, lightLookUp, 3);
    modifyLightLookUp(x-1,y-1, lightLookUp, 2);
    modifyLightLookUp(x+1,y-1, lightLookUp, 2);
    modifyLightLookUp(x-1,y+1, lightLookUp, 2);
    modifyLightLookUp(x+1,y+1, lightLookUp, 2);
    modifyLightLookUp(x-2,y, lightLookUp, 2);
    modifyLightLookUp(x+2,y, lightLookUp, 2);
    modifyLightLookUp(x,y-2, lightLookUp, 2);
    modifyLightLookUp(x,y+2, lightLookUp, 2);

  }
  
  /**
   * Sets up lightLookUp table for dim light
   * @param x center point of light
   * @param y center point of light
   * @param lightLookUp Lookup Table
   */
  private void makeDimLight(int x,int y,int[][] lightLookUp) {
    modifyLightLookUp(x, y, lightLookUp, -2);
    modifyLightLookUp(x-1,y,lightLookUp,-2);
    modifyLightLookUp(x+1,y,lightLookUp, -2);
    modifyLightLookUp(x,y-1,lightLookUp, -2);
    modifyLightLookUp(x,y+1, lightLookUp, -2);
    modifyLightLookUp(x-1,y-1, lightLookUp, -1);
    modifyLightLookUp(x+1,y-1, lightLookUp, -1);
    modifyLightLookUp(x-1,y+1, lightLookUp, -1);
    modifyLightLookUp(x+1,y+1, lightLookUp, -1);
    modifyLightLookUp(x-2,y, lightLookUp, -1);
    modifyLightLookUp(x+2,y, lightLookUp, -1);
    modifyLightLookUp(x,y-2, lightLookUp, -1);
    modifyLightLookUp(x,y+2, lightLookUp, -1);
  }

  /**
   * Modify lightLookup array and make sure point is actually inside array
   * @param x
   * @param y
   * @param lightLookup
   * @param value
   */
  private void modifyLightLookUp(int x, int y, int[][] lightLookUp, int value) {
    if (isInsideArray(x, y, lightLookUp)) {
      lightLookUp[x][y] = value;
    }
  }
  
  /**
   * Create light area which is flickering like a torch
   * @param x Center of light
   * @param y Center of light
   * @param lightLookUp Lookuptable
   */
  private void makeTorchLight(int x,int y,int[][] lightLookUp) {
    modifyLightLookUp(x, y, lightLookUp, -4);
    modifyLightLookUp(x-1,y,lightLookUp,-3);
    modifyLightLookUp(x+1,y,lightLookUp, -3);
    modifyLightLookUp(x,y-1,lightLookUp, -3);
    modifyLightLookUp(x,y+1, lightLookUp, -3);
    modifyLightLookUp(x-1,y-1, lightLookUp, -DiceGenerator.getRandom(2, 3));
    modifyLightLookUp(x+1,y-1, lightLookUp, -DiceGenerator.getRandom(2, 3));
    modifyLightLookUp(x-1,y+1, lightLookUp, -DiceGenerator.getRandom(2, 3));
    modifyLightLookUp(x+1,y+1, lightLookUp, -DiceGenerator.getRandom(2, 3));
    modifyLightLookUp(x-2,y, lightLookUp, -DiceGenerator.getRandom(2, 3));
    modifyLightLookUp(x+2,y, lightLookUp, -DiceGenerator.getRandom(2, 3));
    modifyLightLookUp(x,y-2, lightLookUp, -DiceGenerator.getRandom(2, 3));
    modifyLightLookUp(x,y+2, lightLookUp, -DiceGenerator.getRandom(2, 3));
    
    modifyLightLookUp(x-1,y-2, lightLookUp,-DiceGenerator.getRandom(1, 3));
    modifyLightLookUp(x+1,y-2, lightLookUp,-DiceGenerator.getRandom(1, 3));
    modifyLightLookUp(x+1,y+2, lightLookUp,-DiceGenerator.getRandom(1, 3));
    modifyLightLookUp(x-1,y+2, lightLookUp,-DiceGenerator.getRandom(1, 3));
    modifyLightLookUp(x-2,y-1, lightLookUp,-DiceGenerator.getRandom(1, 3));
    modifyLightLookUp(x+2,y-1, lightLookUp,-DiceGenerator.getRandom(1, 3));
    modifyLightLookUp(x+2,y+1, lightLookUp,-DiceGenerator.getRandom(1, 3));
    modifyLightLookUp(x-2,y+1, lightLookUp,-DiceGenerator.getRandom(1, 3));
  }
  /**
   * Create bright light area 
   * @param x Center of light
   * @param y Center of light
   * @param lightLookUp Lookuptable
   */
  private void makeBrightLight(int x,int y,int[][] lightLookUp) {
    modifyLightLookUp(x, y, lightLookUp, -5);
    modifyLightLookUp(x-1,y,lightLookUp,-4);
    modifyLightLookUp(x+1,y,lightLookUp, -4);
    modifyLightLookUp(x,y-1,lightLookUp, -4);
    modifyLightLookUp(x,y+1, lightLookUp, -4);
    modifyLightLookUp(x-1,y-1, lightLookUp, -4);
    modifyLightLookUp(x+1,y-1, lightLookUp, -4);
    modifyLightLookUp(x-1,y+1, lightLookUp, -4);
    modifyLightLookUp(x+1,y+1, lightLookUp, -4);
    modifyLightLookUp(x-2,y, lightLookUp, -3);
    modifyLightLookUp(x+2,y, lightLookUp, -3);
    modifyLightLookUp(x,y-2, lightLookUp, -3);
    modifyLightLookUp(x,y+2, lightLookUp, -3);
    
    modifyLightLookUp(x-1,y-2, lightLookUp,-2);
    modifyLightLookUp(x+1,y-2, lightLookUp,-2);
    modifyLightLookUp(x+1,y+2, lightLookUp,-2);
    modifyLightLookUp(x-1,y+2, lightLookUp,-2);
    modifyLightLookUp(x-2,y-1, lightLookUp,-2);
    modifyLightLookUp(x+2,y-1, lightLookUp,-2);
    modifyLightLookUp(x+2,y+1, lightLookUp,-2);
    modifyLightLookUp(x-2,y+1, lightLookUp,-2);
  }
  
  /**
   * Sets up lightLookUp table for dark area
   * @param x center point of darkness
   * @param y center point of darkness
   * @param lightLookUp Lookup Table
   */
  private void makeDarkness(int x,int y,int[][] lightLookUp) {
    modifyLightLookUp(x, y, lightLookUp, 5);
    modifyLightLookUp(x-1,y,lightLookUp,5);
    modifyLightLookUp(x+1,y,lightLookUp, 5);
    modifyLightLookUp(x,y-1,lightLookUp, 5);
    modifyLightLookUp(x,y+1, lightLookUp, 5);
    modifyLightLookUp(x-1,y-1, lightLookUp, 4);
    modifyLightLookUp(x+1,y-1, lightLookUp, 4);
    modifyLightLookUp(x-1,y+1, lightLookUp, 4);
    modifyLightLookUp(x+1,y+1, lightLookUp, 4);
    modifyLightLookUp(x-2,y, lightLookUp, 4);
    modifyLightLookUp(x+2,y, lightLookUp, 4);
    modifyLightLookUp(x,y-2, lightLookUp, 4);
    modifyLightLookUp(x,y+2, lightLookUp, 4);
    
    modifyLightLookUp(x-1,y-2, lightLookUp,3);
    modifyLightLookUp(x+1,y-2, lightLookUp,3);
    modifyLightLookUp(x+1,y+2, lightLookUp,3);
    modifyLightLookUp(x-1,y+2, lightLookUp,3);
    modifyLightLookUp(x-2,y-1, lightLookUp,3);
    modifyLightLookUp(x+2,y-1, lightLookUp,3);
    modifyLightLookUp(x+2,y+1, lightLookUp,3);
    modifyLightLookUp(x-2,y+1, lightLookUp,3);
  }
  
  /* These are not used any more, use above*/
  /*private void createLightEffectDim(int x, int y) {
    makeBrighter(x, y, 4, 0);
    makeBrighter(x-1, y, 4, 0);
    makeBrighter(x, y-1, 4, 0);
    makeBrighter(x+1, y, 4, 0);
    makeBrighter(x, y+1, 4, 0);
    makeBrighter(x-1, y-1, 3, 0);
    makeBrighter(x+1, y-1, 3, 0);
    makeBrighter(x-1, y+1, 3, 0);
    makeBrighter(x+1, y+1, 3, 0);

    
    makeBrighter(x-2, y, 3, 0);
    makeBrighter(x, y-2, 3, 0);
    makeBrighter(x+2, y, 3, 0);
    makeBrighter(x, y+2, 3, 0);
    makeBrighter(x-1, y-2, 2, 0);
    makeBrighter(x+1, y-2, 2, 0);
    makeBrighter(x+1, y+2, 2, 0);
    makeBrighter(x-1, y+2, 2, 0);
    makeBrighter(x-2, y-1, 2, 0);
    makeBrighter(x+2, y-1, 2, 0);
    makeBrighter(x+2, y+1, 2, 0);
    makeBrighter(x-2, y+1, 2, 0);

    makeBrighter(x-3, y, 2, 0);
    makeBrighter(x, y-3, 2, 0);
    makeBrighter(x+3, y, 2, 0);
    makeBrighter(x, y+3, 2, 0);

  }
  private void createLightEffectFirePlace(int x, int y) {
    makeBrighter(x, y, 5, 0);
    makeBrighter(x-1, y, 5, 0);
    makeBrighter(x, y-1, 5, 0);
    makeBrighter(x+1, y, 5, 0);
    makeBrighter(x, y+1, 5, 0);
    makeBrighter(x-1, y-1, 5, 0);
    makeBrighter(x+1, y-1, 5, 0);
    makeBrighter(x-1, y+1, 5, 0);
    makeBrighter(x+1, y+1, 5, 0);

    
    makeBrighter(x-2, y, 4, 0);
    makeBrighter(x, y-2, 4, 0);
    makeBrighter(x+2, y, 4, 0);
    makeBrighter(x, y+2, 4, 0);
    makeBrighter(x-2, y-1, 4, 4);
    makeBrighter(x+2, y-1, 4, 4);
    makeBrighter(x-2, y+1, 4, 4);
    makeBrighter(x+2, y+1, 4, 4);
    makeBrighter(x-1, y-2, 4, 4);
    makeBrighter(x+1, y-2, 4, 4);
    makeBrighter(x-1, y+2, 4, 4);
    makeBrighter(x+1, y+2, 4, 4);

    
    makeBrighter(x-3, y, 3, 4);
    makeBrighter(x, y-3, 3, 4);
    makeBrighter(x+3, y, 3, 4);
    makeBrighter(x, y+3, 3, 4);
    
    makeBrighter(x-2, y-2, 3, 4);
    makeBrighter(x-2, y+2, 3, 4);
    makeBrighter(x+2, y-2, 3, 4);
    makeBrighter(x+2, y+2, 3, 4);


  }

  private void createLightEffectNormal(int x, int y) {
    makeBrighter(x, y, 5, 0);
    makeBrighter(x-1, y, 5, 0);
    makeBrighter(x, y-1, 5, 0);
    makeBrighter(x+1, y, 5, 0);
    makeBrighter(x, y+1, 5, 0);
    makeBrighter(x-1, y-1, 5, 0);
    makeBrighter(x+1, y-1, 5, 0);
    makeBrighter(x-1, y+1, 5, 0);
    makeBrighter(x+1, y+1, 5, 0);

    makeBrighter(x-2, y, 4, 0);
    makeBrighter(x, y-2, 4, 0);
    makeBrighter(x+2, y, 4, 0);
    makeBrighter(x, y+2, 4, 0);

    makeBrighter(x-3, y, 3, 0);
    makeBrighter(x, y-3, 3, 0);
    makeBrighter(x+3, y, 3, 0);
    makeBrighter(x, y+3, 3, 0);
    

    makeBrighter(x-2, y-1, 3, 0);
    makeBrighter(x+2, y-1, 3, 0);
    makeBrighter(x-2, y+1, 3, 0);
    makeBrighter(x+2, y+1, 3, 0);

    makeBrighter(x-1, y-2, 3, 0);
    makeBrighter(x+1, y-2, 3, 0);
    makeBrighter(x-1, y+2, 3, 0);
    makeBrighter(x+1, y+2, 3, 0);

    makeBrighter(x-2, y-2, 2, 4);
    makeBrighter(x-2, y+2, 2, 4);
    makeBrighter(x+2, y-2, 2, 4);
    makeBrighter(x+2, y+2, 2, 4);

  }

  private void createLightEffectBright(int x, int y) {
    makeBrighter(x, y, 6, 0);
    makeBrighter(x-1, y, 6, 0);
    makeBrighter(x, y-1, 6, 0);
    makeBrighter(x+1, y, 6, 0);
    makeBrighter(x, y+1, 6, 0);
    makeBrighter(x-1, y-1, 6, 0);
    makeBrighter(x+1, y-1, 6, 0);
    makeBrighter(x-1, y+1, 6, 0);
    makeBrighter(x+1, y+1, 6, 0);

    
    makeBrighter(x-2, y, 5, 0);
    makeBrighter(x, y-2, 5, 0);
    makeBrighter(x+2, y, 5, 0);
    makeBrighter(x, y+2, 5, 0);

    makeBrighter(x-3, y, 3, 0);
    makeBrighter(x, y-3, 3, 0);
    makeBrighter(x+3, y, 3, 0);
    makeBrighter(x, y+3, 3, 0);

    makeBrighter(x-4, y, 3, 0);
    makeBrighter(x, y-4, 3, 0);
    makeBrighter(x+4, y, 3, 0);
    makeBrighter(x, y+4, 3, 0);
    
    

    makeBrighter(x-2, y-1, 4, 0);
    makeBrighter(x+2, y-1, 4, 0);
    makeBrighter(x-2, y+1, 4, 0);
    makeBrighter(x+2, y+1, 4, 0);

    makeBrighter(x-1, y-2, 4, 0);
    makeBrighter(x+1, y-2, 4, 0);
    makeBrighter(x-1, y+2, 4, 0);
    makeBrighter(x+1, y+2, 4, 0);

    makeBrighter(x-3, y-1, 3, 0);
    makeBrighter(x+3, y-1, 3, 0);
    makeBrighter(x-3, y+1, 3, 0);
    makeBrighter(x+3, y+1, 3, 0);

    makeBrighter(x-1, y-3, 2, 0);
    makeBrighter(x+1, y-3, 2, 0);
    makeBrighter(x-1, y+3, 2, 0);
    makeBrighter(x+1, y+3, 2, 0);

    makeBrighter(x-2, y-2, 2, 0);
    makeBrighter(x+2, y-2, 2, 0);
    makeBrighter(x-2, y+2, 2, 0);
    makeBrighter(x+2, y+2, 2, 0);

  }

  private void createLightEffectMagical(int x, int y) {
    makeBrighter(x, y, 5, 1);
    makeBrighter(x-1, y, 4, 1);
    makeBrighter(x, y-1, 4, 1);
    makeBrighter(x+1, y, 4, 1);
    makeBrighter(x, y+1, 4, 1);
    
    makeBrighter(x-2, y, 3, 1);
    makeBrighter(x, y-2, 3, 1);
    makeBrighter(x+2, y, 3, 1);
    makeBrighter(x, y+2, 3, 1);

    makeBrighter(x-3, y, 2, 1);
    makeBrighter(x, y-3, 2, 1);
    makeBrighter(x+3, y, 2, 1);
    makeBrighter(x, y+3, 2, 1);
    
    makeBrighter(x-1, y-1, 4, 1);
    makeBrighter(x+1, y-1, 4, 1);
    makeBrighter(x-1, y+1, 4, 1);
    makeBrighter(x+1, y+1, 4, 1);

    makeBrighter(x-2, y-1, 3, 1);
    makeBrighter(x+2, y-1, 3, 1);
    makeBrighter(x-2, y+1, 3, 1);
    makeBrighter(x+2, y+1, 3, 1);

    makeBrighter(x-1, y-2, 3, 1);
    makeBrighter(x+1, y-2, 3, 1);
    makeBrighter(x-1, y+2, 3, 1);
    makeBrighter(x+1, y+2, 3, 1);

  }

  private void createLightEffectMagicalFast(int x, int y) {
    makeBrighter(x, y, 5, 2);
    makeBrighter(x-1, y, 4, 2);
    makeBrighter(x, y-1, 4, 2);
    makeBrighter(x+1, y, 4, 2);
    makeBrighter(x, y+1, 4, 2);
    
    makeBrighter(x-2, y, 3, 2);
    makeBrighter(x, y-2, 3, 2);
    makeBrighter(x+2, y, 3, 2);
    makeBrighter(x, y+2, 3, 2);

    makeBrighter(x-3, y, 2, 2);
    makeBrighter(x, y-3, 2, 2);
    makeBrighter(x+3, y, 2, 2);
    makeBrighter(x, y+3, 2, 2);
    
    makeBrighter(x-1, y-1, 4, 2);
    makeBrighter(x+1, y-1, 4, 2);
    makeBrighter(x-1, y+1, 4, 2);
    makeBrighter(x+1, y+1, 4, 2);

    makeBrighter(x-2, y-1, 3, 2);
    makeBrighter(x+2, y-1, 3, 2);
    makeBrighter(x-2, y+1, 3, 2);
    makeBrighter(x+2, y+1, 3, 2);

    makeBrighter(x-1, y-2, 3, 2);
    makeBrighter(x+1, y-2, 3, 2);
    makeBrighter(x-1, y+2, 3, 2);
    makeBrighter(x+1, y+2, 3, 2);

  }
   */
  /** 
   * Calculate shading for whole map
   */
  public void doShading() {
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        setLightShade(i, j, true, defaultDayShade[getSectorByCoordinates(i,j)]);
        setLightShade(i, j, false, defaultNightShade[getSectorByCoordinates(i,j)]);
        setLightSpecial(i, j, true, 0);
        setLightSpecial(i, j, false, 0);
      }
    }
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        int lightEffect = 0;
        TileInfo tmp = mapTileset.getTileInfo(walls[i][j]);
        if (tmp != null) {
          lightEffect = tmp.getLightEffect();
        }
        tmp = mapTileset.getTileInfo(objects[i][j]);
        if ((tmp != null) && (lightEffect == 0)) {
          lightEffect = tmp.getLightEffect();
        }
        tmp = mapTileset.getTileInfo(decorations[i][j]);
        if ((tmp != null) && (lightEffect == 0)) {
          lightEffect = tmp.getLightEffect();
        }
        tmp = mapTileset.getTileInfo(top[i][j]);
        if ((tmp != null) && (lightEffect == 0)) {
          lightEffect = tmp.getLightEffect();
        }
        if (lightEffect != TileInfo.LIGHT_EFFECT_NO_EFFECT) {
         switch (lightEffect) {
/*         case TileInfo.LIGHT_EFFECT_DIM_LIGHT: createLightEffectDim(i,j); break;
         case TileInfo.LIGHT_EFFECT_FIREPLACE: createLightEffectFirePlace(i,j); break;
         case TileInfo.LIGHT_EFFECT_NORMAL_LIGHT: createLightEffectNormal(i,j); break;
         case TileInfo.LIGHT_EFFECT_BRIGHT_LIGHT: createLightEffectBright(i,j); break;
         case TileInfo.LIGHT_EFFECT_MAGICAL: createLightEffectMagical(i,j); break;
         case TileInfo.LIGHT_EFFECT_MAGICAL_FAST: createLightEffectMagicalFast(i,j); break;*/
         case TileInfo.LIGHT_EFFECT_DIM_LIGHT: createLightEffectRadius(i, j, 3, 3, 0,false); break;
         case TileInfo.LIGHT_EFFECT_FIREPLACE: createLightEffectRadius(i, j, 4, 4, 4,true); break;
         case TileInfo.LIGHT_EFFECT_NORMAL_LIGHT: createLightEffectRadius(i, j, 5, 5, 0,false); break;
         case TileInfo.LIGHT_EFFECT_BRIGHT_LIGHT: createLightEffectRadius(i, j, 6, 6, 0,false); break;
         case TileInfo.LIGHT_EFFECT_MAGICAL: createLightEffectRadius(i, j, 4, 6, 1,false); break;
         case TileInfo.LIGHT_EFFECT_MAGICAL_FAST: createLightEffectRadius(i, j, 4, 6, 2,false); break;
         case TileInfo.LIGHT_EFFECT_CAST_SHADOW: {
           if (i+1 < maxX) {
             TileInfo nextTmp = mapTileset.getTileInfo(walls[i+1][j]);
             if (nextTmp != null && !nextTmp.isBlocked() && !nextTmp.isWestBlocked()) {
               int shade = getShade(i+1, j, true);
               shade=shade+2;
               if (shade > 7) {
                 shade = 7;
               }
               setLightShade(i+1, j, true, shade);
               shade = getShade(i+1, j, false);
               shade=shade+2;
               if (shade > 7) {
                 shade = 7;
               }
               setLightShade(i+1, j, false, shade);
             }
           }
           break;
         }
         }
        } /*else { // Start of horizontal shadow
          if (i > 0) {
            TileInfo prevTmp = mapTileset.getTileInfo(walls[i-1][j]);
            TileInfo nextTmp = null;
            if (i+1 < maxX) {
              nextTmp = mapTileset.getTileInfo(walls[i+1][j]);
            }
            tmp = mapTileset.getTileInfo(walls[i][j]);
            if (prevTmp != null && tmp != null && nextTmp != null) {
              if ((prevTmp.isBlocked() || prevTmp.isEastBlocked()) &&
                 (!tmp.isBlocked() && !nextTmp.isBlocked())){
                int shade = getShade(i, j, true);
                shade=shade+2;
                if (shade > 7) {
                  shade = 7;
                }
                setLightShade(i, j, true, shade);
                shade = getShade(i, j, false);
                shade=shade+2;
                if (shade > 7) {
                  shade = 7;
                }
                setLightShade(i, j, false, shade);
              }
            }
            if (prevTmp != null && tmp != null && nextTmp == null && i+1==maxX) {
              if ((prevTmp.isBlocked() || prevTmp.isEastBlocked()) &&
                 (!tmp.isBlocked())){
                int shade = getShade(i, j, true);
                shade=shade+2;
                if (shade > 7) {
                  shade = 7;
                }
                setLightShade(i, j, true, shade);
                shade = getShade(i, j, false);
                shade=shade+2;
                if (shade > 7) {
                  shade = 7;
                }
                setLightShade(i, j, false, shade);
              }
            }
          }
        } // End of Horizontal shadow*/
      }
    }
  }

  public void setMapSectors(int mapSectors) {
    if ((mapSectors > 0) && (mapSectors < 5) && (mapSectors != 3)) 
    {
      this.mapSectors = mapSectors;
    }
  }

  public int getMapSectors() {
    return mapSectors;
  }

  public int getMapTilesetIndex() {
    return mapTilesetIndex;
  }

  public void setMapTilesetIndex(int mapTilesetIndex) {
    if ((mapTilesetIndex >= 0) && 
        (mapTilesetIndex < Tileset.TILESET_NAME_RESOURCE.length)) {
      this.mapTilesetIndex = mapTilesetIndex;
    }
  }

  public void setNorthDirection(int sector, int northDirection) {
    if ((sector >= 0) && (sector < 4)) {
      if ((northDirection >= NORTH_DIRECTION_UP) && (northDirection <=NORTH_DIRECTION_LEFT)) {
        this.northDirection[sector] = northDirection;  
      }      
    }
  }

  /**
   * Get north direction by sector
   * @param sector
   * @return
   */
  public int getNorthDirection(int sector) {
    if ((sector >= 0) && (sector < 4)) {
      return northDirection[sector];
    } else {
      return NORTH_DIRECTION_UP;
    }
  }

  public void setDayMusic(int sector, String music) {
    if ((sector >= 0) && (sector < 4)) {
      musicDay[sector] = music;
    }
  }

  public void setNightMusic(int sector, String music) {
    if ((sector >= 0) && (sector < 4)) {
      musicNight[sector] = music;
    }
  }

  public void setCombatMusic(int sector, String music) {
    if ((sector >= 0) && (sector < 4)) {
      musicCombat[sector] = music;
    }
  }

  public String getDayMusicBySector(int sector) {
    if ((sector >= 0) && (sector < 4)) {
      return musicDay[sector];
    } else {
      return "";
    }
  }

  public String getNightMusicBySector(int sector) {
    if ((sector >= 0) && (sector < 4)) {
      return musicNight[sector];
    } else {
      return "";
    }
  }

  public String getCombatMusicBySector(int sector) {
    if ((sector >= 0) && (sector < 4)) {
      return musicCombat[sector];
    } else {
      return "";
    }
  }

  public String getDayMusic(int x, int y) {
    int sector = getSectorByCoordinates(x, y);
    return musicDay[sector];
  }
  public String getNightMusic(int x, int y) {
    int sector = getSectorByCoordinates(x, y);
    return musicNight[sector];
  }
  public String getCombatMusic(int x, int y) {
    int sector = getSectorByCoordinates(x, y);
    return musicCombat[sector];
  }

  
  public int getSectorByCoordinates(int x, int y) {
    int result = 0;
    if ((x>=0) && (y>=0) && (x<maxX) && (y<maxY)) {
      switch (getMapSectors()) {
      case 1: {
              result = 0;            
              break;}
      case 2: {
               if (x>=maxX/2) {
                 result = 1;
               } else {
                 result = 0;
               }
        break;}
      case 4: {
              if (x>=maxX/2) {
                if (y>=maxY/2) {
                  result = 3;
                } else {
                  result = 1;
                }
              } else {
                if (y>=maxY/2) {
                  result = 2;
                } else {
                  result = 0;
                }
              }        
        break;}
      }
    }
    return result;
  }
  
  private int getSectorMaxX(int sector) {
    int result = getMaxX();
    switch (getMapSectors()) {
    case 1: {
            result = maxX;            
            break;}
    case 2: {
             if (sector == 0) {
               result = maxX/2;
             } else {
               result = maxX;
             }
      break;}
    case 4: {
      if ((sector == 0) || (sector == 2)) {
        result = maxX/2;
      } else {
        result = maxX;
      }
      break;}
  }
  return result;
  }
  private int getSectorMaxY(int sector) {
    int result = getMaxY();
    switch (getMapSectors()) {
    case 1: {
            result = maxY;            
            break;}
    case 2: {
            result = maxY;
      break;}
    case 4: {
      if ((sector == 0) || (sector == 1)) {
        result = maxY/2;
      } else {
        result = maxY;
      }
      break;}
  }
  return result;
  }
  private int getSectorMinX(int sector) {
    int result = 0;
    switch (getMapSectors()) {
    case 1: {
            result = 0;            
            break;}
    case 2: {
             if (sector == 0) {
               result = 0;
             } else {
               result = maxX/2;
             }
      break;}
    case 4: {
      if ((sector == 0) || (sector == 2)) {
        result = 0;
      } else {
        result = maxX/2;
      }
      break;}
    }
    return result;
  }
  private int getSectorMinY(int sector) {
    int result = 0;
    switch (getMapSectors()) {
    case 1: {
            result = 0;            
            break;}
    case 2: {
            result = 0;
      break;}
    case 4: {
      if ((sector == 0) || (sector == 1)) {
        result = 0;
      } else {
        result = maxY/2;
      }
      break;}
  }
  return result;
  }
  /**
   * Fill one sector with index
   * @param sector Sector number
   * @param index Tile index
   */
  public void fillSectorFlood(int sector, int index) {
    for (int i=getSectorMinX(sector);i<getSectorMaxX(sector);i++) {
      for (int j=getSectorMinY(sector);j<getSectorMaxY(sector);j++) {
        setMap(i, j, index);
      }
    }
  }
  
  
  /**
   * Get north direction by coordinates
   * @param x
   * @param y
   * @return
   */
  public int getNorthDirection(int x, int y) {    
    return getNorthDirection(getSectorByCoordinates(x, y));
  }
  
  public void setDayShade(int sector, int shade) {
    if ((sector >= 0) && (sector < 4)) {
      if ((shade >= 0) && (shade <=7)) {
        defaultDayShade[sector] = shade;
      }
    }
  }

  public void setNightShade(int sector, int shade) {
    if ((sector >= 0) && (sector <= 4)) {
      if ((shade >= 0) && (shade <=7)) {
        defaultNightShade[sector] = shade;
      }
    }
  }
  
  public int getDayShade(int sector) {
    int result = 0;
    if ((sector >= 0) && (sector < 4)) {
      result = defaultDayShade[sector];
    }
    return result;
  }

  public int getNightShade(int sector) {
    int result = 0;
    if ((sector >= 0) && (sector < 4)) {
      result = defaultNightShade[sector];
    }
    return result;
  }

  
  /**
   * Remove Item by index
   * @param index
   */
  public void removeItemByIndex(int index) {
    if ((index <= listItems.size()-1) &&(index >= 0)) {
     listItems.remove(index);
    }
    
  }
  
  /**
   * Remove item from list
   * @param item
   */
  public void removeItem(Item item) {
    listItems.remove(item);
  }
  
  /**
   * Generate random items
   */
  public void generateRandomItems() {
    if (listItems.size() > 0) {
      for (int i=0;i<listItems.size();i++) {
        Item item = listItems.get(i);
        int x = item.getX();
        int y = item.getY();
        Item tmp = null;
        if (item.getName().equalsIgnoreCase("Lesser random item")) {
          tmp = ItemFactory.generateRandomItem(ItemFactory.LESSER_RANDOM_ITEM);
        }
        if (item.getName().equalsIgnoreCase("Random item")) {
          tmp = ItemFactory.generateRandomItem(ItemFactory.RANDOM_ITEM);
        }
        if (item.getName().equalsIgnoreCase("High random item")) {
          tmp = ItemFactory.generateRandomItem(ItemFactory.HIGH_RANDOM_ITEM);
        }
        if (item.getName().equalsIgnoreCase("Magic random item")) {
          tmp = ItemFactory.generateRandomItem(ItemFactory.MAGIC_RANDOM_ITEM);
        }
        if (tmp != null) {
          tmp.putItemOnMap(x, y);
          listItems.set(i, tmp);
        }
      }
        
    }
  }
  
  /**
   * Get item by index
   * @param index
   * @return
   */
  public Item getItemByIndex(int index) {
    if ((index <= listItems.size()-1 ) &&(index >= 0)) {
      return listItems.get(index);
    } else {
      return null;
    }
  }
  
  /**
   * ItemIndex for searching items from map
   */
  private int itemIndexForSearch=-1;
  
  /**
   * Get ItemIndex by coordinates. Start always from zero, but saves item index
   * if item is found
   * @param x
   * @param y
   * @return int, -1 if not found
   */
  public int getItemIndexByCoordinates(int x, int y) {
    if (listItems.size()>0) {
      for (int i=0;i<listItems.size();i++) {
        Item tmp = listItems.get(i);
        if ((tmp.getX() == x) &&(tmp.getY()==y)) {
          itemIndexForSearch = i;
          return i;
        }
      }
    }
    itemIndexForSearch = -1;
    return -1;
  }
  
  /**
   * Get next item from the coordinates. Start search from itemIndex from previous
   * search
   * @param x
   * @param y
   * @return int, -1 if not found
   */
  public int getNextItemFromCoordinates(int x, int y) {
    if (listItems.size()>0) {
      int start = itemIndexForSearch+1;
      if (start == -1 || start >= listItems.size()) {
        itemIndexForSearch = -1;
        return -1;
      }
      for (int i=start;i<listItems.size();i++) {
        Item tmp = listItems.get(i);
        if ((tmp.getX() == x) &&(tmp.getY()==y)) {
          itemIndexForSearch = i;
          return i;
        }
      }
    }
    itemIndexForSearch = -1;
    return -1;
  }
  
  /**
   * Get items by surrounding 3x3
   * @param x
   * @param y
   * @return ArrayList<Item>
   */
  public ArrayList<Item> getItemsFromSurround(int x,int y) {
    ArrayList<Item> foundItems = new ArrayList<Item>();
    boolean found =false;
    if (listItems.size()>0) {
      for (int i=0;i<listItems.size();i++) {
        Item tmp = listItems.get(i);
        int mx = x-tmp.getX();
        int my = y-tmp.getY();
        mx = Math.abs(mx);
        my = Math.abs(my);
        if ((mx <= 1) &&(my <= 1)) {
          foundItems.add(tmp);
          found = true;
        }
      }
    }
    if (found == true) {
      return foundItems;
    } else {
      return null;
    }
  }
  
  /**
   * Adds new item to map
   * @param x, coordinate
   * @param y, coordinate
   * @param newItem
   */
  public void addNewItem(int x, int y, Item newItem) {
    newItem.putItemOnMap(x, y);
    listItems.add(newItem);
  }
  
  /** 
   * Add new event
   * @param x, Coordinates
   * @param y, Coordinates
   * @param x2, If same as above then point otherwise region
   * @param y2, If same as above then point otherwise region
   * @param name, event name
   * @param command, event command
   * @param param1
   * @param param2
   * @param param3
   */
  public void addNewEvent(int x, int y, int x2,int y2,String name, byte command,
                         String param1, String param2, String param3) {
    Event myEvent = new Event(x, y);
    if ((x2>x) &&(y2>y)) {
      myEvent.setRegion(x, y, x2, y2);
    }
    myEvent.setEventName(name);
    myEvent.setEventCommand(command);
    myEvent.setParam(0, param1);
    myEvent.setParam(1, param2);
    myEvent.setParam(2, param3);
    listEvents.add(myEvent);
    updateWPHashMap();
  }
  
  /**
   * Get Waypoints as Array of string
   * @return String[]
   */
  public String[] getListOfWaypoints() {
    int i;
    int numOfWPs=0;
    if (listEvents.size() >0) {
      for (i=0;i<listEvents.size();i++) {
        Event myEvent = listEvents.get(i);
        if (myEvent.isWaypoint()) {          
          numOfWPs++;
        }      
      }
      if (numOfWPs>0) {
        String[] result = new String[numOfWPs];
        int j =0;
        for (i=0;i<listEvents.size();i++) {
          Event myEvent = listEvents.get(i);
          if (myEvent.isWaypoint()) {          
            result[j] = myEvent.getEventName();
            j++;
          }      
        }
        return result;
      }
    }
    return null;    
  }
  
  private void updateWPHashMap() {
    hashMapWP = new HashMap<String, String>(listEvents.size()+20);
    if (listEvents.size() >0) {
      for (int i=0;i<listEvents.size();i++) {
        Event myEvent = listEvents.get(i);
        hashMapWP.put(myEvent.getEventName().toUpperCase(), String.valueOf(i));
      }
    }
  }
  
  /**
   * Search for waypoint event by name
   * @param name
   * @return eventIndex number and set currentEvent
   */
  public int getEventWaypointByName(String name) {
    if (hashMapWP == null) {
      updateWPHashMap();
    }
    String searchStr = name.toUpperCase();
    String result = (String) hashMapWP.get(searchStr);
    if (result != null) {
     return Integer.valueOf(result);
    } else {
      return -1;
    }
  }
  
  /**
   * Calculate how many tiles are unblocked in map
   * @param index Event index
   * @return how many unblocked tiles found in waypoint
   */
  public int getWaypointNonBlockedSize(int index) {
    Event event = getEventByIndex(index);
    if (event.getType() == Event.TYPE_POINT) {
      return 1;
    } else {
      int result = 0;
      for (int i=event.getX1();i<=event.getX2();i++) {
        for (int j=event.getY1();j<=event.getY2();j++) {
          if (!isTargetBlockedByMap(i, j)) {
            result++;
          }
        }
      }
      return result;
    }
  }
  
  /**
   * Does the actual modifyMap run at coordinates
   * @param x
   * @param y
   * @param tile Tile index
   * @param effect Graph Effect index
   * @param command Strings "Add","Replace","Remove", "RemoveAll", "JustEffect"
   */
  private void runModifyMapByCoordinates(int x, int y, String tile, String effect,
      String command) {
    try {
      int tileIndex = Integer.valueOf(tile);
      int effectIndex = Integer.valueOf(effect);
      if ((x>=0) && (x<maxX) && (y>=0) && (y<maxY)) {
        if (command.equalsIgnoreCase("Add") || command.equalsIgnoreCase("Replace")) {
          setMap(x, y, tileIndex);
          if (effectIndex > 0) {
            addNewGraphEffect(x, y, effectIndex);
          }
        }
        if (command.equalsIgnoreCase("JustEffect"))  {
          if (effectIndex > 0) {
            addNewGraphEffect(x, y, effectIndex);
          }
        }
        if (command.equalsIgnoreCase("Remove")) {
          setMap(x, y, -1, (byte) tileIndex);
          if (effectIndex > 0) {
            addNewGraphEffect(x, y, effectIndex);
          }
        }
        if (command.equalsIgnoreCase("RemoveAll")) {
          setMap(x, y, -1,TileInfo.TILE_PLACE_WALL_OR_FLOOR);
          if (effectIndex > 0) {
            addNewGraphEffect(x, y, effectIndex);
          }
        }
      }
    }
    catch (NumberFormatException e) {
      // Do nothing
    }
  }
  
  /**
   * Activate SFX by name
   * @param name String
   * @param command String for example: Loop, Day, Night, Day#NN
   */
  public void activateSFXsByName(String name, String command) {
    int i;
    if (listEvents.size() >0) {
      for (i=0;i<listEvents.size();i++) {
        Event myEvent = listEvents.get(i);
        if ((myEvent.getEventCommand() == Event.COMMAND_TYPE_PLAY_SOUND) && 
           (myEvent.getEventName().equalsIgnoreCase(name))) {
          myEvent.setParam(1, command);
        }
      }
    }          
  }
  
  /**
   * Runs modify Event at map
   * @param name Event name which to run
   */
  public void runModifyMapByName(String name) {
    int i;
    if (listEvents.size() >0) {
      for (i=0;i<listEvents.size();i++) {
        Event myEvent = listEvents.get(i);
        if ((myEvent.getEventCommand() == Event.COMMAND_TYPE_MODIFY_MAP) && 
           (myEvent.getEventName().equalsIgnoreCase(name))) {
          if (myEvent.getType()==Event.TYPE_POINT) {
            runModifyMapByCoordinates(myEvent.getX(), myEvent.getY(),
                myEvent.getParam(0), myEvent.getParam(1), myEvent.getParam(2));
          } else {
            for (int k=myEvent.getX1();k<=myEvent.getX2();k++) {
              for (int j=myEvent.getY1();j<=myEvent.getY2();j++) {
                runModifyMapByCoordinates(k, j,
                    myEvent.getParam(0), myEvent.getParam(1), myEvent.getParam(2));
                
              }
            }
          }
        }
      }
    }
  }
  
  /**
   * Make gate or door open and remove objects,decorations, and top tiles
   * @param event for LockedGate
   */
  public void doOpenGate(Event event) {
    if (event.getParam(1).equalsIgnoreCase("North")) {
      setMap(event.getX(), event.getY()-1,-1);
      setMap(event.getX(), event.getY(),-1);
    }
    if (event.getParam(1).equalsIgnoreCase("Single")) {
      setMap(event.getX(), event.getY(),-1);
    }
  }
  
  /**
   * Get Event by index number
   * @param index
   * @return Event if found otherwise null
   */
  public Event getEventByIndex(int index) {
    if ((index >= 0) && (index < listEvents.size())) {
      return listEvents.get(index);
    } else {
      return null;
    }
  }
  
  /**
   * Get event index by coordinates
   * @param x
   * @param y
   * @return index if found -1 if not found
   */
  public int getEvent(int x, int y) {
    int i;
    int result = -1;
    if (listEvents.size() >0) {
      for (i=0;i<listEvents.size();i++) {
        Event myEvent = listEvents.get(i);
        if (myEvent.isEventAt(x, y)) {
          if (myEvent.getType()==Event.TYPE_POINT) {
            currentEvent = myEvent;
            return i;
          } else {
            result = i;
            currentEvent = myEvent;
          }
        }
      }
    }
    return result;
  }

  /**
   * Get event by index by coordinates, but does not check for waypoints
   * @param x
   * @param y
   * @return index if found -1 if not found
   */
  public int getEventNotWaypoint(int x, int y) {
    int i;
    int result = -1;
    if (listEvents.size() >0) {
      for (i=0;i<listEvents.size();i++) {
        Event myEvent = listEvents.get(i);
        if ((myEvent.isEventAt(x, y)) && 
            ((myEvent.getEventCommand() != Event.COMMAND_TYPE_WAYPOINT))) {
          if (myEvent.getType()==Event.TYPE_POINT) {
            currentEvent = myEvent;
            return i;
          } else {
            result = i;
            currentEvent = myEvent;
          }
        }
      }
    }
    return result;
  }

  /**
   * Wrapper for getEventType
   * @return -1 No event selected yet
   *         TYPE_POINT = 0;
   *         TYPE_REGION = 1;
   */
  public byte getEventType() {
    if (currentEvent != null) {
      return currentEvent.getType();
    } else return -1;    
  }
  
  /**
   * Wrapper for getEventCommand
   * @return 1 No event selected yet 
   *         COMMAND_TYPE_WAYPOINT = 0;
   *          COMMAND_TYPE_SIGN = 1;
   *          COMMAND_TYPE_TRAVEL = 2;
   *          COMMAND_TYPE_QUICK_TRAVEL = 3;
   */
  public byte getEventCommand() {
    if (currentEvent != null) {
      return currentEvent.getEventCommand();
    } else return -1;
  
  }
  
  /**
   * Wrapper for getEventParameter
   * @param index
   * @return String, empty if not found
   */
  public String getEventParameter(int index) {
    if (currentEvent != null) {
      return currentEvent.getParam(index);
    } else return "";
  }
  
  /**
   * Wrappers for getting coordinates for event
   * @return
   */
  public int getEventX1() {
    if (currentEvent != null) {
      return currentEvent.getX1();
    } else return 0;
  }
  
  /**
   * Wrappers for getting coordinates for event
   * @return
   */
  public int getEventY1() {
    if (currentEvent != null) {
      return currentEvent.getY1();
    } else return 0;
  }
  
  /**
   * Wrappers for getting coordinates for event
   * @return
   */
  public int getEventX2() {
    if (currentEvent != null) {
      return currentEvent.getX2();
    } else return 0;
  }
  
  /**
   * Wrappers for getting coordinates for event
   * @return
   */
public int getEventY2() {
    if (currentEvent != null) {
      return currentEvent.getY2();
    } else return 0;
  }
  
  /**
   * Wrapper for getEventName
   * @return String, empty if not found
   */
  public String getEventName() {
    if (currentEvent != null) {
      return currentEvent.getEventName();
    } else return "";
  
  }
  
  /**
   * Remove event from event list
   * @param index
   */
  public void removeEvent(int index) {
    if ((index > -1) && (index < listEvents.size())) {
      listEvents.remove(index);
      updateWPHashMap();
    }
  }
  
  /**
   * Map constructor reads map information from DataInputStream
   * @param is DataInputStream
   * @throws IOException
   */
  public Map(DataInputStream is) throws IOException {
    String mapVersion = StreamUtilities.readString(is);
    if (!CURRENT_MAP_VERSION.equals(mapVersion) &&
        !MAP_VERSION_1_0.equals(mapVersion)) {
      throw new IOException("Not map file!");
    }
    this.party = null;
    maxX = is.readInt();
    maxY = is.readInt();
    walls = new int[maxX][maxY];
    objects = new int[maxX][maxY];
    decorations = new int[maxX][maxY];
    top = new int[maxX][maxY];
    light = new short[maxX][maxY];
    graphEffects = new int[maxX][maxY];
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        graphEffects[i][j]=-1;
      }
    }


 // Going through the map Row by Row
    // Writing walls, object, decorations, top and light for each coordinate
    // Run length packing, if same as previous then write -2 and then calculate
    // how many identical map positions
    int packingState = 0;
    int amount = 0;
    int oldwall = -2;
    int oldobject = -2;
    int olddecoration = -2;
    int oldtop = -2;
    short oldlight = -2;
    boolean oldreadOnce = false;
    int tmp=0;
    for (int y=0;y<getMaxY();y++) {
      for (int x=0;x<getMaxX();x++) {
        if (packingState == 0) {
          tmp = is.readInt();
        }
        if ((tmp == -2) && (packingState ==2)) {
          walls[x][y] = oldwall;
          objects[x][y] = oldobject;
          decorations[x][y] = olddecoration;
          top[x][y] = oldtop;
          light[x][y] = oldlight;
          objects[x][y] = oldobject;
          amount--;
          if (amount == 0) {
            packingState =0;
          }
        } else
        if ((tmp == -2) && (packingState == 0) && (oldreadOnce == true)) {
          packingState = 1;
          amount = is.readInt();
          walls[x][y] = oldwall;
          objects[x][y] = oldobject;
          decorations[x][y] = olddecoration;
          top[x][y] = oldtop;
          light[x][y] = oldlight;
          objects[x][y] = oldobject;
          packingState = 2;
          amount--;
          if (amount == 0) {
            packingState =0;
          }
        } else
        if (tmp != -2) {
          walls[x][y] = tmp;
          objects[x][y] = is.readInt();
          decorations[x][y] = is.readInt();
          top[x][y] = is.readInt();
          light[x][y] = is.readShort();
          oldwall = walls[x][y];
          oldobject = objects[x][y];
          olddecoration = decorations[x][y];
          oldtop = top[x][y];
          oldlight = light[x][y];
          packingState = 0;
          oldreadOnce = true;
        }
      }
    }
    
    mapTilesetIndex = is.readInt();
    if ((mapTilesetIndex > Tileset.TILESET_NAME_RESOURCE.length) || (mapTilesetIndex == -1)) {
      is.close();
      throw new IOException("Unknown tileset!!");
    }
    myChar = new Character(0);
    myChar.setPosition(5, 5);
    mapCompass = new Compass();
    mapTileset = new Tileset();
    DataInputStream dis = new DataInputStream(new BufferedInputStream(
        Map.class.getResourceAsStream(Tileset.TILESET_NAME_RESOURCE[mapTilesetIndex])));
    int err =mapTileset.loadTileSet(dis);
    if (err != 0){
      throw new IOException("Error while reading tile set! Error code:"+String.valueOf(err));
    }
    northDirection = new int[4]; // Max sector is 4;
    defaultDayShade = new int[4];
    defaultNightShade = new int[4];
    musicNight = new String[4];
    musicDay = new String[4];
    musicCombat = new String[4];
    musicNight[0] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicNight[1] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicNight[2] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicNight[3] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicDay[0] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicDay[1] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicDay[2] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicDay[3] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicCombat[0] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicCombat[1] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicCombat[2] = MusicPlayer.MUSIC_FILE_VILLAGE;
    musicCombat[3] = MusicPlayer.MUSIC_FILE_VILLAGE;
    defaultDayShade[0] = 0;
    defaultDayShade[1] = 0;
    defaultDayShade[2] = 0;
    defaultDayShade[3] = 0;
    defaultNightShade[0] = 7;
    defaultNightShade[1] = 7;
    defaultNightShade[2] = 7;
    defaultNightShade[3] = 7;
    currentEvent = null;
    listEvents = new ArrayList<Event>();
    listItems = new ArrayList<Item>();
    npcList = new ArrayList<Character>();

    
    mapSectors = is.readByte();
    for (int i=0;i<mapSectors;i++) {
      northDirection[i] = is.readByte();
      defaultDayShade[i] = is.readByte();
      defaultNightShade[i] = is.readByte();
      musicDay[i] = StreamUtilities.readString(is);
      musicNight[i] = StreamUtilities.readString(is);
      musicCombat[i] = StreamUtilities.readString(is);
    }
    int size;
    size = is.readInt();
    boolean isPosSet = false;
    for (int i=0;i<size;i++) {
      Event event = new Event(is, mapVersion);
      if ((event.isWaypoint()) && (!isPosSet)) {
        myChar.setPosition(event.getX(), event.getY());
        isPosSet = true;
      }
      listEvents.add(event);
    }
    size = is.readInt();
    for (int i=0;i<size;i++) {
      Item item = ItemFactory.readMapItem(is,mapVersion);
      listItems.add(item);
    }
    size = is.readInt();
    for (int i=0;i<size;i++) {
      Character chr = new Character(0);
      chr.loadCharacter(is,mapVersion);
      npcList.add(chr);
    }
    is.close();
  }
  
  public void saveMap(DataOutputStream os) throws IOException {
    StreamUtilities.writeString(os, CURRENT_MAP_VERSION);
    // Writing the size of the map
    os.writeInt(getMaxX());
    os.writeInt(getMaxY());
    // Going through the map Row by Row
    // Writing walls, object, decorations, top and light for each coordinate
    // Run length packing, if same as previous then write -2 and then calculate
    // how many identical map positions
    int packingState = 0;
    int amount = 0;
    int oldwall = -2;
    int oldobject = -2;
    int olddecoration = -2;
    int oldtop = -2;
    int oldlight = -2;
    for (int y=0;y<getMaxY();y++) {
      for (int x=0;x<getMaxX();x++) {
        if (packingState == 1) {
          if ((oldwall == walls[x][y]) && (oldobject == objects[x][y]) &&
               (olddecoration == decorations[x][y]) && (oldtop == top[x][y]) &&
               (oldlight == light[x][y])) {
            amount++;
            
          } else {
            if (amount > 0) {
              os.writeInt(-2);
              os.writeInt(amount);
            }
            os.writeInt(walls[x][y]);
            os.writeInt(objects[x][y]);
            os.writeInt(decorations[x][y]);
            os.writeInt(top[x][y]);
            os.writeShort(light[x][y]);
            packingState = 0;
            amount =0;
          }
        } else {
          os.writeInt(walls[x][y]);
          os.writeInt(objects[x][y]);
          os.writeInt(decorations[x][y]);
          os.writeInt(top[x][y]);
          os.writeShort(light[x][y]);
          packingState = 0;
          amount =0;
        }
        if (packingState==0) {
          oldwall = walls[x][y];
          oldobject = objects[x][y];
          olddecoration = decorations[x][y];
          oldtop = top[x][y];
          oldlight = light[x][y];
          packingState = 1;
        }
      }
    }
    if ((packingState==1) && (amount > 0)) {
      os.writeInt(-2);
      os.writeInt(amount);
    }
    // Writing map tile index
    os.writeInt(mapTilesetIndex);
    // Writing sector information
    os.writeByte(mapSectors);
    for (int i=0;i<mapSectors;i++) {
      os.writeByte(northDirection[i]);
      os.writeByte(defaultDayShade[i]);
      os.writeByte(defaultNightShade[i]);
      StreamUtilities.writeString(os, musicDay[i]);
      StreamUtilities.writeString(os, musicNight[i]);
      StreamUtilities.writeString(os, musicCombat[i]);
    }
    // Writing events
    os.writeInt(listEvents.size());
    for (int i=0;i<listEvents.size();i++) {
      Event event = listEvents.get(i);
      event.saveEvent(os);
    }
    os.writeInt(listItems.size());
    for (int i=0;i<listItems.size();i++) {
      Item item = listItems.get(i);
      item.writeMapItem(os);
    }
    os.writeInt(npcList.size());
    for (int i=0;i<npcList.size();i++) {
      Character chr = npcList.get(i);
      chr.saveCharacter(os);
    }
    os.close();
  }

  public int getCursorTile() {
    return cursorTile;
  }

  public void setCursorTile(int cursorTile) {
    this.cursorTile = cursorTile;
  }

  public boolean isCursorMode() {
    if (cursorMode != 0) {
      return true;
    } else {
      return false;
    }
  }

  public void setCursorMode(int cursorMode) {
    this.cursorMode = cursorMode;
    switch (this.cursorMode) {
    // Change icons for different cursor modes
    case CURSOR_MODE_LOOK: this.setCursorTile(GRAPH_EFFECT_LOOK_CURSOR); break;
    case CURSOR_MODE_ATTACK: this.setCursorTile(GRAPH_EFFECT_ATTACK_CURSOR); break;
    case CURSOR_MODE_CAST: this.setCursorTile(GRAPH_EFFECT_SPELL_CURSOR); break;
    case CURSOR_MODE_USE: this.setCursorTile(GRAPH_EFFECT_USE_CURSOR); break;
    case CURSOR_MODE_TALK: this.setCursorTile(GRAPH_EFFECT_TALK_CURSOR); break;
    case CURSOR_MODE_SINGLE_ATTACK: this.setCursorTile(GRAPH_EFFECT_ATTACK_CURSOR); break;
    case CURSOR_MODE_DISABLE: castingSpell = null;setAttackText("No target"); break;
    case CURSOR_MODE_EVALUATE: this.setCursorTile(GRAPH_EFFECT_EVALUATE_CURSOR); break;
    }
  }
  public int getCursorMode() {
    return cursorMode;
  }

  public int getCursorX() {
    return cursorX;
  }

  public void setCursorX(int cursorX) {
    this.cursorX = cursorX;
  }

  public int getCursorY() {
    return cursorY;
  }

  public void setCursorY(int cursorY) {
    this.cursorY = cursorY;
  }

  public String getDisplayPopupText() {
    return displayPopupText;
  }

  public void setDisplayPopupText(String displayPopupText) {
    this.displayPopupText = displayPopupText;
  }

  public BufferedImage getPopupImage() {
    return popupImage;
  }

  public void setPopupImage(BufferedImage popupImage) {
    this.popupImage = popupImage;
  }

  public String getAttackText() {
    return attackText;
  }

  public void setAttackText(String attackText) {
    this.attackText = attackText;
    this.textsWritten = false;
  }

  public String getDefenseText() {
    return defenseText;
  }

  public void setDefenseText(String defenseText) {
    this.defenseText = defenseText;
    this.textsWritten = false;
  }
  
  

  /**
   * Get Direction towards
   * @param fromX
   * @param fromY
   * @param targetX
   * @param targetY
   * @param directions 4 or 8
   * @return int
   */
  public int getDirectionTowards(int fromX, int fromY, int targetX, int targetY,int directions) {
    int mx = targetX-fromX;
    int my = targetY-fromY;
    int result =0;
    if (directions == 8) {
      if (mx > 0) {
        if (my < 0) {
          result = 1;
        } else if (my > 0) {
          result = 3;
        } else {
          result = 2;
        }
      } else if (mx < 0) {
        if (my < 0) {
          result = 7;
        } else if (my > 0) {
          result = 5;
        } else {
          result = 6;
        }
      } else {
        if (my < 0) {
          result = 0;
        } else if (my > 0) {
          result = 4;
        } else {
          result = -1;
        } 
      }
      if (result != -1) {
        int north = getNorthDirection(fromX, fromY);
        if (north != 0) {
          if (north == NORTH_DIRECTION_DOWN) {
            result = result+4;
            if (result > 7) {
              result = result -8;
            }
          }
        }
      }
    } else { // Four directions
      if (Math.abs(mx) > Math.abs(my)) {
        if (mx > 0) {
          result = 1;
        } else if (mx < 0) {
          result = 3;
        } else {
          result = -1;
        }
      } else {
        if (my > 0) {
          result = 2;
        } else if (my < 0) {
          result = 0;
        } else {
          result = -1;
        }
      }
      // Currently this correction for 4 direction is not needed
/*      if (result != -1) {
        int north = getNorthDirection(fromX, fromY);
        if (north != 0) {
          if (north == NORTH_DIRECTION_DOWN) {
            result = result+2;
            if (result > 3) {
              result = result -4;
            }
          }
        }
      }*/
    }
    return result;
  }
  
  /**
   * Get direction as String
   * @param direction
   * @param numbers 8 or 4
   * @return String
   */
  public static String getDirectionAsString(int direction, int numbers) {
    String result = "";
    if (numbers == 8) {
      switch (direction) {
      case 0: result="north"; break;
      case 1: result="north-east"; break;
      case 2: result="east"; break;
      case 3: result="south-east"; break;
      case 4: result="south"; break;
      case 5: result="south-west"; break;
      case 6: result="west"; break;
      case 7: result="north-west"; break;
      case -1: result="ground"; break;
      }
    } else {
      switch (direction) {
      case 0: result="north"; break;
      case 1: result="east"; break;
      case 2: result="south"; break;
      case 3: result="west"; break;
      case -1: result="ground"; break;
      }      
    }
    return result;
  }
}
