package org.jheroes.gui.panels;


import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

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
 * Game Panel, panel which can have nice gradient paint.
 * 
 * 
 */
public class GamePanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private int gradientColor = GuiStatics.GRADIENT_COLOR_BROWN;

  public GamePanel(boolean border) {
    super();
    this.setBackground(GuiStatics.COLOR_WHITE);
    if (border) {
      Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
      this.setBorder(etchedBorder);
    }
  }  
  
  /**
   * See GuiStatics gradient colors
   * GRADIENT_COLOR_BROWN = 0;
   * GRADIENT_COLOR_BLUE = 1;
   * GRADIENT_COLOR_GREEN = 2;
   * GRADIENT_COLOR_CYAN = 3;
   * @param gradientColor
   */
  public void setGradientColor(int gradientColor) {
    this.gradientColor = gradientColor;
  }

  public int getGradientColor() {
    return gradientColor;
  }

  @Override
  protected void paintComponent(Graphics g) {
    //super.paintComponent(g);
    GradientPaint gradient = new GradientPaint(0,0, GuiStatics.COLOR_DARK_BROWNISH,
        this.getWidth(),this.getHeight(), GuiStatics.COLOR_BROWNISH_BACKGROUND, true);
    switch (gradientColor) {
    case GuiStatics.GRADIENT_COLOR_BROWN: 
      {  gradient = new GradientPaint(0,0, GuiStatics.COLOR_DARK_BROWNISH,
          this.getWidth(),this.getHeight(), GuiStatics.COLOR_BROWNISH_BACKGROUND, true);
         break;
      }
    case GuiStatics.GRADIENT_COLOR_GREEN: 
    {  gradient = new GradientPaint(0,0, GuiStatics.COLOR_GREEN,
        this.getWidth(),this.getHeight(), GuiStatics.COLOR_DARK_GREEN, true);
       break;
    }
    case GuiStatics.GRADIENT_COLOR_BLUE: 
    {  gradient = new GradientPaint(0,0, GuiStatics.COLOR_BLUE,
        this.getWidth(),this.getHeight(), GuiStatics.COLOR_DARK_BLUE, true);
       break;
    }
    case GuiStatics.GRADIENT_COLOR_CYAN: 
    {  gradient = new GradientPaint(0,0, GuiStatics.COLOR_CYAN,
        this.getWidth(),this.getHeight(), GuiStatics.COLOR_DARK_CYAN, true);
       break;
    }
    case GuiStatics.GRADIENT_COLOR_RED: 
    {  gradient = new GradientPaint(0,0, GuiStatics.COLOR_RED,
        this.getWidth(),this.getHeight(), GuiStatics.COLOR_DARK_RED, true);
       break;
    }
    }
    Graphics2D g2d = (Graphics2D)g;
    if (gradientColor != GuiStatics.GRADIENT_COLOR_INVISIBLE) {
      g2d.setPaint(gradient);    
      g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
  }
  
}
