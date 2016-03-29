package org.jheroes.utilities;

import org.jheroes.map.DiceGenerator;
import org.jheroes.map.Map;
import org.jheroes.tileset.Tile;

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
 * Utility to convert pixels to map coordinate
 * 
 */
public class PixelsToMapCoordinate {

  
  /**
   * Relative X coordinate on map
   */
  private int relativeMapX;
  /**
   * Relative Y coordinate on map
   */
  private int relativeMapY;
  /**
   * Center drawn map X
   */
  private int centerMapX;
  /**
   * Center drawn map Y
   */
  private int centerMapY;
  /**
   * Pixel coordinate X
   */
  private int pixelX;
  /**
   * Pixel coordinate Y
   */
  private int pixelY;
  
  /**
   * Character x coordinate
   */
  private int chrX;
  /**
   * Character y coordinate
   */
  private int chrY;
  
  /**
   * X Coordinate where to start drawing the map in panel
   */
  private static final int START_X = 11;
  /**
   * Y Coordinate where to start drawing the map in panel
   */
  private static final int START_Y = 16;
  /**
   * How many tiles in panel on X axle
   */
  private static final int X_TILES = 21;
  /**
   * How many tiles in panel on Y axle
   */
  private static final int Y_TILES = 17;
  /**
   * X coordinate where panel drawing ends
   */
  private static final int END_X = START_X+X_TILES*Tile.MAX_WIDTH;
  /**
   * Y coordinate where panel drawing ends
   */
  private static final int END_Y = START_Y+Y_TILES*Tile.MAX_HEIGHT;

  /**
   * Is coordinates out of panel
   */
  private boolean outOfPanel;
  
  /**
   * Convert pixel to map coordinate
   * @param mx Center of map X
   * @param my Center of map Y
   * @param px Pixel coordinate X
   * @param py Pixel coordinate Y
   * @param cx Character x coordinate on map 
   * @param cy Character y coordinate on map 
   * 
   */
  public PixelsToMapCoordinate(int mx,int my, int px, int py,int cx,int cy) {
    centerMapX = mx;
    centerMapY = my;
    pixelX = px;
    pixelY = py;
    chrX = cx;
    chrY = cy;
    if (pixelX >= START_X && pixelY >= START_Y 
        && pixelX < END_X && pixelY < END_Y) {
      pixelX = pixelX-START_X;
      pixelY = pixelY-START_Y;
      relativeMapX = (pixelX / Tile.MAX_WIDTH)-Map.VIEW_X_RADIUS;
      relativeMapY = (pixelY / Tile.MAX_HEIGHT)-Map.VIEW_Y_RADIUS;
    } else {
      outOfPanel = true;
      relativeMapX = -1;
      relativeMapY = -1;
    }
  }

  /**
   * get the absolute map X coordinate
   * @return x coordinate or -1 if cannot be calculated
   */
  public int getMapX() {
    if (!isOutOfPanel()) {
      return centerMapX+relativeMapX;
    } else {
      return -1;
    }
  }

  /**
   * get the absolute map Y coordinate
   * @return x coordinate or -1 if cannot be calculated
   */
  public int getMapY() {
    if (!isOutOfPanel()) {
      return centerMapY+relativeMapY;
    } else {
      return -1;
    }
  }
  
  /**
   * Get direction from character
   * @return Map.NORTH_DIRECTION_RIGHT, Map.NORTH_DIRECTION_LEFT,
   *         Map.NORTH_DIRECTION_UP, Map.NORTH_DIRECTION_DOWN
   *         -1 if no direction
   */
  public int getDirection() {
    if (!isOutOfPanel()) {
      int dx = getMapX()-chrX;
      int dy = getMapY()-chrY;
      if (Math.abs(dx)>Math.abs(dy)) {
        if (dx > 0) {
          return Map.NORTH_DIRECTION_RIGHT;
        } else if (dx < 0) {
          return Map.NORTH_DIRECTION_LEFT; 
        } else {
          return -1;
        }
      } else if (Math.abs(dx)<Math.abs(dy)) {
        if (dy > 0) {
          return Map.NORTH_DIRECTION_DOWN;
        } else if (dy < 0) {
          return Map.NORTH_DIRECTION_UP; 
        } else {
          return -1;
        }        
      } else if (dx < 0 && dy < 0) {
         switch (DiceGenerator.getRandom(1)) {
         case 0: return Map.NORTH_DIRECTION_UP;
         case 1: return Map.NORTH_DIRECTION_LEFT;
         }
      } else if (dx > 0 && dy < 0) {
        switch (DiceGenerator.getRandom(1)) {
        case 0: return Map.NORTH_DIRECTION_UP;
        case 1: return Map.NORTH_DIRECTION_RIGHT;
        }
      } else if (dx > 0 && dy > 0) {
        switch (DiceGenerator.getRandom(1)) {
        case 0: return Map.NORTH_DIRECTION_DOWN;
        case 1: return Map.NORTH_DIRECTION_RIGHT;
        }
      } else if (dx < 0 && dy > 0) {
        switch (DiceGenerator.getRandom(1)) {
        case 0: return Map.NORTH_DIRECTION_DOWN;
        case 1: return Map.NORTH_DIRECTION_LEFT;
        }
      } 
    }else {
      return -1;
    }
    return -1;
  }
  

  /**
   * Are given coordinates out of panel and not in the map.
   * If outside then no map coordinates are calculated
   * @return true if pixel coordinates are out of map
   */
  public boolean isOutOfPanel() {
    return outOfPanel;
  }
}
