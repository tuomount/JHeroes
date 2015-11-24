package org.jheroes.game;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.jheroes.game.storyscreen.EndStory;
import org.jheroes.game.storyscreen.LoseStory;
import org.jheroes.game.storyscreen.StartStory;
import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.RasterFonts;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.buttons.ImageGameButton;
import org.jheroes.gui.buttons.MenuButton;
import org.jheroes.gui.labels.CharacterLabel;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameLogArea;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.labels.GameTextField;
import org.jheroes.gui.labels.ImageLabel;
import org.jheroes.gui.lists.PerkList;
import org.jheroes.gui.panels.GamePanel;
import org.jheroes.gui.panels.GameSearchPanel;
import org.jheroes.gui.panels.GameSpellPanel;
import org.jheroes.gui.panels.GameTravelPanel;
import org.jheroes.gui.panels.MapPanel;
import org.jheroes.gui.panels.PanelWithArrows;
import org.jheroes.gui.panels.PartyMemberPanel;
import org.jheroes.gui.panels.TitledPanel;
import org.jheroes.gui.scrollPanel.GameScrollBarUI;
import org.jheroes.gui.slider.GameSliderUI;
import org.jheroes.journal.Journal;
import org.jheroes.map.DiceGenerator;
import org.jheroes.map.Event;
import org.jheroes.map.Map;
import org.jheroes.map.MapUtilities;
import org.jheroes.map.Party;
import org.jheroes.map.character.CharTask;
import org.jheroes.map.character.Character;
import org.jheroes.map.character.Faces;
import org.jheroes.map.character.Perks;
import org.jheroes.map.character.Spell;
import org.jheroes.map.character.SpellFactory;
import org.jheroes.map.item.Item;
import org.jheroes.musicplayer.MusicPlayer;
import org.jheroes.musicplayer.OggPlayer;
import org.jheroes.soundplayer.SoundPlayer;
import org.jheroes.talk.Talk;
import org.jheroes.utilities.StreamUtilities;




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
 * Main class for Game itself
 * 
 **/

public class Game extends JFrame implements ActionListener {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static final int GAME_STATE_MAINMENU = 0;
  public static final int GAME_STATE_CREDITS = 1;
  public static final int GAME_STATE_NEWGAME = 2;
  public static final int GAME_STATE_NEWGAME_SKILLS = 3;
  public static final int GAME_STATE_NEWGAME_PERKS = 4;
  public static final int GAME_STATE_GAME = 5;
  public static final int GAME_STATE_CHARACTER_SHEET = 6;
  public static final int GAME_STATE_TALK = 7;
  public static final int GAME_STATE_JOURNAL = 8;
  public static final int GAME_STATE_BARTERING = 9;
  public static final int GAME_STATE_LOADSCREEN = 10;
  public static final int GAME_STATE_LOADGAME = 11;
  public static final int GAME_STATE_SAVEGAME = 12;
  public static final int GAME_STATE_HERO_DOWN = 13;
  public static final int GAME_STATE_GAME_HELP = 14;
  public static final int GAME_STATE_DEATH_SCREEN = 15;
  public static final int GAME_STATE_INTRO = 16;
  public static final int GAME_STATE_LOSE = 17;
  public static final int GAME_STATE_WIN = 18;
  public static final int GAME_STATE_OPTIONS = 254;
  public static final int GAME_STATE_DEBUGMODE = 255;
  
  public static final String GAME_TITLE = "JHeroes Tutorial";
  public static final String GAME_VERSION ="0.1.0Alpha";
 
  /**
   * Is running in debug mode
   */
  public static boolean debugMode = false;
  /**
   * Is running from JAR, set true when JAR is being built
   */
  public static boolean runningJar = true;
  
  
  /**
   * Current Game state
   */
  private int state;
  
  /**
   * Timer for music
   */
  private Timer musicTimer;
  /**
   * Generic timer used for animation
   */
  private Timer genericTimer;
  /**
   * Turn timer how fast PC can move
   */
  private Timer turnTimer;

  /**
   * Thread used in loading and saving
   */
  private Thread loadThread;
  
  /**
   * Game Party
   */
  private Party party;
  
  /**
   * Game map
   */
  private Map map;
  
  /**
   * Journal for the game
   */
  private Journal journal;
  /**
   * Player characters have not done moves yet
   */
  public static final int TURN_NO_MOVES=0;
  /**
   * 1 Player character is done move, used only in combat
   */
  public static final int TURN_MOVE1=1;
  /**
   * 2 Player character is done move, used only in combat
   */
  public static final int TURN_MOVE2=2;
  /**
   * 3 Player character is done move, used only in combat
   */
  public static final int TURN_MOVE3=3;
  /**
   * 4 Player character is done move, used only in combat
   */
  public static final int TURN_MOVE4=4;
  /**
   * Player characters has done move
   */
  public static final int TURN_MOVES_DONE=999;
  
  private int deathWaiter=0;
  private static final int MAX_NPC_WAITER = 10;
  private int npcWaiter=0;
  private int turnReady;
  /**
   * Player has activated event by walking and event is taking control of map
   */
  private GameEventHandler playingEvent;
  
  private int lastDrawMapPosX=5;
  private int lastDrawMapPosY=5;
  
  //Panels
  private GamePanel mainMenuPanel;
  private GamePanel optionsMenuPanel;
  private GamePanel creditsPanel;
  private GameTextArea creditsTextArea;
  private GamePanel gamePanels;
  private GamePanel partyPanel;
  private GameSearchPanel searchPanel;
  private GameTravelPanel travelPanel;
  private GameSpellPanel spellPanel;
  private Spell castingSpell;
  private int usedItemIndex;
  private MapPanel mapPanel;
  private GameCharacterSheet charSheetPanel;
  private GameTalk gameTalkPanel;
  private GameJournal gameJournalPanel;
  private GameDebugMode gameDebugPanel;
  private GameHelp gameHelpPanel;
  private GameBartering gameBarteringPanel;
  private GameLoadScreen gameLoadScreen;
  private GameGamesScreen gameGamesScreen;
  private GameStoryPanel gameStoryPanel;
  
  private PartyMemberPanel memberPanel[];
  private GameLogArea gameLog;
  
  // Labels
  private ImageLabel faceImageLabel;
  private int faceNumber=0;
  private CharacterLabel newCharLabel;
  private Character newChar;
  private GameButton nextStep;
  private GameButton prevStep;
  
  // Character Creation components
  private GameTextField ccNameField;
  private GameTextField ccFullNameField;
  private GameLabel ccHitpointsLabel;
  private GameLabel ccStaminapointsLabel;
  private GameLabel ccWillpowerLabel;
  private GameLabel ccSkillPointsPerLevelLabel;
  private GameTextArea ccBackText;
  private GameTextArea ccHelpTextAttr;
  private GameTextArea ccHelpTextSkills;
  private GameTextArea ccHelpTextPerks;
  private GameLabel ccAttributePointsLeft;
  private GameLabel ccSkillPointsLeft;
  private GameLabel ccPerksLeft;
  private GamePanel ccAttributePanel;
  private GamePanel ccSkillPanel;
  private GamePanel ccPerkPanel;
  private PerkList ccListPerk;
  private PerkList ccActiveListPerk;
  private TitledPanel ccPanel;
  private int ccPointsLeftValue=7;
  private int perkListSelection=0;
  
  private GameLabel[] ccAttributes;
  private GameLabel[] ccSkills;
  
  private JSlider sfxSlider;
  private JSlider musicSlider;
  
  private boolean autoSaveOnExit=true;
  
  /**
   * Since OpenJDK 1.6 has two AWT-EventQueue these needs to be
   * locked when drawing the map.
   */
  private Semaphore drawingMapLock;
 
  
  public Game(boolean nosound, boolean nomusic, boolean debug, 
      boolean noExtraSize,boolean autoSaveOnExit) {      
    // This should fix look and feel issue on Mac OS X
    try {
      UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
    } catch (Exception e) {
              e.printStackTrace();
    }
    this.autoSaveOnExit = autoSaveOnExit;
    UIManager.put("ScrollBarUI", GameScrollBarUI.class.getName());
    UIManager.put("SliderUI", GameSliderUI.class.getName());
    setTitle(GAME_TITLE+" "+GAME_VERSION);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);         
    addWindowListener(new TWindowListener());
    // Seems that Mac OS and Windows has title bar counted into JFRAME size
    // So adding 20 pixels more
    String os = System.getProperty("os.name");
    String jreVer = System.getProperty("java.version");
    int extraSize=0;
    if (os.startsWith("Windows") || os.startsWith("Mac")) {
      extraSize=20;
    }
    if (jreVer.startsWith("1.6")) {
      System.out.println("You are using Java version "+jreVer+" which is obsolete...");
      System.out.println("I recommend to update 1.7 or newer...");
    }
    if (jreVer.startsWith("1.7")) {
      extraSize=20;
    }
    debugMode = debug; 
    DebugOutput.setDebugLog(debugMode);

    drawingMapLock = new Semaphore(1, true);
    if (noExtraSize) {
      extraSize = 0;
    }
    setSize(950, 700+extraSize);
    setLocationRelativeTo(null);
    setResizable(false);
    genericTimer = new Timer(75,this);
    genericTimer.setActionCommand(ActionCommands.GENERIC_TIMER);
    turnTimer = new Timer(100,this);
    turnTimer.setActionCommand(ActionCommands.GAME_TURN_TIMER);
    changeState(GAME_STATE_MAINMENU);
    musicTimer = new Timer(1000,this);
    musicTimer.setActionCommand(ActionCommands.GENERIC_MUSIC_TIMER);
    Map.setItemsTileset(GuiStatics.getItemsTileset());
    Map.setCharactersTileset(GuiStatics.getCharacterTileset());
    Map.setEffectsTileset(GuiStatics.getEffectsTileset());
    playingEvent = null;

