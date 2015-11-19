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
 * Menu button used in menus. Slightly bigger than game button
 * 
 */
public class MenuButton extends JButton {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  public MenuButton(String text, String actionCommand) {
    super(text);
    this.setActionCommand(actionCommand);
    this.setBackground(GuiStatics.COLOR_BROWNISH_BACKGROUND);
    this.setForeground(GuiStatics.COLOR_DARK_BROWNISH);
    this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    Dimension size = this.getPreferredSize();
    size.width = getText().length()*26;
    size.height = 44;
    this.setMinimumSize(size);
    this.setPreferredSize(size);
  }

  
  @Override
  protected void paintComponent(Graphics g) {
    if (this.isEnabled()) {
      this.setBackground(GuiStatics.COLOR_BROWNISH_BACKGROUND);
      this.setForeground(GuiStatics.COLOR_DARK_BROWNISH);
      if (this.getModel().isPressed()) {
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
      } else {
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
      }
      //super.paintComponent(g);
      if (this.getModel().isRollover()) {
        g.setColor(GuiStatics.COLOR_BROWNISH);
        g.fillRect(0, 0, getWidth(), getHeight());
      } else {
        g.setColor(GuiStatics.COLOR_DARK_BROWNISH);
        g.fillRect(0, 0, getWidth(), getHeight());
      }
      g.setFont(GuiStatics.FONT_BIG);
      int textLen = getText().length();
      if (textLen % 2 == 0) {
        textLen = textLen/2*22+3;
      } else {
        textLen = textLen -1;
        textLen = textLen/2*22+8;
      }
        
      RasterFonts fonts = new RasterFonts(g);
      fonts.setFontType(RasterFonts.FONT_TYPE_BIG);
      fonts.drawString((getWidth()/2-textLen)+1, 5, getText());
    } else {
      this.setBackground(GuiStatics.COLOR_GRAY_BACKGROUND);
      this.setForeground(GuiStatics.COLOR_GRAY);
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
      g.setColor(GuiStatics.COLOR_GRAY_BACKGROUND);
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setFont(GuiStatics.FONT_BIG);
      int textLen = getText().length();
      if (textLen % 2 == 0) {
        textLen = textLen/2*22+3;
      } else {
        textLen = textLen -1;
        textLen = textLen/2*22+8;
      }
      RasterFonts fonts = new RasterFonts(g);
      fonts.setFontType(RasterFonts.FONT_TYPE_BIG);
      fonts.drawString((getWidth()/2-textLen)+1, 5, getText());
/*      g.setColor(GuiStatics.COLOR_BLACK);
      g.drawString(getText(), (getWidth()/2-textLen)+1, getHeight()/2+Y_OFFSET+1);
      g.setColor(GuiStatics.COLOR_GRAY);
      g.drawString(getText(), (getWidth()/2-textLen), getHeight()/2+Y_OFFSET);*/
    }
    
    
  }

  
}
