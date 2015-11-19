package org.jheroes.map;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
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
 * Static functions used with Map
 * 
 */
public class MapUtilities {

  public static final Font smallSansSerif = new Font(Font.SANS_SERIF,0,8);
  public static final Font normalSansSerif = new Font(Font.SANS_SERIF,0,12);
  
  
  /**
   * Is current time already past certain Task time
   * @param currentHour
   * @param currentMin
   * @param taskTime in format hh:mm if something else time is never passed
   * @return boolean
   */
  public static boolean isTimePast(int currentHour, int currentMin, String taskTime) {
    boolean result = false;
    if (taskTime != null) {
      String[] parts = taskTime.split(":");      
      if (parts.length == 2) {
        parts[0] = parts[0].trim();
        parts[1] = parts[1].trim();
        try {
          int taskHour = Integer.valueOf(parts[0]);
          int taskMin = Integer.valueOf(parts[1]);
          if (currentHour > taskHour) {
            result = true;
          }
          if ((currentHour == taskHour) && (currentMin >= taskMin)) {
            result = true;
          }
        } catch (NumberFormatException e) {
          result = false;
        }
      }
    }
    return result;
  }  
  
  /**
   * Calculate distance between two coordinates
   * @param x1 first coordinate's X
   * @param y1 first coordinate's Y
   * @param x2 second coordinate's X
   * @param y2 second coordinate's Y
   * @return distance as integer
   */
  public static int getDistance(int x1,int y1, int x2, int y2) {
    int result = 0;
    int mx = Math.abs(x2-x1);
    int my = Math.abs(y2-y1);
    result = (int) Math.sqrt(mx*mx+my*my);
    return result;
  }
  
  /**
   * Save Buffered Image into file under folder screenshots
   * @param image
   */
  public static void saveScreenShot(BufferedImage image) {
    Calendar cal = Calendar.getInstance();
    File dir = new File("screenshots");
    if (!dir.exists()) {
      dir.mkdir();
    }
    String filename="Screenshot-"+cal.getTimeInMillis()+"-"+DiceGenerator.getRandom(10000)+".png";
    File file = new File("screenshots/"+filename);
    try {
      ImageIO.write(image, "png", file);
    } catch (IOException e) {
      System.err.println("Failing to write screen shot!");
      e.printStackTrace();
    }
  }
  
  private static final String[] SURNAME_FIRSTPART = {"Tiger","Dragon","Steel",
    "Song","Silver", "Red", "Black","White","Bronze","Iron","Green","Gray","Shadow",
    "Armor","Wolf","Forge","Ship","Battle","Spell","Wooden","Stone","Moon","Night",
    "South","North","East","West","Golden","Lion","Hay","Monk","Tall","Bold",
    "Strong","Brave"};
  private static final String[] SURNAME_SECONDPART = {"soul","rider","blade",
    "master","sword", "blood", "bone","hawk","shield","fist","hound","warrior","dagger",
    "smith","dawn","heart","sail","beard","gem","kin","arm","stone","axe","breaker",
    "spoon","fork","wind","knight","beer","tankard","casket","river","arrow","gold",
    "bow","drum","spruce","tree","tool","house","yard","gate","neck","sea","lotus",
    "shovel","knife","mace","hammer","lantern","helm","son","fox","bull"};
  
  private static String getSurname() {
    StringBuilder sb = new StringBuilder();
    if (DiceGenerator.getRandom(10)==0) {
      sb.append(getRandomSyllable(1));
      sb.append(getRandomSyllable(2));
      sb.append(getRandomSyllable(2));
    } else {
      sb.append(SURNAME_FIRSTPART[DiceGenerator.getRandom(SURNAME_FIRSTPART.length-1)]);
      sb.append(SURNAME_SECONDPART[DiceGenerator.getRandom(SURNAME_SECONDPART.length-1)]);
    }
    return sb.toString();
  }
  
  
  private static final String[] MALE_FIRSTNAME_PART1 = {"Cru", "Da", "Jo",
    "Vics","Ker","Mar","Cy","Wer","Zan","Fru","Kev","Sha","Bru","Co","Lan",
    "Ro","Tom","Tor","Thur","Fel","Lar","Hum","Ga","Gar","Her","Har"};
  private static final String[] MALE_FIRSTNAME_PART2 = {"la", "niel", "e",
    "ter","ry","cus","ria","di","no","dak","morn","ward","ek","nan","ce","bin",
    "dor","bern","mer","dalf","der","dam","torn","cu","ak","per"};
  private static final String[] MALE_FIRSTNAME_PART3 = {"min", "cus", "nor",
    "tar","lor","rin","ril","ward","lot","dink","les"};

