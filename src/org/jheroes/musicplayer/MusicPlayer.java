package org.jheroes.musicplayer;

import java.io.BufferedInputStream;
import java.io.InputStream;

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
 * MusicPlayer
 *
 */
public class MusicPlayer {

  // Joel Day's Music
  public static String MUSIC_FILE_ANCIENT_FOREST = "/res/musics/AncientForest.mp3";
  public static String MUSIC_FILE_CLOUD_TOP = "/res/musics/CloudTopLoops.mp3";
  public static String MUSIC_FILE_CORN_FIELDS = "/res/musics/CornFields.mp3";
  public static String MUSIC_FILE_MEADOW_OF_THE_PAST = "/res/musics/MeadowOfThePast.mp3";
  public static String MUSIC_FILE_VILLAGE = "/res/musics/Village.mp3";
  // Andreas Viklund and JoJo's Music
  public static String MUSIC_FILE_THEME_OF_NJALS_SAGA = "/res/musics/av_1200.mp3";
  // Alexandr Zhelanov
  public static String MUSIC_FILE_KINGDOM_THEME = "/res/musics/KingdomTheme.mp3";
  public static String MUSIC_FILE_HEROES_THEME = "/res/musics/HeroesTheme_0.mp3";
  public static String MUSIC_FILE_PATH_TO_LAKE_LAND = "/res/musics/PathToLakeLand.ogg";
  public static String MUSIC_FILE_SDP_THEME_MEDIEVAL = "/res/musics/SDP-ThemeMedieval.ogg";
  public static String MUSIC_FILE_PRELUDE_STORY = "/res/musics/PreludeStory.ogg";
  
  // Kevin MacLeod
  public static String MUSIC_FILE_OURSTORYBEGINS = "/res/musics/OurStoryBegins.mp3";
  public static String MUSIC_FILE_FAILING_DEFENSE = "/res/musics/FailingDefense.mp3";
  public static String MUSIC_FILE_THATCHED_VILLAGERS = "/res/musics/ThatchedVillagers.mp3";
  public static String MUSIC_FILE_WE_GOT_TROUBLE = "/res/musics/WeGotTrouble.mp3";
  public static String MUSIC_FILE_SNEAKY_SNITCH = "/res/musics/SneakySnitch.mp3";
  public static String MUSIC_FILE_GHOSTAPOCALYPSE_MASTER = "/res/musics/Ghostpocalypse-7Master.mp3";
  public static String MUSIC_FILE_INDUSTRIAL_REVOLUTION = "/res/musics/IndustrialRevolution.mp3";
  public static String MUSIC_FILE_HERO_DOWN = "/res/musics/HeroDown.mp3";
  public static String MUSIC_FILE_COLOSSUS = "/res/musics/Colossus.mp3";
  public static String MUSIC_FILE_FIVE_ARMIES = "/res/musics/FiveArmies.mp3";
  public static String MUSIC_FILE_ACHAID_CHEIDE = "/res/musics/AchaidhCheide.mp3";
  public static String MUSIC_FILE_CELTIC_IMPULSE = "/res/musics/CelticImpulse.mp3";
  public static String MUSIC_FILE_FOLK_ROUND = "/res/musics/FolkRound.mp3";
  public static String MUSIC_FILE_THE_PYRE = "/res/musics/ThePyre.mp3";

  // cynicmusic.com pixelsphere.org
  public static String MUSIC_FILE_BATTLE_THEME_A = "/res/musics/battleThemeA.ogg";
  
