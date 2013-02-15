package one.d.electrophoresis;

// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 3/13/2009 10:21:41 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   D:\Dave\Java\Electrophoresis\Parameters.java

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Win-Air
 * 
 */

public class Parameters extends JPanel implements Constants {
	protected Electrophoresis parent;
	protected int std1Ref;
	protected int std2Ref;
	protected int std3Ref;
	protected int std4Ref;
	protected int std5Ref;
	protected int std6Ref;
	protected int std7Ref;
	protected Protein unknown1;
	protected Protein unknown2;
	protected Protein unknown3;
	protected Protein unknown4;
	protected Protein unknown5;
	protected Protein unknown6;
	protected Protein unknown7;
	protected Protein unknown8;
	protected Protein unknown9;
	protected Protein unknown10;
	protected Protein dye1;
	protected Protein dye2;
	protected Protein selectedSample;
	protected Protein stdArray[];
	protected Acrylamide selectedGel;

	// handel the events for speed selection + voltage .
	// private class RadioButtonsHandler implements ItemListener,
	// ChangeListener,
	// ActionListener {
	//
	// @Override
	// public void itemStateChanged(ItemEvent e) {
	// // TODO Auto-generated method stub
	//
	// e.getStateChange();
	// if (e.equals(fiftyV)) {
	// selectedSpeed = low;
	// setSpeed(selectedSpeed);
	// System.out.println("lol");
	//
	// }
	// if (e.equals(hundredV)) {
	// selectedSpeed = medium;
	// setSpeed(selectedSpeed);
	//
	// }
	// if (e.equals(oneFiftyV)) {
	// selectedSpeed = high;
	// setSpeed(selectedSpeed);
	//
	// }
	// if (e.equals(twoHundredV)) {
	// selectedSpeed = highX2;
	// setSpeed(selectedSpeed);
	//
	// }
	// if (e.getSource().equals(slow)) {
	//
	// setAnimationSpeed(slow);
	// }
	// if (e.getSource().equals(moderate)) {
	// setAnimationSpeed(moderate);
	// }
	// if (e.getSource().equals(fast)) {
	// setAnimationSpeed(fast);
	// System.out.println("llkjbjhv");
	// }
	//
	// }
	//
	// @Override
	// public void actionPerformed(ActionEvent e) {
	//
	// if (e.equals(fiftyV)) {
	// selectedSpeed = low;
	// setSpeed(selectedSpeed);
	// System.out.println("selected voltage is " + fiftyV
	// + " speed is " + selectedSpeed);
	//
	// }
	// if (e.equals(hundredV)) {
	// selectedSpeed = medium;
	// setSpeed(selectedSpeed);
	// System.out.println("selected voltage is " + fiftyV
	// + " speed is " + selectedSpeed);
	//
	// }
	// if (e.equals(oneFiftyV)) {
	// selectedSpeed = high;
	// setSpeed(selectedSpeed);
	//
	// System.out.println("selected voltage is " + fiftyV
	// + " speed is " + selectedSpeed);
	//
	// }
	// if (e.equals(twoHundredV)) {
	// selectedSpeed = highX2;
	// setSpeed(selectedSpeed);
	// System.out.println("selected voltage is " + fiftyV
	// + " speed is " + selectedSpeed);
	//
	// }
	// if (e.getSource().equals(slow)) {
	//
	// setAnimationSpeed(slow);
	// System.out.println("animtion sepeed ");
	// }
	// if (e.getSource().equals(moderate)) {
	// setAnimationSpeed(moderate);
	// }
	// if (e.getSource().equals(fast)) {
	// setAnimationSpeed(fast);
	// System.out.println("llkjbjhv");
	// }
	//
	// }
	//
	// @Override
	// public void stateChanged(ChangeEvent arg0) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// }// end class RadioButtonsHandler

