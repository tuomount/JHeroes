package org.jheroes.mapeditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jheroes.talk.TalkAction;
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
 * Actions dialog for Talk Editor
 * 
 **/
public class TalkEditorActionsDialog extends JDialog implements ActionListener {

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private static String ACTION_ADD_ACTION = "AddAction";
  private static String ACTION_REMOVE_ACTION = "RemoveAction";
  private static String ACTION_CLOSE = "Close";
  
  private JComboBox[] actionActions;
  private NumberSpinner[] actionValues;
  private NumberSpinner[] actionStoryVariables;
  private JTextField[] actionItemNames;
  private JTextField[] actionJournalEntry;
  private boolean actionTrue;
  private TalkLine line;

  private JPanel actionPanel;
  
  public TalkEditorActionsDialog(JDialog parent, TalkLine line, boolean action) {
    Point location = parent.getLocationOnScreen();
    location.x = location.x-parent.getWidth()/10;
    location.y = location.y;
    this.setLocation(location);
    this.setSize(600, 300);
    this.setResizable(false);
    this.setTitle("Talk Action Editor");
    this.setModal(true);
    this.line = line;
    this.actionTrue = action;
    JPanel main = new JPanel();
    main.setLayout(new BorderLayout());
    actionPanel = createActionsPanel();
    main.add(actionPanel,BorderLayout.CENTER);
    updateActionPanel();
    JPanel btnPane = new JPanel();
    btnPane.setLayout(new GridLayout(1, 0));
    JButton btn = new JButton("Add Action");
    btn.setActionCommand(ACTION_ADD_ACTION);
    btn.addActionListener(this);
    btnPane.add(btn);
    btn = new JButton("Remove Action");
    btn.setActionCommand(ACTION_REMOVE_ACTION);
    btn.addActionListener(this);
    btnPane.add(btn);
    btn = new JButton("Close");
    btn.setActionCommand(ACTION_CLOSE);
    btn.addActionListener(this);
    btnPane.add(btn);
    
    main.add(btnPane,BorderLayout.SOUTH);

    this.add(main);
    this.setVisible(true);
    
  }
  
  public JPanel createActionsPanel() {
    JPanel result = new JPanel();
    result.setLayout(new GridLayout(0, 5));
    JLabel label = new JLabel("Action");
    result.add(label);
    label = new JLabel("Value");
    result.add(label);
    label = new JLabel("StoryVariable");
    result.add(label);
    label = new JLabel("Item/Quest Name");
    result.add(label);
    label = new JLabel("Journal entry");
    result.add(label);
    
    return result;
  }
  
  public void updateActionPanel() {
    int size = 0;
    size = line.getActionsSize(actionTrue);
    actionActions = new JComboBox[size+1];
    actionValues = new NumberSpinner[size+1];
    actionStoryVariables = new NumberSpinner[size+1];
    actionItemNames = new JTextField[size+1];
    actionJournalEntry = new JTextField[size+1];
    actionPanel.removeAll();
    JLabel label = new JLabel("Action");
    actionPanel.add(label);
    label = new JLabel("Value");
    actionPanel.add(label);
    label = new JLabel("StoryVariable");
    actionPanel.add(label);
    label = new JLabel("Item/Quest Name");
    actionPanel.add(label);
    label = new JLabel("Journal entry");
    actionPanel.add(label);
    if (size > 0) {
      for (int i =0;i<size;i++) {
        TalkAction action = line.getAction(actionTrue, i);
        if (action != null) {
          actionActions[i] = new JComboBox(TalkAction.ACTIONS);
          actionActions[i].setSelectedIndex(action.getAction());
          actionPanel.add(actionActions[i]);
          actionValues[i] = new NumberSpinner(action.getValue(), -32000, 32000, 1);
          actionPanel.add(actionValues[i]);
          actionStoryVariables[i] = new NumberSpinner(action.getStoryVariable(), 0, 255, 1);
          actionPanel.add(actionStoryVariables[i]);
          actionItemNames[i] = new JTextField(action.getItemName());
          actionPanel.add(actionItemNames[i]);
          actionJournalEntry[i] = new JTextField(action.getJournalEntry());
          actionPanel.add(actionJournalEntry[i]);
        }
      }
    }
    actionActions[size] = new JComboBox(TalkAction.ACTIONS);
    actionPanel.add(actionActions[size]);
    actionValues[size] = new NumberSpinner(0, -32000, 32000, 1);
    actionPanel.add(actionValues[size]);
    actionStoryVariables[size] = new NumberSpinner(0, 0, 255, 1);
    actionPanel.add(actionStoryVariables[size]);
    actionItemNames[size] = new JTextField("");
    actionPanel.add(actionItemNames[size]);
    actionJournalEntry[size] = new JTextField("");
    actionPanel.add(actionJournalEntry[size]);

    actionPanel.revalidate();
  }
  
  public void getActionValues() {
    int size = actionActions.length-1;
    if (size > 0) {
      line.removeAllActions(actionTrue);      
      for (int i=0;i<size;i++) {
        TalkAction action = new TalkAction();
        action.setAction(actionActions[i].getSelectedIndex());
        action.setValue(actionValues[i].getSpinValue());
        action.setStoryVariable(actionStoryVariables[i].getSpinValue());
        action.setItemName(actionItemNames[i].getText());
        action.setJournalEntry(actionJournalEntry[i].getText());
        line.addAction(actionTrue, action);
      }
    }
  }
  
  @Override
  public void actionPerformed(ActionEvent arg0) {
    if (ACTION_ADD_ACTION.equals(arg0.getActionCommand())) {
      getActionValues();
      int i = actionActions.length-1;
      TalkAction action = new TalkAction();
      action.setAction(actionActions[i].getSelectedIndex());
      action.setValue(actionValues[i].getSpinValue());
      action.setStoryVariable(actionStoryVariables[i].getSpinValue());
      action.setItemName(actionItemNames[i].getText());
      action.setJournalEntry(actionJournalEntry[i].getText());
      line.addAction(actionTrue, action);
      updateActionPanel();
      repaint();
    }
    if (ACTION_REMOVE_ACTION.equals(arg0.getActionCommand())) {
      int i = line.getActionsSize(actionTrue)-1;
      if (i>=0) {
        line.removeAction(actionTrue, i);
        updateActionPanel();
        repaint();
      }
    }
    if (ACTION_CLOSE.equals(arg0.getActionCommand())) {
      getActionValues();
      this.setVisible(false);
    }

  }

}
