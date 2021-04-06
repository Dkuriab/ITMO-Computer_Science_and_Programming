package ticTacToe;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface PlayerBoard {
    int getWidth();
    int getHeight();
    Cell getCell(int row, int column);
    boolean isValidMove(Move move, Cell cell);
}
