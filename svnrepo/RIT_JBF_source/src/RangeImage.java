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
 * This simple class represents the disabled range fields
 *
 * @author Jill Zapoticznyj
 */

import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Image;


public class RangeImage extends Canvas{

    private Image pic;

    /**
     * constructor - Creates a RangeImage object
     *
     * @param i - the image represented by the object
     */
    public RangeImage( Image i ){
	//give the object a reference to the image to be drawn
	pic = i;
    }

    /**
     * This method draws the image to the screen
     */
    public void paint( Graphics g ){
	
	g.drawImage( pic, 0, 0, this );

    }

}
