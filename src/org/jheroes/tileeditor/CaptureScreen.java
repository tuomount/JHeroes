package org.jheroes.tileeditor;

import java.awt.BorderLayout;
import java.awt.Graphics;
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
import javax.swing.JScrollBar;

import org.jheroes.tileset.Tile;
import org.jheroes.tileset.TileInfo;

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
 * Capture screen dialog for Tile Editor
 * 
 **/
public class CaptureScreen extends JDialog implements ActionListener, AdjustmentListener, MouseListener, MouseMotionListener {
    
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public static final int CAPTURE_TYPE_SINGLE = TileInfo.TILE_SERIES_BASIC_TYPE_SINGLE_OBJECT;
  public static final int CAPTURE_TYPE_BASE = TileInfo.TILE_SERIES_BASIC_TYPE_BASE;
  public static final int CAPTURE_TYPE_WALL = TileInfo.TILE_SERIES_BASIC_TYPE_WALL;
  public static final int CAPTURE_TYPE_OBJECT2X2 = TileInfo.TILE_SERIES_BASIC_TYPE_OBJECT_2X2;
  public static final int CAPTURE_TYPE_OBJECT2X1 = TileInfo.TILE_SERIES_BASIC_TYPE_OBJECT_2X1;
  public static final int CAPTURE_TYPE_OBJECT1X2 = TileInfo.TILE_SERIES_BASIC_TYPE_OBJECT_1X2;
  public static final int CAPTURE_TYPE_OBJECT1X3 = TileInfo.TILE_SERIES_BASIC_TYPE_OBJECT_1X3;
  public static final int CAPTURE_TYPE_OBJECT3X1 = TileInfo.TILE_SERIES_BASIC_TYPE_OBJECT_3X1;
  public static final int CAPTURE_TYPE_OBJECT2X3 = TileInfo.TILE_SERIES_BASIC_TYPE_OBJECT_2X3;
  public static final int CAPTURE_TYPE_OBJECT3X3 = TileInfo.TILE_SERIES_BASIC_TYPE_OBJECT_3X3;
  public static final int CAPTURE_TYPE_NORMAL_CHARACTER = 10;
  public static final int CAPTURE_TYPE_SMALL_CHARACTER = 11;
  
  public static final String[] CAPTURE_TYPE_STRINGS = {"Single object","Base type","Wall","Object 2x2",
    "Object 2x1","Object 1x2", "Object 3x1","Object 1x3","Object 2x3","Object 3x3",
    "Normal Character", "Small Character"};
  
  private BufferedImage originalImage;
  private BufferedImage screen;
  private JScrollBar scX;
  private JScrollBar scY;
  private ImagePanel imagePane;
  private BufferedImage selectImage;
  private int captureType;
  private int captureSizeX;
  private int captureSizeY;
  
  public CaptureScreen(JFrame parent, BufferedImage input, int captType) {
    super(parent, "Capture single tile", true);
    if ((captType >= 0) && (captType <= 11)) {
      captureType = captType;
    } else {
      captureType = CAPTURE_TYPE_SINGLE;
    }
    setCaptureSize();
    originalImage = input;
    screen = originalImage;
    int extentY = screen.getHeight();
    if (extentY > 18*Tile.MAX_HEIGHT) {
      extentY = 18*Tile.MAX_HEIGHT;
    }
    int extentX = screen.getWidth();
    if (extentX > 24*Tile.MAX_WIDTH) {
      extentX = 24*Tile.MAX_WIDTH;
    }
    this.setSize(800, 700);
    this.setResizable(false);
    imagePane = new ImagePanel(screen);
    imagePane.addMouseListener(this);
    imagePane.addMouseMotionListener(this);
    getContentPane().add(imagePane);
    scY = new JScrollBar(JScrollBar.VERTICAL);
    scY.setValues(0, extentY, 0, screen.getHeight());
    scY.setUnitIncrement(Tile.MAX_HEIGHT);
    scY.addAdjustmentListener(this);
    scX = new JScrollBar(JScrollBar.HORIZONTAL);
    scX.setValues(0, extentX, 0, screen.getWidth());
    scX.setUnitIncrement(Tile.MAX_WIDTH);
    scX.addAdjustmentListener(this);
    screen = originalImage.getSubimage(scX.getValue(), scY.getValue(), scX.getVisibleAmount(), scY.getVisibleAmount());
    selectImage = null;
    // Bottom panel
    JPanel buttonPane = new JPanel();
    JButton button = new JButton("Cancel"); 
    buttonPane.add(scX,BorderLayout.NORTH);
    buttonPane.add(button);
    button.addActionListener(this);
    getContentPane().add(buttonPane, BorderLayout.SOUTH);

    // Right panel
    JPanel rightPane = new JPanel();
    rightPane.add(scY, BorderLayout.EAST);
    getContentPane().add(rightPane, BorderLayout.EAST);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
  }
   
