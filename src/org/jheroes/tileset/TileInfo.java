package org.jheroes.tileset;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
 * Single Tile's information
 *
 */
public class TileInfo {
  public static final byte ANIM_SPEED_FAST = 1;
  public static final byte ANIM_SPEED_NORMAL = 4;
  public static final byte ANIM_SPEED_SLOW = 16;
  
  public static final byte TILE_PLACE_WALL_OR_FLOOR = 0;
  public static final byte TILE_PLACE_OBJECT = 1;
  public static final byte TILE_PLACE_ITEM = 2;
  public static final byte TILE_PLACE_DECORATION = 3;
  public static final byte TILE_PLACE_CREATURE = 4;
  public static final byte TILE_PLACE_TOP = 5;
  
  public static final byte TILE_SERIES_BASIC_TYPE_SINGLE_OBJECT = 0;
  public static final byte TILE_SERIES_TYPE_SINGLE = 0;
  public static final byte TILE_SERIES_TYPE_CORNER_NW = 1; 
  public static final byte TILE_SERIES_TYPE_NORTH = 2; 
  public static final byte TILE_SERIES_TYPE_CORNER_NE = 3; 
  public static final byte TILE_SERIES_TYPE_EAST = 4; 
  public static final byte TILE_SERIES_TYPE_CORNER_SE = 5; 
  public static final byte TILE_SERIES_TYPE_SOUTH = 6; 
  public static final byte TILE_SERIES_TYPE_CORNER_SW = 7; 
  public static final byte TILE_SERIES_TYPE_WEST = 8; 
  public static final byte TILE_SERIES_TYPE_CENTER = 9;  
  public static final byte TILE_SERIES_TYPE_INNER_CORNER_NW = 10; 
  public static final byte TILE_SERIES_TYPE_INNER_CORNER_NE = 11; 
  public static final byte TILE_SERIES_TYPE_INNER_CORNER_SE = 12; 
  public static final byte TILE_SERIES_TYPE_INNER_CORNER_SW = 13;
  public static final byte TILE_SERIES_BASIC_TYPE_BASE = 1;
  public static final byte TILE_SERIES_TYPE_WALL_WEST_TOP = 14; 
  public static final byte TILE_SERIES_TYPE_WALL_WEST_BOTTOM = 15; 
  public static final byte TILE_SERIES_TYPE_WALL_CENTER_TOP = 16; 
  public static final byte TILE_SERIES_TYPE_WALL_CENTER_BOTTOM = 17; 
  public static final byte TILE_SERIES_TYPE_WALL_EAST_TOP = 18; 
  public static final byte TILE_SERIES_TYPE_WALL_EAST_BOTTOM = 19; 
  public static final byte TILE_SERIES_BASIC_TYPE_WALL = 2;
  public static final byte TILE_SERIES_TYPE_OBJECT_2X2_NW = 20; 
  public static final byte TILE_SERIES_TYPE_OBJECT_2X2_NE = 21; 
  public static final byte TILE_SERIES_TYPE_OBJECT_2X2_SE = 22; 
  public static final byte TILE_SERIES_TYPE_OBJECT_2X2_SW = 23;
  public static final byte TILE_SERIES_BASIC_TYPE_OBJECT_2X2 = 3;
  public static final byte TILE_SERIES_TYPE_OBJECT_2X1_W = 24; 
  public static final byte TILE_SERIES_TYPE_OBJECT_2X1_E = 25;
  public static final byte TILE_SERIES_BASIC_TYPE_OBJECT_2X1 = 4;
  public static final byte TILE_SERIES_TYPE_OBJECT_1X2_N = 26; 
  public static final byte TILE_SERIES_TYPE_OBJECT_1X2_S = 27; 
  public static final byte TILE_SERIES_BASIC_TYPE_OBJECT_1X2 = 5;
  public static final byte TILE_SERIES_TYPE_OBJECT_3X1_W = 28; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X1_C = 29; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X1_E = 30;
  public static final byte TILE_SERIES_BASIC_TYPE_OBJECT_3X1 = 6;
  public static final byte TILE_SERIES_TYPE_OBJECT_1X3_N = 31; 
  public static final byte TILE_SERIES_TYPE_OBJECT_1X3_C = 32; 
  public static final byte TILE_SERIES_TYPE_OBJECT_1X3_S = 33; 
  public static final byte TILE_SERIES_BASIC_TYPE_OBJECT_1X3 = 7;
  public static final byte TILE_SERIES_TYPE_OBJECT_2X3_NW = 34; 
  public static final byte TILE_SERIES_TYPE_OBJECT_2X3_NE = 35; 
  public static final byte TILE_SERIES_TYPE_OBJECT_2X3_W = 36; 
  public static final byte TILE_SERIES_TYPE_OBJECT_2X3_E = 37; 
  public static final byte TILE_SERIES_TYPE_OBJECT_2X3_SW = 38; 
  public static final byte TILE_SERIES_TYPE_OBJECT_2X3_SE = 39; 
  public static final byte TILE_SERIES_BASIC_TYPE_OBJECT_2X3 = 8;
  public static final byte TILE_SERIES_TYPE_OBJECT_3X3_NW = 40; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X3_N = 41; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X3_NE = 42; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X3_E = 43; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X3_SE = 44; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X3_S = 45; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X3_SW = 46; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X3_W = 47; 
  public static final byte TILE_SERIES_TYPE_OBJECT_3X3_C = 48; 
  public static final byte TILE_SERIES_BASIC_TYPE_OBJECT_3X3 = 9;
  public static final byte TILE_SERIES_TYPE_LAST = 48;
  
