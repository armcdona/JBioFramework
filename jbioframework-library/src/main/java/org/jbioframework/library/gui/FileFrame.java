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
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.features.FeatureInterface;
import org.biojava.nbio.core.sequence.io.GenbankReader;
import org.biojava.nbio.core.sequence.io.GenbankReaderHelper;
import org.biojava.nbio.core.sequence.template.AbstractSequence;
import org.biojava.nbio.core.sequence.template.Sequence;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.io.PDBFileReader;
import org.jbioframework.library.protein.Protein;
import org.jbioframework.library.utilities.DatabaseUtilities;
import org.jbioframework.library.utilities.MessageFrame;
import org.biojava.nbio.structure.StructureIO;
import org.jbioframework.library.utilities.ImageFilter;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.apache.commons.io.FilenameUtils;
import org.jbioframework.library.utilities.XMLUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
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
    private ArrayList<Protein> proteins;
    private String lastFileName;

    final Logger logger = org.slf4j.LoggerFactory.getLogger(FileFrame.class);

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

    private ArrayList<String> getAcceptedExtentions() {
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add(".fasta");
        extensions.add(".faa");
        extensions.add(".pdb");
        extensions.add(".gbk");
        extensions.add(".gb");
        return extensions;
    }

    private boolean isAcceptedExtension(String filename) {
        ArrayList<String> extensions = getAcceptedExtentions();
        for (String acceptedExtension : extensions) {
            if (acceptedExtension.toLowerCase().contains(filename.toLowerCase())
                    || filename.toLowerCase().contains(acceptedExtension.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void refreshFileList() {

        choice.removeAllItems();
        File fl = new File("data");

        if (!fl.exists()) {
            System.err.println("Warning: No data files found at "+fl.getAbsolutePath()+"!");
            fl.mkdir();
        }

        File[] files = fl.listFiles();
        ArrayList<File> acceptedFiles = new ArrayList<>();
        for (int i=0;i<files.length-1;i++) {
            if (files[i].isFile()) {
                if (isAcceptedExtension(files[i].getName())) {
                    acceptedFiles.add(files[i]);
                }
            }
        }
        sa = new String[acceptedFiles.size()];
        int currentFileName = 0;
        for (File file : acceptedFiles) {
            if (file.isFile()) {
                sa[currentFileName] = file.getName();
                currentFileName++;
            }
        }

        for (int file = 0; file < (sa != null ? sa.length : 0); file++) {
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

    public ArrayList<Protein> getProteins() {
        return proteins;
    }

    public String getLastFileName() {
        return lastFileName;
    }

    private void loadFile() {

        proteins = new ArrayList<>();

        //first, get filename from textbox
        String fileName = sa[choice.getSelectedIndex()];

        if (fileName == null || fileName.equals("")) {
            MessageFrame error = new MessageFrame();
            error.setMessage("Please enter a file name.");
            error.setVisible(true);
        } else {

            String extension = FilenameUtils.getExtension(fileName);
            String filePath = "./data/" + fileName; //FilenameUtils.getFullPath("./"+fileName);
            System.out.println("filename: "+fileName+", filepath: "+filePath+", extension:"+ extension+"\n");

            // if the file's extention is not one of the supported types
            // display an error message
            if (!extension.equalsIgnoreCase("faa") &&
                    !extension.equalsIgnoreCase("fasta") &&
                    !extension.equalsIgnoreCase("pdb") &&
                    !extension.equalsIgnoreCase("gbk") &&
                    !extension.equalsIgnoreCase("gb")) {

                MessageFrame error = new MessageFrame();
                error.setMessage("File extension is not valid.");
                error.setVisible(true);

            } else {

                long startTimeIndex = System.currentTimeMillis();
                long endTimeIndex = 0;

                String databaseName = fileName.substring(0,fileName.indexOf("."));
                if (XMLUtilities.doesFileExist(databaseName)) {
                    proteins = XMLUtilities.loadFile(databaseName);
                    endTimeIndex = System.currentTimeMillis();
                    logger.info("Time to load XML in seconds: "+((endTimeIndex-startTimeIndex)/1000.0)+" at a speed of "+((proteins.size())/((endTimeIndex-startTimeIndex)/1000.0))+" entries/sec.");
                } else if (extension.equalsIgnoreCase("faa") || extension.equalsIgnoreCase("fasta")) {
                    try {
                        LinkedHashMap<String, ProteinSequence> proteinData = FastaReaderHelper.readFastaProteinSequence(new File(filePath));
                        if (proteinData != null) {
                            for (Map.Entry<String, ProteinSequence> map : proteinData.entrySet()) {
                                proteins.add(new Protein(map.getValue().getSequenceAsString()));
                            }
                            lastFileName = fileName;
                            XMLUtilities.saveFile(fileName,proteins);
                            endTimeIndex = System.currentTimeMillis();
                            logger.info("Time to save XML in seconds: "+((endTimeIndex-startTimeIndex)/1000.0));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //TODO - Be sure that PDB format works, or remove it
                } else if (extension.equalsIgnoreCase("pdb")) {
                    //GenomeFileParser.pdbParse(filename, electro2D, "", fileNum);
                    PDBFileReader reader = new PDBFileReader();
                    try {
                        List<Chain> chainList = reader.getStructure(filePath).getChains();
                        String sequence = "";
                        for (int i=0; i<chainList.size(); i++) {
                            System.out.println(chainList.get(i).getSeqResSequence()+"\n");
                            //sequence+=chainList.get(i).getSeqResSequence();
                        }
                        proteins.add(new Protein(new ProteinSequence(sequence)));
                        lastFileName = fileName;
                        DatabaseUtilities.saveDatabase(proteins,databaseName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //TODO - Be sure that GBK/GB format works, or remove it
                } else if (extension.equalsIgnoreCase("gbk") || extension.equalsIgnoreCase("gb")) {
                    try {
                        LinkedHashMap<String, ProteinSequence> proteinData = GenbankReaderHelper.readGenbankProteinSequence(new File(filePath));
                        if (proteinData != null) {
                            for (ProteinSequence map : proteinData.values()) {
                                //proteins.add(new Protein(map.getSequenceAsString()));
                                for (FeatureInterface<AbstractSequence<AminoAcidCompound>, AminoAcidCompound> feature : map.getFeatures()) {
                                    int begin = feature.getLocations().getStart().getPosition() - 1;
                                    int end = feature.getLocations().getEnd().getPosition() - 1;
                                    if (begin < 0 || end > map.getSequenceAsString().length()-1)
                                        return;
                                    String sequence = map.getSequenceAsString().substring(begin,end);
                                    //proteins.add(new Protein(map.getSequenceAsString(feature.getLocations().getStart(),feature.getLocations().getEnd(),map.getSequenceAsString())));
                                }
                            }
                            lastFileName = fileName;
                            DatabaseUtilities.saveDatabase(proteins,databaseName);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.setVisible(false);
            }
        }
    }
}

