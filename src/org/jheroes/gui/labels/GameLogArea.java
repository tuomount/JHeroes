package org.jheroes.gui.labels;


import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;

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
 * Game log area which can used to shown game events as texts.
 * 
 */
public class GameLogArea extends GameTextArea {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private static final int Y_OFFSET = 5;
  private static final int NUMBER_OF_ROWS=19;
  /**
   * Constructor for Game Log Area
   */
  public GameLogArea() {
    super();
    this.setEditable(false);
    this.setLineWrap(true);
  }
  
  /**
   * Update Log
   * @param str String Array
   */
  public void updateLog(String[] str) {
    StringBuilder sb = new StringBuilder(100);
    if (str!=null) {
      for (int i=0;i<str.length;i++) {
        if (str[i]!=null) {
          sb.append(str[i]);
          sb.append("\n");
        }
      }
    }
    this.setText(sb.toString());
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    this.setBorder(BorderFactory.createEmptyBorder());
    this.setCaretPosition(this.getDocument().getLength());  
    g.setColor(Color.black);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setFont(GuiStatics.FONT_NORMAL);    
    if (getText() != null ) {
      StringBuilder sb = new StringBuilder(this.getText());      
      if (this.getLineWrap()== true) {
        int lastSpace = -1;
        int rowLen = 0;
        int maxRowLen = (this.getWidth())/11;
        for (int i=0;i<sb.length();i++) {
          if (sb.charAt(i)==' ') {
            lastSpace = i;
          }
          if (sb.charAt(i)=='\n') {
            lastSpace = -1;
            rowLen=0;
          } else {
            rowLen++;
          }
          if (rowLen > maxRowLen) {
            if (lastSpace != -1) {
              sb.setCharAt(lastSpace, '\n');
              rowLen=i-lastSpace;
              lastSpace = -1;
            }
          }
        }
      }
      
      String[] texts = sb.toString().split("\n");
      int start = 0;
      if (texts.length > NUMBER_OF_ROWS) {
        start = texts.length-NUMBER_OF_ROWS;
      }
      int j =0;
      for (int i=start;i<texts.length;i++) {
        g.setColor(GuiStatics.COLOR_GRAY_BACKGROUND);
        
        /*g.drawString(texts[i], 3, j*Y_OFFSET+1+Y_OFFSET);
        g.setColor(this.getForeground());
        g.drawString(texts[i], 2, j*Y_OFFSET+Y_OFFSET);*/
        RasterFonts fonts = new RasterFonts(g);
        fonts.setFontType(RasterFonts.FONT_TYPE_SMALL);
        fonts.drawString(4, j*18+Y_OFFSET, texts[i]);
        j++;
      }
    }

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
