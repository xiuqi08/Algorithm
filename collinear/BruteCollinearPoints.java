/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class BruteCollinearPoints {
    private LineSegment[] res;
    private int n = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("Points are null");
        }
        else {
            for (int i = 0; i < (points.length - 3); i++) {
                for (int j = i + 1; j < (points.length - 2); j++) {
                    for (int k = j + 1; k < (points.length - 1); k++) {
                        for (int m = k + 1; m < points.length; m++) {
                            if ((points[i].slopeOrder().compare(points[j], points[k]) == 0) && (
                                    points[i].slopeOrder().compare(points[j], points[m]) == 0)) {
                                n += 1;
                            }
                        }
                    }
                }
            }
            res = new LineSegment[n];
            int p = 0;
            for (int i = 0; i < (points.length - 3); i++) {
                for (int j = i + 1; j < (points.length - 2); j++) {
                    for (int k = j + 1; k < (points.length - 1); k++) {
                        for (int m = k + 1; m < points.length; m++) {
                            if ((points[i].slopeOrder().compare(points[j], points[k]) == 0) && (
                                    points[i].slopeOrder().compare(points[j], points[m]) == 0)) {
                                res[p++] = new LineSegment(points[i], points[m]);
                            }
                        }
                    }
                }
            }
        }
    } // finds all line segments containing 4 points

    public int numberOfSegments() {
        return n;
    } // the number of line segments

    public LineSegment[] segments() {
        return res;
    } // the line segments
}
