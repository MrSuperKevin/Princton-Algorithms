package percolation;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private int openCount;
    // 1 if the site on the coordinate is opened 
    private int[][] grid;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        size = n;
        openCount = 0;
        grid = new int[n][n];
        // put one virtual point in the head and one in the tail of the array in unionFind, and connect the head with grid[0][*], connect the tail with grid[size - 1][*]
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 1; i <= n; i++) {
//            weightedQuickUnionUF.union(0, calIndexOfArray(1, i));
//            weightedQuickUnionUF.union(n * n + 1, calIndexOfArray(1, i));
        }
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkLegalArguments(row);
        checkLegalArguments(col);

        if (grid[row - 1][col - 1] == 0) {
            grid[row - 1][col - 1] = 1;
            openCount++;

            // connect sites around the new open site
            if (row > 1) {
                if (isOpen(row - 1, col))
                    weightedQuickUnionUF.union(calIndexOfArray(row, col), calIndexOfArray(row - 1, col));
            } else {
                weightedQuickUnionUF.union(calIndexOfArray(row, col), 0);
            }

            if (row < size) {
                if (isOpen(row + 1, col))
                    weightedQuickUnionUF.union(calIndexOfArray(row, col), calIndexOfArray(row + 1, col));
            } else {
                weightedQuickUnionUF.union(calIndexOfArray(row, col), size * size + 1);
            }

            if (col > 1) {
                if (isOpen(row, col - 1))
                    weightedQuickUnionUF.union(calIndexOfArray(row, col), calIndexOfArray(row, col - 1));
            }
            if (col < size) {
                if (isOpen(row, col + 1))
                    weightedQuickUnionUF.union(calIndexOfArray(row, col), calIndexOfArray(row, col + 1));
            }
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

        return grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full? means if the top to here percolates
    public boolean isFull(int row, int col) {
        checkLegalArguments(row);
        checkLegalArguments(col);

        // 网格的头顶点和该点的连通性
        return weightedQuickUnionUF.connected(0, calIndexOfArray(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, size * size + 1);
    }

    // check input index, should between 1~n
    private void checkLegalArguments(int n) {
        if (n <= 0 || n > size) {
            throw new IllegalArgumentException(String.format("Illegal argument: %d", n));
        }
    }
}
