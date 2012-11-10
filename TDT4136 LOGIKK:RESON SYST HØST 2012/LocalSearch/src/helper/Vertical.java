package helper;

import entity.Queen;

public class Vertical {

	public int CheckVertical(Object[][] board, int x, int y) {

		int Attacks = 0;
		int TempX = x;
		int TempY = y;
		while (TempX < board.length) {
			if (board[TempX][TempY] instanceof Queen) {
				Attacks++;
			}
			TempX++;

		}
		TempX = x;
		while (TempX > -1) {
			if (board[TempX][TempY] instanceof Queen) {
				Attacks++;
			}
			TempX--;
		}
		if (board[x][y] instanceof Queen) {
			Attacks -= 2;
		}
		return Attacks;
	}

}
