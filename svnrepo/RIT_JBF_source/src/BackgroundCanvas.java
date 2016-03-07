import javax.swing.*;

/**
 * The Background canvas of the main window.
 */
public class BackgroundCanvas {

    private Electro2D electro2D;
    private JPanel background;

    /**
     * Instantiates the background canvas, and adds the
     *
     * @param e the Electro2D tab
     */
    public BackgroundCanvas( Electro2D e ){
	electro2D = e;
	background = new JPanel();
        background.add(electro2D);
    }

}
