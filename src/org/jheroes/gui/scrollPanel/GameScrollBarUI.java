package org.jheroes.gui.scrollPanel;


import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.ImageGameButton;

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
 * Scroll bar UI overwrites
 * 
 */
public class GameScrollBarUI extends BasicScrollBarUI {

  
  /**
   * Creates a new createUI for component
   *
   * @param c JComponent
   * @return GameScrollBarUI
   */
  public static ComponentUI createUI(JComponent c) {
      return new GameScrollBarUI();
  }
  
  public GameScrollBarUI() {
    super();
  }
  
  

  @Override
  protected JButton createDecreaseButton(int orientation) {
    if (orientation == SwingConstants.NORTH) {
      JButton result = new ImageGameButton(GuiStatics.IMAGE_ARROW_UP,
        GuiStatics.IMAGE_ARROW_UP_PRESSED, false, ""); 
      return result;
    } else {
      JButton result = new ImageGameButton(GuiStatics.IMAGE_SCROLL_ARROW_LEFT,
          GuiStatics.IMAGE_SCROLL_ARROW_LEFT_PRESSED, false, ""); 
        return result;
    }
  }

  @Override
  protected JButton createIncreaseButton(int orientation) {
    if (orientation == SwingConstants.SOUTH) {
      JButton result = new ImageGameButton(GuiStatics.IMAGE_ARROW_DOWN,
           GuiStatics.IMAGE_ARROW_DOWN_PRESSED, false, ""); 
      return result;
    } else {
      JButton result = new ImageGameButton(GuiStatics.IMAGE_SCROLL_ARROW_RIGHT,
          GuiStatics.IMAGE_SCROLL_ARROW_RIGHT_PRESSED, false, ""); 
     return result;      
    }
  }

  @Override
  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
    if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
      int y = incrButton.getHeight()+1;
      int distance=scrollbar.getHeight()-incrButton.getHeight()-decrButton.getHeight();
      y = y+distance*scrollbar.getValue()/(scrollbar.getMaximum()-scrollbar.getMinimum());
      int picHeight = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getHeight();      
      int endHeight = thumbBounds.height/2;
      int bars = 0;
      if (endHeight > picHeight/2) {
        endHeight = thumbBounds.height/3;
        if (endHeight > 32) {
          bars = thumbBounds.height/32;
          bars=bars-2;
          endHeight = (thumbBounds.height-bars*32)/2;
        } else {
          bars = 1;
        }
      }
      if (endHeight > GuiStatics.IMAGE_SCROLL_BAR_THUMB.getHeight()-16) {
        endHeight = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getHeight()-16;
      }
      
      BufferedImage top = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getSubimage(0, 0, 
          GuiStatics.IMAGE_SCROLL_BAR_THUMB.getWidth(), endHeight);
      BufferedImage bottom = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getSubimage(0,GuiStatics.IMAGE_SCROLL_BAR_THUMB.getHeight()-endHeight, 
          GuiStatics.IMAGE_SCROLL_BAR_THUMB.getWidth(), endHeight);
      BufferedImage middle = GuiStatics.IMAGE_SCROLL_BAR_THUMB.getSubimage(0, 16, 
          GuiStatics.IMAGE_SCROLL_BAR_THUMB.getWidth(), 32);
      g.drawImage(top,0,y,null);
      if (bars > 0) {
        for (int i=0;i<bars;i++) {
          g.drawImage(middle,0,y+endHeight+i*32,null);

        }
      }
      g.drawImage(bottom,0,y+endHeight+bars*32-1,null);
    } else {
      int x = incrButton.getWidth()+1;
      int distance=scrollbar.getWidth()-incrButton.getWidth()-decrButton.getWidth();
      x = x+distance*scrollbar.getValue()/(scrollbar.getMaximum()-scrollbar.getMinimum());
      int picWidth = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getHeight();      
      int endWidth = thumbBounds.width/2;
      int bars = 0;
      if (endWidth > picWidth/2) {
        endWidth = thumbBounds.width/3;
        if (endWidth > 32) {
          bars = thumbBounds.width/32;
          bars=bars-2;
          endWidth = (thumbBounds.width-bars*32)/2;
        } else {
          bars = 1;
        }
      }
      if (endWidth > GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getWidth()-16) {
        endWidth = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getWidth()-16;
      }
      
      BufferedImage left = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getSubimage(0, 0, 
          endWidth,GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getHeight());
      BufferedImage right = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getSubimage(GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getWidth()-endWidth,0, 
          endWidth, GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getHeight());
      BufferedImage middle = GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getSubimage(16,0, 
          32,GuiStatics.IMAGE_SCROLL_BAR_THUMB2.getHeight());
      g.drawImage(left,x,0,null);
      if (bars > 0) {
        for (int i=0;i<bars;i++) {
          g.drawImage(middle,x+endWidth+i*32,0,null);

        }
      }
      g.drawImage(right,x+endWidth+bars*32-1,0,null);
    }
  }

  @Override
  protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
      GradientPaint gradient = new GradientPaint(0,0, GuiStatics.COLOR_GRAY,
          trackBounds.width,trackBounds.height, GuiStatics.COLOR_GRAY_BACKGROUND, true);
      Graphics2D g2d = (Graphics2D)g;
      g2d.setPaint(gradient);    
      g2d.fillRect(0, 0, scrollbar.getWidth(), scrollbar.getHeight());
      g2d.fillRect(0, incrButton.getHeight(), trackBounds.width, trackBounds.height);
    } else {
      GradientPaint gradient = new GradientPaint(0,0, GuiStatics.COLOR_GRAY,
          trackBounds.width,trackBounds.height, GuiStatics.COLOR_GRAY_BACKGROUND, true);
      Graphics2D g2d = (Graphics2D)g;
      g2d.setPaint(gradient);    
      g2d.fillRect(0, 0, scrollbar.getWidth(), scrollbar.getHeight());
      g2d.fillRect(0, incrButton.getHeight(), trackBounds.width, trackBounds.height);
    }
    
  }
  
  
  
  
}
