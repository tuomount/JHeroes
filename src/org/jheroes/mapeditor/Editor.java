package org.jheroes.mapeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.swing.Box;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jheroes.map.Event;
import org.jheroes.map.Map;
import org.jheroes.map.MapUtilities;
import org.jheroes.map.character.Character;
import org.jheroes.map.item.Item;
import org.jheroes.map.item.ItemFactory;
import org.jheroes.musicplayer.MusicPlayer;

import org.jheroes.soundplayer.SoundPlayer;
import org.jheroes.tileset.Tile;
import org.jheroes.tileset.TileInfo;
import org.jheroes.tileset.Tileset;
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
 * Main class for Map Editor
 * 
 **/
public class Editor extends JFrame implements ActionListener {

      /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static String ACTION_CHARACTER_EDITOR = "characterEditor";
    private static String ACTION_ADD_CHARACTER = "addCharacter";
    private static String ACTION_REMOVE_CHARACTER = "removeCharacter";
    private static String ACTION_COPY_CHARACTER = "copyCharacter";
    private static String ACTION_SAVE_MAP = "saveMap";
    private static String ACTION_LOAD_MAP = "loadMap";
    private static String ACTION_REMOVE_EVENT ="removeEvent";
    private static String ACTION_TALK_EDITOR ="talkEditor";
    private static String ACTION_EXPORT_WHOLE_MAP ="ExportMap";
    
    
    private MapPanel panelMap;
    private JPanel panelButtons;
    private JLabel labelCoordinates;
    private JLabel labelEventCoord1;
    private JLabel labelEventCoord2;
    private JLabel labelItemName;
    private JLabel labelItemsInMap;
    private Tileset mapTileset;
    private Tileset itemTileset;
    private Tileset charactersTileset;
    private Tile myTile;
    private TileInfo myTileInfo;
    private Item myItem;
    private int myItemIndex;
    private Map myMap;
    private int cursorX, cursorY,index;
    private int eventX1,eventY1,eventX2,eventY2;
    private int copyX1,copyY1,copyX2,copyY2;
    private BufferedImage myTileScreen;
    private BufferedImage myItemScreen;
            
    private JFileChooser fcLoad;
    private boolean day = true;
    private Timer timer;
    private Timer musicTimer;
    
    private JMenuBar mbEditor;
    private JMenu menuFile;
    private JMenu menuMap;
    private JMenu menuCharacter;
    private JMenuItem itemNew;
    private JMenuItem itemSave;
    private JMenuItem itemLoad;
    private JMenuItem itemSettings;
    private JMenuItem itemAddEvent;
    private JMenuItem itemRemoveEvent;
    private JMenuItem itemEditCharater;
    private JMenuItem itemAddCharater;
    private JMenuItem itemRemoveCharater;
    private JMenuItem itemCopyCharater;
    private JMenuItem itemEditTalk;
    private JCheckBoxMenuItem itemEditorMode;
    private JCheckBoxMenuItem itemEnableMusic;
    private JMenuItem itemExit;
    
    private static int smallMapSizeX=2;
    private static int smallMapSizeY=2;
    private int[][] smallMapWall;
    private int[][] smallMapObject;
    private int[][] smallMapDecoration;
    private int[][] smallMapTop;
    private boolean smallMapReady;
    
    private Character myChar;
    private int extraSize=0;
    
    private boolean painting = false;
    
    /**
     * Since OpenJDK 1.6 has two AWT-EventQueue these needs to be
     * locked when drawing the map.
     */
    private Semaphore drawingMapLock;

    
    public Editor(boolean noExtraSize) {
          setTitle("Tileset 2D Map Editor");
          setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);         
          addWindowListener(new TWindowListener());
          String jreVer = System.getProperty("java.version");
          
          if (jreVer.startsWith("1.7")) {
            extraSize=20;
            drawingMapLock = null;
          } else {
            drawingMapLock = new Semaphore(1, true);
          }
          if (noExtraSize) {
            extraSize = 0;
          }
          setSize(900, 700+extraSize);
          
          setLocationRelativeTo(null);
          setResizable(false);
          timer = new Timer(75,this);
          timer.setActionCommand("timer");
          musicTimer = new Timer(5000,this);
          musicTimer.setActionCommand("musicTimer");
          myItemIndex = 0;
          myItem = ItemFactory.Create(myItemIndex);
          fcLoad = new JFileChooser();

