package org.jheroes.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.jheroes.gui.ActionCommands;
import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.buttons.GameButton;
import org.jheroes.gui.buttons.ItemButton;
import org.jheroes.gui.labels.GameLabel;
import org.jheroes.gui.labels.GameTextArea;
import org.jheroes.gui.labels.ImageLabel;
import org.jheroes.gui.lists.EffectList;
import org.jheroes.gui.lists.ItemList;
import org.jheroes.gui.lists.PerkList;
import org.jheroes.gui.lists.SpellList;
import org.jheroes.gui.panels.GamePanel;
import org.jheroes.gui.panels.PanelWithButton;
import org.jheroes.map.Map;
import org.jheroes.map.Party;
import org.jheroes.map.character.Attack;
import org.jheroes.map.character.CharEffect;
import org.jheroes.map.character.Character;
import org.jheroes.map.character.Faces;
import org.jheroes.map.character.Perks;
import org.jheroes.map.character.Spell;
import org.jheroes.map.character.SpellFactory;
import org.jheroes.map.item.Item;
import org.jheroes.map.item.ItemFactory;



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
 * Character Sheet screen for the Game 
 * 
 */ 
public class GameCharacterSheet extends GamePanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Show inventory TAB as default
   */
  public static final int SELECTED_TAB_INVENTORY = 1;
  
  /**
   * Show skills and perks tab as default
   */
  public static final int SELECTED_TAB_SKILLS_N_PERKS = 2;
  
  /**
   * Show attribute tab as default
   */
  public static final int  SELECTED_TAB_ATTRIBUTES = 3;

  /**
   * Show spells and effects tab as default 
   */
  public static final int SELECTED_TAB_SPELLSNEFFECTS = 4;
  
  private Map map;
  private Party party;
  private int charIndex;
  private Character currentChar;
  
  public Character getCurrentChar() {
    return currentChar;
  }


  private GameLabel nameLabel;
  private ImageLabel faceLabel;
  private GameLabel hpLabel;
  private GameLabel spLabel;
  private GameLabel lvlLabel;
  private GameLabel expLabel;
  private GameTextArea ccBackText;
  private GameTextArea perkHelpText;
  private GameTextArea skillHelpText;
  private GameTextArea attributeHelpText;

  private GameTextArea mainAttackText;
  private GameTextArea secondAttackText;
  private GameTextArea defenseText;
  private GameTextArea itemHelpText;
  private GameTextArea effectText;
  private GameTextArea spellText;

  
  private GameLabel[] attributesLabel;
  private GameLabel[] skillsLabel;
  private PanelWithButton[] skillBtn;
  private GameButton addPerkBtn;
  private GameLabel skillPointsLeft;
  private GameLabel perksLeft;
  private GameLabel carryLabel;
  private GameLabel moneyLabel;
  
  private PerkList perkList;
  private PerkList activePerkList;
  
  private ItemList itemList;
  private SpellList spellList;
  private EffectList effectList;
  
  private ItemButton firstHandBtn;
  private ItemButton secondHandBtn;
  private ItemButton armorBtn;
  private ItemButton amuletBtn;
  private ItemButton headGearBtn;
  private ItemButton bootsBtn;
  private ItemButton ringBtn;
  private int perkListSelection;
  
  private boolean playerMakesAMove;
  
  /**
   * Constructor for CharacterSheet Panel
   * @param map Game map
   * @param party Party
   * @param charIndex character index in party
   * @param selectTab Which tab is show as default
   * @param listener ActionListener
   */
  public GameCharacterSheet(Map map, Party party, int charIndex,int selectTab, 
      ActionListener listener) {
    super(true);    
    this.map = map;
    this.party = party;
    currentChar = this.party.getPartyChar(charIndex);
    this.charIndex = charIndex;
    this.perkListSelection=0;
    this.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    this.setLayout(new BorderLayout());
    this.add(createUpPanel(),BorderLayout.NORTH);
    this.add(createDownPanel(listener),BorderLayout.SOUTH);
    this.add(createCenterPanels(selectTab,listener),BorderLayout.CENTER);
    this.updateSpellsNEffects();
    this.updatePanels();    
    this.setPlayerMakesAMove(false);
  }
  
  public void requestFocusAtStart() {
    itemList.requestFocusInWindow();
  }
  
  /**
   * Create Attribute and background panel
   * @return GamePanel
   */
  private GamePanel createAttributeNBack(ActionListener listener) {
    GamePanel result = new GamePanel(true);
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    result.setLayout(new BorderLayout());
    
    GameLabel label = new GameLabel("Background story:    ");
    GamePanel backPanel = new GamePanel(false);
    backPanel.setLayout(new BorderLayout());
    backPanel.add(label,BorderLayout.NORTH);
    ccBackText = new GameTextArea();
    ccBackText.setRows(30);
    ccBackText.setText(GameTexts.HERO_KNIGHT_BACKGROUND);
    ccBackText.setEditable(true);
    ccBackText.setLineWrap(true);
    ccBackText.setWrapStyleWord(true);
    backPanel.add(ccBackText,BorderLayout.CENTER);
    result.add(backPanel,BorderLayout.WEST);
    
    GamePanel attrPanel = new GamePanel(true);
    attrPanel.setLayout(new GridLayout(0, 1));
    label = new GameLabel("Attributes:");
    attrPanel.add(label);
    attributesLabel = new GameLabel[Character.MAX_NUMBERS_OF_ATTRIBUTES];
    PanelWithButton[] attrBtns = new PanelWithButton[Character.MAX_NUMBERS_OF_ATTRIBUTES];
    for (int i = 0;i<Character.MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      attributesLabel[i] = new GameLabel(Character.getAttributeName(i)+": 5");
       attrBtns[i] = new PanelWithButton(true, attributesLabel[i],
          ActionCommands.ATTRIBUTE_HELPS[i], GuiStatics.IMAGE_INFO,
          GuiStatics.IMAGE_INFO_PRESS, listener);
      attrPanel.add(attrBtns[i]);
    }
    attributeHelpText = new GameTextArea();
    attributeHelpText.setEditable(false);
    attributeHelpText.setText("                    ");
    attributeHelpText.setLineWrap(true);
    attrPanel.add(attributeHelpText);
    result.add(attrPanel,BorderLayout.CENTER);
    return result;    
  }
  
  /**
   * Create Skill and perks panel
   * @param listener ActionListener
   * @return GamePanel
   */
  private GamePanel createSkillsNPerks(ActionListener listener) {
    GamePanel result = new GamePanel(true);
    result.setLayout(new BorderLayout());
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    
    
    GamePanel skillPanel = new GamePanel(true);
    skillPanel.setLayout(new GridLayout(0, 1));

    GamePanel topSkillPanel = new GamePanel(false);
    topSkillPanel.setLayout(new GridLayout(0, 1));
    GamePanel midSkillPanel = new GamePanel(false);
    midSkillPanel.setLayout(new GridLayout(0, 1));

    skillPointsLeft = new GameLabel("Skills (Points left: 10):");
    topSkillPanel.add(skillPointsLeft);

    skillsLabel = new GameLabel[Character.MAX_NUMBERS_OF_SKILL];
    skillBtn = new PanelWithButton[Character.MAX_NUMBERS_OF_SKILL];
    for (int i=0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
      skillsLabel[i] = new GameLabel(Character.getSkillName(i)+": 100"+"(100)");
      skillBtn[i] = new PanelWithButton(true, skillsLabel[i],
          ActionCommands.NEWGAME_PLUS_SKILLS[i], GuiStatics.IMAGE_PLUS,
          GuiStatics.IMAGE_PLUS_PRESS, listener);
      skillBtn[i].addSecondButton(ActionCommands.SKILL_HELPS[i],
          GuiStatics.IMAGE_INFO, GuiStatics.IMAGE_INFO_PRESS, listener);
      if (i<5) {
        topSkillPanel.add(skillBtn[i]);
      } else {
        midSkillPanel.add(skillBtn[i]);  
      }
      
    }
    skillHelpText = new GameTextArea();
    skillHelpText.setEditable(false);
    skillHelpText.setText("                    ");
    skillHelpText.setLineWrap(true);
    skillPanel.add(topSkillPanel);
    skillPanel.add(midSkillPanel);
    skillPanel.add(skillHelpText);

    result.add(skillPanel,BorderLayout.WEST);
    
    GamePanel perkPanel = new GamePanel(true);
    perkPanel.setLayout(new BorderLayout());
    perkPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    GameLabel label = new GameLabel("Perks:");
    perkPanel.add(label,BorderLayout.NORTH);
    
    GamePanel perkAddPanel = new GamePanel(true);
    perkAddPanel.setLayout(new BorderLayout());
    perkAddPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    perksLeft = new GameLabel("Select 1 perk:");
    perkAddPanel.add(perksLeft,BorderLayout.NORTH);
    addPerkBtn = new GameButton("Select Perk", ActionCommands.SHEET_SELECT_PERK);
    addPerkBtn.addActionListener(listener);
    perkAddPanel.add(addPerkBtn,BorderLayout.SOUTH);
    perkList = new PerkList(
      currentChar.getPerks().getAvailablePerks(currentChar.getAttributes(), 
      currentChar.getSkills(), currentChar.getLevel()), 
      currentChar.getPerks().getNotAvailablePerks(currentChar.getAttributes(), 
      currentChar.getSkills(), currentChar.getLevel()));
    JScrollPane scroll = new JScrollPane(perkList);
    scroll.setBackground(GuiStatics.COLOR_BROWNISH);
    scroll.setForeground(GuiStatics.COLOR_CYAN);    
    perkAddPanel.add(scroll,BorderLayout.CENTER);

    GamePanel perkSelectedPanel = new GamePanel(true);
    perkSelectedPanel.setLayout(new BorderLayout());
    perkSelectedPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    label = new GameLabel("Active perks:");
    perkSelectedPanel.add(label,BorderLayout.NORTH);
    activePerkList = new PerkList(currentChar.getPerks().getActivePerks());
    perkSelectedPanel.add(activePerkList,BorderLayout.CENTER);
    
    perkHelpText = new GameTextArea();
    perkHelpText.setEditable(false);
    perkHelpText.setText("Here should be help!");
    
    perkPanel.add(perkAddPanel,BorderLayout.CENTER);
    perkPanel.add(perkSelectedPanel,BorderLayout.EAST);
    perkPanel.add(perkHelpText,BorderLayout.SOUTH);
    result.add(perkPanel,BorderLayout.CENTER);
    
    return result;
  }
  
  /**
   * Create Inventory panel
   * @param listener ActionListener
   * @return GamePanel
   */
  private GamePanel createInventory(ActionListener listener) {
    GamePanel result = new GamePanel(true);
    result.setLayout(new BorderLayout());
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    
    GamePanel backPackPanel = new GamePanel(true);
    backPackPanel.setLayout(new BorderLayout());
    backPackPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GamePanel northPanel = new GamePanel(true);
    northPanel.setLayout(new BorderLayout());
    northPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameLabel label = new GameLabel("Carry weight:");
    northPanel.add(label,BorderLayout.NORTH);
    carryLabel = new GameLabel("Carrying: 0/15 kg");
    northPanel.add(carryLabel,BorderLayout.CENTER);
    moneyLabel = new GameLabel("Copper pieces: 0");
    northPanel.add(moneyLabel,BorderLayout.SOUTH);

    backPackPanel.add(northPanel,BorderLayout.NORTH);
    GamePanel useEquipPanel = new GamePanel(true);
    useEquipPanel.setLayout(new BorderLayout());
    useEquipPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameButton useBtn = new GameButton("Use/Equip", 
        ActionCommands.SHEET_USE_EQUIP);
    useBtn.addActionListener(listener);
    useEquipPanel.add(useBtn,BorderLayout.EAST);
    GameButton dropBtn = new GameButton("Drop", 
        ActionCommands.SHEET_DROP);
    dropBtn.addActionListener(listener);
    useEquipPanel.add(dropBtn,BorderLayout.WEST);
    backPackPanel.add(useEquipPanel,BorderLayout.SOUTH);
    
    itemList = new ItemList();
    Item[] myItemList = new Item[3];
    myItemList[0] = ItemFactory.Create(3);
    myItemList[1] = ItemFactory.Create(7);
    myItemList[2] = ItemFactory.Create(12);
    itemList.setListData(myItemList);
    itemList.setActionCommand(ActionCommands.SHEET_USE_EQUIP);
    itemList.addActionListener(listener);
    JScrollPane scroll = new JScrollPane(itemList);
    backPackPanel.add(scroll,BorderLayout.CENTER);    
    result.add(backPackPanel,BorderLayout.WEST);
    
    GamePanel equipsPanel = new GamePanel(true);
    equipsPanel.setLayout(new GridLayout(0,1));
    equipsPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_INVISIBLE);
    firstHandBtn = new ItemButton(myItemList[0],"Main hand", ActionCommands.SHEET_FIRSTHAND);
    firstHandBtn.addActionListener(listener);
    equipsPanel.add(firstHandBtn);
    secondHandBtn = new ItemButton(null,"Off hand", ActionCommands.SHEET_SECONDHAND);
    secondHandBtn.addActionListener(listener);
    equipsPanel.add(secondHandBtn);
    armorBtn = new ItemButton(null,"Armor", ActionCommands.SHEET_ARMOR);
    armorBtn.addActionListener(listener);
    equipsPanel.add(armorBtn);
    headGearBtn = new ItemButton(null,"Head", ActionCommands.SHEET_HEADGEAR);
    headGearBtn.addActionListener(listener);
    equipsPanel.add(headGearBtn);
    amuletBtn = new ItemButton(null,"Amulet", ActionCommands.SHEET_AMULET);
    amuletBtn.addActionListener(listener);
    equipsPanel.add(amuletBtn);
    ringBtn = new ItemButton(null,"Ring", ActionCommands.SHEET_RING);
    ringBtn.addActionListener(listener);
    equipsPanel.add(ringBtn);
    bootsBtn = new ItemButton(null,"Boots", ActionCommands.SHEET_BOOTS);
    bootsBtn.addActionListener(listener);
    equipsPanel.add(bootsBtn);
    result.add(equipsPanel,BorderLayout.CENTER); 
    
    GamePanel infoPanel = new GamePanel(true);
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    label = new GameLabel("Main attack:");
    infoPanel.add(label);
    mainAttackText = new GameTextArea(currentChar.getFirstAttack().getAttackAsString());
    mainAttackText.setEditable(false);
    infoPanel.add(mainAttackText);
    label = new GameLabel("Secondary attack:");
    infoPanel.add(label);
    secondAttackText = new GameTextArea("No attack");
    secondAttackText.setEditable(false);
    infoPanel.add(secondAttackText);
    label = new GameLabel("Defense:");
    infoPanel.add(label);
    defenseText = new GameTextArea(currentChar.getDefense().getDefenseAsString());
    defenseText.setEditable(false);
    infoPanel.add(defenseText);
    label = new GameLabel("Item info:");
    infoPanel.add(label);
    itemHelpText = new GameTextArea("");
    itemHelpText.setEditable(false);
    infoPanel.add(itemHelpText);
    
    result.add(infoPanel,BorderLayout.EAST);
    
    return result;
    
  }
  
  /**
   * Create panel for Spells and magical effects
   * @param listener ActionListener
   * @return GamePanel
   */
  private GamePanel createSpellsNEffects(ActionListener listener) {
    GamePanel result = new GamePanel(true);
    result.setLayout(new BorderLayout());
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    
    GamePanel effectPanel = new GamePanel(true);
    effectPanel.setLayout(new BorderLayout());
    effectPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    GameLabel label = new GameLabel("Magical Effects:");
    effectPanel.add(label,BorderLayout.NORTH);
    
    effectList = new EffectList(null);
    effectPanel.add(effectList,BorderLayout.CENTER);
    effectText = new GameTextArea("");
    effectText.setRows(10);
    effectText.setEditable(false);
    effectText.setWrapStyleWord(true);
    effectText.setLineWrap(true);
    effectPanel.add(effectText,BorderLayout.SOUTH);
    
    result.add(effectPanel,BorderLayout.WEST);

    spellList = new SpellList(null);
    JScrollPane scroll = new JScrollPane(spellList);
    GamePanel spellPanel = new GamePanel(true);
    spellPanel.setLayout(new BorderLayout());
    spellPanel.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    label = new GameLabel("Spells:");
    spellPanel.add(label,BorderLayout.NORTH);
    GamePanel spellPanel2 = new GamePanel(false);
    spellPanel2.setLayout(new BorderLayout());
    spellPanel2.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);

    spellPanel2.add(scroll,BorderLayout.CENTER);
    spellText = new GameTextArea("");
    spellText.setRows(10);
    spellText.setEditable(false);
    spellText.setWrapStyleWord(true);
    spellText.setLineWrap(true);
    spellPanel2.add(spellText,BorderLayout.SOUTH);
    spellPanel.add(spellPanel2,BorderLayout.CENTER);
    GameButton castBtn = new GameButton("Cast", 
        ActionCommands.SHEET_CAST);
    castBtn.addActionListener(listener);
    spellPanel.add(castBtn,BorderLayout.SOUTH);
    result.add(spellPanel,BorderLayout.CENTER);

    return result;
  }
  /**
   * Create Tabbed view for panel
   * @param selectedTab, default shown panel
   * @param listener ActionListener
   * @return GamePanel
   */
  private GamePanel createCenterPanels(int selectedTab,ActionListener listener) {
    GamePanel result = new GamePanel(true);
    result.setLayout(new BorderLayout());
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setFont(GuiStatics.FONT_SMALL);
    tabbedPane.setForeground(GuiStatics.COLOR_BLACK);
    tabbedPane.setBackground(GuiStatics.COLOR_GOLD_DARK);
    GamePanel attrNBack = createAttributeNBack(listener);
    tabbedPane.add("Attributes and Background",attrNBack);
    GamePanel skillsNPerk = createSkillsNPerks(listener);
    tabbedPane.add("Skills and Perks",skillsNPerk);
    GamePanel inventory = createInventory(listener);
    tabbedPane.add("Inventory",inventory);
    GamePanel spellsNEffects = createSpellsNEffects(listener);
    tabbedPane.add("Spells and Magical effects",spellsNEffects);
    if (selectedTab == SELECTED_TAB_ATTRIBUTES) {
      tabbedPane.setSelectedComponent(attrNBack);
    }
    if (selectedTab == SELECTED_TAB_SKILLS_N_PERKS) {
      tabbedPane.setSelectedComponent(skillsNPerk);
    }
    if (selectedTab == SELECTED_TAB_INVENTORY) {
      tabbedPane.setSelectedComponent(inventory);
    }
    if (selectedTab == SELECTED_TAB_SPELLSNEFFECTS) {
      tabbedPane.setSelectedComponent(spellsNEffects);
    }
    result.add(tabbedPane,BorderLayout.CENTER);
    return result;
  }
  
  /**
   * Create North Panel
   * with Full Name and Face image
   * @return GamePanel
   */
  private GamePanel createUpPanel() {
    GamePanel result = new GamePanel(true);
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);
    result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));
    nameLabel = new GameLabel("Name: John Doefoo");
    result.add(nameLabel);
    faceLabel = new ImageLabel(Faces.getFace(0), true);
    result.add(Box.createRigidArea(new Dimension(15, 25)));
    result.add(faceLabel);
    result.add(Box.createRigidArea(new Dimension(15, 25)));
    hpLabel = new GameLabel("HP: 50/50");
    result.add(hpLabel);
    spLabel = new GameLabel("SP: 50/50");
    result.add(spLabel);
    lvlLabel = new GameLabel("Level: 1");
    result.add(lvlLabel);
    expLabel = new GameLabel("EXP: 1000/1000");
    result.add(expLabel);
    return result;
  }
  
  /**
   * Create south panel
   * @param listener
   * @return GamePanel
   */
  private GamePanel createDownPanel(ActionListener listener) {
    GamePanel result = new GamePanel(true);
    result.setGradientColor(GuiStatics.GRADIENT_COLOR_BLUE);    
    result.setLayout(new BorderLayout());
    GameButton prevBtn = new GameButton("Prev", 
        ActionCommands.SHEET_PREV_CHAR);
    prevBtn.addActionListener(listener);
    result.add(prevBtn,BorderLayout.WEST);
    GameButton backBtn = new GameButton("Return", 
        ActionCommands.SHEET_BACK_TO_GAME);
    backBtn.addActionListener(listener);
    result.add(backBtn,BorderLayout.CENTER);
    GameButton nextBtn = new GameButton("Next", 
        ActionCommands.SHEET_NEXT_CHAR);
    nextBtn.addActionListener(listener);
    result.add(nextBtn,BorderLayout.EAST);
    return result;
  }
  
  /**
   * Update all panels and labels with currentChar information
   */
  private void updatePanels() {
    // Information in top
    ccBackText.setText(currentChar.getDescription());
    nameLabel.setText(currentChar.getLongName());
    faceLabel.setImage(Faces.getFace(currentChar.getFaceNumber()));
    lvlLabel.setText("Level:"+currentChar.getLevel());
    expLabel.setText("EXP:"+currentChar.getExperience()+"/"+currentChar.getNextNeededExp());
    
    // Attributes
    for (int i=0;i<Character.MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      attributesLabel[i].setText(Character.getAttributeName(i)+":"+currentChar.getEffectiveAttribute(i));
    }
    // Skills
    for (int i=0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
      skillsLabel[i].setText(Character.getSkillName(i)+":"+currentChar.getEffectiveSkill(i)+"("+currentChar.getSkill(i)+")");
      if (party.getSkillPointsLeft(charIndex)==0) {
        skillBtn[i].setButtonEnabled(false);
      } else {
        skillBtn[i].setButtonEnabled(true);
      }

    }
    skillPointsLeft.setText("Skills (Points left: "+party.getSkillPointsLeft(charIndex)+"):");
    //Perks
    perksLeft.setText("Select "+party.getPerksLeft(charIndex)+" perk:");
    if (party.getPerksLeft(charIndex)==0) {
      addPerkBtn.setEnabled(false);
    } else {
      addPerkBtn.setEnabled(true);
    }
    // Hitpoints and Stamina
    hpLabel.setText("HP: "+currentChar.getCurrentHP()+"/"+currentChar.getMaxHP());
    spLabel.setText("SP: "+currentChar.getCurrentSP()+"/"+currentChar.getMaxStamina());
    // Perks
    perkList.updatePerkList(currentChar.getPerks().getAvailablePerks(currentChar.getAttributes(), 
      currentChar.getSkills(), currentChar.getLevel()), 
      currentChar.getPerks().getNotAvailablePerks(currentChar.getAttributes(), 
      currentChar.getSkills(), currentChar.getLevel()));
    activePerkList.setListData(currentChar.getPerks().getActivePerks());
    perkList.setEnabled(true);
    perkList.setSelectedIndex(0);
    activePerkList.setEnabled(true);
    
    //Inventory
    itemList.setListData(new Vector<Item>());
    if (currentChar.inventorySize()>0) {
      int index=0;
      Item[] items = new Item[currentChar.inventorySize()];
      for (int i=currentChar.inventorySize()-1;i>-1;i--) {
        if (index < items.length) {
          items[index] = currentChar.inventoryGetIndex(i);
          index++;
        }
      }
      itemList.setListData(items);
    } 
    if (itemList.getSelectedIndex() == -1 && currentChar.inventorySize() > 0) {
      itemList.setSelectedIndex(0);
      itemList.transferFocus();
      itemList.requestFocusInWindow();
    }
    StringBuilder sb = new StringBuilder();
    if (currentChar.isLightLoad()) {
      sb.append("Light load ");
    }
    if (currentChar.isMediumLoad()) {
      sb.append("Medium load ");
    }
    if (currentChar.isHeavyLoad()) {
      sb.append("Heavy load ");
    }
    sb.append(currentChar.inventoryGetCurrentLoadAsString());
    sb.append("/");
    sb.append(currentChar.inventoryGetMaxCarryingCapacityAsString());
    sb.append("kg");
    carryLabel.setText(sb.toString());
    moneyLabel.setText("Coppers:"+currentChar.getMoney());
    
    firstHandBtn.setItem(currentChar.inventoryGetFirstHand());
    secondHandBtn.setItem(currentChar.inventoryGetSecondHand());
    armorBtn.setItem(currentChar.inventoryGetArmor());
    amuletBtn.setItem(currentChar.inventoryGetAmulet());
    bootsBtn.setItem(currentChar.inventoryGetBoots());
    ringBtn.setItem(currentChar.inventoryGetRing());
    headGearBtn.setItem(currentChar.inventoryGetHeadGear());
    Attack att = currentChar.getFirstAttack();
    if (att != null) {
      mainAttackText.setText(att.getAttackAsString());
    } else {
      mainAttackText.setText("No attack");
    }
    att = currentChar.getSecondAttack();
    if (att != null) {
      secondAttackText.setText(att.getAttackAsString());
    } else {
      secondAttackText.setText("No attack");
    }
    defenseText.setText(currentChar.getDefense().getDefenseAsString());
    //Help for selected item
    int index = itemList.getSelectedIndex();
    index = currentChar.getInventoryItemIndexByReversed(index);
    Item item = currentChar.inventoryGetIndex(index);
    if (item != null) {
      itemHelpText.setText(item.getItemInformation());
    } else {
      itemHelpText.setText("");
    }
    Spell spell = (Spell) spellList.getSelectedValue();
    if (spell != null) {
      spellText.setText(spell.toString());
    }
    CharEffect eff = (CharEffect) effectList.getSelectedValue();
    if (eff != null) {
      effectText.setText(eff.getEffectAsString());
    }
  }
    
  /**
   * Recreate spells and effect list. Only update when needed.
   */
  public void updateSpellsNEffects() {
    Spell[] spellArr = new Spell[currentChar.getSpellList().size()];
    for (int i=0;i<currentChar.getSpellList().size();i++) {
      spellArr[i] = SpellFactory.getSpell(currentChar.getSpellList().get(i));
    }
    spellList.setListData(spellArr);
    if (currentChar.getEffects() != null) {
      effectList.setListData(currentChar.getEffects());
    } else {
      effectList.setListData(new CharEffect[0]);
    }
  }
  
  /**
   * Update Perk selection list
   * @return boolean, true if perk selection is active, otherwise false
   */
  private boolean csPerkSelectionListUpdate() {
    String value = (String) perkList.getSelectedValue();    
    int index= perkList.getSelectedIndex();
    if (value != null) {
      if (value.startsWith("!")) {
        value = value.substring(1);
      }
      index = Perks.getPerkIndex(value);
      if (index != -1) {
        activePerkList.clearSelection();
        perkListSelection = 1;
        perkHelpText.setText(Perks.ALL_PERK_NAMES_DESC[index]+
            "\n\nRequirements:\n"+Perks.ALL_PERK_NAMES_REQU[index]);
        perkHelpText.setWrapStyleWord(true);
        perkHelpText.setLineWrap(true);
        return true;
      }
    }   
    return false;
  }
  
  /**
   * Update Active perk list
   * @return boolean if active perk is active, otherwise false
   */
  private boolean csPerkActiveListUpdate() {
    String value = (String) activePerkList.getSelectedValue();        
    int activeIndex=-1;
    if (value != null) {
      activeIndex = Perks.getPerkIndex(value);
      if (activeIndex != -1) {
        perkList.clearSelection();
        perkListSelection = 2;
        perkHelpText.setText(Perks.ALL_PERK_NAMES_DESC[activeIndex]+
            "\n\nRequirements:\n"+Perks.ALL_PERK_NAMES_REQU[activeIndex]);
        perkHelpText.setWrapStyleWord(true);
        perkHelpText.setLineWrap(true);
        return true;
      }           
    }
    return false;
  }
  
  /**
   * Get selected spell from the list
   * @return Spell
   */
  public Spell getSelectedSpell() {
    return (Spell) spellList.getSelectedValue();
  }
  
  /**
   * Get selected item from the list
   * @return int
   */
  public int getSelectedItemIndex() {
    return itemList.getSelectedIndex();
  }
  
  public boolean isPlayerMakesAMove() {
    return playerMakesAMove;
  }

  public void setPlayerMakesAMove(boolean playerMakesAMove) {
    this.playerMakesAMove = playerMakesAMove;
  }

  /**
   * Handle Character Sheet Action event
   * @param arg0 ActionEvent
   */
  public void handleActionEvent(ActionEvent arg0) {
    if (arg0.getActionCommand().equals(ActionCommands.GENERIC_TIMER)) {
      // Show help on perks
      if ((perkListSelection==0) || (perkListSelection==2)) {
        if (!csPerkSelectionListUpdate()) {
          csPerkActiveListUpdate();
        }          
      } else {
        if (!csPerkActiveListUpdate()) {
          csPerkSelectionListUpdate();
        }
      }
      // Show item information
      int index = itemList.getSelectedIndex();
      index = currentChar.getInventoryItemIndexByReversed(index);
      Item item = currentChar.inventoryGetIndex(index);      
      if (item != null) {
        itemHelpText.setText(item.getItemInformation());
      } else {
        itemHelpText.setText("");
      }
      // Show spell and magic effect information
      Spell spell = (Spell) spellList.getSelectedValue();
      if (spell != null) {
        spellText.setText(spell.toString());
      }
      CharEffect eff = (CharEffect) effectList.getSelectedValue();
      if (eff != null) {
        effectText.setText(eff.getEffectAsString());
      }
      
      this.repaint();
      
    }
    if (arg0.getActionCommand().equals(ActionCommands.ATTRIBUTE_HELP_STRENGTH)) {
      attributeHelpText.setText(GameTexts.HELP_ATTRIBUTE_STRENGTH);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ATTRIBUTE_HELP_AGILITY)) {
      attributeHelpText.setText(GameTexts.HELP_ATTRIBUTE_AGILITY);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ATTRIBUTE_HELP_ENDURANCE )) {
      attributeHelpText.setText(GameTexts.HELP_ATTRIBUTE_ENDURANCE);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ATTRIBUTE_HELP_INTELLIGENCE)) {
      attributeHelpText.setText(GameTexts.HELP_ATTRIBUTE_INTELLIGENCE);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ATTRIBUTE_HELP_WISDOM)) {
      attributeHelpText.setText(GameTexts.HELP_ATTRIBUTE_WISDOM);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ATTRIBUTE_HELP_CHARISMA)) {
      attributeHelpText.setText(GameTexts.HELP_ATTRIBUTE_CHARISMA);
    }
    if (arg0.getActionCommand().equals(ActionCommands.ATTRIBUTE_HELP_LUCK)) {
      attributeHelpText.setText(GameTexts.HELP_ATTRIBUTE_LUCK);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_UNARMED)) {
      skillHelpText.setText(GameTexts.HELP_SKILL_UNARMED);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_MELEE)) {
      skillHelpText.setText(GameTexts.HELP_SKILL_MELEE);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_RANGED_WEAPONS )) {
      skillHelpText.setText(GameTexts.HELP_SKILL_RANGED_ATTACK);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_DODGING)) {
      skillHelpText.setText(GameTexts.HELP_SKILL_DODGING);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_WIZARDY)) {
      skillHelpText.setText(GameTexts.HELP_SKILL_WIZARDY);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_SORCERY)) {
      skillHelpText.setText(GameTexts.HELP_SKILL_SORCERY);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_QI_MAGIC)) {
      skillHelpText.setText(GameTexts.HELP_SKILL_QI_MAGIC);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_BARTERING)) {
      skillHelpText.setText(GameTexts.HELP_SKILL_BARTERING);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_DIPLOMACY)) {
      skillHelpText.setText(GameTexts.HELP_SKILL_DIPLOMACY);
    }
    if (arg0.getActionCommand().equals(ActionCommands.SKILL_HELP_LOCKPICKING)) {
      skillHelpText.setText(GameTexts.HELP_SKILL_LOCK_PICKING);
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_BARTERING)) {
      if (currentChar.setSkill(Character.SKILL_BARTERING,
          currentChar.getSkill(Character.SKILL_BARTERING)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_DIPLOMACY)) {
      if (currentChar.setSkill(Character.SKILL_DIPLOMACY, 
          currentChar.getSkill(Character.SKILL_DIPLOMACY)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_DODGING)) {
      if (currentChar.setSkill(Character.SKILL_DODGING,
          currentChar.getSkill(Character.SKILL_DODGING)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_LOCKPICKING)) {
      if (currentChar.setSkill(Character.SKILL_LOCK_PICKING, 
          currentChar.getSkill(Character.SKILL_LOCK_PICKING)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_MELEE)) {
      if (currentChar.setSkill(Character.SKILL_MELEE, 
          currentChar.getSkill(Character.SKILL_MELEE)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_QI_MAGIC)) {
      if (currentChar.setSkill(Character.SKILL_QI_MAGIC, 
          currentChar.getSkill(Character.SKILL_QI_MAGIC)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_RANGED_WEAPONS)) {
      if (currentChar.setSkill(Character.SKILL_RANGED_WEAPONS, 
          currentChar.getSkill(Character.SKILL_RANGED_WEAPONS)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_SORCERY)) {
      if (currentChar.setSkill(Character.SKILL_SORCERY, 
          currentChar.getSkill(Character.SKILL_SORCERY)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_UNARMED)) {
      if (currentChar.setSkill(Character.SKILL_UNARMED, 
          currentChar.getSkill(Character.SKILL_UNARMED)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.NEWGAME_PLUS_WIZARDY)) {
      if (currentChar.setSkill(Character.SKILL_WIZARDRY, 
          currentChar.getSkill(Character.SKILL_WIZARDRY)+1)) {
        party.setSkillPointsLeft(charIndex, party.getSkillPointsLeft(charIndex)-1);
        updatePanels();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_SELECT_PERK)) {
      String perk = (String) perkList.getSelectedValue();
      if ((perk != null) && (Perks.getPerkIndex(perk) != -1)) {
        currentChar.getPerks().setPerk(Perks.getPerkIndex(perk), true);
        if ((perk.equalsIgnoreCase(Perks.PERK_TRAINED_AGILITY)) ||
            (perk.equalsIgnoreCase(Perks.PERK_TRAINED_AGILITY2))){
          int attr = Character.ATTRIBUTE_AGILITY;
          int value = currentChar.getAttribute(attr);
          currentChar.setAttribute(attr, value+1);
        }
        if ((perk.equalsIgnoreCase(Perks.PERK_TRAINED_STRENGTH)) ||
            (perk.equalsIgnoreCase(Perks.PERK_TRAINED_STRENGTH2))){
          int attr = Character.ATTRIBUTE_STRENGTH;
          int value = currentChar.getAttribute(attr);
          currentChar.setAttribute(attr, value+1);
        }
        if ((perk.equalsIgnoreCase(Perks.PERK_TRAINED_CHARISMA)) ||
            (perk.equalsIgnoreCase(Perks.PERK_TRAINED_CHARISMA2))){
          int attr = Character.ATTRIBUTE_CHARISMA;
          int value = currentChar.getAttribute(attr);
          currentChar.setAttribute(attr, value+1);
        }
        if ((perk.equalsIgnoreCase(Perks.PERK_TRAINED_ENDURANCE)) ||
            (perk.equalsIgnoreCase(Perks.PERK_TRAINED_ENDURANCE2))){
          int attr = Character.ATTRIBUTE_ENDURANCE;
          int value = currentChar.getAttribute(attr);
          currentChar.setAttribute(attr, value+1);
        }
        if ((perk.equalsIgnoreCase(Perks.PERK_TRAINED_INTELLIGENCE)) ||
            (perk.equalsIgnoreCase(Perks.PERK_TRAINED_INTELLIGENCE2))){
          int attr = Character.ATTRIBUTE_INTELLIGENCE;
          int value = currentChar.getAttribute(attr);
          currentChar.setAttribute(attr, value+1);
        }
        if ((perk.equalsIgnoreCase(Perks.PERK_TRAINED_WISDOM)) ||
            (perk.equalsIgnoreCase(Perks.PERK_TRAINED_WISDOM2))){
          int attr = Character.ATTRIBUTE_WISDOM;
          int value = currentChar.getAttribute(attr);
          currentChar.setAttribute(attr, value+1);
        }
        if ((perk.equalsIgnoreCase(Perks.PERK_TRAINED_LUCK)) ||
            (perk.equalsIgnoreCase(Perks.PERK_TRAINED_LUCK2))){
          int attr = Character.ATTRIBUTE_LUCK;
          int value = currentChar.getAttribute(attr);
          currentChar.setAttribute(attr, value+1);
        }
        if (perk.equalsIgnoreCase(Perks.PERK_SMITE_UNDEAD)) {
          currentChar.addSpell("Smite Undead");
        }
        party.setPerksLeft(charIndex, party.getPerksLeft(charIndex)-1);
      }
      updatePanels();
    }
    // Handle inventory
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_USE_EQUIP)) {
      if (!party.isCombat() || (party.isCombat() && party.getActiveChar().equals(currentChar))) {
        int i = itemList.getSelectedIndex();
        i = currentChar.getInventoryItemIndexByReversed(i);
        if (currentChar.equipItemByIndex(i,party)) {
          setPlayerMakesAMove(true);
          updatePanels();
          updateSpellsNEffects();
        }
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_FIRSTHAND)) {
      if (!party.isCombat() || (party.isCombat() && party.getActiveChar().equals(currentChar))) {
        currentChar.unequipFirstHand();
        setPlayerMakesAMove(true);
        updatePanels();
        updateSpellsNEffects();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_SECONDHAND)) {
      if (!party.isCombat() || (party.isCombat() && party.getActiveChar().equals(currentChar))) {
        currentChar.unequipSecondHand();
        setPlayerMakesAMove(true);
        updatePanels();
        updateSpellsNEffects();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_ARMOR)) {
      if (!party.isCombat() || (party.isCombat() && party.getActiveChar().equals(currentChar))) {
        currentChar.unequipArmor();
        setPlayerMakesAMove(true);
        updatePanels();
        updateSpellsNEffects();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_AMULET)) {
      if (!party.isCombat() || (party.isCombat() && party.getActiveChar().equals(currentChar))) {
        currentChar.unequipAmulet();
        setPlayerMakesAMove(true);
        updatePanels();
        updateSpellsNEffects();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_HEADGEAR)) {
      if (!party.isCombat() || (party.isCombat() && party.getActiveChar().equals(currentChar))) {
        currentChar.unequipHeadGear();
        updatePanels();
        updateSpellsNEffects();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_BOOTS)) {
      if (!party.isCombat() || (party.isCombat() && party.getActiveChar().equals(currentChar))) {
        currentChar.unequipBoots();
        setPlayerMakesAMove(true);
        updatePanels();
        updateSpellsNEffects();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_RING)) {
      if (!party.isCombat() || (party.isCombat() && party.getActiveChar().equals(currentChar))) {
        currentChar.unequipRing();
        setPlayerMakesAMove(true);
        updatePanels();
        updateSpellsNEffects();
      }
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_DROP)) {
      if (!party.isCombat() || (party.isCombat() && party.getActiveChar().equals(currentChar))) {
        int i = itemList.getSelectedIndex();
        i = currentChar.getInventoryItemIndexByReversed(i);
        Item item = currentChar.inventoryGetIndex(i);
        if ((item != null) && (item.getType() != Item.TYPE_ITEM_QUEST)) {
          if (currentChar.inventoryRemoveItem(i)) {
            map.addNewItem(currentChar.getX(), currentChar.getY(), item);
            updatePanels();
            setPlayerMakesAMove(true);
            party.addLogText(currentChar.getName()+" dropped "+item.getItemNameInGame());
          }
        }
      }
    }
    // End of Handle inventory
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_NEXT_CHAR)) {
      do {
        charIndex++;
        if (charIndex > Party.MAX_PARTY_SIZE) {
          charIndex =0;
        }
        currentChar = this.party.getPartyChar(charIndex);
      }
      while (currentChar == null);
      updatePanels();
      updateSpellsNEffects();
    }
    if (arg0.getActionCommand().equals(ActionCommands.SHEET_PREV_CHAR)) {
      do {
        charIndex--;
        if (charIndex == -1) {
          charIndex = Party.MAX_PARTY_SIZE-1;
        }
        currentChar = this.party.getPartyChar(charIndex);
      }
      while (currentChar == null);
      updatePanels();
      updateSpellsNEffects();
    }
  }
}
