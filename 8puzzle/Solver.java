/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean solvable;
    private SearchNode goal;

    private class SearchNode implements Comparable<SearchNode> {
        private int move = 0;
        private SearchNode predecessor = null;
        private final Board board;
        private int priority;

        public SearchNode(Board board) {
            this.board = board;
            priority = board.manhattan();
        }

        public void addpredecessor(SearchNode psn) {
            predecessor = psn;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority + this.move, that.priority + that.move);
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException("Null board");
        MinPQ<SearchNode> mpq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> tpq = new MinPQ<SearchNode>();
        mpq.insert(new SearchNode(initial));
        tpq.insert(new SearchNode(initial.twin()));
        while (!mpq.isEmpty() && !tpq.isEmpty()) {
            SearchNode sn = mpq.delMin();
            // System.out.println(sn.board.toString());
            if (sn.board.isGoal()) {
                solvable = true;
                goal = sn;
                break;
            }
            for (Board ngh : sn.board.neighbors()) {
                if (sn.predecessor == null || !ngh.equals(sn.predecessor.board)) {
                    SearchNode nsn = new SearchNode(ngh);
                    nsn.addpredecessor(sn);
                    nsn.move = sn.move + 1;
                    mpq.insert(nsn);
                }
            }

            SearchNode tsn = tpq.delMin();
            // System.out.println(tsn.board.toString());
            if (tsn.board.isGoal()) {
                solvable = false;
                break;
            }
            for (Board b : tsn.board.neighbors()) {
                if (tsn.predecessor == null || !b.equals(tsn.predecessor.board)) {
                    SearchNode nsn = new SearchNode(b);
                    nsn.addpredecessor(tsn);
                    nsn.move = tsn.move + 1;
                    tpq.insert(nsn);
                }
            }
        }
    } // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return solvable;
    } // is the initial board solvable?

    public int moves() {
        if (isSolvable()) return goal.move;
        else return -1;
    } // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> res = new Stack<Board>();
            for (SearchNode i = goal; i != null; i = i.predecessor) res.push(i.board);
            return res;
        }
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