          mapTileset = new Tileset();
          DataInputStream dis = new DataInputStream(Editor.class.getResourceAsStream(Tileset.TILESET_NAME_RESOURCE[0]));
          mapTileset.loadTileSet(dis);
          itemTileset = new Tileset();
          dis = new DataInputStream(Editor.class.getResourceAsStream(Tileset.TILESET_ITEMS));
          itemTileset.loadTileSet(dis);
          charactersTileset = new Tileset();
          dis = new DataInputStream(Editor.class.getResourceAsStream(Tileset.TILESET_CHARACTERS));
          charactersTileset.loadTileSet(dis);

          myTileScreen = new BufferedImage(Tile.MAX_WIDTH+2, Tile.MAX_HEIGHT+2, BufferedImage.TYPE_INT_ARGB);
          myItemScreen = new BufferedImage(Tile.MAX_WIDTH+2, Tile.MAX_HEIGHT+2, BufferedImage.TYPE_INT_ARGB);
          index = 0;
          myTile = mapTileset.getTile(index);
          myTileInfo = mapTileset.getTileInfo(index);
          smallMapWall = new int[smallMapSizeX][smallMapSizeY];
          smallMapObject = new int[smallMapSizeX][smallMapSizeY];
          smallMapDecoration = new int[smallMapSizeX][smallMapSizeY];
          smallMapTop = new int[smallMapSizeX][smallMapSizeY];
          smallMapReady = false;
          try {
            myMap = new Map(100, 100, 0);
            myMap.setMapSectors(1);
            for (int i=0;i<4;i++) {
              myMap.setNorthDirection(i, Map.NORTH_DIRECTION_UP);
              myMap.setDayShade(i, 0);
              myMap.setNightShade(i, 7);
            }
            myMap.fillMap(0);
            myMap.doShading();
            Map.setItemsTileset(itemTileset);
            Map.setCharactersTileset(charactersTileset);
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();repaint();
          }          
          panelMap = new MapPanel();
          panelMap.setVisible(true);
          panelMap.setEnabled(true);
          //panelMap.setPreferredSize(new Dimension(700,590));
          panelMap.setBackground(Color.black);
          cursorX = 5;
          cursorY = 5;
          panelMap.drawMap(myMap, cursorX, cursorY,true,day);
          panelButtons = new JPanel();
          panelButtons.setSize(190, 600);
          panelButtons.setPreferredSize(new Dimension(190,600));
          panelButtons.setBackground(Color.gray);
          
          labelCoordinates = new JLabel("X:"+String.valueOf(cursorX)+
                                        " Y:"+String.valueOf(cursorY));
          eventX1 = 5;
          eventY1 = 5;
          eventX2 = 5;
          eventY2 = 5;
          copyX1 = 5;
          copyX2 = 5;
          copyY1 = 5;
          copyY2 = 5;
          labelEventCoord1 = new JLabel("EX1:"+String.valueOf(eventX1)+
                                        " EY1:"+String.valueOf(eventY1));
          labelEventCoord2 = new JLabel("EX2:"+String.valueOf(eventX2)+
                                        " EY2:"+String.valueOf(eventY2));
          
          labelItemName = new JLabel(myItem.getItemNameInGame());
          
          labelItemsInMap = new JLabel("");
          addKeyListener(new TAdapter());

