package org.jheroes.soundplayer;

import java.io.IOException;
import java.util.HashMap;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import org.jheroes.game.DebugOutput;

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
 * Audio Manager to read SoundPlayer name and description list and
 * play sound according the list. This should be run on separate thread.
 *
 */
public class AudioManager extends Thread {

  
  private final int MAX_SOUNDS = 16;
  private HashMap<String, AudioFile> audioBuffers = new HashMap<String, AudioFile>();
  private Clip[] clips=new Clip[MAX_SOUNDS];
  private String[] audioNames = new String[MAX_SOUNDS];
  private int streamIndex=0;

  /**
   * Is sound playing in clips currently
   * @param name Sound name
   * @return true if playing otherwise false
   */
  public boolean isSoundPlaying(String name) {
    for (int i=0;i<MAX_SOUNDS;i++) {
      if ((clips[i] != null) && (clips[i].isRunning())) {
        if (audioNames[i] != null && audioNames[i].equalsIgnoreCase(name)) {
          return true;
        }
      }
    }
    return false;
  }
  
  /**
   * Get number of sounds playing
   * @return int
   */
  public int getCurrentSounds() {
    int result = 0;
    for (int i=0;i<MAX_SOUNDS;i++) {
      if ((clips[i] != null) && (clips[i].isRunning())) {
        result++;
      }
    }
    return result;
  }

  /**
   * Get Audio file for certain file
   * @param name Sound name
   * @return AudioFile or null
   */
  public AudioFile getAudioFile(String name) {
    AudioFile result = audioBuffers.get(name);
    if (result == null) {
      try {
        result = new AudioFile(name);
      } catch (IOException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        result = null;
      }
      if (result != null) {
        audioBuffers.put(name, result);
      }
    }
    return result;
  }
  
  
  /**
   * Playsound with description
   * @param name Sound Name
   * @param soundDesc Description
   */
  private void playSound(String name,String soundDesc) {
    try {
      if (clips[streamIndex] != null) {
        try {
          clips[streamIndex].close();
        } catch (IllegalStateException e) {
          DebugOutput.debugLog("Catched Illegal State Exception!");
        }
      }
      AudioFile af = getAudioFile(name);
      if (af != null) {
        DataLine.Info info = new DataLine.Info(Clip.class, af.getFormat());
        clips[streamIndex] = (Clip) AudioSystem.getLine(info);
        clips[streamIndex].open(af.getFormat(),af.getBuffer(), 0,af.getBufferLength());
        //Volume set for SFX
        try {  
          FloatControl gainControl = (FloatControl) clips[streamIndex].getControl(FloatControl.Type.VOLUME);      
          gainControl.setValue(gainControl.getMaximum()*SoundPlayer.getSoundVolume()/100);
        } catch (IllegalArgumentException e) {
          try {
            FloatControl gainControl = (FloatControl) clips[streamIndex].getControl(FloatControl.Type.MASTER_GAIN);      
            gainControl.setValue(gainControl.getMinimum()*((100-SoundPlayer.getSoundVolume())/100));
          } catch (IllegalArgumentException e1){
            System.out.println("Your audio system does not support VOLUME or MASTER_GAIN control...Playing default volume");
          }
        }
        try {
          Thread.sleep(5);
        } catch (InterruptedException e) {
          // Do nothing
        }
        clips[streamIndex].start();
        audioNames[streamIndex] = soundDesc;
        streamIndex++;
        if(streamIndex==MAX_SOUNDS) {
          streamIndex=0;
        }
      } else {
        System.out.println("NULL sound "+name);
      }
      //DebugOutput.debugLog("Playing sounds...Total:"+getCurrentSounds());
    } catch (LineUnavailableException e) {
      System.out.println("Playing sound failed: "+e.getMessage());
      System.out.println("Audio file:"+name);
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    String name;
    String desc;
    while (true) {
      try {
        Thread.sleep(25);
      } catch (InterruptedException e) {
      }
      do {
        name = SoundPlayer.getNextSoundName();
        desc = SoundPlayer.getNextSoundDesc();
        if (name != null && desc != null) {
          playSound(name, desc);
        }
      } while(name != null);
    }

  }

}
