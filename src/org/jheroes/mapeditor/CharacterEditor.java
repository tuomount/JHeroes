package org.jheroes.mapeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.jheroes.map.MapUtilities;
import org.jheroes.map.Party;
import org.jheroes.map.character.CharTask;
import org.jheroes.map.character.Character;
import org.jheroes.map.character.CharacterAnimation;
import org.jheroes.map.character.Perks;
import org.jheroes.map.character.SpellFactory;
import org.jheroes.map.item.Item;
import org.jheroes.map.item.ItemFactory;
import org.jheroes.tileset.Tileset;

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
 *
 * Character editor for map editor
 * 
 **/



public class CharacterEditor extends JDialog implements ActionListener, MouseListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private static final String ACTION_NEXT_CHAR = "NextChar";
  private static final String ACTION_PREV_CHAR = "PrevChar";
  private static final String ACTION_SAVE_ALL = "SaveAll";
  private static final String ACTION_SELECT_CHAR = "SelectChar";
  private static final String ACTION_CHANGE_FACE = "ChangeFace";
  private static final String ACTION_NEXT_TILE = "NextTile";
  private static final String ACTION_PREV_TILE = "PrevTile";
  private static final String ACTION_NEXT_ITEM = "NextItem";
  private static final String ACTION_PREV_ITEM = "PrevItem";
  private static final String ACTION_UPDATE_CHAR ="UpdateChar";
  private static final String ACTION_TURN = "TurnHeading";
  private static final String ACTION_RANDOMMALE = "RandomMale";
  private static final String ACTION_RANDOMFEMALE = "RandomFemal";
  
  public final static String CHARACTER_LIST_FILE = "/res/characterlist.res";
  /**
   * Gui Components
   */
  private JPanel main;
  private JPanel topPanel;
  private JPanel bottomPanel;
  private JLabel characterLabel;
  private JPanel basicCharPanel;
  private JPanel perkPanel;
  private JPanel inventoryPanel;
  private JPanel timeTablePanel;
  private JPanel spellPanel;
  private CharacterEditorCommonPanel basicCharTopPanel;
  private boolean selectCharacterClicked = false;
  
  private JComboBox cbHostilityLevel;
  
  private final static String[] HOSTILITY_LEVEL_STRINGS 
         = {"Avoid", "Aggressive","Guard"};

  /**
   * Character index in list
   */
  private int characterIndex=0;
  
  private ArrayList<Character> characterList;
  
  
  private Character currentChar;
  private Item currentItem;
  private int currentItemIndex;
  private int lastCharIndex;
  private int lastTaskIndex;
  
  /**
   * Total values of attributes
   */
  private NumberSpinner[] attrSet;
  /**
   * Total values of skills
   */
  private JLabel[] skillLabel;
  /**
   * Skill points
   */
  private NumberSpinner[] skillSet;
  private NumberSpinner moneySpin;
  
  private JCheckBox[] cbPerks;
  
  private JTextField nameText;
  private JTextField longNameText;
  private JTextField shopKeeperText;
  private JList inventoryList;
  private JList spellList;
  private JComboBox spellCB;
  private JLabel newItemLabel;
  private JLabel mainAttackLabel;
  private JLabel secondaryAttackLabel;
  private JLabel defenseLabel;
  private JLabel levelLabel;
  private JLabel hpLabel;
  private JButton firstHandBtn;
  private JButton secondHandBtn;
  private JButton armorBtn;
  private JButton headGearBtn;
  private JButton amuletBtn;
  private JButton ringBtn;
  private JButton bootsBtn;
  
  private JTextField[] timeFields;
  private JComboBox[] taskFields;
  private JComboBox[] waypointFields;
  private JTextField[] descriptionFields;
  private JTextField characterDescField;
  
  private static Tileset charactersTileset = null;
  
  
  
  private String[] wayPoints;
  
  private Party party;
  
  public CharacterEditor(JFrame parent, Character target, Tileset charactersTileset,
      String[] listOfWaypoints) {
    if (CharacterEditor.charactersTileset == null) {
      CharacterEditor.charactersTileset = charactersTileset;
    }
    party = new Party(target);
    characterList = new ArrayList<Character>();
    Point location = parent.getLocationOnScreen();
    location.x = location.x+parent.getWidth()/10;
    location.y = location.y+parent.getHeight()/9;
    this.setLocation(location);
    this.setSize(750, 550);
    this.setResizable(false);
    this.setTitle("Character Editor");
    this.setModal(true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    currentItem = ItemFactory.Create(currentItemIndex);
    currentItemIndex = 0;
    lastCharIndex = 0;
    readSavedCharacters();
    wayPoints = listOfWaypoints;
    if (wayPoints == null) {
      wayPoints = new String[1];
      wayPoints[0] = "";
    }
    
    
    main = new JPanel();
    main.setLayout(new BorderLayout());
    this.add(main);
    if (target == null) {
       topPanel = createTopPanel();
       main.add(topPanel, BorderLayout.NORTH);
    } else {
      topPanel = createTopPanel();
      main.add(topPanel, BorderLayout.NORTH);
      currentChar = target;      
    }
    bottomPanel = createBottomPanel();
    basicCharPanel = createCharBasicPanel();
    perkPanel = createPerkPanel();
    inventoryPanel = createInventoryPanel();
    timeTablePanel = createTimeTablePanel();
    spellPanel = createSpellPanel();
    JPanel timeTableEditorBtns = new JPanel();
    timeTableEditorBtns.setLayout(new GridLayout(1,4));
    JButton taskAdd = new JButton("Add task");
    taskAdd.setActionCommand("TaskAdd");
    taskAdd.addActionListener(this);
    JButton taskInsert = new JButton("Insert task");
    taskInsert.setActionCommand("InsertTask");
    taskInsert.addActionListener(this);
    JButton taskDelete = new JButton("Delete task");
    taskDelete.setActionCommand("DeleteTask");
    taskDelete.addActionListener(this);
    JButton taskRemove = new JButton("Remove task");
    taskRemove.setActionCommand("TaskRemove");
    taskRemove.addActionListener(this);
    timeTableEditorBtns.add(taskAdd);
    timeTableEditorBtns.add(taskInsert);
    timeTableEditorBtns.add(taskDelete);
    timeTableEditorBtns.add(taskRemove);
    JPanel timeTableEditor = new JPanel();
    timeTableEditor.setLayout(new BorderLayout());
    JScrollPane scroll = new JScrollPane(timeTablePanel);
    timeTableEditor.add(scroll, BorderLayout.CENTER);
    timeTableEditor.add(timeTableEditorBtns, BorderLayout.SOUTH);
    updateTimeTablePanel();
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Common", basicCharPanel);
    tabbedPane.addTab("Perks", perkPanel);
    tabbedPane.addTab("Inventory", inventoryPanel);
    tabbedPane.addTab("TimeTable", timeTableEditor);
    tabbedPane.addTab("SpellList", spellPanel);
    main.add(tabbedPane,BorderLayout.CENTER);
    main.add(bottomPanel,BorderLayout.SOUTH);
    updateAllValues();
    this.setVisible(true);    
  }

  /**
   * Read save characters for character list file
   */
  private void readSavedCharacters() {
    DataInputStream is;
    InputStream tmpIs = CharacterEditor.class.getResourceAsStream(CHARACTER_LIST_FILE);
    if (tmpIs != null) {
	    is = new DataInputStream(tmpIs);
	    try {
	      byte[] magicBytes = {67,72,82,76,73,83,84};
	      for (int i = 0;i<7;i++) {
	       byte byt = is.readByte();
	       if (byt != magicBytes[i]) {
	         is.close();
	         throw new IOException("Not character list!");
	       }
	      }
	      int size = is.readInt();
	      if (size > 0) {
	        characterList.clear();
	        for (int i=0;i<size;i++) {
	          Character tmpChr = new Character(0);
	          tmpChr.loadCharacter(is);
	          characterList.add(tmpChr);
	          lastCharIndex=i;
	        }
	        currentChar = characterList.get(0);
	      } else {
	        is.close();
	        throw new IOException("No characters in list!");
	      }
	      
	      is.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	      currentChar = new Character(0);
	      characterList.add(currentChar);
	    }
    } else {
	    currentChar = new Character(0);
	    characterList.add(currentChar);    	
    }
  }
  
  private void updateTimeTablePanel() {
    int tasksNum = currentChar.getNumberOfTasks();
    timeFields = new JTextField[tasksNum+1];
    taskFields = new JComboBox[tasksNum+1];
    waypointFields = new JComboBox[tasksNum+1];
    descriptionFields = new JTextField[tasksNum+1];
    timeTablePanel.removeAll();
    JLabel label = new JLabel("Time");
    timeTablePanel.add(label);
    label = new JLabel("Task");
    timeTablePanel.add(label);
    label = new JLabel("Waypoint");
    timeTablePanel.add(label);
    label = new JLabel("Description");
    timeTablePanel.add(label);
    if (tasksNum > 0) {
      for (int i=0;i<tasksNum;i++) {
       CharTask task = currentChar.getTaskByIndex(i);
       if (task != null) {
         timeFields[i] = new JTextField(task.getTime());
         timeFields[i].setColumns(15);
         timeFields[i].addMouseListener(this);
         timeTablePanel.add(timeFields[i]);       
         taskFields[i] = new JComboBox(CharTask.TASKS);
         taskFields[i].setSelectedItem(task.getTask());
         taskFields[i].addMouseListener(this);
         timeTablePanel.add(taskFields[i]);       
         waypointFields[i] = new JComboBox(wayPoints);
         waypointFields[i].setEditable(true);
         waypointFields[i].setSelectedItem(task.getWayPointName());
         waypointFields[i].addMouseListener(this);
         timeTablePanel.add(waypointFields[i]);       
         descriptionFields[i] = new JTextField(task.getDescription());
         descriptionFields[i].setColumns(15);
         descriptionFields[i].addMouseListener(this);
         timeTablePanel.add(descriptionFields[i]);       
       }
      }
    }
    timeFields[tasksNum] = new JTextField(" ");
    timeTablePanel.add(timeFields[tasksNum]);       
    taskFields[tasksNum] = new JComboBox(CharTask.TASKS);
    timeTablePanel.add(taskFields[tasksNum]);       
    waypointFields[tasksNum] = new JComboBox(wayPoints);
    waypointFields[tasksNum].setEditable(true);
    timeTablePanel.add(waypointFields[tasksNum]);       
    descriptionFields[tasksNum] = new JTextField(" ");
    timeTablePanel.add(descriptionFields[tasksNum]);       
    
  }
  
  private void updateTimeTablePanelAddNew(int index) {
    CharTask newTask = new CharTask("-",CharTask.TASK_MOVE,"",""); 
    currentChar.addTask(newTask,index);
    int tasksNum = currentChar.getNumberOfTasks();
    timeFields = new JTextField[tasksNum+1];
    taskFields = new JComboBox[tasksNum+1];
    waypointFields = new JComboBox[tasksNum+1];
    descriptionFields = new JTextField[tasksNum+1];
    timeTablePanel.removeAll();
    JLabel label = new JLabel("Time");
    timeTablePanel.add(label);
    label = new JLabel("Task");
    timeTablePanel.add(label);
    label = new JLabel("Waypoint");
    timeTablePanel.add(label);
    label = new JLabel("Description");
    timeTablePanel.add(label);
    if (tasksNum > 0) {
      for (int i=0;i<tasksNum;i++) {
       CharTask task = currentChar.getTaskByIndex(i);
       if (task != null) {
         timeFields[i] = new JTextField(task.getTime());
         timeFields[i].setColumns(15);
         timeTablePanel.add(timeFields[i]);       
         taskFields[i] = new JComboBox(CharTask.TASKS);
         taskFields[i].setSelectedItem(task.getTask());
         timeTablePanel.add(taskFields[i]);       
         waypointFields[i] = new JComboBox(wayPoints);
         waypointFields[i].setEditable(true);
         waypointFields[i].setSelectedItem(task.getWayPointName());
         timeTablePanel.add(waypointFields[i]);       
         descriptionFields[i] = new JTextField(task.getDescription());
         descriptionFields[i].setColumns(15);
         timeTablePanel.add(descriptionFields[i]);       
       }
      }
    }
    timeFields[tasksNum] = new JTextField(" ");
    timeTablePanel.add(timeFields[tasksNum]);       
    taskFields[tasksNum] = new JComboBox(CharTask.TASKS);
    timeTablePanel.add(taskFields[tasksNum]);       
    waypointFields[tasksNum] = new JComboBox(wayPoints);
    waypointFields[tasksNum].setEditable(true);
    timeTablePanel.add(waypointFields[tasksNum]);       
    descriptionFields[tasksNum] = new JTextField(" ");
    timeTablePanel.add(descriptionFields[tasksNum]);       
    
  }

  private void updateTimeTablePanelDelete(int index) {
    currentChar.removeTaskByIndex(index);
    int tasksNum = currentChar.getNumberOfTasks();
    timeFields = new JTextField[tasksNum+1];
    taskFields = new JComboBox[tasksNum+1];
    waypointFields = new JComboBox[tasksNum+1];
    descriptionFields = new JTextField[tasksNum+1];
    timeTablePanel.removeAll();
    JLabel label = new JLabel("Time");
    timeTablePanel.add(label);
    label = new JLabel("Task");
    timeTablePanel.add(label);
    label = new JLabel("Waypoint");
    timeTablePanel.add(label);
    label = new JLabel("Description");
    timeTablePanel.add(label);
    if (tasksNum > 0) {
      for (int i=0;i<tasksNum;i++) {
       CharTask task = currentChar.getTaskByIndex(i);
       if (task != null) {
         timeFields[i] = new JTextField(task.getTime());
         timeFields[i].setColumns(15);
         timeTablePanel.add(timeFields[i]);       
         taskFields[i] = new JComboBox(CharTask.TASKS);
         taskFields[i].setSelectedItem(task.getTask());
         timeTablePanel.add(taskFields[i]);       
         waypointFields[i] = new JComboBox(wayPoints);
         waypointFields[i].setEditable(true);
         waypointFields[i].setSelectedItem(task.getWayPointName());
         timeTablePanel.add(waypointFields[i]);       
         descriptionFields[i] = new JTextField(task.getDescription());
         descriptionFields[i].setColumns(15);
         timeTablePanel.add(descriptionFields[i]);       
       }
      }
    }
    timeFields[tasksNum] = new JTextField(" ");
    timeTablePanel.add(timeFields[tasksNum]);       
    taskFields[tasksNum] = new JComboBox(CharTask.TASKS);
    timeTablePanel.add(taskFields[tasksNum]);       
    waypointFields[tasksNum] = new JComboBox(wayPoints);
    waypointFields[tasksNum].setEditable(true);
    timeTablePanel.add(waypointFields[tasksNum]);       
    descriptionFields[tasksNum] = new JTextField(" ");
    timeTablePanel.add(descriptionFields[tasksNum]);       
    
  }

  
  private JPanel createSpellPanel() {
    JPanel result = new JPanel();
    result.setLayout(new BorderLayout());
    spellList = new JList();
    result.add(spellList,BorderLayout.CENTER);
    JPanel bottom = new JPanel();
    bottom.setLayout(new BorderLayout());
    spellCB = new JComboBox(SpellFactory.SPELL_LIST);
    bottom.add(spellCB,BorderLayout.WEST);
    JButton addBtn = new JButton("Add spell");
    addBtn.setActionCommand("AddSpell");
    addBtn.addActionListener(this);
    bottom.add(addBtn,BorderLayout.CENTER);
    JButton removeBtn = new JButton("Remove spell");
    removeBtn.setActionCommand("RemoveSpell");
    removeBtn.addActionListener(this);
    bottom.add(removeBtn,BorderLayout.EAST);
    result.add(bottom,BorderLayout.SOUTH);
    return result;
  }

  private JPanel createTimeTablePanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0, 4));
    JLabel label = new JLabel("Time");
    result.add(label);
    label = new JLabel("Task");
    result.add(label);
    label = new JLabel("Waypoint");
    result.add(label);
    label = new JLabel("Description");
    result.add(label);
    
    return result;
  }
  
  private JPanel createBackPackPanel() {
    JPanel result = new JPanel();
    result.setBorder(BorderFactory.createLineBorder(Color.black));
    result.setLayout(new BorderLayout());
    JLabel backpackLabel = new JLabel("BackPack:                           ");
    result.add(backpackLabel,BorderLayout.NORTH);
    inventoryList = new JList();
    JScrollPane scroll = new JScrollPane(inventoryList);
    result.add(scroll,BorderLayout.CENTER);
    JPanel bottom =  new JPanel();
    bottom.setLayout(new GridLayout(1,0));
    JButton equipBtn = new JButton("Equip");
    equipBtn.addActionListener(this);
    equipBtn.setActionCommand("EquipItem");
    bottom.add(equipBtn);
    JButton removeBtn = new JButton("Remove");
    removeBtn.addActionListener(this);
    removeBtn.setActionCommand("RemoveItem");
    bottom.add(removeBtn);
    result.add(bottom,BorderLayout.SOUTH);
    return result;
  }
  
  private JPanel createEquipedItemsPanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0, 2, 5, 5));
    JLabel label;
    label = new JLabel("Main hand:");
    result.add(label);
    firstHandBtn = new JButton("Empty");
    firstHandBtn.setEnabled(false);
    firstHandBtn.setActionCommand("UnequipFirstHand");
    firstHandBtn.addActionListener(this);
    result.add(firstHandBtn);
    label = new JLabel("Off hand:");
    result.add(label);
    secondHandBtn = new JButton("Empty");
    secondHandBtn.setEnabled(false);
    secondHandBtn.setActionCommand("UnequipSecondHand");
    secondHandBtn.addActionListener(this);
    result.add(secondHandBtn);
    label = new JLabel("Armor:");
    result.add(label);
    armorBtn = new JButton("Empty");
    armorBtn.setEnabled(false);
    armorBtn.setActionCommand("UnequipArmor");
    armorBtn.addActionListener(this);
    result.add(armorBtn);
    label = new JLabel("Head gear:");
    result.add(label);
    headGearBtn = new JButton("Empty");
    headGearBtn.setEnabled(false);
    headGearBtn.setActionCommand("UnequipHeadGear");
    headGearBtn.addActionListener(this);
    result.add(headGearBtn);
    label = new JLabel("Amulet:");
    result.add(label);
    amuletBtn = new JButton("Empty");
    amuletBtn.setEnabled(false);
    amuletBtn.setActionCommand("UnequipAmulet");
    amuletBtn.addActionListener(this);
    result.add(amuletBtn);
    label = new JLabel("Boots:");
    result.add(label);
    bootsBtn = new JButton("Empty");
    bootsBtn.setEnabled(false);
    bootsBtn.setActionCommand("UnequipBoots");
    bootsBtn.addActionListener(this);
    result.add(bootsBtn);
    label = new JLabel("Ring:");
    result.add(label);
    ringBtn = new JButton("Empty");
    ringBtn.setEnabled(false);
    ringBtn.setActionCommand("UnequipRing");
    ringBtn.addActionListener(this);
    result.add(ringBtn);
    return result;
  }
  
  private JPanel createInformationPanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0,2,5,5));
    JLabel label;
    label = new JLabel("Main Attack:");
    result.add(label);
    mainAttackLabel = new JLabel("No attack");    
    result.add(mainAttackLabel);
    label = new JLabel("Secondary Attack:");
    result.add(label);
    secondaryAttackLabel = new JLabel("No attack");
    result.add(secondaryAttackLabel);
    defenseLabel = new JLabel("Defense");
    result.add(defenseLabel);
    return result;
  }
  
  private JPanel createInventoryPanel() {
    JPanel result = new JPanel();
    result.setLayout(new BorderLayout());
    result.add(createBackPackPanel(),BorderLayout.WEST);
    result.add(createInformationPanel(),BorderLayout.EAST);
    result.add(createEquipedItemsPanel(),BorderLayout.CENTER);
    JPanel bottom = new JPanel();
    bottom.setLayout(new BorderLayout());
    JPanel shopKeeperPanel = new JPanel();
    shopKeeperPanel.setLayout(new GridLayout(1, 0));
    JButton updateShopKeeper = new JButton("Update shopkeeper list");
    updateShopKeeper.addActionListener(this);
    updateShopKeeper.setActionCommand("UpdateShopkeeperList");
    shopKeeperPanel.add(updateShopKeeper,BorderLayout.WEST);
    shopKeeperText = new JTextField(" ");
    shopKeeperPanel.add(shopKeeperText,BorderLayout.EAST);
      
    JPanel bottomBtnPanel = new JPanel();
    bottomBtnPanel.setLayout(new GridLayout(1, 0));
    JButton prevItem = new JButton("Prev");
    prevItem.addActionListener(this);
    prevItem.setActionCommand("PrevItem");
    bottomBtnPanel.add(prevItem);
    newItemLabel = new JLabel(currentItem.getName());
    bottomBtnPanel.add(newItemLabel);
    JButton addItem = new JButton("Add");
    addItem.addActionListener(this);
    addItem.setActionCommand("AddItem");
    bottomBtnPanel.add(addItem);
    JButton nextItem = new JButton("Next");
    nextItem.addActionListener(this);
    nextItem.setActionCommand("NextItem");
    bottomBtnPanel.add(nextItem);
    
    bottom.add(bottomBtnPanel,BorderLayout.NORTH);
    bottom.add(shopKeeperPanel,BorderLayout.SOUTH);
    result.add(bottom,BorderLayout.SOUTH);
    return result;
  }
  
  /**
   * Create the very top panel 
   * @return JPanel
   */
  private JPanel createTopPanel() {
    JPanel result = new JPanel();
    characterLabel = new JLabel(String.valueOf(characterIndex));
    JButton btnPrev = new JButton("Previous");
    btnPrev.setActionCommand(ACTION_PREV_CHAR);
    btnPrev.addActionListener(this);
    JButton btnNext = new JButton("Next");
    btnNext.setActionCommand(ACTION_NEXT_CHAR);
    btnNext.addActionListener(this);
    JButton btnSave = new JButton("Save All");
    btnSave.setActionCommand(ACTION_SAVE_ALL);
    btnSave.addActionListener(this);
    result.add(btnPrev);
    result.add(characterLabel);
    result.add(btnNext);
    result.add(btnSave);
    return result;
  }

  /**
   * Create the bottom main panel
   * @return
   */
  private JPanel createBottomPanel() {
    JPanel result = new JPanel();
    JButton btnSelect = new JButton("Select Character");
    btnSelect.setActionCommand(ACTION_SELECT_CHAR);
    btnSelect.addActionListener(this);
    result.add(btnSelect);
    return result;
  }
  
  private JPanel createCharBasicPanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0, 1));
    
    basicCharTopPanel = new CharacterEditorCommonPanel(charactersTileset);
    basicCharTopPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    basicCharTopPanel.add(new JLabel("Name:"));
    nameText = new JTextField("Joe");
    Dimension textDimension = new Dimension(150, 20);
    nameText.setMinimumSize(textDimension);
    nameText.setSize(textDimension);
    nameText.setPreferredSize(textDimension);
    basicCharTopPanel.add(nameText);
    basicCharTopPanel.add(Box.createRigidArea(new Dimension(20,20)));
    JButton changeFaceBtn = new JButton("Change Face");
    changeFaceBtn.setActionCommand(ACTION_CHANGE_FACE);
    changeFaceBtn.addActionListener(this);
    basicCharTopPanel.add(changeFaceBtn);
    basicCharTopPanel.add(Box.createRigidArea(new Dimension(80,70)));

    JButton prevTileBtn = new JButton("-");
    prevTileBtn.setActionCommand(ACTION_PREV_TILE);
    prevTileBtn.addActionListener(this);
    basicCharTopPanel.add(prevTileBtn);
    basicCharTopPanel.add(Box.createRigidArea(new Dimension(50,70)));
    JButton nextTileBtn = new JButton("+");
    nextTileBtn.setActionCommand(ACTION_NEXT_TILE);
    nextTileBtn.addActionListener(this);
    basicCharTopPanel.add(nextTileBtn);
    basicCharTopPanel.add(Box.createRigidArea(new Dimension(80,70)));
    basicCharTopPanel.add(new JLabel("Full name:"));
    longNameText = new JTextField("Joe Doe");
    longNameText.setMinimumSize(textDimension);
    longNameText.setSize(textDimension);
    longNameText.setPreferredSize(textDimension);
    basicCharTopPanel.add(longNameText);
    basicCharTopPanel.add(Box.createRigidArea(new Dimension(10,20)));
    basicCharTopPanel.add(new JLabel("Money:"));
    moneySpin = new NumberSpinner(0, 0, 1000000, 1);
    basicCharTopPanel.add(moneySpin);
    basicCharTopPanel.add(Box.createRigidArea(new Dimension(10,20)));
    basicCharTopPanel.add(new JLabel("Description:"));
    characterDescField = new JTextField("");
    characterDescField.setPreferredSize(new Dimension(150, 20));
    basicCharTopPanel.add(characterDescField);
    JButton updateBtn = new JButton("Update");
    updateBtn.addActionListener(this);
    updateBtn.setActionCommand(ACTION_UPDATE_CHAR);
    basicCharTopPanel.add(updateBtn);
    levelLabel = new JLabel("LevelLabel");
    levelLabel.setText(currentChar.calculateNPCLevelAndExperienceAsString());
    basicCharTopPanel.add(levelLabel);
    hpLabel = new JLabel("HP: Stamina:");
    hpLabel.setText("HP:"+String.valueOf(currentChar.getMaxHP())+" Stamina:"+
        String.valueOf(currentChar.getMaxStamina()));
    basicCharTopPanel.add(hpLabel);
    JButton turnBtn = new JButton("Turn");
    turnBtn.addActionListener(this);
    turnBtn.setActionCommand(ACTION_TURN);
    basicCharTopPanel.add(turnBtn);
    cbHostilityLevel = new JComboBox(HOSTILITY_LEVEL_STRINGS);
    basicCharTopPanel.add(cbHostilityLevel);

    //result.add(basicCharTopPanel,BorderLayout.NORTH);
    result.add(basicCharTopPanel);
    basicCharTopPanel.updateImages(currentChar.getHeadTile(), currentChar.getBodyTile(),currentChar.getFaceNumber());

    JButton nameBtn = new JButton("Random Male name");
    nameBtn.addActionListener(this);
    nameBtn.setActionCommand(ACTION_RANDOMMALE);
    basicCharTopPanel.add(nameBtn);
    nameBtn = new JButton("Random Female name");
    nameBtn.addActionListener(this);
    nameBtn.setActionCommand(ACTION_RANDOMFEMALE);
    basicCharTopPanel.add(nameBtn);
    
    JPanel center = new JPanel();
    center.setLayout(new GridLayout(1, 0));
    center.add(attributePanel());
    center.add(skillPanel());
    //result.add(center,BorderLayout.CENTER);
    result.add(center);
    return result;
  }
  
  /**
   * Create Attribute panel
   * @return JPanel
   */
  private JPanel attributePanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0, 2,5,5));
    result.setBorder(BorderFactory.createLineBorder(Color.black));
    attrSet = new NumberSpinner[Character.MAX_NUMBERS_OF_ATTRIBUTES];
    for (int i=0;i<Character.MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      attrSet[i] = new NumberSpinner(5, 1, 10, 1);
      result.add(new JLabel(Character.getAttributeName(i)));
      result.add(attrSet[i]);
    }
    
    return result;
  }
  
  /**
   * Create Skill Panel
   * @return JPanel
   */
  private JPanel skillPanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0, 3,5,5));
    result.setBorder(BorderFactory.createLineBorder(Color.black));
    skillSet = new NumberSpinner[Character.MAX_NUMBERS_OF_SKILL];
    skillLabel = new JLabel[Character.MAX_NUMBERS_OF_SKILL];
    for (int i=0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
      skillSet[i] = new NumberSpinner(0, 0, 100, 1);
      skillLabel[i] = new JLabel("Update");
      result.add(new JLabel(Character.getSkillName(i)));
      result.add(skillSet[i]);
      result.add(skillLabel[i]);
    }

    return result;    
  }
  
  private JPanel createPerkPanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0, 3,5,5));
    result.setBorder(BorderFactory.createLineBorder(Color.black));
    cbPerks = new JCheckBox[Perks.ALL_PERK_NAMES.length];
    for (int i=0;i<Perks.ALL_PERK_NAMES.length;i++) {
      cbPerks[i] = new JCheckBox(Perks.ALL_PERK_NAMES[i], false);
      cbPerks[i].setToolTipText(Perks.ALL_PERK_NAMES_DESC[i]);
      result.add(cbPerks[i]);
    }
    
    return result;
  }
  
  private void updateAllValues() {
    characterLabel.setText(String.valueOf(characterIndex));
    for (int i=0;i<Character.MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      attrSet[i].setSpinValue(currentChar.getAttribute(i));  
    }
    for (int i=0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
      skillSet[i].setSpinValue(currentChar.getSkill(i));  
    }
    for (int i=0;i<Perks.ALL_PERK_NAMES.length;i++) {
      Perks charPerks = currentChar.getPerks();
      cbPerks[i].setSelected(charPerks.getPerk(i));
    }
    nameText.setText(currentChar.getName());
    longNameText.setText(currentChar.getLongName());
    characterDescField.setText(currentChar.getDescription());
    moneySpin.setSpinValue(currentChar.getMoney());
    for (int i=0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
      skillLabel[i].setText("Total:"+currentChar.getEffectiveSkill(i));
    }
    cbHostilityLevel.setSelectedIndex(currentChar.getHostilityLevel());
    updateTimeTablePanel();
    updateInventoryList();
    updateSpellList();
    levelLabel.setText(currentChar.calculateNPCLevelAndExperienceAsString());
    basicCharTopPanel.updateImages(currentChar.getHeadTile(), currentChar.getBodyTile(),currentChar.getFaceNumber());
    levelLabel.setText(currentChar.calculateNPCLevelAndExperienceAsString());
    hpLabel.setText("HP:"+String.valueOf(currentChar.getMaxHP())+" Stamina:"+
        String.valueOf(currentChar.getMaxStamina()));
    repaint();
  }
  
  private void getAllValues() {
    for (int i=0;i<Character.MAX_NUMBERS_OF_ATTRIBUTES;i++) {
      currentChar.setAttribute(i, attrSet[i].getSpinValue());  
    }
    for (int i=0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
      currentChar.setSkill(i, skillSet[i].getSpinValue());  
    }
    for (int i=0;i<Perks.ALL_PERK_NAMES.length;i++) {
      Perks charPerks = currentChar.getPerks();
      charPerks.setPerk(i, cbPerks[i].isSelected());
    }
    currentChar.setName(nameText.getText());
    currentChar.setLongName(longNameText.getText());
    currentChar.setDescription(characterDescField.getText());
    currentChar.setHostilityLevel(cbHostilityLevel.getSelectedIndex());
    currentChar.setMoney(moneySpin.getSpinValue());
    if (currentChar.getNumberOfTasks()>0) {      
      for (int i=0;i<currentChar.getNumberOfTasks();i++) {
        CharTask task = currentChar.getTaskByIndex(i);
        task.setTime(timeFields[i].getText());
        task.setTask((String) taskFields[i].getSelectedItem());
        task.setWayPointName((String) waypointFields[i].getSelectedItem());
        task.setDescription(descriptionFields[i].getText());
      }
    }
    currentChar.calculateNPCLevelAndExperience();
    currentChar.setCurrentHP(currentChar.getMaxHP());
    currentChar.setCurrentSP(currentChar.getMaxStamina());
    levelLabel.setText(currentChar.calculateNPCLevelAndExperienceAsString());
    hpLabel.setText("HP:"+String.valueOf(currentChar.getMaxHP())+" Stamina:"+
        String.valueOf(currentChar.getMaxStamina()));

  }
  
  private void updateTaskList(boolean delete) {
    if (delete) {
      int i =currentChar.getNumberOfTasks();
      if (i>0) {
        currentChar.removeTaskByIndex(i-1);
      }
    } else {
      if (currentChar.getNumberOfTasks()>0) {      
        for (int i=0;i<currentChar.getNumberOfTasks();i++) {
          CharTask task = currentChar.getTaskByIndex(i);
          task.setTime(timeFields[i].getText());
          task.setTask((String) taskFields[i].getSelectedItem());
          task.setWayPointName((String) waypointFields[i].getSelectedItem());
          task.setDescription(descriptionFields[i].getText());
        }
      }
      String tmp = timeFields[timeFields.length-1].getText();
      if (!tmp.isEmpty() && !tmp.equals(" ")) {
        int i = timeFields.length-1;
        CharTask task = new CharTask(timeFields[i].getText(), 
            (String) taskFields[i].getSelectedItem(), 
            (String) waypointFields[i].getSelectedItem(), 
            descriptionFields[i].getText());
        currentChar.addTask(task);
      }
    }
    updateTimeTablePanel();
    repaint();
  }
  
  private void updateSpellList() {
    spellList.setListData(new Vector<String>());
    if (currentChar.getSpellList().size() >0) {
      String[] spellArr = new String[currentChar.getSpellList().size()];
      for (int i=0;i<currentChar.getSpellList().size();i++) {
        spellArr[i] = currentChar.getSpellList().get(i);
      }
      spellList.setListData(spellArr);
    } 
  }
  
  private void updateInventoryList() {
    inventoryList.setListData(new Vector<Item>());
    if (currentChar.inventorySize()>0) {
      Item[] itemList = new Item[currentChar.inventorySize()];
      for (int i=0;i<currentChar.inventorySize();i++) {
        itemList[i] = currentChar.inventoryGetIndex(i);
      }
      inventoryList.setListData(itemList);
    } 
    if (currentChar.inventoryGetFirstHand() != null) {
      firstHandBtn.setEnabled(true);
      firstHandBtn.setText(currentChar.inventoryGetFirstHand().getItemNameInGame());
    } else {
      firstHandBtn.setEnabled(false);
      firstHandBtn.setText("empty");
    }
    if (currentChar.inventoryGetSecondHand() != null) {
      secondHandBtn.setEnabled(true);
      secondHandBtn.setText(currentChar.inventoryGetSecondHand().getItemNameInGame());
    } else {
      secondHandBtn.setEnabled(false);
      secondHandBtn.setText("empty");
    }
    if (currentChar.inventoryGetArmor() != null) {
      armorBtn.setEnabled(true);
      armorBtn.setText(currentChar.inventoryGetArmor().getItemNameInGame());
    } else {
      armorBtn.setEnabled(false);
      armorBtn.setText("empty");
    }
    if (currentChar.inventoryGetAmulet() != null) {
      amuletBtn.setEnabled(true);
      amuletBtn.setText(currentChar.inventoryGetAmulet().getItemNameInGame());
    } else {
      amuletBtn.setEnabled(false);
      amuletBtn.setText("empty");
    }
    if (currentChar.inventoryGetBoots() != null) {
      bootsBtn.setEnabled(true);
      bootsBtn.setText(currentChar.inventoryGetBoots().getItemNameInGame());
    } else {
      bootsBtn.setEnabled(false);
      bootsBtn.setText("empty");
    }
    if (currentChar.inventoryGetHeadGear() != null) {
      headGearBtn.setEnabled(true);
      headGearBtn.setText(currentChar.inventoryGetHeadGear().getItemNameInGame());
    } else {
      headGearBtn.setEnabled(false);
      headGearBtn.setText("empty");
    }
    if (currentChar.inventoryGetRing() != null) {
      ringBtn.setEnabled(true);
      ringBtn.setText(currentChar.inventoryGetRing().getItemNameInGame());
    } else {
      ringBtn.setEnabled(false);
      ringBtn.setText("empty");
    }
    mainAttackLabel.setText(currentChar.getFirstAttack().getAttackAsHTMLString());
    if (currentChar.getSecondAttack() != null) {
      secondaryAttackLabel.setText(currentChar.getSecondAttack().getAttackAsHTMLString());
    } else {
      secondaryAttackLabel.setText("No attack");
    }
    defenseLabel.setText(currentChar.getDefense().getDefenseAsHTMLString());
  }
  
  /**
   * Return edited character, notice that coordinates are still wrong
   * @return Character null if not selected character clicked
   */
  public Character getEditedCharacter() {
    if (selectCharacterClicked) {
      return currentChar;
    } else {
      return null;
    }
  }
  
  @Override
  public void actionPerformed(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase("UpdateShopkeeperList")) {
      StringBuilder sb = new StringBuilder(50);
      sb.append("#");
      for (int i=0;i<currentChar.inventorySize();i++) {
        Item item = currentChar.inventoryGetIndex(i);
        if (item != null) {
          sb.append(item.getName());
          if (i<currentChar.inventorySize()-1) {
            sb.append("|");
          }
        }
      }
      shopKeeperText.setText(sb.toString());
    }
    if (arg0.getActionCommand().equalsIgnoreCase("AddSpell")) {
      String spell = (String) spellCB.getSelectedItem();
      if (spell != null) {
        currentChar.addSpell(spell);
        updateSpellList();
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase("RemoveSpell")) {
      if (spellList.getSelectedIndex()!=-1) {
        if (spellList.getSelectedValue() != null) {
          currentChar.removeSpell((String) spellList.getSelectedValue());
          updateSpellList();
        }
      }
    }
    if (arg0.getActionCommand().equals(ACTION_RANDOMMALE)) {
      String tmp = MapUtilities.getMaleName();
      String parts[] = tmp.split(" ");
      nameText.setText(parts[0]);
      longNameText.setText(tmp);
      getAllValues();
    }
    if (arg0.getActionCommand().equals(ACTION_RANDOMFEMALE)) {
      String tmp = MapUtilities.getFemaleName();
      String parts[] = tmp.split(" ");
      nameText.setText(parts[0]);
      longNameText.setText(tmp);
      getAllValues();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_UPDATE_CHAR)) {
      getAllValues();
      for (int i=0;i<Character.MAX_NUMBERS_OF_SKILL;i++) {
        skillLabel[i].setText("Total:"+currentChar.getEffectiveSkill(i));
      }
      updateInventoryList();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_CHANGE_FACE)) {
      CharacterFaceSelection faceSelect = new CharacterFaceSelection(null);
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }
      currentChar.setFaceNumber(faceSelect.getSelected());
        basicCharTopPanel.updateImages(currentChar.getHeadTile(), currentChar.getBodyTile(),currentChar.getFaceNumber());
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_PREV_ITEM)) {
      if (currentItemIndex > 0) {
        currentItemIndex--;
        currentItem = ItemFactory.Create(currentItemIndex);
        newItemLabel.setText(currentItem.getName());
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_TURN)) {
      byte heading = currentChar.getHeading();
      heading++;
      if (heading > CharacterAnimation.DIRECTION_WEST) {
        heading = CharacterAnimation.DIRECTION_NORTH;
      }
      currentChar.setHeading(heading);
      basicCharTopPanel.updateImages(currentChar.getHeadTile(), currentChar.getBodyTile(),currentChar.getFaceNumber());
      
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_NEXT_TILE)) {
      int index = currentChar.getTileOffset();      
      int series = charactersTileset.getSeries(index);
      series++;
      index = charactersTileset.findSeriesFirst(series);
      if (index != -1) {
        currentChar.setTileOffset(index);
        if (charactersTileset.getSeriesSize(series)==12) {
          currentChar.setType(CharacterAnimation.ANIMATION_TYPE_SMALL);
        }
        if (charactersTileset.getSeriesSize(series)==24) {
          currentChar.setType(CharacterAnimation.ANIMATION_TYPE_NORMAL);
        }
        basicCharTopPanel.updateImages(currentChar.getHeadTile(), currentChar.getBodyTile(),currentChar.getFaceNumber());
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_PREV_TILE)) {
      int index = currentChar.getTileOffset();      
      int series = charactersTileset.getSeries(index);
      series--;
      index = charactersTileset.findSeriesFirst(series);
      if (index != -1) {
        currentChar.setTileOffset(index);
        if (charactersTileset.getSeriesSize(series)==12) {
          currentChar.setType(CharacterAnimation.ANIMATION_TYPE_SMALL);
        }
        if (charactersTileset.getSeriesSize(series)==24) {
          currentChar.setType(CharacterAnimation.ANIMATION_TYPE_NORMAL);
        }
        basicCharTopPanel.updateImages(currentChar.getHeadTile(), currentChar.getBodyTile(),currentChar.getFaceNumber());
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_NEXT_ITEM)) {
        currentItemIndex++;
        currentItem = ItemFactory.Create(currentItemIndex);
        if (currentItem != null) {
          newItemLabel.setText(currentItem.getName());
        } else {
          currentItemIndex--;
          currentItem = ItemFactory.Create(currentItemIndex);
          newItemLabel.setText(currentItem.getName());
        }          
    }
    if (arg0.getActionCommand().equalsIgnoreCase("TaskAdd")) {
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }
      updateTaskList(false);
      lastTaskIndex = -1;
    }
    if (arg0.getActionCommand().equalsIgnoreCase("InsertTask")) {
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }
      
      if (lastTaskIndex != -1) {
        updateTimeTablePanelAddNew(lastTaskIndex);
        updateTaskList(false);
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase("deleteTask")) {
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }
      if (lastTaskIndex != -1) {
        updateTimeTablePanelDelete(lastTaskIndex);
        updateTaskList(false);
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase("TaskRemove")) {
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }
      updateTaskList(true);
      lastTaskIndex = -1;
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_PREV_CHAR)) {
      getAllValues();
      if (characterIndex > 0) {
        characterIndex--;
        currentChar = characterList.get(characterIndex);
      }
      updateAllValues();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_NEXT_CHAR)) {
      getAllValues();      
      if (characterIndex < lastCharIndex) {
        characterIndex++;
        if (characterIndex <characterList.size()) {
          currentChar = characterList.get(characterIndex);
        } else {
          characterIndex--;
        }
      } else {
        if (characterIndex == lastCharIndex) {
          characterIndex++;
          currentChar = new Character(0);
          characterList.add(currentChar);
        } else {
          // Do nothing
        }
      }
      updateAllValues();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_SELECT_CHAR)) {
      getAllValues();  
      selectCharacterClicked = true;
      this.setVisible(false);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(ACTION_SAVE_ALL)) {
      DataOutputStream os;
      JFileChooser fcSave = new JFileChooser();
      fcSave.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      int returnVal = fcSave.showSaveDialog(this);
      if (returnVal == JFileChooser.OPEN_DIALOG) {
          String saveStr = fcSave.getSelectedFile().getAbsolutePath();
        try {
          os = new DataOutputStream(new FileOutputStream(saveStr));
          try {
            os.writeBytes("CHRLIST");
            os.writeInt(characterList.size());
            for (int i=0;i<characterList.size();i++) {
              Character tmpChr = characterList.get(i);
              tmpChr.saveCharacter(os);
            }
            
            os.close();
          } catch (IOException e) {
            e.printStackTrace();
          }  
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase("AddItem")) {
      currentChar.inventoryPickUpItemForce(currentItem); 
      updateInventoryList();
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase("RemoveItem")) {
      int i =inventoryList.getSelectedIndex();
      if (currentChar.inventoryRemoveItem(i)) {
        updateInventoryList();
        if (characterIndex == lastCharIndex+1) {
          lastCharIndex = characterIndex;
        }

      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase("EquipItem")) {
      int i =inventoryList.getSelectedIndex();
      if (currentChar.equipItemByIndex(i,party)) {
        updateInventoryList();
        if (characterIndex == lastCharIndex+1) {
          lastCharIndex = characterIndex;
        }

      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase("UnequipFirstHand")) {
      currentChar.unequipFirstHand();
      updateInventoryList();
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }

    }
    if (arg0.getActionCommand().equalsIgnoreCase("UnequipSecondHand")) {
      currentChar.unequipSecondHand();
      updateInventoryList();
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }

    }
    if (arg0.getActionCommand().equalsIgnoreCase("UnequipArmor")) {
      currentChar.unequipArmor();
      updateInventoryList();
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }

    }
    if (arg0.getActionCommand().equalsIgnoreCase("UnequipHeadGear")) {
      currentChar.unequipHeadGear();
      updateInventoryList();
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }

    }
    if (arg0.getActionCommand().equalsIgnoreCase("UnequipAmulet")) {
      currentChar.unequipAmulet();
      updateInventoryList();
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }

    }
    if (arg0.getActionCommand().equalsIgnoreCase("UnequipRing")) {
      currentChar.unequipRing();
      updateInventoryList();
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }

    }
    if (arg0.getActionCommand().equalsIgnoreCase("UnequipBoots")) {
      currentChar.unequipBoots();
      updateInventoryList();
      if (characterIndex == lastCharIndex+1) {
        lastCharIndex = characterIndex;
      }

    }
    
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseEntered(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseExited(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mousePressed(MouseEvent arg0) {
    int index=-1;
    for (int i=0;i<timeFields.length;i++) {
      if (timeFields[i].isFocusOwner()) {
        index=i;
        break;
      }
      if (taskFields[i].isFocusOwner()) {
        index=i;
        break;
      }
      if (waypointFields[i].isFocusOwner()) {
        index=i;
        break;
      }
      if (descriptionFields[i].isFocusOwner()) {
        index=i;
        break;
      }
    }
    lastTaskIndex = index;
  }

  @Override
  public void mouseReleased(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
  }
  
}
