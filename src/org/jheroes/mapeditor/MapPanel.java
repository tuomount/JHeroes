package org.jheroes.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.jheroes.map.Map;
/**
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
 * Map Panel for map editor
 * 
 **/
public class MapPanel extends JPanel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private BufferedImage screen;
  
  
  public MapPanel() {
    screen = new BufferedImage(693, 575, BufferedImage.TYPE_INT_ARGB);
    this.setSize(693, 575);
    this.setPreferredSize(new Dimension(693,575));
    this.setBackground(Color.black);
    Graphics2D gr = screen.createGraphics();
    gr.setColor(new Color(0,0,0));
    gr.fillRect(0, 0, screen.getWidth(), screen.getHeight());
  
    
  }
  
  public void drawMap(Map myMap, int vx,int vy, boolean editorMode,boolean day) {
    Graphics2D gr = screen.createGraphics();
    if (myMap.isFullRepaint()) {
      gr.setColor(new Color(0,0,0));
      gr.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    }
    myMap.drawMap(screen, vx, vy, 10, 15,editorMode,day);
    this.repaint();
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    
    g.drawImage(screen, 0, 0, null);
  }


}
