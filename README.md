# JHeroes
JHeroes CRPG engine and Heroes of the Hawks Haven.
Code is licensed under GPL2, graphics is under CC-BY-SA, music is under CC-BY
and sound effects are either under CC-BY or public domain from OpenGameArt.org

## The project

This projects contains open sourced Heroes of the Hawks Haven and
editors to edit and create content with JHeroes CRPG engine.
JHeroes CRPG engine is not totally independent of current adventure.
To create totally new adventure easiest way is to fork the project
and editing couple of files.

There are three main programs in this project:

* src/org.jheroes.game.Game.java:
  This is the actual game program which is used when player is playing the game
* src/org.jheroes.mapeditor.Editor.java:
  This is the mapeditor which is used to create new maps for the game.
* src/org.jheroes.tileeditor.Editor.java:
  This is the TileEditor for editing the tileset
  
Each of the main programs can be run from Eclipse simple left clicking and
selecting "Run as Java Application".

Eclipse should automatically compile the project and after compiling editors and
game can left clicking correct java class and run it as Java Application.
Other option is to run build.xml with ant which will create JAR file
that run Heroes of the Hawks Haven. 

## Difference between OpenSource Heroes of the Hawks Haven and closed source

Biggest difference is the tileset used. OpenSource Heroes of the Hawks Haven uses LPC(Liberated Pixel Cup)
tileset under CC-BY-SA license. Closed source used tileset which did not allowed redistribution
expect for playing the game. OpenSource version uses JOrbis with GPL2 licenses when the ClosedSource
used it under LGPL license.

Story, quests and maps are currently almost identical in both versions. Couple of
texts I have fixed in Open source version but some of the map have graphical glitches
due the different tilesets. These glitches are actually in closedsource version but
they are not that visible due the different tileset.

## JHeroes CRPG Engine

Heroes of the Hawks Haven uses JHeroes CRPG Engine which is supports 1 to 4 player characters in
a party. One character is created in the beginning of the game. JHeroes game runs in turns even though
animation runs smoothly. There are not limitations how many maps one game can have. 

One map can be up to 256x256 tiles size. Single map can contain one, two or four sectors. 
One sector can be outside, one inside of the buildings. There can be any number of characters in 
map each of having their own talk. Maps have 4 different layers:
* Wall or floor, tile which is always present. Tile can be blockable.
* Object, Tile is on top of wall or floor. Also items are placed on top of object layer. Tile can be blockable.
* Decoration, tile is on top of object. If there is item on same place as decoration. This item is consider
as a hidden. Tile can be blockable.
* Top, Tile is on top of everything even characters and special effects are below this. Tile cannot be blockable.
This is used from example top part of the tree so characters can be behind the trees.

Each talk can contain 255 different states and each talk can be branched multiple times. Talks have 
quite powerful "scripting" tool which is quite easy to use since every action is picked from 
dropdown menu and multiple actions can be combined. JHeroes also has very simple scripting language 
for events. Each script can have optional conditions and if condition is met then commands are 
executed. There is a short scriptingHelp.txt in project folder. JHeroes has 256 shared story 
variables which value is between 0 and 255. This might sound as a big limitation but for example 
Heroes of the Hawks Haven used only 44 of these variables. These variables can be checked both talks 
and script conditions also modify in both talks and event scripts.

Player characters gain experience, levels and perks. There is a level cap at 20 and every even level
one perk is gained. One level one each character gains two perks. Engine uses single PNG file to hold up to
400 different 48x48 pixel faces. Tiles used in game are 32x32 pixel. Tilesets are created using separate
tile editor which allows giving more information about each tile. Tile has information about it's animation,
is it blocked from certain direction, does it create light or cast shadow.

### Files that require editing when forking the adventure

* src/org.jheroes.game.Game.java:
  Change the GAME_TITLE and GAME_VERSION attributes.
* src/org.jheroes.game.GameMaps.java:
  Contains list of game maps: GAMEMAPS this list must match list
  of used game maps.
* src/org.jheroes.game.GameTalk.java:
  Contains list of possible NPC which can join the party.
  List is being used to give correct amount of experience to new party members
  and set default roles for each new party member. See handleTalkLineActions() method
  with following switch case: case TalkAction.ACTION_JOIN_PARTY:
* Rewrite following classes:
  src/org.jheroes.game.storyscreen.EndStory.java:
    Contains the ending screens when player has finished the game 
  src/org.jheroes.game.storyscreen.LoseStory.java:
    Contains the ending screen when dead line has occured and player has lost the game
    without actually dying.
  src/org.jheroes.game.storyscreen.StartStory.java:
    Contains the very beginning screens before new character is created.
