package org.jheroes.gui.buttons;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolTip;
import javax.swing.border.EtchedBorder;

import org.jheroes.gui.GuiStatics;

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
 * Button which has image and no text
 * 
 */
public class ImageGameButton extends JButton {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private BufferedImage notPressedImage;
  private BufferedImage pressedImage;
  private boolean border;

  /**
   * Game button which has only image
   * @param notPressedImage BufferedImage
   * @param pressedImage BufferedImage
   * @param border Booolean
   * @param actionCommand String
   */
  public ImageGameButton(BufferedImage notPressedImage, 
                         BufferedImage pressedImage, boolean border,
                         String actionCommand) {
    super();
    if ((pressedImage == null) || (notPressedImage == null)) {
      pressedImage = GuiStatics.IMAGE_ARROW_LEFT_PRESSED;
      notPressedImage = GuiStatics.IMAGE_ARROW_LEFT;
    }
    ImageIcon icon = new ImageIcon(notPressedImage, "");
    this.setIcon(icon);
    this.setDisabledIcon(icon);
    this.setNotPressedImage(notPressedImage);
    this.setPressedImage(pressedImage);
    this.setActionCommand(actionCommand);
    this.setBorder(border);
    if (border) {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    } else {
      this.setBorder(BorderFactory.createEmptyBorder());

    }
/*    Dimension size = this.getPreferredSize();
    size.width = notPressedImage.getWidth()-4;
    size.height = notPressedImage.getHeight()-2;
    this.setMinimumSize(size);
    this.setPreferredSize(size);*/    
  }

  
  
  
  @Override
  protected void paintComponent(Graphics g) {
    int x = getWidth()/2;
    int y = getHeight()/2;
    if (this.isEnabled()) {
      if (this.getModel().isPressed()) {
        x = x -getPressedImage().getWidth()/2;
        y = y -getPressedImage().getHeight()/2;
        if(isBorder()) {
          this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
          g.drawImage(getPressedImage(), x+2,y+2, null);        
        } else {
          g.drawImage(getPressedImage(), x,y, null);
        }
      } else {
        x = x -getNotPressedImage().getWidth()/2;
        y = y -getNotPressedImage().getHeight()/2;
        if(isBorder()) {
          this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
          g.drawImage(getNotPressedImage(), x+2, y+2, null);
        } else {
          g.drawImage(getNotPressedImage(), x, y, null);
        }
      }
    } else {
      x = x -getPressedImage().getWidth()/2;
      y = y -getPressedImage().getHeight()/2;
      if(isBorder()) {
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
        g.drawImage(getPressedImage(), x+2,y+2, null);        
      } else {
        g.drawImage(getPressedImage(), x,y, null);
      }
     
    }
    
  }

  @Override
  public JToolTip createToolTip()
  {
      JToolTip toolTip = super.createToolTip();
      toolTip.setForeground(GuiStatics.COLOR_GOLD);
      toolTip.setBackground(GuiStatics.COLOR_BLACK);
      toolTip.setBorder(BorderFactory.createLineBorder(GuiStatics.COLOR_GOLD_DARK));
      return toolTip;
  }

  public void setNotPressedImage(BufferedImage notPressedImage) {
    this.notPressedImage = notPressedImage;
  }


  public BufferedImage getNotPressedImage() {
    return notPressedImage;
  }


  public void setPressedImage(BufferedImage pressedImage) {
    this.pressedImage = pressedImage;
  }


  public BufferedImage getPressedImage() {
    return pressedImage;
  }


  public void setBorder(boolean border) {
    this.border = border;
  }


  public boolean isBorder() {
    return border;
  }
  
}
