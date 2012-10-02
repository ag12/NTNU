package driver;

import java.util.Scanner;

import task1.PStar;
import task2.AStar;

public class Play {

	/***
	 * 
	 * Dette er driverklassen og starter begge oppgvene.
	 * 
	 */

	public static void showInConsole(String output) {
		
		System.out.println(output);
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		showInConsole("\n\tTask one");
		showInConsole("\n\nTast inn to tall, den f¿rste som numerator og andre som denominator.....");
		PStar puzzleStar = new PStar();
		long begin = System.currentTimeMillis();
		puzzleStar.search(scanner.nextFloat(), scanner.nextFloat());
		long end = System.currentTimeMillis();
		showInConsole("\nThis operations took: " + (end - begin) + " ms");

		while(true){
			
		
		showInConsole("\n\tTask two");
		showInConsole("\nTast inn ett partall for brettst¿rrelsen....ANBEFALSES Œ starte med 4 og ¿ke det gradvis :=)");
		AStar aStar = new task2.AStar();
		begin = System.currentTimeMillis();
		aStar.main(scanner.nextInt());
		end = System.currentTimeMillis();
		showInConsole("\nThis operations took: " + (end - begin) + " ms\n");
		}

	}

}
