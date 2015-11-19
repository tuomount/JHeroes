package org.jheroes.mapeditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jheroes.map.Map;
import org.jheroes.musicplayer.MusicPlayer;
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
 * Editor for map settings
 * 
 **/
public class MapSettings extends JDialog implements ActionListener {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private boolean newMap;
  private boolean okClicked=false;
  
  private NumberSpinner spinMapX;
  private NumberSpinner spinMapY;
  private JPanel panelSettings;
  private JComboBox cbTileset;
  private JComboBox cbNumberOfSectors;
  private SectorEdit seSector1;
  private SectorEdit seSector2;
  private SectorEdit seSector3;
  private SectorEdit seSector4;
  
  private static String[] numSecs = {"1","2","4"};
  
  public MapSettings(JFrame parent, boolean newMap, Map myMap) {   
    super(parent, "Map Settings", true);
    this.newMap = newMap;
    if (this.newMap) {
      this.setTitle("Creating a new map");
    } else {
      this.setTitle("Changing map settings");
    }
    panelSettings = new JPanel();
    panelSettings.setLayout(new BorderLayout());
    getContentPane().add(panelSettings);
    cbTileset = new JComboBox(Tileset.TILESET_NAME);
    cbNumberOfSectors = new JComboBox(numSecs);
    cbNumberOfSectors.setActionCommand("numOfSectors");
    cbNumberOfSectors.addActionListener(this);    
    Point location = parent.getLocationOnScreen();
    location.x = location.x+parent.getWidth()/4;
    location.y = location.y+parent.getHeight()/4;
    this.setLocation(location);
    this.setSize(650, 550);
    this.setResizable(false);    
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    panelSettings.add(topPanel(myMap),BorderLayout.NORTH);
    panelSettings.add(centerPanel(myMap),BorderLayout.CENTER);
    
    JButton okBtn = new JButton("Ok");
    okBtn.setActionCommand("ok");
    okBtn.addActionListener(this);
    panelSettings.add(okBtn,BorderLayout.SOUTH);

    switch (myMap.getMapSectors()) {
    case 1: cbNumberOfSectors.setSelectedItem(numSecs[0]); break;
    case 2: cbNumberOfSectors.setSelectedItem(numSecs[1]); break;
    case 4: cbNumberOfSectors.setSelectedItem(numSecs[2]); break;
    }

    setVisible(true);
    
  }

  private JPanel centerPanel(Map myMap) {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(2, 2));
    seSector1 = new SectorEdit("Sector 1",myMap.getNorthDirection(0), 
        myMap.getDayShade(0), myMap.getNightShade(0),
        myMap.getDayMusicBySector(0),
        myMap.getNightMusicBySector(0),
        myMap.getCombatMusicBySector(0));
    result.add(seSector1,BorderLayout.CENTER);
    
    seSector2 = new SectorEdit("Sector 2",myMap.getNorthDirection(1), 
        myMap.getDayShade(1), myMap.getNightShade(1),
        myMap.getDayMusicBySector(1),
        myMap.getNightMusicBySector(1),
        myMap.getCombatMusicBySector(1));
    seSector2.setEnabled(false);
    result.add(seSector2,BorderLayout.CENTER);
    
    seSector3 = new SectorEdit("Sector 3",myMap.getNorthDirection(2), 
        myMap.getDayShade(2), myMap.getNightShade(2),
        myMap.getDayMusicBySector(2),
        myMap.getNightMusicBySector(2),
        myMap.getCombatMusicBySector(2));
    seSector3.setEnabled(false);
    result.add(seSector3,BorderLayout.CENTER);
    
