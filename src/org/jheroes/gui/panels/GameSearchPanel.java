package org.jheroes.gui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.lists.ItemList;
import org.jheroes.map.item.Item;



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
 * When player searches items and found multiple then this panel is shown
 * 
 */
public class GameSearchPanel extends GamePanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private ItemList list;
  private Item[] itemArr;

  /**
   * Constructor of Game Search Panel
   * @param characterName String
   * @param items found
   * @param listener ActionListener
   */
  public GameSearchPanel(String characterName, ArrayList<Item> items, 
      ActionListener listener) {
    super(false);
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    this.setLayout(new BorderLayout());
    GameLabel label = new GameLabel(characterName+" found");
    this.add(label,BorderLayout.NORTH);
    itemArr = new Item[items.size()];
    for (int i=0;i<items.size();i++) {
      itemArr[i] = items.get(i);
    }
    list = new ItemList(itemArr);
    JScrollPane scroll = new JScrollPane(list);
    this.add(scroll,BorderLayout.CENTER);
    GamePanel buttonPane = new GamePanel(true);
    buttonPane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    buttonPane.setLayout(new BorderLayout());
    GameButton btn = new GameButton("Take", ActionCommands.GAME_SEARCH_TAKE);
    btn.addActionListener(listener);
    buttonPane.add(btn,BorderLayout.WEST);
    btn = new GameButton("Take all", ActionCommands.GAME_SEARCH_TAKE_ALL);
    btn.addActionListener(listener);
    buttonPane.add(btn,BorderLayout.EAST);
    this.add(buttonPane,BorderLayout.SOUTH);
  }
  
  public ItemList getList() {
    return list;
  }
  public Item[] getAllItems() {
    return itemArr;
  }
}
