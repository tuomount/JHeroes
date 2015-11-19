package org.jheroes.gui.buttons;


import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

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
 * Game button which is brownish with golden font
 * 
 */
public class GameButton extends JButton {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * Game Button with brown background and golder font
   * @param text Text shown in button
   * @param actionCommand Action command as String
   */
  public GameButton(String text, String actionCommand) {
    super(text);
    this.setActionCommand(actionCommand);
    this.setBackground(GuiStatics.COLOR_BROWNISH_BACKGROUND);
    this.setForeground(GuiStatics.COLOR_DARK_BROWNISH);
    this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    Dimension size = this.getPreferredSize();
    size.width = getText().length()*20;
    size.height = 25;
    this.setMinimumSize(size);
    this.setPreferredSize(size);
  }

  
  @Override
  protected void paintComponent(Graphics g) {
    if (this.getModel().isPressed()) {
      this.setBackground(GuiStatics.COLOR_BROWNISH_BACKGROUND);
      this.setForeground(GuiStatics.COLOR_DARK_BROWNISH);
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      
    } else {
      if (this.isEnabled()) {
        this.setBackground(GuiStatics.COLOR_BROWNISH_BACKGROUND);
        this.setForeground(GuiStatics.COLOR_DARK_BROWNISH);
      } else {
        this.setBackground(GuiStatics.COLOR_GRAY_BACKGROUND);
        this.setForeground(GuiStatics.COLOR_GRAY);
      }
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }
    //super.paintComponent(g);
    if (this.isEnabled())
    {
      if (this.getModel().isRollover()) {
        g.setColor(GuiStatics.COLOR_BLUE);
        g.fillRect(0, 0, getWidth(), getHeight());
      } else {
        g.setColor(GuiStatics.COLOR_DARK_BLUE);
        g.fillRect(0, 0, getWidth(), getHeight());
      }
      int textLen = getText().length();
      if (textLen % 2 == 0) {
        textLen = textLen/2*10+3;
      } else {
        textLen = textLen -1;
        textLen = textLen/2*10+8;
      }
        
/*      if (this.getModel().isRollover()) {
        g.setColor(GuiStatics.COLOR_BROWNISH);
        g.drawString(getText(), (getWidth()/2-textLen)-1, getHeight()/2+Y_OFFSET);
        g.drawString(getText(), (getWidth()/2-textLen)+1, getHeight()/2+Y_OFFSET);
        g.drawString(getText(), (getWidth()/2-textLen), getHeight()/2+Y_OFFSET-1);
        g.drawString(getText(), (getWidth()/2-textLen), getHeight()/2+Y_OFFSET+1);
        g.setColor(GuiStatics.COLOR_DARK_BROWNISH);
        g.drawString(getText(), (getWidth()/2-textLen), getHeight()/2+Y_OFFSET);
         
      } else {
        g.setColor(GuiStatics.COLOR_BLACK);
        g.drawString(getText(), (getWidth()/2-textLen)+1, getHeight()/2+Y_OFFSET+1);
        g.setColor(GuiStatics.COLOR_DARK_BROWNISH);
        g.drawString(getText(), (getWidth()/2-textLen), getHeight()/2+Y_OFFSET);
      }*/
      RasterFonts fonts = new RasterFonts(g);
      fonts.setFontType(RasterFonts.FONT_TYPE_SMALL);
      fonts.drawString((getWidth()/2-textLen)+1, 5, getText());

    } else {
      g.setColor(GuiStatics.COLOR_GRAY);
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setFont(GuiStatics.FONT_NORMAL);
      int textLen = getText().length();
      if (textLen % 2 == 0) {
        textLen = textLen/2*10+3;
      } else {
        textLen = textLen -1;
        textLen = textLen/2*10+8;
      }
      
      RasterFonts fonts = new RasterFonts(g);
      fonts.setFontType(RasterFonts.FONT_TYPE_SMALL);
      fonts.drawString((getWidth()/2-textLen)+1, 5, getText());

/*      g.setColor(GuiStatics.COLOR_BLACK);
      g.drawString(getText(), (getWidth()/2-textLen)+1, getHeight()/2+Y_OFFSET+1);
      g.setColor(GuiStatics.COLOR_GRAY_BACKGROUND);
      g.drawString(getText(), (getWidth()/2-textLen), getHeight()/2+Y_OFFSET);*/
      
      
    }
    
  }

}
