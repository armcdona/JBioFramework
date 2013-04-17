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
/**
 * ColorFrame.java works as a key for the protein colors displayed in the
 * gel's final product.
 */

import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Label;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ColorFrame {
    
    private static Frame colorFrame;
    private Panel colorPanel;
    private ArrayList labels;
    private HashMap colorkey;
    
    public ColorFrame(){
	colorkey = E2DProtein.getColorGuide();
	
	colorFrame = new Frame( "Color Key" );
	colorFrame.addWindowListener( new WindowAdapter(){
		public void windowClosing( WindowEvent e ){
		    colorFrame.hide();
		}
	    }
				      );
	labels = new ArrayList();
	
	colorPanel = new Panel();
	colorPanel.setLayout( new GridLayout( 0,1 ) );
	
	labels.add( new Label( "dna in Title", Label.CENTER ) );
	((Label)labels.get( 0 )).setBackground( (Color)colorkey.get( "dna in Title" ) );
	colorPanel.add( (Label)labels.get(0) );
	
	labels.add( new Label( "ribosomal in Title", Label.CENTER ) );
	((Label)labels.get( labels.size() - 1 )).setBackground( (Color)colorkey.get( "ribosomal in Title" ));
	colorPanel.add( (Label)labels.get( labels.size() - 1 ) );
	
	labels.add( new Label( "Enzyme EC in Function", Label.CENTER ) );
	((Label)labels.get( labels.size() - 1 )).setBackground( (Color)colorkey.get( "Enzyme EC in Function" ) );
	colorPanel.add( (Label)labels.get( labels.size() - 1 ) );

	labels.add( new Label( "hypothetical protein", Label.CENTER ) );
	((Label)labels.get(labels.size() - 1)).setBackground( (Color)colorkey.get( "hypothetical protein" ) );
	colorPanel.add( (Label)labels.get(labels.size() - 1) );
	
	labels.add( new Label( "transport protein in Function", 
			       Label.CENTER ) );
	((Label)labels.get(labels.size() - 1)).setBackground( (Color)colorkey.get( "transport protein in Function" ) );
	((Label)labels.get(labels.size() - 1)).setForeground( Color.WHITE );
	colorPanel.add( (Label)labels.get(labels.size() - 1) );

	labels.add( new Label( "receptor in Function", Label.CENTER ) );
	((Label)labels.get(labels.size() - 1)).setBackground( (Color)colorkey.get( "receptor in Function" ) );
	colorPanel.add( (Label)labels.get(labels.size() - 1) );

	labels.add( new Label( "transduction in Function", Label.CENTER ) );
	((Label)labels.get(labels.size() - 1)).setBackground( (Color)colorkey.get( "transduction in Function" ) );
	colorPanel.add( (Label)labels.get(labels.size() - 1) );

	colorFrame.setBounds( 0, 0, 400, 300 );
	colorPanel.setBounds( 0, 0, 400, 300 );
	colorFrame.add( colorPanel );
    }

    public void showKey(){
	colorFrame.pack();
	colorFrame.show();
    }
}