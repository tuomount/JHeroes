package org.jheroes.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.jheroes.map.Map;


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
 * This is panel where map is actually drawn.
 * 
 */
public class MapPanel extends JPanel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private static BufferedImage background=null;
  
  private BufferedImage screen;
  
  private boolean forceRepaint = false; 
  
  public MapPanel() {
    screen = new BufferedImage(693, 700, BufferedImage.TYPE_INT_ARGB);
    Dimension size = new Dimension(693, 700);
    this.setSize(size);
    this.setPreferredSize(size);
    this.setMinimumSize(size);
    this.setBackground(Color.black);
    forceRepaint = false;
    Graphics2D gr = screen.createGraphics();
    gr.setColor(new Color(128,0,0));
    gr.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    if (background == null) {
      try {
        background = ImageIO.read(MapPanel.class.getResource("/res/images/gamepanelback.png"));
      } catch (IOException e) {
        System.out.println("Failed loading background image:"+e.getMessage());
      }
    }
  }

  public void forceFullRepaint() {
    forceRepaint = true;
  }
  
  public void drawMap(Map myMap, int vx,int vy, boolean day,boolean fullRepaint) {
    Graphics2D gr = screen.createGraphics();
    if (forceRepaint) {
      fullRepaint = true;
      forceRepaint = false;
    }
    if (background == null) {
    gr.setColor(new Color(128,0,0));
    gr.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    } else {
      if (fullRepaint) {
        gr.drawImage(background,0,0,null);
        myMap.forceMapFullRepaint();
      }
    }
    myMap.drawMap(screen, vx, vy, 10, 15,false,day);
    this.repaint();
  }
  
  public void drawImage(Map myMap, int vx,int vy,BufferedImage image, boolean fullRepaint) {
    Graphics2D gr = screen.createGraphics();
    if (forceRepaint) {
      fullRepaint = true;
      forceRepaint = false;
    }
    if (background == null) {
    gr.setColor(new Color(128,0,0));
    gr.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    } else {
      if (fullRepaint) {
        gr.drawImage(background,0,0,null);
        myMap.forceMapFullRepaint();
      }
    }
    myMap.drawImageAndCompass(screen, vx, vy, 10, 15,image);
    this.repaint();
    
  }
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    
    g.drawImage(screen, 0, 0, null);
  }
  
}
