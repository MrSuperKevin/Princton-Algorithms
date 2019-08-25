/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private static final int top = 0;
	private final int size;
	private final int bottom;
	private final WeightedQuickUnionUF weightedQuickUnionUF;
	private final WeightedQuickUnionUF fullness;
	private int openCount;
	// 1 if the site on the coordinate is opened
	private boolean[][] grid;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		size = n;
		openCount = 0;
		bottom = size * size + 1;
		grid = new boolean[n][n];
		// put one virtual point in the head and one in the tail of the array in unionFind
		weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
		fullness = new WeightedQuickUnionUF(n * n + 1);
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		checkLegalArguments(row);
		checkLegalArguments(col);

		if (isOpen(row, col)) {
			return;
		}

		grid[row - 1][col - 1] = true;
		openCount++;

		if (row == 1) {
			weightedQuickUnionUF.union(calIndexOfArray(row, col), top);
			fullness.union(calIndexOfArray(row, col), top);
		}
		if (row == size) {
			weightedQuickUnionUF.union(calIndexOfArray(row, col), bottom);
		}

		// connect sites around the new open site
		if (row > 1 && isOpen(row - 1, col)) {
			weightedQuickUnionUF.union(calIndexOfArray(row, col), calIndexOfArray(row - 1, col));
			fullness.union(calIndexOfArray(row, col), calIndexOfArray(row - 1, col));
		}

		if (row < size && isOpen(row + 1, col)) {
			weightedQuickUnionUF.union(calIndexOfArray(row, col), calIndexOfArray(row + 1, col));
			fullness.union(calIndexOfArray(row, col), calIndexOfArray(row + 1, col));
		}

		if (col > 1 && isOpen(row, col - 1)) {
			weightedQuickUnionUF.union(calIndexOfArray(row, col), calIndexOfArray(row, col - 1));
			fullness.union(calIndexOfArray(row, col), calIndexOfArray(row, col - 1));
		}
		if (col < size && isOpen(row, col + 1)) {
			weightedQuickUnionUF.union(calIndexOfArray(row, col), calIndexOfArray(row, col + 1));
			fullness.union(calIndexOfArray(row, col), calIndexOfArray(row, col + 1));
		}
	}

	// calculate the index of array in union find class, skip one in the head
	private int calIndexOfArray(int row, int col) {
		return (row - 1) * size + col;
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		checkLegalArguments(row);
		checkLegalArguments(col);

		return grid[row - 1][col - 1] == true;
	}

	// is the site (row, col) full? means if the top to here percolates
	public boolean isFull(int row, int col) {
		checkLegalArguments(row);
		checkLegalArguments(col);

		// 网格的头顶点和该点的连通性
		return fullness.connected(top, calIndexOfArray(row, col));
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return openCount;
	}

	// does the system percolate?
	public boolean percolates() {
		return weightedQuickUnionUF.connected(top, bottom);
	}

	// check input index, should between 1~n
	private void checkLegalArguments(int n) {
		if (n <= 0 || n > size) {
			throw new IllegalArgumentException(String.format("Illegal argument: %d", n));
		}
	}
}
