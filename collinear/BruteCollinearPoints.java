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

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> res = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("Points are null");
        }
        else {
            Arrays.sort(points);
            checkPoints(points);
            for (int i = 0; i < (points.length - 3); i++) {
                for (int j = i + 1; j < (points.length - 2); j++) {
                    for (int k = j + 1; k < (points.length - 1); k++) {
                        for (int m = k + 1; m < points.length; m++) {
                            if ((points[i].slopeOrder().compare(points[j], points[k]) == 0) && (
                                    points[i].slopeOrder().compare(points[j], points[m]) == 0)) {
                                res.add(new LineSegment(points[i], points[m]));
                            }
                        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
