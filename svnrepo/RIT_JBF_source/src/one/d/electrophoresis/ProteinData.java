package one.d.electrophoresis;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProteinData extends JPanel {
	Electro1DMain parent;
	String mw;
	JTextField name;
	JTextField fullName;
	JTextField abbr;
	JTextField molwt;
	JTextField logMolWt;
	JPanel titlePanel;
	JPanel namePanel;
	JPanel fullNamePanel;
	JPanel abbrPanel;
	JPanel molWtPanel;
	JPanel logMolWtPanel;

	/*
	 * setting the protein info to be deployed in the simulation panel note1:
	 * method name is m
	 */

	public void displayData(Protein protein) {
		name.setText(protein.name);
		fullName.setText(protein.fullName);
		abbr.setText(protein.abbr);
		mw = String.valueOf(protein.mw);
		molwt.setText(mw);
		double d = Math.log(protein.mw) / Math.log(10D);
		logMolWt.setText(String.valueOf(d));
	}

	ProteinData(Electro1DMain electro1DMain) {
		mw = "0";
		titlePanel = new JPanel();
		namePanel = new JPanel();
		fullNamePanel = new JPanel();
		abbrPanel = new JPanel();
		molWtPanel = new JPanel();
		logMolWtPanel = new JPanel();
		parent = electro1DMain;
		setLayout(new GridLayout(6, 1));
		titlePanel.setBackground(Color.lightGray);
		namePanel.setBackground(Color.lightGray);
		fullNamePanel.setBackground(Color.lightGray);
		abbrPanel.setBackground(Color.lightGray);
		molWtPanel.setBackground(Color.lightGray);
		logMolWtPanel.setBackground(Color.lightGray);
		titlePanel.add(new JLabel("PROTEIN DATA"));
		namePanel.add(new JLabel("Identifier"));
		name = new JTextField(8);
		namePanel.add(name);
		fullNamePanel.add(new JLabel("Protein Name"));
		fullName = new JTextField(15);
		fullNamePanel.add(fullName);
		abbrPanel.add(new JLabel("Abbreviation"));
		abbr = new JTextField(5);
		abbrPanel.add(abbr);
		molWtPanel.add(new JLabel("Molecular Wt"));
		molwt = new JTextField(5);
		molWtPanel.add(molwt);
		logMolWtPanel.add(new JLabel("Log Mol Wt"));
		logMolWt = new JTextField(5);
		logMolWtPanel.add(logMolWt);
		name.setEditable(false);
		fullName.setEditable(false);
		abbr.setEditable(false);
		molwt.setEditable(false);
		logMolWt.setEditable(false);
		add(titlePanel);
		add(namePanel);
		add(fullNamePanel);
		add(abbrPanel);
		add(molWtPanel);
		add(logMolWtPanel);
	}

}