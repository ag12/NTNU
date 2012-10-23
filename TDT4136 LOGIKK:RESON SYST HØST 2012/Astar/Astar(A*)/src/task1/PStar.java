package task1;

import help.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PStar {

	
	/*
	 * Denne klassen inneholder mange av de metodene 
	 * task2.Astar inneholder. og burde egentlig blitt slått til en og samme klasse
	 * på grunn av tid var ikke dette mulig 
	 * 
	 */
	
	
	
	
	
	
	
	
	
	
	public Puzzle root;
	public Puzzle goal;

	public boolean foundGoal;

	public ArrayList<Puzzle> openList;

	public ArrayList<Puzzle> closeList;

	public HashMap<Puzzle, Puzzle> nodeTable;

	public PStar() {

		root = new Puzzle();
		goal = new Puzzle();

		foundGoal = false;
		openList = new ArrayList<Puzzle>();
		closeList = new ArrayList<Puzzle>();
		nodeTable = new HashMap<Puzzle, Puzzle>();

	}

	public void search(float n, float d) {

		root = root.onCreate();
		goal = goal.onCreateManually(n, d);
		runAStar(root, goal);

	}

	public void runAStar(Puzzle start, Puzzle goal) {

		openList.add(start);
		start.g = 0;
		start.getF(goal);

		while (!openList.isEmpty()) {

			Puzzle current = getLowestPuzzle(openList);

			// Is this our goal??
			if (current.areTheyEqualPuzzle(goal)) {

				reconstructPath(current);
				return;
			}

			openList.remove(current);
			closeList.add(current);

			ArrayList<Puzzle> childeren = this.getPossibleChilderen(current);
			for (Puzzle child : childeren) {

				if (closeList.contains(child)) {
					continue;
				}
				float temp = current.g + child.g;// eller current.g + 1 or ..

				if (!openList.contains(child) || temp < child.g) {

					nodeTable.put(child, child.parent);
					child.g = temp;
					child.getF(goal);

					if (!openList.contains(child)) {
						openList.add(child);
					}

				}
			}
		}
	}

	public Puzzle getLowestPuzzle(ArrayList<Puzzle> puzzles) {

		float temp = Float.MAX_VALUE;
		Puzzle lowestPuzzle = new Puzzle();
		for (Puzzle item : puzzles) {
			if (item.getF(goal) < temp) {

				temp = item.getF(item);
				lowestPuzzle = item;

			}
		}
		return lowestPuzzle;
	}

	public ArrayList<Puzzle> getPossibleChilderen(Puzzle cloneRoot) {

		
		
		/*
		 * 
		 * Dette er ikke i bruk nå. virker som det tar lengere tid å swappe tall
		 * 
		 * ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>();
		 * 
		 * Puzzle tempPuzzle = (Puzzle) cloneRoot.clone();
		 * 
		 * Puzzle newPuzzle = new Puzzle(cloneRoot);
		 * 
		 * newPuzzle = newPuzzle.onCreate();
		 * 
		 * tempPuzzle = tempPuzzle.swapeTwoNumbers(tempPuzzle);
		 * tempPuzzle.parent = cloneRoot;
		 * 
		 * if (newPuzzle.getF(goalPuzz) < tempPuzzle.getF(goalPuzz)) {
		 * 
		 * puzzles.add(newPuzzle); return puzzles;
		 * 
		 * }
		 * 
		 * puzzles.add(tempPuzzle);
		 * 
		 * return puzzles;
		 */

		Puzzle tempPuzzle = new Puzzle();
		tempPuzzle = tempPuzzle.onCreate();
		tempPuzzle.parent = cloneRoot;
		ArrayList<Puzzle> p = new ArrayList<Puzzle>();
		p.add(tempPuzzle);
		return p;

	}

	//Går gjennom alle foreldre til puzzle, de blir satt i getPossibleChilderen
	public void reconstructPath(Puzzle puzzle) {

		ArrayList<Puzzle> path = new ArrayList<Puzzle>();
		while (puzzle.parent != null) {
			path.add(puzzle);
			puzzle = puzzle.parent;
		}

		Collections.reverse(path);

		Helper.showInConsole("\nDone after " + path.size() + " rounds." );
		//Helper.showList(path);
	}


}
