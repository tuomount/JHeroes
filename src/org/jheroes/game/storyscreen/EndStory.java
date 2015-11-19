package org.jheroes.game.storyscreen;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jheroes.gui.GuiStatics;
import org.jheroes.gui.RasterFonts;
import org.jheroes.map.Party;
import org.jheroes.map.character.Character;
import org.jheroes.map.character.Faces;


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
 * EndStory Screens
 * 
 */
public class EndStory extends StoryScreen {

  /**
   * Constructor of End Story
   * @param party
   */
  public EndStory(Party party) {
    super(party);
  }

  /** 
   * Story Screen Data
   */
  private StoryScreenData screenData;
  
  /**
   * Possible hero title shown in some screens
   */
  private String heroTitle = "hero";


  /**
   * Draw whole party into throne room
   */
  private void drawCharactersIntoThroneRoom() {
    int throne_x = 325;
    int throne_y = 300;
    int party_x = 325;
    int party_y = 370;
    int party_mod = -16*(this.getParty().getNumberOfPartyMembers()-1);
    if (this.getParty().getStoryVariable(0)==11) {
      // Marcus
      GuiStatics.getCharacterTileset().getTile(2034).putTile(screenData.getImage(), throne_x, throne_y, 0);
      GuiStatics.getCharacterTileset().getTile(2023).putTile(screenData.getImage(), throne_x, throne_y-32, 0);
      for (int i=0;i<this.getParty().getNumberOfPartyMembers();i++) {
        Character chr = this.getParty().getPartyChar(i);
        chr.doStand();
        chr.doStand();
        chr.doStand();
        chr.doStand();
        chr.setHeading(Character.DIRECTION_NORTH);
        GuiStatics.getCharacterTileset().getTile(chr.getBodyTile()).putTile(screenData.getImage(), party_x+party_mod+(i)*32, party_y, 0);
        GuiStatics.getCharacterTileset().getTile(chr.getHeadTile()).putTile(screenData.getImage(), party_x+party_mod+(i)*32, party_y-32, 0);
      }
    }
    if (this.getParty().getStoryVariable(43)==3) {
      // Vicster
      GuiStatics.getCharacterTileset().getTile(1470).putTile(screenData.getImage(), throne_x, throne_y, 0);
      GuiStatics.getCharacterTileset().getTile(1459).putTile(screenData.getImage(), throne_x, throne_y-32, 0);
      Character chr = this.getParty().getPartyChar(0);
      chr.setHeading(Character.DIRECTION_NORTH);
      GuiStatics.getCharacterTileset().getTile(chr.getBodyTile()).putTile(screenData.getImage(), party_x, party_y, 0);
      GuiStatics.getCharacterTileset().getTile(chr.getHeadTile()).putTile(screenData.getImage(), party_x, party_y-32, 0);
    }
    if (this.getParty().getStoryVariable(42)==3) {
      // Player becomes empiror
      Character chr = this.getParty().getPartyChar(0);
      chr.setHeading(Character.DIRECTION_SOUTH);
      GuiStatics.getCharacterTileset().getTile(chr.getBodyTile()).putTile(screenData.getImage(), throne_x, throne_y, 0);
      GuiStatics.getCharacterTileset().getTile(chr.getHeadTile()).putTile(screenData.getImage(), throne_x, throne_y-32, 0);
    }
  }
  