  public static String MUSIC_FILE_ANCIENT_FOREST_NAME = "Ancient Forest";
  public static String MUSIC_FILE_CLOUD_TOP_NAME = "Cloud Top";
  public static String MUSIC_FILE_CORN_FIELDS_NAME = "Corn Fields";
  public static String MUSIC_FILE_MEADOW_OF_THE_PAST_NAME = "Meadow Of The Past";
  public static String MUSIC_FILE_VILLAGE_NAME = "Village";
  public static String MUSIC_FILE_OURSTORYBEGINS_NAME = "Our Story Begins";
  public static String MUSIC_FILE_THEME_OF_NJALS_SAGA_NAME = "Theme of Njals Saga";
  public static String MUSIC_FILE_FAILING_DEFENSE_NAME = "Failing Defense";
  public static String MUSIC_FILE_THATCHED_VILLAGERS_NAME = "Thatched Villagers";
  public static String MUSIC_FILE_WE_GOT_TROUBLE_NAME = "We Got Trouble";
  public static String MUSIC_FILE_SNEAKY_SNITCH_NAME = "SneakySnitch";
  public static String MUSIC_FILE_GHOSTAPOCALYPSE_MASTER_NAME = "Ghostapocalypse:Master";
  public static String MUSIC_FILE_INDUSTRIAL_REVOLUTION_NAME = "Industrial Revolution";
  public static String MUSIC_FILE_HERO_DOWN_NAME = "Hero Down";
  public static String MUSIC_FILE_COLOSSUS_NAME = "Colossus";
  public static String MUSIC_FILE_FIVE_ARMIES_NAME = "Five Armies";
  public static String MUSIC_FILE_ACHAID_CHEIDE_NAME = "Achaidh Cheide";
  public static String MUSIC_FILE_CELTIC_IMPULSE_NAME = "Celtic Impulse";
  public static String MUSIC_FILE_FOLK_ROUND_NAME = "Folk Round";
  public static String MUSIC_FILE_THE_PYRE_NAME = "The Pyre";
  public static String MUSIC_FILE_KINGDOM_THEME_NAME = "Kingdom Theme";
  public static String MUSIC_FILE_HEROES_THEME_NAME = "Heroes Theme";
  public static String MUSIC_FILE_PATH_TO_LAKE_LAND_NAME = "Path To Lake Land";
  public static String MUSIC_FILE_SDP_THEME_MEDIEVAL_NAME = "SDP - Theme Medieval";
  public static String MUSIC_FILE_BATTLE_THEME_A_NAME = "Battle Theme A";
  public static String MUSIC_FILE_PRELUDE_STORY_NAME = "Prelude(Story)";

  public static String[] MUSIC_FILES = {MUSIC_FILE_VILLAGE,
    MUSIC_FILE_ANCIENT_FOREST,MUSIC_FILE_CLOUD_TOP,MUSIC_FILE_CORN_FIELDS,
    MUSIC_FILE_MEADOW_OF_THE_PAST, MUSIC_FILE_OURSTORYBEGINS,
    MUSIC_FILE_THEME_OF_NJALS_SAGA,MUSIC_FILE_FAILING_DEFENSE,
    MUSIC_FILE_THATCHED_VILLAGERS,MUSIC_FILE_WE_GOT_TROUBLE,
    MUSIC_FILE_SNEAKY_SNITCH,MUSIC_FILE_GHOSTAPOCALYPSE_MASTER,
    MUSIC_FILE_INDUSTRIAL_REVOLUTION,MUSIC_FILE_HERO_DOWN,MUSIC_FILE_COLOSSUS,
    MUSIC_FILE_FIVE_ARMIES,MUSIC_FILE_ACHAID_CHEIDE,MUSIC_FILE_CELTIC_IMPULSE,
    MUSIC_FILE_FOLK_ROUND,MUSIC_FILE_THE_PYRE,MUSIC_FILE_KINGDOM_THEME,
    MUSIC_FILE_HEROES_THEME,MUSIC_FILE_PATH_TO_LAKE_LAND,
    MUSIC_FILE_SDP_THEME_MEDIEVAL,MUSIC_FILE_BATTLE_THEME_A,MUSIC_FILE_PRELUDE_STORY};

