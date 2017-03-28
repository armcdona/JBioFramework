package org.jbioframework.library.utilities;

import com.thoughtworks.xstream.XStream;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.storage.ArrayListSequenceReader;
import org.jbioframework.library.protein.AminoAcid;
import org.jbioframework.library.protein.Protein;
import org.jbioframework.library.protein.ProteinList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

public class XMLUtilities {

    private final static Logger logger = LoggerFactory.getLogger(XMLUtilities.class);

    private static XStream getXStream() {
        XStream xstream = new XStream();
        xstream.alias("proteinsequence", ProteinSequence.class);
        xstream.alias("aminoacid", AminoAcid.class);
        xstream.alias("proteinlist", ProteinList.class);
        xstream.addImplicitCollection(ProteinList.class,"proteins",Protein.class);
        xstream.alias("protein",Protein.class);
        xstream.alias("sequencereader",ArrayListSequenceReader.class);
        xstream.alias("aminoacidcompoundset",AminoAcidCompoundSet.class);
        xstream.alias("aminoacidcompound", AminoAcidCompound.class);
        return xstream;
    }

    private static void checkXMLFolderExists() {
        String filePath = getXMLRelativePath("");
        String folderPath = processRelativePath(filePath.substring(0,filePath.indexOf(".xml")-1));
        File XMLFolder = new File(folderPath);
        if (!( XMLFolder.exists())) {
            if (!( XMLFolder.mkdir())) {
                logger.error("Could not make XML folder ("+ XMLFolder.getAbsolutePath()+")!");
            }
        }

    }

    private static String getXMLRelativePath(String fileName) {
        return processRelativePath((new Configuration().getProperty(ConfigurationConstants.XML_FOLDER))+fileName+".xml");
    }

    private static String processRelativePath(String relativePath) {
        String processedRelativePath = relativePath;
        if (relativePath.contains("//")) {
            if ((relativePath.indexOf("//") + 2) > (relativePath.length() - 1) ){
                processedRelativePath = relativePath.substring(0,relativePath.indexOf("//"));
            } else {
                processedRelativePath = relativePath.substring(0,relativePath.indexOf("//")) + relativePath.substring(relativePath.indexOf("//")+2,relativePath.length()-1);
            }
        }
        return processedRelativePath;
    }
    public static boolean doesFileExist(String fileName) {
        boolean exists = false;
        File XMLFile = new File(getXMLRelativePath(fileName));
        if (XMLFile.exists()) {
            exists = true;
        }
        return exists;
    }

    public static ArrayList<Protein> loadFile(String filename) {
        checkXMLFolderExists();
        ArrayList<Protein> proteins = new ArrayList<>();
        XStream xstream = getXStream();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getXMLRelativePath(filename)));
            proteins = ((ProteinList)(xstream.fromXML(reader))).getProteins();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return proteins;
    }

    public static void saveFile(String filename, ArrayList<Protein> proteins) {
        checkXMLFolderExists();
        XStream xstream = getXStream();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getXMLRelativePath(filename)));
            xstream.toXML(new ProteinList(proteins),writer);
            writer.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
