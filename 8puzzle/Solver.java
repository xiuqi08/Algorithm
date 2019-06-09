/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private int m;
    private int tm;
    private boolean solvable;
    private final ArrayList<Board> res = new ArrayList<Board>();

    private class SearchNode implements Comparable<SearchNode> {
        private int move = 0;
        private Board predecessor = null;
        private final Board board;
        private final int priority;

        public SearchNode(Board board) {
            this.board = board;
            priority = board.manhattan() + move;
        }

        public void addpredecessor(Board pboard) {
            predecessor = pboard;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    public Solver(Board initial) {
        MinPQ<SearchNode> mpq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> tpq = new MinPQ<SearchNode>();
        mpq.insert(new SearchNode(initial));
        tpq.insert(new SearchNode(initial.twin()));
        while (!mpq.isEmpty() && !tpq.isEmpty()) {
            SearchNode sn = mpq.delMin();
            res.add(sn.board);
            if (sn.board.isGoal()) {
                solvable = true;
                break;
            }
            m++;
            for (Board ngh : sn.board.neighbors()) {
                SearchNode nsn = new SearchNode(ngh);
                nsn.addpredecessor(sn.board);
                nsn.move = m;
                if (!nsn.board.equals(sn.predecessor)) mpq.insert(nsn);
            }

            SearchNode tsn = tpq.delMin();
            if (tsn.board.isGoal()) {
                solvable = false;
                break;
            }
            tm++;
            for (Board ngh : tsn.board.neighbors()) {
                SearchNode nsn = new SearchNode(ngh);
                nsn.addpredecessor(tsn.board);
                nsn.move = tm;
                if (!nsn.board.equals(tsn.predecessor)) tpq.insert(nsn);
            }
        }
    } // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return solvable;
    } // is the initial board solvable?

    public int moves() {
        if (isSolvable()) return m;
        else return -1;
    } // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        if (isSolvable()) return res;
        else return null;
    } // sequence of boards in a shortest solution; null if unsolvable

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } // solve a slider puzzle (given below)
}
