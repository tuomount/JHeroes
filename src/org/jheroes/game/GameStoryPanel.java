package org.jheroes.game;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.jheroes.game.storyscreen.StoryScreen;
import org.jheroes.game.storyscreen.StoryScreenData;
import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.MenuButton;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.labels.ImageLabel;
import org.jheroes.gui.panels.GamePanel;


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
 * 
 * Game Panel which show Story screens
 *
 */
public class GameStoryPanel extends GamePanel {

  /**
   * ImageLabel for story image 
   */
  private ImageLabel imLabel;
  
  /**
   * GameTextArea for story text
   */
  private GameTextArea textArea;
  
  /**
   * Story Screen Data
   */
  private StoryScreenData screenData;
  
  /**
   * Story Screen
   */
  private StoryScreen story;
  
  private static final long serialVersionUID = 7947719676708954224L;

  /**
   * Constructor
   * @param border Is border or not
   * @param story StoryScreen to use
   * @param listener ActionListener from the game
   */
  public GameStoryPanel(boolean border, StoryScreen story,ActionListener listener) {
    super(border);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    this.story = story;
    screenData = this.story.getFirstStoryScreen();
    imLabel = new ImageLabel(screenData.getImage(), true);
    imLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(imLabel);
    this.add(Box.createRigidArea(new Dimension(5, 5)));
    
    textArea = new GameTextArea();
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setText(screenData.getText());
    textArea.setMaximumSize(new Dimension(800,170));
    this.add(textArea);
    this.add(Box.createRigidArea(new Dimension(5, 5)));
    
    MenuButton continueBtn = new MenuButton("Continue",
        ActionCommands.ACTION_STORY_CONTINUE);    
    continueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    continueBtn.setMaximumSize(new Dimension(200,20));
    continueBtn.addActionListener(listener);
    this.add(continueBtn);
  }
  
  /**
   * Update Story Panel
   * @return true if story still continues, false if story has ended
   */
  public boolean updateStoryPanel() {
    screenData = this.story.getNextStoryScreen();
    if (screenData != null) {
      imLabel.setImage(screenData.getImage());
      textArea.setText(screenData.getText());
      return true;
    } else {
      return false;
    }
  }

}
