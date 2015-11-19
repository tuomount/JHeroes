package org.jheroes.gui.slider;


import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSliderUI;

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
 * 
 * Overwrite Slider UI
 * 
 */
public class GameSliderUI extends BasicSliderUI {

  public GameSliderUI(JSlider b) {
    super(b);
    slider = b;
  }

  /**
   * Creates a new createUI for compnent
   *
   * @param c JComponent
   * @return GameScrollBarUI
   */
  public static ComponentUI createUI(JComponent c) {
      return new GameSliderUI((JSlider) c);
  }

  @Override
  public void paintThumb(Graphics g) {
    Rectangle t= thumbRect;
    g.drawImage(GuiStatics.IMAGE_SLIDER_THUMB,t.x,t.y,t.width,t.height,null);
  }

  
  
  @Override
  public void paintFocus(Graphics g) {
  }

  @Override
  public void paintTrack(Graphics arg0) {
    Rectangle t= trackRect;
    GradientPaint gradient = new GradientPaint(t.x,t.y, GuiStatics.COLOR_BLACK,
        t.width,t.height, GuiStatics.COLOR_GRAY, true);
    Graphics2D g2d = (Graphics2D)arg0;
    g2d.setPaint(gradient);    
    g2d.fillRect(t.x, t.y,t.width, t.height);
    g2d.setColor(GuiStatics.COLOR_DARK_CYAN);
    g2d.drawLine(t.x, t.height/2+t.y-1, t.x+t.width-1, t.height/2+t.y-1);
    g2d.drawLine(t.x, t.height/2+t.y+1, t.x+t.width-1, t.height/2+t.y+1);
    g2d.setColor(GuiStatics.COLOR_CYAN);
    g2d.drawLine(t.x, t.height/2+t.y, t.x+t.width-1, t.height/2+t.y);

    int y1 = t.y+2;
    int y2 = t.y+t.height-3;
    g2d.setColor(GuiStatics.COLOR_DARK_CYAN);
    g2d.drawLine(t.x-1, y1, t.x-1, y2);
    g2d.drawLine(t.x+1, y1, t.x+1, y2);

    g2d.drawLine(t.x+t.width/4-1, y1, t.x+t.width/4-1, y2);
    g2d.drawLine(t.x+t.width/4+1, y1, t.x+t.width/4+1, y2);

    g2d.drawLine(t.x+t.width/2-1, y1, t.x+t.width/2-1, y2);
    g2d.drawLine(t.x+t.width/2+1, y1, t.x+t.width/2+1, y2);
    
    g2d.drawLine(t.x+t.width*3/4-1, y1, t.x+t.width*3/4-1, y2);
    g2d.drawLine(t.x+t.width*3/4+1, y1, t.x+t.width*3/4+1, y2);
    
    g2d.drawLine(t.x+t.width-2, y1, t.x+t.width-2, y2);
    g2d.drawLine(t.x+t.width, y1, t.x+t.width, y2);

    g2d.setColor(GuiStatics.COLOR_CYAN);
    g2d.drawLine(t.x, y1, t.x, y2);
    
    g2d.drawLine(t.x+t.width/4, y1, t.x+t.width/4, y2);

    g2d.drawLine(t.x+t.width/2, y1, t.x+t.width/2, y2);
    
    g2d.drawLine(t.x+t.width*3/4, y1, t.x+t.width*3/4, y2);
    
    g2d.drawLine(t.x+t.width-1, y1, t.x+t.width-1, y2);
}

  @Override
  public void paint(Graphics g, JComponent c) {
    Rectangle t= new Rectangle(0, 0, c.getWidth(), c.getHeight());
    GradientPaint gradient = new GradientPaint(t.x,t.y, GuiStatics.COLOR_BLACK,
        t.width,t.height, GuiStatics.COLOR_GRAY, true);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setPaint(gradient);    
    g2d.fillRect(t.x, t.y,t.width, t.height);
    super.paint(g, c);
  }

  @Override
  public void paintTicks(Graphics g) {
  }

  

  
  
}
