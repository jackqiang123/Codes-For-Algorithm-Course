/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
//import java.lang.Double;
//import java.util.Arrays;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate
  
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
     
    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
       
        if (this.x == that.x && this.y == that.y) // same point
            return Double.NEGATIVE_INFINITY;
        if (this.y == that.y) //horizontal line
            return +0.0;
        if (this.x == that.x)//vertical line
            return Double.POSITIVE_INFINITY;
        return  (double)(that.y-this.y)/(that.x-this.x);
        /* YOUR CODE HERE */
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y<that.y)
            return -1;
        if (this.y>that.y)
            return 1;
        if (this.x<that.x)
            return -1;
        if (this.x>that.x)
            return 1;
        else 
            return 0;   
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class SlopeOrder implements Comparator<Point>
    {
       public int compare(Point v, Point w)
       {
           if (Point.this.slopeTo(v) < Point.this.slopeTo(w))
               return -1;
           else if (Point.this.slopeTo(v) > Point.this.slopeTo(w))
               return 1;
           else 
               return 0;
       }
    }
    
    
    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
      //  Point [] test = new Point[2];
     //  test[0] = new Point(10,10000);
    //  test[1] = new Point(1234,10000);
     //   Arrays.sort(test);
       // StdOut.println(test[0].toString()+test[1].toString());
    }
}