  public static final int LIGHT_EFFECT_NO_EFFECT = 0;
  public static final int LIGHT_EFFECT_DIM_LIGHT = 1;
  public static final int LIGHT_EFFECT_FIREPLACE = 2;
  public static final int LIGHT_EFFECT_BRIGHT_LIGHT = 3;
  public static final int LIGHT_EFFECT_NORMAL_LIGHT = 4;
  public static final int LIGHT_EFFECT_MAGICAL = 5;
  public static final int LIGHT_EFFECT_MAGICAL_FAST = 6;
  public static final int LIGHT_EFFECT_CAST_SHADOW = 7;
  
  private int nextAnimTile;
  private byte animSpeed;
  private byte defaultPlace;
  private int tileSeries;
  private byte tileSeriesType;
  private int lightEffect;
  /**
   * Blocked Flags
   * BitMask  Meaning
   * 1        Tile is blocked
   * 2        Tile on north is blocked
   * 4        Tile on west is blocked
   * 8        Tile on south is blocked
   * 16       Tile on east is blocked
   */
  private byte blockedFlags; 
  
  public TileInfo(int tilenumber) {
    setNextAnimTile(tilenumber);
    setAnimSpeed(ANIM_SPEED_FAST);
    setDefaultPlace(TILE_PLACE_WALL_OR_FLOOR);
    setTileSeries(0);
    setTileSeriesType(TILE_SERIES_TYPE_SINGLE);    
    setTilePassable();
  }

  public TileInfo(DataInputStream is) throws IOException {
    blockedFlags = is.readByte();
    tileSeries = is.readInt();
    tileSeriesType = is.readByte();
    defaultPlace = is.readByte();
    animSpeed = is.readByte();
    nextAnimTile = is.readInt();
    lightEffect = is.readInt();
  }
  
  /**
   * Write Tile info to DataOutputStream
   * @param os
   * @throws IOException
   */
  public void writeTileInfo(DataOutputStream os) throws IOException {
    os.writeByte(blockedFlags);  // Blocked flags
    os.writeInt(tileSeries); // Tileseries
    os.writeByte(tileSeriesType); // TileseriesType
    os.writeByte(defaultPlace); // Defaultplace
    os.writeByte(animSpeed); // Defaultplace
    os.writeInt(nextAnimTile); // nextAnimTile
    os.writeInt(lightEffect); // lightEffect
  }
  
  /**
   * getSpeedIndex for editor
   * @return int
   */
  public int getSpeedIndex() {
    if (animSpeed == ANIM_SPEED_FAST) {
      return 0;
    }
    if (animSpeed == ANIM_SPEED_NORMAL) {
      return 1;
    }
    if (animSpeed == ANIM_SPEED_SLOW) {
      return 2;
    }
    return 0;
  }
  
