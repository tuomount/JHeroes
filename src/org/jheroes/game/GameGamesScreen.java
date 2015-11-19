package org.jheroes.game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.buttons.ImageGameButton;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.panels.GamePanel;
import org.jheroes.map.Party;



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
 * Game screen used when loading or saving game
 * 
 */
public class GameGamesScreen extends GamePanel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Maximum number of saved games
   */
  private static final int MAX_GAMES = 6; 
  
  /**
   * Description about the saved/loaded game
   */
  private GameTextArea[] gameDesc;
  /**
   * Button containing screen shot from the saved/loaded game
   */
  private ImageGameButton[] gameBtn;  
  
  
  public GameGamesScreen(boolean loading, ActionListener listener) {
    super(true);
    this.setLayout(new BorderLayout());
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameLabel label = null;
    if (loading) {
      label =  new GameLabel("Load game");
      label.setAlignmentX(Component.CENTER_ALIGNMENT);
    } else {
      label =  new GameLabel("Save game");
      label.setAlignmentX(Component.CENTER_ALIGNMENT);            
    }
    this.add(label,BorderLayout.NORTH);
    GameButton btn = new GameButton("Cancel", ActionCommands.GAMES_SCREEN_CANCEL);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(btn,BorderLayout.SOUTH);
    
    GamePanel center = new GamePanel(true);
    center.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    gameBtn = new ImageGameButton[MAX_GAMES];
    gameDesc = new GameTextArea[MAX_GAMES];
    
    for (int i =0;i<MAX_GAMES;i++) {
      String folderName = "save"+String.valueOf(i+1);
      BufferedImage image = GuiStatics.IMAGE_NO_FACE;
      if (GameMaps.isSaveFolderThere(folderName)) {
        File file = new File(folderName+"/"+GameMaps.SCREEN_FILE);
        try {
          image = ImageIO.read(file);
          
        } catch (IOException e) {
          image = GuiStatics.IMAGE_NO_FACE;
        }
        int width = 460;
        int heigth = 350;
        BufferedImage scaledDown = new BufferedImage(width, heigth, image.getType()); 
        Graphics2D g = scaledDown.createGraphics();
        g.drawImage(image, 0, 0, width, heigth, null);
        g.dispose();
        if (loading) {
          gameBtn[i] = new ImageGameButton(scaledDown, scaledDown, false, ActionCommands.GAMES_SCREEN_LOAD[i]);
        } else {
          gameBtn[i] = new ImageGameButton(scaledDown, scaledDown, false, ActionCommands.GAMES_SCREEN_SAVE[i]);
        }
        try {
          Party tmpParty = GameMaps.readPartyFromSave(folderName);
          
          gameDesc[i] = new GameTextArea("Save "+String.valueOf(i+1)+":\n"+tmpParty.getPartyMembersAsString()+
              "\nDate: "+tmpParty.getDateAsString()+"\n"+tmpParty.getTimeAsString(false)+
              "\nPlaying time: "+tmpParty.getTotalPlayingTimeAsString());
        } catch (IOException e) {
          gameDesc[i] = new GameTextArea("READ ERROR!");
        }
      } else {
        gameDesc[i] = new GameTextArea("Save "+String.valueOf(i+1)+": Empty");
        image = GuiStatics.IMAGE_NO_FACE;
        if (loading) {
          gameBtn[i] = new ImageGameButton(image, image, false, ActionCommands.GAMES_SCREEN_LOAD[i]);
        } else {
          gameBtn[i] = new ImageGameButton(image, image, false, ActionCommands.GAMES_SCREEN_SAVE[i]);
        }
      }
      gameBtn[i].addActionListener(listener);
      gameDesc[i].setLineWrap(true);
      gameDesc[i].setWrapStyleWord(true);
      gameDesc[i].setEditable(false);
      gameDesc[i].setRows(4);
      GamePanel gamePane = new GamePanel(true);
      gamePane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
      gamePane.setLayout(new BoxLayout(gamePane, BoxLayout.X_AXIS));
      gamePane.add(gameBtn[i]);
      gamePane.add(gameDesc[i]);
      center.add(gamePane);
    }
    
    JScrollPane scroll = new JScrollPane(center);
    scroll.getVerticalScrollBar().setUnitIncrement(32);    
    this.add(scroll,BorderLayout.CENTER);
  }
 
}
