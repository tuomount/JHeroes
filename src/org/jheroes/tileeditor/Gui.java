package org.jheroes.tileeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.jheroes.tileset.Tile;
import org.jheroes.tileset.TileInfo;
import org.jheroes.tileset.Tileset;
import org.jheroes.utilities.FileUtil;


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
 * Tile Editor GUI
 * 
 **/
public class Gui extends JPanel implements ActionListener, MouseListener{
    /**
  	 * 
  	 */
	private static final long serialVersionUID = 1L;

	private JFileChooser fcImage;
	private JFileChooser fcTileset;
	private JTextField textFilename;
	private JFormattedTextField textSeriesNum;
	private BufferedImage biImage = null;
	private BufferedImage screen;
	private BufferedImage nextFrame;
	private BufferedImage animFrame;
	private Tile currentTile;
	private TileInfo currentTileInfo;
	private Tileset currentTileset;
	private Tile nextTile;
	private TileInfo nextTileInfo;
	private CaptureScreen dlgCaptureScreen;
	private JLabel labelTransparent;
	private JLabel labelIndexNumber;
	private JLabel labelSeriesType;
	private JLabel labelNextAnimNumber;
	private JComboBox cbSpeed;
	private JComboBox cbPosition;
  private JComboBox cbLightEffect;
  private JComboBox cbCaptureType;
	private int index=0;
	private int animIndex = 0;
	private int newTileIndex=1;
	private final String[] speedSelections = {"Fast", "Normal", "Slow"};
	private final String[] positionSelections = {"Wall/Floor", "Object", "Item",
	                       "Decoration", "Creature", "Top"};
	
  private final String[] lightSelections = {"No Light", "Dim light", "Fireplace",
      "Bright Light", "Normal Light", "Magical", "Magical Fast","Cast shadow"};
	private Timer timer;
	private int timerSpeed=0;
	
