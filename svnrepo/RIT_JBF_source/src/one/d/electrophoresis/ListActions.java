package one.d.electrophoresis;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ListActions {
  public static void main(String args[]) {
    Runnable runner = new Runnable() {
      public void run() {
        JFrame frame = new JFrame("TextAction List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String components[] = {
          "JTextField", "JFormattedTextField", "JPasswordField",
          "JTextArea", "JTextPane", "JEditorPane"};

        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        ActionListener actionListener = new ActionListener() {
          public void actionPerformed(ActionEvent actionEvent) {
            // Determine which component selected
            String command = actionEvent.getActionCommand();
            JTextComponent component = null;
            if (command.equals("JTextField")) {
              component = new JTextField();
            } else if (command.equals("JFormattedTextField")) {
              component = new JFormattedTextField();
            } else if (command.equals("JPasswordField")) {
              component = new JPasswordField();
            } else if (command.equals("JTextArea")) {
              component = new JTextArea();
            } else if (command.equals("JTextPane")) {
              component = new JTextPane();
            } else {
              component = new JEditorPane();
            }

            // Process action list
            Action actions[] = component.getActions();
            // Define comparator to sort actions
            Comparator<Action> comparator = new Comparator<Action>() {
              public int compare(Action a1, Action a2) {
                String firstName = (String)a1.getValue(Action.NAME);
                String secondName = (String)a2.getValue(Action.NAME);
                return firstName.compareTo(secondName);
              }
            };
            Arrays.sort(actions, comparator);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            int count = actions.length;
            pw.println("Count: " + count);
            for (int i=0; i<count; i++) {
              pw.print (actions[i].getValue(Action.NAME));
              pw.print (" : ");
              pw.println(actions[i].getClass().getName());
            }
            pw.close();
            textArea.setText(sw.toString());
            textArea.setCaretPosition(0);
          }
        };

        final Container componentsContainer =
          RadioButtonUtils.createRadioButtonGrouping(components,
            "Pick to List Actions", actionListener);

        frame.add(componentsContainer, BorderLayout.WEST);
        frame.setSize(400, 250);
        frame.setVisible(true);
      }
    };
    EventQueue.invokeLater(runner);
  }
  
  public static class RadioButtonUtils {
	  private RadioButtonUtils() {
	    // Private constructor so you can't create instances
	  }
	public static Enumeration<String> getSelectedElements(Container container) {
	  Vector<String> selections = new Vector<String>();
	  Component components[] = container.getComponents();
	  for (int i=0, n=components.length; i<n; i++) {
	    if (components[i] instanceof AbstractButton) {
	      AbstractButton button = (AbstractButton)components[i];
	      if (button.isSelected()) {
	        selections.addElement(button.getText());
	      }
	    }
	  }
	  return selections.elements();
	}

	public static Container createRadioButtonGrouping (String elements[]) {
	  return createRadioButtonGrouping(elements, null, null, null, null);
	}

	public static Container createRadioButtonGrouping (String elements[],
	    String title) {
	  return createRadioButtonGrouping(elements, title, null, null, null);
	}

	public static Container createRadioButtonGrouping(String elements[],
	    String title, ItemListener itemListener) {
	  return createRadioButtonGrouping(elements, title, null, itemListener, null);
	}

	public static Container createRadioButtonGrouping(String elements[],
	    String title, ActionListener actionListener) {
	  return createRadioButtonGrouping(elements, title, actionListener, null,
	    null);
	}

	public static Container createRadioButtonGrouping(String elements[],
	    String title, ActionListener actionListener, ItemListener itemListener) {
	  return createRadioButtonGrouping(elements, title, actionListener,
	    itemListener, null);
	}

	public static Container createRadioButtonGrouping(String elements[],
	    String title, ActionListener actionListener, ItemListener itemListener,
	    ChangeListener changeListener) {
	  JPanel panel = new JPanel(new GridLayout(0, 1));
	  // If title set, create titled border
	    if (title != null) {
	      Border border = BorderFactory.createTitledBorder(title);
	      panel.setBorder(border);
	    }
	    // Create group
	    ButtonGroup group = new ButtonGroup();
	    JRadioButton aRadioButton;
	    // For each String passed in:
	    // Create button, add to panel, and add to group
	    for (int i=0, n=elements.length; i<n; i++) {
	      aRadioButton = new JRadioButton (elements[i]);
	      panel.add(aRadioButton);
	      group.add(aRadioButton);
	      if (actionListener != null) {
	        aRadioButton.addActionListener(actionListener);
	      }
	      if (itemListener != null) {
	        aRadioButton.addItemListener(itemListener);
	      }
	      if (changeListener != null) {
	        aRadioButton.addChangeListener(changeListener);
	      }
	    }
	    return panel;
	  }
	}

}