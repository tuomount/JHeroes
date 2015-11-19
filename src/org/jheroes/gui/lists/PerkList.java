package org.jheroes.gui.lists;


import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

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
 * List which shows perks
 * 
 */
public class PerkList extends JActionList {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String[] perks;
  
  /**
   * Perk list for selectable perks
   * @param perksAvailable
   * @param perksNotAvailable
   */
  public PerkList(ArrayList<String> perksAvailable, ArrayList<String> perksNotAvailable) {
    super();
    perks = new String[perksAvailable.size()+perksNotAvailable.size()];
    int i=0;
    for (String str : perksAvailable) {
      perks[i] = str;
      i++;
    }
    for (String str : perksNotAvailable) {
      perks[i] = "!"+str;
      i++;
    }
    this.setListData(perks);
    ListCellRenderer renderer = new PerkListCellRenderer();
    this.setCellRenderer(renderer);
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.setAutoscrolls(true);
    this.setBackground(Color.black);
    this.setForeground(GuiStatics.COLOR_GOLD);
  }

  /**
   * Update selectable and all perks which are not available
   * @param perksAvailable
   * @param perksNotAvailable
   */
  public void updatePerkList(ArrayList<String> perksAvailable, ArrayList<String> perksNotAvailable) {
    perks = new String[perksAvailable.size()+perksNotAvailable.size()];
    int i=0;
    for (String str : perksAvailable) {
      perks[i] = str;
      i++;
    }
    for (String str : perksNotAvailable) {
      perks[i] = "!"+str;
      i++;
    }
    this.setListData(perks);    
  }
  
  /**
   * Constructor for Active Perks
   * @param activePerks
   */
  public PerkList(String[] activePerks) {
    super();
    this.setListData(activePerks);
    if (activePerks.length==0) {
      this.setVisibleRowCount(14);
    }
    ListCellRenderer renderer = new PerkListCellRenderer();
    this.setCellRenderer(renderer);
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.setAutoscrolls(true);
    this.setBackground(Color.black);
    this.setForeground(GuiStatics.COLOR_GOLD);
  }

}

class PerkListCellRenderer implements ListCellRenderer {

  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

  public Component getListCellRendererComponent(JList list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
        isSelected, cellHasFocus);
    renderer.setFont(GuiStatics.FONT_SMALL);
    if (isSelected) {
      renderer.setForeground(GuiStatics.COLOR_BLACK);
      renderer.setBackground(GuiStatics.COLOR_CYAN);
      if (value instanceof String) {
        String str = (String) value;
        if (str.startsWith("!")) {
          renderer.setForeground(GuiStatics.COLOR_GRAY);          
          renderer.setBackground(GuiStatics.COLOR_DARK_CYAN);
          renderer.setText(str.substring(1, str.length()));
        }
      }
    } else {
      renderer.setForeground(GuiStatics.COLOR_GOLD);
      renderer.setBackground(GuiStatics.COLOR_BLACK);
      if (value instanceof String) {
        String str = (String) value;
        if (str.startsWith("!")) {
          renderer.setForeground(GuiStatics.COLOR_GRAY);
          renderer.setText(str.substring(1, str.length()));          
        }
      }
      
    }
    return renderer;
  }
}
