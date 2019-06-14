/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;

public class KdTree {
    private final double POINT_WIDTH = 0.01;
    private final double LINE_WIDTH = 0.005;
    private int n;
    private Node root = null;
    private final ArrayList<Point2D> res = new ArrayList<Point2D>(); // points in rect
    private double min; // nearest distance to p
    private Point2D nr = null; // nearest point

    public KdTree() {
        n = 0;
    } // construct an empty set of points

    private class Node implements Comparable<Node> {
        private Node parent = null;
        private Node left = null;
        private Node right = null;
        private boolean isVertical;
        private final Point2D p;
        private double min;
        private double max;

        public Node(Point2D p) {
            this.p = p;
        }

        public void setParent(Node par) {
            if (par == null) {
                isVertical = true;
                min = 0;
                max = 1;
            }
            else {
                parent = par;
                isVertical = !parent.isVertical;
            }
        }

        public void setminmax(Node par, boolean isLeft) {
            if (isLeft) {
                // set min
                if (par.parent != null) min = par.parent.min;
                else min = 0;

                // set max
                if (par.isVertical) {
                    if (par.parent != null) max = Double.min(par.p.x(), par.parent.max);
                    else max = par.p.x();
                }
                else {
                    if (par.parent != null) max = Double.min(par.p.y(), par.parent.max);
                    else max = par.p.y();
                }
            }
            else {
                // set min
                if (par.isVertical) {
                    if (par.parent != null) min = Double.max(par.p.x(), par.parent.min);
                    else min = par.p.x();
                }
                else {
                    if (par.parent != null) min = Double.max(par.p.y(), par.parent.min);
                    else min = par.p.y();
                }

                // set max
                if (par.parent != null) max = par.parent.max;
                else max = 1;
            }
        }

        public int compareTo(Node that) {
            if ((this.p.x() == that.p.x()) && (this.p.y() == that.p.y())) {
                return 0;
            }
            else if (isVertical) {
                // System.out.println("Vertical");
                // System.out.println(this.toString());
                // System.out.println(that.toString());
                int c = Double.compare(this.p.x(), that.p.x());
                if (c == 0) return Double.compare(this.p.y(), that.p.y());
                else return c;
            }
            else {
                // System.out.println("Horizontal");
                // System.out.println(this.toString());
                // System.out.println(that.toString());
                int c = Double.compare(this.p.y(), that.p.y());
                if (c == 0) return Double.compare(this.p.x(), that.p.x());
                else return c;
            }
        }

        public String toString() {
            return p.x() + " " + p.y();
        }
    }

    public boolean isEmpty() {
        return (n == 0);
    } // is the set empty?

