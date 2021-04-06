package ticTacToe;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ServerBoard {
    PlayerBoard getPlayerBoard();
    Cell getTurn();
    Result move(Move move);
}
