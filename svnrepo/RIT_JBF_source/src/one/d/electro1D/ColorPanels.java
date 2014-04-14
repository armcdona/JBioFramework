package one.d.electro1D;
import java.awt.Component;
import javax.swing.JPanel;

/**
 * color panel class
 * initiate set of panels for  standard proteins, so that each protein band has its own unique color
 */
public class ColorPanels extends JPanel  {
	JPanel controlPanel;
	JPanel color1Panel;
	JPanel color2Panel;
	JPanel color3Panel;
	JPanel color4Panel;
	JPanel color5Panel;
	JPanel color6Panel;
	JPanel color7Panel;

    /**
     * creates standard proteins colors
     * @return gui component JPanel
     */
	public Component createStdColors(){
		
		JPanel panel = new JPanel();
		return panel;

	}

}