  private void setCaptureSize() {
    switch (captureType) {
    case CAPTURE_TYPE_SINGLE: captureSizeX = 1; captureSizeY = 1; break;
    case CAPTURE_TYPE_OBJECT1X2: captureSizeX = 1; captureSizeY = 2; break;
    case CAPTURE_TYPE_OBJECT1X3: captureSizeX = 1; captureSizeY = 3; break;
    case CAPTURE_TYPE_OBJECT2X1: captureSizeX = 2; captureSizeY = 1; break;
    case CAPTURE_TYPE_OBJECT2X2: captureSizeX = 2; captureSizeY = 2; break;
    case CAPTURE_TYPE_OBJECT2X3: captureSizeX = 2; captureSizeY = 3; break;
    case CAPTURE_TYPE_OBJECT3X1: captureSizeX = 3; captureSizeY = 1; break;
    case CAPTURE_TYPE_OBJECT3X3: captureSizeX = 3; captureSizeY = 3; break;
    case CAPTURE_TYPE_NORMAL_CHARACTER: captureSizeX = 3; captureSizeY = 8; break;
    case CAPTURE_TYPE_SMALL_CHARACTER: captureSizeX = 3; captureSizeY = 4; break;
    case CAPTURE_TYPE_WALL: captureSizeX = 3; captureSizeY = 2; break;
    case CAPTURE_TYPE_BASE: captureSizeX = 3; captureSizeY = 3; break;
    default: captureSizeX = 1; captureSizeY = 1; break;
    }
  }
    
  public void actionPerformed(ActionEvent e) {
    setVisible(false); 
    dispose(); 
  }


  
  @Override
  public void adjustmentValueChanged(AdjustmentEvent arg0) {
    screen = originalImage.getSubimage(scX.getValue(), scY.getValue(), scX.getVisibleAmount(), scY.getVisibleAmount());
    update(getGraphics());
  }

  

  @Override
  public void mouseClicked(MouseEvent arg0) {        
    int mouseX = arg0.getPoint().x / (Tile.MAX_WIDTH);
    int mouseY = arg0.getPoint().y / (Tile.MAX_HEIGHT);
    selectImage = originalImage.getSubimage(mouseX*Tile.MAX_WIDTH,
        mouseY*Tile.MAX_HEIGHT, Tile.MAX_WIDTH*captureSizeX, 
        Tile.MAX_HEIGHT*captureSizeY);
    setVisible(false);    
  }

  /**
   * Get selected image, return null if no image selected.
   * @return
   */
  public BufferedImage getSelected() {
    if (selectImage != null) {
      return selectImage;
    } else {
      return null;
    }
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
    int mouseX = arg0.getPoint().x / (Tile.MAX_WIDTH);
    int mouseY = arg0.getPoint().y / (Tile.MAX_HEIGHT);
    screen = new BufferedImage(scX.getVisibleAmount(), scY.getVisibleAmount(), 
                                originalImage.getType());
    screen.createGraphics().drawImage(originalImage.getSubimage(scX.getValue(), 
                                      scY.getValue(), scX.getVisibleAmount(), 
                                      scY.getVisibleAmount()), 0, 0, null);    
    screen.getGraphics().drawRect(mouseX*Tile.MAX_WIDTH,
        mouseY*Tile.MAX_HEIGHT, Tile.MAX_WIDTH*captureSizeX,
        Tile.MAX_HEIGHT*captureSizeY);
    update(getGraphics());
    
  }
  
  
}
