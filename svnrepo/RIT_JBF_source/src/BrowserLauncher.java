import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
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
public class BrowserLauncher {

	/**
	 * Attempts to open the default web browser to the given URL.
     *
     * Code was written by Dem Pilafian and was retrieved from Bare Bones
     * Browser Launch for Java on April 16, 2012 by Amanda Fisher.
     * http://www.centerkey.com/java/browser/
     *
	 * @param url The URL to open
	 * @throws IOException If the web browser could not be located or does not run
	 */
    public static void openURL(String url) throws IOException {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles opening the Help and About files.
     * @param  file         The html file to open. Generally help.html or about.html
     * @throws IOException  Thrown if opening the file fails
     * @throws FileNotFoundException    Thrown if the file does not exist
     */
    public static void openHTMLFile(File file) throws IOException {
        // Make sure the file exists
        if(!file.exists()) {
            throw new FileNotFoundException("File does not exist: " + file.getName());
        }

        // Launch the HTML file
        Desktop.getDesktop().open(file);
    }
}
