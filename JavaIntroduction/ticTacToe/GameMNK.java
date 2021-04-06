package ticTacToe;

import java.util.*;

public class GameMNK implements GameType {
	private int m;
    private int n;
    private int k;

	public GameMNK (int m, int n, int k){
		this.m = m;
		this.n = n;
		this.k = k;
	}

	public Board getBoard() {
		return new Board(m, n, k);
	}
}