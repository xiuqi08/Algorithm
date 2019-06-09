/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class Board {
    private final String nl = System.lineSeparator();
    private final int[] blocks;
    private final int n;

    public Board(int[][] b) {
        if (b == null)
            throw new java.lang.IllegalArgumentException("Can't initialize with null.");
        n = b.length;
        blocks = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[(twotoone(i, j) - 1)] = b[i][j];
            }
        }
    } // construct a board from an n-by-n array of blocks

    private int twotoone(int i, int j) {
        return i * n + j + 1;
    } // return d according to i,j

    private int[] onetotwo(int d) {
        int[] des = new int[2];
        des[0] = d / n;
        des[1] = d % n - 1;
        if (des[1] < 0) {
            des[0] -= 1;
            des[1] += n;
        }
        return des;
    } // return i,j from d

    private int abs(int a) {
        if (a >= 0) return a;
        else return -a;
    }

    private int[] index(int value) {
        int[] ind = new int[2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[twotoone(i, j) - 1] == value) {
                    ind[0] = i;
                    ind[1] = j;
                    return ind;
                }
            }
        }
        throw new java.lang.IllegalArgumentException("There is no 0.");
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return n;
    } // board dimension n

    public int hamming() {
        int h = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((blocks[twotoone(i, j) - 1] != i * n + j + 1) && (blocks[(twotoone(i, j) - 1)]
                        != 0))
                    h += 1;
            }
        }
        return h;
    } // number of blocks out of place

    public int manhattan() {
        int m = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((blocks[twotoone(i, j) - 1] != twotoone(i, j)) && (blocks[twotoone(i, j) - 1]
                        != 0)) {
                    int[] des = onetotwo(blocks[twotoone(i, j) - 1]);
                    m += (abs(i - des[0]) + abs(j - des[1]));
                }
            }
        }
        return m;
    } // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        return (hamming() == 0);
    } // is this board the goal board?

    private Board exch(int i, int j, int a, int b) {
        int[][] nblocks = new int[n][n];
        for (int c = 0; c < n; c++) {
            for (int d = 0; d < n; d++) {
                nblocks[c][d] = blocks[twotoone(c, d) - 1];
            }
        }
        int tmp = nblocks[i][j];
        nblocks[i][j] = nblocks[a][b];
        nblocks[a][b] = tmp;
        return new Board(nblocks);
    }

    public Board twin() {
        if ((blocks[0] != 0) && (blocks[1] != 0)) return exch(0, 0, 0, 1);
        else return exch(1, 0, 1, 1);
    } // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        else if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (this.blocks.length != that.blocks.length) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.blocks[twotoone(i, j) - 1] != blocks[twotoone(i, j) - 1]) return false;
            }
        }
        return true;
    } // does this board equal y?

    public Iterable<Board> neighbors() {
        ArrayList<Board> nghs = new ArrayList<Board>();
        int[] ind = index(0);
        if (ind[0] != 0) nghs.add(exch(ind[0], ind[1], ind[0] - 1, ind[1]));
        if (ind[1] != 0) nghs.add(exch(ind[0], ind[1], ind[0], ind[1] - 1));
        if (ind[0] < (n - 1)) nghs.add(exch(ind[0], ind[1], ind[0] + 1, ind[1]));
        if (ind[1] < (n - 1)) nghs.add(exch(ind[0], ind[1], ind[0], ind[1] + 1));
        return nghs;
    } // all neighboring boards

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n);
        s.append(nl);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[twotoone(i, j) - 1]));
            }
            s.append(nl);
        }
        return s.toString();
    } // string representation of this board (in the output format specified below)

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        int[][] comp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                comp[i][j] = 1;
            }
        }
        System.out.print(initial.toString());
        System.out.println("Hamming" + initial.hamming());
        System.out.println("Manhattan" + initial.manhattan());
        System.out.println(initial.equals(new Board(comp)));
        for (Board neighbor : initial.neighbors()) {
            System.out.print(neighbor.toString());
        }
        System.out.print(initial.twin().toString());
    } // unit tests (not graded)
}
