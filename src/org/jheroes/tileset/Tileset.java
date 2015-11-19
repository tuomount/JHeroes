package org.jheroes.tileset;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
 * Tileset contains multiple tiles with information
 *
 */
public class Tileset {
  
  private ArrayList<Tile> tiles;
  private ArrayList<TileInfo> tilesInfo;
  private final static byte TILESET_VERSION = 0x31; // Character 1
  
  public final static int ERROR_CODE_FILE_NOT_TILESET = -1;
  public final static int ERROR_CODE_DATA_IS_MISSING = -2;
  public final static int ERROR_CODE_WRONG_FORMAT = -3;
  
  public final static String[] TILESET_NAME = {"Town","Cave&Ruins","Forest",
    "City"};
  public final static String[] TILESET_NAME_RESOURCE = {"/res/tilesets/town.tls",
    "/res/tilesets/cavern_ruins.tls","/res/tilesets/forest.tls",
    "/res/tilesets/city.tls"};
  
  public final static String TILESET_ITEMS = "/res/tilesets/items.tls";

  public final static String TILESET_CHARACTERS = "/res/tilesets/characters.tls";
  public final static String TILESET_EFFECTS = "/res/tilesets/effects.tls";

  public Tileset() {
    tiles = new ArrayList<Tile>();
    tilesInfo = new ArrayList<TileInfo>();
  }
  
  /**
   * Number of tiles
   * @return int
   */
  public int size() {
    int i = tiles.size();
    int j = tilesInfo.size();
    if (i == j) { 
      return j;
    } else {
      return 0;
    }
  }
  
  /**
   * Add new tile to tileset
   * @param input
   */
  public void addTile(Tile input) {
    if (input != null) {
      tiles.add(input);
      TileInfo info = new TileInfo(tiles.size()-1);
      if (tiles.size()>2) {
        int index = tiles.size()-2;
        TileInfo prev = tilesInfo.get(index);
        if (prev != null) {
            info.setTileSeries(prev.getTileSeries());    
            byte type = prev.getTileSeriesType();
            if (type != TileInfo.TILE_SERIES_TYPE_SINGLE) {
              type = (byte) (type +1);
              if (type <= TileInfo.TILE_SERIES_TYPE_LAST) {
                info.setTileSeriesType(type);
              }
            }
        }
      }
      tilesInfo.add(info);
    }
  }

  /**
   * Inserts the specified tile at the specified position in this tileset.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices). Also keeps animation
   * information correct 
   * @param input
   */
  public void addTile(Tile input, int index) {
    if ((input != null) && (index < tiles.size())) {
      // Fix the animation numbers
      for (int i=0;i<tilesInfo.size();i++) {
        TileInfo tmp = tilesInfo.get(i);
        if (tmp.getNextAnimTile() >= index) {
          tmp.setNextAnimTile(tmp.getNextAnimTile()+1);
        }
      }
      // insert tile and new tileinfo
      tiles.add(index, input);
      TileInfo info = new TileInfo(index);
      tilesInfo.add(index, info);
    }
  }

  /**
   * Removes the tile at the specified position in this tileset. 
   * Shifts any subsequent elements to the left 
   * (subtracts one from their indices). Also tries to keep animation information
   * correct.
   * @param index
   */
  public void removeTile(int index) {
    if (index < tiles.size()) {
      // Fix the animation numbers
      for (int i=0;i<tilesInfo.size();i++) {
        TileInfo tmp = tilesInfo.get(i);
        if (tmp.getNextAnimTile() >= index) {
          tmp.setNextAnimTile(tmp.getNextAnimTile()-1);
        }
      }
      tiles.remove(index);
      tilesInfo.remove(index);
      
    }
  }
  
  /**
   * Return tile from index, if not found then returns null
   * @param index
   * @return
   */
  public Tile getTile(int index) {
    if (index < tiles.size()) {
      return tiles.get(index);
    } else {
      return null;
    }
  }

  /**
   * Return TileInfo from index, if not fund then returns null
   * @param index
   * @return
   */
  public TileInfo getTileInfo(int index) {
    if ((index < tilesInfo.size() && index >= 0)) {
      return tilesInfo.get(index);      
    } else {
      return null;
    }
  }

  private void writeSingleTile(int index, DataOutputStream os) throws IOException {
    Tile tmp = getTile(index);
    tmp.writeTile(os);
    TileInfo info = getTileInfo(index);
    info.writeTileInfo(os);
  }
  
  private int readSingleTile(DataInputStream is) throws IOException {
    if (is.available() >= Tile.MAX_HEIGHT*Tile.MAX_WIDTH*4+1+4+12) {
      Tile tmp = new Tile(is);
      tiles.add(tmp);
      TileInfo info = new TileInfo(is);
      tilesInfo.add(info);
      return 0;     
    } else {
      return ERROR_CODE_DATA_IS_MISSING;
    }
      
  }
  
