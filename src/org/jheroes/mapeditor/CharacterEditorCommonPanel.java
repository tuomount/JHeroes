package org.jheroes.mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.jheroes.map.character.Faces;

import org.jheroes.tileset.Tile;
import org.jheroes.tileset.Tileset;
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
 * Common panel for character editor
 * 
 **/

public class CharacterEditorCommonPanel extends JPanel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private BufferedImage charScreen;
  private BufferedImage faceScreen;
  private static Tileset charactersTileset = null;
  private int headIndex;
  private int bodyIndex;
  private int faceIndex;
  
  public CharacterEditorCommonPanel(Tileset charactersTileset) {
    super();
    if (CharacterEditorCommonPanel.charactersTileset == null) {
      CharacterEditorCommonPanel.charactersTileset = charactersTileset;
    }
    charScreen = new BufferedImage(Tile.MAX_WIDTH+2, Tile.MAX_HEIGHT*2+2, BufferedImage.TYPE_INT_ARGB);
    faceScreen = new BufferedImage(Faces.WIDTH,Faces.HEIGHT,BufferedImage.TYPE_INT_ARGB);
    headIndex = -1;
    bodyIndex = -1;
    faceIndex = -1;
  }
  
  public void updateImages(int head, int body, int face) {
    headIndex = head;
    bodyIndex = body;
    faceIndex = face;
    repaint();
  }
  
  @Override
  public void paint(Graphics g) {    
    super.paint(g);
    Graphics2D gr = charScreen.createGraphics();
    gr.setColor(new Color(158,158,158));
    gr.fillRect(1, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1, 1+Tile.MAX_HEIGHT, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1+Tile.MAX_HEIGHT+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.setColor(new Color(104, 104, 104));
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1+Tile.MAX_HEIGHT, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1, 1+Tile.MAX_HEIGHT+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.setColor(Color.black);        
    gr.drawRect(0, 0, Tile.MAX_WIDTH+1, Tile.MAX_HEIGHT*2+1);    
    if (headIndex != -1) {
      Tile myTile = charactersTileset.getTile(headIndex);
      if ((myTile != null)) {
        myTile.putTile(charScreen, 1,1,0);
      }
    }
    if (bodyIndex != -1) {
      Tile myTile = charactersTileset.getTile(bodyIndex);
      if ((myTile != null)) {
        myTile.putTile(charScreen, 1,1+Tile.MAX_HEIGHT,0);
      }
    }
    g.drawImage(charScreen, 530, 5, null);

    faceScreen = Faces.getFace(faceIndex);
    if (faceScreen != null) {
      g.drawImage(faceScreen, 400, 5,null);
    }
    
  }


}
