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
 * Where should AI character moved if it wants to go certain coordinates.
 * 
 */
public class AIPath {

  private int target_x;
  private int target_y;
  private int start_x;
  private int start_y;
  
  /**
   * Primary direction to move
   */
  private int firstDirection;
  /**
   * Secondary direction to move if First is blocked
   */
  private int secondDirection;
  
  /**
   * Last used direction
   */
  private int lastDirection;
  
  public final static int DIRECTION_NONE = -1;
  public final static int DIRECTION_UP       =0;
  public final static int DIRECTION_UPRIGHT  =1;
  public final static int DIRECTION_RIGHT    =2;
  public final static int DIRECTION_DOWNRIGHT=3;
  public final static int DIRECTION_DOWN     =4;
  public final static int DIRECTION_DOWNLEFT =5;
  public final static int DIRECTION_LEFT     =6;
  public final static int DIRECTION_UPLEFT   =7;
  
  /**
   * Constructor for AI Path, Currently this AIPath supports only moving
   * UP,RIGHT,DOWN and LEFT
   * @param start_x
   * @param start_y
   * @param target_x
   * @param target_y
   */
  public AIPath(int start_x,int start_y,int target_x,int target_y) {
    this.start_x = start_x;
    this.start_y = start_y;
    this.target_x = target_x;
    this.target_y = target_y;
    firstDirection = getDirectionToMove(start_x, start_y, target_x, target_y);
    lastDirection = -1;
    updateSecondDirection();
  }
  
