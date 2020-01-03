import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] grid;
    private int sz;
    private WeightedQuickUnionUF uf;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        grid = new int[n + 1][n + 1];
        sz = n;
        openSites = 0;
        uf = new WeightedQuickUnionUF((n * n) + 2);
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > sz)
            throw new IllegalArgumentException();
        if (col < 1 || col > sz)
            throw new IllegalArgumentException();
        if (isOpen(row, col))
            return;
        grid[row][col] = 1;
        openSites++;
        // Top row should union to shadow point above row
        if (row == 1) {
            uf.union(0, col);
        }
        // Bottom row should union to shadow point below row
        if (row == sz) {
            uf.union((sz * sz) + 1, (row * sz) - (sz - col));
        }
        // Union current site to adjacent open sites
        try {
            if (isOpen(row - 1, col)) {
                uf.union((row * sz) - (sz - col), ((row - 1) * sz) - (sz - col));
            }
        }
        catch (IllegalArgumentException e) {
            // do nothing
        }
        try {
            if (isOpen(row, col - 1)) {
                uf.union((row * sz) - (sz - col), (row * sz) - (sz - col + 1));
            }
        }
        catch (IllegalArgumentException e) {
            // do nothing
        }
        try {
            if (isOpen(row + 1, col)) {
                uf.union((row * sz) - (sz - col), ((row + 1) * sz) - (sz - col));
            }
        }
        catch (IllegalArgumentException e) {
            // do nothing
        }
        try {
            if (isOpen(row, col + 1)) {
                uf.union((row * sz) - (sz - col), (row * sz) - (sz - col - 1));
            }
        }
        catch (IllegalArgumentException e) {
            // do nothing
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > sz)
            throw new IllegalArgumentException();
        if (col < 1 || col > sz)
            throw new IllegalArgumentException();
        return (grid[row][col] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > sz)
            throw new IllegalArgumentException();
        if (col < 1 || col > sz)
            throw new IllegalArgumentException();
        // Site will be full if connected to shadow top site
        return uf.connected(0, (row * sz) - (sz - col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, (sz * sz) + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
