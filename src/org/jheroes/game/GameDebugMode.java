package org.jheroes.game;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.labels.GameLabel;
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
 * Game Debug screen
 */ 
public class GameDebugMode extends GamePanel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private ActionListener listener;
  private JList list;
  private Party party;
  
  /**
   * Constructor for GameDebugMode
   * @param party Party
   * @param listener ActionListener
   */
  public GameDebugMode(Party party,ActionListener listener) {
    super(true);
    this.listener = listener;
    this.party = party;
    this.setLayout(new BorderLayout());
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameLabel label = new GameLabel("DebugMode");
    this.add(label,BorderLayout.NORTH);
    GamePanel btnPane = new GamePanel(true);    
    btnPane.setLayout(new BorderLayout());
    btnPane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameButton btn = new GameButton("Exit", ActionCommands.DEBUGMODE_EXIT);
    btn.addActionListener(this.listener);
    btnPane.add(btn,BorderLayout.CENTER);
    btn = new GameButton("+", ActionCommands.DEBUGMODE_PLUS);
    btn.addActionListener(this.listener);
    btnPane.add(btn,BorderLayout.WEST);
    btn = new GameButton("-", ActionCommands.DEBUGMODE_MINUS);
    btn.addActionListener(this.listener);
    btnPane.add(btn,BorderLayout.EAST);
    String[] data = new String[Party.MAX_STORY_VARIABLE];
    for (int i=0;i<Party.MAX_STORY_VARIABLE;i++) {
      data[i] = "StoryVar["+i+"]="+party.getStoryVariable(i)+" ("+party.getDebugInfo(i) +")";
    }
    list = new JList(data);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scroll = new JScrollPane(list);
    this.add(scroll,BorderLayout.WEST);
    this.add(btnPane,BorderLayout.SOUTH);
    
  }
  
  public int getSelectedStoryVar() {
    return list.getSelectedIndex();
  }
  
  public void updateList() {
    String[] data = new String[Party.MAX_STORY_VARIABLE];
    for (int i=0;i<Party.MAX_STORY_VARIABLE;i++) {
      data[i] = "StoryVar["+i+"]="+party.getStoryVariable(i)+" ("+party.getDebugInfo(i) +")";
    }
    list.setListData(data);
  }
  
}
