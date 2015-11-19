package org.jheroes.game.storyscreen;

import org.jheroes.gui.GuiStatics;
import org.jheroes.tileset.Tileset;


/**
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
 * Start story class for showing introduction to game
 *
 */
public class StartStory extends StoryScreen {


  /**
   * Constructor for start story, but since this is start there is no party.
   * So party variable in story is null.
   * @param characterTileset
   */
  public StartStory(Tileset characterTileset) {
    super(null);
  }

  /** 
   * Story Screen Data
   */
  private StoryScreenData screenData;
  

  @Override
  public StoryScreenData getStoryScreen(int index) {
    if (screenData == null) {
      screenData = new StoryScreenData();
    }
    switch (index) {
    case 0: {
      screenData.setImage(GuiStatics.loadImage(Screens.START_SCREEN_1));
      screenData.setText("Empire of Hawks Haven is in great danger. Emperor is sick" +
         " and there is no heir for the throne. Rebels are also gathering to overthrow" +
         " empire and his reign. Dangerous beasts and monsters are living in the Empire...");
      break;
    }
    case 1: {
      screenData.setImage(GuiStatics.loadImage(Screens.CROWN_IMAGE));
      screenData.setText("Fear not since there is new hero to proctect the crown. " +
      "You are about the graduate from Nerisella Songsteel School of Adventurers. " +
      "You just need to decide what you have learned while studying. " +
      "Who know what kind of adventurers are waiting for you...");
      break;
    }
    default: {
      return null;
    }
    }
    return screenData;
  }

  
}
