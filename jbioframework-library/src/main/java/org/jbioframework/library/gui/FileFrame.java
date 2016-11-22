package org.jbioframework.library.gui;
/**
 * Electro2D.FileFrame.java
 * <p>
 * This class encapsulates all the functionality required to pop up a frame
 * and load protein data from a file.
 *
 * @author John Manning
 * @author Jill Zapoticznyj
 * @author Adam Bazinet
 */

import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.io.GenbankReader;
import org.biojava.nbio.core.sequence.io.GenbankReaderHelper;
import org.biojava.nbio.core.sequence.template.Sequence;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.io.PDBFileReader;
import org.jbioframework.library.protein.Protein;
import org.jbioframework.library.utilities.MessageFrame;
import org.biojava.nbio.structure.StructureIO;
import org.jbioframework.library.utilities.ImageFilter;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.List;

public class FileFrame extends JFrame implements ActionListener {

    /** variables for the file reading pop-up frame **/
    private WindowListener ffwl;          //listen for window closing, etc.
    private int fileNum;
    private final String directoryString = "." + File.separator + ".." + File.separator + "data";
    private JTextArea instructions;
    private JLabel select;
    private JComboBox choice;
    private JButton button;
    private JPanel center;
    private JPanel south;
    private String[] sa;
    private Vector<Protein> proteins;
    private String lastFileName;

    public FileFrame(int i) {

        fileNum = i;

        setTitle("Load Protein Data File");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        instructions = new JTextArea();
        instructions.append(
                "Instructions: Select the name of the file that contains your protein sequence data.\n" +
                        "Please note: Some files may take longer to load.");
        instructions.setEditable(false);
        instructions.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

        select = new JLabel("Select Filename: ", JLabel.RIGHT);

        String[] files = {"file1", "file2", "file3", "file4"};
        choice = new JComboBox();
        for (String f : files) choice.addItem(f);

        button = new JButton("Load");
        button.addActionListener(this);

        // layout

        getContentPane().add(instructions, BorderLayout.NORTH);

        center = new JPanel();
        center.add(select);
        center.add(choice);

        getContentPane().add(center, BorderLayout.CENTER);

        south = new JPanel();
        south.add(button);

        getContentPane().add(south, BorderLayout.SOUTH);

        pack();
        refreshFileList();
    }

    public void refreshFileList() {

        choice.removeAllItems();
        File fl = new File("data");

        if (!fl.exists()) {
            System.err.println("Warning: No data files found!");
            fl.mkdir();
        }

        sa = fl.list(new ImageFilter());

        for (int file = 0; file < sa.length; file++) {
            choice.addItem(sa[file]);
        }
    }

    public void actionPerformed(ActionEvent e) {
        // change the cursor image
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        //try to read the contents of the file
        loadFile();

        // set the cursor image back to normal
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        //close the frame
        dispose();

        refreshFileList();
    }

    public Vector<Protein> getProteins() {
        return proteins;
    }

    public String getLastFileName() {
        return lastFileName;
    }

    public void loadFile() {

        proteins = new Vector<Protein>();

        //first, get filename from textbox
        String fileName = sa[choice.getSelectedIndex()];

        if (fileName == null || fileName.equals("")) {
            MessageFrame error = new MessageFrame();
            error.setMessage("Please enter a file name.");
            error.setVisible(true);
        } else {

            String extension = FilenameUtils.getExtension(fileName);
            String filePath = FilenameUtils.getFullPath("./"+fileName);

            // if the file's extention is not one of the supported types
            // display an error message
            if (!extension.equalsIgnoreCase("faa") &&
                    !extension.equalsIgnoreCase("fasta") &&
                    !extension.equalsIgnoreCase("pdb") &&
                    !extension.equalsIgnoreCase("gbk")) {

                MessageFrame error = new MessageFrame();
                error.setMessage("File extension is not valid.");
                error.setVisible(true);

            } else {

                //call the proper method to read the file depending on its type
                if (extension.equalsIgnoreCase("faa") || extension.equalsIgnoreCase("fasta")) {
                    //GenomeFileParser.fastaParse(filename, electro2D, "", fileNum);
                    try {
                        LinkedHashMap<String, ProteinSequence> proteinData = FastaReaderHelper.readFastaProteinSequence(new File(filePath));
                        for (int i=0;i<proteinData.size(); i++) {
                            proteins.add(new Protein(proteinData.get(i).getSequenceAsString()));
                        }
                        lastFileName = fileName;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (extension.equalsIgnoreCase("pdb")) {
                    //GenomeFileParser.pdbParse(filename, electro2D, "", fileNum);
                    PDBFileReader reader = new PDBFileReader();
                    try {
                        List<Chain> chainList = reader.getStructure(filePath).getChains();
                        String sequence = "";
                        for (int i=0; i<chainList.size(); i++) {
                            sequence+=chainList.get(i).getSeqResSequence();
                        }
                        proteins.add(new Protein(new ProteinSequence(sequence)));
                        lastFileName = fileName;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (extension.equalsIgnoreCase("gbk")) {
                    try {
                        GenbankReaderHelper reader = new GenbankReaderHelper();
                        reader.readGenbankProteinSequence(new File(filePath));
                        lastFileName = fileName;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //GenomeFileParser.gbkParse(filename, electro2D, "", fileNum);
                }
                this.setVisible(false);
            }
        }
    }
}