  /**
   * Draw generic party information screen
   * @return BufferedImage
   */
  public BufferedImage drawPartyInfo() {
    BufferedImage result = new BufferedImage(672, 544, BufferedImage.TYPE_4BYTE_ABGR);
    GradientPaint gradient = new GradientPaint(0,0, GuiStatics.COLOR_BLUE,
        result.getWidth(),result.getHeight(), GuiStatics.COLOR_DARK_BLUE, true);
    Graphics2D g2d = (Graphics2D) result.getGraphics();
    g2d.setPaint(gradient);    
    g2d.fillRect(0, 0, result.getWidth(), result.getHeight());
    
    RasterFonts fonts = new RasterFonts(result.getGraphics());
    fonts.setFontType(RasterFonts.FONT_TYPE_BIG);
    fonts.drawString((result.getWidth()/2-200)+1, 5, "Heroes of Hawks Haven");
    fonts = new RasterFonts(result.getGraphics());
    fonts.setFontType(RasterFonts.FONT_TYPE_WHITE);

    int party_x = 50;
    int party_y = 90;
    int size_Y = 80;
    for (int i = 0;i<this.getParty().getNumberOfPartyMembers();i++) {
      Character chr = this.getParty().getPartyChar(i);
      chr.setHeading(Character.DIRECTION_SOUTH);
      g2d.drawImage(Faces.getFace(chr.getFaceNumber()),party_x,party_y+i*size_Y-20,null);
      GuiStatics.getCharacterTileset().getTile(chr.getBodyTile()).putTile(result, party_x+50, party_y+i*size_Y, 0);
      GuiStatics.getCharacterTileset().getTile(chr.getHeadTile()).putTile(result, party_x+50, party_y+i*size_Y-32, 0);
      fonts.drawString(party_x+160, party_y+i*size_Y-20, chr.getLongName());
      fonts.drawString(party_x+160, party_y+i*size_Y, "Lvl"+chr.getLevel()+" Exp:"+chr.getExperience());
      fonts.drawString(party_x+160, party_y+i*size_Y+20, "Played role: "+this.getParty().getCharacterRole(i));
      g2d.setColor(GuiStatics.COLOR_GREEN);
      g2d.drawRect(party_x-5, party_y-25+i*size_Y, 600, 80);
      g2d.setColor(GuiStatics.COLOR_DARK_GREEN);
      g2d.drawRect(party_x-6, party_y-26+i*size_Y, 602, 82);
      g2d.drawRect(party_x-4, party_y-24+i*size_Y, 599, 79);
    }
    return result;
  }
  
