package org.jheroes.gui.panels;


import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.ImageGameButton;
import org.jheroes.gui.labels.BarLabel;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.map.Party;
import org.jheroes.map.character.Character;
import org.jheroes.map.character.Faces;


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
 * Party member panels shown next to map panel
 * 
 */
public class PartyMemberPanel extends GamePanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int memberNumber;
  private ImageGameButton leaderBtn;
  private ImageGameButton faceBtn;
  private Party party;
  private GameLabel nameLabel;
  private BarLabel hpLabel;
  private BarLabel spLabel;
  
  public PartyMemberPanel(int memberNumber, Party party, ActionListener listener) {
    super(true);
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    this.party = party;
    this.memberNumber = memberNumber;
    this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
    switch (this.memberNumber) {
    case 0: {leaderBtn = new ImageGameButton(GuiStatics.IMAGE_LEADER_NOT_ACTIVE,
        GuiStatics.IMAGE_LEADER_PRESSED, false, ActionCommands.GAME_LEADER_PARTY_1);
        faceBtn = new ImageGameButton(GuiStatics.IMAGE_NO_FACE,
            GuiStatics.IMAGE_NO_FACE, false, ActionCommands.GAME_SELECT_MEMBER1);
        break;}
    case 1: {leaderBtn = new ImageGameButton(GuiStatics.IMAGE_LEADER_NOT_ACTIVE,
        GuiStatics.IMAGE_LEADER_PRESSED, false, ActionCommands.GAME_LEADER_PARTY_2);
    faceBtn = new ImageGameButton(GuiStatics.IMAGE_NO_FACE,
        GuiStatics.IMAGE_NO_FACE, false, ActionCommands.GAME_SELECT_MEMBER2);
        break;}
    case 2: {leaderBtn = new ImageGameButton(GuiStatics.IMAGE_LEADER_NOT_ACTIVE,
        GuiStatics.IMAGE_LEADER_PRESSED, false, ActionCommands.GAME_LEADER_PARTY_3);
        faceBtn = new ImageGameButton(GuiStatics.IMAGE_NO_FACE,
        GuiStatics.IMAGE_NO_FACE, false, ActionCommands.GAME_SELECT_MEMBER3);
        break;}
    case 3: {leaderBtn = new ImageGameButton(GuiStatics.IMAGE_LEADER_NOT_ACTIVE,
        GuiStatics.IMAGE_LEADER_PRESSED, false, ActionCommands.GAME_LEADER_PARTY_4);
        faceBtn = new ImageGameButton(GuiStatics.IMAGE_NO_FACE,
        GuiStatics.IMAGE_NO_FACE, false, ActionCommands.GAME_SELECT_MEMBER4);
        break;}
    }    
    leaderBtn.addActionListener(listener);
    faceBtn.addActionListener(listener);
    GamePanel statsPanel = new GamePanel(false);
    statsPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    statsPanel.setLayout(new BoxLayout(statsPanel,BoxLayout.Y_AXIS));
    nameLabel = new GameLabel("          ");
    statsPanel.add(nameLabel);
    hpLabel = new BarLabel("HP:",50, 10);
    hpLabel.setGradientColor(GuiStatics.GRADIENT_COLOR_RED);
    statsPanel.add(hpLabel);
    spLabel = new BarLabel("SP:",50, 10);
    spLabel.setGradientColor(GuiStatics.GRADIENT_COLOR_GREEN);
    statsPanel.add(spLabel);
    this.add(leaderBtn);
    this.add(faceBtn);
    this.add(statsPanel);
    updatePanel(party.getPartyChar(memberNumber));
  }
  
  public void updatePanel(Character member) {
    if (member != null) {
      BufferedImage face = new BufferedImage(48, 48, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics g = face.getGraphics();
      g.drawImage(Faces.getFace(member.getFaceNumber()), 0, 0,null);
      if ((party.getPerksLeft(memberNumber)>0) || (party.getSkillPointsLeft(memberNumber)>0)) {
        g.drawImage(GuiStatics.IMAGE_LEVEL_UP, 24, 24,null);
      }
      faceBtn.setPressedImage(face);
      faceBtn.setNotPressedImage(face);
      faceBtn.setEnabled(true);
      leaderBtn.setEnabled(true);
      nameLabel.setText(member.getName());
      if (nameLabel.getText().length()>12) {
        nameLabel.setFont(GuiStatics.FONT_SMALL);
      }
      hpLabel.setMax(member.getMaxHP());
      hpLabel.setCurrent(member.getCurrentHP());
      spLabel.setMax(member.getMaxStamina());
      spLabel.setCurrent(member.getCurrentSP());
      if (memberNumber == party.getActiveCharIndex()) {
        if (party.getMode() == Party.MODE_SOLO_MODE) {
          leaderBtn.setNotPressedImage(GuiStatics.IMAGE_LEADER_SOLO);
        } else {
          leaderBtn.setNotPressedImage(GuiStatics.IMAGE_LEADER_ACTIVE);
        }
      } else {
        leaderBtn.setNotPressedImage(GuiStatics.IMAGE_LEADER_NOT_ACTIVE);
      }
    } else {
      faceBtn.setPressedImage(GuiStatics.IMAGE_NO_FACE);
      faceBtn.setNotPressedImage(GuiStatics.IMAGE_NO_FACE);
      faceBtn.setEnabled(false);
      leaderBtn.setEnabled(false);
      nameLabel.setText("              ");
      hpLabel.setMax(50);
      hpLabel.setCurrent(0);
      spLabel.setMax(50);
      spLabel.setCurrent(0);
    }
  }
  
}
