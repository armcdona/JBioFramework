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

public class Pipette
{

    public void setSampleDepth(int i)
    {
        sampleDepth = i;
    }

    private boolean IncrPosition()
    {
        int i = y1;
        int j = 0;
        y1float = y1float + speed;
        y1 = (int)y1float;
        j = y1 - i;
        if(j > 0)
            if(!dropped)
            {
                pipetteCoordsY[1] += j;
                pipetteCoordsY[2] += j;
                pipetteCoordsYf[1] = pipetteCoordsY[1] - emptyTip;
                pipetteCoordsYf[2] = pipetteCoordsY[2] - emptyTip;
                if(pipetteCoordsY[1] > sampleDepth)
                {
                    pipetteCoordsYf[0] += j;
                    pipetteCoordsYf[3] += j;
                }
                if(pipetteCoordsY[1] > maxYPosition)
                {
                    dropped = true;
                    dropStart = pipetteCoordsY[1] - emptyTip;
                    dropLength = wellBottom - dropStart;
                }
            } else
            if(!emptied)
            {
                pipetteCoordsYf[0] += j;
                pipetteCoordsYf[3] += j;
                if(pipetteCoordsYf[0] > maxYPosition)
                    emptied = true;
            } else
            if(!retracted)
            {
                pipetteCoordsY[1] -= j;
                pipetteCoordsY[2] -= j;
                if(pipetteCoordsY[1] < 0)
                    retracted = true;
            }
        return !retracted;
    }

    public boolean fillWell(Graphics g)
    {
        if(!emptied)
        {
            g.setColor(sampleColor);
            g.fillPolygon(pipetteCoordsX, pipetteCoordsYf, pts);
        }
        g.setColor(Color.black);
        g.drawPolygon(pipetteCoordsX, pipetteCoordsY, pts);
        if(dropped)
        {
            sample.drawSwitch(true);
            if(!emptied)
            {
                int i = pipetteCoordsY[1] - emptyTip;
                int j = wellBottom - i;
                g.setColor(sampleColor);
                g.fillRect(pipetteCoordsX[1], i, 4, j);
            }
        }
        if(emptied)
            sample.drawSwitch(false);
        return IncrPosition();
    }

    public void setSample(Sample sample1)
    {
        sample = sample1;
    }

    public void setStartXPosition(int i)
    {
        int j = 0;
        ResetFlags();
        x1 = i;
        y1 = 0;
        y1float = 0.0F;
        pipetteCoordsX[0] = x1 - 4;
        pipetteCoordsX[1] = x1 - 2;
        pipetteCoordsX[2] = x1 + 2;
        pipetteCoordsX[3] = x1 + 4;
        j = 0;
        do
        {
            pipetteCoordsY[j] = 0;
            pipetteCoordsYf[j] = 0;
        } while(++j < 4);
        pts = pipetteCoordsX.length;
    }

    public void setMaxYPosition(int i)
    {
        maxYPosition = i - gap;
        wellBottom = i;
    }

    private void ResetFlags()
    {
        dropped = false;
        emptied = false;
        hasSample = false;
        retracted = false;
    }

    public Pipette()
    {
        sampleColor = Color.blue;
        gap = 6;
        speed = 2.0F;
        pipetteCoordsX = new int[4];
        pipetteCoordsY = new int[4];
        pipetteCoordsYf = new int[4];
        emptyTip = 4;
        sample = new Sample();
    }

    public Color sampleColor;
    public int x1;
    public int y1;
    public int gap;
    public int sampleDepth;
    public int initTipLength;
    public float y1float;
    public int maxYPosition;
    int wellBottom;
    public float speed;
    int pipetteCoordsX[];
    int pipetteCoordsY[];
    int pipetteCoordsYf[];
    int pts;
    int emptyTip;
    int dropStart;
    int dropLength;
    boolean dropped;
    boolean emptied;
    boolean hasSample;
    boolean retracted;
    Sample sample;
}