

import javax.swing.tree.TreeNode;
import java.util.*;

public class Board {
    private int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // board = new int[]
        int row = tiles.length;
        int col = tiles[0].length;
        if (row != col) {
            throw new UnsupportedOperationException();
        }
        board = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = tiles[i][j];
            }
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(board.length + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++)
                s.append(String.format("%2d ", board[i][j]));
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    private int xyToid(int row, int col) {
        return row * dimension() + col + 1;
    }
    public int hamming() {
        int count = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (i * dimension() + j + 1 >= dimension() * dimension()) break;
                if (board[i][j] != i * dimension() + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    private int[] getIndex(int n) {
        int[] res = new int[2];
        // the position of x
        res[0] = (n-1) / dimension();
        res[1] = (n-1) % dimension();
        return res;
    }
    public int manhattan() {
        int manh = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                int row = (board[i][j] - 1) / dimension();
                int col = (board[i][j] - 1) % dimension();
                manh += Math.abs(row - i) + Math.abs(col - j);
            }
        }
        return manh;
    }


    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j =0 ; j < dimension(); j++) {
                if (board[i][j] != xyToid(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board newOne = (Board) y;
        if (newOne.dimension() != this.dimension()) {
            return false;
        }
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (newOne.board[i][j] != this.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> res = new ArrayList<Board>();
        int[][] dirs = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] == 0) {
                    for (int[] dir : dirs) {
                        Board b = this;
                        swap(i, j, i+dir[0], j+dir[1], b);
                        res.add(b);
                    }
                }
            }
        }
        return res;
    }
    private void swap(int row, int col, int x, int y, Board b) {
        if (x < 0 || x >= dimension() || y < 0 || y >= dimension()) {
            return;
        }
        int temp = b.board[row][col];
        b.board[row][col] = board[x][y];
        b.board[x][y] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int a = 1;
        while (board[(a - 1) / dimension()][(a - 1) % dimension()] == 0 && a < dimension() * dimension()) {
            a++;
        }
        int b = a + 1;
        while (board[(b - 1) / dimension()][(b - 1) % dimension()] == 0 && b < dimension() * dimension()) {
            b++;
        }
        int rowa = (a - 1) / dimension(), cola = (a - 1) % dimension(), rowb = (b - 1) / dimension(), colb = (b - 1) % dimension();
        Board twin = new Board(board);
        twin.swap(rowa, cola, rowb, colb, twin);
        return twin;
    }

    // unit testing (not graded)
    class Solution {
        List<TreeNode> list = new LinkedList<>();
        public void flatten(TreeNode root) {
            if (root == null) return;
            list.add()
                    list.
        }
    }
        /*private String reverse(String s) {
            char[] chars = s.toCharArray();
            int lo = 0;
            int hi = s.length();
            while (lo < hi) {
                char c = chars[lo];
                chars[lo] = chars[hi];
                chars[hi] = c;
                lo++;
                hi--;
            }
            String res = "";
            for (char c : chars) {
                res += c;
            }
            return res;*/


    }



}
