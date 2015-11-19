package org.jheroes.gui.lists;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListModel;

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
 * JList with double click function and enter key function
 * @author tuomount
 *
 */
public abstract class JActionList extends JList {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private ActionListener al;
  private String actionCommand;

  
  public JActionList() {
    super();
  }

  public JActionList(ListModel dataModel) {
    super(dataModel);
  }

  public JActionList(Object[] listData) {
    super(listData);
  }

  public JActionList(Vector<?> listData) {
    super(listData);
  }

  /**
   * Add new ActionListener
   * @param ActionListener
   */
  public void addActionListener(ActionListener al){
    this.al = al;
    if (al != null && actionCommand != null) {
      this.addMouseListener(new ListMouseListener(al, actionCommand));
      this.addKeyListener(new ListKeyboardListener(al, actionCommand));
    }
  }
  
  /**
   * Get current Action Command
   * @return
   */
  public String getActionCommand() {
    return actionCommand;
  }

  /**
   * Get current Action command
   * @param actionCommand
   */
  public void setActionCommand(String actionCommand) {
    this.actionCommand = actionCommand;
    if (al != null && actionCommand != null) {
      this.addMouseListener(new ListMouseListener(al, actionCommand));
      this.addKeyListener(new ListKeyboardListener(al, actionCommand));
    }
  }
}

class ListKeyboardListener implements KeyListener {
  
  private ActionListener al;
  private String actionCommand;
  public ListKeyboardListener(ActionListener al, String actionCommand) {
    this.al = al;
    this.actionCommand = actionCommand;
  }
  
  @Override
  public void keyPressed(KeyEvent arg0) {
    if (arg0.getKeyCode() == KeyEvent.VK_ENTER && al != null &&
        actionCommand != null) {
      ActionEvent action = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand);
      al.actionPerformed(action);
    }
    
  }

  @Override
  public void keyReleased(KeyEvent arg0) {
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
  }

}

class ListMouseListener implements MouseListener {
  
  private ActionListener al;
  private String actionCommand;
  public ListMouseListener(ActionListener al, String actionCommand) {
    this.al = al;
    this.actionCommand = actionCommand;
  }
  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2 && al != null &&
        actionCommand != null)
    {
      ActionEvent action = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand);
      al.actionPerformed(action);
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

}
