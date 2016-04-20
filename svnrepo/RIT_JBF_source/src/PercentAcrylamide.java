
/**
 * @author Amanda Fisher
 */

import javax.swing.*;

/**
 * Dropdown box for selecting the percent acrylamide for the electro2D simulation
 */
public class PercentAcrylamide extends JComboBox {

    /**
     * Adds the various percentages of Acrylamide that can be used in the simulation
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