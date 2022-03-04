/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    //private boolean[][] grid;
    private byte[] grid;
    private WeightedQuickUnionUF uf;
    private int len;
    //private int virtualTop;
    //private int virtualBottom;
    private int siteCount;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        //grid = new boolean[n][n];
        grid = new byte[n * n];
        len = n;
        //virtualBottom = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > len || col > len) throw new IllegalArgumentException();
        if (isOpen(row, col)) return;
        int index = getIndex(row, col);
        grid[index] = (byte) (grid[index] | 1);
        siteCount++;

        /*
        //Union virtual top
        if (row == 1) uf.union(virtualTop, getIndex(row, col));
        //Union virtual bottom
        if (row == len) uf.union(virtualBottom, getIndex(row, col));
        */
        if (row == 1) grid[index] = (byte) (grid[index] | 2);
        if (row == len) grid[index] = (byte) (grid[index] | 4);

        //Union site above
        if (row > 1 && isOpen(row - 1, col)) {
            int newRootValue = grid[uf.find(index)] | grid[uf.find(index - len)];
            uf.union(index - len, index);
            int newRoot = uf.find(index);
            grid[newRoot] = (byte) (grid[newRoot] | newRootValue);
        }
        //Union site below
        if (row < len && isOpen(row + 1, col)) {
            int newRootValue = grid[uf.find(index)] | grid[uf.find(index + len)];
            uf.union(index + len, index);
            int newRoot = uf.find(index);
            grid[newRoot] = (byte) (grid[newRoot] | newRootValue);
        }
        //Union site left
        if (col > 1 && isOpen(row, col - 1)) {
            int newRootValue = grid[uf.find(index)] | grid[uf.find(index - 1)];
            uf.union(index - 1, index);
            int newRoot = uf.find(index);
            grid[newRoot] = (byte) (grid[newRoot] | newRootValue);
        }
        //Union site right
        if (col < len && isOpen(row, col + 1)) {
            int newRootValue = grid[uf.find(index)] | grid[uf.find(index + 1)];
            uf.union(index + 1, index);
            int newRoot = uf.find(index);
            grid[newRoot] = (byte) (grid[newRoot] | newRootValue);
        }

        if (grid[uf.find(index)] == 7) {
            percolates = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > len || col > len) throw new IllegalArgumentException();
        return grid[getIndex(row, col)] % 2 != 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > len || col > len) throw new IllegalArgumentException();
        //return uf.find(virtualTop) == uf.find(getIndex(row, col));
        int index = getIndex(row, col);
        byte num = grid[uf.find(index)];
        return num == 3 || num == 7;
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return siteCount; }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    private int getIndex(int row, int col) {
        return (len * row - (len + 1 - col));
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
