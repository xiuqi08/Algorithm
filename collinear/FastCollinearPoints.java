/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> copy = new ArrayList<LineSegment>();
    private LineSegment[] res;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("Points are null");
        }
        else {
            for (int i = 0; i < points.length; i++) {
                Point[] tmp = new Point[points.length - 1];
                int k = 0;
                for (int j = 0; j < points.length && j != i; j++) {
                    tmp[k++] = points[k];
                }
                Comparator<Point> cmp = points[i].slopeOrder();
                Arrays.sort(tmp, cmp);
                for (int j = 0; j < tmp.length; j++) {
                    if ((points[i].slopeTo(tmp[j]) == points[i].slopeTo(tmp[j + 1])) && (
                            points[i].slopeTo(tmp[j]) == points[i].slopeTo(tmp[j + 2])) && (
                            points[i].slopeTo(tmp[j]) != points[i].slopeTo(tmp[j + 3]))) {
                        copy.add(new LineSegment(points[i], tmp[i + 2]));
                    }
                }
            }
        }
    } // finds all line segments containing 4 points

    public int numberOfSegments() {
        return copy.size();
    } // the number of line segments

    public LineSegment[] segments() {
        res = new LineSegment[copy.size()];
        int i = 0;
        for (LineSegment t : copy) {
            res[i++] = t;
        }
        return res;
    } // the line segments
}
