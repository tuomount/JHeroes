package org.jheroes.game.storyscreen;

import org.jheroes.gui.GuiStatics;
import org.jheroes.map.Party;
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
 * When Player lose without actually dying then this story is shown
 *
 */
public class LoseStory extends StoryScreen {

  /**
   * Constructor for lose story
   * @param characterTileset
   * @param party
   */
  public LoseStory(Tileset characterTileset, Party party) {
    super(party);
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
      screenData.setImage(GuiStatics.loadImage(Screens.CROWN_IMAGE));
      screenData.setText("Crownings begun without you. Vicster Arborshate was" +
      		" crowned as new empire. He immediately commanded soldiers to march" +
      		" Shadow woods and get rid of all rebels. Fighting against rebels" +
      		" took weeks and both sides took heavy casualties. Eventually rebels were gone.");
      break;
    }
    case 1: {
      screenData.setImage(GuiStatics.loadImage(Screens.CROWN_IMAGE));
      screenData.setText("Since you missed the crownings you were not nominated as" +
      "hero or you did not get any fame. You never made your fortune...");
      break;
    }
    default: {
      return null;
    }
    }
    return screenData;
  }

}
