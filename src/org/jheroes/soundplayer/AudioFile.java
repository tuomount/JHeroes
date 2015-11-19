package org.jheroes.soundplayer;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 *
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
 * Reads the Audio File format and buffer
 *
 */
public class AudioFile {

  private static final int blockSize=64000;
  
  private AudioFormat format;
  private byte[] buffer;
  private String audioName;
  
  /**
   * Constructor for AudioFile
   * @param audioName String to resource
   * @throws IOException
   */
  public AudioFile(String audioName) throws IOException {
    this.audioName = audioName;
    
    URL url = getClass().getResource(this.audioName);
    AudioInputStream ais;
    
    try {
      ais = AudioSystem.getAudioInputStream(url);
    } catch (UnsupportedAudioFileException e) {
      throw new IOException("Unsupported audio file: "+e.getMessage());
    }
    format = ais.getFormat();
    int len =0;
    int offset =0;
    do {
      byte[] tmpBuf = new byte[blockSize];
      try {
        len = ais.read(tmpBuf, 0, tmpBuf.length);
      } catch (IOException e) {
        ais.close();
        throw new IOException("Reading audio failed: "+e.getMessage());
      }
      if (len != -1) {
        byte[] targetBuf;
        if (buffer == null) {
          targetBuf = new byte[len];
          offset = 0;
        } else {
          targetBuf = new byte[buffer.length+len];          
          System.arraycopy(buffer, 0, targetBuf, 0, buffer.length);
          offset = buffer.length;
        }
        System.arraycopy(tmpBuf, 0, targetBuf, offset, len-1);
        buffer = targetBuf;
      }
    } while (len != -1);    
    ais.close();
    if (buffer == null) {
      throw new IOException("No audio buffer available!");
    }
  }
  
  public AudioFormat getFormat() {
    return format;
  }
  
  public byte[] getBuffer() {
    return buffer;
  }
  public int getBufferLength() {
    return buffer.length;
  }
}
