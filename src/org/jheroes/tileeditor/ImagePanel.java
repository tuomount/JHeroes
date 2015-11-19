package org.jheroes.tileeditor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

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
 * Image panel for drawing images
 * 
 **/
public class ImagePanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private BufferedImage img;
  public ImagePanel(BufferedImage image) {
    super();
    img = image;
  }
  
  public void UpdateImage(BufferedImage image) {
    img = image;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.drawImage(img, 0, 0, null);
  }
  
  
}
