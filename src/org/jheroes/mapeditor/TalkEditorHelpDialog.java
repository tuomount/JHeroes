package org.jheroes.mapeditor;

import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

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
 * Help dialog for talk Editor
 * 
 **/
public class TalkEditorHelpDialog extends JDialog {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public TalkEditorHelpDialog(JFrame parent) {
    super(parent);
    JTextArea helpText = new JTextArea();
    StringBuilder sbText = new StringBuilder(50);
    helpText.setEditable(false);
    helpText.setWrapStyleWord(true);
    helpText.setLineWrap(true);
    sbText.append("Special words:\n");
    sbText.append("<PLAYER> : PC's short name\n");
    sbText.append("<PLAYERLONG> : PC's full name\n");
    sbText.append("<NPC> : NPC's short name\n");
    sbText.append("<NPCLONG> : NPC's full name\n");
    sbText.append("<TIME> : Current time\n");
    sbText.append("<DATE> : Current Date\n");
    sbText.append("<I am/We are> : I am/We are\n");
    sbText.append("<I am/we are> : I am/we are\n");
    sbText.append("<Deadline> : today/tomorrow/in a...\n");
    Point location = parent.getLocationOnScreen();
      location.x = location.x+parent.getWidth()+20;
      location.y = location.y;
      this.setLocation(location);
      this.setSize(200, 550);
      this.setResizable(false);
      this.setTitle("Talk Editor Help");
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    helpText.setText(sbText.toString());
    this.add(helpText);
    this.setVisible(true);
  }
}
