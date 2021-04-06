package ticTacToe;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final PlayerBoard board, final Cell cell) {
        Move move;
        do {
            move = new Move(random.nextInt(board.getHeight()), random.nextInt(board.getWidth()));
        } while (!board.isValidMove(move, cell));
        return move;
    }
}
