package localSearch;

import helper.Helper;

public class Play {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CSP CSP = new CSP(16);
		long begin = System.currentTimeMillis();
	    CSP.RunLocalSearchCSP(CSP.Chessboard);
	    long end = System.currentTimeMillis();
		Helper.show("\nThis operations took: " + (end - begin) + " ms");

	}

}
