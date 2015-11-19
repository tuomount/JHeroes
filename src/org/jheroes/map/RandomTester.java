package org.jheroes.map;

import javax.swing.JFrame;

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
 * Pseudo Random Number Generator test class
 * 
 */
public class RandomTester extends JFrame {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public RandomTester() {
        add(new RandomTesterPanel());
        setTitle("Random Tester");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args) {
        new RandomTester();
    }
}