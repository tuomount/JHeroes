package org.jheroes.gui.panels;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

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
 * Panel that has one image
 * 
 */ 
 public class ImagePanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private BufferedImage image;
  


  public ImagePanel(BufferedImage image, boolean border) {
    super();
    this.setImage(image);
    Dimension size = new Dimension(image.getWidth(), image.getHeight());
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    if (border) {
      Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
      this.setBorder(etchedBorder);
    }
  }
  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    g.drawImage(image, 0, 0, null);
  }
}
