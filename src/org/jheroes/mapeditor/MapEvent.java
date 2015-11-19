package org.jheroes.mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jheroes.map.Event;

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
 * Editor for map events
 * 
 **/
public class MapEvent extends JDialog implements ActionListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int diagSizeX = 800;
  private int diagSizeY = 350;
  
  private JComboBox cbEventType;
  private JComboBox cbEventCommand;
  private boolean newEvent;
  private JPanel panelSettings;
  
  private JTextField nameText;
  private JTextField param1Text;
  private JComboBox param2Text;
  private JTextField param3Text;
  
  private int cx,cy,x1,y1,x2,y2;
  
  private boolean okClicked;
  private MapWaypointsList mapWaypoints;
    
  public MapEvent(JFrame parent,int cx,int cy, int x1,int y1, int x2, int y2, 
		   boolean newEvent, String[] waypoints) {
    super(parent,"Event Editor",true);
    this.newEvent = newEvent;
    okClicked = false;
    if (this.newEvent) {
      this.setTitle("Creating a map event");
    } else {
      this.setTitle("Editing event");
    }
    this.cx = cx;
    this.cy = cy;
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
    Point location = parent.getLocationOnScreen();
    location.x = location.x;
    location.y = location.y+parent.getHeight()/4;
    this.setLocation(location);
    this.setSize(diagSizeX, diagSizeY);
    this.setMaximumSize(new Dimension(diagSizeX, diagSizeY));
    this.setMinimumSize(new Dimension(diagSizeX, diagSizeY));
    this.setPreferredSize(new Dimension(diagSizeX, diagSizeY));
    this.setResizable(false);      
        
    panelSettings = new JPanel();
    panelSettings.setLayout(new GridLayout(8,2));
    panelSettings.setMaximumSize(new Dimension(diagSizeX/2, diagSizeY));
    panelSettings.setPreferredSize(new Dimension(diagSizeX/2, diagSizeY));
    

    JLabel nameLabel = new JLabel("Event Name:");
    panelSettings.add(nameLabel);
    nameText = new JTextField("Waypoint");
    panelSettings.add(nameText);

    
    JLabel labelType = new JLabel("Type:");
    panelSettings.add(labelType);
    cbEventType = new JComboBox(Event.EVENT_TYPE_STRINGS);
    panelSettings.add(cbEventType);
    JLabel labelCoord1 = new JLabel("Coordinates X1:"+String.valueOf(this.x1)+
                             " Y1:"+String.valueOf(this.y1));
    panelSettings.add(labelCoord1);
    JLabel labelCoord2 = new JLabel("Coordinates X2:"+String.valueOf(this.x2)+
        " Y2:"+String.valueOf(this.y2));
    panelSettings.add(labelCoord2);


    JLabel labelCommand = new JLabel("Command:");
    panelSettings.add(labelCommand);
    cbEventCommand = new JComboBox(Event.EVENT_COMMAND_STRINGS);
    panelSettings.add(cbEventCommand);
    
    JLabel param1Label = new JLabel("Parameter 1:");
    panelSettings.add(param1Label);
    param1Text = new JTextField("");
    panelSettings.add(param1Text);
    JLabel param2Label = new JLabel("Parameter 2:");
    panelSettings.add(param2Label);
    if (waypoints != null) {
      param2Text = new JComboBox(waypoints);
    } else {
      param2Text = new JComboBox();
    }
    param2Text.setEditable(true);
    panelSettings.add(param2Text);
    JLabel param3Label = new JLabel("Parameter 3:");
    panelSettings.add(param3Label);
    param3Text = new JTextField("");
    panelSettings.add(param3Text);
     
    JButton btnOk = new JButton("Ok");
    btnOk.setActionCommand("OK");
    btnOk.addActionListener(this);
    panelSettings.add(btnOk);
    JButton btnCancel = new JButton("Cancel");
    btnCancel.setActionCommand("Cancel");
    btnCancel.addActionListener(this);
    panelSettings.add(btnCancel);
    JPanel base = new JPanel();
    base.setLayout(new BorderLayout());
    getContentPane().add(base);
    base.add(panelSettings,BorderLayout.WEST);
    mapWaypoints = new MapWaypointsList(waypoints);
    base.add(mapWaypoints,BorderLayout.CENTER);
  }
  
  @Override
  public void setVisible(boolean b) {
	super.setVisible(b);
	if (mapWaypoints != null) {
	  mapWaypoints.setVisible(b);
	}
  }

public void setValues(int type, int command, String name, String param1,
                        String param2, String param3) {
    cbEventType.setSelectedIndex(type);
    cbEventCommand.setSelectedIndex(command);
    nameText.setText(name);
    param1Text.setText(param1);
    param2Text.setSelectedItem(param2);
    param3Text.setText(param3);
  }

  /**
   * Get Event param by given index
   * @param index 0-2
   * @return
   */
  public String getParam(int index) {
    String result = "";
    switch (index) {
    case 0: result = param1Text.getText(); break;
    case 1: result = (String) param2Text.getSelectedItem(); break;
    case 2: result = param3Text.getText(); break;
    }
    return result;
  }

  /**
   * Get event name
   */
  public String getName() {
    return nameText.getText();
  }
  
  /**
   * Get event command
   * @return
   */
  public byte getCommand() {
    return (byte) cbEventCommand.getSelectedIndex();
  }

  public int getX1() {
    if ((cbEventType.getSelectedIndex()==0) && (newEvent)) {
      return cx;
    } else {
      return x1;
    }    
  }
  public int getX2() {
    if (cbEventType.getSelectedIndex()==0) {
      return x1;
    } else {
      return x2;
    }
  }
  public int getY1() {
    if ((cbEventType.getSelectedIndex()==0) && (newEvent)) {
      return cy;
    } else {
      return y1;
    }    
  }
  public int getY2() {
    if (cbEventType.getSelectedIndex()==0) {
      return y1;
    } else {
      return y2;
    }
  }
  /**
   * Is OK button clicked
   * @return boolean
   */
  public boolean isOkClicked() {
    return okClicked;
  }
  
  @Override
  public void actionPerformed(ActionEvent arg0) {
    if (arg0.getActionCommand().equals("OK")) {
      okClicked = true;
      this.setVisible(false);
    }
    if (arg0.getActionCommand().equals("Cancel")) {
      okClicked = false;
      this.setVisible(false);
    }
    
  }
}
