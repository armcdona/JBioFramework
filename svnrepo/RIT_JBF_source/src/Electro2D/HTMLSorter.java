package Electro2D;/*
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
 * This class creates the ordered Vector of all of the proteins to be 
 * displayed in hte HTML file.
 */

import java.util.Vector;
import java.util.TreeSet;

public class HTMLSorter{
    
    private TreeSet proteinInfo;
    private int compBy;
    
    public HTMLSorter( int cb, Vector t, Vector p, Vector m, Vector f ){
	
	compBy = cb;
	proteinInfo = new TreeSet( new HTMLComparator( compBy ) );
	Vector tmp;
	
	for( int i = 0; i < t.size(); i++ ){
	    tmp = new Vector();
	    tmp.add((String)t.elementAt( i ) );
	    tmp.add((String)p.elementAt( i ) );
	    tmp.add((String)m.elementAt( i ) );
	    tmp.add((String)f.elementAt( i ) );
	    proteinInfo.add( tmp );
	}
    }

    public TreeSet getSorted(){
	return proteinInfo;
    }
}
