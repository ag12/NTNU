package entity;
import java.util.ArrayList;
import java.util.Stack;
import helper.Helper;
public class Queen {

	public int Row;
	public int Column;
	public ArrayList<Integer> Domain;
	public Stack<ArrayList<Integer>> Conflicts;
	public int Attacks;

	public Queen() {
	}

	public Queen(int row, int column, int[] domain, Stack<int[]> conflicts) {
	}
	public Queen(int row, int column) {
		Row = row;
		Column = column;
	}

	public Queen(int row, int column, int size, boolean isInit) {

		Row = row + 1;
		Column = column + 1;
		Domain = new ArrayList<Integer>();
		Conflicts = new Stack<ArrayList<Integer>>();
		Conflicts.add(new ArrayList<Integer>());
		InPlaced(size);

	}

	public Queen(int row, int column, int size) {

		Row = row + 1;
		Column = column + 1;
		Domain = new ArrayList<Integer>();
		Conflicts = new Stack<ArrayList<Integer>>();
		InPlaced(size);

	}

	public void QueenIsSet(int r) {

	}

	public void InPlaced(int size) {

		Domain.add(Column);
		ArrayList<Integer> Board = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			if ((i + 1) != Column) {
				Board.add((i + 1));
			}
		}
		Conflicts.add(Board);

	}

	public ArrayList<Integer> AtStart(int column) {

		ArrayList<Integer> domain = new ArrayList<Integer>();
		domain.add(column);
		return domain;
	}

	public Object[] RomoveFromDomain(int n, int[] Array) {

		int[] Copy = Helper.CopyArray(Array);
		ArrayList<Integer> CopyList = new ArrayList<Integer>();
		for (int item : Copy) {
			if (item != n) {
				CopyList.add(item);
			}
		}
		return CopyList.toArray();

	}

	public String toPuzzle() {
		return "Queen [ Row=" + Row + ", Column=" + Column + ", Domain="
				+ Domain.toString() + ", Conflicts="
				+ Helper.ShowStack(Conflicts) + "]";
	}
	public String toAttack(){
		return "Q, a:" + Attacks + " " ;//" Q, (" + Row + ", " + Column + ") A:" + Attacks + " " ;
	}

	@Override
	public String toString() {
		return " (Q) ";
	}

}
