package org.jheroes.mapeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jheroes.musicplayer.MusicPlayer;

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
 * Sector editor
 * 
 **/
public class SectorEdit extends JPanel {
   
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private JComboBox cbNorthSelection;
  private JComboBox cbDayShade;
  private JComboBox cbNightShade;
  private JComboBox cbDayMusic;
  private JComboBox cbNightMusic;
  private JComboBox cbCombatMusic;
  private static String[] shades = {"0","1","2","3","4","5","6","7"};
  private static String[] northDirection = {"Up","Right","Down","Left"};
  public SectorEdit(String Title,int north,int dayShade, int nightShade,
      String dayMusic, String nightMusic, String combatMusic) {
    super();
    this.setLayout(new GridLayout(0,2));
    this.setMaximumSize(new Dimension(250, 200));
    this.setMinimumSize(new Dimension(250, 200));
    this.setPreferredSize(new Dimension(250,200));
    this.setBorder(BorderFactory.createLineBorder(Color.black));
    JLabel labelTitle = new JLabel(Title);
    this.add(labelTitle);
    JLabel labelTitleEmpty = new JLabel("  ");
    this.add(labelTitleEmpty);
    JLabel labelNorth = new JLabel("North Direction:");
    this.add(labelNorth);
    cbNorthSelection = new JComboBox(northDirection);
    cbNorthSelection.setSelectedIndex(north);
    this.add(cbNorthSelection);
    
    JLabel labelDS = new JLabel("Day Shade:");
    this.add(labelDS);
    cbDayShade = new JComboBox(shades);
    cbDayShade.setSelectedIndex(dayShade);
    this.add(cbDayShade,BorderLayout.CENTER);

    JLabel labelNS = new JLabel("Night Shade:");
    this.add(labelNS,BorderLayout.SOUTH);
    cbNightShade = new JComboBox(shades);
    cbNightShade.setSelectedIndex(nightShade);
    this.add(cbNightShade,BorderLayout.SOUTH);
    
    JLabel labelDM = new JLabel("Day Music:");
    this.add(labelDM,BorderLayout.SOUTH);
    cbDayMusic = new JComboBox(MusicPlayer.MUSIC_FILES_NAMES);
    cbDayMusic.setSelectedIndex(MusicPlayer.findMusicFileByName(dayMusic));
    this.add(cbDayMusic,BorderLayout.SOUTH);

    JLabel labelNM = new JLabel("Night Music:");
    this.add(labelNM,BorderLayout.SOUTH);
    cbNightMusic = new JComboBox(MusicPlayer.MUSIC_FILES_NAMES);
    cbNightMusic.setSelectedIndex(MusicPlayer.findMusicFileByName(nightMusic));
    this.add(cbNightMusic,BorderLayout.SOUTH);

    JLabel labelCM = new JLabel("Combat Music:");
    this.add(labelCM,BorderLayout.SOUTH);
    cbCombatMusic = new JComboBox(MusicPlayer.MUSIC_FILES_NAMES);
    cbCombatMusic.setSelectedIndex(MusicPlayer.findMusicFileByName(combatMusic));
    this.add(cbCombatMusic,BorderLayout.SOUTH);

    this.setVisible(true);
  }
  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    cbNightShade.setEnabled(enabled);
    cbDayShade.setEnabled(enabled);
    cbNorthSelection.setEnabled(enabled);
  }
  
  /**
   * Get Night shade
   * @return int 0-7
   */
  public int getNightShade() {
    return cbNightShade.getSelectedIndex();
  }

  /**
   * Get day shade
   * @return int 0-7
   */
  public int getDayShade() {
    return cbDayShade.getSelectedIndex();
  }

  /**
   * Get north direction
   * @return 0 up, 1 right, 2 down, 3 left
   */
  public int getNorthDirection() {
    return cbNorthSelection.getSelectedIndex();
  }
  
  /**
   * Get Day music
   * @return int
   */
  public int getDayMusic() {
    return cbDayMusic.getSelectedIndex();
  }

  /**
   * Get Night music
   * @return int
   */
  public int getNightMusic() {
    return cbNightMusic.getSelectedIndex();
  }

  /**
   * Get Combat music
   * @return int
   */
  public int getCombatMusic() {
    return cbCombatMusic.getSelectedIndex();
  }

}
