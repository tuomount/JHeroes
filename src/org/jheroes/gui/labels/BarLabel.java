package org.jheroes.gui.labels;

import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JLabel;

import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.RasterFonts;

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
 * Bar having a text and value printed on.
 * 
 */
public class BarLabel extends JLabel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final int offset_x=35;
  private int max;
  private int current;
  private int gradientColor;
  public BarLabel(String text, int max, int current) {
    super(text);
    if ((max > 0) && (current >= 0) && (current <= max)) {
    this.max = max;
    this.current= current;
    } else {
      this.max = 10;
      this.current = 5;      
    }
    gradientColor = GuiStatics.GRADIENT_COLOR_CYAN;
    Dimension size = new Dimension();
    size.width =  125;
    size.height = 25;
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
    this.setForeground(GuiStatics.COLOR_WHITE);
    this.setBackground(GuiStatics.COLOR_BLACK);
  }

  
  
  @Override
  public String getToolTipText() {
    StringBuilder sb = new StringBuilder(10);
    sb.append(String.valueOf(current));
    sb.append("/");
    sb.append(String.valueOf(max));
    return sb.toString();
  }



  @Override
  protected void paintComponent(Graphics g) {
    g.setFont(GuiStatics.FONT_NORMAL);
    g.setColor(GuiStatics.COLOR_BLACK);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
/*    g.setColor(GuiStatics.COLOR_GRAY);
    g.drawString(getText(), 3, 19);
    g.setColor(GuiStatics.COLOR_WHITE);
    g.drawString(getText(),2, 18);*/
    RasterFonts fonts = new RasterFonts(g);
    fonts.setFontType(RasterFonts.FONT_TYPE_SMALL);
    fonts.drawString(3, 5, getText());
    g.setColor(GuiStatics.COLOR_WHITE);
    g.drawRect(0, 0,offset_x, this.getHeight()-1);
    g.drawRect(offset_x, 0, this.getWidth()-offset_x-1, this.getHeight()-1);
    
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
    int maxWidth = this.getWidth()-offset_x-2;
    int width = (current*maxWidth)/max;
    g2d.setPaint(gradient);    
    g2d.fillRect(offset_x+1, 1, width, this.getHeight()-2);
    g.setFont(GuiStatics.FONT_SMALL);
    g.setColor(GuiStatics.COLOR_GRAY);
    g.drawString(getToolTipText(),offset_x+6, 19);
    g.setColor(GuiStatics.COLOR_WHITE);
    g.drawString(getToolTipText(),offset_x+5, 18);
    
  }

  
  /**
   * Get Maximum value
   * @return int
   */
  public int getMax() {
    return max;
  }
  /**
   * Set maximum value
   * @param max int
   */
  public void setMax(int max) {
    if (max >= 0)
    this.max = max;
  }
  /**
   * Get Current value
   * @return int
   */
  public int getCurrent() {
    return current;
  }
  /**
   * Set current value, must be between min and max
   * @param current
   */
  public void setCurrent(int current) {
    if ((current >= 0) && (current <= max))
    this.current = current;
  }
  
  public int getGradientColor() {
    return gradientColor;
  }
  public void setGradientColor(int gradientColor) {
    this.gradientColor = gradientColor;
  }
}
