package helper;

import entity.Empty;
import entity.Queen;

import java.util.ArrayList;
import java.util.Stack;

/***
 * 
 * Denne klassen er en hjelpe klasse som det står i navnet, den kopiere
 * forskjellige type arrayer og arraylister/ stacks samt lagert utskrift metoder
 * for dem
 * 
 * 
 * 
 * 
 */
public class Helper {

	/*
	 * Skriver ut en array, enkelt ved å legge verdien i en StringBuilder
	 */
	public static String ShowArray(int[] Array) {

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < Array.length; i++) {
			stringBuilder.append(" " + Array[i] + " ");
		}
		return stringBuilder.toString();
	}

	public static String ShowArrayList(ArrayList<Integer> Array) {

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < Array.size(); i++) {
			stringBuilder.append(" " + Array.get(i) + " ");
		}
		return stringBuilder.toString();
	}

	/*
	 * Skriver ut en Stack, enkelt ved å legge verdien i en StringBuilder
	 */
	public static String ShowStack(Stack<ArrayList<Integer>> Stack) {

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < Stack.size(); i++) {
			// stringBuilder.append(" " + ShowArrayList(Stack.get(i)) + " ");
			stringBuilder.append(" " + Stack.get(i).toString() + " ");
		}
		return stringBuilder.toString();
	}

	/*
	 * Kopierer en arrays verdier til en ny array og retunerer den
	 */
	public static int[] CopyArray(int[] Array) {

		int[] Copy = new int[Array.length];
		for (int i = 0; i < Array.length; i++) {
			Copy[i] = Array[i];
		}
		return Copy;
	}

	public static ArrayList<Integer> CopyArrayList(ArrayList<Integer> Array) {

		ArrayList<Integer> Copy = new ArrayList<Integer>();
		for (int i = 0; i < Array.size(); i++) {
			Copy.add(Array.get(i));
		}
		return Copy;
	}

	public static Object[][] CopyArray(Object[][] Array) {

		int Size = Array.length;
		Object[][] Copy = new Object[Size][Size];
		for (int x = 0; x < Array.length; x++) {
			for (int y = 0; y < Array[x].length; y++) {
				Copy[x][y] = Array[x][y];
			}
		}
		return Copy;
	}

	/*
	 * Kopierer en stack verdier til en ny Stack og retunerer den(kopiere int[]
	 * først
	 */
	public static Stack<ArrayList<Integer>> CopyStack(
			Stack<ArrayList<Integer>> Stack) {

		Stack<ArrayList<Integer>> Copy = new Stack<ArrayList<Integer>>();
		for (int i = 0; i < Stack.size(); i++) {
			ArrayList<Integer> Temp = CopyArrayList(Stack.get(i));
			Copy.add(Temp);
		}
		return Copy;
	}

	public static String toQueen(Object[][] Array) {

		StringBuilder stringBuilder = new StringBuilder();
		for (int x = 0; x < Array.length; x++) {

			stringBuilder.append("\n");
			for (int y = 0; y < Array[x].length; y++) {

				if (Array[x][y] instanceof Queen) {
					Queen queen = (Queen) Array[x][y];
					stringBuilder.append(" " + queen.toPuzzle() + " ");

				}

			}
		}
		return stringBuilder.toString();

	}

	public static String toString(Object[][] Array, int task) {

		StringBuilder stringBuilder = new StringBuilder();
		for (int x = 0; x < Array.length; x++) {

			stringBuilder.append("\n");
			for (int y = 0; y < Array[x].length; y++) {

				if (Array[x][y] instanceof Empty) {
					Empty Empty = (Empty) Array[x][y];
					if (task == 1) {
						stringBuilder.append(" " + Empty.toString() + " ");
					} else {
						stringBuilder.append(" " + Empty.toAttack() + " ");
					}
				} else {
					Queen Queen = (Queen) Array[x][y];
					if (task == 1) {
						stringBuilder.append(" " + Queen.toString() + " ");
					} else {
						stringBuilder.append(" " + Queen.toAttack() + " ");
					}
				}

			}
		}
		return stringBuilder.toString();

	}

	/**
	 * 
	 * Denne metoden går gjenneom hele arrayen og summerer alle mulige angrepene
	 * hver eneste dronning har
	 * Så hvis Attacks er like 0 vil det si at ingen av dronningene er i position til å angripe hverandre
	 */
	public static int GetCSP(Object[][] board) {
		int Attacks = 0;
		for (int x = 0; x < board.length; x++) {
			F2: for (int y = 0; y < board[x].length; y++) {
				if (board[x][y] instanceof Queen) {
					
					Queen Queen = (Queen) board[x][y];
					Attacks += Queen.Attacks;
					//Hopper ut fordi vi vet det er EN dronning PER RAD. (Kanskje vi sparer litt tid?)
					break F2;
				}
			}
		}
		return Attacks;
	}

	public static void show(String s) {
		System.out.println(" " + s);
	}

}