  public static String[] MUSIC_FILES_NAMES = {MUSIC_FILE_VILLAGE_NAME,
    MUSIC_FILE_ANCIENT_FOREST_NAME,MUSIC_FILE_CLOUD_TOP_NAME,MUSIC_FILE_CORN_FIELDS_NAME,
    MUSIC_FILE_MEADOW_OF_THE_PAST_NAME, MUSIC_FILE_OURSTORYBEGINS_NAME,
    MUSIC_FILE_THEME_OF_NJALS_SAGA_NAME,MUSIC_FILE_FAILING_DEFENSE_NAME,
    MUSIC_FILE_THATCHED_VILLAGERS_NAME,MUSIC_FILE_WE_GOT_TROUBLE_NAME,
    MUSIC_FILE_SNEAKY_SNITCH_NAME,MUSIC_FILE_GHOSTAPOCALYPSE_MASTER_NAME,
    MUSIC_FILE_INDUSTRIAL_REVOLUTION_NAME,MUSIC_FILE_HERO_DOWN_NAME,
    MUSIC_FILE_COLOSSUS_NAME,MUSIC_FILE_FIVE_ARMIES_NAME,
    MUSIC_FILE_ACHAID_CHEIDE_NAME,MUSIC_FILE_CELTIC_IMPULSE_NAME,
    MUSIC_FILE_FOLK_ROUND_NAME,MUSIC_FILE_THE_PYRE_NAME,
    MUSIC_FILE_KINGDOM_THEME_NAME,MUSIC_FILE_HEROES_THEME_NAME,
    MUSIC_FILE_PATH_TO_LAKE_LAND_NAME,MUSIC_FILE_SDP_THEME_MEDIEVAL_NAME,
    MUSIC_FILE_BATTLE_THEME_A_NAME,MUSIC_FILE_PRELUDE_STORY_NAME};

  private static boolean playing = false;
  private static boolean musicEnabled = true;
  private static OggPlayer player; 
  private static String currentlyPlaying="";
  private static String nextSong=MUSIC_FILE_CORN_FIELDS;
  

  public static int findMusicFileByName(String name) {
    for (int i=0;i<MUSIC_FILES.length;i++) {
      if (MUSIC_FILES[i].equals(name)) {
        return i;
      }
    }
    return 0;
  }
  
  /**
   *  play the MP3 file to the sound card
   * @param musicFile String
   */
  public synchronized static void play(String musicFile) {
      if (musicEnabled) {
        currentlyPlaying = musicFile;
        if (playing) {
          stop();
        }
        try {
            InputStream is = MusicPlayer.class.getResourceAsStream(musicFile);
            BufferedInputStream bis = new BufferedInputStream(is);
            if (player == null) {
              player = new OggPlayer(bis);
            } else {
              player.stop();
              int loop=0;
              while(!player.isStopped()) {
                loop++;
                Thread.sleep(5);
                if (loop > 1000) {
                  break;
                }
              }
              player = new OggPlayer(bis);
            }
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + musicFile);
            System.out.println(e);
            return;
        }
  
        // run in new thread to play in background
        new Thread() {
            public void run() {
                try {
                  playing = true;
                  player.play();
                  playing = false;
                }
                catch (Exception e) { System.out.println(e);
                  System.out.println(currentlyPlaying);
                  e.printStackTrace();
                }
                
            }
        }.start();
      }
  }
   
  
  public static boolean isPlaying() {
    return playing;
  }

  public static void stop() {
    player.stop();
  }
  
  public synchronized static String getCurrentlyPlaying() {
    return currentlyPlaying;
  }

  public static void setMusicEnabled(boolean musicEnabled) {   
    MusicPlayer.musicEnabled = musicEnabled;
    if ((!MusicPlayer.isMusicEnabled()) && (isPlaying())) {
      player.close();
      playing = false;      
      currentlyPlaying = "";
    }
  }


  public static boolean isMusicEnabled() {
    return musicEnabled;
  }

  public static void setNextSong(String nextSong) {
    MusicPlayer.nextSong = nextSong;
  }

  public static String getNextSong() {
    return nextSong;
  }
  
}
