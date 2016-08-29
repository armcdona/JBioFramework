package main.java.Electro2D; /**
 * This simple class represents the disabled range fields
 *
 * @author Jill Zapoticznyj
 */

import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Image;


public class RangeImage extends Canvas {

    private Image pic;

    /**
     * constructor - Creates a Electro2D.RangeImage object
     *
     * @param i - the image represented by the object
     */
    public RangeImage(Image i) {
        //give the object a reference to the image to be drawn
        pic = i;
    }

    /**
     * This method draws the image to the screen
     */
    public void paint(Graphics g) {

        g.drawImage(pic, 0, 0, this);

    }

}
