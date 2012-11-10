package helper;

import entity.Empty;
import entity.Queen;

public class Horizontal {

	public int CheckHorizontal(Object[][] board, int x, int y) {
		int Attacks = 0;
		int TempX = x;
		int TempY = y;

		while (TempY < board.length) {
			if (board[TempX][TempY] instanceof Queen) {
				Attacks++;
			}
			TempY++;

		}
		TempY = y;
		while (TempY > -1) {
			if (board[TempX][TempY] instanceof Queen) {
				Attacks++;
			}
			TempY--;
		}
		if (board[x][y] instanceof Queen) {
			Attacks -= 2;
		}
		if (board[x][y] instanceof Empty) {
			Attacks--;
		}

		return Attacks;
	}

}