  /**
   * Update Movement by using block map
   * First direction is get by A* for the size of block map
   * @param bMap
   * @param sizeX
   * @param sizeY
   */
  public void updateMovementByBlockMap(int[][] bMap, int sizeX,int sizeY) {    
    if (bMap != null) {
      if (sizeX % 2 == 1 && sizeY % 2 ==1){
        int[][] map = new int[sizeX][sizeY];
        boolean targetPlaced=false;

        for (int i=0;i<sizeX;i++) {
          for (int j=0;j<sizeY;j++) {
           map[i][j] = 999; 
          }
        }

        int cx = sizeX/2;
        int cy = sizeY/2;
        int tx = target_x-start_x;
        int ty = target_y-start_y;
        int horizontalTarget = 2;
        int verticalTarget = 2;        
        switch (lastDirection) {
        case DIRECTION_RIGHT: {
          bMap[cx-1][cy] =1;
          break;
        }
        case DIRECTION_LEFT: {
          bMap[cx+1][cy] =1;
          break;
        }
        case DIRECTION_DOWN: {
          bMap[cx][cy-1] =1;
          break;
        }
        case DIRECTION_UP: {
          bMap[cx][cy+1] =1;
          break;
        }
        }
        if (Math.abs(tx) < Math.abs(ty)) {
          verticalTarget = 1;
        } else if (Math.abs(tx) > Math.abs(ty)) {
          horizontalTarget = 1;
        } else {
          horizontalTarget = 1;
          verticalTarget = 1;
        }
        if (Math.abs(tx) < cx && Math.abs(ty) < cy) {
          tx = tx+cx;
          ty = ty+cy;
          // On single target
          if (cx != tx || cy != ty) { 
            map[tx][ty] = 1;
          }
          targetPlaced = true;
          
        } else {
          if (Math.abs(tx) < cx || Math.abs(ty) < cy) {
            if (Math.abs(tx)>Math.abs(ty)) {
              if (tx < 0) {
                // Left border
                for (int i=0;i<sizeY;i++) {
                  if (bMap[0][i]==0) {
                    targetPlaced = true;
                    map[0][i] = 1;
                  }
                }
              } else {
                // Right border
                for (int i=0;i<sizeY;i++) {
                  if (bMap[sizeX-1][i]==0) {
                    targetPlaced = true;
                    map[sizeX-1][i] = 1;
                  }
                }
              }
            } else {
              if (ty < 0) {
                // Up border
                for (int i=0;i<sizeX;i++) {
                  if (bMap[i][0]==0) {
                    targetPlaced = true;
                    map[i][0] = 1;
                  }
                }
              } else {
                // Down border
                for (int i=0;i<sizeX;i++) {
                  if (bMap[i][sizeY-1]==0) {
                    targetPlaced = true;
                    map[i][sizeY-1] = 1;
                  }
                }
              }            
            }
          } else {
            if (tx < 0) {
              // Left border
              for (int i=0;i<sizeY;i++) {
                if (bMap[0][i]==0) {
                  targetPlaced = true;
                  map[0][i] = horizontalTarget;
                }
              }
            } else {
              // Right border
              for (int i=0;i<sizeY;i++) {
                if (bMap[sizeX-1][i]==0) {
                  targetPlaced = true;
                  map[sizeX-1][i] = horizontalTarget;
                }
              }
            }
            if (ty < 0) {
              // Up border
              for (int i=0;i<sizeX;i++) {
                if (bMap[i][0]==0) {
                  targetPlaced = true;
                  map[i][0] = verticalTarget;
                }
              }
            } else {
              // Down border
              for (int i=0;i<sizeX;i++) {
                if (bMap[i][sizeY-1]==0) {
                  targetPlaced = true;
                  map[i][sizeY-1] = verticalTarget;
                }
              }
            }   
          }
        }
        boolean targetFound = false;
        while (!targetFound) {
          boolean mapChanged = false;
          for (int i=0;i<sizeX;i++) {
            for (int j=0;j<sizeY;j++) {
              if (map[i][j] != 999) {
                int val = map[i][j]+1;
                if (i>0) {                 
                  if (bMap[i-1][j] == 0) {
                    if (map[i-1][j] > val) {
                      map[i-1][j] = val;
                      mapChanged = true;
                    }
                  }
                }
                if (i<sizeX-1) {                 
                  if (bMap[i+1][j] == 0) {
                    if (map[i+1][j] > val) {
                      map[i+1][j] = val;
                      mapChanged = true;
                    }
                  }
                }
                if (j>0) {                 
                  if (bMap[i][j-1] == 0) {
                    if (map[i][j-1] > val) {
                      map[i][j-1] = val;
                      mapChanged = true;
                    }
                  }
                }
                if (j<sizeY-1) {                 
                  if (bMap[i][j+1] == 0) {
                    if (map[i][j+1] > val) {
                      map[i][j+1] = val;
                      mapChanged = true;
                    }
                  }
                }
              }
            } // END of FOR LOOP            
          } // END OF FOR LOOP
          int up = map[cx][cy-1];
          int right = map[cx+1][cy];
          int down = map[cx][cy+1];
          int left = map[cx-1][cy];
          if (up < down && up < left && up < right) {
            firstDirection = DIRECTION_UP;
            targetFound=true;
          }
          if (down < up && down < left && down < right) {
            firstDirection = DIRECTION_DOWN;
            targetFound=true;
          }
          if (left < up && left < down && left < right) {
            firstDirection = DIRECTION_LEFT;
            targetFound=true;
          }
          if (right < up && right < down && right < left) {
            firstDirection = DIRECTION_RIGHT;
            targetFound=true;
          }
          if (up < down && up < left && up < right) {
            firstDirection = DIRECTION_UP;
            targetFound=true;
          }
          if (down == up && down < left && down < right) {
            firstDirection = upOrDown();
            targetFound=true;
          }
          if (left == right && left < down && left < up) {
            firstDirection = leftOrRight();
            targetFound=true;
          }
          if (right == up && right < down && right < left) {
            firstDirection = twoDirection(DIRECTION_RIGHT, DIRECTION_UP);
            targetFound=true;
          }
          if (left == up && left < down && left < right) {
            firstDirection = twoDirection(DIRECTION_LEFT, DIRECTION_UP);
            targetFound=true;
          }
          if (left == down && left < up && left < right) {
            firstDirection = twoDirection(DIRECTION_LEFT, DIRECTION_DOWN);
            targetFound=true;
          }
          if (right == down && right < up && right < left) {
            firstDirection = twoDirection(DIRECTION_RIGHT, DIRECTION_DOWN);
            targetFound=true;
          }
          if (!mapChanged) {
            break;
          }
        } // END OF WHILE
        if (!targetPlaced) {
          firstDirection = randomDirection();
        }
        updateSecondDirection();
      }
    }
  }
  
  /**
   * Check if coordinates are in diagonal or straight line
   * @param x
   * @param y
   * @param x2
   * @param y2
   * @return boolean
   */
  public boolean isDiagonalMove() {
    boolean result = false;
    if (start_x==target_x || start_y==target_y) {
      result = false;
    } else {
      result = true;
    }
    return result;
  }

  public int getFirstDirection() {
    return firstDirection;
  }

  /**
   * Set first direction but also updates second one according the first one
   * @param firstDirection
   */
  public void setFirstDirection(int firstDirection) {
    this.firstDirection = firstDirection;
    updateSecondDirection();
  }

