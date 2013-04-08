import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import chemaxon.marvin.beans.MSketchPane;
import chemaxon.marvin.common.UserSettings;
import chemaxon.marvin.sketch.SketchParameterConstants;

public class MarvinTab extends JPanel {

	private JTextArea textarea = new JTextArea(10,50);

	private MSketchPane createSketchPane() {
		MSketchPane pane = new MSketchPane(createUserSettings());
		pane.setMol("AspGluLysArg");
		pane.setPreferredSize(new Dimension(500, 500));
		return pane;
	}

	private UserSettings createUserSettings() {
		UserSettings settings = new UserSettings(this.getClass().getResourceAsStream("marvin.properties"));
		settings.setProperty(SketchParameterConstants.MENU_CUSTOMIZATION_FILE,
				System.getProperty("user.dir") + "/src/customization.xml");
		settings.setProperty(SketchParameterConstants.SHORTCUTS,
				System.getProperty("user.dir") + "/src/shortchuts.xml");
		settings.setProperty(SketchParameterConstants.TOOLBAR_TEMPLATES + "20",
				":specials:specialTemplates.mrv");
		return settings;
	}

	public JPanel createMainPanel() {
		JPanel topPanel = new JPanel();
		MSketchPane sketch = createSketchPane();
		JPanel sketchPanel = new JPanel();
		sketchPanel.add(sketch);
		
		topPanel.add(sketchPanel);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(topPanel);
		return mainPanel;
	}
}