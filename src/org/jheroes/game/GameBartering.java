package org.jheroes.game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.lists.ItemList;
import org.jheroes.gui.panels.GamePanel;
import org.jheroes.map.character.Character;
import org.jheroes.map.item.Item;
import org.jheroes.soundplayer.SoundPlayer;



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
 * Bartering screen for JHeroes engine
 * 
 */ 
public class GameBartering extends GamePanel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private ItemList npcItems;
  private ItemList pcItems;
  private Character npc;
  private Character pc;
  private GameTextArea npcItemDesc;
  private GameTextArea pcItemDesc;
  private GameLabel npcMoney;
  private GameLabel pcMoney;
  private boolean isPartyMember;
  
  public GameBartering(Character activeChar, Character npc,boolean isPartyMember,
      ActionListener listener) {
     super(true);
     this.pc = activeChar;
     this.npc = npc;
     this.isPartyMember = isPartyMember;
     this.setLayout(new BorderLayout());
     this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
     GameLabel label;
     if (this.isPartyMember) {
       label = new GameLabel("Trading");
     } else {
       label = new GameLabel("Bartering");
     }
     this.add(label,BorderLayout.NORTH);
     GameButton exitBtn = new GameButton("Exit", ActionCommands.BARTERING_EXIT);
     exitBtn.addActionListener(listener);
     this.add(exitBtn,BorderLayout.SOUTH);
     
     GamePanel centerPanel = new GamePanel(true);
     centerPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
     centerPanel.setLayout(new GridLayout(1,0));
     
     centerPanel.add(createCharacterBarteringPanel(true,listener));
     centerPanel.add(createCharacterBarteringPanel(false,listener)); 
     this.add(centerPanel,BorderLayout.CENTER);
     updateItemList();
     pcItems.setSelectedIndex(0);
     npcItems.setSelectedIndex(0);
  }
   
  private void updateItemList() {
    pcItems.setListData(new Vector<Item>());
    if (pc.inventorySize()>0) {
      Item[] items = new Item[pc.inventorySize()];
      for (int i=0;i<pc.inventorySize();i++) {
        items[i] = pc.inventoryGetIndex(i);
      }
      pcItems.setListData(items);
    }
    pcMoney.setText("Coppers:"+pc.getMoney());
    npcItems.setListData(new Vector<Item>());
    if (npc.inventorySize()>0) {
      Item[] items = new Item[npc.inventorySize()];
      for (int i=0;i<npc.inventorySize();i++) {
        items[i] = npc.inventoryGetIndex(i);
      }
      npcItems.setListData(items);
    }      
    npcMoney.setText("Coppers:"+npc.getMoney());
  }
  
  private GamePanel createCharacterBarteringPanel(boolean isPlayer, ActionListener listener) {
    GamePanel result = new GamePanel(true);
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    result.setLayout(new BorderLayout());
    
    GamePanel topPane = new GamePanel(true);
    topPane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    topPane.setLayout(new GridLayout(0, 1));
    String name;
    if (isPlayer) {
      name = pc.getLongName();
    } else {
      name = npc.getLongName();
    }
    GameLabel label = new GameLabel(name);
    topPane.add(label);
    if (isPlayer) {
      pcMoney = new GameLabel("Coppers:");
      topPane.add(pcMoney);
      if (isPartyMember) {
        GameButton btn = new GameButton("Give 1 copper", ActionCommands.BARTERING_GIVE_1_COPPER);
        btn.addActionListener(listener);
        topPane.add(btn);
        btn = new GameButton("Give 10 coppers", ActionCommands.BARTERING_GIVE_10_COPPER);
        btn.addActionListener(listener);
        topPane.add(btn);
      }
    } else {
      npcMoney = new GameLabel("Coppers:");
      topPane.add(npcMoney);
      if (isPartyMember) {
        GameButton btn = new GameButton("Take 1 copper", ActionCommands.BARTERING_TAKE_1_COPPER);
        btn.addActionListener(listener);
        topPane.add(btn);
        btn = new GameButton("Take 10 coppers", ActionCommands.BARTERING_TAKE_10_COPPER);
        btn.addActionListener(listener);
        topPane.add(btn);
      }
    }
    result.add(topPane,BorderLayout.NORTH);
    GameButton btn;
    if (isPlayer) {
      if (isPartyMember) {
        btn = new GameButton("Give", ActionCommands.BARTERING_GIVE);
        btn.addActionListener(listener);        
      } else {
        btn = new GameButton("Sell", ActionCommands.BARTERING_SELL);
        btn.addActionListener(listener);
      }
    } else {
      if (isPartyMember) {
        btn = new GameButton("Take", ActionCommands.BARTERING_TAKE);
        btn.addActionListener(listener);        
      } else {
        btn = new GameButton("Buy", ActionCommands.BARTERING_BUY);
        btn.addActionListener(listener);
      }
    }
    result.add(btn,BorderLayout.SOUTH);
    
    GamePanel center = new GamePanel(false);
    center.setLayout(new BorderLayout());
    center.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    if (isPlayer) {
      pcItems = new ItemList();
      JScrollPane scrollingList = new JScrollPane(pcItems);
      center.add(scrollingList,BorderLayout.CENTER);
      pcItemDesc = new GameTextArea();
      pcItemDesc.setRows(13);
      pcItemDesc.setWrapStyleWord(true);
      pcItemDesc.setLineWrap(true);
      pcItemDesc.setEditable(false);
      center.add(pcItemDesc,BorderLayout.SOUTH);
    } else {
      npcItems = new ItemList();
      JScrollPane scrollingList = new JScrollPane(npcItems);
      scrollingList.setForeground(GuiStatics.COLOR_BROWNISH);
      scrollingList.setBackground(GuiStatics.COLOR_DARK_BROWNISH);
      center.add(scrollingList,BorderLayout.CENTER);
      npcItemDesc = new GameTextArea();
      npcItemDesc.setRows(13);
      npcItemDesc.setWrapStyleWord(true);
      npcItemDesc.setLineWrap(true);
      npcItemDesc.setEditable(false);
      center.add(npcItemDesc,BorderLayout.SOUTH);      
    }
    result.add(center);
    return result;
  }
  
  public void handleActions(ActionEvent arg0) {
    if (arg0.getActionCommand().equals(ActionCommands.GENERIC_TIMER)) {
      Item item = (Item) pcItems.getSelectedValue();
      if (item != null) {
        pcItemDesc.setText(item.getItemInformationPrice(Item.PRICE_PLAYER_SELLING,
            pc.getEffectiveSkill(Character.SKILL_BARTERING),
            npc.getEffectiveSkill(Character.SKILL_BARTERING)));
      }
      item = (Item) npcItems.getSelectedValue();
      if (item != null) {
        npcItemDesc.setText(item.getItemInformationPrice(Item.PRICE_PLAYER_BUYING,
            pc.getEffectiveSkill(Character.SKILL_BARTERING),
            npc.getEffectiveSkill(Character.SKILL_BARTERING)));
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.BARTERING_SELL)) {
      Item item = (Item) pcItems.getSelectedValue();
      if ((item != null) && (item.getType() != Item.TYPE_ITEM_QUEST)) {
        int price = item.getSellPrice(pc.getEffectiveSkill(Character.SKILL_BARTERING),
            npc.getEffectiveSkill(Character.SKILL_BARTERING));
        if (npc.getMoney()>=price) {
          SoundPlayer.playEnemySoundsByName("bartering");
          npc.inventoryPickUpItemForce(item);
          pc.skillCheck(Character.SKILL_BARTERING, 50+npc.getEffectiveSkill(Character.SKILL_BARTERING));
          pc.inventoryRemoveItem(pcItems.getSelectedIndex());
          pc.setMoney(pc.getMoney()+price);
          npc.setMoney(npc.getMoney()-price);
          updateItemList();
        }
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.BARTERING_GIVE_1_COPPER)) {
      if (pc.getMoney() > 0) {
        pc.setMoney(pc.getMoney()-1);
        npc.setMoney(npc.getMoney()+1);
        updateItemList();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.BARTERING_GIVE_10_COPPER)) {
      if (pc.getMoney() > 9) {
        pc.setMoney(pc.getMoney()-10);
        npc.setMoney(npc.getMoney()+10);
        updateItemList();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.BARTERING_TAKE_1_COPPER)) {
      if (npc.getMoney() > 0) {
        pc.setMoney(pc.getMoney()+1);
        npc.setMoney(npc.getMoney()-1);
        updateItemList();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.BARTERING_TAKE_10_COPPER)) {
      if (npc.getMoney() > 9) {
        pc.setMoney(pc.getMoney()+10);
        npc.setMoney(npc.getMoney()-10);
        updateItemList();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.BARTERING_GIVE)) {
      Item item = (Item) pcItems.getSelectedValue();
      if (item != null) {
        npc.inventoryPickUpItemForce(item);
        pc.inventoryRemoveItem(pcItems.getSelectedIndex());
        updateItemList();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.BARTERING_TAKE)) {
      Item item = (Item) npcItems.getSelectedValue();
      if (item != null) {
        if (pc.inventoryPickUpItem(item)) {
          npc.inventoryRemoveItem(npcItems.getSelectedIndex());
          updateItemList();
        }
        
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.BARTERING_BUY)) {
      Item item = (Item) npcItems.getSelectedValue();
      if (item != null) {
        int price = item.getBuyPrice(pc.getEffectiveSkill(Character.SKILL_BARTERING),
            npc.getEffectiveSkill(Character.SKILL_BARTERING));
        if (pc.getMoney()>=price) {
          if (pc.inventoryPickUpItem(item)) {
            SoundPlayer.playEnemySoundsByName("bartering");
            pc.skillCheck(Character.SKILL_BARTERING, 50+npc.getEffectiveSkill(Character.SKILL_BARTERING));
            npc.inventoryRemoveItem(npcItems.getSelectedIndex());
            pc.setMoney(pc.getMoney()-price);
            npc.setMoney(npc.getMoney()+price);
            updateItemList();
          }
        }
      }
    }
    
  }
}
