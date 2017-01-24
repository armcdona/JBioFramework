package org.jbioframework.massspec;
/*
 * This class is an exception thrown by AminoAcid when an incorrect input is
 * given to one of its two constructors.
 * 
 * version 3
 */

/**
 * Thrown when a character in an amino acid sequence isn't recognized
 */
public class AminoException extends Exception {

    public AminoException(String message) {
        super(message);
    }
}
