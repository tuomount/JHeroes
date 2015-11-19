package org.jheroes.mapeditor;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
 * Waypoint list for a character
 * 
 **/
public class MapWaypointsList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea waypointsList;
	private JTextArea eventHelp;
	
	private static final String helpText = "Waypoint: 0 Params\n\n"+
    "Sign:\n" +
    "Parameter 1: Sign text\n\n"+
    "Travel:\n" +
    "Parameter 1: Target map\n"+
    "Parameter 2: Waypoint name\n"+
    "Parameter 3: Travel confirm text#/res/image/image.png\n\n"+
    "QuickTravel:\n" +
    "Parameter 1: Target map\n"+
    "Parameter 2: Waypoint name\n"+
    "Parameter 3: Sound: Door, rope etc...\n\n"+
    "Locked Door:\n" +
    "Parameter 1: Key/Difficulty\n"+
    "Parameter 2: Target map\n"+
    "Parameter 3: Waypoint name\n\n"+
    "Clock:\n" +
    "Parameter 1: Sun/Clock\n\n"+
    "NPC Yell:\n" +
    "Parameter 1: NPC Name\n"+
    "Parameter 2: Text, # page chage\n"+
    "Parameter 3: Script\n\n"+
    "NPC Talk:\n" +
    "Parameter 1: NPC Name#[PC name whom to talk]\n"+
    "Parameter 2: Talk (wo .tlk)\n"+
    "Parameter 3: Script\n\n"+
    "PC Yell:\n" +
    "Parameter 1: PC Name/Empty for active#Override face\n"+
    "Parameter 2: Text, # page chage\n"+
    "Parameter 3: Script\n\n"+
    "Look Info:\n" +
    "Parameter 1: text\n\n"+
    "Modify Map:\n" +
    "Parameter 1: Tile index\n"+
    "Parameter 2: Graph effect number\n" +
    "              NONE = 0\n" +
    "              SWEAT = 12\n" +
    "              SPELL_POSITIVE = 47\n"+
    "              SPELL_POISON = 54\n"+
    "              SPELL_FLAME = 59\n"+
    "              SPELL_MINDAFFECTING = 85\n"+
    "              SPELL_CURSE = 91\n"+
	  "Parameter 3: Add/Remove/RemoveAll/JustEffect\n\n"+
	  "Encounter:\n" +
    "Parameter 1: NPC long names separated by #\n"+
    "Parameter 2: waypoint name\n"+
    "Parameter 3: Script\n\n"+
    "PC Talk:\n" +
    "Parameter 1: PC Name\n"+
    "Parameter 2: Talk\n"+
    "Parameter 3: Script\n\n"+
    "Conditional Travel:\n" +
    "Parameter 1: Travel confirm text can be empty\n#/res/image/image.png\n"+
    "Parameter 2: Waypoint # Sound\n"+
    "Parameter 3: Script\n\n"+
    "Locked Gate:\n" +
    "Parameter 1: Key/Difficulty\n"+
    "Parameter 2: Single/North\n"+
    "Parameter 3: Sound\n\n"+
    "Play sound:\n" +
    "Parameter 1: Sound name\n"+
    "Parameter 2: NN\\Loop\\Day\\Night\\Day#NN\\Night#NN\n\n"+
    "YESNO Question:\n" +
    "Parameter 1: Question\n"+
    "Parameter 2: set variabales on YES, story[N] = X,story[M] = Y\n"+
    "Parameter 3: set variabales on NO, story[N] = X,story[M] = Y\n\n"+
    "Hole to Dig:\n" +
    "Parameter 1: Reserved\n"+
    "Parameter 2: Text, # page chage\n"+
    "Parameter 3: Script\n\n"+
    "Conditional Map Travel:\n" +
    "Parameter 1: Travel confirm text can be empty\n#/res/image/image.png\n"+
    "Parameter 2: Waypoint # Target map\n"+
    "Parameter 3: Script\n\n";
    

	
	public MapWaypointsList(String[] wps) {
		super();
    this.setLayout(new BorderLayout());
    eventHelp = new JTextArea();
    eventHelp.setText(helpText);
    eventHelp.setWrapStyleWord(true);
    eventHelp.setCaretPosition(0);
    
    JScrollPane scroll = new JScrollPane(eventHelp);
    this.add(scroll,BorderLayout.CENTER);
    
		waypointsList = new JTextArea();
		StringBuilder sbText = new StringBuilder(50);
		if (wps != null) {
			for (int i=0;i<wps.length;i++) {
			  	sbText.append(wps[i]);
			  	if (i<wps.length-1) {
			  		sbText.append(",\n");
			  	}
			}
		}
    waypointsList.setText(sbText.toString());
    scroll = new JScrollPane(waypointsList);
    this.add(scroll,BorderLayout.WEST);
    
	}
	
}
