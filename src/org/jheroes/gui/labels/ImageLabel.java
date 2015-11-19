package org.jheroes.gui.labels;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
 * Label not having text at all just an image
 * 
 */
public class ImageLabel extends JLabel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private BufferedImage image;
  private boolean border;
  
  public ImageLabel(BufferedImage image, boolean border) {
    super();
    
    ImageIcon icon = new ImageIcon(image, "");
    this.setIcon(icon);
    this.setImage(image);
    this.setBorder(border);
    if (isBorder()) {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    } else {
      this.setBorder(BorderFactory.createEmptyBorder());

    }

  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setBorder(boolean border) {
    this.border = border;
  }

  public boolean isBorder() {
    return border;
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    if(isBorder()) {
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        g.drawImage(getImage(), 2,2, null);
    } else {
      g.drawImage(getImage(), 0,0, null);
    }
  }

  
}
