package org.jheroes.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.lists.StringList;
import org.jheroes.gui.panels.GamePanel;


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
 * Ingame help
 * 
 */
public class GameHelp extends GamePanel {

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private ActionListener listener;
  private StringList list;
  private GameTextArea area;

  
  
  public GameHelp(ActionListener listener) {
    super(true);
    this.listener = listener;
    this.setLayout(new BorderLayout());
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameLabel label = new GameLabel("Game Help");
    this.add(label,BorderLayout.NORTH);
    GameButton btn = new GameButton("Exit", ActionCommands.GAMEHELP_EXIT);
    btn.addActionListener(this.listener);
    this.add(btn,BorderLayout.SOUTH);
    String[] helpTopics = new String[6];
    helpTopics[0] = "Keys";
    helpTopics[1] = "Attributes";
    helpTopics[2] = "Skills";
    helpTopics[3] = "Perks";
    helpTopics[4] = "Leveling up";
    helpTopics[5] = "Tips and Hints";
    list = new StringList(helpTopics);
    list.setActionCommand(ActionCommands.GAMEHELP_SELECT);
    list.addActionListener(listener);   
    this.add(list,BorderLayout.WEST);
    
    GamePanel journalPane = new GamePanel(true);    
    journalPane.setLayout(new BorderLayout());
    journalPane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);    
    area = new GameTextArea();
    area.setLineWrap(true);
    area.setWrapStyleWord(true);
    area.setEditable(false);
    area.setText(GameTexts.HELP_TEXT_KEYS);
    journalPane.add(area,BorderLayout.CENTER);
    
    GamePanel bottom = new GamePanel(false);
    bottom.setLayout(new BorderLayout());
    bottom.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    
    this.add(journalPane,BorderLayout.CENTER);
  }
  
  /**
   * Request focus for the list
   */
  public void requestFocusAtStart() {
    list.requestFocusInWindow();
  }
  
  public void actionHandler(ActionEvent arg0) {
   if (arg0.getActionCommand().equals(ActionCommands.GAMEHELP_SELECT)) {
     switch (list.getSelectedIndex()) {
     case 0:area.setText(GameTexts.HELP_TEXT_KEYS); break;
     case 1:area.setText(GameTexts.HELP_TEXT_ATTRIBUTES); break;
     case 2:area.setText(GameTexts.HELP_TEXT_SKILLS); break;
     case 3:area.setText(GameTexts.HELP_TEXT_PERKS); break;
     case 4:area.setText(GameTexts.HELP_TEXT_LEVELING_UP); break;
     case 5:area.setText(GameTexts.HELP_TEXT_HOW_TO_START); break;
     }
   }
  }
}
