package ticTacToe;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SequencePlayer implements Player {
    @Override
    public Move move(final PlayerBoard board, final Cell cell) {
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                final Move move = new Move(r, c);
                if (board.isValidMove(move, cell)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }
}
