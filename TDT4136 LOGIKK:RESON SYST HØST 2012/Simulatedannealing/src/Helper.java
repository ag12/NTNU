import java.util.Random;

public class Helper {

	/*
	 * Denne metoden tar imot en int[][] og retunerer en copy av den ved å gå
	 * gjennom alle kordinatene i arrayen og overføre det til en ny array
	 */
	public int[][] CopyMaskin(int[][] board, int n, int m) {

		int[][] copy = new int[n][m];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				copy[x][y] = board[x][y];
			}
		}
		return copy;
	}

	/*
	 * Denne metoden tar imot et "komplett" carton(int[n][m] med ant egg) 
	 * først så lages det en kopi av denne. 
	 * Så velges det ut to forskjellige random x'er, disse representere de 
	 * x radene, så hvis x = 0; så henter vi ut 0'te rad, så bytter vi ut rad 
	 * x med x2, der etter velger to random y'er og putter tilbake disse to radene i de 
	 * nye radene som vi har velgt ut random. 
	 * Da dette kan/vil sannsynlighvis føre til endring i eggs/k så går vi gjennom arrayen for 
	 * å sørge for at det er rinktig antall egg i den.
	 */
	public int[][] RandomizeCarton(int[][] OldBoard, int n, int m, int eggs) {

		int[][] newBoard = CopyMaskin(OldBoard, n, m);

		Random random = new Random();
		int x = 0;
		int x2 = 0;
		int y = 0;
		int y2 = 0;
		
		while (x == x2) {
			x = random.nextInt(n);
			x2 = random.nextInt(n);
		}
		while (y == y2) {
			y = random.nextInt(m);
			y2 = random.nextInt(m);
		}
		int[] firstRow = newBoard[x];
		int[] secondRow = newBoard[x2];
		int[] temp = firstRow;
		firstRow = secondRow;
		secondRow = temp;
		for (int i = 0; i < newBoard.length; i++) {
			newBoard[i][y] = secondRow[i];
		}
		for (int i = 0; i < newBoard.length; i++) {
			newBoard[i][y2] = firstRow[i];
		}

		int innEggs = getEggs(newBoard);

		// vi må fjerne egg
		if (innEggs > eggs) {


			while (innEggs > eggs) {

				for (int i = 0; i < newBoard.length; i++) {
					for (int j = 0; j < newBoard[i].length; j++) {

						if (newBoard[i][j] == 1 && innEggs > eggs) {
							newBoard[i][j] = 0;
							innEggs--;
						}
					}
				}
			}

		}
		// vi må legge til egg
		else if (innEggs < eggs) {

			while (innEggs < eggs) {

				for (int i = 0; i < newBoard.length; i++) {
					for (int j = 0; j < newBoard[i].length; j++) {

						if (newBoard[i][j] == 0 && innEggs < eggs) {
							newBoard[i][j] = 1;
							innEggs++;
						}
					}
				}
			}

		}

		return newBoard;
	}

	/*
	 * Denne metoden sjekker antall egg som befinner seg i int[][] board
	 */
	public int getEggs(int[][] board) {

		int eggs = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1) {
					eggs++;
				}
			}
		}
		return eggs;
	}

}
