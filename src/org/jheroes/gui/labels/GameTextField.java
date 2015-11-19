package org.jheroes.gui.labels;


import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

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
 * Single line text where player can write something
 *
 */
public class GameTextField extends JTextField {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private boolean blinking = false;

  public GameTextField() {
    super();
    Dimension size = this.getPreferredSize();
    size.width = 10*16;
    size.height = 25;
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setForeground(GuiStatics.COLOR_WHITE);
    this.setBackground(GuiStatics.COLOR_BLACK);
  }
  
  public GameTextField(String text){
    super(text);
    Dimension size = this.getPreferredSize();
    size.width = getText().length()*16;
    size.height = 25;
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setForeground(GuiStatics.COLOR_WHITE);
    this.setBackground(GuiStatics.COLOR_BLACK);
  }
  
  /**
   * Set Text Field width in characters
   * @param i number of characters
   */
  public void setTextFieldWidth(int i) {
    Dimension size = this.getPreferredSize();
    size.width = i*16;
    size.height = 25;
    this.setMinimumSize(size);
    this.setPreferredSize(size);
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    this.setBorder(BorderFactory.createEmptyBorder());
    
    g.setColor(this.getBackground());
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setFont(GuiStatics.FONT_NORMAL);    
    int textLen = getText().length();
    if (textLen % 2 == 0) {
      textLen = textLen/2*11+3;
    } else {
      textLen = textLen -1;
      textLen = textLen/2*11+8;
    }
      
   
    g.setColor(GuiStatics.COLOR_GRAY_BACKGROUND);
    StringBuilder sb = new StringBuilder(getText());
    if ((this.hasFocus()) && (this.isEditable())) {
      if (blinking) {
        blinking = false;
      } else {
        blinking = true;
        sb.insert(this.getCaretPosition(), '|');
      }
    }
    RasterFonts fonts = new RasterFonts(g);
    fonts.setFontType(RasterFonts.FONT_TYPE_SMALL);
    fonts.drawString((getWidth()/2-textLen), 5, sb.toString());


    g.setColor(GuiStatics.COLOR_GOLD);
    g.drawLine(0, 0, getWidth(), 0);
    g.drawLine(0, 0, 0, getHeight());
    g.drawLine(0, getHeight()-2, getWidth(), getHeight()-2);
    g.drawLine(getWidth()-2, 0, getWidth()-2, getHeight());
    g.setColor(GuiStatics.COLOR_GOLD_DARK);
    g.drawLine(1, 1, getWidth()-2, 1);
    g.drawLine(1, 1, 1, getHeight()-2);
    g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
    g.drawLine(getWidth()-1, 0, getWidth()-1, getHeight());
    
  }
}
