package org.jheroes.gui.panels;


import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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
 * Panel with label and button
 * 
 */
public class PanelWithButton extends GamePanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private ImageGameButton igButton;
  
  private ImageGameButton igButton2;

  /**
   * Constructs Panel with single button
   * @param border
   * @param middleLabel
   * @param buttonAction
   * @param image
   * @param imagePressed
   * @param listener
   */
  public PanelWithButton(boolean border, GameLabel middleLabel, String buttonAction,
       BufferedImage image,BufferedImage imagePressed, ActionListener listener) {
    super(border);
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.add(Box.createRigidArea(new Dimension(50, 25)));
    igButton = new ImageGameButton(image,imagePressed,false,
        buttonAction);
    igButton.addActionListener(listener);
    this.add(igButton);
    this.add(Box.createRigidArea(new Dimension(5, 25)));
    if (middleLabel != null) {
      Dimension size = new Dimension(270, 25);
      middleLabel.setLeftAlignment(true);      
      this.add(middleLabel);
      middleLabel.setSize(size);
      middleLabel.setMinimumSize(size);
      middleLabel.setMaximumSize(size);
      middleLabel.setPreferredSize(size);
    }
    this.add(Box.createRigidArea(new Dimension(5, 25)));
    setButtonEnabled(true);
  }

  public void addSecondButton(String buttonAction,
       BufferedImage image,BufferedImage imagePressed, ActionListener listener) {
    igButton2 = new ImageGameButton(image,imagePressed,false,
        buttonAction);
    igButton2.addActionListener(listener);
    this.add(igButton2);
    this.add(Box.createRigidArea(new Dimension(5, 25)));
    setButton2Enabled(true);
    
  }
  
  public boolean isButtonEnabled() {
    return igButton.isEnabled();
  }

  public void setButtonEnabled(boolean buttonEnabled) {
    igButton.setEnabled(buttonEnabled);
  }

  public boolean isButton2Enabled() {
    return igButton2.isEnabled();
  }

  public void setButton2Enabled(boolean buttonEnabled) {
    igButton2.setEnabled(buttonEnabled);
  }

}
