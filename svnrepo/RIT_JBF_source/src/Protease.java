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

/**
 * Protease is the abstract class inherited by all classes that are responsible
 * for cutting a protein's sequence at specific points.
 *
 * Only method they are required
 * to have is cut.
 *
 * |proteins|
 * @author Amanda Fisher
 */
import java.util.ArrayList;

public abstract class Protease {
    public abstract ArrayList<String> cut(String sequence) throws ProteaseException;
}