  /**
   * Clears all blockedFlags, tile is passable
   */
  public void setTilePassable() {
    blockedFlags = 0;
  }
  
  /**
   * Tile is blocked and cannot be walked through
   */
  public void setTileIsBlocked() {
    blockedFlags = 1;
  }

  /**
   * setTileNorthBlocked value
   * @param value, true for set and false for disable
   */
  public void setTileNorthBlocked(boolean value) {
    byte original = blockedFlags;    
    byte mask = 2;
    if (value) {
      blockedFlags = (byte) (blockedFlags | mask);
    } else {
      blockedFlags = (byte) (blockedFlags & ~mask);
    }
    if (!isBlockedFlagsValid()) {
      blockedFlags = original;
    }
  }

  /**
   * setTileWestBlocked value
   * @param value, true for set and false for disable
   */
  public void setTileWestBlocked(boolean value) {
    byte original = blockedFlags;    
    byte mask = 4;
    if (value) {
      blockedFlags = (byte) (blockedFlags | mask);
    } else {
      blockedFlags = (byte) (blockedFlags & ~mask);
    }
    if (!isBlockedFlagsValid()) {
      blockedFlags = original;
    }
  }

  /**
   * setTileSouthBlocked value
   * @param value, true for set and false for disable
   */
  public void setTileSouthBlocked(boolean value) {
    byte original = blockedFlags;    
    byte mask = 8;
    if (value) {
      blockedFlags = (byte) (blockedFlags | mask);
    } else {
      blockedFlags = (byte) (blockedFlags & ~mask);
    }
    if (!isBlockedFlagsValid()) {
      blockedFlags = original;
    }
  }

  /**
   * setTileEastBlocked value
   * @param value, true for set and false for disable
   */
  public void setTileEastBlocked(boolean value) {
    byte original = blockedFlags;    
    byte mask = 16;
    if (value) {
      blockedFlags = (byte) (blockedFlags | mask);
    } else {
      blockedFlags = (byte) (blockedFlags & ~mask);
    }
    if (!isBlockedFlagsValid()) {
      blockedFlags = original;
    }
  }

