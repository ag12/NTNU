package task2;

import help.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AStar {

	private Board root;
	private Board goal;

	public ArrayList<Board> openList;

	public ArrayList<Board> closeList;

	private HashMap<Board, Board> nodeTabel;

	public boolean foundGoal;

	public AStar() {

		foundGoal = false;

		root = new Board();
		goal = new Board();

		openList = new ArrayList<Board>();
		closeList = new ArrayList<Board>();
		nodeTabel = new HashMap<Board, Board>();

	}

	//oppretter brett
	public void main(int board_size) {
		
		search(board_size);

	}

	/**
	 * 
	 * @param board_size hvor stort brettet vårt skal være
	 * starter runAstar
	 */
	public void search(int board_size) {

		// Lager startListen
		root = new Board().onCreate(board_size, true);
		// Lager goalListen
		goal = new Board().onCreate(board_size, false);
		runAstar(root, goal);

	}


	/***
	 * 
	 * @param root det vi starter med 
	 * @param goal det vi vil komme fremtil 
	 * 
	 * Metoden er laget ut fra Wikipedia http://en.wikipedia.org/wiki/A*_search_algorithm
	 * og blitt følgt systematisk
	 */
	public void runAstar(Board root, Board goal) {

		openList.add(root);
		root.g = 0;
		root.getF(root);

		System.out.print("\n" + root.toString());
		
		while (!openList.isEmpty()) {
			Board current = getLowestNode(openList);

			// Is this the goal?
			if (current.areTheyEqualBoard(goal)) {

				Helper.showInConsole("\n");

				reconstructPath(current);
				return;

			}
			openList.remove(current);

			closeList.add(current);

			ArrayList<Board> childeren = this.getPossibleChilderen(current);

			for (Board child : childeren) {

				if (closeList.contains(child)) {
					continue;
				}

				// Bruker heller barnets sin g verdi som vi kan kalkulere ved å
				// se på plasseringen, i stedenfor 1
				float temp = current.g + child.g;// findG();

				if (!openList.contains(child) || temp < child.g) {

					nodeTabel.put(child, child.parent);
					child.g = temp;
					child.getF(child);
					// showInConsole(child.f + "");

					if (!openList.contains(child)) {
						openList.add(child);
					}
				}

			}
		}// openList.isEmpty()

	}

	/**
	 * Går gjennom alle nodene og dens foreldre
	 * skriver dem ut tilslutt som svar 
	 * @param goal
	 */
	public void reconstructPath(Board goal) {

		ArrayList<Board> path = new ArrayList<Board>();

		while (goal.parent != null) {
			path.add(goal);
			goal = goal.parent;
		}

		Collections.reverse(path);
		Helper.showList(path);
	}

	/***
	 * 
	 * @param agenda dette er theopenlist der alle de nodene vi ikke har sett på ligger i 
	 * @return den minste av dem
	 */
	public Board getLowestNode(ArrayList<Board> agenda) {

		float temp = Float.MAX_VALUE;
		Board tempBoard = null;
		for (Board item : agenda) {

			if (item.getF(item) < temp) {

				temp = item.getF(item);
				tempBoard = item;
			}
		}
		return tempBoard;
	}
	
	
	
	public void setChilderenToOpenList(Board board) {

		ArrayList<Board> tempList = getPossibleChilderen(board);
		for (Board item : tempList) {
			openList.add(item);
		}
	}

	
	
	/****
	 * 
	 * @param cloneRoot Dette er jo rooten "første gang" men som senere endrer seg.. 
	 *        Et brett kan ha fire mulige barn maks, eller 2 minst
	 *        metoden kaller på en anne metode som bytter om på rekkeføgen til nodene i brettet .
	 * 
	 * 
	 * @return
	 */
	public ArrayList<Board> getPossibleChilderen(Board cloneRoot) {

		ArrayList<Board> boards = new ArrayList<Board>();

		// Disse er de "mulige" barna en node KAN lage :)
		Board possibleChild1 = null, possibleChild2 = null, possibleChild3 = null, possibleChild4 = null;

		Board rootBoard = (Board) cloneRoot.clone();

		for (int index = 0; index < rootBoard.size(); index++) {

			if (rootBoard.get(index).empty == true) {

				if (index == 0) {

					// Child oneToRight
					possibleChild1 = new Board().swap(rootBoard, index,
							index + 1);
					possibleChild1.parent = cloneRoot;

					
					possibleChild1 = setG(possibleChild1, 1);
					
					boards.add(possibleChild1);

					// Child twoToRight
					possibleChild2 = new Board().swap(rootBoard, index,
							index + 2);
					possibleChild2.parent = cloneRoot;

					
					possibleChild2 = setG(possibleChild2, 2);
					
					boards.add(possibleChild2);

				} else if (index == 1) {

					// Child oneToLeft

					possibleChild1 = new Board().swap(rootBoard, index,
							index - 1);
					possibleChild1.parent = cloneRoot;

				
					possibleChild1 = setG(possibleChild1, 1);
					
					boards.add(possibleChild1);

					// Child oneToRight
					possibleChild3 = new Board().swap(rootBoard, index,
							index + 1);
					possibleChild3.parent = cloneRoot;
					
					
					possibleChild3 = setG(possibleChild3, 1);
					
					
					boards.add(possibleChild3);

					// Child twoToRight
					possibleChild4 = new Board().swap(rootBoard, index,
							index + 2);
					possibleChild4.parent = cloneRoot;

					
					
					possibleChild4 = setG(possibleChild4, 2);
					
					
					boards.add(possibleChild4);

				} else if (index == (rootBoard.size() - 1)) {

					// Child oneToLeft

					possibleChild1 = new Board().swap(rootBoard, index,
							index - 1);
					possibleChild1.parent = cloneRoot;
					
					
					possibleChild1 = setG(possibleChild1, 1);
					
					
					
					boards.add(possibleChild1);

					// Childe twoToLeft
					possibleChild2 = new Board().swap(rootBoard, index,
							index - 2);
					possibleChild2.parent = cloneRoot;				
					
					
					possibleChild2 = setG(possibleChild2, 0);
					
					
					boards.add(possibleChild2);

				} else if (index == (rootBoard.size() - 2)) {

					// Child oneToLeft

					possibleChild1 = new Board().swap(rootBoard, index,
							index - 1);
					possibleChild1.parent = cloneRoot;
					
					
					possibleChild1 = setG(possibleChild1, 1);
					
					
					boards.add(possibleChild1);

					// Childe twoToLeft
					possibleChild2 = new Board().swap(rootBoard, index,
							index - 2);
					possibleChild2.parent = cloneRoot;
					
					
					possibleChild2 = setG(possibleChild2, 0);
					
					
					
					boards.add(possibleChild2);

					// Child oneToRight
					possibleChild3 = new Board().swap(rootBoard, index,
							index + 1);
					possibleChild3.parent = cloneRoot;
					
					possibleChild3 = setG(possibleChild3, 1);
					
					
					boards.add(possibleChild3);

				}

				else {

					// Child oneToLeft

					possibleChild1 = new Board().swap(rootBoard, index,
							index - 1);
					possibleChild1.parent = cloneRoot;

					
					possibleChild1 = setG(possibleChild1, 1);
					
					
					boards.add(possibleChild1);

					// Childe twoToLeft
					possibleChild2 = new Board().swap(rootBoard, index,
							index - 2);
					possibleChild2.parent = cloneRoot;
					
					
					possibleChild2 = setG(possibleChild2, 0);
					
					
					boards.add(possibleChild2);

					// Child oneToRight
					possibleChild3 = new Board().swap(rootBoard, index,
							index + 1);
					possibleChild3.parent = cloneRoot;
					
					
					possibleChild3= setG(possibleChild3, 1);
					
					
					boards.add(possibleChild3);

					// Child twoToRight
					possibleChild4 = new Board().swap(rootBoard, index,
							index + 2);
					possibleChild4.parent = cloneRoot;
					
					
					possibleChild4 = setG(possibleChild4, 2);
					
					
					boards.add(possibleChild4);

				}
				return boards;
			}
		}
		return boards;
	}
	
	
	/*****
	 * 
	 * @param board en node
	 * @param where entilvenste? totilvenste? osv 
	 * @return den samme "noden"/brettet men "opptimaliserer" søket ihvertfall klarer den 6 på 15 move
	 */
	private Board setG(Board board, int where){
		
		if( where == 1){
			board.g = 5;
		}
		else if ( where == 2){
			board.g = (float) 2; 
		}
		else if( where == 0){
			board.g = (float) 0.2;
		}
		
		return board;
		
	}
}
