package org.jheroes.gui.panels;


import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.lists.SpellList;
import org.jheroes.map.character.Spell;
import org.jheroes.map.character.SpellFactory;


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
 * When player casts spells in game then this panel is used.
 * 
 */
public class GameSpellPanel extends GamePanel {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Spell spellList[];
  private SpellList list;
  private GameTextArea spellInfo;
  
  public GameSpellPanel(String characterName, ArrayList<String> spells, 
      ActionListener listener) {
    super(false);
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    this.setLayout(new BorderLayout());
    GameLabel label = new GameLabel(characterName+"'s spells");
    this.add(label,BorderLayout.NORTH);
    spellList = new Spell[spells.size()];
    for (int i=0;i<spells.size();i++) {
      spellList[i] = SpellFactory.getSpell(spells.get(i));
    }
    list = new SpellList(spellList);
    JScrollPane scroll = new JScrollPane(list);    
    final JScrollBar vertical = scroll.getVerticalScrollBar();

    list.addListSelectionListener(new ListSelectionListener() {
      
      @Override
      public void valueChanged(ListSelectionEvent arg0) {
        int j = vertical.getBlockIncrement();
        int i = vertical.getValue();
        if (arg0.getFirstIndex() == 0 && arg0.getLastIndex() == spellList.length-1) {
          if (i ==0) {
            vertical.setValue(vertical.getMaximum());
          } else {
            vertical.setValue(0);
          }
        } else {
          vertical.setValue((arg0.getFirstIndex()+arg0.getLastIndex())/2*j);
        }
        
      }
    });

    
    this.add(scroll,BorderLayout.CENTER);
    
    GamePanel buttonPane = new GamePanel(true);
    buttonPane.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    buttonPane.setLayout(new BorderLayout());
    GameButton btn = new GameButton("Cast", ActionCommands.GAME_SPELL_CAST);
    btn.addActionListener(listener);
    buttonPane.add(btn,BorderLayout.EAST);
    btn = new GameButton("Cancel", ActionCommands.GAME_SPELL_CANCEL);
    btn.addActionListener(listener);
    buttonPane.add(btn,BorderLayout.WEST);
    
    GamePanel infoPanel = new GamePanel(true);
    infoPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    infoPanel.setLayout(new BorderLayout());
    spellInfo = new GameTextArea();
    spellInfo.setEditable(false);
    spellInfo.setLineWrap(true);
    infoPanel.add(spellInfo,BorderLayout.CENTER);
    infoPanel.add(buttonPane,BorderLayout.SOUTH);
    this.add(infoPanel,BorderLayout.SOUTH);
    if (spells.size() > 0) {
      list.setSelectedIndex(0);
    }
    updateSpellInfo();
  }
  
  public void updateSpellInfo() {
    Spell spell = (Spell) list.getSelectedValue();
    if (spell != null) {
      spellInfo.setText(spell.toString());
    } else {
      spellInfo.setText("");
    }
  }
  public Spell getSelectedSpell() {
     return (Spell) list.getSelectedValue();     
  }
  public void moveSelectionDown() {
    int i = list.getSelectedIndex();    
    if (i!=-1) {
      i++;
      if (i>=list.getModel().getSize()) {
        i=0;
      }
      list.setSelectedIndex(i);
    } else {
      list.setSelectedIndex(0);
    }
    updateSpellInfo();
  }

  public void moveSelectionUp() {
    int i = list.getSelectedIndex();
    if (i!=-1) {
      i--;
      if (i<0) {
        i=list.getModel().getSize()-1;
      }
      list.setSelectedIndex(i);
    } else {
      list.setSelectedIndex(0);
    }
    updateSpellInfo();
  }
  
  public int getSelectedIndex() {
    return list.getSelectedIndex();
  }
  
  public void setSelection(int i) {
    if ((i >= 0) && (i <= list.getModel().getSize()-1)) {
      list.setSelectedIndex(i);
    } else {
      list.setSelectedIndex(0);
    }
    updateSpellInfo();
  }

}
