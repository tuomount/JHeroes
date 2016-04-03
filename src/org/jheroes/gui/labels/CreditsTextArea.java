package org.jheroes.gui.labels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * JHeroes CRPG Engine and Game
 * Copyright (C) 2016  Tuomo Untinen
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
 * Extends GameTextArea, allows clicking to make scrolling go faster.
 * 
 */

public class CreditsTextArea extends GameTextArea {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor for Credits Text Area
   * @param text Text to display
   */
  public CreditsTextArea(String text) {
    super(text);
    this.addMouseListener(new CreditMouseListener());
  }
  
  private class CreditMouseListener extends MouseAdapter {

    
    
    @Override
    public void mouseClicked(MouseEvent e) {
      getNextLine();
      getNextLine();
      getNextLine();
      getNextLine();
      getNextLine();
    }

    
    
  }
  
}

