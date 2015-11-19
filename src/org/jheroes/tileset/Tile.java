package org.jheroes.tileset;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

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
 * One single Tile
 *
 */
public class Tile {
  
  public static final int MAX_WIDTH = 32;
  public static final int MAX_HEIGHT = 32;
  
  private int[][] imageData;
  private boolean useTransparentColor;
  private int transparentColor;
  private BufferedImage img;
  
  private static BufferedImage[] shades;
  
  /**
   * Constructor for single Tile
   */
  public Tile() {
    imageData = new int[MAX_WIDTH][MAX_HEIGHT];
    useTransparentColor = false;    
    for (int i = 0;i<MAX_WIDTH;i++) {
            for (int j = 0;j<MAX_HEIGHT;j++) {
                 imageData[i][j] = 0;
                }
            }
    img = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR_PRE);
  }
  
  public Tile(DataInputStream is) throws IOException {
    imageData = new int[MAX_WIDTH][MAX_HEIGHT];
    for (int i=0;i<Tile.MAX_WIDTH;i++) {
      for (int j=0;j<Tile.MAX_HEIGHT;j++) {
        imageData[i][j] = is.readInt();
      }
    }
    useTransparentColor = is.readBoolean(); // read TransparentColor boolean
    transparentColor = is.readInt(); // Color itself
    img = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR_PRE);
    for (int i = 0;i<MAX_WIDTH;i++) {
      for (int j = 0;j<MAX_HEIGHT;j++) {
            img.setRGB(i, j, imageData[i][j]);
      }
    }
  }
  
  /**
   * Do not use transparent color 
   */
  public void removeTransparentColor() {
     useTransparentColor = false;
  }
   
  /**
   * Getter for useTransparentColor
   * @return true for transparent color on
   */
  public boolean isTransparentColor() {
    return useTransparentColor;
  }
  
  /**
   * Getter for transparentColor
   * notice that this does not check if useTransparentColor is true or not
   * @return int
   */
  public int getTransparentColor() {
    return transparentColor;
  }
  
  /**
   * Set transparent color to use
   * @param color as int
   */
  public void setTransparentColor(int color)
  {
    useTransparentColor = true;
    transparentColor = color;
  }
  
  public int getColor(int x, int y) {
    if ((x >= 0) && (x < MAX_WIDTH) && (y >= 0) && (y < MAX_HEIGHT)) {
      return imageData[x][y];
    }
    return 0;
  }
  
  /**
   * Draw tile to buffered Image, if Tile has not transparent color it mix
   * the with background smoothly
   * @param screen, target bufferedImage
   * @param x, coordinates in target,
   * @param y, coordinates in target
   * @param shade 0-7, 0 is brightest and 7 darkest
   */
  public void putTile(BufferedImage screen, int x, int y, int shade) {
    if (screen != null) {
      if (useTransparentColor) {
        int height = screen.getHeight();
        int width = screen.getWidth();
        for (int i = 0;i<MAX_WIDTH;i++) {
          for (int j = 0;j<MAX_HEIGHT;j++) {
            if (imageData[i][j] != transparentColor) {
              if ((x+i < width) && (y+j < height)) {
                screen.setRGB(x+i, y+j, getShaded(imageData[i][j],shade));
              }
            }
          }
        }
      } else {
        int height = screen.getHeight();
        int width = screen.getWidth();
        for (int i = 0;i<MAX_WIDTH;i++) {
          for (int j = 0;j<MAX_HEIGHT;j++) {
            if (x+i < width && y+j < height && x+i>-1 && y+j>-1) {
              //screen.setRGB(x+i, y+j, imageData[i][j]);
              screen.setRGB(x+i, y+j, mixColors(imageData[i][j], screen.getRGB(x+i, y+j),shade));
            }
          }
        }
      }
    }
  }
  
  public void putFast(Graphics2D g, int x,int y) {
    g.drawImage(img, x, y, null);
  }
  
  /**
   * Draw tile to buffered Image, no transparentColor used 
   * @param screen, target bufferedImage
   * @param x, coordinates in target,
   * @param y, coordinates in target
   * @param shade 0-7, 0 is brightest and 7 darkest
   */
  public void putTileNoMixing(BufferedImage screen, int x, int y, int shade) {
    if (screen != null) {
      int height = screen.getHeight();
      int width = screen.getWidth();
      for (int i = 0;i<MAX_WIDTH;i++) {
        for (int j = 0;j<MAX_HEIGHT;j++) {
          if ((x+i < width) && (y+j < height)) {
              screen.setRGB(x+i, y+j, getShaded(imageData[i][j],shade));
          }
        }
      }
    }
  }
  
  /**
   * Get Tile as Icon
   * @return Icon
   */
  public Icon getTileAsIcon() {
    BufferedImage screen = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gr = screen.createGraphics();
    gr.setColor(new Color(0,0,0));
    gr.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    putTile(screen,0,0,0);
    return new ImageIcon(screen);
  }
  
  /**
   * Load tile from integer array, must be 32x32
   * @param input
   */
  public void loadTile(int[][] input) {
    for (int i = 0;i<MAX_WIDTH;i++) {
      for (int j = 0;j<MAX_WIDTH;j++) {
        imageData[i][j] = input[i][j];        
      }
    }  
  }

  /**
   * Load tile from bufferedImage, image must be 32x32
   * @param input
   */
  public void loadTile(BufferedImage input) {
    if (input != null) {
      if ((input.getHeight() == MAX_HEIGHT) && (input.getWidth() == MAX_WIDTH))
      for (int i = 0;i<MAX_WIDTH;i++) {
        for (int j = 0;j<MAX_HEIGHT;j++) {
          imageData[i][j] = input.getRGB(i, j);        
        }
      }
    }
  }

  /**
   * get Shaded color without alphachannel
   * @param drawColor
   * @param shade, 0-7
   * @return
   */
  public int getShaded(int drawColor, int shade) {
    int dr = getRed(drawColor);
    int dg = getGreen(drawColor);
    int db = getBlue(drawColor);
    if ((shade >= 0) && (shade < 8)) {
      dr = dr -shade*dr/8;
      dg = dg -shade*dg/8;
      db = db -shade*db/8;
    }
    return getColor(dr,dg,db);    
  }
  
  /**
   * Mix two colors using alpha channel
   * @param drawColor, color which is going to be drawn with alpha channel
   * @param backColor, background color
   * @return mixed color.
   */
  public int mixColors(int drawColor, int backColor,int shade) {
    int da = getAlpha(drawColor);
    int dr = getRed(drawColor);
    int dg = getGreen(drawColor);
    int db = getBlue(drawColor);
    int br = getRed(backColor);
    int bg = getGreen(backColor);
    int bb = getBlue(backColor);
    if ((shade >= 0) && (shade < 8)) {
      dr = dr -shade*dr/12;
      dg = dg -shade*dg/12;
      db = db -shade*db/12;
    }
    if (da == 255) {
      // there is any opaque so just the drawcolor.
      //return drawColor;
      return getColor(dr,dg,db);
    } else if (da == 128) {
      // Half opaque
      return getColor((dr+br)/2,(dg+bg)/2,(db+bb)/2);
    } else if (da == 0) {
      // full opaque so just the background color
      return backColor;
    } else {
      // Need to calculate opaque
      float div = 255;
      float  nr =  (da/div) *dr+((255-da)/div)*br;
      float ng =  (da/div)*dg+ ((255-da)/div)*bg;
      float nb =  (da/div)*db+((255-da)/div)*bb;      
      return getColor(Math.round(nr),Math.round(ng),Math.round(nb));
    }
  }
  
  /**
   * Return int color as ARGB, where A is 255.
   * @param r
   * @param g
   * @param b
   * @return int
   */
  public static int getColor(int r, int g, int b) {
    int tmp = 0xff000000;
    r = r << 16;
    g = g << 8;
    tmp = tmp | r;
    tmp = tmp | g;
    tmp = tmp | b;
    return tmp;
    
  }
  
  /**
   * Get the colors Alpha
   * @param input color in ARGB
   * @return int as 0-255
   */
  public static int getAlpha(int input) {
    int tmp = input & 0xff000000;
    tmp = tmp >> 24;
    int j = tmp & 0x000000ff;
    return j;
  }
  
  /**
   * Get color's red channel
   * @param input color in ARGB
   * @return int as 0-255
   */
  public static int getRed(int input) {
    int tmp = input & 0x00ff0000;
    tmp = tmp >> 16;
    return tmp;
  }
  /**
   * Get color's Green channel
   * @param input color in ARGB
   * @return int as 0-255
   */
  public static int getGreen(int input) {
    int tmp = input & 0x0000ff00;
    tmp = tmp >> 8;
    return tmp;
  }
  /**
   * Get color's Blue channel
   * @param input color in ARGB
   * @return int as 0-255
   */
  public static int getBlue(int input) {
    int tmp = input & 0x000000ff;
    return tmp;
  }
  
  public void writeTile(DataOutputStream os) throws IOException {
    for (int i=0;i<Tile.MAX_WIDTH;i++) {
      for (int j=0;j<Tile.MAX_HEIGHT;j++) {
        os.writeInt(getColor(i, j));
      }
    }
    os.writeBoolean(isTransparentColor()); // Write TransparentColor boolean
    os.writeInt(getTransparentColor()); // Color itself
  }
  
  public static void drawShade(Graphics2D g,int x,int y, int shade) {
    if (shades == null) {
      shades = new BufferedImage[7];
      for (int i =0;i<shades.length;i++) {
        shades[i] = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D g2 = shades[i].createGraphics();
        g2.setColor(new Color(0, 0, 0, (i+1)*27));
        g2.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
      }
    }
    if (shade > 0 && shade < 8)
    g.drawImage(shades[shade-1], x, y, null);
  }
}
