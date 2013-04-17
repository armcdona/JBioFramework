/**
 * 
 */
package one.d.electrophoresis;

import java.awt.Color;
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
 * @author Bader Alharbi Constants interface initiate many final attributes, by
 *         Parameters.java this will make locating these fixed values easier 
 * 
 */
public interface Constants {

	public final double low = 80000000000000004D;
	public final double medium = 1.0D;
	public final double high = 1.5D;
	public final double highX2 = 2D;
	public final String fiftyV = "50V";
	public final String hundredV = "100V";
	public final String oneFiftyV = "150V";
	public final String twoHundred = "200";
	public final String slow = "Slow";
	public final String moderate = "Moderate ";
	public final String fast = "Fast";

	public Color dyeColor = new Color(132, 50, 237);
	public Protein dye1 = new Protein("Dye", "Dye", "Dye", 6000, dyeColor);
	public Protein dye2 = new Protein("Dye", "Dye", "Dye", 6000, dyeColor);

	public Acrylamide gel1 = new Acrylamide("7.5%", 7.5D);
	public Acrylamide gel2 = new Acrylamide("10%", 10D);
	public Acrylamide gel3 = new Acrylamide("12%", 12D);
	public Acrylamide gel4 = new Acrylamide("15%", 15);

	public int std2Ref = 1;
	public int std3Ref = 2;
	public int std4Ref = 3;
	public int std5Ref = 4;
	public int std6Ref = 5;
	public int std7Ref = 6;

}
