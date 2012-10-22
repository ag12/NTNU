package task1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import task2.Node;

public class Puzzle extends ArrayList<Node> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public float g;
	public float h;
	public float f;
	public Puzzle parent;

	public Puzzle() {
	}

	public Puzzle(float n, float d) {

	}

	public Puzzle(Puzzle parent){this.parent = parent;}

	public Puzzle onCreateManually(float n, float d) {

		Node node = new Node(n, d);
		Puzzle puzzle = new Puzzle();
		puzzle.add(node);
		System.out.println("\nA 'manual' puzzleboard has been created\n\n"
				+ puzzle.toString() + " this is our goal....\n");
		return puzzle;

	}

	public Puzzle onCreate() {

		List<Integer> numbers = new ArrayList<Integer>();
		int[] numbersToAdd = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random random = new Random();
		for (int i = 0; i < numbersToAdd.length; i++) {
			numbers.add(numbersToAdd[i]);
		}
		// numerator
		String numeratorString = "";
		for (int i = 0; i < 4; i++) {

			int index = random.nextInt(numbers.size());
			int r = numbers.get(index);
			numeratorString += String.valueOf(r);
			numbers.remove(index);
		}

		String denominatorString = "";
		for (int i = 0; i < 5; i++) {

			int index = random.nextInt(numbers.size());
			int r = numbers.get(index);
			denominatorString += String.valueOf(r);
			numbers.remove(index);
		}
		// 1/8 == 3187/25496
		Puzzle puzzle = new Puzzle();
		Node node = new Node();
		node.n = Integer.parseInt(numeratorString);
		node.d = Integer.parseInt(denominatorString);
		puzzle.add(node);
		// System.out.println("A 'random' puzzleboard has been created\n" +
		// puzzle.toString());
		return puzzle;
	}

	public float getF(Puzzle goal) {

		h = setH(goal);
		f = g + h;
		return f;

	}

	private float setH(Puzzle goal) {

		float distance = convertToStandardDecimal(Math.abs((this
				.getN_DividedD() - goal.getN_DividedD())));
		return distance;

	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.size(); i++) {

			sb.append(" [ " + this.get(i).n + " ] ").append(" / ")
					.append(" [ " + this.get(i).d + " ] ").append(" == > ")
					.append(getN_DividedD());
		}
		return sb.toString();
	}

	public float getN_DividedD() {
		return convertToStandardDecimal(this.get(0).n / this.get(0).d);
	}

	public boolean areTheyEqualPuzzle(Puzzle goal) {
		boolean yes = this.getN_DividedD() == goal.getN_DividedD();

		if (yes) {
			System.out.println(this.toString());// + " Goal  " + goal.toString());
		}

		return yes;
	}

	private float convertToStandardDecimal(float value) {

		DecimalFormat df = new DecimalFormat("#.###");
		return Float.valueOf(df.format(value));
	}

	public Puzzle swapeTwoNumbers(Puzzle puzzleRoot) {

		Puzzle puzzle = (Puzzle) puzzleRoot.clone();
		Puzzle currentPuzzle = new Puzzle();
		Node currentNode = new Node();
		int n = (int) puzzle.get(0).n;
		int d = (int) puzzle.get(0).d;
		String numeratorString = String.valueOf(n);
		String denominatorString = String.valueOf(d);

		Random random = new Random();

		String first = "", last = "";

		boolean done = false;

		for (int i = 0; i < 2; i++) {

			int index = random.nextInt(numeratorString.length());

			if (i == 0) {

				first += numeratorString.charAt(index);
				last += denominatorString.charAt(index);

			}

			else {

				if (numeratorString.charAt(index) != first.toCharArray()[0]) {
					first += numeratorString.charAt(index);
					done = true;
				}
				if (numeratorString.charAt(index) == first.toCharArray()[0]) {
					done = false;
				}
				if (denominatorString.charAt(index) != last.toCharArray()[0]) {
					last += denominatorString.charAt(index);
					done = true;
				}
				if (denominatorString.charAt(index) == last.toCharArray()[0]) {
					done = false;
				}
			}

		}

		if (done) {

			numeratorString = numeratorString.replace(first.toCharArray()[0],
					last.toCharArray()[0]);
			numeratorString = numeratorString.replace(first.toCharArray()[1],
					last.toCharArray()[1]);

			denominatorString = denominatorString.replace(
					last.toCharArray()[0], first.toCharArray()[0]);
			denominatorString = denominatorString.replace(
					last.toCharArray()[1], first.toCharArray()[1]);

			n = Integer.parseInt(numeratorString);
			d = Integer.parseInt(denominatorString);
			currentNode.n = n;
			currentNode.d = d;
			currentPuzzle.add(currentNode);
			return currentPuzzle;
		}

		if (!done) {
			swapeTwoNumbers(puzzle);
		}
		return currentPuzzle;

	}
}