    TAdapter adapter = new TAdapter();
    adapter.setActionListener(this);
    addKeyListener(adapter);
    GameOptions.readAndSetOptions();
    this.setVisible(true);    
    MusicPlayer.setMusicEnabled(!nomusic);
    SoundPlayer.setSoundEnabled(!nosound);
    musicTimer.start();
    // Final tuning for screen size just for safe
    if (this.getInsets().top > 1 && extraSize == 0) {
      extraSize = 20;
      setSize(920, 700+20);
    }
  }
  

  /**
   * Get Screen shot from whole JFrame
   * @return
   */
  public BufferedImage getScreenShot() {
    BufferedImage result = new BufferedImage(this.getWidth(),this.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    this.paint(result.getGraphics());
    return result;
  }

  public void showOptionsMenu() {
      optionsMenuPanel = new GamePanel(true);
      optionsMenuPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
      GamePanel titlePanel = new GamePanel(true);
      titlePanel.setLayout(new BorderLayout());
      titlePanel.setPreferredSize(new Dimension(this.getWidth(), 50));
      titlePanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
      GameLabel titleLabel = new GameLabel("Options");
      titleLabel.setRasterFontType(RasterFonts.FONT_TYPE_SYSTEM);
      titleLabel.setFont(GuiStatics.FONT_BIG);
      Dimension size = new Dimension();
      size.width = titleLabel.getText().length()*25;
      size.height = 50;
      titleLabel.setMinimumSize(size);
      titleLabel.setPreferredSize(size);
      titleLabel.setMaximumSize(size);
      titlePanel.add(titleLabel,BorderLayout.CENTER);
      optionsMenuPanel.add(titlePanel);

      optionsMenuPanel.add(Box.createRigidArea(new Dimension(30,50)));

      GameLabel sfxVolumeLabel = new GameLabel("Sound volume:");
      sfxVolumeLabel.setRasterFontType(RasterFonts.FONT_TYPE_WHITE);
      optionsMenuPanel.add(sfxVolumeLabel);
      sfxSlider = new JSlider(SwingConstants.HORIZONTAL,0, 100, SoundPlayer.getSoundVolume());
      optionsMenuPanel.add(sfxSlider);
      
      GameLabel musicVolumeLabel = new GameLabel("Music volume:");
      musicVolumeLabel.setRasterFontType(RasterFonts.FONT_TYPE_WHITE);
      optionsMenuPanel.add(musicVolumeLabel);
      musicSlider = new JSlider(SwingConstants.HORIZONTAL,0, 100, OggPlayer.getMusicVolume());
      optionsMenuPanel.add(musicSlider);
      
      optionsMenuPanel.add(Box.createRigidArea(new Dimension(50,50)));

      
      MenuButton saveBtn = new MenuButton("OK",
          ActionCommands.ACTION_OPTIONS_SAVENEXIT);
      saveBtn.addActionListener(this);
      saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
      optionsMenuPanel.add(saveBtn);
      MenuButton exitBtn = new MenuButton("Cancel",
          ActionCommands.ACTION_OPTIONS_EXIT);
      exitBtn.addActionListener(this);
      exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
      optionsMenuPanel.add(exitBtn);
      this.getContentPane().removeAll();
      this.add(optionsMenuPanel);
      this.validate();
  }
  
  /**
   * Creates main menu
   */
  public void showMainMenu() {
    mainMenuPanel = new GamePanel(true);
    //mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel,BoxLayout.Y_AXIS));
    mainMenuPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GamePanel titlePanel = new GamePanel(true);
    titlePanel.setLayout(new BorderLayout());
    titlePanel.setPreferredSize(new Dimension(this.getWidth(), 50));
    titlePanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameLabel titleLabel = new GameLabel(GAME_TITLE);
    titleLabel.setRasterFontType(RasterFonts.FONT_TYPE_SYSTEM);
    titleLabel.setFont(GuiStatics.FONT_BIG);
    Dimension size = new Dimension();
    size.width = titleLabel.getText().length()*25;
    size.height = 50;
    titleLabel.setMinimumSize(size);
    titleLabel.setPreferredSize(size);
    titleLabel.setMaximumSize(size);
    titlePanel.add(titleLabel,BorderLayout.CENTER);

    mainMenuPanel.add(titlePanel);

    
    MenuButton newGameBtn = new MenuButton("New Game",
                     ActionCommands.ACTION_MAINMENU_NEWGAME);
    newGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    newGameBtn.addActionListener(this);
    mainMenuPanel.add(newGameBtn);
    MenuButton continueGameBtn = new MenuButton("Journey Onward",
        ActionCommands.ACTION_MAINMENU_CONTINUE);
    continueGameBtn.addActionListener(this);
    continueGameBtn.setEnabled(GameMaps.isCurrentFolderThere());
    continueGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainMenuPanel.add(continueGameBtn);
    MenuButton loadGameBtn = new MenuButton("Load Game",
        ActionCommands.ACTION_MAINMENU_LOADGAME);
    loadGameBtn.addActionListener(this);
    loadGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainMenuPanel.add(loadGameBtn);
    MenuButton saveGameBtn = new MenuButton("Save Game",
        ActionCommands.ACTION_MAINMENU_SAVEGAME);
    saveGameBtn.addActionListener(this);
    saveGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    if (party != null) {
      saveGameBtn.setEnabled(true);
    } else {
      saveGameBtn.setEnabled(false);
    }
    mainMenuPanel.add(Box.createRigidArea(new Dimension(60,50)));
    mainMenuPanel.add(saveGameBtn);
    

    MenuButton optionsBtn = new MenuButton("Options",
        ActionCommands.ACTION_MAINMENU_OPTIONS);
    optionsBtn.addActionListener(this);
    optionsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainMenuPanel.add(optionsBtn);
    MenuButton helpBtn = new MenuButton("Help",
        ActionCommands.ACTION_MAINMENU_HELP);
    helpBtn.addActionListener(this);
    helpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainMenuPanel.add(helpBtn);
    MenuButton creditsBtn = new MenuButton("Credits",
        ActionCommands.ACTION_MAINMENU_CREDITS);
    creditsBtn.addActionListener(this);
    creditsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainMenuPanel.add(creditsBtn);
    MenuButton exitBtn = new MenuButton("Exit",
        ActionCommands.ACTION_MAINMENU_EXIT);
    exitBtn.addActionListener(this);
    exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainMenuPanel.add(exitBtn);
    
    ImageLabel imLabel = new ImageLabel(GuiStatics.IMAGE_MAINMENU, true);
    mainMenuPanel.add(Box.createRigidArea(new Dimension(80,50)));
    mainMenuPanel.add(imLabel);
    mainMenuPanel.add(Box.createRigidArea(new Dimension(50,50)));

    if (debugMode) {
      GameLabel label = new GameLabel("DebugMode enabled!!!");
      label.setAlignmentX(Component.CENTER_ALIGNMENT);
      mainMenuPanel.add(label);
    }
    this.getContentPane().removeAll();
    this.add(mainMenuPanel);
    this.validate();
  }

  /**
   * Creates Hero down menu
   */
  public void showHeroDownMenu() {
    mainMenuPanel = new GamePanel(true);
    mainMenuPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GamePanel titlePanel = new GamePanel(true);
    titlePanel.setLayout(new BorderLayout());
    titlePanel.setPreferredSize(new Dimension(this.getWidth(), 50));
    titlePanel.setGradientColor(GuiStatics.GRADIENT_COLOR_RED);
    GameLabel titleLabel = new GameLabel("You were killed!");
    titleLabel.setFont(GuiStatics.FONT_BIG);
    Dimension size = new Dimension();
    size.width = titleLabel.getText().length()*25;
    size.height = 50;
    titleLabel.setMinimumSize(size);
    titleLabel.setPreferredSize(size);
    titleLabel.setMaximumSize(size);
    titlePanel.add(titleLabel,BorderLayout.CENTER);
    mainMenuPanel.add(titlePanel);
    ImageLabel imLabel = new ImageLabel(GuiStatics.IMAGE_HERO_DOWN, true);
    mainMenuPanel.add(Box.createRigidArea(new Dimension(50,50)));
    mainMenuPanel.add(imLabel);
    mainMenuPanel.add(Box.createRigidArea(new Dimension(50,50)));
    
    MenuButton newGameBtn = new MenuButton("Main menu",
                     ActionCommands.ACTION_HERODOWN_MAINMENU);
    newGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    newGameBtn.addActionListener(this);
    mainMenuPanel.add(newGameBtn);
    MenuButton continueGameBtn = new MenuButton("Continue from last save",
        ActionCommands.ACTION_HERODOWN_CONTINUE);
    continueGameBtn.addActionListener(this);
    continueGameBtn.setEnabled(GameMaps.isCurrentFolderThere());
    continueGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainMenuPanel.add(continueGameBtn);
    MenuButton loadGameBtn = new MenuButton("Load Game",
        ActionCommands.ACTION_HERODOWN_LOADGAME);
    loadGameBtn.addActionListener(this);
    loadGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainMenuPanel.add(loadGameBtn);
    this.getContentPane().removeAll();
    this.add(mainMenuPanel);
    this.validate();
  }

  
  /**
   * Creates credits
   */
  public void showCredits() {
    String creditsText = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
        "\n\n\n\n\n\n\n\n\n\n\n"+GAME_TITLE+" "+GAME_VERSION+"\n\n" +
        "Using: "+
        Map.ENGINE_NAME+" "+Map.ENGINE_VERSION+"\n\n"+
        "Heroes of The Hawks Haven and JHeroes CRPG engine is licensed under GPL2\n\n"+
        "Credits\n" +
        "Programming and Design by\n" +
        "Tuomo Untinen\n\n" +
        "Graphics \n" +
        "All graphics licensed under CC-BY-SA \n" +
        "(http://creativecommons.org/licenses/by-sa/3.0/legalcode)\n\n"+
        "Full and detailed graphical credits are after other credits.\n"+
        "Graphics by (Alphabetical order):\n\n"+
        "ArtisticDude\n" +
        "Charles Sanchez (AKA CharlesGabriel)\n" +
        "Curt\n" +
        "Dalonedrau\n" +
        "Daniel Armstrong (AKA HughSpectrum)\n" +
        "Daniel Eddeland\n" +
        "Guido Bos\n" +
        "Gwes\n" +
        "Frank Ni\n" +
        "Hyptosis\n" +
        "Jaidyn Reiman\n" +
        "Jetrel from OGA\n" +
        "Joe White\n" +
        "Johann C\n" +
        "Johannes Sjolund AKA Wulax\n" +
        "laetissima\n" +
        "Lanea Zimmerman (AKA Sharm)\n" +
        "MadMarcel\n" +
        "makrohn\n" +
        "Manuel Riecke (AKA MrBeast)\n" +
        "Markeus Brumfield\n" +
        "Matthew Krohn\n" +
        "Nemisys\n" +
        "Nila122\n" +
        "pennomi\n" +
        "Radomir Dopieralsi\n" +
        "Ravenmore\n"+
        "RPG Action\n" +
        "Stephen Challener\n" +
        "Svetlana Kushnariova\n" +
        "Tuomo Untinen\n" +
        "William Thompson\n" +
        "Xenodora\n" +
        "Zabin from OGA\n" +
        "Zeldyn\n" +
        "ZeNeRIA29\n\n" +
        
        "Musics\n"+
        "All musics licensed under CC-BY (http://creativecommons.org/licenses/by/3.0/legalcode)\n\n" +
        "Ancient Forest By Joel Day\n"+
        "Cloud Top By Joel Day\n"+
        "Corn Fields By Joel Day\n"+
        "Meadow Of The Past By Joel Day\n"+
        "Village By Joel Day\n\n"+
        "Theme Of Njals Saga by Andreas Viklund and Jojo\n\n" +
        "Kingdom Theme by Alexandr Zhelanov\n" +
        "Heroes Theme by Alexandr Zhelanov\n"+
        "SDP - Theme Medieval by Alexandr Zhelanov\n"+
        "Path To Lake Land by Alexandr Zhelanov\n" +
        "Prelude(Story) by Alexandr Zhelanov\n\n"+
        "Our Story Begins by Kevin MacLeod\n" +
        "Failing Defense by Kevin MacLeod\n" +
        "Thatched Villagers by Kevin MacLeod\n" +
        "We Got Trouble by Kevin MacLeod\n" +
        "Sneaky Snitch by Kevin MacLeod\n" +
        "Ghostapocalypse Master by Kevin MacLeod\n" +
        "Industrial Revolution by Kevin MacLeod\n" +
        "Hero Down by Kevin MacLeod\n" +
        "Colossus by Kevin MacLeod\n" +
        "Five Armies by Kevin MacLeod\n" +
        "Achaid Cheide by Kevin MacLeod\n" +
        "Celtic Impulse by Kevin MacLeod\n" +
        "Folk Round by Kevin MacLeod\n" +
        "The Pyre by Kevin MacLeod\n\n" +
        "Battle Theme A by cynicmusic.com pixelsphere.org\n\n"+
        "Sound Effects by\n" +
        "Sound package from Heroes of Hawks Haven By Tuomo Untinen\n"
        +"http://opengameart.org/content/rpg-sound-package\n"
        +"Contains also public domain sound effects from OpenGameArt.org\n\n" +
        "Uses JOrbis\n"+
        "a pure Java(TM) Ogg Vorbis decoder\n"+
        "by ymnk, JCraft,Inc.\n" +
        "Licensed under GNU General Public License 2.0.\n\n";
    
    InputStream is = Game.class.getResourceAsStream("/res/licenses/GPL2.txt");
    BufferedInputStream bis = new BufferedInputStream(is);
    DataInputStream dis = new DataInputStream(bis);
    String gpl2License="";
    try {
      gpl2License = StreamUtilities.readTextFile(dis);
      dis.close();
    } catch (IOException e) {
      System.out.println("Reading GPL2 license failed!");
      closeGame();
    }
    is = Game.class.getResourceAsStream("/res/licenses/graphics-credits.txt");
    bis = new BufferedInputStream(is);
    dis = new DataInputStream(bis);
    String graphCredits="";
    try {
      graphCredits = StreamUtilities.readTextFile(dis);
      dis.close();
    } catch (IOException e) {
      System.out.println("Reading graphics credits failed!");
      closeGame();
    }
    creditsText = creditsText+"\n\n"+graphCredits+"\n\n"+gpl2License;

    creditsPanel = new GamePanel(true);
    creditsPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    creditsPanel.setLayout(new BorderLayout());
    creditsTextArea = new GameTextArea(creditsText);
    creditsTextArea.setEditable(false);
    creditsTextArea.setScrollText(creditsText, 38);
    creditsTextArea.setSmoothScroll(true);
    creditsPanel.add(creditsTextArea,BorderLayout.CENTER);
    GameButton okBtn = new GameButton("Ok",ActionCommands.ACTION_CREDITS_OK);
    okBtn.addActionListener(this);
    creditsPanel.add(okBtn,BorderLayout.SOUTH);
    this.getContentPane().removeAll();
    this.add(creditsPanel);
    this.validate();
  }
  
  
  private void updateCharacterCreationAttributes() {
    ccAttributePointsLeft.setText("Points left:"+String.valueOf(ccPointsLeftValue));
    for (int i=0;i<Character.MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      StringBuilder sb = new StringBuilder(25);
      sb.append(Character.getAttributeName(i));
      sb.append(": ");
      sb.append(newChar.getAttribute(i));
      
     
      ccAttributes[i].setText(sb.toString());
    }
    if (ccPointsLeftValue == 0) {
      nextStep.setEnabled(true);
    } else {
      nextStep.setEnabled(false);
    }
    ccHitpointsLabel.setText("Hit points:"+newChar.getMaxHP());
    ccStaminapointsLabel.setText("Stamina points:"+newChar.getMaxStamina());
    ccWillpowerLabel.setText("Willpower:"+newChar.getEffectiveWillPower());
    ccSkillPointsPerLevelLabel.setText("Skill points per lvl:"+
      String.valueOf(10+newChar.getEffectiveAttribute(Character.ATTRIBUTE_INTELLIGENCE)+
          newChar.getEffectiveAttribute(Character.ATTRIBUTE_WISDOM)));
    repaint();
  }

  private void updateCharacterCreationSkills() {
    ccSkillPointsLeft.setText("Skill points left:"+String.valueOf(ccPointsLeftValue));
    for (int i=0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
      ccSkills[i].setText(Character.getSkillName(i)+":"+newChar.getEffectiveSkill(i));
    }
    if (ccPointsLeftValue == 0) {
      nextStep.setEnabled(true);
    } else {
      nextStep.setEnabled(false);
    }
    ccHitpointsLabel.setText("Hit points:"+newChar.getMaxHP());
    ccStaminapointsLabel.setText("Stamina points:"+newChar.getMaxStamina());
    ccWillpowerLabel.setText("Willpower:"+newChar.getEffectiveWillPower());
    ccSkillPointsPerLevelLabel.setText("Skill points per lvl:"+
        String.valueOf(10+newChar.getEffectiveAttribute(Character.ATTRIBUTE_INTELLIGENCE)+
            newChar.getEffectiveAttribute(Character.ATTRIBUTE_WISDOM)));
    repaint();
  }

  private GamePanel createCharacterCreationPerkPanel() {
    GamePanel perkPanel = new GamePanel(true);
    perkPanel.setLayout(new BorderLayout());
    perkPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    ccPointsLeftValue = 7;
    ccPerksLeft = new GameLabel("Perks left: 2");
    perkPanel.add(ccPerksLeft,BorderLayout.NORTH);
    
    GamePanel perkAddPanel = new GamePanel(true);
    perkAddPanel.setLayout(new BorderLayout());
    perkAddPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    GameLabel label = new GameLabel("Select perks:");
    perkAddPanel.add(label,BorderLayout.NORTH);
    GameButton addPerk = new GameButton("Select Perk", ActionCommands.NEWGAME_SELECT_PERK);
    addPerk.addActionListener(this);
    perkAddPanel.add(addPerk,BorderLayout.SOUTH);
    ccListPerk = new PerkList(
      newChar.getPerks().getAvailablePerks(newChar.getAttributes(), 
      newChar.getSkills(), newChar.getLevel()), 
      newChar.getPerks().getNotAvailablePerks(newChar.getAttributes(), 
      newChar.getSkills(), newChar.getLevel()));
    ccListPerk.setActionCommand(ActionCommands.NEWGAME_SELECT_PERK);
    ccListPerk.addActionListener(this);
    JScrollPane scroll = new JScrollPane(ccListPerk);    
/*    scroll.setBackground(GuiStatics.COLOR_BROWNISH);
    scroll.setForeground(GuiStatics.COLOR_CYAN);*/    
    perkAddPanel.add(scroll,BorderLayout.CENTER);

    GamePanel perkSelectedPanel = new GamePanel(true);
    perkSelectedPanel.setLayout(new BorderLayout());
    perkSelectedPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    label = new GameLabel("Active perks:");
    perkSelectedPanel.add(label,BorderLayout.NORTH);
    ccActiveListPerk = new PerkList(newChar.getPerks().getActivePerks());
    perkSelectedPanel.add(ccActiveListPerk,BorderLayout.CENTER);
    
    ccHelpTextPerks = new GameTextArea();
    ccHelpTextPerks.setEditable(false);
    
    perkPanel.add(perkAddPanel,BorderLayout.CENTER);
    perkPanel.add(perkSelectedPanel,BorderLayout.EAST);
    perkPanel.add(ccHelpTextPerks,BorderLayout.SOUTH);
    return perkPanel;
  }
  
  private GamePanel createCharacterCreationSkillPanel() {
    GamePanel skillPanel = new GamePanel(true);
    skillPanel.setLayout(new GridLayout(0, 1));
    skillPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    ccPointsLeftValue = 7;
    ccSkillPointsLeft = new GameLabel("Points left: 7");
    ccSkills = new GameLabel[Character.MAX_NUMBERS_OF_SKILL];
    skillPanel.add(ccSkillPointsLeft);

    for (int i = 0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
      ccSkills[i] = new GameLabel("012345678901234567890123");
      PanelWithArrows arrowPanel = new PanelWithArrows(false,
          ccSkills[i], ActionCommands.NEWGAME_MINUS_SKILLS[i],
          ActionCommands.NEWGAME_PLUS_SKILLS[i],ActionCommands.SKILL_HELPS[i], this);
      skillPanel.add(arrowPanel);
    }
    ccHelpTextSkills = new GameTextArea();
    ccHelpTextSkills.setEditable(false);
    ccHelpTextSkills.setLineWrap(true);
    skillPanel.add(ccHelpTextSkills);
    updateCharacterCreationSkills();

    return skillPanel;
  }
  
  /**
   * Create Attribute panel in Character Creation
   * @return GamePanel
   */
  private GamePanel createCharacterCreationAttributePanel(){
    GamePanel attrPanel = new GamePanel(true);
    attrPanel.setLayout(new GridLayout(0, 1));
    attrPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    ccPointsLeftValue = 7;
    ccAttributePointsLeft = new GameLabel("Points Left: 7");
    ccAttributes = new GameLabel[7];
    attrPanel.add(ccAttributePointsLeft);
    
    for (int i = 0;i<Character.MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      ccAttributes[i] = new GameLabel("012345678901234567890123");
      PanelWithArrows arrowPanel = new PanelWithArrows(false,
          ccAttributes[i], ActionCommands.NEWGAME_MINUS_ATTRIBUTES[i],
          ActionCommands.NEWGAME_PLUS_ATTRIBUTES[i],ActionCommands.ATTRIBUTE_HELPS[i], this);
      attrPanel.add(arrowPanel);
    }
    ccHelpTextAttr = new GameTextArea();
    ccHelpTextAttr.setEditable(false);
    ccHelpTextAttr.setLineWrap(true);
    ccHelpTextAttr.setCharacterWidth(11);
    attrPanel.add(ccHelpTextAttr);
    updateCharacterCreationAttributes();
    return attrPanel;
  }
  
  /**
   * New character creation
   */
  public void showCharacterCreation(){
    ccPanel = new TitledPanel("Character Creation");    
    ccPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    ccPanel.setLayout(new BorderLayout());
    
    GamePanel namePanel = new GamePanel(false);
    namePanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
    GameLabel label = new GameLabel("Character name:");
    namePanel.add(label);
    ccNameField = new GameTextField(GameTexts.HERO_KNIGHT_FIRST1);
    ccNameField.setTextFieldWidth(10);
    namePanel.add(ccNameField);
    label = new GameLabel("Character last name:");
    namePanel.add(label);
    ccFullNameField = new GameTextField(GameTexts.HERO_KNIGHT_LAST);
    ccFullNameField.setTextFieldWidth(10);
    namePanel.add(ccFullNameField);
    ccPanel.add(namePanel,BorderLayout.NORTH);
    
    GamePanel facePanel = new GamePanel(false);
    facePanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    facePanel.setLayout(new BoxLayout(facePanel, BoxLayout.X_AXIS));
    facePanel.add(Box.createRigidArea(new Dimension(25, 25)));
    label = new GameLabel("Face:    ");
    facePanel.add(label);
    ImageGameButton igButton = new ImageGameButton(GuiStatics.IMAGE_BIG_ARROW_LEFT,
        GuiStatics.IMAGE_BIG_ARROW_LEFT_PRESSED,false,ActionCommands.NEWGAME_PREV_CHAR);
    igButton.addActionListener(this);
    facePanel.add(igButton);
    
    facePanel.add(Box.createRigidArea(new Dimension(5, 25)));
    
    faceImageLabel = new ImageLabel(Faces.getFace(faceNumber), true);
    facePanel.add(faceImageLabel);
    facePanel.add(Box.createRigidArea(new Dimension(5, 25)));
    
    igButton = new ImageGameButton(GuiStatics.IMAGE_BIG_ARROW_RIGHT,
        GuiStatics.IMAGE_BIG_ARROW_RIGHT_PRESSED,false,ActionCommands.NEWGAME_NEXT_CHAR);
    igButton.addActionListener(this);
    facePanel.add(igButton);
    facePanel.add(Box.createRigidArea(new Dimension(5, 25)));    
    newChar = new Character(0);    
    newCharLabel = new CharacterLabel(newChar.getBodyTile(),
        newChar.getHeadTile(), true);
    facePanel.add(newCharLabel);
    
    GamePanel backPanel = new GamePanel(false);
    backPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    backPanel.setLayout(new BorderLayout());
    label = new GameLabel("Background story:    ");
    backPanel.add(label,BorderLayout.NORTH);
    ccBackText = new GameTextArea();
    ccBackText.setRows(27);
    ccBackText.setText(GameTexts.HERO_KNIGHT_BACKGROUND);
    ccBackText.setEditable(true);
    ccBackText.setLineWrap(true);
    ccBackText.setWrapStyleWord(true);
    backPanel.add(ccBackText,BorderLayout.CENTER);
    
    GamePanel statPanel = new GamePanel(false);
    statPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    statPanel.setLayout(new GridLayout(4, 1));
    ccHitpointsLabel = new GameLabel("Hit points: 10");
    statPanel.add(ccHitpointsLabel);
    ccStaminapointsLabel = new GameLabel("Stamina points: 10");
    statPanel.add(ccStaminapointsLabel);
    ccWillpowerLabel = new GameLabel("Willpower: 10");
    statPanel.add(ccWillpowerLabel);
    ccSkillPointsPerLevelLabel = new GameLabel("Skill points per lvl: 20");
    statPanel.add(ccSkillPointsPerLevelLabel);
    
    GamePanel faceNStat = new GamePanel(false);
    faceNStat.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    faceNStat.setLayout(new BorderLayout());
    faceNStat.add(facePanel,BorderLayout.NORTH);
    faceNStat.add(statPanel,BorderLayout.SOUTH);
    
    GamePanel faceNBack = new GamePanel(true);
    faceNBack.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    faceNBack.setLayout(new BorderLayout());
    faceNBack.add(faceNStat,BorderLayout.NORTH);
    faceNBack.add(backPanel,BorderLayout.SOUTH);
    
    ccPanel.add(faceNBack,BorderLayout.WEST);
    
    GamePanel stepPanel = new GamePanel(true);
    stepPanel.setLayout(new BorderLayout());
    stepPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    prevStep = new GameButton("Back", ActionCommands.GENERIC_BACK);
    prevStep.addActionListener(this);
    stepPanel.add(prevStep,BorderLayout.WEST);
    nextStep = new GameButton("Next", ActionCommands.GENERIC_NEXT);
    nextStep.setEnabled(false);
    nextStep.addActionListener(this);
    stepPanel.add(nextStep,BorderLayout.EAST);


    ccAttributePanel = createCharacterCreationAttributePanel();
    ccSkillPanel = createCharacterCreationSkillPanel();
    ccSkillPanel.setVisible(false);
    ccPerkPanel = createCharacterCreationPerkPanel();
    ccPerkPanel.setVisible(false);
    ccPanel.add(ccAttributePanel,BorderLayout.CENTER);
    ccPanel.add(stepPanel,BorderLayout.SOUTH);
    
    this.getContentPane().removeAll();
    this.add(ccPanel);
    this.validate();
  }
  
  public void showGamePanels() {
    gamePanels = new GamePanel(false);
    gamePanels.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE); 
    gamePanels.setLayout(new BoxLayout(gamePanels, BoxLayout.LINE_AXIS));
    mapPanel = new MapPanel();
    gamePanels.add(mapPanel);
    mapPanel.drawMap(map, party.getActiveChar().getX(),
        party.getActiveChar().getY(), party.isDay(),true);

    partyPanel = new GamePanel(true);
    partyPanel.setLayout(new BoxLayout(partyPanel, BoxLayout.Y_AXIS));
    partyPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    memberPanel = new PartyMemberPanel[Party.MAX_PARTY_SIZE];
    for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
      memberPanel[i] =  new PartyMemberPanel(i, party, this);
      partyPanel.add(memberPanel[i]);
    }
    gameLog = new GameLogArea();
    partyPanel.add(gameLog);
      
    gamePanels.add(partyPanel);    
    map.setAttackText("No target");
    StringBuilder sb = new StringBuilder();
    sb.append("Defense: ");
    sb.append(party.getActiveChar().getDefense().getDefense());
    sb.append("\nWillpower: ");
    sb.append(party.getActiveChar().getDefense().getWillPower());
    map.setDefenseText(sb.toString());
    
    this.getContentPane().removeAll();
    this.add(gamePanels);
    this.validate();
  }
  
  public void changeState(int newState) {
    state = newState;
    genericTimer.stop();
    turnTimer.stop();
    switch (state) {
    case GAME_STATE_HERO_DOWN: {
      MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_HERO_DOWN);
      deathWaiter=0;
      genericTimer.start();
      //showHeroDownMenu();
      break;
    }
    case GAME_STATE_DEATH_SCREEN: {
      MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_HERO_DOWN);
      showHeroDownMenu();
      break;
    }
    case GAME_STATE_OPTIONS: {
      showOptionsMenu();
      break;
    }
    case GAME_STATE_LOADGAME: {
      if (gameGamesScreen != null) {
        this.getContentPane().removeAll();
        this.add(gameGamesScreen);
        this.validate();
      } else {
        state = GAME_STATE_MAINMENU;
        MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_CLOUD_TOP);
        showMainMenu();      
      }
      break;
    }
    case GAME_STATE_SAVEGAME: {
      if (gameGamesScreen != null) {
        this.getContentPane().removeAll();
        this.add(gameGamesScreen);
        this.validate();
      } else {
        state = GAME_STATE_MAINMENU;
        MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_CLOUD_TOP);
        showMainMenu();      
      }
      break;
    }
    case GAME_STATE_LOADSCREEN: {
      if (gameLoadScreen != null) {
        this.getContentPane().removeAll();
        this.add(gameLoadScreen);
        this.validate();
        genericTimer.start();        
      } else {
        state = GAME_STATE_GAME;
        showGamePanels();
        turnTimer.start();
        genericTimer.start();        
      }
      break;
    }
    case GAME_STATE_CHARACTER_SHEET: {
      if (charSheetPanel != null) {
        this.getContentPane().removeAll();
        this.add(charSheetPanel);
        this.validate();
        charSheetPanel.requestFocusAtStart();
        genericTimer.start();
      } else {
        state = GAME_STATE_GAME;
        showGamePanels();
        turnTimer.start();
        genericTimer.start();
      }
      break;
    }
    case GAME_STATE_JOURNAL: {
      if (gameJournalPanel != null) {
        this.getContentPane().removeAll();
        this.add(gameJournalPanel);
        this.validate();
        genericTimer.start();
      } else {
        state = GAME_STATE_GAME;
        showGamePanels();
        turnTimer.start();
        genericTimer.start();
      }
      break;
    }
    case GAME_STATE_GAME_HELP: {
      if (gameHelpPanel != null) {
        this.getContentPane().removeAll();
        this.add(gameHelpPanel);
        this.validate();
        gameHelpPanel.requestFocusAtStart();
        genericTimer.start();
      } else {
        state = GAME_STATE_GAME;
        showGamePanels();
        turnTimer.start();
        genericTimer.start();
      }
      break;
    }
    case GAME_STATE_DEBUGMODE: {
      if (gameDebugPanel != null) {
        this.getContentPane().removeAll();
        this.add(gameDebugPanel);
        this.validate();
        genericTimer.start();
      } else {
        state = GAME_STATE_GAME;
        showGamePanels();
        turnTimer.start();
        genericTimer.start();
      }
      break;
    }
    case GAME_STATE_BARTERING: {
      if (gameBarteringPanel != null) {
        this.getContentPane().removeAll();
        this.add(gameBarteringPanel);
        this.validate();
        genericTimer.start();
      } else {
        state = GAME_STATE_GAME;
        showGamePanels();
        turnTimer.start();
        genericTimer.start();
      }
      break;
    }
    case GAME_STATE_TALK: {
      if (gameTalkPanel != null) {
        this.getContentPane().removeAll();
        this.add(gameTalkPanel);
        this.validate();
        genericTimer.start();             
      } else {
        state = GAME_STATE_GAME;
        showGamePanels();
        turnTimer.start();
        genericTimer.start();        
      }
      break;
    }
    case GAME_STATE_GAME: {
      if (gamePanels == null) {
        party.addLogText("Game started...");
        party.addLogText("Press F1 to see help...");
      }
      showGamePanels();
      spellPanel = null;
      searchPanel = null;
      turnTimer.start();
      genericTimer.start();
      break;
    }
    case GAME_STATE_LOSE: {
      genericTimer.start();
      gameStoryPanel = new GameStoryPanel(true, new LoseStory(null, null), this);
      MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_HERO_DOWN);
      if (gameStoryPanel != null) {
        this.getContentPane().removeAll();
        this.add(gameStoryPanel);
        this.validate();
      } else {
        state = GAME_STATE_MAINMENU;
        MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_CLOUD_TOP);
        showMainMenu();      
      }
      break;
    }
    case GAME_STATE_WIN: {
      genericTimer.start();
      gameStoryPanel = new GameStoryPanel(true, new EndStory(party), this);
      MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_PRELUDE_STORY);
      if (gameStoryPanel != null) {
        this.getContentPane().removeAll();
        this.add(gameStoryPanel);
        this.validate();
      } else {
        state = GAME_STATE_MAINMENU;
        MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_CLOUD_TOP);
        showMainMenu();      
      }
      break;
    }
    case GAME_STATE_NEWGAME: {
      genericTimer.start();
      MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_OURSTORYBEGINS);
      showCharacterCreation();
      break;
    }
    case GAME_STATE_INTRO: {
      genericTimer.start();
      MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_OURSTORYBEGINS);
      if (gameStoryPanel != null) {
        this.getContentPane().removeAll();
        this.add(gameStoryPanel);
        this.validate();
      } else {
        state = GAME_STATE_MAINMENU;
        MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_CLOUD_TOP);
        showMainMenu();      
      }
      break;
    }
    case GAME_STATE_NEWGAME_SKILLS: {
      genericTimer.start();
      ccPanel.add(ccSkillPanel,BorderLayout.CENTER);
      ccAttributePanel.setVisible(false);
      ccSkillPanel.setVisible(true);
      ccPointsLeftValue = 10+newChar.getAttribute(Character.ATTRIBUTE_INTELLIGENCE)+
        newChar.getAttribute(Character.ATTRIBUTE_WISDOM);
      for (int i=0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
        newChar.setSkill(i, 0);
      }
      updateCharacterCreationSkills();
      break;
    }
    case GAME_STATE_NEWGAME_PERKS: {
      genericTimer.start();
      ccPanel.add(ccPerkPanel,BorderLayout.CENTER);
      ccSkillPanel.setVisible(false);
      ccPerkPanel.setVisible(true);
      ccPointsLeftValue = 2;
      for (int i=0;i<Perks.ALL_PERK_NAMES.length;i++) {
        newChar.getPerks().setPerk(i, false);
      }
      ccListPerk.updatePerkList(
          newChar.getPerks().getAvailablePerks(newChar.getAttributes(), 
              newChar.getSkills(), newChar.getLevel()), 
              newChar.getPerks().getNotAvailablePerks(newChar.getAttributes(), 
              newChar.getSkills(), newChar.getLevel()));
      ccActiveListPerk.setListData(newChar.getPerks().getActivePerks());
      ccPerksLeft.setText("Perks left:"+String.valueOf(ccPointsLeftValue));
      nextStep.setText("Start");
      nextStep.setEnabled(false);
      repaint();
      //updateCharacterCreationSkills();
      break;
    }
    case GAME_STATE_MAINMENU: {
      MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_CLOUD_TOP);
      showMainMenu();
      break;
    }
    case GAME_STATE_CREDITS: {
      showCredits();
      genericTimer.start();
      break;
    }
    }
    repaint();
  }
    
  /**
   * @param args
   */
  public static void main(String[] args) {
      boolean nosound = false;
      boolean nomusic = false;
      boolean debug = false;
      boolean showHelp = false;
      boolean noExtraSize = false;
      boolean autoSaveOnExit = true;
      for (int i=0;i<args.length;i++) {
          if (args[i].equalsIgnoreCase("--nosound")) {
              nosound = true;
          } else if (args[i].equalsIgnoreCase("--nomusic")) {
              nomusic = true;
          } else if (args[i].equalsIgnoreCase("--debugmode")) {
              debug = true;
          } else if (args[i].equalsIgnoreCase("--help")) {
            showHelp = true;
          } else if (args[i].equalsIgnoreCase("--noexitsave")) {
            autoSaveOnExit = false;
          } else if (args[i].equalsIgnoreCase("--noextrasize")) {
            noExtraSize = true;
          } else {
              System.out.println("Unknown argument: "+args[i]);
              System.out.println("Showing help...");
              showHelp = true;
          }
              
      }
      if (!showHelp) {
        new Game(nosound,nomusic,debug,noExtraSize,autoSaveOnExit);
      } else {
        System.out.println("Argument help for Heroes of Hawks Haven");
        System.out.println("---------------------------------------");
        System.out.println("--nosound     : Disables sound from the game.");
        System.out.println("--nomusic     : Disables music from the game.");
        System.out.println("--noextrasize : Does not add extra 20 pixel for screen height.");
        System.out.println("--noexitsave  : Disables auto save on game exit.");
        System.out.println("--help        : Displays this help");
      }
   }

  
  /**
   * Actions in credits panel
   * @param arg0 ActionEvent
   */
  private void actionPerformedCredits(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase(
                       ActionCommands.ACTION_CREDITS_OK)) {
      changeState(GAME_STATE_MAINMENU);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.GENERIC_TIMER)) {
      if (creditsTextArea.getSmoothScrollNextRow()) {
        creditsTextArea.getNextLine();
      }
      repaint();

    }
  }

  private void actionPerformedMainMenu(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase(
                       ActionCommands.ACTION_MAINMENU_CREDITS)) {
      changeState(GAME_STATE_CREDITS);
      // Quick view the hero down menu
      //changeState(GAME_STATE_HERO_DOWN);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
                      ActionCommands.ACTION_MAINMENU_EXIT)) {
      closeGame();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.ACTION_MAINMENU_OPTIONS)) {
     changeState(GAME_STATE_OPTIONS);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.ACTION_MAINMENU_NEWGAME)) {
      gameStoryPanel = new GameStoryPanel(true, new StartStory(null),this);
      changeState(GAME_STATE_INTRO);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ACTION_MAINMENU_CONTINUE)) {
      if (party != null) {
        changeState(GAME_STATE_GAME);
      } else {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(null, null,null,null, 
            GameSaveAndLoadTask.TASK_LOAD_CURRENT_GAME, "current");
        loadThread = new Thread(saveNLoad);        
        gameLoadScreen = new GameLoadScreen("Journey onward",
            GameSaveAndLoadTask.TASK_LOAD_CURRENT_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.ACTION_MAINMENU_HELP)) {
      gameHelpPanel = new GameHelp(this);
      changeState(GAME_STATE_GAME_HELP);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ACTION_MAINMENU_LOADGAME)) {
      gameGamesScreen = new GameGamesScreen(true, this);
      changeState(GAME_STATE_LOADGAME);
    }    
    if (arg0.getActionCommand().equals(ActionCommands.ACTION_MAINMENU_SAVEGAME)) {
      gameGamesScreen = new GameGamesScreen(false, this);
      changeState(GAME_STATE_SAVEGAME);
    }
  }
  
  private void actionPerformedDeathScreen(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.ACTION_HERODOWN_MAINMENU)) {
      party = null;
      changeState(GAME_STATE_MAINMENU);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ACTION_HERODOWN_CONTINUE)) {
      GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(null, null,null,null, 
          GameSaveAndLoadTask.TASK_LOAD_CURRENT_GAME, "current");
      loadThread = new Thread(saveNLoad);        
      gameLoadScreen = new GameLoadScreen("Journey onward",
          GameSaveAndLoadTask.TASK_LOAD_CURRENT_GAME);
      loadThread.start();
      changeState(GAME_STATE_LOADSCREEN);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ACTION_HERODOWN_LOADGAME)) {
      gameGamesScreen = new GameGamesScreen(true, this);
      changeState(GAME_STATE_LOADGAME);
    }
  }
  
  private boolean ccPerkSelectionListUpdate() {
    String value = (String) ccListPerk.getSelectedValue();
    int index=-1;
    if (value != null) {
      if (value.startsWith("!")) {
        value = value.substring(1);
      }
      index = Perks.getPerkIndex(value);
      if (index != -1) {
        ccActiveListPerk.clearSelection();
        perkListSelection = 1;
        ccHelpTextPerks.setText(Perks.ALL_PERK_NAMES_DESC[index]+
            "\n\nRequirements:\n"+Perks.ALL_PERK_NAMES_REQU[index]);
        ccHelpTextPerks.setWrapStyleWord(true);
        ccHelpTextPerks.setLineWrap(true);
        return true;
      }
    }   
    return false;
  }
  
  private boolean ccPerkActiveListUpdate() {
    String value = (String) ccActiveListPerk.getSelectedValue();        
    int activeIndex=-1;
    if (value != null) {
      activeIndex = Perks.getPerkIndex(value);
      if (activeIndex != -1) {
        ccListPerk.clearSelection();
        perkListSelection = 2;
        ccHelpTextPerks.setText(Perks.ALL_PERK_NAMES_DESC[activeIndex]+
            "\n\nRequirements:\n"+Perks.ALL_PERK_NAMES_REQU[activeIndex]);
        ccHelpTextPerks.setWrapStyleWord(true);
        ccHelpTextPerks.setLineWrap(true);
        return true;
      }           
    }
    return false;
  }
  
  /**
   * Check if ccNameField and ccFullNameField has default value or not
   */
  private boolean isNewNameModified() {
    String name = ccNameField.getText()+" "+ccFullNameField.getText();
    for (int i=0;i<GameTexts.HERO_FULL_NAMES.length;i++) {
      if (name.equalsIgnoreCase(GameTexts.HERO_FULL_NAMES[i])) {
        return false;
      }
    }
    return true;
  }
  
  private void actionPerformedNewGame(ActionEvent arg0) {
    if (arg0.getActionCommand().equals(ActionCommands.GENERIC_TIMER)) {
      repaint();
      if (state == GAME_STATE_NEWGAME_PERKS) {
        if ((perkListSelection==0) || (perkListSelection==2)) {
          if (!ccPerkSelectionListUpdate()) {
            ccPerkActiveListUpdate();
          }          
        } else {
          if (!ccPerkActiveListUpdate()) {
            ccPerkSelectionListUpdate();
          }
        }
      }
        
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
               ActionCommands.NEWGAME_PREV_CHAR)) {
      if (faceNumber > 0) {
        switch (faceNumber) {
        case 1: {faceNumber = 0; newChar.setTileOffset(24*0); 
        ccBackText.setText(GameTexts.HERO_KNIGHT_BACKGROUND); 
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KNIGHT_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_KNIGHT_LAST);
        }
         break; }
        case 2: {faceNumber = 1; newChar.setTileOffset(24*1);
        ccBackText.setText(GameTexts.HERO_RANGER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_RANGER_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_RANGER_LAST);
        }
        break; }
        case 3: {faceNumber = 2; newChar.setTileOffset(24*2);
        ccBackText.setText(GameTexts.HERO_WIZARD_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_WIZARD_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_WIZARD_LAST);
        }
        break; }
        case 20: {faceNumber = 3; newChar.setTileOffset(24*3);
        ccBackText.setText(GameTexts.HERO_KUNGFU_MAN_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KUNGFU_MAN_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_KUNGFU_MAN_LAST);
        }
        break; }
        case 21: {faceNumber = 20; newChar.setTileOffset(24*4);
        ccBackText.setText(GameTexts.HERO_SPELLSWORD_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_SPELLSWORD_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_SPELLSWORD_LAST);
        }
        break; }
        case 22: {faceNumber = 21; newChar.setTileOffset(24*5);
        ccBackText.setText(GameTexts.HERO_SWASHBUCKLER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_SWASHBUCKLER_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_SWASHBUCKLER_LAST);
        }
        break; }
        case 23: {faceNumber = 22; newChar.setTileOffset(24*6);
        ccBackText.setText(GameTexts.HERO_HEALER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_HEALER_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_HEALER_LAST);
        }
        break; }
        case 40: {faceNumber = 23; newChar.setTileOffset(24*7);
        ccBackText.setText(GameTexts.HERO_KUNGFU_WOMAN_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KUNGFU_WOMAN_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_KUNGFU_WOMAN_LAST);
        }
        break; }
        case 41: {faceNumber = 40; newChar.setTileOffset(24*0);
        ccBackText.setText(GameTexts.HERO_KNIGHT_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KNIGHT_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_KNIGHT_LAST);
        }
        break; }
        case 42: {faceNumber = 41; newChar.setTileOffset(24*1);
        ccBackText.setText(GameTexts.HERO_RANGER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_RANGER_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_RANGER_LAST);
        }
        break; }
        case 43: {faceNumber = 42; newChar.setTileOffset(24*2);
        ccBackText.setText(GameTexts.HERO_WIZARD_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_WIZARD_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_WIZARD_LAST);
        }
        break; }
        case 60: {faceNumber = 43; newChar.setTileOffset(24*3);
        ccBackText.setText(GameTexts.HERO_KUNGFU_MAN_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KUNGFU_MAN_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_KUNGFU_MAN_LAST);
        }
        break; } 
        case 61: {faceNumber = 60; newChar.setTileOffset(24*4);
        ccBackText.setText(GameTexts.HERO_SPELLSWORD_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_SPELLSWORD_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_SPELLSWORD_LAST);
        }
        break; }
        case 62: {faceNumber = 61; newChar.setTileOffset(24*5);
        ccBackText.setText(GameTexts.HERO_SWASHBUCKLER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_SWASHBUCKLER_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_SWASHBUCKLER_LAST);
        }
        break; } 
        case 63: {faceNumber = 62; newChar.setTileOffset(24*6);
        ccBackText.setText(GameTexts.HERO_HEALER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_HEALER_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_HEALER_LAST);
        }
        break; }
        }
        newChar.setFaceNumber(faceNumber);
        newCharLabel.updateImages(newChar.getBodyTile(), newChar.getHeadTile());
        faceImageLabel.setImage(Faces.getFace(faceNumber));
        repaint();
      }
      
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_NEXT_CHAR)) {
      if (faceNumber < 400) {
        switch (faceNumber) {
        case 0: {faceNumber = 1; newChar.setTileOffset(24*1);
        ccBackText.setText(GameTexts.HERO_RANGER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_RANGER_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_RANGER_LAST);
        }
        break;}
        case 1: {faceNumber = 2; newChar.setTileOffset(24*2);
        ccBackText.setText(GameTexts.HERO_WIZARD_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_WIZARD_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_WIZARD_LAST);
        }
        break;}
        case 2: {faceNumber = 3; newChar.setTileOffset(24*3);
        ccBackText.setText(GameTexts.HERO_KUNGFU_MAN_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KUNGFU_MAN_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_KUNGFU_MAN_LAST);
        }
        break;}
        case 3: {faceNumber = 20; newChar.setTileOffset(24*4);
        ccBackText.setText(GameTexts.HERO_SPELLSWORD_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_SPELLSWORD_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_SPELLSWORD_LAST);
        }
        break;}
        case 20: {faceNumber = 21; newChar.setTileOffset(24*5);
        ccBackText.setText(GameTexts.HERO_SWASHBUCKLER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_SWASHBUCKLER_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_SWASHBUCKLER_LAST);
        }
        break;}
        case 21: {faceNumber = 22; newChar.setTileOffset(24*6);
        ccBackText.setText(GameTexts.HERO_HEALER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_HEALER_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_HEALER_LAST);
        }
        break;}
        case 22: {faceNumber = 23; newChar.setTileOffset(24*7);
        ccBackText.setText(GameTexts.HERO_KUNGFU_WOMAN_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KUNGFU_WOMAN_FIRST1);
          ccFullNameField.setText(GameTexts.HERO_KUNGFU_WOMAN_LAST);
        }
        break;}
        case 23: {faceNumber = 40; newChar.setTileOffset(24*0);
        ccBackText.setText(GameTexts.HERO_KNIGHT_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KNIGHT_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_KNIGHT_LAST);
        }
        break;}
        case 40: {faceNumber = 41; newChar.setTileOffset(24*1);
        ccBackText.setText(GameTexts.HERO_RANGER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_RANGER_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_RANGER_LAST);
        }
        break;}
        case 41: {faceNumber = 42; newChar.setTileOffset(24*2);
        ccBackText.setText(GameTexts.HERO_WIZARD_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_WIZARD_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_WIZARD_LAST);
        }
        break;}
        case 42: {faceNumber = 43; newChar.setTileOffset(24*3);
        ccBackText.setText(GameTexts.HERO_KUNGFU_MAN_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KUNGFU_MAN_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_KUNGFU_MAN_LAST);
        }
        break;}
        case 43: {faceNumber = 60; newChar.setTileOffset(24*4);
        ccBackText.setText(GameTexts.HERO_SPELLSWORD_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_SPELLSWORD_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_SPELLSWORD_LAST);
        }
        break;}
        case 60: {faceNumber = 61; newChar.setTileOffset(24*5);
        ccBackText.setText(GameTexts.HERO_SWASHBUCKLER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_SWASHBUCKLER_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_SWASHBUCKLER_LAST);
        }
        break;}
        case 61: {faceNumber = 62; newChar.setTileOffset(24*6);
        ccBackText.setText(GameTexts.HERO_HEALER_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_HEALER_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_HEALER_LAST);
        }
        break;}
        case 62: {faceNumber = 63; newChar.setTileOffset(24*7);
        ccBackText.setText(GameTexts.HERO_KUNGFU_WOMAN_BACKGROUND);
        if (!isNewNameModified()) {
          ccNameField.setText(GameTexts.HERO_KUNGFU_WOMAN_FIRST2);
          ccFullNameField.setText(GameTexts.HERO_KUNGFU_WOMAN_LAST);
        }
        break;}
        }
        newChar.setFaceNumber(faceNumber);
        newCharLabel.updateImages(newChar.getBodyTile(), newChar.getHeadTile());
        faceImageLabel.setImage(Faces.getFace(faceNumber));
        repaint();
      }

    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.GENERIC_BACK)) {
      if (state == GAME_STATE_NEWGAME) {
        changeState(GAME_STATE_MAINMENU);
      }
      if (state == GAME_STATE_NEWGAME_SKILLS) {
        state = GAME_STATE_NEWGAME;
        ccPanel.add(ccAttributePanel,BorderLayout.CENTER);
        ccAttributePanel.setVisible(true);
        ccSkillPanel.setVisible(false);
        ccPointsLeftValue = 0;
        updateCharacterCreationAttributes();
      }
      if (state == GAME_STATE_NEWGAME_PERKS) {
        state = GAME_STATE_NEWGAME_SKILLS;
        ccPanel.add(ccSkillPanel,BorderLayout.CENTER);
        ccPerkPanel.setVisible(false);
        ccSkillPanel.setVisible(true);
        ccPointsLeftValue = 0;
        nextStep.setText("Next");
        updateCharacterCreationSkills();
        for (int i=0;i<Perks.ALL_PERK_NAMES.length;i++) {
          if (newChar.getPerks().getPerk(i)) {
            String value = Perks.ALL_PERK_NAMES[i];
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_STRENGTH)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_STRENGTH2))) {
              newChar.setAttribute(Character.ATTRIBUTE_STRENGTH,
                  newChar.getAttribute(Character.ATTRIBUTE_STRENGTH)-1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_AGILITY)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_AGILITY2))) {
              newChar.setAttribute(Character.ATTRIBUTE_AGILITY,
                  newChar.getAttribute(Character.ATTRIBUTE_AGILITY)-1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_ENDURANCE)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_ENDURANCE2))) {
              newChar.setAttribute(Character.ATTRIBUTE_ENDURANCE,
                  newChar.getAttribute(Character.ATTRIBUTE_ENDURANCE)-1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_INTELLIGENCE)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_INTELLIGENCE2))) {
              newChar.setAttribute(Character.ATTRIBUTE_INTELLIGENCE,
                  newChar.getAttribute(Character.ATTRIBUTE_INTELLIGENCE)-1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_WISDOM)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_WISDOM2))) {
              newChar.setAttribute(Character.ATTRIBUTE_WISDOM,
                  newChar.getAttribute(Character.ATTRIBUTE_WISDOM)-1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_CHARISMA)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_CHARISMA2))) {
              newChar.setAttribute(Character.ATTRIBUTE_CHARISMA,
                  newChar.getAttribute(Character.ATTRIBUTE_CHARISMA)-1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_LUCK)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_LUCK2))) {
              newChar.setAttribute(Character.ATTRIBUTE_LUCK,
                  newChar.getAttribute(Character.ATTRIBUTE_LUCK)-1);
            }
            newChar.getPerks().setPerk(i, false);
          }
        }
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.NEWGAME_SELECT_PERK)) {
      if (ccPointsLeftValue > 0) {
        String value = (String) ccListPerk.getSelectedValue();
        if (value != null) {
          if (value.startsWith("!")) {
            
          } else {
            newChar.getPerks().setPerk(Perks.getPerkIndex(value), true);
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_STRENGTH)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_STRENGTH2))) {
              newChar.setAttribute(Character.ATTRIBUTE_STRENGTH,
                  newChar.getAttribute(Character.ATTRIBUTE_STRENGTH)+1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_AGILITY)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_AGILITY2))) {
              newChar.setAttribute(Character.ATTRIBUTE_AGILITY,
                  newChar.getAttribute(Character.ATTRIBUTE_AGILITY)+1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_ENDURANCE)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_ENDURANCE2))) {
              newChar.setAttribute(Character.ATTRIBUTE_ENDURANCE,
                  newChar.getAttribute(Character.ATTRIBUTE_ENDURANCE)+1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_INTELLIGENCE)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_INTELLIGENCE2))) {
              newChar.setAttribute(Character.ATTRIBUTE_INTELLIGENCE,
                  newChar.getAttribute(Character.ATTRIBUTE_INTELLIGENCE)+1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_WISDOM)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_WISDOM2))) {
              newChar.setAttribute(Character.ATTRIBUTE_WISDOM,
                  newChar.getAttribute(Character.ATTRIBUTE_WISDOM)+1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_CHARISMA)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_CHARISMA2))) {
              newChar.setAttribute(Character.ATTRIBUTE_CHARISMA,
                  newChar.getAttribute(Character.ATTRIBUTE_CHARISMA)+1);
            }
            if ((value.equalsIgnoreCase(Perks.PERK_TRAINED_LUCK)) || 
                (value.equalsIgnoreCase(Perks.PERK_TRAINED_LUCK2))) {
              newChar.setAttribute(Character.ATTRIBUTE_LUCK,
                  newChar.getAttribute(Character.ATTRIBUTE_LUCK)+1);
            }
            ccPointsLeftValue--;
            if (ccPointsLeftValue == 0) {
              nextStep.setEnabled(true);
            }
            ccListPerk.updatePerkList(
                newChar.getPerks().getAvailablePerks(newChar.getAttributes(), 
                newChar.getSkills(), newChar.getLevel()), 
                newChar.getPerks().getNotAvailablePerks(newChar.getAttributes(), 
                newChar.getSkills(), newChar.getLevel()));
            ccActiveListPerk.setListData(newChar.getPerks().getActivePerks());
            ccPerksLeft.setText("Perks left: "+String.valueOf(ccPointsLeftValue));
            ccHitpointsLabel.setText("Hit points:"+newChar.getMaxHP());
            ccStaminapointsLabel.setText("Stamina points:"+newChar.getMaxStamina());
            ccWillpowerLabel.setText("Willpower:"+newChar.getEffectiveWillPower());
            ccSkillPointsPerLevelLabel.setText("Skill points per lvl:"+
                String.valueOf(10+newChar.getEffectiveAttribute(Character.ATTRIBUTE_INTELLIGENCE)+
                    newChar.getEffectiveAttribute(Character.ATTRIBUTE_WISDOM)));
            repaint();
          }
        }
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ATTRIBUTE_HELP_STRENGTH)) {
      ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_STRENGTH);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ATTRIBUTE_HELP_ENDURANCE)) {
      ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_ENDURANCE);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ATTRIBUTE_HELP_AGILITY)) {
      ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_AGILITY);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ATTRIBUTE_HELP_INTELLIGENCE)) {
      ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_INTELLIGENCE);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ATTRIBUTE_HELP_WISDOM)) {
      ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_WISDOM);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ATTRIBUTE_HELP_CHARISMA)) {
      ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_CHARISMA);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ATTRIBUTE_HELP_LUCK)) {
      ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_LUCK);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_STRENGTH)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_STRENGTH);
      if (value>1) {
        newChar.setAttribute(Character.ATTRIBUTE_STRENGTH, value-1);
        ccPointsLeftValue++;
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_STRENGTH);
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_STRENGTH)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_STRENGTH);
      if ((value<10) && (ccPointsLeftValue > 0)) {
        newChar.setAttribute(Character.ATTRIBUTE_STRENGTH, value+1);
        ccPointsLeftValue--;
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_STRENGTH);
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_AGILITY)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_AGILITY);
      if (value>1) {
        newChar.setAttribute(Character.ATTRIBUTE_AGILITY, value-1);
        ccPointsLeftValue++;
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_AGILITY);
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_AGILITY)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_AGILITY);
      if ((value<10) && (ccPointsLeftValue > 0)) {
        newChar.setAttribute(Character.ATTRIBUTE_AGILITY, value+1);
        ccPointsLeftValue--;
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_AGILITY);
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_ENDURANCE)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_ENDURANCE);
      if (value>1) {
        newChar.setAttribute(Character.ATTRIBUTE_ENDURANCE, value-1);
        ccPointsLeftValue++;
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_ENDURANCE);
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_ENDURANCE)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_ENDURANCE);
      if ((value<10) && (ccPointsLeftValue > 0)) {
        newChar.setAttribute(Character.ATTRIBUTE_ENDURANCE, value+1);
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_ENDURANCE);
        ccPointsLeftValue--;
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_INTELLIGENCE)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_INTELLIGENCE);
      if (value>1) {
        newChar.setAttribute(Character.ATTRIBUTE_INTELLIGENCE, value-1);
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_INTELLIGENCE);
        ccPointsLeftValue++;
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_INTELLIGENCE)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_INTELLIGENCE);
      if ((value<10) && (ccPointsLeftValue > 0)) {
        newChar.setAttribute(Character.ATTRIBUTE_INTELLIGENCE, value+1);
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_INTELLIGENCE);
        ccPointsLeftValue--;
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_WISDOM)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_WISDOM);
      if (value>1) {
        newChar.setAttribute(Character.ATTRIBUTE_WISDOM, value-1);
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_WISDOM);
        ccPointsLeftValue++;
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_WISDOM)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_WISDOM);
      if ((value<10) && (ccPointsLeftValue > 0)) {
        newChar.setAttribute(Character.ATTRIBUTE_WISDOM, value+1);
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_WISDOM);
        ccPointsLeftValue--;
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_CHARISMA)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_CHARISMA);
      if (value>1) {
        newChar.setAttribute(Character.ATTRIBUTE_CHARISMA, value-1);
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_CHARISMA);
        ccPointsLeftValue++;
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_CHARISMA)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_CHARISMA);
      if ((value<10) && (ccPointsLeftValue > 0)) {
        newChar.setAttribute(Character.ATTRIBUTE_CHARISMA, value+1);
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_CHARISMA);
        ccPointsLeftValue--;
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_LUCK)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_LUCK);
      if (value>1) {
        newChar.setAttribute(Character.ATTRIBUTE_LUCK, value-1);
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_LUCK);
        ccPointsLeftValue++;
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_LUCK)) {
      int value = newChar.getAttribute(Character.ATTRIBUTE_LUCK);
      if ((value<10) && (ccPointsLeftValue > 0)) {
        newChar.setAttribute(Character.ATTRIBUTE_LUCK, value+1);
        ccHelpTextAttr.setText(GameTexts.HELP_ATTRIBUTE_LUCK);
        ccPointsLeftValue--;
        updateCharacterCreationAttributes();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_UNARMED)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_UNARMED);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_MELEE)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_MELEE);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_RANGED_WEAPONS)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_RANGED_ATTACK);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_DODGING)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_DODGING);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_WIZARDY)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_WIZARDY);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_SORCERY)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_SORCERY);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_QI_MAGIC)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_QI_MAGIC);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_BARTERING)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_BARTERING);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_DIPLOMACY)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_DIPLOMACY);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.SKILL_HELP_LOCKPICKING)) {
      ccHelpTextSkills.setText(GameTexts.HELP_SKILL_LOCK_PICKING);
    }
    
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_UNARMED)) {
      int value = newChar.getSkill(Character.SKILL_UNARMED);
      if (value>0) {
        newChar.setSkill(Character.SKILL_UNARMED, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_UNARMED);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_UNARMED)) {
      int value = newChar.getSkill(Character.SKILL_UNARMED);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_UNARMED, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_UNARMED);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_MELEE)) {
      int value = newChar.getSkill(Character.SKILL_MELEE);
      if (value>0) {
        newChar.setSkill(Character.SKILL_MELEE, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_MELEE);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_MELEE)) {
      int value = newChar.getSkill(Character.SKILL_MELEE);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_MELEE, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_MELEE);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_RANGED_WEAPONS)) {
      int value = newChar.getSkill(Character.SKILL_RANGED_WEAPONS);
      if (value>0) {
        newChar.setSkill(Character.SKILL_RANGED_WEAPONS, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_RANGED_ATTACK);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_RANGED_WEAPONS)) {
      int value = newChar.getSkill(Character.SKILL_RANGED_WEAPONS);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_RANGED_WEAPONS, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_RANGED_ATTACK);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_DODGING)) {
      int value = newChar.getSkill(Character.SKILL_DODGING);
      if (value>0) {
        newChar.setSkill(Character.SKILL_DODGING, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_DODGING);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_DODGING)) {
      int value = newChar.getSkill(Character.SKILL_DODGING);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_DODGING, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_DODGING);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_WIZARDY)) {
      int value = newChar.getSkill(Character.SKILL_WIZARDRY);
      if (value>0) {
        newChar.setSkill(Character.SKILL_WIZARDRY, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_WIZARDY);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_WIZARDY)) {
      int value = newChar.getSkill(Character.SKILL_WIZARDRY);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_WIZARDRY, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_WIZARDY);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_SORCERY)) {
      int value = newChar.getSkill(Character.SKILL_SORCERY);
      if (value>0) {
        newChar.setSkill(Character.SKILL_SORCERY, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_SORCERY);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_SORCERY)) {
      int value = newChar.getSkill(Character.SKILL_SORCERY);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_SORCERY, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_SORCERY);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_QI_MAGIC)) {
      int value = newChar.getSkill(Character.SKILL_QI_MAGIC);
      if (value>0) {
        newChar.setSkill(Character.SKILL_QI_MAGIC, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_QI_MAGIC);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_QI_MAGIC)) {
      int value = newChar.getSkill(Character.SKILL_QI_MAGIC);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_QI_MAGIC, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_QI_MAGIC);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_BARTERING)) {
      int value = newChar.getSkill(Character.SKILL_BARTERING);
      if (value>0) {
        newChar.setSkill(Character.SKILL_BARTERING, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_BARTERING);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_BARTERING)) {
      int value = newChar.getSkill(Character.SKILL_BARTERING);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_BARTERING, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_BARTERING);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_DIPLOMACY)) {
      int value = newChar.getSkill(Character.SKILL_DIPLOMACY);
      if (value>0) {
        newChar.setSkill(Character.SKILL_DIPLOMACY, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_DIPLOMACY);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_DIPLOMACY)) {
      int value = newChar.getSkill(Character.SKILL_DIPLOMACY);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_DIPLOMACY, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_DIPLOMACY);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_MINUS_LOCKPICKING)) {
      int value = newChar.getSkill(Character.SKILL_LOCK_PICKING);
      if (value>0) {
        newChar.setSkill(Character.SKILL_LOCK_PICKING, value-1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_LOCK_PICKING);
        ccPointsLeftValue++;
        updateCharacterCreationSkills();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.NEWGAME_PLUS_LOCKPICKING)) {
      int value = newChar.getSkill(Character.SKILL_LOCK_PICKING);
      if ((value<100) && (ccPointsLeftValue > 0)) {
        newChar.setSkill(Character.SKILL_LOCK_PICKING, value+1);
        ccHelpTextSkills.setText(GameTexts.HELP_SKILL_LOCK_PICKING);
        ccPointsLeftValue--;
        updateCharacterCreationSkills();
      }
    }
    
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.GENERIC_NEXT)) {
      switch (state) { 
      case GAME_STATE_NEWGAME: {changeState(GAME_STATE_NEWGAME_SKILLS); break;}
      case GAME_STATE_NEWGAME_SKILLS: {changeState(GAME_STATE_NEWGAME_PERKS); break;}
      case GAME_STATE_NEWGAME_PERKS: {
          String name = ccNameField.getText()+" "+ccFullNameField.getText();
          if (name.length()>25) {
            name = name.substring(0,24);
          }
          newChar.setLongName(name);
          name = ccNameField.getText();
          if (name.length()>12) {
            name = name.substring(0,11);
          }
          newChar.setName(name);
          newChar.setDescription(ccBackText.getText());
          newChar.setCurrentHP(newChar.getMaxHP());
          newChar.setCurrentSP(newChar.getMaxStamina());
          newChar.setIsPlayer(true);
          // Get starting spells
          if (newChar.getSkill(Character.SKILL_QI_MAGIC)>4) {
            newChar.addSpell(SpellFactory.SPELL_NAME_HAZE);
            if (newChar.getSkill(Character.SKILL_QI_MAGIC)>9) {
              newChar.addSpell(SpellFactory.SPELL_NAME_MAGIC_FISTS);
            }
          }
          if (newChar.getSkill(Character.SKILL_WIZARDRY)>4) {
            newChar.addSpell(SpellFactory.SPELL_NAME_MINOR_HEAL);
            if (newChar.getSkill(Character.SKILL_WIZARDRY)>9) {
              newChar.addSpell(SpellFactory.SPELL_NAME_WIZARD_LIGHT);
            }
          }
          if (newChar.getSkill(Character.SKILL_SORCERY)>4) {
            newChar.addSpell(SpellFactory.SPELL_NAME_MAGIC_DART);
            if (newChar.getSkill(Character.SKILL_SORCERY)>9) {
              newChar.addSpell(SpellFactory.SPELL_NAME_MAGIC_ARROW);
            }
          }          
          party = new Party(newChar);
          party.setCurrentMapName(GameMaps.MAP_FILE_TUTORIAL);
          journal = new Journal();
          InputStream is = GameMaps.class.getResourceAsStream(GameMaps.MAPS_IN_JAR+GameMaps.MAP_FILE_TUTORIAL);
          DataInputStream dis = new DataInputStream(is);
          try {
            map = new Map(dis);
            map.generateRandomItems();
            int start = map.getEventWaypointByName("Start");
            Event event = map.getEventByIndex(start);
            party.getPartyChar(0).setPosition(event.getX1(), event.getY1());
            map.setParty(party);
            map.doNPCsCheckCorrectPositions(party.getHours(), party.getMins(),true);
          } catch (IOException e) {
            System.out.println("Starting game failed:"+e.getMessage());
            e.printStackTrace();
            closeGame();
          }
          GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,null,null, 
              GameSaveAndLoadTask.TASK_CREATE_NEW_GAME, "current");
          loadThread = new Thread(saveNLoad);
          gameLoadScreen = new GameLoadScreen("Starting new game...",GameSaveAndLoadTask.TASK_CREATE_NEW_GAME);
          loadThread.start();
          changeState(GAME_STATE_LOADSCREEN);
          break;
          }
      }
      
    }
  }
  
  /**
   * Say yes for travel panel
   */
  private void actionTravelPanelYes() {    
    Event event = travelPanel.getEvent();
    if (event.getEventCommand() == Event.COMMAND_TYPE_TRAVEL) {
      GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,getScreenShot(),map, 
          GameSaveAndLoadTask.TASK_TRAVEL_MAP, event.getParam(0));
      loadThread = new Thread(saveNLoad);
      gameLoadScreen = new GameLoadScreen("Traveling...",GameSaveAndLoadTask.TASK_TRAVEL_MAP);
      gameLoadScreen.setEvent(event);
      loadThread.start();
      changeState(GAME_STATE_LOADSCREEN);
    }
    if (event.getEventCommand() == Event.COMMAND_TYPE_CONDITIONAL_TRAVEL) {
      map.partyTravel(event);
      SoundPlayer.playSoundBySoundName(event.getEventSound());
      if (playingEvent != null) {
        playingEvent.runScript();
        scriptHandlingAfterRun(playingEvent.getEventIndex());
        playingEvent = null;
      }
    }
    if (event.getEventCommand() == Event.COMMAND_TYPE_CONDITIONAL_MAP_TRAVEL) {
      if (playingEvent != null) {
        playingEvent.runScript();
        scriptHandlingAfterRun(playingEvent.getEventIndex());
        playingEvent = null;
      }
      String[] params = event.getParam(1).split("#");
      String newMap = "";
      if (params.length == 2) {
        newMap = params[1];
      }
      GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,getScreenShot(),map, 
          GameSaveAndLoadTask.TASK_TRAVEL_MAP, newMap);
      loadThread = new Thread(saveNLoad);
      gameLoadScreen = new GameLoadScreen("Traveling...",GameSaveAndLoadTask.TASK_TRAVEL_MAP);
      gameLoadScreen.setEvent(event);
      loadThread.start();
      changeState(GAME_STATE_LOADSCREEN);
    }
    if (event.getEventCommand() == Event.COMMAND_TYPE_YESNO_QUESTION) {
      party.setStoryVariable(event.getParam(1),"Yes:"+event.getEventName());
      int index = map.getEventNotWaypoint(event.getX(), event.getY());
      map.removeEvent(index);
    }
    
    gamePanels.remove(travelPanel);
    gamePanels.add(partyPanel);
    gamePanels.validate();
    travelPanel = null;      
  }
  
  /**
   * Closes travel panel
   */
  private void actionTravelPanelNo() {
    Event event = travelPanel.getEvent();
    if (event.getEventCommand() == Event.COMMAND_TYPE_YESNO_QUESTION) {
      party.setStoryVariable(event.getParam(2),"No:"+event.getEventName());
      int index = map.getEventNotWaypoint(event.getX(), event.getY());
      map.removeEvent(index);
    }
    if (playingEvent != null) {
      // Conditional travel will add playingEvent which needs to be nulled
      playingEvent = null;
    }
    gamePanels.remove(travelPanel);
    gamePanels.add(partyPanel);
    gamePanels.validate();
    travelPanel = null;
  }
  
  private void actionCastingSpellInGame(Spell spell) {
    Character chr = party.getActiveChar();
    
    if (chr.canCastSpells()) {
      if (spell.getMinimumSkill() <= chr.getEffectiveSkill(spell.getSkill())) {
        if (spell.needTarget()) {
          castingSpell = spell;          
          if (!map.isCursorMode()) {
            map.setCursorMode(Map.CURSOR_MODE_CAST);
            map.setCursorX(chr.getX());
            map.setCursorY(chr.getY());
            map.setCastingSpell(castingSpell);
          } else {
            map.setCursorMode(Map.CURSOR_MODE_DISABLE);
          }
        } else {
          if (spell.getTargetType() == Spell.SPELL_TARGET_CASTER) {                
            chr.receiveNonLethalDamage(spell.getCastingCost()+chr.getStaminaCostFromLoad());
            int exp = map.doSpell(chr, chr, spell,true);
            chr.setExperience(chr.getExperience()+exp);
            if (party.isCombat()) {
              playerMakesAMove();
            }
          }
          if (spell.getTargetType() == Spell.SPELL_TARGET_PARTY) {                
            chr.receiveNonLethalDamage(spell.getCastingCost()+chr.getStaminaCostFromLoad());
            for (int i=0;i<party.getPartySize();i++) {
              Character target = party.getPartyChar(i);
              if (target != null) {
                int exp = map.doSpell(chr, target, spell,true);
                chr.setExperience(chr.getExperience()+exp);
                if (party.isCombat()) {
                  playerMakesAMove();
                }
              }
            }
          }
        }
      } else {
        party.addLogText(chr.getName()+" has too low skill to cast.");
      } // End of no skill to cast
    } else { // END of no enough empty hands
      party.addLogText(chr.getName()+" has not free hands to cast a spell.");
    }
  }
  
  /**
   * Cancel spell casting
   */
  private void actionCastSpellCancel() {
    if (spellPanel != null) {
      gamePanels.remove(spellPanel);
      gamePanels.add(partyPanel);
      gamePanels.validate();
      spellPanel = null;     
    }
  }
  
  /** 
   * Cast spell
   */
  private void actionCastSpell() {
    if (spellPanel != null) {
      Spell spell = spellPanel.getSelectedSpell();
      if (spell != null) {
        actionCastingSpellInGame(spell);
        party.setLastCastSpell(party.getActiveCharIndex(), spellPanel.getSelectedIndex());
      }
      gamePanels.remove(spellPanel);
      gamePanels.add(partyPanel);
      gamePanels.validate();
      spellPanel = null;
    }
  }
  
  private void actionPerformedHeroDown(ActionEvent arg0) {
    
    if (ActionCommands.GENERIC_TIMER.equals(arg0.getActionCommand())) {
      deathWaiter++;
      if (deathWaiter > 200) {
        changeState(GAME_STATE_DEATH_SCREEN);
      }

      try {
        drawingMapLock.acquire();
      } catch (InterruptedException e) {
        // Continue running
      }
      map.doAnim();
      map.setDisplayPopupText(null);
      map.setPopupImage(null);
      mapPanel.drawMap(map, lastDrawMapPosX,lastDrawMapPosY, party.isDay(),false);            
      gameLog.updateLog(party.getLogText());
      for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
        memberPanel[i].updatePanel(party.getPartyChar(i));
      }
      this.repaint();
      drawingMapLock.release();
    }
  }
  
  private void actionPerformedGame(ActionEvent arg0) {
    if (ActionCommands.GAME_TURN_TIMER.equals(arg0.getActionCommand())) {
      if (this.isActive()) {
        this.requestFocus();
      }
      if (npcWaiter > 0) {
        npcWaiter--;
      }
      boolean didIdoIteration=false;
      if (map.isNPCMovementInIteration()&&npcWaiter==0&&party.isCombat()) {
        map.doNPCsMove(party.getHours(), party.getMins(), false);
        npcWaiter=MAX_NPC_WAITER;
        didIdoIteration=true;
      }
      
      if (turnReady==TURN_MOVES_DONE && !map.isNPCMovementInIteration()) {
        turnReady=TURN_NO_MOVES;
        if (didIdoIteration) {
          moveToNextTurn();
        }
      }
    }
    
    if (ActionCommands.GENERIC_TIMER.equals(arg0.getActionCommand())) {
      try {
        drawingMapLock.acquire();
      } catch (InterruptedException e) {
        // Continue running
      }
      map.doAnim();
      if (playingEvent != null) {
        if (!playingEvent.isConditionalTalk()) {
          if (playingEvent.moveCamera()){
            mapPanel.forceFullRepaint();
          }
          map.setDisplayPopupText(party.changeAllKeyWord((playingEvent.getText())));
          map.setPopupImage(playingEvent.getFaceImage());
          if (playingEvent.getImage() == null) {
            mapPanel.drawMap(map, playingEvent.getCameraX(),
              playingEvent.getCameraY(), party.isDay(),map.isFullRepaint());
          } else {
            // Drawing image instead of map
            mapPanel.drawImage(map, playingEvent.getCameraX(),
                playingEvent.getCameraY(), playingEvent.getImage(),map.isFullRepaint());
          }
        } else {
          map.setDisplayPopupText(null);
          map.setPopupImage(null);
          if (party.getActiveChar() != null) {
            lastDrawMapPosX = party.getActiveChar().getX();
            lastDrawMapPosY = party.getActiveChar().getY();
            // Check if travel panel is active and has image to show
            if (travelPanel != null && travelPanel.getImage() != null) {
              // Drawing image instead of map
              mapPanel.drawImage(map, party.getActiveChar().getX(),
                  party.getActiveChar().getY(), travelPanel.getImage(), false); 
            } else {
                   mapPanel.drawMap(map, party.getActiveChar().getX(),
                      party.getActiveChar().getY(), party.isDay(),false);
            }
          } else {
            if (travelPanel != null && travelPanel.getImage() != null) {
              // Drawing image instead of map
              mapPanel.drawImage(map, lastDrawMapPosX,
                  lastDrawMapPosY, travelPanel.getImage(),false);
            } else {
              mapPanel.drawMap(map, lastDrawMapPosX,
                  lastDrawMapPosY, party.isDay(),false);            
            }
          }  
        }
      } else {
        map.setDisplayPopupText(null);
        map.setPopupImage(null);
        if (party.getActiveChar() != null) {
          lastDrawMapPosX = party.getActiveChar().getX();
          lastDrawMapPosY = party.getActiveChar().getY();
          // Check if travel panel is active and has image to show
          if (travelPanel != null && travelPanel.getImage() != null) {
            // Drawing image instead of map
            mapPanel.drawImage(map, party.getActiveChar().getX(),
                party.getActiveChar().getY(), travelPanel.getImage(), false); 
          } else {
                 mapPanel.drawMap(map, party.getActiveChar().getX(),
                    party.getActiveChar().getY(), party.isDay(),false);
          }
        } else {
          if (travelPanel != null && travelPanel.getImage() != null) {
            // Drawing image instead of map
            mapPanel.drawImage(map, lastDrawMapPosX,
                lastDrawMapPosY, travelPanel.getImage(),false);
          } else {
            mapPanel.drawMap(map, lastDrawMapPosX,
                lastDrawMapPosY, party.isDay(),false);            
          }
        }
      }
      gameLog.updateLog(party.getLogText());
      for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
        memberPanel[i].updatePanel(party.getPartyChar(i));
      }
      if (spellPanel != null) {
        spellPanel.updateSpellInfo();
      }
      this.repaint();
      drawingMapLock.release();
    }
    
    if (ActionCommands.GAME_LEADER_PARTY_1.equals(arg0.getActionCommand())) {
      if (!party.isCombat()) {
        if (party.getActiveCharIndex()==0) {
          if (party.getMode()==Party.MODE_PARTY_MODE) {
            party.setMode(Party.MODE_SOLO_MODE);          
          } else {
            if (party.checkPartyDistancesClose()) {
              party.setMode(Party.MODE_PARTY_MODE);
            } else {
              party.addLogText("Party members too far away for party mode...");
            }
          }
        } else {
          party.setActiveChar(0);
        }
        for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
          memberPanel[i].updatePanel(party.getPartyChar(i));
        }
      }
    }
    if (ActionCommands.GAME_LEADER_PARTY_2.equals(arg0.getActionCommand())) {
      if (!party.isCombat()) {
        if (party.getActiveCharIndex()==1) {
          if (party.getMode()==Party.MODE_PARTY_MODE) {
            party.setMode(Party.MODE_SOLO_MODE);          
          } else {
            if (party.checkPartyDistancesClose()) {
              party.setMode(Party.MODE_PARTY_MODE);
            } else {
              party.addLogText("Party members too far away for party mode...");
            }
          }
        } else {
          party.setActiveChar(1);
        }
        for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
          memberPanel[i].updatePanel(party.getPartyChar(i));
        }
      }
    }
    if (ActionCommands.GAME_LEADER_PARTY_3.equals(arg0.getActionCommand())) {
      if (!party.isCombat()) {
        if (party.getActiveCharIndex()==2) {
          if (party.getMode()==Party.MODE_PARTY_MODE) {
            party.setMode(Party.MODE_SOLO_MODE);          
          } else {
            if (party.checkPartyDistancesClose()) {
              party.setMode(Party.MODE_PARTY_MODE);
            } else {
              party.addLogText("Party members too far away for party mode...");
            }
          }
        } else {
          party.setActiveChar(2);
        }
        for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
          memberPanel[i].updatePanel(party.getPartyChar(i));
        }
      }
    }
    if (ActionCommands.GAME_LEADER_PARTY_4.equals(arg0.getActionCommand())) {
      if (!party.isCombat()) {
        if (party.getActiveCharIndex()==3) {
          if (party.getMode()==Party.MODE_PARTY_MODE) {
            party.setMode(Party.MODE_SOLO_MODE);          
          } else {
            if (party.checkPartyDistancesClose()) {
              party.setMode(Party.MODE_PARTY_MODE);
            } else {
              party.addLogText("Party members too far away for party mode...");
            }
          }
        } else {
          party.setActiveChar(3);
        }
        for (int i=0;i<Party.MAX_PARTY_SIZE;i++) {
          memberPanel[i].updatePanel(party.getPartyChar(i));
        }
      }
    }
    if (ActionCommands.GAME_SEARCH_TAKE.equalsIgnoreCase(arg0.getActionCommand())) {
      if (searchPanel != null) {
        Object[] objs = searchPanel.getList().getSelectedValues();
        if (objs != null) {
          Character chr = party.getActiveChar();
          for (int i=0;i<objs.length;i++) {
            Item item = (Item) objs[i];
            if (chr.inventoryPickUpItem(item)) {
              party.addLogText(chr.getName()+" founds "+item.getItemNameInGame()+".");
              map.removeItem(item);
            } else {
              party.addLogText(item.getItemNameInGame()+" was too heavy for "+chr.getName()+".");
            }
          }
        }        
        gamePanels.remove(searchPanel);
        gamePanels.add(partyPanel);
        gamePanels.validate();
        searchPanel = null;
      }
    }
    if (ActionCommands.GAME_SEARCH_TAKE_ALL.equalsIgnoreCase(arg0.getActionCommand())) {
      if (searchPanel != null) {
        Item[] items = searchPanel.getAllItems();
        if (items != null) {
          Character chr = party.getActiveChar();
          for (int i=0;i<items.length;i++) {
            if (chr.inventoryPickUpItem(items[i])) {
              party.addLogText(chr.getName()+" found "+items[i].getItemNameInGame()+".");
              map.removeItem(items[i]);
            } else {
              party.addLogText(items[i].getItemNameInGame()+"\nwas too heavy for "+chr.getName()+".");
            }
          }
        }        
        gamePanels.remove(searchPanel);
        gamePanels.add(partyPanel);
        gamePanels.validate();
        searchPanel = null;
      }
    }
    if (ActionCommands.GAME_TRAVEL_YES.equals(arg0.getActionCommand())) {
      actionTravelPanelYes();
    }
    if (ActionCommands.GAME_TRAVEL_NO.equals(arg0.getActionCommand())) {      
      actionTravelPanelNo();      
    }
    if (ActionCommands.GAME_SPELL_CANCEL.equalsIgnoreCase(arg0.getActionCommand())) {
      actionCastSpellCancel();
    }
    if (ActionCommands.GAME_SPELL_CAST.equalsIgnoreCase(arg0.getActionCommand())) {
      actionCastSpell();
    }
    if (ActionCommands.GAME_SELECT_MEMBER1.equalsIgnoreCase(arg0.getActionCommand()) && party.isMainCharacterAlive()) {
      if (party.getPartyChar(0) != null) {
        if ((party.getPerksLeft(0)==0) && (party.getSkillPointsLeft(0)==0)) {
          charSheetPanel = new GameCharacterSheet(map, party, 0,GameCharacterSheet.SELECTED_TAB_INVENTORY, this);
        } else {
          charSheetPanel = new GameCharacterSheet(map, party, 0,GameCharacterSheet.SELECTED_TAB_SKILLS_N_PERKS, this);
        }
        changeState(GAME_STATE_CHARACTER_SHEET);
      }
    }
    if (ActionCommands.GAME_SELECT_MEMBER2.equalsIgnoreCase(arg0.getActionCommand()) && party.isMainCharacterAlive()) {
      if (party.getPartyChar(1) != null) {
        if ((party.getPerksLeft(1)==0) && (party.getSkillPointsLeft(1)==0)) {
          charSheetPanel = new GameCharacterSheet(map, party, 1,GameCharacterSheet.SELECTED_TAB_INVENTORY, this);
        } else {
          charSheetPanel = new GameCharacterSheet(map, party, 1,GameCharacterSheet.SELECTED_TAB_SKILLS_N_PERKS, this);
        }
        changeState(GAME_STATE_CHARACTER_SHEET);
      }
    }
    if (ActionCommands.GAME_SELECT_MEMBER3.equalsIgnoreCase(arg0.getActionCommand()) && party.isMainCharacterAlive()) {
      if (party.getPartyChar(2) != null) {
        if ((party.getPerksLeft(2)==0) && (party.getSkillPointsLeft(2)==0)) {
          charSheetPanel = new GameCharacterSheet(map, party, 2,GameCharacterSheet.SELECTED_TAB_INVENTORY, this);
        } else {
          charSheetPanel = new GameCharacterSheet(map, party, 2,GameCharacterSheet.SELECTED_TAB_SKILLS_N_PERKS, this);
        }
        changeState(GAME_STATE_CHARACTER_SHEET);
      }
    }
    if (ActionCommands.GAME_SELECT_MEMBER4.equalsIgnoreCase(arg0.getActionCommand()) && party.isMainCharacterAlive()) {
      if (party.getPartyChar(3) != null) {
        if ((party.getPerksLeft(3)==0) && (party.getSkillPointsLeft(3)==0)) {
          charSheetPanel = new GameCharacterSheet(map, party, 3,GameCharacterSheet.SELECTED_TAB_INVENTORY, this);
        } else {
          charSheetPanel = new GameCharacterSheet(map, party, 3,GameCharacterSheet.SELECTED_TAB_SKILLS_N_PERKS, this);
        }
        changeState(GAME_STATE_CHARACTER_SHEET);
      }
    }
  }
  
  /**
   * This should be called after script is being run.
   * This handles possible map changes, journal changes which
   * are now available while running the script.
   */
  private void scriptHandlingAfterRun(int index) {
    if (playingEvent.getJournalQuestName() != null) {
      DebugOutput.debugLog("Updating journal...");
      journal.addQuestEntry(party, playingEvent.getJournalQuestName(),
          playingEvent.getJournalEntry(), playingEvent.getJournalQuestStatus());
    }
    if (playingEvent.shouldRemoveEvent()) {
      DebugOutput.debugLog("Removing event...");
      map.removeEvent(index);
    }
    if (playingEvent.getPassTurns() > 0) {
      DebugOutput.debugLog("Passing turn...");
      for (int i=0;i<playingEvent.getPassTurns();i++) {
        party.timeAddTurn();
      }
    }
    if (playingEvent.shouldRunModifyMaps() && 
        playingEvent.getRunModifyMapEvents() != null) {
      DebugOutput.debugLog("Modifying maps...");
      for (int i=0;i<playingEvent.getRunModifyMapEvents().size();i++) {
        String name = playingEvent.getRunModifyMapEvents().get(i);
        map.runModifyMapByName(name);
      }
      map.doShading();
    }
    if (playingEvent.isActivateSFX() && 
        playingEvent.getActivateSFXList() != null &&
        playingEvent.getActivateSFXCommandList() != null &&
        playingEvent.getActivateSFXCommandList().size() == 
        playingEvent.getActivateSFXList().size()) {
      DebugOutput.debugLog("Activating SFX...");
      for (int i=0;i<playingEvent.getActivateSFXList().size();i++) {
        String name = playingEvent.getActivateSFXList().get(i);
        String command = playingEvent.getActivateSFXCommandList().get(i);
        map.activateSFXsByName(name, command);
      }
      map.doShading();
    }
    if (playingEvent.shouldRemoveNPC() &&
        playingEvent.getRemoveNPCList() != null) {
      DebugOutput.debugLog("Removing NPCs...");
      for (int i=0;i<playingEvent.getRemoveNPCList().size();i++) {
        String name = playingEvent.getRemoveNPCList().get(i);
        int j = map.getNPCByName(name);
        if (j != -1) {
          map.removeNPCbyIndex(j);
          DebugOutput.debugLog("Removed "+name);
        }
      }
    }
    if (playingEvent.isNPCGoingToMove()) {
      DebugOutput.debugLog("Moving NPCs...");
      String[] npcs = playingEvent.getNPCsToMove();
      String[] wps = playingEvent.getNPCsNewWP();
      if (npcs.length > 0 && npcs.length == wps.length) {
        for (int i=0;i<npcs.length;i++) {
          Character npc = map.getNPCbyIndex(map.getNPCByName(npcs[i]));
          if (npc != null) {
            Event wp = map.getEventByIndex(map.getEventWaypointByName(wps[i]));
            if (wp != null) {
              npc.clearTaskList();
              CharTask task = new CharTask("-", CharTask.TASK_MOVE_INSIDE_WP, wps[i], "");
              npc.addTask(task);
              npc.setPosition(wp.getX(), wp.getY());
            }
          }
        }
      }
    }
    if (playingEvent.getText().isEmpty()) {
      playingEvent = null;
    }

  }
  
  /**
   * Add npcs according the encounter event
   * @param event
   */
  private void handleEncounter(Event event) {
    int wpIndex =map.getEventWaypointByName(event.getTargetWaypointName());
    if (wpIndex != -1) {
      Event wp = map.getEventByIndex(wpIndex);
      int maxNPCs = map.getWaypointNonBlockedSize(wpIndex);
      int targetLvl = party.getTotalLevel();
      int lvl = 0;
      int num = 0;
      int roundsNumSame=0;
      int lastNum=0;
      String[] names = event.getParam(0).split("#");
      for (;;) {
        Character tmp = GameMaps.getCharacterFromListByName(names[DiceGenerator.getRandom(names.length-1)]);
        if (tmp != null) {
          Character newChr = new Character(0);
          newChr.copyOf(tmp);
          int x = wp.getRandomX();
          int y = wp.getRandomY();
          if (!map.isTargetBlocked(x, y)) {
            newChr.setPosition(x, y);
            CharTask task = new CharTask("-", CharTask.TASK_MOVE_INSIDE_WP_AND_ATTACK, wp.getEventName(), "");
            newChr.addTask(task);
            newChr.setHostilityLevel(Character.HOSTILITY_LEVEL_AGGRESSIVE);
            if (map.addNPC(newChr)) {
              lvl = lvl +newChr.getLevel();
              num = num +1;
              if (lvl > targetLvl) {
                break;
              }
              if (num == maxNPCs) {
                break;
              }
            }
          }
        }
        // Making sure that if no monster found or cannot be place 
        // Encounter will exit at some point
        if (num == lastNum) {
          roundsNumSame++;
        } else {
          roundsNumSame = 0;
          lastNum = num;
        }
        if (roundsNumSame > 20) {
          break;
        }
      }
    }

  }

  /**
   * Make Quick travel according the event
   * @param event
   * @return event
   */
  private Event makeQuickTravel(Event event) {
    mapPanel.forceFullRepaint();
    if (event.getParam(0).isEmpty()) {
      map.partyTravel(event);
      SoundPlayer.playSoundBySoundName(event.getParam(2));
      // Run new event on new spot
      int x = party.getActiveChar().getX();
      int y = party.getActiveChar().getY();
      int index = map.getEventNotWaypoint(x, y);
      if (index != -1) {
        event = map.getEventByIndex(index);
      }
    } else {
      GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,getScreenShot(),map, 
          GameSaveAndLoadTask.TASK_TRAVEL_MAP, event.getParam(0));
      loadThread = new Thread(saveNLoad);
      gameLoadScreen = new GameLoadScreen("Traveling...",GameSaveAndLoadTask.TASK_TRAVEL_MAP);
      gameLoadScreen.setEvent(event);
      loadThread.start();
      changeState(GAME_STATE_LOADSCREEN);
    }
    return event;
  }
  
  private void playerMakesAMove() {
    int x = party.getActiveChar().getX();
    int y = party.getActiveChar().getY();
    int index = map.getEvent(x, y);
    if (index != -1) {
      Event event = map.getEventByIndex(index);
      if (event.getEventCommand()==Event.COMMAND_TYPE_TRAVEL) {
        mapPanel.forceFullRepaint();
        if (event.getParam(0).isEmpty()) {
          map.partyTravel(event);
          SoundPlayer.playSoundBySoundName(event.getParam(2));
          // Run new event on new spot
          x = party.getActiveChar().getX();
          y = party.getActiveChar().getY();
          index = map.getEventNotWaypoint(x, y);
          if (index != -1) {
            event = map.getEventByIndex(index);
          }
        } else {
          travelPanel = new GameTravelPanel(event.getParam(2), event, this);
          gamePanels.remove(partyPanel);
          gamePanels.add(travelPanel);
          gamePanels.validate();
        }
      }
      if (event.getEventCommand() ==Event.COMMAND_TYPE_LOCKED_GATE) {
        if (event.getLockDoorDifficulty() == 0) {
          map.doOpenGate(event);
          SoundPlayer.playSoundBySoundName(event.getEventSound());
          map.removeEvent(index);
        }
      }
      if (event.getEventCommand()==Event.COMMAND_TYPE_QUICK_TRAVEL) {
        event = makeQuickTravel(event);
      }
      if (event.getEventCommand()==Event.COMMAND_TYPE_CONDITIONAL_TRAVEL) {
        mapPanel.forceFullRepaint();
        playingEvent = new GameEventHandler(event, party.getActiveChar(), x, y, party);
        if (!playingEvent.isPlayable()) {
          playingEvent = null;
        } else {
          DebugOutput.debugLog("Conditional travel playable...");
          playingEvent.setEventIndex(index);
          if (event.getParam(0).isEmpty()) {
            // No question asked
            playingEvent.runScript();
            scriptHandlingAfterRun(index);
            map.partyTravel(event);
            SoundPlayer.playSoundBySoundName(event.getEventSound());
            // Run new event on new spot
            x = party.getActiveChar().getX();
            y = party.getActiveChar().getY();
            index = map.getEventNotWaypoint(x, y);
            if (index != -1) {
              event = map.getEventByIndex(index);
            }
            playingEvent = null;
          } else {
            travelPanel = new GameTravelPanel(event.getParam(0), event, this);
            gamePanels.remove(partyPanel);
            gamePanels.add(travelPanel);
            gamePanels.validate();
          }
        }
        
        
      }
      if (event.getEventCommand()==Event.COMMAND_TYPE_CONDITIONAL_MAP_TRAVEL) {
        mapPanel.forceFullRepaint();
        playingEvent = new GameEventHandler(event, party.getActiveChar(), x, y, party);
        if (!playingEvent.isPlayable()) {
          playingEvent = null;
        } else {
          DebugOutput.debugLog("Conditional map travel playable...");
          playingEvent.setEventIndex(index);
          if (event.getParam(0).isEmpty()) {
            event.setParam(0, "Do you want to travel?");
          } 
          if (event.getParam(1).split("#").length == 2) {
            travelPanel = new GameTravelPanel(event.getParam(0), event, this);
            gamePanels.remove(partyPanel);
            gamePanels.add(travelPanel);
            gamePanels.validate();
          }
        }
        
        
      }
      if (event.getEventCommand()==Event.COMMAND_TYPE_YESNO_QUESTION) {
         travelPanel = new GameTravelPanel(event.getParam(0), event, this);
         gamePanels.remove(partyPanel);
         gamePanels.add(travelPanel);
         gamePanels.validate();
      }
      if (event.getEventCommand()==Event.COMMAND_TYPE_NPC_YELL) {
        Character npc = map.getNPCbyIndex(map.getNPCByName(event.getParam(0)));
        if (npc != null) {
          if (MapUtilities.getDistance(npc.getX(), npc.getY(), x, y) < Map.MAX_RANGED_RANGE) {
            // Makes sure that NPC is close enough. 
            playingEvent = new GameEventHandler(event, npc, x, y, party);
            if (!playingEvent.isPlayable()) {
              playingEvent = null;
            } else {
              DebugOutput.debugLog(npc.getLongName()+" starts talking...");
              map.addNewGraphEffect(playingEvent.getEffectX(),
                  playingEvent.getEffectY(), playingEvent.getEffect());          
              playingEvent.runScript();
              scriptHandlingAfterRun(index);
            }
          }
        }
      } // End of NPC YELL
      if (!party.isCombat()) {
        // Events that can happen only when not in combat
        if (event.getEventCommand()==Event.COMMAND_TYPE_NPC_TALK) {
          String[] npcNames = event.getParam(0).split("#");
          Character npc = map.getNPCbyIndex(map.getNPCByName(npcNames[0]));
          Character chr = null;
          if ((npcNames.length==2) && (npc != null)) {
            // NPC starts talking to certain PC
            chr = party.getPartyChar(party.getPartyMemberByName(npcNames[1]));
            if (chr != null) {
              if (MapUtilities.getDistance(npc.getX(), npc.getY(), chr.getX(), chr.getY()) < Map.MAX_TALK_RANGE) {
                party.setActiveChar(party.getPartyMemberByName(npcNames[1]));
                // Makes sure that NPC is close enough. 
                playingEvent = new GameEventHandler(event, npc, x, y, party);
                if (!playingEvent.isPlayable()) {
                  playingEvent = null;
                } else {
                  DebugOutput.debugLog(npc.getLongName()+" talks to "+chr.getLongName());
                  map.addNewGraphEffect(playingEvent.getEffectX(),
                      playingEvent.getEffectY(), playingEvent.getEffect());          
                  playingEvent.runScript();
                  scriptHandlingAfterRun(index);
                }
              }
            }
          } else {
            if (npc != null) {
              // NPC starts talking to active character
              if (MapUtilities.getDistance(npc.getX(), npc.getY(), x, y) < Map.MAX_TALK_RANGE) {
                // Makes sure that NPC is close enough. 
                playingEvent = new GameEventHandler(event, npc, x, y, party);
                if (!playingEvent.isPlayable()) {
                  playingEvent = null;
                } else {
                  DebugOutput.debugLog(npc.getLongName()+" starts talking...");
                  map.addNewGraphEffect(playingEvent.getEffectX(),
                      playingEvent.getEffectY(), playingEvent.getEffect());          
                  playingEvent.runScript();
                  scriptHandlingAfterRun(index);
                }
              }
            }
          }
        } // End of NPC TALK
        if (event.getEventCommand()==Event.COMMAND_TYPE_PC_TALK) {
          Character pc = party.getPartyChar(party.getPartyMemberByName(event.getParam(0)));
          if (pc != null && (party.getMode() == Party.MODE_PARTY_MODE)) {
            // PC starts talking to main character
            if (MapUtilities.getDistance(pc.getX(), pc.getY(), x, y) < Map.MAX_TALK_RANGE) {
              // Makes sure that PC is close enough. 
              x = party.getActiveChar().getX();
              y = party.getActiveChar().getY();
              playingEvent = new GameEventHandler(event, pc, x, y, party);
              if (!playingEvent.isPlayable()) {
                playingEvent = null;
              } else {
                DebugOutput.debugLog(pc.getLongName()+" starts talking...");
                map.addNewGraphEffect(playingEvent.getEffectX(),
                    playingEvent.getEffectY(), playingEvent.getEffect());          
                playingEvent.runScript();
                scriptHandlingAfterRun(index);
              }
            }
          }            
        } // End of PC TALK
        if (event.getEventCommand()==Event.COMMAND_TYPE_PC_YELL) {
          Character npc = null;
          if (event.getParam(0).isEmpty()) {
            npc = party.getActiveChar();
          } else {
            String[] params = event.getParam(0).split("#");
            String nameParam=params[0];
            if (params[0].isEmpty()) {
              npc = party.getActiveChar();
            } else {
              npc = party.getPartyChar(party.getPartyMemberByName(nameParam));
            }
          }
          if (npc != null) {
            if (MapUtilities.getDistance(npc.getX(), npc.getY(), x, y) < Map.MAX_RANGED_RANGE) {
              // Makes sure that NPC is close enough. 
              playingEvent = new GameEventHandler(event, npc, x, y, party);
              if (!playingEvent.isPlayable()) {
                playingEvent = null;
              } else {
                DebugOutput.debugLog(npc.getLongName()+" starts talking...");
                map.addNewGraphEffect(playingEvent.getEffectX(),
                    playingEvent.getEffectY(), playingEvent.getEffect());          
                playingEvent.runScript();
                scriptHandlingAfterRun(index);
              }
            }
          }
        } // End of PC Yell
        if (event.getEventCommand()==Event.COMMAND_TYPE_ENCOUNTER) {
          Character npc = null;
          npc = party.getActiveChar();
          if (npc != null) {
            playingEvent = new GameEventHandler(event, npc, x, y, party);
            if (!playingEvent.isPlayable()) {
              playingEvent = null;
            } else {
              DebugOutput.debugLog(npc.getLongName()+" triggers encounter to "+event.getTargetWaypointName()+"...");
              playingEvent.runScript();
              scriptHandlingAfterRun(index);
              handleEncounter(event);
              playingEvent = null;
            }
          }
        } // End of Encounter
      }
    }
    if (turnReady < TURN_MOVES_DONE) {
      if (party.isCombat()) {
        if (party.getMode()==Party.MODE_PARTY_MODE) {
          turnReady++;
          party.setActiveChar(turnReady);
          if (party.getPartySize() == turnReady) {
            party.setActiveChar(0);
            turnReady = TURN_MOVES_DONE;
            npcWaiter=MAX_NPC_WAITER;
            map.doNPCsMove(party.getHours(), party.getMins());
            if (!party.isMainCharacterAlive()) {
              changeState(GAME_STATE_HERO_DOWN);
            } else if (party.isHeroDown() && party.getMode() == Party.MODE_SOLO_MODE) {
              party.setCombat(false);
              party.setMode(Party.MODE_PARTY_MODE);
              party.setActiveChar(0);
            }
            if (!map.isNPCMovementInIteration()) {
              moveToNextTurn();
            }
          }
        } else {
          map.doNPCsMove(party.getHours(), party.getMins());
          npcWaiter=MAX_NPC_WAITER;
          turnReady = TURN_MOVES_DONE;
          if (!party.isMainCharacterAlive()) {
            changeState(GAME_STATE_HERO_DOWN);
          } else if (party.isHeroDown() && party.getMode() == Party.MODE_SOLO_MODE) {
            party.setCombat(false);
            party.setMode(Party.MODE_PARTY_MODE);
            party.setActiveChar(0);
          }
          if (!map.isNPCMovementInIteration()) {
            moveToNextTurn();
          }
        }
      } else {
        turnReady = TURN_MOVES_DONE;
        if (playingEvent == null) {
          if (party.getMode() == Party.MODE_PARTY_MODE) {
            map.doMovePartyMembers();
          }
          map.doNPCsMove(party.getHours(), party.getMins());
        }
        if (!party.isMainCharacterAlive()) {
          changeState(GAME_STATE_HERO_DOWN);
        } else if (party.isHeroDown() && party.getMode() == Party.MODE_SOLO_MODE) {
          party.setCombat(false);
          party.setMode(Party.MODE_PARTY_MODE);
          party.setActiveChar(0);
        }
        
        moveToNextTurn();
        }
    }
  }
  
  /**
   * Move to next turn and do all the needful
   */
  private void moveToNextTurn() {
    if (!party.isHeroDown()) {
      for (int i=0;i<party.getNumberOfPartyMembers();i++) {
        Character chr = party.getPartyChar(i);
        if (chr.isDead()) {
          map.dropAllItems(chr);
          party.setHeroDown(true);
          party.removePartyChar(i);
          party.addLogText(chr.getLongName()+" has died!");      
          if (i != 0) {
            party.setActiveChar(0);
          }
          break;
        }
      }
    }
    party.timeAddTurn();
    if ((party.getTime().equalsIgnoreCase("18:0:0")) ||
       (party.getTime().equalsIgnoreCase("6:0:0"))) {
      map.forceMapFullRepaint();
    }
    if (party.isGameEndDueDeadLine()) {
      if (party.getStoryVariable(0)==11 || party.getStoryVariable(42)==3 || party.getStoryVariable(43)==3) {
        changeState(GAME_STATE_WIN);
      } else {
        changeState(GAME_STATE_LOSE);
      }
      
    }
    if (party.isAddJournalInfoDueDeadLine()) {
      if (party.getDeadLine() < 3 && party.getDeadLine() > 0) {
        journal.addQuestEntry(party, Party.QUEST_NAME_WITH_DEAD_LINE,
            "Crowning will be in "+party.getDeadLine()+" days. Travel back to Hawks Haven castle before that...", 1);        
      } else {
        journal.addQuestEntry(party, Party.QUEST_NAME_WITH_DEAD_LINE,
          "Crowning will be in "+party.getDeadLine()+" days.", 1);
      }
      party.setAddJournalInfoDueDeadLine(false);
    }
    party.checkLevelUps(); 
  }
  
  @Override
  public void actionPerformed(ActionEvent arg0) {
    if (ActionCommands.GENERIC_MUSIC_TIMER.equals(arg0.getActionCommand())) {    
      
      // Music handling in game
      if (state == GAME_STATE_GAME) {
        if (party.getActiveChar() != null) {
          int sector = map.getSectorByCoordinates(party.getActiveChar().getX(),
              party.getActiveChar().getY());
  
          if (party.isCombat()) {
            if (party.isHeroDown()) {
              MusicPlayer.setNextSong(MusicPlayer.MUSIC_FILE_HERO_DOWN);
            } else {
              MusicPlayer.setNextSong(map.getCombatMusicBySector(sector));
            }
          } else {
            if (party.isDay()) {
              MusicPlayer.setNextSong(map.getDayMusicBySector(sector));
            } else {
              MusicPlayer.setNextSong(map.getNightMusicBySector(sector));            
            }
          }
        }
      }
      if (!MusicPlayer.getNextSong().equals(MusicPlayer.getCurrentlyPlaying())) {
        MusicPlayer.play(MusicPlayer.getNextSong());
      }
    }

    switch (state) {
    case GAME_STATE_INTRO: {
      if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ACTION_STORY_CONTINUE) 
          && gameStoryPanel != null) {
        if (!gameStoryPanel.updateStoryPanel()) {
          gameStoryPanel = null;
          changeState(GAME_STATE_NEWGAME);
        } else {
          repaint();
        }
      }

      break;
    }
    case GAME_STATE_LOSE: {
      if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ACTION_STORY_CONTINUE) 
          && gameStoryPanel != null) {
        if (!gameStoryPanel.updateStoryPanel()) {
          gameStoryPanel = null;
          changeState(GAME_STATE_MAINMENU);
        } else {
          repaint();
        }
      }

      break;
    }
    case GAME_STATE_WIN: {
      if (arg0.getActionCommand().equalsIgnoreCase(ActionCommands.ACTION_STORY_CONTINUE) 
          && gameStoryPanel != null) {
        if (!gameStoryPanel.updateStoryPanel()) {
          
          gameStoryPanel = null;
          changeState(GAME_STATE_CREDITS);
        } else {
          repaint();
        }
      }

      break;
    }
    case GAME_STATE_CREDITS: {
      actionPerformedCredits(arg0); break;
    }
    case GAME_STATE_HERO_DOWN: {
      actionPerformedHeroDown(arg0); break;
    }
    case GAME_STATE_MAINMENU: {
      actionPerformedMainMenu(arg0); break;
    }
    case GAME_STATE_OPTIONS: {
      actionPerformedOptionsMenu(arg0); break;
    }
    case GAME_STATE_DEATH_SCREEN: {
      actionPerformedDeathScreen(arg0); break;
    }
    case GAME_STATE_NEWGAME: {
      actionPerformedNewGame(arg0); break;
    }
    case GAME_STATE_NEWGAME_SKILLS: {
      actionPerformedNewGame(arg0); break;
    }
    case GAME_STATE_NEWGAME_PERKS: {
      actionPerformedNewGame(arg0); break;
    }
    case GAME_STATE_GAME: {
      actionPerformedGame(arg0); break;
    }
    case GAME_STATE_GAME_HELP: {
      if (arg0.getActionCommand().equals(ActionCommands.GAMEHELP_EXIT)) {
        gameHelpPanel = null;
        if (gamePanels != null) {
          changeState(GAME_STATE_GAME);
        } else {
          changeState(GAME_STATE_MAINMENU);
        }
      } else {
        gameHelpPanel.actionHandler(arg0);
      }
      break;
    }
    case GAME_STATE_LOADSCREEN: {
      gameLoadScreen.updateAll(GameMaps.getProgress());
      if ((!loadThread.isAlive()) &&(gameLoadScreen.isReady())) {
        switch ( gameLoadScreen.getTask()) {
        case GameSaveAndLoadTask.TASK_CREATE_NEW_GAME: {        
            gameLoadScreen = null;
            changeState(GAME_STATE_GAME);
            break;
          }
        case GameSaveAndLoadTask.TASK_SAVE_GAME:{
          gameLoadScreen = null;
          changeState(GAME_STATE_MAINMENU);      
          break;
        }
        case GameSaveAndLoadTask.TASK_LOAD_CURRENT_GAME:
        case GameSaveAndLoadTask.TASK_LOAD_GAME:{
          // Loads a new saved game
          party = GameMaps.getNewParty();
          turnReady = party.getActiveCharIndex();
          journal = GameMaps.getNewJournal();
          map = GameMaps.getNewMap();
          map.setParty(party);
          gameLoadScreen = null;
          changeState(GAME_STATE_GAME);
          break;
        }
        case GameSaveAndLoadTask.TASK_TRAVEL_MAP:{
          // Party travels to another map
          map = GameMaps.getNewMap();
          party.setCurrentMapName(GameMaps.getNewMapName());
          map.setParty(party);
          map.doNPCsCheckCorrectPositions(party.getHours(), party.getMins(),true);
          Event event = gameLoadScreen.getEvent();
          map.partyTravel(event);
          if (event.getEventCommand() == Event.COMMAND_TYPE_QUICK_TRAVEL || 
              event.getEventCommand() == Event.COMMAND_TYPE_TRAVEL) {
            SoundPlayer.playSoundBySoundName(event.getParam(2));
          }
          // Run new event on new spot
          int x = party.getActiveChar().getX();
          int y = party.getActiveChar().getY();
          int index = map.getEventNotWaypoint(x, y);
          if (index != -1) {
            event = map.getEventByIndex(index);
          }
          if (event.getEventName().equalsIgnoreCase(Event.TALK_TRAVEL)) {
            BufferedImage image = getScreenShot();
            try {
              GameMaps.saveCurrentMapAndParty(party, journal, map, image);
            } catch (IOException e1) {          
              e1.printStackTrace();
              System.out.print("Failing to save current game...");
            }
          }
          gameLoadScreen = null;
          changeState(GAME_STATE_GAME);
          break;
        }
        }
      }
      break;
    }
    case GAME_STATE_LOADGAME: 
    case GAME_STATE_SAVEGAME: {
      if (ActionCommands.GAMES_SCREEN_CANCEL.equals(arg0.getActionCommand())) {
        changeState(GAME_STATE_MAINMENU);
      } 
      if (ActionCommands.GAMES_SCREEN_LOAD1.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(null, null,null,null, 
            GameSaveAndLoadTask.TASK_LOAD_GAME, "save1");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Load game...",GameSaveAndLoadTask.TASK_LOAD_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_LOAD2.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(null, null,null,null, 
            GameSaveAndLoadTask.TASK_LOAD_GAME, "save2");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Load game...",GameSaveAndLoadTask.TASK_LOAD_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_LOAD3.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(null, null,null,null, 
            GameSaveAndLoadTask.TASK_LOAD_GAME, "save3");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Load game...",GameSaveAndLoadTask.TASK_LOAD_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_LOAD4.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(null, null,null,null, 
            GameSaveAndLoadTask.TASK_LOAD_GAME, "save4");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Load game...",GameSaveAndLoadTask.TASK_LOAD_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_LOAD5.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(null, null,null,null, 
            GameSaveAndLoadTask.TASK_LOAD_GAME, "save5");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Load game...",GameSaveAndLoadTask.TASK_LOAD_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_LOAD6.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(null, null,null,null, 
            GameSaveAndLoadTask.TASK_LOAD_GAME, "save6");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Load game...",GameSaveAndLoadTask.TASK_LOAD_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_SAVE1.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,null,null, 
            GameSaveAndLoadTask.TASK_SAVE_GAME, "save1");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Saving game...",GameSaveAndLoadTask.TASK_SAVE_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_SAVE2.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,null,null, 
            GameSaveAndLoadTask.TASK_SAVE_GAME, "save2");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Saving game...",GameSaveAndLoadTask.TASK_SAVE_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_SAVE3.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,null,null, 
            GameSaveAndLoadTask.TASK_SAVE_GAME, "save3");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Saving game...",GameSaveAndLoadTask.TASK_SAVE_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_SAVE4.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,null,null, 
            GameSaveAndLoadTask.TASK_SAVE_GAME, "save4");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Saving game...",GameSaveAndLoadTask.TASK_SAVE_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_SAVE5.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,null,null, 
            GameSaveAndLoadTask.TASK_SAVE_GAME, "save5");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Saving game...",GameSaveAndLoadTask.TASK_SAVE_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      if (ActionCommands.GAMES_SCREEN_SAVE6.equals(arg0.getActionCommand())) {
        GameSaveAndLoadTask saveNLoad = new GameSaveAndLoadTask(party, journal,null,null, 
            GameSaveAndLoadTask.TASK_SAVE_GAME, "save6");
        loadThread = new Thread(saveNLoad);
        gameLoadScreen = new GameLoadScreen("Saving game...",GameSaveAndLoadTask.TASK_SAVE_GAME);
        loadThread.start();
        changeState(GAME_STATE_LOADSCREEN);
      }
      break;
    }
    case GAME_STATE_CHARACTER_SHEET: {
      if (ActionCommands.SHEET_BACK_TO_GAME.equalsIgnoreCase(arg0.getActionCommand())) {    
        charSheetPanel = null;
        changeState(GAME_STATE_GAME);
      } else 
      if (ActionCommands.SHEET_CAST.equalsIgnoreCase(arg0.getActionCommand())) {
        if (!party.isCombat() || (party.isCombat() && 
            party.getActiveChar().equals(charSheetPanel.getCurrentChar()))) {
          Spell spell = charSheetPanel.getSelectedSpell();
          if (spell != null) {
            actionCastingSpellInGame(spell);  
          }
        charSheetPanel = null;
        changeState(GAME_STATE_GAME);
        }
      } else 
      if (ActionCommands.SHEET_USE_EQUIP.equalsIgnoreCase(arg0.getActionCommand())) {
        usedItemIndex = charSheetPanel.getSelectedItemIndex();
        usedItemIndex = party.getActiveChar().getInventoryItemIndexByReversed(usedItemIndex);
        Item item = party.getActiveChar().inventoryGetIndex(usedItemIndex);
        if (item != null) {
          if ((item.getType() == Item.TYPE_ITEM_KEY) ||
              (item.getType() == Item.TYPE_ITEM_PICKLOCK) ||
              (item.getType() == Item.TYPE_ITEM_DIGGING_TOOL)) {
            if (!map.isCursorMode()) {
              map.setCursorMode(Map.CURSOR_MODE_USE);
              map.setCursorX(party.getActiveChar().getX());
              map.setCursorY(party.getActiveChar().getY());
              map.setUsedItem(item);
              charSheetPanel = null;
              changeState(GAME_STATE_GAME);
            } else {
              map.setCursorMode(Map.CURSOR_MODE_DISABLE);
            }
          } else if (item.getType() == Item.TYPE_ITEM_MAGICL_COMPASS) {
            if (item.getName().equalsIgnoreCase("Voodoo head")) {
              charSheetPanel = null;
              changeState(GAME_STATE_GAME);
              if (party.getCurrentMapName().equalsIgnoreCase("theisland.map")) {
                int tx = 198;
                int ty = 167;
                if (map.getSectorByCoordinates(party.getActiveChar().getX(),
                    party.getActiveChar().getY())==3) {
                  party.addLogText("Head points to "+Map.getDirectionAsString(
                      map.getDirectionTowards(party.getActiveChar().getX(),
                          party.getActiveChar().getY(), tx, ty, 8), 8));
                  byte heading = (byte) map.getDirectionTowards(party.getActiveChar().getX(),
                      party.getActiveChar().getY(), tx, ty, 4);
                  if (heading != -1) {
                    party.getActiveChar().setHeading(heading);
                  } else {
                    party.getActiveChar().setHeading(Character.DIRECTION_SOUTH);
                  }
                } else {
                  party.addLogText("Head points to ground...");
                }
              } else {
                party.addLogText("Head just rotates around...");
              }
            }
          } else {
            charSheetPanel.handleActionEvent(arg0);
            if (charSheetPanel.isPlayerMakesAMove() && party.isCombat()) {
              charSheetPanel = null;
              changeState(GAME_STATE_GAME);
              playerMakesAMove();
            }
          }
        }
      } else
      if (charSheetPanel != null) {
          charSheetPanel.handleActionEvent(arg0);         
      }
      break;
    }
    case GAME_STATE_JOURNAL: {
      if (ActionCommands.JOURNAL_EXIT.equalsIgnoreCase(arg0.getActionCommand())) {
        gameJournalPanel = null;
        changeState(GAME_STATE_GAME);
      } else        
      if (gameJournalPanel != null) {
          gameJournalPanel.handleActions(arg0);
      }
      break;
    }
    case GAME_STATE_DEBUGMODE: {
      if (ActionCommands.DEBUGMODE_EXIT.equalsIgnoreCase(arg0.getActionCommand())) {
        gameDebugPanel = null;
        changeState(GAME_STATE_GAME);
      }
      if (ActionCommands.DEBUGMODE_PLUS.equals(arg0.getActionCommand())) {
        if (gameDebugPanel.getSelectedStoryVar() != -1) {
          int value = party.getStoryVariable(gameDebugPanel.getSelectedStoryVar());
          if (value < 255) {
            value++;
            party.setStoryVariable(gameDebugPanel.getSelectedStoryVar(), value,
                "Debug screen set");
            gameDebugPanel.updateList();
            repaint();
          }
        }
      }
      if (ActionCommands.DEBUGMODE_MINUS.equals(arg0.getActionCommand())) {
        if (gameDebugPanel.getSelectedStoryVar() != -1) {
          int value = party.getStoryVariable(gameDebugPanel.getSelectedStoryVar());
          if (value > 0) {
            value--;
            party.setStoryVariable(gameDebugPanel.getSelectedStoryVar(), value,
                "Debug screen set");
            gameDebugPanel.updateList();
            repaint();
          }
        }
      }
      break;
    }
    case GAME_STATE_BARTERING: {
      if (ActionCommands.BARTERING_EXIT.equalsIgnoreCase(arg0.getActionCommand())) {
        gameBarteringPanel = null;
        changeState(GAME_STATE_GAME);
      } else        
      if (gameBarteringPanel != null) {
          gameBarteringPanel.handleActions(arg0);
      }
      break;
    }
    case GAME_STATE_TALK: {
      if (ActionCommands.TALK_END.equals(arg0.getActionCommand())) {
        gameTalkPanel = null;
        changeState(GAME_STATE_GAME);
      } else {
        if (gameTalkPanel != null) {
           gameTalkPanel.handleActions(arg0);
           if (gameTalkPanel.isCheckNPCPosition()) {
             map.doNPCsCheckCorrectPositions(party.getHours(), party.getMins(),true);
           }
           if (gameTalkPanel.isEndTalk() && gameTalkPanel.isRunShop()) {
             gameBarteringPanel = new GameBartering(party.getActiveChar(), gameTalkPanel.getNPC(),false, this);
             gameTalkPanel = null;
             changeState(GAME_STATE_BARTERING);             

           } else
           if (gameTalkPanel.isEndTalk() && gameTalkPanel.isRunTrade()) {
             gameBarteringPanel = new GameBartering(party.getActiveChar(), gameTalkPanel.getNPC(),true, this);
             gameTalkPanel = null;
             changeState(GAME_STATE_BARTERING);             
           } else
           if (gameTalkPanel.isEndTalk()){
             if (gameTalkPanel.isRunExit()) {
               Character npc =map.getNPCbyIndex(map.getNPCByName(gameTalkPanel.getNPC().getLongName())); 
               if (npc != null) {
                 npc.addTaskRunExit(gameTalkPanel.getExitDirection());
               }
             }
             if (gameTalkPanel.isDead()) {
               Character npc =map.getNPCbyIndex(map.getNPCByName(gameTalkPanel.getNPC().getLongName())); 
               if (npc != null) {
                 npc.setCurrentHP(0);
               }
             }
             if (gameTalkPanel.isAddNPC()) {
               map.addNPC(gameTalkPanel.getNPC());
             }
             if (gameTalkPanel.isMoveAway()) {
               Character npc =map.getNPCbyIndex(map.getNPCByName(gameTalkPanel.getNPC().getLongName())); 
               if (npc != null) {
                 npc.addTaskMoveAway(gameTalkPanel.getMoveAwayDirection());
               }
             }
             if (gameTalkPanel.isTeleportToExit()) {
               Character npc =map.getNPCbyIndex(map.getNPCByName(gameTalkPanel.getNPC().getLongName())); 
               if (npc != null) {
                 npc.addTaskTeleportToExit(gameTalkPanel.getTeleportWP());
               }
             }
             if (gameTalkPanel.isMoveToWP()) {
               Character npc =map.getNPCbyIndex(map.getNPCByName(gameTalkPanel.getNPC().getLongName())); 
               if (npc != null) {
                 npc.addTaskToMoveWP(gameTalkPanel.getMoveWP(),gameTalkPanel.getMoveAwayDirection());
               }
             }
             if (gameTalkPanel.isRemoveNPC()) {
               map.removeNPCbyIndex(map.getNPCByName(gameTalkPanel.getNPC().getLongName()));
             }

             if (gameTalkPanel.isTravelSet()) {
               // Making custom event
               Event event = new Event(party.getActiveChar().getX(),
                   party.getActiveChar().getY());
               event.setEventCommand(Event.COMMAND_TYPE_QUICK_TRAVEL);
               if (gameTalkPanel.getTravelMap() != null) {
                 event.setParam(0, gameTalkPanel.getTravelMap());
               } else {
                 event.setParam(0,"");
               }
               event.setParam(1, gameTalkPanel.getTravelWP());
               event.setParam(2, "");
               event.setEventName(Event.TALK_TRAVEL);
               makeQuickTravel(event);
               gameTalkPanel = null;
               if (event.getParam(0).isEmpty()) {
                 // No loading screen so game state must be changed.
                 changeState(GAME_STATE_GAME);
               }
             } else {
               // Travelling causes change in game state so no more change after
               // talk
               gameTalkPanel = null;
               changeState(GAME_STATE_GAME);
             }
           }
        }
      }
    }
    break;
    }
    
  }

  
  private void actionPerformedOptionsMenu(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.ACTION_OPTIONS_EXIT)) {
      changeState(GAME_STATE_MAINMENU);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        ActionCommands.ACTION_OPTIONS_SAVENEXIT)) {
      SoundPlayer.setSoundVolume(sfxSlider.getValue());
      OggPlayer.setMusicVolume(musicSlider.getValue());
      GameOptions.writeOptions();
      changeState(GAME_STATE_MAINMENU);
    }
    
  }


  /**
   * New keyboard adapter for game
   *
   */
  class TAdapter extends KeyAdapter {
    
    private ActionListener listener;
    
    private void keyPressedInGamePlayingEvent(int key) {
      if (key == KeyEvent.VK_ENTER) {
        if (playingEvent.getNextText()) {
          if (playingEvent.getTalkFile() != null) {
            if (playingEvent.isPCTalk()) {
              // Changing to main char
              party.setActiveChar(0);
            }
            DebugOutput.debugLog("Load talk file:"+playingEvent.getTalkFile()+" ...");
            Talk talk = Talk.readTalkResource(playingEvent.getTalkFile());
            if (talk != null) {
              DebugOutput.debugLog("OK");
              gameTalkPanel = new GameTalk(party, playingEvent.getCharacter(),
                  talk,journal, listener);
              changeState(GAME_STATE_TALK);
            } else {
              DebugOutput.debugLog("Could not found talk file");
              party.addLogText(playingEvent.getCharacter().getName()+" has nothing to say.");
            }
          }
          mapPanel.forceFullRepaint();
          playingEvent = null;
          map.setDisplayPopupText(null);
          map.setPopupImage(null);
          map.clearAllGraphEffects();
        }
      }      
    }
    
    private void keyPressedInGameNotCursorMode(int key) {
      if (turnReady != TURN_MOVES_DONE) {
        if (key == KeyEvent.VK_UP) {
          Character chr = party.getActiveChar();
          int x = chr.getX();
          int y = chr.getY();
          y--;
          int hit = map.isBlockOrPartyMember(chr.getX(), chr.getY(), x, y,true);
          if (hit ==-1) {
            chr.doMove(x, y);
            playerMakesAMove();
          } else {
            if ((hit != 999) && (!party.isCombat())) {
              Character target = party.getPartyChar(hit);
              if (target != null) {
                target.doMove(chr.getX(), chr.getY());
                chr.doMove(x, y);
                playerMakesAMove();
              }
            }
          }
        }
        if (key == KeyEvent.VK_DOWN) {
          Character chr = party.getActiveChar();
          int x = chr.getX();
          int y = chr.getY();
          y++;
          int hit = map.isBlockOrPartyMember(chr.getX(), chr.getY(), x, y,true);
          if (hit ==-1) {
            chr.doMove(x, y);
            playerMakesAMove();
          } else {
            if ((hit != 999) && (!party.isCombat())) {
              Character target = party.getPartyChar(hit);
              if (target != null) {
                target.doMove(chr.getX(), chr.getY());
                chr.doMove(x, y);
                playerMakesAMove();
              }
            }
          }
        }
        if (key == KeyEvent.VK_LEFT) {
          Character chr = party.getActiveChar();
          int x = chr.getX();
          int y = chr.getY();
          x--;
          int hit = map.isBlockOrPartyMember(chr.getX(), chr.getY(), x, y,true);
          if (hit ==-1) {
            chr.doMove(x, y);
            playerMakesAMove();
          } else {
            if ((hit != 999) && (!party.isCombat())) {
              Character target = party.getPartyChar(hit);
              if (target != null) {
                target.doMove(chr.getX(), chr.getY());
                chr.doMove(x, y);
                playerMakesAMove();
              }
            }
          }
        }
        if (key == KeyEvent.VK_RIGHT) {
          Character chr = party.getActiveChar();
          int x = chr.getX();
          int y = chr.getY();
          x++;
          int hit = map.isBlockOrPartyMember(chr.getX(), chr.getY(), x, y,true);
          if (hit ==-1) {
            chr.doMove(x, y);
            playerMakesAMove();
          } else {
            if ((hit != 999) && (!party.isCombat())) {
              Character target = party.getPartyChar(hit);
              if (target != null) {
                target.doMove(chr.getX(), chr.getY());
                chr.doMove(x, y);
                playerMakesAMove();
              }
            }
          }
        }
        if (key == KeyEvent.VK_P) {
          Character chr = party.getActiveChar();
          int index = map.getItemIndexByCoordinates(chr.getX(), chr.getY());
          if (index != -1) {
            Item item = map.getItemByIndex(index);
            if (chr.inventoryPickUpItem(item)) {
              party.addLogText(chr.getName()+" picked up "+item.getItemNameInGame()+".");
              map.removeItemByIndex(index);
              playerMakesAMove();
            }
          }
        }
        if (key == KeyEvent.VK_S) {
          if (party.isCombat()) {
            Character chr = party.getActiveChar();
            party.addLogText(chr.getName()+" cannot search in combat.");
          } else {
          Character chr = party.getActiveChar();
          ArrayList<Item> items = map.getItemsFromSurround(chr.getX(), chr.getY());
          if (items  != null) {
            if (items.size() == 1) {
              Item item = items.get(0);
              if (chr.inventoryPickUpItem(item)) {
                party.addLogText(chr.getName()+" found "+item.getItemNameInGame()+".");
                map.removeItem(item);
              } else {
                party.addLogText(chr.getName()+" found "+item.getItemNameInGame()+" but cannot carry it.");
              }
              
            } else {
              searchPanel = new GameSearchPanel(chr.getName(), items, listener);
              gamePanels.remove(partyPanel);
              gamePanels.add(searchPanel);
              gamePanels.validate();
            }
          } else {
            party.addLogText(chr.getName()+" found nothing.");
          }
          playerMakesAMove();
          }
        }
        if (key == KeyEvent.VK_C) {
          Character chr = party.getActiveChar();
          ArrayList<String> spells = chr.getSpellList();
          if (spells  != null) {
            if (spells.size() > 0) {
              spellPanel = new GameSpellPanel(chr.getName(), chr.getSpellList(), listener);
              spellPanel.setSelection(party.getLastCastSpell(party.getActiveCharIndex()));
              gamePanels.remove(partyPanel);
              gamePanels.add(spellPanel);
              gamePanels.validate();
            }
          }
        }
        if (key == KeyEvent.VK_R) {
          if (party.isCombat()) {
            party.addLogText("Cannot wait for hour while in combat.");
          } else {
            for (int i=0;i<party.getPartySize();i++) {
              Character chr = party.getPartyChar(i);
              if (chr != null) {
                chr.setCurrentSP(chr.getMaxStamina());
              }
            }
            party.timeAddHour();
            if (party.getHours()==6) {
              mapPanel.forceFullRepaint();
            }
            if (party.getHours()==18) {
              mapPanel.forceFullRepaint();
            }              
            map.doNPCsCheckCorrectPositions(party.getHours(), party.getMins(),false);
          }
        }
        if (key == KeyEvent.VK_W) {
            Character chr = party.getActiveChar();
            if ((party.isCombat()) || (party.getMode()==Party.MODE_SOLO_MODE)) {
              chr.getStaminaInRestTurn();
              playerMakesAMove();
              if ((party.getHours()==6) && (party.getMins()==0) && (party.getSecs()==0)) {
                mapPanel.forceFullRepaint();
              }
              if ((party.getHours()==18) && (party.getMins()==0) && (party.getSecs()==0)) {
                mapPanel.forceFullRepaint();
              }
            } else {
              party.restOneTurn();
              party.timeAddTurn();              
              map.doNPCsMove(party.getHours(), party.getMins());
            }
        }
      } // END OF KEYS which require one turn
      
    }
    
    private void keyPressedInGameMoveCursor(int key) {
      if (key == KeyEvent.VK_UP) {
        int x = map.getLastDrawnX();
        int y = map.getLastDrawnY();
        int cx = map.getCursorX();
        int cy = map.getCursorY();
        cy--;
        int mx = Math.abs(x-cx);
        int my = Math.abs(y-cy);;
        if ((map.getSectorByCoordinates(x, y)==map.getSectorByCoordinates(cx, cy)) &&
           (mx<=Map.VIEW_X_RADIUS) && (my<=Map.VIEW_Y_RADIUS)){
          map.setCursorX(cx);
          map.setCursorY(cy);
        }
      }
      if (key == KeyEvent.VK_DOWN) {
        int x = map.getLastDrawnX();
        int y = map.getLastDrawnY();
        int cx = map.getCursorX();
        int cy = map.getCursorY();
        cy++;
        int mx = Math.abs(x-cx);
        int my = Math.abs(y-cy);;
        if ((map.getSectorByCoordinates(x, y)==map.getSectorByCoordinates(cx, cy)) &&
           (mx<=Map.VIEW_X_RADIUS) && (my<=Map.VIEW_Y_RADIUS)){
          map.setCursorX(cx);
          map.setCursorY(cy);
        }
      }
        
      if (key == KeyEvent.VK_LEFT) {
        int x = map.getLastDrawnX();
        int y = map.getLastDrawnY();
        int cx = map.getCursorX();
        int cy = map.getCursorY();
        cx--;
        int mx = Math.abs(x-cx);
        int my = Math.abs(y-cy);;
        if ((map.getSectorByCoordinates(x, y)==map.getSectorByCoordinates(cx, cy)) &&
           (mx<=Map.VIEW_X_RADIUS) && (my<=Map.VIEW_Y_RADIUS)){
          map.setCursorX(cx);
          map.setCursorY(cy);
        }
      }
      if (key == KeyEvent.VK_RIGHT) {
        int x = map.getLastDrawnX();
        int y = map.getLastDrawnY();
        int cx = map.getCursorX();
        int cy = map.getCursorY();
        cx++;
        int mx = Math.abs(x-cx);
        int my = Math.abs(y-cy);;
        if ((map.getSectorByCoordinates(x, y)==map.getSectorByCoordinates(cx, cy)) &&
           (mx<=Map.VIEW_X_RADIUS) && (my<=Map.VIEW_Y_RADIUS)){
          map.setCursorX(cx);
          map.setCursorY(cy);
        }
      }
      
    }
    
    private void keyEnterInLookMode() {
      Character chr = party.getActiveChar();
      boolean interesting=false;
      int cx = map.getCursorX();
      int cy = map.getCursorY();
      int tileIndex = map.getDecoration(cx, cy);
      if (tileIndex != -1) {
        party.addLogText("There might be items hidden.");
        interesting = true;
      } else {
        int index = map.getItemIndexByCoordinates(cx, cy);
        Item item = map.getItemByIndex(index);
        if (item != null) {
          party.addLogText(chr.getName()+" sees "+item.getItemNameInGame()+".");
          interesting = true;
        }
      }
     
      int index = map.getCharacterByCoordinates(cx, cy);
      if (index != -1) {
        Character npc = map.getNPCbyIndex(index);
        if (npc != null) {
          party.addLogText(chr.getName()+" sees "+npc.getLongName()+".");
          party.addLogText(npc.getDescription());
          party.addLogText(npc.getWearings());
          if (party.isCombat()) {
            party.addLogText(npc.getHealthAsString());
          }
          DebugOutput.debugLog(npc.getName()+" task: "+npc.getCurrentTaskDesc());
          interesting = true;
        }
      }
      index = map.getEvent(cx, cy);
      if (index != -1) {
        Event event = map.getEventByIndex(index);
        if (event != null) {
          if (event.getEventCommand() == Event.COMMAND_TYPE_SIGN) {
            party.addLogText(chr.getName()+" reads the sign:\""+event.getParam(0)+"\"");
            interesting = true;
          }
          if (event.getEventCommand() == Event.COMMAND_TYPE_CLOCK) {
            interesting = true;
            if (event.getParam(0).equalsIgnoreCase("SUN")) {
              party.addLogText(chr.getName()+" looks sun dial: "+party.getTimeAsString(true));
            } else {
              party.addLogText(chr.getName()+" looks clock: "+party.getTimeAsString(false));
            }
          }
          if (event.getEventCommand() == Event.COMMAND_TYPE_LOOK_INFO) {
            party.addLogText(event.getParam(0));
            interesting = true;
          }

        }
      }
      if (!interesting) {
        party.addLogText(chr.getName()+" sees nothing interesting.");
      }
      map.setCursorMode(Map.CURSOR_MODE_DISABLE);
      
    }
    
    private void keyEnterInTalkMode() {
      int cx = map.getCursorX();
      int cy = map.getCursorY();
      int dist = MapUtilities.getDistance(party.getActiveChar().getX(), 
          party.getActiveChar().getY(), cx, cy);
      if ((dist < Map.MAX_TALK_RANGE) && (!party.isCombat())) {
        Character npc = map.getNPCbyIndex(map.getCharacterByCoordinates(cx, cy));
        if (npc != null) {
          Talk talk = Talk.readTalkResource(npc.getTalkFileName());
          if (talk != null) {
            gameTalkPanel = new GameTalk(party, npc,talk,journal, listener);
            changeState(GAME_STATE_TALK);
          } else {
            party.addLogText(npc.getName()+" has nothing to say.");
            DebugOutput.debugLog("Could not find resource file:"+npc.getTalkFileName());  
          }
        } else {
          if (party.getActiveCharIndex() != party.getPartyMemberByCoordinates(cx, cy)) {
            npc = party.getPartyChar(party.getPartyMemberByCoordinates(cx, cy));
            if (npc != null) {
              Talk talk = Talk.readTalkResource(npc.getTalkFileName());
              if (talk != null) {
                gameTalkPanel = new GameTalk(party, npc,talk,journal, listener);
                changeState(GAME_STATE_TALK);
              } else {
                party.addLogText(npc.getName()+" has nothing to say.");
                DebugOutput.debugLog("Could not find resource file:"+npc.getTalkFileName());  
              }
            }
          }
        }
      } else {
        if (party.isCombat()) {
          party.addLogText(party.getActiveChar().getName()+" cannot talk while in combat!");
        } else {
          party.addLogText(party.getActiveChar().getName()+" cannot talk that far away!");
        }
      }
      map.setCursorMode(Map.CURSOR_MODE_DISABLE);      
    }
    
    /**
     * Locked door handling when used with key or pick lock
     * @param event Event
     * @param item Item with to use
     * @param cx cursor positions
     * @param cy cursor positions
     */
    private void keyEnterToLockedDoor(Event event, Item item, int cx, int cy) {
      if ((item.getType()==Item.TYPE_ITEM_KEY) &&
      (item.getKeyValue().equalsIgnoreCase(event.getLockDoorKeyName()))) {
        String param1 = event.getParam(1);
        String param2 = event.getParam(2);
        event.setEventCommand(Event.COMMAND_TYPE_QUICK_TRAVEL);
        event.setParam(0, param1);
        event.setParam(1, param2);
        event.setParam(2, "Door");
        party.addLogText(party.getActiveChar().getName()+" opened lock with "+item.getName());
        party.getActiveChar().inventoryRemoveItem(usedItemIndex);
        usedItemIndex = -1;
        SoundPlayer.playSound(SoundPlayer.SOUND_FILE_PICKLOCK);
      }
      if (item.getType()==Item.TYPE_ITEM_PICKLOCK) { 
        int tn = event.getLockDoorDifficulty();
        if (tn >= 500) {
          party.addLogText("This lock cannot be picked!");
        } else {
          int skillBonus = party.getActiveChar().getEffectiveSkill(Character.SKILL_LOCK_PICKING)+item.getBonusPickLock();
          int check =party.getActiveChar().skillBonusCheck(skillBonus, tn);
          if (check > Character.CHECK_FAIL) {
            String param1 = event.getParam(1);
            String param2 = event.getParam(2);
            event.setEventCommand(Event.COMMAND_TYPE_QUICK_TRAVEL);
            event.setParam(0, param1);
            event.setParam(1, param2);
            event.setParam(2, "Door");
            party.addLogText(party.getActiveChar().getName()+" picked the lock.");
            usedItemIndex = -1;
            SoundPlayer.playSound(SoundPlayer.SOUND_FILE_PICKLOCK);
          } else {
            check = party.getActiveChar().luckCheck(false);
            SoundPlayer.playSoundBySoundName("PICKLOCKFAIL");
            if (check == Character.CHECK_FAIL) {
              party.addLogText(party.getActiveChar().getName()+" failed opening the lock and broke "+item.getName());
              party.getActiveChar().inventoryRemoveItem(usedItemIndex);
              usedItemIndex = -1;
            } else {
              party.addLogText(party.getActiveChar().getName()+" failed opening the lock.");
            }
          }
        }
      }
      
    }
    
    /**
     * Handling for locked gate
     * @param event Event
     * @param item Item
     * @param cx cursor positions
     * @param cy cursor positions
     * @param index Event index which to remove
     */
    private void keyEnterToLockedGate(Event event, Item item, int cx, int cy, int index) {
      if ((item.getType()==Item.TYPE_ITEM_KEY) &&
      (item.getKeyValue().equalsIgnoreCase(event.getLockDoorKeyName()))) {
        party.addLogText(party.getActiveChar().getName()+" opened lock with "+item.getName());
        party.getActiveChar().inventoryRemoveItem(usedItemIndex);
        usedItemIndex = -1;
        SoundPlayer.playSound(SoundPlayer.SOUND_FILE_PICKLOCK);
        SoundPlayer.playSoundBySoundName(event.getEventSound());
        map.doOpenGate(event);
        map.removeEvent(index);
        map.forceMapFullRepaint();
      }
      if (item.getType()==Item.TYPE_ITEM_PICKLOCK) { 
        int tn = event.getLockDoorDifficulty();
        int skillBonus = party.getActiveChar().getEffectiveSkill(Character.SKILL_LOCK_PICKING)+item.getBonusPickLock();
        int check =party.getActiveChar().skillBonusCheck(skillBonus, tn);
        if (check > Character.CHECK_FAIL) {
          party.addLogText(party.getActiveChar().getName()+" picked the lock.");
          usedItemIndex = -1;
          SoundPlayer.playSound(SoundPlayer.SOUND_FILE_PICKLOCK);
          SoundPlayer.playSoundBySoundName(event.getEventSound());
          map.doOpenGate(event);
          map.removeEvent(index);
          map.forceMapFullRepaint();
        } else {
          check = party.getActiveChar().luckCheck(false);
          SoundPlayer.playSoundBySoundName("PICKLOCKFAIL");
          if (check == Character.CHECK_FAIL) {
            party.addLogText(party.getActiveChar().getName()+" failed opening the lock and broke "+item.getName());
            party.getActiveChar().inventoryRemoveItem(usedItemIndex);
            usedItemIndex = -1;
          } else {
            party.addLogText(party.getActiveChar().getName()+" failed opening the lock.");
          }
        }
      }
    }
    
    private void keyEnterInUseMode() {
      int cx = map.getCursorX();
      int cy = map.getCursorY();
      int dist = MapUtilities.getDistance(party.getActiveChar().getX(), 
          party.getActiveChar().getY(), cx, cy);
      if ((dist == 1) && (!party.isCombat())) {
        // Distance is okay
        Item item = party.getActiveChar().inventoryGetIndex(usedItemIndex);
        if (item != null) {
          int index = map.getEvent(cx, cy);
          Event event = map.getEventByIndex(index);
          if (event != null) {
            if (event.getEventCommand() == Event.COMMAND_TYPE_LOCKED_DOOR) {                  
              keyEnterToLockedDoor(event, item, cx, cy);
            }
            if (event.getEventCommand() == Event.COMMAND_TYPE_LOCKED_GATE) {                  
              keyEnterToLockedGate(event, item, cx, cy, index);
            }
            if (event.getEventCommand() == Event.COMMAND_TYPE_HOLE_TO_DIG) {
              keyEnterToDigHole(event, item, cx, cy, index);
            }
          }
        }
      } else {
        party.addLogText("Out of range!");
      }
      map.setCursorMode(Map.CURSOR_MODE_DISABLE);      
    }
    
    private void keyEnterToDigHole(Event event, Item item, int cx, int cy,
        int index) {
      if (item.getType()==Item.TYPE_ITEM_DIGGING_TOOL) {
        party.addLogText(party.getActiveChar().getName()+" dig a hole...");
        playingEvent = new GameEventHandler(event, null, cx, cy, party);
        if (!playingEvent.isPlayable()) {
          playingEvent = null;
        } else {
          DebugOutput.debugLog(party.getActiveChar().getName()+" dig a hole at "+cx+" "+cy);
          playingEvent.runScript();
          scriptHandlingAfterRun(index);
        }
      }      
    }

    private void keyEnterInAttackMode() {
      int fullAttack = Map.ATTACK_TYPE_FULL_ATTACK;
      if (map.getCursorMode()==Map.CURSOR_MODE_SINGLE_ATTACK) {
        fullAttack = Map.ATTACK_TYPE_SINGLE_ATTACK;
      }
      if (turnReady != TURN_MOVES_DONE) {
        Character chr = party.getActiveChar();
        if ((chr.getCurrentSP()==0) && (chr.getCurrentHP() < chr.getMaxHP()/2)) {
          party.addLogText(chr.getName()+" is too exhausted!");
        } else {
          int cx = map.getCursorX();
          int cy = map.getCursorY();
          int dist = MapUtilities.getDistance(chr.getX(), chr.getY(), cx, cy);
          if ((chr.getFirstAttack().isRangedAttack()) 
              && (dist <Map.MAX_RANGED_RANGE) &&
              (map.isClearShot(chr.getX(), chr.getY(), cx, cy))) {
            if (((dist==1) && (chr.getPerks().isPerkActive(Perks.PERK_POINT_BLANK_SHOT))) ||
            (dist > 1)){
              Character npc = map.getNPCbyIndex(map.getCharacterByCoordinates(cx, cy));
              if (npc != null) {
                if (npc.getHostilityLevel() == Character.HOSTILITY_LEVEL_AGGRESSIVE) {
                  int exp = map.doAttack(chr,npc,fullAttack);
                  if (exp > 0) {
                    party.shareExperience(exp);
                  }
                  int taskIndex = npc.getCurrentTaskIndex(party.getHours(), party.getMins());
                  CharTask task = npc.getTaskByIndex(taskIndex);
                  if ((task != null) && (task.getTask().equals(CharTask.TASK_ATTACK_PC))) {
                    // Do nothing
                  } else {
                    // Add player to killing list
                    npc.addTaskKillPartyMember(chr.getName());
                  }
                  party.setCombat(true);
                } else {
                  party.addLogText(npc.getName()+" is on your side!");
                }
              } else {
                party.addLogText("There is nothing to attack.");
              }
            } else {
              party.addLogText("Cannot shoot to close combat!");
            }
          }else if (chr.getFirstAttack().isThrowingAttackPossible() 
              && dist <Map.MAX_RANGED_RANGE && dist > 1 &&
              map.isClearShot(chr.getX(), chr.getY(), cx, cy)) {
              Character npc = map.getNPCbyIndex(map.getCharacterByCoordinates(cx, cy));
              if (npc != null) {
                if (npc.getHostilityLevel() == Character.HOSTILITY_LEVEL_AGGRESSIVE) {
                  int exp = map.doAttack(chr,npc,Map.ATTACK_TYPE_THROW_ATTACK);
                  if (exp > 0) {
                    party.shareExperience(exp);
                  }
                  int taskIndex = npc.getCurrentTaskIndex(party.getHours(), party.getMins());
                  CharTask task = npc.getTaskByIndex(taskIndex);
                  if ((task != null) && (task.getTask().equals(CharTask.TASK_ATTACK_PC))) {
                    // Do nothing
                  } else {
                    // Add player to killing list
                    npc.addTaskKillPartyMember(chr.getName());
                  }
                  party.setCombat(true);
                } else {
                  party.addLogText(npc.getName()+" is on your side!");
                }
              } else {
                party.addLogText("There is nothing to attack.");
              }
          } else
          if ((!chr.getFirstAttack().isRangedAttack()) && (dist ==1)) {
            Character npc = map.getNPCbyIndex(map.getCharacterByCoordinates(cx, cy));
            if (npc != null) {
              if (npc.getHostilityLevel() == Character.HOSTILITY_LEVEL_AGGRESSIVE) {
                int exp = map.doAttack(chr,npc,fullAttack);
                if (exp > 0) {
                  party.shareExperience(exp);
                }
                int taskIndex = npc.getCurrentTaskIndex(party.getHours(), party.getMins());
                CharTask task = npc.getTaskByIndex(taskIndex);
                if ((task != null) && (task.getTask().equals(CharTask.TASK_ATTACK_PC))) {
                    if (!task.getWayPointName().equalsIgnoreCase(chr.getName())) {
                      task.setWayPointName(chr.getName()); 
                    }                        
                } else {
                  npc.addTaskKillPartyMember(chr.getName());
                }

                party.setCombat(true);                    
              } else {
                party.addLogText(npc.getName()+" is on your side!");
              }
            } else {
              party.addLogText("There is nothing to attack.");
            }
          } else {
            party.addLogText("Out of range!");
          }
          map.setCursorMode(Map.CURSOR_MODE_DISABLE);
          playerMakesAMove();
        }
      } 
      
    }
    
    private void keyEnterInCastingMode() {
      if (turnReady != TURN_MOVES_DONE) {
        Character chr = party.getActiveChar();
        if ((chr.getCurrentSP()==0) && (chr.getCurrentHP() < chr.getMaxHP()/2)) {
          party.addLogText(chr.getName()+" is too exhausted!");
        } else {
          int cx = map.getCursorX();
          int cy = map.getCursorY();
          int dist = MapUtilities.getDistance(chr.getX(), chr.getY(), cx, cy);
          if ((dist <Map.MAX_RANGED_RANGE) &&
              (map.isClearShot(chr.getX(), chr.getY(), cx, cy))) {
            Character npc = map.getNPCbyIndex(map.getCharacterByCoordinates(cx, cy));
            if (npc != null) {
              if (castingSpell.getAttack()!=null) {
                if (npc.getHostilityLevel() == Character.HOSTILITY_LEVEL_AGGRESSIVE) {
                  int exp = map.doSpell(chr, npc, castingSpell,true);
                  chr.receiveNonLethalDamage(castingSpell.getCastingCost()+chr.getStaminaCostFromLoad());
                  if (chr.getCurrentSP()<chr.getMaxStamina()/2) {
                    map.addNewGraphEffect(chr.getX(), chr.getY(), Map.GRAPH_EFFECT_SWEAT);
                  }
                  if (exp > 0) {
                    chr.setExperience(chr.getExperience()+exp);
                  }
                  party.setCombat(true);
                } else {
                  party.addLogText(npc.getName()+" is on your side!");  
                }
              } else {
                int exp = map.doSpell(chr, npc, castingSpell,true);
                chr.setExperience(chr.getExperience()+exp);
                chr.receiveNonLethalDamage(castingSpell.getCastingCost()+chr.getStaminaCostFromLoad());
                if (chr.getCurrentSP()<chr.getMaxStamina()/2) {
                  map.addNewGraphEffect(chr.getX(), chr.getY(), Map.GRAPH_EFFECT_SWEAT);
                }

              }
            } else {
              npc = party.getPartyChar(party.getPartyMemberByCoordinates(cx, cy));
              if ((npc != null) && (castingSpell.getAttack()==null)) {
                int exp = map.doSpell(chr, npc, castingSpell,true);
                chr.setExperience(chr.getExperience()+exp);
                chr.receiveNonLethalDamage(castingSpell.getCastingCost()+chr.getStaminaCostFromLoad());
                if (chr.getCurrentSP()<chr.getMaxStamina()/2) {
                  map.addNewGraphEffect(chr.getX(), chr.getY(), Map.GRAPH_EFFECT_SWEAT);
                }

              } else {
                party.addLogText("There is no target for casting.");
              }
            }
          } else {
            party.addLogText("Out of range!");
          }
          map.setCursorMode(Map.CURSOR_MODE_DISABLE);
          playerMakesAMove();
        }
      } 
      
    }
    
    private void keyPressedInSpellPanel(int key) {
      if (key == KeyEvent.VK_ENTER) {
        actionCastSpell();
      }
      if (key == KeyEvent.VK_UP) {
        spellPanel.moveSelectionUp();
      }
      if (key == KeyEvent.VK_DOWN) {
        spellPanel.moveSelectionDown();
      }
    }
    
    private void keyPressedInGame(KeyEvent e) {
      int key = e.getKeyCode();
      if (playingEvent != null) {
        //Active event
        keyPressedInGamePlayingEvent(key);
      } else // End of active event
      if (!map.isCursorMode() && (searchPanel == null) && (spellPanel == null) &&
          (travelPanel == null)) {
        // Not cursor mode
        keyPressedInGameNotCursorMode(key);
      } else { // END of not cursor mode
        // Cursor mode
        if (map.isCursorMode()) {
          keyPressedInGameMoveCursor(key);
          if (key == KeyEvent.VK_ENTER) {
            if (map.getCursorMode() == Map.CURSOR_MODE_LOOK) {
              keyEnterInLookMode();
            } // END OF LOOK
            if (map.getCursorMode() == Map.CURSOR_MODE_TALK) {
              keyEnterInTalkMode();
            } // END OF TALK
            if (map.getCursorMode() == Map.CURSOR_MODE_USE) {
              keyEnterInUseMode();
            } // END OF USE
            if (map.getCursorMode() == Map.CURSOR_MODE_ATTACK) {
              keyEnterInAttackMode();
            } // END of Attack
            if (map.getCursorMode() == Map.CURSOR_MODE_SINGLE_ATTACK) {
              keyEnterInAttackMode();
            } // END of Attack
            if ((map.getCursorMode() == Map.CURSOR_MODE_CAST) && (castingSpell != null)) {
              keyEnterInCastingMode();
            } // END of Casting            
          } 
        } else {
          if (spellPanel != null) {
            keyPressedInSpellPanel(key);
          }
        }

      }
      if (travelPanel != null) {
        if (key == KeyEvent.VK_N) {
          actionTravelPanelNo();
        }
        if (key == KeyEvent.VK_Y) {
          actionTravelPanelYes();
        }
      }
      if (party.getActiveCharIndex()==0&&party.getActiveChar() != null &&
          party.getActiveChar().getLongName().equalsIgnoreCase("Kasvis Nakkimakkara") &&
          key == KeyEvent.VK_NUMPAD0 && party.getActiveChar().getLevel()<20) {
        party.getActiveChar().setExperience(party.getActiveChar().getExperience()+1000);
      }
      // Generic keys which are always pressable
      if (key == KeyEvent.VK_I && turnReady != TURN_MOVES_DONE) {        
        charSheetPanel = new GameCharacterSheet(map, party, party.getActiveCharIndex(),
            GameCharacterSheet.SELECTED_TAB_INVENTORY, listener);
        changeState(GAME_STATE_CHARACTER_SHEET);
      }
      if (key == KeyEvent.VK_J) {
        gameJournalPanel = new GameJournal(journal, listener);
        changeState(GAME_STATE_JOURNAL);
      }
      if ((key == KeyEvent.VK_F1) && (playingEvent == null) &&
          (travelPanel == null) && (spellPanel == null)) {
        gameHelpPanel = new GameHelp(listener);
        changeState(GAME_STATE_GAME_HELP);
      }
      if ((key == KeyEvent.VK_F2) && (debugMode)) {
        gameDebugPanel = new GameDebugMode(party, listener);
        changeState(GAME_STATE_DEBUGMODE);
      }
      if ((key == KeyEvent.VK_F3) && (debugMode)) {        
        MapUtilities.saveScreenShot(map.drawFullMap(party.isDay()));
      }
      if (key == KeyEvent.VK_F10) {
        BufferedImage image = getScreenShot();
        MapUtilities.saveScreenShot(image);
      }
      if ((key == KeyEvent.VK_ESCAPE) && (playingEvent == null)) {
        if (travelPanel != null) {
          actionTravelPanelNo();
        } else if (searchPanel != null) {
          gamePanels.remove(searchPanel);
          gamePanels.add(partyPanel);
          gamePanels.validate();
          searchPanel = null;
        } else if (spellPanel != null){
          actionCastSpellCancel();
        } else if  (map.isCursorMode()) {
          map.setCursorMode(Map.CURSOR_MODE_DISABLE);
        } else {
          if (party.isMainCharacterAlive()) {
            BufferedImage image = getScreenShot();
            try {
              GameMaps.saveCurrentMapAndParty(party, journal, map, image);
            } catch (IOException e1) {          
              e1.printStackTrace();
              System.out.print("Failing to save current game...");
            }
          }
          changeState(GAME_STATE_MAINMENU);
        }
      }
      if (key == KeyEvent.VK_L) {
        if (!map.isCursorMode()) {
          map.setCursorMode(Map.CURSOR_MODE_LOOK);
          Character chr = party.getActiveChar();
          map.setCursorX(chr.getX());
          map.setCursorY(chr.getY());
        } else {
          map.setCursorMode(Map.CURSOR_MODE_DISABLE);
        }
      }
      if (key == KeyEvent.VK_T) {
        if (!map.isCursorMode()) {
          map.setCursorMode(Map.CURSOR_MODE_TALK);
          Character chr = party.getActiveChar();
          map.setCursorX(chr.getX());
          map.setCursorY(chr.getY());
        } else {
          map.setCursorMode(Map.CURSOR_MODE_DISABLE);
        }
      }
      if (key == KeyEvent.VK_A) {
        if (!map.isCursorMode()) {
          Character chr = party.getActiveChar();
          if (!chr.isPacified()) {
            map.setCursorMode(Map.CURSOR_MODE_ATTACK);         
            map.setCursorX(chr.getX());
            map.setCursorY(chr.getY());
          } else {
            party.addLogText(chr.getName()+" is pacified and cannot attack!");
          }
        } else {
          if (map.getCursorMode() == Map.CURSOR_MODE_SINGLE_ATTACK) {
            map.setCursorMode(Map.CURSOR_MODE_ATTACK);
          } else {
            map.setCursorMode(Map.CURSOR_MODE_DISABLE);
          }
        }
      }
      if (key == KeyEvent.VK_Z) {
        if (!map.isCursorMode()) {
          Character chr = party.getActiveChar();
          if (!chr.isPacified()) {
            map.setCursorMode(Map.CURSOR_MODE_SINGLE_ATTACK);         
            map.setCursorX(chr.getX());
            map.setCursorY(chr.getY());
          } else {
            party.addLogText(chr.getName()+" is pacified and cannot attack!");
          }
        } else {
          if (map.getCursorMode() == Map.CURSOR_MODE_ATTACK) {
            map.setCursorMode(Map.CURSOR_MODE_SINGLE_ATTACK);
          } else {
            map.setCursorMode(Map.CURSOR_MODE_DISABLE);
          }
        }
      }
    }
    
    public void setActionListener(ActionListener listener) {
      this.listener = listener;
    }
    
    public void keyPressed(KeyEvent e) {
      switch (state) {
      case GAME_STATE_HERO_DOWN: {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE ||
            e.getKeyCode() == KeyEvent.VK_ENTER) {
          changeState(GAME_STATE_DEATH_SCREEN);
        }
          
        break;
      }
      case GAME_STATE_GAME: {
          try {
            drawingMapLock.acquire();
          } catch (InterruptedException e1) {
            //Just continue
          }
          keyPressedInGame(e);
          drawingMapLock.release();
          break; }
      case GAME_STATE_GAME_HELP: {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)  {
          if (gameHelpPanel != null) {
            gameHelpPanel = null;
            if (gamePanels != null) {
              changeState(GAME_STATE_GAME);
            } else {
              changeState(GAME_STATE_MAINMENU);
            }
          }
        }
      }
      default: {
        if (e.getKeyCode() == KeyEvent.VK_F10) {
          BufferedImage image = getScreenShot();
          MapUtilities.saveScreenShot(image);
        }
        break;
        }
      }
    }
  }
  
  class TWindowListener extends WindowAdapter{
    
    @Override
    public void windowOpened(WindowEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void windowIconified(WindowEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void windowDeiconified(WindowEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void windowDeactivated(WindowEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void windowClosing(WindowEvent e) {      
      closeGame();
    }
    
    @Override
    public void windowClosed(WindowEvent e) {
      // TODO Auto-generated method stub
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
      // TODO Auto-generated method stub
      
    } 
 }

  public void closeGame() {
    if (state == GAME_STATE_GAME && playingEvent == null && autoSaveOnExit) {
      DebugOutput.debugLog("Save game on exit...");
      BufferedImage image = getScreenShot();
      try {
        GameMaps.saveCurrentMapAndParty(party, journal, map, image);
      } catch (IOException e1) {          
        e1.printStackTrace();
        System.out.print("Failing to save current game...");
      }
    }
    System.exit(0);
  }

}
