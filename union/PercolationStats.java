/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] res;
    private double ave;
    private double dev;
    private final double num1 = 1.96;

    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0)) {
            throw new IllegalArgumentException("Both n and trials must be larger than 0.");
        }
        else {
            res = new double[trials];
            for (int i = 0; i < trials; i += 1) {
                Percolation perc = new Percolation(n);
                int[] serial = new int[n * n];
                for (int j = 0; j < n * n; j += 1) {
                    serial[j] = j + 1;
                }
                StdRandom.shuffle(serial);
                int j = 0;
                while ((j < serial.length) && (!perc.percolates())) {
                    // System.out.println(j);
                    int row = serial[j] / n + 1;
                    int col;
                    if ((serial[j] % n) == 0) {
                        col = n;
                        row -= 1;
                    }
                    else {
                        col = serial[j] % n;
                    }
                    // System.out.printf("serial[j]:%d row:%d col:%d", serial[j], row, col);
                    // System.out.println();
                    perc.open(row, col);
                    j += 1;
                }
                res[i] = j / (double) (n * n);
            }
        }
    } // perform trials independent experiments on an n-by-n grid

    public double mean() {
        ave = StdStats.mean(res);
        return ave;
    } // sample mean of percolation threshold

    public double stddev() {
        dev = StdStats.stddev(res);
        return dev;
    } // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return ave - (num1 * dev) / (Math.sqrt(res.length));
    } // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return ave + (num1 * dev) / (Math.sqrt(res.length));
    } // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = 200;
        int trials = 100;
        PercolationStats percSim = new PercolationStats(n, trials);
        System.out.println(percSim.mean());
        System.out.println(percSim.stddev());
        System.out.println(percSim.confidenceLo());
        System.out.println(percSim.confidenceHi());
    } // test client (described below)
}