* src/org.jheroes.map.Party.java:
   Contains information about possible dead line quest see attribute: QUEST_NAME_WITH_DEAD_LINE
   and timeAddTurn() methods mentions that dead line is crowning and when to travel back to Hawks Haven.
   These both require editing if new adventure has a dead line.
   
## Files that JHeroes uses

All the data files which JHeroes engine uses are under src/res.

* characterlist.res: This file contains list of characters which can be
generated in game. For example characters created by encounter events.
This file is automatically loaded in MapEditor when character editor is started.
List of pregenerated characters are really from this list. List can be
saved again from character editor simply clicking the Save All button.

* images: This folder contains PNG images including ending and start screen and UI.

* licenses: This folder contains licenses required to be JHeroes and Heroes of The Hawks Haven.
Currently there are only GPL2 and graphcis-credits.txt. These files are shown when credits is clicked
in main menu.

* maps: This folder contains all the game maps. When new game is started these files are copied into
current folder and each map containing random items are also in same time actually randomized
into items.

* musics: This folder contains all the game music. Currently only supported format is OGG. Some files
have mp3 extension but they really are mp3. This is because in very early alphas I was using MP3
and maps directly point to music file names so changing the file names was not that easy to do.

* sounds: This folder contains all the sound effects for the game. Currently only supported format
is wave in PCM.

* talks: This folder contains all the talks NPC use when talking with player characters. Talk files
can be greated with Map Editor. Under character menu there is Talk Editor. Talks has greeting lines in a list.
Lines can have preconditions. When talk starts first greetingline is checked if preconditions are met
if they are that greeting line is set and particular state is selected. Then each state can
have multpile lines. Each line can have a condition. If condition is met then true branch is
selected and those actions are done and state changes. If condition is unmet then false branch is
selected and those actions are done and state changes. Actions in talk means like give/taking items
from player, gaining quests and so on. This is repeated as long as state contains no state changes.

* tilesets: This folder contains all the tileset used in game. Tileset can be created with TileEditor.
Tilesets contains information about next animation frame on certain tile, possible light effect, is it blocked
or not, default position on the maps and which part of possible series it belongs. Following tiles are mandatory:
characters.tls, effects.tls, items.tls. These are always loaded into memory. Then one tileset depending on map
can be changed.

## Map Editor keys

MapEditor has couple of modes. In map editing mode almost every thing is done with keyboard.
Here is the list of keys:

*  Arrow keys: Move the cursor/Character

* 1: Set current position as upperleft corner of event
* 2: Set current position as lowerright corner of event
* E: Edit/Add new event. 
* Q: Quick travel, Useful check that Door event really do work

* 3: Set current position as upperleft corner of copy tilearray 
* 4: Set current position as lowerright corner of copy tilearray
* P: Paste copy tilearray. Note copied data is not copied into buffer but directly taking copy from the map.

* PageUp: Go to next item 
* PageDown: Go to previous item
* I: Place item to map
* Backspace: Remove all the items from current map coordinates. Remove all the objects, decorations and top
from current coordinates. 

* Insert or Numpad +: Go to next tile
* Delete or Numpad -: Go to previous tile
* Home or Numpad *: Go to next tile series(For example walls that belong same series)
* End or Numpad /: Go to previous tile series(For example walls that belong same series)
* Space: Place tile into it's default layer/position  in tileset.
* D: Place tile into decoration layer/position.
* T: Place tile into top layer/position.
* O: Place tile into object layer/position.

* F: Copy all 4 layers into 2x2 buffer. Current position is upperleft corner.
* J: Paste all 4 layer from 2x2 buffer. Current position is upperleft corner.
* G: Get the tile from most top of layers in current position.
* H: Place 2x2 tiles into it's default layer/position in tileset. 
* B: Place 3x3 tiles into it's default layer/position in tileset.
 
* C: Fill whole sector with selected tile. Only if tile default position is floor or wall.

* S: Smooth visible part of the map. Smooth walls and floor which belong in same series.
* V: Recalculate lighting. This should be done at least before saving the map.
* N: Toggle between night and day lighting.

## Future plans with JHeroes

* First to have tutorial how to use JHeroes engine to create a new adventure.
* Add new feature to characters to modify sound effects for certain characters. Currently
character sounds are checked from the character name which is not very good way to do it.
* Add new feature to characters to give subtype to enemies. Currently subtype of undead is
checked based on character names which is not the good either. If characters would have
subtype then it would be possible to have weakness and resistance certain kind of attacks.
* Adding new places to visit in Heroes of the Hawks Haven and quests.

## Contact Information

If you want to send comment or any questions about the Heroes of Hawks Haven 
or JHeroes engine you can send email to following address.

`` tuomount_at_kapsi_dot_fi ``



   