package org.jheroes.gui.labels;


import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

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
 * Generic Text Area used into to game various place. Can be both editable, non
 * editable. Can scroll smoothly.
 * 
 */
public class GameTextArea extends JTextArea {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final int Y_OFFSET = 5;
  private boolean blinking = false;
  private boolean autoScroll = false;
  private String[] scrollText;
  private int numberOfLines=0;
  private int currentLine= 0;
  private boolean smoothScroll=false;
  private boolean smoothScrollNextRow=false;
  
  public boolean isSmoothScroll() {
    return smoothScroll;
  }


  public void setSmoothScroll(boolean smoothScroll) {
    this.smoothScroll = smoothScroll;
  }


  private int smoothScrollY=0;
  
  public GameTextArea() {
    super(20,25);
    this.setForeground(GuiStatics.COLOR_WHITE);
    this.setBackground(GuiStatics.COLOR_BLACK);
    autoScroll = false;
  }
  
  
  public GameTextArea(String text){
    super(text);
    this.setForeground(GuiStatics.COLOR_WHITE);
    this.setBackground(GuiStatics.COLOR_BLACK);
    autoScroll = false;
  }
  
  public void disableScrollText() {
    autoScroll = false;
  }
  
  /**
   * Scroll to next line
   */
  public void getNextLine() {
    currentLine++;
    if(currentLine >= scrollText.length) {
      currentLine =0;
    }
  }

  /**
   * Scroll to previous line
   */
  public void getPrevLine() {
    currentLine--;
    if(currentLine < 0) {
      currentLine =scrollText.length-1;
    }
  }

  /**
   * Set Scrollable text
   * @param text String
   * @param lines number of shown lines
   */
  public void setScrollText(String text, int lines) {
    if (text != null) {
      scrollText = text.split("\n");
      autoScroll = true;
      numberOfLines = lines;
      currentLine = 0;
    }
  }
  
  private int customCharWidth = -1;
  public void setCharacterWidth(int width) {
    if (width < 1) {
      customCharWidth = -1;
    } else {
      customCharWidth = width;
    }
  }
  
  @Override
  public void paintImmediately(int x, int y, int w, int h) {
    super.paintImmediately(0, 0, getWidth(), getHeight());
  }


  @Override
  protected void paintComponent(Graphics g) {
    this.setBorder(BorderFactory.createEmptyBorder());
    this.setCaretPosition(this.getDocument().getLength());  
    g.setColor(Color.black);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setFont(GuiStatics.FONT_NORMAL);    
    if (getText() != null ) {
      StringBuilder sb = new StringBuilder();
      if (autoScroll == false) {
        sb = new StringBuilder(this.getText());
      } else {
        for (int i=0;i<numberOfLines;i++) {
          if (i+currentLine<scrollText.length) {
            sb.append(scrollText[i+currentLine]+"\n");
          }
        }
      }
      if (this.getLineWrap()== true) {
        int lastSpace = -1;
        int rowLen = 0;
        int maxRowLen = (this.getWidth())/12;
        if (customCharWidth > 0) {
          maxRowLen = (this.getWidth())/customCharWidth;
        } 
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
              
            } else {
              sb.setCharAt(i, '\n');
              lastSpace = -1;
              rowLen=0;
            }
          }
        }
      }
      if ((this.hasFocus()) && (this.isEditable())) {
        if (blinking) {
          blinking = false; 
        } else {
          blinking = true;
          sb.insert(this.getCaretPosition(), '|');
        }
      }
      String[] texts = sb.toString().split("\n");
      for (int i=0;i<texts.length;i++) {
        g.setColor(GuiStatics.COLOR_GRAY_BACKGROUND);
        
/*          g.drawString(texts[i], 3, i*Y_OFFSET+1+Y_OFFSET);
          g.setColor(this.getForeground());
          g.drawString(texts[i], 2, i*Y_OFFSET+Y_OFFSET);*/
        RasterFonts fonts = new RasterFonts(g);
        fonts.setFontType(RasterFonts.FONT_TYPE_SMALL);
        if (!smoothScroll) {
          fonts.drawString(5, i*18+Y_OFFSET, texts[i]);
        } else {
          fonts.drawString(5, i*18+Y_OFFSET-smoothScrollY, texts[i]);
        }
        
      }
    }
    smoothScrollY++;
    if (smoothScrollY==18) {
      smoothScrollY=0;
      smoothScrollNextRow = true;
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
  
  public boolean getSmoothScrollNextRow() {
    if (smoothScrollNextRow) {
      smoothScrollNextRow = false;
      return true;
    } else {
      return false;
    }
  }
  

}