	/*
	 * constructor
	 */
	Parameters(Electrophoresis electrophoresis) {

		low = 0.80000000000000004D;
		medium = 1.0D;
		high = 1.5D;
		highX2 = 2D;
		selectedSpeed = highX2;

		fiftyV = "50V";
		hundredV = "100V";
		oneFiftyV = "150V";
		twoHundredV = "200V";

		slow = "Slow";
		moderate = "Moderate";
		fast = "Fast";

		std2Ref = 1;
		std3Ref = 2;
		std4Ref = 3;
		std5Ref = 4;
		std6Ref = 5;
		std7Ref = 6;

		stdArray = new Protein[7];

		dyeColor = new Color(132, 50, 237);

		unknown1 = new Protein("Unknown #1", "Aconitase", "Acon", 0x14250,
				Color.black);
		unknown2 = new Protein("Unknown #2", "Conconavalin A", "Con A", 25556,
				Color.black);
		unknown3 = new Protein("Unknown #3", "Glucose Oxidase", "GO", 63058,
				Color.black);
		unknown4 = new Protein("Unknown #4", "Neuraminidase", "Neur", 43505,
				Color.black);
		unknown5 = new Protein("Unknown #5", "Phosphorylase b", "Phos b",
				0x172f9, Color.black);
		unknown6 = new Protein("Unknown #6", "Pyruvate Kinase", "Pyr Kin",
				56773, Color.black);
		unknown7 = new Protein("Unknown #7", "Ribonuclease A", "Ribo A", 13673,
				Color.black);
		unknown8 = new Protein("Unknown #8", "Chymotrypsinogen", "Chymo",
				23564, Color.black);
		unknown9 = new Protein("Unknown #9", "p-Hydroxybenzoate", "Hydrox",
				43939, Color.black);
		unknown10 = new Protein("Unknown #10", "Ribonuclease H", "Ribo H",
				16638, Color.black);
		dye1 = new Protein("Dye", "Dye", "Dye", 6000, dyeColor);
		dye2 = new Protein("Dye", "Dye", "Dye", 6000, dyeColor);

		gel1 = new Acrylamide("7.5%", 7.5D);
		gel2 = new Acrylamide("10%", 10D);
		gel3 = new Acrylamide("12%", 12D);
		gel4 = new Acrylamide("15%", 15D);

		String[] gelList = { gel1.percentGel, gel2.percentGel, gel3.percentGel,
				gel4.percentGel };

		acrylamide = new JComboBox<String>(gelList);
		// string array, holds unknown proteins names
		String[] samples = { unknown1.name, unknown3.name, unknown3.name,
				unknown4.name, unknown5.name, unknown6.name, unknown7.name,
				unknown8.name, unknown9.name };

		sample = new JComboBox<>(samples);

		// button groups
		voltage = new ButtonGroup();
		speed = new ButtonGroup();
		// panels

		headerPanel = new JPanel();
		headerSub1 = new JPanel(new GridLayout(1, 3, 1, 1));
		headerSub2 = new JPanel();
		labelPanel1 = new JPanel();
		labelPanel2 = new JPanel();
		dropPanel = new JPanel();
		selectionPanel1 = new JPanel();
		selectionPanel2 = new JPanel();
		standardPanel = new JPanel();
		colorPanel = new JPanel();
		voltagePanel = new JPanel();
		voltageSub1Panel = new JPanel();
		voltageSub2Panel = new JPanel();
		controlPanel = new JPanel();
		color1Panel = new JPanel();
		color2Panel = new JPanel();
		color3Panel = new JPanel();
		color4Panel = new JPanel();
		color5Panel = new JPanel();
		color6Panel = new JPanel();
		color7Panel = new JPanel();
		parent = electrophoresis;
		// helper methods
		setPanelsColors();

		setLayout(new GridLayout(5, 1, 2, 2));
		headerPanel.setLayout(new GridLayout(1, 1, 5, 5));
		Border border = BorderFactory
				.createTitledBorder("ELECTROPHORESIS PARAMETERS");
		headerPanel.setBorder(border);

		JRadioButton slowbutton = new JRadioButton(slow, false);
		JRadioButton modbutton = new JRadioButton(moderate, false);
		JRadioButton fastbutton = new JRadioButton(fast, true);

		JRadioButton fiftyV = new JRadioButton("50V");
		JRadioButton hundredV = new JRadioButton("100V");
		JRadioButton oneFiftyV = new JRadioButton("150V", true);
		JRadioButton twoHundredV = new JRadioButton("200V");

		slowbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				selectedSpeed = low;
				setSpeed(selectedSpeed);
				System.out.println("speed " + selectedSpeed);
			}
		});

		oneFiftyV.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				selectedSpeed = medium;
				setSpeed(selectedSpeed);
				System.out.println("speed " + selectedSpeed);

			}
		});
		twoHundredV.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				selectedSpeed = high;
				setSpeed(selectedSpeed);
				System.out.println("speed " + selectedSpeed);

			}
		});

		// Listen to RadioButtons
		// Register buttons Listeners
		// RadioButtonsHandler rHandler = new RadioButtonsHandler();
		//
		// slowbutton.addItemListener(rHandler);
		// slowbutton.addChangeListener(rHandler);
		// slowbutton.addActionListener(rHandler);
		//
		// modbutton.addItemListener(rHandler);
		// modbutton.addActionListener(rHandler);
		// modbutton.addChangeListener(rHandler);
		//
		// fastbutton.addItemListener(rHandler);
		// fastbutton.addActionListener(rHandler);
		// fastbutton.addItemListener(rHandler);
		//
		// fiftyV.addItemListener(rHandler);
		// fiftyV.addActionListener(rHandler);
		//
		// hundredV.addItemListener(rHandler);
		// hundredV.addActionListener(rHandler);
		//
		// oneFiftyV.addItemListener(rHandler);
		// oneFiftyV.addActionListener(rHandler);
		// twoHundredV.addItemListener(rHandler);
		// twoHundredV.addActionListener(rHandler);

		speed.add(fastbutton);
		speed.add(modbutton);
		speed.add(slowbutton);

		Border border1 = BorderFactory.createTitledBorder("Animation Speed");

		headerSub2.add(slowbutton);
		headerSub2.add(modbutton);
		headerSub2.add(fastbutton);
		headerSub2.setBorder(border1);
		// headerPanel.add(headerSub1);
		headerPanel.add(headerSub2);

		dropPanel.setLayout(new GridLayout(3, 1));
		labelPanel1.setLayout(new GridLayout(1, 2));
		labelPanel1.add(new JLabel("Unknown Proteins"));
		labelPanel1.add(new JLabel("% Acrylamide"));
		labelPanel2.add(new JLabel("Standards"));
		selectionPanel1.setLayout(new GridLayout(1, 2));
		selectionPanel2.setLayout(new GridLayout(1, 2));

		voltagePanel.setLayout(new GridLayout(2, 1));
		voltageSub1Panel.add(new Label("Voltage "));
		voltageSub2Panel.setLayout(new GridLayout(1, 4));
		voltage.add(fiftyV);
		voltage.add(hundredV);
		voltage.add(oneFiftyV);
		voltage.add(twoHundredV);

		voltageSub2Panel.add(fiftyV);
		voltageSub2Panel.add(hundredV);
		voltageSub2Panel.add(oneFiftyV);
		voltageSub2Panel.add(twoHundredV);

		voltagePanel.add(voltageSub1Panel);
		voltagePanel.add(voltageSub2Panel);

		standardPanel.setLayout(new GridLayout(7, 1));

		createCheckBoxes();

		color1Panel.setBackground(stdArray[std1Ref].color);
		color2Panel.setBackground(stdArray[std2Ref].color);
		color3Panel.setBackground(stdArray[std3Ref].color);
		color4Panel.setBackground(stdArray[std4Ref].color);
		color5Panel.setBackground(stdArray[std5Ref].color);
		color6Panel.setBackground(stdArray[std6Ref].color);
		color7Panel.setBackground(stdArray[std7Ref].color);
		colorPanel.setLayout(new GridLayout(7, 1, 1, 3));
		colorPanel.add(color1Panel);
		colorPanel.add(color2Panel);
		colorPanel.add(color3Panel);
		colorPanel.add(color4Panel);
		colorPanel.add(color5Panel);
		colorPanel.add(color6Panel);
		colorPanel.add(color7Panel);
		selectionPanel1.add(sample);
		selectionPanel1.add(acrylamide);
		selectionPanel2.add(standardPanel);
		selectionPanel2.add(colorPanel);
		dropPanel.add(labelPanel1);
		dropPanel.add(selectionPanel1);
		dropPanel.add(labelPanel2);
		controlPanel.setLayout(new GridLayout(2, 2, 8, 8));

		// create the
		JButton addStdButton = new JButton("Add Strandard");
		// add anonymous inner listener
		addStdButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				parent.startRun(stdArray, dye1, dye2, selectedSample);

			}
		});

		JButton addSampleButton = new JButton("Add Sample");

		addSampleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.addSample();

			}
		});
		JButton startButton = new JButton("Start Run");

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.stopRun();
				parent.setPlotData(stdArray, selectedSample, dye1);
			}
		});

		JButton stopButton = new JButton("Stop Run");

		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		controlPanel.add(addStdButton);
		controlPanel.add(addSampleButton);
		controlPanel.add(startButton);
		controlPanel.add(stopButton);

		add(headerPanel);
		add(dropPanel);
		add(selectionPanel2);
		add(voltagePanel);
		add(controlPanel);
		setSpeed(selectedSpeed);
		selectedSample = unknown1;
	}

	protected void setAnimationSpeed(String s) {
		parent.setAnimationSpeed(s);
	}

	protected void setAcrylamideEffect() {
		int i = 0;
		do
			if (selectedGel.getConc() > 12D) {
				if (stdArray[i].mw > 26000)
					stdArray[i].SetDecider(selectedGel.suppressor);
				else
					stdArray[i].ResetDecider();
			} else if (selectedGel.getConc() > 10D) {
				if (stdArray[i].mw > 29000)
					stdArray[i].SetDecider(selectedGel.suppressor);
				else
					stdArray[i].ResetDecider();
			} else if (selectedGel.getConc() > 7.5D) {
				if (stdArray[i].mw > 40000)
					stdArray[i].SetDecider(selectedGel.suppressor);
				else
					stdArray[i].ResetDecider();
			} else {
				stdArray[i].ResetDecider();
			}
		while (++i < 7);
		if (selectedGel.getConc() > 12D)
			if (selectedSample.mw > 26000) {
				selectedSample.SetDecider(selectedGel.suppressor);
				return;
			} else {
				selectedSample.ResetDecider();
				return;
			}
		if (selectedGel.getConc() > 10D)
			if (selectedSample.mw > 29000) {
				selectedSample.SetDecider(selectedGel.suppressor);
				return;
			} else {
				selectedSample.ResetDecider();
				return;
			}
		if (selectedGel.getConc() > 7.5D) {
			if (selectedSample.mw > 40000) {
				selectedSample.SetDecider(selectedGel.suppressor);
				return;
			} else {
				selectedSample.ResetDecider();
				return;
			}
		} else {
			selectedSample.ResetDecider();
			return;
		}
	}

	protected void setSpeed(double d) {
		dye1.speed = 0.94528800000000002D * d;
		dye2.speed = 0.94528800000000002D * d;
		stdArray[std1Ref].speed = 0.048245000000000003D * d;
		stdArray[std2Ref].speed = 0.35087200000000002D * d;
		stdArray[std3Ref].speed = 0.46814299999999998D * d;
		stdArray[std4Ref].speed = 0.49524400000000002D * d;
		stdArray[std5Ref].speed = 0.62672099999999997D * d;
		stdArray[std6Ref].speed = 0.68241399999999997D * d;
		stdArray[std7Ref].speed = 0.92105300000000001D * d;
		unknown1.speed = 0.15166299999999999D * d;
		unknown2.speed = 0.50653499999999996D * d;
		unknown3.speed = 0.233075D * d;
		unknown4.speed = 0.34545900000000002D * d;
		unknown5.speed = 0.10909099999999999D * d;
		unknown6.speed = 0.26486500000000002D * d;
		unknown7.speed = 0.69590399999999997D * d;
		unknown8.speed = 0.53110599999999997D * d;
		unknown9.speed = 0.34245300000000001D * d;
		unknown10.speed = 0.63648000000000005D * d;
	}

	private void setPanelsColors() {
		// TODO hi

		headerPanel.setBackground(Color.lightGray);
		selectionPanel1.setBackground(Color.lightGray);
		selectionPanel2.setBackground(Color.lightGray);
		standardPanel.setBackground(Color.lightGray);
		voltagePanel.setBackground(Color.lightGray);
		voltageSub1Panel.setBackground(Color.lightGray);
		voltageSub2Panel.setBackground(Color.lightGray);
		controlPanel.setBackground(Color.lightGray);
		labelPanel1.setBackground(Color.lightGray);
		labelPanel2.setBackground(Color.lightGray);
		dropPanel.setBackground(Color.lightGray);

		stdArray[std1Ref] = new Protein("Standard #1", "beta-Galactosidase",
				"b-gal", 0x1c58b, Color.blue);
		stdArray[std2Ref] = new Protein("Standard #2", "Ovalbumin", "oval",
				42734, Color.yellow);
		stdArray[std3Ref] = new Protein("Standard #3", "Carbonic Anhydrase",
				"carb anh", 29011, Color.gray);
		stdArray[std4Ref] = new Protein("Standard #4",
				"Triose Phosphate Isomerase", "TIM", 26527, Color.green);
		stdArray[std5Ref] = new Protein("Standard #5", "Myoglobin", "Myo",
				17183, Color.magenta);
		stdArray[std6Ref] = new Protein("Standard #6", "Lysozyme", "Lyso",
				14296, Color.white);
		stdArray[std7Ref] = new Protein("Standard #7", "Trypsin Inhibitor",
				"BPTI", 6500, Color.red);

	}

	private void createCheckBoxes() {
		final JCheckBox standard1 = new JCheckBox(stdArray[std1Ref].abbr);
		final JCheckBox standard2 = new JCheckBox(stdArray[std2Ref].abbr);
		final JCheckBox standard3 = new JCheckBox(stdArray[std3Ref].abbr);
		final JCheckBox standard4 = new JCheckBox(stdArray[std4Ref].abbr);
		final JCheckBox standard5 = new JCheckBox(stdArray[std5Ref].abbr);
		final JCheckBox standard6 = new JCheckBox(stdArray[std6Ref].abbr);
		final JCheckBox standard7 = new JCheckBox(stdArray[std7Ref].abbr);

		standardPanel.add(standard1);
		standardPanel.add(standard2);
		standardPanel.add(standard3);
		standardPanel.add(standard4);
		standardPanel.add(standard5);
		standardPanel.add(standard6);
		standardPanel.add(standard7);

		// StandardsListListener
		class StandardsListListener implements ItemListener {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				Object source = e.getItemSelectable();

				if (source == standard1) {
					stdArray[std1Ref].selected = standard1.isSelected();
					if (stdArray[std1Ref].selected)
						parent.displayProtein(stdArray[std1Ref]);

				}
				if (source == standard2) {
					stdArray[std2Ref].selected = standard2.isSelected();
					if (stdArray[std2Ref].selected)
						parent.displayProtein(stdArray[std2Ref]);

				}
				if (source == standard3) {
					stdArray[std3Ref].selected = standard3.isSelected();
					if (stdArray[std3Ref].selected)
						parent.displayProtein(stdArray[std3Ref]);

				}
				if (source == standard4) {
					stdArray[std4Ref].selected = standard4.isSelected();
					if (stdArray[std4Ref].selected)
						parent.displayProtein(stdArray[std4Ref]);

				}
				if (source == standard5) {
					stdArray[std5Ref].selected = standard5.isSelected();
					if (stdArray[std5Ref].selected)
						parent.displayProtein(stdArray[std5Ref]);

				}
				if (source == standard6) {
					stdArray[std6Ref].selected = standard6.isSelected();
					if (stdArray[std6Ref].selected)
						parent.displayProtein(stdArray[std6Ref]);

				}
				if (source == standard7) {
					stdArray[std7Ref].selected = standard7.isSelected();
					if (stdArray[std7Ref].selected)
						parent.displayProtein(stdArray[std7Ref]);

				}
				if (e.getStateChange() == ItemEvent.DESELECTED) {

				}

			}

		}// end class StandardsListListener
		StandardsListListener sll = new StandardsListListener();
		standard1.addItemListener(sll);
		standard2.addItemListener(sll);
		standard3.addItemListener(sll);
		standard4.addItemListener(sll);
		standard5.addItemListener(sll);
		standard6.addItemListener(sll);
		standard7.addItemListener(sll);

	}

	class ListHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if (unknown1.name.equals(e)) {
				selectedSample = unknown1;
				parent.displayProtein(unknown1);
			}
			if (unknown2.name.equals(e)) {
				selectedSample = unknown2;
				parent.displayProtein(unknown2);
			}
			if (unknown3.name.equals(e)) {
				selectedSample = unknown3;
				parent.displayProtein(unknown3);
			}
			if (unknown4.name.equals(e)) {
				selectedSample = unknown4;
				parent.displayProtein(unknown4);
			}
			if (unknown5.name.equals(e)) {
				selectedSample = unknown5;
				// lol
				parent.displayProtein(unknown5);

			}
			if (unknown6.name.equals(e)) {
				selectedSample = unknown6;
				parent.displayProtein(unknown6);
			}
			if (unknown7.name.equals(e)) {
				selectedSample = unknown7;
				parent.displayProtein(unknown7);
			}
			if (unknown8.name.equals(e)) {
				selectedSample = unknown8;
				parent.displayProtein(unknown8);

			}
			if (unknown9.name.equals(e)) {
				selectedSample = unknown9;
				parent.displayProtein(unknown9);
			}
			if (unknown10.name.equals(e)) {
				selectedSample = unknown10;
				parent.displayProtein(unknown10);
			}
			if (gel1.percentGel.equals(e)) {
				selectedGel = gel1;
				parent.setAcrylaminde(gel1);
				selectedGel.setSuppressor(gel1.getConc());
				setAcrylamideEffect();
			}
			if (gel2.percentGel.equals(e)) {
				selectedGel = gel2;
				parent.setAcrylaminde(gel2);
				selectedGel.setSuppressor(gel2.getConc());
				setAcrylamideEffect();
			}
			if (gel3.percentGel.equals(e)) {
				selectedGel = gel3;
				parent.setAcrylaminde(gel3);
				selectedGel.setSuppressor(gel3.getConc());
				setAcrylamideEffect();
			}
			if (gel4.percentGel.equals(e)) {
				selectedGel = gel4;
				parent.setAcrylaminde(gel4);
				selectedGel.setSuppressor(gel4.getConc());
				setAcrylamideEffect();
			}

		}

	}

	double low;
	double medium;
	double high;
	double highX2;
	double selectedSpeed;
	String fiftyV;
	String hundredV;
	String oneFiftyV;
	String twoHundredV;
	String slow;
	String moderate;
	String fast;
	Color dyeColor;
	Acrylamide gel1;
	Acrylamide gel2;
	Acrylamide gel3;
	Acrylamide gel4;
	JComboBox<String> acrylamide;
	JComboBox<String> sample;
	ButtonGroup voltage;
	ButtonGroup speed;
	JPanel headerPanel;
	JPanel headerSub1;
	JPanel headerSub2;

	JPanel labelPanel1;
	JPanel labelPanel2;
	JPanel dropPanel;
	JPanel selectionPanel1;
	JPanel selectionPanel2;
	JPanel standardPanel;
	JPanel colorPanel;
	JPanel voltagePanel;
	JPanel voltageSub1Panel;
	JPanel voltageSub2Panel;
	JPanel controlPanel;
	JPanel color1Panel;
	JPanel color2Panel;
	JPanel color3Panel;
	JPanel color4Panel;
	JPanel color5Panel;
	JPanel color6Panel;
	JPanel color7Panel;

	public void setDefaults() {
		parent.setAcrylaminde(gel1);
		selectedGel = gel1;
		setAcrylamideEffect();

	}

}