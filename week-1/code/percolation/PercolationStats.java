/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

	private double[] threshold;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}

		threshold = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);

			int[] rows = new int[n], cols = new int[n];
			while (!percolation.percolates()) {
				int row = getRandomNotRepeat(1, n + 1, rows);
				int col = getRandomNotRepeat(1, n + 1, cols);

				percolation.open(row, col);
			}

			threshold[i] = percolation.numberOfOpenSites() * 1.0 / (n * n);
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

	private int getRandomNotRepeat(int min, int max, int[] exists) {
		int random;
		do {
			random = (int) (Math.random() * (max - min) + min);
		} while (exists[random - 1] == 1);
		exists[random - 1] = 1;
		return random;
	}

	// sample mean of percolation threshold
	public double mean() {
		double sum = 0.0;
		for (int i = 0; i < threshold.length; i++) {
			sum += threshold[i];
		}
		return sum / threshold.length;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		double mean = mean();
		double sum = 0.0;
		for (int i = 0; i < threshold.length; i++) {
			sum += (threshold[i] - mean) * (threshold[i] - mean);
		}

		return sum * 1.0 / (threshold.length - 1);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(threshold.length);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(threshold.length);
	}

}
