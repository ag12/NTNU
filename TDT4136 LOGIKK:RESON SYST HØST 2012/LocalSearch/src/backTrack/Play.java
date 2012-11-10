package backTrack;
import helper.Helper;

public class Play {

	public static void show(String s) {
		System.out.println(" " + s);
	}
	public static void main(String[] args) {


		Backtrack backtrack = new Backtrack(4);
		show(Helper.toString(backtrack.Chessboard,1));
		show(Helper.toQueen(backtrack.Chessboard));
		
	}
}