  /**
   * Is tile blocked or not
   * @return
   */
  public boolean isBlocked() {
    if (blockedFlags  == 1) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Is tile on north blocked or not
   * @return
   */
  public boolean isNorthBlocked() {
    if ((blockedFlags & 2) == 2) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Is tile on west blocked or not
   * @return
   */
  public boolean isWestBlocked() {
    if ((blockedFlags & 4) == 4) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Is tile on south blocked or not
   * @return
   */
  public boolean isSouthBlocked() {
    if ((blockedFlags & 8) == 8) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Is tile on east blocked or not
   * @return
   */
  public boolean isEastBlocked() {
    if ((blockedFlags & 16) == 16) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check that blocked is still valid
   * @return true if value is still valid
   */
  private boolean isBlockedFlagsValid() {
    // Check that tile is not block and also direction blocked
    if (((blockedFlags & 1) == 1) && (blockedFlags > 1)) {
      return false;      
    }
    // Check that tile is not blocked for all directions
    if ((blockedFlags & 30) == 30) {
      return false;
    }
    return true;
  }

  public String getSeriesTypeAsString() {
    String tmp="Unknown";
    switch (tileSeriesType) {
    case TILE_SERIES_TYPE_CENTER: tmp = "Center"; break;
    case TILE_SERIES_TYPE_CORNER_NE: tmp = "Corner NE"; break;
    case TILE_SERIES_TYPE_CORNER_NW: tmp = "Corner NW"; break;
    case TILE_SERIES_TYPE_CORNER_SE: tmp = "Corner SE"; break;
    case TILE_SERIES_TYPE_CORNER_SW: tmp = "Corner SW"; break;
    case TILE_SERIES_TYPE_EAST: tmp = "East"; break;
    case TILE_SERIES_TYPE_NORTH: tmp = "North"; break;
    case TILE_SERIES_TYPE_WEST: tmp = "West"; break;
    case TILE_SERIES_TYPE_SOUTH: tmp = "South"; break;
    case TILE_SERIES_TYPE_SINGLE: tmp = "Single"; break;
    case TILE_SERIES_TYPE_INNER_CORNER_NE: tmp = "Inner corner NE"; break;
    case TILE_SERIES_TYPE_INNER_CORNER_NW: tmp = "Inner corner NW"; break;
    case TILE_SERIES_TYPE_INNER_CORNER_SE: tmp = "Inner corner SE"; break;
    case TILE_SERIES_TYPE_INNER_CORNER_SW: tmp = "Inner corner SW"; break;
    case TILE_SERIES_TYPE_WALL_EAST_TOP: tmp = "Wall east top"; break;
    case TILE_SERIES_TYPE_WALL_EAST_BOTTOM: tmp = "Wall east bottom"; break;
    case TILE_SERIES_TYPE_WALL_CENTER_TOP: tmp = "Wall center top"; break;
    case TILE_SERIES_TYPE_WALL_CENTER_BOTTOM: tmp = "Wall center bottom"; break;
    case TILE_SERIES_TYPE_WALL_WEST_TOP: tmp = "Wall west top"; break;
    case TILE_SERIES_TYPE_WALL_WEST_BOTTOM: tmp = "Wall west bottom"; break;
    case TILE_SERIES_TYPE_OBJECT_1X2_N: tmp = "Object 1x2 North"; break;
    case TILE_SERIES_TYPE_OBJECT_1X2_S: tmp = "Object 1x2 South"; break;
    case TILE_SERIES_TYPE_OBJECT_2X1_E: tmp = "Object 2x1 East"; break;
    case TILE_SERIES_TYPE_OBJECT_2X1_W: tmp = "Object 2x1 West"; break;
    case TILE_SERIES_TYPE_OBJECT_2X2_NE: tmp = "Object 2x2 NE"; break;
    case TILE_SERIES_TYPE_OBJECT_2X2_NW: tmp = "Object 2x2 NW"; break;
    case TILE_SERIES_TYPE_OBJECT_2X2_SE: tmp = "Object 2x2 SE"; break;
    case TILE_SERIES_TYPE_OBJECT_2X2_SW: tmp = "Object 2x2 SW"; break;
    case TILE_SERIES_TYPE_OBJECT_1X3_C: tmp = "Object 1x3 Center"; break;
    case TILE_SERIES_TYPE_OBJECT_1X3_N: tmp = "Object 1x3 North"; break;
    case TILE_SERIES_TYPE_OBJECT_1X3_S: tmp = "Object 1x3 South"; break;
    case TILE_SERIES_TYPE_OBJECT_3X1_C: tmp = "Object 3x1 Center"; break;
    case TILE_SERIES_TYPE_OBJECT_3X1_W: tmp = "Object 3x1 West"; break;
    case TILE_SERIES_TYPE_OBJECT_3X1_E: tmp = "Object 3x1 East"; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_E: tmp = "Object 2x3 East"; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_W: tmp = "Object 2x3 West"; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_NE: tmp = "Object 2x3 NE"; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_NW: tmp = "Object 2x3 NW"; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_SW: tmp = "Object 2x3 SW"; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_SE: tmp = "Object 2x3 SE"; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_NW: tmp = "Object 3x3 NW"; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_NE: tmp = "Object 3x3 NE"; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_N: tmp = "Object 3x3 N"; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_E: tmp = "Object 3x3 E"; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_SE: tmp = "Object 3x3 SE"; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_S: tmp = "Object 3x3 S"; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_SW: tmp = "Object 3x3 SW"; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_W: tmp = "Object 3x3 W"; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_C: tmp = "Object 3x3 C"; break;

    }
    return tmp;
  }

  public void setDefaultPlace(byte defaultPlace) {
    this.defaultPlace = defaultPlace;
  }



  /**
   * public static final byte TILE_PLACE_WALL_OR_FLOOR = 0;
   * public static final byte TILE_PLACE_OBJECT = 1;
   * public static final byte TILE_PLACE_ITEM = 2;
   * public static final byte TILE_PLACE_DECORATION = 3;
   * public static final byte TILE_PLACE_CREATURE = 4;
   * public static final byte TILE_PLACE_TOP = 5;
   * @return default place for tile
   */
  public byte getDefaultPlace() {
    return defaultPlace;
  }



  public void setAnimSpeed(byte animSpeed) {
    this.animSpeed = animSpeed;
  }



  public byte getAnimSpeed() {
    return animSpeed;
  }



  public void setNextAnimTile(int tilenumber) {
    this.nextAnimTile = tilenumber;
  }



  public int getNextAnimTile() {
    return nextAnimTile;
  }



  public void setTileSeries(int i) {
    this.tileSeries = i;
  }



  public int getTileSeries() {
    return tileSeries;
  }



  public void setTileSeriesType(byte tileSeriesType) {
    this.tileSeriesType = tileSeriesType;
  }

  public byte getTileSeriesBasicType() {
    byte tmp=TILE_SERIES_BASIC_TYPE_BASE;
    switch (tileSeriesType) {
    case TILE_SERIES_TYPE_CENTER: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_CORNER_NE: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_CORNER_NW: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_CORNER_SE: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_CORNER_SW: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_EAST: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_NORTH: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_WEST: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_SOUTH: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_SINGLE: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_INNER_CORNER_NE: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_INNER_CORNER_NW: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_INNER_CORNER_SE: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_INNER_CORNER_SW: tmp = TILE_SERIES_BASIC_TYPE_BASE; break;
    case TILE_SERIES_TYPE_WALL_EAST_TOP: tmp = TILE_SERIES_BASIC_TYPE_WALL; break;
    case TILE_SERIES_TYPE_WALL_EAST_BOTTOM: tmp = TILE_SERIES_BASIC_TYPE_WALL; break;
    case TILE_SERIES_TYPE_WALL_CENTER_TOP: tmp = TILE_SERIES_BASIC_TYPE_WALL; break;
    case TILE_SERIES_TYPE_WALL_CENTER_BOTTOM: tmp = TILE_SERIES_BASIC_TYPE_WALL; break;
    case TILE_SERIES_TYPE_WALL_WEST_TOP: tmp = TILE_SERIES_BASIC_TYPE_WALL; break;
    case TILE_SERIES_TYPE_WALL_WEST_BOTTOM: tmp = TILE_SERIES_BASIC_TYPE_WALL; break;
    case TILE_SERIES_TYPE_OBJECT_1X2_N: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_1X2; break;
    case TILE_SERIES_TYPE_OBJECT_1X2_S: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_1X2; break;
    case TILE_SERIES_TYPE_OBJECT_2X1_E: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X1; break;
    case TILE_SERIES_TYPE_OBJECT_2X1_W: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X1; break;
    case TILE_SERIES_TYPE_OBJECT_2X2_NE: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X2; break;
    case TILE_SERIES_TYPE_OBJECT_2X2_NW: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X2; break;
    case TILE_SERIES_TYPE_OBJECT_2X2_SE: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X2; break;
    case TILE_SERIES_TYPE_OBJECT_2X2_SW: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X2; break;
    case TILE_SERIES_TYPE_OBJECT_1X3_C: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_1X3; break;
    case TILE_SERIES_TYPE_OBJECT_1X3_N: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_1X3; break;
    case TILE_SERIES_TYPE_OBJECT_1X3_S: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_1X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X1_C: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X1; break;
    case TILE_SERIES_TYPE_OBJECT_3X1_W: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X1; break;
    case TILE_SERIES_TYPE_OBJECT_3X1_E: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X1; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_E: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X3; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_W: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X3; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_NE: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X3; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_NW: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X3; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_SW: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X3; break;
    case TILE_SERIES_TYPE_OBJECT_2X3_SE: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_2X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_NW: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_NE: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_N: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_E: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_SE: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_S: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_SW: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_W: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X3; break;
    case TILE_SERIES_TYPE_OBJECT_3X3_C: tmp = TILE_SERIES_BASIC_TYPE_OBJECT_3X3; break;

    }
    return tmp;
  }


  public byte getTileSeriesType() {
    return tileSeriesType;
  }

  public void setLightEffect(int lightEffect) {
    this.lightEffect = lightEffect;
  }

  public int getLightEffect() {
    return lightEffect;
  }
}
