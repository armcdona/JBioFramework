/**
 * ColorFrame.java works as a key for the protein colors displayed in the
 * gel's final product.
 */

import java.util.HashMap;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

/**
 * a window with the various colors and what they represent
 */
public class ColorFrame {

    private static Frame colorFrame;
    private Panel colorPanel;

    public ColorFrame() {
        String[][] colorKey = E2DProtein.getColorGuide();

        colorFrame = new Frame("Color Key");
        colorFrame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        colorFrame.setVisible(false);
                    }

                    public void windowDeactivated(WindowEvent e) {
                        windowClosing(e);//Closes the color window if the window loses focus
                    }
                }
        );

        colorPanel = new Panel();
        colorPanel.setLayout(new GridLayout(0, 1));

        for (String[] labelStrings : colorKey) {
            Label colorLabel = new Label(labelStrings[0], Label.CENTER);
            Color backColor = new Color(Integer.parseInt(labelStrings[1]));
            colorLabel.setBackground(backColor);
            double Luminance = ((backColor.getRed() * 299) + (backColor.getGreen() * 587) + (backColor.getBlue() * 114)) / 1000; //Calculate the luminance of the background color and determine if the text should be white or black
            if (Luminance <= 125) {
                colorLabel.setForeground(Color.WHITE);
            } else {
                colorLabel.setForeground(Color.BLACK);
            }
            System.out.println(labelStrings[0] + ", " + labelStrings[1] + ", " + (backColor.getRed() / 255) + ", " + backColor.getGreen() / 255 + ", " + backColor.getBlue() / 255 + ", " + Double.toString(Luminance));
            colorPanel.add(colorLabel);
        }

        colorFrame.setBounds(0, 0, 400, (300 / 7) * colorKey.length);//each color label gets (300/7) pixels of length
        colorPanel.setBounds(0, 0, 400, (300 / 7) * colorKey.length);//each color label gets (300/7) pixels of length
        colorFrame.add(colorPanel);
    }

    /**
     * Show the color
     */
    public void showKey() {
        colorFrame.pack();
        colorFrame.setVisible(true);
    }
}