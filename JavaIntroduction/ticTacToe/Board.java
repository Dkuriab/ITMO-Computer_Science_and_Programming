package ticTacToe;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class Board implements PlayerBoard, ServerBoard {
    private static final Map<Cell, String> SYMBOLS = new EnumMap<>(Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "O"
    ));

    private final Cell[][] board;
    private Cell turn;
    protected int empty;
    private final int k;

    public Board(final int high, final int length, final int k) {
        this.empty = high*length;
        this.k = k;

        board = new Cell[high][length];
        for (final Cell[] row : board) {
            Arrays.fill(row, Cell.E);
        }

        turn = Cell.X;
    }

    @Override
    public int getWidth() {
        return board[0].length;
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public Cell getCell(final int row, final int column) {
        return board[row][column];
    }

    @Override
    public PlayerBoard getPlayerBoard() {
        return this;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public boolean isValidMove(final Move move, final Cell cell) {
        return checkBounds(move) && getCell(move.getRow(), move.getColumn()) == Cell.E;
    }

    private boolean checkBounds(final Move move) {
        final int row = move.getRow();
        final int col = move.getColumn();
        return 0 <= row && row < board.length && 0 <= col && col < board[0].length;
    }

    private int count(final int f, final int s, final Move move) {
        int counter = 0;

    	int m = move.getRow();
        int n = move.getColumn();
    	while (m < board.length && n >= 0 && m >= 0 && n < board[0].length && board[m][n] == turn) {
    		counter++;
    		m += f;
    		n += s;
    	}
    	return count;
	}

    private int checkWin(final int f, final int s, final Move move, boolean isFirst) {
    	return count(f, s, move) + count(-f, -s, move) - 1 >= k;
	}
  

    @Override
    public Result move(final Move move) {
        
        board[move.getRow()][move.getColumn()] = turn;
        empty--;

        if (checkWin(0, 1, move) || checkWin(1, 0, move) ||
            checkWin(1, 1, move) || checkWin(-1, 1, move)) {
            return Result.WIN;
        }
        if (empty == 0) {
            return Result.DRAW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNDEFINED;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int r = 0; r < board[0].length; r++) {
            sb.append(" " + r);
        }
        sb.append("\n");
        for (int r = 0; r < board.length; r++) {
            sb.append(r + "|");
            for (int c = 0; c < board[0].length; c++) {
                sb.append(SYMBOLS.get(board[r][c]) + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
