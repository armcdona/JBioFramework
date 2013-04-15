package one.d.electrophoresis;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BrowserLauncher {

	/**
	 * Attempts to open the default web browser to the given URL.
     *
     * Code was written by Dem Pilafian and was retrieved from Bare Bones
     * Browser Launch for Java on April 16, 2012 by Amanda Fisher.
     * http://www.centerkey.com/java/browser/
     *
	 * @param url The URL to open
	 * @throws java.io.IOException If the web browser could not be located or does not run
	 */
    public static void openURL(String url) throws IOException {
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles opening the Help and About files.
     * @param  file         The html file to open. Generally help.html or about.html
     * @throws java.io.IOException  Thrown if opening the file fails
     * @throws java.io.FileNotFoundException    Thrown if the file does not exist
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
