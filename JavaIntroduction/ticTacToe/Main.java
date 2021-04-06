package ticTacToe;

import java.util.*;

public class Main {
	static final int M = 4;
	static final int N = 5;
	static final int K = 3;

    public static void main(String[] args) {
        Scanner mnk = new Scanner(System.in);
		final int CIRCLES = 5;
		final int NUMBER_OF_PLAYERS = 10;
		final List<Player> list = List.of(new RandomPlayer(), new RandomPlayer(), new RandomPlayer(), new HumanPlayer());

        GameType settings = new GameMNK(M, N, K);
        
        Boolean showEachCircleRes = true;
        Boolean showLogs = false;

        Tournament game = new Tournament(settings, CIRCLES, list, showLogs, showEachCircleRes);
        game.play();
         System.out.println(game.getResOf(1) + );
        System.out.println(game.getResOf(2));
        System.out.println();

        System.out.println(game.getAllRes());
    }
}
