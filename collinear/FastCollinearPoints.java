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
    private List<LineSegment> res = new ArrayList<LineSegment>();
    
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("Points are null");
        }
        else {
            for (int i = 0; i < points.length; i++) {
                Point[] tmp = new Point[points.length - 1];
                int k = 0;
                for (int j = i+1; j < points.length; j++) {
                    tmp[k++] = points[k];
                }
                Comparator<Point> cmp = points[i].slopeOrder();
                Arrays.sort(tmp, cmp);
                for (int j = 0; j < (tmp.length -2); j++) {
                    if ((points[i].slopeTo(tmp[j]) == points[i].slopeTo(tmp[j + 1])) && (
                            points[i].slopeTo(tmp[j]) == points[i].slopeTo(tmp[j + 2]))) {
                        if (j < tmp.length -3) {
                            if (points[i].slopeTo(tmp[j]) != points[i].slopeTo(tmp[j + 3])){
                                res.add(new LineSegment(points[i], tmp[j + 2]));
                            }
                        else {
                            res.add(new LineSegment(points[i], tmp[j + 2]));
                        }
                    }
                }
            }
        }
    } // finds all line segments containing 4 points

    public int numberOfSegments() {
        return res.size();
    } // the number of line segments

    public LineSegment[] segments() {
        return res.toArray(new LineSegment[res.size()]);
    } // the line segments
}
