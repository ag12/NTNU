package backTrack;

import java.util.Random;

import entity.Empty;
import entity.Queen;
import helper.Helper;

public class Backtrack {

	public int N;
	public Object[][] Chessboard;

	public Backtrack(int n) {
		N = n;
		Chessboard = OnInitialBoard(N);
		Chessboard = AfterInitialBoard(Chessboard);
		run(Chessboard);

	}

	public Object[][] OnInitialBoard(int n) {

		Object[][] Board = new Object[n][n];
		for (int x = 0; x < Board.length; x++) {
			for (int y = 0; y < Board[x].length; y++) {
				Board[x][y] = new Empty(x, y);
			}
		}
		return Board;
	}

	public Object[][] AfterInitialBoard(Object[][] chessboard) {

		Object[][] Board = Helper.CopyArray(chessboard);
		Random Random = new Random();
		int Y = Random.nextInt(N);

		Queen queen = new Queen(N - N, Y, N, true);
		Board[N - N][Y] = queen;

		return Board;
	}

	public void run(Object[][] chessboard) {

		Object[][] Board = Helper.CopyArray(chessboard);
		Queen tempQueen = null;
		 for (int x = 0; x < Board.length; x++) {
			F2: for (int y = 0; y < Board[x].length; y++) {
				if (Board[x][y] instanceof Queen) {
					tempQueen = (Queen) Board[x][y];
					break F2;
				}
			}
			if (tempQueen != null) {

			}

		}

	}

}
