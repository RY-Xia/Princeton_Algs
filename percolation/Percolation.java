import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int vTop;
    private int vBottom;
    private int numberOfopenSites;
    private boolean[][] grid;
    private int size;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF backwash;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        grid = new boolean[n][n];
        size = n;
        numberOfopenSites = 0;
        vTop = n * n;
        vBottom = n * n + 1;
        uf = new WeightedQuickUnionUF(n*n+2);
        backwash = new WeightedQuickUnionUF(n*n+1);
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                // false means the site is blocked
                grid[r][c] = false;
            }
        }
        // then we need to union the first line with vTop and last line with vBottom
        /* for (int col = 0; col < n; col++) {
            backwash.union(vTop, id(0, col));
            uf.union(vTop, id(0, col));
        }
        for (int col = 0; col < n; col++) {
            uf.union(id((n-1), col), vBottom);
        }

         */
    }
    // first of all , need to check if the row and col are valid
    private boolean isValid(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size){
            return false;
        }
        return true;
    }
    private int id(int row, int col){
        return size * row + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        numberOfopenSites++;
        grid[row][col] = true;
        int id = id(row, col);

        if (row == 0) {
            uf.union(vTop, id);
            backwash.union(vTop, id);
        }
        if (row == size-1) {
            uf.union(vBottom, id);
        }
        // 检查四个方向的site是否打开，如果打开，那么union他们（注意validate）
        int[] dx = { 0, 0, -1, 1};
        int[] dy = { 1, -1, 0, 0};
        for (int i = 0; i < 4; i++) {
            int rowNB = row + dx[i];
            int colNB = col + dy[i];
            if (!isValid(rowNB, colNB)) { continue;}
            if (isOpen(rowNB, colNB)) {
                int newId = id(rowNB, colNB);
                uf.union(id, newId);
                backwash.union(id, newId);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException();
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException();
        }
        if (!isOpen(row, col)) {
            return false;
        }
        return backwash.find(id(row, col)) == backwash.find(vTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {return numberOfopenSites;}

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(vTop) == uf.find(vBottom)) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation system = new Percolation(4);
        system.open(0, 0);
        system.open(1, 0);
        system.open(1, 1);
        system.open(2, 1);
        system.open(3, 3);
        System.out.println(system.percolates());

    }

}