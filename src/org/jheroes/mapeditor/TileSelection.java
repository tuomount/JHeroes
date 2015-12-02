package org.jheroes.mapeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jheroes.map.character.Faces;
import org.jheroes.tileset.Tile;
import org.jheroes.tileset.Tileset;

/**
 * JHeroes CRPG Engine and Game
 * Copyright (C) 2015  Tuomo Untinen
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
 * Tile selection panel for Map editor
 * 
 **/
public class TileSelection extends JDialog implements ActionListener, AdjustmentListener, MouseListener, MouseMotionListener {
    
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private BufferedImage originalImage;
  private BufferedImage screen;
  private ImagePanel imagePane;
  private int startTile;
  private int index;
  private Tileset tileset;
  
  /**
   * Constuctor fot Tile Selection dialog
   * @param parent Parent dialog
   * @param tileset Tileset used to draw tiles
   * @param currentIndex current tile index
   */
  public TileSelection(JFrame parent,Tileset tileset, int currentIndex) {
    super(parent, "Select Tile", true);
    this.tileset = tileset;
    index = currentIndex;
    startTile = index-200;
    if (startTile < 0) {
      startTile = 0;
    }
    originalImage = new BufferedImage(20*Tile.MAX_WIDTH,20*Tile.MAX_HEIGHT,
                        BufferedImage.TYPE_4BYTE_ABGR);
    drawTiles();
    screen = originalImage;
    this.setSize(screen.getWidth()+100, screen.getHeight()+100);
    this.setResizable(false);
    imagePane = new ImagePanel(screen);
    imagePane.addMouseListener(this);
    imagePane.addMouseMotionListener(this);
    getContentPane().add(imagePane);
    // Bottom panel
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new BorderLayout());
    JButton button = new JButton("Cancel");
    button.setActionCommand("Cancel");
    button.addActionListener(this);
    buttonPane.add(button,BorderLayout.CENTER);
    button = new JButton("-100");
    button.setActionCommand("-100");
    button.addActionListener(this);
    buttonPane.add(button,BorderLayout.WEST);
    button = new JButton("+100");
    button.setActionCommand("+100");
    button.addActionListener(this);
    buttonPane.add(button,BorderLayout.EAST);
    getContentPane().add(buttonPane, BorderLayout.SOUTH);

    // Right panel
    JPanel rightPane = new JPanel();
    getContentPane().add(rightPane, BorderLayout.EAST);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
  }
    

  /**
   * Draw tiles to originalImage
   */
  private void drawTiles() {
    Graphics2D g = (Graphics2D) originalImage.getGraphics();
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, originalImage.getWidth(), originalImage.getHeight());
    int k=startTile;
    for (int i=0;i<20;i++) {
      for (int j=0;j<20;j++) {
        Tile tile = tileset.getTile(k);
        if (tile != null) {
          tile.putFast(g, j*Tile.MAX_WIDTH, i*Tile.MAX_HEIGHT);
          k++;
          if (k > tileset.size()) {
            return;
          }
        } else {
          return;
        }
      }
    }
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equalsIgnoreCase("cancel")) {    
      setVisible(false); 
      dispose();
    }
    if (e.getActionCommand().equalsIgnoreCase("-100")) {
      if (index > 0) {
        index = index -100;
        if (index < 0) {
          index = 0;
        }
        startTile = index;
        if (startTile < 0) {
          startTile = 0;
        }
        drawTiles();
        update(getGraphics());
      }
    }
    if (e.getActionCommand().equalsIgnoreCase("+100")) {
      if (index+400 < tileset.size()) {
        index = index +100;
        if (index > tileset.size()) {
          index = tileset.size()-1;
        }
        startTile = index;
        if (startTile < 0) {
          startTile = 0;
        }
        drawTiles();
        update(getGraphics());
      }
      
    }
  }


  
  

  @Override
  public void mouseClicked(MouseEvent arg0) {        
    int mouseX = arg0.getPoint().x / Tile.MAX_WIDTH;
    int mouseY = arg0.getPoint().y / Tile.MAX_HEIGHT;
    int selected = mouseY*20+mouseX+startTile;
    if ((selected >= 0) && (selected < tileset.size())) {
      index = selected;
    } else {
      index = 0;
    }
    setVisible(false);
  }

  /**
   * Get selected tile index
   * @return
   */
  public int getSelected() {
    return index;
  }


  @Override
  public void update(Graphics g) {
    imagePane.UpdateImage(screen);
    super.update(g);    
  }


  @Override
  public void mouseEntered(MouseEvent e) {
    // No need to do anything
    
  }


  @Override
  public void mouseExited(MouseEvent e) {
    // No need to do anything    
  }


  @Override
  public void mousePressed(MouseEvent e) {
    // No need to do anything    
  }


  @Override
  public void mouseReleased(MouseEvent e) {
    // No need to do anything    
  }


  @Override
  public void mouseDragged(MouseEvent arg0) {
    // No need to do anything    
    
  }


  @Override
  public void mouseMoved(MouseEvent arg0) {
    int mouseX = arg0.getPoint().x / Tile.MAX_WIDTH;
    int mouseY = arg0.getPoint().y / Tile.MAX_HEIGHT;
    screen = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), 
        originalImage.getType());
    screen.createGraphics().drawImage(originalImage,0,0,null);
    screen.getGraphics().drawRect(mouseX*Tile.MAX_WIDTH, mouseY*Tile.MAX_HEIGHT,
                                  Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
    update(getGraphics());
    
  }


  @Override
  public void adjustmentValueChanged(AdjustmentEvent arg0) {
    // TODO Auto-generated method stub
    
  }
  
  
}
