package org.jheroes.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jheroes.tileset.Tileset;


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
 * Static class which has predefined colors, gradients etc
 * 
 */
public class GuiStatics {
  
  // Colors
  public final static Color COLOR_GOLD = new Color(255,216,0);
  public final static Color COLOR_GOLD_DARK = new Color(213,185,0);
  public final static Color COLOR_BROWNISH_BACKGROUND = new Color(224,115,22);
  public final static Color COLOR_DARK_BROWNISH = new Color(118,80,6);
  public final static Color COLOR_BROWNISH = new Color(167,113,8);
  public final static Color COLOR_BLACK = new Color(0,0,0);
  public final static Color COLOR_WHITE = new Color(255,255,255);
  public final static Color COLOR_GRAY_BLUE= new Color(50,50,70);
  public final static Color COLOR_DARK_GRAY_BLUE= new Color(30,30,50);
  public final static Color COLOR_GRAY_BACKGROUND= new Color(50,50,50);
  public final static Color COLOR_GRAY= new Color(120,120,120);
  public final static Color COLOR_GREEN= new Color(50,255,50);
  public final static Color COLOR_DARK_GREEN= new Color(0,50,0);
  public final static Color COLOR_CYAN= new Color(50,255,255);
  public final static Color COLOR_DARK_CYAN= new Color(0,50,50);
  public final static Color COLOR_BLUE= new Color(50,50,255);
  public final static Color COLOR_DARK_BLUE= new Color(0,0,50);
  public final static Color COLOR_RED= new Color(255,50,50);
  public final static Color COLOR_DARK_RED= new Color(50,0,0);
  
  // Fonts
  public final static Font FONT_SMALL = new Font("monospaced",Font.PLAIN,12);
  public final static Font FONT_NORMAL = new Font("monospaced",Font.BOLD,14);
  public final static Font FONT_BIG = new Font("monospaced",Font.BOLD,28);

  
  

  // Gradients
  public final static int GRADIENT_COLOR_INVISIBLE = -1;
  public final static int GRADIENT_COLOR_BROWN = 0;
  public final static int GRADIENT_COLOR_BLUE = 1;
  public final static int GRADIENT_COLOR_GREEN = 2;
  public final static int GRADIENT_COLOR_CYAN = 3;
  public final static int GRADIENT_COLOR_RED = 4;
  
