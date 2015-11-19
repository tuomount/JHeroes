package org.jheroes.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

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
 * Class which handled drawing Rasterized fonts
 *
 */
 public class RasterFonts {

  
  public static int FONT_TYPE_SYSTEM = -1;
  public static int FONT_TYPE_SMALL = 0;
  public static int FONT_TYPE_BIG = 1;
  public static int FONT_TYPE_WHITE = 2;
  
  // Raster fonts
  public final static BufferedImage IMAGE_BIG_FONTS = loadImage(
      RasterFonts.class.getResource("/res/images/big_font.png"));
  public final static BufferedImage IMAGE_SMALL_FONTS = loadImage(
      RasterFonts.class.getResource("/res/images/small_font.png"));
  public final static BufferedImage IMAGE_WHITE_FONTS = loadImage(
      RasterFonts.class.getResource("/res/images/whitefont2.png"));

  
  private final static char[] RASTER_FONT_FACES = {'A','B','C','D','E','F',
    'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y',
    'Z','.',',','!','?',';',':','\'','0','1','2','3','4','5','6','7','8','9',
    'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s',
    't','u','v','w','x','y','z','%','|','(',')','-','+','&','"','[',']','/'}; 
  
  private final static int[] SMALL_RASTER_FONT_SIZE = {
   //'A','B','C','D','E','F',
     11 ,11 , 11, 11, 11, 11,  
   //'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y',
      11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
   //'Z','.',',','!','?',';',':','\'','0','1','2','3','4','5','6','7','8','9',
      11, 6 , 6 , 11, 11,  6 , 6 , 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
   //'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s',
      11, 11, 9 , 11, 10, 9, 11,  11,  7,  9, 11,  8, 11, 10, 11, 11, 10,  9, 10,
   //'t','u','v','w','x','y','z','%','|','(',')','-','+','&','"','[',']','/'
       8, 10, 11, 11, 11, 11, 11, 11, 11,  8, 10, 11, 11, 11, 11, 11, 11, 11
 };
  private final static int[] WHITE_RASTER_FONT_SIZE = {
    //'A','B','C','D','E','F',
      12 ,12 , 12, 13, 10,  9, 
    //'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y',
       12, 12, 6, 10, 12, 12, 13, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 12, 12,
    //'Z','.',',','!','?',';',':','\'','0','1','2','3','4','5','6','7','8','9',
       12,  6,  7,  6, 12,  7,  6,  10, 12, 10, 12, 12, 12, 12, 12, 12, 12, 12,
    //'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s',
       12, 12, 12, 12, 12, 11, 12, 12,  6, 10, 12,  8, 13, 12, 12, 12, 12, 11, 12,
    //'t','u','v','w','x','y','z','%','|','(',')','-','+','&','"','[',']','/'
       12, 12, 12, 13, 12, 12, 10, 12, 12,  8, 8, 12, 12, 13, 13,   8,  8, 12
  };
  
  private Graphics g;
  private int fontType;
  private int x_size;
  private int y_size;
  
  private boolean monospace;
  
  public RasterFonts(Graphics g) {
    this.g = g;
    x_size = 11;
    y_size = 14;
    fontType = FONT_TYPE_SMALL;
    monospace = false;
  }
  
  public void setFontType(int fontType) {
    if (fontType == FONT_TYPE_SMALL) {
      this.fontType = fontType;
      this.x_size = 11;
      this.y_size = 18;
      monospace = false;
    }
    if (fontType == FONT_TYPE_WHITE) {
      this.fontType = fontType;
      this.x_size = 12;
      this.y_size = 18;
      monospace = false;
    }
    if (fontType == FONT_TYPE_BIG) {
      this.fontType = fontType;
      this.x_size = 22;
      this.y_size = 36;
    }
  }
  
  public int getFontType() {
    return fontType;
  }
  
  private int matchCharToFace(char ch) {
    for (int i=0;i<RASTER_FONT_FACES.length;i++) {
      if (ch == RASTER_FONT_FACES[i]) {
        return i;
      }
    }
    return -1;
  }
  
  private int getNonMonoSpaceWidth(int index) {
    int result = x_size;
    if (fontType == FONT_TYPE_SMALL) {
      if (index > -1 && index < SMALL_RASTER_FONT_SIZE.length) {
        result = SMALL_RASTER_FONT_SIZE[index];
      }
    }
    if (fontType == FONT_TYPE_WHITE) {
      if (index > -1 && index < WHITE_RASTER_FONT_SIZE.length) {
        result = WHITE_RASTER_FONT_SIZE[index];
      }
    }
    return result;
  }
  
  /**
   * Draw string with raster fonts
   * @param x coordinate
   * @param y coordinate
   * @param str String
   */
  public void drawString(int x, int y, String str) {
    int pos = x;
    BufferedImage temp = IMAGE_SMALL_FONTS.getSubimage(0, 0,
                                                              11, 18);
    if (str != null) {
      for (int i=0;i<str.length();i++) {
        if (str.charAt(i)!=' ') {
          int index = matchCharToFace(str.charAt(i));
          if (index != -1) {
            if (fontType == FONT_TYPE_SMALL) {
              temp = IMAGE_SMALL_FONTS.getSubimage(index*x_size, 0,
                  x_size, y_size);
            }
            if (fontType == FONT_TYPE_WHITE) {
              temp = IMAGE_WHITE_FONTS.getSubimage(index*x_size, 0,
                  x_size, y_size);
            }
            if (fontType == FONT_TYPE_BIG) {
              temp = IMAGE_BIG_FONTS.getSubimage(index*x_size, 0,
                  x_size, y_size);
            }
            g.drawImage(temp, pos, y, null);
          }
          if (isMonospace()) {
            pos = pos +x_size;
          } else {
            pos = pos +getNonMonoSpaceWidth(index);
          }
          
        } else {
          pos = pos +x_size;
        }
      }
    }
  }

  private static BufferedImage loadImage(URL urlToImage) {
    try {
      return ImageIO.read(urlToImage);
    } catch (IOException e) {
      System.err.print(urlToImage.toString()+" not found!");
      return null;
    } 
  }

  public boolean isMonospace() {
    return monospace;
  }

  public void setMonospace(boolean monospace) {
    this.monospace = monospace;
  }
  
  public int getTextWidth(String text) {
    int result = 0;
    if (text != null && text.length() > 0) {
      for (int i=0;i<text.length();i++) {
        result = result +getNonMonoSpaceWidth(matchCharToFace(text.charAt(i)));
      }
    }
    return result;
  }

  
}
