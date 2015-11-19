package org.jheroes.mapeditor;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

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
 * Number spinner UI component
 * 
 **/
public class NumberSpinner extends JSpinner {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private SpinnerNumberModel spinModel;
  
  public NumberSpinner(int value, int min, int max, int step) {
    super();
    spinModel = new SpinnerNumberModel(value, min, max, step);
    this.setModel(spinModel);
  }
  
  public int getSpinValue() {
    Integer i = (Integer) spinModel.getValue();
    return i.intValue();
  }
  
  public void setSpinValue(int value) {
    Integer i = new Integer(value);
    spinModel.setValue(i);
  }
}
