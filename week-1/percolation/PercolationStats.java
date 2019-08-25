/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
	private static final double CONFIDENCE_95 = 1.96;
	private final double[] threshold;
	private double mean;
	private double stddev;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}

		threshold = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);

			int count = 0;
			while (!percolation.percolates()) {
				int row, col;
				do {
					row = StdRandom.uniform(n) + 1;
					col = StdRandom.uniform(n) + 1;
				} while (percolation.isOpen(row, col));

				percolation.open(row, col);
				count++;
			}

			threshold[i] = count * 1.0 / (n * n);
		}
	}

	// test client (see below)
	public static void main(String[] args) {
		Stopwatch stopwatch = new Stopwatch();
		int n = 200, trials = 100;
		PercolationStats percolationStats = new PercolationStats(n, trials);
		System.out.println("Experiment status");
		System.out.println("-----------------------------------");
		System.out.println(String.format("Percolation size: %d, trial num: %d", n, trials));
		System.out.println(String.format("Total time: %fs", stopwatch.elapsedTime()));
		System.out.println(String.format("Mean of percolation threshold: %f", percolationStats.mean()));
		System.out.println(String.format("Standard deviation of percolation threshold: %f", percolationStats.stddev()));
		System.out.println(String.format("Endpoint of 95 confidence interval: %f ~ %f", percolationStats.confidenceLo(), percolationStats.confidenceHi()));
	}

	// sample mean of percolation threshold
	public double mean() {
		if (mean < 0.0001) {
			mean = StdStats.mean(threshold, 0, threshold.length);
		}
		return mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		if (stddev <= 0.0001) {
			stddev = StdStats.stddev(threshold, 0, threshold.length);
		}
		return stddev;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(threshold.length);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(threshold.length);
	}

}
