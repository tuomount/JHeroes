package org.jheroes.game.storyscreen;

import org.jheroes.map.Party;

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
 * 
 * Story Screen class for creating sequence of story screens.
 *
 */
public abstract class StoryScreen {

  /**
   * Current story screen
   */
  private int state =0;
  
  
  /**
   * Party to use story variables
   */
  private Party party;
  

  /**
   * Constructor for Story screen
   * @param party Game Party
   */
  public StoryScreen(Party party) {
    this.party = party;
    this.state = 0;
  }
  
  /**
   * Get next story screen returns null if no more story screens available
   * @param index Story screen index
   * @return StoryScreenData or null
   */
  public abstract StoryScreenData getStoryScreen(int index);

  /**
   * Get First Story screen. Also resets state
   * @return StoryScreen
   */
  public StoryScreenData getFirstStoryScreen() {
    state = 0;
    return getStoryScreen(0);
  }

  /**
   * Get next Story screen. Also increases state
   * @return StoryScreen if available or null if all shown
   */
  public StoryScreenData getNextStoryScreen() {
    state++;
    return getStoryScreen(state);
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public Party getParty() {
    return party;
  }

  public void setParty(Party party) {
    this.party = party;
  }
}
