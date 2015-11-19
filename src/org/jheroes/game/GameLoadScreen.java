package org.jheroes.game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.labels.ProgressBar;
import org.jheroes.gui.panels.GamePanel;
import org.jheroes.map.DiceGenerator;
import org.jheroes.map.Event;




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
 * Screen which actually does loading or saving the game
 * 
 */
public class GameLoadScreen extends GamePanel {

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private GameTextArea loadText;
  private ProgressBar progBar;
  private int count;
  private int readyCount=0;
  private boolean ready;
  private int task;
  private Event event;
  
  public GameLoadScreen(String text, int task) {
    super(true);
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    this.setLayout(new BorderLayout());
    GamePanel center = new GamePanel(false);
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    center.add(Box.createRigidArea(new Dimension(50,200)));
    center.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameLabel label = new GameLabel(text);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    center.add(label);
    progBar = new ProgressBar();
    progBar.setAlignmentX(Component.CENTER_ALIGNMENT);
    center.add(progBar);
    center.add(Box.createRigidArea(new Dimension(50,400)));
    
    loadText = new GameTextArea();
    loadText.setEditable(false);
    loadText.setWrapStyleWord(true);
    loadText.setLineWrap(true);
    loadText.setRows(4);
    int index = DiceGenerator.getRandom(GameTexts.LOAD_TEXTS.length-1);
    loadText.setText(GameTexts.LOAD_TEXTS[index]);
    this.add(loadText,BorderLayout.SOUTH);
    this.add(center,BorderLayout.CENTER);
    count =0;
    readyCount = 0;
    ready = false;
    setTask(task);
  }
  
  public void updateAll(int progress) {
    progBar.setProgress(progress);
    count++;
    if (count == 60) {
      int index = DiceGenerator.getRandom(GameTexts.LOAD_TEXTS.length-1);
      loadText.setText(GameTexts.LOAD_TEXTS[index]);
      if (progress >= 100) {
        ready = true;
      }
      count = 0;
    }
    if (Game.runningJar && progress >= 100) {
      readyCount++;
      if (readyCount > 10) {
        ready = true;
      }
    }
    this.repaint();
  }
  
  public boolean isReady() {
    return ready;
  }

  /**
   * Get task what is being done
   * @return GameSaveAndLoad.TASKS
   */
  public int getTask() {
    return task;
  }

  private void setTask(int task) {
    this.task = task;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}
