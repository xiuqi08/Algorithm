/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> ts;

    public PointSET() {
        ts = new TreeSet<Point2D>();
    } // construct an empty set of points

    public boolean isEmpty() {
        return ts.isEmpty();
    } // is the set empty?

    public int size() {
        return ts.size();
    } // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("Point is null");
        else ts.add(p);
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("Point is null");
        else return ts.contains(p);
    } // does the set contain point p?

    public void draw() {
        for (Point2D p : ts) {
            p.draw();
        }
    } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException("Point is null");
        else {
            ArrayList<Point2D> res = new ArrayList<Point2D>();
            for (Point2D p : ts) {
                if (rect.contains(p)) res.add(p);
            }
            return res;
        }
    } // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("Point is null");
        else {
            if (ts.isEmpty()) return null;
            else {
                double min = Double.POSITIVE_INFINITY;
                Point2D n = null;
                for (Point2D q : ts) {
                    double dis = p.distanceSquaredTo(q);
                    if (p.distanceSquaredTo(q) < min) {
                        min = dis;
                        n = q;
                    }
                }
                return n;
            }
        }
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        PointSET brute = new PointSET();
        brute.insert(new Point2D(0.7, 0.2));
        brute.insert(new Point2D(0.5, 0.4));
        brute.insert(new Point2D(0.2, 0.3));
        brute.insert(new Point2D(0.4, 0.7));
        brute.insert(new Point2D(0.9, 0.6));
        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
    } // unit testing of the methods (optional)
}
