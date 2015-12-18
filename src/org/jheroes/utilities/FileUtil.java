package org.jheroes.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
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
 * Common file utilities
 * 
 **/
public class FileUtil {

    /**
     * Get the file extension part
     * @param f, file
     * @return return extension part as a lower case
     */
    public static String getExtension(File f) {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /**
     * Reads image from URL
     * @param fn
     * @return
     */
    public static BufferedImage readImageFile(String fn) {
        BufferedImage img = null;
        try {
            File f = new File(fn);
            img = ImageIO.read(f);
            return img;
        } catch (IOException e) {
            // Error while reading just return null
            return null;
        }
    }

    /**
     * Check that two byte buffers have same bytes or both are null
     * @param buf1 Byte buffer 1
     * @param buf2 Byte buffer 2
     * @return boolean, true if all bytes are equal in both buffers
     */
    public static boolean equalByteArray(byte[] buf1, byte[] buf2) {
      if (buf1 == null && buf2 == null) {
        return true;
      }
      if (buf1 != null && buf2 != null && buf1.length == buf2.length) {
        for (int i=0;i<buf1.length;i++) {
          if (buf1[i] != buf2[i]) {
            return false;
          }
        }
        return true;
      } else {
        return false;
      }
    }
}