  /**
   * Save tileset to file
   * @param filename
   */
  public void saveTileSet(String filename) {
    DataOutputStream os;
    try {
      os = new DataOutputStream(new FileOutputStream(filename));
      try {
        os.writeBytes("TLST");
        os.writeByte(TILESET_VERSION); // Version "1"
        os.writeByte(Tile.MAX_HEIGHT); // MAX_HEIGHT
        os.writeByte(Tile.MAX_WIDTH); //MAX_WIDTH
        os.writeByte(32); // Bit per Plane
        os.writeInt(this.size());
        for (int i=0;i<this.size();i++) {
          writeSingleTile(i,os);
        }
        
        os.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }  
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Find the series first tile
   * @param series as number
   * @return Index number, if not found then -1
   */
  public int findSeriesFirst(int series) {
    for (int i =0;i<size();i++) {
      TileInfo tmp = getTileInfo(i);
      if (series == tmp.getTileSeries()) {
        return i;
      }
    }
    return -1;
  }
  
  /**
   * Get series with tile index
   * @param index
   * @return series number if not found returns -1
   */
  public int getSeries(int index) {
    int series=-1;
    TileInfo tmp = getTileInfo(index);
    if (tmp != null) {
      series = tmp.getTileSeries();      
    }
    return series;
  }
  
  /**
   * get Series basic type
   * @param index
   * @return -1 if not found, otherwise TILE_SERIES_BASIC_TYPE_???
   */
  public byte getSeriesBasicType(int index) {
    byte res = -1;
    TileInfo tmp = getTileInfo(index);
    if (tmp != null) {      
      res= tmp.getTileSeriesBasicType();
      if (res == TileInfo.TILE_SERIES_BASIC_TYPE_BASE) {
        if (isSeriesOfSingles(index)) {
          res = TileInfo.TILE_SERIES_BASIC_TYPE_SINGLE_OBJECT;
        }
      }
    }
    return res;
    
  }
  
  /**
   * Get the first index of series
   * @param index
   * @return return index if found -1 if not found
   */
  public int getSeriesFirst(int index) {
    int series=-1;
    TileInfo tmp = getTileInfo(index);
    if (tmp != null) {
      series = tmp.getTileSeries();
      for (int i=0;i<=index;i++) {
        tmp = getTileInfo(i);
        if (tmp.getTileSeries() == series) {
          return i;
        }
      }
    }
    return series;
  }
  
  /**
   * Get size of series
   * @param index series number
   * @return size of series
   */
  public int getSeriesSize(int series) {
    int size = 0;
    int index = findSeriesFirst(series);
    if (index != -1) {
      TileInfo tmp = getTileInfo(index);
      for (int i=index;i<size();i++) {
        tmp = getTileInfo(i);
        if (tmp.getTileSeries() == series) {
          size++;
        } else {
          return size;
        }
      }
    }
    return size;
  }
  
  public boolean isSeriesOfSingles(int index) {
    boolean result = false;
    int first = index;
    int second=first+1;
    TileInfo tmp = getTileInfo(second);
    TileInfo tmp2 = getTileInfo(first);
    if ((tmp != null) && (tmp2 != null)) {
      if ((getSeries(first) == getSeries(second)) &&
         (tmp.getTileSeriesType()==TileInfo.TILE_SERIES_TYPE_SINGLE) &&
         (tmp2.getTileSeriesType()==TileInfo.TILE_SERIES_TYPE_SINGLE)) {
        result = true;
      }
    }
    return result;
  }
  
  /**
   * Get index for certain series and type
   * @param series
   * @param type
   * @return tile index, if not found but series is found returns the first
   * otherwise return -1
   */
  public int getSeriesType(int series, byte type) {
    int j = -1;
      for (int i=0;i<size();i++) {
        TileInfo tmp = getTileInfo(i);
        if ((tmp.getTileSeries() == series) &&(tmp.getTileSeriesType()==type)) {
          return i;
        }
        if (tmp.getTileSeries() == series) {
          j = i;
        }
      }
      return j;
    }
  
  /**
   * Is tile animated or not
   * @param index tile Index
   * @return boolean
   */
  public boolean isTileAnimated(int index) {
    TileInfo tmp = getTileInfo(index);
    if (tmp != null) {
      if (tmp.getNextAnimTile() != index) {
        return true;
      }
    }
    return false;
  }
  
  public int loadTileSet(DataInputStream is) {
    int errorCode = 0;
    try {
      if (is.available() >= 12) {
        byte[] header = new byte[5];
        header[0] = is.readByte();
        header[1] = is.readByte();
        header[2] = is.readByte();
        header[3] = is.readByte();
        header[4] = is.readByte();
        if ((header[0]==0x54) && (header[1]==0x4C) && (header[2]==0x53) &&
            (header[3]==0x54) && (header[4]==0x31)) {
          int height = is.readByte();
          int weigth = is.readByte();
          int bpp = is.readByte();
          if ((bpp == 32) && (height==Tile.MAX_HEIGHT) &&(weigth==Tile.MAX_WIDTH)) {
            int numberOfTiles = is.readInt();
            tiles = new ArrayList<Tile>();
            tilesInfo = new ArrayList<TileInfo>();
            for (int i=0;i<numberOfTiles;i++) {
              int error=readSingleTile(is);
              if (error!=0) { errorCode = error;}
            }              
          } else {
            errorCode = ERROR_CODE_WRONG_FORMAT;
          }
        } else {
          errorCode = ERROR_CODE_WRONG_FORMAT;
        }
      } else {
        errorCode = ERROR_CODE_DATA_IS_MISSING;
      }
      
      is.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  
    return errorCode;
  }
  
  public int loadTileSet(String filename) {
    int errorCode = 0;
    DataInputStream is;
    try {
      
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename));
      is = new DataInputStream(bis);
      errorCode = loadTileSet(is);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return errorCode;
  }
}