  @Override
  public StoryScreenData getStoryScreen(int index) {
    if (screenData == null) {
      screenData = new StoryScreenData();
    }
    switch (index) {
    case 0: {
      screenData.setImage(GuiStatics.loadImage(Screens.THRONE_ROOM_IMAGE));
      drawCharactersIntoThroneRoom();
      if (this.getParty().getStoryVariable(43)==3) {
        screenData.setText("Marcus Agnius was killed by your hand. True heir is now dead. You also dispose all" +
            " the evidence about Marcus Agnius. Now Vicster Arboshate is crowned as Empiror. Vicster made you" +
            " a high counsilor.");
        heroTitle = "high counsilor";
      }
      if (this.getParty().getStoryVariable(42)==3) {
        screenData.setText("Both Marcus Agnius and Vicster Arboshate were killed by your hand. True heir is now dead." +
            " You claimed Marcus's locket as yours and you were crowned as Empiror.");
        heroTitle = "Empiror";
      }
      if (this.getParty().getStoryVariable(0)==11) {
        StringBuilder sb = new StringBuilder();
        for (int i =0;i<this.getParty().getNumberOfPartyMembers();i++) {
          sb.append(this.getParty().getPartyChar(i).getLongName());
          if (i+1<this.getParty().getNumberOfPartyMembers()){
            sb.append(", ");
          }
        }
        screenData.setText("Marcus Agnius was crowned as new Empiror. He is true heir to empiror Cyriacus." +
            " Old high counsilor Vicster Arborshate was killed and declared as enemy of Empire. Marcus Agnius announced" +
            " that party members "+sb.toString()+" are Heroes of Hawks Haven!");
        if (this.getParty().getNumberOfPartyMembers()==1) {
          heroTitle = "Hero";
        } else {
          heroTitle = "Heroes";          
        }
      }
      break;
    }
    case 1: {
      screenData.setImage(GuiStatics.loadImage(Screens.THRONE_ROOM_IMAGE));
      drawCharactersIntoThroneRoom();
      if (this.getParty().getStoryVariable(43)==3) {
        screenData.setText("Vicster immediately raised taxes. Majority of taxes went to his pockets, some also yours." +
        		" People who refused to pay taxes were taken as land slaves. People does not really love new empiror or" +
        		" high counsilor but they cannot do anything for that. Let's see what happened elsewhere...");
      }
      if (this.getParty().getStoryVariable(42)==3) {
        screenData.setText("Since you got power with brute force people kind of liked you, especially those" +
        		" who like powerful leaders. But you did not stand still, you ventured forth and brutally slayed" +
        		" everyone whom opposed you. Let's see what happened elsewhere...");
      }
      if (this.getParty().getStoryVariable(0)==11) {
        StringBuilder sb = new StringBuilder();
        for (int i =0;i<this.getParty().getNumberOfPartyMembers();i++) {
          sb.append(this.getParty().getPartyChar(i).getLongName());
          if (i+1<this.getParty().getNumberOfPartyMembers()){
            sb.append(", ");
          }
        }
        screenData.setText("Marcus ruled fair and honestly. People of Hawks Haven really liked him. Since you are" +
        		" the Hero of Hawks Haven you did heroic adventurer where ever such tasks were needed." +
        		" Let's see what happened elsewhere...");
      }
      break;
    }
    case 2: {
      // Nerisella Quests
      if (this.getParty().getStoryVariable(1)==5) {
        screenData.setImage(GuiStatics.loadImage(Screens.GOOD_PARTY_IMAGE));
        screenData.setText("Nerisella Songsteel's adventuring school keeps training more good adventurers whom" +
        		" make heroic deeds and help people of Hawks Haven.");
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.EVIL_PARTY_IMAGE));
        screenData.setText("Nerisella Songsteel's adventuring school keeps training more adventurers but" +
        		" these are not good. They do adventuring for coppers and sometime cause more trouble than help.");
        
      }
      break;
    }
    case 3: {
      // Witch of Ravenwoods
      if (this.getParty().getStoryVariable(2)==2) {
        screenData.setImage(GuiStatics.loadImage(Screens.FARM_CARTS_IMAGE));
        screenData.setText("Since witch of Ravenwoods is gone Tigersoul farm produces" +
        		" farm goods better than ever before.");
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.FARM_GONE_IMAGE));
        screenData.setText("Witch of Ravenwoods gets more aggressive and first it curses farm. Crop grows" +
        		" rotten vegetables and after few month each sheep and goat mysteriously dies. Tigersoul family" +
        		" moves away from Ravenrow...");
        
      }
      break;
    }
    case 4: {
      // Zombie well
      if (this.getParty().getStoryVariable(3)==3) {
        screenData.setImage(GuiStatics.loadImage(Screens.FRESH_WELL_IMAGE));
        screenData.setText("Water in Ravenrow well stays clean and clear. Village of Ravenrow grows a bit" +
        		" and becomes important farming village in Hawks Haven.");
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.WELL_OF_DEATH_IMAGE));
        screenData.setText("Water in Ravenrow well first starts taste foul and bad, but quite soon" +
        		" after this people start get weird sickness and they die. Village of Ravenrow dies out...");
        
      }
      break;
    }
    case 5: {
      // Lonely Inn
      if (this.getParty().getStoryVariable(5)==2) {
        screenData.setImage(GuiStatics.loadImage(Screens.LONELY_INN_SUCCESS_IMAGE));
        screenData.setText("Since Wenni Whitespoon got back her amulet business in Lonely Inn started to" +
        		" flourish. She was able to make Lonely Inn excellent Inn with great breakfasts.");
      } else if (this.getParty().getStoryVariable(5)==3){
        screenData.setImage(GuiStatics.loadImage(Screens.LONELY_INN_NO_MORE_IMAGE));
        screenData.setText("Since facts about Wenni and Robin Whitespoons being robbed people started to" +
        		" avoid Lonely Inn. Finally Lonely Inn was forced to closed.");
        
      } else {
        return getNextStoryScreen();
      }
      break;
    }
    case 6: {
      // Werdinor Quest
      if (this.getParty().getStoryVariable(6)==6) {
        screenData.setImage(GuiStatics.loadImage(Screens.NECROMANCER_TOWER_IMAGE));
        screenData.setText("Far away in north mysterious tower has been risen. People talk that" +
        		" dead people wander around the tower and whole place is run by powerful necromancer." +
        		" No body knows who is really living in the tower...");
      } else {
        return getNextStoryScreen();
      }
      break;
    }
    case 7: {
      // Raiding Hobgoblins
      if (this.getParty().getStoryVariable(7)>=2) {
        if (this.getParty().getStoryVariable(8)==2) {
          screenData.setImage(GuiStatics.loadImage(Screens.HOBGOBLIN_CAVE_2_IMAGE));
          screenData.setText("Hobgoblins and their leader Burt are killed in massive" +
          		" explosion. This explosion also sealed the cave entrance...");
        } else {
          screenData.setImage(GuiStatics.loadImage(Screens.HOBGOBLIN_CAVE_1_IMAGE));
          screenData.setText("Hobgoblins leave the caves in Shadowwoods since their leader Burt is killed...");          
        }
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.HOBGOBLIN_CAVE_1_IMAGE));
        screenData.setText("Hobgoblins still live in Cave in the Shadowwoods. This makes" +
        		" Shadowwoods dangerous and grim place...");
      }
      break;
    }
    case 8: {
      // Raiding Hobgoblins continue
      if (this.getParty().getStoryVariable(7)>=2) {
        if (this.getParty().getStoryVariable(8)<2) {
          if (this.getParty().getStoryVariable(1)==5) {
            screenData.setImage(GuiStatics.loadImage(Screens.GOOD_PARTY_CAVE_IMAGE));
            screenData.setText("Since hobgoblin cave entrance was open, wandering party went adventuring there" +
            		" and found a gold vein from there...");
          } else {
            screenData.setImage(GuiStatics.loadImage(Screens.EVIL_PARTY_CAVE_IMAGE));
            screenData.setText("Since hobgoblin cave entrance was open, wandering party went adventuring there" +
                " and found a gold vein from there...");
          }
        } else {
          screenData.setImage(GuiStatics.loadImage(Screens.HOBGOBLIN_CAVE_2_IMAGE));
          screenData.setText("Since explosion sealed the cave entrance nobody found a" +
          		" gold vein from the cave.");
        }
      } else {
        return getNextStoryScreen();
      }
      break;
    }
    case 9: {
      // Kerry Silverblade
      if (this.getParty().getStoryVariable(10)==11) {
        screenData.setImage(GuiStatics.loadImage(Screens.KERRY_SILVERBLADE_IMAGE));
        screenData.setText("Kerry Silverblade managed city guards so well that they were able get rid of" +
        		" hobgoblins under the Hawks Haven. He was promoted as personal guard of Empiror. Rumor says" +
        		" that "+heroTitle+" of Hawks Haven was also in city guards.");
      } else {
        return getNextStoryScreen();
      }
      break;
    }
    case 10: {
      // Orphan kids
      if (this.getParty().getStoryVariable(11)>4) {
        screenData.setImage(GuiStatics.loadImage(Screens.ORPHAN_KIDS_IMAGE));
        screenData.setText("Heroon's Oprhanage helps more oprhan street children than ever before." +
        		" They all get good care and food now that Witch is gone...");
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.WITCHES_IMAGE));
        screenData.setText("Mysterious witches start flying at night after a month they start terrorizing " +
        		" people of Hawks Haven. No one knows where all these witches came from...");
      }
      break;
    }
    case 11: {
      // Guild Fight
      if (this.getParty().getStoryVariable(14)==7) {
        screenData.setImage(GuiStatics.loadImage(Screens.MAGE_GUILD_FIRE_IMAGE));
        screenData.setText("Thieves Guild get upper hand from Mage Guild and accidentaly" +
        		" Mage Guild house catch fire one night. For this reason Mage Guild decided" +
        		" to leave from Hawks Haven.");
      } else if (this.getParty().getStoryVariable(15)==7) {
        screenData.setImage(GuiStatics.loadImage(Screens.THIEVES_GUILD_DESTRUCTION_IMAGE));
        screenData.setText("Mage Guild becomes more powerful than Thieves Guild and one" +
        		" night Thieves Guild house simply vanishes. Since Thieves Guild house is gone thieves leave from Hawks Haven.");
      } else {
        return getNextStoryScreen();
      }
      break;
    }
    case 12: {
      // Heroon takeover
      if (this.getParty().getStoryVariable(13)<5 || this.getParty().getStoryVariable(13)==6) {
        screenData.setImage(GuiStatics.loadImage(Screens.HEROON_TAKE_OVER_IMAGE));
        screenData.setText("After couple of year a huge number of sketal warrior and wizards " +
        		" arrive to Hawks Haven and they take control of Hawks Haven Arcane District. Castle district" +
        		" stays clean from undeads but they also patrol on Market District.");
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.TEMPLE_IMAGE));
        screenData.setText("Heroon does not take over the Hawks Haven temple and whole" +
        		" city stays clean from undead army...");
      }
      break;
    }
    case 13: {
      // Heroon takeover
      if (this.getParty().getStoryVariable(13)==0 || this.getParty().getStoryVariable(13)==6) {
        screenData.setImage(GuiStatics.loadImage(Screens.HEROON_TAKE_OVER_IMAGE));
        screenData.setText("Rumors tell that Heroon has risen and become evil. He now" +
        		" controls major parts of Hawks Haven city.");
      } else {
        if (this.getParty().getStoryVariable(17)==1) {
          screenData.setImage(GuiStatics.loadImage(Screens.TEMPLE_BROKE_IMAGE));
          screenData.setText("Since Heroon's body was destroyed pilgrims stopped visiting temple of" +
          		" Hawks Haven and temple did not get donations anymore. This cause temple to decay slowly...");
        }
        if (this.getParty().getStoryVariable(17)==2) {
          screenData.setImage(GuiStatics.loadImage(Screens.TEMPLE_PILGRIMS_IMAGE));
          screenData.setText("Since Heroon's body was saved and well kept more pilgrims come to" +
          		" pilgrimage to Temple of Hawks Haven. Pilgrim donate more copper than ever before.");
        }
      } 
      break;
    }
    case 14: {
      // Vicsdor Dog is dead
      if (this.getParty().getStoryVariable(19)==9 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.DEAD_DOG_IMAGE));
        screenData.setText("Vicsdor Woodenknight dog was killed by a skeleton in Royal tomb...");
      } else {
        return getNextStoryScreen();
      }
      break;
    }
    case 15: {
      // Aliya Northfork ring
      if (this.getParty().getStoryVariable(20)==3 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.LOTTERY_IMAGE));
        screenData.setText("Aliya Northfork got her ring bacḱ and she got extremely luckly." +
        		" She won Hawks Haven lottery and won awful lot of coppers. She never found true love" +
        		" since she felt that she needed to take care of her money.");
      } else if (this.getParty().getStoryVariable(20)==4 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.MARRIAGE_IMAGE));
        if (this.getParty().getStoryVariable(19)==3 ) {
          GuiStatics.getCharacterTileset().getTile(198).putTile(screenData.getImage(), 350, 200, 0);
        }
        screenData.setText("Aliya Northfork never got back her ring but she married Vicsdor Woodenknight." +
        		" They lived happyly ever after...");
      }else if (this.getParty().getStoryVariable(20)==5 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.GRAVE_IMAGE));
        screenData.setText("When you turned down Aliya Northfork quest she went to desparate and" +
        		" started to worry if she ever find her family ring. Eventually she died" +
        		" because of sorrow and worry.");
      } else{
        return getNextStoryScreen();
      }
      break;
    }
    case 16: {
      // Larek's fishing rod
      if (this.getParty().getStoryVariable(22)==9 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.LAREK_FISHERMAN_IMAGE));
        screenData.setText("Larek got back his fishing rod and he keep fishing. Felper keeps farming" +
        		" his small farms in Riverton.");
      } else if (this.getParty().getStoryVariable(20)==4 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.LAREK_DROWN_IMAGE));
        screenData.setText("Larek believed that he lost his fishing rod. He became mad and drowned himself." +
        		" Felper started to fishing but he never really learn it well.");
      } else{
        return getNextStoryScreen();
      }
      break;
    }
    case 17: {
      // Wolfmen in Riverton
      if (this.getParty().getStoryVariable(25)==6 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.RIVERTON_IMAGE));
        screenData.setText("Riverton was saved from wolfmen whom planed to occupy the town.");
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.RIVERTON_OCCUPIED_IMAGE));
        screenData.setText("After six months mysterious wolfmen arrived into Riverton and" +
        		" started to kill people. Wolfmen kill everyone in town. Riverton is now occupied by wolfmen.");
      } 
      break;
    }
    case 18: {
      // Mystery island
      if (this.getParty().getStoryVariable(27)==7 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.GHOST_SHIP_IMAGE));
        screenData.setText("Mysterious ghost ship has been seen sailing in the sea. " +
        		" On stormy weather ghost ship sail close to the Hawks Haven. Rumor says" +
        		" that undead pirate captain commands the ship.");
      } else  if (this.getParty().getStoryVariable(27)==8 ){
        screenData.setImage(GuiStatics.loadImage(Screens.TREASURE_CHEST_IMAGE));
        screenData.setText("Treasure of mysterious island is covered by "+heroTitle +" of Hawks Haven. " +
        		" Stories start to spread about this mysterious island and adventures on it.");
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.LOST_ISLAND_IMAGE));
        screenData.setText("This lost island is never found. Who knows what kind of treasures it holds...");        
      }
      break;
    }
    case 19: {
      // Crystal lake
      if (this.getParty().getStoryVariable(30)==7 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.CRYSTAL_LAKE_EVIL_IMAGE));
        screenData.setText("Elvirella evil spirit grows stongest power in Crystal Lake. All" +
        		" the tree dies nearby and Elvirella's castle grows bigger than ever before.");
      } else  if (this.getParty().getStoryVariable(31)==7 ){
        screenData.setImage(GuiStatics.loadImage(Screens.CRYSTAL_LAKE_GOOD_IMAGE));
        screenData.setText("Mirella forest spirit grows strongest power in Crystal Lake. Elvirella's" +
        		" castle crumbles down and forests overgrows the lake.");
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.CRYSTAL_LAKE_NEUTRAL_IMAGE));
        screenData.setText("Balance stays between good and evil in Crystal Lake...");
      }
      break;
    }
    case 20: {
      // Drunken man snatcher
      if (this.getParty().getStoryVariable(36)==6 ) {
        screenData.setImage(GuiStatics.loadImage(Screens.DRUNKENMAN_IMAGE));
        screenData.setText("Drunken men are not disappearing any more in Corndale. Nobody knew what caused this." +
        		" Crulamin Tigersoul was last person whom disappeared.");
      } else  if (this.getParty().getStoryVariable(36)==9 ){
        screenData.setImage(GuiStatics.loadImage(Screens.DRUNKENMAN_IMAGE));
        screenData.setText("Drunken men are not disappearing any more in Corndale. Crulamin Tigersoul knows" +
        		" that female troll was snatching drunken men but Crulamin never talk about his encounter with" +
        		" this troll.");
      } else {
        screenData.setImage(GuiStatics.loadImage(Screens.DRUNKENMAN_SNATCHER_IMAGE));
        screenData.setText("Drunken men are disappering in Corndale. Nobody knew where they are gone or why." +
        		" One thing is sure they never comeback and are always men.");
      }
      break;
    }
    case 21: {
      // Last Info screen
      screenData.setImage(drawPartyInfo());
        screenData.setText("Congratulations! You have finished Heroes of Hawks Haven. " +
        		"Total playing time: "+this.getParty().getTotalPlayingTimeAsString()+". End of Heroes Of Hawks Haven varies" +
        				" what quests you have completed and there are different solutions for main quest. Have you find them all?");
      break;
    }
    default: {
      return null;
    }
    }
    return screenData;
  }

}
