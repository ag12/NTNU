package helper;

import entity.Queen;

public class Diagonal {

	public int CheckLeftSideDiagonal(Object[][] board, int x, int y) {

		int Attacks = 0;
		int TempX = x;
		int TempY = y;
		/*
		 * Sjekker nedover
		 */
		while (TempX < board.length && TempY < board.length) {

			if (board[TempX][TempY] instanceof Queen) {
				Attacks++;
			}
			TempX++;
			TempY++;
		}
		TempX = x;
		TempY = y;
		/*
		 * Sjekker oppover
		 */
		while (TempX > -1 && TempY > -1) {

			if (board[TempX][TempY] instanceof Queen) {
				Attacks++;
			}
			TempX--;
			TempY--;

		}
		/*
		 * hvis det er en queen i angitt (x,y) plassering så må vi trekke 2 fra
		 * Attack da i metoden ikke tas hensyn til dette, fordi vi har to
		 * while-løkker som sjekekr både oppover og nedover diagonaler vil den
		 * da telle seg selv to ganger som en mulig attacker.
		 */
		if (board[x][y] instanceof Queen) {
			Attacks -= 2;
		}
		return Attacks;
	}

	public int CheckRightSideDiagonal(Object[][] board, int x, int y) {

		int Attacks = 0;
		int TempX = x;
		int TempY = y;
		/*
		 * sjekker nedover
		 */
		while (TempX < board.length && TempY > -1) {
			if (board[TempX][TempY] instanceof Queen) {
				Attacks++;
			}
			TempX++;
			TempY--;

		}
		TempX = x;
		TempY = y;
		while (TempX > -1 && TempY < board.length) {
			if (board[TempX][TempY] instanceof Queen) {
				Attacks++;
			}
			TempX--;
			TempY++;

		}
		if (board[x][y] instanceof Queen) {
			Attacks -= 2;
		}
		return Attacks;

	}

}
