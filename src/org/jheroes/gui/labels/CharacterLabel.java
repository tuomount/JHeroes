package org.jheroes.gui.labels;


import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

import org.jheroes.gui.GuiStatics;
import org.jheroes.tileset.Tile;
import org.jheroes.tileset.Tileset;


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
 * Character label contain charater from tileset
 * 
 */
public class CharacterLabel extends JLabel {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private BufferedImage image;
  private boolean border;
  
  public CharacterLabel(int bodyIndex, int headIndex, boolean border) {
    super();
    this.image = new BufferedImage(Tile.MAX_WIDTH+2, Tile.MAX_HEIGHT*2+2, 
                                    BufferedImage.TYPE_INT_ARGB);
    drawImage(bodyIndex,headIndex);
    ImageIcon icon = new ImageIcon(image, "");
    this.setIcon(icon);
    this.setBorder(border);
    if (isBorder()) {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    } else {
      this.setBorder(BorderFactory.createEmptyBorder());

    }

  }

  private void drawImage(int bodyIndex, int headIndex) {
    Tileset charactersTileset = GuiStatics.getCharacterTileset();
    Graphics2D gr = this.image.createGraphics();
    GradientPaint gradient = new GradientPaint(0,0, GuiStatics.COLOR_GREEN,
        this.image.getWidth(),this.image.getHeight(), GuiStatics.COLOR_DARK_GREEN, true);
    gr.setPaint(gradient);    
    gr.fillRect(0, 0, this.image.getWidth(), this.image.getHeight());
    gr.setColor(Color.black);        
    gr.drawRect(0, 0, Tile.MAX_WIDTH+1, Tile.MAX_HEIGHT*2+1);    
    if (headIndex != -1) {
      Tile myTile = charactersTileset.getTile(headIndex);
      if ((myTile != null)) {
        myTile.putTile(this.image, 1,1,0);
      }
    }
    if (bodyIndex != -1) {
      Tile myTile = charactersTileset.getTile(bodyIndex);
      if ((myTile != null)) {
        myTile.putTile(this.image, 1,1+Tile.MAX_HEIGHT,0);
      }
    }
  }
  
  public void updateImages(int bodyIndex, int headIndex) {
    drawImage(bodyIndex, headIndex);    
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    if (border) {
      g.drawImage(this.image, 2,2, null);
    } else {
     g.drawImage(this.image, 0,0, null);
    }
  }

  public void setBorder(boolean border) {
    this.border = border;
  }

  public boolean isBorder() {
    return border;
  }

}
