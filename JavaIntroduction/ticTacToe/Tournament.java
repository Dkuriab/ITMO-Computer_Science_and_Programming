package ticTacToe;

import java.util.*;

public class Tournament {

    private GameServer[][] tournament;
	private boolean isLog;
    private List<Player> list;
    private GameType settings;
	private int circles;
	private int numberOfPlayers;
	private boolean showEveryCircle;
	private int[] player_results;
	private int[][] finishtable;

    public Tournament(GameType settings, int circles, List<Player> list, boolean isLog, boolean showEveryCircle) {
        this.isLog = isLog;
        this.list = list;
        this.settings = settings;
        this.circles = circles;
        this.numberOfPlayers = list.size();
        this.showEveryCircle = showEveryCircle;
        finishtable = new int[numberOfPlayers][numberOfPlayers];
        player_results = new int[numberOfPlayers];
        tournament  = new GameServer[numberOfPlayers][numberOfPlayers];  
          
        for (int i = 0; i < numberOfPlayers; i++) {
            for (int j = 0; j < i; j++) {
                tournament[i][j] = new GameServer(isLog, list.get(i), list.get(j));
            }
        }
    }

    public String getResOf(int l) {
    	return (l < numberOfPlayers) ? ("Player " + l  + " earned " + player_results[l] + " points") : "Invalid num";
    }

    public String getAllRes() {
    	StringBuilder results = new StringBuilder();
    	for (int i = 0; i < numberOfPlayers; i++) {
    		results.append("Player " + i  + " earned " + player_results[i] + " points" + "\n");
    	}
    	return results.toString();
    }

    public void resCircle (int[][] table) {

    for (int i = 0; i < numberOfPlayers; i++) {
            for (int j = 0; j < numberOfPlayers; j++) {
                if (j != i) {
                    System.out.print(table[i][j] + " ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println();  
        }
		System.out.println();
	}

    public void play() {

        for (int c = 0; c < circles; c++) { 
        int[][] table = new int[numberOfPlayers][numberOfPlayers];
	        for (int i = 0; i < numberOfPlayers; i++) {
	            for (int j = 0; j < i; j++) {
	                int result = tournament[i][j].play(settings.getBoard());
	                switch (result) {
                        case 0: {
                            table[i][j] += 1;
                            finishtable[i][j] += 1;
                            table[j][i] += 1;
                            finishtable[j][i] += 1;
                            break;
                        }
	                    case 1: {
	                    	finishtable[i][j] += 3;
	                        table[i][j] += 3;
	                        break;
	                    }
	                    case 2: {
	                    	finishtable[j][i] += 3;
	                        table[j][i] += 3;
	                        break;
	                    }
	                }
	            }
	        }
	        if (showEveryCircle) {
	        	resCircle(table);
	        } 
	    }

	    System.out.println("Final Result"); 
	    resCircle(finishtable);

        

        for (int i = 0; i < numberOfPlayers; i++) {
            int res = 0;
            for (int k = 0; k < numberOfPlayers; k++) {
                res += finishtable[i][k];
            }
            player_results[i] = res;
        }
    }
}