package org.jheroes.tileeditor;

import java.io.File;

import javax.swing.filechooser.FileFilter;

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
 * Filefilter for tileset
 * 
 **/
public class TilesetFilter extends FileFilter {

  @Override
  public boolean accept(File f) {
    if (f.isDirectory()) {
      return true;
    }
    String extension = FileUtil.getExtension(f);
    if ((extension.equals("tls")) || (extension.equals("TLS"))) {
        return true;
    }
 

    return false;
  }

  @Override
  public String getDescription() {    
    return "Tileset";
  }

}
