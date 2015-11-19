package org.jheroes.utilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
 * 
 * Utilities for handling streams
 */
public class StreamUtilities {

  /**
   * Reads string from DataInputStream. First 4 octets tell string length
   * then each character is read with 2 octets.
   * @param is DataInputStream
   * @return string
   * @throws IOException if read fails
   */
  public static String readString(DataInputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    int len = is.readInt();
    for (int i=0;i<len;i++) {
      char ch =is.readChar();
      sb.append(ch);
    }
    return sb.toString();
  }
  
  /**
   * Writes string into DataOutputStream. First 4 octets tell string length
   * then each characger is written with 2 octets
   * @param os
   * @param str
   * @throws IOException
   */
  public static void writeString(DataOutputStream os, String str) 
                                               throws IOException {
    if (str!=null) {
      os.writeInt(str.length());
      os.writeChars(str);
    } else {
      os.writeInt(0);
    }
  }
  
  /**
   * Read file as text file return string
   * @param is DataInputStream
   * @return String
   * @throws IOException
   */
  public static String readTextFile(DataInputStream is) throws IOException {
    byte[] dataBuf = new byte[is.available()];
    is.readFully(dataBuf);
    String str = new String(dataBuf,"US-ASCII");
    return str;
  }
  
}
