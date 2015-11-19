package org.jheroes.mapeditor;

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

import org.jheroes.map.character.Faces;

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
 * Character face panel for character editor
 * 
 **/
public class CharacterFaceSelection extends JDialog implements ActionListener, AdjustmentListener, MouseListener, MouseMotionListener {
    
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private BufferedImage originalImage;
  private BufferedImage screen;
  private ImagePanel imagePane;
  private int selectImage;
  
  public CharacterFaceSelection(JFrame parent) {
    super(parent, "Select Character Face", true);
    originalImage = Faces.getAllFaces();
    screen = originalImage;
    this.setSize(screen.getWidth()+100, screen.getHeight()+100);
    this.setResizable(false);
    imagePane = new ImagePanel(screen);
    imagePane.addMouseListener(this);
    imagePane.addMouseMotionListener(this);
    getContentPane().add(imagePane);
    selectImage = 0;
    // Bottom panel
    JPanel buttonPane = new JPanel();
    JButton button = new JButton("Cancel"); 
    buttonPane.add(button);
    button.addActionListener(this);
    getContentPane().add(buttonPane, BorderLayout.SOUTH);

    // Right panel
    JPanel rightPane = new JPanel();
    getContentPane().add(rightPane, BorderLayout.EAST);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
  }
    
    
  public void actionPerformed(ActionEvent e) {
    setVisible(false); 
    dispose(); 
  }


  
  

  @Override
  public void mouseClicked(MouseEvent arg0) {        
    int mouseX = arg0.getPoint().x / Faces.WIDTH;
    int mouseY = arg0.getPoint().y / Faces.HEIGHT;
    selectImage = Faces.getFaceIndex(mouseX, mouseY);
    setVisible(false);    
  }

  /**
   * Get selected image index
   * @return
   */
  public int getSelected() {
    return selectImage;
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
    int mouseX = arg0.getPoint().x / Faces.WIDTH;
    int mouseY = arg0.getPoint().y / Faces.HEIGHT;
    screen = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), 
        originalImage.getType());
    screen.createGraphics().drawImage(originalImage,0,0,null);
    screen.getGraphics().drawRect(mouseX*Faces.WIDTH, mouseY*Faces.HEIGHT, Faces.WIDTH, Faces.HEIGHT);
    update(getGraphics());
    
  }


  @Override
  public void adjustmentValueChanged(AdjustmentEvent arg0) {
    // TODO Auto-generated method stub
    
  }
  
  
}
