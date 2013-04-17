/*
 * Copyright (C) 2013 Rochester Institute of Technology
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 */

/*
 * This class has two static methods that are used to parse sequence data from
 * fasta files.
 *
 * version 3
 */

/**
 *
 * @author Amanda Fisher
 */
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class FastaParser {

    /**
     * The parse method is given a file to read from, line by line. It passes
     * each line to the process static method to get rid of any white spaces and
     * to check if the line is a comment. Parse returns the sequence to the
     * MassSpecMain to be put into the inputArea.
     *
     * @param file Selected by the user in MassSpecMain.
     * @return Sequence data.
     */
    public static String parse(File file) {
        String returnSequence = new String();
        try {
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            String line;
            while((line = bReader.readLine()) != null) {
                line = process(line);
                returnSequence = returnSequence.concat(line);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return returnSequence;
    }

    /**
     * Process checks a string to see if it is a comment and should be ignored,
     * as well as removes white space from the line.
     *
     * @param line Given by the prase method as it reads lines from the user
     *             supplied file.
     * @return String to be added to the end of the sequence to be sent to
     *         MassSpecMain.
     */
    private static String process(String line) {
        if(line.charAt(0) != ';' && line.charAt(0) != '>') {
            int starIndex = line.indexOf("[^\\w]");
            if(starIndex != -1) {
                String[] splitLine = line.split("[^\\w]");
                line = new String();
                for(String split : splitLine) {
                    line = line.concat(split);
                }
            }
            return line;
        }
        return "";
    }

}
