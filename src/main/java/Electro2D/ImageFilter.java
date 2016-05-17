package main.java.Electro2D;/*
 * Copyright (C) 2013 Rochester Institute of Technology
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 */

import java.io.File;
import java.io.FilenameFilter;

/* Electro2D.ImageFilter.java is a 1.4 example used by FileChooserDemo2.java. */
public class ImageFilter implements FilenameFilter {

    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f, String s) {
        String extension = s.substring(s.lastIndexOf(".") + 1);
        if (extension != null) {
            if (extension.equals("e2d") ||
                    extension.equals("gbk") ||
                    extension.equals("faa") ||
                    extension.equals("pdb") ||
                    extension.equals("fasta")) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Just Images";
    }
}