    public int size() {
        return n;
    } // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("Point is null");
        else {
            Node node = new Node(p);
            if (n == 0) {
                node.setParent(null);
                root = node;
                n += 1;
            }
            else {
                Node cmp = root;
                while (cmp != null) {
                    int c = cmp.compareTo(node);
                    if (c == 0) {
                        // System.out.println("Add existed node");
                        break;
                    }
                    else if (c > 0) {
                        if (cmp.left == null) {
                            // System.out.println("Add left node");
                            // System.out.println(node.p.toString());
                            cmp.left = node;
                            node.setParent(cmp);
                            node.setminmax(cmp, true);
                            n += 1;
                            break;
                        }
                        else cmp = cmp.left;
                    }
                    else {
                        if (cmp.right == null) {
                            // System.out.println("Add right node");
                            // System.out.println(node.p.toString());
                            cmp.right = node;
                            node.setParent(cmp);
                            node.setminmax(cmp, false);
                            n += 1;
                            break;
                        }
                        else cmp = cmp.right;
                    }
                }
            }
        }
    } // add the point to the set (if it is not already in the set)

    private boolean containnode(Node node, Node cmp) {
        int c = cmp.compareTo(node);
        if (c == 0) return true;
        else if (c > 0) {
            if (cmp.left != null) return containnode(node, cmp.left);
            else return false;
        }
        else if (cmp.right != null) return containnode(node, cmp.right);
        else return false;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("Point is null");
        else if (root == null) return false;
        else {
            Node node = new Node(p);
            return containnode(node, root);
        }
    } // does the set contain point p?

    private void mydraw(Node node) {
        StdDraw.setPenColor(Color.black);
        StdDraw.setPenRadius(POINT_WIDTH);
        node.p.draw();
        if (node.isVertical) {
            StdDraw.setPenColor(Color.red);
            StdDraw.setPenRadius(LINE_WIDTH);
            StdDraw.line(node.p.x(), node.min, node.p.x(), node.max);
        }
        else {
            StdDraw.setPenColor(Color.blue);
            StdDraw.setPenRadius(LINE_WIDTH);
            StdDraw.line(node.min, node.p.y(), node.max, node.p.y());
        }
        if (node.left != null) mydraw(node.left);
        if (node.right != null) mydraw(node.right);
    }

    public void draw() {
        if (n != 0) {
            StdDraw.setPenColor(Color.black);
            StdDraw.setPenRadius(POINT_WIDTH);
            root.p.draw();
            StdDraw.setPenColor(Color.red);
            StdDraw.setPenRadius(LINE_WIDTH);
            StdDraw.line(root.p.x(), 0, root.p.x(), 1);
            if (root.left != null) mydraw(root.left);
            if (root.right != null) mydraw(root.right);
        }
    } // draw all points to standard draw

    private void search(RectHV rect, Node node) {
        if (rect.contains(node.p)) res.add(node.p);
        if (node.isVertical) {
            if ((rect.xmin() <= node.p.x()) && (node.left != null)) search(rect, node.left);
            if ((rect.xmax() >= node.p.x()) && (node.right != null)) search(rect, node.right);
        }
        else {
            if ((rect.ymin() <= node.p.y()) && (node.left != null)) search(rect, node.left);
            if ((rect.ymax() >= node.p.y()) && (node.right != null)) search(rect, node.right);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException("Point is null");
        else if (root == null) return null;
        else {
            search(rect, root);
            return res;
        }
    } // all points that are inside the rectangle (or on the boundary)

    private void searchpoint(Node query, Node cmp) {
        double dis = cmp.p.distanceTo(query.p);
        // System.out.println(cmp.p.toString());
        if (dis < min) {
            // System.out.println("dis" + dis);
            nr = cmp.p;
            min = dis;
        }
        int c = cmp.compareTo(query);
        if (c == 0) nr = cmp.p;
        else if (c > 0) {
            // System.out.println("Left");
            if (cmp.left != null) {
                searchpoint(query, cmp.left);
            }
            // System.out.println(
            //         "LConditions: " + cmp.isVertical + " " + (cmp.p.x() - query.p.x()) + " < " + min
            //                 + " " + (cmp.right != null));
            if ((cmp.isVertical) && ((cmp.p.x() - query.p.x()) < min) && (cmp.right != null)) {
                searchpoint(query, cmp.right);
            }
            else if ((!cmp.isVertical) && ((cmp.p.y() - query.p.y()) < min) && (cmp.right
                    != null)) {
                searchpoint(query, cmp.right);
            }
        }
        else {
            // System.out.println("Right");
            if (cmp.right != null) {
                searchpoint(query, cmp.right);
            }
            // if (cmp.isVertical) {
            //     // System.out.println(
            //     // "RConditions: cmp.p: " + cmp.p.toString() + " " + query.p.x() + " - "
            //     //         + cmp.p.x() + " < " + min
            //     //         + " " + (cmp.left != null));
            // }
            // else {
            //     // System.out.println(
            //     // "RConditions: cmp.p: " + cmp.p.toString() + " " + query.p.y() + " - "
            //     //         + cmp.p.y() + " < " + min
            //     //         + " " + (cmp.left != null));
            // }
            if ((cmp.isVertical) && ((query.p.x() - cmp.p.x()) < min) && (cmp.left != null)) {
                // System.out.println("#####y#####" + (query.p.y() - cmp.p.y()));
                // if ((query.p.y() - cmp.p.y()) < min)
                searchpoint(query, cmp.left);
            }
            else if ((!cmp.isVertical) && ((query.p.y() - cmp.p.y()) < min) && (cmp.left != null)) {
                // System.out.println("#####x#####" + (query.p.x() - cmp.p.x()));
                // if ((query.p.x() - cmp.p.x()) < min)
                searchpoint(query, cmp.left);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("Point is null");
        else {
            if (n == 0) return null;
            else {
                min = Double.POSITIVE_INFINITY;
                nr = null;
                Node node = new Node(p);
                searchpoint(node, root);
                return nr;
            }
        }
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        // KdTree kd = new KdTree();
        // kd.insert(new Point2D(0.7, 0.2));
        // System.out.println("size" + kd.size());
        // kd.insert(new Point2D(0.5, 0.4));
        // kd.insert(new Point2D(0.2, 0.3));
        // kd.insert(new Point2D(0.4, 0.7));
        // kd.insert(new Point2D(0.9, 0.6));
        // System.out.println("size" + kd.size());
        // System.out.println(kd.contains(new Point2D(0.7, 0.2)));
        // System.out.println(kd.contains(new Point2D(0.7, 0.3)));
        // // draw the points
        // StdDraw.clear();
        // kd.draw();

        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        // System.out.println("size" + kdtree.size());
        // System.out.println(kdtree.contains(new Point2D(0.7, 0.2)));
        // System.out.println(kdtree.contains(new Point2D(0.7, 0.3)));

        Point2D q = new Point2D(0.7, 0.936);
        System.out.println(kdtree.nearest(q));
        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        kdtree.draw();
        q.draw();
        StdDraw.show();
    } // unit testing of the methods (optional)
}
