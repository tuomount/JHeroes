package org.jheroes.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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
 * Class to draw compass
 * 
 */
public class Compass {
  
  private BufferedImage compass_north;
  private BufferedImage compass_north_east;
  private BufferedImage compass_east;
  private BufferedImage compass_south_east;
  private BufferedImage compass_south;
  private BufferedImage compass_south_west;
  private BufferedImage compass_west;
  private BufferedImage compass_north_west;
  
  private int direction;
  private int speed;
  
  private static final int N = 0;
  private static final int NE = 1;
  private static final int E = 2;
  private static final int SE = 3;
  private static final int S = 4;
  private static final int SW = 5;
  private static final int W = 6;
  private static final int NW = 7;
  
  public Compass() throws IOException {
    compass_north = ImageIO.read(Map.class.getResource("/res/images/compass_n.png"));
    compass_north_east = ImageIO.read(Map.class.getResource("/res/images/compass_ne.png"));
    compass_north_west = ImageIO.read(Map.class.getResource("/res/images/compass_nw.png"));
    compass_west = ImageIO.read(Map.class.getResource("/res/images/compass_w.png"));
    compass_east = ImageIO.read(Map.class.getResource("/res/images/compass_e.png"));
    compass_south = ImageIO.read(Map.class.getResource("/res/images/compass_s.png"));
    compass_south_east = ImageIO.read(Map.class.getResource("/res/images/compass_se.png"));
    compass_south_west = ImageIO.read(Map.class.getResource("/res/images/compass_sw.png"));
    direction = N;
    speed = 0;
  }
  
  public void drawCompass(BufferedImage screen, int x, int y) {
    Graphics2D g = screen.createGraphics();
    switch (this.direction) {
    case N:  g.drawImage(compass_north,x,y,null); break;
    case NE:  g.drawImage(compass_north_east,x,y,null); break;
    case E:  g.drawImage(compass_east,x,y,null); break;
    case SE:  g.drawImage(compass_south_east,x,y,null); break;
    case S:  g.drawImage(compass_south,x,y,null); break;
    case SW:  g.drawImage(compass_south_west,x,y,null); break;
    case W:  g.drawImage(compass_west,x,y,null); break;
    case NW:  g.drawImage(compass_north_west,x,y,null); break;
    }
  }
  
  public void updateCompass(int newDirection) {
   if ((newDirection != direction) && (speed == 0)) {
    int diff1 = newDirection-direction; //6-0=6  0-4=-4
    int diff2 = direction-newDirection; //0-6+8=2  4-0+8=12
    if (diff1 < 0) {
      diff1=diff1+8;
    }
    if (diff2 < 0) {
      diff2=diff2+8;
    }
    if (diff1<diff2) {
      speed=1;
    } else if (diff1>diff2) {
      speed=-1;
    } else {
      switch (DiceGenerator.getRandom(1)) {
      case 0: speed = -1; break;
      case 1: speed = 1; break;
      }
    }    
   }
   if ((newDirection != direction) && (speed != 0)) {
     direction = direction+speed;
     if (direction < 0) {
       direction = direction +8;
     }
     if (direction > 7) {
       direction = direction -8;
     }
      
   }
   if (newDirection == direction) {
     speed = 0;
   }
  }

}
