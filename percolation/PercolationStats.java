/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats n T
 *  Dependencies: Percolation.java StdStats.java StdRandom.java
 *                StdOut.java
 *
 *  This program takes the grid size n as a command-line argument.
 *  Then, the user repeatedly clicks sites to open with the mouse.
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] open;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        open = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            open[i] = (perc.numberOfOpenSites() * 1.0) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(open);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(open);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(open.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(open.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 1;
        int T = 1;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }
        PercolationStats pstats = new PercolationStats(n, T);
        StdOut.println("mean\t\t\t= " + pstats.mean());
        StdOut.println("stddev\t\t\t= " + pstats.stddev());
        StdOut.println(
                "95% confidence interval = [" + pstats.confidenceLo() + ", " + pstats.confidenceHi()
                        + "]");
    }

}
