/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int trials;
    private double[] percThresholdArray;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.trials = trials;
        this.percThresholdArray = new double[trials];
        for (int i = 0; i < trials; i++) {
            //System.out.printf("\rTrial number: %d", i+1);
            Percolation perc = new Percolation(n);
            while(!perc.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if(!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            double percThreshold = (double) perc.numberOfOpenSites() / (n * n);
            percThresholdArray[i] = percThreshold;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percThresholdArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percThresholdArray);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.valueOf(args[0]);
        int trials = Integer.valueOf(args[1]);

        Stopwatch stopwatch = new Stopwatch();
        PercolationStats percStats = new PercolationStats(n, trials);
        String confidence = percStats.confidenceLo() + ", "
                + percStats.confidenceHi();
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + confidence + "]");
        //System.out.printf("Elapsed time: %fs\n\n", stopwatch.elapsedTime());
    }
}
