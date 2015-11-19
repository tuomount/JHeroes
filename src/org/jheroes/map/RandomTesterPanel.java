package org.jheroes.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jheroes.tileset.Tile;


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
 * Panel for PRNG test
 * 
 */
public class RandomTesterPanel extends JPanel implements ActionListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private BufferedImage myScreen;
  private JButton continueBtn;
  private int count = 0;
  
  public RandomTesterPanel() {
    myScreen = new BufferedImage(500, 500, BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D g = myScreen.createGraphics();
    g.setColor(Color.black);
    g.drawRect(0, 0, 499, 499);
    
    continueBtn = new JButton("Continue");
    continueBtn.setActionCommand("continue");
    count = 0;
    this.add(continueBtn);
    continueBtn.addActionListener(this);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.drawImage(myScreen, 20, 35, null);
  }

  @Override
  public void actionPerformed(ActionEvent e) {   
    count++;
    continueBtn.setText("Continue - "+count);
   for (int i=0;i<50000;i++) {
     int x = DiceGenerator.getRandom(499);
     int y = DiceGenerator.getRandom(499);
     try {
       int c = myScreen.getRGB(x, y);
       c = Tile.getRed(c);
       c=c+20;
       if (c>255) {c=255;}
       c = Tile.getColor(c, c, c);
       myScreen.setRGB(x, y, c);
     } catch (ArrayIndexOutOfBoundsException ex) {
       
      System.out.println(ex.getMessage()+"X:"+String.valueOf(x)+" Y:"+String.valueOf(y));
    }
   }
   this.repaint();
    
    
  }
  
}