          add(panelMap,BorderLayout.CENTER);
          add(panelButtons,BorderLayout.LINE_END);
          mbEditor = new JMenuBar();
          menuFile = new JMenu("File");
          itemNew = new JMenuItem("New map");
          itemNew.setActionCommand("NewMap");
          itemNew.addActionListener(this);
          menuFile.add(itemNew);
          itemLoad = new JMenuItem("Load map");
          itemLoad.setActionCommand(ACTION_LOAD_MAP);
          itemLoad.addActionListener(this);
          menuFile.add(itemLoad);
          itemSave = new JMenuItem("Save map");
          itemSave.setActionCommand(ACTION_SAVE_MAP);
          itemSave.addActionListener(this);
          menuFile.add(itemSave);
          menuFile.addSeparator();
          itemExit = new JMenuItem("Exit editor");
          itemExit.setActionCommand("Exit");
          itemExit.addActionListener(this);
          menuFile.add(itemExit);
          menuMap = new JMenu("Map");
          itemSettings = new JMenuItem("Settings");
          itemSettings.setActionCommand("Settings");
          itemSettings.addActionListener(this);
          menuMap.add(itemSettings);
          itemAddEvent = new JMenuItem("Add/Edit Event");
          itemAddEvent.setActionCommand("AddEvent");
          itemAddEvent.addActionListener(this);          
          menuMap.add(itemAddEvent);
          itemRemoveEvent = new JMenuItem("Remove Event");
          itemRemoveEvent.setActionCommand(ACTION_REMOVE_EVENT);
          itemRemoveEvent.addActionListener(this);
          menuMap.add(itemRemoveEvent);
          itemEditorMode = new JCheckBoxMenuItem("View map in Editor mode", true);
          itemEditorMode.setActionCommand("editorMode");
          itemEditorMode.addActionListener(this);          
          menuMap.add(itemEditorMode);
          itemEnableMusic = new JCheckBoxMenuItem("Enable Music", true);
          itemEnableMusic.setActionCommand("enableMusic");
          itemEnableMusic.addActionListener(this);          
          menuMap.add(itemEnableMusic);
          menuCharacter = new JMenu("Character");
          itemEditCharater = new JMenuItem("Character Editor");
          itemEditCharater.setActionCommand(ACTION_CHARACTER_EDITOR);
          itemEditCharater.addActionListener(this);
          menuCharacter.add(itemEditCharater);
          itemAddCharater = new JMenuItem("Add/Edit");
          itemAddCharater.setActionCommand(ACTION_ADD_CHARACTER);
          itemAddCharater.addActionListener(this);
          itemAddCharater.setEnabled(false);
          menuCharacter.add(itemAddCharater);
          itemRemoveCharater = new JMenuItem("Remove");
          itemRemoveCharater.setActionCommand(ACTION_REMOVE_CHARACTER);
          itemRemoveCharater.addActionListener(this);
          menuCharacter.add(itemRemoveCharater);
          itemCopyCharater = new JMenuItem("Copy");
          itemCopyCharater.setActionCommand(ACTION_COPY_CHARACTER);
          itemCopyCharater.addActionListener(this);
          menuCharacter.add(itemCopyCharater);
          itemEditTalk = new JMenuItem("Talk Editor");
          itemEditTalk.setActionCommand(ACTION_TALK_EDITOR);
          itemEditTalk.addActionListener(this);
          menuCharacter.add(itemEditTalk);
          JMenu menuSpecial = new JMenu("Extra");
          itemNew = new JMenuItem("Export map as PNG");
          itemNew.setActionCommand(ACTION_EXPORT_WHOLE_MAP);
          itemNew.addActionListener(this);
          menuSpecial.add(itemNew);

          mbEditor.add(menuFile);
          mbEditor.add(menuMap);
          mbEditor.add(menuCharacter);
          mbEditor.add(menuSpecial);

          
          panelButtons.add(Box.createRigidArea(new Dimension(150,50)));
          panelButtons.add(labelCoordinates,BorderLayout.CENTER);
          panelButtons.add(labelEventCoord1);
          panelButtons.add(labelEventCoord2);
          panelButtons.add(Box.createRigidArea(new Dimension(180,50)));
          panelButtons.add(labelItemName);
          panelButtons.add(labelItemsInMap);

          myChar = null;
          
