package main.java.Chromatography;

//GUI Components

import java.awt.*;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;


public class ChromatographyMain extends JPanel {

    public ChromatographyMain() {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        super.add(mkHeadPanel(), c);
        c.gridy = 1;
        super.add(mkHeadPanel(), c);

    }

    private JPanel mkHeadPanel() {
        JPanel jp = new JPanel();
        TextField textField = new TextField("Input the information you want");
        Button button = new Button();
        jp.add(textField);
        jp.add(button);
        return jp;
    }

   /* Label label1 = new Label("Name:");
    TextField textField = new TextField ();
    HBox hb = new HBox();
    hb.getChildren().addAll(label1, textField);
    hb.setSpacing(10);*/
}