  // Images  
  public final static BufferedImage IMAGE_BIG_ARROW_LEFT = loadImage(
               GuiStatics.class.getResource("/res/images/arrowLeft.png"));
  public final static BufferedImage IMAGE_BIG_ARROW_LEFT_PRESSED = loadImage(
      GuiStatics.class.getResource("/res/images/arrowLeft_press.png"));
  public final static BufferedImage IMAGE_BIG_ARROW_RIGHT = loadImage(
      GuiStatics.class.getResource("/res/images/arrowRight.png"));
  public final static BufferedImage IMAGE_BIG_ARROW_RIGHT_PRESSED = loadImage(
  GuiStatics.class.getResource("/res/images/arrowRight_press.png"));
  public final static BufferedImage IMAGE_SCROLL_BAR_THUMB = loadImage(
  GuiStatics.class.getResource("/res/images/thumb.png"));
  public final static BufferedImage IMAGE_SCROLL_BAR_THUMB2 = loadImage(
  GuiStatics.class.getResource("/res/images/thumb2.png"));

  
  public final static BufferedImage IMAGE_ARROW_LEFT = loadImage(
      GuiStatics.class.getResource("/res/images/SmallArrowLeft.png"));
  public final static BufferedImage IMAGE_ARROW_LEFT_PRESSED = loadImage(
  GuiStatics.class.getResource("/res/images/SmallArrowLeft_press.png"));
  public final static BufferedImage IMAGE_ARROW_RIGHT = loadImage(
  GuiStatics.class.getResource("/res/images/SmallArrowRight.png"));
  public final static BufferedImage IMAGE_ARROW_RIGHT_PRESSED = loadImage(
  GuiStatics.class.getResource("/res/images/SmallArrowRight_press.png"));

public final static BufferedImage IMAGE_ARROW_UP = loadImage(
      GuiStatics.class.getResource("/res/images/arrowUp.png"));
public final static BufferedImage IMAGE_ARROW_UP_PRESSED = loadImage(
GuiStatics.class.getResource("/res/images/arrowUp_press.png"));
public final static BufferedImage IMAGE_ARROW_DOWN = loadImage(
GuiStatics.class.getResource("/res/images/arrowDown.png"));
public final static BufferedImage IMAGE_ARROW_DOWN_PRESSED = loadImage(
GuiStatics.class.getResource("/res/images/arrowDown_press.png"));

public final static BufferedImage IMAGE_SLIDER_THUMB = loadImage(
GuiStatics.class.getResource("/res/images/slider_thumb.png"));

public final static BufferedImage IMAGE_SCROLL_ARROW_LEFT = loadImage(
    GuiStatics.class.getResource("/res/images/scrollArrowLeft.png"));
public final static BufferedImage IMAGE_SCROLL_ARROW_LEFT_PRESSED = loadImage(
GuiStatics.class.getResource("/res/images/scrollArrowLeft_press.png"));
public final static BufferedImage IMAGE_SCROLL_ARROW_RIGHT = loadImage(
GuiStatics.class.getResource("/res/images/scrollArrowRight.png"));
public final static BufferedImage IMAGE_SCROLL_ARROW_RIGHT_PRESSED = loadImage(
GuiStatics.class.getResource("/res/images/scrollArrowRight_press.png"));


  
  public final static BufferedImage IMAGE_LEADER_ACTIVE = loadImage(
  GuiStatics.class.getResource("/res/images/leader.png"));
  public final static BufferedImage IMAGE_LEADER_SOLO = loadImage(
  GuiStatics.class.getResource("/res/images/leader_solo.png"));
  public final static BufferedImage IMAGE_LEADER_PRESSED = loadImage(
  GuiStatics.class.getResource("/res/images/leader_press.png"));
  public final static BufferedImage IMAGE_LEADER_NOT_ACTIVE = loadImage(
  GuiStatics.class.getResource("/res/images/leader_not_active.png"));

  public final static BufferedImage IMAGE_NO_FACE = loadImage(
  GuiStatics.class.getResource("/res/images/noface.png"));

  public final static BufferedImage IMAGE_LEVEL_UP = loadImage(
  GuiStatics.class.getResource("/res/images/levelup.png"));

  public final static BufferedImage IMAGE_MENU_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/menuBtn.png"));
  public final static BufferedImage IMAGE_MENU_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/menuBtn_press.png"));
  public final static BufferedImage IMAGE_CAST_SPELL_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/cast_spell.png"));
  public final static BufferedImage IMAGE_CAST_SPELL_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/cast_spell_press.png"));
  public final static BufferedImage IMAGE_EVALUATE_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/evaluate.png"));
  public final static BufferedImage IMAGE_EVALUATE_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/evaluate_press.png"));
  public final static BufferedImage IMAGE_FULL_ATTACK_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/full_attack.png"));
  public final static BufferedImage IMAGE_FULL_ATTACK_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/full_attack_press.png"));
  public final static BufferedImage IMAGE_HELP_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/help_btn.png"));
  public final static BufferedImage IMAGE_HELP_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/help_btn_press.png"));
  public final static BufferedImage IMAGE_INVENTORY_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/inventory.png"));
  public final static BufferedImage IMAGE_INVENTORY_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/inventory_press.png"));
  public final static BufferedImage IMAGE_LOOK_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/look.png"));
  public final static BufferedImage IMAGE_LOOK_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/look_press.png"));
  public final static BufferedImage IMAGE_REST_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/rest_hour.png"));
  public final static BufferedImage IMAGE_REST_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/rest_hour_press.png"));
  public final static BufferedImage IMAGE_SEARCH_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/search.png"));
  public final static BufferedImage IMAGE_SEARCH_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/search_press.png"));
  public final static BufferedImage IMAGE_SINGLE_ATTACK_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/single_attack.png"));
  public final static BufferedImage IMAGE_SINGLE_ATTACK_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/single_attack_press.png"));
  public final static BufferedImage IMAGE_WAIT_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/wait_turn.png"));
  public final static BufferedImage IMAGE_WAIT_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/wait_turn_press.png"));
  public final static BufferedImage IMAGE_JOURNAL_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/journal.png"));
  public final static BufferedImage IMAGE_JOURNAL_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/journal_press.png"));
  public final static BufferedImage IMAGE_PICKUP_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/pickup.png"));
  public final static BufferedImage IMAGE_PICKUP_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/pickup_press.png"));
  public final static BufferedImage IMAGE_TALK_BUTTON = loadImage(
  GuiStatics.class.getResource("/res/images/talk.png"));
  public final static BufferedImage IMAGE_TALK_BUTTON_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/talk_press.png"));

