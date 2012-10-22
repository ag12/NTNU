package help;

import java.util.ArrayList;

public class Helper {

	/***
	 * Denne klassen skriver til console
	 * 
	 */
	
	
	
	
	
	public static <T> void showList(ArrayList<T> list) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append("\n").append(list.get(i).toString());
		}
		System.out.println(sb.toString());

		System.out.println("\nThis took " + list.size() + " rounds...");
	}

	public static void showInConsole(String output) {
		System.out.println(output);
	}
}
