/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> res = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("Points are null");
        }
        else {
            Arrays.sort(points);
            checkPoints(points);
            for (int i = 0; i < points.length; i++) {
                Point[] tmp = new Point[points.length - i - 1];
                for (int j = i + 1; j < points.length; j++) {
                    tmp[j - i - 1] = points[j];
                }
                Comparator<Point> cmp = points[i].slopeOrder();
                Arrays.sort(tmp, cmp);
                for (int j = 0; j < (tmp.length - 2); j++) {
                    if ((points[i].slopeTo(tmp[j]) == points[i].slopeTo(tmp[j + 1])) && (
                            points[i].slopeTo(tmp[j]) == points[i].slopeTo(tmp[j + 2]))) {
                        if (j < (tmp.length - 3)) {
                            if (points[i].slopeTo(tmp[j]) != points[i].slopeTo(tmp[j + 3]))
                                res.add(new LineSegment(points[i], tmp[j + 2]));
                        }
                        else res.add(new LineSegment(points[i], tmp[j + 2]));
                    }
                }
            }
        }
    } // finds all line segments containing 4 points

    private void checkPoints(Point[] points) {
        for (int i = 0; i < (points.length - 1); i++) {
            if ((points[i] == null) || (points[i].compareTo(points[i + 1]) == 0))
                throw new java.lang.IllegalArgumentException("Illegal argument");
        }
    }

    public int numberOfSegments() {
        return res.size();
    } // the number of line segments

    public LineSegment[] segments() {
        return res.toArray(new LineSegment[res.size()]);
    } // the line segments

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