    seSector4 = new SectorEdit("Sector 4",myMap.getNorthDirection(3), 
        myMap.getDayShade(3), myMap.getNightShade(3),
        myMap.getDayMusicBySector(3),
        myMap.getNightMusicBySector(3),
        myMap.getCombatMusicBySector(3));
    seSector4.setEnabled(false);
    result.add(seSector4,BorderLayout.CENTER);
    return result;
  }
  private JPanel topPanel(Map myMap) {
    JPanel result = new JPanel();
    JLabel labelMapSizeX = new JLabel("Map size (x):");
    result.add(labelMapSizeX,BorderLayout.CENTER);
    spinMapX = new NumberSpinner(myMap.getMaxX(), 32, 255, 1);
    spinMapX.setEnabled(this.newMap);
    result.add(spinMapX,BorderLayout.CENTER);
    JLabel labelMapSizeY = new JLabel("Map size (y):");
    result.add(labelMapSizeY,BorderLayout.CENTER);
    spinMapY = new NumberSpinner(myMap.getMaxY(), 32, 255, 1);
    spinMapY.setEnabled(this.newMap);
    result.add(spinMapY,BorderLayout.CENTER);
    JLabel labelMapTileSet = new JLabel("Tileset:");
    result.add(labelMapTileSet,BorderLayout.CENTER);
    cbTileset.setEnabled(this.newMap);
    result.add(cbTileset,BorderLayout.CENTER);
    JLabel labelNumberOfSector = new JLabel("Sectors:");
    result.add(labelNumberOfSector,BorderLayout.CENTER);
    result.add(cbNumberOfSectors,BorderLayout.CENTER);
    //result.add(Box.createRigidArea(new Dimension(150,30)));
    return result;
  }
  
  /**
   * Get night shade for one sector
   * @param sector 1-4
   * @return
   */
  public int getNightShade(int sector) {
    int result = 7;
    switch (sector) {
    case 0: result = seSector1.getNightShade(); break;
    case 1: result = seSector2.getNightShade(); break;
    case 2: result = seSector3.getNightShade(); break;
    case 3: result = seSector4.getNightShade(); break;
    }
    return result;
  }

  /**
   * Get Day shade for one sector
   * @param sector 1-4
   * @return
   */
  public int getDayShade(int sector) {
    int result = 0;
    switch (sector) {
    case 0: result = seSector1.getDayShade(); break;
    case 1: result = seSector2.getDayShade(); break;
    case 2: result = seSector3.getDayShade(); break;
    case 3: result = seSector4.getDayShade(); break;
    }
    return result;
  }

  /**
   * Get North Direction for sector
   * @param sector 1-4
   * @return
   */
  public int getNorthDirection(int sector) {
    int result = 0;
    switch (sector) {
    case 0: result = seSector1.getNorthDirection(); break;
    case 1: result = seSector2.getNorthDirection(); break;
    case 2: result = seSector3.getNorthDirection(); break;
    case 3: result = seSector4.getNorthDirection(); break;
    }
    return result;
  }

  /**
   * Get Day music for sector
   * @param sector 1-4
   * @return String
   */
  public String getDayMusic(int sector) {
    String result = "";
    switch (sector) {
    case 0: result = MusicPlayer.MUSIC_FILES[seSector1.getDayMusic()]; break;
    case 1: result = MusicPlayer.MUSIC_FILES[seSector2.getDayMusic()]; break;
    case 2: result = MusicPlayer.MUSIC_FILES[seSector3.getDayMusic()]; break;
    case 3: result = MusicPlayer.MUSIC_FILES[seSector4.getDayMusic()]; break;
    }
    return result;
    
  }

  /**
   * Get Night music for sector
   * @param sector 1-4
   * @return String
   */
  public String getNightMusic(int sector) {
    String result = "";
    switch (sector) {
    case 0: result = MusicPlayer.MUSIC_FILES[seSector1.getNightMusic()]; break;
    case 1: result = MusicPlayer.MUSIC_FILES[seSector2.getNightMusic()]; break;
    case 2: result = MusicPlayer.MUSIC_FILES[seSector3.getNightMusic()]; break;
    case 3: result = MusicPlayer.MUSIC_FILES[seSector4.getNightMusic()]; break;
    }
    return result;
    
  }

  /**
   * Get Combat music for sector
   * @param sector 1-4
   * @return String
   */
  public String getCombatMusic(int sector) {
    String result = "";
    switch (sector) {
    case 0: result = MusicPlayer.MUSIC_FILES[seSector1.getCombatMusic()]; break;
    case 1: result = MusicPlayer.MUSIC_FILES[seSector2.getCombatMusic()]; break;
    case 2: result = MusicPlayer.MUSIC_FILES[seSector3.getCombatMusic()]; break;
    case 3: result = MusicPlayer.MUSIC_FILES[seSector4.getCombatMusic()]; break;
    }
    return result;
    
  }
  
  /**
   * Number of sectors
   * @return
   */
  public int getNumberOfSector() {
    int result = 1;
    switch (cbNumberOfSectors.getSelectedIndex()) {
    case 0: {     result = 1;
            break;}
    case 1: {    result = 2;
            break;}
    case 2: {     result = 4;
            break;}
    }
    return result;
  }
  
  /**
   * Get Map X size
   * @return
   */
  public int getMapSizeX() {
    return spinMapX.getSpinValue();
  }
  
  /**
   * Get Map y size
   * @return
   */
  public int getMapSizeY() {
    return spinMapY.getSpinValue();
  }
  
  /**
   * Return tileset index
   * @return
   */
  public int getTilesetIndex() {
    if (cbTileset.getSelectedIndex() < Tileset.TILESET_NAME_RESOURCE.length) {
      return cbTileset.getSelectedIndex();
    } else {
      return 0;
    }
  }
  
  /**
   * Get tileset resource name
   * @return
   */
  public String getTilesetResourceName() {
    if (cbTileset.getSelectedIndex() < Tileset.TILESET_NAME_RESOURCE.length) {
      return Tileset.TILESET_NAME_RESOURCE[ cbTileset.getSelectedIndex()];
    } else {
      return Tileset.TILESET_NAME_RESOURCE[0];
    }
  }
  @Override
  public void actionPerformed(ActionEvent arg0) {
    
    if ("numOfSectors".equals(arg0.getActionCommand())) {
      switch (cbNumberOfSectors.getSelectedIndex()) {
      case 0: {     seSector2.setEnabled(false);
                    seSector3.setEnabled(false);
                    seSector4.setEnabled(false);
              break;}
      case 1: {     seSector2.setEnabled(true);
                    seSector3.setEnabled(false);
                    seSector4.setEnabled(false);
              break;}
      case 2: {     seSector2.setEnabled(true);
                    seSector3.setEnabled(true);
                    seSector4.setEnabled(true);
              break;}
      }
    }
    if ("ok".equals(arg0.getActionCommand())) {
      okClicked = true;
      setVisible(false);
    }
    
  }
  
  public boolean isOk() {
    return okClicked;
  }

}
