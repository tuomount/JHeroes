package org.jheroes.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.lists.QuestList;
import org.jheroes.gui.panels.GamePanel;
import org.jheroes.journal.Journal;
import org.jheroes.journal.Quest;



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
 * Journal used to keep track of quests in game
 * 
 */
public class GameJournal extends GamePanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Journal journal;
  private ActionListener listener;
  private QuestList list;
  private GameTextArea area;
  private int entryNum;
  private GameButton nextBtn;
  private GameButton prevBtn;
  private Quest oldSelection;
  
  public GameJournal(Journal journal,ActionListener listener) {
    super(true);
    this.journal = journal;
    this.listener = listener;
    this.setLayout(new BorderLayout());
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    oldSelection = null;
    entryNum = -1;
    GameLabel label = new GameLabel("Journal");
    this.add(label,BorderLayout.NORTH);
    GameButton btn = new GameButton("Exit", ActionCommands.JOURNAL_EXIT);
    btn.addActionListener(this.listener);
    this.add(btn,BorderLayout.SOUTH);
    list = new QuestList(this.journal.getQuestsSorted());
    JScrollPane scroll = new JScrollPane(list);
    this.add(scroll,BorderLayout.WEST);
    
    GamePanel journalPane = new GamePanel(true);    
    journalPane.setLayout(new BorderLayout());
    journalPane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);    
    area = new GameTextArea();
    area.setLineWrap(true);
    area.setWrapStyleWord(true);
    area.setEditable(false);
    journalPane.add(area,BorderLayout.CENTER);
    
    GamePanel bottom = new GamePanel(false);
    bottom.setLayout(new BorderLayout());
    bottom.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    prevBtn = new GameButton("Previous entry",ActionCommands.JOURNAL_PREV);
    prevBtn.addActionListener(this.listener);
    bottom.add(prevBtn,BorderLayout.WEST);
    nextBtn = new GameButton("Next entry",ActionCommands.JOURNAL_NEXT);
    nextBtn.addActionListener(this.listener);
    bottom.add(nextBtn,BorderLayout.EAST);
    journalPane.add(bottom,BorderLayout.SOUTH);
    
    this.add(journalPane,BorderLayout.CENTER);
    updateArea();
  }
  
  private void updateArea() {
    if (entryNum != -1) {
      Quest quest = (Quest) list.getSelectedValue();
      if (quest!=null) {
        area.setText(quest.getQuestEntry(entryNum));
        if (entryNum ==quest.sizeOfQuestEntries()-1) {
          nextBtn.setEnabled(false);
        } else {
          nextBtn.setEnabled(true);
        }
        if (entryNum ==0) {
          prevBtn.setEnabled(false);
        } else {
          prevBtn.setEnabled(true);
        }
      } else {
        prevBtn.setEnabled(false);
        nextBtn.setEnabled(false);
      }
    } else {
      prevBtn.setEnabled(false);
      nextBtn.setEnabled(false);      
    }
  }
  
  public void handleActions(ActionEvent arg0) {
    if (arg0.getActionCommand().equals(ActionCommands.GENERIC_TIMER)) {
      Quest quest = (Quest) list.getSelectedValue();
      if (quest != null) {
        if (oldSelection != quest) {
          oldSelection = quest;
          entryNum = quest.sizeOfQuestEntries()-1;
          updateArea();
          repaint();
        }
      }
    }
    if (ActionCommands.JOURNAL_NEXT.equals(arg0.getActionCommand())) {
      entryNum++;
      updateArea();
      repaint();
    }
    if (ActionCommands.JOURNAL_PREV.equals(arg0.getActionCommand())) {
      entryNum--;
      updateArea();
      repaint();
    }
  }
}
