/*
 * Point.java
 */

/**
 * Represents a coordinate pair on a graph.
 *
 * @author Kyle Dewey
 */
public class Point {
    /**
     * The X.
     */
    public int x;
    /**
     * The Y.
     */
    public int y;

    /**
     * Instantiates a new Point.
     *
     * @param x the x
     * @param y the y
     */
    public Point( int x, int y ) {
	this.x = x;
	this.y = y;
    }
    public String toString() {
	return "(" + x + ", " + y + ")";
    }
}
