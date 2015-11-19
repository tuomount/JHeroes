package org.jheroes.gui.panels;


import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.ImageGameButton;
import org.jheroes.gui.labels.GameLabel;

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
 * Panel which has two arrows and label changing between arrows.
 * 
 */
public class PanelWithArrows extends GamePanel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a panel with two arrows and middle label
   * @param border
   * @param middleLabel
   * @param arrowLeftAction
   * @param arrowRightAction
   * @param listener
   */
  public PanelWithArrows(boolean border, GameLabel middleLabel, String arrowLeftAction,
       String arrowRightAction, ActionListener listener) {
    super(border);
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.add(Box.createRigidArea(new Dimension(50, 15)));
    ImageGameButton igButton = new ImageGameButton(GuiStatics.IMAGE_ARROW_LEFT,
        GuiStatics.IMAGE_ARROW_LEFT_PRESSED,false,arrowLeftAction);
    igButton.addActionListener(listener);
    this.add(igButton);
    this.add(Box.createRigidArea(new Dimension(15, 25)));
    if (middleLabel != null) {
      Dimension size = middleLabel.getSize();
      this.add(middleLabel);
      middleLabel.setSize(size);
    }
    this.add(Box.createRigidArea(new Dimension(15, 25)));
    igButton = new ImageGameButton(GuiStatics.IMAGE_ARROW_RIGHT,
        GuiStatics.IMAGE_ARROW_RIGHT_PRESSED,false,arrowRightAction);
    igButton.addActionListener(listener);
    this.add(igButton);
    this.add(Box.createRigidArea(new Dimension(25, 25)));
  }
  
  /**
   * Creates a panel with two arrows and label. After label there is also a help button
   * @param border Panel has borders
   * @param middleLabel
   * @param arrowLeftAction
   * @param arrowRightAction
   * @param helpAction
   * @param listener
   */
  public PanelWithArrows(boolean border, GameLabel middleLabel, String arrowLeftAction,
      String arrowRightAction, String helpAction, ActionListener listener) {
   super(border);
   this.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
   this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
   this.add(Box.createRigidArea(new Dimension(25, 15)));
   ImageGameButton igButton = new ImageGameButton(GuiStatics.IMAGE_ARROW_LEFT,
       GuiStatics.IMAGE_ARROW_LEFT_PRESSED,false,arrowLeftAction);
   igButton.addActionListener(listener);
   this.add(igButton);
   this.add(Box.createRigidArea(new Dimension(10, 25)));
   if (middleLabel != null) {
     Dimension size = middleLabel.getSize();
     this.add(middleLabel);
     middleLabel.setSize(size);
   }
   this.add(Box.createRigidArea(new Dimension(10, 25)));
   igButton = new ImageGameButton(GuiStatics.IMAGE_INFO, 
              GuiStatics.IMAGE_INFO_PRESS, false, helpAction);
   igButton.addActionListener(listener);
   this.add(igButton);
   this.add(Box.createRigidArea(new Dimension(10, 25)));
   igButton = new ImageGameButton(GuiStatics.IMAGE_ARROW_RIGHT,
       GuiStatics.IMAGE_ARROW_RIGHT_PRESSED,false,arrowRightAction);
   igButton.addActionListener(listener);
   this.add(igButton);
   this.add(Box.createRigidArea(new Dimension(25, 25)));
 }

}