	public Gui() {
		
	final JLabel labelFilename = new JLabel("Image file:");
    final JLabel labelSpeed = new JLabel("Speed: ");
    final JLabel labelSeriesNum = new JLabel("Series: ");
    labelSeriesType = new JLabel("Type: ");
    final JLabel labelPlace = new JLabel("Position: ");
    timer = new Timer(100,this);
    timer.setActionCommand("timer");

    labelTransparent = new JLabel("No transparent color");
    labelIndexNumber = new JLabel("Index:"+String.valueOf(index));
    labelNextAnimNumber = new JLabel("Next frame:"+String.valueOf(index));
    // Text for image file
    textFilename = new JTextField();
    textFilename.setPreferredSize(new Dimension(400, 30));
    textFilename.setText("");
    cbSpeed = new JComboBox(speedSelections);
    cbSpeed.setActionCommand("cbSpeed");
    textSeriesNum = new JFormattedTextField(NumberFormat.getInstance());
    textSeriesNum.setText("0");
    textSeriesNum.setActionCommand("SeriesNum");
    textSeriesNum.setPreferredSize(new Dimension(50, 20));
    
    cbPosition = new JComboBox(positionSelections);
    cbPosition.setActionCommand("cbPosition");

    cbLightEffect = new JComboBox(lightSelections);
    cbLightEffect.setActionCommand("cbLight");

    // Browse button
    final JButton buttonFileBrowse = new JButton("Browse");
    buttonFileBrowse.setPreferredSize(new Dimension(120,30));
    buttonFileBrowse.setActionCommand("browse");
    
    cbCaptureType = new JComboBox(CaptureScreen.CAPTURE_TYPE_STRINGS);
    // Capture all button
    final JButton buttonCapture = new JButton("Capture all");
    buttonCapture.setPreferredSize(new Dimension(120,30));
    buttonCapture.setActionCommand("capture");
    
    // Capture single button
    final JButton buttonCaptureSingle = new JButton("Capture Single");
    buttonCaptureSingle.setPreferredSize(new Dimension(180,30));
    buttonCaptureSingle.setActionCommand("capture single");

    // Previous button
    final JButton buttonPrev = new JButton("Prev");
    buttonPrev.setPreferredSize(new Dimension(80,30));
    buttonPrev.setActionCommand("prev");
    // Next button
    final JButton buttonNext = new JButton("Next");
    buttonNext.setPreferredSize(new Dimension(80,30));
    buttonNext.setActionCommand("next");

    // Previous button
    final JButton buttonPrevAnim = new JButton("Prev");
    buttonPrevAnim.setPreferredSize(new Dimension(80,30));
    buttonPrevAnim.setActionCommand("prev-anim");
    // Next button
    final JButton buttonNextAnim = new JButton("Next");
    buttonNextAnim.setPreferredSize(new Dimension(80,30));
    buttonNextAnim.setActionCommand("next-anim");

    // increase type
    final JButton buttonIncType = new JButton("+");
    buttonIncType.setPreferredSize(new Dimension(80,30));
    buttonIncType.setActionCommand("nexttype");
    // decrease type
    final JButton buttonDecType = new JButton("-");
    buttonDecType.setPreferredSize(new Dimension(80,30));
    buttonDecType.setActionCommand("prevtype");

    final JButton buttonApplyAll = new JButton("Apply for whole series");
    buttonApplyAll.setActionCommand("ApplyAll");

    final JButton buttonInsert = new JButton("Insert new Tile");
    buttonInsert.setActionCommand("Insert");

    final JButton buttonDelete = new JButton("Delete Tile");
    buttonDelete.setActionCommand("Delete");
    
    
    // Save Tileset
    final JButton buttonSave = new JButton("Save");
    buttonSave.setPreferredSize(new Dimension(120,30));
    buttonSave.setActionCommand("save");

    // Load Tileset
    final JButton buttonLoad = new JButton("Load");
    buttonLoad.setPreferredSize(new Dimension(120,30));
    buttonLoad.setActionCommand("load");

    // Goto End
    final JButton buttonGoStart = new JButton("Start");
    buttonGoStart.setPreferredSize(new Dimension(120,30));
    buttonGoStart.setActionCommand("Start");
    
    // Goto End
    final JButton buttonGoEnd = new JButton("End");
    buttonGoEnd.setPreferredSize(new Dimension(120,30));
    buttonGoEnd.setActionCommand("End");

    // Initialize screen
    screen = new BufferedImage(Tile.MAX_WIDTH+2, Tile.MAX_HEIGHT+2, BufferedImage.TYPE_INT_ARGB);
    nextFrame = new BufferedImage(Tile.MAX_WIDTH+2, Tile.MAX_HEIGHT+2, BufferedImage.TYPE_INT_ARGB);
    animFrame = new BufferedImage(Tile.MAX_WIDTH+2, Tile.MAX_HEIGHT+2, BufferedImage.TYPE_INT_ARGB);
    // Filechooser
    fcImage = new JFileChooser();
    fcImage.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    fcImage.setFileFilter(new ImageFileFilter());

    fcTileset = new JFileChooser();
    fcTileset.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    fcTileset.setFileFilter(new TilesetFilter());

    // Add components to panel
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    topPanel.add(labelFilename);
    topPanel.add(textFilename);
    topPanel.add(buttonFileBrowse);
    //this.add(Box.createRigidArea(new Dimension(50,30)));
    JPanel capturePanel = new JPanel();
    capturePanel.setLayout(new BoxLayout(capturePanel, BoxLayout.X_AXIS));

    capturePanel.add(cbCaptureType);
    capturePanel.add(buttonCapture);
    capturePanel.add(buttonCaptureSingle);
    //this.add(Box.createRigidArea(new Dimension(250,30)));
    //this.add(Box.createRigidArea(new Dimension(100,30)));
    
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
    controlPanel.add(buttonPrev,BorderLayout.CENTER);
    controlPanel.add(Box.createRigidArea(new Dimension(5,30)));
    controlPanel.add(labelIndexNumber,BorderLayout.CENTER);
    controlPanel.add(Box.createRigidArea(new Dimension(5,30)));
    controlPanel.add(buttonNext,BorderLayout.CENTER);    
    controlPanel.add(labelTransparent,BorderLayout.CENTER);
    controlPanel.add(Box.createRigidArea(new Dimension(10,30)));    
    controlPanel.add(labelSeriesNum,BorderLayout.CENTER);    
    controlPanel.add(textSeriesNum,BorderLayout.CENTER);

    JPanel animPanel = new JPanel();
    animPanel.setLayout(new BoxLayout(animPanel, BoxLayout.X_AXIS));

    //this.add(Box.createRigidArea(new Dimension(100,30)));
    //this.add(Box.createRigidArea(new Dimension(100,30)));
    animPanel.add(buttonPrevAnim,BorderLayout.CENTER);
    animPanel.add(Box.createRigidArea(new Dimension(5,30)));
    animPanel.add(labelNextAnimNumber,BorderLayout.CENTER);
    animPanel.add(Box.createRigidArea(new Dimension(5,30)));
    animPanel.add(buttonNextAnim,BorderLayout.CENTER);   
    animPanel.add(Box.createRigidArea(new Dimension(5,30)));
    animPanel.add(labelPlace,BorderLayout.CENTER);
    animPanel.add(cbPosition,BorderLayout.CENTER);
    animPanel.add(Box.createRigidArea(new Dimension(5,30)));
    animPanel.add(labelSpeed,BorderLayout.CENTER);
    animPanel.add(cbSpeed,BorderLayout.CENTER);
    
    //this.add(Box.createRigidArea(new Dimension(100,30)));
    //this.add(Box.createRigidArea(new Dimension(100,30)));
    JPanel typePanel = new JPanel();
    typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));

    typePanel.add(buttonDecType,BorderLayout.CENTER);
    typePanel.add(Box.createRigidArea(new Dimension(5,30)));
    typePanel.add(labelSeriesType,BorderLayout.CENTER);
    typePanel.add(Box.createRigidArea(new Dimension(5,30)));
    typePanel.add(buttonIncType,BorderLayout.CENTER);
    typePanel.add(Box.createRigidArea(new Dimension(10,30)));
    typePanel.add(cbLightEffect,BorderLayout.CENTER);
    typePanel.add(buttonApplyAll,BorderLayout.CENTER);
    
    JPanel editorPanel = new JPanel();
    editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.X_AXIS));
    
    editorPanel.add(buttonInsert,BorderLayout.CENTER);
    editorPanel.add(buttonDelete,BorderLayout.CENTER);
    editorPanel.add(buttonSave,BorderLayout.CENTER);
    editorPanel.add(buttonLoad,BorderLayout.CENTER);
    editorPanel.add(buttonGoStart);
    editorPanel.add(buttonGoEnd);

    JPanel middlePanel = new JPanel();
    middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
 
    middlePanel.add(capturePanel);
    middlePanel.add(Box.createRigidArea(new Dimension(5,50)));
    middlePanel.add(controlPanel);
    middlePanel.add(Box.createRigidArea(new Dimension(5,100)));
    middlePanel.add(animPanel);
    middlePanel.add(Box.createRigidArea(new Dimension(5,100)));
    middlePanel.add(typePanel);
    middlePanel.add(Box.createRigidArea(new Dimension(5,200)));
    
    this.setLayout(new BorderLayout());
    this.add(topPanel,BorderLayout.NORTH);
    this.add(middlePanel,BorderLayout.CENTER);
    this.add(Box.createRigidArea(new Dimension(50,200)),BorderLayout.WEST);
    this.add(editorPanel,BorderLayout.SOUTH);
    
    // Add actions
    buttonFileBrowse.addActionListener(this);
    buttonCapture.addActionListener(this);
    buttonCaptureSingle.addActionListener(this);
    buttonPrev.addActionListener(this);
    buttonNext.addActionListener(this);
    buttonPrevAnim.addActionListener(this);
    buttonNextAnim.addActionListener(this);
    buttonDecType.addActionListener(this);
    buttonIncType.addActionListener(this);
    buttonSave.addActionListener(this);
    buttonLoad.addActionListener(this);
    buttonGoEnd.addActionListener(this);
    buttonGoStart.addActionListener(this);
    buttonApplyAll.addActionListener(this);
    buttonInsert.addActionListener(this);
    buttonDelete.addActionListener(this);
    
    cbSpeed.addActionListener(this);
    cbPosition.addActionListener(this);
    cbLightEffect.addActionListener(this);
    textSeriesNum.addActionListener(this);
    this.addMouseListener(this);
    
    // Initialize tiles
    currentTile = new Tile();
    currentTileset = new Tileset();
    currentTileset.addTile(currentTile);    
    index = 0;
    currentTileInfo = currentTileset.getTileInfo(index);

    timer.start();
    }

 /**
  * Get Buffered Image from another bufferedImage with Tile size
  * @param input BufferedImage
  * @param x Tile x coordinate
  * @param y Tile y coodinate
  * @return BufferedImage or null if not valid values
  */
  public BufferedImage getSubTile(BufferedImage input, int x, int y) {
    BufferedImage result = null;
    if ((x>=0) && (y>=0) &&(x*Tile.MAX_WIDTH+Tile.MAX_WIDTH<=input.getWidth()) &&
       (y*Tile.MAX_HEIGHT+Tile.MAX_HEIGHT<=input.getHeight())) {
      result = input.getSubimage(x*Tile.MAX_WIDTH, y*Tile.MAX_HEIGHT,
         Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
    }
    return result;
  }

  public void putTileInTileset(BufferedImage input) {
    currentTile.loadTile(input);
    currentTileInfo.setTileSeries(Integer.valueOf(textSeriesNum.getText()));
    currentTileInfo.setDefaultPlace((byte) cbPosition.getSelectedIndex());
    if (index == newTileIndex) {
      newTileIndex++;
    }
  }

  public void getNextTileInTileset() {
    if (index < newTileIndex) {
      index++;
      if ((index == newTileIndex) && (currentTileset.size()-1 < newTileIndex)) {
        currentTile = new Tile();
        currentTileset.addTile(currentTile);
        currentTileInfo = currentTileset.getTileInfo(index);
      } else {
        currentTile = currentTileset.getTile(index);
        currentTileInfo = currentTileset.getTileInfo(index);
      }          
      cbSpeed.setSelectedIndex(currentTileInfo.getSpeedIndex());
      cbPosition.setSelectedIndex(currentTileInfo.getDefaultPlace());
      cbLightEffect.setSelectedIndex(currentTileInfo.getLightEffect());
      textSeriesNum.setText(String.valueOf(currentTileInfo.getTileSeries()));
      animIndex = index;
    }
  }

  /**
  * Action handler for Editor
  */
  public void actionPerformed(ActionEvent e) {
    if ("browse".equals(e.getActionCommand())) {
      int returnVal = fcImage.showOpenDialog(Gui.this);
      if (returnVal == JFileChooser.OPEN_DIALOG) {
          textFilename.setText(fcImage.getSelectedFile().getAbsolutePath());
      }
    }
    if ("save".equals(e.getActionCommand())) {
      int returnVal = fcTileset.showSaveDialog(Gui.this);
      if (returnVal == JFileChooser.OPEN_DIALOG) {
          currentTileset.saveTileSet(fcTileset.getSelectedFile().getAbsolutePath());
      }        
    }
    if ("load".equals(e.getActionCommand())) {
      int returnVal = fcTileset.showOpenDialog(Gui.this);
      if (returnVal == JFileChooser.OPEN_DIALOG) {
        currentTileset.loadTileSet(fcTileset.getSelectedFile().getAbsolutePath());
        index = 0;
        newTileIndex = currentTileset.size();
        currentTileInfo = currentTileset.getTileInfo(index);
        currentTile = currentTileset.getTile(index);
        cbSpeed.setSelectedIndex(currentTileInfo.getSpeedIndex());
        cbPosition.setSelectedIndex(currentTileInfo.getDefaultPlace());
        textSeriesNum.setText(String.valueOf(currentTileInfo.getTileSeries()));
        animIndex = index;
        this.repaint();
      }
    }
    if ("capture".equals(e.getActionCommand())) {
      biImage = FileUtil.readImageFile(textFilename.getText());
      if (biImage != null) {
        dlgCaptureScreen = new CaptureScreen(new JFrame(), biImage, cbCaptureType.getSelectedIndex());
        BufferedImage tmp = dlgCaptureScreen.getSelected();
        if (tmp != null) {
          BufferedImage tmp2;
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_SINGLE) {
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_SINGLE);
            tmp2 = getSubTile(tmp, 0, 0);
            putTileInTileset(tmp2);
          }
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_OBJECT1X2){
            tmp2 = getSubTile(tmp, 0, 0);
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_OBJECT_1X2_N);
            currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_OBJECT);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 1);
            putTileInTileset(tmp2);
          }
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_OBJECT2X1){
            tmp2 = getSubTile(tmp, 0, 0);
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_OBJECT_2X1_W);
            currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_OBJECT);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 0);
            putTileInTileset(tmp2);
          }
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_SMALL_CHARACTER){
            tmp2 = getSubTile(tmp, 1, 3);
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_SINGLE);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 3);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 3);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 2);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 2);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 2);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 1);
            putTileInTileset(tmp2);
          }
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_NORMAL_CHARACTER){
            tmp2 = getSubTile(tmp, 1, 6);
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_SINGLE);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 6);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 6);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 4);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 4);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 4);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 2);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 2);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 2);
            putTileInTileset(tmp2);
            // HEAD done
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 7);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 7);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 7);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 5);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 5);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 5);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 3);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 3);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 3);
            putTileInTileset(tmp2);
          }
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_OBJECT1X3){
            tmp2 = getSubTile(tmp, 0, 0);
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_OBJECT_1X3_N);
            currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_OBJECT);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 2);
            putTileInTileset(tmp2);
          }
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_OBJECT2X3){
            tmp2 = getSubTile(tmp, 0, 0);
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_OBJECT_2X3_NW);
            currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_OBJECT);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 2);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 2);
            putTileInTileset(tmp2);
          }
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_OBJECT3X1){
            tmp2 = getSubTile(tmp, 0, 0);
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_OBJECT_3X1_W);
            currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_OBJECT);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 0);
            putTileInTileset(tmp2);
          }
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_OBJECT2X2){
            tmp2 = getSubTile(tmp, 0, 0);
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_OBJECT_2X2_NW);
            currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_OBJECT);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 1);
            putTileInTileset(tmp2);
          }
          if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_WALL){
            tmp2 = getSubTile(tmp, 0, 0);
            currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_WALL_WEST_TOP);
            currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_WALL_OR_FLOOR);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 1);
            putTileInTileset(tmp2);
          }
          if ((cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_BASE) || 
          (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_OBJECT3X3)){
            tmp2 = getSubTile(tmp, 0, 0);
            if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_BASE) {
              currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_CORNER_NW);
              currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_WALL_OR_FLOOR);
            }
            if (cbCaptureType.getSelectedIndex() == CaptureScreen.CAPTURE_TYPE_OBJECT3X3) {
              currentTileInfo.setTileSeriesType(TileInfo.TILE_SERIES_TYPE_OBJECT_3X3_NW);
              currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_OBJECT);
            }
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 0);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 2, 2);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 2);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 2);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 0, 1);
            putTileInTileset(tmp2);
            getNextTileInTileset();
            tmp2 = getSubTile(tmp, 1, 1);
            putTileInTileset(tmp2);
          }
        }
        this.repaint();
      }
   }
   if ("capture single".equals(e.getActionCommand())) {
     biImage = FileUtil.readImageFile(textFilename.getText());
     if (biImage != null) {
       dlgCaptureScreen = new CaptureScreen(new JFrame(), biImage, 0);
       BufferedImage tmp = dlgCaptureScreen.getSelected();
       if (tmp != null) {
         currentTile.loadTile(tmp);
         tmp.flush();
         dlgCaptureScreen.dispose();
         if (index == newTileIndex) {
           newTileIndex++;
         }
         this.repaint();
       }
    }
   }
   if ("prev-anim".equals(e.getActionCommand())) {
     if (currentTileInfo.getNextAnimTile() > 0) {
       currentTileInfo.setNextAnimTile(currentTileInfo.getNextAnimTile()-1);
       this.repaint();
     }
   }
   if ("next-anim".equals(e.getActionCommand())) {
     if (currentTileInfo.getNextAnimTile() < currentTileset.size()-1) {
       currentTileInfo.setNextAnimTile(currentTileInfo.getNextAnimTile()+1);
       this.repaint();
     }
   }
   if ("prevtype".equals(e.getActionCommand())) {
     if (currentTileInfo.getTileSeriesType() > 0) {
       currentTileInfo.setTileSeriesType((byte) (currentTileInfo.getTileSeriesType()-1));
     }
     this.repaint();
   }
   if ("nexttype".equals(e.getActionCommand())) {
     if (currentTileInfo.getTileSeriesType() < TileInfo.TILE_SERIES_TYPE_LAST) {
       currentTileInfo.setTileSeriesType((byte) (currentTileInfo.getTileSeriesType()+1));
     }
     this.repaint();
   }
   if ("prev".equals(e.getActionCommand())) {
     if (index > 0) {
       index--;
       currentTile = currentTileset.getTile(index);
       currentTileInfo = currentTileset.getTileInfo(index);
       cbSpeed.setSelectedIndex(currentTileInfo.getSpeedIndex());
       cbPosition.setSelectedIndex(currentTileInfo.getDefaultPlace());
       cbLightEffect.setSelectedIndex(currentTileInfo.getLightEffect());
       textSeriesNum.setText(String.valueOf(currentTileInfo.getTileSeries()));
       animIndex = index;
       this.repaint();
     }
   }
   if ("next".equals(e.getActionCommand())) {
     if (index < newTileIndex) {
       index++;
       if ((index == newTileIndex) && (currentTileset.size()-1 < newTileIndex)) {
         currentTile = new Tile();
         currentTileset.addTile(currentTile);
         currentTileInfo = currentTileset.getTileInfo(index);
         this.repaint();
       } else {
         currentTile = currentTileset.getTile(index);
         currentTileInfo = currentTileset.getTileInfo(index);
         this.repaint();
       }          
       cbSpeed.setSelectedIndex(currentTileInfo.getSpeedIndex());
       cbPosition.setSelectedIndex(currentTileInfo.getDefaultPlace());
       cbLightEffect.setSelectedIndex(currentTileInfo.getLightEffect());
       textSeriesNum.setText(String.valueOf(currentTileInfo.getTileSeries()));
       animIndex = index;
     }        
   }
   if ("Start".equals(e.getActionCommand())) {
     index = 0;
     currentTile = currentTileset.getTile(index);
     currentTileInfo = currentTileset.getTileInfo(index);
     this.repaint();
     cbSpeed.setSelectedIndex(currentTileInfo.getSpeedIndex());
     cbPosition.setSelectedIndex(currentTileInfo.getDefaultPlace());
     cbLightEffect.setSelectedIndex(currentTileInfo.getLightEffect());
     textSeriesNum.setText(String.valueOf(currentTileInfo.getTileSeries()));
     animIndex = index;
   }
   if ("End".equals(e.getActionCommand())) {
     if (newTileIndex-1 > 0) {
       index = newTileIndex-1;
       currentTile = currentTileset.getTile(index);
       currentTileInfo = currentTileset.getTileInfo(index);
       this.repaint();
       cbSpeed.setSelectedIndex(currentTileInfo.getSpeedIndex());
       cbPosition.setSelectedIndex(currentTileInfo.getDefaultPlace());
       cbLightEffect.setSelectedIndex(currentTileInfo.getLightEffect());
       textSeriesNum.setText(String.valueOf(currentTileInfo.getTileSeries()));
       animIndex = index;
     }
   }
   if ("Insert".equals(e.getActionCommand())) {
     currentTile = new Tile();
     currentTileset.addTile(currentTile, index);
     currentTileInfo = currentTileset.getTileInfo(index);
     newTileIndex++;
     this.repaint();
     cbSpeed.setSelectedIndex(currentTileInfo.getSpeedIndex());
     cbPosition.setSelectedIndex(currentTileInfo.getDefaultPlace());
     cbLightEffect.setSelectedIndex(currentTileInfo.getLightEffect());
     textSeriesNum.setText(String.valueOf(currentTileInfo.getTileSeries()));
     animIndex = index;        
   }
   if ("Delete".equals(e.getActionCommand())) {
     if (currentTileset.size() > 1) {
       currentTileset.removeTile(index);
       newTileIndex--;
       if (index > newTileIndex) {
         index = newTileIndex;
       }
       currentTile = currentTileset.getTile(index);
       currentTileInfo = currentTileset.getTileInfo(index);
       cbSpeed.setSelectedIndex(currentTileInfo.getSpeedIndex());
       cbPosition.setSelectedIndex(currentTileInfo.getDefaultPlace());
       cbLightEffect.setSelectedIndex(currentTileInfo.getLightEffect());
       textSeriesNum.setText(String.valueOf(currentTileInfo.getTileSeries()));
       animIndex = index;
       this.repaint();
     }
   }
   if ("ApplyAll".equals(e.getActionCommand())) {
     byte pos = currentTileInfo.getDefaultPlace();
     byte speed = currentTileInfo.getAnimSpeed();
     int light  = currentTileInfo.getLightEffect();
     for (int i=index;i<currentTileset.size();i++) {
       TileInfo tmp = currentTileset.getTileInfo(i);
       if (tmp != null) {
         tmp.setAnimSpeed(speed);
         tmp.setDefaultPlace(pos);
         tmp.setLightEffect(light);
       }
     }
   }
   if ("cbSpeed".equals(e.getActionCommand())) {
     switch (cbSpeed.getSelectedIndex()) {
     case 0: {currentTileInfo.setAnimSpeed(TileInfo.ANIM_SPEED_FAST); break;} 
     case 1: {currentTileInfo.setAnimSpeed(TileInfo.ANIM_SPEED_NORMAL); break;} 
     case 2: {currentTileInfo.setAnimSpeed(TileInfo.ANIM_SPEED_SLOW); break;} 
     }
     
   }
   if ("cbPosition".equals(e.getActionCommand())) {
       switch (cbPosition.getSelectedIndex()) {
       case 0: {currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_WALL_OR_FLOOR); break;} 
       case 1: {currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_OBJECT); break;} 
       case 2: {currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_ITEM); break;} 
       case 3: {currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_DECORATION); break;} 
       case 4: {currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_CREATURE); break;} 
       case 5: {currentTileInfo.setDefaultPlace(TileInfo.TILE_PLACE_TOP); break;} 
       }
       
     }
   if ("cbLight".equals(e.getActionCommand())) {
     switch (cbLightEffect.getSelectedIndex()) {
     case 0: {currentTileInfo.setLightEffect(TileInfo.LIGHT_EFFECT_NO_EFFECT); break;} 
     case 1: {currentTileInfo.setLightEffect(TileInfo.LIGHT_EFFECT_DIM_LIGHT); break;} 
     case 2: {currentTileInfo.setLightEffect(TileInfo.LIGHT_EFFECT_FIREPLACE); break;} 
     case 3: {currentTileInfo.setLightEffect(TileInfo.LIGHT_EFFECT_BRIGHT_LIGHT); break;} 
     case 4: {currentTileInfo.setLightEffect(TileInfo.LIGHT_EFFECT_NORMAL_LIGHT); break;} 
     case 5: {currentTileInfo.setLightEffect(TileInfo.LIGHT_EFFECT_MAGICAL); break;} 
     case 6: {currentTileInfo.setLightEffect(TileInfo.LIGHT_EFFECT_MAGICAL_FAST); break;} 
     case 7: {currentTileInfo.setLightEffect(TileInfo.LIGHT_EFFECT_CAST_SHADOW); break;} 
     }
     
   }
   if ("timer".equals(e.getActionCommand())) {        
     timerSpeed++;
     nextTileInfo = currentTileset.getTileInfo(animIndex);
     if (timerSpeed%nextTileInfo.getAnimSpeed()==0) {
       animIndex = nextTileInfo.getNextAnimTile();
     }
     if (timerSpeed == TileInfo.ANIM_SPEED_SLOW) {
       timerSpeed = 0;
     }
     this.repaint();
   }
   if ("SeriesNum".equals(e.getActionCommand())) {
       currentTileInfo.setTileSeries(Integer.valueOf(textSeriesNum.getText()));
   }
 }

 
  /**
   * Tiles x coordinate
   */
  private int tileX = 5;
  /**
   * Current tile Y coordinate
   */
  private int currentTileY = 100;
  /**
   * Next frame tile y coordinate
   */
  private int nextFrameTileY=240;
  /**
   * Animation frame tile y coordinate
   */
  private int animFrameTileY=280;

  @Override	
  public void paint(Graphics g) {
    super.paint(g);
    if (currentTile.isTransparentColor()) {
      labelTransparent.setText("Transparent color");
      labelTransparent.setForeground(new Color(currentTile.getTransparentColor(), true));
    } else {
      labelTransparent.setText("No transparent color");
      labelTransparent.setForeground(Color.black);
    }
    labelIndexNumber.setText("Index:"+String.valueOf(index));
    labelNextAnimNumber.setText("Next Frame:"+String.valueOf(currentTileInfo.getNextAnimTile()));
    labelSeriesType.setText("Type:"+currentTileInfo.getSeriesTypeAsString()+
        "'"+String.valueOf(currentTileInfo.getTileSeriesType())+"'");
    
    // CurrentTile drawing
    Graphics2D gr = screen.createGraphics();
    gr.setColor(new Color(158,158,158));
    gr.fillRect(1, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.setColor(new Color(104, 104, 104));
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.setColor(Color.black);        
    gr.drawRect(0, 0, Tile.MAX_WIDTH+1, Tile.MAX_HEIGHT+1);
    currentTile.putTile(screen, 1,1,0);
    g.drawImage(screen, tileX, currentTileY, null);
    // nextFram drawing
    gr = nextFrame.createGraphics();
    gr.setColor(new Color(158,158,158));
    gr.fillRect(1, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.setColor(new Color(104, 104, 104));
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.setColor(Color.black);        
    gr.drawRect(0, 0, Tile.MAX_WIDTH+1, Tile.MAX_HEIGHT+1);
    nextTile = currentTileset.getTile(currentTileInfo.getNextAnimTile());
    if (nextTile != null) {
      nextTile.putTile(nextFrame, 1,1,0);
    }
    g.drawImage(nextFrame, tileX, nextFrameTileY, null);
    // animFram drawing
    gr = animFrame.createGraphics();
    gr.setColor(new Color(158,158,158));
    gr.fillRect(1, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.setColor(new Color(104, 104, 104));
    gr.fillRect(1+Tile.MAX_WIDTH/2, 1, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.fillRect(1, 1+Tile.MAX_HEIGHT/2, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
    gr.setColor(Color.black);        
    gr.drawRect(0, 0, Tile.MAX_WIDTH+1, Tile.MAX_HEIGHT+1);
    nextTile = currentTileset.getTile(animIndex);
    if (nextTile != null) {
      nextTile.putTile(animFrame, 1,1,0);
    }
    if (currentTileInfo.isBlocked()) {
        gr.setColor(Color.red);
        gr.drawLine(6, 6, Tile.MAX_WIDTH-5, Tile.MAX_HEIGHT-5);
        gr.drawLine(Tile.MAX_WIDTH-5, 6, 6, Tile.MAX_HEIGHT-5);
    }
    if (currentTileInfo.isNorthBlocked()) {
        gr.setColor(Color.red);
        gr.drawLine(1, 1, Tile.MAX_WIDTH, 1);            
    }
    if (currentTileInfo.isWestBlocked()) {
        gr.setColor(Color.red);
        gr.drawLine(1, 1, 1,Tile.MAX_HEIGHT);            
    }
    if (currentTileInfo.isEastBlocked()) {
        gr.setColor(Color.red);
        gr.drawLine(Tile.MAX_WIDTH+1, 1, Tile.MAX_WIDTH+1,Tile.MAX_HEIGHT);            
    }
    if (currentTileInfo.isSouthBlocked()) {
        gr.setColor(Color.red);
        gr.drawLine(1, Tile.MAX_HEIGHT+1, Tile.MAX_WIDTH, Tile.MAX_HEIGHT+1);            
    }
    g.drawImage(animFrame, tileX, animFrameTileY, null);
  }



  @Override
  public void mouseClicked(MouseEvent e) {
    int mouseX = e.getPoint().x;
    int mouseY = e.getPoint().y;
    // Check that mouse is inside currentTile
    if ((mouseX > tileX+1) && (mouseX <tileX+1+Tile.MAX_WIDTH) &&
        (mouseY > currentTileY+1) && (mouseY <currentTileY+1+Tile.MAX_HEIGHT)) {
      int myColor = currentTile.getColor(mouseX-6, mouseY-76);
      // and set transparent color
      currentTile.setTransparentColor(myColor);
    } else if ((mouseX > tileX+1+5) && (mouseX <tileX+1+Tile.MAX_WIDTH-5) &&
            (mouseY > animFrameTileY+1+5) && (mouseY <animFrameTileY+1+Tile.MAX_HEIGHT-5)) {
        if (currentTileInfo.isBlocked()) {
          currentTileInfo.setTilePassable();   
        } else {
          currentTileInfo.setTileIsBlocked();
        }
      } else if ((mouseX > tileX+1) && (mouseX <tileX+1+Tile.MAX_WIDTH) &&
              (mouseY > animFrameTileY+1) && (mouseY <animFrameTileY+1+5)) {
          if (currentTileInfo.isNorthBlocked()) {
            currentTileInfo.setTileNorthBlocked(false);   
          } else {
            currentTileInfo.setTileNorthBlocked(true);
          }
        } else if ((mouseX > tileX+1) && (mouseX <tileX+1+5) &&
                (mouseY > animFrameTileY+1) && (mouseY <animFrameTileY+1+Tile.MAX_HEIGHT)) {
            if (currentTileInfo.isWestBlocked()) {
              currentTileInfo.setTileWestBlocked(false);   
            } else {
              currentTileInfo.setTileWestBlocked(true);
            }
          } else if ((mouseX > tileX+1) && (mouseX <tileX+1+Tile.MAX_WIDTH) &&
                  (mouseY > animFrameTileY+1+Tile.MAX_HEIGHT-5) && (mouseY <animFrameTileY+1+Tile.MAX_HEIGHT)) {
              if (currentTileInfo.isSouthBlocked()) {
                currentTileInfo.setTileSouthBlocked(false);   
              } else {
                currentTileInfo.setTileSouthBlocked(true);
              }
            } else if ((mouseX > tileX+1+Tile.MAX_WIDTH-5) && (mouseX <tileX+1+Tile.MAX_WIDTH) &&
                    (mouseY > animFrameTileY+1) && (mouseY <animFrameTileY+1+Tile.MAX_HEIGHT)) {
                if (currentTileInfo.isEastBlocked()) {
                  currentTileInfo.setTileEastBlocked(false);   
                } else {
                  currentTileInfo.setTileEastBlocked(true);
                }
              } else{// If not inside then just remove transparent color from tile
      currentTile.removeTransparentColor();
    }
    this.repaint();
  }



  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }



  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }



  @Override
  public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }



  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }


	
}
