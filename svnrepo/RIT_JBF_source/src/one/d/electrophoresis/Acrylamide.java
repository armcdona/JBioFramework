package one.d.electrophoresis;
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



public class Acrylamide
{

    Acrylamide(String s, double d)
    {
        percentGel = "0.0%";
        suppressor = 1;
        percentGel = s;
        concentration = d;
        setSuppressor(concentration);
    }

    public double getConc()
    {
        return concentration;
    }

    public void setSuppressor(double d)
    {
        if(d > 12D)
        {
            suppressor = 6;
            return;
        }
        if(d > 10D)
        {
            suppressor = 3;
            return;
        }
        if(d > 7.5D)
        {
            suppressor = 2;
            return;
        } else
        {
            suppressor = 1;
            return;
        }
    }

    private double concentration;
    public String percentGel;
    public int suppressor;
}