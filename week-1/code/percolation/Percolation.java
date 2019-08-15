package percolation;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Percolation {

    private int size;
    private int openCount;
    // 1 if the site on the coordinate is opened 
    private int[][] grid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        size = n;
        openCount = 0;
        grid = new int[n + 1][n + 1];
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkLegalArguments(row);
        checkLegalArguments(col);

        if (grid[row][col] == 0) {
            grid[row][col] = 1;
            openCount++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkLegalArguments(row);
        checkLegalArguments(col);

        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkLegalArguments(row);
        checkLegalArguments(col);

        return openCount == size * size;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        // todo: 1. 网格的头和尾增加一个顶点 2. 检验头和尾的连通性
        return true;
    }

    private void checkLegalArguments(int n) {
        if (n <= 0 || n > size) {
            throw new IllegalArgumentException(String.format("Illegal argument: %d", n));
        }
    }
}
