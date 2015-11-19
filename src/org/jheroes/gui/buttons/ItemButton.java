package org.jheroes.gui.buttons;


import javax.swing.JButton;

import org.jheroes.gui.GuiStatics;
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
 * Button having item picture as button and text where item is placed.
 * 
 */
public class ItemButton extends JButton {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Item item;
  private String place;
  
  /**
   * Create Item button. If Item is null then empty button which is disabled
   * @param item
   * @param actionCommand Button's action command
   */
  public ItemButton(Item item,String place, String actionCommand) {
    super();
    this.place = place;
    setBackground(GuiStatics.COLOR_BLACK);
    setForeground(GuiStatics.COLOR_GOLD);
    setFont(GuiStatics.FONT_NORMAL);
    setItem(item);
    setActionCommand(actionCommand);
  }
  
  /**
   * Change item button's item
   * @param item, can be null. Null is for empty button which is disabled
   */
  public void setItem(Item item) {
    this.item = item;
    if (this.item != null) {
      if (this.place.length()+this.item.getItemNameInGame().length() > 18) {
        setText(this.item.getItemNameInGame());
      } else {
        setText(this.place+":"+this.item.getItemNameInGame());
      }
      setIcon(GuiStatics.getItemsTileset().getTile(this.item.getTileNumber()).getTileAsIcon());
      setEnabled(true);
    } else {
      setText(this.place+": Empty");
      setEnabled(false);
      setIcon(null);
    }
  }
  
  /**
   * Get button's item
   * @return Item
   */
  public Item getItem() {
    return item;
  }
}