  public final static BufferedImage IMAGE_PLUS = loadImage(
  GuiStatics.class.getResource("/res/images/plus.png"));
  public final static BufferedImage IMAGE_PLUS_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/plus_press.png"));

  public final static BufferedImage IMAGE_INFO = loadImage(
  GuiStatics.class.getResource("/res/images/info.png"));

  public final static BufferedImage IMAGE_INFO_PRESS = loadImage(
  GuiStatics.class.getResource("/res/images/info_press.png"));

  public final static BufferedImage IMAGE_QUEST_ACTIVE = loadImage(
  GuiStatics.class.getResource("/res/images/quest_active.png"));
  public final static BufferedImage IMAGE_QUEST_DONE = loadImage(
  GuiStatics.class.getResource("/res/images/quest_done.png"));
  public final static BufferedImage IMAGE_QUEST_FAILED = loadImage(
  GuiStatics.class.getResource("/res/images/quest_failed.png"));
  
  public final static BufferedImage IMAGE_HERO_DOWN = loadImage(
  GuiStatics.class.getResource("/res/images/herodown.png"));

  public final static BufferedImage IMAGE_MAINMENU = loadImage(
  GuiStatics.class.getResource("/res/images/main.png"));

  public static BufferedImage loadImage(URL urlToImage) {
    try {
      return ImageIO.read(urlToImage);
    } catch (IOException e) {
      System.err.print(urlToImage.toString()+" not found!");
      return null;
    } 
  }
  
  private static Tileset charactersTileset;
  private static Tileset itemsTileset;
  private static Tileset effectsTileset;
  
  /**
   * Get Characters tileset as static
   * @return Characters tileset
   */
  public static Tileset getCharacterTileset() {
    if (charactersTileset == null) {
      charactersTileset = new Tileset();
    BufferedInputStream bis = new BufferedInputStream(
           GuiStatics.class.getResourceAsStream(Tileset.TILESET_CHARACTERS));
    DataInputStream dis = new DataInputStream(bis);
    charactersTileset.loadTileSet(dis);
    return charactersTileset;
    } else {
      return charactersTileset;
    }
  }

  /**
   * Get items tileset as static
   * @return items tileset
   */
  public static Tileset getItemsTileset() {
    if (itemsTileset == null) {
      itemsTileset = new Tileset();
      BufferedInputStream bis = new BufferedInputStream(
           GuiStatics.class.getResourceAsStream(Tileset.TILESET_ITEMS));
           DataInputStream dis = new DataInputStream(bis);
    itemsTileset.loadTileSet(dis);
    return itemsTileset;
    } else {
      return itemsTileset;
    }
  }

  /**
   * Get effects tileset as static
   * @return effects tileset
   */
  public static Tileset getEffectsTileset() {
    if (effectsTileset == null) {
      effectsTileset = new Tileset();
      BufferedInputStream bis = new BufferedInputStream(
           GuiStatics.class.getResourceAsStream(Tileset.TILESET_EFFECTS));
      DataInputStream dis = new DataInputStream(bis);
    effectsTileset.loadTileSet(dis);
    return effectsTileset;
    } else {
      return effectsTileset;
    }
  }

  
}
