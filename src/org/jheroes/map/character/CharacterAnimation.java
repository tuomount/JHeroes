package org.jheroes.map.character;

import org.jheroes.map.DiceGenerator;

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
 * Animation of Character
 * 
 */
public class CharacterAnimation {
  
  /**
   * Character tile is actually 32x48
   */
  public static final byte ANIMATION_TYPE_NORMAL = 0;
  
  /**
   * Character tile is 32x32
   */
  public static final byte ANIMATION_TYPE_SMALL = 1;
  
  /**
   * Character heading up
   */
  public static final byte DIRECTION_NORTH = 0;
  
  /**
   * Character heading right
   */
  public static final byte DIRECTION_EAST = 1;
  
  /**
   * Character heading down
   */
  public static final byte DIRECTION_SOUTH = 2;
  
  /**
   * Character heading left
   */
  public static final byte DIRECTION_WEST = 3;
  
  private static final int WAIT_BEFORE_STANDING = 3;
  
  /**
   * Used only if animation type is normal
   * This is head coordinate(X)
   */
  private int head_x;
  /**
   * Used only if animation type is normal
   * This is head coordinate(y)
   */
  private int head_y;
  
  /**
   * Character coordinate(X)
   */
  private int x;
  
  /**
   * Character coordinate(Y)
   */
  private int y;
  
  /**
   * Animation type
   */
  private byte type;
  
  /**
   * Character heading
   */
  private byte heading;
  
  /**
   * Animation frame
   */
  private int animation;
  
  private int waitForStanding;
  
  private int nextAnim;
  
  /**
   * Tile offset in tile file
   */
  private int tileOffset;
  
  public int getTileOffset() {
    return tileOffset;
  }

  public void setTileOffset(int tileOffset) {
    this.tileOffset = tileOffset;
  }

  public CharacterAnimation(byte type, int x, int y, int tileOffset) {
     setType(type);
     setPosition(x, y);
     setHeading(DIRECTION_SOUTH);
     this.animation=0;
     this.waitForStanding=0;
     this.nextAnim = DiceGenerator.getRandom(1,2);
     setTileOffset(tileOffset);
  }

  /**
   * Do move with animation. New coordinations must be in NORTH, WEST, EAST or South
   * from old one. Otherwise this does not do anything. Also new heading is calculated
   * and animation frame is done.
   * @param x, new x coordinate
   * @param y, new y coordinate
   * @return true if move actually happened
   */
  public boolean doMove(int x, int y) {
    boolean validMove = false;
    int mx = x-this.x;
    int my = y-this.y;
    if ((mx == -1) && (my == 0)) {
      setHeading(DIRECTION_WEST);
      validMove = true;
    }
    if ((mx == 1) && (my == 0)) {
      setHeading(DIRECTION_EAST);
      validMove = true;
    }
    if ((mx == 0) && (my == 1)) {
      setHeading(DIRECTION_SOUTH);
      validMove = true;
    }/**
     * 
     */
    if ((mx == 0) && (my == -1)) {
      setHeading(DIRECTION_NORTH);
      validMove = true;
    }
    if (validMove) {
      setPosition(x, y);
      this.waitForStanding = WAIT_BEFORE_STANDING;
      if (this.nextAnim == 1) {
        this.nextAnim = 2;
        this.animation = 1;
      } else if (this.nextAnim == 2) {
        this.nextAnim = 1;
        this.animation = 2;
      } else {
        this.animation = DiceGenerator.getRandom(1,2);
        this.nextAnim = DiceGenerator.getRandom(1,2);
      }
    }
    return validMove;
  }
  
  /**
   * Get Head tile index
   * @return, int
   */
  public int getHeadTile() {
    int result = -1;
    if (getType() == ANIMATION_TYPE_NORMAL) {
      result = tileOffset+getHeading()*3+this.animation;
    }
    return result;
  }
  
  /**
   * Get the body tile index
   * @return
   */
  public int getBodyTile() {
    int result = -1;
    if (getType() == ANIMATION_TYPE_NORMAL) {
      result = tileOffset+getHeading()*3+this.animation+12;
    }
    if (getType() == ANIMATION_TYPE_SMALL) {
      result = tileOffset+getHeading()*3+this.animation;
    }
    return result;    
  }
  
  /**
   * This just clears the animation
   */
  public void doStand() {
    if (this.waitForStanding > 0) {
      this.waitForStanding--;
    } else {
      this.animation =0;
    }
  }
  
  /**
   * set Animation type
   * @param type, ANIMATION_TYPE_????
   */
  public void setType(byte type) {
    if ((type == ANIMATION_TYPE_NORMAL) || (type == ANIMATION_TYPE_SMALL)) {
      this.type = type;
    } else {
      this.type = ANIMATION_TYPE_NORMAL;
    }
  }

  /**
   * Get animation type
   * @return
   */
  public byte getType() {
    return type;
  }
  
  /**
   * Set Character animation position in map
   * @param x
   * @param y
   */
  public void setPosition(int x, int y) {
    switch (type) {
    case ANIMATION_TYPE_SMALL: {
      this.x=x;
      this.y=y;
      break;
      }
    case ANIMATION_TYPE_NORMAL: {
      this.x=x;
      this.y=y;
      this.head_x=x;
      this.head_y=y-1;
    }
    }
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getHeadX() {
    if (getType()==ANIMATION_TYPE_NORMAL) {
      return this.head_x;
    } else {
      return -1;
    }
  }

  public int getHeadY() {
    if (getType()==ANIMATION_TYPE_NORMAL) {
      return this.head_y;
    } else {
      return -1;
    }
  }

  public void setHeading(byte heading) {
    if ((heading == DIRECTION_NORTH) || (heading == DIRECTION_EAST) ||
        (heading == DIRECTION_SOUTH) || (heading == DIRECTION_WEST)) {
      this.heading = heading;
    } else {
      this.heading = DIRECTION_NORTH;
    }
  }

  public byte getHeading() {
    return heading;
  }
}
