package ticTacToe;

import java.io.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;
    private final PrintWriter out;

    public HumanPlayer(final Reader reader, final PrintWriter writer) {
        in = new Scanner(new BufferedReader(reader));
        out = writer;
    }

    public HumanPlayer() {
        this(new InputStreamReader(System.in), new PrintWriter(System.out));
    }

    public int tryScan(String a) {
    	while (!in.hasNextInt()) {
    		if (!in.hasNext()) {
    			return -1;
    		}
    		
			in.next();
			out.println("Invalid " + a + " try again...");
			out.flush();
    	}
    	return in.nextInt();
    }

    @Override
    public Move move(final PlayerBoard board, final Cell cell) {
        while (true) {
            out.println(cell + "'s move");
            out.println("Current position:");
            out.println(board);
            out.println("Enter row and column:");
            out.flush();
			
            final Move move = new Move(tryScan("row"), tryScan("column"));
            
            if (board.isValidMove(move, cell)) {
                return move;
            }

            if (!in.hasNext()) {
            	return null;
            }

            out.format("Invalid move: row=%d, column=%d%n", move.getRow(), move.getColumn());
        }
    }
}
