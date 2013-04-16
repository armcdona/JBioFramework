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
 * UnknownLineException.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *      $Log$
 *
 */

/**
 * Exception thrown when a given line in a line graph isn't recognized
 * @author Kyle Dewey
 */
public class UnknownLineException extends Exception {
    public UnknownLineException() {}
    public UnknownLineException( String message ) {
	super( message );
    }
}
