package org.jheroes.gui.labels;


import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import org.jheroes.gui.GuiStatics;

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
 * Progress bar as label
 * 
 */
public class ProgressBar extends JLabel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int progress;
  
  public ProgressBar() {
    super();
    progress = 0;
    Dimension size = new Dimension();
    size.width =  500;
    size.height = 30;
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
  }
  
  public void setProgress(int value) {
    if ((value >=0) && (value < 101)) {
      progress = value;
    }
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    g.setColor(GuiStatics.COLOR_BLACK);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    g.setColor(GuiStatics.COLOR_GRAY);
    g.drawRect(0, 0,this.getWidth()-1, this.getHeight()-1);
    g.setColor(GuiStatics.COLOR_WHITE);
    g.drawRect(1, 1,this.getWidth()-3, this.getHeight()-3);
    g.setColor(GuiStatics.COLOR_GRAY);
    g.drawRect(2, 2,this.getWidth()-4, this.getHeight()-4);
    
 
    GradientPaint  gradient = new GradientPaint(0,0, GuiStatics.COLOR_GREEN,
        this.getWidth(),this.getHeight(), GuiStatics.COLOR_DARK_GREEN, true);
    Graphics2D g2d = (Graphics2D)g;
    
    int maxWidth = this.getWidth()-4;
    int width = (maxWidth*progress)/100;
    g2d.setPaint(gradient);    
    g2d.fillRect(3, 3, width, this.getHeight()-5);
    
  }

}
