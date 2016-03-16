
/**
 * @author Amanda Fisher
 */

import javax.swing.*;

/**
 * The type Percent acrylamide swing version.
 */
public class PercentAcrylamide extends JComboBox {

    /**
     * Instantiates a new Percent acrylamide swing version.
     */
    public PercentAcrylamide() {

        super();
        addItem("5");
        addItem("7.5");
        addItem("10");
        addItem("15");
        addItem("18");
        addItem("4 - 15");
        addItem("4 - 20");
        addItem("8 - 16");
        addItem("10 - 20");
        setSelectedItem("15");

    }

}