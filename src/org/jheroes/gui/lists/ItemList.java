package org.jheroes.gui.lists;


import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.jheroes.gui.GuiStatics;
import org.jheroes.map.item.Item;
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
 * List which shows Items
 * 
 */
public class ItemList extends JActionList {

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor for inventory Item List
   */
  public ItemList() {
    super();    
    ListCellRenderer renderer = new ItemListCellRenderer();
    this.setCellRenderer(renderer);
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.setAutoscrolls(true);
    this.setBackground(Color.black);
    this.setForeground(GuiStatics.COLOR_GOLD);    
  }
  
  /**
   * Constructor for found item list
   * @param foundItems
   */
  public ItemList(Item[] foundItems) {    
    super(foundItems);    
    ListCellRenderer renderer = new FoundItemListCellRenderer();
    this.setCellRenderer(renderer);
    this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    this.setAutoscrolls(true);
    this.setBackground(Color.black);
    this.setForeground(GuiStatics.COLOR_GOLD);    
  }
}

class ItemListCellRenderer implements ListCellRenderer {

  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
  
  public Component getListCellRendererComponent(JList list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
        isSelected, cellHasFocus);
    renderer.setFont(GuiStatics.FONT_NORMAL);
    if (value instanceof Item) {
      Item item = (Item) value;
      Tile tile = GuiStatics.getItemsTileset().getTile(item.getTileNumber());
      renderer.setIcon(tile.getTileAsIcon());
      if (isSelected) {
        renderer.setForeground(GuiStatics.COLOR_BLACK);
        renderer.setBackground(GuiStatics.COLOR_CYAN);
      } else {
        renderer.setForeground(GuiStatics.COLOR_GOLD);
        renderer.setBackground(GuiStatics.COLOR_BLACK);
      }
    }    
    return renderer;
  }
}

class FoundItemListCellRenderer implements ListCellRenderer {

  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
  
  public Component getListCellRendererComponent(JList list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
        isSelected, cellHasFocus);
    renderer.setFont(GuiStatics.FONT_SMALL);
    if (value instanceof Item) {
      Item item = (Item) value;
      Tile tile = GuiStatics.getItemsTileset().getTile(item.getTileNumber());
      renderer.setIcon(tile.getTileAsIcon());
      renderer.setText(item.getItemNameInGame()+" "+item.getWeightAsString()+" kg");
      if (isSelected) {
        renderer.setForeground(GuiStatics.COLOR_BLACK);
        renderer.setBackground(GuiStatics.COLOR_CYAN);
      } else {
        renderer.setForeground(GuiStatics.COLOR_GOLD);
        renderer.setBackground(GuiStatics.COLOR_BLACK);
      }
    }    
    return renderer;
  }
}
