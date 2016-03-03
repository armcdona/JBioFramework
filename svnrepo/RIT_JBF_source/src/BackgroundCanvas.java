import javax.swing.*;

/**
 * The type Background canvas.
 */
public class BackgroundCanvas {

    private Electro2D electro2D;
    private JPanel background;

    /**
     * Instantiates a new Background canvas.
     *
     * @param e the e
     */
    public BackgroundCanvas( Electro2D e ){
	electro2D = e;
	background = new JPanel();
        background.add(electro2D);
    }

}
