package org.jheroes.map.character;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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
 * Get correct face for certain character.
 * 
 */
public class Faces {
  
  private static BufferedImage allFaces=null;
  private static int maxFace=0;
  public static int HEIGHT = 48;
  public static int WIDTH = 48;
  private static int amountInRow=0;
  private static int amountInColumn=0;
  
  private static void loadFaces() throws IOException{
    if (allFaces == null) {
      allFaces = ImageIO.read(Map.class.getResource("/res/images/faces.png"));
      amountInRow =allFaces.getWidth()/WIDTH;
      amountInColumn = allFaces.getHeight()/HEIGHT;
      maxFace = amountInRow * amountInColumn;
    }
    
  }
  
  public static int getMaxFace() {
    try {
      loadFaces();
    } catch (IOException ioe) {
      return 0;
    }
    return maxFace;
  }
  
  public static BufferedImage getAllFaces() {
    try {
      loadFaces();
    } catch (IOException ioe) {
      return null;
    }
    return allFaces;
  }
  
  public static int getFaceIndex(int x, int y)
  {
    int index = y*amountInRow+x;
    if ((index >= 0) && (index < maxFace)) {
      return index;
    } else {
      return 0;
    }
      
  }
  
  public static BufferedImage getFace(int number) {
    try {
      loadFaces();
    } catch (IOException ioe) {
      return null;
    }
    int x =0;
    int y =0;
    if ((number >= maxFace) && (number > -1)) {
      return null;
    }
    x = number;
    if (x >= amountInRow) {
      y = x / amountInRow;
      x = x % amountInRow;
    }
    return allFaces.getSubimage(x*WIDTH, y*HEIGHT, WIDTH, HEIGHT);
  }
  
}
