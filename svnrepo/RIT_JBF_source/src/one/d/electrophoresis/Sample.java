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

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 */
public class Sample
{

    public void setXPosition(int i)
    {
        sampX = i;
    }

    public void setYPosition(int i)
    {
        sampY = i;
    }

    public void drawSwitch(boolean flag)
    {
        changeOn = flag;
    }

    public void empty()
    {
        fillSwitch = false;
        emptySwitch = true;
    }

    public void fill()
    {
        fillSwitch = true;
        emptySwitch = false;
    }

    public Sample()
    {
    }

    public void drawSample(Graphics g)
    {
        if(changeOn)
        {
            if(fillCounter > fillRatio)
            {
                if(fillSwitch)
                {
                    sampY--;
                    sampHeight++;
                }
                if(emptySwitch)
                {
                    sampY++;
                    sampHeight--;
                }
                fillCounter = 0;
            } else
            {
                fillCounter++;
            }
            if(emptySwitch && sampY > maxY)
            {
                changeOn = false;
                sampHeight = 0;
            }
        }
        if(sampHeight > 0)
        {
            g.setColor(Color.blue);
            g.fillRect(sampX, sampY, sampWidth, sampHeight);
        }
    }

    public void setWidth(int i)
    {
        sampWidth = i;
    }

    public void setMaxY(int i)
    {
        maxY = i;
    }

    public void setRatio(int i)
    {
        fillRatio = i;
    }

    public void reset()
    {
        changeOn = false;
        fillSwitch = false;
        emptySwitch = false;
        sampX = 0;
        sampY = 0;
        sampWidth = 0;
        sampHeight = 0;
        fillCounter = 0;
        fillRatio = 0;
        maxY = 0;
    }

    int sampX;
    int sampY;
    int sampWidth;
    int sampHeight;
    int fillCounter;
    int fillRatio;
    int maxY;
    boolean changeOn;
    boolean fillSwitch;
    boolean emptySwitch;
}