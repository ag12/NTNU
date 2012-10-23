package task2;

import java.util.ArrayList;

public class Board extends ArrayList<Node> {

	private static final long serialVersionUID = 1L;

	public static final char BLACK = 'B';
	public static final char RED = 'R';
	public static final char EMPTY = ' ';

	public State state;

	public float f;
	public float g;
	public float h;

	public Board parent;


	public boolean checked;

	public float getF(Board current) {
		
		h = getHAlterTwo(current);
		f = g + h;
		return f;
	}

	public float getHAlterOne(/* Board current, */Board goal) {

		float temp = this.size();
		for (int index = 0; index < this.size(); index++) {

			if (this.get(index).c == goal.get(index).c) {

				temp--;
			}

		}
		this.h = temp;
		return h;

	}

	private float getHAlterTwo(Board current) {

		float temp = 50;

		for (int index = 0; index < current.size(); index++) {

			if (current.get(index).c == BLACK) {

				temp -= index;
			}
			if (current.get(index).c == RED) {

				temp += index;
			}
		}

		h = temp;
		return h;

	}

	/*
	 * Dette er klassen som inneholder en enkelt liste med noder f.eks A A A B B
	 * B
	 */
	public Board onCreate(int board_size, boolean startNodes) {

	
		Board board = this.create(board_size, startNodes);
		board.state = new State(board);
		return board;
	}

	/*
	 * Denne metoden retunere en ArrayList av noder, som representerer start
	 * eller goal
	 * 
	 * f.eks listOfNode(6, true) ==> BBB RRR
	 * 
	 * listOfNode(6, false) ==> RRR BBB
	 */
	public Board create(int board_size, boolean startNodes) {

		Board board = new Board();

		int size = board_size % 2;
		if (size != 0 || board_size < 2) {
			System.out.println(board_size + " is a invalid integer");
			return board;
		}

		size = (board_size / 2) * 2 + 1;
		/*System.out.print("A board for " + (size - 1) + " checkers is init");
		System.out.print("\n" + (size / 2) + " checkers are red(R) and "
				+ (size / 2) + " are Black(B)\n");*/

		for (int i = 1; i <= size; i++) {
			Node node = new Node();

			if (i <= size / 2) {
				// LEFT SIDE
				node.empty = false;
				if (startNodes) {
					node.c = BLACK;
				} else if (!startNodes) {
					node.c = RED;
				}

			} else if (i == (size / 2) + 1) {
				// MIDDLE
				node.empty = true;
				node.c = EMPTY;

			} else {
				// RIGHT SIDE
				node.empty = false;
				if (startNodes) {
					node.c = RED;
				} else if (!startNodes) {
					node.c = BLACK;
				}

			}
			board.add(node);
		}
		return board;
	}

	/*
	 * retunerer egentlig bare toString metoden som viser characterenes verdi
	 */
	public String id() {

		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < this.size(); index++) {
			sb.append(this.get(index).c);
		}
		return sb.toString();
	}

	/*
	 * Denne metoden sjekker om to arraylister er like eller ikke
	 */
	public boolean areTheyEqualBoard(/* Board startList, */Board goalList) {

		ArrayList<Boolean> trueStory = new ArrayList<Boolean>();
		for (int index = 0; index < this.size(); index++) {
			if (this.get(index).c == goalList.get(index).c) {
				trueStory.add(true);
			} else {
				trueStory.add(false);
			}
		}
		int counter = 0;
		for (Boolean trueItems : trueStory) {
			if (trueItems) {
				counter++;
			}
		}
		return counter == this.size();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#toString()
	 * 
	 * Bytter to elementert fra ArrayLista.
	 */

	public Board swap(Board board, int from, int to) {

		Board tempBoard = (Board) board.clone();

		Node fromNode = tempBoard.get(from);

		Node toNode = tempBoard.get(to);

		tempBoard.set(to, fromNode);

		tempBoard.set(from, toNode);

		return tempBoard;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#toString()
	 * 
	 * legger til [ ] for hver node
	 */
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.size(); i++) {

			sb.append(" [ " + this.get(i).c + " ] ");
		}
		return sb.toString();
	}

}