  /**
   * Get random direction
   * @return
   */
  private int randomDirection() {
    switch (DiceGenerator.getRandom(3)) {
    case 0: return DIRECTION_LEFT;
    case 1: return DIRECTION_RIGHT; 
    case 2: return DIRECTION_UP;
    case 3: return DIRECTION_DOWN; 
    }
    return DIRECTION_LEFT;
    
  }
  
  /**
   * Get random direction from left or Right
   * @return
   */
  private int leftOrRight() {
    switch (DiceGenerator.getRandom(1)) {
    case 0: return DIRECTION_LEFT;
    case 1: return DIRECTION_RIGHT; 
    }
    return DIRECTION_LEFT;
  }
  
  private int twoDirection(int direc1, int direc2) {
    switch (DiceGenerator.getRandom(1)) {
    case 0: return direc1;
    case 1: return direc2; 
    }
    return direc1;
    
  }

  /**
   * Get random direction from up or down
   * @return
   */
  private int upOrDown() {
    switch (DiceGenerator.getRandom(1)) {
    case 0: return DIRECTION_UP; 
    case 1: return DIRECTION_DOWN; 
    }
    return DIRECTION_UP;
  }

  
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("From (");
    sb.append(start_x);
    sb.append(",");
    sb.append(start_y);
    sb.append(") to ( ");
    sb.append(target_x);
    sb.append(",");
    sb.append(target_y);
    sb.append(")");
    return sb.toString();
  }

  /**
   * Update Second direction according the first one
   */
  private void updateSecondDirection() {
    switch (getFirstDirection()) {
    case DIRECTION_UP: {
      if (isDiagonalMove()) {
        if(target_x < start_x) {
          secondDirection = DIRECTION_LEFT;
        } else {
          secondDirection = DIRECTION_RIGHT;
        }
      } else {
        secondDirection = leftOrRight();
      }
      break;
    }
    case DIRECTION_DOWN: {
      if (isDiagonalMove()) {
        if(target_x < start_x) {
          secondDirection = DIRECTION_LEFT;
        } else {
          secondDirection = DIRECTION_RIGHT;
        }
      } else {
        secondDirection = leftOrRight();
      }
      break;
    }
    case DIRECTION_LEFT: {
      if (isDiagonalMove()) {
        if(target_y < start_y) {
          secondDirection = DIRECTION_UP;
        } else {
          secondDirection = DIRECTION_DOWN;
        }
      } else {
        secondDirection = upOrDown();
      }
      break;
    }
    case DIRECTION_RIGHT: {
      if (isDiagonalMove()) {
        if(target_y < start_y) {
          secondDirection = DIRECTION_UP;
        } else {
          secondDirection = DIRECTION_DOWN;
        }
      } else {
        secondDirection = upOrDown();
      }
      break;
    }
    default: secondDirection = DIRECTION_NONE;
    }
  }
  public int getSecondDirection() {
    return secondDirection;
  }


  /**
   * Get closest direction to target
   * @param fromX
   * @param fromY
   * @param targetX
   * @param targetY
   * @return Direction
   */
  public static int getDirectionToMove(int fromX, int fromY, int targetX, int targetY) {
    int mx = targetX-fromX;
    int my = targetY-fromY;
    if (mx == 0) {
      if (my > 0) {
        return DIRECTION_DOWN;
      }
      if (my < 0) {
        return DIRECTION_UP;
      }
    }
    if (mx < 0){
      if (my == 0) {
          return DIRECTION_LEFT;
      }
      if (my < 0) {
        if (DiceGenerator.getRandom(1)==0) {
          return DIRECTION_LEFT;
        } else {
          return DIRECTION_UP;
        }
      }
      if (my > 0) {
        if (DiceGenerator.getRandom(1)==0) {
          return DIRECTION_LEFT;
        } else {
          return DIRECTION_DOWN;
        }
      }
    }
    if (mx > 0){
      if (my == 0) {
          return DIRECTION_RIGHT;
      }
      if (my < 0) {
        if (DiceGenerator.getRandom(1)==0) {
          return DIRECTION_RIGHT;
        } else {
          return DIRECTION_UP;
        }
      }
      if (my > 0) {
        if (DiceGenerator.getRandom(1)==0) {
          return DIRECTION_RIGHT;
        } else {
          return DIRECTION_DOWN;
        }
      }
    }
    return DIRECTION_NONE;
  }

  public int getLastDirection() {
    return lastDirection;
  }

  public void setLastDirection(int lastDirection) {
    this.lastDirection = lastDirection;
  }
}
