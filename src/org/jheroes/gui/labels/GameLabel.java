package org.jheroes.gui.labels;


import java.awt.Dimension;
import java.awt.Graphics;

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
 * Game label with with font
 */
public class GameLabel extends JLabel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final int Y_OFFSET = 6;
  
  
  private boolean leftAlignment;
  
  private int rasterFontType;
  
  /**
   * Game Label with white font
   * @param text String
   */
  public GameLabel(String text) {
    super(text);
    Dimension size = new Dimension();
    size.width = getText().length()*15;
    size.height = 25;
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
    this.setForeground(GuiStatics.COLOR_WHITE);
    this.setFont(GuiStatics.FONT_NORMAL);
    this.setLeftAlignment(false);
    this.setRasterFontType(RasterFonts.FONT_TYPE_WHITE);
  }
  
  
  
  @Override
  protected void paintComponent(Graphics g) {
    g.setFont(this.getFont());
    int textLen = getText().length();
    if (textLen % 2 == 0) {      
      textLen = textLen/2*this.getFont().getSize()/2+this.getFont().getSize();
    } else {
      textLen = textLen -1;
      textLen = textLen/2*this.getFont().getSize()/2+this.getFont().getSize()+this.getFont().getSize()/2;
    }
      
    g.setColor(GuiStatics.COLOR_GRAY_BACKGROUND);    
    int x = getWidth()/2-textLen;
    if(this.isLeftAlignment()) {
      x = 0;
    }
    int y = getHeight()/2+Y_OFFSET;
    if (x < 1) {
      x = 0;
    }
    if (y < 1) {
      y = 0;
    }
    if (getRasterFontType() != RasterFonts.FONT_TYPE_SYSTEM) {
      RasterFonts fonts = new RasterFonts(g);
      fonts.setFontType(getRasterFontType());
      x = getWidth()/2-fonts.getTextWidth(getText())/2;
      if(this.isLeftAlignment()) {
        x = 0;
      }
      if (x < 1) {
        x = 0;
      }
      fonts.drawString(x, 2, getText());
    } else {
      g.drawString(getText(), x+1, y+1);
      g.setColor(this.getForeground());
      g.drawString(getText(), x, y);
    }
  }



  public boolean isLeftAlignment() {
    return leftAlignment;
  }



  public void setLeftAlignment(boolean leftAlignment) {
    this.leftAlignment = leftAlignment;
  }



  public int getRasterFontType() {
    return rasterFontType;
  }



  public void setRasterFontType(int rasterFontType) {
    this.rasterFontType = rasterFontType;
  }
}
