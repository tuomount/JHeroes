package org.jheroes.mapeditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.jheroes.talk.GreetingLine;
import org.jheroes.talk.Talk;
import org.jheroes.talk.TalkLine;


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
 * Talk Editor
 * 
 **/
public class TalkEditor extends JDialog implements ActionListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final String ACTION_OPEN = "Open";
  private static final String ACTION_SAVE = "Save";
  private static final String ACTION_CLOSE = "Close";  
  private static final String ACTION_ADD_GREETING="AddGreeting";
  private static final String ACTION_REMOVE_GREETING="RemoveGreeting";
  private static final String ACTION_ADD_LINE="AddLine";
  private static final String ACTION_REMOVE_LINE="RemoveLine";
  private static final String ACTION_UP_LINE="UpLine";
  private static final String ACTION_DOWN_LINE="DownLine";
  private static final String ACTION_EDIT_ACTIONS_TRUE="EditActionsTrue";
  private static final String ACTION_EDIT_ACTIONS_FALSE="EditActionsFalse";
  private static final String ACTION_CANCEL="EditCancel";
  private static final String ACTION_OK="EditOk";
  private static final String ACTION_TIMER="Timer";
  private static final String ACTION_TEST_STATE="TestState";

  private static final String ACTION_UP="MoveUp";
  private static final String ACTION_DOWN="MoveDown";
  private static final String ACTION_COPY="COPY";

  private Talk currentTalk;
  private JPanel greetingPanel;
  
  private JCheckBox[] greetingSelected;
  private JTextField[] greetingFields;
  private JComboBox[] greetingConditionCB;
  private NumberSpinner[] greetingParam1;
  private NumberSpinner[] greetingParam2;
  private JTextField[] greetingItemName;
  private NumberSpinner[] greetingNextState;
  
  private JTextField talkStatesWhenVisible;
  private JTextField talkPlayerText;
  private JTextField talkTextTrue;
  private JTextField talkTextFalse;
  private JComboBox  talkConditionCB;
  private NumberSpinner talkParam1;
  private NumberSpinner talkParam2;
  private JTextField talkItemName;
  
  private NumberSpinner testState;
  private JList testLines;
  private JTextArea testTrueText;
  private JTextArea testFalseText;
  private JLabel testTrueState;
  private JLabel testFalseState;
  
  private JPopupMenu talkListMenu;
  
  private JList talkList;
  
  private TalkEditorHelpDialog helpDiag;
  
  private Timer timer;
  
  private JFileChooser fcLoad;
  
  public TalkEditor(JFrame parent) {
    Point location = parent.getLocationOnScreen();
    location.x = location.x-parent.getWidth()/10;
    location.y = location.y;
    this.setLocation(location);
    this.setSize(1000, 700);
    this.setResizable(false);
    this.setTitle("Talk Editor");
    this.setModal(true);
    
    fcLoad = new JFileChooser();
    timer = new Timer(500, this);
    timer.setActionCommand(ACTION_TIMER);
    currentTalk = new Talk();
    JMenuBar mbEditor = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    JMenuItem itemOpen = new JMenuItem("Open talk");
    itemOpen.setActionCommand(ACTION_OPEN);
    itemOpen.addActionListener(this);
    menuFile.add(itemOpen);
    JMenuItem itemSave = new JMenuItem("Save talk");
    itemSave.setActionCommand(ACTION_SAVE);
    itemSave.addActionListener(this);
    menuFile.add(itemSave);
    JMenuItem itemClose = new JMenuItem("Close Talk Editor");
    itemClose.setActionCommand(ACTION_CLOSE);
    itemClose.addActionListener(this);
    menuFile.add(itemClose);
    mbEditor.add(menuFile);
    JPanel main = new JPanel();
    main.setLayout(new GridLayout(0, 1));
    
    talkListMenu = new JPopupMenu();
    JMenuItem itemUp = new JMenuItem("Move Up");
    itemUp.setActionCommand(ACTION_UP);
    itemUp.addActionListener(this);
    talkListMenu.add(itemUp);
    JMenuItem itemDown = new JMenuItem("Move Down");
    itemDown.setActionCommand(ACTION_DOWN);
    itemDown.addActionListener(this);
    talkListMenu.add(itemDown);
    JMenuItem itemCopy = new JMenuItem("Copy");
    itemCopy.setActionCommand(ACTION_COPY);
    itemCopy.addActionListener(this);
    talkListMenu.add(itemCopy);
    
    // Do greeting panel
    greetingPanel = createGreetingPanel();    
    JScrollPane scroll = new JScrollPane(greetingPanel);
    JPanel greetPaneWithBorder = new JPanel();
    greetPaneWithBorder.setLayout(new BorderLayout());
    greetPaneWithBorder.add(scroll,BorderLayout.CENTER);
    // Title
    JLabel label = new JLabel("Greetings");
    greetPaneWithBorder.add(label,BorderLayout.NORTH);
    // Add new greeting button
    JPanel greetBtnPane = new JPanel();
    greetBtnPane.setLayout(new BorderLayout());
    JButton btn = new JButton("Add greeting");
    btn.setActionCommand(ACTION_ADD_GREETING);
    btn.addActionListener(this);
    // Add remove greeting button
    greetBtnPane.add(btn,BorderLayout.WEST);
    btn = new JButton("Remove greeting");
    btn.setActionCommand(ACTION_REMOVE_GREETING);
    btn.addActionListener(this);
    greetBtnPane.add(btn,BorderLayout.EAST);
    
    JPanel btnPane2 = new JPanel();
    btnPane2.setLayout(new BorderLayout());
    btn = new JButton("Move Line Up");
    btn.setActionCommand(ACTION_UP_LINE);
    btn.addActionListener(this);        
    btnPane2.add(btn,BorderLayout.WEST);
    btn = new JButton("Move Line Down");
    btn.setActionCommand(ACTION_DOWN_LINE);
    btn.addActionListener(this);        
    btnPane2.add(btn,BorderLayout.EAST);
    greetBtnPane.add(btnPane2,BorderLayout.CENTER);

    
    greetPaneWithBorder.add(greetBtnPane,BorderLayout.SOUTH);
    
    // Add components to main panel
    main.add(greetPaneWithBorder);
    main.add(createTalkLinePanel());
    main.add(createTalkTestPanel());
    
    this.add(main);
    this.setJMenuBar(mbEditor);
    updateGreetingPanel();
    helpDiag = new TalkEditorHelpDialog(parent);
    timer.start();
    setVisible(true);
  }
  
  @Override
  public void dispose() {
    super.dispose();
    helpDiag.dispose();
  }

  /**
   * Creates greeting panel
   * @return JPane
   */
  public JPanel createGreetingPanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0, 6));
    JLabel label = new JLabel("Greeting");
    result.add(label);
    label = new JLabel("Condition");
    result.add(label);
    label = new JLabel("Param1");
    result.add(label);
    label = new JLabel("Param2");
    result.add(label);
    label = new JLabel("ItemName");
    result.add(label);
    label = new JLabel("NextState");
    result.add(label);
    
    return result;
  }
  
  public JPanel createTalkDataPanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0, 2));
    JLabel label = new JLabel("States:");
    result.add(label);  
    talkStatesWhenVisible = new JTextField("1");
    result.add(talkStatesWhenVisible);
    label = new JLabel("Player text:");
    result.add(label);  
    talkPlayerText = new JTextField("Hello");
    talkPlayerText.setColumns(15);
    result.add(talkPlayerText);
    label = new JLabel("Text True:");
    result.add(label);  
    talkTextTrue = new JTextField("Hello!");
    talkTextTrue.setColumns(15);
    result.add(talkTextTrue);
    label = new JLabel("Text False:");
    result.add(label);  
    talkTextFalse = new JTextField("");
    talkTextFalse.setColumns(15);
    result.add(talkTextFalse);
    label = new JLabel("Condition:");
    result.add(label);  
    talkConditionCB = new JComboBox(Talk.getTalkConditionsAsArray());
    result.add(talkConditionCB);
    label = new JLabel("Param1:");
    result.add(label);  
    talkParam1 = new NumberSpinner(0, 0, 32000, 1);
    result.add(talkParam1);
    label = new JLabel("Param2:");
    result.add(label);  
    talkParam2 = new NumberSpinner(0, 0, 32000, 1);
    result.add(talkParam2);
    label = new JLabel("ItemName:");
    result.add(label);  
    talkItemName = new JTextField("");
    talkItemName.setColumns(15);
    result.add(talkItemName);
    JButton editTrueActions = new JButton("True branch actions");
    editTrueActions.setActionCommand(ACTION_EDIT_ACTIONS_TRUE);
    editTrueActions.addActionListener(this);
    result.add(editTrueActions);
    JButton editFalseActions = new JButton("False branch actions");
    editFalseActions.setActionCommand(ACTION_EDIT_ACTIONS_FALSE);
    editFalseActions.addActionListener(this);
    result.add(editFalseActions);
    JButton okBtn = new JButton("Ok");
    okBtn.setActionCommand(ACTION_OK);
    okBtn.addActionListener(this);
    result.add(okBtn);
    JButton cancelBtn = new JButton("Cancel");
    cancelBtn.setActionCommand(ACTION_CANCEL);
    cancelBtn.addActionListener(this);
    result.add(cancelBtn);
    return result;
  }
  
  public void updateTalkDataPanel(int index) {
    TalkLine line =currentTalk.getTalkLineByIndex(index);
    if (line != null) {
      talkStatesWhenVisible.setText(line.getStatesWhenVisibleAsString());
      talkPlayerText.setText(line.getPlayerText());
      talkTextTrue.setText(line.getTextTrue());
      talkTextFalse.setText(line.getTextFalse());
      talkConditionCB.setSelectedIndex(line.getCondition());
      talkParam1.setSpinValue(line.getParam1());
      talkParam2.setSpinValue(line.getParam2());
      talkItemName.setText(line.getItemName());
    }
  }
  
  public JPanel createTalkLinePanel() {
    JPanel result = new JPanel();
    result.setLayout(new BorderLayout());
    JLabel label = new JLabel("Talk lines:");
    result.add(label,BorderLayout.NORTH);
    talkList = new JList(currentTalk.getAllLines());
    talkList.addMouseListener(new MouseAdapter() {

      
      public void mousePressed(MouseEvent e)  {
        if (e.getButton()==MouseEvent.BUTTON3) { //if the event shows the menu
          talkList.setSelectedIndex(talkList.locationToIndex(e.getPoint())); //select the item
          talkListMenu.show(talkList, e.getX(), e.getY());
        }
      }
 });

    talkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scroll = new JScrollPane(talkList);
    result.add(scroll,BorderLayout.WEST);
    result.add(createTalkDataPanel(),BorderLayout.CENTER);
    
    JPanel btnPane = new JPanel();
    btnPane.setLayout(new BorderLayout());
    JButton btn = new JButton("Add Line");
    btn.setActionCommand(ACTION_ADD_LINE);
    btn.addActionListener(this);        
    btnPane.add(btn,BorderLayout.WEST);
    btn = new JButton("Remove Line");
    btn.setActionCommand(ACTION_REMOVE_LINE);
    btn.addActionListener(this);        
    btnPane.add(btn,BorderLayout.EAST);

    
    result.add(btnPane,BorderLayout.SOUTH);
    return result;
  }
  
  public JPanel createTalkTestPanel() {
    JPanel result = new JPanel();
    result.setLayout(new BorderLayout());
    
    JPanel top = new JPanel();
    top.setLayout(new GridLayout(1,0));
    JLabel label = new JLabel("Test State:");
    top.add(label);
    testState = new NumberSpinner(0, 0, 255, 1);
    top.add(testState);
    JButton btn = new JButton("Test state");
    btn.setActionCommand(ACTION_TEST_STATE);
    btn.addActionListener(this);
    top.add(btn);
    result.add(top,BorderLayout.NORTH);
    
    testLines = new JList(currentTalk.getAllLinesWithState(testState.getSpinValue()));
    testLines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);    
    JScrollPane scroll = new JScrollPane(testLines);
    result.add(scroll,BorderLayout.WEST);
    
    JPanel trueText = new JPanel();
    trueText.setLayout(new BorderLayout());
    Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    trueText.setBorder(etchedBorder);
    label = new JLabel("True Text:");
    trueText.add(label,BorderLayout.NORTH);
    testTrueText = new JTextArea();
    testTrueText.setEditable(false);
    testTrueText.setWrapStyleWord(true);
    testTrueText.setLineWrap(true);
    trueText.add(testTrueText,BorderLayout.CENTER);
    testTrueState = new JLabel("Next state:1");
    trueText.add(testTrueState,BorderLayout.SOUTH);

    JPanel falseText = new JPanel();
    falseText.setLayout(new BorderLayout());
    etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    falseText.setBorder(etchedBorder);
    label = new JLabel("False Text:");
    falseText.add(label,BorderLayout.NORTH);
    testFalseText = new JTextArea();
    testFalseText.setEditable(false);
    testFalseText.setWrapStyleWord(true);
    testFalseText.setLineWrap(true);
    testTrueText.setLineWrap(true);
    falseText.add(testFalseText,BorderLayout.CENTER);
    testFalseState = new JLabel("Next state:1");
    falseText.add(testFalseState,BorderLayout.SOUTH);

    JPanel textPane = new JPanel();
    textPane.setLayout(new GridLayout(0,2));
    textPane.add(trueText);
    textPane.add(falseText);
    result.add(textPane,BorderLayout.CENTER);
    return result;
  }
  
  public void updateTalkTest() {
    TalkLine line = (TalkLine) testLines.getSelectedValue();
    if (line != null) {
      testTrueText.setText(line.getTextTrue());
      testTrueState.setText("Next state:"+line.getNextState(true));
      testFalseText.setText(line.getTextFalse());
      testFalseState.setText("Next state:"+line.getNextState(false));
    }
  }
  
  public void getGreetingValues() {
    int size = greetingFields.length-1;
    if (size > 0) {
      currentTalk.removeAllGreetings();
      for (int i=0;i<size;i++) {
        GreetingLine greet = new GreetingLine(greetingFields[i].getText());
        greet.setCondition(greetingConditionCB[i].getSelectedIndex());
        greet.setParam1(greetingParam1[i].getSpinValue());
        greet.setParam2(greetingParam2[i].getSpinValue());
        greet.setItemName(greetingItemName[i].getText());
        greet.setNextState(greetingNextState[i].getSpinValue());
        currentTalk.addGreetingLine(greet);
      }
    }
  }
  
  public void getTalkDataValues(int index) {
    TalkLine line = currentTalk.getTalkLineByIndex(index);
    if (line != null) {
      line.setStatesWhenVisible(talkStatesWhenVisible.getText());
      line.setPlayerText(talkPlayerText.getText());
      line.setTextTrue(talkTextTrue.getText());
      line.setTextFalse(talkTextFalse.getText());
      line.setCondition(talkConditionCB.getSelectedIndex());
      line.setParam1(talkParam1.getSpinValue());
      line.setParam2(talkParam2.getSpinValue());
      line.setItemName(talkItemName.getText());
    }
  }
  
  public void updateGreetingPanel() {
    // Initialize arrays
    int greetNum = currentTalk.getNumberOfGreetings();
    greetingSelected = new JCheckBox[greetNum+1];
    greetingFields = new JTextField[greetNum+1];
    greetingConditionCB = new JComboBox[greetNum+1];
    greetingParam1 = new NumberSpinner[greetNum+1];
    greetingParam2 = new NumberSpinner[greetNum+1];
    greetingItemName = new JTextField[greetNum+1];
    greetingNextState = new NumberSpinner[greetNum+1];
    // Clear components from greeting panel
    greetingPanel.removeAll();
    // Add titles
    JLabel label = new JLabel("Greeting");
    greetingPanel.add(label);
    label = new JLabel("Condition");
    greetingPanel.add(label);
    label = new JLabel("Param1");
    greetingPanel.add(label);
    label = new JLabel("Param2");
    greetingPanel.add(label);
    label = new JLabel("ItemName");
    greetingPanel.add(label);
    label = new JLabel("NextState");
    greetingPanel.add(label);
    // Add greetings from talk
    if (greetNum > 0) {
      for (int i =0;i<greetNum;i++) {
        JPanel tempPane = new JPanel();
        tempPane.setLayout(new BorderLayout());
        greetingSelected[i] = new JCheckBox("", false);
        //greetingPanel.add(greetingSelected[i]);
        tempPane.add(greetingSelected[i],BorderLayout.WEST);
        greetingFields[i] = new JTextField(currentTalk.getGreetingByIndex(i).getText());
        greetingFields[i].setColumns(15);
        tempPane.add(greetingFields[i],BorderLayout.CENTER);
        //greetingPanel.add(greetingFields[i]);
        greetingPanel.add(tempPane);
       
        
        greetingConditionCB[i] = new JComboBox(Talk.getTalkConditionsAsArray());
        greetingConditionCB[i].setSelectedIndex(currentTalk.getGreetingByIndex(i).getCondition());
        greetingPanel.add(greetingConditionCB[i]);
        greetingParam1[i] = new NumberSpinner(currentTalk.getGreetingByIndex(i).getParam1(), 0, 32000, 1);
        greetingPanel.add(greetingParam1[i]);
        greetingParam2[i] = new NumberSpinner(currentTalk.getGreetingByIndex(i).getParam2(), 0, 32000, 1);
        greetingPanel.add(greetingParam2[i]);
        greetingItemName[i] = new JTextField(currentTalk.getGreetingByIndex(i).getItemName());
        greetingPanel.add(greetingItemName[i]);
        greetingNextState[i] = new NumberSpinner(currentTalk.getGreetingByIndex(i).getNextState(), 0, 255, 1);
        greetingPanel.add(greetingNextState[i]);
      }
    }
    // Add extra line where editing happens
    greetingFields[greetNum] = new JTextField("");
    greetingPanel.add(greetingFields[greetNum]);
    greetingConditionCB[greetNum] = new JComboBox(Talk.getTalkConditionsAsArray());
    greetingPanel.add(greetingConditionCB[greetNum]);
    greetingParam1[greetNum] = new NumberSpinner(0, 0, 32000, 1);
    greetingPanel.add(greetingParam1[greetNum]);
    greetingParam2[greetNum] = new NumberSpinner(0, 0, 32000, 1);
    greetingPanel.add(greetingParam2[greetNum]);
    greetingItemName[greetNum] = new JTextField("");
    greetingPanel.add(greetingItemName[greetNum]);
    greetingNextState[greetNum] = new NumberSpinner(0, 0, 255, 1);
    greetingPanel.add(greetingNextState[greetNum]);
    
    
    greetingPanel.revalidate();
  }
  
  public void updateTalkPanel() {
    talkList.setListData(currentTalk.getAllLines());    
  }
  
  private void moveGreeting(int direction) {
    int moveIndex = -1;
    for (int i=0;i<greetingSelected.length;i++) {
      if (greetingSelected[i].isSelected()) {
        moveIndex = i;
        break;
      }
    }
    if (moveIndex+direction > greetingFields.length-1 ||
        moveIndex+direction < 0) {
      return;
    }
    JCheckBox tempGreetSeletected = greetingSelected[moveIndex];
    JTextField tempGreetField = greetingFields[moveIndex];
    JComboBox tempGreetCB = greetingConditionCB[moveIndex];
    NumberSpinner tempGreetParam1 = greetingParam1[moveIndex];
    NumberSpinner tempGreetParam2 = greetingParam2[moveIndex];
    JTextField tempGreetItem = greetingItemName[moveIndex];
    NumberSpinner tempGreetNextState = greetingNextState[moveIndex];
    int targetIndex = moveIndex+direction;
    greetingSelected[moveIndex] = greetingSelected[targetIndex];
    greetingFields[moveIndex] = greetingFields[targetIndex];
    greetingConditionCB[moveIndex] = greetingConditionCB[targetIndex];
    greetingParam1[moveIndex] = greetingParam1[targetIndex];
    greetingParam2[moveIndex] = greetingParam2[targetIndex];
    greetingItemName[moveIndex] = greetingItemName[targetIndex];
    greetingNextState[moveIndex] = greetingNextState[targetIndex];
    greetingSelected[targetIndex] = tempGreetSeletected;
    greetingFields[targetIndex] = tempGreetField;
    greetingConditionCB[targetIndex] = tempGreetCB;
    greetingParam1[targetIndex] = tempGreetParam1;
    greetingParam2[targetIndex] = tempGreetParam2;
    greetingItemName[targetIndex] = tempGreetItem;
    greetingNextState[targetIndex] = tempGreetNextState;
    
    getGreetingValues();
    updateGreetingPanel();
    repaint();

  }
  
  @Override
  public void actionPerformed(ActionEvent arg0) {
    if (ACTION_SAVE.equals(arg0.getActionCommand()))  {
      getGreetingValues();
      DataOutputStream os;        
      fcLoad.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      int returnVal = fcLoad.showSaveDialog(this);
      if (returnVal == JFileChooser.OPEN_DIALOG) {
          String saveStr = fcLoad.getSelectedFile().getAbsolutePath();
        try {
          os = new DataOutputStream(new FileOutputStream(saveStr));
          currentTalk.saveTalk(os);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    if (ACTION_OPEN.equals(arg0.getActionCommand())) {
      DataInputStream is;
      fcLoad.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      int returnVal = fcLoad.showOpenDialog(this);
      if (returnVal == JFileChooser.OPEN_DIALOG) {
          String saveStr = fcLoad.getSelectedFile().getAbsolutePath();
        try {
          is = new DataInputStream(new FileInputStream(saveStr));
          currentTalk = new Talk(is);
          updateGreetingPanel();
          updateTalkPanel();
          repaint();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    if (ACTION_CLOSE.equals(arg0.getActionCommand())) {
      this.setVisible(false);
    }
    if (ACTION_OK.equals(arg0.getActionCommand())) {
      int index = talkList.getSelectedIndex();
      getTalkDataValues(index);
      getGreetingValues();
      updateGreetingPanel();
      repaint();
    }
    if (ACTION_UP.equals(arg0.getActionCommand())) {
      int index = talkList.getSelectedIndex();
      currentTalk.moveUp(index);
      index = talkList.getSelectedIndex();
      if ((index != -1) && (talkList.isFocusOwner())) {
        updateTalkDataPanel(index);
      }
      if (testLines.isFocusOwner()) {
        updateTalkTest();
      }
      updateTalkPanel();
      repaint();
    }
    if (ACTION_DOWN.equals(arg0.getActionCommand())) {
      int index = talkList.getSelectedIndex();
      currentTalk.moveDown(index);
      index = talkList.getSelectedIndex();
      if ((index != -1) && (talkList.isFocusOwner())) {
        updateTalkDataPanel(index);
      }
      if (testLines.isFocusOwner()) {
        updateTalkTest();
      }
      updateTalkPanel();
      repaint();
    }
    if (ACTION_COPY.equals(arg0.getActionCommand())) {
        int index = talkList.getSelectedIndex();
        TalkLine line =currentTalk.getTalkLineByIndex(index);
        TalkLine line2 = new TalkLine("", "", "0");
        line2.copyOf(line);
        currentTalk.addTalkLine(line2);
        updateTalkPanel();
        repaint();
      }
    if (ACTION_TIMER.equals(arg0.getActionCommand())) {
      int index = talkList.getSelectedIndex();
      if ((index != -1) && (talkList.isFocusOwner())) {
        updateTalkDataPanel(index);
      }
      if (testLines.isFocusOwner()) {
        updateTalkTest();
      }
    }
    if (ACTION_UP_LINE.equals(arg0.getActionCommand())) {
      moveGreeting(-1);
    }
    if (ACTION_DOWN_LINE.equals(arg0.getActionCommand())) {
      moveGreeting(1);
    }
    if (ACTION_ADD_GREETING.equals(arg0.getActionCommand())) {
      getGreetingValues();
      int i = greetingFields.length-1;
      GreetingLine greet = new GreetingLine(greetingFields[i].getText());
      greet.setCondition(greetingConditionCB[i].getSelectedIndex());
      greet.setParam1(greetingParam1[i].getSpinValue());
      greet.setParam2(greetingParam2[i].getSpinValue());
      greet.setItemName(greetingItemName[i].getText());
      greet.setNextState(greetingNextState[i].getSpinValue());
      currentTalk.addGreetingLine(greet);
      updateGreetingPanel();
      repaint();
    }
    if (ACTION_REMOVE_GREETING.equals(arg0.getActionCommand())) {
      int i = currentTalk.getNumberOfGreetings()-1;
      if (i>0) {
        currentTalk.removeGreetingLine(i);
        updateGreetingPanel();
        repaint();
      }
    }
    if (ACTION_TEST_STATE.equals(arg0.getActionCommand())) {
      testLines.setListData(currentTalk.getAllLinesWithState(testState.getSpinValue()));
      repaint();
    }
    if (ACTION_ADD_LINE.equals(arg0.getActionCommand())) {
      TalkLine line = new TalkLine("Hello!", "Hello!", "1");
      currentTalk.addTalkLine(line);
      updateTalkPanel();
      repaint();
    }
    if (ACTION_REMOVE_LINE.equals(arg0.getActionCommand())) {
      int index = talkList.getSelectedIndex();
      if (index != -1) {
        currentTalk.removeTalkLine(index);
        updateTalkPanel();
        repaint();
      }
    }
    if (ACTION_EDIT_ACTIONS_TRUE.equals(arg0.getActionCommand())) {
      int index = talkList.getSelectedIndex();
      if (index != -1) {
        TalkEditorActionsDialog actDiag = new TalkEditorActionsDialog(this,
          currentTalk.getTalkLineByIndex(index), true);
        actDiag.dispose();
      }
    }
    if (ACTION_EDIT_ACTIONS_FALSE.equals(arg0.getActionCommand())) {
      int index = talkList.getSelectedIndex();
      if (index != -1) {
        TalkEditorActionsDialog actDiag = new TalkEditorActionsDialog(this,
          currentTalk.getTalkLineByIndex(index), false);
        actDiag.dispose();
      }
    }

  }

}
