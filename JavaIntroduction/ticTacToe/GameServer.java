package ticTacToe;

public class GameServer {
    private final boolean trace;
    private final Player player1;
    private final Player player2;

    public GameServer(final boolean trace, final Player player1, final Player player2) {
        this.trace = trace;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(final ServerBoard board) {
        while (true) {
            final Result result1 = turn(board, player1, "First");
            if (result1 == Result.WIN) {
                return 1;
            } else if (result1 == Result.DRAW) {
                return 0;
            }
            final Result result2 = turn(board, player2, "Second");
            if (result2 == Result.WIN) {
                return 2;
            } else if (result2 == Result.DRAW) {
                return 0;
            }
        }
    }

    private Result turn(final ServerBoard board, final Player player, final String name) {
        if (trace) {
            log(name + " player's move");
            log(board.toString());
        }
        final PlayerBoard playerBoard = board.getPlayerBoard();
        Move move;
        do {
            move = player.move(playerBoard, board.getTurn());
        } while (!playerBoard.isValidMove(move, board.getTurn()));
        log(name + " player move: " + move);
        final Result result = board.move(move);
        if (result != Result.UNDEFINED) {
            log(name + " player " + result);
            log(board.toString());
        }
        return result;
    }

    private void log(final String message) {
        if (trace) {
            System.out.println(message);
        }
    }
}
