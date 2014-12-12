package Electro2D;/*
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

/**
 * This class is an exception thrown by one of the Electro2D.Protease classes whenever
 * an incorrect input is given to the cut method.
 *
 * |proteins|
 * @author Amanda Fisher
 */
public class ProteaseException extends Exception {
    //
    public ProteaseException() {}
    public ProteaseException(String message) {
        super(message);
    }
}