  private static final String[] FEMALE_FIRSTNAME_PART1 = {"Ne", "El", "Ant",
    "Dia","Cla","Mar","Hol","Wen","Una","Eil","Kris","Ara","Ali","Ra","Lin","Jes",
    "Tam","Aes","Li","Eli","Gra","Kel","Nin","Brid","Re","Tran","Be","Ro","Ari",
    "Le","Sum"};
  private static final String[] FEMALE_FIRSTNAME_PART2 = {"ri", "no", "hess",
    "na","ra","ia","ly","dy","da","ah","ha","sys","dia","ce","ve","va","ie","si","my",
    "nia","ni","ya","ge","bec","ka","sa","el","eh","mer"};
  private static final String[] FEMALE_FIRSTNAME_PART3 = {"sella", "ra", "la",
    "rina","na","assa","dove","lynn","ca","ya","lina","tte"};

  private static final String[] CONSONANTS = {"b","c","d","f","g","h","j",
    "k","l","m","n","p","q","r","s","t","w","v","x","z"};
  private static final String[] VOWELS = {"a","e","i","o","u","y"};
  
  private static String getRandomSyllable(int i) {
    StringBuilder sb = new StringBuilder();
    if (i==1) {
      switch (DiceGenerator.getRandom(4)) {
      case 0: { 
             sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)].toUpperCase());
             sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
             sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
      break;}
      case 1: { 
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)].toUpperCase());
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
      break;}
      case 2: { 
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)].toUpperCase());
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
      break;}          
      case 3: { 
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)].toUpperCase());
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
      break;}
      case 4: { 
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)].toUpperCase());
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
      break;}
    }     
    }
    if (i==2) {
      switch (DiceGenerator.getRandom(3)) {
      case 0: { 
             sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
             sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
      break;}
      case 1: { 
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
      break;}
      case 2: { 
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
      break;}          
      case 3: { 
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
        sb.append(VOWELS[DiceGenerator.getRandom(VOWELS.length-1)]);
        sb.append(CONSONANTS[DiceGenerator.getRandom(CONSONANTS.length-1)]);
      break;}          
    }     
    }
    return sb.toString();
  }
  
  public static String getMaleName() {   
  StringBuilder sb = new StringBuilder();
  if (DiceGenerator.getRandom(3)==0) {
    sb.append(getRandomSyllable(1));
  } else {
    sb.append(MALE_FIRSTNAME_PART1[DiceGenerator.getRandom(MALE_FIRSTNAME_PART1.length-1)]);
  }
  if (DiceGenerator.getRandom(3)==0) {
    sb.append(getRandomSyllable(2));
  } else {
    sb.append(MALE_FIRSTNAME_PART2[DiceGenerator.getRandom(MALE_FIRSTNAME_PART2.length-1)]);
  }
  if (DiceGenerator.getRandom(3)==0) {
    if (DiceGenerator.getRandom(3)==0) {
      sb.append(getRandomSyllable(2));
    } else {
      sb.append(MALE_FIRSTNAME_PART3[DiceGenerator.getRandom(MALE_FIRSTNAME_PART3.length-1)]);
    }
  }
  sb.append(" ");
  sb.append(getSurname());
  return sb.toString();
  
  }
  public static String getFemaleName() {   
  StringBuilder sb = new StringBuilder();
  if (DiceGenerator.getRandom(3)==0) {
    sb.append(getRandomSyllable(1));
  } else {
    sb.append(FEMALE_FIRSTNAME_PART1[DiceGenerator.getRandom(FEMALE_FIRSTNAME_PART1.length-1)]);
  }
  if (DiceGenerator.getRandom(3)==0) {
    sb.append(getRandomSyllable(2));
  } else {
    sb.append(FEMALE_FIRSTNAME_PART2[DiceGenerator.getRandom(FEMALE_FIRSTNAME_PART2.length-1)]);
  }
  if (DiceGenerator.getRandom(3)==0) {
    if (DiceGenerator.getRandom(3)==0) {
      sb.append(getRandomSyllable(2));
    } else {
      sb.append(FEMALE_FIRSTNAME_PART3[DiceGenerator.getRandom(FEMALE_FIRSTNAME_PART3.length-1)]);
    }
  }
  sb.append(" ");
  sb.append(getSurname());
  return sb.toString();
  
  }
}
