package org.jbioframework.library.utilities;

import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.storage.ArrayListSequenceReader;
import org.jbioframework.library.protein.AminoAcid;
import org.jbioframework.library.protein.Protein;
import org.jbioframework.library.protein.ProteinList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdom2.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class XMLUtilities {

    private final static Logger logger = LoggerFactory.getLogger(XMLUtilities.class);

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
                processedRelativePath = relativePath.substring(0,relativePath.indexOf("//")) +
                        relativePath.substring(relativePath.indexOf("//")+2,relativePath.length()-1);
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

    private static Element getElementFromProtein(Protein protein) {
        Element proteinElement = new Element("Protein");
        proteinElement.setAttribute("name",protein.getName());
        proteinElement.setAttribute("functions",protein.getFunctions());

        Element sequence = new Element("Sequence");
        sequence.addContent(new Text(protein.getProteinSequenceAsString()));

        Element pI = new Element("pI");
        pI.addContent(new Text(Double.toString(protein.getpI())));

        Element molecularWeight = new Element("MW");
        molecularWeight.addContent(new Text(Double.toString(protein.getMolecularWeight())));

        return proteinElement;
    }

    private static Protein getProteinFromElement(Element proteinElement) {
        Protein protein = new Protein("");
        return protein;
    }

    public static ArrayList<Protein> loadFile(String filename) {
        checkXMLFolderExists();
        ArrayList<Protein> proteins = new ArrayList<>();
        try {
            DOMBuilder builder = new DOMBuilder();
            Document xmlDocument = builder.build(DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(getXMLRelativePath(filename)));
            ArrayList<Element> proteinElements = new ArrayList<>(xmlDocument.getRootElement().getChildren("Proteins"));
            for (Element proteinElement : proteinElements) {
                proteins.add(getProteinFromElement(proteinElement));
            }
        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage());
        } catch (SAXException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return proteins;
    }

    public static void saveFile(String filename, ArrayList<Protein> proteins) {
        checkXMLFolderExists();
        Element proteinsElement = new Element("Proteins");
        for (Protein protein : proteins) {
            proteinsElement.addContent(getElementFromProtein(protein));
        }
        Document xmlDocument = new Document(proteinsElement);
        XMLOutputter xmlOutputter = new XMLOutputter();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getXMLRelativePath(filename)));
            xmlOutputter.output(xmlDocument,writer);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
