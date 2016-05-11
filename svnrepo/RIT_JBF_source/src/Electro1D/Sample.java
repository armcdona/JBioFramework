package Electro1D;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 */
public class Sample {

    public void setXPosition(int i) {
        sampX = i;
    }

    public void setYPosition(int i) {
        sampY = i;
    }

    public void drawSwitch(boolean flag) {
        changeOn = flag;
    }

    public void empty() {
        fillSwitch = false;
        emptySwitch = true;
    }

    public void fill() {
        fillSwitch = true;
        emptySwitch = false;
    }

    public Sample() {
    }

    /**
     * Draw the sample onto the given graphics - run every time the class is drawn.
     *
     * @param g graphics reference
     */
    public void drawSample(Graphics g) {
        if (changeOn) {
            if (fillCounter > fillRatio) {
                if (fillSwitch) {
                    sampY--;
                    sampHeight++;
                }
                if (emptySwitch) {
                    sampY++;
                    sampHeight--;
                }
                fillCounter = 0;
            } else {
                fillCounter++;
            }
            if (emptySwitch && sampY > maxY) {
                changeOn = false;
                sampHeight = 0;
            }
        }
        if (sampHeight > 0) {
            g.setColor(Color.blue);
            g.fillRect(sampX, sampY, sampWidth, sampHeight);
        }
    }

    /**
     * Sets the width of the sample
     *
     * @param i the width of the sample
     */
    public void setWidth(int i) {
        sampWidth = i;
    }

    /**
     * Sets maximum y of the sample
     *
     * @param i the maximum y
     */
    public void setMaxY(int i) {
        maxY = i;
    }

    public void setRatio(int i) {
        fillRatio = i;
    }

    public void reset() {
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