package org.jheroes.gui.panels;


import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jheroes.game.DebugOutput;
import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.map.Event;


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
 * When player is about to travel and question is asked then this panel is used.
 * 
 */
public class GameTravelPanel extends GamePanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Event event;
  
  private BufferedImage image;
  
  public GameTravelPanel(String question,Event event, ActionListener listener) {
    super(false);
    this.setEvent(event);
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    this.setLayout(new BorderLayout());
    GameLabel label = new GameLabel("Travelling");
    if (event.getEventCommand() == Event.COMMAND_TYPE_YESNO_QUESTION) {
      label.setText("Question");
    }
    this.add(label,BorderLayout.NORTH);
    String[] results = question.split("#");    
    if (results.length == 2) {
       question = results[0];
       setImage(results[1]);
    }
    GameTextArea text = new GameTextArea(question);
    text.setEditable(false);
    text.setLineWrap(true);
    text.setWrapStyleWord(true);
    this.add(text,BorderLayout.CENTER);
    
    GamePanel buttonPane = new GamePanel(true);
    buttonPane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    buttonPane.setLayout(new BorderLayout());
    GameButton btn = new GameButton("Yes", ActionCommands.GAME_TRAVEL_YES);
    btn.addActionListener(listener);
    buttonPane.add(btn,BorderLayout.WEST);
    btn = new GameButton("No", ActionCommands.GAME_TRAVEL_NO);
    btn.addActionListener(listener);
    buttonPane.add(btn,BorderLayout.EAST);
    this.add(buttonPane,BorderLayout.SOUTH);
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
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
        this.image = ImageIO.read(GameTravelPanel.class.getResource(imageFilename));
      } catch (IOException e){
        this.image = null;
        DebugOutput.debugLog("Failed reading image: "+imageFilename+"\nError:"+e.getMessage());
      }
    }  catch (IllegalArgumentException e){
      this.image = null;
      DebugOutput.debugLog("Failed reading image: "+imageFilename+"\nError:"+e.getMessage());      
    }
  }
  
}
