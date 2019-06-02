/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private boolean[] grid;
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;

    public Percolation(int n) {
        this.n = n;
        if (this.n <= 0) {
            throw new IllegalArgumentException("Size of grid must larger than 0.");
        }
        else {
            grid = new boolean[n * n + 2];
            for (int i = 0; i < n * n; i += 1) {
                grid[i] = false;
            }
            uf1 = new WeightedQuickUnionUF(n * n + 2);
            uf2 = new WeightedQuickUnionUF(n * n + 1);
        }
    } // create n-by-n grid, with all sites blocked

    public void open(int row, int col) {
        if ((row > this.n) || (col > this.n) || (row <= 0) || (col <= 0)) {
            throw new IllegalArgumentException("Row or column outsides prescribed range.");
        }
        else if (!(isOpen(row, col))) {
            grid[(row - 1) * this.n + col - 1] = true;
            if (row == 1) {
                uf1.union(n * n, col - 1);
                uf2.union(n * n, col - 1);
            }
            if (row == n) {
                uf1.union(n * n + 1, (n - 1) * n + col - 1);
            }
            if ((row > 1) && (isOpen(row - 1, col))) {
                uf1.union((row - 1) * this.n + col - 1, (row - 2) * this.n + col - 1);
                uf2.union((row - 1) * this.n + col - 1, (row - 2) * this.n + col - 1);
            }
            if ((col > 1) && (isOpen(row, col - 1))) {
                uf1.union((row - 1) * this.n + col - 1, (row - 1) * this.n + col - 2);
                uf2.union((row - 1) * this.n + col - 1, (row - 1) * this.n + col - 2);
            }
            if ((row < this.n) && (isOpen(row + 1, col))) {
                uf1.union((row - 1) * this.n + col - 1, row * this.n + col - 1);
                uf2.union((row - 1) * this.n + col - 1, row * this.n + col - 1);
            }
            if ((col < this.n) && (isOpen(row, col + 1))) {
                uf1.union((row - 1) * this.n + col - 1, (row - 1) * this.n + col);
                uf2.union((row - 1) * this.n + col - 1, (row - 1) * this.n + col);
            }
        }
    } // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        if ((row > this.n) || (col > this.n) || (row <= 0) || (col <= 0)) {
            throw new IllegalArgumentException("Row or column outsides prescribed range.");
        }
        else {
            return grid[(row - 1) * this.n + col - 1];
        }
    } // is site (row, col) open?

    public boolean isFull(int row, int col) {
        if ((row > this.n) || (col > this.n) || (row <= 0) || (col <= 0)) {
            throw new IllegalArgumentException("Row or column outsides prescribed range.");
        }
        else {
            return (uf2.connected(n * n, (row - 1) * this.n + col - 1));
        }
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        int s = 0;
        for (int i = 0; i < n; i += 1) {
            for (int j = 0; j < n; j += 1) {
                if (isOpen(i + 1, j + 1)) {
                    s += 1;
                }
            }
        }
        return s;
    } // number of open sites

    public boolean percolates() {
        return (uf1.connected(n * n, n * n + 1));
    } // does the system percolate?

    public static void main(String[] args) {
        int n = 4;
        Percolation perc = new Percolation(n);
        perc.open(4, 1);
        perc.open(3, 1);
        perc.open(2, 1);
        perc.open(1, 1);
        perc.open(1, 4);
        perc.open(2, 4);
        perc.open(4, 4);
        System.out.println(perc.isFull(4, 4));
        // System.out.println(perc.percolates());
    }
}