          this.setJMenuBar(mbEditor);
          painting = false;
          setVisible(true);
          timer.start();
          musicTimer.start();
          // Final tuning for screen size just for safe
          if (this.getInsets().top > 1 && extraSize == 0) {
            extraSize = 20;
            setSize(920, 700+extraSize);
          }

      }
    
    
    
     


      public static void main(String[] args) {
        boolean noExtraSize = false;
        for (int i=0;i<args.length;i++) {
            if (args[i].equalsIgnoreCase("--noextrasize")) {
              noExtraSize = true;
            } 
                
        }
          new Editor(noExtraSize);
          if (MusicPlayer.isPlaying()) {
            MusicPlayer.stop();
          }
      }


     /**
      * Quick travel to target waypoint if found in same map
      */
     public void quickTravelEvent() {
       int index = myMap.getEvent(cursorX, cursorY);
       if (index != -1) {
         Event event = myMap.getEventByIndex(index);
         if (!event.getEventSound().isEmpty()) {
           SoundPlayer.playSoundBySoundName(event.getEventSound());
         }
         String wp = event.getTargetWaypointName();
         if (wp != null) {
           index = myMap.getEventWaypointByName(wp);
           if (index != -1) {
             event = myMap.getEventByIndex(index);
             cursorX = event.getX();
             cursorY = event.getY();
             myMap.getMyChar().setPosition(cursorX, cursorY);
           }
         }
       }
     }
      
     public void addEditEvent() {
       int index = myMap.getEvent(cursorX, cursorY);
       if (index == -1) {
         MapEvent diagMapEvent = new MapEvent(this,cursorX,cursorY,eventX1,eventY1,
                                                   eventX2,eventY2, true,
                                                   myMap.getListOfWaypoints());
         diagMapEvent.setVisible(true);
         if (diagMapEvent.isOkClicked()) {
           myMap.addNewEvent(diagMapEvent.getX1(), diagMapEvent.getY1(), diagMapEvent.getX2(), diagMapEvent.getY2(),
               diagMapEvent.getName(),diagMapEvent.getCommand(), diagMapEvent.getParam(0), 
               diagMapEvent.getParam(1), diagMapEvent.getParam(2)); 
         }
         diagMapEvent.setVisible(false);
       } else {
         MapEvent diagMapEvent = new MapEvent(this,cursorX,cursorY,myMap.getEventX1(),
             myMap.getEventY1(),myMap.getEventX2(),myMap.getEventY2(), false,
             myMap.getListOfWaypoints());
         diagMapEvent.setValues(myMap.getEventType(), myMap.getEventCommand(),
             myMap.getEventName(), myMap.getEventParameter(0),
             myMap.getEventParameter(1), myMap.getEventParameter(2));
         diagMapEvent.setVisible(true);
         if (diagMapEvent.isOkClicked()) {
           myMap.removeEvent(index);
           myMap.addNewEvent(diagMapEvent.getX1(), diagMapEvent.getY1(), diagMapEvent.getX2(), diagMapEvent.getY2(),
               diagMapEvent.getName(),diagMapEvent.getCommand(), diagMapEvent.getParam(0), 
               diagMapEvent.getParam(1), diagMapEvent.getParam(2)); 
         }
         diagMapEvent.setVisible(false);
       }
     }


      class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

          if (drawingMapLock != null) {
            try {
              drawingMapLock.acquire();
            } catch (InterruptedException e1) {
              //Just continue
            }
          }
          int key = e.getKeyCode();
          if ((key == KeyEvent.VK_UP) &&(cursorY > 0)) {
            cursorY = cursorY -1;
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_DOWN) &&(cursorY < myMap.getMaxY()-1)) {
            cursorY = cursorY +1;
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_LEFT) &&(cursorX > 0)) {
            cursorX = cursorX -1;
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_RIGHT) &&(cursorX < myMap.getMaxX()-1)) {
            cursorX = cursorX +1;
            myMap.forceMapFullRepaint();
          }
          if (key==KeyEvent.VK_E) {
            addEditEvent();
            myMap.forceMapFullRepaint();
          }
          if (key==KeyEvent.VK_Q) {
            quickTravelEvent(); 
            myMap.forceMapFullRepaint();
          }
          if (key==KeyEvent.VK_1) {
            eventX1 = cursorX;
            eventY1 = cursorY;
          }
          if (key==KeyEvent.VK_2) {
            eventX2 = cursorX;
            eventY2 = cursorY;
          }
          if (key==KeyEvent.VK_3) {
            copyX1 = cursorX;
            copyY1 = cursorY;
          }
          if (key==KeyEvent.VK_4) {
            copyX2 = cursorX;
            copyY2 = cursorY;
          }
          if (key==KeyEvent.VK_P) {
            int lenX = copyX2-copyX1+1;
            int lenY = copyY2-copyY1+1;
            if ((lenX>0) && (lenY>0)) {
              for (int i=0;i<lenX;i++) {
                for (int j=0;j<lenY;j++) {
                  if ((cursorX+i < myMap.getMaxX()) && (cursorY+j <myMap.getMaxY())) {
                    myMap.setMap(cursorX+i, cursorY+j, myMap.getFloor(copyX1+i, copyY1+j), TileInfo.TILE_PLACE_WALL_OR_FLOOR);
                    myMap.setMap(cursorX+i, cursorY+j,myMap.getObjects(copyX1+i, copyY1+j),TileInfo.TILE_PLACE_OBJECT);
                    myMap.setMap(cursorX+i, cursorY+j,myMap.getDecoration(copyX1+i, copyY1+j),TileInfo.TILE_PLACE_DECORATION);
                    myMap.setMap(cursorX+i, cursorY+j,myMap.getTop(copyX1+i, copyY1+j),TileInfo.TILE_PLACE_TOP);
                  }
                }
              }
            }
            myMap.forceMapFullRepaint();
          }

          if (key == KeyEvent.VK_PAGE_UP) {
            myItemIndex++;
            myItem = ItemFactory.Create(myItemIndex);
            if (myItem == null) {
              myItemIndex--;
              myItem = ItemFactory.Create(myItemIndex);
            }
          }
          if ((key == KeyEvent.VK_PAGE_DOWN) && (myItemIndex >0)) {
            myItemIndex--;
            myItem = ItemFactory.Create(myItemIndex);
          }
          if (key == KeyEvent.VK_9) {
            //FIXME: Not sure if this affects on anything...
            if (myItem.getItemStatus() == Item.IDENTIFIED_STATUS_KNOWN) {
              myItem.setItemStatus(Item.IDENTIFIED_STATUS_UNKNOWN);
            } else {
              myItem.setItemStatus(Item.IDENTIFIED_STATUS_KNOWN);
            }
          }

          if ((key == KeyEvent.VK_ADD || key == KeyEvent.VK_INSERT) 
              && (index < mapTileset.size())) {
            index++;
            myTile = mapTileset.getTile(index);
            myTileInfo = mapTileset.getTileInfo(index);
          }
          if (key == KeyEvent.VK_MULTIPLY || key == KeyEvent.VK_HOME) {
            int series = mapTileset.getSeries(index);            
            if (index+mapTileset.getSeriesSize(series)<mapTileset.size()) {
              index = index+mapTileset.getSeriesSize(series);
              myTile = mapTileset.getTile(index);
              myTileInfo = mapTileset.getTileInfo(index);
            }
          }
          if (key == KeyEvent.VK_DIVIDE || key == KeyEvent.VK_END) {
            int series = mapTileset.getSeries(index);
            series--;
            if (index-mapTileset.getSeriesSize(series)>-1) {
              index = index-mapTileset.getSeriesSize(series);
              index = mapTileset.getSeriesFirst(index);
              if (index < 0) {
                index = 0;
              }
              myTile = mapTileset.getTile(index);
              myTileInfo = mapTileset.getTileInfo(index);
            }
          }
          if ((key == KeyEvent.VK_SUBTRACT || key == KeyEvent.VK_DELETE)
              && (index > -1)) {
            index--;
            if (index > -1) {
              myTile = mapTileset.getTile(index);
              myTileInfo = mapTileset.getTileInfo(index);
            }
          }
          if ((key == KeyEvent.VK_SPACE)) {
              myMap.setMap(cursorX, cursorY, index);
              myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_D)) {
            myMap.setMap(cursorX, cursorY, index,TileInfo.TILE_PLACE_DECORATION);
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_T)) {
            myMap.setMap(cursorX, cursorY, index,TileInfo.TILE_PLACE_TOP);
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_O)) {
            myMap.setMap(cursorX, cursorY, index,TileInfo.TILE_PLACE_OBJECT);
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_F)) {
            if ((cursorX < myMap.getMaxX()-1)&& (cursorY < myMap.getMaxY()-1)) {
              for (int i=0;i<smallMapSizeX;i++) {
                for (int j=0;j<smallMapSizeY;j++) {
                  smallMapWall[i][j] = myMap.getFloor(cursorX+i, cursorY+j);
                  smallMapObject[i][j] = myMap.getObjects(cursorX+i, cursorY+j);
                  smallMapDecoration[i][j] = myMap.getDecoration(cursorX+i, cursorY+j);
                  smallMapTop[i][j] = myMap.getTop(cursorX+i, cursorY+j);
                }
              }
              smallMapReady = true;
            }
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_G)) {
            index = myMap.getTop(cursorX,cursorY);
            if (index == -1) {
              index = myMap.getDecoration(cursorX,cursorY);
            }
            if (index == -1) {
              index = myMap.getObjects(cursorX,cursorY);
            }
            if (index == -1) {
              index = myMap.getFloor(cursorX,cursorY);
            }
            if (index > -1) {
              myTile = mapTileset.getTile(index);
              myTileInfo = mapTileset.getTileInfo(index);
            }
          }
          if ((key == KeyEvent.VK_H)) {
            myMap.setMap(cursorX, cursorY, index);
            myMap.setMap(cursorX+1, cursorY, index);
            myMap.setMap(cursorX, cursorY+1, index);
            myMap.setMap(cursorX+1, cursorY+1, index);
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_J)) {
            if (smallMapReady) {
              if ((cursorX < myMap.getMaxX()-1)&& (cursorY < myMap.getMaxY()-1)) {
                for (int i=0;i<smallMapSizeX;i++) {
                  for (int j=0;j<smallMapSizeY;j++) {
                    myMap.setMap(cursorX+i, cursorY+j, smallMapWall[i][j],
                        TileInfo.TILE_PLACE_WALL_OR_FLOOR);
                    myMap.setMap(cursorX+i, cursorY+j, smallMapObject[i][j],
                        TileInfo.TILE_PLACE_OBJECT);
                    myMap.setMap(cursorX+i, cursorY+j, smallMapDecoration[i][j],
                        TileInfo.TILE_PLACE_DECORATION);
                    myMap.setMap(cursorX+i, cursorY+j, smallMapTop[i][j],
                        TileInfo.TILE_PLACE_TOP);
                  }
                }
              }
            }
          }
          if ((key == KeyEvent.VK_B)) {
            myMap.setMap(cursorX-1, cursorY-1, index);
            myMap.setMap(cursorX, cursorY-1, index);
            myMap.setMap(cursorX+1, cursorY-1, index);
            myMap.setMap(cursorX-1, cursorY, index);
            myMap.setMap(cursorX, cursorY, index);
            myMap.setMap(cursorX+1, cursorY, index);
            myMap.setMap(cursorX-1, cursorY+1, index);
            myMap.setMap(cursorX, cursorY+1, index);
            myMap.setMap(cursorX+1, cursorY+1, index);
            myMap.forceMapFullRepaint();
          }
          if (key == KeyEvent.VK_I) {            
            myMap.addNewItem(cursorX, cursorY, ItemFactory.copy(myItem));
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_BACK_SPACE)) {
            myMap.setMap(cursorX, cursorY,-1);
            int i = -1;
            do {
              i =myMap.getItemIndexByCoordinates(cursorX, cursorY);
              if (i != -1) {
                  myMap.removeItemByIndex(i);
              }
            }while(i!=-1);
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_C)) {
            if (myTileInfo.getDefaultPlace() == TileInfo.TILE_PLACE_WALL_OR_FLOOR) {
              int sector = myMap.getSectorByCoordinates(cursorX, cursorY);
              myMap.fillSectorFlood(sector, index);
            }
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_S)) {
            myMap.smoothMap(cursorX,cursorY);
            myMap.forceMapFullRepaint();
          }
          if ((key == KeyEvent.VK_V)) {
            myMap.doShading();
            myMap.forceMapFullRepaint();
            repaint();
          }
          if ((key == KeyEvent.VK_N)) {
            if (day) {
              day = false;
            } else {
              day = true;
            }
            myMap.forceMapFullRepaint();
          }
          if (drawingMapLock != null) {
            drawingMapLock.release();
          }

        }
        
    }


    
      
    @Override
      public void paint(Graphics g) {
        if (!painting ) {
          painting = true;
          super.paint(g);
          
          labelCoordinates.setText("Index:"+index+" X:"+String.valueOf(cursorX)+
              " Y:"+String.valueOf(cursorY));
  
          labelEventCoord1.setText("EX1:"+String.valueOf(eventX1)+
              " EY1:"+String.valueOf(eventY1));
          labelEventCoord2.setText("EX2:"+String.valueOf(eventX2)+
              " EY2:"+String.valueOf(eventY2));
          
          labelItemName.setText(myItem.getName()+" ("+myItem.getIndex()+")");
          
          int i =myMap.getItemIndexByCoordinates(cursorX, cursorY);
          if (i != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append(myMap.getItemByIndex(i).getName());
            while (i != -1) {
              i = myMap.getNextItemFromCoordinates(cursorX, cursorY);
              if (i != -1) {
                sb.append("<br>");
                sb.append(myMap.getItemByIndex(i).getName());
              }
            }
            sb.append("</html>");
            labelItemsInMap.setText(sb.toString());
          } else {
            labelItemsInMap.setText("");
          }
          
          Graphics2D gr = myTileScreen.createGraphics();
          gr.setColor(new Color(158,158,158));
          gr.fillRect(1, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
          gr.fillRect(1+Tile.MAX_WIDTH/2, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
          gr.setColor(new Color(104, 104, 104));
          gr.fillRect(1+Tile.MAX_WIDTH/2, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
          gr.fillRect(1, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
          gr.setColor(Color.black);        
          gr.drawRect(0, 0, Tile.MAX_WIDTH+1, Tile.MAX_HEIGHT+1);
          if ((index > -1) && (myTile != null)) {
            myTile.putTile(myTileScreen, 1,1,0);
          }
          g.drawImage(myTileScreen, 720, 35+extraSize, null);

          gr = myItemScreen.createGraphics();
          gr.setColor(new Color(158,158,158));
          gr.fillRect(1, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
          gr.fillRect(1+Tile.MAX_WIDTH/2, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
          gr.setColor(new Color(104, 104, 104));
          gr.fillRect(1+Tile.MAX_WIDTH/2, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
          gr.fillRect(1, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
          gr.setColor(Color.black);        
          gr.drawRect(0, 0, Tile.MAX_WIDTH+1, Tile.MAX_HEIGHT+1);
          if (myItem != null) {
            Tile itemTile = itemTileset.getTile(myItem.getTileNumber());
            itemTile.putTile(myItemScreen, 1,1,0);
          }
          g.drawImage(myItemScreen, 720, 135+extraSize, null);
          
          painting = false;
        }

      }






    @Override
    public void actionPerformed(ActionEvent arg0) {
      if (("timer".equals(arg0.getActionCommand())) && (this.isActive())){
        if (drawingMapLock != null) {
          try {
            drawingMapLock.acquire();
          } catch (InterruptedException e) {
            // Continue running
          }
        }
        myMap.doAnim();
        //myMap.forceMapFullRepaint();
        panelMap.drawMap(myMap, cursorX, cursorY,itemEditorMode.getState(),day);
        this.repaint();
        if (drawingMapLock != null) {
          drawingMapLock.release();
        }
      }
      if ("musicTimer".equals(arg0.getActionCommand())) {
        if (itemEnableMusic.getState()!=MusicPlayer.isMusicEnabled()) {
          MusicPlayer.setMusicEnabled(itemEnableMusic.getState());
        }
        String music;
        if (day) {
          music = myMap.getDayMusic(cursorX, cursorY);
        } else {
          music = myMap.getNightMusic(cursorX, cursorY);
        }
        if (!music.equals(MusicPlayer.getCurrentlyPlaying())) {
          MusicPlayer.play(music);
        }
      }
      if (ACTION_EXPORT_WHOLE_MAP.equalsIgnoreCase(arg0.getActionCommand())) {
        MapUtilities.saveScreenShot(myMap.drawFullMap(true));
      }
      if (ACTION_TALK_EDITOR.equalsIgnoreCase(arg0.getActionCommand())) {
        TalkEditor talkEdit = new TalkEditor(this);
        talkEdit.dispose();
      }
      if (ACTION_SAVE_MAP.equalsIgnoreCase(arg0.getActionCommand())) {
        DataOutputStream os;        
        fcLoad.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fcLoad.showSaveDialog(this);
        if (returnVal == JFileChooser.OPEN_DIALOG) {
            String saveStr = fcLoad.getSelectedFile().getAbsolutePath();
          try {
            os = new DataOutputStream(new FileOutputStream(saveStr));
            myMap.saveMap(os);
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      if (ACTION_LOAD_MAP.equalsIgnoreCase(arg0.getActionCommand())) {
        DataInputStream is;
        fcLoad.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fcLoad.showOpenDialog(this);
        if (returnVal == JFileChooser.OPEN_DIALOG) {
            String saveStr = fcLoad.getSelectedFile().getAbsolutePath();
          try {
            is = new DataInputStream(new FileInputStream(saveStr));
            myMap = new Map(is);
            cursorX = myMap.getMyChar().getX();
            cursorY = myMap.getMyChar().getY();
            if (myChar != null) {
              myChar.setPosition(cursorX, cursorY);
              myMap.setMyChar(myChar);
            }
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        mapTileset = new Tileset();
        DataInputStream dis = new DataInputStream(Editor.class.getResourceAsStream(
            Tileset.TILESET_NAME_RESOURCE[myMap.getMapTilesetIndex()]));
        mapTileset.loadTileSet(dis);
      }
      if (ACTION_CHARACTER_EDITOR.equalsIgnoreCase(arg0.getActionCommand())) {
        CharacterEditor charEditor = new CharacterEditor(this, null, charactersTileset,
            myMap.getListOfWaypoints());
          Character newChar = charEditor.getEditedCharacter();
          if (newChar != null) {
            myChar = new Character(0);
            myChar.copyOf(newChar);          
            myChar.setPosition(cursorX, cursorY);
            itemAddCharater.setEnabled(true);
            myMap.setMyChar(myChar);
          }
          charEditor.dispose();
        
      }
      if (ACTION_ADD_CHARACTER.equals(arg0.getActionCommand())) {
        int index = myMap.findNPCbyCoordinates(cursorX, cursorY);
        if (index != -1) {
          // Edit character
          Character tmpChr = myMap.getNPCbyIndex(index);
          Character newChr = new Character(0);
          newChr.copyOf(tmpChr);
          CharacterEditor charEditor = new CharacterEditor(this, newChr, charactersTileset,
            myMap.getListOfWaypoints());
          Character newChar = charEditor.getEditedCharacter();
          if (newChar != null) {
            newChar.setPosition(cursorX, cursorY);
            myMap.removeNPCbyIndex(index);
            myMap.addNPC(newChar);
          }
          charEditor.dispose();
        } else {
         if (myChar != null) {
           Character tmpChr = new Character(0);
           tmpChr.copyOf(myChar);
           myMap.addNPC(tmpChr);
         }
        }
      }
      if (ACTION_REMOVE_CHARACTER.equalsIgnoreCase(arg0.getActionCommand())) {
        int index = myMap.findNPCbyCoordinates(cursorX, cursorY);
        if (index != -1) {
          myMap.removeNPCbyIndex(index);
        }
        
      }
      if (ACTION_COPY_CHARACTER.equalsIgnoreCase(arg0.getActionCommand())) {
        int index = myMap.findNPCbyCoordinates(cursorX, cursorY);
        if (index != -1) {
          Character tmpChr = myMap.getNPCbyIndex(index);
          myChar = new Character(0);
          myChar.copyOf(tmpChr);          
          myChar.setPosition(cursorX, cursorY);
          itemAddCharater.setEnabled(true);
          myMap.setMyChar(myChar);
        }
        
      }
      if ("AddEvent".equals(arg0.getActionCommand())) {
        addEditEvent();
      }
      if (ACTION_REMOVE_EVENT.equals(arg0.getActionCommand())) {
    	  int index = myMap.getEvent(cursorX, cursorY);
    	  if (index != -1) {
    		  myMap.removeEvent(index);
    	  }
      }
      if ("NewMap".equals(arg0.getActionCommand())) {
        MapSettings mapSet = new MapSettings(this,true,myMap);
        if (mapSet.isOk()) {
          mapTileset = new Tileset();
          myMap.setMapTilesetIndex(mapSet.getTilesetIndex());
          DataInputStream dis = new DataInputStream(Editor.class.getResourceAsStream(mapSet.getTilesetResourceName()));
          mapTileset.loadTileSet(dis);
          index = 0;
          myTile = mapTileset.getTile(index);
          myTileInfo = mapTileset.getTileInfo(index);
          try {
            myMap = new Map(mapSet.getMapSizeX(), mapSet.getMapSizeY(), mapSet.getTilesetIndex());
            myMap.fillMap(0);
            myMap.setMapSectors(mapSet.getNumberOfSector());
            for (int i=0;i<4;i++) {
              myMap.setNorthDirection(i, mapSet.getNorthDirection(i));
              myMap.setDayShade(i, mapSet.getDayShade(i));
              myMap.setNightShade(i, mapSet.getNightShade(i));
              myMap.setDayMusic(i, mapSet.getDayMusic(i));
              myMap.setNightMusic(i, mapSet.getNightMusic(i));
              myMap.setCombatMusic(i, mapSet.getCombatMusic(i));
            }
            myMap.doShading();
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();repaint();
          }
          cursorX = 5;
          cursorY = 5;
        }
        mapSet.dispose();
        
      }
      if ("Settings".equals(arg0.getActionCommand())) {
        MapSettings mapSet = new MapSettings(this,false,myMap);
        if (mapSet.isOk()) {
          myMap.setMapSectors(mapSet.getNumberOfSector());
          for (int i=0;i<4;i++) {
            myMap.setNorthDirection(i, mapSet.getNorthDirection(i));
            myMap.setDayShade(i, mapSet.getDayShade(i));
            myMap.setNightShade(i, mapSet.getNightShade(i));
            myMap.setDayMusic(i, mapSet.getDayMusic(i));
            myMap.setNightMusic(i, mapSet.getNightMusic(i));
            myMap.setCombatMusic(i, mapSet.getCombatMusic(i));

          }
        }
        mapSet.dispose();
      }
      if ("Exit".equals(arg0.getActionCommand())) {
        closeEditor();
      }
    }



    public void closeEditor() {
      System.exit(0);
    }


    class TWindowListener extends WindowAdapter{
      
      @Override
      public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        closeEditor();
      }
      
      @Override
      public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
      }
      
      @Override
      public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
      } 
   }
}


